///////////////////////////// //////////////////////////////////////
// Stores information about the cost weighting for different components,
// hint and error types, etc.
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

public class CALL_costWeightStruct {
  // Static definitions
  static final int LENGTH_COST = 0;
  static final int INTERMEDIATE_COST = 1;
  static final int DICTIONARY_COST = 2;
  static final int FINALFORM_COST = 3;

  static final double DEFAULT_WEIGHT = 0.0;
  static final double COST_MULTIPLIER = 10.0;
  static final double MIN_HINT_COST = 0.0;
  static final double MIN_ERROR_COST = 1.0;

  // The settings
  // -----------

  // Component Specific Weights
  double component_verb;
  double component_noun;
  double component_particle;
  double component_adjective;
  double component_adverb;
  double component_counter;
  double component_location;
  double component_definitive;
  double component_other;

  // Hint Specific Weights
  double hint_type_length;
  double hint_type_intermediate;
  double hint_type_dictionary;
  double hint_type_final;
  double hint_total_components;
  double hint_levelTotal;

  // Error Specific Weights
  double error_type_grammar;
  double error_type_lexical;
  double error_type_input;
  double error_type_concept;
  double error_type_nonSpelling;

  // Hint Costs - To be calculated from the above;
  int hint_cost_verb[];
  int hint_cost_noun[];
  int hint_cost_particle[];
  int hint_cost_adjective[];
  int hint_cost_adverb[];
  int hint_cost_counter[];
  int hint_cost_location[];
  int hint_cost_definitive[];
  int hint_cost_other[];

  // The main constructor
  // ///////////////////////////////////////////////////////////////////////////////
  public CALL_costWeightStruct() {
    // Initialise the weights to default settings
    init();
  }

  //
  // Initialise the weights to default settings
  public void init() {
    // Component Specific Weights
    component_verb = DEFAULT_WEIGHT;
    component_noun = DEFAULT_WEIGHT;
    component_particle = DEFAULT_WEIGHT;
    component_adjective = DEFAULT_WEIGHT;
    component_adverb = DEFAULT_WEIGHT;
    component_counter = DEFAULT_WEIGHT;
    component_location = DEFAULT_WEIGHT;
    component_definitive = DEFAULT_WEIGHT;
    component_other = DEFAULT_WEIGHT;

    // Hint Specific Weights
    hint_type_length = DEFAULT_WEIGHT;
    hint_type_intermediate = DEFAULT_WEIGHT;
    hint_type_dictionary = DEFAULT_WEIGHT;
    hint_type_final = DEFAULT_WEIGHT;
    hint_total_components = DEFAULT_WEIGHT;

    // Error Specific Weights
    error_type_grammar = DEFAULT_WEIGHT;
    error_type_lexical = DEFAULT_WEIGHT;
    error_type_input = DEFAULT_WEIGHT;
    error_type_concept = DEFAULT_WEIGHT;
    error_type_nonSpelling = DEFAULT_WEIGHT;

    hint_cost_verb = new int[CALL_wordHintsStruct.MAX_LEVELS];
    hint_cost_noun = new int[CALL_wordHintsStruct.MAX_LEVELS];
    hint_cost_particle = new int[CALL_wordHintsStruct.MAX_LEVELS];
    hint_cost_adjective = new int[CALL_wordHintsStruct.MAX_LEVELS];
    hint_cost_adverb = new int[CALL_wordHintsStruct.MAX_LEVELS];
    hint_cost_counter = new int[CALL_wordHintsStruct.MAX_LEVELS];
    hint_cost_location = new int[CALL_wordHintsStruct.MAX_LEVELS];
    hint_cost_definitive = new int[CALL_wordHintsStruct.MAX_LEVELS];
    hint_cost_other = new int[CALL_wordHintsStruct.MAX_LEVELS];

    for (int i = 0; i < CALL_wordHintsStruct.MAX_LEVELS; i++) {
      // Initialise all costs to 0 initially
      // These will get updated after loading the cost weights
      hint_cost_verb[i] = 0;
      hint_cost_noun[i] = 0;
      hint_cost_particle[i] = 0;
      hint_cost_adjective[i] = 0;
      hint_cost_adverb[i] = 0;
      hint_cost_counter[i] = 0;
      hint_cost_location[i] = 0;
      hint_cost_definitive[i] = 0;
      hint_cost_other[i] = 0;
    }

  }

  //
  // Load the cost weights - only those specified in file will change
  public boolean load(String filename) {
    boolean rc = true;
    FileReader in;
    int readstatus = 0;

    int tempInt;
    double tempDouble;
    String tempString;
    String readLine;
    StringTokenizer st;

    // Open file
    try {

      InputStream inputStream = new FileInputStream(filename);
      Reader reader = new InputStreamReader(inputStream);
      BufferedReader bufferedReader = new BufferedReader(reader);
      // Read Line by line
      while (readstatus == 0) {
        readLine = bufferedReader.readLine().trim();

        if (readLine == null) {
          // Reached end of file - stop reading
          readstatus = 1;
        } else if (readLine.startsWith("#")) {
          // A comment...skip
          continue;
        } else {
          // We have some valid text, so parse it
          // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.DEBUG,
          // "Parsing line [" + readLine + "]");
          st = new StringTokenizer(readLine);
          tempString = st.nextToken();

          // Debug
          // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.DEBUG,
          // "Token: [" + tempString + "]");

          // General Settings
          if (tempString.startsWith("-eof")) {
            // EOF marker
            readstatus = 1;
          } else if (tempString.startsWith("-component")) {
            // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.DEBUG,
            // "Loading component cost");

            // Component weight settings
            // -----------------------
            if (tempString.equals("-component_verb")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                component_verb = tempDouble;
                // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.DEBUG,
                // "Verb Cost: " + component_verb);
              }
            } else if (tempString.equals("-component_noun")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                component_noun = tempDouble;
              }
            } else if (tempString.equals("-component_particle")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                component_particle = tempDouble;
              }
            } else if (tempString.equals("-component_adjective")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                component_adjective = tempDouble;
              }
            } else if (tempString.equals("-component_adverb")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                component_adverb = tempDouble;
              }
            } else if (tempString.equals("-component_counter")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                component_counter = tempDouble;
              }
            } else if (tempString.equals("-component_location")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                component_location = tempDouble;
              }
            } else if (tempString.equals("-component_definitive")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                component_definitive = tempDouble;
              }
            } else if (tempString.equals("-component_other")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                component_other = tempDouble;
              }
            }
          } else if (tempString.startsWith("-hint")) {
            // Hint weight settings
            // ------------------
            if (tempString.equals("-hint_type_length")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                hint_type_length = tempDouble;
              }
            }
            if (tempString.equals("-hint_type_intermediate")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                hint_type_intermediate = tempDouble;
              }
            }
            if (tempString.equals("-hint_type_dictionary")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                hint_type_dictionary = tempDouble;
              }
            }
            if (tempString.equals("-hint_type_final")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                hint_type_final = tempDouble;
              }
            }
            if (tempString.equals("-hint_total_components")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                hint_total_components = tempDouble;
              }
            }
          } else if (tempString.startsWith("-error")) {
            // Error weight settings
            // ------------------
            if (tempString.equals("-error_type_grammar")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                error_type_grammar = tempDouble;
              }
            }
            if (tempString.equals("-error_type_lexical")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                error_type_lexical = tempDouble;
              }
            }
            if (tempString.equals("-error_type_input")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                error_type_input = tempDouble;
              }
            }
            if (tempString.equals("-error_type_concept")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                error_type_concept = tempDouble;
              }
            }
            if (tempString.equals("-error_type_nonSpelling")) {
              tempDouble = CALL_io.getNextDouble(st);
              if (tempDouble != CALL_io.INVALID_DBL) {
                error_type_nonSpelling = tempDouble;
              }
            }
          }
        }
      }

    } catch (IOException e) {
      // File does not exist
      // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.ERROR,
      // "Problem opening config file [" + WEIGHTS_FILENAME + "]");
      readstatus = -1;
      rc = false;
      in = null;
    }

    if (rc) {
      // Now update the hint costs
      updateHintCosts();

      // And print the debug (only actually prints if debug level set)
      printDebug();
    }
    System.out.println("Cost Height:");
    return rc;
  }

  //
  // Sets the Hint costs for each level of each component type
  public void updateHintCosts() {
    double weight;
    double maxError;

    double lCost;
    double iCost;
    double dCost;
    double fCost;

    double errorFactor;

    // Take into account the component counter as well for levels
    lCost = hint_type_length + hint_total_components;
    iCost = hint_type_intermediate + hint_total_components;
    dCost = hint_type_dictionary + hint_total_components;
    fCost = hint_type_final + hint_total_components;

    // For each component type
    for (int i = 0; i < CALL_lexiconStruct.LEX_NUM_TYPES; i++) {
      // 1) Add in the max error cost that could occur
      // ---------------------------------------
      // Might want to configure which component type has which errors
      // seperately, maybe in config file somewhere
      if ((i == CALL_lexiconStruct.LEX_TYPE_NOUN)) {
        // Do not allow grammatical errors
        maxError = Math.max(Math.max(error_type_lexical, error_type_concept), error_type_input)
            + error_type_nonSpelling;
      } else {
        // Allow any type of error
        maxError = Math.max(Math.max(Math.max(error_type_lexical, error_type_concept), error_type_input),
            error_type_grammar) + error_type_nonSpelling;
      }

      // 3) Now calculate for each hint level
      // -------------------------------
      switch (i) {
        case CALL_lexiconStruct.LEX_TYPE_NOUN:
          maxError += component_noun;
          maxError *= COST_MULTIPLIER;

          errorFactor = (maxError / (lCost + iCost + dCost));
          hint_cost_noun[0] = (int) (errorFactor * lCost);
          hint_cost_noun[1] = (int) (errorFactor * iCost);
          hint_cost_noun[2] = (int) (errorFactor * dCost);
          hint_cost_noun[3] = 0;
          break;
        case CALL_lexiconStruct.LEX_TYPE_NOUN_AREANAME:
          maxError += component_noun;
          maxError *= COST_MULTIPLIER;

          errorFactor = (maxError / (lCost + iCost + dCost));
          hint_cost_noun[0] = (int) (errorFactor * lCost);
          hint_cost_noun[1] = (int) (errorFactor * iCost);
          hint_cost_noun[2] = (int) (errorFactor * dCost);
          hint_cost_noun[3] = 0;
          break;
        case CALL_lexiconStruct.LEX_TYPE_NOUN_PERSONNAME:
          maxError += component_noun;
          maxError *= COST_MULTIPLIER;

          errorFactor = (maxError / (lCost + iCost + dCost));
          hint_cost_noun[0] = (int) (errorFactor * lCost);
          hint_cost_noun[1] = (int) (errorFactor * iCost);
          hint_cost_noun[2] = (int) (errorFactor * dCost);
          hint_cost_noun[3] = 0;
          break;
        case CALL_lexiconStruct.LEX_TYPE_NOUN_PRONOUN_PERSON:
          maxError += component_noun;
          maxError *= COST_MULTIPLIER;

          errorFactor = (maxError / (lCost + iCost + dCost));
          hint_cost_noun[0] = (int) (errorFactor * lCost);
          hint_cost_noun[1] = (int) (errorFactor * iCost);
          hint_cost_noun[2] = (int) (errorFactor * dCost);
          hint_cost_noun[3] = 0;
          break;
        case CALL_lexiconStruct.LEX_TYPE_NOUN_SURU:
          maxError += component_noun;
          maxError *= COST_MULTIPLIER;

          errorFactor = (maxError / (lCost + iCost + dCost));
          hint_cost_noun[0] = (int) (errorFactor * lCost);
          hint_cost_noun[1] = (int) (errorFactor * iCost);
          hint_cost_noun[2] = (int) (errorFactor * dCost);
          hint_cost_noun[3] = 0;
          break;
        case CALL_lexiconStruct.LEX_TYPE_VERB:
          maxError += component_verb;
          maxError *= COST_MULTIPLIER;
          errorFactor = (maxError / (lCost + iCost + dCost + fCost));
          hint_cost_verb[0] = (int) (errorFactor * lCost);
          hint_cost_verb[1] = (int) (errorFactor * iCost);
          hint_cost_verb[2] = (int) (errorFactor * dCost);
          hint_cost_verb[3] = (int) (errorFactor * fCost);
          break;

        case CALL_lexiconStruct.LEX_TYPE_PARTICLE_AUXIL:
          maxError += component_particle;
          maxError *= COST_MULTIPLIER;
          errorFactor = (maxError / (lCost + iCost + dCost));
          hint_cost_particle[0] = (int) (errorFactor * lCost);
          hint_cost_particle[1] = (int) (errorFactor * iCost);
          hint_cost_particle[2] = (int) (errorFactor * dCost);
          hint_cost_particle[3] = 0;
          break;

        case CALL_lexiconStruct.LEX_TYPE_ADJECTIVE:
          maxError += component_adjective;
          maxError *= COST_MULTIPLIER;
          errorFactor = (maxError / (lCost + iCost + dCost + fCost));
          hint_cost_adjective[0] = (int) (errorFactor * lCost);
          hint_cost_adjective[1] = (int) (errorFactor * iCost);
          hint_cost_adjective[2] = (int) (errorFactor * dCost);
          hint_cost_adjective[3] = (int) (errorFactor * fCost);
          break;
        case CALL_lexiconStruct.LEX_TYPE_ADJVERB:
          maxError += component_adjective;
          maxError *= COST_MULTIPLIER;
          errorFactor = (maxError / (lCost + iCost + dCost + fCost));
          hint_cost_adjective[0] = (int) (errorFactor * lCost);
          hint_cost_adjective[1] = (int) (errorFactor * iCost);
          hint_cost_adjective[2] = (int) (errorFactor * dCost);
          hint_cost_adjective[3] = (int) (errorFactor * fCost);
          break;

        case CALL_lexiconStruct.LEX_TYPE_ADVERB:
          maxError += component_adverb;
          maxError *= COST_MULTIPLIER;
          errorFactor = (maxError / (lCost + iCost + dCost + fCost));
          hint_cost_adverb[0] = (int) (errorFactor * lCost);
          hint_cost_adverb[1] = (int) (errorFactor * iCost);
          hint_cost_adverb[2] = (int) (errorFactor * dCost);
          hint_cost_adverb[3] = (int) (errorFactor * fCost);
          break;

        case CALL_lexiconStruct.LEX_TYPE_NOUN_NUMERAL:
          maxError += component_counter;
          maxError *= COST_MULTIPLIER;
          errorFactor = (maxError / (lCost + iCost + dCost + fCost));
          hint_cost_counter[0] = (int) (errorFactor * lCost);
          hint_cost_counter[1] = (int) (errorFactor * iCost);
          hint_cost_counter[2] = (int) (errorFactor * dCost);
          hint_cost_counter[3] = (int) (errorFactor * fCost);
          break;
        case CALL_lexiconStruct.LEX_TYPE_NUMQUANT:
          maxError += component_counter;
          maxError *= COST_MULTIPLIER;
          errorFactor = (maxError / (lCost + iCost + dCost + fCost));
          hint_cost_counter[0] = (int) (errorFactor * lCost);
          hint_cost_counter[1] = (int) (errorFactor * iCost);
          hint_cost_counter[2] = (int) (errorFactor * dCost);
          hint_cost_counter[3] = (int) (errorFactor * fCost);
          break;
        // case CALL_lexiconStruct.LEX_TYPE_POSITIONS:
        // maxError += component_location;
        // maxError *= COST_MULTIPLIER;
        // errorFactor = (maxError / (lCost + iCost + dCost));
        // hint_cost_location[0] = (int)(errorFactor * lCost);
        // hint_cost_location[1] = (int)(errorFactor * iCost);
        // hint_cost_location[2] = (int)(errorFactor * dCost);
        // hint_cost_location[3] = 0;
        // break;

        case CALL_lexiconStruct.LEX_TYPE_NOUN_DEFINITIVES:
          maxError += component_definitive;
          maxError *= COST_MULTIPLIER;
          errorFactor = (maxError / (lCost + iCost + dCost));
          hint_cost_definitive[0] = (int) (errorFactor * lCost);
          hint_cost_definitive[1] = (int) (errorFactor * iCost);
          hint_cost_definitive[2] = (int) (errorFactor * dCost);
          hint_cost_definitive[3] = 0;
          break;

        case CALL_lexiconStruct.LEX_TYPE_KANDOU:
          maxError += component_other;
          maxError *= COST_MULTIPLIER;
          errorFactor = (maxError / (lCost + iCost + dCost));
          hint_cost_other[0] = (int) (errorFactor * lCost);
          hint_cost_other[1] = (int) (errorFactor * iCost);
          hint_cost_other[2] = (int) (errorFactor * dCost);
          hint_cost_other[3] = 0;
          break;
        case CALL_lexiconStruct.LEX_TYPE_RENTAI:
          maxError += component_other;
          maxError *= COST_MULTIPLIER;
          errorFactor = (maxError / (lCost + iCost + dCost));
          hint_cost_other[0] = (int) (errorFactor * lCost);
          hint_cost_other[1] = (int) (errorFactor * iCost);
          hint_cost_other[2] = (int) (errorFactor * dCost);
          hint_cost_other[3] = 0;
          break;
        //
        // case CALL_lexiconStruct.LEX_TYPE_MISC:
        // maxError += component_other;
        // maxError *= COST_MULTIPLIER;
        // errorFactor = (maxError / (lCost + iCost + dCost));
        // hint_cost_other[0] = (int)(errorFactor * lCost);
        // hint_cost_other[1] = (int)(errorFactor * iCost);
        // hint_cost_other[2] = (int)(errorFactor * dCost);
        // hint_cost_other[3] = 0;
        // break;

        default:
          // Ignore for now (later all unknowns get mapped to "other"
          // but for now we only want to calculate it the once!
          continue;
      }
    }
  }

  //
  // Returns the costs for each level of the specified hint type
  public int getHintCost(int component, int level) {
    int cost = 0;

    if (level < CALL_wordHintsStruct.MAX_LEVELS) {
      switch (component) {
        case CALL_lexiconStruct.LEX_TYPE_NOUN:
          cost = hint_cost_noun[level];
          break;
        case CALL_lexiconStruct.LEX_TYPE_NOUN_AREANAME:
          cost = hint_cost_noun[level];
          break;
        case CALL_lexiconStruct.LEX_TYPE_NOUN_PERSONNAME:
          cost = hint_cost_noun[level];
          break;
        case CALL_lexiconStruct.LEX_TYPE_NOUN_PRONOUN_PERSON:
          cost = hint_cost_noun[level];
          break;
        case CALL_lexiconStruct.LEX_TYPE_NOUN_SURU:
          cost = hint_cost_noun[level];
          break;
        case CALL_lexiconStruct.LEX_TYPE_VERB:
          cost = hint_cost_verb[level];
          break;

        case CALL_lexiconStruct.LEX_TYPE_PARTICLE_AUXIL:
          cost = hint_cost_particle[level];
          break;

        case CALL_lexiconStruct.LEX_TYPE_ADJECTIVE:
          cost = hint_cost_adjective[level];
          break;
        case CALL_lexiconStruct.LEX_TYPE_ADJVERB:
          cost = hint_cost_adjective[level];
          break;

        case CALL_lexiconStruct.LEX_TYPE_ADVERB:
          cost = hint_cost_adverb[level];
          break;

        case CALL_lexiconStruct.LEX_TYPE_NOUN_NUMERAL:
          cost = hint_cost_counter[level];
          break;
        case CALL_lexiconStruct.LEX_TYPE_NUMQUANT:
          cost = hint_cost_counter[level];
          break;
        // case CALL_lexiconStruct.LEX_TYPE_POSITIONS:
        // cost = hint_cost_location[level];
        // break;

        case CALL_lexiconStruct.LEX_TYPE_NOUN_DEFINITIVES:
          cost = hint_cost_definitive[level];
          break;

        default:
          cost = hint_cost_other[level];
          break;
      }
    }

    return cost;
  }

  //
  // Returns the base cost for an error on a specific component (not factoring
  // in the type of error etc)
  public double getComponentErrorCost(int component) {
    double cost = 0;

    switch (component) {

      case CALL_lexiconStruct.LEX_TYPE_NOUN:
        cost = component_noun;
        break;
      case CALL_lexiconStruct.LEX_TYPE_NOUN_AREANAME:
        cost = component_noun;
        break;
      case CALL_lexiconStruct.LEX_TYPE_NOUN_PERSONNAME:
        cost = component_noun;
        break;
      case CALL_lexiconStruct.LEX_TYPE_NOUN_PRONOUN_PERSON:
        cost = component_noun;
        break;
      case CALL_lexiconStruct.LEX_TYPE_NOUN_SURU:
        cost = component_noun;
        break;
      case CALL_lexiconStruct.LEX_TYPE_VERB:
        cost = component_verb;
        break;

      case CALL_lexiconStruct.LEX_TYPE_PARTICLE_AUXIL:
        cost = this.component_particle;
        break;

      case CALL_lexiconStruct.LEX_TYPE_ADJECTIVE:
        cost = component_adjective;
        break;
      case CALL_lexiconStruct.LEX_TYPE_ADJVERB:
        cost = component_adjective;
        break;

      case CALL_lexiconStruct.LEX_TYPE_ADVERB:
        cost = component_adverb;
        break;

      case CALL_lexiconStruct.LEX_TYPE_NOUN_NUMERAL:
        cost = component_counter;
        break;

      case CALL_lexiconStruct.LEX_TYPE_NUMQUANT:
        cost = component_counter;
        break;

      // case CALL_lexiconStruct.LEX_TYPE_POSITIONS:
      // cost = hint_cost_location[level];
      // break;

      case CALL_lexiconStruct.LEX_TYPE_NOUN_DEFINITIVES:
        cost = component_definitive;

      default:
        cost = component_other;
        break;
    }

    return cost;
  }

  //
  // Returns the cost for the given spelling error likelihood (based on
  // non-spelling error cost weight)
  public double getSpellingCost(double spellingErrorLikelihood) {
    return (1.0 - spellingErrorLikelihood) * error_type_nonSpelling;
  }

  //
  // Returns the cost for the given error type (with subtype...although that
  // bits not implemented right now)
  public double getErrorTypeCost(int errorType, int subErrorType) {
    double typeCost;

    switch (errorType) {
      case CALL_errorPairStruct.GRAMMAR_ERROR:
        typeCost = error_type_grammar;
        break;
      case CALL_errorPairStruct.LEXICAL_ERROR:
        typeCost = error_type_lexical;
        break;
      case CALL_errorPairStruct.CONCEPT_ERROR:
        typeCost = error_type_concept;
        break;
      case CALL_errorPairStruct.INPUT_ERROR:
        typeCost = error_type_input;
        break;
      default:
        typeCost = 0.0;
        break;
    }

    return typeCost;
  }

  public void printDebug() {
    // Component costs
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-component_verb: " + component_verb);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-component_noun: " + component_noun);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-component_particle: " + component_particle);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-component_adjective: " + component_adjective);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-component_adverb: " + component_adverb);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-component_counter: " + component_counter);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-component_location: " + component_location);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-component_definitive: " + component_definitive);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-component_verb: " + component_other);

    // Hint Specific Weights
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-hint_type_length: " + hint_type_length);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-hint_type_intermediate: " + hint_type_intermediate);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-hint_type_dictionary: " + hint_type_dictionary);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-hint_type_final: " + hint_type_final);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-hint_total_components: " + hint_total_components);

    // Error Specific Weights
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-error_type_grammar: " + error_type_grammar);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-error_type_lexical: " + error_type_lexical);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-error_type_input: " + error_type_input);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-error_type_concept: " + error_type_concept);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "-error_type_nonSpelling: " + error_type_nonSpelling);

    // Now the hint costs
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "Verb Hints: " + hint_cost_verb[0] + "," + hint_cost_verb[1] + "," +
    // hint_cost_verb[2] + "," + hint_cost_verb[3]);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "Noun Hints: " + hint_cost_noun[0] + "," + hint_cost_noun[1] + "," +
    // hint_cost_noun[2]);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "Particle Hints: " + hint_cost_particle[0] + "," + hint_cost_particle[1]
    // + "," + hint_cost_particle[2]);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "Adjective Hints: " + hint_cost_adjective[0] + "," +
    // hint_cost_adjective[1] + "," + hint_cost_adjective[2] + "," +
    // hint_cost_adjective[3]);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "Adverb Hints: " + hint_cost_adverb[0] + "," + hint_cost_adverb[1] + ","
    // + hint_cost_adverb[2] + "," + hint_cost_adverb[3]);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "Counter Hints: " + hint_cost_counter[0] + "," + hint_cost_counter[1] +
    // "," + hint_cost_counter[2] + "," + hint_cost_counter[3]);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "Location Hints: " + hint_cost_location[0] + "," + hint_cost_location[1]
    // + "," + hint_cost_location[2] );
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "Definitive Hints: " + hint_cost_definitive[0] + "," +
    // hint_cost_definitive[1] + "," + hint_cost_definitive[2]);
    // CALL_debug.printlog(CALL_debug.MOD_COSTS, CALL_debug.DEBUG,
    // "Other Hints: " + hint_cost_other[0] + "," + hint_cost_other[1] + "," +
    // hint_cost_other[2]);
  }
}