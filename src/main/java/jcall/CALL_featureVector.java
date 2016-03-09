//////////////////////////////////////////////////////////////
// A set of features, i.e. floating point values
// For use in the various pattern classifiers in the system
//
package jcall;

import java.util.Vector;

public class CALL_featureVector {
  Vector features;
  Vector output;

  // Basic constructor
  public CALL_featureVector() {
    init();
  }

  // Basic initialization
  private void init() {
    features = new Vector();
    output = new Vector();
  }

  // Size of vector
  public int featureSize() {
    return features.size();
  }

  // Add element to vector
  public void addFeature(double f) {
    features.addElement(new Double(f));
  }

  // Returns the feature at the given index (in double form)
  public double featureAt(int index) {
    Double tempDbl;
    double rv = 0.0;

    if (index < features.size()) {
      tempDbl = (Double) features.elementAt(index);
      if (tempDbl != null) {
        rv = tempDbl.doubleValue();
      }
    }

    return rv;
  }

  // Size of output
  public int outputSize() {
    return output.size();
  }

  // Add element to vector
  public void addOutput(double o) {
    output.addElement(new Double(o));
  }

  // Returns the feature at the given index (in double form)
  public double outputAt(int index) {
    Double tempDbl;
    double rv = 0.0;

    if (index < output.size()) {
      tempDbl = (Double) output.elementAt(index);
      if (tempDbl != null) {
        rv = tempDbl.doubleValue();
      }
    }

    return rv;
  }
}