//////////////////////////////////////////////////////////////
// A set of instances comprised of featureVectors
// For use in the various pattern classifiers in the system
//
package jcall;

import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

public class CALL_featureVectorSet {
  static final int STATE_FINISHED = -1;
  static final int STATE_FEATURES = 0;
  static final int STATE_OUTPUT = 1;

  Vector instances;

  // Basic constructor
  public CALL_featureVectorSet() {
    instances = new Vector();
  }

  // Load constructor
  public CALL_featureVectorSet(String filename) {
    instances = new Vector();
    loadInstances(filename);
  }

  // Creates a set of instances based on the feature template passed in (just
  // for one output group)
  public void generateInstances(CALL_featureTemplate template, int number) {
  }

  // Creates a set of instances based on the feature templates passed in (for
  // all output groups)
  public void generateInstances(CALL_featureTemplateSet templates, int number) {
  }

  // Returns size of data set
  public int size() {
    return instances.size();
  }

  // Return number of features per instance (based on first instance)
  public int numFeatures() {
    CALL_featureVector featureVec;
    int features = 0;

    if (instances.size() > 0) {
      featureVec = (CALL_featureVector) instances.elementAt(0);
      if (featureVec != null) {
        features = featureVec.featureSize();
      }
    }

    return features;
  }

  // Return number of features per instance (based on first instance)
  public int numOutputs() {
    CALL_featureVector featureVec;
    int outputs = 0;

    if (instances.size() > 0) {
      featureVec = (CALL_featureVector) instances.elementAt(0);
      if (featureVec != null) {
        outputs = featureVec.outputSize();
      }
    }

    return outputs;
  }

  // Simple add instance function
  public void addInstance(CALL_featureVector instance) {
    instances.addElement(instance);
  }

  // Simple get instance function
  public CALL_featureVector getInstance(int index) {
    CALL_featureVector rv = null;

    if ((index >= 0) && (index < instances.size())) {
      rv = (CALL_featureVector) instances.elementAt(index);
    }

    return rv;
  }

  // Load all instances from a data file
  public boolean loadInstances(String filename) {
    int readStatus = STATE_FEATURES; // 0: Expecting feature list, 1: Expecting
                                     // output list, -1: Finished
    boolean rc = true;
    double tempDbl;
    int featureCount, outputCount;
    String readLine;
    StringTokenizer st;
    CALL_featureVector currentVector;

    FileReader in;

    // Open file
    try {
      in = new FileReader(filename);
      if (in == null) {
        // File does not exist
        // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
        // "Data file not found [" + filename + "]");
        return false;
      }
    } catch (IOException e) {
      // File does not exist
      // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
      // "Problem opening data file [" + filename + "]");
      return false;
    }

    // Read Line by line
    currentVector = null;
    featureCount = -1;
    outputCount = -1;

    while (readStatus != STATE_FINISHED) {
      readLine = CALL_io.readString(in);

      if (readLine == null) {
        // Reached end of file - stop reading
        readStatus = STATE_FINISHED;
      } else if (readLine.startsWith("#")) {
        // Comment, do nothing
      } else if (readLine.startsWith("-eof")) {
        // End of file!
        readStatus = STATE_FINISHED;
      } else {
        st = new StringTokenizer(readLine);

        switch (readStatus) {
          case STATE_FEATURES:
            // Expecting a string of doubles expressing the features that make
            // up the instance
            // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.DEBUG,
            // "New feature vector...reading features");
            currentVector = new CALL_featureVector();

            for (;;) {
              tempDbl = CALL_io.getNextDouble(st);
              if (tempDbl == CALL_io.INVALID_DBL) {
                // Finished set
                // CALL_debug.printlog(CALL_debug.MOD_NEURALNET,
                // CALL_debug.DEBUG, "Finished reading features.");
                readStatus = STATE_OUTPUT;
                break;
              }

              // Add double as a feature
              // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.DEBUG,
              // "Adding feature: " + tempDbl);
              currentVector.addFeature(tempDbl);
            }

            break;

          case STATE_OUTPUT:
            // Expecting a string of doubles expressing the desired output for
            // this instance
            // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.DEBUG,
            // "Reading desired outputs for feature vector");
            if ((currentVector == null) || (currentVector.featureSize() <= 0)) {
              // Out of sync somehow
              // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.WARN,
              // "No features currently definined...skipping");
              rc = false;
              readStatus = STATE_FINISHED;
            } else {
              for (;;) {
                tempDbl = CALL_io.getNextDouble(st);
                if (tempDbl == CALL_io.INVALID_DBL) {
                  // Finished set
                  // Check the size of this vector to see if it matches that for
                  // the data set
                  if ((currentVector.outputSize() == 0) || (currentVector.featureSize() == 0)) {
                    // Invalid vector - no elements. Skip it!
                    // CALL_debug.printlog(CALL_debug.MOD_NEURALNET,
                    // CALL_debug.WARN, "Invalid instance, skipping");
                    currentVector = null;
                  }
                  break;
                }

                // Add output
                // CALL_debug.printlog(CALL_debug.MOD_NEURALNET,
                // CALL_debug.DEBUG, "Adding ouput: " + tempDbl);
                currentVector.addOutput(tempDbl);
              }

              // Check the size before adding it to the list
              // ------------------------------------
              if (currentVector != null) {
                if ((featureCount == -1) && (outputCount == -1)) {
                  // First entry, so no need to check with current
                  featureCount = currentVector.featureSize();
                  outputCount = currentVector.outputSize();
                  instances.addElement(currentVector);
                } else {
                  // Does it match the current size set - if not, skip it!
                  if ((featureCount == currentVector.featureSize()) && (outputCount == currentVector.outputSize())) {
                    instances.addElement(currentVector);
                    // CALL_debug.printlog(CALL_debug.MOD_NEURALNET,
                    // CALL_debug.DEBUG, "Data instance added");
                  } else {
                    // CALL_debug.printlog(CALL_debug.MOD_NEURALNET,
                    // CALL_debug.WARN, "Mismatch in instance size, skipping");
                  }
                }

                // Get ready to move on
                currentVector = null;
              }

              // Go back to reading features
              readStatus = STATE_FEATURES;
            }
            break;

          default:
            // Unknown status, abort
            rc = false;
            readStatus = STATE_FINISHED;
            break;
        }
      }
    }

    // Close file
    if (in != null) {
      try {
        in.close();
      } catch (IOException e) {
        // Ignore for now
        // Probably just that the file didn't exist
      }
    }

    return rc;
  }

  // Save all instances to a data file
  public boolean saveInstances(String file) {
    boolean rc = false;

    return rc;
  }

  // Return the mean of feature index i

  // Return the variance of feature index i

  // Return the co-variance of features indexes i and j

}