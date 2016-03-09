///////////////////////////////////////////////////////////////////
// Verb Rule Structures - holds the information about how to construct
//				  the various inflections for each verb group.
//				  All data is loaded from the verbRules.txt file.
//
///////////////////////////////////////////////////////////////////

package jcall;

import java.util.StringTokenizer;

public class CALL_stringPairStruct {
  // Fields
  String parameter;
  String value;

  public String getParameter() {
    return parameter;
  }

  public void setParameter(String parameter) {
    this.parameter = parameter;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  // Empty Constructor
  public CALL_stringPairStruct() {
    // Does nothing
    parameter = null;
    value = null;
  }

  // Constructor for type "<PARAMETER> = <VALUE>"
  public CALL_stringPairStruct(String s1) {
    StringTokenizer st;
    String tempString;

    parameter = null;
    value = null;

    st = new StringTokenizer(s1);

    // Get parameter
    tempString = CALL_io.getNextToken(st);
    if (tempString != null) {
      parameter = tempString;

      // Get operator (ignore) - if not operator, assume its the value
      tempString = CALL_io.getNextToken(st);
      if (tempString != null) {
        if ((tempString.equals("=")) || (tempString.equals("=="))) {
          // Skip, and go on to get value
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            value = tempString;
          }
        } else {
          // Assume its the value
          value = tempString;
        }
      }
    }
  }

  // Complete Constructor (seperate s1 and s2)
  public CALL_stringPairStruct(String s1, String s2) {
    // Does nothing]
    parameter = s1;
    value = s2;
  }
}