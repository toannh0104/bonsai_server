/////St//////////////////////////////////////////////////////////////
// Verb Rule Structures - holds the information about how to construct
//				  the various inflections for each verb group.
//				  All data is loaded from the verbRules.txt file.
//
///////////////////////////////////////////////////////////////////

package jcall;

import java.util.Vector;

import jcall.db.JCALL_Lexicon;

import org.apache.log4j.Logger;

public class CALL_sentenceHintsStruct {
  static Logger logger = Logger.getLogger(CALL_sentenceHintsStruct.class.getName());

  static final int BALANCED_TOTAL_COST = 2500;
  static final int BALANCED_COMPONENT_COST = 250;
  static final int MAX_HINT_COMPONENTS = 32;
  static final int LEVEL_COST_COEFFICIENT = 4;
  static final int LT_DELAY = 50;
  static final int MT_DELAY = 20;
  static final int ST_DELAY = 10;

  // For perceived difficulty calcs (percentage that the hint level difficulty
  // effects the total compared with component difficulty)
  static final int LEVEL_BIAS = 33;

  // Fields
  Vector wordHints; // Of type CALL_wordHintsStruct, one for each word in the
                    // target sentence
  Vector levelStrings; // Stores the names of the active levels
  CALL_database db;

  // If scaling of costs is used (in which case we use same measure to scale
  // error costs)
  double costCoefficient;

  // Empty Constructor
  public CALL_sentenceHintsStruct() {
    // Does nothing
    costCoefficient = 1.0;
    wordHints = new Vector();
    levelStrings = new Vector();
  }

  /*
   * Getter and setter Add by quyettv
   */

  public Vector getWordHints() {
    return wordHints;
  }

  public void setWordHints(Vector wordHints) {
    this.wordHints = wordHints;
  }

  public Vector getLevelStrings() {
    return levelStrings;
  }

  public void setLevelStrings(Vector levelStrings) {
    this.levelStrings = levelStrings;
  }

  public double getCostCoefficient() {
    return costCoefficient;
  }

  public void setCostCoefficient(double costCoefficient) {
    this.costCoefficient = costCoefficient;
  }

  public void reset(){
	  levelStrings.clear();
	  costCoefficient = 1.0;
	  wordHints.clear();
	  db = null;
  }
  // Constructor which also generates the full hint structure
  public CALL_sentenceHintsStruct(CALL_questionStruct question) {
    costCoefficient = 1.0;
    wordHints = new Vector();
    levelStrings = new Vector();
    generate(question);
  }

  // Generate the hint structure from the given question
  public void generate(CALL_questionStruct question) {
    CALL_sentenceStruct sentence;
    CALL_sentenceWordStruct sentenceWord;
    CALL_wordStruct word;
    CALL_wordHintsStruct wordHint;
    CALL_hintStruct currentHint = null;
    int oldCost;

    int currentLevel;

    int comsCost;
    int diffCost;
    int componentCost;
    int subHintCost;
    int lengthHintCost;

    int desiredTotalCost; // For cost balancing
    int totalCost; // For cost balancing
    int totalComponentCost; // For cost balancing

    Integer tempCost;

    double wordLearningImpact; // The maximum discount we can have via word
                               // experience (0-100%)

    double biasLT; // Bias towards long term
    double biasMT; // Bias towards medium term
    double biasST; // Bias towards short term

    double issuesCoefficientLT; // Experience based on Long Term exposure (all
                                // time experience)
    double issuesCoefficientMT; // Experience based on Medium Term exposure
                                // (current log-in session)
    double issuesCoefficientST; // Experience based on Short Term exposure
                                // (current lesson run)

    double wordBiasDiscountLT;
    double wordBiasDiscountMT;
    double wordBiasDiscountST;

    double wordBiasDiscount; // Total discount calculated from all the above

    // Word Experience (Exposure)
    long wordExperienceLT;
    long wordExperienceMT;
    long wordExperienceST;

    // Word Experience (Word issues)
    long wordIssuesLT;
    long wordIssuesMT;
    long wordIssuesST;

    String tempString, tempString2;

    int s, t, p, q; // Form settings

    // For creating the sub-hints
    int stages, stagenum;
    String stageString;

    Vector sentenceWords;

    Vector alternatives;

    // Set the database, we will need this for verb rules etc.
    db = question.db;

    // Create the level strings vector (the tags of what the levels are called)
    levelStrings = new Vector();

    if (question.config.hintLevelType) {
      // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
      // "Using Hint Category TYPE");
      levelStrings.addElement(new String("Type"));
    }
    if (question.config.hintLevelEnglish) {
      // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
      // "Using Hint Category ENGLISH");
      levelStrings.addElement(new String("English"));
    }
    if (question.config.hintLevelLength) {
      // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
      // "Using Hint Category BASE");
      levelStrings.addElement(new String("Length"));
    }
    if (question.config.hintLevelBase) {
      // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
      // "Using Hint Category BASE");
      levelStrings.addElement(new String("Base"));
    }
    if (question.config.hintLevelSurface) {
      // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
      // "Using Hint Category SURFACE");
      levelStrings.addElement(new String("Surface"));
    }

    sentence = question.sentence;
    if (sentence != null) {
      if (sentence.sentence.size() > 0) {
        totalCost = 0;

        // Get the list of words
        sentenceWords = sentence.getSentenceWords();

        // Just use the top element for the hints
        if ((sentenceWords != null) && (sentenceWords.size() > 0)) {
          for (int i = 0; i < sentenceWords.size(); i++) {
            // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
            // "Creating hint for sentence word [" + i + "]");
            sentenceWord = (CALL_sentenceWordStruct) sentenceWords.elementAt(i);

            if (sentenceWord != null) {
              // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
              // "Sentence word [" + sentenceWord.componentName + "]");
              word = sentenceWord.word;

              if (word != null) {
                // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
                // "Word [" + word.kana + "]");

                // Get the student's experience with this word
                if (db.currentStudent != null) {
                  wordExperienceLT = db.currentStudent.wordExperienceLT.getWordExperience(word.type, word.id);
                  wordExperienceMT = db.currentStudent.wordExperienceMT.getWordExperience(word.type, word.id);
                  wordExperienceST = db.currentStudent.wordExperienceST.getWordExperience(word.type, word.id);
                  wordIssuesLT = db.currentStudent.wordExperienceLT.getWordIssues(word.type, word.id);
                  wordIssuesMT = db.currentStudent.wordExperienceMT.getWordIssues(word.type, word.id);
                  wordIssuesST = db.currentStudent.wordExperienceST.getWordIssues(word.type, word.id);
                  // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
                  // "Word Experience [" + wordExperienceLT + "L, " +
                  // wordExperienceMT + "M, " + wordExperienceST +
                  // "S] - <Word Type + " + word.type + ", ID: " + word.id +
                  // ">");
                } else {
                  // This should only happen if generating sentences before
                  // log-in (for a test, for example)
                  wordExperienceLT = 0;
                  wordExperienceMT = 0;
                  wordExperienceST = 0;
                  wordIssuesLT = 0;
                  wordIssuesMT = 0;
                  wordIssuesST = 0;
                }

                // Right, we have enough info to make the hint
                wordHint = new CALL_wordHintsStruct();

                if (wordHint != null) {
                  // PERCEIVED COMPONENT DIFFICULTY
                  // (not used for cost, just perceived difficulty)
                  // =====================================
                  if (question.gconfig.useWordExperience) {
                    wordLearningImpact = question.gconfig.hintWordLearningImpact;
                    biasLT = question.gconfig.hintWordLearningBiasLT;
                    biasMT = question.gconfig.hintWordLearningBiasMT;
                    biasST = question.gconfig.hintWordLearningBiasST;

                    // Defaults (low means good accuracy record)
                    wordBiasDiscountLT = 100;
                    wordBiasDiscountMT = 100;
                    wordBiasDiscountST = 100;

                    // LONG TERM Coefficient - Long term success rate (high is
                    // good!)
                    if (wordExperienceLT > 0) {
                      issuesCoefficientLT = ((wordExperienceLT - wordIssuesLT) * 100) / wordExperienceLT;
                      // CALL_debug.printlog(CALL_debug.MOD_HINTS,
                      // CALL_debug.DEBUG, "Long Term Word AR%: " +
                      // issuesCoefficientLT);
                      wordBiasDiscountLT = 100 - ((wordLearningImpact * issuesCoefficientLT) / 100.0);
                    }

                    // MEDIUM TERM Coefficient - Medium term success rate
                    if (wordExperienceMT > 0) {
                      issuesCoefficientMT = ((wordExperienceMT - wordIssuesMT) * 100) / wordExperienceMT;
                      // CALL_debug.printlog(CALL_debug.MOD_HINTS,
                      // CALL_debug.DEBUG, "Medium Term Word AR%: " +
                      // issuesCoefficientMT);
                      wordBiasDiscountMT = 100 - ((wordLearningImpact * issuesCoefficientMT) / 100.0);
                    }

                    // SHORT TERM Coefficient - Short term success rate
                    if (wordExperienceST > 0) {
                      issuesCoefficientST = ((wordExperienceST - wordIssuesST) * 100) / wordExperienceST;
                      // CALL_debug.printlog(CALL_debug.MOD_HINTS,
                      // CALL_debug.DEBUG, "Short Term Word AR%: " +
                      // issuesCoefficientST);
                      wordBiasDiscountST = 100 - ((wordLearningImpact * issuesCoefficientST) / 100.0);
                    }

                    // Now bring it all together...
                    wordBiasDiscount = ((biasST * wordBiasDiscountST) + (biasMT * wordBiasDiscountMT) + (biasLT * wordBiasDiscountLT))
                        / (biasLT + biasMT + biasST + 1);

                    // CALL_debug.printlog(CALL_debug.MOD_HINTS,
                    // CALL_debug.DEBUG, "Student experience discount [" +
                    // wordBiasDiscount + "%]");
                    // CALL_debug.printlog(CALL_debug.MOD_HINTS,
                    // CALL_debug.DEBUG, "Breakdown: LT [" + wordBiasDiscountLT
                    // + "%], MT [" + wordBiasDiscountMT + "%], ST [" +
                    // wordBiasDiscountST + "%]");
                  } else {
                    // We will not discount based on word experience
                    wordBiasDiscount = 100.0;
                  }

                  // CREATE THE HINTS AT EACH LEVEL
                  // ==================================
                  currentLevel = 0;

                  // Component Type (Subject, Object, etc)
                  // ==================================================================================
                  if (question.config.hintLevelType) {
                    currentHint = new CALL_hintStruct();

                    currentHint.hintKana = sentenceWord.componentName;
                    currentHint.hintKanji = sentenceWord.componentName;

                    // Cost at this level
                    currentHint.cost = 0;
                    totalCost += currentHint.cost;

                    // CALL_debug.printlog(CALL_debug.MOD_HINTS,
                    // CALL_debug.DEBUG, "Level " + currentLevel + " Cost: " +
                    // currentHint.cost);

                    // Perceived difficulty at this level
                    currentHint.difficulty = (int) (100 - wordBiasDiscount);

                    wordHint.hints[currentLevel] = currentHint;
                    currentLevel++;
                  }

                  // Component Length (******)
                  // ==================================================================================
                  if (question.config.hintLevelLength) {
                    currentHint = new CALL_hintStruct();
                    if ((!sentenceWord.useFormSettings) && (sentenceWord.omit)) {
                      // An omitted word
                      currentHint.hintKana = new String("");
                      currentHint.hintKanji = new String("");
                    } else {
                      // Build up the length string
                      stageString = new String("");
                      for (int m = 0; m < word.kana.length(); m++) {
                        stageString = stageString + "*";
                      }

                      currentHint.hintKana = stageString;
                      currentHint.hintKanji = stageString;
                    }

                    // Cost at this level
                    lengthHintCost = db.costs.getHintCost(word.type, CALL_costWeightStruct.LENGTH_COST);
                    currentHint.cost = lengthHintCost;
                    totalCost += currentHint.cost;

                    // Add this hint
                    wordHint.hints[currentLevel] = currentHint;
                    // CALL_debug.printlog(CALL_debug.MOD_HINTS,
                    // CALL_debug.DEBUG, "Level " + currentLevel + " Cost: " +
                    // currentHint.cost);

                    // Perceived difficulty at this level
                    // Out of date now - CJW
                    currentHint.difficulty = (int) (100 - wordBiasDiscount);

                    // Move on to next level
                    currentLevel++;
                  }

                  // Dictionary form (may be final if no form)
                  // NOTE: THIS FORM HAS SUB-FORMS (we build the word up out of
                  // sylables)
                  // ==================================================================================
                  if ((question.config.hintLevelBase) || (question.config.hintLevelSylable)) {
                    currentHint = new CALL_hintStruct();
                    currentHint.hintKana = word.kana;
                    currentHint.hintKanji = word.kanji;

                    // CALL_debug.printlog(CALL_debug.MOD_HINTS,
                    // CALL_debug.DEBUG, "Adding dictionary hint: " +
                    // currentHint.hintKana + " - Level " + currentLevel);

                    // Cost of top level hint at this category level (dictionary
                    // level hint)
                    currentHint.cost = db.costs.getHintCost(word.type, CALL_costWeightStruct.DICTIONARY_COST);
                    totalCost += currentHint.cost;

                    // CALL_debug.printlog(CALL_debug.MOD_HINTS,
                    // CALL_debug.DEBUG, "Level " + currentLevel + " Cost: " +
                    // currentHint.cost);

                    // Perceived difficulty at this level
                    // Out of date now, need level bias! - CJW
                    currentHint.difficulty = (int) (100 - wordBiasDiscount);

                    if ((!sentenceWord.useFormSettings) && (sentenceWord.omit)) {
                      currentHint.hintKana = new String("<none>");
                      currentHint.hintKanji = new String("<none>");
                    }

                    // Now do the intermediate hints
                    // --------------------------
                    if (question.config.hintLevelSylable) {
                      // How many stages do we need to do
                      stages = currentHint.hintKana.length() - 1;
                      currentHint.stages = stages;

                      if (stages > 0) {
                        // The cost we will use for the rest is based on
                        // intermediate weighting
                        subHintCost = db.costs.getHintCost(word.type, CALL_costWeightStruct.INTERMEDIATE_COST) / stages;
                        if (subHintCost < 1) {
                          // Can't have hint cost being less than 1 point per
                          // stage
                          subHintCost = 1;
                        }

                        for (stagenum = 0; stagenum < stages; stagenum++) {
                          // First the kana
                          stageString = currentHint.hintKana.substring(0, (stagenum + 1));

                          // Then the stars ****
                          for (int m = stagenum; m < stages; m++) {
                            stageString = stageString + "*";
                          }

                          // Add the string
                          currentHint.subHintKana.addElement(stageString);
                          // CALL_debug.printlog(CALL_debug.MOD_HINTS,
                          // CALL_debug.DEBUG, "Adding intermediate hint: " +
                          // stageString + " - Level " + currentLevel);

                          // And then the sub-level cost - based on length
                          // intermediate costs
                          currentHint.subHintCost.addElement(new Integer(subHintCost));
                          totalCost += subHintCost;
                        }
                      }
                    }

                    // Finally, add the hint
                    wordHint.hints[currentLevel] = currentHint;

                    // And move on to next level
                    currentLevel++;
                  }

                  // Surface form
                  // ==================================================================================
                  if (question.config.hintLevelSurface) {
                    if (sentenceWord.useFormSettings) {
                      // We have form settings, so need to resolve
                      if (sentenceWord.transformationRule != null) {
                        s = sentenceWord.sign;
                        t = sentenceWord.tense;
                        p = sentenceWord.politeness;
                        q = sentenceWord.question;

                        // Get the string
                        tempString = db.applyTransformationRule(word, sentenceWord.transformationRule, s, t, p, q,
                            false);
                        tempString2 = db.applyTransformationRule(word, sentenceWord.transformationRule, s, t, p, q,
                            true);
                        if ((tempString != null) && (tempString2 != null)) {
                          currentHint = new CALL_hintStruct();
                          currentHint.hintKana = tempString;
                          currentHint.hintKanji = tempString2;

                          // Cost at this level
                          currentHint.cost = db.costs.getHintCost(word.type, CALL_costWeightStruct.FINALFORM_COST);
                          totalCost += currentHint.cost;
                          // CALL_debug.printlog(CALL_debug.MOD_HINTS,
                          // CALL_debug.DEBUG, "Level " + currentLevel +
                          // " Cost: " + currentHint.cost);

                          // Perceived difficulty at this level
                          // Out of date now, need level bias! - CJW
                          currentHint.difficulty = (int) (wordBiasDiscount);

                          if (sentenceWord.omit) {
                            currentHint.hintKana = new String("<none>");
                            currentHint.hintKanji = new String("<none>");

                            // Do we have to worry about punctuation?
                            if (i == sentence.sentence.size() - 1) {
                              // it's the last word
                              if (q == CALL_formStruct.QUESTION) {
                                currentHint.hintKana = currentHint.hintKana + "?";
                                currentHint.hintKanji = currentHint.hintKanji + "?";
                              }
                            }

                          }
                          wordHint.hints[currentLevel] = currentHint;
                          currentLevel++;
                        }
                      }
                    } else if (sentenceWord.useCounterSettings) {
                      // We have counter settings, so need to resolve
                      tempString = null;
                      tempString2 = null;

                      // FIRST KANA
                      alternatives = db.crules.getCounter(sentenceWord.ref_object, sentenceWord.word.parameter1,
                          CALL_io.kana);
                      if ((alternatives != null) && (alternatives.size() > 0)) {
                        // Just using top answer for now - CJW
                        tempString = (String) alternatives.elementAt(0);
                      }

                      // THEN KANJI
                      alternatives = db.crules.getCounter(sentenceWord.ref_object, sentenceWord.word.parameter1,
                          CALL_io.kanji);
                      if ((alternatives != null) && (alternatives.size() > 0)) {
                        // Just using top answer for now - CJW
                        tempString2 = (String) alternatives.elementAt(0);
                      }

                      if ((tempString != null) && (tempString2 != null)) {
                        currentHint = new CALL_hintStruct();
                        currentHint.hintKana = tempString;
                        currentHint.hintKanji = tempString2;

                        // Cost at this level
                        currentHint.cost = db.costs.getHintCost(word.type, 3);
                        totalCost += currentHint.cost;
                        // CALL_debug.printlog(CALL_debug.MOD_HINTS,
                        // CALL_debug.DEBUG, "Level " + currentLevel + " Cost: "
                        // + currentHint.cost);

                        // Perceived difficulty at this level
                        // Out of date now - CJW
                        currentHint.difficulty = (int) (100 - wordBiasDiscount);

                        if (sentenceWord.omit) {
                          currentHint.hintKana = new String("<none>");
                          currentHint.hintKanji = new String("<none>");
                        }

                        wordHint.hints[currentLevel] = currentHint;
                        currentLevel++;
                      }
                    }
                    // (consider about postfix / prefix here )

                    else if (sentenceWord.word.type == JCALL_Lexicon.LEX_TYPE_NOUN_PERSONNAME) {

                      tempString = null;
                      tempString2 = null;

                      // FIRST KANA
                      tempString = sentenceWord.word.kana + "さん";

                      // THEN KANJI
                      tempString2 = sentenceWord.word.kanji + "さん";

                      currentHint = new CALL_hintStruct();
                      currentHint.hintKana = tempString;
                      currentHint.hintKanji = tempString2;

                      // Cost at this level
                      currentHint.cost = db.costs.getHintCost(word.type, 3);
                      totalCost += currentHint.cost;
                      logger.debug("Level " + currentLevel + " Cost: " + currentHint.cost);

                      // Perceived difficulty at this level
                      // Out of date now - CJW
                      currentHint.difficulty = (int) (100 - wordBiasDiscount);

                      wordHint.hints[currentLevel] = currentHint;
                      currentLevel++;

                    }

                  }
                }

                // Add this word hint structure
                wordHints.addElement(wordHint);
              }
            }
          }
        }

        if (question.gconfig.hintCostAutoBalance != 0) {
          if (question.gconfig.hintCostAutoBalance == 1) {
            // We are taking the number of words into account in balancing our
            // costs
            desiredTotalCost = wordHints.size() * BALANCED_COMPONENT_COST;
            // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
            // "Balancing Hint Costs (1) - desired score of: "
            // +desiredTotalCost);
          } else {
            // All sentences (questions) will be worth the same regardless of
            // length
            desiredTotalCost = BALANCED_TOTAL_COST;
            // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
            // "Balancing Hint Costs (2) - desired score of: "
            // +desiredTotalCost);
          }

          // Calculate the coefficient we need to multiply by to get desired
          // component cost
          costCoefficient = (float) desiredTotalCost / (float) totalCost;

          // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
          // "Coefficient = " + costCoefficient + "(" + desiredTotalCost + "/" +
          // totalCost + ")");

          // Autobalance (based on number of components in sentence
          totalCost = 0;

          for (int i = 0; i < wordHints.size(); i++) {
            // First pass - get the total cost of the component
            wordHint = (CALL_wordHintsStruct) wordHints.elementAt(i);
            if (wordHint != null) {
              for (int j = 0; j < 4; j++) {
                // All levels
                currentHint = wordHint.hints[j];
                if (currentHint != null) {
                  // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
                  // "Hint Level: " + j);
                  oldCost = currentHint.cost;
                  currentHint.cost = (int) ((float) oldCost * costCoefficient);
                  // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
                  // "Updating cost: " + oldCost + " => " + currentHint.cost);
                  totalCost += currentHint.cost;

                  // Now for sub-hints if they exist
                  for (int k = 0; k < (currentHint.stages - 1); k++) {
                    oldCost = 0;
                    tempCost = (Integer) currentHint.subHintCost.elementAt(k);
                    if (tempCost != null) {
                      oldCost = tempCost.intValue();
                    }
                    currentHint.subHintCost.setElementAt(new Integer((int) ((float) oldCost * costCoefficient)), k);
                  }
                }
              }
            }
          }
        }
      }
    }

  }

  // Set to level
  // If check is on, set to highest level up to the level suggested where hint
  // exists
  // Also, if check is on, we can not set to a level below the current level
  public void setGlobalLevel(int level, boolean check) {
    CALL_wordHintsStruct wordHint;

    for (int i = 0; i < wordHints.size(); i++) {
      wordHint = (CALL_wordHintsStruct) wordHints.elementAt(i);
      if (wordHint != null) {
        if (!check) {
          wordHint.currentLevel = level;
        } else {
          for (int j = wordHint.currentLevel; j <= level; j++) {
            if (j < 0)
              continue;
            if (wordHint.hints[j] != null) {
              // Set it to level, and reveal all stages
              wordHint.currentLevel = j;
              wordHint.currentStage = (wordHint.getStages(j));
            }
          }
        }
      }
    }
  }

  public boolean moreHints() {
    CALL_wordHintsStruct wordHint;
    boolean rc = false;

    for (int i = 0; i < wordHints.size(); i++) {
      wordHint = (CALL_wordHintsStruct) wordHints.elementAt(i);
      if (wordHint != null) {
        if (wordHint.areThereMoreHints()) {
          // At least one word still has unrevealed hints
          rc = true;
          break;
        }
      }
    }

    return rc;
  }

  // Gets the lowest level which still has at least one unrevealed hint
  public int getUnrevealedLevel() {
    int level = 9999;
    int tempLevel;
    CALL_wordHintsStruct wordHint;

    for (int i = 0; i < wordHints.size(); i++) {
      wordHint = (CALL_wordHintsStruct) wordHints.elementAt(i);
      if (wordHint != null) {
        tempLevel = wordHint.getNextLevel();
        if ((tempLevel >= 0) && (tempLevel < level)) {
          // This is the lowest level seen so far
          level = tempLevel;
        }
      }
    }

    // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
    // "Current Unrevealed Level: " + level);

    return level;
  }

  public int getCostToLevel(int level) {
    int cost = 0;
    CALL_wordHintsStruct wordHint;

    for (int i = 0; i < wordHints.size(); i++) {
      wordHint = (CALL_wordHintsStruct) wordHints.elementAt(i);
      if (wordHint != null) {
        cost += wordHint.costToLevel(level);
      }
    }

    return cost;
  }

  public int getRemainingCost() {
    return getCostToLevel((CALL_wordHintsStruct.MAX_LEVELS - 1));
  }

  public int getWordRemainingCost(int index) {
    CALL_wordHintsStruct wordHint;
    int cost = 0;

    wordHint = (CALL_wordHintsStruct) wordHints.elementAt(index);
    if (wordHint != null) {
      cost = wordHint.costToLevel(CALL_wordHintsStruct.MAX_LEVELS - 1);
    }

    return cost;
  }

  public int recommendNextHint() {
    // Returns the component index of the next recommended hint
    // Basically, this is the component with the highest difficultying, as yet
    // unrevealed hint

    CALL_wordHintsStruct wHint;
    int level;
    int total;
    int index = -1;
    int highest = -1;
    int highestTotal = -1;

    for (int i = 0; i < wordHints.size(); i++) {
      wHint = (CALL_wordHintsStruct) wordHints.elementAt(i);
      if (wHint != null) {
        level = wHint.currentLevel;
        if (level < CALL_wordHintsStruct.MAX_LEVELS) {
          for (int j = level; j < CALL_wordHintsStruct.MAX_LEVELS; j++) {
            if (wHint.hints[j] != null) {
              if (j == wHint.currentLevel) {
                // This hint is at least part revealed - check there is more
                // left before considering it
                if (wHint.currentStage >= (wHint.hints[j].stages - 1)) {
                  // We've finished with the current level, so don't consider it
                  // further
                  continue;
                }
              }

              if (wHint.hints[j].difficulty > highest) {
                // This is our new candidate hint - but check if it's component
                // total is also highest
                total = 0;
                for (int k = 0; k < CALL_wordHintsStruct.MAX_LEVELS; k++) {
                  if (wHint.hints[k] != null) {
                    total += wHint.hints[k].difficulty;
                  }
                }

                if (total > highestTotal) {
                  // This component also has a higher total score than previous
                  // candidate - this is the one (for now!)
                  highest = wHint.hints[j].difficulty;
                  highestTotal = total;
                  index = i;
                }
              }
            }
          }
        }
      }
    }

    return index;
  }

  public String getLevelString(int level) {
    String retString = null;

    if ((level >= 0) && (level < levelStrings.size())) {
      retString = (String) levelStrings.elementAt(level);
    } else {
      retString = "Unknown";
    }

    return retString;
  }

  public void print_debug() {
    CALL_wordHintsStruct wordHint;

    for (int i = 0; i < wordHints.size(); i++) {
      wordHint = (CALL_wordHintsStruct) wordHints.elementAt(i);
      if (wordHint != null) {
        // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.INFO, "Hint [" +
        // i + "]");
        wordHint.print_debug();
      }
    }
  }
}