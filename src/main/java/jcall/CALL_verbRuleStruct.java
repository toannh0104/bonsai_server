///////////////////////////////////////////////////////////////////
// Verb Rule Structures - holds the information about how to construct
//				  the various inflections for each verb group.
//				  All data is loaded from the verbRules.txt file.
//
///////////////////////////////////////////////////////////////////

package jcall;

import java.util.Vector;

import jcall.db.JCALL_Word;
import jcall.recognition.util.CharacterUtil;

import org.apache.log4j.Logger;

public class CALL_verbRuleStruct {
  static Logger logger = Logger.getLogger(CALL_verbRuleStruct.class.getName());

  // Fields
  String name;

  // Sub Structures
  Vector subrules[];

  // Statics

  // Empty Constructor
  public CALL_verbRuleStruct() {
    subrules = new Vector[CALL_verbRulesStruct.VERB_GROUPS];
    for (int i = 0; i < CALL_verbRulesStruct.VERB_GROUPS; i++) {
      subrules[i] = new Vector();
    }

    // CALL_debug.printlog(CALL_debug.MOD_VRULES, CALL_debug.DEBUG,
    // "Created empty verb rule");
  }

  // Name Constructor
  public CALL_verbRuleStruct(String nameString) {
    name = new String(nameString);

    subrules = new Vector[CALL_verbRulesStruct.VERB_GROUPS];
    for (int i = 0; i < CALL_verbRulesStruct.VERB_GROUPS; i++) {
      subrules[i] = new Vector();
    }

    // Debug
    // CALL_debug.printlog(CALL_debug.MOD_VRULES, CALL_debug.DEBUG,
    // "Created verb rule [" + name + "]");
  }

  // Add a rule structure to the object
  public void add_rule(CALL_verbSubRuleStruct data) {
    subrules[data.group].add(data);
  }

  // Search for a particular sub-rule
  public String getRuleString(CALL_wordStruct pverb, int psign, int ptense, int ppoliteness, int pquestion) {
    int i;
    int verbGroup;
    String returnString = null;
    CALL_verbSubRuleStruct subrule = null;

    // What group is the verb
    verbGroup = (pverb.parameter1 - 1);

    if ((verbGroup >= 0) && (verbGroup < CALL_verbRulesStruct.VERB_GROUPS)) {
      for (i = 0; i < subrules[verbGroup].size(); i++) {
        subrule = (CALL_verbSubRuleStruct) subrules[verbGroup].elementAt(i);
        if ((subrule.tense == ptense) && (subrule.politeness == ppoliteness) && (subrule.positive == psign)) {
          returnString = subrule.getRuleString(pverb);

          // Deal with the question part - this is the same for all verbs, and
          // all rules, so can be done here
          if (pquestion == CALL_formStruct.QUESTION) {
            if (ppoliteness == CALL_formStruct.POLITE) {
              // Polite
              returnString = returnString + " + " + CharacterUtil.STR_KA;
            } else {
              // Plain
              returnString = returnString + " + " + CharacterUtil.STR_KA;
            }
          }

          break;
        }
      }
    } else {
      // CALL_debug.printlog(CALL_debug.MOD_VRULES, CALL_debug.WARN,
      // "Verb group out of range, " + verbGroup + ", [" + pverb.kanji + "]");
    }

    return returnString;
  }

  public String getRuleString(JCALL_Word pverb, int psign, int ptense, int ppoliteness, int pquestion) {
    // logger.debug("Enter  getRuleString");

    int i;
    int verbGroup;
    String group;
    String returnString = null;
    CALL_verbSubRuleStruct subrule = null;

    // What group is the verb
    // verbGroup = (pverb.parameter1 - 1);
    group = pverb.getStrGroup();
    verbGroup = Integer.parseInt(group) - 1;

    if ((verbGroup >= 0) && (verbGroup < CALL_verbRulesStruct.VERB_GROUPS)) {
      for (i = 0; i < subrules[verbGroup].size(); i++) {
        subrule = (CALL_verbSubRuleStruct) subrules[verbGroup].elementAt(i);
        // logger.debug("subrule["+i+"], tense: "+ subrule.tense+ "politeness "+
        // subrule.politeness + "positve: " + subrule.positive);
        // logger.debug("")
        if ((subrule.tense == ptense) && (subrule.politeness == ppoliteness) && (subrule.positive == psign)) {
          returnString = subrule.getRuleString(pverb);

          // Deal with the question part - this is the same for all verbs, and
          // all rules, so can be done here
          if (pquestion == CALL_formStruct.QUESTION) {
            if (ppoliteness == CALL_formStruct.POLITE) {
              // Polite
              returnString = returnString + " + " + CharacterUtil.STR_KA;
            } else {
              // Plain
              returnString = returnString + " + " + CharacterUtil.STR_KA;
            }
          }

          break;
        }
      }
    } else {
      // CALL_debug.printlog(CALL_debug.MOD_VRULES, CALL_debug.WARN,
      // "Verb group out of range, " + verbGroup + ", [" + pverb.getStrKanji() +
      // "]");
      logger.error("Verb group out of range, " + verbGroup + ", [" + pverb.getStrKanji() + "]");
    }

    return returnString;
  }

  // For debug
  public void dump_debug() {
  }
}