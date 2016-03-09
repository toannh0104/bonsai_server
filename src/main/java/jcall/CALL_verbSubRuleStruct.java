///////////////////////////////////////////////////////////////////
// Verb Rule Structures - holds the information about how to construct
//				  the various inflections for each verb group.
//				  All data is loaded from the verbRules.txt file.
//
///////////////////////////////////////////////////////////////////

package jcall;

import java.util.Vector;

import jcall.db.JCALL_Word;

import org.apache.log4j.Logger;

public class CALL_verbSubRuleStruct {
  static Logger logger = Logger.getLogger(CALL_verbSubRuleStruct.class.getName());

  int group; // 1, 2, 3

  // SEE CALL_formStruct FOR DEFINITIONS ON THESE VALUES
  // -----------------------------------------------------
  int tense; // 0: Past, 1: Non-past
  int politeness; // 0: Polte, 1: Plain, 2: Super Polite, 3: Humble, 4: Crude
  int positive; // 0: Positive, 1: Negative

  String default_rule; // Default rule
  Vector rules; // A vector of restriction/rule pairs (CALL_rulePairStruct)

  // Empty Constructor
  public CALL_verbSubRuleStruct() {
    rules = new Vector();
  }

  // Constructor with type defined
  public CALL_verbSubRuleStruct(int pgroup, int ptense, int ppoliteness, int ppositive) {
    rules = new Vector();

    group = pgroup;
    tense = ptense;
    politeness = ppoliteness;
    positive = ppositive;
  }

  // Return the rule string for the given verb (default, if not specified in the
  // restriction "list")
  public String getRuleString(CALL_wordStruct pverb) {
    int i;
    CALL_rulePairStruct pairStruct;
    String verbStr;
    String returnString = null;
    String tempString;

    verbStr = pverb.kanji;

    // Check restriction Strings
    for (i = 0; i < rules.size(); i++) {
      pairStruct = (CALL_rulePairStruct) rules.elementAt(i);

      // Chec Restriction
      if (pairStruct.restriction.startsWith("*")) {
        // Matching with a wildcard
        tempString = pairStruct.restriction.substring(1, pairStruct.restriction.length());
        if (verbStr.endsWith(tempString)) {
          returnString = pairStruct.rule;
          break;
        }
      } else {
        // Standard match
        if (pairStruct.restriction.equals(verbStr)) {
          returnString = pairStruct.rule;
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

  public String getRuleString(JCALL_Word pverb) {
    // logger.debug(" enter getRuleString, pverb: "+ pverb.getStrKanji());
    //
    int i;
    CALL_rulePairStruct pairStruct;
    String verbStr;
    String returnString = null;
    String tempString;

    verbStr = pverb.getStrKanji();

    // Check restriction Strings
    for (i = 0; i < rules.size(); i++) {
      pairStruct = (CALL_rulePairStruct) rules.elementAt(i);

      // Chec Restriction
      if (pairStruct.restriction.startsWith("*")) {
        // Matching with a wildcard
        tempString = pairStruct.restriction.substring(1, pairStruct.restriction.length());
        if (verbStr.endsWith(tempString)) {
          returnString = pairStruct.rule;
          break;
        }
      } else {
        // Standard match
        if (pairStruct.restriction.equals(verbStr)) {
          returnString = pairStruct.rule;
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

  // For debug
  public void dump_debug() {
  }
}