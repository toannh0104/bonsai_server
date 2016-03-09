//////////////////////////////////////////////////////////////
// A set of features, i.e. floating point values
// For use in the various pattern classifiers in the system
//
package jcall;

import java.util.Vector;

public class CALL_featureTemplate {
  Vector mean;
  Vector variance;

  public CALL_featureTemplate() {
    init();
  }

  public void init() {
    mean = new Vector();
    variance = new Vector();
  }
}