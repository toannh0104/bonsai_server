//////////////////////////////////////////////////////////////////////////////////////
// Adjective Rule Structures - holds the information about how to construct
//				  				the various inflections for adjective type.
//				  				All data is loaded from the adjective-rules.txt 
//								file.
//
///////////////////////////////////////////////////////////////////////////////////////

package jcall;

import java.util.Vector;

import jcall.db.JCALL_Word;

public class CALL_adjectiveSubRuleStruct {
  int group; // 1: i Adjectives. 2: Na adjectives

  // SEE CALL_formStruct FOR DEFINITIONS ON THESE VALUES
  // -----------------------------------------------------
  int tense; // 0: Past, 1: Non-past, -1: All
  int politeness; // 0: Polte, 1: Plain, -1: All
  int positive; // 0: Positive, 1: Negative, -1: All

  String default_rule; // Default rule
  Vector rules; // A vector of restriction/rule pairs (CALL_rulePairStruct)

  // Empty Constructor
  public CALL_adjectiveSubRuleStruct() {
    rules = new Vector();
  }

  // Constructor with type defined
  public CALL_adjectiveSubRuleStruct(int pgroup, int ptense, int ppoliteness, int ppositive) {
    rules = new Vector();

    group = pgroup;
    tense = ptense;
    politeness = ppoliteness;
    positive = ppositive;
  }

  // Return the rule string for the given adjective (default, if not specified
  // in the restriction "list")
  public String getRuleString(CALL_wordStruct padj) {
    int i;
    CALL_rulePairStruct pairStruct;
    String adjStr;
    String returnString = null;
    String tempString;

    adjStr = padj.kanji;

    // Check restriction Strings
    for (i = 0; i < rules.size(); i++) {
      pairStruct = (CALL_rulePairStruct) rules.elementAt(i);

      // Chec Restriction
      if (pairStruct.restriction.startsWith("*")) {
        // Matching with a wildcard
        tempString = pairStruct.restriction.substring(1, pairStruct.restriction.length());
        if (adjStr.endsWith(tempString)) {
          returnString = pairStruct.rule;
          break;
        }
      } else {
        // Standard match
        if (pairStruct.restriction.equals(adjStr)) {
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

  public String getRuleString(JCALL_Word padj) {
    int i;
    CALL_rulePairStruct pairStruct;
    String adjStr;
    String returnString = null;
    String tempString;

    adjStr = padj.getStrKanji();

    // Check restriction Strings
    for (i = 0; i < rules.size(); i++) {
      pairStruct = (CALL_rulePairStruct) rules.elementAt(i);

      // Chec Restriction
      if (pairStruct.restriction.startsWith("*")) {
        // Matching with a wildcard
        tempString = pairStruct.restriction.substring(1, pairStruct.restriction.length());
        if (adjStr.endsWith(tempString)) {
          returnString = pairStruct.rule;
          break;
        }
      } else {
        // Standard match
        if (pairStruct.restriction.equals(adjStr)) {
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