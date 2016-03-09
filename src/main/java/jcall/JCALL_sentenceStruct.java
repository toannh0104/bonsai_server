/**
 * Created on 2008/07/09
 * @author wang
 * Copyrights @kawahara lab
 */

package jcall;

import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

public class JCALL_sentenceStruct {
  static Logger logger = Logger.getLogger(JCALL_sentenceStruct.class.getName());
  // Fields
  Vector sentence; // This is a vector of sentenceWordStruct objects, which
                   // themselves link to other WordObjects to form sentences
  Vector allWords; // A vector of all words

  CALL_database db;

  // The forms specified for this sentence
  Vector<CALL_formInstanceStruct> formSettings; // A vector of ojects type
                                                // CALL_formInstanceStruct
  Vector<String> formNames; // The names of the form classes (verbForm, adjForm,
                            // etc). Same size as vector above.

  // adding for error prediction
  Vector<CALL_formStruct> formSettingStructs; // A vector of ojects type
                                              // CALL_formStruct, the instance
                                              // is generated from this

  // Variables for the form of the whole sentence (based on first specified form
  // setting in the top level rule)
  // the form setting instance choosing from the first <form> , it values equal
  // values of formSettings.get(0);
  boolean formSet;
  int type;
  int sign;
  int style;
  int tense;

  // Vector vGWNetwork;

  public JCALL_sentenceStruct() {
    init();
  }

  public JCALL_sentenceStruct(CALL_database data, String gruleStr, CALL_conceptInstanceStruct instance) {
    init();
    db = data;
    createSentence(gruleStr, instance);

  }

  public void init() {
    db = null;
    formSet = false;
    sign = 0;
    tense = 0;
    style = 0;
    type = 0;
    sentence = new Vector();
    allWords = new Vector();
    formSettings = new Vector();
    formNames = new Vector();
    formSettingStructs = new Vector();
  }

  // Take the grammar rule and an instance, and make the sentence
  public void createSentence(String gruleStr, CALL_conceptInstanceStruct instance) {
    CALL_grammarRuleStruct grule;
    // logger.debug("enter createSentence");
    if (instance.getType() == CALL_questionStruct.QTYPE_CONTEXT) {
      // Need to build a full sentence

      // Start by getting the top level rule
      grule = db.grules.getGrammarRule(null, gruleStr);

      // Resolve using the recursive function
      if (grule != null) {
        recursive_resolve(null, null, grule, null, instance, -1, -1);
      } else {
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.ERROR,
        // "Failed to find rule [" + gruleStr + "]");
      }
    } else if (instance.getType() == CALL_questionStruct.QTYPE_VOCAB) {
      // It's just a single word we're dealing with, no grammar rules etc
      oneWordSentence(instance);
    }
  }

  private boolean oneWordSentence(CALL_conceptInstanceStruct instance) {
    CALL_sentenceWordStruct newWord;
    CALL_wordStruct tempWord;
    Integer wID;
    int wordID;
    Vector wordList, tempV, words;

    wordID = -1;

    if (instance.getId().size() > 0) {
      wID = (Integer) instance.getId().elementAt(0);
      if (wID != null) {
        wordID = wID.intValue();
      }
    }

    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
    // "Creating sentence on one word, id: " + wordID);

    if (wordID >= 0) {
      tempV = db.lexicon.getWords(wordID);
      if (tempV != null) {
        words = new Vector();

        for (int k = 0; k < tempV.size(); k++) {
          // For each number word (tempV), and then each object word (tempV2),
          // add a sentence word
          tempWord = (CALL_wordStruct) tempV.elementAt(k);

          newWord = new CALL_sentenceWordStruct(db, tempWord, "WORD", "WORD");
          newWord.cost_grammar = 1;
          newWord.cost_communication = 1;
          sentence.addElement(newWord);
        }
      }
    }

    if (sentence.size() > 0) {
      return true;
    }

    return false;
  }

  // The vector returned is the last set of words added to the sentence
  // recursive_resolve(null, null, grule, null, instance, -1, -1);
  private Vector recursive_resolve(String fullRule, Vector prevWords, CALL_grammarRuleStruct grule, Vector parameters,
      CALL_conceptInstanceStruct instance, int diffCost, int comCost) {
    boolean rc = true;
    CALL_grammarRuleStruct subRule;
    CALL_sentenceTemplateStruct temp_template = null;
    CALL_sentenceTemplateStruct template = null;
    CALL_sentenceWordStruct newWord = null;
    CALL_wordStruct tempWord = null;
    CALL_wordStruct tempWord2 = null;

    String templateStr = null;

    String currentGrammarRule = null;
    String previousGrammarRule = null;

    int i;

    int diffCostInt;
    int comCostInt;

    StringTokenizer st, st2;
    String tempString;
    String restrictionString;
    String keyString;
    String numberString;
    String wordString;
    String typeString;
    String labelString;
    String dataString;
    String formString = null;

    Integer id;
    int id_val;
    int wordType;

    Vector previousWords = null;
    Vector tempVector = null;
    Vector totalWordsAdded = null;
    Vector wordsAdded = null;

    Vector validTemplates = null;
    Vector words = null;
    Vector moreWords = null;
    Vector tempV = null;
    Vector tempV2 = null;
    Vector parmsToPass = null;

    // For the sentences form calculations
    Vector formVector = null;
    String formName = null;
    CALL_formStruct form = null;
    CALL_formStruct currentTemplateFormStruct = null;
    CALL_formInstanceStruct formInstance = null;
    CALL_formInstanceStruct currentTemplateForm = null;
    String formStr = null;
    String currentFormStr = null;

    boolean found;
    boolean form_complete;
    int index;
    int formTemplateCount;
    int formTemplateIndex;
    int chosenFormIndex;

    Random rand = new Random();

    // logger.debug("Calling recursive resolve: Rule: " + grule.name +
    // ", Cost: " + diffCost + ", Rule Path: " + fullRule);
    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
    // "Calling recursive resolve: Rule: " + grule.name + ", Cost: " + diffCost
    // + ", Rule Path: " + fullRule);

    // Set the current rule path
    if (fullRule == null) {
      previousGrammarRule = grule.name;
    } else {
      previousGrammarRule = fullRule + "." + grule.name;
    }

    // STEP 1: PICK AND ADD FORMS SPECIFIED BY THIS RULE TO THE SENTENCE RULE
    // SETTINGS
    // =====================================================================================

    // logger.debug("STEP 1: PICK AND ADD FORMS SPECIFIED BY THIS RULE TO THE SENTENCE RULE SETTINGS");
    // logger.debug("Different <form> size : " + grule.formSettings.size());
    // logger.debug("For each <form>, pick one form setting template from the group at random ");

    for (i = 0; i < grule.formSettings.size(); i++) {
      formVector = (Vector) grule.formSettings.elementAt(i);
      formName = (String) grule.formNames.elementAt(i);

      if ((formVector != null) && (formName != null)) {
        // Now, pick one of the form templates from the form group at random
        if (formVector.size() > 0) {
          // logger.debug("choose one setting in formVector, seletable size(): "
          // + formVector.size() );

          form = (CALL_formStruct) formVector.elementAt(rand.nextInt(formVector.size()));
          if (form != null) {
            // Now from this form template, generate a form instance
            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
            // "Detemine settings for form: " + formName);
            // logger.debug("Detemine settings and its instance for form: " +
            // formName);
            // logger.debug("Detemine form setting: " + form.toString());

            formInstance = form.pickSettings(db, instance);

            if (formInstance != null) {
              formSettingStructs.addElement(form);
              formSettings.addElement(formInstance);
              formNames.addElement(formName);
              // logger.debug("Detemine form instance: " +
              // formInstance.toString());

              // Also, if the sentence form is not yet set, use this as the form
              if (!formSet) {
                formSet = true;
                sign = formInstance.sign;
                tense = formInstance.tense;
                style = formInstance.style;
                type = formInstance.type;
              }
            }
          }
        }
      }
    } // end for

    // STEP 2: PARSE ALL OF THE TEMPLATES WITHIN THIS RULE
    // ======================================================

    // Go through each element in template

    if (grule.templates.size() <= 0) {
      // No templates, invalid rule
      // logger.debug("No templates, invalid rule, terminate" );
      return prevWords;
    }

    // Get the list of valid templates at this point
    validTemplates = new Vector();

    // Set the words added to the previous value, just in case it all changes
    totalWordsAdded = new Vector();

    formTemplateCount = 0;

    // First check the restricted templates to see if we have any matching cases
    // ================================================================
    // logger.debug("STEP 2: Deal with templates; first restricted templates to see if we have any matching cases");

    for (i = 0; i < grule.templates.size(); i++) {
      temp_template = (CALL_sentenceTemplateStruct) grule.templates.elementAt(i);
      if (temp_template != null) {
        if (temp_template.restriction != null) {
          // Check if the restriction matches
          if (restriction_match(grule, temp_template.restriction, instance, parameters)) {
            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
            // "Adding restricted template: " + temp_template.structure);
            // logger.debug("Adding restricted template: " +
            // temp_template.structure);

            validTemplates.addElement(temp_template);

            if (temp_template.form != null) {
              // This is a form based template
              formTemplateCount++;
            }
          }
        }
      }
    }

    // If we didn't match with any restricted templates, use the default ones
    // ============================================================

    if (validTemplates.size() <= 0) {
      for (i = 0; i < grule.templates.size(); i++) {
        temp_template = (CALL_sentenceTemplateStruct) grule.templates.elementAt(i);
        if (temp_template != null) {
          if (temp_template.restriction == null) {
            // No restriction, so can be used
            validTemplates.addElement(temp_template);
          }
        }
      }
    }

    // This happens if we have no unrestricted templates, and no matching
    // restricted ones.
    // In this case, the rule is nulled, and ignored
    if (validTemplates.size() <= 0) {
      return prevWords;
    }

    // Now go through all the valid templates at this level, expanding them, and
    // adding them to the word passed in
    // If the passed in word is NULL, this is the sentence start so each new
    // word should be an element in the top
    // level sentences vector
    for (int t = 0; t < validTemplates.size(); t++) {
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
      // "Processing next template");

      // Get the next template
      template = (CALL_sentenceTemplateStruct) validTemplates.elementAt(t);
      // logger.debug("Processing next template: " +template.toString());

      // Set the previous words vector (a list of all the words added in the
      // last stage (final, appendable words)
      // We set this above, but it will likely have been changed during the
      // processing of the last template
      previousWords = prevWords;

      if (template != null) {
        // Set the form for this template (if specified, else use the default)
        currentTemplateForm = null;
        formStr = template.form; // form name

        if (formStr != null) {
          // logger.debug("Search the form for this template, form: "+ formStr);
          // Find the settings specified by the named form
          currentTemplateForm = getForm(formStr);
          currentTemplateFormStruct = getFormStruct(formStr);
          currentFormStr = formStr;
        }

        if (currentTemplateForm == null) {
          // Just use a default template
          // logger.debug("form for this template is null, Just use a default: pos present plain statement");
          currentTemplateForm = new CALL_formInstanceStruct(0); // using default
                                                                // "pos present polite statement"
          currentTemplateFormStruct = new CALL_formStruct("pos present plain statement");
          currentFormStr = new String("Default");
        }

        templateStr = template.structure;
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Template String: " + templateStr);
        // logger.debug("Template String(structure): " + templateStr);

        logger.debug("Parsing Template  String");
        // Resolve depending on element type
        st = new StringTokenizer(templateStr);

        while (st.hasMoreTokens()) {
          wordsAdded = new Vector();

          tempString = CALL_io.getNextBracketedToken(st); // This should thus
                                                          // get {ITEM cost} etc
          // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
          // "Processing template token [" + tempString + "]");
          logger.debug("Processing template token [" + tempString + "]");

          if (tempString != null) {
            if (tempString.length() >= 3) {
              // logger.debug("In if, Token has at least three parts");
              // If it's a variable, convert the variable to it's value
              if (tempString.startsWith("|")) {
                // A variable
                // =========
                // logger.debug("A variable");
                // Strip off the brackets
                keyString = tempString.substring(1, tempString.length());
                // logger.debug("A variable: "+keyString);
                index = -1;
                index = keyString.indexOf('|', 0);
                if (index != -1) {
                  // Trim final | as well as first
                  keyString = keyString.substring(0, index);
                }

                // Should be 3 strings - the parameter, and the 2 costs
                dataString = null;
                diffCostInt = diffCost;
                comCostInt = comCost;

                st2 = new StringTokenizer(keyString);
                if (st2.hasMoreTokens()) {
                  dataString = CALL_io.getNextToken(st2);
                  if (st2.hasMoreTokens()) {
                    // We're now looking for the difficulty based cost
                    // ----------------------------------------
                    diffCostInt = CALL_io.getNextInt(st2);
                    if ((diffCostInt == CALL_io.INVALID_INT) || (diffCostInt == -1)) {
                      // We'll use the cost passed in instead
                      diffCostInt = diffCost;
                    }

                    if (st2.hasMoreTokens()) {
                      // We're now looking for the communication based cost
                      // ---------------------------------------------
                      comCostInt = CALL_io.getNextInt(st2);
                      if ((comCostInt == CALL_io.INVALID_INT) || (comCostInt == -1)) {
                        // We'll use the cost passed in instead
                        comCostInt = comCost;
                      }
                    }
                  }
                }

                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                // CALL_debug.DEBUG, "Variable - converting |" + dataString +
                // "|, DCost: " + diffCostInt + ", CCost: " + comCostInt);
                // logger.debug("Variable - converting |" + dataString +
                // "|, DCost: " + diffCostInt + ", CCost: " + comCostInt);

                tempString = convertParameter(dataString, grule, parameters);
                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                // CALL_debug.DEBUG, "Converted to " + tempString);
                // logger.debug("Converted to " + tempString);

                // We now use this string in the checks below to see what type
                // it is...
              } // end of variable

              if (tempString.equals("omit")) {
                // Skip
              } else if (tempString.startsWith("(")) {
                // A direct lexical reference
                // ======================
                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                // CALL_debug.DEBUG, "Direct lexical reference");
                // logger.debug("Direct lexical reference");

                currentGrammarRule = previousGrammarRule;

                index = -1;
                index = tempString.indexOf(')', 0);
                if (index != -1) {
                  // Trim final ) as well as first
                  keyString = tempString.substring(1, index);
                } else {
                  // Just trim opening (
                  keyString = tempString.substring(1, tempString.length());
                }

                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                // CALL_debug.DEBUG, "Using string: " + keyString);
                // logger.debug("Using string: " + keyString);
                // Should be 4 strings - the type, the value, and the 2 cost
                // coefficients
                typeString = null;
                dataString = null;
                diffCostInt = diffCost;
                comCostInt = comCost;

                st2 = new StringTokenizer(keyString);
                if (st2.hasMoreTokens()) {
                  typeString = CALL_io.getNextToken(st2);
                  if (st2.hasMoreTokens()) {
                    dataString = CALL_io.getNextToken(st2);
                    if (st2.hasMoreTokens()) {
                      // We're now looking for the difficulty based cost
                      // ----------------------------------------
                      diffCostInt = CALL_io.getNextInt(st2);
                      if ((diffCostInt == CALL_io.INVALID_INT) || (diffCostInt == -1)) {
                        // We'll use the cost passed in instead
                        diffCostInt = diffCost;
                      }

                      if (st2.hasMoreTokens()) {
                        // We're now looking for the communication based cost
                        // ---------------------------------------------
                        comCostInt = CALL_io.getNextInt(st2);
                        if ((comCostInt == CALL_io.INVALID_INT) || (comCostInt == -1)) {
                          // We'll use the cost passed in instead
                          comCostInt = comCost;
                        }
                      }
                    }
                  }
                }

                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                // CALL_debug.DEBUG, "Adding lexical reference, type: " +
                // typeString + ", Value: " + dataString + ", Cost: " +
                // diffCostInt + ", CCost: " + comCostInt);

                logger.debug("Adding lexical reference, type: " + typeString + ", Value: " + dataString + ", Cost: "
                    + diffCostInt + ", CCost: " + comCostInt);

                if ((typeString != null) && (dataString != null)) {
                  // Get type
                  if (typeString.equals("verb")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_VERB;
                  } else if (typeString.equals("noun")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN;
                  } else if (typeString.equals("adjective")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_ADJECTIVE;
                  } else if (typeString.equals("adjverb")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_ADJVERB;
                  } else if (typeString.equals("adverb")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_ADVERB;
                  } else if (typeString.equals("particle")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_PARTICLE_AUXIL;
                  } else if (typeString.equals("time")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN_TIME;
                  } else if (typeString.equals("definitive")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN_DEFINITIVES;
                  } else if (typeString.equals("digit")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN_NUMERAL;
                  } else if (typeString.equals("position")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN;
                  } else if (typeString.equals("counter")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NUMQUANT;
                  } else if (typeString.equals("quantifier")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN_QUANTIFIER;
                  } else if (typeString.equals("time")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN_TIME;
                  } else if (typeString.equals("postfix")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN_SUFFIX;
                  } else if (typeString.equals("prefix")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN_PREFIX;
                  } else {
                    wordType = CALL_lexiconStruct.LEX_TYPE_UNSPECIFIED;
                  }

                  if (wordType == CALL_lexiconStruct.LEX_TYPE_VERB || wordType == CALL_lexiconStruct.LEX_TYPE_ADJECTIVE
                      || wordType == CALL_lexiconStruct.LEX_TYPE_ADJVERB) {
                    if (dataString != null) {
                      // We have a list of verbs, get the right form, and add
                      tempV = db.lexicon
                          .getWords(null, wordType, CALL_lexiconStruct.LEX_FORMAT_UNSPECIFIED, dataString);

                      if (tempV != null) {
                        // Vector for alternatives
                        logger.info("Find word: " + dataString);
                        words = new Vector();

                        for (int j = 0; j < tempV.size(); j++) {
                          // Get the correct form for each verb
                          tempWord = (CALL_wordStruct) tempV.elementAt(j);

                          // Temp debug
                          // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                          // CALL_debug.DEBUG, "Creating new word: " + tempWord
                          // + ", " + grule + ", " + currentFormStr);

                          logger.info("Creating new word: " + tempWord + ", " + grule + ", " + currentFormStr);

                          newWord = new CALL_sentenceWordStruct(db, tempWord, grule.name, currentGrammarRule,
                              grule.description, template.rule, currentTemplateForm.sign, currentTemplateForm.tense,
                              currentTemplateForm.style, currentTemplateForm.type);
                          // adding for prediction
                          if (currentTemplateFormStruct != null) {
                            newWord.formString = currentTemplateFormStruct.toString();
                            logger.info("new word formString: " + currentTemplateFormStruct.toString());

                          }
                          newWord.cost_grammar = diffCostInt;
                          newWord.cost_communication = comCostInt;
                          words.addElement(newWord);

                          // The wordsAdded list is all words added at this
                          // template stage which will be
                          // follow on words in the next stage
                          wordsAdded.addElement(newWord);
                        }

                        if (words.size() > 0) {
                          // Add the words to the current sentence structure
                          // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                          // CALL_debug.DEBUG, "Adding vector of " +
                          // words.size() + " words");
                          addWordsToSentence(previousWords, words);
                        } else {
                          // No words - warning
                          // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                          // CALL_debug.WARN, "No words found to match [" +
                          // dataString + "]");

                        }
                      } else {
                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                        // CALL_debug.WARN, "No words list found to match [" +
                        // dataString + "]");
                      }
                    }
                  } else {
                    // First, trim anything after and including the trailing
                    // bracket
                    index = -1;
                    index = dataString.indexOf(')', 0);
                    if (index != -1) {
                      dataString = dataString.substring(0, index);
                    }

                    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                    // CALL_debug.DEBUG, "Looking up " + dataString);

                    // Then add word Will need to actually specifiy the type if
                    // possible! - CJW
                    tempV = db.lexicon.getWords(null, wordType, CALL_lexiconStruct.LEX_FORMAT_UNSPECIFIED, dataString);

                    if (tempV != null) {
                      // Add the words along with null form structures (no form
                      // needed, as not a verb)
                      words = new Vector();

                      for (int j = 0; j < tempV.size(); j++) {
                        tempWord = (CALL_wordStruct) tempV.elementAt(j);

                        if (wordType == CALL_lexiconStruct.LEX_TYPE_NOUN_SUFFIX) {
                          // Postfix - add to previous sentence word list
                          if (previousWords != null) {
                            for (int k = 0; k < previousWords.size(); k++) {
                              newWord = (CALL_sentenceWordStruct) previousWords.elementAt(k);
                              newWord.postfix.addElement(tempWord);

                              // We actually re-add the name to the list again,
                              // as this is the word
                              // any following words will actually link from,
                              // not from the postfix
                              // itself which is just a sub-word
                              wordsAdded.addElement(newWord);
                            }
                            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                            // CALL_debug.DEBUG, "Adding postfix " +
                            // tempWord.kana);
                          }
                        } else {
                          newWord = new CALL_sentenceWordStruct(db, tempWord, grule.name, currentGrammarRule,
                              grule.description);
                          newWord.cost_grammar = diffCostInt;
                          newWord.cost_communication = comCostInt;
                          words.addElement(newWord);

                          // The wordsAdded list is all words added at this
                          // template stage which will be
                          // follow on words in the next stage
                          wordsAdded.addElement(newWord);

                          // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                          // CALL_debug.DEBUG, "Adding word: " +
                          // newWord.word.kanji + ", CostD: " +
                          // newWord.cost_grammar + ", CostC: " +
                          // newWord.cost_communication + ", Alt ID: " + j);

                          // Now add the word list to the sentence
                          if (words.size() > 0) {
                            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                            // CALL_debug.DEBUG, "Adding vector of " +
                            // words.size() + " words");
                            addWordsToSentence(previousWords, words);
                          } else {
                            // No words - warning
                            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                            // CALL_debug.WARN, "No words found to match [" +
                            // dataString + "]");
                          }
                        }
                      }
                    } else {
                      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                      // CALL_debug.WARN, "No words list found to match [" +
                      // dataString + "]");
                    }
                  }
                }
              } else if (tempString.startsWith("[")) {
                // A concept instance reference
                // =========================
                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                // CALL_debug.DEBUG, "A concept instance reference");
                // logger.debug("Starts with [; A concept instance reference");

                keyString = tempString.substring(1, tempString.length() - 1);
                found = false;

                // There should be 2 strings, the reference, and the cost
                // coefficient
                dataString = null;
                diffCostInt = diffCost;
                comCostInt = comCost;

                st2 = new StringTokenizer(keyString);
                if (st2.hasMoreTokens()) {
                  dataString = CALL_io.getNextToken(st2);
                  currentGrammarRule = previousGrammarRule + "." + dataString;

                  if (st2.hasMoreTokens()) {
                    // We're now looking for the difficulty based cost
                    // ----------------------------------------
                    diffCostInt = CALL_io.getNextInt(st2);
                    if ((diffCostInt == CALL_io.INVALID_INT) || (diffCostInt == -1)) {
                      // We'll use the cost passed in instead
                      diffCostInt = diffCost;
                    }

                    if (st2.hasMoreTokens()) {
                      // We're now looking for the communication based cost
                      // ---------------------------------------------
                      comCostInt = CALL_io.getNextInt(st2);
                      if ((comCostInt == CALL_io.INVALID_INT) || (comCostInt == -1)) {
                        // We'll use the cost passed in instead
                        comCostInt = comCost;
                      }
                    }
                  }
                }

                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                // CALL_debug.DEBUG, "Conceptual reference: " + dataString +
                // ", CostD: " + diffCostInt + ", CostC: " + comCostInt);
                // logger.debug("Conceptual reference: " + dataString +
                // ", CostD: " + diffCostInt + ", CostC: " + comCostInt);

                // Try and find this slot in the instance
                // logger.debug("Then, Try and find this slot in the instance");
                for (i = 0; i < instance.getLabel().size(); i++) {
                  if (i >= instance.getData().size()) {
                    // Must be a mismatch in the label and data vectors
                    break;
                  }

                  labelString = (String) instance.getLabel().elementAt(i);
                  if (labelString != null) {
                    if (labelString.equals(dataString)) {
                      // We have our match
                      found = true;
                      dataString = (String) instance.getData().elementAt(i);
                      id = (Integer) instance.getId().elementAt(i);

                      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                      // CALL_debug.DEBUG, "Found in instance!  Data: " +
                      // dataString + ", ID: " + id);
                      // logger.debug("Found in instance!  Data: " + dataString
                      // + ", ID: " + id);

                      if (id != null) {
                        id_val = id.intValue();
                        if (id_val >= 0) {
                          // Use the ID value to find the exact word we're after
                          tempV = db.lexicon.getWords(id_val);
                        } else {
                          // Use the string
                          tempV = db.lexicon.getWords(null, CALL_lexiconStruct.LEX_TYPE_UNSPECIFIED,
                              CALL_lexiconStruct.LEX_FORMAT_ENGLISH, dataString);
                        }
                      } else {
                        // Use the string
                        if (dataString != null) {
                          tempV = db.lexicon.getWords(null, CALL_lexiconStruct.LEX_TYPE_UNSPECIFIED,
                              CALL_lexiconStruct.LEX_FORMAT_ENGLISH, dataString);
                        }
                      }

                      if ((tempV != null) && (tempV.size() > 0)) {
                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                        // CALL_debug.DEBUG, "Found " + tempV.size() +
                        // " words in dictionary");
                        // logger.debug("Found " + tempV.size() +
                        // " words in dictionary");

                        words = new Vector();

                        // Now take the vector of words, and combine with form
                        // information
                        for (int j = 0; j < tempV.size(); j++) {
                          // Get the correct form for each verb
                          tempWord = (CALL_wordStruct) tempV.elementAt(j);

                          // *************************
                          // *****************************************************************:
                          // **************should be dealt with more types like
                          // adjective

                          if (tempWord.type == CALL_lexiconStruct.LEX_TYPE_VERB
                              || tempWord.type == CALL_lexiconStruct.LEX_TYPE_ADJECTIVE
                              || tempWord.type == CALL_lexiconStruct.LEX_TYPE_ADJVERB) {
                            // If its a verb (etc) need to do the form
                            if (currentTemplateForm != null) {
                              newWord = new CALL_sentenceWordStruct(db, tempWord, labelString, currentGrammarRule,
                                  grule.description, template.rule, currentTemplateForm.sign,
                                  currentTemplateForm.tense, currentTemplateForm.style, currentTemplateForm.type);

                              // adding for prediction
                              if (currentTemplateFormStruct != null) {
                                newWord.formString = currentTemplateFormStruct.toString();
                                // logger.debug("new word formString: " +
                                // currentTemplateFormStruct.toString());

                              }

                              newWord.cost_grammar = diffCostInt;
                              newWord.cost_communication = comCostInt;

                              // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                              // CALL_debug.DEBUG, "Adding verb: " +
                              // newWord.word.kanji + ", CostD: " +
                              // newWord.cost_grammar + ", CostC: " +
                              // newWord.cost_communication);
                              // logger.debug("Adding verb/Adj/Adjverb: " +
                              // newWord.word.kanji + ", CostD: " +
                              // newWord.cost_grammar + ", CostC: " +
                              // newWord.cost_communication);

                              words.addElement(newWord);

                              // The wordsAdded list is all words added at this
                              // template stage which will be
                              // follow on words in the next stage
                              wordsAdded.addElement(newWord);
                            }
                          } else if (template.type == CALL_sentenceTemplateStruct.TYPE_COUNTER) {
                            // If it is a counter, we need to convert it
                            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                            // CALL_debug.DEBUG, "Dealing with a counter!");
                            // logger.debug("Dealing with a counter!");
                            // First, get the object we are counting (from
                            // counter_form)
                            if (template.counter_form != null) {
                              id_val = instance.getWordID(template.counter_form);
                              if (id_val >= 0) {
                                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                // CALL_debug.DEBUG, "Reference word(s) found");
                                // logger.debug("Reference word(s) found, id_val: "
                                // + id_val);
                                // Use the ID value to find the exact word we're
                                // after
                                tempV2 = db.lexicon.getWords(id_val);

                                for (int k = 0; k < tempV2.size(); k++) {
                                  // For each number word (tempV), and then each
                                  // object word (tempV2), add a sentence word
                                  tempWord2 = (CALL_wordStruct) tempV2.elementAt(k);

                                  newWord = new CALL_sentenceWordStruct(db, tempWord, tempWord2, labelString,
                                      currentGrammarRule, grule.description);
                                  newWord.cost_grammar = diffCostInt;
                                  newWord.cost_communication = comCostInt;

                                  // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                  // CALL_debug.DEBUG, "Adding sentence word: "
                                  // + tempWord.kanji +", counter reference " +
                                  // tempWord2.kanji);
                                  // logger.debug("Adding sentence word: " +
                                  // tempWord.kanji +", counter reference " +
                                  // tempWord2.kanji);

                                  words.addElement(newWord);

                                  // The wordsAdded list is all words added at
                                  // this template stage which will be
                                  // follow on words in the next stage
                                  wordsAdded.addElement(newWord);
                                }
                              } else {
                                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                // CALL_debug.WARN,
                                // "But no referencing object word!");
                                // logger.debug("But no referencing object word!");
                              }
                            }
                          } else {
                            // Just add the word as is (no form modification
                            // etc)
                            newWord = new CALL_sentenceWordStruct(db, tempWord, labelString, currentGrammarRule,
                                grule.description);
                            newWord.cost_grammar = diffCostInt;
                            newWord.cost_communication = comCostInt;

                            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                            // CALL_debug.DEBUG, "Adding word: " +
                            // newWord.word.kanji + ", CostD: " +
                            // newWord.cost_grammar + ", CostC: " +
                            // newWord.cost_communication);
                            // logger.debug("Adding word: " + newWord.word.kanji
                            // + ", CostD: " + newWord.cost_grammar +
                            // ", CostC: " + newWord.cost_communication);

                            words.addElement(newWord);

                            // The wordsAdded list is all words added at this
                            // template stage which will be
                            // follow on words in the next stage
                            wordsAdded.addElement(newWord);
                          }

                        }

                        // Add the words to the currently forming sentence
                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                        // CALL_debug.DEBUG, "Adding vector of " + words.size()
                        // + " words");
                        // logger.debug("Adding vector of " + words.size() +
                        // " words");

                        addWordsToSentence(previousWords, words);
                      } else {
                        // No words - assume its a disabled concept tag, don't
                        // resolve the rule any further
                        if (totalWordsAdded.size() <= 0) {
                          // No words added in this parse, use the words passed
                          // in originally
                          totalWordsAdded = prevWords;
                        }
                        return totalWordsAdded;
                      }

                      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                      // CALL_debug.DEBUG, "Added [" + keyString + "]");
                      // logger.debug("Added [" + keyString + "]");
                    }
                  }
                }// end for --trying to find

                if (!found) {
                  // The concept does not exist (Changed from returng
                  // previousWords - CJW)
                  return prevWords;
                }

              } else if (tempString.startsWith("{")) {
                // A sub-rule
                // =========
                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                // CALL_debug.DEBUG, "A sub rule to expand");
                // logger.debug("Starts with {, A sub rule to expand");
                // A sub rule - trim the brackets
                keyString = tempString.substring(1, tempString.length() - 1);
                currentGrammarRule = previousGrammarRule;

                // There should be 3 strings; the reference, and the two cost
                // coefficients
                dataString = null;
                diffCostInt = diffCost;
                comCostInt = comCost;

                st2 = new StringTokenizer(keyString);
                if (st2.hasMoreTokens()) {
                  dataString = CALL_io.getNextToken(st2);

                  if (st2.hasMoreTokens()) {
                    // We're now looking for the difficulty based cost
                    // ----------------------------------------
                    diffCostInt = CALL_io.getNextInt(st2);
                    if ((diffCostInt == CALL_io.INVALID_INT) || (diffCostInt == -1)) {
                      // We'll use the cost passed in instead
                      diffCostInt = diffCost;
                    }

                    if (st2.hasMoreTokens()) {
                      // We're now looking for the communication based cost
                      // ---------------------------------------------
                      comCostInt = CALL_io.getNextInt(st2);
                      if ((comCostInt == CALL_io.INVALID_INT) || (comCostInt == -1)) {
                        // We'll use the cost passed in instead
                        comCostInt = comCost;
                      }
                    }
                  }
                }

                // Does the rule also have parameters (these are optional, so
                // come LAST, after the costs - CJW
                if (st2.hasMoreTokens()) {
                  parmsToPass = new Vector();
                  while (st2.hasMoreTokens()) {
                    parmsToPass.addElement(CALL_io.getNextToken(st2));
                  }
                  // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                  // CALL_debug.DEBUG, "Adding Subrule: " + dataString +
                  // "; CostD: " + diffCostInt + "; CostC: " + comCostInt + ";"
                  // + parmsToPass.size() + " Parameters");
                  // logger.debug("Adding Subrule: " + dataString + "; CostD: "
                  // + diffCostInt + "; CostC: " + comCostInt + ";" +
                  // parmsToPass.size() + " Parameters");
                } else {
                  // No parameters to be passed
                  parmsToPass = null;
                  // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                  // CALL_debug.DEBUG, "Adding Subrule: " + dataString +
                  // "; CostD: " + diffCostInt + "; CostC: " + comCostInt );
                  // logger.debug("Adding Subrule: " + dataString + "; CostD: "
                  // + diffCostInt + "; CostC: " + comCostInt + ";");
                }

                subRule = db.grules.getGrammarRule(grule, dataString);
                if (subRule != null) {
                  // Recursively call the subrule
                  tempVector = recursive_resolve(currentGrammarRule, previousWords, subRule, parmsToPass, instance,
                      diffCostInt, comCostInt);
                  if (tempVector != null) {
                    // Add the final words from the sub-grammar into the added
                    // words vector which
                    // will get used as the previousWords in the next template
                    // item
                    wordsAdded.addAll(tempVector);
                  }
                } else {
                  // Error, failed to find subrule in database
                  // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                  // CALL_debug.ERROR, "Failed to find subrule [" + keyString +
                  // "]");
                  // logger.debug("Failed to find subrule [" + keyString + "]");
                }

              } // Token begins with {, [, < etc
            } // If token is at least 3 chars long
          } // If token != null

          // Now update the previous words vector to be all the (final) words
          // added for the current template object
          previousWords = wordsAdded;

        } // While more tokens in template

        // The last set of words added will be the final ones from the template,
        // the ones any further templates will append to
        if ((wordsAdded != null) && (wordsAdded.size() > 0)) {
          totalWordsAdded.addAll(wordsAdded);
        }

      } // If template != null
    } // For i = 0 to all templates

    if (totalWordsAdded.size() <= 0) {
      // No words added in this parse, use the words passed in originally
      totalWordsAdded = prevWords;
    }

    return totalWordsAdded;
  }

  private Vector resolvegrule(String fullRule, Vector prevWords, CALL_grammarRuleStruct grule, Vector parameters,
      CALL_conceptInstanceStruct instance, int diffCost, int comCost) {
    boolean rc = true;
    CALL_grammarRuleStruct subRule;
    CALL_sentenceTemplateStruct temp_template = null;
    CALL_sentenceTemplateStruct template = null;
    CALL_sentenceWordStruct newWord = null;
    CALL_wordStruct tempWord = null;
    CALL_wordStruct tempWord2 = null;

    String templateStr = null;

    String currentGrammarRule = null;
    String previousGrammarRule = null;

    int i;

    int diffCostInt;
    int comCostInt;

    StringTokenizer st, st2;
    String tempString;
    String keyString;
    String numberString;
    String wordString;
    String typeString;
    String labelString;
    String dataString;
    String formString = null;

    Integer id;
    int id_val;
    int wordType;

    Vector previousWords = null;
    Vector tempVector = null;
    Vector totalWordsAdded = null;
    Vector wordsAdded = null;

    Vector validTemplates = null;
    Vector words = null;
    Vector moreWords = null;
    Vector tempV = null;
    Vector tempV2 = null;
    Vector parmsToPass = null;

    // For the sentences form calculations
    Vector formVector = null;
    String formName = null;
    CALL_formStruct form = null;
    CALL_formInstanceStruct formInstance = null;
    CALL_formInstanceStruct currentTemplateForm = null;
    String formStr = null;
    String currentFormStr = null;

    boolean found;
    boolean form_complete;
    int index;
    int formTemplateCount;
    int formTemplateIndex;
    int chosenFormIndex;

    Random rand = new Random();

    // logger.debug("Calling recursive resolve: Rule: " + grule.name +
    // ", Cost: " + diffCost + ", Rule Path: " + fullRule);
    // Set the current rule path
    if (fullRule == null) {
      previousGrammarRule = grule.name;
    } else {
      previousGrammarRule = fullRule + "." + grule.name;
    }

    // STEP 1: GRT All FORMS SPECIFIED BY THIS RULE TO THE SENTENCE RULE
    // SETTINGS
    // STEP 2: PARSE ALL OF THE TEMPLATES WITHIN THIS RULE

    // Go through each element in template
    if (grule.templates.size() <= 0) {
      // No templates, invalid rule
      return prevWords;
    }

    // Get the list of valid templates at this point
    validTemplates = new Vector();

    // Set the words added to the previous value, just in case it all changes
    // totalWordsAdded = new Vector();

    formTemplateCount = 0;

    // First check the restricted templates to see if we have any matching
    // cases//
    // If we didn't match with any restricted templates, use the default ones
    // ============================================================
    if (validTemplates.size() <= 0) {
      for (i = 0; i < grule.templates.size(); i++) {
        temp_template = (CALL_sentenceTemplateStruct) grule.templates.elementAt(i);
        if (temp_template != null) {
          // No restriction, so can be used
          validTemplates.addElement(temp_template);
        }
      }
    }

    // This happens if we have no unrestricted templates, and no matching
    // restricted ones. In this case, the rule is nulled, and ignored
    if (validTemplates.size() <= 0) {
      return prevWords;
    }

    // Now go through all the valid templates at this level, expanding them, and
    // adding them to the word passed in
    // If the passed in word is NULL, this is the sentence start so each new
    // word should be an element in the top
    // level sentences vector
    for (int t = 0; t < validTemplates.size(); t++) {
      // logger.debug("Processing next template");

      // Get the next template
      template = (CALL_sentenceTemplateStruct) validTemplates.elementAt(t);
      if (template != null) {
        // Set the form for this template (if specified, else use the default)

        templateStr = template.structure;
        // logger.debug("Template String: " + templateStr);

        st = new StringTokenizer(templateStr);

        while (st.hasMoreTokens()) {
          wordsAdded = new Vector();

          tempString = CALL_io.getNextBracketedToken(st); // This should thus
                                                          // get {ITEM cost} etc
          // logger.debug("Processing template token [" + tempString + "]");

          if (tempString != null) {
            if (tempString.length() >= 3) {
              // no variable
              if (tempString.startsWith("(")) {
                // A direct lexical reference
                // logger.debug("Direct lexical reference");
                currentGrammarRule = previousGrammarRule;

                index = -1;
                index = tempString.indexOf(')', 0);
                if (index != -1) {
                  // Trim final ) as well as first
                  keyString = tempString.substring(1, index);
                } else {
                  // Just trim opening (
                  keyString = tempString.substring(1, tempString.length());
                }

                // logger.debug("Using string: " + keyString);

                // Should be 4 strings - the type, the value, and the 2 cost
                // coefficients
                typeString = null;
                dataString = null;
                diffCostInt = diffCost;
                comCostInt = comCost;

                st2 = new StringTokenizer(keyString);
                if (st2.hasMoreTokens()) {
                  typeString = CALL_io.getNextToken(st2);
                  if (st2.hasMoreTokens()) {
                    dataString = CALL_io.getNextToken(st2);
                  }
                }

                // logger.debug("Adding lexical reference, type: " + typeString
                // + ", Value: " + dataString + ", Cost: " + diffCostInt +
                // ", CCost: " + comCostInt);

                if ((typeString != null) && (dataString != null)) {
                  // Get type
                  if (typeString.equals("verb")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_VERB;
                  } else if (typeString.equals("noun")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN;
                  } else if (typeString.equals("adjective")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_ADJECTIVE;
                  } else if (typeString.equals("adjverb")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_ADJVERB;
                  } else if (typeString.equals("adverb")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_ADVERB;
                  } else if (typeString.equals("particle")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_PARTICLE_AUXIL;
                  } else if (typeString.equals("time")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN_TIME;
                  } else if (typeString.equals("definitive")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN_DEFINITIVES;
                  } else if (typeString.equals("digit")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN_NUMERAL;
                  } else if (typeString.equals("position")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN;
                  }
                  // else if(typeString.equals("misc"))
                  // {
                  // wordType = CALL_lexiconStruct.LEX_TYPE_MISC;
                  // }
                  else if (typeString.equals("postfix")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN_SUFFIX;
                  } else if (typeString.equals("prefix")) {
                    wordType = CALL_lexiconStruct.LEX_TYPE_NOUN_PREFIX;
                  } else {
                    wordType = CALL_lexiconStruct.LEX_TYPE_UNSPECIFIED;
                  }

                  if (wordType == CALL_lexiconStruct.LEX_TYPE_VERB) {
                    if (dataString != null) {
                      // We have a list of verbs, get the right form, and add
                      tempV = db.lexicon
                          .getWords(null, wordType, CALL_lexiconStruct.LEX_FORMAT_UNSPECIFIED, dataString);

                      if (tempV != null) {
                        // Vector for alternatives
                        words = new Vector();

                        for (int j = 0; j < tempV.size(); j++) {
                          // Get the correct form for each verb
                          tempWord = (CALL_wordStruct) tempV.elementAt(j);

                          // Temp debug
                          // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                          // CALL_debug.DEBUG, "Creating new word: " + tempWord
                          // + ", " + grule + ", " + currentFormStr);

                          newWord = new CALL_sentenceWordStruct(db, tempWord, grule.name, currentGrammarRule,
                              grule.description, template.rule, currentTemplateForm.sign, currentTemplateForm.tense,
                              currentTemplateForm.style, currentTemplateForm.type);
                          newWord.cost_grammar = diffCostInt;
                          newWord.cost_communication = comCostInt;
                          words.addElement(newWord);

                          // The wordsAdded list is all words added at this
                          // template stage which will be
                          // follow on words in the next stage
                          wordsAdded.addElement(newWord);
                        }

                        if (words.size() > 0) {
                          // Add the words to the current sentence structure
                          // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                          // CALL_debug.DEBUG, "Adding vector of " +
                          // words.size() + " words");
                          addWordsToSentence(previousWords, words);
                        } else {
                          // No words - warning
                          // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                          // CALL_debug.WARN, "No words found to match [" +
                          // dataString + "]");

                        }
                      } else {
                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                        // CALL_debug.WARN, "No words list found to match [" +
                        // dataString + "]");
                      }
                    }
                  } else {
                    // First, trim anything after and including the trailing
                    // bracket
                    index = -1;
                    index = dataString.indexOf(')', 0);
                    if (index != -1) {
                      dataString = dataString.substring(0, index);
                    }

                    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                    // CALL_debug.DEBUG, "Looking up " + dataString);

                    // Then add word Will need to actually specifiy the type if
                    // possible! - CJW
                    tempV = db.lexicon.getWords(null, wordType, CALL_lexiconStruct.LEX_FORMAT_UNSPECIFIED, dataString);

                    if (tempV != null) {
                      // Add the words along with null form structures (no form
                      // needed, as not a verb)
                      words = new Vector();

                      for (int j = 0; j < tempV.size(); j++) {
                        tempWord = (CALL_wordStruct) tempV.elementAt(j);

                        if (wordType == CALL_lexiconStruct.LEX_TYPE_NOUN_SUFFIX) {
                          // Postfix - add to previous sentence word list
                          if (previousWords != null) {
                            for (int k = 0; k < previousWords.size(); k++) {
                              newWord = (CALL_sentenceWordStruct) previousWords.elementAt(k);
                              newWord.postfix.addElement(tempWord);

                              // We actually re-add the name to the list again,
                              // as this is the word
                              // any following words will actually link from,
                              // not from the postfix
                              // itself which is just a sub-word
                              wordsAdded.addElement(newWord);
                            }
                            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                            // CALL_debug.DEBUG, "Adding postfix " +
                            // tempWord.kana);
                          }
                        } else {
                          newWord = new CALL_sentenceWordStruct(db, tempWord, grule.name, currentGrammarRule,
                              grule.description);
                          newWord.cost_grammar = diffCostInt;
                          newWord.cost_communication = comCostInt;
                          words.addElement(newWord);

                          // The wordsAdded list is all words added at this
                          // template stage which will be
                          // follow on words in the next stage
                          wordsAdded.addElement(newWord);

                          // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                          // CALL_debug.DEBUG, "Adding word: " +
                          // newWord.word.kanji + ", CostD: " +
                          // newWord.cost_grammar + ", CostC: " +
                          // newWord.cost_communication + ", Alt ID: " + j);

                          // Now add the word list to the sentence
                          if (words.size() > 0) {
                            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                            // CALL_debug.DEBUG, "Adding vector of " +
                            // words.size() + " words");
                            addWordsToSentence(previousWords, words);
                          } else {
                            // No words - warning
                            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                            // CALL_debug.WARN, "No words found to match [" +
                            // dataString + "]");
                          }
                        }
                      }
                    } else {
                      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                      // CALL_debug.WARN, "No words list found to match [" +
                      // dataString + "]");
                    }
                  }
                }
              } else if (tempString.startsWith("[")) {
                // A concept instance reference
                // =========================
                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                // CALL_debug.DEBUG, "A concept instance reference");

                keyString = tempString.substring(1, tempString.length() - 1);
                found = false;

                // There should be 2 strings, the reference, and the cost
                // coefficient
                dataString = null;
                diffCostInt = diffCost;
                comCostInt = comCost;

                st2 = new StringTokenizer(keyString);
                if (st2.hasMoreTokens()) {
                  dataString = CALL_io.getNextToken(st2);
                  currentGrammarRule = previousGrammarRule + "." + dataString;

                  if (st2.hasMoreTokens()) {
                    // We're now looking for the difficulty based cost
                    // ----------------------------------------
                    diffCostInt = CALL_io.getNextInt(st2);
                    if ((diffCostInt == CALL_io.INVALID_INT) || (diffCostInt == -1)) {
                      // We'll use the cost passed in instead
                      diffCostInt = diffCost;
                    }

                    if (st2.hasMoreTokens()) {
                      // We're now looking for the communication based cost
                      // ---------------------------------------------
                      comCostInt = CALL_io.getNextInt(st2);
                      if ((comCostInt == CALL_io.INVALID_INT) || (comCostInt == -1)) {
                        // We'll use the cost passed in instead
                        comCostInt = comCost;
                      }
                    }
                  }
                }

                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                // CALL_debug.DEBUG, "Conceptual reference: " + dataString +
                // ", CostD: " + diffCostInt + ", CostC: " + comCostInt);

                // Try and find this slot in the instance
                for (i = 0; i < instance.getLabel().size(); i++) {
                  if (i >= instance.getData().size()) {
                    // Must be a mismatch in the label and data vectors
                    break;
                  }

                  labelString = (String) instance.getLabel().elementAt(i);
                  if (labelString != null) {
                    if (labelString.equals(dataString)) {
                      // We have our match
                      found = true;
                      dataString = (String) instance.getData().elementAt(i);
                      id = (Integer) instance.getId().elementAt(i);

                      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                      // CALL_debug.DEBUG, "Found in instance!  Data: " +
                      // dataString + ", ID: " + id);
                      if (id != null) {
                        id_val = id.intValue();
                        if (id_val >= 0) {
                          // Use the ID value to find the exact word we're after
                          tempV = db.lexicon.getWords(id_val);
                        } else {
                          // Use the string
                          tempV = db.lexicon.getWords(null, CALL_lexiconStruct.LEX_TYPE_UNSPECIFIED,
                              CALL_lexiconStruct.LEX_FORMAT_ENGLISH, dataString);
                        }
                      } else {
                        // Use the string
                        if (dataString != null) {
                          tempV = db.lexicon.getWords(null, CALL_lexiconStruct.LEX_TYPE_UNSPECIFIED,
                              CALL_lexiconStruct.LEX_FORMAT_ENGLISH, dataString);
                        }
                      }

                      if ((tempV != null) && (tempV.size() > 0)) {
                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                        // CALL_debug.DEBUG, "Found " + tempV.size() +
                        // " words in dictionary");
                        words = new Vector();

                        // Now take the vector of words, and combine with form
                        // information
                        for (int j = 0; j < tempV.size(); j++) {
                          // Get the correct form for each verb
                          tempWord = (CALL_wordStruct) tempV.elementAt(j);

                          if (tempWord.type == CALL_lexiconStruct.LEX_TYPE_VERB) {
                            // If its a verb (etc) need to do the form
                            if (currentTemplateForm != null) {
                              newWord = new CALL_sentenceWordStruct(db, tempWord, labelString, currentGrammarRule,
                                  grule.description, template.rule, currentTemplateForm.sign,
                                  currentTemplateForm.tense, currentTemplateForm.style, currentTemplateForm.type);

                              newWord.cost_grammar = diffCostInt;
                              newWord.cost_communication = comCostInt;

                              // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                              // CALL_debug.DEBUG, "Adding verb: " +
                              // newWord.word.kanji + ", CostD: " +
                              // newWord.cost_grammar + ", CostC: " +
                              // newWord.cost_communication);
                              words.addElement(newWord);

                              // The wordsAdded list is all words added at this
                              // template stage which will be
                              // follow on words in the next stage
                              wordsAdded.addElement(newWord);
                            }
                          } else if (template.type == CALL_sentenceTemplateStruct.TYPE_COUNTER) {
                            // If it is a counter, we need to convert it
                            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                            // CALL_debug.DEBUG, "Dealing with a counter!");

                            // First, get the object we are counting (from
                            // counter_form)
                            if (template.counter_form != null) {
                              id_val = instance.getWordID(template.counter_form);
                              if (id_val >= 0) {
                                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                // CALL_debug.DEBUG, "Reference word(s) found");

                                // Use the ID value to find the exact word we're
                                // after
                                tempV2 = db.lexicon.getWords(id_val);

                                for (int k = 0; k < tempV2.size(); k++) {
                                  // For each number word (tempV), and then each
                                  // object word (tempV2), add a sentence word
                                  tempWord2 = (CALL_wordStruct) tempV2.elementAt(k);

                                  newWord = new CALL_sentenceWordStruct(db, tempWord, tempWord2, labelString,
                                      currentGrammarRule, grule.description);
                                  newWord.cost_grammar = diffCostInt;
                                  newWord.cost_communication = comCostInt;

                                  // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                  // CALL_debug.DEBUG, "Adding sentence word: "
                                  // + tempWord.kanji +", counter reference " +
                                  // tempWord2.kanji);
                                  words.addElement(newWord);

                                  // The wordsAdded list is all words added at
                                  // this template stage which will be
                                  // follow on words in the next stage
                                  wordsAdded.addElement(newWord);
                                }
                              } else {
                                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                // CALL_debug.WARN,
                                // "But no referencing object word!");
                              }
                            }
                          } else {
                            // Just add the word as is (no form modification
                            // etc)
                            newWord = new CALL_sentenceWordStruct(db, tempWord, labelString, currentGrammarRule,
                                grule.description);
                            newWord.cost_grammar = diffCostInt;
                            newWord.cost_communication = comCostInt;

                            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                            // CALL_debug.DEBUG, "Adding word: " +
                            // newWord.word.kanji + ", CostD: " +
                            // newWord.cost_grammar + ", CostC: " +
                            // newWord.cost_communication);
                            words.addElement(newWord);

                            // The wordsAdded list is all words added at this
                            // template stage which will be
                            // follow on words in the next stage
                            wordsAdded.addElement(newWord);
                          }

                        }

                        // Add the words to the currently forming sentence
                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                        // CALL_debug.DEBUG, "Adding vector of " + words.size()
                        // + " words");
                        addWordsToSentence(previousWords, words);
                      } else {
                        // No words - assume its a disabled concept tag, don't
                        // resolve the rule any further
                        if (totalWordsAdded.size() <= 0) {
                          // No words added in this parse, use the words passed
                          // in originally
                          totalWordsAdded = prevWords;
                        }
                        return totalWordsAdded;
                      }

                      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                      // CALL_debug.DEBUG, "Added [" + keyString + "]");
                    }
                  }
                }

                if (!found) {
                  // The concept does not exist (Changed from returng
                  // previousWords - CJW)
                  return prevWords;
                }
              } else if (tempString.startsWith("{")) {
                // A sub-rule
                // =========
                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                // CALL_debug.DEBUG, "A sub rule to expand");

                // A sub rule - trim the brackets
                keyString = tempString.substring(1, tempString.length() - 1);
                currentGrammarRule = previousGrammarRule;

                // There should be 3 strings; the reference, and the two cost
                // coefficients
                dataString = null;
                diffCostInt = diffCost;
                comCostInt = comCost;

                st2 = new StringTokenizer(keyString);
                if (st2.hasMoreTokens()) {
                  dataString = CALL_io.getNextToken(st2);

                  if (st2.hasMoreTokens()) {
                    // We're now looking for the difficulty based cost
                    // ----------------------------------------
                    diffCostInt = CALL_io.getNextInt(st2);
                    if ((diffCostInt == CALL_io.INVALID_INT) || (diffCostInt == -1)) {
                      // We'll use the cost passed in instead
                      diffCostInt = diffCost;
                    }

                    if (st2.hasMoreTokens()) {
                      // We're now looking for the communication based cost
                      // ---------------------------------------------
                      comCostInt = CALL_io.getNextInt(st2);
                      if ((comCostInt == CALL_io.INVALID_INT) || (comCostInt == -1)) {
                        // We'll use the cost passed in instead
                        comCostInt = comCost;
                      }
                    }
                  }
                }

                // Does the rule also have parameters (these are optional, so
                // come LAST, after the costs - CJW
                if (st2.hasMoreTokens()) {
                  parmsToPass = new Vector();
                  while (st2.hasMoreTokens()) {
                    parmsToPass.addElement(CALL_io.getNextToken(st2));
                  }
                  // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                  // CALL_debug.DEBUG, "Adding Subrule: " + dataString +
                  // "; CostD: " + diffCostInt + "; CostC: " + comCostInt + ";"
                  // + parmsToPass.size() + " Parameters");
                } else {
                  // No parameters to be passed
                  parmsToPass = null;
                  // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                  // CALL_debug.DEBUG, "Adding Subrule: " + dataString +
                  // "; CostD: " + diffCostInt + "; CostC: " + comCostInt );
                }

                subRule = db.grules.getGrammarRule(grule, dataString);
                if (subRule != null) {
                  // Recursively call the subrule
                  tempVector = recursive_resolve(currentGrammarRule, previousWords, subRule, parmsToPass, instance,
                      diffCostInt, comCostInt);
                  if (tempVector != null) {
                    // Add the final words from the sub-grammar into the added
                    // words vector which
                    // will get used as the previousWords in the next template
                    // item
                    wordsAdded.addAll(tempVector);
                  }
                } else {
                  // Error, failed to find subrule in database
                  // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                  // CALL_debug.ERROR, "Failed to find subrule [" + keyString +
                  // "]");
                }

              } // Token begins with {, [, < etc
            } // If token is at least 3 chars long
          } // If token != null

          // Now update the previous words vector to be all the (final) words
          // added for the current template object
          previousWords = wordsAdded;

        } // While more tokens in template

        // The last set of words added will be the final ones from the template,
        // the ones any further templates will append to
        if ((wordsAdded != null) && (wordsAdded.size() > 0)) {
          totalWordsAdded.addAll(wordsAdded);
        }

      } // If template != null
    } // For i = 0 to all templates

    if (totalWordsAdded.size() <= 0) {
      // No words added in this parse, use the words passed in originally
      totalWordsAdded = prevWords;
    }

    return totalWordsAdded;
  }

  // Takes a restriction string, and sees if an instance matches that string
  // eg1. [VERB] == is_also
  // eg2. ([VERB] == give) && ([SUBJ] == oneself)
  // eg3. [VERB] exists
  // CURRENT RESTRICTION: ONLY 1 OPERATOR PER CLAUSE!! IE.
  // (A && B) && C is OK!
  // A && B && C is not!!
  public boolean restriction_match(CALL_grammarRuleStruct grule, String restriction,
      CALL_conceptInstanceStruct instance, Vector parameters) {
    String lhString, rhString, operatorString;
    String tempString;
    String clauseString = null;
    String subClauseString = null;
    StringTokenizer st;
    int lhID, rhID;
    int clause_level = 0;
    boolean rc = false;
    Vector tempV;
    CALL_wordStruct tempWord;

    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
    // "Attempting to resolve restriction [" + restriction + "]");
    // logger.debug("Attempting to resolve restriction: [" + restriction + "]");

    // Resolve all sub clauses first
    st = new StringTokenizer(restriction);
    int intRestrict = 0;
    while (st.hasMoreTokens()) {

      tempString = CALL_io.getNextToken(st);

      // logger.debug("restriction, part[: " + intRestrict + "]: "+ tempString
      // );

      intRestrict++;

      if (tempString != null) {
        if (tempString.startsWith("(")) // means have sub clauses
        {
          // logger.debug(" string startsWith -----[(]" );

          if (clause_level == 0) {
            subClauseString = tempString; // eg, "(A && B)"
            // logger.debug(" clause_level == 0 ");
          } else {
            // A sub-sub-clause. Add to string, will be dealt with in recursive
            // function
            subClauseString = subClauseString + " " + tempString;

          }
          // logger.debug(" sub clause string == " + subClauseString );
          // New sub clause
          clause_level++;
          // logger.debug(" clause_level++ and == " +clause_level);

        } else if (tempString.endsWith(")")) {
          // logger.debug(" string endsWith -----[)]" );

          clause_level--;
          // logger.debug(" clause_level-- and == " +clause_level);

          subClauseString = subClauseString + " " + tempString; // eg,
                                                                // "(A && B) B"
          // logger.debug(" sub clause string == " + subClauseString );

          if (clause_level == 0) {
            // Finished the sub-clause, process it and add to clauseString
            if (restriction_match(grule, subClauseString, instance, parameters)) {
              // logger.debug("Match" );

              if (clauseString == null) {
                clauseString = new String("true");
              } else {
                clauseString = clauseString + " true";
              }
              // logger.debug(" clauseString: "+clauseString );
            } else {
              // logger.debug("No Match" );
              if (clauseString == null) {
                clauseString = new String("false");
              } else {
                clauseString = clauseString + " false";
              }
              // logger.debug(" clauseString: "+clauseString );
            }
          }
        } else {
          // logger.debug(" No Operater like ( or )" );
          if (clauseString == null) {
            clauseString = tempString;
          } else {
            clauseString = clauseString + " " + tempString;
          }
        }
      }
    }

    // Finished pre-processing the clause, now evaluate (should be 3 strings for
    // comparrison clauses, or 2 in the case of "exists")
    // logger.debug("Finished pre-processing the clause, now evaluate (should be 3 strings for comparrison clauses, or 2 in the case of [exists]"
    // );
    // logger.debug("clause string: " +clauseString);

    st = new StringTokenizer(clauseString);

    lhString = CALL_io.getNextToken(st);
    operatorString = CALL_io.getNextToken(st);
    rhString = CALL_io.getNextToken(st);

    if ((rhString == null) && (operatorString != null) && (lhString != null)) {
      // It's a 2 stringer (or less) - only allowed for the "exists" case
      // logger.debug("The clause is consist of two parts");

      if (operatorString.equals("exists")) {
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Checking whether " + lhString +" exists");
        // logger.debug("Checking whether [" + lhString +"] exists");

        tempString = instance.getDataString(lhString);
        if (tempString != null) {
          // There is a word for this slot
          // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
          // "It does (" + tempString + ")");
          // logger.debug("It does, (" + tempString + ")");

          rc = true;
        } else {
          // There is no word for this slot
          // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
          // "It doesn't");
          // logger.debug("It doesn't");
          rc = false;
        }
      }
    } else if ((lhString != null) && (operatorString != null) && (rhString != null)) {
      // logger.debug("The clause is consist of three parts");

      // Initialise IDs...only used if using lexicon reference
      rhID = -1;
      lhID = -1;

      // If LHS or RHS are variables, convert them!
      if (rhString.startsWith("|")) {
        rhString = convertParameter(rhString, grule, parameters);
      }

      if (lhString.startsWith("|")) {
        lhString = convertParameter(lhString, grule, parameters);
      }

      // Determine whether the word matches our restriction
      rc = db.lexicon.wordRestrictionMatch(instance, lhString, rhString, operatorString);

    }

    if (rc) {
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
      // "Match");
      // logger.debug("Match");
    } else {
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
      // "No Match");
      // logger.debug("No Match");
    }

    return rc;
  }

  // This adds a selection of words on to the end of the previous words
  // If there are no previous words, it adds the words as starting elements of
  // the sentence
  public void addWordsToSentence(Vector prevWords, Vector words) {
    CALL_sentenceWordStruct word, prevWord;
    int w, p;

    if (words != null) {
      if ((prevWords == null) || (prevWords.size() == 0)) {
        // Starting words
        for (w = 0; w < words.size(); w++) {
          word = (CALL_sentenceWordStruct) words.elementAt(w);
          if (word != null) {
            sentence.addElement(word);
            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
            // "Adding [" + word.word.kana + "] as starting word.");

            // Add to all words list
            if (allWords.indexOf(word) == -1) {
              allWords.addElement(word);
            }
          }
        }
      } else {
        // Append words to previous words
        for (p = 0; p < prevWords.size(); p++) {
          prevWord = (CALL_sentenceWordStruct) prevWords.elementAt(p);
          if (prevWord != null) {
            for (w = 0; w < words.size(); w++) {
              word = (CALL_sentenceWordStruct) words.elementAt(w);
              if (word != null) {
                // Add word to previous word, linking back as well
                word.previousWords = prevWords;
                prevWord.nextWords.addElement(word);

                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                // CALL_debug.DEBUG, "Adding [" + word.word.kana +
                // "] to follow word " + prevWord.word.kana);

                // Add to all words list
                if (allWords.indexOf(word) == -1) {
                  allWords.addElement(word);
                }
              }
            }
          }
        }
      }
    }
  }

  // Returns a vector of words for the top sentence only
  // This is currently used when determining which words
  // to add experience for during practice
  // ===============================================
  public Vector getWords() {
    Vector rVector;
    CALL_sentenceWordStruct tempSentenceWord, nextSentenceWord;
    CALL_wordStruct tempWord;

    rVector = new Vector();

    if ((sentence != null) && (sentence.size() > 0)) {
      nextSentenceWord = (CALL_sentenceWordStruct) sentence.elementAt(0);

      for (;;) {
        // Follow the nextWord links until reaching the end of the sentence
        if (nextSentenceWord != null) {
          tempSentenceWord = nextSentenceWord;
          tempWord = tempSentenceWord.word;

          // Add the word
          if (tempWord != null) {

            rVector.addElement(tempWord);
          }

          // Move on to the next word
          if ((tempSentenceWord.nextWords != null) && (tempSentenceWord.nextWords.size() != 0)) {
            nextSentenceWord = (CALL_sentenceWordStruct) tempSentenceWord.nextWords.elementAt(0);
          } else {
            nextSentenceWord = null;
          }
        } else {
          // Finished the sentence
          break;
        }
      }
    }

    return rVector;
  }

  // Returns a vector of SENTENCE words for the top sentence only
  // Differenct from getSentenceWords in that it includes ALL the
  // words from the various templates that match the desired top
  // level component pattern
  // =======================================================
  public Vector getSentenceWordVectors() {
    Vector wordList;
    Vector wVector;
    Vector rVector;
    String fullRule;
    CALL_sentenceWordStruct nextSentenceWord;

    rVector = new Vector();

    // Use the top level only
    if ((sentence != null) && (sentence.size() > 0)) {
      nextSentenceWord = (CALL_sentenceWordStruct) sentence.elementAt(0);
      for (;;) {
        // Follow the nextWord links until reaching the end of the sentence
        if (nextSentenceWord != null) {
          // What is the full grammar rule of this word? Add all the words in
          // our lattice that match this rule
          fullRule = nextSentenceWord.fullGrammarRule;
          if (fullRule != null) {
            wVector = getFullGrammarWords(fullRule);
            if ((wVector != null) && (wVector.size() > 0)) {
              rVector.addElement(wVector);
            }
          }

          // Move on to the next word
          if ((nextSentenceWord.nextWords != null) && (nextSentenceWord.nextWords.size() != 0)) {
            nextSentenceWord = (CALL_sentenceWordStruct) nextSentenceWord.nextWords.elementAt(0);
          } else {
            nextSentenceWord = null;
          }
        } else {
          // Finished the sentence
          break;
        }
      }
    }

    return rVector;
  }

  // Returns a vector of SENTENCE words for the top sentence only
  // This is used when creating the hint structures
  // =======================================================
  public Vector getSentenceWords() {
    Vector wordList;
    Vector rVector;
    CALL_sentenceWordStruct nextSentenceWord;

    rVector = new Vector();

    // Use the top level only
    if ((sentence != null) && (sentence.size() > 0)) {
      nextSentenceWord = (CALL_sentenceWordStruct) sentence.elementAt(0);
      for (;;) {
        // Follow the nextWord links until reaching the end of the sentence
        if (nextSentenceWord != null) {
          // Add the word
          rVector.addElement(nextSentenceWord);

          // Move on to the next word
          if ((nextSentenceWord.nextWords != null) && (nextSentenceWord.nextWords.size() != 0)) {
            nextSentenceWord = (CALL_sentenceWordStruct) nextSentenceWord.nextWords.elementAt(0);
          } else {
            nextSentenceWord = null;
          }
        } else {
          // Finished the sentence
          break;
        }
      }
    }

    return rVector;
  }

  // All sentence words in the network, not just the top sentence ones
  // ========================================================
  public Vector getAllSentenceWords() {
    return allWords;
  }

  // Returns a string representing the top sentence only
  // ===============================================
  public String getSentenceString(int format) {
    Vector wordList;
    Vector alternatives;
    String sentenceStr;
    String tempString;
    CALL_sentenceWordStruct tempSentenceWord;
    CALL_wordStruct tempWord;

    int form[];
    int s, t, q, p;

    sentenceStr = new String("");

    wordList = getSentenceWords();
    if (wordList != null) {
      for (int i = 0; i < wordList.size(); i++) {
        tempSentenceWord = (CALL_sentenceWordStruct) wordList.elementAt(i);
        if (tempSentenceWord != null) {
          tempWord = tempSentenceWord.word;

          if (tempWord != null) {

            // Get the formatted word string (including correct form etc)
            tempString = tempSentenceWord.getTopWordString(format);

            if (tempString != null) {
              if (tempSentenceWord.omit) {
                // Word omitted - need to consider sentence endings...
                if (tempString.endsWith("?")) {
                  tempString = new String("?");
                } else if (tempString.endsWith("!")) {
                  tempString = new String("!");
                } else {
                  tempString = new String("");
                }
              }

              if ((format == CALL_io.romaji) && (i != 0)) {
                sentenceStr = sentenceStr + " " + tempString;
              } else {
                sentenceStr = sentenceStr + tempString;
              }
            }
          }
        }
      }
    }

    return sentenceStr;
  }

  // Returns a string representing the top sentence only
  // have a space between words
  public String getTopSentenceString(int format) {
    Vector wordList;
    Vector alternatives;
    String sentenceStr;
    String tempString;
    CALL_sentenceWordStruct tempSentenceWord;
    CALL_wordStruct tempWord;

    int form[];
    int s, t, q, p;

    sentenceStr = new String("");

    wordList = getSentenceWords();
    if (wordList != null) {
      for (int i = 0; i < wordList.size(); i++) {
        tempSentenceWord = (CALL_sentenceWordStruct) wordList.elementAt(i);
        if (tempSentenceWord != null) {
          tempWord = tempSentenceWord.word;

          if (tempWord != null) {

            // Get the formatted word string (including correct form etc)
            tempString = tempSentenceWord.getTopWordString(format);

            if (tempString != null) {
              if (tempSentenceWord.omit) {
                // Word omitted - need to consider sentence endings...
                if (tempString.endsWith("?")) {
                  tempString = new String("?");
                } else if (tempString.endsWith("!")) {
                  tempString = new String("!");
                } else {
                  tempString = new String("");
                }
              }

              if ((format == CALL_io.romaji) && (i != 0)) {
                sentenceStr = sentenceStr + " " + tempString;
              } else {
                sentenceStr = sentenceStr + " " + tempString;
              }
            }
          }
        }
      }
    }

    return sentenceStr;
  }

  // Takes a parameter name, and gives the associated value if it exists
  // This is used when passing parameters into grammar rules
  public String convertParameter(String tempString, CALL_grammarRuleStruct grule, Vector parameters) {
    // logger.debug("enter convertParameter");
    String returnString = tempString;
    int index;

    // Remove the |s if necessary...sometimes this is done before getting to
    // this point, so be carefull
    if (tempString.startsWith("|")) {
      // The opening bracket
      tempString = tempString.substring(1, tempString.length());
    }

    index = -1;
    index = tempString.indexOf('|', 0);
    if (index != -1) {
      // Trim final | as well as first
      tempString = tempString.substring(0, index);
    }

    if ((parameters != null) && (grule.parameters != null)) {
      for (int p = 0; p < grule.parameters.size(); p++) {
        if (tempString.equals((String) grule.parameters.elementAt(p))) {
          // We have found the matching parameter, now see if we have a value!
          if (p < parameters.size()) {
            returnString = (String) parameters.elementAt(p);
          }
        }
      }
    }

    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
    // "Parameter |" + tempString + "| converted to " + returnString);

    return returnString;
  }

  // Returns all strings that validly express the concept
  // Does a recursive depth-first search of the grammar
  // ===============================================
  public Vector getAllSentenceStrings(int format, boolean extraInfo) {
    Vector allStrings = new Vector();
    CALL_sentenceWordStruct startWord;

    for (int i = 0; i < sentence.size(); i++) {
      startWord = (CALL_sentenceWordStruct) sentence.elementAt(i);
      if (startWord != null) {
        recursiveStringConstructor(null, startWord, allStrings, format, extraInfo);
      }
    }

    return allStrings;
  }

  // The recursive string construction function
  // ===============================================
  private Vector recursiveStringConstructor(String prefix, CALL_sentenceWordStruct currentWord, Vector allStrings,
      int format, boolean extraInfo) {
    Vector allTheStrings;
    Vector alternatives;
    String tempString = null;
    CALL_wordStruct wordObj = null;
    CALL_sentenceWordStruct nextWord = null;
    String wordString = null;
    String sentenceString = null;
    boolean exists;

    allTheStrings = allStrings;

    if (currentWord != null) {
      // First, add the string for the current word
      // For counters...if a word can have more than one string, do that here...
      alternatives = currentWord.getWordStrings(format);

      if (alternatives != null) {
        for (int a = 0; a < alternatives.size(); a++) {
          // Note - we don't worry about word prefixes and suffixes here, as
          // these are already contained within the alternatives vector
          wordString = (String) alternatives.elementAt(a);

          if (wordString != null) {
            if (prefix != null) {
              // Already have some sentence
              sentenceString = prefix + " " + wordString;
            } else {
              // Starting from scratch
              sentenceString = wordString;
            }

            // If we have extra Information, add that now
            if (extraInfo) {
              wordObj = currentWord.word;
              if (wordObj != null) {
                sentenceString += " [" + wordObj.type + " " + wordObj.id + "]";
              }
            }

            // Now gor through all the nextNode links, doing the recursive
            // process
            if (currentWord.nextWords.size() > 0) {
              for (int i = 0; i < currentWord.nextWords.size(); i++) {
                nextWord = (CALL_sentenceWordStruct) currentWord.nextWords.elementAt(i);
                if (nextWord != null) {
                  allTheStrings = recursiveStringConstructor(sentenceString, nextWord, allTheStrings, format, extraInfo);
                }
              }
            } else {
              // If this is the last word in the chain, add the final string to
              // the all strings vector
              if (sentenceString != null) {
                // Check the string isn't already in the vector
                exists = false;
                for (int s = 0; s < allTheStrings.size(); s++) {
                  tempString = (String) allTheStrings.elementAt(s);
                  if (tempString != null) {
                    if (tempString.equals(sentenceString)) {
                      exists = true;
                      break;
                    }
                  }
                }

                if (!exists) {
                  allTheStrings.addElement(sentenceString);
                }
              }
            }
          }
        }
      }
    }

    return allTheStrings;
  }

  // Search for a word based on it's grammar tag (SUBJ, VERB etc)
  public CALL_sentenceWordStruct getGrammarWord(String grammarRule) {
    CALL_sentenceWordStruct word;
    CALL_sentenceWordStruct returnWord = null;

    for (int i = 0; i < allWords.size(); i++) {
      word = (CALL_sentenceWordStruct) allWords.elementAt(i);
      if (word != null) {
        if (word.grammarRule != null) {
          if (word.grammarRule.equals(grammarRule)) {
            // Found it
            returnWord = word;
            break;
          }
        }
      }
    }

    return returnWord;
  }

  // Search for words based on it's full length grammar tag
  // (DESU_SIMPLE.SUBJ.PARTICLE, etc)
  public Vector getFullGrammarWords(String grammarRule) {
    Vector words = new Vector();
    CALL_sentenceWordStruct word;

    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
    // "Looking for words with rule: " + grammarRule + " amongst " +
    // allWords.size() + " words");

    for (int i = 0; i < allWords.size(); i++) {
      word = (CALL_sentenceWordStruct) allWords.elementAt(i);
      if (word != null) {
        if (word.word != null) {
          // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
          // "Considering word: " + word.word.kanji);
          if (word.fullGrammarRule != null) {
            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
            // "Comparing rules: " + grammarRule + " with " +
            // word.fullGrammarRule);
            if (word.fullGrammarRule.equals(grammarRule)) {
              // Found one, so add it!
              // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
              // "Adding word: " + word.word.kanji);
              words.addElement(word);
            }
          }
        }
      }
    }

    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG, "Found " +
    // words.size() + " words for rule: " + grammarRule);
    return words;
  }

  public CALL_formInstanceStruct getForm(String formStr) {
    // logger.debug("enter getForm, formStr is: "+
    // formStr+" in CALL_sentenceStruct");
    CALL_formInstanceStruct form = null;
    String tempString;

    for (int f = 0; f < formNames.size(); f++) {
      tempString = (String) formNames.elementAt(f);
      if (tempString.equals(formStr)) {
        form = (CALL_formInstanceStruct) formSettings.elementAt(f);// the
                                                                   // matched
                                                                   // index is
                                                                   // always
                                                                   // [0];
        // logger.debug("in getForm, formStr is: "+
        // formStr+" the matched index is: "+f);
        break;
      }
    }
    return form;
  }

  public CALL_formStruct getFormStruct(String formStr) {
    // logger.debug("enter getFormStruct, formStr is: "+
    // formStr+" in CALL_sentenceStruct");
    CALL_formStruct form = null;
    String tempString;

    for (int f = 0; f < formNames.size(); f++) {
      tempString = (String) formNames.elementAt(f);
      if (tempString.equals(formStr)) {
        form = (CALL_formStruct) formSettingStructs.elementAt(f);// the matched
                                                                 // index is
                                                                 // always [0];
        // logger.debug("in getForm, formStr is: "+
        // formStr+" the matched index is: "+f);
        break;
      }
    }
    return form;
  }

  // This function takes the input sentence, and attempts to find the closest
  // matching template based on a number of features
  public Vector getMatchingTemplate(String input) {
    CALL_sentenceWordStruct nextSentenceWord;
    Vector closestMatch = null;
    int currentClosest = -1;

    for (int i = 0; i < sentence.size(); i++) {
      nextSentenceWord = (CALL_sentenceWordStruct) sentence.elementAt(0);
      if (nextSentenceWord != null) {
        // This function work recursively
        recursive_getMatchingScore(0, nextSentenceWord, input, closestMatch, currentClosest);
      }
    }

    return closestMatch;
  }

  // Used during template matching
  public void recursive_getMatchingScore(int score, CALL_sentenceWordStruct current, String remaining, Vector closestV,
      int closestI) {
    // Check the difference between current word
  }

  public void print_debug() {
    Vector wordList;
    Vector sentenceList;
    String sentenceStr;
    CALL_sentenceWordStruct tempSentenceWord;

    sentenceStr = getSentenceString(CALL_io.kanji);

    // First the sentence form
    // ====================
    if (formSet) {
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.INFO,
      // "Sentence Form: Unset");
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.INFO, "Type: "
      // + type);
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.INFO, "Sign: "
      // + sign);
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.INFO,
      // "Stlye: "+ style);
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.INFO,
      // "Tense: "+ tense);
    } else {
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.INFO,
      // "Sentence Form: Unset");
    }

    // First the top string only
    // ====================
    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.INFO,
    // "Top Sentence Created: " + sentenceStr);

    wordList = getSentenceWords();
    if (wordList != null) {
      for (int i = 0; i < wordList.size(); i++) {
        tempSentenceWord = (CALL_sentenceWordStruct) wordList.elementAt(i);
        if (tempSentenceWord != null) {
          tempSentenceWord.print_debug();
        }
      }
    }

    // Now for all the strings
    // ===================
    sentenceList = getAllSentenceStrings(CALL_io.kanji, false);
    if (sentenceList != null) {
      for (int j = 0; j < sentenceList.size(); j++) {
        sentenceStr = (String) sentenceList.elementAt(j);
        if (sentenceStr != null) {
          // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.INFO,
          // "Sentence[" + j + "]: " + sentenceStr);
        }
      }
    }
  }

  // public Vector getVGWNetwork() {
  // return vGWNetwork;
  // }
  //
  // public void setVGWNetwork(Vector network) {
  // vGWNetwork = network;
  // }
}