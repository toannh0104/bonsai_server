/**
 * Created on 2008/06/13
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import java.util.Vector;

import jcall.config.FindConfig;
import jcall.db.JCALL_Lexicon;
import jcall.db.JCALL_NetworkSubNodeStruct;
import jcall.db.JCALL_PredictionProcess;
import jcall.db.JCALL_Word;
import jcall.recognition.database.DataManager;
import jcall.recognition.database.SentenceDataMeta;
import jcall.recognition.database.TextSentenceDataMeta;
import jcall.recognition.database.WordDataMeta;
import jcall.recognition.dataprocess.NewLogParser;
import jcall.recognition.dataprocess.OldCallLogParser;
import jcall.util.FileManager;

import org.apache.log4j.Logger;

public class ComputeScore {
  static Logger logger = Logger.getLogger(ComputeScore.class.getName());

  // read hints files
  // read errors from db
  // For each sentence, read weights number and compute score, then subtract
  // penalty
  /*
   * static weights
   */

  // Lexical, grammar, input, concept. 6,2,1,3.
  // verb,
  static String[] Errors_Verb = { "TW_DForm", "DW_TForm", "DW_DForm", "TW_WIF", "OTHERS" };
  // static int[] ErrorPenalty_Verb = {9,4,1,2,4};
  static int[] ErrorPenalty_Verb = { 10, 10, 10, 10, 10 };

  // static int[] ErrorPenalty_Verb = {3,6,2,6};

  static String[] Errors_Noun = { "DW", "TW_NTD", "TW_PCE", "DW_PCE", "OTHERS" };
  // static int[] ErrorPenalty_Noun = {6,2,4,1,7};
  static int[] ErrorPenalty_Noun = { 10, 10, 10, 10, 10 };

  static String[] Errors_Particle = { "DW", "OTHERS" };
  // static int[] ErrorPenalty_Particle = {17,3};
  static int[] ErrorPenalty_Particle = { 10, 10 };

  // static int[] ErrorPenalty_Particle = {3,6};
  static String[] Errors_Counter = { "DQ_SNum", "NQ_SNum", "TW_WForm", "TW_PCE", "DQ_PCE" };
  static int[] ErrorPenalty_Counter = { 5, 2, 2, 4, 3 };

  // static int[] ErrorPenalty_Counter = {6,6,6,6,6};
  static String[] Errors_Component = { "verb", "noun", "particle", "counter" };
  // static int[] ErrorPenalty_Component = {4,4,1,6};
  static int[] ErrorPenalty_Component = { 10, 10, 10, 10 };

  // static int[] ErrorPenalty_Component = {6,4,1,7};h

  static String[] Errors_Type = { "Lexical", "Grammar", "Concept", "Input" };
  // static int[] ErrorPenalty_Type = {4,4,1,6};
  static int[] ErrorPenalty_Type = { 10, 10, 10, 10 };

  // static int[] ErrorPenalty_Score = {36,28,17,30};
  static int[] ErrorPenalty_Score = { 100, 100, 100, 100 };

  static String HintFile = "./data/hints.txt";
  static String Ex1HintFile = "./data/firstexprimenthints.txt";
  // static String[] Errors_Counter = {"DW","OTHERS"};
  // static int[] ErrorPenalty_Counter = {17,3};
  //
  JCALL_PredictionProcess pp;
  HintStruct hs;
  JCALL_Lexicon l;

  // static String Ex1ErrorTableName = "WordErrTable";
  // static String Ex1SenTableName = "Sentneces";

  public ComputeScore() {
    // TODO Auto-generated constructor stub
    l = JCALL_Lexicon.getInstance();
  }

  public int[] scoreing(SentenceDataMeta sdm, Vector vHints) {
    // Vector hints, Vector errors,
    int[] ret = { 0, 0, 0 };
    String strTar;
    String strRec;
    int score = 0; // 100
    int total = 0; // any
    int penalty = 0; // any
    int hintPelanlty = 0;
    Vector hints = getHints(sdm, vHints);

    String TS = sdm.getStrTargetAnswer();
    String Rec = sdm.getStrSpeechAnswer();
    StringTokenizer stTarget = new StringTokenizer(TS);
    StringTokenizer stRecognization = new StringTokenizer(Rec);
    Vector<String> vTarget = new Vector<String>();
    Vector<String> vRec = new Vector<String>();

    while (stTarget.hasMoreTokens()) {
      String str = stTarget.nextToken();
      if (str != null && str.length() > 0) {
        vTarget.addElement(str);
      }
    }

    while (stRecognization.hasMoreTokens()) {
      String str = stRecognization.nextToken();
      if (str != null && str.length() > 0) {
        vRec.addElement(str);
      }
    }

    if (vTarget != null && vRec != null && vTarget.size() == vRec.size()) {
      logger.debug("word size: " + vTarget.size());
      // System.out.println("word size: "+vTarget.size());
      for (int i = 0; i < vTarget.size(); i++) {
        strTar = (String) vTarget.elementAt(i);
        strRec = (String) vRec.elementAt(i);
        String strKudasai = "ください";
        String strKudasai2 = "kudasai";
        // System.out.println("target word: "+ strTar + " recognition word: "+
        // strRec);
        // using hint?
        if (i == vTarget.size() - 1 && (strTar.equals(strKudasai) || strTar.equals(strKudasai2))) {
          total = total + ErrorPenalty_Score[0];
          if (!strTar.equals(strRec)) {
            penalty = penalty + ErrorPenalty_Verb[5] * ErrorPenalty_Component[0];
          } else {
            if (hints != null && hints.size() > 0) {
              penalty = penalty + getHintPenalty(hints, strTar, JCALL_Lexicon.LEX_TYPE_VERB);
            }
          }

        } else {// not the special word

          JCALL_Word call_word = l.getWordFmSurForm(strTar);
          if (call_word == null) {
            logger.error("Not Found Word in Lexicon: " + strTar);
            // System.out.println("Not Found Word in Lexicon: "+strTar);

          } else {
            // System.out.println("Find word: "+call_word.getStrKanji()
            // +" type: "+ call_word.getIntType() );
            total = total + computeTotal(call_word.getIntType());

            hintPelanlty = 0;

            if (hints != null && hints.size() > 0) {
              // System.out.println("May Use hint: ");
              hintPelanlty = getHintPenalty(hints, strTar, call_word.getIntType());

              penalty = penalty + hintPelanlty;
            } else {
              // System.out.println("No hints");
            }
            if (!strTar.equals(strRec)) {
              int lesson = Integer.parseInt(sdm.getStrLesson());
              int p = computePenalty(call_word, strRec, lesson);

              if (hintPelanlty == 0) {

                penalty = penalty + p;
              } else {
                int sum = p + hintPelanlty;
                if (sum >= computeTotal(call_word.getIntType())) {
                  penalty = penalty + computeTotal(call_word.getIntType()) - hintPelanlty;
                } else {
                  penalty = penalty + p;
                }
              }
            }
          }

        }

      }
    } else {

      logger.error("Different length betwen target s and rec s");
    }

    System.out.println("StudentID: " + sdm.getIntStudentID() + " Lesson: " + sdm.getStrLesson() + " total: " + total
        + " Penalty: " + penalty + " Score: " + (total - penalty) * 100 / total);

    ret[0] = total;
    ret[1] = penalty;
    ret[2] = (total - penalty) * 100 / total;
    return ret;

  }

  public int[] scoreing_plusDebug(SentenceDataMeta sdm, Vector vHints) {
    // Vector hints, Vector errors,
    int[] ret = { 0, 0, 0 };
    String strTar;
    String strRec;
    int score = 0; // 100
    int total = 0; // any
    int penalty = 0; // any
    int hintPelanlty = 0;
    Vector hints = getHints_withDebug(sdm, vHints);

    String TS = sdm.getStrTargetAnswer();
    String Rec = sdm.getStrSpeechAnswer();
    StringTokenizer stTarget = new StringTokenizer(TS);
    StringTokenizer stRecognization = new StringTokenizer(Rec);
    Vector<String> vTarget = new Vector<String>();
    Vector<String> vRec = new Vector<String>();

    while (stTarget.hasMoreTokens()) {
      String str = stTarget.nextToken();
      if (str != null && str.length() > 0) {
        vTarget.addElement(str);
      }
    }

    while (stRecognization.hasMoreTokens()) {
      String str = stRecognization.nextToken();
      if (str != null && str.length() > 0) {
        vRec.addElement(str);
      }
    }

    if (vTarget != null && vRec != null && vTarget.size() == vRec.size()) {
      logger.debug("word size: " + vTarget.size());
      // System.out.println("word size: "+vTarget.size());
      for (int i = 0; i < vTarget.size(); i++) {
        strTar = (String) vTarget.elementAt(i);
        strRec = (String) vRec.elementAt(i);
        String strKudasai = "ください";
        System.out.println("target word: " + strTar + " recognition word: " + strRec);
        // using hint?
        if (i == vTarget.size() - 1 && strTar.equals(strKudasai)) {
          total = total + ErrorPenalty_Score[0];
          if (!strTar.equals(strRec)) {
            penalty = penalty + ErrorPenalty_Verb[5] * ErrorPenalty_Component[0];
          } else {
            if (hints != null && hints.size() > 0) {
              penalty = penalty + getHintPenalty(hints, strTar, JCALL_Lexicon.LEX_TYPE_VERB);
            }
          }

        } else {// not the special word

          JCALL_Word call_word = l.getWordFmSurForm(strTar);
          if (call_word == null) {
            logger.error("Not Found Word in Lexicon: " + strTar);
            System.out.println("Not Found Word in Lexicon: " + strTar);

          } else {
            // System.out.println("Find word: "+call_word.getStrKanji()
            // +" type: "+ call_word.getIntType() );
            total = total + computeTotal(call_word.getIntType());

            hintPelanlty = 0;

            if (hints != null && hints.size() > 0) {
              System.out.println("May Use hint: ");
              hintPelanlty = getHintPenalty(hints, strTar, call_word.getIntType());

              penalty = penalty + hintPelanlty;
            } else {
              // System.out.println("No hints");
            }
            if (!strTar.equals(strRec)) {
              System.out.println("strTar!= strRec, need compute penalty");
              int lesson = Integer.parseInt(sdm.getStrLesson());
              int p = computePenalty(call_word, strRec, lesson);
              System.out.println("computePenalty: " + p);
              if (hintPelanlty == 0) {
                penalty = penalty + p;
              } else {
                int sum = p + hintPelanlty;
                if (sum >= computeTotal(call_word.getIntType())) {
                  penalty = penalty + computeTotal(call_word.getIntType()) - hintPelanlty;

                } else {
                  penalty = penalty + p;
                }
              }
            }
          }

        }

      }
    } else {

      logger.error("Different length betwen target s and rec s");
    }

    System.out.println("StudentID: " + sdm.getIntStudentID() + " Lesson: " + sdm.getStrLesson() + " total: " + total
        + " Penalty: " + penalty + " Score: " + (total - penalty) * 100 / total);

    ret[0] = total;
    ret[1] = penalty;
    ret[2] = (total - penalty) * 100 / total;
    return ret;

  }

  public int computeTotal(int wType) {
    int intRet = 0;
    // String strWType = ""+wType;
    if (wType == JCALL_Lexicon.LEX_TYPE_VERB) {
      intRet = ErrorPenalty_Score[0];
    } else if (wType == JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL) {

      intRet = ErrorPenalty_Score[2];

    } else if (wType == JCALL_Lexicon.LEX_TYPE_NUMQUANT) {

      intRet = ErrorPenalty_Score[3];

    } else {
      intRet = ErrorPenalty_Score[1];

    }
    // System.out.println("computeTotal: "+ intRet);
    return intRet;
  }

  public int computePenalty(JCALL_Word word, String strRec, int lesson) {
    // System.out.println("entering computePenalty;");
    int intRet = 0;
    // String strWType = ""+wType;
    // String strKana = word.getStrKana();
    // String strKanji = word.getStrKanji();
    Vector vSubs = getSubstitution(word, lesson);
    if (vSubs != null && vSubs.size() > 0) {
      // System.out.println("Substitution size: "+ vSubs.size());
      for (int i = 0; i < vSubs.size(); i++) {
        JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vSubs.get(i);
        String strKana = node.getStrKana();
        String strKanji = node.getStrKanji();
        if (strRec.equalsIgnoreCase(strKana) || strRec.equalsIgnoreCase(strKanji)) {
          if (node.isBAccept()) {
            intRet = 0;
          } else {
            String error = node.getStrError();
            intRet = getPenaltyWeight(error, word.getIntType());
          }
          // System.out.println("computePenalty: "+ intRet);
          return intRet;
        }

      }

    }

    if (intRet == 0) {
      intRet = getPenaltyWeight4Others(word.getIntType());
    }

    // System.out.println("computePenalty: "+ intRet);
    return intRet;
  }

  public int getPenaltyWeight(String error, int wType) {
    int intRet = 0;

    if (wType == JCALL_Lexicon.LEX_TYPE_VERB) {
      for (int i = 0; i < Errors_Verb.length; i++) {
        String errorTemp = Errors_Verb[i];
        if (error.equalsIgnoreCase(errorTemp)) {
          intRet = ErrorPenalty_Verb[i] * ErrorPenalty_Component[0];
          return intRet;
        }
      }
      intRet = ErrorPenalty_Verb[ErrorPenalty_Verb.length - 1] * ErrorPenalty_Component[0];

    } else if (wType == JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL) {
      for (int i = 0; i < Errors_Particle.length; i++) {
        String errorTemp = Errors_Particle[i];
        if (error.equalsIgnoreCase(errorTemp)) {
          intRet = ErrorPenalty_Particle[i] * ErrorPenalty_Component[2];
          // System.out.println("Find error weight item");
          return intRet;
        }
      }
      intRet = ErrorPenalty_Particle[ErrorPenalty_Particle.length - 1] * ErrorPenalty_Component[2];

    } else if (wType == JCALL_Lexicon.LEX_TYPE_NUMQUANT) {

      for (int i = 0; i < Errors_Counter.length; i++) {
        String errorTemp = Errors_Counter[i];
        if (error.equalsIgnoreCase(errorTemp)) {
          intRet = ErrorPenalty_Counter[i] * ErrorPenalty_Component[3];
          return intRet;
        }
      }
      intRet = ErrorPenalty_Counter[ErrorPenalty_Counter.length - 1] * ErrorPenalty_Component[3];

    } else {

      for (int i = 0; i < Errors_Noun.length; i++) {
        String errorTemp = Errors_Noun[i];
        if (error.equalsIgnoreCase(errorTemp)) {
          intRet = ErrorPenalty_Noun[i] * ErrorPenalty_Component[1];
          return intRet;
        }
      }
      intRet = ErrorPenalty_Noun[ErrorPenalty_Noun.length - 1] * ErrorPenalty_Component[1];
    }

    return intRet;
  }

  public int getPenaltyWeight4Others(int wType) {
    int intRet = 0;
    if (wType == JCALL_Lexicon.LEX_TYPE_VERB) {

      intRet = ErrorPenalty_Verb[ErrorPenalty_Verb.length - 1] * ErrorPenalty_Component[0];

    } else if (wType == JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL) {

      intRet = ErrorPenalty_Particle[ErrorPenalty_Particle.length - 1] * ErrorPenalty_Component[2];

    } else if (wType == JCALL_Lexicon.LEX_TYPE_NUMQUANT) {

      intRet = ErrorPenalty_Counter[ErrorPenalty_Counter.length - 1] * ErrorPenalty_Component[3];

    } else {

      intRet = ErrorPenalty_Noun[ErrorPenalty_Noun.length - 1] * ErrorPenalty_Component[1];
    }
    return intRet;

  }

  public int getHintPenalty(Vector hints, String strTargetWord, int wType) {
    // System.out.println("entering getHintPenalty. strTargetWord: "+
    // strTargetWord);

    int intRet = 0;
    for (int i = 0; i < hints.size(); i++) {
      HintStruct hs = (HintStruct) hints.get(i);
      String hw = hs.TargetW;

      if (hw.equalsIgnoreCase(strTargetWord)) {
        String level = hs.hintLevel;
        if (level.equalsIgnoreCase("length") || level.equalsIgnoreCase("1")) {
          if (wType == JCALL_Lexicon.LEX_TYPE_VERB) {
            intRet = computeTotal(wType) / 4;
          } else {
            intRet = computeTotal(wType) / 3;
          }
        } else if (level.equalsIgnoreCase("intermediate") || level.equalsIgnoreCase("2")) {
          if (wType == JCALL_Lexicon.LEX_TYPE_VERB) {
            intRet = computeTotal(wType) / 2;
          } else {
            intRet = (computeTotal(wType) * 2) / 3;
          }
          // System.out.println("hintPelanlty: "+ intRet);
        } else if (level.equalsIgnoreCase("baseform") || level.equalsIgnoreCase("3")) {
          if (wType == JCALL_Lexicon.LEX_TYPE_VERB) {
            intRet = (computeTotal(wType) * 3) / 4;
          } else {
            intRet = computeTotal(wType);
          }
          // System.out.println("hintPelanlty: "+ intRet);
        } else if (level.equalsIgnoreCase("final") || level.equalsIgnoreCase("4")) {
          intRet = computeTotal(wType);
          // System.out.println("hintPelanlty: "+ intRet);
        }
        System.out.println("hintPelanlty: " + intRet);
        break;
      }

    }

    // if(intRet==0){
    // logger.error("using hint: "+
    // strTargetWord+" ,but no match in hints.tex");
    // }

    return intRet;

  }

  public int getErrorPenalty(Vector errors, String strTargetWord, int wType) {
    // System.out.println("entering getHintPenalty. strTargetWord: "+
    // strTargetWord);

    int intRet = 0;
    for (int i = 0; i < errors.size(); i++) {
      WordDataMeta hs = (WordDataMeta) errors.get(i);
      String hw = hs.getStrTargetWord();

      System.out.println("Error word: " + hw);

      if (hw.equalsIgnoreCase(strTargetWord)) {
        String error = hs.getStrSpecificType();
        intRet = getPenaltyWeight(error, wType);

        System.out.println("Find Error Item; ");

        break;
      }
    }

    // if(intRet==0){
    // intRet = getPenaltyWeight4Others(wType);
    // }

    return intRet;

  }

  public Vector getHints(SentenceDataMeta sdm, Vector vHints) {
    // System.out.println("get hints for s: "+ sdm.getStrTargetAnswer());
    Vector<HintStruct> vResult = new Vector<HintStruct>();
    for (int i = 0; i < vHints.size(); i++) {
      HintStruct hs = (HintStruct) vHints.get(i);
      int lesson = Integer.parseInt(sdm.getStrLesson());
      if (hs.lesson == lesson && hs.studentID == sdm.getIntStudentID()) {
        // System.out.println("lesson and studentID is the same, HintStruct's TargetS: "+
        // hs.TargetS );
        if (hs.TargetS.equalsIgnoreCase(sdm.getStrTargetAnswer())) {
          vResult.addElement(hs);
        }
      }
    }
    return vResult;

  }

  public Vector getHints_withDebug(SentenceDataMeta sdm, Vector vHints) {
    System.out.println("get hints for s: " + sdm.getStrTargetAnswer());
    Vector<HintStruct> vResult = new Vector<HintStruct>();
    for (int i = 0; i < vHints.size(); i++) {
      HintStruct hs = (HintStruct) vHints.get(i);
      int lesson = Integer.parseInt(sdm.getStrLesson());
      if (hs.lesson == lesson && hs.studentID == sdm.getIntStudentID()) {
        System.out.println("lesson and studentID is the same, HintStruct's TargetS: " + hs.TargetS);
        if (hs.TargetS.equalsIgnoreCase(sdm.getStrTargetAnswer())) {
          vResult.addElement(hs);
        }
      }
    }
    return vResult;

  }

  public Vector getSubstitution(String word, int lesson) {

    pp = new JCALL_PredictionProcess();
    logger.debug("enter getSubstitution");
    Vector vReturn = new Vector();
    String strKudasai = "ください";
    if (word.equals(strKudasai)) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setStrKana(strKudasai);
      node.setStrKanji(strKudasai);
      node.setStrError("correct");
      vReturn.add(node);
      return vReturn;
    }

    JCALL_Word call_word = JCALL_Lexicon.getInstance().getWordFmSurForm(word);
    if (call_word == null) {
      logger.error("Not Found Word in Lexicon: " + word);
      return null;
    }
    int intType = call_word.getIntType();
    try {
      if (intType == JCALL_Lexicon.LEX_TYPE_VERB) {

        vReturn = pp.getVerbErrors(call_word, lesson);

      } else if (intType == JCALL_Lexicon.LEX_TYPE_NUMQUANT) {
        // get the predicted words, then add to the network;
        logger.debug("Counter prediction: ");

        String strCounter = call_word.getStrKanji();
        logger.debug("Counter kanji: " + strCounter);
        vReturn = pp.getNQErrors(call_word, lesson);

      }

      else if (intType == JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL) {

        vReturn = pp.getParticleErrors(call_word, lesson);
        // get the predicted words, then add to the network;

      } else {// the only possible is noun
              // get the predicted words, then add to the network;
        vReturn = pp.getNounSubstitution(call_word, lesson);

      }
    } catch (Exception e) {
      logger.error("Exception");
      vReturn = null;
    }

    return vReturn;

  }

  public Vector getSubstitution(JCALL_Word call_word, int lesson) {

    pp = new JCALL_PredictionProcess();

    logger.debug("enter getSubstitution");
    // System.out.println("enter getSubstitution");
    Vector vReturn = new Vector();

    int intType = call_word.getIntType();
    try {
      if (intType == JCALL_Lexicon.LEX_TYPE_VERB) {

        vReturn = pp.getVerbErrors(call_word, lesson);

      } else if (intType == JCALL_Lexicon.LEX_TYPE_NUMQUANT) {
        // get the predicted words, then add to the network;
        logger.debug("Counter prediction: ");

        String strCounter = call_word.getStrKanji();
        logger.debug("Counter kanji: " + strCounter);
        vReturn = pp.getNQErrors(call_word, lesson);

      }

      else if (intType == JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL) {

        vReturn = pp.getParticleErrors(call_word, lesson);
        // get the predicted words, then add to the network;

      } else {// the only possible is noun
              // get the predicted words, then add to the network;
        vReturn = pp.getNounSubstitution(call_word, lesson);

      }
    } catch (Exception e) {
      logger.error("Exception");
      System.out.println("Exception in getSubstitution; word: " + call_word.getStrKanji() + " Lesson: " + lesson);

      vReturn = null;
    }

    return vReturn;

  }

  // assign hs
  // hints
  public Vector loadHints(String filename) {

    BufferedReader br;
    Vector vReturn = new Vector();
    int currentIndex = 0;
    try {
      br = new BufferedReader(new FileReader(new File(filename)));
      // load and parse data
      int readstatus = 0;
      // Read Line by line
      while (readstatus == 0) {

        String line = br.readLine();
        if (line == null) {
          continue;
        } else {
          line = line.trim();
          if (line.equalsIgnoreCase("-eof")) {
            return vReturn;
          }
          StringTokenizer token = new StringTokenizer(line, "	");
          currentIndex = 1;
          HintStruct hstruct = new HintStruct();
          while (token.hasMoreTokens()) {
            String str = token.nextToken();
            if (currentIndex == 1) {
              int intID = Integer.parseInt(str);
              hstruct.studentID = intID;
            } else if (currentIndex == 2) {
              int intID = Integer.parseInt(str);
              hstruct.lesson = intID;
            } else if (currentIndex == 3) {
              // int intID = Integer.parseInt(str);
              hstruct.Question = str;
            } else if (currentIndex == 4) {
              String strTemp = str;
              if (str.indexOf("[") != -1 && str.indexOf("]") != -1) {
                strTemp = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
              }
              hstruct.TargetS = strTemp;
            } else if (currentIndex == 5) {
              String strTemp = str;
              hstruct.TargetW = strTemp;
            } else if (currentIndex == 6) {
              String strTemp = str;

              hstruct.hintLevel = strTemp;
            } else if (currentIndex == 7) {
              String strTemp = str;
              hstruct.component = Integer.parseInt(strTemp);
            }

            currentIndex++;
          }

          vReturn.add(hstruct);

        }

      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return vReturn;

  }

  // in experiment db
  public Vector getSentencesFromDb(String tablename) {

    Vector vReturn = new Vector();
    SentenceDataMeta sdm;

    String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
    DataManager dm = new DataManager();
    dm.connectToAccess(strOdbc);

    Vector<WordDataMeta> v = new Vector<WordDataMeta>();
    // strwordTableName
    // read data into WordErrDataMeta one by one, change attribute on the basis
    // of its type
    try {

      // WordDataMeta objMeta;
      ResultSet tmpRs = dm.searchTable(tablename, "");
      while (tmpRs.next()) {
        sdm = new SentenceDataMeta();
        int id = tmpRs.getInt("ID");
        int studentID = tmpRs.getInt("StudentID");
        String lesson = tmpRs.getString("Lesson");
        String strTarget = tmpRs.getString("strTargetAnswer");
        // String strObserved = tmpRs.getString("strTextAnswer");
        String strRecognition = tmpRs.getString("strSpeechAnswer");
        // String strListen = tmpRs.getString("strListeningAnswer");
        String strSenError = tmpRs.getString("SentenceLevelError");
        sdm.setIntID(id);
        sdm.setIntStudentID(studentID);
        sdm.setStrLesson(lesson);
        sdm.setStrTargetAnswer(strTarget);
        sdm.setStrSpeechAnswer(strRecognition);
        // sdm.setStrListeningAnswer(strListen);
        sdm.setStrSentenceLevelError(strSenError);
        vReturn.add(sdm);

      }// end while

    } catch (Exception e) {
      System.out.println("Exception in getSentencesFromDb" + e.getMessage());
      e.printStackTrace();
    }

    dm.releaseConn();

    return vReturn;
  }

  public Vector getSentencesWithStudent(Vector sentences, int studentID) {
    Vector vReturn = new Vector();
    for (int j = 0; j < sentences.size(); j++) {
      SentenceDataMeta sdm = (SentenceDataMeta) sentences.get(j);
      if (studentID == sdm.getIntStudentID()) {
        vReturn.addElement(sdm);
      }

    }
    // System.out.println("Student["+studentID+"] answer sentence : "+
    // vReturn.size());
    return vReturn;
  }

  public void getScores(Vector hints, Vector sentences, int[] studentIDs, String scorefile) {
    FileManager.createFile(scorefile);
    PrintWriter pw;
    try {
      pw = new PrintWriter(new FileWriter(scorefile, true));

      // Vector<Integer> sscore;
      int ss = 0; // compute total score for each student;
      for (int i = 0; i < studentIDs.length; i++) {
        int studentID = studentIDs[i];
        Vector v = getSentencesWithStudent(sentences, studentID);
        if (v != null && v.size() > 0) {
          ss = 0;
          System.out.println("size: " + v.size());
          for (int j = 0; j < v.size(); j++) {
            SentenceDataMeta sdm = (SentenceDataMeta) v.get(j);
            if (sdm != null) {
              int[] scores = scoreing(sdm, hints);
              String strResult = "ID [" + sdm.getIntID() + "] ";
              strResult = strResult + "StudentID [" + sdm.getIntStudentID() + "] ";
              strResult = strResult + "Lesson [" + sdm.getStrLesson() + "] ";
              strResult = strResult + "Score: [" + scores[2] + "]";
              pw.println(strResult);
              ss = ss + scores[2];
            }
          }
          pw.println("=========Avarage score for student[" + studentID + "]=======" + (ss / v.size()));
          System.out.println("=========Avarage score for student[" + studentID + "]=======" + (ss / v.size()));

        }

      }
      pw.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  // /////////////////////////
  // //////
  // ///// for the first experiment data
  // ////////////////////////

  int state;
  static final int STATE_NEW_SESSION = 0;
  static final int STATE_NEW_LESSON = 1;
  static final int STATE_NEXT_QUESTION = 2;
  static final int STATE_HINT_DETAIL = 3;
  static final int STATE_ERRORSENTENCE = 4;
  static final int STATE_CORRETSENTENCE = 5;
  static final int STATE_QUESTION_END = 6;
  static final int STATE_ERROE_SUBSTITION = 7;
  BufferedReader br;
  PrintWriter pw;

  public void loadHintsFromLogFiles(String logfilepath) throws ClassNotFoundException, IOException {
    // create table "correctSentences"
    // then get logfile path from configuration
    // Configuration configuration = new
    // Configuration("../../../JCALLConfig.xml");
    // logger.debug("opened config file: "+"JCALLConfig.xml");
    // String logfilepath = configuration.getItemValue("Data","TrialLogs");
    logger.debug("get log file path: " + logfilepath);
    File filePath = new File(logfilepath);
    if (!filePath.exists()) {
      logger.debug("goes wrong,does not exist log file in path: " + logfilepath);
      System.out.println("goes wrong,does not exist log file in path: " + logfilepath);
    } else {
      logger.debug(filePath.getName());
      // System.out.println(filePath.getName());
      File[] arrayFile = filePath.listFiles();
      for (int i = 0; i < arrayFile.length; i++) {
        File fileTemp = arrayFile[i];
        if (fileTemp.isFile()) {
          // load data one by one

          String file = fileTemp.getName() + "_hint.txt";
          String fn = "./Data/hints/" + file;
          loadHintsFromOneLogFile(fileTemp, fn);
        }
      }
    }
  }

  void loadHintsFromOneLogFile(File oneFile, String savingFilename) throws IOException {

    state = 0;
    String name = oneFile.getName();
    name = name.substring(0, name.indexOf("-"));
    int intStudent = Integer.parseInt(name);

    TextSentenceDataMeta tsdm = null;

    HintStruct hs = null;

    FileManager.createFile(savingFilename);
    pw = new PrintWriter(new FileWriter(savingFilename, true));
    br = new BufferedReader(new FileReader(oneFile));

    boolean token = false; // keep to remember if the sentence is wrong;
    int intTotalSentence = 0;
    HintStruct hstruct = null;
    String line;
    boolean token1 = false;
    line = br.readLine();
    while (line != null) {
      switch (state) {
        case STATE_NEW_SESSION:
          if (line.indexOf("Flow: Usage: Selected lesson") != -1) {
            hs = new HintStruct();
            hs.setStudentID(intStudent);
            token1 = false;
            hstruct = null;
            state = STATE_NEW_LESSON;
          }
          break;
        case STATE_NEW_LESSON:
          if (line.indexOf("Flow: Usage: Practice started, lesson") != -1) {
            String strLesson = line.substring(line.indexOf("lesson") + 7);
            strLesson = strLesson.trim();
            int intLesson = Integer.parseInt(strLesson) + 1;
            // strLesson = Integer.toString(intLesson);
            hs.setLesson(intLesson);
            // tsdm.setStrLesson(strLesson);

          } else if (line.indexOf("=NEXT QUESTION") != -1) {
            state = STATE_NEXT_QUESTION;
          }
          break;
        case STATE_NEXT_QUESTION:
          if (line.indexOf("Flow: Usage: Lesson") != -1) {
            // System.out.println("Line: "+ line);

            String strQuestion = line.substring(line.indexOf("Question") + 9).trim();

            if (strQuestion == null) {
              System.out.println("Question is null ");
            } else {
              // System.out.println("Question: "+ strQuestion);
              // tsdm.setStrQuestion(strQuestion);
              hs.setQuestion(strQuestion);
              state = STATE_HINT_DETAIL;
            }
          }
          break;
        case STATE_HINT_DETAIL:
          // Hnt: Usage:
          if (line.indexOf("Hnt: Usage:") != -1) {
            System.out.println("Hnt; Line: " + line);
            // the hint line, like: Hnt: Usage: Hint Selected (Free Choice)::
            // Component: 0, Level: 1, Cat: Subject, Desc: *****
            line = line.trim();
            if (line.indexOf("Component:") != -1) {
              String comp = line.substring(line.indexOf("Component:") + 11, line.indexOf("Component:") + 12);
              System.out.println("Component:" + comp);
              int component = Integer.parseInt(comp);
              if (hs.component < 0) {
                System.out.println("First using hint line");
                hs.setComponent(component);
                System.out.println("Hint comp: " + hs.getComponent());
              } else {
                System.out.println("Not First using hint line");
                if (hs.component == component) {
                  System.out.println("But Same Compoente");
                  // hs.setComponent(component);
                } else {
                  System.out.println("Diff Compoenent, add new hint stuct in hs");
                  if (hs.hints == null) {
                    hs.hints = new Vector();
                    hstruct = new HintStruct();
                    hstruct.studentID = hs.studentID;
                    hstruct.lesson = hs.lesson;
                    hstruct.Question = hs.Question;
                    hstruct.component = component;
                    hs.hints.add(hstruct);
                    token1 = true;
                  } else {
                    // Vector v = hs.hints;
                    for (int i = 0; i < hs.hints.size(); i++) {
                      HintStruct hsTemp = (HintStruct) hs.hints.get(i);
                      if (hsTemp.component == component) {
                        hstruct = hsTemp;
                        token1 = true;
                        break;
                      }
                    }

                    if (hstruct == null) {
                      hstruct = new HintStruct();
                      hstruct.studentID = hs.studentID;
                      hstruct.lesson = hs.lesson;
                      hstruct.Question = hs.Question;
                      hstruct.component = component;
                      hs.hints.add(hstruct);
                      token1 = true;

                    }

                  }

                }

              }
            }

            if (line.indexOf("Level:") != -1) {
              String level = line.substring(line.indexOf("Level:") + 7, line.indexOf("Level:") + 8);
              // int intLevel = Integer.parseInt(level);
              if (token1 && hstruct != null) {
                hstruct.hintLevel = level;
              } else {
                hs.setHintLevel(level);
              }

            }

            if (line.indexOf("Desc:") != -1) {
              String desc = line.substring(line.indexOf("Desc:") + 5);
              // int intLevel = Integer.parseInt(level);
              if (token1 && hstruct != null) {
                hstruct.setDesc(desc.trim());
                System.out.println("set hstruct.desc: " + hstruct.desc);
              } else {
                hs.setDesc(desc.trim());
                System.out.println("set hs.desc: " + hs.desc);
              }
              System.out.println("Desc:" + desc + "hs.desc: " + hs.desc);
            }
            // state = STATE_CORRETSENTENCE;
            // pw.println(hs.toString());
          }
          if (line.indexOf("I: Errors:") != -1) {
            // this means sentence contain wrong;
            state = STATE_CORRETSENTENCE;
            // token =true;
          } else if (line.indexOf("Flow: Usage: Lesson Ends") != -1 || line.indexOf("Flow: Usage: Lesson Quit") != -1) {
            state = STATE_NEW_LESSON;
          } else if (line.indexOf("Flow: Usage: Next Question") != -1) {
            state = STATE_QUESTION_END;
          } else if (line.indexOf("Usage: Given Answer:") != -1) {
            state = STATE_CORRETSENTENCE;
          } else if (line.indexOf("Flow: Usage: Example Skipped") != -1) {
            state = STATE_QUESTION_END;
          }
          break;
        case STATE_CORRETSENTENCE:
          if (line.indexOf("Res: Usage: Matched Answer:") != -1) {
            String strTargetSentence = line.substring(line.indexOf("Answer:") + 7);
            strTargetSentence = strTargetSentence.trim();
            // tsdm.setStrTargetSentence(strTargetSentence);
            if (hs.component >= 0) {
              hs.setTargetS(strTargetSentence);
              // System.out.println(hs.toString());
              hs.printHint(pw);
              // pw.print(hs.toString());
              token1 = false;
              hstruct = null;
              hs.clear();
            }

          } else if (line.indexOf("Flow: Usage: Lesson Ends") != -1 || line.indexOf("Flow: Usage: Lesson Quit") != -1) {
            state = STATE_NEW_LESSON;
          } else if (line.indexOf("Flow: Usage: Next Question") != -1) {
            state = STATE_QUESTION_END;
          }
          break;

        case STATE_QUESTION_END:
          if (line.indexOf("=NEXT QUESTION") != -1) {

            state = STATE_NEXT_QUESTION;
          }
          break;
      }
      line = br.readLine();
    }

    pw.close();

  }

  public void loadHintsFromLogFiles(String logfilepath, PrintWriter pw) throws ClassNotFoundException, IOException {
    // create table "correctSentences"
    // then get logfile path from configuration
    // Configuration configuration = new
    // Configuration("../../../JCALLConfig.xml");
    // logger.debug("opened config file: "+"JCALLConfig.xml");
    // String logfilepath = configuration.getItemValue("Data","TrialLogs");
    logger.debug("get log file path: " + logfilepath);
    File filePath = new File(logfilepath);
    if (!filePath.exists()) {
      logger.debug("goes wrong,does not exist log file in path: " + logfilepath);
      System.out.println("goes wrong,does not exist log file in path: " + logfilepath);
    } else {
      logger.debug(filePath.getName());
      // System.out.println(filePath.getName());
      File[] arrayFile = filePath.listFiles();
      for (int i = 0; i < arrayFile.length; i++) {
        File fileTemp = arrayFile[i];
        if (fileTemp.isFile()) {
          // load data one by one

          String file = fileTemp.getName() + "_hint.txt";
          // String fn = "./Data/hints/"+ file;
          loadHintsFromOneLogFile(fileTemp, pw);
        }
      }
    }

  }

  void loadHintsFromOneLogFile(File oneFile, PrintWriter pw) throws IOException {

    state = 0;
    String name = oneFile.getName();
    name = name.substring(0, name.indexOf("-"));
    int intStudent = Integer.parseInt(name);

    TextSentenceDataMeta tsdm = null;

    HintStruct hs = null;

    // FileManager.createFile(savingFilename);
    // pw = new PrintWriter(new FileWriter(savingFilename, true));
    br = new BufferedReader(new FileReader(oneFile));

    boolean token = false; // keep to remember if the sentence is wrong;
    int intTotalSentence = 0;
    HintStruct hstruct = null;
    String line;
    boolean token1 = false;
    line = br.readLine();
    while (line != null) {
      switch (state) {
        case STATE_NEW_SESSION:
          if (line.indexOf("Flow: Usage: Selected lesson") != -1) {
            hs = new HintStruct();
            hs.setStudentID(intStudent);
            token1 = false;
            hstruct = null;
            state = STATE_NEW_LESSON;
          }
          break;
        case STATE_NEW_LESSON:
          if (line.indexOf("Flow: Usage: Practice started, lesson") != -1) {
            String strLesson = line.substring(line.indexOf("lesson") + 7);
            strLesson = strLesson.trim();
            int intLesson = Integer.parseInt(strLesson) + 1;
            // strLesson = Integer.toString(intLesson);
            hs.setLesson(intLesson);
            // tsdm.setStrLesson(strLesson);

          } else if (line.indexOf("=NEXT QUESTION") != -1) {
            state = STATE_NEXT_QUESTION;
          }
          break;
        case STATE_NEXT_QUESTION:
          if (line.indexOf("Flow: Usage: Lesson") != -1) {
            // System.out.println("Line: "+ line);

            String strQuestion = line.substring(line.indexOf("Question") + 9).trim();

            if (strQuestion == null) {
              System.out.println("Question is null ");
            } else {
              // System.out.println("Question: "+ strQuestion);
              // tsdm.setStrQuestion(strQuestion);
              hs.setQuestion(strQuestion);
              state = STATE_HINT_DETAIL;
            }
          }
          break;
        case STATE_HINT_DETAIL:
          // Hnt: Usage:
          if (line.indexOf("Hnt: Usage:") != -1) {
            System.out.println("Hnt; Line: " + line);
            // the hint line, like: Hnt: Usage: Hint Selected (Free Choice)::
            // Component: 0, Level: 1, Cat: Subject, Desc: *****
            line = line.trim();
            if (line.indexOf("Component:") != -1) {
              String comp = line.substring(line.indexOf("Component:") + 11, line.indexOf("Component:") + 12);
              System.out.println("Component:" + comp);
              int component = Integer.parseInt(comp);
              if (hs.component < 0) {
                System.out.println("First using hint line");
                hs.setComponent(component);
                System.out.println("Hint comp: " + hs.getComponent());
              } else {
                System.out.println("Not First using hint line");
                if (hs.component == component) {
                  System.out.println("But Same Compoente");
                  // hs.setComponent(component);
                } else {
                  System.out.println("Diff Compoenent, add new hint stuct in hs");
                  if (hs.hints == null) {
                    hs.hints = new Vector();
                    hstruct = new HintStruct();
                    hstruct.studentID = hs.studentID;
                    hstruct.lesson = hs.lesson;
                    hstruct.Question = hs.Question;
                    hstruct.component = component;
                    hs.hints.add(hstruct);
                    token1 = true;
                  } else {
                    // Vector v = hs.hints;
                    for (int i = 0; i < hs.hints.size(); i++) {
                      HintStruct hsTemp = (HintStruct) hs.hints.get(i);
                      if (hsTemp.component == component) {
                        hstruct = hsTemp;
                        token1 = true;
                        break;
                      }
                    }

                    if (hstruct == null) {
                      hstruct = new HintStruct();
                      hstruct.studentID = hs.studentID;
                      hstruct.lesson = hs.lesson;
                      hstruct.Question = hs.Question;
                      hstruct.component = component;
                      hs.hints.add(hstruct);
                      token1 = true;

                    }

                  }

                }

              }
            }

            if (line.indexOf("Level:") != -1) {
              String level = line.substring(line.indexOf("Level:") + 7, line.indexOf("Level:") + 8);
              // int intLevel = Integer.parseInt(level);
              if (token1 && hstruct != null) {
                hstruct.hintLevel = level;
              } else {
                hs.setHintLevel(level);
              }

            }

            if (line.indexOf("Desc:") != -1) {
              String desc = line.substring(line.indexOf("Desc:") + 5);
              // int intLevel = Integer.parseInt(level);
              if (token1 && hstruct != null) {
                hstruct.setDesc(desc.trim());
                System.out.println("set hstruct.desc: " + hstruct.desc);
              } else {
                hs.setDesc(desc.trim());
                System.out.println("set hs.desc: " + hs.desc);
              }
              System.out.println("Desc:" + desc + "hs.desc: " + hs.desc);
            }
            // state = STATE_CORRETSENTENCE;
            // pw.println(hs.toString());
          }
          if (line.indexOf("I: Errors:") != -1) {
            // this means sentence contain wrong;
            state = STATE_CORRETSENTENCE;
            // token =true;
          } else if (line.indexOf("Flow: Usage: Lesson Ends") != -1 || line.indexOf("Flow: Usage: Lesson Quit") != -1) {
            state = STATE_NEW_LESSON;
          } else if (line.indexOf("Flow: Usage: Next Question") != -1) {
            state = STATE_QUESTION_END;
          } else if (line.indexOf("Usage: Given Answer:") != -1) {
            state = STATE_CORRETSENTENCE;
          } else if (line.indexOf("Flow: Usage: Example Skipped") != -1) {
            state = STATE_QUESTION_END;
          }
          break;
        case STATE_CORRETSENTENCE:
          if (line.indexOf("Res: Usage: Matched Answer:") != -1) {
            String strTargetSentence = line.substring(line.indexOf("Answer:") + 7);
            strTargetSentence = strTargetSentence.trim();
            // tsdm.setStrTargetSentence(strTargetSentence);
            if (hs.component >= 0) {
              hs.setTargetS(strTargetSentence);
              // System.out.println(hs.toString());
              hs.printHint(pw);
              // pw.print(hs.toString());
              token1 = false;
              hstruct = null;
              hs.clear();
            }

          } else if (line.indexOf("Flow: Usage: Lesson Ends") != -1 || line.indexOf("Flow: Usage: Lesson Quit") != -1) {
            state = STATE_NEW_LESSON;
          } else if (line.indexOf("Flow: Usage: Next Question") != -1) {
            state = STATE_QUESTION_END;
          }
          break;

        case STATE_QUESTION_END:
          if (line.indexOf("=NEXT QUESTION") != -1) {

            state = STATE_NEXT_QUESTION;
          }
          break;
      }
      line = br.readLine();
    }

  }

  public int[] scoreing_ex1(SentenceDataMeta sdm, Vector vHints, Vector vErrors) {
    // Vector hints, Vector errors,

    System.out.println("Target s: " + sdm.getStrTargetAnswer() + " And Observed s: " + sdm.getStrSpeechAnswer());

    int[] ret = { 0, 0, 0 };
    String strTar;
    String strRec;
    int score = 0; // 100
    int total = 0; // any
    int penalty = 0; // any
    int hintPelanlty = 0;
    int errorPenalty = 0;

    Vector hints = getHints(sdm, vHints);
    Vector errors = getErrors(sdm, vErrors);

    String TS = sdm.getStrTargetAnswer();
    // String Rec = sdm.getStrSpeechAnswer();
    StringTokenizer stTarget = new StringTokenizer(TS);
    // StringTokenizer stRecognization = new StringTokenizer(Rec);
    Vector<String> vTarget = new Vector<String>();

    while (stTarget.hasMoreTokens()) {
      String str = stTarget.nextToken();
      if (str != null && str.length() > 0) {
        vTarget.addElement(str);
      }
    }

    if (vTarget != null) {
      logger.debug("word size: " + vTarget.size());

      // System.out.println("word size: "+vTarget.size());

      for (int i = 0; i < vTarget.size(); i++) {
        strTar = (String) vTarget.elementAt(i);
        // strRec = (String) vRec.elementAt(i);
        // // not the special word
        // System.out.println(" Target word: "+ strTar);
        JCALL_Word call_word = l.getWordFmSurFormPRoma(strTar);
        int type = -1;
        if (call_word == null) {
          logger.error("Not Found Word in Lexicon: " + strTar);
          System.out.println("Warning: Not Found Word in Lexicon: " + strTar);
          if (i == vTarget.size() - 1) {
            type = JCALL_Lexicon.LEX_TYPE_VERB;
          } else if (i % 2 == 0) {
            type = JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL;
          } else {
            if (!sdm.getStrLesson().equalsIgnoreCase("21")) {
              type = JCALL_Lexicon.LEX_TYPE_NOUN;
            } else {
              System.out.println("Error: Can't Determine the word type now");
            }
          }

        } else {
          System.out.println("Find word: " + call_word.getStrKanji() + " type: " + call_word.getIntType());
          type = call_word.getIntType();

        }

        if (type > 0) {
          total = total + computeTotal(type);

          hintPelanlty = 0;
          if (hints != null && hints.size() > 0) {
            System.out.println("May Use hint: ");
            hintPelanlty = getHintPenalty(hints, strTar, type);

            System.out.println("hintPelanlty: " + hintPelanlty);

            penalty = penalty + hintPelanlty;
          } else {
            System.out.println("No hints");
          }
          errorPenalty = 0;
          if (errors != null && errors.size() > 0) {

            System.out.println("May Make Error: ");

            errorPenalty = getErrorPenalty(errors, strTar, type);

            System.out.println("ErrorPenalty: " + errorPenalty);

            if (hintPelanlty == 0) {
              penalty = penalty + errorPenalty;
            } else {
              int sum = errorPenalty + hintPelanlty;
              if (sum > computeTotal(type)) {
                penalty = penalty + computeTotal(type) - hintPelanlty;
              } else {
                penalty = penalty + errorPenalty;
              }
            }
          }
        } else {

          System.out.println("Type == 0 ");
        }

      }
    }

    System.out.println("StudentID: " + sdm.getIntStudentID() + " Lesson: " + sdm.getStrLesson() + " total: " + total
        + " Penalty: " + penalty + " Score: " + (total - penalty) * 100 / total);

    ret[0] = total;
    ret[1] = penalty;
    ret[2] = (total - penalty) * 100 / total;
    return ret;

  }

  public void getScores_ex1(Vector hints, Vector errors, Vector sentences, int[] studentIDs, String scorefile) {
    FileManager.createFile(scorefile);
    PrintWriter pw;
    try {
      pw = new PrintWriter(new FileWriter(scorefile, true));

      // Vector<Integer> sscore;
      int ss = 0; // compute total score for each student;
      for (int i = 0; i < studentIDs.length; i++) {
        int studentID = studentIDs[i];
        Vector v = getSentencesWithStudent(sentences, studentID);
        if (v != null && v.size() > 0) {
          ss = 0;
          System.out.println("size: " + v.size());
          for (int j = 0; j < v.size(); j++) {
            SentenceDataMeta sdm = (SentenceDataMeta) v.get(j);
            if (sdm != null) {

              int[] scores = scoreing_ex1(sdm, hints, errors);

              String strResult = "ID [" + sdm.getIntID() + "] ";
              strResult = strResult + "StudentID [" + sdm.getIntStudentID() + "] ";
              strResult = strResult + "Lesson [" + sdm.getStrLesson() + "] ";
              strResult = strResult + "Score: [" + scores[2] + "]";
              pw.println(strResult);
              ss = ss + scores[2];

            }
          }
          pw.println("=========Avarage score for student[" + studentID + "]=======" + (ss / v.size()));

          System.out.println("=========Avarage score for student[" + studentID + "]=======" + (ss / v.size()));

        }

      }
      pw.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  // in ErrorDB db
  // tablename: Sentences
  public Vector getSentencesFromDb_ex1(String tablename) {

    // System.out.println("Enter getSentencesFromDb_ex1, Table: "+ tablename);

    Vector vReturn = new Vector();
    SentenceDataMeta sdm;

    String strOdbc = FindConfig.getConfig().findStr(OldCallLogParser.FirstOdbcName);
    if (strOdbc == null) {
      strOdbc = "history";
    }
    // System.out.println("strOdbc: "+ strOdbc);

    DataManager dm = new DataManager();
    dm.connectToAccess(strOdbc);

    // dm.connectToAccess("history");

    try {

      // WordDataMeta objMeta;
      ResultSet tmpRs = dm.searchTable(tablename, "");
      while (tmpRs.next()) {
        sdm = new SentenceDataMeta();
        int id = tmpRs.getInt("ID");
        int studentID = tmpRs.getInt("StudentID");
        String lesson = tmpRs.getString("Lesson");
        String quesiton = tmpRs.getString("Question");
        String strTarget = tmpRs.getString("TargetSentence");
        String strRecognition = tmpRs.getString("InputSentence");
        String strSenError = tmpRs.getString("SentenceLevelError");
        sdm.setIntID(id);
        sdm.setIntStudentID(studentID);
        sdm.setStrLesson(lesson);
        sdm.setStrQuestion(quesiton);
        sdm.setStrTargetAnswer(strTarget);
        sdm.setStrSpeechAnswer(strRecognition);
        // sdm.setStrListeningAnswer(strListen);
        // sdm.setStrSentenceLevelError(strSenError);
        vReturn.add(sdm);

      }// end while

    } catch (Exception e) {
      System.out.println("Exception in getSentencesFromDb_ex1" + e.getMessage());
      e.printStackTrace();
    }

    dm.releaseConn();

    return vReturn;
  }

  // tablename: WordErrTable (OldCallLogParser.WordTableName)
  public Vector getErrorsFromDb_ex1(String tablename) {

    Vector vReturn = new Vector();
    WordDataMeta wdm;

    String strOdbc = FindConfig.getConfig().findStr(OldCallLogParser.FirstOdbcName);
    DataManager dm = new DataManager();
    dm.connectToAccess(strOdbc);

    // dm.connectToAccess("history");

    Vector<WordDataMeta> v = new Vector<WordDataMeta>();
    // strwordTableName
    // read data into WordErrDataMeta one by one, change attribute on the basis
    // of its type
    try {

      // WordDataMeta objMeta;
      ResultSet tmpRs = dm.searchTable(tablename, "");
      while (tmpRs.next()) {
        wdm = new WordDataMeta();
        int id = tmpRs.getInt("ID");
        int studentID = tmpRs.getInt("StudentID");
        String lesson = tmpRs.getString("Lesson");
        String quesiton = tmpRs.getString("Question");

        String strObserve = tmpRs.getString("ObservedWord");
        String strTarget = tmpRs.getString("TargetWord");
        String strTargetType = tmpRs.getString("TargetType");

        String strError = tmpRs.getString("SpecificType");

        wdm.setIntID(id);
        wdm.setIntStudentID(studentID);
        wdm.setStrLesson(lesson);
        wdm.setStrQuestion(quesiton);
        wdm.setStrTargetWord(strTarget);
        wdm.setStrObservedWord(strObserve);
        wdm.setStrTargetType(strTargetType);
        wdm.setStrSpecificType(strError);

        // sdm.setStrListeningAnswer(strListen);
        // sdm.setStrSentenceLevelError(strSenError);
        vReturn.add(wdm);

      }// end while

    } catch (Exception e) {
      System.out.println("Exception in getSentencesFromDb_ex1" + e.getMessage());
      e.printStackTrace();
    }

    dm.releaseConn();

    return vReturn;
  }

  public Vector getErrors(SentenceDataMeta sdm, Vector vErrors) {
    // System.out.println("get hints for s: "+ sdm.getStrTargetAnswer());

    Vector<WordDataMeta> vResult = new Vector<WordDataMeta>();

    // System.out.println("vErrors size: "+ vErrors.size());

    for (int i = 0; i < vErrors.size(); i++) {

      WordDataMeta meta = (WordDataMeta) vErrors.get(i);

      // System.out.println(meta.toString4print());

      String metaLesson = meta.strLesson;

      if (metaLesson.equalsIgnoreCase(sdm.getStrLesson()) && meta.getIntStudentID() == sdm.getIntStudentID()
          && meta.getStrQuestion().equalsIgnoreCase(sdm.getStrQuestion())) {
        // System.out.println("lesson and studentID is the same, HintStruct's TargetS: "+
        // hs.TargetS );
        // if(meta.TargetS.equalsIgnoreCase(sdm.getStrTargetAnswer())){
        vResult.addElement(meta);
        // }
      }
    }
    return vResult;

  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

    ComputeScore score = new ComputeScore();

    /*
     * String scorefile = "./Data/ex1scores.txt"; Vector hints =
     * score.loadHints(Ex1HintFile); Vector errors =
     * score.getErrorsFromDb_ex1(OldCallLogParser.WordTableName); Vector ss =
     * score.getSentencesFromDb_ex1(OldCallLogParser.SentencesTable); // int[]
     * studentIDs = {2,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21}; //
     * int[] studentIDs = {11}; int[] studentIDs =
     * {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21}; //8 is over
     * 
     * // int[] studentIDs = {14,15,16,17,18,19,20,21}; //8,11,17,12,13,
     * score.getScores_ex1(hints, errors,ss, studentIDs, scorefile);
     */

    /*
     * Vector hints = score.loadHints(HintFile); Vector errors =
     * score.getErrorsFromDb_ex1(OldCallLogParser.WordTableName);
     * 
     * SentenceDataMeta sdm = new SentenceDataMeta(); // sdm.setIntID(1); //
     * sdm.setIntStudentID(1); // sdm.setStrLesson("1"); //
     * sdm.setStrQuestion("1"); // sdm.setStrTargetAnswer("おじいさん も おかねもち desu");
     * // sdm.setStrSpeechAnswer("watashi no ojiisan wa okanemochi hitho desu");
     * 
     * sdm.setIntID(117); sdm.setIntStudentID(11); sdm.setStrLesson("21");
     * sdm.setStrQuestion("6"); sdm.setStrTargetAnswer("サム は イチゴ を ふたつ かいます");
     * sdm.setStrSpeechAnswer("サム は  を ふたつ ください");
     * 
     * 
     * score.scoreing_ex1(sdm, hints, errors);
     */

    /*
     * 
     * Vector vec = score.getErrorsFromDb_ex1(OldCallLogParser.WordTableName);
     * if(vec!=null && vec.size()>0){ System.out.println("size: "+ vec.size());
     * for (int j = 0; j < vec.size(); j++) { WordDataMeta sdm =
     * (WordDataMeta)vec.get(j);
     * 
     * System.out.println("Number: "+ j + "===="+ sdm.toString4print());
     * 
     * 
     * } }
     */

    /*
     * String log = "./Data/Trial Logs"; try { score.loadHintsFromLogFiles(log);
     * } catch (ClassNotFoundException e) { // TODO Auto-generated catch block
     * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
     * catch block e.printStackTrace(); }
     */

    /*
     * String savingFilename = "./Data/hints/new.txt"; String log =
     * "./Data/Trial Logs"; try { FileManager.createFile(savingFilename);
     * PrintWriter pw = new PrintWriter(new FileWriter(savingFilename, true));
     * score.loadHintsFromLogFiles(log,pw); pw.close(); } catch
     * (ClassNotFoundException e) { // TODO Auto-generated catch block
     * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
     * catch block e.printStackTrace(); }
     */

    /*
     * String hFileName = "./Data/Trial Logs/2-lee-INT2.txt"; String savefile =
     * "./Data/hints/new.txt"; File f = new File(hFileName); if(f.exists()){ try
     * { score.loadHintsFromOneLogFile(f, savefile); } catch (IOException e) {
     * // TODO Auto-generated catch block e.printStackTrace(); }
     * 
     * }
     */

    /*
     * String file = "./data/firstexprimenthints.txt"; // Vector v =
     * score.loadHints(HintFile); Vector v = score.loadHints(file); if(v!=null
     * && v.size()>0){ System.out.println("size: "+ v.size()); for (int i = 0; i
     * < v.size(); i++) { HintStruct hs = (HintStruct)v.get(i);
     * 
     * System.out.println("Target Word: "+ hs.TargetW +" Comp: "+ hs.component);
     * }
     * 
     * }
     */

    // /*
    int wCount = 0;
    Vector vec = score.getSentencesFromDb(NewLogParser.SpeakSentencesTable3);
    if (vec != null && vec.size() > 0) {
      System.out.println("sentence size: " + vec.size());
      for (int j = 0; j < vec.size(); j++) {
        SentenceDataMeta sdm = (SentenceDataMeta) vec.get(j);
        if (sdm != null) {
          String targetS = sdm.getStrTargetAnswer();
          if (targetS != null && targetS.length() != 0) {
            StringTokenizer st = new StringTokenizer(targetS);
            wCount = wCount + st.countTokens();
          }

        } else {
          System.out.println("SentenceDataMeta[" + j + "] is null");
        }

      }

      System.out.println("word size : " + wCount);
    }
    // */

    /*
     * 
     * Vector vec =
     * score.getSentencesFromDb_ex1(OldCallLogParser.SentencesTable);
     * if(vec!=null && vec.size()>0){ System.out.println("size: "+ vec.size());
     * for (int j = 0; j < vec.size(); j++) { SentenceDataMeta sdm =
     * (SentenceDataMeta)vec.get(j);
     * 
     * System.out.println("Sentence: "+ sdm.getIntID()); }
     * 
     * }
     */

    /*
     * Vector hints = score.loadHints(HintFile); SentenceDataMeta sdm = new
     * SentenceDataMeta(); // sdm.setIntID(37); // sdm.setIntStudentID(1); //
     * sdm.setStrLesson("6"); // sdm.setStrTargetAnswer("ワイン を のみましょうか"); //
     * sdm.setStrSpeechAnswer("ワイン を のみます");
     * 
     * // sdm.setIntID(117); // sdm.setIntStudentID(8); //
     * sdm.setStrLesson("2"); // sdm.setStrTargetAnswer("わたし は あるきません"); //
     * sdm.setStrSpeechAnswer("わたし は あるます");
     * 
     * // sdm.setIntID(126); // sdm.setIntStudentID(8); //
     * sdm.setStrLesson("22"); //
     * sdm.setStrTargetAnswer("いまにし は おばあさん に はいざら を くれます"); //
     * sdm.setStrSpeechAnswer("いまにしさん は おじいさん に はいざら を くれました");
     * 
     * // sdm.setIntID(22); // sdm.setIntStudentID(11); //
     * sdm.setStrLesson("6"); // sdm.setStrTargetAnswer("しょうがっこう に いきましょうか"); //
     * sdm.setStrSpeechAnswer("しょうがこう に いきましょうか");
     * 
     * // sdm.setIntID(22); // sdm.setIntStudentID(11); //
     * sdm.setStrLesson("22"); // sdm.setStrTargetAnswer("いまにし に かびん を もらいました");
     * // sdm.setStrSpeechAnswer("いまにしさん から かびん を もらいましたか");
     * 
     * sdm.setIntID(93); sdm.setIntStudentID(6); sdm.setStrLesson("7");
     * sdm.setStrTargetAnswer("サム に おこさないでください");
     * sdm.setStrSpeechAnswer("サム に おきないでください");
     * 
     * 
     * // score.scoreing(sdm, hints); score.scoreing_plusDebug(sdm, hints);
     */

    /*
     * String scorefile = "./Data/scores.txt"; Vector hints =
     * score.loadHints(HintFile); Vector ss =
     * score.getSentencesFromDb(NewLogParser.SpeakSentencesTable3); int[]
     * studentIDs = {1,3,4,5,6,7,8,9,10,11}; // int[] studentIDs = {1}; // int[]
     * studentIDs = {1,8}; score.getScores(hints, ss, studentIDs, scorefile);
     */
    /*
     * String strTar ="リンゴ";
     * 
     * JCALL_Word call_word
     * =JCALL_Lexicon.getInstance().getWordFmSurForm(strTar);
     * if(call_word==null){ //
     * logger.error("Not Found Word in Lexicon: "+strTar);
     * System.out.println("Not Found Word in Lexicon: "+strTar); //??????
     * 
     * }else { System.out.println("Found Word in Lexicon: "+strTar); }
     */

    /*
     * int component = 1; HintStruct hs = new HintStruct(); hs.studentID= 1;
     * hs.lesson = 2; hs.Question = "3"; hs.component =2; HintStruct hstruct
     * =null; if(hs.component == component){ hs.setComponent(component); }else{
     * if(hs.hints == null){ hs.hints = new Vector(); hstruct = new
     * HintStruct(); hstruct.studentID= hs.studentID; hstruct.lesson =
     * hs.lesson; hstruct.Question = hs.Question; hstruct.component = component;
     * hs.hints.add(hstruct); } } hstruct.TargetW ="wa ta shi"; hstruct = null;
     * for (int i = 0; i < hs.hints.size(); i++) { HintStruct hsTempt =
     * (HintStruct)hs.hints.get(i); System.out.println("target word: "+
     * hsTempt.TargetW); }
     */
  }

}
