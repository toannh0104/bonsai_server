//////////////////////////////////////////////////////////////
// A set of features, i.e. floating point values
// For use in the various pattern classifiers in the system
//
package jcall;

import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

public class CALL_featureTemplateSet {
  static final int STATE_FINISHED = -1;
  static final int STATE_MEAN = 0;
  static final int STATE_VARIANCE = 1;
  static final int STATE_OUTPUT = 2;

  // The vector of templates (each representing a different output category in
  // the network)
  Vector templates;

  public CALL_featureTemplateSet() {
    init();
  }

  public CALL_featureTemplateSet(String filename) {
    init();
    load(filename);
  }

  public void init() {
    templates = new Vector();
  }

  public boolean load(String filename) {
    int readStatus = STATE_MEAN;
    boolean rc = true;
    double tempDbl;
    int meanCount, varianceCount, outputCount;
    String readLine;
    StringTokenizer st;
    CALL_featureTemplate currentTemplate;

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
    currentTemplate = null;
    meanCount = -1;
    varianceCount = -1;
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
      }
    }

    return rc;
  }
}