///////////////////////////////////////////////////////////////////
// Grammar Rule Structure
//

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

public class CALL_grammarRulesStruct {

  static Logger logger = Logger.getLogger(CALL_grammarRulesStruct.class.getName());
  // Fields
  Vector rules; // A list of all top level grammar rules (represented using
                // CALL_grammarRuleStruct)

  public CALL_grammarRulesStruct(CALL_database p) {
    rules = new Vector();
  }

  // Load the concept defintions
  public boolean loadRules(String filename) {
    boolean rc = true;
    FileReader in;

    String tempString;
    String readLine;
    StringTokenizer st;
    String commandString[];

    boolean finished = false;

    CALL_grammarRuleStruct newRule = null;

    // Open file
    try {
      InputStream inputStream = new FileInputStream(filename);
      Reader reader = new InputStreamReader(inputStream, "UTF8");
      BufferedReader bufferedReader = new BufferedReader(reader);

      while (!finished) {

        readLine = bufferedReader.readLine();
        // || readLine.length()==0
        if (readLine == null) {
          // Prematurely reached end of file - error, stop reading
          // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.ERROR,
          // "Null line in grammar rules file");
          rc = false;
          finished = true;
        } else if (readLine.startsWith("#")) {
          // A comment...skip
          continue;
        } else if (readLine.startsWith("-eof")) {
          rc = true;
          finished = true;
        } else {
          readLine = readLine.trim();

          // We have some valid text, so parse it
          // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
          // "Parsing line [" + readLine + "]");

          // Get the command,
          commandString = CALL_io.extractCommand(readLine);

          // Looking for a grammar rule
          if (commandString[0] != null) // commandString[0] is command between
                                        // in "<>"
          {
            st = new StringTokenizer(commandString[0]);
            tempString = CALL_io.getNextToken(st);

            if (tempString.equals("rule")) {
              if (st.hasMoreTokens()) {
                tempString = CALL_io.strRemainder(st);

                // Top-level rule: Parent = null
                newRule = load_rule(tempString, bufferedReader, null);

                if (newRule != null) {
                  // Loaded a new rule (and any sub-rules). Add to list
                  rules.addElement(newRule);
                } else {
                  // Had some error, abort
                  // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                  // CALL_debug.ERROR, "Failed to load rule [" + tempString +
                  // "]");
                  rc = false;
                  finished = true;
                }
              } else {
                // Error - rule has no name
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.ERROR,
                // "No further tokens in rule string");
                rc = false;
                finished = true;
              }
            }
          }
        }

      }

    } catch (IOException e) {
      // File does not exist
      // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.ERROR,
      // "Problem opening grammar rules file");
      return false;
    }

    // Read Line by line
    while (!finished) {
    }

    if (rc) {
      // Debug the grammar rules
      print_debug();
    }
    System.out.println("Grammar:" + rules.size());
    return rc;
  }

  // Load sub-rule - this is required as we need recursion
  public CALL_grammarRuleStruct load_rule(String name, BufferedReader bufferReader, CALL_grammarRuleStruct parent) {
    // Read State
    // NOTE: ERROR AND END STATES MUST BE THE HIGHEST NUMBERED (i.e. At the end
    // of the following list)!
    final int initState = 0;
    final int formState = 1;
    final int formSettingState = 2;
    final int formConditionalState = 3;
    final int templateState = 4;
    final int restrictState = 5;
    final int verbRuleState = 6;
    final int adjectiveRuleState = 7;
    final int verbRestrictState = 8;
    final int counterRuleState = 9;
    final int counterFormState = 10;
    final int errorState = 11;
    final int endState = 12;

    // Current State
    int readState = initState;
    CALL_stack stateHistory;

    String tempString = null;
    String commandString = null;
    String dataString = null;
    String readLine;
    StringTokenizer st;
    String parsedString[];

    String conditionRestrictionString = null;
    String formRestrictionString = null;
    String restrictionString = null;
    String verbRuleString = null;
    String verbRuleFormString = null;
    String adjRuleString = null;
    String adjRuleFormString = null;
    String formString = null;

    CALL_grammarRuleStruct newRule = null;
    CALL_grammarRuleStruct subRule = null;
    CALL_sentenceTemplateStruct newTemplate = null;

    CALL_stringPairsStruct formRestrictPairs = null;
    CALL_stringPairsStruct formConditionPairs = null;

    CALL_formStruct formSettingsStruct = null;

    Vector formVector = null;
    String formName = null;

    stateHistory = new CALL_stack();
    stateHistory.push((Object) (new Integer(initState)));

    // Create the rule - note that the name string may also include parameters!
    st = new StringTokenizer(name);
    tempString = CALL_io.getNextToken(st);
    newRule = new CALL_grammarRuleStruct(tempString);

    while (st.hasMoreTokens()) {
      // The rule allows parameters
      tempString = CALL_io.getNextToken(st);
      newRule.addParameter(tempString);
    }

    newRule.parent = parent;

    // Read Line by line
    while (readState < errorState) {
      try {
        if (dataString == null) {
          readLine = bufferReader.readLine().trim();
        } else {
          // Continue from data left over
          readLine = dataString;
        }

        if (readLine.startsWith("#")) {
          // A comment...skip
          continue;
        } else if (readLine.startsWith("-eof")) {
          // Error - Unexpected end of file (mid rule!)
          readState = errorState;
        } else {
          // We have some valid text, so parse it
          // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
          // "Parsing line [" + readLine + "]");

          // Get the command and or data strings
          parsedString = CALL_io.extractCommand(readLine);
          commandString = parsedString[0];
          dataString = parsedString[1];

          // Use the state machine from this point onwards
          // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
          // "Read state: " + readState);

          switch (readState) {
            case initState:
              // Within a grammar rule
              if (commandString != null) {
                st = new StringTokenizer(commandString);
                tempString = CALL_io.getNextToken(st);

                if (tempString.equals("/rule")) {
                  // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                  // CALL_debug.DEBUG, "Completed rule [" + newRule.name + "]");
                  readState = endState;
                } else if (tempString.equals("description")) {
                  if (dataString != null) {
                    newRule.description = dataString;
                    dataString = null;
                  }

                } else if (tempString.equals("template")) {
                  stateHistory.push((Object) (new Integer(readState)));
                  readState = templateState;
                } else if (tempString.equals("form")) {
                  // We have a form specification for this rule
                  if (st.hasMoreTokens()) {
                    formName = CALL_io.strRemainder(st);
                    formVector = new Vector();

                    stateHistory.push((Object) (new Integer(readState)));
                    readState = formState;
                  }
                } else if (tempString.equals("rule")) {
                  // Load a sub rule
                  if (st.hasMoreTokens()) {
                    tempString = CALL_io.strRemainder(st);
                    subRule = load_rule(tempString, bufferReader, newRule);
                    if (subRule != null) {
                      // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                      // CALL_debug.DEBUG, "Adding rule [" + subRule.name +
                      // "] to [" + newRule.name + "]");
                      newRule.subRules.addElement(subRule);
                    } else {
                      // Error loading subrule
                      // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                      // CALL_debug.ERROR, "Failed to load subrule [" +
                      // tempString + "]");
                      readState = errorState;
                      break;
                    }
                  }
                }
              }
              break;

            case formState:

              if ((formName == null) || (formVector == null)) {
                // Error
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.ERROR,
                // "Failed to load rule form");
                readState = errorState;
              } else if (commandString != null) {
                st = new StringTokenizer(commandString);
                tempString = CALL_io.getNextToken(st);

                if (tempString.equals("/form")) {
                  // End of form definition - add this form set
                  newRule.formSettings.addElement(formVector);
                  newRule.formNames.addElement(formName);

                  // A bit of debugging
                  // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                  // CALL_debug.DEBUG, "Added the new form, " + formName);

                  // Reset the variables
                  formVector = null;
                  formName = null;

                  // Then pop back up to the previous state
                  readState = ((Integer) stateHistory.pop()).intValue();
                } else if (tempString.equals("setting")) {
                  // Create new settings object
                  formSettingsStruct = new CALL_formStruct();
                  stateHistory.push((Object) (new Integer(readState)));
                  readState = formSettingState;
                }
              }

              break;

            case formSettingState:

              if (formSettingsStruct == null) {
                // Error
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.ERROR,
                // "Failed to load rule form settings");
                readState = errorState;
              }

              if (commandString != null) {
                st = new StringTokenizer(commandString);
                tempString = CALL_io.getNextToken(st);

                if (tempString.equals("/setting")) {
                  // Add the conditions to the setting
                  formSettingsStruct.conditions = formConditionPairs;

                  // Add the current settings to the form
                  formVector.addElement(formSettingsStruct);

                  // A bit of debugging
                  // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                  // CALL_debug.DEBUG, "Adding Form Settings");
                  formSettingsStruct.print_debug();

                  // Reset the variables
                  formSettingsStruct = null;
                  formConditionPairs = null;

                  // Then pop back up to the previous state
                  readState = ((Integer) stateHistory.pop()).intValue();
                } else if (tempString.equals("condition")) {
                  // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                  // CALL_debug.DEBUG, "Processing a form condition clause");

                  // Read the conditional clause
                  conditionRestrictionString = CALL_io.strRemainder(st);
                  // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                  // CALL_debug.DEBUG, "Condition: [" +
                  // conditionRestrictionString + "]");

                  // If this is the first condition on the form, we need to
                  // create the
                  // Conditions Object
                  if (formConditionPairs == null) {
                    formConditionPairs = new CALL_stringPairsStruct();
                  }

                  // Move on
                  stateHistory.push((Object) (new Integer(readState)));
                  readState = formConditionalState;
                }
              } else if (dataString != null) {
                // Set up the form settings via this string
                formSettingsStruct.setFromString(dataString);
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
                // "Set the form template.");
                dataString = null;
              }

              break;

            case formConditionalState:

              if (conditionRestrictionString == null) {
                // Error
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.ERROR,
                // "Failed to load rule form conditions");
                readState = errorState;
              }

              if (commandString != null) {
                st = new StringTokenizer(commandString);
                tempString = CALL_io.getNextToken(st);

                if (tempString.equals("/condition")) {
                  // End of restriction
                  conditionRestrictionString = null;
                  readState = ((Integer) stateHistory.pop()).intValue();
                }
              } else if (dataString != null) {
                // Add restriction value
                formConditionPairs.addElement(conditionRestrictionString, dataString, true);
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
                // "Adding form condition [" + conditionRestrictionString + ", "
                // + dataString + "]");
                dataString = null;
              }

              break;

            case templateState:
              // Within a template clause
              if (commandString != null) {
                st = new StringTokenizer(commandString);
                tempString = CALL_io.getNextToken(st);

                if (tempString.equals("restriction")) {
                  if (st.hasMoreTokens()) {
                    // Store the restriction clause
                    restrictionString = CALL_io.strRemainder(st);
                    // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                    // CALL_debug.DEBUG, "New restriction [" + restrictionString
                    // + "]");
                    stateHistory.push((Object) (new Integer(readState)));
                    readState = restrictState;
                  } else {
                    // Error: No specification for restriction
                    // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                    // CALL_debug.ERROR, "Restriction ill-defined");
                    readState = errorState;
                  }
                } else if (tempString.equals("vrule")) {
                  formString = null;
                  restrictionString = null;

                  if (st.hasMoreTokens()) {
                    // Store the verb rule name
                    verbRuleString = CALL_io.getNextToken(st);

                    if (st.hasMoreTokens()) {
                      verbRuleFormString = CALL_io.getNextToken(st);
                      // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                      // CALL_debug.DEBUG, "New verb rule [" + verbRuleString +
                      // "], using form ["+ verbRuleFormString + "]");
                      stateHistory.push((Object) (new Integer(readState)));
                      readState = verbRuleState;
                    } else {
                      // Error: No specification for restriction
                      // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                      // CALL_debug.ERROR, "Verb Rule ill-defined - no form");
                      stateHistory.push((Object) (new Integer(readState)));
                      readState = errorState;
                    }
                  } else {
                    // Error: No specification for restriction
                    // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                    // CALL_debug.ERROR,
                    // "Verb Rule ill-defined - no rule name");
                    stateHistory.push((Object) (new Integer(readState)));
                    readState = errorState;
                  }

                } else if (tempString.equals("arule")) {
                  formString = null;
                  restrictionString = null;

                  if (st.hasMoreTokens()) {
                    // Store the adjective rule name
                    adjRuleString = CALL_io.getNextToken(st);

                    if (st.hasMoreTokens()) {
                      adjRuleFormString = CALL_io.getNextToken(st);
                      // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                      // CALL_debug.DEBUG, "New adjective rule [" +
                      // adjRuleString + "], using form ["+ adjRuleFormString +
                      // "]");
                      stateHistory.push((Object) (new Integer(readState)));
                      readState = adjectiveRuleState;
                    } else {
                      // Error: No specification for restriction
                      // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                      // CALL_debug.ERROR,
                      // "Adjective Rule ill-defined - no form");
                      stateHistory.push((Object) (new Integer(readState)));
                      readState = errorState;
                    }
                  } else {
                    // Error: No specification for restriction
                    // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                    // CALL_debug.ERROR,
                    // "Adjective Rule ill-defined - no rule name");
                    stateHistory.push((Object) (new Integer(readState)));
                    readState = errorState;
                  }

                } else if (tempString.equals("counter")) {
                  formString = null;
                  restrictionString = null;

                  stateHistory.push((Object) (new Integer(readState)));
                  readState = counterRuleState;
                } else if (commandString.equals("/template")) {
                  readState = ((Integer) stateHistory.pop()).intValue();
                }
              } else if (dataString != null) {
                // A template itself, add it to the sub vector
                newTemplate = new CALL_sentenceTemplateStruct(dataString);
                newRule.templates.addElement(newTemplate);
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
                // "Template added [" + dataString + "]");
                dataString = null;
              }
              break;

            case restrictState:
              // Within a restriction clause
              if (commandString != null) {
                st = new StringTokenizer(commandString);
                tempString = CALL_io.getNextToken(st);

                if (commandString.equals("/restriction")) {
                  // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                  // CALL_debug.DEBUG, "Finished restriction");
                  restrictionString = null;
                  readState = ((Integer) stateHistory.pop()).intValue();
                }
              } else if (dataString != null) {
                // Add a new restricted template
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
                // "Added template [" + dataString + "]");
                newTemplate = new CALL_sentenceTemplateStruct(dataString);
                newTemplate.restriction = restrictionString;
                newRule.templates.addElement(newTemplate);
                dataString = null;
              }
              break;

            case verbRuleState:
              // Parsing verb rule
              if (commandString != null) {
                st = new StringTokenizer(commandString);
                tempString = CALL_io.getNextToken(st);

                if (commandString.equals("/vrule")) {
                  // Reset verb rule string
                  // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                  // CALL_debug.DEBUG, "Finished verb rule");
                  verbRuleString = null;
                  readState = ((Integer) stateHistory.pop()).intValue();
                } else if (tempString.equals("restriction")) {
                  // This is the form definition string
                  if (st.hasMoreTokens()) {
                    // Store the restriction clause
                    restrictionString = CALL_io.strRemainder(st);
                    // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                    // CALL_debug.DEBUG, "New restriction [" + restrictionString
                    // + "]");
                    stateHistory.push((Object) (new Integer(readState)));
                    readState = restrictState;
                  } else {
                    // Error: No specification for restriction
                    // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                    // CALL_debug.ERROR, "Restriction ill-defined");
                    readState = errorState;
                  }
                }
              } else if (dataString != null) {
                // Read in template, and add to the rule
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
                // "Added template [" + dataString + "] ");
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
                // "Restriction [" + restrictionString + "] ");
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
                // "Rule [" + verbRuleString + "] ");
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
                // "Form [" + verbRuleFormString + "]");
                newTemplate = new CALL_sentenceTemplateStruct(dataString);
                newTemplate.type = CALL_sentenceTemplateStruct.TYPE_VERB;
                newTemplate.restriction = restrictionString;
                newTemplate.form = verbRuleFormString;
                newTemplate.rule = verbRuleString;
                newRule.templates.addElement(newTemplate);
                dataString = null;
              }

              break;

            case adjectiveRuleState:
              // Parsing verb rule
              if (commandString != null) {
                st = new StringTokenizer(commandString);
                tempString = CALL_io.getNextToken(st);

                if (commandString.equals("/arule")) {
                  // Reset verb rule string
                  // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                  // CALL_debug.DEBUG, "Finished adjective rule");
                  adjRuleString = null;
                  readState = ((Integer) stateHistory.pop()).intValue();
                } else if (tempString.equals("restriction")) {
                  // This is the form definition string
                  if (st.hasMoreTokens()) {
                    // Store the restriction clause
                    restrictionString = CALL_io.strRemainder(st);
                    // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                    // CALL_debug.DEBUG, "New restriction [" + restrictionString
                    // + "]");
                    stateHistory.push((Object) (new Integer(readState)));
                    readState = restrictState;
                  } else {
                    // Error: No specification for restriction
                    // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                    // CALL_debug.ERROR, "Restriction ill-defined");
                    readState = errorState;
                  }
                }
              } else if (dataString != null) {
                // Read in template, and add to the rule
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
                // "Added template [" + dataString + "] ");
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
                // "Restriction [" + restrictionString + "] ");
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
                // "Rule [" + adjRuleString + "] ");
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
                // "Form [" + adjRuleFormString + "]");
                newTemplate = new CALL_sentenceTemplateStruct(dataString);
                newTemplate.type = CALL_sentenceTemplateStruct.TYPE_ADJECTIVE;
                newTemplate.restriction = restrictionString;
                newTemplate.form = adjRuleFormString;
                newTemplate.rule = adjRuleString;
                newRule.templates.addElement(newTemplate);
                dataString = null;
              }

              break;

            case counterRuleState:
              // Parsing Counter rule
              if (commandString != null) {
                st = new StringTokenizer(commandString);
                tempString = CALL_io.getNextToken(st);

                if (commandString.equals("/counter")) {
                  // Reset verb rule string
                  // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                  // CALL_debug.DEBUG, "Finished counter rule");
                  readState = ((Integer) stateHistory.pop()).intValue();
                } else if (tempString.equals("form")) {
                  // This is the form definition string
                  formString = CALL_io.strRemainder(st);
                  stateHistory.push((Object) (new Integer(readState)));
                  readState = counterFormState;
                } else if (tempString.equals("restriction")) {
                  // This is the form definition string
                  if (st.hasMoreTokens()) {
                    // Store the restriction clause
                    restrictionString = CALL_io.strRemainder(st);
                    // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                    // CALL_debug.DEBUG, "New restriction [" + restrictionString
                    // + "]");
                    stateHistory.push((Object) (new Integer(readState)));
                    readState = restrictState;
                  } else {
                    // Error: No specification for restriction
                    // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                    // CALL_debug.ERROR, "Restriction ill-defined");
                    readState = errorState;
                  }
                }

              }
              break;

            case counterFormState:
              // Parsing counter form
              // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
              // "In counter form state");
              if (commandString != null) {
                st = new StringTokenizer(commandString);
                tempString = CALL_io.getNextToken(st);

                if (commandString.equals("/form")) {
                  // Exit the form specification state
                  // CALL_debug.printlog(CALL_debug.MOD_GRULES,
                  // CALL_debug.DEBUG, "Finished form");
                  readState = ((Integer) stateHistory.pop()).intValue();
                  formRestrictPairs = null;
                  formString = null;
                }
              } else if (dataString != null) {
                // Read the template now!
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
                // "Added template [" + dataString + "] ");
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
                // "Restriction [" + restrictionString + "] ");
                // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.DEBUG,
                // "Form [" + formString + "]");
                newTemplate = new CALL_sentenceTemplateStruct(dataString);
                newTemplate.type = CALL_sentenceTemplateStruct.TYPE_COUNTER;
                newTemplate.restriction = restrictionString;
                newTemplate.counter_form = formString;
                newRule.templates.addElement(newTemplate);
                dataString = null;
              }

              break;

            default:
              // Should never get here
              readState = errorState;
              break;
          }

        } // If #, Readable Line, etc

      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    } // While

    if (readState == 3) {
      // Error, set return to null
      newRule = null;
    }

    return newRule;
  }

  // Get Grammar Rule - either search all (top level) rules, or start first with
  // a reference rules sub-grammars, and then top-level
  public CALL_grammarRuleStruct getGrammarRule(CALL_grammarRuleStruct reference, String name) {

    // logger.debug("enter getGrammarRule, searching rule name: "+ name );

    CALL_grammarRuleStruct rRule = null;
    CALL_grammarRuleStruct tempRule;

    if (reference != null) {
      // logger.debug("reference rule is: "+ reference.name);

      // Look for subrule based on reference
      rRule = reference.getSubRule(name);
    }

    if (rRule == null) {
      // logger.debug("not found the target rule in the reference rule stuctures");

      // No sub rule, or simply no reference
      for (int i = 0; i < rules.size(); i++) {
        tempRule = (CALL_grammarRuleStruct) rules.elementAt(i);
        if (tempRule != null) {
          if (tempRule.name.equals(name)) {
            rRule = tempRule;
            break;
          }
        }
      }
    }

    return rRule;
  }

  // Output debug on grammar rule structure
  public void print_debug() {
    CALL_grammarRuleStruct currentRule;

    for (int i = 0; i < rules.size(); i++) {
      currentRule = (CALL_grammarRuleStruct) rules.elementAt(i);
      print_rule_debug(currentRule, 1);
    }
  }

  // Output debug on grammar rule structure
  public void print_rule_debug(CALL_grammarRuleStruct rule, int depth) {
    String tempString, depthString;
    CALL_grammarRuleStruct subRule;
    CALL_sentenceTemplateStruct template;

    // Output this rule name
    depthString = CALL_io.getNString(" ", depth);
    tempString = depthString + rule.name;
    // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.INFO, tempString);

    // Output any parameters
    tempString = depthString + "->" + new String("Parameters: ");
    for (int i = 0; i < rule.parameters.size(); i++) {
      tempString = tempString + (String) rule.parameters.elementAt(i);
    }
    // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.INFO, tempString);

    // Print templates
    for (int j = 0; j < rule.templates.size(); j++) {
      template = (CALL_sentenceTemplateStruct) rule.templates.elementAt(j);
      if (template != null) {
        if (template.restriction == null) {
          // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.INFO,
          // depthString + "-> Default Template: " + template.structure + "");
        } else {
          // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.INFO,
          // depthString + "-> Restriction: " + template.restriction);
          // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.INFO,
          // depthString + "-->Template: " + template.structure + "");
        }

        if (template.form != null) {
          // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.INFO,
          // depthString + "-> Verb Rule: " + template.rule);
          // CALL_debug.printlog(CALL_debug.MOD_GRULES, CALL_debug.INFO,
          // depthString + "--> Form: " + template.form);
        }
      }
    }

    // Move on to subrules
    for (int i = 0; i < rule.subRules.size(); i++) {
      subRule = (CALL_grammarRuleStruct) rule.subRules.elementAt(i);
      print_rule_debug(subRule, depth + 1);
    }

  }

  public static void main(String[] agrs) {
    CALL_grammarRulesStruct rulesStruct = new CALL_grammarRulesStruct(null);
    rulesStruct.loadRules("/Data/grammar-rule.txt");
    System.out.println("1");
  }
}
