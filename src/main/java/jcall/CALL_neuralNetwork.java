//////////////////////////////////////////////////////////////
// A neural network, with the functionality to train it, and get results from it
//
package jcall;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Vector;

public class CALL_neuralNetwork {
  // Defines
  static final double DEFAULT_SF = 2.0;
  static final int DEFAULT_CUTOFF = 99999999;
  static final double DEFAULT_MAX_LR = 5.0;
  static final double DEFAULT_MIN_LR = 0.005;
  static final double DEFAULT_LR_ADJUSTMENT = 10000;
  static final int DEBUG_FREQ = 100;
  static final double MOMENTUM_FACTOR = 0.5;

  static final int TYPE_HIDDEN = 0;
  static final int TYPE_OUTPUT = 1;

  // Network Settings
  double sigmoidFactor;
  double initialLearningRate;
  double minLearningRate;
  double minimumLearningRate;
  double momentum;
  double accuracyThreshold;
  double sre;
  double spread;

  int maxItterations;

  // The Nodes
  Vector hiddenNodes; // Vector of Vectors (Layers X Nodes Per Layer) of
                      // CALL_hiddenNode objects
  Vector outputNodes; // Vector of CALL_outputNode objects

  public CALL_neuralNetwork() {
    init();
  }

  public CALL_neuralNetwork(int out, int hidden, int layers, int in, double s) {
    CALL_neuralNetworkNode newNode;
    Vector layerVector;
    int l, h, o;

    // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.INFO,
    // "Initialising Network..");
    // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.INFO,
    // "-output nodes: "+ out);
    // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.INFO,
    // "-hidden layers: " + layers );
    // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.INFO,
    // "-hidden nodes: " + hidden);
    // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.INFO,
    // "-inputs: " + in);

    // Initialise settings
    init();

    // What spread are we using on the randomized initial weights
    spread = s;

    // Create the unitialised network
    // =========================

    // Hidden Nodes
    // ------------
    hiddenNodes = new Vector();
    for (l = 0; l < layers; l++) {
      layerVector = new Vector();
      for (h = 0; h < hidden; h++) {
        if (l == 0) {
          // First layer - has number on inputs equal to the network inputs
          newNode = new CALL_neuralNetworkNode(TYPE_HIDDEN, h, l, in, spread);
        } else {
          // Subsequent layer - has number on inputs equal the number of hidden
          // nodes in a layer
          newNode = new CALL_neuralNetworkNode(TYPE_HIDDEN, h, l, hidden, spread);
        }

        // Add node to layer
        layerVector.addElement(newNode);
      }

      // Add layer to Hidden Layer list
      hiddenNodes.addElement(layerVector);
    }

    // Output Nodes
    // ------------
    outputNodes = new Vector();
    for (o = 0; o < out; o++) {
      // Output nodes have as many inputs as there are hidden nodes in a layer
      newNode = new CALL_neuralNetworkNode(TYPE_OUTPUT, o, -1, hidden, spread);
      outputNodes.addElement(newNode);
    }
  }

  // Initialises the ANN
  private void init() {
    sigmoidFactor = DEFAULT_SF;
    maxItterations = DEFAULT_CUTOFF;
    initialLearningRate = DEFAULT_MAX_LR;
    minimumLearningRate = DEFAULT_MIN_LR;
    momentum = MOMENTUM_FACTOR;
    accuracyThreshold = 0.005;
    sre = 0.0;
  }

  // Trains the neural network based on the training file supplied
  public boolean train(String dataFile) {
    CALL_featureVectorSet dataSet;

    dataSet = new CALL_featureVectorSet(dataFile);
    if ((dataSet != null) && (dataSet.size() > 0)) {
      return (train(dataSet));
    } else {
      // Didn't get a valid data set
      return false;
    }
  }

  public boolean train(CALL_featureVectorSet dataSet) {
    CALL_featureVector dataInstance;
    double currentLearningRate, errorBasedLearningRate, useThisLearningRate;
    int inputs, outputs;

    // Load data

    if (dataSet != null) {
      // Set input and output numbers
      inputs = dataSet.numFeatures();
      outputs = dataSet.numOutputs();

      // Check outputs matches output nodes, if not, big problem!
      if (outputs != outputNodes.size()) {
        // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
        // "Output size in data set does not match that in network, aborting");
      }

      // The error measure
      sre = 50.0;

      // Run training
      for (int i = 0; i < DEFAULT_CUTOFF; i++) {
        // Calculate current learning rate based on the Initial Learning rate,
        // reduced to 0 over the total number of itterations
        if (i < maxItterations) {
          // Only update up to the maxItterations point
          currentLearningRate = minLearningRate
              + ((initialLearningRate - minLearningRate) * (1.0 - ((double) i / (double) maxItterations)));
        } else {
          // Otherwise just use the minimum rate
          currentLearningRate = minLearningRate;
        }

        // Calculate current learning rate based on current error
        // The idea being that the nearer the error gets to 0, the lower the
        // rate will become (to a point)
        errorBasedLearningRate = (currentLearningRate + sre) / 2;

        // Chose the lower of the two weights
        if (errorBasedLearningRate > currentLearningRate) {
          // Use the standard itteration based rate
          useThisLearningRate = currentLearningRate;
        } else {
          // Use the error based one
          useThisLearningRate = errorBasedLearningRate;
        }

        sre = 0.0;

        for (int j = 0; j < dataSet.size(); j++) {
          dataInstance = dataSet.getInstance(j);

          if (dataInstance != null) {
            // Check instance size
            if (dataInstance.outputSize() != outputs) {
              // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.WARN,
              // "Output size in instance [" + j +
              // "] does not match that in network, skipping");
              continue;
            }

            if (!backPropagate(dataInstance, useThisLearningRate)) {
              // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
              // "Error during backpropagation, aborting");
              return false;
            }

            // Update the error rate during this itteration
            sre += getErrorRate(dataInstance);
          }
        }

        // Debug - error rate, learning rate and itteration information
        // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.INFO,
        // "Itteration: " + i + ", Learning Rate: ["+
        // CALL_io.dblDecPlace(useThisLearningRate, 4) + "], Error Factor: [" +
        // sre + "]");

        if ((i % DEBUG_FREQ) == 0) {
          System.out.println("Itteration: " + i + ", Learning Rate: [" + CALL_io.dblDecPlace(useThisLearningRate, 4)
              + "], Error Factor: [" + sre + "]");
        }

        // Have we reached an acceptable error rate (thus quit)
        if (sre < accuracyThreshold) {
          // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.INFO,
          // "Reached acceptable accuracy (" + sre +", Threshold: " +
          // accuracyThreshold + "), breaking off training");
          break;
        }
      }
    }

    return true;
  }

  // Run one itteration of the BP algorithm for a particular data instance
  public boolean backPropagate(CALL_featureVector instance, double currentLearningRate) {
    // STAGE 1: PROPOGATE THE INSTANCE THROUGH THE NETWORK
    if (forward(instance)) {
      // STAGE 2: BACK-PROPOGATE THE ERRORS UP THROUGH THE NETWORK
      return (backward(instance, currentLearningRate));
    }

    return false;
  }

  // Propogates the instance through the network to get the output scores
  // Used in TRAINING and EVALUATION
  public boolean forward(CALL_featureVector instance) {
    Vector currentLayer;
    Vector aboveLayer = null;
    int aboveLayerSize;

    CALL_neuralNetworkNode currentNode;
    CALL_neuralNetworkNode inputNode;

    int i, h, l, o;
    int inputs, outputs;

    double input, net;

    inputs = instance.featureSize();
    outputs = instance.outputSize();

    // Within the hidden layers
    // ===========================================
    if (hiddenNodes.size() <= 0) {
      return false;
    }

    aboveLayerSize = inputs;

    for (l = 0; l < hiddenNodes.size(); l++) {
      // Input values passed into first layer of hidden nodes
      currentLayer = (Vector) hiddenNodes.elementAt(l);
      if (currentLayer.size() == 0) {
        // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
        // "Empty hidden layer, aborting");
        return false;
      } else {
        for (h = 0; h < currentLayer.size(); h++) {
          currentNode = (CALL_neuralNetworkNode) currentLayer.elementAt(h);

          if (currentNode != null) {
            // Check the input size matches the node input weights (there should
            // be one more weight than inputs)
            if (currentNode.inputWeights.size() != aboveLayerSize + 1) {
              // Incorrect number of input weights for the number of input
              // features
              // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
              // "Invalid number of input weights [" +
              // currentNode.inputWeights.size() + "] to input features [" +
              // inputs + "].  Should be features + 1");
              return false;
            }

            // This is the balancing factor - all nodes have a -1 input used
            // alongside real inputs when calculating the output
            net = currentNode.inputWeight(0) * -1;

            for (i = 0; i < aboveLayerSize; i++) {
              // Process inputs to get output - the method varies depending on
              // whether we are at the top
              // level (0) of the nodes, in which case the input is directly
              // from the instances features,
              // or at any subsequent level in which case the input is taken
              // from the above hidden node layer

              if (l == 0) {
                // Top level node
                // -------------
                input = instance.featureAt(i);
              } else {
                // Intermeddiate hidden nodes
                // ------------------------
                inputNode = (CALL_neuralNetworkNode) aboveLayer.elementAt(i);
                if (inputNode == null) {
                  // CALL_debug.printlog(CALL_debug.MOD_NEURALNET,
                  // CALL_debug.ERROR, "Missing input node - aborting");
                  return false;
                }

                input = inputNode.output;
              }

              // Comine this input with the input weight to update the output
              // (and then pass through sigmoid function)
              currentNode.inputs.setElementAt(new Double(input), i + 1);
              net += (currentNode.inputWeight(i + 1) * input);

            }

            // Apply sigmoid threshold function to the output
            currentNode.output = (1 / (1 + Math.exp(-sigmoidFactor * net)));
          }
        }
      }

      // Update the above layer size to be set to the current layer size
      aboveLayerSize = currentLayer.size();
      aboveLayer = currentLayer;
    }

    // Hidden to output nodes
    // ===========================================
    for (o = 0; o < outputNodes.size(); o++) {
      // Get the output node
      currentNode = (CALL_neuralNetworkNode) outputNodes.elementAt(o);

      if (currentNode != null) {
        // Check the input size matches the node input weights (there should be
        // one more weight than inputs)
        if (currentNode.inputWeights.size() != aboveLayerSize + 1) {
          // Incorrect number of input weights for the number of input features
          // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
          // "Invalid number of input weights [" +
          // currentNode.inputWeights.size() + "] to input features [" + inputs
          // + "].  Should be features + 1");
          return false;
        }

        // This is the balancing factor - all nodes have a -1 input used
        // alongside real inputs when calculating the output
        net = currentNode.inputWeight(0) * -1;

        // Now deal with all the other inputs (from the above hidden layer
        for (i = 0; i < aboveLayerSize; i++) {
          // Intermeddiate hidden nodes
          // ------------------------
          inputNode = (CALL_neuralNetworkNode) aboveLayer.elementAt(i);
          if (inputNode == null) {
            // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
            // "Missing input node - aborting");
            return false;
          }

          // Update output with input of this hidden node
          net += (currentNode.inputWeight(i + 1) * inputNode.output);
          currentNode.inputs.setElementAt(new Double(inputNode.output), i + 1);
        }

        // Apply sigmoid threshold to the output
        currentNode.output = (1 / (1 + Math.exp(-sigmoidFactor * net)));
      }
    }

    // Right, all the outputs should now be set, so return
    return true;
  }

  // Takes the output scores currently set, and the desired outputs in the
  // instance, and propogates the
  // error backwards up through the network, adjusting the weights as it goes
  // Used in TRAINING only
  public boolean backward(CALL_featureVector instance, double currentLearningRate) {
    int i, h, l, o;
    double target, output, newWeight, propogatedErrors, dw, weight;
    double input, dwPrev1, dwPrev2, dwPrev3;
    double momentumWeight;

    Double tempDouble;
    Vector aboveLayer, currentLayer, lowerLayer;
    CALL_neuralNetworkNode currentNode;
    CALL_neuralNetworkNode inputNode;
    CALL_neuralNetworkNode outputNode;

    // STEP 1. CALCULATE ERRORS FOR EACH NODE IN THE NETWORK
    // ===========================================================

    // First, the output node errors
    for (o = 0; o < outputNodes.size(); o++) {
      // Get the output node
      currentNode = (CALL_neuralNetworkNode) outputNodes.elementAt(o);

      if (currentNode == null) {
        // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
        // "Failed to find output node during backward propagation");
        return false;
      }

      // Get the desired output for this node, given the instance passed in
      target = instance.outputAt(o);

      // Calculate the error of the current output to the target output
      // ----------------------------------------------------
      output = currentNode.output;

      // The Output Nodes Error Rating
      // Given by: o (1-o) (t-o)
      // ---------------------------
      currentNode.outputError = ((output * (1 - output)) * (target - output));
    }

    lowerLayer = outputNodes;

    // Then the errors within the hidden layers
    for (l = (hiddenNodes.size() - 1); l >= 0; l--) {
      currentLayer = (Vector) hiddenNodes.elementAt(l);

      if (currentLayer == null) {
        // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
        // "Failed to find hidden layer during backward propagation");
        return false;
      }
      if (lowerLayer == null) {
        // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
        // "We've lost reference to the layer of nodes beneath the current one");
        return false;
      }

      for (h = 0; h < currentLayer.size(); h++) {
        currentNode = (CALL_neuralNetworkNode) currentLayer.elementAt(h);
        if (currentNode == null) {
          // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
          // "Failed to find hidden node during backward propagation");
          return false;
        }

        // Calculate the error of the current hidden nodes output based on the
        // previous layers errors
        output = currentNode.output;

        // Get the total propogated errors from the nodes receiving output from
        // this one,
        // combined with the weight that leads to that node
        propogatedErrors = 0.0;

        for (o = 0; o < lowerLayer.size(); o++) {
          outputNode = (CALL_neuralNetworkNode) lowerLayer.elementAt(o);
          if (outputNode == null) {
            // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
            // "Failed to find output node during backward propagation");
            return false;
          }
          weight = outputNode.inputWeight(h);
          propogatedErrors += (outputNode.outputError * weight);
        }

        // The hidden Nodes Error Rating
        // Given by: o (1-o) E(we) <= This is the propogated errors from below
        // -----------------------------------------------------------
        currentNode.outputError = ((output * (1 - output)) * propogatedErrors);
      }

      // The layer we are currently dealing with will be seen as the lower layer
      // on the next itteration
      lowerLayer = currentLayer;
    }

    // STEP 2. NOW GO THROUGH THE NETWORK UPDATING ALL THE WEIGHTS
    // ==================================================================

    // Output Weights
    for (o = 0; o < outputNodes.size(); o++) {
      // Get the output node
      currentNode = (CALL_neuralNetworkNode) outputNodes.elementAt(o);

      if (currentNode == null) {
        // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
        // "Failed to find output node during weight adjustment");
        return false;
      }

      // Calculate dw, the change in weight, and update the weights
      for (i = 0; i < currentNode.inputWeights.size(); i++) {
        // Work out the momentum weight change value (based on last 3
        // itterations)
        dwPrev1 = (Double) currentNode.n_1WeightChange.elementAt(i);
        dwPrev2 = (Double) currentNode.n_2WeightChange.elementAt(i);
        dwPrev3 = (Double) currentNode.n_3WeightChange.elementAt(i);

        // momentumWeight = (((momentum * dwPrev1) * 4.0) + ((momentum *
        // dwPrev2) * 2.0) + (momentum * dwPrev3)) / 7.0;
        momentumWeight = momentum * dwPrev1;

        tempDouble = (Double) currentNode.inputs.elementAt(i);

        if (tempDouble != null) {
          // Calculate weight based on error and input
          input = tempDouble.doubleValue();
          dw = (currentLearningRate * (currentNode.outputError * input)) + momentumWeight;

          // Update this weight
          weight = currentNode.inputWeight(i);
          weight += dw;

          // Save the new weight
          currentNode.inputWeights.setElementAt(new Double(weight), i);

          // Save the weight change value (for momentum next time around)
          currentNode.n_3WeightChange.setElementAt(new Double(dwPrev2), i);
          currentNode.n_2WeightChange.setElementAt(new Double(dwPrev1), i);
          currentNode.n_1WeightChange.setElementAt(new Double(dw), i);
        } else {
          // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
          // "Problem with node inputs during weight adjustment");
        }
      }
    }

    // Hidden unit Weights
    for (l = 0; l < hiddenNodes.size(); l++) {
      currentLayer = (Vector) hiddenNodes.elementAt(l);

      if (currentLayer == null) {
        // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
        // "Failed to find hidden layer during during weight adjustment");
        return false;
      }

      for (h = 0; h < currentLayer.size(); h++) {
        currentNode = (CALL_neuralNetworkNode) currentLayer.elementAt(h);
        if (currentNode == null) {
          // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
          // "Failed to find hidden node during weight adjustment");
          return false;
        }

        // Calculate dw, the change in weight, and update the weights
        for (i = 0; i < currentNode.inputWeights.size(); i++) {
          tempDouble = (Double) currentNode.inputs.elementAt(i);

          // Work out the momentum weight change value (based on last 3
          // itterations)
          dwPrev1 = (Double) currentNode.n_1WeightChange.elementAt(i);
          dwPrev2 = (Double) currentNode.n_2WeightChange.elementAt(i);
          dwPrev3 = (Double) currentNode.n_3WeightChange.elementAt(i);

          // momentumWeight = (((momentum * dwPrev1) * 4.0) + ((momentum *
          // dwPrev2) * 2.0) + (momentum * dwPrev3)) / 7.0;
          momentumWeight = momentum * dwPrev1;

          if (tempDouble != null) {
            // Calculate weight based on error and input
            input = tempDouble.doubleValue();
            dw = (currentLearningRate * (currentNode.outputError * input)) + momentumWeight;

            weight = currentNode.inputWeight(i);
            weight += dw;

            // Save the weight
            currentNode.inputWeights.setElementAt(new Double(weight), i);

            // Save the weight change value (for momentum next time around)
            currentNode.n_3WeightChange.setElementAt(new Double(dwPrev2), i);
            currentNode.n_2WeightChange.setElementAt(new Double(dwPrev1), i);
            currentNode.n_1WeightChange.setElementAt(new Double(dw), i);
          } else {
            // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
            // "Problem with node inputs during weight adjustment");
          }
        }
      }
    }

    // It all seems to have worked
    return true;
  }

  // Gets the current error rate across data set (assuming we're at the end of a
  // training cycle
  public double getErrorRate() {
    return sre;
  }

  // Get's the current error rate given an instance
  public double getErrorRate(CALL_featureVector instance) {
    CALL_neuralNetworkNode outputNode;
    double errorRating = 0.0;
    double target, output;

    // Check outputs matches output nodes, if not, big problem!
    if (instance.outputSize() == outputNodes.size()) {
      if (forward(instance)) {
        for (int o = 0; o < outputNodes.size(); o++) {
          outputNode = (CALL_neuralNetworkNode) outputNodes.elementAt(o);
          if (outputNode != null) {
            output = outputNode.output;
            target = instance.outputAt(o);
            // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.DEBUG,
            // "Error Calculation: E+=(" + target + " - " + output + ")^2");
            errorRating += ((target - output) * (target - output));
          }
        }
      } else {
        // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.WARN,
        // "Forward algorithm problem during error calculation");
      }
    } else {
      // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.WARN,
      // "Mismatch in output sizes whilst calculating error");
    }

    return errorRating;
  }

  // Evaluates the neural network based on the evaluation file supplied
  public void evaluate(String dataFile) {
    CALL_featureVectorSet dataSet;

    dataSet = new CALL_featureVectorSet(dataFile);
    if ((dataSet != null) && (dataSet.size() > 0)) {
      evaluate(dataSet);
    }
  }

  // Evaluates the neural network based on the evaluation data set provided
  public void evaluate(CALL_featureVectorSet dataSet) {
    CALL_featureVector instance;
    CALL_neuralNetworkNode outputNode;
    double target, output, highP;
    int count, desiredGroup, chosenGroup;
    boolean groupType;

    for (int i = 0; i < dataSet.size(); i++) {
      instance = (CALL_featureVector) dataSet.getInstance(i);
      if (instance != null) {
        // Run the instance through the network
        if (forward(instance)) {
          // Now debug the network outputs with respect to the desired outputs
          if (outputNodes.size() == 1) {
            // Just using one output, so compare directly to expected result
            outputNode = (CALL_neuralNetworkNode) outputNodes.elementAt(0);
            if (outputNode != null) {
              output = outputNode.output;
              target = instance.outputAt(0);
              // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.INFO,
              // "Output Node = " + output + ". Expected: " + target);
            }
          } else {
            // If desired outputs are all 0.0s except one 1.0, it's a group
            // selector, so just show top chosen group compared with desired
            count = 0;
            groupType = true;
            desiredGroup = -1;

            for (int o = 0; o < outputNodes.size(); o++) {
              target = instance.outputAt(o);
              if ((target != 0.0) && (target != 1.0)) {
                // Not a group selection type
                groupType = false;
                break;
              }

              if (target == 1.0) {
                desiredGroup = o;
                count++;
              }

              if (count > 1) {
                // Not a group selection type
                groupType = false;
                break;
              }
            }

            // Final check - we are just checking against 1 group?
            if (count != 1) {
              groupType = false;
            }

            if ((groupType) && (desiredGroup != -1)) {
              highP = -1.0;
              chosenGroup = -1;

              for (int o = 0; o < outputNodes.size(); o++) {
                outputNode = (CALL_neuralNetworkNode) outputNodes.elementAt(o);
                if (outputNode != null) {
                  output = outputNode.output;
                  if (output > highP) {
                    highP = output;
                    chosenGroup = o;
                  }
                }

                // CALL_debug.printlog(CALL_debug.MOD_NEURALNET,
                // CALL_debug.INFO, "Group = " + chosenGroup + " (Desired: " +
                // desiredGroup + ")");
              }
            } else {
              for (int o = 0; o < outputNodes.size(); o++) {
                outputNode = (CALL_neuralNetworkNode) outputNodes.elementAt(o);
                if (outputNode != null) {
                  output = outputNode.output;
                  target = instance.outputAt(o);
                  // CALL_debug.printlog(CALL_debug.MOD_NEURALNET,
                  // CALL_debug.INFO, "Output Node [" + o + "]  = " + output +
                  // ". Expected: " + target);
                }
              }
            }
          }
        }
      }
    }
  }

  // Returns the index of the highest ranking class given the input feature set
  // If there is only one output, we assume it's a boolean operator, thus return
  // 0 or 1 depending on
  // whether the output is greater than 0.5 or less.
  public int classify(CALL_featureVector input) {
    int rv = 0;

    return rv;
  }

  // Save the network
  public boolean save(String filename) {
    boolean rc = true;
    FileWriter out;
    PrintWriter outP;
    Vector currentLayer;
    CALL_neuralNetworkNode currentNode;
    int numHiddenNodes;
    Double tempDouble;

    if ((hiddenNodes.size() > 0) && (outputNodes.size() > 0)) {
      // Open file
      try {
        out = new FileWriter(filename);
        if (out == null) {
          // File does not exist
          // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.ERROR,
          // "Failed to write config file");
          return false;
        }

      } catch (IOException e) {
        // File does not exist
        // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.ERROR,
        // "Failed to write config file");
        return false;
      }

      outP = new PrintWriter(out);

      // Write ANN file
      outP.println("#");
      outP.println("# CallJ ANN file");
      outP.println("#");
      outP.println("##############");
      outP.println("#");

      // Network settings
      outP.println("-sigmoidFactor " + sigmoidFactor);
      outP.println("-maxLR " + initialLearningRate);
      outP.println("-minLR " + minimumLearningRate);
      outP.println("-mom " + momentum);

      // Network size (layers, hidden nodes, output nodes)
      currentLayer = (Vector) hiddenNodes.elementAt(0);
      if (currentLayer != null) {
        numHiddenNodes = currentLayer.size();
      } else {
        // This is a problem
        numHiddenNodes = 0;
      }
      outP.println("-networkSize " + hiddenNodes.size() + " " + numHiddenNodes + " " + outputNodes.size());

      // Hidden layers
      for (int l = 0; l < hiddenNodes.size(); l++) {
        currentLayer = (Vector) hiddenNodes.elementAt(l);
        if (currentLayer != null) {
          for (int h = 0; h < currentLayer.size(); h++) {
            currentNode = (CALL_neuralNetworkNode) currentLayer.elementAt(h);
            if (currentNode != null) {
              outP.println("-nodeType " + currentNode.type);
              outP.println("-nodeNum " + currentNode.nodeNum);
              outP.println("-nodeLayer " + currentNode.layer);
              for (int e = 0; e < currentNode.inputWeights.size(); e++) {
                tempDouble = (Double) currentNode.inputWeights.elementAt(e);
                if (tempDouble != null) {
                  outP.println("-nodeInputWeight " + tempDouble);
                }
              }
            }
          }
        }
      }

      // Output Nodes
      for (int o = 0; o < outputNodes.size(); o++) {
        currentNode = (CALL_neuralNetworkNode) outputNodes.elementAt(o);
        if (currentNode != null) {
          outP.println("-nodeType " + currentNode.type);
          outP.println("-nodeNum " + currentNode.nodeNum);
          for (int e = 0; e < currentNode.inputWeights.size(); e++) {
            tempDouble = (Double) currentNode.inputWeights.elementAt(e);
            if (tempDouble != null) {
              outP.println("-nodeInputWeight " + tempDouble);
            }
          }
        }
      }

      // Close the file handles
      try {
        out.close();
        outP.close();
      } catch (IOException e) {
        // Ignore for now
      }
    }

    return rc;
  }

  // Load the network
  public boolean load(String filename) {
    boolean rc = true;

    return rc;
  }

  // Set the momentum
  public void setMomentum(double val) {
    momentum = val;
  }

  // Set the initial learning rate
  public void setRates(double val1, double val2) {
    initialLearningRate = val1;
    minLearningRate = val2;
  }

  public void setAccuracyCutoff(double val) {
    accuracyThreshold = val;
  }
}

class CALL_neuralNetworkNode {
  // The node layer and number information
  int nodeNum;
  int type; // 0: Input, 1: Hidden, 2: Output
  int layer; // If type is Hidden, represents layer

  // The current output from the node (given the last instance)
  double output;
  double outputError;

  // The nodes outputs
  Vector inputWeights; // A vector of doubles representing the weights on it's
                       // input
  Vector n_1WeightChange; // A vector of doubles representing the previous (n-1)
                          // weight change, used in momentum
  Vector n_2WeightChange; // A vector of doubles representing the (n-2) weight
                          // change, used in momentum
  Vector n_3WeightChange; // A vector of doubles representing the (n-3) weight
                          // change, used in momentum
  Vector inputs; // A vector of the previous inputs to flow into the node

  public CALL_neuralNetworkNode(int t, int n, int l, int inum, double spread) {
    Random rand;
    Double weight;

    nodeNum = n;
    layer = l;
    type = t;

    rand = new Random();

    // Now create the output weights
    inputWeights = new Vector();
    n_1WeightChange = new Vector();
    n_2WeightChange = new Vector();
    n_3WeightChange = new Vector();
    inputs = new Vector();

    for (int i = 0; i <= inum; i++) {
      // Gives a randomly initialised weight between -0.5 and 0.5
      // Note we have 1 more weight than we have inputs, because all nodes also
      // have a constant -1 input as input 0.
      weight = (spread / 2.0) - (spread * new Double(rand.nextDouble()));
      inputWeights.addElement(weight);
      inputs.addElement(new Double(-1.0));
      n_1WeightChange.addElement(new Double(0.0));
      n_2WeightChange.addElement(new Double(0.0));
      n_3WeightChange.addElement(new Double(0.0));
    }
  }

  public double inputWeight(int index) {
    Double tempDouble;
    double rv = 0.0;

    if ((index >= 0) && (index < inputWeights.size())) {
      tempDouble = (Double) inputWeights.elementAt(index);
      if (tempDouble != null) {
        rv = tempDouble.doubleValue();
      }
    }

    return rv;
  }
}
