///////////////////////////////////////
//
// Extracts the student's answer and correct
// answer from a log file, storing them in a 
// new file ready to be processed.
//
///////////////////////////////////////
package jcall;

import javax.servlet.http.HttpServletRequest;

public class annTrainer {
  static final double TRAINING_THRESHOLD = 0.005;
  
  CALL_featureVectorSet data;
  CALL_neuralNetwork network;
  int hiddenLayers;
  int hiddenNodes;
  int outputNodes;
  double spread;
  
  public annTrainer(String dataFile, String hl, String hn, String on, double s) {
    hiddenLayers = CALL_io.str2int(hl);
    hiddenNodes = CALL_io.str2int(hn);
    outputNodes = CALL_io.str2int(on);
    spread = s;
    
    if ((hiddenLayers != CALL_io.INVALID_INT) && (hiddenNodes != CALL_io.INVALID_INT)
        && (outputNodes != CALL_io.INVALID_INT)) {
      data = new CALL_featureVectorSet(dataFile);
      if (data != null) {
        network = new CALL_neuralNetwork(outputNodes, hiddenNodes, hiddenLayers, data.numFeatures(), spread);
      }
    }
  }
  
  // Do the training (how many itterations, and how many data set cycles per
  // itteration
  public boolean train(int itterations, HttpServletRequest r) {
    int i;
    double currentError;
    
    if (data == null) {
      // Invalid data
      CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.DEBUG, "Invalid data (null data pointer) - aborting", r);
      return false;
    }
    
    if (data.size() <= 0) {
      // Invalid data
      CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.DEBUG, "Invalid data (empty data structure) - aborting",
          r);
      return false;
    }
    
    if (network == null) {
      // Invalid data
      CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.DEBUG, "Invalid Network (null pointer) - aborting", r);
      return false;
    }
    
    // Set network preferences
    network.maxItterations = itterations;
    
    // Run training
    network.train(data);
    
    return true;
  }
  
  // Final clean up function
  public static void cleanup() {
    CALL_debug.closeLog();
  }
  
  // public static void main(String args[]) throws Exception
  // {
  // annTrainer main;
  // double momentum, minRate, maxRate, accuracy;
  // double s;
  // String outputFile;
  // int itterations;
  //
  // // We want full debugging
  // CALL_debug.set_level(CALL_debug.MOD_NEURALNET, CALL_debug.INFO);
  //
  // try
  // {
  // if(args.length < 10)
  // {
  // System.out.println("Usage: sentenceExtractor [input data] [hidden layers] [hidden nodes] [outputs] [target itterations] [Max Rate] [Min Rate] [momentum] [spread] [accuracy cutoff] <output file>");
  // System.out.println("" + args.length + " arguments detected.");
  // cleanup();
  // }
  // else
  // {
  // if(args.length == 11)
  // {
  // outputFile = args[10];
  // }
  // else
  // {
  // outputFile = null;
  // }
  //
  // // Set spread
  // s = CALL_io.str2double(args[8]);
  // if(s == CALL_io.INVALID_DBL)
  // {
  // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
  // "Invalid spread number string: " + args[8]);
  // s = 10.0;
  // }
  //
  // // Initialise
  // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.DEBUG,
  // "Creating Trainer Environment");
  // main = new annTrainer(args[0], args[1], args[2], args[3], s);
  //
  // // Set rates
  // maxRate = CALL_io.str2double(args[5]);
  // minRate = CALL_io.str2double(args[6]);
  //
  // if((maxRate != CALL_io.INVALID_DBL) && (minRate != CALL_io.INVALID_DBL))
  // {
  // main.network.setRates(maxRate, minRate);
  // }
  // else
  // {
  // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
  // "Invalid rate number string: " + args[5]);
  // }
  //
  // // Set momentum
  // momentum = CALL_io.str2double(args[7]);
  // if(momentum != CALL_io.INVALID_DBL)
  // {
  // main.network.setMomentum(momentum);
  // }
  // else
  // {
  // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
  // "Invalid momentum number string: " + args[7]);
  // }
  //
  // // Set accuracy cutoff point
  // accuracy = CALL_io.str2double(args[9]);
  // if(accuracy != CALL_io.INVALID_DBL)
  // {
  // main.network.setAccuracyCutoff(accuracy);
  // }
  // else
  // {
  // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
  // "Invalid momentum number string: " + args[7]);
  // }
  //
  // // Training
  // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.DEBUG,
  // "Initializing training");
  //
  // itterations = CALL_io.str2int(args[4]);
  // if(itterations != CALL_io.INVALID_INT)
  // {
  // main.train(itterations);
  // }
  // else
  // {
  // CALL_debug.printlog(CALL_debug.MOD_NEURALNET, CALL_debug.ERROR,
  // "Invalid itteration number string: " + args[4]);
  // }
  //
  // // Evaluate
  // main.network.evaluate(args[0]);
  //
  // // Save
  // if(outputFile != null)
  // {
  // main.network.save(outputFile);
  // }
  // }
  // }
  // catch(Exception e)
  // {
  // // Keep the exception flying out
  // throw(e);
  // }
  // finally
  // {
  // cleanup();
  // }
  //
  // }
}