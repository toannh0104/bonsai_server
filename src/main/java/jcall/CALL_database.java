///////////////////////////////////////////////////////////////////
// This holds the top level of the database structure, which links
// into all the underlying structures (lessons, students, history etc)
//
//

package jcall;

import java.util.Properties;

import jcall.db.JCALL_Lexicon;
import jcall.db.JCALL_PredictionDatasStruct;
import jcall.db.JCALL_VerbErrorRules;
import jp.co.gmo.rs.ghs.model.DataConfig;

import org.apache.log4j.Logger;

public class CALL_database {

  static Logger logger = Logger.getLogger(CALL_database.class.getName());

  // Data structs
  public CALL_lexiconStruct lexicon;
  public CALL_lessonsStruct lessons;
  public CALL_verbRulesStruct vrules;
  public CALL_adjectiveRulesStruct arules;
  public CALL_counterRulesStruct crules;
  public CALL_grammarRulesStruct grules;
  public CALL_conceptRulesStruct concepts;
  public JCALL_PredictionDatasStruct eps;
  public JCALL_VerbErrorRules verules;
  private Properties property;
  CALL_studentsStruct students;

  CALL_costWeightStruct costs;
  CALL_errorTextStruct helpText;

  // Currently logged in student (if any)
  CALL_studentStruct currentStudent;
  // Static Defines
  static String dataDirectory;
  static String lessonFileName;
  static String verbRuleFileName;
  static String adjectiveRuleFileName;
  static String costWeights;
  static String costWeightsSpeech;
  static String errorHelp;
  static String counterRuleFileName;
  static String grammarRuleFileName;
  static String lexiconFileName;
  static String conceptRuleFileName;
  static final boolean RUN_VERB_CHECKS = false;
  static final boolean RUN_ADJECTIVE_CHECKS = false;

  public static String PREDICTIONFILE;
  public static String VerbErrorRuleFile;

  static final int MAX_LESSONS = 64;

  public CALL_database() {
    logger.warn("Contructor CALL_database");

    // logger.debug("enter CALL_database(), class construction function ");
    String kanaString, kanjiString;

    int i;
    property = new DataConfig().getProperty();

    // set data path
    dataDirectory = property.getProperty("data");
    lessonFileName = property.getProperty("data.lessons");
    verbRuleFileName = property.getProperty("data.verb-rules");
    adjectiveRuleFileName = property.getProperty("data.adjectives");
    costWeights = property.getProperty("data.costWeights");
    errorHelp = property.getProperty("data.errorHelp");
    counterRuleFileName = property.getProperty("data.counter-rules");
    grammarRuleFileName = property.getProperty("data.grammar-rules");
    lexiconFileName = property.getProperty("data.lexicon");
    conceptRuleFileName = property.getProperty("data.concepts");
    PREDICTIONFILE = property.getProperty("data.EP");
    VerbErrorRuleFile = property.getProperty("data.verberror-rules");

    // For Debug
    CALL_grammarRuleStruct grule;
    CALL_conceptInstanceStruct instance;
    CALL_sentenceStruct sentence;
    CALL_wordStruct tempVerb, tempAdjective;

    // Initialise structures
    lexicon = new CALL_lexiconStruct(this);
    vrules = new CALL_verbRulesStruct();
    arules = new CALL_adjectiveRulesStruct();
    grules = new CALL_grammarRulesStruct(this);
    crules = new CALL_counterRulesStruct(this);
    concepts = new CALL_conceptRulesStruct(this);
    lessons = new CALL_lessonsStruct(this);
    students = new CALL_studentsStruct();
    costs = new CALL_costWeightStruct();
    helpText = new CALL_errorTextStruct();
    // eps = new JCALL_PredictionDatasStruct();
    // verules = new JCALL_VerbErrorRules();

    currentStudent = null;

    // Load data
    loadData();

    // For checking verb form functionality
    if (RUN_VERB_CHECKS) {
      for (i = 0; i < lexicon.verbs.size(); i++) {
        tempVerb = (CALL_wordStruct) lexicon.verbs.elementAt(i);

        if (tempVerb != null) {
          // Run a check of expansion using this verb
          kanaString = vrules.applyRule(tempVerb, "mashou", 0, 0, 0, 0, false);
          kanjiString = vrules.applyRule(tempVerb, "mashou", 0, 0, 0, 0, true);
          // CALL_debug.printlog(CALL_debug.MOD_VRULES,
          // CALL_debug.INFO, "Mashou Form: [" + kanaString + "] / ["
          // + kanjiString + "]");

          kanaString = vrules.applyRule(tempVerb, "mashou", 0, 1, 0, 0, false);
          kanjiString = vrules.applyRule(tempVerb, "mashou", 0, 1, 0, 0, true);
          // CALL_debug.printlog(CALL_debug.MOD_VRULES,
          // CALL_debug.INFO, "-You Form: [" + kanaString + "] / [" +
          // kanjiString + "]");

          kanaString = vrules.applyRule(tempVerb, "basic", 1, 1, 0, 0, false);
          kanjiString = vrules.applyRule(tempVerb, "basic", 1, 1, 0, 0, true);
          // CALL_debug.printlog(CALL_debug.MOD_VRULES,
          // CALL_debug.INFO, "-Ta Form: [" + kanaString + "] / [" +
          // kanjiString + "]");

          kanaString = vrules.applyRule(tempVerb, "basic", 0, 0, 0, 0, false);
          kanjiString = vrules.applyRule(tempVerb, "basic", 0, 0, 0, 0, true);
          // CALL_debug.printlog(CALL_debug.MOD_VRULES,
          // CALL_debug.INFO, "Masu Form: [" + kanaString + "] / [" +
          // kanjiString + "]");
        }
      }
    }

    // For checking adjective form functionality
    if (RUN_ADJECTIVE_CHECKS) {
      for (i = 0; i < lexicon.adjectives.size(); i++) {
        tempAdjective = (CALL_wordStruct) lexicon.adjectives.elementAt(i);

        if (tempAdjective != null) {
          // Run a check of expansion using this verb
          kanaString = arules.applyRule(tempAdjective, "standard", 0, 1, 0, 0, false);
          kanjiString = arules.applyRule(tempAdjective, "standard", 0, 1, 0, 0, true);
          // CALL_debug.printlog(CALL_debug.MOD_ARULES,
          // CALL_debug.INFO,
          // "Standard Form, Positive, Present, Polite: [" +
          // kanaString + "] / [" + kanjiString + "]");

          kanaString = arules.applyRule(tempAdjective, "modifier", 0, 1, 0, 0, false);
          kanjiString = arules.applyRule(tempAdjective, "modifier", 0, 1, 0, 0, true);
          // CALL_debug.printlog(CALL_debug.MOD_ARULES,
          // CALL_debug.INFO,
          // "Modifier Form, Positive, Present, Polite: [" +
          // kanaString + "] / [" + kanjiString + "]");

          kanaString = arules.applyRule(tempAdjective, "connective", 0, 1, 0, 0, false);
          kanjiString = arules.applyRule(tempAdjective, "connective", 0, 1, 0, 0, true);
          // CALL_debug.printlog(CALL_debug.MOD_ARULES,
          // CALL_debug.INFO,
          // "Connective Form, Positive, Present, Polite: [" +
          // kanaString + "] / [" + kanjiString + "]");

          kanaString = arules.applyRule(tempAdjective, "adverb", 0, 1, 0, 0, false);
          kanjiString = arules.applyRule(tempAdjective, "adverb", 0, 1, 0, 0, true);
          // CALL_debug.printlog(CALL_debug.MOD_ARULES,
          // CALL_debug.INFO,
          // "Adverbial Form, Positive, Present, Polite: [" +
          // kanaString + "] / [" + kanjiString + "]");

          kanaString = arules.applyRule(tempAdjective, "standard", 1, 0, 0, 0, false);
          kanjiString = arules.applyRule(tempAdjective, "standard", 1, 0, 0, 0, true);
          // CALL_debug.printlog(CALL_debug.MOD_ARULES,
          // CALL_debug.INFO,
          // "Standard Form, Negative, Past, Polite: [" + kanaString +
          // "] / [" + kanjiString + "]");

        }
      }
    }

  }

  public boolean loadData() {
    // logger.debug("enter loadData()");

    boolean rc = true;

    if (rc == true) {
      // Load the error prediction text
      // CALL_debug.printlog(CALL_debug.MOD_DATABASE, CALL_debug.INFO,
      // "Loading prediction text...");
      // JCALL_PredictionParser epp = new JCALL_PredictionParser();
      // eps = epp.loadDataFromFile(PREDICTIONFILE);
      eps = JCALL_PredictionDatasStruct.getInstance();
      logger.warn("Loading prediction text...");
      rc = true;
    }

    if (rc == true) {
      // Load the help text associated with the various error types
      // CALL_debug.printlog(CALL_debug.MOD_DATABASE, CALL_debug.INFO,
      // "Loading verb error rules...");
      logger.warn("Loading verb error rules...");
      verules = JCALL_VerbErrorRules.getInstance();
    }

    if (rc == true) {
      // Load the lexicon
      // CALL_debug.printlog(CALL_debug.MOD_DATABASE, CALL_debug.INFO,
      // "Loading lexicon...");
      logger.warn("Loading lexicon...");
      // 2011.02.28 T.Tajima change for reload
      // JCALL_Lexicon lex = JCALL_Lexicon.getInstance();
      JCALL_Lexicon lex = JCALL_Lexicon.getNewInstance();
      rc = lexicon.load_lexicon(lex);
    }
    if (rc == true) {
      // Load the verb rules
      // CALL_debug.printlog(CALL_debug.MOD_DATABASE, CALL_debug.INFO,
      // "Loading verb rules...");
      logger.warn("Loading verb rules...");
      rc = vrules.loadRules(verbRuleFileName);
    }
    if (rc == true) {
      // Load the adjective rules
      // CALL_debug.printlog(CALL_debug.MOD_DATABASE, CALL_debug.INFO,
      // "Loading adjective rules...");
      logger.warn("Loading adjective rules...");
      rc = arules.loadRules(adjectiveRuleFileName);
    }

    if (rc == true) {
      // Load the grammar rules
      // CALL_debug.printlog(CALL_debug.MOD_DATABASE, CALL_debug.INFO,
      // "Loading grammar rules...");
      logger.warn("Loading grammar rules...");
      rc = grules.loadRules(grammarRuleFileName);
    }
    if (rc == true) {
      // Load the counter rules
      // CALL_debug.printlog(CALL_debug.MOD_DATABASE, CALL_debug.INFO,
      // "Loading counter rules...");
      logger.warn("Loading counter rules...");
      rc = crules.loadRules(counterRuleFileName);
    }
    if (rc == true) {
      // Load the concept definitions
      // CALL_debug.printlog(CALL_debug.MOD_DATABASE, CALL_debug.INFO,
      // "Loading concept rules...");
      logger.warn("Loading concept rules...");
      rc = concepts.load_concepts(conceptRuleFileName);
    }
    if (rc == true) {
      // Load the lesson specifications (do last as links to grammar,
      // concepts, etc)
      // CALL_debug.printlog(CALL_debug.MOD_DATABASE, CALL_debug.INFO,
      // "Loading lessons...");
      logger.warn("Loading lessons...");
      rc = lessons.load(lessonFileName);
    }

    if (rc == true) {
      // Load the hint and error cost weights
      // CALL_debug.printlog(CALL_debug.MOD_DATABASE, CALL_debug.INFO,
      // "Loading costs...");
      logger.warn("Loading costs...");
      rc = costs.load(costWeights);
    }
    if (rc == true) {
      // Load the help text associated with the various error types
      // CALL_debug.printlog(CALL_debug.MOD_DATABASE, CALL_debug.INFO,
      // "Loading help text...");
      logger.warn("Loading help text...");
      rc = helpText.load(errorHelp);
    }
    // Not use
    // if(rc == true)
    // {
    // // Load the student profiles
    // // CALL_debug.printlog(CALL_debug.MOD_DATABASE, CALL_debug.INFO,
    // "Loading students...");
    // logger.warn("Loading students...");
    // rc = students.load(dataDirectory);
    // }

    if (!rc) {
      // CALL_debug.printlog(CALL_debug.MOD_DATABASE, CALL_debug.ERROR,
      // "Error loading data");
      logger.error("Error loading data");
    }

    return rc;
  }

  // For calling tranformation rules
  public String applyTransformationRule(CALL_wordStruct word, String rname, int sign, int tense, int politeness,
      int question, boolean kanji, boolean warning) {
    String returnString = null;

    if (word != null) {
      if (word.type == CALL_lexiconStruct.LEX_TYPE_VERB) {
        // logger.debug("In applyTransformationRule, [LEX_TYPE_VERB]: "+word.kanji);

        returnString = vrules.applyRule(word, rname, sign, tense, politeness, question, kanji, warning);
      } else {
        // logger.debug("In applyTransformationRule, [LEX_TYPE_ADJ]: "+word.kanji);

        returnString = arules.applyRule(word, rname, sign, tense, politeness, question, kanji, warning);
      }
    }

    return returnString;
  }

  // For compatibility (the version without the warning flag)
  public String applyTransformationRule(CALL_wordStruct word, String rname, int sign, int tense, int politeness,
      int question, boolean kanji) {
    return applyTransformationRule(word, rname, sign, tense, politeness, question, kanji, true);
  }

  public static void main(String[] agrs) {
    CALL_database database = new CALL_database();
    // database.loadData();
    System.out.println("11111");

  }

}