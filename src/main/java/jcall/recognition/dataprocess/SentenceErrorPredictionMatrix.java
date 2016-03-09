/**
 * Created on 2007/06/03
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;

import jcall.recognition.database.DataManager;
import jcall.recognition.database.WordErrDataMeta;
import jcall.util.FileManager;

import org.apache.log4j.Logger;

public class SentenceErrorPredictionMatrix {
  static Logger logger = Logger.getLogger(SentenceErrorPredictionMatrix.class.getName());

  static final String PREDICTIONDATA = "./Data/PredictionData.txt";
  static final String ERRSTATISTICS = "./Data/error.txt";
  static final String[] NOUN_OLDTYPE = { "WRONGFORM", "SUBS1", "SUBS2", "SUBS3", "SUBS4", "SUBS5", "NTDERIV", "ERRW1",
      "ERRW2", "ERRW3_Ge", "ERRW3_ONEEDIT", "SUBS1-ERRW1", "SUBS1-ERRW4" };// 13
  static final String[] NOUN_NEWTYPE = { "VDG_STYLE", "VDG", "VDG_ELABEL", "VDOUT", "VDOUT", "VOTHER", "INVS_WFORM",
      "INVS_PCE_JONEEDIT", "INVOUT_J", "INVOUT_E", "INVS_PCE_EONEEDIT", "INVDG_PCE_JONEEDIT", "INVDG_WFORM" };// 13
  static final String[] VERB_OLDTYPE = { "WRONGFORM", "SUBS1_VSFORM", "SUBS1_VDFORM", "SUBS4", "SUBS5", "INVALIDFORM",
      "INVALIDFORM_WRONGW", "ERRW2" };// 8
  static final String[] VERB_NEWTYPE = { "VS_DFORM", "VDG_SFORM", "VDG_DFORM", "VDOUT", "VOTHER", "INVS_REF",
      "INVDOUT", "INVDOUT" };// 8
  static final String[] PARTICAL_OLDTYPE = { "SUBS1_ALT1", "SUBS1_ALT2", "SUBS1_ALT3", "VDOUT" };
  static final String[] PARTICAL_NEWTYPE = { "VDG_ALT1", "VDG_ALT2", "VDG_ALT3", "VDOUT" };
  static final String[] DIGIT_OLDTYPE = { "QUANTITY", "QUANTITY2", "SUBS1", "SUBS1_ONEEDIT", "INVALIDFORM_ERRW1",
      "WRONGNUMBER", "OTHER" };// 7
  static final String[] DIGIT_NEWTYPE = { "QUANTITY", "QUANTITY2", "VDG", "INVDG_PCE", "INVS_PCE", "VDOUT_WNUM",
      "INVOUT" };// 7

  static final String[] ERROE_PATTERN = { "SemanticConfusion", "DifferentForm", "WrongForm", "AddLong", "AddVoiced",
      "AddShort", "OmitLong", "OmitShort", "OmitVoiced" };

  // static final String[] NOUN_NEWTYPE
  // ={"VDG_STYLE","VDG","VDG_ELABEL","VDOUT","VOTHER","INVS_WFORM","INVS_PCE_JONEEDIT","INVOUT_J","INVOUT_E","INVS_PCE_EONEEDIT","INVDG_PCE_JONEEDIT","INVDG_WFORM"};//12
  // static final String[] VERB_NEWTYPE
  // ={"VS_DFORM","VDG_SFORM","VDG_DFORM","VDOUT","VOTHER","INVALIDFORM","INVDOUT","INVDG_REF"};//8
  // static final String[] PARTICAL_NEWTYPE
  // ={"VDG_ALT1","VDG_ALT2","VDG_ALT3","VDOUT"};//4
  // static final String[] DIGIT_NEWTYPE
  // ={"QUANTITY","QUANTITY2","VDG","INVDG_PCE","INVS_PCE","VDOUT_WNUM","INVOUT"};//7

  // different form of lesson define Vs different form out of lesson definition;
  PrintWriter pw;
  ErrorPredictionProcess errorpredictionprocess;
  SentenceStatisticStructure sss;
  LexiconProcess lprocess;

  public SentenceErrorPredictionMatrix() throws IOException {
    errorpredictionprocess = new ErrorPredictionProcess();
    sss = new SentenceStatisticStructure();
    lprocess = new LexiconProcess();
  }

  public void predictionOfSentencesOld(String strPath) throws IOException, SQLException {

    FileManager.createFile(strPath);
    pw = new PrintWriter(new FileWriter(strPath, true));

    DataManager dm = new DataManager();
    dm.connectToAccess("history");
    StringTokenizer st;
    String sql = "SELECT * FROM Sentences";
    ResultSet rs = dm.executeQuery(sql);
    String strWord = "";
    boolean booVerb = false;
    int intWordType = -1;
    while (rs.next()) {
      int intId = rs.getInt("ID");
      int intLesson = rs.getInt("Lesson");
      String strTargetSentence = rs.getString("TargetSentence");

      pw.println(strTargetSentence + "---ID=" + intId);
      System.out.println("ID--" + intId + "lesson" + intLesson + "TS is " + strTargetSentence);
      // split the sentence into components
      st = new StringTokenizer(strTargetSentence);
      while (st.hasMoreTokens()) {
        strWord = st.nextToken();
        // First, get word type;
        if (st.hasMoreTokens()) {
          booVerb = false;
          int intPrediction = errorpredictionprocess.getWordPrediction(strWord, intLesson, booVerb);
          pw.write(intPrediction + " ");
        } else {
          booVerb = true;
          int intPrediction = errorpredictionprocess.getWordPrediction(strWord, intLesson, booVerb);
          pw.write(intPrediction + " ");
          pw.println();
        }
        // int intPrediction = ErrorPredictionProcess.getWordPrediction(strWord,
        // intLesson, booVerb);
        // pw.write(intPrediction +" ");
      }

      // System.out.println(st.nextToken());
    }
    pw.close();
  }

  public void predictionOfSentences(String strPath) throws IOException, SQLException {

    FileManager.createFile(strPath);
    pw = new PrintWriter(new FileWriter(strPath, true));

    DataManager dm = new DataManager();
    dm.connectToAccess("history");
    StringTokenizer st;
    String sql = "SELECT * FROM Sentences";
    ResultSet rs = dm.executeQuery(sql);
    String strWord = "";
    boolean booVerb = false;
    boolean booParticle = false;
    int intIndex = 0; // words' index of one sentence'
    int intTotle = 0;
    int allNumber = 0;// number of all of the words;
    int noun = 0;
    int particle = 0;
    int NQ = 0;
    int verb = 0;
    int errorWord = 0;
    double doubleE; // expectation words of this sentence
    double doubleETotal = 0.0;
    while (rs.next()) {
      int intId = rs.getInt("ID");
      int intLesson = rs.getInt("Lesson");
      String strTargetSentence = rs.getString("TargetSentence");

      pw.println(strTargetSentence + "---ID=" + intId);
      System.out.println("ID--" + intId + "lesson" + intLesson + "TS is " + strTargetSentence);
      // split the sentence into components
      st = new StringTokenizer(strTargetSentence);
      intIndex = 0;
      intTotle = 0;
      while (st.hasMoreTokens()) {
        intIndex++;
        strWord = st.nextToken();
        strWord = strWord.trim();
        // First, get word type;
        if (st.hasMoreTokens()) {
          booVerb = false;
          if (intIndex % 2 == 0) {// count particle
            booParticle = true;
            particle++;
            int intPrediction = errorpredictionprocess.getWordPrediction(strWord, intLesson, booVerb, booParticle);
            // int intPrediction =
            // errorpredictionprocess.getWordPrediction(strWord, intLesson,
            // booVerb);
            intTotle += intPrediction;
            pw.write(intPrediction + " ");
          } else {// noun+NQ
            booParticle = false;
            LexiconWordMeta lwm = ErrorPredictionProcess.getLexiconWordNoun(strWord);
            if (lwm != null) { // type = noun
              int intPrediction = ErrorPredictionProcess.getNounErrorNumber(lwm, intLesson);
              noun++;
              intTotle += intPrediction;
              pw.write(intPrediction + " ");
            } else { // NQ
              QuantifierNode qn = ErrorPredictionProcess.getQN(strWord);
              if (qn != null) {// type = QN
                int intPrediction = ErrorPredictionProcess.getNQErrorNumber(qn.getStrKana().trim(), qn, intLesson);
                NQ++;
                intTotle += intPrediction;
                pw.write(intPrediction + " ");
              } else {
                // System.out.println("----"+strWord);
                if (strWord.equalsIgnoreCase("�����Ԃ�")) {
                  // System.out.println("--equal to-�����Ԃ�-");
                  // int test =
                  // ErrorPredictionProcess.getWordPrediction("�앶",28,false,false);
                  noun++;
                  intTotle += 5;
                  pw.write(5 + " ");
                  logger.info("[" + strWord + "]" + 5);
                } else if (strWord.equalsIgnoreCase("�ڂ���")) {
                  // System.out.println("--equal to-�����Ԃ�-");
                  // int intPrediction =
                  // ErrorPredictionProcess.getWordPrediction("�X�q",28,false,false);
                  noun++;
                  intTotle += 3;
                  pw.write(3 + " ");
                  logger.info("[" + strWord + "]" + 3);
                } else if (strWord.equalsIgnoreCase("�낤����")) {
                  // System.out.println("--equal to-�����Ԃ�-");
                  // int intPrediction =
                  // ErrorPredictionProcess.getWordPrediction("�낤����",28,false,false);
                  noun++;
                  intTotle += 5;
                  pw.write(5 + " ");
                  logger.info("[" + strWord + "]" + 5);
                } else {
                  // int test =
                  // ErrorPredictionProcess.getWordPrediction(strWord,28,false,false);
                  errorWord++;
                  logger.info("sth wrong , the target word is not active or not exists in our data base ---[" + strWord
                      + "]");

                }// System.out.println("sth wrong , the target word is not active or not exists in our data base ---["+
                 // strWord +"]");
              }
            }
          }

        } else {
          booVerb = true;
          verb++;
          int intPrediction = errorpredictionprocess.getWordPrediction(strWord, intLesson, booVerb, booParticle);
          intTotle += intPrediction;
          pw.write(intPrediction + " ");

        }
      }// end of while st
      intTotle += intIndex;
      allNumber += intIndex;
      doubleE = (double) intTotle / intIndex;
      doubleETotal += doubleE;
      pw.write("-----" + doubleE);
      pw.println();
    }
    pw.write("-----" + doubleETotal / 475);
    pw.close();
    System.out.println("all words appears" + allNumber + "--noun is" + noun + "--particle is" + particle + "--NQ is "
        + NQ + "--verb" + verb + "--errorWord" + errorWord);
  }

  public void predictionOfSentencesNew(String strPath) throws IOException, SQLException {

    FileManager.createFile(strPath);
    pw = new PrintWriter(new FileWriter(strPath, true));
    DataManager dm = new DataManager();
    dm.connectToAccess("history");
    StringTokenizer st;
    String sql = "SELECT * FROM Sentences";
    ResultSet rs = dm.executeQuery(sql);
    String strWord = "";
    boolean booVerb = false;
    boolean booParticle = false;
    int intIndex = 0; // words' index of one sentence'
    int intTotle = 0;
    int allNumber = 0;// number of all of the words;
    int noun = 0;
    int particle = 0;
    int NQ = 0;
    int verb = 0;
    int errorWord = 0;
    double doubleE; // expectation words of this sentence
    double doubleETotal = 0.0;
    while (rs.next()) {
      int intId = rs.getInt("ID");
      int intLesson = rs.getInt("Lesson");
      String strTargetSentence = rs.getString("TargetSentence");

      pw.println(strTargetSentence + "---ID=" + intId);
      System.out.println("ID--" + intId + "lesson" + intLesson + "TS is " + strTargetSentence);
      // split the sentence into components
      st = new StringTokenizer(strTargetSentence);
      intIndex = 0;
      intTotle = 0;
      while (st.hasMoreTokens()) {
        intIndex++;
        strWord = st.nextToken();
        strWord = strWord.trim();
        // First, get word type;
        if (st.hasMoreTokens()) {
          booVerb = false;
          if (intIndex % 2 == 0) {// count particle
            booParticle = true;
            particle++;
            int intPrediction = errorpredictionprocess.getWordPrediction(strWord, intLesson, booVerb, booParticle);
            // int intPrediction =
            // errorpredictionprocess.getWordPrediction(strWord, intLesson,
            // booVerb);
            intTotle += intPrediction;
            pw.write(intPrediction + " ");
          } else {// noun+NQ
            booParticle = false;
            LexiconWordMeta lwm = ErrorPredictionProcess.getLexiconWordNoun(strWord);
            if (lwm != null) { // type = noun
              int intPrediction = ErrorPredictionProcess.getNounErrorNumber(lwm, intLesson);
              noun++;
              intTotle += intPrediction;
              pw.write(intPrediction + " ");
            } else { // NQ
              QuantifierNode qn = ErrorPredictionProcess.getQN(strWord);
              if (qn != null) {// type = QN
                int intPrediction = ErrorPredictionProcess.getNQErrorNumber(qn.getStrKana().trim(), qn, intLesson);
                NQ++;
                intTotle += intPrediction;
                pw.write(intPrediction + " ");
              } else {
                // System.out.println("----"+strWord);
                if (strWord.equalsIgnoreCase("�����Ԃ�")) {
                  // System.out.println("--equal to-�����Ԃ�-");
                  // int test =
                  // ErrorPredictionProcess.getWordPrediction("�앶",28,false,false);
                  noun++;
                  intTotle += 5;
                  pw.write(5 + " ");
                  logger.info("[" + strWord + "]" + 5);
                } else if (strWord.equalsIgnoreCase("�ڂ���")) {
                  // System.out.println("--equal to-�����Ԃ�-");
                  // int intPrediction =
                  // ErrorPredictionProcess.getWordPrediction("�X�q",28,false,false);
                  noun++;
                  intTotle += 3;
                  pw.write(3 + " ");
                  logger.info("[" + strWord + "]" + 3);
                } else if (strWord.equalsIgnoreCase("�낤����")) {
                  // System.out.println("--equal to-�����Ԃ�-");
                  // int intPrediction =
                  // ErrorPredictionProcess.getWordPrediction("�낤����",28,false,false);
                  noun++;
                  intTotle += 5;
                  pw.write(5 + " ");
                  logger.info("[" + strWord + "]" + 5);
                } else {
                  // int test =
                  // ErrorPredictionProcess.getWordPrediction(strWord,28,false,false);
                  errorWord++;
                  logger.info("sth wrong , the target word is not active or not exists in our data base ---[" + strWord
                      + "]");

                }// System.out.println("sth wrong , the target word is not active or not exists in our data base ---["+
                 // strWord +"]");
              }
            }
          }

        } else {
          booVerb = true;
          verb++;
          int intPrediction = errorpredictionprocess.getWordPrediction(strWord, intLesson, booVerb, booParticle);
          intTotle += intPrediction;
          pw.write(intPrediction + " ");

        }
      }// end of while st
      intTotle += intIndex;
      allNumber += intIndex;
      doubleE = (double) intTotle / intIndex;
      doubleETotal += doubleE;
      pw.write("-----" + doubleE);
      pw.println();
    }
    pw.write("-----" + doubleETotal / 475);
    pw.close();
    System.out.println("all words appears" + allNumber + "--noun is" + noun + "--particle is" + particle + "--NQ is "
        + NQ + "--verb" + verb + "--errorWord" + errorWord);
  }

  public void predictionOfSentencesNewVector(String strPath) throws IOException, SQLException {

    FileManager.createFile(strPath);
    pw = new PrintWriter(new FileWriter(strPath, true));
    DataManager dm = new DataManager();
    dm.connectToAccess("history");
    StringTokenizer st;
    String sql = "SELECT * FROM AllSentences";
    ResultSet rs = dm.executeQuery(sql);
    String strWord = "";
    boolean booVerb = false;
    boolean booParticle = false;
    int intIndex = 0; // words' index of one sentence'
    int intTotle = 0;
    int allNumber = 0;// number of all of the words;
    int noun = 0;
    int particle = 0;
    int NQ = 0;
    int verb = 0;
    int errorWord = 0;
    LexiconWordMeta lwm;
    double doubleE; // expectation words of this sentence
    double doubleETotal = 0.0;
    while (rs.next()) {
      int intId = rs.getInt("ID");
      int intLesson = rs.getInt("Lesson");
      String strTargetSentence = rs.getString("TargetSentence");

      pw.println(strTargetSentence + "---ID=" + intId);
      System.out.println("ID--" + intId + "lesson" + intLesson + "TS is " + strTargetSentence);
      // split the sentence into components
      st = new StringTokenizer(strTargetSentence);
      intIndex = 0;
      intTotle = 0;
      while (st.hasMoreTokens()) {
        intIndex++;
        strWord = st.nextToken();
        strWord = strWord.trim();
        // First, get word type;
        if (st.hasMoreTokens()) {
          booVerb = false;
          if (intIndex % 2 == 0) {// count particle
            booParticle = true;
            // particle++;
            lwm = lprocess.getLexiconWordParticle(strWord);
            if (lwm != null) {
              particle++;
              int intPrediction = errorpredictionprocess.getWordPrediction(strWord, intLesson, booVerb, booParticle,
                  sss);
              // int intPrediction =
              // errorpredictionprocess.getWordPrediction(strWord, intLesson,
              // booVerb);
              intTotle += intPrediction;
              pw.write(intPrediction + " ");
            } else {
              errorWord++;
              logger.error("wrong,target particle not exist in db ---[" + strWord + "]" + "in sentence " + intId
                  + " in lesson " + intLesson);
            }
            // int intPrediction =
            // errorpredictionprocess.getWordPrediction(strWord, intLesson,
            // booVerb,booParticle,sss);
            // int intPrediction =
            // errorpredictionprocess.getWordPrediction(strWord, intLesson,
            // booVerb);
            // intTotle +=intPrediction;
            // pw.write(intPrediction +" ");
          } else {// noun+NQ
            booParticle = false;
            lwm = lprocess.getNQ(strWord);
            if (lwm != null) {// type = QN
              // ok QN count is added
              NQ++;
              QuantifierNode qn = ErrorPredictionProcess.getQN(lwm.getStrKana());
              //
              int intPrediction = ErrorPredictionProcess.getNQErrorNumber(qn.getStrKana().trim(), qn, intLesson, sss);
              // NQ++;
              intTotle += intPrediction;
              pw.write(intPrediction + " ");

            }
            // LexiconWordMeta lwm
            // =ErrorPredictionProcess.getLexiconWordNoun(strWord);
            // if(lwm!=null){ //type = noun
            // int intPrediction =
            // ErrorPredictionProcess.getNounErrorNumber(lwm,intLesson,sss);
            // noun++;
            // intTotle +=intPrediction;
            // pw.write(intPrediction +" ");
            // }
            /*
             * else{ //NQ QuantifierNode qn =
             * ErrorPredictionProcess.getQN(strWord); if(qn!=null){//type = QN
             * int intPrediction =
             * ErrorPredictionProcess.getNQErrorNumber(qn.getStrKana
             * ().trim(),qn,intLesson,sss); NQ++; intTotle +=intPrediction;
             * pw.write(intPrediction +" "); }else{
             * //System.out.println("----"+strWord);
             * if(strWord.equalsIgnoreCase("�����Ԃ�")){
             * //System.out.println("--equal to-�����Ԃ�-"); //int test =
             * ErrorPredictionProcess.getWordPrediction("�앶",28,false,false);
             * noun++; intTotle +=5; pw.write(5 +" "); sss.addNoun("VDG", 2);
             * sss.addNoun("INVDG_PCE", 3); //logger.info("["+ strWord +"]"+5);
             * }else if(strWord.equalsIgnoreCase("�ڂ���")){
             * //System.out.println("--equal to-�����Ԃ�-"); //int intPrediction
             * =
             * ErrorPredictionProcess.getWordPrediction("�X�q",28,false,false);
             * noun++; intTotle +=3; pw.write(3 +" "); sss.addNoun("INVS_PCE",
             * 1); sss.addNoun("VDG", 1); sss.addNoun("INVDG_PCE", 1);
             * //logger.info("["+ strWord +"]"+3); }else
             * if(strWord.equalsIgnoreCase("�낤����")){
             * //System.out.println("--equal to-�����Ԃ�-"); //int intPrediction
             * =
             * ErrorPredictionProcess.getWordPrediction("�낤����",28,false,false
             * ); noun++; intTotle +=5; pw.write(5 +" ");
             * sss.addNoun("INVS_PCE", 2); sss.addNoun("VDG", 1);
             * sss.addNoun("INVDG_PCE", 2); //logger.info("["+ strWord +"]"+5);
             * }else{ //int test =
             * ErrorPredictionProcess.getWordPrediction(strWord,28,false,false);
             * errorWord++; logger.info(
             * "sth wrong , the target word is not active or not exists in our data base ---["
             * + strWord +"]");
             * 
             * }//System.out.println(
             * "sth wrong , the target word is not active or not exists in our data base ---["
             * + strWord +"]"); }
             * 
             * } }
             * 
             * }else{ booVerb = true; verb++; int intPrediction =
             * errorpredictionprocess.getWordPrediction(strWord, intLesson,
             * booVerb,booParticle,sss); intTotle +=intPrediction;
             * pw.write(intPrediction +" ");
             * 
             * }
             */
          }
        }
      }// end of while st
      intTotle += intIndex;
      allNumber += intIndex;
      doubleE = (double) intTotle / intIndex;
      doubleETotal += doubleE;
      pw.write("-----" + doubleE);
      pw.println();
    }
    pw.write("-----" + doubleETotal / 475);
    // getWordErrorStatistics(sss);
    sss.writePredictNum(pw);
    pw.close();
    System.out.println("all words appears" + allNumber + "--noun is" + noun + "--particle is" + particle + "--NQ is "
        + NQ + "--verb" + verb + "--errorWord" + errorWord);
  }

  public void getWordErrorStatistics(SentenceStatisticStructure sss) throws IOException, SQLException {

    DataManager dm = new DataManager();
    dm.connectToAccess("history");
    // FileManager.createFile(strPath);
    // pw = new PrintWriter(new FileWriter(strPath, true));
    String sql = "SELECT * FROM WordErrTable";
    ResultSet tmpRs = dm.executeQuery(sql);
    while (tmpRs.next()) {
      // int intID = tmpRs.getInt("ID");
      String strTargetType = tmpRs.getString("TargetType").trim();
      String strSpecificType = tmpRs.getString("SpecificType").trim();
      // NOUN
      if (strTargetType.equalsIgnoreCase("NOUN") || strTargetType.equalsIgnoreCase("DEFINITIVE")
          || strTargetType.equalsIgnoreCase("Misc") || strTargetType.equalsIgnoreCase("LOCATION")) {
        for (int i = 0; i < NOUN_NEWTYPE.length; i++) {
          if (strSpecificType.equalsIgnoreCase(NOUN_NEWTYPE[i]) || NOUN_NEWTYPE[i].startsWith(strSpecificType)) {
            sss.addNounErr(i, 1);
            break;

          }
        }

      } else if (strTargetType.equalsIgnoreCase("VERB")) {
        // for (int i = 0; i < NOUN_OLDTYPE.length; i++) {
        for (int i = 0; i < VERB_NEWTYPE.length; i++) {
          if (strSpecificType.equalsIgnoreCase(VERB_NEWTYPE[i])) {
            // verb[i]++;
            sss.addVerbErr(i, 1);
            break;
          }
        }
      } else if (strTargetType.equalsIgnoreCase("PARTICLE")) {
        for (int i = 0; i < PARTICAL_NEWTYPE.length; i++) {
          if (strSpecificType.equalsIgnoreCase(PARTICAL_NEWTYPE[i])) {
            // particle[i]++;
            sss.addParticleErr(i, 1);
            break;
          }
        }

      } else if (strTargetType.equalsIgnoreCase("DIGIT")) {
        for (int i = 0; i < DIGIT_NEWTYPE.length; i++) {
          if (strSpecificType.equalsIgnoreCase(DIGIT_NEWTYPE[i])) {
            // digit[i]++;
            sss.addDigitErr(i, 1);
            break;
          }
        }

      }
    }
    dm.releaseConn();

  }

  public Vector getWordErrorTypeChange() throws IOException, SQLException {
    // String strPath

    DataManager dm = new DataManager();
    Vector<WordErrDataMeta> vec = new Vector<WordErrDataMeta>();
    dm.connectToAccess("history");
    StringTokenizer st;
    String sql = "SELECT * FROM WordErrTable";
    ResultSet tmpRs = dm.executeQuery(sql);
    int intIndex = 0;
    // strwordTableName
    // read data into WordErrDataMeta one by one, change attribute on the basis
    // of its type

    WordErrDataMeta objEDMeta;
    while (tmpRs.next()) {

      objEDMeta = new WordErrDataMeta();
      objEDMeta.intID = tmpRs.getInt("ID");
      objEDMeta.strTargetType = tmpRs.getString("TargetType");
      objEDMeta.strSpecificType = tmpRs.getString("SpecificType");

      String strDetailType = objEDMeta.strSpecificType.trim();
      String StrType = objEDMeta.strTargetType.trim();
      if (strDetailType.equalsIgnoreCase("NULL") || strDetailType.equalsIgnoreCase("CORRECT")
          || strDetailType.equalsIgnoreCase("SENTENCE") || strDetailType.equalsIgnoreCase("INS")) {
        System.out.println("need not change, type is [" + strDetailType + "]");
      } else {
        // type parsing
        // System.out.println("strTargetType="+StrType+"--strSpecificType="+strDetailType);
        String str = ErrTypeParse(StrType, strDetailType);
        intIndex++;
        if (str != null) {

          objEDMeta.strSpecificType = str;

          System.out.println("strTargetType=" + StrType + "--strSpecificType=" + strDetailType + "----new type is "
              + str);
          vec.addElement(objEDMeta);
          /*
           * sql = "UPDATE HELLO_WORLD_TABLE "+
           * "SET LANGUAGE='�X�y�C����',MESSAGE='Hola Mundo' " +
           * "WHERE HELLO_WORLD_TABLE.NO=1"; sql = "update " + strTableName +
           * " set " + strSetValues + " where " + strWhere;
           */
          // String strSetValues = "SpecificType='"+str+"'";
          // String strWhere = "WordErrTable.ID="+objEDMeta.intID;
          // dm.modifyRecord("WordErrTable",strSetValues,strWhere);

        }

      }

    }
    dm.releaseConn();
    System.out.println(vec.size() + "---all---" + intIndex);
    return vec;
  }

  public void WordErrorTypeChange(String oldType, String newType) throws IOException, SQLException {
    DataManager dm = new DataManager();
    dm.connectToAccess("history");
    Vector v = new Vector();
    String sql = "SELECT * FROM WordErrTable where TargetType ='VERB'";
    ResultSet tmpRs = dm.executeQuery(sql);
    int num = 0;
    while (tmpRs.next()) {
      int id = tmpRs.getInt("ID");
      String stype = tmpRs.getString("SpecificType");
      if (stype.startsWith(oldType)) {
        v.addElement(id);
        num++;
      }
    }
    System.out.println(num);
    dm.releaseConn();
    for (int i = 0; i < v.size(); i++) {
      String strSetValues = "SpecificType='" + newType + "'";
      String strWhere = "WordErrTable.ID=" + v.get(i);
      dm.modifyRecord("WordErrTable", strSetValues, strWhere);
    }
  }

  /*
   * error type parsing
   */
  // String strFields =
  // "(StudentID,Lesson,Question,BaseGrammar,FullGrammar,ObservedWord,TargetWord,ObservedType,TargetType,SameType,Valid,SameConfusionGroup,Spell,EnglishStem,SameStem)";
  // String strValues = wedm.toVALUES();
  // System.out.println("before add to our database,strValues is ["+strValues+"]");
  // dm.insertRecord(strwordTableName, strFields, strValues);
  /*
   * String[] strType = strNewSpecificType.split("_");
   */
  // NOUN DEFINITIVE DIGIT

  public void changeWordTypeOfNoun() throws SQLException {

    DataManager dm = new DataManager();
    dm.connectToAccess("history");
    Vector v = new Vector();
    String sql = "SELECT * FROM WordErrTable where TargetType IN('DEFINITIVE','MISC','LOCATION')";
    ResultSet tmpRs = dm.executeQuery(sql);
    while (tmpRs.next()) {
      // objEDMeta = new WordErrDataMeta();
      // objEDMeta.intID = tmpRs.getInt("ID");
      v.addElement(tmpRs.getInt("ID"));
    }
    dm.releaseConn();
    for (int i = 0; i < v.size(); i++) {
      String strSetValues = "TargetType='NOUN'";
      String strWhere = "WordErrTable.ID=" + v.get(i);
      dm.modifyRecord("WordErrTable", strSetValues, strWhere);
    }

  }

  public String ErrTypeParse(String strTargetType, String strSpecificType) {
    // String strTargetType = objEDMeta.strTargetType;
    // String strSpecificType = objEDMeta.strSpecificType;
    String strResult = null;
    // NOUN
    if (strTargetType.equalsIgnoreCase("NOUN") || strTargetType.equalsIgnoreCase("DEFINITIVE")
        || strTargetType.equalsIgnoreCase("Misc") || strTargetType.equalsIgnoreCase("LOCATION")) {
      for (int i = 0; i < NOUN_OLDTYPE.length; i++) {
        if (strSpecificType.equalsIgnoreCase(NOUN_OLDTYPE[i])) {
          String strNewSpecificType = NOUN_NEWTYPE[i];
          strResult = strNewSpecificType;
          break;

        }
      }

    } else if (strTargetType.equalsIgnoreCase("VERB")) {
      // for (int i = 0; i < NOUN_OLDTYPE.length; i++) {
      for (int i = 0; i < VERB_OLDTYPE.length; i++) {
        if (strSpecificType.equalsIgnoreCase(VERB_OLDTYPE[i])) {
          String strNewSpecificType = VERB_NEWTYPE[i];
          strResult = strNewSpecificType;
          // objEDMeta.setStrSpecificType(strNewSpecificType);
          break;
        }
      }
    } else if (strTargetType.equalsIgnoreCase("PARTICLE")) {
      for (int i = 0; i < PARTICAL_OLDTYPE.length; i++) {
        if (strSpecificType.equalsIgnoreCase(PARTICAL_OLDTYPE[i])) {
          String strNewSpecificType = PARTICAL_NEWTYPE[i];
          strResult = strNewSpecificType;
          break;
        }
      }

    } else if (strTargetType.equalsIgnoreCase("DIGIT")) {
      for (int i = 0; i < DIGIT_OLDTYPE.length; i++) {
        if (strSpecificType.equalsIgnoreCase(DIGIT_OLDTYPE[i])) {
          String strNewSpecificType = DIGIT_NEWTYPE[i];
          strResult = strNewSpecificType;
          break;
        }
      }

    }
    return strResult;
  }

  public static void main(String[] args) throws IOException, SQLException {
    SentenceErrorPredictionMatrix sepm = new SentenceErrorPredictionMatrix();
    sepm.predictionOfSentencesNewVector(PREDICTIONDATA);
    // sepm.WordErrorTypeChange("INVALIDFORM","INVS_REF");
    // sepm.WordErrorTypeChange();
    // sepm.changeWordTypeOfNoun();

    // int intPrediction =
    // ErrorPredictionProcess.getWordPrediction("�����Ԃ�",28,false,false);
    // int intPrediction =
    // ErrorPredictionProcess.getWordPrediction("�X�q",28,false,false);
    // int intPrediction =
    // ErrorPredictionProcess.getWordPrediction("�낤����",28,false,false);
    // System.out.println("--["+ intPrediction +"]");
    // System.out.println(sepm.sss.computeTotal());

  }

}
