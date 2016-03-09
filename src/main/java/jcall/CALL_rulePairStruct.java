///////////////////////////////////////////////////////////////////
// Verb Rule Structures - holds the information about how to construct
//				  the various inflections for each verb group.
//				  All data is loaded from the verbRules.txt file.
//
///////////////////////////////////////////////////////////////////

package jcall;

public class CALL_rulePairStruct {
  // Fields
  String restriction;
  String rule;

  // Empty Constructor
  public CALL_rulePairStruct() {
    // Does nothing
  }

  // Complete Constructor
  public CALL_rulePairStruct(String prestriction, String prule) {
    // Does nothing]
    restriction = new String(prestriction);
    rule = new String(prule);
  }

  public String getRestriction() {
    return restriction;
  }

  public void setRestriction(String restriction) {
    this.restriction = restriction;
  }

  public String getRule() {
    return rule;
  }

  public void setRule(String rule) {
    this.rule = rule;
  }
}