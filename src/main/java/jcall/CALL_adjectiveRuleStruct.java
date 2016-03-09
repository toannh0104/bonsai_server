//////////////////////////////////////////////////////////////////////////////////////
// Adjective Rule Structures - holds the information about how to construct
//				  				the various inflections for adjective type.
//				  				All data is loaded from the adjective-rules.txt 
//								file.
//
///////////////////////////////////////////////////////////////////////////////////////

package jcall;

import java.util.Vector;

import jcall.db.JCALL_Lexicon;
import jcall.db.JCALL_Word;
import jcall.recognition.util.CharacterUtil;

import org.apache.log4j.Logger;

public class CALL_adjectiveRuleStruct {
  static Logger logger = Logger.getLogger(CALL_adjectiveRuleStruct.class.getName());

  // Fields
  String name;

  // Sub Structures
  Vector subrules[];

  // Statics

  // Empty Constructor
  public CALL_adjectiveRuleStruct() {
    subrules = new Vector[CALL_adjectiveRulesStruct.ADJECTIVE_GROUPS];
    for (int i = 0; i < CALL_adjectiveRulesStruct.ADJECTIVE_GROUPS; i++) {
      subrules[i] = new Vector();
    }

    // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
    // "Created empty adjective rule");
  }

  // Name Constructor
  public CALL_adjectiveRuleStruct(String nameString) {
    name = new String(nameString);

    subrules = new Vector[CALL_adjectiveRulesStruct.ADJECTIVE_GROUPS];
    for (int i = 0; i < CALL_adjectiveRulesStruct.ADJECTIVE_GROUPS; i++) {
      subrules[i] = new Vector();
    }

    // Debug
    // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
    // "Created adjective rule [" + name + "]");
  }

  // Add a rule structure to the object
  public void add_rule(CALL_adjectiveSubRuleStruct data) {
    subrules[data.group].add(data);
  }

  // Search for a particular sub-rule
  public String getRuleString(CALL_wordStruct padj, int psign, int ptense, int ppoliteness, int pquestion) {
    logger.debug("Enter getRuleString; sign: " + psign + " tense: " + ptense + " politeness: " + ppoliteness
        + " question: " + pquestion);

    if (padj == null) {
      logger.error("CALL_wordStruct parameter is NULL");
      return "";
    }

    int i;
    int adjectiveGroup;
    String returnString = null;
    CALL_adjectiveSubRuleStruct subrule = null;

    // What group is the adjective
    adjectiveGroup = (padj.parameter1 - 1);
    logger.debug("Adjective group: " + adjectiveGroup);

    if ((adjectiveGroup >= 0) && (adjectiveGroup < CALL_adjectiveRulesStruct.ADJECTIVE_GROUPS)) {
      for (i = 0; i < subrules[adjectiveGroup].size(); i++) {
        subrule = (CALL_adjectiveSubRuleStruct) subrules[adjectiveGroup].elementAt(i);
        if (((subrule.tense == ptense) || (subrule.tense == -1))
            && ((subrule.politeness == ppoliteness) || (subrule.politeness == -1))
            && ((subrule.positive == psign) || (subrule.positive == -1))) {
          returnString = subrule.getRuleString(padj);

          // Deal with the question part - this is the same for all adjectives,
          // and all rules, so can be done here
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
      // "Adjective type out of range, " + adjectiveGroup + ", [" + padj.kanji +
      // "]");
    }

    return returnString;
  }

  public String getRuleString(JCALL_Word padj, int psign, int ptense, int ppoliteness, int pquestion) {
    int i;
    int adjectiveGroup = -1;
    String returnString = null;
    CALL_adjectiveSubRuleStruct subrule = null;

    // the group of adj is 1;
    // the group of adjv is 2;
    // adjectiveGroup = (padj.parameter1 - 1); //old one
    if (padj.getStrKana().equals("ã�„ã�„") && padj.getIntType() == JCALL_Lexicon.LEX_TYPE_ADJECTIVE) {
      adjectiveGroup = (3 - 1);

    } else if (padj.getIntType() == JCALL_Lexicon.LEX_TYPE_ADJECTIVE) {
      adjectiveGroup = (1 - 1);
    } else if (padj.getIntType() == JCALL_Lexicon.LEX_TYPE_ADJVERB) {
      adjectiveGroup = (2 - 1);
    }

    if ((adjectiveGroup >= 0) && (adjectiveGroup < CALL_adjectiveRulesStruct.ADJECTIVE_GROUPS)) {
      for (i = 0; i < subrules[adjectiveGroup].size(); i++) {
        subrule = (CALL_adjectiveSubRuleStruct) subrules[adjectiveGroup].elementAt(i);
        if (((subrule.tense == ptense) || (subrule.tense == -1))
            && ((subrule.politeness == ppoliteness) || (subrule.politeness == -1))
            && ((subrule.positive == psign) || (subrule.positive == -1))) {
          returnString = subrule.getRuleString(padj);

          // Deal with the question part - this is the same for all adjectives,
          // and all rules, so can be done here
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
      // "Adjective type out of range, " + adjectiveGroup + ", [" +
      // padj.getStrKanji() + "]");
    }

    return returnString;
  }

  // For debug
  public void dump_debug() {
  }
}