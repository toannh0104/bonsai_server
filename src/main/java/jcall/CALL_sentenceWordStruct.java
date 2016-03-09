///////////////////////////////////////////////////////////////////
// Grammar Rule Structure
//

package jcall;

import java.util.StringTokenizer;
import java.util.Vector;

import jcall.db.JCALL_Lexicon;

import org.apache.log4j.Logger;

public class CALL_sentenceWordStruct {
  static Logger logger = Logger.getLogger(CALL_sentenceWordStruct.class.getName());
  // Fields
  Vector prefix;
  // word is the top word, also is the target word now
  CALL_wordStruct word; // for counter, word.parameter1 is the number in arabic
  Vector postfix;

  // We want these to link together to form a finite-state grammar like
  // structure
  Vector previousWords;
  Vector nextWords;

  // The cost coefficients
  int cost_grammar; // From the grammar file - represents difficulty
  int cost_communication; // From the grammar file - represents communication
                          // impact

  boolean useCounterSettings;
  boolean useFormSettings;
  boolean omit;

  String componentName; // Usually based on grammar rule, but may be overrided
                        // by rule description if exists
  String grammarRule; // Abreviated rule: eg. PARTICLE
  String fullGrammarRule; // Full rule: eg. DESU_SIMPLE1.SUBJECT.PARTICLE

  // This one is for counters only
  // ref_object is Noun
  CALL_wordStruct ref_object;

  // The database (useful for verb rule transformations etc)
  CALL_database db;

  // These settings only used if word is a verb, adjective etc
  String transformationRule; // like "basis" for verb

  int sign;
  int tense;
  int politeness;
  int question;

  // form string for prediting the DForm
  String formString; // like

  public CALL_sentenceWordStruct() {
    // Empty
    init();
  }

  public CALL_sentenceWordStruct(CALL_database data, CALL_wordStruct w) {
    // STANDARD
    init();
    db = data;
    word = w;
  }

  public CALL_sentenceWordStruct(CALL_database data, CALL_wordStruct w, String gr, String component) {
    // STANDARD, WITH GRAMMAR RULE
    init();
    db = data;
    word = w;
    grammarRule = gr;
    fullGrammarRule = gr;

    if (component != null) {
      componentName = component;
    } else {
      componentName = gr;
    }
  }

  public CALL_sentenceWordStruct(CALL_database data, CALL_wordStruct w, String gr, String fgr, String component) {
    // STANDARD, WITH GRAMMAR RULE, AND FULL-GRAMMAR RUKE
    init();
    db = data;
    word = w;
    grammarRule = gr;
    fullGrammarRule = fgr;

    if (component != null) {
      componentName = component;
    } else {
      componentName = gr;
    }
  }

  public CALL_sentenceWordStruct(CALL_database data, CALL_wordStruct w, CALL_wordStruct w2, String gr, String component) {
    // FOR COUNTERS
    init();
    db = data;
    word = w;
    ref_object = w2;
    grammarRule = gr;
    fullGrammarRule = gr;
    useCounterSettings = true;

    if (component != null) {
      componentName = component;
    } else {
      componentName = gr;
    }
  }

  public CALL_sentenceWordStruct(CALL_database data, CALL_wordStruct w, CALL_wordStruct w2, String gr, String fgr,
      String component) {
    // FOR COUNTERS, WITH FULL GRAMMAR RULE
    init();
    db = data;
    word = w;
    ref_object = w2;
    grammarRule = gr;
    fullGrammarRule = fgr;
    useCounterSettings = true;

    if (component != null) {
      componentName = component;
    } else {
      componentName = gr;
    }

  }

  public CALL_sentenceWordStruct(CALL_database data, CALL_wordStruct w, String gr, String component, String tr, int s,
      int t, int p, int q) {
    // FOR VERBS, ADJECTIVES (TRANSFORMATION RULE INCLUDED)
    init();
    db = data;
    word = w;
    transformationRule = tr;
    grammarRule = gr;
    fullGrammarRule = gr;
    sign = s;
    tense = t;
    politeness = p;
    question = q;
    useFormSettings = true;

    if (component != null) {
      componentName = component;
    } else {
      componentName = gr;
    }
  }

  public CALL_sentenceWordStruct(CALL_database data, CALL_wordStruct w, String gr, String fgr, String component,
      String tr, int s, int t, int p, int q) {
    // FOR VERBS, ADJECTIVES (TRANSFORMATION RULE INCLUDED)

    init();
    db = data;
    word = w;
    transformationRule = tr;
    grammarRule = gr;
    fullGrammarRule = fgr;
    sign = s;
    tense = t;
    politeness = p;
    question = q;
    useFormSettings = true;

    if (component != null) {
      componentName = component;
    } else {
      componentName = gr;
    }
  }

  public CALL_sentenceWordStruct(CALL_database data, CALL_wordStruct w, String gr, String fgr, String component,
      String tr, String formStr, int s, int t, int p, int q) {
    // FOR VERBS, ADJECTIVES (TRANSFORMATION RULE INCLUDED)

    init();
    db = data;
    word = w;
    transformationRule = tr;
    grammarRule = gr;
    fullGrammarRule = fgr;
    sign = s;
    tense = t;
    politeness = p;
    question = q;
    useFormSettings = true;

    if (component != null) {
      componentName = component;
    } else {
      componentName = gr;
    }
    if (formStr != null) {
      formString = formStr;
    } else {
      formString = null;
      // logger.debug("full form String is NULL");
    }
  }

  public CALL_sentenceWordStruct(CALL_database data, CALL_wordStruct w, String gr, String fgr, String component,
      String tr, int s, int t, int p, int q, String formStr) {
    // FOR VERBS, ADJECTIVES (TRANSFORMATION RULE INCLUDED)
    System.out.println("FOR VERBS, ADJECTIVES, enter CALL_sentenceWordStruct");
    init();
    db = data;
    word = w;
    transformationRule = tr;
    grammarRule = gr;
    fullGrammarRule = fgr;
    sign = s;
    tense = t;
    politeness = p;
    question = q;
    useFormSettings = true;
    formString = formStr;
    if (component != null) {
      componentName = component;
    } else {
      componentName = gr;
    }
  }

  public CALL_sentenceWordStruct(CALL_database data, String wordString, CALL_sentenceWordStruct targetWord) {

    init();

    if (targetWord != null) {

      // logger.debug("Enter CALL_sentenceWordStruct; wordObserved: "+
      // wordString +" Target Word: "+ targetWord.getWord().getKana());

    } else {
      // logger.debug("Enter CALL_sentenceWordStruct; wordObserved: "+
      // wordString +" Target Word: NULL");

    }
    // ESTIMATE THE STRUCTURE FROM THE GIVEN STRING AND TARGET WORD
    Vector matchingWords;
    Vector possibleForms;
    Vector targetGroups;

    Vector searchVector;

    CALL_wordStruct tempWord;
    CALL_wordWithFormStruct tempFormWord;

    String tempString;
    int targetType;
    boolean found = false;

    // Basic initialization
    init();
    db = data;

    if (targetWord != null) {

      // Get the target information
      if (targetWord.word != null) {
        targetType = targetWord.word.type;
        targetGroups = targetWord.word.categories;
      } else {
        // Target word invalid - just use defaults for now
        targetType = -1;
        targetGroups = null;
      }

      // VERBS
      // -------
      if ((targetWord.word.type == CALL_lexiconStruct.LEX_TYPE_VERB)
          || (targetWord.word.type == CALL_lexiconStruct.LEX_TYPE_ADJECTIVE)
          || (targetWord.word.type == CALL_lexiconStruct.LEX_TYPE_ADJVERB)) {
        if (targetWord.word.type == CALL_lexiconStruct.LEX_TYPE_VERB) {
          // Alternative forms of current verb
          // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
          // "Checking to see if we have a matching verb of different form");
          possibleForms = db.vrules.getVerbForms(targetWord.word, null, -1, -1, -1, -1);
        } else {
          // Alternative forms of current adjective
          // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
          // "Checking to see if we have a matching adjective of different form");
          possibleForms = db.arules.getAdjectiveForms(targetWord.word, null, -1, -1, -1, -1);
        }

        if (possibleForms != null) {
          // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
          // "Have " + possibleForms.size() + " forms to check");
          for (int i = 0; i < possibleForms.size(); i++) {
            // Does this match our string?
            tempFormWord = (CALL_wordWithFormStruct) possibleForms.elementAt(i);

            if (tempFormWord != null) {
              // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
              // "Comparing " + wordString + " with " +
              // tempFormWord.surfaceFormKanji);
              // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
              // "Comparing " + wordString + " with " +
              // tempFormWord.surfaceFormKana);
              // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
              // "Comparing " + wordString + " with " +
              // tempFormWord.surfaceFormRomaji);

              // logger.debug("Comparing " + wordString + " with " +
              // tempFormWord.surfaceFormKanji);
              // logger.debug( "Comparing " + wordString + " with " +
              // tempFormWord.surfaceFormKana);
              // logger.debug("Comparing " + wordString + " with " +
              // tempFormWord.surfaceFormRomaji);

              if (wordString.equals(tempFormWord.surfaceFormKanji) || wordString.equals(tempFormWord.surfaceFormKana)
                  || wordString.equals(tempFormWord.surfaceFormRomaji)) {
                // We have found a matching word, so store it
                // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
                // "Match");

                // logger.debug("Match");

                found = true;
                useFormSettings = true;
                word = tempFormWord.baseWord;
                transformationRule = tempFormWord.transformationRule;
                sign = tempFormWord.sign;
                tense = tempFormWord.tense;
                politeness = tempFormWord.style;
                question = tempFormWord.type;
                break;
              }
            }
          }
        }

        if (!found) {
          // Then check target forms from other verbs / adjectives
          // Don't really want to do all verbs/adjectives...just do once with
          // close string distance, and/or semantic similarities??
          // This needs to be fully implement - CJW

          if (targetWord.word.type == CALL_lexiconStruct.LEX_TYPE_VERB) {
            // We're looking for a verb
            searchVector = db.lexicon.verbs;
          } else if (targetWord.word.type == CALL_lexiconStruct.LEX_TYPE_ADJECTIVE) {
            // We're looking for an adjective
            searchVector = db.lexicon.adjectives;
          } else {
            // We're looking for an adjective
            searchVector = db.lexicon.adjverbs;
          }

          for (int v = 0; v < searchVector.size(); v++) {
            tempWord = (CALL_wordStruct) searchVector.elementAt(v);
            if (tempWord != null) {
              // Do we want to skip this word? Is it similar to target? Do some
              // checks
              // MUST ADD THESE CHECKS, IF WE DECIDE THEY ARE NEEDED - CJW
              // WITHOUT THEM, IT RUNS TOO SLOWLY

              if (targetWord.word.type == CALL_lexiconStruct.LEX_TYPE_VERB) {
                // Now go through all the verb forms for this verb
                possibleForms = db.vrules.getVerbForms(tempWord, null, -1, -1, -1, -1);
              } else {
                // Now go through all the adjectives forms for this adjective
                possibleForms = db.arules.getAdjectiveForms(tempWord, null, -1, -1, -1, -1);
              }

              for (int i = 0; i < possibleForms.size(); i++) {
                // Does this match our string?
                tempFormWord = (CALL_wordWithFormStruct) possibleForms.elementAt(i);

                if (tempFormWord != null) {
                  // CALL_debug.printlog(CALL_debug.MOD_ERRORS,
                  // CALL_debug.DEBUG, "Comparing " + wordString + " with " +
                  // tempFormWord.surfaceFormKanji);
                  // CALL_debug.printlog(CALL_debug.MOD_ERRORS,
                  // CALL_debug.DEBUG, "Comparing " + wordString + " with " +
                  // tempFormWord.surfaceFormKana);
                  // CALL_debug.printlog(CALL_debug.MOD_ERRORS,
                  // CALL_debug.DEBUG, "Comparing " + wordString + " with " +
                  // tempFormWord.surfaceFormRomaji);

                  if (wordString.equals(tempFormWord.surfaceFormKanji)
                      || wordString.equals(tempFormWord.surfaceFormKana)
                      || wordString.equals(tempFormWord.surfaceFormRomaji)) {
                    // Found a match!
                    // CALL_debug.printlog(CALL_debug.MOD_ERRORS,
                    // CALL_debug.DEBUG, "Match");
                    found = true;
                    useFormSettings = true;
                    word = tempFormWord.baseWord;
                    transformationRule = tempFormWord.transformationRule;
                    sign = tempFormWord.sign;
                    tense = tempFormWord.tense;
                    politeness = tempFormWord.style;
                    question = tempFormWord.type;
                    break;
                  }
                }
              }

            }
          }
        }
      }

      // COUNTERS
      // ----------
      // Do later - CJW

      // Then check words of the same type as the target word
      // -----------------------------------------------
      if (!found) {
        if (targetType != -1) {
          matchingWords = db.lexicon.getWords(null, targetType, -1, wordString);
          if ((matchingWords != null) && (matchingWords.size() > 0)) {
            // We have some! Check to see if any share the same category,
            // otherwise use just the top one.
            for (int i = 0; i < matchingWords.size(); i++) {
              tempWord = (CALL_wordStruct) matchingWords.elementAt(i);
              if (tempWord != null) {
                if (targetGroups != null) {
                  for (int j = 0; j < targetGroups.size(); j++) {
                    tempString = (String) targetGroups.elementAt(j);
                    if (tempString != null) {
                      if (tempWord.isInCategory(tempString)) {
                        // We have a category match! Use this word
                        word = tempWord;
                        found = true;
                        break;
                      }
                    }
                  }
                }
              }
            }

            if (!found) {
              // We couldn't find a matching category group, so use the top word
              word = (CALL_wordStruct) matchingWords.elementAt(0);
              if (word != null) {
                found = true;
              }
            }
          }
        }
      }

      // Then check words in the same semantical group(s) as the target
      if (!found) {
        if (targetGroups != null) {
          for (int j = 0; j < targetGroups.size(); j++) {
            tempString = (String) targetGroups.elementAt(j);
            if (tempString != null) {
              // Get all matching words from this category
              matchingWords = db.lexicon.getWords(tempString, -1, -1, wordString);
              if ((matchingWords != null) && (matchingWords.size() > 0)) {
                // Just take top word in this case
                word = (CALL_wordStruct) matchingWords.elementAt(0);
                if (word != null) {
                  found = true;
                }
              }
            }
          }
        }
      }

      // Then check all words
      if (!found) {
        matchingWords = db.lexicon.getWords(null, -1, -1, wordString);
        if ((matchingWords != null) && (matchingWords.size() > 0)) {
          // Just take top word in this case
          word = (CALL_wordStruct) matchingWords.elementAt(0);
          if (word != null) {
            found = true;
          }
        }
      }

      // Still not found....it must be a malformed word
      // The structure word will remain a null pointer, and this will be dealt
      // with by higher functions
    }// end if(targetWord != null)
     // else{
    //
    //
    //
    // }

    // NOTE - WE MAY WANT TO DO SOME CLOSEST MATCH SEARCHING AT THIS POINT,
    // SEEING IF THE OBSERVED
    // WORD CAN BE MATCHED SIGNIFICANTLY CLOSER TO ANOTHER WORD IN THE
    // DICTIONARY THAN WITH THE
    // TARGET WORD - CJW
  }

  private void init() {
    db = null;
    omit = false;
    word = null;
    prefix = new Vector();
    postfix = new Vector();
    previousWords = new Vector();
    nextWords = new Vector();
    transformationRule = null;
    grammarRule = null;
    fullGrammarRule = null;
    useFormSettings = false;
    useCounterSettings = false;
    ref_object = null;
    sign = 0;
    tense = 0;
    politeness = 0;
    question = 0;
    cost_grammar = 0;
    formString = "";
  }

  // No add prefixes / suffixes - these get done at a higher level

  public String getTopWordString(int format) {
    // logger.debug("enter getTopWordString, word ["+word.kanji+"]");

    String rString = null;
    CALL_wordStruct tempWord;
    Vector tempVector;
    tempWord = word;

    if (tempWord != null) {
      if (useFormSettings) {
        // logger.debug("useFormSettings");

        if (format == CALL_io.romaji) {
          // Romaji
          rString = db.applyTransformationRule(tempWord, transformationRule, sign, tense, politeness, question, false);
          rString = CALL_io.strKanaToRomaji(rString);
        } else if (format == CALL_io.kana) {
          // Kana
          rString = db.applyTransformationRule(tempWord, transformationRule, sign, tense, politeness, question, false);
        } else {
          // Kanji
          rString = db.applyTransformationRule(tempWord, transformationRule, sign, tense, politeness, question, true);
        }
      } else if (useCounterSettings) {
        // A counter - must use counter rules to get final form
        // =============================================
        // logger.debug("use counter setting");
        //
        int para1 = tempWord.parameter1;
        if (tempWord.type == JCALL_Lexicon.LEX_TYPE_NOUN_NUMERAL) {
          if (para1 < 1) {
            String eng = tempWord.english;
            if (eng == null) {
              eng = tempWord.genEnglish;
            }
            if (eng == null && eng.length() > 0) {
              para1 = Integer.parseInt(eng);
            }
          }
        } else {
          para1 = 1;
          logger.error("No digit");
        }

        tempVector = db.crules.getCounter(ref_object, para1, format);

        if ((tempVector != null) && (tempVector.size() > 0)) {
          rString = (String) tempVector.elementAt(0);
        } else {
          logger.error("no counter returned");
        }

      } else {
        switch (format) {
          case CALL_io.kanji:
            if (word != null) {
              rString = word.kanji;
              // Don't add prefixes / suffixes - these get done at a higher
              // level
            }
            break;

          default:
            if (word != null) {
              // Don't add prefixes / suffixes - these get done at a higher
              // level
              rString = word.kana;
            }
            break;
        }
      }
    }

    return rString;
  }

  // return Final Surface Form of this word in specified format
  // Choose the most aproporiate one
  public String getWordString(int format) {
    // logger.debug("getWordString, format: "+format );

    String rString = null;
    Vector<String> rVector = null;
    Vector<String> tempVector = null;
    Vector wordVector = null;
    CALL_wordStruct tempWord, tempPrefix, tempPostfix;
    String tempString;
    String originalString;

    tempWord = word;

    if (tempWord != null) {
      // logger.debug("Word kanji: "+ word.kanji);

      if (useFormSettings) {
        rVector = new Vector<String>();

        if (format == CALL_io.romaji) {
          // Romaji
          rString = db.applyTransformationRule(tempWord, transformationRule, sign, tense, politeness, question, false);
          rString = CALL_io.strKanaToRomaji(rString);
        } else if (format == CALL_io.kana) {
          // Kana
          rString = db.applyTransformationRule(tempWord, transformationRule, sign, tense, politeness, question, false);
        } else {
          // Kanji
          rString = db.applyTransformationRule(tempWord, transformationRule, sign, tense, politeness, question, true);
        }

        // if(rString != null)
        // {
        // // Add the word to the vector (will only be size of 1 as no
        // alternatives, for now)
        // rVector.add(rString);
        // }
      } else if (useCounterSettings) {
        // A counter - must use counter rules to get final form
        // =============================================
        rVector = db.crules.getCounter(ref_object, tempWord.parameter1, format);

        if (rVector != null && rVector.size() > 0) {
          rString = rVector.elementAt(0);
        } else {

          logger.info("No return any Counter by applying the counter rules");
        }

      } else {
        // normal words
        // logger.debug("normal words, no transformation");

        rString = word.getWordString(format);

        // logger.debug("after get word string: "+ rString);
      }

    }// if(tempWord != null)

    // logger.debug("Now consider the prefixes and suffixes");

    // Now consider the prefixes and suffixes
    tempVector = new Vector<String>();

    if (rString != null && rString.length() > 0) {
      originalString = rString;

      for (int p = -1; p < prefix.size(); p++) {
        for (int s = -1; s < postfix.size(); s++) {
          tempString = new String(originalString);

          // If both are -1, skip (it's the case with no prefixes/suffixes,
          // which we already have
          if ((s == -1) && (p == -1)) {
            continue;
          }

          // Get prefixes
          if (p != -1) {
            tempPrefix = (CALL_wordStruct) prefix.elementAt(p);
            tempString = tempPrefix.getWordString(format) + tempString;
          }

          // Get suffixes
          if (s != -1) {
            tempPostfix = (CALL_wordStruct) postfix.elementAt(s);
            tempString = tempString + tempPostfix.getWordString(format);
          }

          // Add the newly generated string
          if ((tempString != null) && (!tempString.equals(originalString))) {
            // logger.debug( "Add Word form: " + tempString);
            tempVector.addElement(tempString);
          }

        }
      }// End for prefix + postfix
    }

    // Now add the tempVector to the rVector

    // rVector.addAll(tempVector);
    if (tempVector != null && tempVector.size() > 0) {
      rString = tempVector.elementAt(tempVector.size() - 1);

    }

    return rString;
  }

  // Gets a vector of all alternative strings for this word (including word
  // string itself)
  // This vector will often just be of size 1
  // return a vector of Final Surface Form in specified format
  public Vector getWordStrings(int format) {
    // logger.debug("getWordStrings, format: "+format );

    String rString = null;
    Vector<String> rVector = null;
    Vector<String> tempVector = null;
    Vector wordVector = null;
    CALL_wordStruct tempWord, tempPrefix, tempPostfix;
    String tempString;
    String originalString;

    tempWord = word;

    if (tempWord != null) {
      // logger.debug("Word kanji: "+ word.kanji);

      if (useFormSettings) {
        rVector = new Vector<String>();

        if (format == CALL_io.romaji) {
          // Romaji
          rString = db.applyTransformationRule(tempWord, transformationRule, sign, tense, politeness, question, false);
          rString = CALL_io.strKanaToRomaji(rString);
        } else if (format == CALL_io.kana) {
          // Kana
          rString = db.applyTransformationRule(tempWord, transformationRule, sign, tense, politeness, question, false);
        } else {
          // Kanji
          rString = db.applyTransformationRule(tempWord, transformationRule, sign, tense, politeness, question, true);
        }

        if (rString != null) {
          // Add the word to the vector (will only be size of 1 as no
          // alternatives, for now)
          rVector.add(rString);
        }
      } else if (useCounterSettings) {
        // A counter - must use counter rules to get final form
        // =============================================
        rVector = db.crules.getCounter(ref_object, tempWord.parameter1, format);
      } else {
        // normal words
        // logger.debug("normal words, no transformation");

        wordVector = word.getWordStrings(format);

        // logger.debug("after get word string plus other aternative word string ");

        if (wordVector != null) {
          for (int w = 0; w < wordVector.size(); w++) {
            rString = (String) wordVector.elementAt(w);

            if (rString != null) {
              // Add the word to the vector (will only be size of 1 as not using
              // alternatives, for now)
              if (rVector == null) {
                rVector = new Vector<String>();
              }

              rVector.addElement(rString);
            }
          }
        }
      }

    }// if(tempWord != null)

    // logger.debug("Now consider the prefixes and suffixes");

    // Now consider the prefixes and suffixes
    tempVector = new Vector<String>();
    if (rVector != null) {
      for (int i = 0; i < rVector.size(); i++) {
        originalString = (String) rVector.elementAt(i);

        if (originalString != null) {
          for (int p = -1; p < prefix.size(); p++) {
            for (int s = -1; s < postfix.size(); s++) {
              tempString = new String(originalString);

              // If both are -1, skip (it's the case with no prefixes/suffixes,
              // which we already have
              if ((s == -1) && (p == -1)) {
                continue;
              }

              // Get prefixes
              if (p != -1) {
                tempPrefix = (CALL_wordStruct) prefix.elementAt(p);
                tempString = tempPrefix.getWordString(format) + tempString;
              }

              // Get suffixes
              if (s != -1) {
                tempPostfix = (CALL_wordStruct) postfix.elementAt(s);
                tempString = tempString + tempPostfix.getWordString(format);
              }

              // Add the newly generated string
              if ((tempString != null) && (!tempString.equals(originalString))) {
                // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                // CALL_debug.DEBUG, "Added word form: " + tempString);
                // logger.debug( "Added word form: " + tempString);
                tempVector.addElement(tempString);
              }

            }
          }
        }
      }// end of for(int i = 0; i < rVector.size(); i++)

    }

    // Now add the tempVector to the rVector

    // rVector.addAll(tempVector);
    if (rVector != null && tempVector != null) {
      for (int i = 0; i < rVector.size(); i++) {
        tempVector.addElement(rVector.elementAt(i));
      }
    }

    if (tempVector != null) {
      rVector = tempVector;
    }

    return rVector;
  }

  // Get the grammar rule at a certain depth. For example:
  // 0: DESU_SIMPLE1
  // 1: SUBJ
  // 2: PERSON
  // 3: SUBJ_PARTICLE
  // ...
  public String grammarRuleDepth(int depth) {
    String tempString = null;
    String rString = null;

    StringTokenizer st = new StringTokenizer(fullGrammarRule, ".");

    for (int i = 0; i < depth; i++) {
      tempString = CALL_io.getNextToken(st);
      if (tempString == null) {
        // Run out of depth
        break;
      }

      if (i == depth) {
        // This is the required depth
        rString = tempString;
        break;
      }
    }

    return rString;
  }

  // Gets the final, most specific rule
  public String leafGrammarRule() {
    String rString = null;
    String tempString;
    StringTokenizer st = new StringTokenizer(fullGrammarRule, ".");

    for (;;) {
      tempString = CALL_io.getNextToken(st);
      if (tempString == null) {
        // Run out of depth
        break;
      }

      rString = tempString;
    }

    return rString;
  }

  // Gets the first, most general rule
  public String baseGrammarRule() {
    StringTokenizer st = new StringTokenizer(fullGrammarRule, ".");
    String rString = CALL_io.getNextToken(st);
    return rString;
  }

  public String getFormString() {
    String signString;
    String tenseString;
    String styleString;
    String typeString;
    String rString;

    // Sign
    if (sign == CALL_formStruct.POSITIVE) {
      signString = new String("POS");
    } else {
      signString = new String("NEG");
    }

    // Tense
    if (tense == CALL_formStruct.PAST) {
      tenseString = new String("PAST");
    } else {
      tenseString = new String("PRESENT");
    }

    // Style
    if (politeness == CALL_formStruct.POLITE) {
      styleString = new String("POLITE");
    } else if (politeness == CALL_formStruct.PLAIN) {
      styleString = new String("PLAIN");
    } else if (politeness == CALL_formStruct.SUPER_POLITE) {
      styleString = new String("S-POL");
    } else if (politeness == CALL_formStruct.HUMBLE) {
      styleString = new String("HUMBLE");
    } else {
      styleString = new String("CRUDE");
    }

    // Type
    if (question == CALL_formStruct.STATEMENT) {
      typeString = new String("STATEMENT");
    } else {
      typeString = new String("QUESTION");
    }

    rString = (transformationRule + " " + signString + " " + tenseString + " " + styleString + " " + typeString);
    return rString;
  }

  public void print_debug() {
    CALL_wordStruct tempWord;

    if (word != null) {
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
      // "Sentence word: " + word.kanji);
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
      // "--> Verb Rule: " + transformationRule);
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
      // "--> Grammar Rule: " + grammarRule);
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
      // "--> Full Grammar Rule: " + fullGrammarRule);
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
      // "--> Sign: " + sign);
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
      // "--> Tense: " + tense);
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
      // "--> Politeness: " + politeness);
      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
      // "--> Question: " + question);

      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
      // "--> Prefixes:");

      // logger.debug( "Sentence word: " + word.kanji);
      // logger.debug( "--> Verb Rule: " + transformationRule);
      // logger.debug( "--> Grammar Rule: " + grammarRule);
      // logger.debug( "--> Full Grammar Rule: " + fullGrammarRule);
      // logger.debug( "--> Sign: " + sign);
      // logger.debug( "--> Tense: " + tense);
      // logger.debug( "--> Politeness: " + politeness);
      // logger.debug( "--> Question: " + question);
      // logger.debug( "--> FormString: " + formString);
      // logger.debug( "--> Prefixes:");

      for (int i = 0; i < prefix.size(); i++) {
        tempWord = (CALL_wordStruct) prefix.elementAt(i);
        if ((tempWord != null) && (tempWord.kanji != null)) {
          // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
          // "---->" + tempWord.kanji);
          // logger.debug( "---->" + tempWord.kanji);
        }
      }

      // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
      // "--> Suffixes:");
      // logger.debug( "--> Suffixes:");
      for (int i = 0; i < postfix.size(); i++) {
        tempWord = (CALL_wordStruct) postfix.elementAt(i);
        if ((tempWord != null) && (tempWord.kanji != null)) {
          // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
          // "---->" + tempWord.kanji);
          // logger.debug("---->" + tempWord.kanji);
        }
      }
    }
  }

  public String getFullGrammarRule() {
    return fullGrammarRule;
  }

  public void setFullGrammarRule(String fullGrammarRule) {
    this.fullGrammarRule = fullGrammarRule;
  }

  public String getGrammarRule() {
    return grammarRule;
  }

  public void setGrammarRule(String grammarRule) {
    this.grammarRule = grammarRule;
  }

  public Vector getNextWords() {
    return nextWords;
  }

  public void setNextWords(Vector nextWords) {
    this.nextWords = nextWords;
  }

  public boolean isOmit() {
    return omit;
  }

  public void setOmit(boolean omit) {
    this.omit = omit;
  }

  public int getPoliteness() {
    return politeness;
  }

  public void setPoliteness(int politeness) {
    this.politeness = politeness;
  }

  public Vector getPostfix() {
    return postfix;
  }

  public void setPostfix(Vector postfix) {
    this.postfix = postfix;
  }

  public Vector getPrefix() {
    return prefix;
  }

  public void setPrefix(Vector prefix) {
    this.prefix = prefix;
  }

  public Vector getPreviousWords() {
    return previousWords;
  }

  public void setPreviousWords(Vector previousWords) {
    this.previousWords = previousWords;
  }

  public int getQuestion() {
    return question;
  }

  public void setQuestion(int question) {
    this.question = question;
  }

  public CALL_wordStruct getRef_object() {
    return ref_object;
  }

  public void setRef_object(CALL_wordStruct ref_object) {
    this.ref_object = ref_object;
  }

  public int getSign() {
    return sign;
  }

  public void setSign(int sign) {
    this.sign = sign;
  }

  public int getTense() {
    return tense;
  }

  public void setTense(int tense) {
    this.tense = tense;
  }

  public String getTransformationRule() {
    return transformationRule;
  }

  public void setTransformationRule(String transformationRule) {
    this.transformationRule = transformationRule;
  }

  public boolean isUseCounterSettings() {
    return useCounterSettings;
  }

  public void setUseCounterSettings(boolean useCounterSettings) {
    this.useCounterSettings = useCounterSettings;
  }

  public boolean isUseFormSettings() {
    return useFormSettings;
  }

  public void setUseFormSettings(boolean useFormSettings) {
    this.useFormSettings = useFormSettings;
  }

  public CALL_wordStruct getWord() {
    return word;
  }

  public void setWord(CALL_wordStruct word) {
    this.word = word;
  }

  public String getComponentName() {
    return componentName;
  }

  public void setComponentName(String componentName) {
    this.componentName = componentName;
  }

  public int getCost_communication() {
    return cost_communication;
  }

  public void setCost_communication(int cost_communication) {
    this.cost_communication = cost_communication;
  }

  public int getCost_grammar() {
    return cost_grammar;
  }

  public void setCost_grammar(int cost_grammar) {
    this.cost_grammar = cost_grammar;
  }

  public CALL_database getDb() {
    return db;
  }

  public void setDb(CALL_database db) {
    this.db = db;
  }

  public String getFullFormString() { // for formString variable
    return formString;

  }

  public void setFullFormString(String formString) {
    this.formString = formString;
  }
}