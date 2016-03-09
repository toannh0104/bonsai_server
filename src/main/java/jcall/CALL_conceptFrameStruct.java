///////////////////////////////////////////////////////////////////
// Concept Structure - for each verb, holds a list of the possible components 
// that may be associated with it.
//

package jcall;

import java.util.Vector;

public class CALL_conceptFrameStruct {
  // Defines

  // Fields
  String name; // "Verb", "Time", "Place", "Object" etc (complex, more than one
               // word, structures)
  Vector slots; // A list of CALL_conceptSlotStruct objects

  public CALL_conceptFrameStruct() {
    name = null;
    slots = new Vector();
  }

  public CALL_conceptFrameStruct(String pname) {
    name = new String(pname);
    slots = new Vector();
  }

  public Vector getSlots() {
    return slots;
  }

  public void setSlots(Vector slots) {
    this.slots = slots;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}