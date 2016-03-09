///////////////////////////////////////////////////////////////////
// Holds the information about the concept diagram
//
//

package jcall;

import java.util.Vector;

public class CALL_formDescStruct {
  // Fields
  String description;
  Vector restrictions; // A vector of strings such as "{VERB} type == question"

  public CALL_formDescStruct() {
    restrictions = new Vector();
  }

  public void print_debug() {
    String tempStr;

    // Print description
    // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO, "--Point: ["
    // + description + "]");

    // Go through and print all the string pairs
    for (int i = 0; i < restrictions.size(); i++) {
      tempStr = (String) restrictions.elementAt(i);
      if (tempStr != null) {
        // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO, "----> "
        // + tempStr);
      }
    }
  }
}