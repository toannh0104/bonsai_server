///////////////////////////////////////////////////////////////////
// Verb Rule Structures - holds the information about how to construct
//				  the various inflections for each verb group.
//				  All data is loaded from the verbRules.txt file.
//
///////////////////////////////////////////////////////////////////

package jcall;

import java.util.Vector;

public class CALL_stringPairsStruct {
  // Fields
  Vector parameters; // Of type CALL_stringPairStruct

  public Vector getParameters() {
    return parameters;
  }

  public void setParameters(Vector parameters) {
    this.parameters = parameters;
  }

  // Empty Constructor
  public CALL_stringPairsStruct() {
    // Does nothing
    parameters = new Vector();
  }

  // Copy Constructor
  public CALL_stringPairsStruct(CALL_stringPairsStruct data) {
    String newStr;

    // Does nothing
    parameters = new Vector();
    for (int i = 0; i < data.parameters.size(); i++) {
      newStr = (String) data.parameters.elementAt(i);
      if (newStr != null) {
        parameters.addElement(newStr);
      }
    }
  }

  // Find matching parameter and return value. Return null otherwise.
  public String getValue(String parameter) {
    String rStr = null;
    CALL_stringPairStruct current;

    for (int i = 0; i < parameters.size(); i++) {
      current = (CALL_stringPairStruct) parameters.elementAt(i);
      if (current != null) {
        if (current.parameter.equals(parameter)) {
          // We have a match
          rStr = current.value;
          break;
        }
      }
    }

    return rStr;
  }

  // Find matching parameter and return value. Return null otherwise.
  public CALL_stringPairStruct getPair(int index) {
    CALL_stringPairStruct current;

    if ((index >= 0) && (index < parameters.size())) {
      current = (CALL_stringPairStruct) parameters.elementAt(index);
    } else {
      current = null;
    }

    return current;
  }

  public int size() {
    return parameters.size();
  }

  // Basic add element - adds without checking if parameter exists or not
  public void addElement(CALL_stringPairStruct newParam) {
    parameters.addElement(newParam);
  }

  // Add Element - check if parameter exists. Boolean passed in determines if
  // parameter should be overwritten if exists
  public void addElement(CALL_stringPairStruct newParam, boolean overwrite) {
    CALL_stringPairStruct current;
    boolean add = true;

    for (int i = 0; i < parameters.size(); i++) {
      current = (CALL_stringPairStruct) parameters.elementAt(i);
      if (current != null) {
        if (current.parameter.equals(newParam.parameter)) {
          // We already have the parameter - overwrite?
          if (overwrite) {
            parameters.removeElement(i);
          } else {
            // Do nothing, we will not overwrite
            add = false;
          }
          break;
        }
      }
    }

    if (add) {
      // Add parameter
      parameters.addElement(newParam);
    }
  }

  // Add Element - check if parameter exists. Boolean passed in determines if
  // parameter should be overwritten if exists
  public void addElement(String paramStr, String valueStr, boolean overwrite) {
    CALL_stringPairStruct newParam;

    newParam = new CALL_stringPairStruct(paramStr, valueStr);
    addElement(newParam, overwrite);
  }

  public void print_debug(int debug_module, String prefix) {
    CALL_stringPairStruct currentP;

    for (int i = 0; i < parameters.size(); i++) {
      currentP = (CALL_stringPairStruct) parameters.elementAt(i);
      if (currentP != null) {
        // CALL_debug.printlog(debug_module, CALL_debug.INFO, prefix +
        // "--Parameter: [" + currentP.parameter + " | " + currentP.value +
        // "]");
      }
    }
  }
}