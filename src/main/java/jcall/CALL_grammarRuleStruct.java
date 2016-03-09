///////////////////////////////////////////////////////////////////
// Grammar Rule Structure
//

package jcall;

import java.util.Vector;

public class CALL_grammarRuleStruct {
  // Fields
  String name; // Name of the rule (or subrule)
  String description; // Not sure what I'll use this for, but could be useful,
                      // maybe in feedback, lesson description etc.

  // Parameter(s) may be passed to the rule (if name is null, no parameter
  // allowed). This vector stores the names of parameters
  // A vector outside the structure will be created with the associated values
  // during parsing
  Vector parameters;

  // A pointer to the rules parent rule (if any)
  CALL_grammarRuleStruct parent;

  // Local Grammars
  Vector subRules; // Also of type CALL_grammarRuleStruct

  // A list of CALL_sentenceTemplateStruct objects, representing sentence
  // structure
  Vector templates;

  // The forms specified for this grammar
  Vector formSettings; // A vector of vectors, each object being of type
                       // CALL_formStruct (each sub-vector is the list of forms
                       // for a particular form class, eg verbForm)
  Vector formNames; // The names of the form classes (verbForm, adjForm, etc).
                    // Same size as vector above, although just 1 dimensional.

  // adding
  String formSetting; // no constrict, the mixture of all formsettings in a
                      // <form> label

  public CALL_grammarRuleStruct() {
    init();
  }

  public CALL_grammarRuleStruct(String str) {
    init();
    name = new String(str);
  }

  private void init() {
    name = null;
    description = null;
    parameters = new Vector();
    templates = new Vector();
    subRules = new Vector();
    formSettings = new Vector();
    formNames = new Vector();
  }

  public CALL_grammarRuleStruct getSubRule(String name) {
    CALL_grammarRuleStruct rRule = null;
    CALL_grammarRuleStruct tempRule;

    // Looking just at subrule here
    for (int i = 0; i < subRules.size(); i++) {
      tempRule = (CALL_grammarRuleStruct) subRules.elementAt(i);
      if (tempRule != null) {
        if (tempRule.name.equals(name)) {
          // We've found it
          rRule = tempRule;
          break;
        }
      }
    }

    return rRule;
  }

  // Returns the index of the parameter with the specified name (key)
  public int parameterIndex(String key) {
    int rc = -1;
    String parm;

    for (int i = 0; i < parameters.size(); i++) {
      parm = (String) parameters.elementAt(i);
      if (parm != null) {
        if (parm.equals(key)) {
          // We have found the parameter
          rc = i;
          break;
        }
      }
    }

    return rc;
  }

  public void addParameter(String parm) {
    parameters.addElement(parm);
  }
}