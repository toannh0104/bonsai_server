///////////////////////////////////////////////////////////////////
// Concept Structure - for each verb, holds a list of the possible components 
// that may be associated with it.
//

package jcall;

import java.util.Vector;

public class CALL_conceptSlotRestrictionStruct {
  // Fields
  String restriction;
  Vector fillers; // A list of slot fillers (CALL_conceptSlotFillerStruct) -
                  // only one used in any instance!
  double fillerWeightTotal; // Use this to balance the use of fillers

  public CALL_conceptSlotRestrictionStruct() {
    init();
  }

  public CALL_conceptSlotRestrictionStruct(String str) {
    init();
    restriction = str;
  }

  private void init() {
    restriction = null;
    fillers = new Vector();
    fillerWeightTotal = 0.0;
  }
}