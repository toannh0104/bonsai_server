///////////////////////////////////////////////////////////////////
// Verb Rule Structures - holds the information about how to construct
//				  the various inflections for each verb group.
//				  All data is loaded from the verbRules.txt file.
//
///////////////////////////////////////////////////////////////////

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

import org.apache.log4j.Logger;

public class CALL_counterRulesStruct {
  static Logger logger = Logger.getLogger(CALL_counterRulesStruct.class.getName());

  // Defines
  static final String DEFAULT_COUNTER = new String("counter_tsu");

  static final int STATE_INITIAL = 0;
  static final int STATE_CATEGORY = 1;
  static final int STATE_END = 2;
  static final int STATE_ERROR = 3;

  // Fields
  Vector rules; // A vector of type CALL_counterRuleStruct, one for each counter
                // type
  CALL_database parent;

  // Empty Constructor
  public CALL_counterRulesStruct(CALL_database p) {
    parent = p;
    rules = new Vector();
  }

  public boolean loadRules(String filename) {
    boolean rc = true;
    boolean done = false;
    FileReader in;

    // Read States
    int readState = STATE_INITIAL;

    String commandString[];
    String tempString;
    String categoryString;
    String readLine;
    StringTokenizer st;

    int number;
    String kana;
    String kanji;

    CALL_counterRuleStruct newRule;

    // Open file
    try {
      InputStream inputStream = new FileInputStream(filename);
      Reader reader = new InputStreamReader(inputStream, "Shift-JIS");
      BufferedReader bufferedReader = new BufferedReader(reader);

      // Initialise data
      newRule = null;

      // Read Line by line
      while (readState < STATE_END) {

        readLine = bufferedReader.readLine();
        if (readLine == null) {
          // Prematurely reached end of file - stop reading
          readState = STATE_ERROR;
          break;
        } else if (readLine.startsWith("#")) {
          // A comment...skip
          continue;
        } else {
          // We have some valid text, so parse it
          // CALL_debug.printlog(CALL_debug.MOD_CRULES, CALL_debug.DEBUG,
          // "Parsing line [" + readLine + "]");

          if (readLine.startsWith("-eof")) {
            // EOF
            readState = STATE_END;
          }

          // Otherwise, refer to the state machine
          done = false;
          tempString = readLine;
          commandString = CALL_io.extractCommand(tempString);

          switch (readState) {
            case STATE_INITIAL:

              if (commandString[0] != null) {
                // We have a command of some sort, so parse it

                st = new StringTokenizer(commandString[0]);
                tempString = CALL_io.getNextToken(st);

                if (tempString.startsWith("category")) {
                  if (st.hasMoreTokens()) {
                    categoryString = CALL_io.strRemainder(st);
                    newRule = new CALL_counterRuleStruct(categoryString);
                    readState = STATE_CATEGORY;
                  }
                }
              }
              break;

            case STATE_CATEGORY:
              if (commandString[0] != null) {
                if (commandString[0].equals("/category")) {
                  if (newRule != null) {
                    rules.addElement(newRule);
                  }

                  readState = STATE_INITIAL;
                }
              } else if (commandString[1] != null) {
                if (newRule != null) {
                  st = new StringTokenizer(commandString[1]);
                  tempString = CALL_io.getNextToken(st);

                  if (tempString != null) {
                    number = CALL_io.str2int(tempString);
                    if (number != CALL_io.INVALID_INT) {
                      kana = CALL_io.getNextToken(st);
                      if (kana != null) {
                        kanji = CALL_io.getNextToken(st);
                        if (kanji != null) {
                          // We have all the information for a rule component
                          newRule.addCounter(number, kana, kanji);
                        }
                      }
                    }
                  }
                }
              }

              break;

            case STATE_END:
              // Do nothing
              break;

            case STATE_ERROR:
              // Do nothing
              break;
          }
        }

      }
      if (readState == STATE_ERROR) {
        return false;
      }

    } catch (IOException e) {
      // File does not exist
      // CALL_debug.printlog(CALL_debug.MOD_CRULES, CALL_debug.ERROR,
      // "Problem opening counter rule file");
      return false;
    }

    // All worked as expected
    print_debug();
    System.out.println("Counter rule:" + rules.size());
    return true;
  }

  // Returns a vector of Strings, the acceptable counters for the word
  // NOTE - only dealing with numbers 1 to 10 right now!!!
  public Vector getCounter(CALL_wordStruct word, int number, int outputType) {
    logger.debug("enter getCounter, word: " + word.kanji + " number: " + number);
    String tempString;
    CALL_counterRuleStruct rule;
    Vector returnV = new Vector();

    // Go through each of the categories in turn
    for (int i = 0; i < rules.size(); i++) {
      rule = (CALL_counterRuleStruct) rules.elementAt(i);
      if (rule != null) {
        if ((rule.category.equals(DEFAULT_COUNTER)) || (word.isInCategory(rule.category))) {
          logger.debug("Match category of rule: " + rule.toString());

          tempString = rule.getCounter(number, outputType);

          if (tempString != null) {
            returnV.addElement(tempString);
          }
        }
      }
    }

    logger.debug("Counter size: " + returnV.size());
    return returnV;
  }

  // public Vector getCounter(JCALL_Word word, int number, int outputType)
  // {
  //
  // String tempString;
  // CALL_counterRuleStruct rule;
  // Vector returnV = new Vector();
  //
  // // Go through each of the categories in turn
  // for(int i = 0; i < rules.size(); i++)
  // {
  // rule = (CALL_counterRuleStruct) rules.elementAt(i);
  // if(rule != null)
  // {
  // if((rule.category.equals(DEFAULT_COUNTER)) ||
  // (word.isInCategory(rule.category)))
  // {
  // tempString = rule.getCounter(number, outputType);
  // if(tempString != null)
  // {
  // returnV.addElement(tempString);
  // }
  // }
  // }
  // }
  // return returnV;
  // }

  public void print_debug() {
    CALL_counterRuleStruct rule;

    for (int i = 0; i < rules.size(); i++) {
      rule = (CALL_counterRuleStruct) rules.elementAt(i);
      if (rule != null) {
        rule.print_debug();
      }
    }
  }

  public static void main(String[] agrs) {
    CALL_counterRulesStruct rulesStruct = new CALL_counterRulesStruct(null);
    rulesStruct.loadRules("/Data/counter-rules.txt");
    System.out.println("111");

  }
}