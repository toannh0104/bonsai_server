///////////////////////////// //////////////////////////////////////
// Application config information
//

package jcall;

public class CALL_configDataStruct {
  final static int DEFAULT_LEVEL_COST = 1;
  final static int DEFAULT_LEVEL_BIAS = 25;
  final static int DEFAULT_WORD_BIAS = 50;
  final static int DEFAULT_GRAMMAR_BIAS = 50;
  final static int DEFAULT_ST_ERROR_IMPACT = 50;
  final static int DEFAULT_LT_ERROR_IMPACT = 50;
  final static int DEFAULT_AUTO_BALANCE = 1;
  final static int DEFAULT_NUM_PRACTICE_QUESTIONS = -1;

  final static boolean DEFAULT_LESSON_DISPLAY = true;

  final static String HINT_LEVEL_TYPE = "type";
  final static String HINT_LEVEL_ENGLISH = "english";
  final static String HINT_LEVEL_BASE = "base";
  final static String HINT_LEVEL_SYLABLE = "sylable";
  final static String HINT_LEVEL_SURFACE = "surface";

  // Last student name used (only used in global config
  String lastStudent;

  // Session information
  int sessionNumber;

  // UI Options
  int outputStyle;
  boolean showInactiveLessons;

  // Misc options
  boolean runMiscTests;

  // Number of questions
  int numQuestionsPractice;

  // Language

  // Hints
  boolean hintCostEnabled;
  boolean hintChoice;
  boolean diagMarkersEnabled;

  // Hint Bias
  // --------
  int hintGrammarBias; // 0: All Communication -----> 100: All Proficiency
  int hintLevelBias; // Bias of the Level cost vs Component cost

  boolean useWordExperience;

  int hintWordLearningRate; // The rate the discount changes
  int hintWordLearningImpact; // The maximum effect the discount can have (0 -
                              // 100% discount)

  int hintWordLearningBiasLT; // Effects the impact of long term experience on
                              // the total
  int hintWordLearningBiasMT; // Medium term word learning bias (current log in
                              // session only)
  int hintWordLearningBiasST; // Short term word learning bias (current practice
                              // / test only)

  int hintCostAutoBalance; // 0: No, 1: Yes, by # Components. 2: Yes, to fixed
                           // total ammount

  int hintErrorSTImpact; // Effect of short term errors on cost
  int hintErrorLTImpact; // Effect of long term errors on cost

  // Hint Levels
  boolean hintLevelType;
  boolean hintLevelEnglish;
  boolean hintLevelLength;
  boolean hintLevelBase;
  boolean hintLevelSylable;
  boolean hintLevelSurface;

  // The main constructor
  // ////////////////////////////////////////////////////////////////////////////////
  public CALL_configDataStruct() {
    // Set defaults
    lastStudent = null;

    sessionNumber = 0;
    hintCostEnabled = true;
    hintChoice = true;
    diagMarkersEnabled = true;
    outputStyle = CALL_io.romaji;

    showInactiveLessons = DEFAULT_LESSON_DISPLAY;

    useWordExperience = true;

    runMiscTests = false;

    hintCostAutoBalance = DEFAULT_AUTO_BALANCE;
    hintGrammarBias = DEFAULT_GRAMMAR_BIAS;
    hintLevelBias = DEFAULT_LEVEL_BIAS;

    hintWordLearningImpact = DEFAULT_WORD_BIAS;
    hintWordLearningRate = DEFAULT_WORD_BIAS;
    hintWordLearningBiasLT = DEFAULT_WORD_BIAS;
    hintWordLearningBiasMT = DEFAULT_WORD_BIAS;
    hintWordLearningBiasST = DEFAULT_WORD_BIAS;

    hintErrorSTImpact = DEFAULT_ST_ERROR_IMPACT;
    hintErrorLTImpact = DEFAULT_LT_ERROR_IMPACT;

    hintLevelType = true;
    hintLevelEnglish = false;
    hintLevelLength = true;
    hintLevelBase = true;
    hintLevelSylable = true;
    hintLevelSurface = true;

    numQuestionsPractice = DEFAULT_NUM_PRACTICE_QUESTIONS;

  }

  // The copy constructor
  // ////////////////////////////////////////////////////////////////////////////////
  public CALL_configDataStruct(CALL_configDataStruct data) {
    // Set defaults
    lastStudent = null;
    sessionNumber = 0;

    hintCostEnabled = data.hintCostEnabled;
    hintChoice = data.hintChoice;
    diagMarkersEnabled = data.diagMarkersEnabled;
    outputStyle = data.outputStyle;
    showInactiveLessons = data.showInactiveLessons;

    hintGrammarBias = data.hintGrammarBias;
    hintLevelBias = data.hintLevelBias;

    useWordExperience = data.useWordExperience;
    runMiscTests = data.runMiscTests;

    hintWordLearningImpact = data.hintWordLearningImpact;
    hintWordLearningRate = data.hintWordLearningRate;
    hintWordLearningBiasLT = data.hintWordLearningBiasLT;
    hintWordLearningBiasMT = data.hintWordLearningBiasMT;
    hintWordLearningBiasST = data.hintWordLearningBiasST;

    hintErrorSTImpact = data.hintErrorSTImpact;
    hintErrorLTImpact = data.hintErrorLTImpact;

    hintLevelType = data.hintLevelType;
    hintLevelEnglish = data.hintLevelEnglish;
    hintLevelBase = data.hintLevelBase;
    hintLevelSylable = data.hintLevelSylable;
    hintLevelSurface = data.hintLevelSurface;
  }
}