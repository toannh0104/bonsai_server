/**
 * Created on 2008/03/12
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

import java.util.Vector;

import jcall.CALL_rulePairStruct;

public class JCALL_VerbErrorSubRule {

  int group; // 1, 2, 3,4,5

  int tense; // 0: Past, 1: Non-past
  int politeness; // 0: Polte, 1: Plain, 2: Super Polite, 3: Humble, 4: Crude
  int positive; // 0: Positive, 1: Negative

  String default_rule; // Default rule

  Vector rulepairs; // A vector of restriction/rule pairs (CALL_rulePairStruct)

  // Empty Constructor
  public JCALL_VerbErrorSubRule() {
    rulepairs = new Vector();
  }

  // Constructor with type defined

  public JCALL_VerbErrorSubRule(int pgroup, int ptense, int ppoliteness, int ppositive) {
    rulepairs = new Vector();

    group = pgroup;
    tense = ptense;
    politeness = ppoliteness;
    positive = ppositive;
  }

  // Return the rule string for the given verb (default, if not specified in the
  // restriction "list")
  // using Kanji
  public String getrulepairstring(JCALL_WordBase pverb) {
    int i;
    CALL_rulePairStruct pairStruct;
    String verbStr;
    String returnString = null;
    String tempString;

    verbStr = pverb.getStrKanji();
    // Check restriction Strings
    for (i = 0; i < rulepairs.size(); i++) {
      pairStruct = (CALL_rulePairStruct) rulepairs.elementAt(i);

      // Chec Restriction
      if (pairStruct.getRestriction().startsWith("*")) {
        // Matching with a wildcard
        tempString = pairStruct.getRestriction().substring(1, pairStruct.getRestriction().length());
        if (verbStr.endsWith(tempString)) {
          returnString = pairStruct.getRule();
          break;
        }
      } else {
        // Standard match
        if (pairStruct.getRestriction().equals(verbStr)) {
          returnString = pairStruct.getRule();
          break;
        }
      }
    }

    // If not set, return default rule
    if (returnString == null) {
      returnString = default_rule;
    }

    return returnString;
  }

}