///////////////////////////////////////////////////////////////////
// Concept Structure - for each verb, holds a list of the possible components 
// that may be associated with it.
//

package jcall;

import java.util.Vector;

public class CALL_conceptSlotStruct {
  // Defines
  public static final double DEFAULT_LIKELIHOOD = 1.0;

  // Fields
  String name; // Slot name
  double likelihood; // Probability of inclusion (assumed 1.0 if mandatory =
                     // 1.0)

  // The slot filler information
  Vector fillers; // A list of slot fillers (CALL_conceptSlotFillerStruct) -
                  // only one used in any instance!
  double fillerWeightTotal; // Use this to balance the use of fillers

  // Restrictions
  Vector restrictions; // Of type CALL_conceptSlotRestrictionStruct

  public CALL_conceptSlotStruct() {
    init();
  }

  public CALL_conceptSlotStruct(String str) {
    init();
    name = str;
  }

  private void init() {
    name = null;
    likelihood = DEFAULT_LIKELIHOOD;
    fillers = new Vector();
    fillerWeightTotal = 0.0;
    restrictions = new Vector();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Vector getFillers() {
    return fillers;
  }

  public void setFillers(Vector fillers) {
    this.fillers = fillers;
  }

}