/**
 * Created on 2008/07/09
 * @author wang
 * Copyrights @kawahara lab
 */
///////////////////////////////////////////////////////////////////
// Grammar Rule Structure
//

package jcall;

import java.util.StringTokenizer;
import java.util.Vector;

public class JCALL_errorPairStruct {
  // STATICS
  static final double SPELLING_DISTANCE_UPPER_THRESHOLD = 0.60;
  static final double SPELLING_DISTANCE_LOWER_THRESHOLD = 0.20;

  static final int MIN_ERROR_COST = 5;

  // Error Types
  static final int GRAMMAR_ERROR = 0;
  static final int LEXICAL_ERROR = 1;
  static final int CONCEPT_ERROR = 2;
  static final int INPUT_ERROR = 3;
  static final int UNDEFINED_ERROR = 4;

  // Sub Error Types
  static final int SUBS0 = 0;
  static final int SUBS1 = 1;
  static final int SUBS2 = 2;
  static final int SUBS3 = 3;
  static final int SUBS4 = 4;
  static final int SUBS5 = 5;
  static final int SUBS6 = 6;
  static final int SUBS7 = 7;
  static final int SUBS8 = 8;
  static final int SUBS9 = 9;
  static final int SUBS10 = 10;
  static final int SUBS11 = 11;
  static final int SUBS12 = 12;
  static final int SUBS13 = 13;
  static final int WRONG_FORM = 14;
  static final int INVALID_FORM = 15;
  static final int DELETION = 16;
  static final int INSERTION = 17;

  private static final String typeStrings[] = { "GRAMMAR", "LEXICAL", "CONCEPT", "INPUT", "UNDEFINED" };

  private static final String typeVerboseStrings[] = { "A Grammatical error", "A Lexical error", "A Conceptual error",
      "An Input error", "An Underfined error" };

  private static final String typeIconStrings[] = { "Misc/grammarIcon.gif", "Misc/lexicalIcon.gif",
      "Misc/conceptIcon.gif", "Misc/inputIcon.gif", "Misc/undefinedIcon.gif" };

  private static final String subTypeStrings[] = { "SUBS0", "SUBS1", "SUBS2", "SUBS3", "SUBS4", "SUBS5", "SUBS6",
      "SUBS7", "SUBS8", "SUBS9", "SUBS10", "SUBS11", "SUBS12", "SUBS13", "WRONG_FORM", "INVALID_FORM", "DELETION",
      "INSERTION" };

  // Fields
  int lesson;

  String observedString;
  String targetString;

  // The two words in question
  CALL_sentenceWordStruct targetWord;
  CALL_sentenceWordStruct observedWord;

  // The database
  CALL_database db;

  // Some factors about the error
  // ------------------------
  int targetComponentType; // Component types defined in CALL_lexicon.java
  int observedComponentType;

  boolean b_typeMatch;
  boolean b_stemMatch;
  boolean b_formMatch;
  boolean b_partialSemanticMatch;
  boolean b_fulllSemanticMatch;

  double spellingErrorLikelihood; // 0.0 = non-spelling <----> 1.0 spelling

  int errorType; // Error types defined above
  int subErrorType; // Again, defined above

  String observedFormDiff; // Has the form components that are different from
                           // target (Past, Positive etc)
  String targetFormDiff; // Has the form components that are different from
                         // observed (Present, Negative etc)

  // Constructor
  public JCALL_errorPairStruct(CALL_database data, String target, String observed, CALL_sentenceWordStruct wordT) {
    db = data;

    targetString = target;
    observedString = observed;

    // Set the target word
    targetWord = wordT;

    if (observedString == null || observedString.length() == 0) {
      observedWord = null;
    }
    // Estimate the observed word information
    observedWord = new CALL_sentenceWordStruct(db, observedString, targetWord);

    // Get the form strings
    createFormStrings();

    // Classify the error
    classifyError();
  }

  // Creates strings that highlight difference in form, if appropriate
  // /////////////////////////////////////////////////////////
  public void createFormStrings() {
    String targetForm;
    String observedForm;
    StringTokenizer stO, stT;
    String str1, str2;

    // Reset
    targetFormDiff = null;
    observedFormDiff = null;

    if ((observedWord != null) && (targetWord != null)) {
      if ((targetWord.useFormSettings) && (observedWord.useFormSettings)) {
        // We have to assume the ordering of form components in form string is
        // consistent here!
        // eg. TENSE SIGN STYLE...etc
        targetForm = targetWord.getFormString();
        observedForm = observedWord.getFormString();

        if ((targetForm != null) && (observedForm != null)) {
          stO = new StringTokenizer(observedForm);
          stT = new StringTokenizer(targetForm);

          for (;;) {
            str1 = CALL_io.getNextToken(stO);
            str2 = CALL_io.getNextToken(stT);
            if ((str1 == null) || (str2 == null)) {
              // End of string(s)
              break;
            }

            if (!str1.equals(str2)) {
              if (targetFormDiff == null) {
                // First additions to the strings
                observedFormDiff = CALL_io.getDisplayString(str1);
                targetFormDiff = CALL_io.getDisplayString(str2);

              } else {
                // Append to existing strings
                observedFormDiff = observedFormDiff + ", " + CALL_io.getDisplayString(str1);
                targetFormDiff = targetFormDiff + ", " + CALL_io.getDisplayString(str2);
              }
            }
          }
        }
      }
    }
  }

  public void classifyError() {
    // Is it likely to be a spelling error
    spellingErrorLikelihood = spellingErrorScore();

    // First deal with insertions, deletions
    if ((targetWord == null) && (observedWord == null)) {
      // Shouldn't really be an error? An odd case, shouldn't happen, but just
      // to cover it
      targetComponentType = -1;
      observedComponentType = -1;
      errorType = UNDEFINED_ERROR;
      subErrorType = UNDEFINED_ERROR;
    }
    if (observedWord == null) {
      // DELETIONS
      // ===========
      targetComponentType = targetWord.word.type;
      observedComponentType = -1;
      errorType = GRAMMAR_ERROR;
      subErrorType = DELETION;
      b_typeMatch = false;
      b_stemMatch = false;
      b_formMatch = false;
      b_partialSemanticMatch = false;
    } else if (targetWord == null) {
      // INSERTIONS
      // ===========
      targetComponentType = -1;
      observedComponentType = observedWord.word.type;
      errorType = GRAMMAR_ERROR;
      subErrorType = INSERTION;
      b_typeMatch = false;
      b_stemMatch = false;
      b_formMatch = false;
      b_partialSemanticMatch = false;
    } else {
      // SUBSTITUTIONS
      // ===============
      // What is the target component type?
      if (targetWord.word != null) {
        targetComponentType = targetWord.word.type;
      } else {
        // Shouldn't really happen with target word, but just in case
        targetComponentType = -1;
      }

      // What is the observed component type?
      if (observedWord.word != null) {
        observedComponentType = observedWord.word.type;
      } else {
        observedComponentType = -1;
      }

      // First, is it simplay an input (Hiragana vs Katakana) issue?
      if (katakanaHiraganaIssue()) {
        b_typeMatch = true;
        b_stemMatch = true;
        b_formMatch = true;
        b_partialSemanticMatch = true;
        b_fulllSemanticMatch = true;
        errorType = INPUT_ERROR;
        subErrorType = SUBS13;
      } else {
        if (targetComponentType != observedComponentType) {
          // We're dealing with components of different types
          if (observedComponentType == -1) {
            // The observed word is not a valid word
            if (targetWord.useFormSettings) {
              // Consider an invalid form first
              b_typeMatch = true;
              b_stemMatch = true;
              b_formMatch = false;
              b_partialSemanticMatch = true;
              b_fulllSemanticMatch = true;
              subErrorType = INVALID_FORM;
              errorType = GRAMMAR_ERROR;
            } else {
              // Word doesn't accept forms, just class it as lexical
              b_typeMatch = true;
              b_stemMatch = true;
              b_formMatch = true;
              b_partialSemanticMatch = true;
              b_fulllSemanticMatch = true;
              subErrorType = SUBS5;
              errorType = LEXICAL_ERROR;
            }
          } else {
            // It's a valid word, but of different type
            b_typeMatch = false;
            b_stemMatch = false;
            b_formMatch = false;
            b_partialSemanticMatch = semanticMatch();
            b_fulllSemanticMatch = false;
            subErrorType = SUBS4;

            if (spellingErrorLikelihood <= 0.5) {
              // We'll guess it was just a spelling, lexical error
              // which just happened to end up at a valid word
              errorType = LEXICAL_ERROR;
            } else {
              // Looks like a different type of word was chosen deliberately
              errorType = GRAMMAR_ERROR;
            }
          }
        } else {
          // When the types are the same
          if (targetWord.useFormSettings) {
            // Form taking words (Verbs, adjectives, counters, etc)
            if (stemMatch()) {
              // Same stem, inappropriate but valid form (CONCEPT)
              b_typeMatch = true;
              b_stemMatch = true;
              b_formMatch = false;
              b_partialSemanticMatch = true;
              b_fulllSemanticMatch = true;
              subErrorType = WRONG_FORM;
              errorType = CONCEPT_ERROR;
            } else {
              // Different Verb!
              if (semanticMatch()) {
                // Is the meaning exactly the same in fact? (just context
                // difference)
                if (fullSemanticMatch()) {
                  // The same meaning exactly, but inappropriate - lexical error
                  b_typeMatch = true;
                  b_stemMatch = true;
                  b_formMatch = true;
                  b_partialSemanticMatch = true;
                  b_fulllSemanticMatch = true;
                  subErrorType = SUBS0;
                  errorType = GRAMMAR_ERROR;
                } else {
                  // Related, but inappropriate - lexical error
                  b_typeMatch = true;
                  b_stemMatch = true;
                  b_formMatch = true;
                  b_partialSemanticMatch = true;
                  b_fulllSemanticMatch = false;
                  subErrorType = SUBS1;
                  errorType = LEXICAL_ERROR;
                }
              } else {
                b_typeMatch = true;
                b_stemMatch = false;
                b_formMatch = true;
                b_partialSemanticMatch = false;
                b_fulllSemanticMatch = false;

                if (spellingErrorLikelihood <= 0.5) {
                  // We'll guess it was just a spelling, lexical error
                  // which just happened to end up at a valid word
                  subErrorType = SUBS2;
                  errorType = LEXICAL_ERROR;
                } else {
                  // Totaly different verb - assume concept error
                  subErrorType = SUBS3;
                  errorType = CONCEPT_ERROR;
                }
              }
            }
          } else {
            // Non form taking words

            if (targetComponentType == CALL_lexiconStruct.LEX_TYPE_PARTICLES) {
              // A PARTICLE to PARTICLE substitution - special rules for these
              // --------------------------------------------------
              if ((targetWord.word.kana.equals("��")) || (observedWord.word.kana.equals("��"))) {
                if ((targetWord.word.kana.equals("��")) || (observedWord.word.kana.equals("��"))
                    || (targetWord.word.kana.equals("��")) || (observedWord.word.kana.equals("��"))) {
                  // Wa/Ga and mo swapping, down to context more often than not
                  b_typeMatch = true;
                  b_stemMatch = false;
                  b_formMatch = true;
                  b_partialSemanticMatch = true;
                  b_fulllSemanticMatch = false;
                  subErrorType = SUBS11;
                  errorType = CONCEPT_ERROR;

                } else {
                  // A standard particle swap - put this one down as grammar
                  b_typeMatch = true;
                  b_stemMatch = false;
                  b_formMatch = true;
                  b_partialSemanticMatch = true;
                  b_fulllSemanticMatch = false;
                  subErrorType = SUBS10;
                  errorType = GRAMMAR_ERROR;
                }
              }
            } else if (targetComponentType == CALL_lexiconStruct.LEX_TYPE_NOUN_DEFINITIVES) {
              // A DEFINITIVE to DEFINITIVE substitution - special rules for
              // these
              b_typeMatch = true;
              b_stemMatch = false;
              b_formMatch = true;
              b_partialSemanticMatch = true;
              b_fulllSemanticMatch = false;
              subErrorType = SUBS7;
              errorType = LEXICAL_ERROR;
            } else {
              // The rest of the non form taking words (Nouns, Location, etc)
              if (semanticMatch()) {
                b_typeMatch = true;
                b_stemMatch = false;
                b_formMatch = true;
                b_partialSemanticMatch = true;

                // NEED SOMETHING HERE TO DO SUBS0 TYPE, ENG MEANING...
                if (fullSemanticMatch()) {
                  // The same meaning exactly, but inappropriate - lexical error
                  b_fulllSemanticMatch = true;
                  subErrorType = SUBS0;
                  errorType = LEXICAL_ERROR;
                } else {
                  // Related, but inappropriate - lexical error
                  b_fulllSemanticMatch = false;
                  subErrorType = SUBS1;
                  errorType = LEXICAL_ERROR;
                }
              } else {
                b_typeMatch = true;
                b_stemMatch = false;
                b_formMatch = true;
                b_partialSemanticMatch = false;
                b_fulllSemanticMatch = true;

                if (spellingErrorLikelihood <= 0.5) {
                  // We'll guess it was just a spelling, lexical error
                  // which just happened to end up at a valid word
                  subErrorType = SUBS2;
                  errorType = LEXICAL_ERROR;
                } else {
                  // Totaly different verb - assume concept errro
                  subErrorType = SUBS3;
                  errorType = CONCEPT_ERROR;
                }
              }
            }
          }
        }
      }
    }
  }

  // Get the grammar rule at a certain depth. For example:
  // 0: DESU_SIMPLE1
  // 1: SUBJ
  // 2: PERSON
  // 3: SUBJ_PARTICLE
  // ...
  public String specificGrammarRule(int depth) {
    String rString = null;

    if (targetWord != null) {
      rString = targetWord.grammarRuleDepth(depth);
    }

    return rString;
  }

  // Gets the grammar rule at the leaf (the most specific rule)
  public String leafGrammarRule() {
    String rString = null;

    if (targetWord != null) {
      rString = targetWord.leafGrammarRule();
    }

    return rString;
  }

  // Gets the grammar rule at the leaf (the most specific rule)
  public String baseGrammarRule() {
    String rString = null;

    if (targetWord != null) {
      rString = targetWord.baseGrammarRule();
    }

    return rString;
  }

  // Gives a value relating to the string distance
  // This value may be between 0 (the same string), and 1 (totally different)
  public double stringDistance() {
    return CALL_distance.getDistance(observedString, targetString);
  }

  // A set of functions for giving information about error types
  // =================================================================================================================
  public boolean typeMatch() {
    int observedType = -1;
    int targetType = -1;
    boolean rVal = false;

    String rString = null;

    if (observedWord.word != null) {
      observedType = observedWord.word.type;
    }

    if (targetWord.word != null) {
      targetType = targetWord.word.type;
    }

    if (targetType == observedType) {
      rVal = true;
    }

    return rVal;
  }

  public boolean stemMatch() {
    boolean rVal = false;

    if (observedWord.word != null) {
      if (targetWord.word != null) {
        if (observedWord.word == targetWord.word) {
          rVal = true;
        }
      }
    }

    return rVal;
  }

  // Whether the two words actually have the same meaning, just different
  // contexts (my mother, your mother, etc)
  public boolean fullSemanticMatch() {
    boolean rVal = false;

    if ((observedWord.word != null) && (targetWord.word != null)) {
      if (observedWord.word.genEnglish == targetWord.word.genEnglish) {
        rVal = true;
      }
    }

    return rVal;
  }

  // Whether the two words share ANY of the same semantic groups
  public boolean semanticMatch() {
    Vector observedGroups = null;
    String group1, group2;
    Vector targetGroups = null;
    boolean rVal = false;

    if (observedWord.word != null) {
      observedGroups = observedWord.word.categories;
    }

    if (targetWord.word != null) {
      targetGroups = targetWord.word.categories;
    }

    if (stemMatch()) {
      // Same stem -> same group
      rVal = true;
    } else {
      if ((targetGroups != null) && (observedGroups != null)) {
        for (int i = 0; i < targetGroups.size(); i++) {
          for (int j = 0; j < observedGroups.size(); j++) {
            group1 = (String) targetGroups.elementAt(i);
            group2 = (String) observedGroups.elementAt(j);

            if ((group1 != null) && (group2 != null) && (group1.equals(group2))) {
              rVal = true;
              break;
            }
          }

          // We have found a match
          if (rVal)
            break;
        }
      }
    }

    return rVal;
  }

  public boolean spellingError() {
    boolean rVal = false;
    if (spellingErrorScore() > 0.5)
      rVal = true;

    return rVal;
  }

  public double spellingErrorScore() {
    double rVal = 0.0;

    if ((!semanticMatch()) && (!stemMatch()) && (!typeMatch())) {
      // It may be a known word, but if it is not seen to be matching in any
      // other
      // significant way, we may do the spelling check
      if (stringDistance() <= SPELLING_DISTANCE_UPPER_THRESHOLD) {
        rVal = 0.5 + (((SPELLING_DISTANCE_UPPER_THRESHOLD - stringDistance()) / SPELLING_DISTANCE_UPPER_THRESHOLD) / 2.0);
      }
    } else {
      // It's a known word, and has some relation to the target word. But if
      // it's really close,
      // it may still be the result of a spelling error
      if (stringDistance() <= SPELLING_DISTANCE_LOWER_THRESHOLD) {
        rVal = 0.5 + (((SPELLING_DISTANCE_LOWER_THRESHOLD - stringDistance()) / SPELLING_DISTANCE_LOWER_THRESHOLD) / 2.0);
      }
    }

    // Make sure rVal is within certain limits
    if (rVal < 0.0)
      rVal = 0.0;
    if (rVal > 1.0)
      rVal = 1.0;

    return rVal;
  }

  public boolean katakanaHiraganaIssue() {
    String tempString1;
    String tempString2;

    boolean rVal = false;

    if ((observedString != null) && (targetString != null)) {
      // Change observedString to Katakana (if no already)
      tempString1 = CALL_io.correctRomajiFormat(CALL_io.strKanaToRomaji(observedString));

      // Change targetString to Katakana (if no already)
      tempString2 = CALL_io.correctRomajiFormat(CALL_io.strKanaToRomaji(targetString));

      if ((tempString1 != null) && (tempString2 != null)) {
        if (tempString1.equals(tempString2)) {
          // They match in romaji form, but as we know not in Kana form.
          // Thus, there must be a hirigana / katakana issue!
          rVal = true;
        }
      }
    }

    return rVal;
  }

  // A set of functions for returning a string for logging the error types
  // =================================================================================================================
  public String typeMatchString() {
    int observedType = -1;
    String rString = null;

    if (observedWord.word != null) {
      observedType = observedWord.word.type;
    }

    if (typeMatch()) {
      rString = new String("True");
    } else {
      rString = new String("False " + CALL_wordStruct.getTypeString(observedType));
    }

    return rString;
  }

  public String stemMatchString() {
    String rString = null;

    if (stemMatch()) {
      if (targetWord.word != null) {
        rString = new String("True " + targetWord.word.kanji);
      } else {
        rString = new String("True NOSTEM");
      }
    } else {
      rString = new String("False");
    }

    return rString;
  }

  public boolean sameForm() {
    boolean match;
    String targetForm = targetWord.getFormString();
    String observedForm = observedWord.getFormString();

    if ((targetForm != null) && (observedForm != null)) {
      if (targetForm.equals(observedForm)) {
        match = true;
      } else {
        match = false;
      }
    } else if ((targetForm == null) && (observedForm == null)) {
      // Both form-free, so the same
      match = true;
    } else {
      // Mismatch in nulls, not the same
      match = false;
    }

    return match;
  }

  public String semanticMatchString() {
    Vector observedGroups = null;
    String group1, group2;
    Vector targetGroups = null;
    boolean matched = false;

    String rString = null;

    if (semanticMatch()) {
      rString = new String("True");
      // We have to go through again and find the matches
      if (observedWord.word != null) {
        observedGroups = observedWord.word.categories;
      }

      if (targetWord.word != null) {
        targetGroups = targetWord.word.categories;
      }

      if ((targetGroups != null) && (observedGroups != null)) {
        for (int i = 0; i < targetGroups.size(); i++) {
          for (int j = 0; j < observedGroups.size(); j++) {
            group1 = (String) targetGroups.elementAt(i);
            group2 = (String) observedGroups.elementAt(j);

            if ((group1 != null) && (group2 != null) && (group1.equals(group2))) {
              matched = true;
              rString = rString + " " + group1;
            }
          }
        }
      }
    } else {
      // No matches - give up
      rString = new String("False");
    }

    return rString;
  }

  public String spellingErrorString() {
    String rString = new String("Unlikely");

    if (spellingError()) {
      rString = new String("Likely");
    }

    return rString;
  }

  public String katakanaHiraganaIssueString() {
    String rString = new String("No");

    if (katakanaHiraganaIssue()) {
      rString = "Yes";
    } else {
      rString = "No";
    }

    return rString;
  }

  // The function that returns the cost of an error
  // ======================================================================================================
  public int getErrorCost() {
    double errorTypeCost;
    double componentTypeCost;
    double spellingCost;

    int totalCost;

    componentTypeCost = db.costs.getComponentErrorCost(targetComponentType);
    errorTypeCost = db.costs.getErrorTypeCost(errorType, subErrorType);
    spellingCost = db.costs.getSpellingCost(spellingErrorLikelihood);

    totalCost = (int) ((errorTypeCost + spellingCost + componentTypeCost) * CALL_costWeightStruct.COST_MULTIPLIER);

    // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
    // "Component ("+ targetComponentType + ") Cost: " + componentTypeCost);
    // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
    // "Error Type Cost: " + errorTypeCost);
    // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
    // "Spelling Cost: " + spellingCost);
    // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
    // "Total Cost: " + totalCost);

    // We always want to charge something for an error, however small!
    if (totalCost < MIN_ERROR_COST)
      totalCost = MIN_ERROR_COST;

    return totalCost;
  }

  // Returns the string representing the error type
  // ======================================================================================================
  public String errorTypeString() {
    if ((errorType >= 0) && (errorType < typeStrings.length)) {
      return typeStrings[errorType];
    } else {
      // Non defined error type
      return typeStrings[UNDEFINED_ERROR];
    }
  }

  public String subErrorTypeString() {
    // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
    // "Getting Sub Error Type - " + subErrorType);
    if ((subErrorType >= 0) && (subErrorType < subTypeStrings.length)) {
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
      // "Returning " + subTypeStrings[subErrorType]);
      return subTypeStrings[subErrorType];
    } else {
      // Non defined error type
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
      // "Returning  UNK");
      return "UNK";
    }
  }

  public String errorTypeIcon() {
    if ((errorType >= 0) && (errorType < typeIconStrings.length)) {
      return typeIconStrings[errorType];
    } else {
      // Non defined error type
      return typeIconStrings[UNDEFINED_ERROR];
    }
  }

  public String componentTypeIcon() {
    return CALL_lexiconStruct.getTypeIconString(targetComponentType);
  }

  public String errorTargetComponentString() {
    int componentType;

    componentType = targetComponentType + 1;

    if ((componentType >= 0) && (componentType < CALL_lexiconStruct.typeString.length)) {
      return CALL_lexiconStruct.typeString[componentType];
    } else {
      // Non defined error type
      return CALL_lexiconStruct.typeString[0];
    }
  }

  public String errorObservedComponentString() {
    int componentType;

    componentType = observedComponentType + 1;

    if ((componentType >= 0) && (componentType < CALL_lexiconStruct.typeString.length)) {
      return CALL_lexiconStruct.typeString[componentType];
    } else {
      // Non defined error type
      return CALL_lexiconStruct.typeString[0];
    }
  }

  public String sharedSemanticGroups() {
    String outString = null;

    Vector observedGroups = null;
    Vector targetGroups = null;
    String group1, group2;

    if (semanticMatch()) {
      // We have to go through again and find the matches
      if (observedWord.word != null) {
        observedGroups = observedWord.word.categories;
      }

      if (targetWord.word != null) {
        targetGroups = targetWord.word.categories;
      }

      if ((targetGroups != null) && (observedGroups != null)) {
        for (int i = 0; i < targetGroups.size(); i++) {
          for (int j = 0; j < observedGroups.size(); j++) {
            group1 = (String) targetGroups.elementAt(i);
            group2 = (String) observedGroups.elementAt(j);

            if ((group1 != null) && (group2 != null) && (group1.equals(group2))) {
              if (outString == null) {
                // The first group to our "matched" list
                outString = group1;
              } else {
                // Already have groups in the strings, so append this one
                outString = outString + ", " + group1;
              }
            }
          }
        }
      }
    }

    return outString;
  }

  // The error logging function
  // ======================================================================================================
  public void logError() {
    String targetForm, observedForm;

    if ((targetWord != null) && (observedWord != null)) {
      // Some kind of substitution error

      // General Error information
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "SUBSTITUTION ERROR:");
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO, "Target: "
      // + targetString);
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "Observed: " +observedString);
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO, "Lesson: "
      // + lesson + 1);
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "Full Rule: " + targetWord.fullGrammarRule);
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "Base Rule: " + baseGrammarRule());
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "Leaf Rule: " + leafGrammarRule());
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO, "Type: " +
      // CALL_wordStruct.getTypeString(targetWord.word.type));

      // For katakana type checking
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "Mismatch in Katakana/Hiragana: " + katakanaHiraganaIssueString());

      // For verbs
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "Same Stem: " + stemMatchString());

      // Vocabulary information
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "Same Type: " + typeMatchString());
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "Same Semantic Group: " + semanticMatchString());

      // Automated Form Differentiating - do later
      if ((observedWord.useFormSettings) && (targetWord.useFormSettings)) {
        targetForm = targetWord.getFormString();
        observedForm = observedWord.getFormString();
        if ((targetForm != null) && (observedForm != null)) {
          if (targetForm.equals(observedForm)) {
            // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
            // "Same Form: True");
          } else {
            // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
            // "Same Form: False");
          }

          // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
          // "Target Form:" + targetForm);
          // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
          // "Output Form:" + observedForm);
        }
      }

      // For counters
      // //CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "Same Base Number:");
      // //CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "Counter Type:");

      // Lexical comparisson
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "String Distance: " + stringDistance());
    } else if (observedWord != null) {
      // Insertion Error
      // NOTE, CAN'T REALLY HAVE THIS WITH CURRENT INPUT SYSTEM....
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "INSERTION ERROR:");
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO, "Observed:"
      // + observedString);
    } else if (targetWord != null) {
      // Deletion Error
      // NOTE, CAN'T REALLY HAVE THIS WITH CURRENT INPUT SYSTEM....
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "DELETION ERROR:");
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO, "Target:" +
      // targetString);
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "Full Rule:" + targetWord.fullGrammarRule);
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "Base Rule:" + baseGrammarRule());
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
      // "Leaf Rule:" + leafGrammarRule());
    }

    // Now the error categorisation
    // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
    // "Error Type: " + typeStrings[errorType]);
    // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
    // "Sub-Error Type: " + subTypeStrings[subErrorType]);
    // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
    // "Probably Spelling Error: " + spellingErrorLikelihood);
  }
}