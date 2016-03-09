/**
 * Created on 2008/03/12
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

import java.util.Vector;

import jcall.CALL_formStruct;

import org.apache.log4j.Logger;

public class JCALL_VerbErrorRule {

  static Logger logger = Logger.getLogger(JCALL_VerbErrorRule.class.getName());
  // Fields
  String name;

  // Sub Structures
  Vector subrules[];

  // Statics

  // Empty Constructor
  public JCALL_VerbErrorRule() {
    subrules = new Vector[JCALL_VerbErrorRules.VERB_GROUPS];
    for (int i = 0; i < JCALL_VerbErrorRules.VERB_GROUPS; i++) {
      subrules[i] = new Vector();
    }
  }

  // Name Constructor
  public JCALL_VerbErrorRule(String nameString) {
    name = new String(nameString);

    subrules = new Vector[JCALL_VerbErrorRules.VERB_GROUPS];
    for (int i = 0; i < JCALL_VerbErrorRules.VERB_GROUPS; i++) {
      subrules[i] = new Vector();
    }

  }

  // Add a rule structure to the object
  public void add_rule(JCALL_VerbErrorSubRule data) {
    subrules[data.group].add(data);
  }

  // Search for a particular sub-rule
  public String getRuleString(JCALL_Word pverb, int psign, int ptense, int ppoliteness, int pquestion) {
    int i;
    int verbGroup;
    String returnString = null;
    JCALL_VerbErrorSubRule subrule = null;

    // What group is the verb
    String group = pverb.getStrGroup();
    verbGroup = (Integer.parseInt(group) - 1);

    if ((verbGroup >= 0) && (verbGroup < JCALL_VerbErrorRules.VERB_GROUPS)) {
      for (i = 0; i < subrules[verbGroup].size(); i++) {
        subrule = (JCALL_VerbErrorSubRule) subrules[verbGroup].elementAt(i);
        if ((subrule.tense == ptense) && (subrule.politeness == ppoliteness) && (subrule.positive == psign)) {
          returnString = subrule.getrulepairstring(pverb);

          // Deal with the question part - this is the same for all verbs, and
          // all rules, so can be done here
          if (pquestion == CALL_formStruct.QUESTION) {
            if (ppoliteness == CALL_formStruct.POLITE) {
              // Polite
              returnString = returnString + " + か";
            } else {
              // Plain
              returnString = returnString + " + か";
            }
          }

          break;
        }
      }
    } else {
      logger.error("Verb group out of range, " + verbGroup + ", [" + pverb.getStrKanji() + "]");
    }

    return returnString;
  }

  // For debug
  public void dump_debug() {
  }
}