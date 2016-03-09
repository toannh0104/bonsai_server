//////////////////////////////////////////////////////////////////////////////////////
// Adjective Rule Structures - holds the information about how to construct
//				  				the various inflections for adjective type.
//				  				All data is loaded from the adjective-rules.txt 
//								file.
//
///////////////////////////////////////////////////////////////////////////////////////

package jcall;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import jcall.db.JCALL_Word;

import org.apache.log4j.Logger;

public class CALL_adjectiveRulesStruct {
  static Logger logger = Logger.getLogger(CALL_adjectiveRulesStruct.class.getName());

  // Defines
  static final int ADJECTIVE_GROUPS = 3;

  static final int I_ADJECTIVE_GROUP = 1;
  static final int NA_ADJECTIVE_GROUP = 2;
  static final int OTHER_ADJECTIVE_GROUP = 3;

  static final int MAX_RESTRICTIONS = 32;
  private HttpServletRequest r;

  // Sub Structures
  Vector rules;

  // Fields
  boolean initialised;

  // Empty Constructor
  public CALL_adjectiveRulesStruct() {
    rules = new Vector();
    initialised = false;
  }

  // Load Constructor
  public CALL_adjectiveRulesStruct(String filename, HttpServletRequest request) {
    rules = new Vector();
    initialised = false;
    r = request;
    loadRules(filename);

  }

  // Load function
  public boolean loadRules(String filename) {
    CALL_adjectiveRuleStruct rule;
    CALL_adjectiveSubRuleStruct subrule;
    FileReader in;
    String tempString;
    String restriction;
    String readLine;
    StringTokenizer st;

    int currentgroup = -1;
    int readstatus = 0;
    int fieldState = 0;
    boolean rc = true;

    // Initialise, otherwise it gets humpty
    restriction = null;
    rule = null;
    subrule = null;

    // Open file
    try {
      InputStream inputStream = new FileInputStream(filename);
      Reader reader = new InputStreamReader(inputStream, "UTF8");
      BufferedReader bufferedReader = new BufferedReader(reader);
      while (readstatus == 0) {
        readLine = bufferedReader.readLine();
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
          // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
          // "Token: [" + tempString + "]",r);

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
            rule = new CALL_adjectiveRuleStruct(tempString);
            rules.addElement(rule);
            fieldState = 1;
            currentgroup = -1;
          } else if (tempString.startsWith("-i_adjectives")) {
            currentgroup = I_ADJECTIVE_GROUP;
            // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
            // "Changed to I-Adjective Group",r);
            fieldState = 2;
          } else if (tempString.startsWith("-na_adjectives")) {
            currentgroup = NA_ADJECTIVE_GROUP;
            // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
            // "Changed to NA-Adjective Group",r);
            fieldState = 2;
          } else if (tempString.startsWith("-other_adjectives")) {
            currentgroup = OTHER_ADJECTIVE_GROUP;
            // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
            // "Changed to Other Adjective Group",r);
            fieldState = 2;
          } else if (tempString.startsWith("-type")) {
            // Add a new rule
            if ((fieldState >= 2) && (currentgroup >= 1)) {
              int tense = -1;
              int politeness = -1;
              int positive = -1;

              // Parse line to get type information
              // If there are no tags, stay at default
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
                if (tempString.equals("neg")) {
                  positive = CALL_formStruct.NEGATIVE;
                }
                if (tempString.equals("pos")) {
                  positive = CALL_formStruct.POSITIVE;
                }
              }

              if (rule != null) {
                // We have a complete type definition, create the subrule
                subrule = new CALL_adjectiveSubRuleStruct(currentgroup, tense, politeness, positive);
                rule.subrules[(currentgroup - 1)].addElement(subrule);
                fieldState = 3;
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
                subrule.rules.addElement(newRule);
                restriction = null;
                fieldState = 3;
              }
            }
          }
        }

      }

      // Ok, we've finished
      initialised = true;

      // Debug, if level is set
      dump_debug();
    } catch (IOException e) {
      // File does not exist
      // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.ERROR,
      // "Problem opening adjective rule file [" + filename + "]",r);
      return false;
    }

    System.out.println("Adj:" + rules.size());
    return rc;
  }

  // This is a flexible adjective form search - returns a vector of
  // CALL_wordWithForm structures
  // Word: The adjective to be expaned. Must not be null.
  // Rname: The name of the rule to be used. If null, use all rules.
  // Sign: The adjective sign. If -1, use all signs.
  // Tense: The adjective tense. If -1, use all tenses.
  // Style: The adjective politeness. If -1, use all styles.
  // Question: Whether a question or not. If -1, use both options
  public Vector getAdjectiveForms(CALL_wordStruct word, String rname, int sign, int tense, int style, int type) {
    String adjectiveStringKanji;
    String adjectiveStringKana;
    String ruleString;
    CALL_adjectiveRuleStruct rule;
    CALL_wordWithFormStruct newWord;

    Vector rVector = new Vector();

    if (word != null) {

      // Go through all rules (if unspecified)
      for (int a = 0; a < rules.size(); a++) {
        rule = (CALL_adjectiveRuleStruct) rules.elementAt(a);
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
                            adjectiveStringKanji = applyRule(word, ruleString, b, c, d, e, true, false);
                            adjectiveStringKana = applyRule(word, ruleString, b, c, d, e, false, false);
                            newWord = new CALL_wordWithFormStruct(adjectiveStringKanji, adjectiveStringKana, word,
                                ruleString, b, c, d, e);
                            if (newWord != null) {
                              if (newWord.complete) {
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
  public String applyRule(CALL_wordStruct word, String rname, int sign, int tense, int politeness, int question,
      boolean kanji) {
    return applyRule(word, rname, sign, tense, politeness, question, kanji, true);
  }

  // The method get the string for a specific adjective given a specific
  // situation
  public String applyRule(CALL_wordStruct word, String rname, int sign, int tense, int politeness, int question,
      boolean kanji, boolean warning) {

    CALL_adjectiveRuleStruct rule;
    String ruleStr = null;
    String surfaceForm = null;
    String tokenString = null;
    String tempString = null;
    StringTokenizer st;
    String c1, c2;
    char wildcard = '-';

    int ruleState = 0; // 0: Initial, 1: Creating, 2: Subtraction , 3: Append
    int i, index;

    // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
    // "Adjective: [" + word.kanji + "], rule: [" + rname + "]");

    logger.debug("Adjective: [" + word.kanji + "], searching rule: [" + rname + "]");

    // Find rule
    logger.debug("rules.size(): " + rules.size());

    for (i = 0; i < rules.size(); i++) {
      rule = (CALL_adjectiveRuleStruct) rules.elementAt(i);

      if (rule != null) {
        logger.debug("rule.name == " + rule.name);

        if (rule.name.equals(rname)) {
          logger.debug("Found the rule, now continue to find the instance");
          // Found the rule, now find the instance
          ruleStr = rule.getRuleString(word, sign, tense, politeness, question);

          break;
        }
      }
    }

    // Parse rule
    if (ruleStr != null) {
      // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
      // "Using rule string: [" + ruleStr + "]");

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
                  surfaceForm = word.kana;
                } else {
                  surfaceForm = word.kanji;
                }
                // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
                // "Initial base: [" + surfaceForm + "]");
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
                // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
                // "Into Addition Mode");
              } else if (tokenString.equals("-")) {
                // Switch to append mode
                wildcard = '-';
                // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
                // "Into Subtraction Mode");
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
                  // CALL_debug.printlog(CALL_debug.MOD_ARULES,
                  // CALL_debug.DEBUG, "Post Subtraction: [" + surfaceForm +
                  // "]");
                  // CALL_debug.printlog(CALL_debug.MOD_ARULES,
                  // CALL_debug.DEBUG, "Wildcard: [" + wildcard + "]");
                }
              } else {
                // A straight substitution
                if (surfaceForm != null) {
                  if (surfaceForm.endsWith(tokenString)) {
                    index = surfaceForm.length() - tokenString.length();
                    tempString = surfaceForm.substring(0, index);
                    surfaceForm = tempString;
                    // CALL_debug.printlog(CALL_debug.MOD_ARULES,
                    // CALL_debug.DEBUG, "Post Subtraction: [" + surfaceForm +
                    // "]");
                    // CALL_debug.printlog(CALL_debug.MOD_ARULES,
                    // CALL_debug.DEBUG, "No wildcard");
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
                      // CALL_debug.printlog(CALL_debug.MOD_ARULES,
                      // CALL_debug.DEBUG, "Romaji: [" + romaji + "]");
                      newStart = CALL_io.kanaLookup(romaji, 0);
                      // CALL_debug.printlog(CALL_debug.MOD_ARULES,
                      // CALL_debug.DEBUG, "New start to append: [" + newStart +
                      // "]");
                      tempString = tempString.substring(1, tempString.length());
                      tempString = newStart + tempString;
                    }
                  }
                }

                // Finally create surface string
                surfaceForm = surfaceForm + tempString;
                // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
                // "Post Addition: [" + surfaceForm + "]");
              } else {
                // A straight append
                surfaceForm = surfaceForm + tokenString;
                // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
                // "Post Addition: [" + surfaceForm + "]");
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
        // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.WARN,
        // "Rule not found to match adjective [" + word.kanji + "]");
        // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.WARN,
        // "Rule name: " + rname);
        // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.WARN, "Sign:" +
        // sign);
        // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.WARN, "Tense:"
        // + tense);
        // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.WARN,
        // "Politeness:" + politeness);
        // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.WARN,
        // "Question:" + question);
      }
    }

    return surfaceForm;
  }

  // For debug
  public void dump_debug() {
    int i, j, k, l;
    CALL_adjectiveRuleStruct rule;
    CALL_adjectiveSubRuleStruct subrule;
    CALL_rulePairStruct rRule;

    // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.INFO,
    // "Printing adjective rule set to log");

    for (i = 0; i < rules.size(); i++) {
      rule = (CALL_adjectiveRuleStruct) rules.elementAt(i);
      if (rule != null) {
        // Rule information
        // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.INFO,
        // "Adjective Rule: " + rule.name);

        // Sub Rule Information
        for (j = 0; j < ADJECTIVE_GROUPS; j++) {
          // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.INFO,
          // "<Group " + (j + 1) + ">");

          for (k = 0; k < rule.subrules[j].size(); k++) {
            subrule = (CALL_adjectiveSubRuleStruct) rule.subrules[j].elementAt(k);
            if (subrule != null) {
              // Show the sub rule settings
              // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.INFO,
              // "Subrule [" + (k + 1) + "]");
              // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.INFO,
              // "Tense: " + subrule.tense);
              // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.INFO,
              // "Style: " + subrule.politeness);
              // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.INFO,
              // "Positive: " + subrule.positive);

              // Default Rule
              // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.INFO,
              // "Default Rule: " + subrule.default_rule);

              // Restricted rules
              for (l = 0; l < subrule.rules.size(); l++) {
                rRule = (CALL_rulePairStruct) subrule.rules.elementAt(l);
                // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.INFO,
                // "Restriction: [" + rRule.restriction + "]");
                // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.INFO,
                // "-> Rule: [" + rRule.rule + "]");
              }
            }
          }
        }
      }
    }
  }

  // This is a flexible adjective form search - returns a vector of
  // CALL_wordWithForm structures
  // Word: The adjective to be expaned. Must not be null.
  // Rname: The name of the rule to be used. If null, use all rules.
  // Sign: The adjective sign. If -1, use all signs.
  // Tense: The adjective tense. If -1, use all tenses.
  // Style: The adjective politeness. If -1, use all styles.
  // Question: Whether a question or not. If -1, use both options
  public Vector getAdjectiveForms(JCALL_Word word, String rname, int sign, int tense, int style, int type) {
    String adjectiveStringKanji;
    String adjectiveStringKana;
    String ruleString;
    CALL_adjectiveRuleStruct rule;
    CALL_wordWithFormStruct newWord;

    Vector rVector = new Vector();

    if (word != null) {

      // Go through all rules (if unspecified)
      for (int a = 0; a < rules.size(); a++) {
        rule = (CALL_adjectiveRuleStruct) rules.elementAt(a);
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
                            adjectiveStringKanji = applyRule(word, ruleString, b, c, d, e, true, false);
                            adjectiveStringKana = applyRule(word, ruleString, b, c, d, e, false, false);
                            newWord = new CALL_wordWithFormStruct(adjectiveStringKanji, adjectiveStringKana, word,
                                ruleString, b, c, d, e);
                            if (newWord != null) {
                              if (newWord.complete) {
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
  public String applyRule(JCALL_Word word, String rname, int sign, int tense, int politeness, int question,
      boolean kanji) {
    return applyRule(word, rname, sign, tense, politeness, question, kanji, true);
  }

  // The method get the string for a specific adjective given a specific
  // situation
  public String applyRule(JCALL_Word word, String rname, int sign, int tense, int politeness, int question,
      boolean kanji, boolean warning) {
    CALL_adjectiveRuleStruct rule;
    String ruleStr = null;
    String surfaceForm = null;
    String tokenString = null;
    String tempString = null;
    StringTokenizer st;
    String c1, c2;
    char wildcard = '-';

    int ruleState = 0; // 0: Initial, 1: Creating, 2: Subtraction , 3: Append
    int i, index;

    // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
    // "Adjective: [" + word.getStrKanji() + "], rule: [" + rname + "]");

    // Find rule
    for (i = 0; i < rules.size(); i++) {
      rule = (CALL_adjectiveRuleStruct) rules.elementAt(i);
      if (rule != null) {
        if (rule.name.equals(rname)) {
          // Found the rule, now find the instance
          ruleStr = rule.getRuleString(word, sign, tense, politeness, question);
          break;
        }
      }
    }

    // Parse rule
    if (ruleStr != null) {
      // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
      // "Using rule string: [" + ruleStr + "]");

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
                  surfaceForm = word.getStrKana();
                } else {
                  surfaceForm = word.getStrKanji();
                }
                // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
                // "Initial base: [" + surfaceForm + "]");
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
                // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
                // "Into Addition Mode");
              } else if (tokenString.equals("-")) {
                // Switch to append mode
                wildcard = '-';
                // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
                // "Into Subtraction Mode");
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
                  // CALL_debug.printlog(CALL_debug.MOD_ARULES,
                  // CALL_debug.DEBUG, "Post Subtraction: [" + surfaceForm +
                  // "]");
                  // CALL_debug.printlog(CALL_debug.MOD_ARULES,
                  // CALL_debug.DEBUG, "Wildcard: [" + wildcard + "]");
                }
              } else {
                // A straight substitution
                if (surfaceForm != null) {
                  if (surfaceForm.endsWith(tokenString)) {
                    index = surfaceForm.length() - tokenString.length();
                    tempString = surfaceForm.substring(0, index);
                    surfaceForm = tempString;
                    // CALL_debug.printlog(CALL_debug.MOD_ARULES,
                    // CALL_debug.DEBUG, "Post Subtraction: [" + surfaceForm +
                    // "]");
                    // CALL_debug.printlog(CALL_debug.MOD_ARULES,
                    // CALL_debug.DEBUG, "No wildcard");
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
                      // CALL_debug.printlog(CALL_debug.MOD_ARULES,
                      // CALL_debug.DEBUG, "Romaji: [" + romaji + "]");
                      newStart = CALL_io.kanaLookup(romaji, 0);
                      // CALL_debug.printlog(CALL_debug.MOD_ARULES,
                      // CALL_debug.DEBUG, "New start to append: [" + newStart +
                      // "]");
                      tempString = tempString.substring(1, tempString.length());
                      tempString = newStart + tempString;
                    }
                  }
                }

                // Finally create surface string
                surfaceForm = surfaceForm + tempString;
                // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
                // "Post Addition: [" + surfaceForm + "]");
              } else {
                // A straight append
                surfaceForm = surfaceForm + tokenString;
                // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.DEBUG,
                // "Post Addition: [" + surfaceForm + "]");
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
        // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.WARN,
        // "Rule not found to match adjective [" + word.getStrKanji() + "]");
        // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.WARN,
        // "Rule name: " + rname);
        // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.WARN, "Sign:" +
        // sign);
        // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.WARN, "Tense:"
        // + tense);
        // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.WARN,
        // "Politeness:" + politeness);
        // CALL_debug.printlog(CALL_debug.MOD_ARULES, CALL_debug.WARN,
        // "Question:" + question);
      }
    }

    return surfaceForm;
  }

  public static void main(String[] args) throws IOException {

    CALL_adjectiveRulesStruct adjRules = new CALL_adjectiveRulesStruct();
    String adjRuleFileName = "/Data/adjective-rules.txt";
    adjRules.loadRules(adjRuleFileName);
    System.out.println("1");
  }

}