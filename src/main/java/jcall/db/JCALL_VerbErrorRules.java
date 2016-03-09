/**
 * Created on 2008/03/12
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;

import jcall.CALL_database;
import jcall.CALL_formStruct;
import jcall.CALL_io;
import jcall.CALL_rulePairStruct;
import jcall.CALL_wordWithFormStruct;

import org.apache.log4j.Logger;

public class JCALL_VerbErrorRules {

  static Logger logger = Logger.getLogger(JCALL_VerbErrorRules.class.getName());

  // Defines
  static final int VERB_GROUPS = 5;

  // Sub Structures
  // Vector rules;
  private static Vector<JCALL_VerbErrorRule> rules = null;
  private static JCALL_VerbErrorRules verberrorrules;

  // Fields
  // private boolean initialised;

  // Empty Constructor
  private JCALL_VerbErrorRules() {
    // rules = new Vector();
    // initialised = false;
    init();
  }

  private void init() {

    loadRules(CALL_database.VerbErrorRuleFile);

  }

  // Load Constructor
  // public JCALL_VerbErrorRules(String filename)
  // {
  // initialised = false;
  // loadRules(filename);
  // }

  private void addObject(JCALL_VerbErrorRule obj) {
    if (rules == null) {
      rules = new Vector<JCALL_VerbErrorRule>();
    }
    rules.addElement(obj);
  }

  public static JCALL_VerbErrorRules getInstance() {
    if (verberrorrules == null) {
      verberrorrules = new JCALL_VerbErrorRules();
    }
    return verberrorrules;
  }

  // Load function
  private void loadRules(String filename) {
    JCALL_VerbErrorRule rule;
    JCALL_VerbErrorSubRule subrule;
    FileReader in;
    String tempString;
    String restriction;
    String readLine;
    StringTokenizer st;

    int currentgroup = -1;
    int readstatus = 0;
    int fieldState = 0;
    // boolean rc = true;

    // Initialise, otherwise it gets humpty
    restriction = null;
    rule = null;
    subrule = null;

    rules = new Vector<JCALL_VerbErrorRule>();

    // Open file
    try {

      InputStream inputStream = new FileInputStream(filename);
      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "SJIS"));

      while (readstatus == 0) {

        readLine = br.readLine();

        if (readLine == null) {
          // Reached end of file - stop reading
          readstatus = 1;
        } else if (readLine.startsWith("#")) {
          // A comment...skip
          continue;
        } else {

          // We have some valid text, so parse it
          st = new StringTokenizer(readLine);
          tempString = st.nextToken();

          // Debug
          // logger.debug("Token: [" + tempString + "]");

          if (tempString.startsWith("-eof")) {
            // EOF marker
            readstatus = 1;
          } else if (tempString.startsWith("-name")) {
            // The start of a new rule!

            // Get name
            if (!st.hasMoreTokens())
              continue;
            tempString = st.nextToken();

            // Create the rule structure
            rule = new JCALL_VerbErrorRule(tempString);
            addObject(rule);
            fieldState = 1;
            currentgroup = -1;
          } else if (tempString.startsWith("-group")) {
            // Reset a couple of the variables
            restriction = null;
            subrule = null;

            if (fieldState >= 1) {
              // Get current group to work with
              tempString = CALL_io.getNextToken(st);
              if (tempString != null) {
                currentgroup = CALL_io.str2int(tempString);
                // logger.debug( "Changed to Verb Group " + currentgroup);
                fieldState = 2;
              } else {
                logger.warn("Rule Creation: Invalid verb group number");
                fieldState = 1;
                currentgroup = -1;
              }
            }
          } else if (tempString.startsWith("-type")) {
            // Add a new rule
            if ((fieldState >= 2) && (currentgroup >= 1)) {
              int tense = -1;
              int politeness = -1;
              int positive = -1;

              // Parse line to get type information
              for (;;) {
                tempString = CALL_io.getNextToken(st);
                if ((tempString == null) || tempString.equals(" ")) {
                  // No more information on this line
                  break;
                }
                if (tempString.equals("past")) {
                  tense = CALL_formStruct.PAST;
                }
                if (tempString.equals("present")) {
                  tense = CALL_formStruct.PRESENT;
                }
                if (tempString.equals("plain")) {
                  politeness = CALL_formStruct.PLAIN;
                }
                if (tempString.equals("polite")) {
                  politeness = CALL_formStruct.POLITE;
                }
                if (tempString.equals("humble")) {
                  politeness = CALL_formStruct.HUMBLE;
                }
                if (tempString.equals("super-polite")) {
                  politeness = CALL_formStruct.SUPER_POLITE;
                }
                if (tempString.equals("crude")) {
                  politeness = CALL_formStruct.CRUDE;
                }
                if (tempString.equals("neg")) {
                  positive = CALL_formStruct.NEGATIVE;
                }
                if (tempString.equals("pos")) {
                  positive = CALL_formStruct.POSITIVE;
                }
              }

              if ((tense != -1) && (politeness != -1) && (positive != -1)) {
                if (rule != null) {
                  // We have a complete type definition, create the subrule
                  subrule = new JCALL_VerbErrorSubRule(currentgroup, tense, politeness, positive);
                  rule.subrules[(currentgroup - 1)].addElement(subrule);
                  fieldState = 3;
                }
              }
            }
          } else if (tempString.startsWith("-case")) {
            if (fieldState == 3) {
              // Add a subrule to the current rule
              fieldState = 4;
              restriction = CALL_io.strRemainder(st);
            }
          } else if (tempString.startsWith("-rule")) {
            // Get rest of rule
            tempString = CALL_io.strRemainder(st);
            if (tempString != null) {
              // Add the specific rule definitions within the subrule
              if (fieldState == 3) {
                // The default rule
                subrule.default_rule = tempString;
              } else if (fieldState == 4) {
                // Within a restricted case
                CALL_rulePairStruct newRule = new CALL_rulePairStruct(restriction, tempString);
                subrule.rulepairs.addElement(newRule);
                restriction = null;
                fieldState = 3;
              }
            }
          }
        }

      }

    } catch (IOException e) {
      // File does not exist
      logger.error("Problem opening verb rule file [" + filename + "]");
      // return false;
      return;
    }

    // Read Line by line

    // Ok, we've finished
    // initialised = true;

    // Debug, if level is set
    // dump_debug();

    // return rc;
    // System.out.println(rules.size());
  }

  // This is a flexible verb form search - returns a vector of CALL_wordWithForm
  // structures
  // Verb: The verb to be expaned. Must not be null.
  // Rname: The name of the rule to be used. If null, use all rules.
  // Sign: The verb sign. If -1, use all signs.
  // Tense: The verb tense. If -1, use all tenses.
  // Style: The verb politeness. If -1, use all styles.
  // Question: Whether a question or not. If -1, use both options

  public Vector<CALL_wordWithFormStruct> getVerbErrorForms(JCALL_Word verb, String rname, int sign, int tense,
      int style, int type) {

    // System.out.println("enter getVerbErrorForms");

    String verbStringKanji;
    String verbStringKana;
    String ruleString;
    JCALL_VerbErrorRule rule;
    CALL_wordWithFormStruct newWord;

    Vector<CALL_wordWithFormStruct> rVector = new Vector<CALL_wordWithFormStruct>();

    if (verb != null) {
      // Go through all rules (if unspecified)
      for (int a = 0; a < rules.size(); a++) {
        rule = rules.elementAt(a);
        if (rule != null) {
          ruleString = rule.name;
          if ((rname == null) || (rname.equals(ruleString))) {
            // Use this rule
            // Consider the sign
            for (int b = 0; b < CALL_formStruct.SIGN_FORMS; b++) {
              if ((b == sign) || (sign == -1)) {
                // Consider the tense
                for (int c = 0; c < CALL_formStruct.TENSE_FORMS; c++) {
                  if ((c == tense) || (tense == -1)) {
                    // Consider the style
                    for (int d = 0; d < CALL_formStruct.STYLE_FORMS; d++) {
                      if ((d == style) || (style == -1)) {
                        // Consider the type
                        for (int e = 0; e < CALL_formStruct.TYPE_FORMS; e++) {
                          if ((e == type) || (type == -1)) {
                            verbStringKanji = applyRule(verb, ruleString, b, c, d, e, true, false);
                            verbStringKana = applyRule(verb, ruleString, b, c, d, e, false, false);
                            newWord = new CALL_wordWithFormStruct(verbStringKanji, verbStringKana, verb, ruleString, b,
                                c, d, e);
                            if (newWord != null) {
                              if (newWord.isComplete()) {
                                rVector.addElement(newWord);
                              }
                            }
                          }
                        }

                      }
                    }
                  }
                }

              }
            }
          }
        }
      }
    }

    return rVector;
  }

  // For compatibility (the version without the warning flag)
  public String applyRule(JCALL_Word verb, String rname, int sign, int tense, int politeness, int question,
      boolean kanji) {
    return applyRule(verb, rname, sign, tense, politeness, question, kanji, true);
  }

  // The method get the string for a specific verb given a specific situation
  public String applyRule(JCALL_Word verb, String rname, int sign, int tense, int politeness, int question,
      boolean kanji, boolean warning) {
    JCALL_VerbErrorRule rule;
    String ruleStr = null;
    String surfaceForm = null;
    String tokenString = null;
    String tempString = null;
    StringTokenizer st;
    String c1, c2;
    char wildcard = '-';

    int ruleState = 0; // 0: Initial, 1: Creating, 2: Subtraction , 3: Append
    int i, index;

    logger.debug("Verb: [" + verb.getStrKanji() + "], rule: [" + rname + "]");

    // Find rule
    for (i = 0; i < rules.size(); i++) {
      rule = rules.elementAt(i);
      if (rule != null) {
        if (rule.name.equals(rname)) {
          // Found the rule, now find the instance
          ruleStr = rule.getRuleString(verb, sign, tense, politeness, question);
          break;
        }
      }
    }

    // Parse rule
    if (ruleStr != null) {
      logger.debug("Using rule string: [" + ruleStr + "]");
      // Process the rule
      st = new StringTokenizer(ruleStr);
      for (;;) {
        tokenString = CALL_io.getNextToken(st);
        if (tokenString != null) {
          switch (ruleState) {
            case 0:
              // Initial state - expecting indictation on base form
              if (tokenString.equals("[dic]")) {
                if (!kanji) {
                  surfaceForm = verb.getStrKana();
                } else {
                  surfaceForm = verb.getStrKanji();
                }
                logger.debug("Initial base: [" + surfaceForm + "]");
                ruleState = 1;
              } else {
                // Just use the word given (this is used for kure - korareru,
                // etc)
                surfaceForm = tokenString;
                ruleState = 1;
              }
              break;
            case 1:
              // Creation state - expecting an operator
              if (tokenString.equals("+")) {
                // Switch to append mode
                ruleState = 3;
                logger.debug("Into Addition Mode");
              } else if (tokenString.equals("-")) {
                // Switch to append mode
                wildcard = '-';
                logger.debug("Into Subtraction Mode");
                ruleState = 2;
              }
              break;
            case 2:
              // Subtraction mode - looking for some form to subtract from
              // string
              if (tokenString.startsWith("*")) {
                // It's a wild card subtraction
                tempString = tokenString.substring(1, tokenString.length());

                if (tempString.length() < surfaceForm.length()) {

                  c1 = CALL_io.strKanaToRomaji(tempString);
                  c2 = CALL_io.strKanaToRomaji(surfaceForm);

                  // Ok, we have a good match - remove last character, but store
                  // it for reference
                  index = surfaceForm.length() - tempString.length();
                  wildcard = surfaceForm.charAt(index);
                  tempString = surfaceForm.substring(0, index);
                  surfaceForm = tempString;
                  logger.debug("Post Subtraction: [" + surfaceForm + "]");
                  logger.debug("Wildcard: [" + wildcard + "]");
                }
              } else {
                // A straight substitution
                if (surfaceForm != null) {
                  if (surfaceForm.endsWith(tokenString)) {
                    index = surfaceForm.length() - tokenString.length();
                    tempString = surfaceForm.substring(0, index);
                    surfaceForm = tempString;
                    logger.debug("Post Subtraction: [" + surfaceForm + "]");
                    logger.debug("No wildcard");
                  }
                }
              }
              ruleState = 1;
              break;
            case 3:
              // Append state - looking for some form to append to string
              if (tokenString.startsWith("*")) {
                // It's a wild card append
                tempString = tokenString.substring(1, tokenString.length());
                if (wildcard != '-') {
                  // We have a wildcard, so modify the start of appended string
                  char modType = CALL_io.kanaConstClass(wildcard);
                  char vowelType = CALL_io.kanaVowelClass(tempString.charAt(0));
                  String newStart;

                  if (modType != '-') {
                    if (vowelType != '-') {
                      String romaji = new String();
                      romaji = "" + modType + vowelType;
                      logger.debug("Romaji: [" + romaji + "]");
                      newStart = CALL_io.kanaLookup(romaji, 0);
                      logger.debug("New start to append: [" + newStart + "]");
                      tempString = tempString.substring(1, tempString.length());
                      tempString = newStart + tempString;
                    }
                  }
                }

                // Finally create surface string
                surfaceForm = surfaceForm + tempString;
                logger.debug("Post Addition: [" + surfaceForm + "]");
              } else {
                // A straight append
                surfaceForm = surfaceForm + tokenString;
                logger.debug("Post Addition: [" + surfaceForm + "]");
              }
              ruleState = 1;
              break;
            case -1:
              // We had an error somewhere...no progress from here on;
              break;
            default:
              // Hopefully never get here!
              break;
          }
        } else {
          // No more tokens
          break;
        }

      }
    } else {
      if (warning) {
        // We don't warn when searching all possible form combinations, as some
        // will not exist and this is ok
        logger.debug("Rule not found to match verb [" + verb.getStrKanji() + "]");
        logger.debug("Rule name: " + rname);
        logger.debug("Sign:" + sign);
        logger.debug("Tense:" + tense);
        logger.debug("Politeness:" + politeness);
        logger.debug("Question:" + question);
      }
    }

    return surfaceForm;
  }

  // For debug
  public void dump_debug() {
    int i, j, k, l;
    JCALL_VerbErrorRule rule;
    JCALL_VerbErrorSubRule subrule;
    CALL_rulePairStruct rRule;

    // CALL_debug.printlog(CALL_debug.MOD_VRULES, CALL_debug.INFO,
    // "Printing rule set to log");

    for (i = 0; i < rules.size(); i++) {
      rule = rules.elementAt(i);
      if (rule != null) {
        // Rule information
        // CALL_debug.printlog(CALL_debug.MOD_VRULES, CALL_debug.INFO,
        // "Verb Rule: " + rule.name);

        // Sub Rule Information
        for (j = 0; j < VERB_GROUPS; j++) {
          // CALL_debug.printlog(CALL_debug.MOD_VRULES, CALL_debug.INFO,
          // "<Group " + (j + 1) + ">");

          for (k = 0; k < rule.subrules[j].size(); k++) {
            subrule = (JCALL_VerbErrorSubRule) rule.subrules[j].elementAt(k);
            if (subrule != null) {
              // Show the sub rule settings
              // CALL_debug.printlog(CALL_debug.MOD_VRULES, CALL_debug.INFO,
              // "Subrule [" + (k + 1) + "]");
              // CALL_debug.printlog(CALL_debug.MOD_VRULES, CALL_debug.INFO,
              // "Tense: " + subrule.tense);
              // CALL_debug.printlog(CALL_debug.MOD_VRULES, CALL_debug.INFO,
              // "Style: " + subrule.politeness);
              // CALL_debug.printlog(CALL_debug.MOD_VRULES, CALL_debug.INFO,
              // "Positive: " + subrule.positive);

              // Default Rule
              // CALL_debug.printlog(CALL_debug.MOD_VRULES, CALL_debug.INFO,
              // "Default Rule: " + subrule.default_rule);

              // Restricted rules
              for (l = 0; l < subrule.rulepairs.size(); l++) {
                rRule = (CALL_rulePairStruct) subrule.rulepairs.elementAt(l);
                // CALL_debug.printlog(CALL_debug.MOD_VRULES, CALL_debug.INFO,
                // "Restriction: [" + rRule.getRestriction() + "]");
                // CALL_debug.printlog(CALL_debug.MOD_VRULES, CALL_debug.INFO,
                // "-> Rule: [" + rRule.getRule() + "]");
              }
            }
          }
        }
      }
    }
  }

  public static void main(String[] args) throws SQLException, IOException {
    // TODO Auto-generated method stub
    JCALL_VerbErrorRules errorRules = new JCALL_VerbErrorRules();
    String filename = "/Data/verberror-rules.txt";
    errorRules.loadRules(filename);
    System.out.println("1");
  }

}