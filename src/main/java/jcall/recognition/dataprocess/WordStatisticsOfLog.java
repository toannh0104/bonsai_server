/**
 * Created on 2007/06/15
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

import jcall.recognition.database.DataManager;
import jcall.recognition.languagemodel.JulianGram;
import jcall.util.FileManager;

import org.apache.log4j.Logger;

public class WordStatisticsOfLog {

  static Logger logger = Logger.getLogger(WordStatisticsOfLog.class.getName());
  final static String WSRFILENAME = "./Data/WordStatisticResult.txt";
  PrintWriter pw;
  LexiconProcess lprocess;
  DataManager dm;
  ErrorPredictionProcess epp;
  SentenceStatisticStructure sss;
  PredictionProcess pp;

  public WordStatisticsOfLog() throws IOException {
    init();
  }

  private void init() throws IOException {
    lprocess = new LexiconProcess();
    dm = new DataManager();
    sss = new SentenceStatisticStructure();
    pp = new PredictionProcess();
    // epp = new ErrorPredictionProcess();
  }

  public void WordFindInLexicon2File(String DBTableName, String strFileName) throws SQLException, IOException {

    FileManager.createFile(strFileName);
    pw = new PrintWriter(new FileWriter(strFileName, true));

    StringTokenizer st;
    dm.connectToAccess("history");
    String sql = "SELECT * FROM " + DBTableName;
    ResultSet rs = dm.executeQuery(sql);
    String strWord = "";
    LexiconWordMeta lwm;
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
    // HashSet hsNoun =new HashSet();
    // HashSet hsVerb =new HashSet();
    // HashSet hsParticle =new HashSet();
    // HashSet hsNQ =new HashSet();
    while (rs.next()) {// still remains sentences
      int intId = rs.getInt("ID");
      int intLesson = rs.getInt("Lesson");
      String strTargetSentence = rs.getString("TargetSentence");
      // split the sentence into components
      st = new StringTokenizer(strTargetSentence);
      intIndex = 0;
      while (st.hasMoreTokens()) { // handle word
        intIndex++;
        strWord = st.nextToken();
        strWord = strWord.trim();
        // First, get word type;
        if (st.hasMoreTokens()) {
          booVerb = false;
          if (intIndex % 2 == 0) {// = particle
            booParticle = true;
            lwm = lprocess.getLexiconWordParticle(strWord);
            if (lwm != null) {
              particle++;

              pw.println("Particle: " + lwm.id + " " + lwm.strKana);
            } else {
              errorWord++;
              logger.error("wrong,target particle not exist in db ---[" + strWord + "]" + "in sentence " + intId
                  + " in lesson " + intLesson);
            }
          } else {// noun+NQ
            booParticle = false;
            lwm = lprocess.getNQ(strWord);
            if (lwm != null) {// type = QN
              // ok QN count is added
              NQ++;
              pw.println("NQ: " + lwm.id + " " + lwm.strKana);
            } else { // could be noun
              lwm = lprocess.getLexiconWordNoun(strWord);
              if (lwm != null) { // type = noun
                noun++;

                pw.println("Noun: " + lwm.id + " " + lwm.strKana);
              } else {
                errorWord++;
                logger.error("wrong,target noun/NQ not exist in db ---[" + strWord + "]" + "in sentence " + intId
                    + " in lesson " + intLesson);
              }
            }
          }

        } else {
          booVerb = true;
          lwm = lprocess.getLexiconWordVerb(strWord, intLesson);
          if (lwm != null) {
            verb++;
            pw.println("Verb: " + lwm.id + " " + lwm.strKana);
          } else {
            errorWord++;
            logger.error("wrong,target verb not exist in db ---[" + strWord + "]" + "in sentence " + intId
                + " in lesson " + intLesson);
          }

        }
      }// end of while st
      allNumber += intIndex;
    }
    System.out.println("all words appears" + allNumber + "--noun is" + noun + "--particle is" + particle + "--NQ is "
        + NQ + "--verb" + verb + "--errorWord" + errorWord);
    System.out.println("all error word is " + errorWord);
    dm.releaseConn();
  }

  public void WordOcurrenceNumberAnalysis(String DBTableName, WordStatisticsStruct wss) throws SQLException,
      IOException {
    // FileManager.createFile(strFileName);
    // pw = new PrintWriter(new FileWriter(strFileName, true));
    WordStatisticsDataMeta wsdm;
    StringTokenizer st;
    dm.connectToAccess("history");
    String sql = "SELECT * FROM " + DBTableName;
    ResultSet rs = dm.executeQuery(sql);
    String strWord = "";
    LexiconWordMeta lwm;
    boolean booVerb = false;
    boolean booParticle = false;
    int intIndex = 0; // words' index of one sentence'
    // int intTotle =0;
    int allNumber = 0;// number of all of the words;
    int noun = 0;
    int particle = 0;
    int NQ = 0;
    int verb = 0;
    int errorWord = 0;
    // HashSet hsNoun =new HashSet();
    // HashSet hsVerb =new HashSet();
    // HashSet hsParticle =new HashSet();
    // HashSet hsNQ =new HashSet();
    while (rs.next()) {// still remains sentences
      int intId = rs.getInt("ID");
      int intLesson = rs.getInt("Lesson");
      String strTargetSentence = rs.getString("TargetSentence");
      // split the sentence into components
      st = new StringTokenizer(strTargetSentence);
      intIndex = 0;
      while (st.hasMoreTokens()) { // handle word
        intIndex++;
        strWord = st.nextToken();
        strWord = strWord.trim();
        // First, get word type;
        if (st.hasMoreTokens()) {
          booVerb = false;
          if (intIndex % 2 == 0) {// = particle
            booParticle = true;
            lwm = lprocess.getLexiconWordParticle(strWord);
            if (lwm != null) {
              particle++;
              wss.addWord(lwm);
              // pw.println("Particle: "+lwm.id +" "+lwm.strKana);
            } else {
              errorWord++;
              logger.error("wrong,target particle not exist in db ---[" + strWord + "]" + "in sentence " + intId
                  + " in lesson " + intLesson);
            }
          } else {// noun+NQ
            booParticle = false;
            lwm = lprocess.getNQ(strWord);
            if (lwm != null) {// type = QN
              // ok QN count is added
              NQ++;
              wss.addWord(lwm);
              // pw.println("NQ: "+lwm.id +" "+lwm.strKana);
            } else { // could be noun
              lwm = lprocess.getLexiconWordNoun(strWord);
              if (lwm != null) { // type = noun
                noun++;
                wss.addWord(lwm);
                // pw.println("Noun: "+lwm.id +" "+lwm.strKana);
              } else {
                errorWord++;
                logger.error("wrong,target noun/NQ not exist in db ---[" + strWord + "]" + "in sentence " + intId
                    + " in lesson " + intLesson);
              }
            }
          }

        } else {
          booVerb = true;
          lwm = lprocess.getLexiconWordVerb(strWord, intLesson);
          if (lwm != null) {
            verb++;
            wss.addWord(lwm);
            // pw.println("Verb: "+lwm.id +" "+lwm.strKana);
          } else {
            errorWord++;
            logger.error("wrong,target verb not exist in db ---[" + strWord + "]" + "in sentence " + intId
                + " in lesson " + intLesson);
          }

        }
      }// end of while st
      allNumber += intIndex;
    }
    System.out.println("all words appears" + allNumber + "--noun is" + noun + "--particle is" + particle + "--NQ is "
        + NQ + "--verb" + verb + "--errorWord" + errorWord);
    System.out.println("all error word is " + errorWord);
    System.out.println("all WordStatisticsDataMeta word is " + wss.getVecWord().size());
    dm.releaseConn();
  }

  public void WordOcurrenceAnalysisResultToFile(String filename) throws SQLException, IOException {
    WordStatisticsStruct wss = new WordStatisticsStruct();
    // String SentencesTable="AllSentences";
    WordOcurrenceNumberAnalysis(OldCallLogParser.SentencesTable, wss);
    // String onefile = "./Data/WordStatisticResult.txt";

    FileManager.createFile(filename);
    PrintWriter pwriter = new PrintWriter(new FileWriter(filename, true));
    wss.write(pwriter);
    pwriter.flush();
    pwriter.println("-eof");
    pwriter.close();
  }

  public void WordErrorCompute2File(String DBTableName, String strFileName) throws SQLException, IOException {

    PredictionProcess pp = new PredictionProcess();
    // SentenceStatisticStructure sss = new SentenceStatisticStructure();

    FileManager.createFile(strFileName);
    pw = new PrintWriter(new FileWriter(strFileName, true));

    StringTokenizer st;
    // dm.connectToAccess("history");
    dm.connectToAccess("EXPERIMENTS");

    String sql = "SELECT * FROM " + DBTableName;
    ResultSet rs = dm.executeQuery(sql);
    String strWord = "";
    LexiconWordMeta lwm;
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

    int nounPeplexicity = 0;
    int particlePeplexicity = 0;
    int NQPeplexicity = 0;
    int verbPeplexicity = 0;

    double doubleE; // expectation words of this sentence
    double doubleETotal = 0.0;

    while (rs.next()) {// still remains sentences
      int intId = rs.getInt("ID");
      // int intLesson = rs.getInt("Lesson");
      // String strTargetSentence = rs.getString("TargetSentence");

      String strLesson = rs.getString("Lesson");
      int intLesson = Integer.parseInt(strLesson.trim());
      String strTargetSentence = rs.getString("strTargetAnswer");

      pw.println(strTargetSentence + "---ID=" + intId);

      // split the sentence into components
      st = new StringTokenizer(strTargetSentence);
      intIndex = 0;
      intTotle = 0;
      while (st.hasMoreTokens()) { // handle word
        intIndex++;
        strWord = st.nextToken();
        strWord = strWord.trim();
        // First, get word type;
        if (st.hasMoreTokens()) {
          booVerb = false;
          if (intIndex % 2 == 0) {// = particle
            booParticle = true;
            lwm = lprocess.getLexiconWordParticle(strWord);
            if (lwm != null) {
              particle++;
              int a = pp.getParticleErrorNumber(lwm, intLesson, sss);
              intTotle += a;
              particlePeplexicity += a;
              pw.write(a + " ");
              logger.debug("getParticleErrorNumber: " + a);
            } else {
              errorWord++;
              logger.error("wrong,target particle not exist in db ---[" + strWord + "]" + "in sentence " + intId
                  + " in lesson " + intLesson);
            }

          } else {// noun+NQ
            booParticle = false;
            lwm = lprocess.getNQ(strWord);
            if (lwm != null) {// type = QN
              // ok QN count is added
              NQ++;
              int a = pp.getNQErrorNumber(lwm, intLesson, sss);
              intTotle += a;
              pw.write(a + " ");
              NQPeplexicity += a;
              logger.debug("getNQErrorNumber: " + a);

            } else { // could be noun
            // if(strWord.equalsIgnoreCase("T�V���c")){
            // strWord= "�e�B�[�V���c";
            // }
              lwm = lprocess.getLexiconWordNoun(strWord);
              if (lwm != null) { // type = noun
                noun++;
                int a = pp.getNounErrorNumber(lwm, intLesson, sss);
                intTotle += a;
                nounPeplexicity += a;
                pw.write(a + " ");
                logger.debug("getNounErrorNumber: " + a);
              } else {
                errorWord++;
                logger.error("wrong,target noun/NQ not exist in db ---[" + strWord + "]" + "in sentence " + intId
                    + " in lesson " + intLesson);
              }
            }
          }

        } else {
          booVerb = true;
          lwm = lprocess.getLexiconWordVerb(strWord, intLesson);
          if (lwm != null) {
            verb++;
            int a = pp.getVerbErrorNumber(lwm, intLesson, sss);
            intTotle += a;
            verbPeplexicity += a;
            pw.write(a + " ");
            logger.debug("getVerbErrorNumber: " + a);
          } else {
            errorWord++;
            logger.error("wrong,target verb not exist in db ---[" + strWord + "]" + "in sentence " + intId
                + " in lesson " + intLesson);
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
    pw.write("-----" + doubleETotal / 140);
    // int nounPeplexicity=0;
    // int particlePeplexicity=0;
    // int NQPeplexicity=0;
    // int verbPeplexicity=0;

    System.out.println("all words appears" + allNumber + "--noun is" + noun + "--particle is" + particle + "--NQ is "
        + NQ + "--verb" + verb + "--errorWord" + errorWord);
    System.out.println("all words appears" + allNumber + "--noun is" + noun + "--particle is" + particle + "--NQ is "
        + NQ + "--verb" + verb + "--errorWord" + errorWord);
    System.out.println("all error word is " + errorWord);
    dm.releaseConn();

    sss.writePredictNum(pw);
    pw.flush();
    pw.close();

  }

  /*
   * BaseMethod is general method
   */

  public void WordErrorCompute2File4BaseMethod(String DBTableName, String strFileName) throws SQLException, IOException {

    JulianGram jg = new JulianGram();
    PredictionProcess pp = new PredictionProcess();
    // SentenceStatisticStructure sss = new SentenceStatisticStructure();

    FileManager.createFile(strFileName);
    pw = new PrintWriter(new FileWriter(strFileName, true));

    StringTokenizer st;
    // dm.connectToAccess("history");
    dm.connectToAccess("EXPERIMENTS");
    String sql = "SELECT * FROM " + DBTableName;
    ResultSet rs = dm.executeQuery(sql);
    String strWord = "";
    LexiconWordMeta lwm;
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

    while (rs.next()) {// still remains sentences
      int intId = rs.getInt("ID");
      // int intLesson = rs.getInt("Lesson");
      // String strTargetSentence = rs.getString("TargetSentence");

      String strLesson = rs.getString("Lesson");
      int intLesson = Integer.parseInt(strLesson.trim());
      String strTargetSentence = rs.getString("strTargetAnswer");

      pw.println(strTargetSentence + "---ID=" + intId);

      // split the sentence into components
      st = new StringTokenizer(strTargetSentence);
      intIndex = 0;
      intTotle = 0;
      while (st.hasMoreTokens()) { // handle word
        intIndex++;
        strWord = st.nextToken();
        strWord = strWord.trim();
        // First, get word type;
        if (st.hasMoreTokens()) {
          booVerb = false;
          if (intIndex % 2 == 0) {// = particle
            booParticle = true;
            lwm = lprocess.getLexiconWordParticle(strWord);
            if (lwm != null) {
              particle++;
              // int a = pp.getParticleErrorNumber(lwm, intLesson, sss);
              int a = 11;
              intTotle += a;
              pw.write(a + " ");
              logger.debug("getParticleErrorNumber: " + a);
            } else {
              errorWord++;
              logger.error("wrong,target particle not exist in db ---[" + strWord + "]" + "in sentence " + intId
                  + " in lesson " + intLesson);
            }

          } else {// noun+NQ
            booParticle = false;
            lwm = lprocess.getNQ(strWord);
            if (lwm != null) {// type = QN
              // ok QN count is added
              NQ++;
              // int a = pp.getNQErrorNumber(lwm,intLesson,sss);
              int a = 18;
              intTotle += a;
              pw.write(a + " ");
              logger.debug("getNQErrorNumber: " + a);

            } else { // could be noun
              lwm = lprocess.getLexiconWordNoun(strWord);
              if (lwm != null) { // type = noun
                noun++;
                // int a = pp.getNounErrorNumber(lwm,intLesson,sss);
                int a = jg.getConceptNumber(intLesson - 1, lwm.getStrKanji(), lwm.getStrKana());
                intTotle += a;
                pw.write(a + " ");
                logger.debug("getNounErrorNumber: " + a);
              } else {
                errorWord++;
                logger.error("wrong,target noun/NQ not exist in db ---[" + strWord + "]" + "in sentence " + intId
                    + " in lesson " + intLesson);
              }
            }
          }

        } else {
          booVerb = true;
          lwm = lprocess.getLexiconWordVerb(strWord, intLesson);
          if (lwm != null) {
            verb++;
            // int a = pp.getVerbErrorNumber(lwm, intLesson, sss);
            int a = pp.getVerbError_VS_DFORM(lwm, intLesson, sss);
            intTotle += a;
            pw.write(a + " ");
            logger.debug("getVerbErrorNumber: " + a);
          } else {
            errorWord++;
            logger.error("wrong,target verb not exist in db ---[" + strWord + "]" + "in sentence " + intId
                + " in lesson " + intLesson);
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
    pw.write("-----" + doubleETotal / 140);
    System.out.println("all words appears" + allNumber + "--noun is" + noun + "--particle is" + particle + "--NQ is "
        + NQ + "--verb" + verb + "--errorWord" + errorWord);
    System.out.println("all error word is " + errorWord);
    dm.releaseConn();

    sss.writePredictNum(pw);
    pw.flush();
    pw.close();

  }

  public void Voca4BaseMethod(String DBTableName, String strFileName) throws SQLException, IOException {

    JulianGram jg = new JulianGram();
    PredictionProcess pp = new PredictionProcess();
    // SentenceStatisticStructure sss = new SentenceStatisticStructure();

    FileManager.createFile(strFileName);
    pw = new PrintWriter(new FileWriter(strFileName, true));

    StringTokenizer st;
    // dm.connectToAccess("history");
    dm.connectToAccess("EXPERIMENTS");
    String sql = "SELECT * FROM " + DBTableName;
    ResultSet rs = dm.executeQuery(sql);
    String strWord = "";
    LexiconWordMeta lwm;
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

    while (rs.next()) {// still remains sentences
      int intId = rs.getInt("ID");
      // int intLesson = rs.getInt("Lesson");
      // String strTargetSentence = rs.getString("TargetSentence");

      String strLesson = rs.getString("Lesson");
      int intLesson = Integer.parseInt(strLesson.trim());
      String strTargetSentence = rs.getString("strTargetAnswer");

      pw.println(strTargetSentence + "---ID=" + intId);

      // split the sentence into components
      st = new StringTokenizer(strTargetSentence);
      intIndex = 0;
      intTotle = 0;
      while (st.hasMoreTokens()) { // handle word
        intIndex++;
        strWord = st.nextToken();
        strWord = strWord.trim();
        // First, get word type;
        if (st.hasMoreTokens()) {
          booVerb = false;
          if (intIndex % 2 == 0) {// = particle
            booParticle = true;
            lwm = lprocess.getLexiconWordParticle(strWord);
            if (lwm != null) {
              particle++;
              // int a = pp.getParticleErrorNumber(lwm, intLesson, sss);
              int a = 11;
              intTotle += a;
              pw.write(a + " ");
              logger.debug("getParticleErrorNumber: " + a);
            } else {
              errorWord++;
              logger.error("wrong,target particle not exist in db ---[" + strWord + "]" + "in sentence " + intId
                  + " in lesson " + intLesson);
            }

          } else {// noun+NQ
            booParticle = false;
            lwm = lprocess.getNQ(strWord);
            if (lwm != null) {// type = QN
              // ok QN count is added
              NQ++;
              // int a = pp.getNQErrorNumber(lwm,intLesson,sss);
              int a = 18;
              intTotle += a;
              pw.write(a + " ");
              logger.debug("getNQErrorNumber: " + a);

            } else { // could be noun
              lwm = lprocess.getLexiconWordNoun(strWord);
              if (lwm != null) { // type = noun
                noun++;
                // int a = pp.getNounErrorNumber(lwm,intLesson,sss);
                int a = jg.getConceptNumber(intLesson - 1, lwm.getStrKanji(), lwm.getStrKana());
                intTotle += a;
                pw.write(a + " ");
                logger.debug("getNounErrorNumber: " + a);
              } else {
                errorWord++;
                logger.error("wrong,target noun/NQ not exist in db ---[" + strWord + "]" + "in sentence " + intId
                    + " in lesson " + intLesson);
              }
            }
          }

        } else {
          booVerb = true;
          lwm = lprocess.getLexiconWordVerb(strWord, intLesson);
          if (lwm != null) {
            verb++;
            // int a = pp.getVerbErrorNumber(lwm, intLesson, sss);
            int a = pp.getVerbError_VS_DFORM(lwm, intLesson, sss);
            intTotle += a;
            pw.write(a + " ");
            logger.debug("getVerbErrorNumber: " + a);
          } else {
            errorWord++;
            logger.error("wrong,target verb not exist in db ---[" + strWord + "]" + "in sentence " + intId
                + " in lesson " + intLesson);
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
    pw.write("-----" + doubleETotal / 140);
    System.out.println("all words appears" + allNumber + "--noun is" + noun + "--particle is" + particle + "--NQ is "
        + NQ + "--verb" + verb + "--errorWord" + errorWord);
    System.out.println("all error word is " + errorWord);
    dm.releaseConn();

    sss.writePredictNum(pw);
    pw.flush();
    pw.close();

  }

  /*
   * baseline
   */
  public void WordErrorCompute2File4BaseLine(String DBTableName, String strFileName) throws SQLException, IOException {
    JulianGram jg = new JulianGram();
    PredictionProcess pp = new PredictionProcess();
    // SentenceStatisticStructure sss = new SentenceStatisticStructure();

    FileManager.createFile(strFileName);
    pw = new PrintWriter(new FileWriter(strFileName, true));

    StringTokenizer st;
    // dm.connectToAccess("history");
    dm.connectToAccess("EXPERIMENTS");

    String sql = "SELECT * FROM " + DBTableName;
    ResultSet rs = dm.executeQuery(sql);
    String strWord = "";
    LexiconWordMeta lwm;
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

    while (rs.next()) {// still remains sentences
      int intId = rs.getInt("ID");
      // int intLesson = rs.getInt("Lesson");
      // String strTargetSentence = rs.getString("TargetSentence");

      String strLesson = rs.getString("Lesson");
      int intLesson = Integer.parseInt(strLesson.trim());
      String strTargetSentence = rs.getString("strTargetAnswer");

      pw.println(strTargetSentence + "---ID=" + intId);

      // split the sentence into components
      st = new StringTokenizer(strTargetSentence);
      intIndex = 0;
      intTotle = 0;
      while (st.hasMoreTokens()) { // handle word
        intIndex++;
        strWord = st.nextToken();
        strWord = strWord.trim();
        // First, get word type;
        if (st.hasMoreTokens()) {
          booVerb = false;
          if (intIndex % 2 == 0) {// = particle
            booParticle = true;
            lwm = lprocess.getLexiconWordParticle(strWord);
            if (lwm != null) {
              particle++;
              int a = pp.getParticleErrorNumber(lwm, intLesson, sss);
              // int a = 11;
              intTotle += a;
              pw.write(a + " ");
              logger.debug("getParticleErrorNumber: " + a);
            } else {
              errorWord++;
              logger.error("wrong,target particle not exist in db ---[" + strWord + "]" + "in sentence " + intId
                  + " in lesson " + intLesson);
            }

          } else {// noun+NQ
            booParticle = false;
            lwm = lprocess.getNQ(strWord);
            if (lwm != null) {// type = QN
              // ok QN count is added
              NQ++;
              // int a = pp.getNQErrorNumber(lwm,intLesson,sss);
              int a = 89;
              intTotle += a;
              pw.write(a + " ");
              logger.debug("getNQErrorNumber: " + a);

            } else { // could be noun
              lwm = lprocess.getLexiconWordNoun(strWord);
              if (lwm != null) { // type = noun
                noun++;
                // int a = pp.getNounErrorNumber(lwm,intLesson,sss);
                int a = jg.getQuestionNumber(intLesson - 1, lwm.getStrKanji(), lwm.getStrKana());
                intTotle += a;
                pw.write(a + " ");
                logger.debug("getNounErrorNumber: " + a);
              } else {
                errorWord++;
                logger.error("wrong,target noun/NQ not exist in db ---[" + strWord + "]" + "in sentence " + intId
                    + " in lesson " + intLesson);
              }
            }
          }

        } else {
          booVerb = true;
          lwm = lprocess.getLexiconWordVerb(strWord, intLesson);
          if (lwm != null) {
            verb++;
            // int a = pp.getVerbErrorNumber(lwm, intLesson, sss);
            int a = pp.getVerbError_VS_DFORM(lwm, intLesson, sss);
            int b = jg.getQuestionNumber(intLesson - 1, lwm.getStrKanji(), lwm.getStrKana());
            if (b > 0) {
              a = b * (a + 1) - 1;
            }
            intTotle += a;
            pw.write(a + " ");
            logger.debug("getVerbErrorNumber: " + a);
          } else {
            errorWord++;
            logger.error("wrong,target verb not exist in db ---[" + strWord + "]" + "in sentence " + intId
                + " in lesson " + intLesson);
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
    pw.write("-----" + doubleETotal / 140);
    System.out.println("all words appears" + allNumber + "--noun is" + noun + "--particle is" + particle + "--NQ is "
        + NQ + "--verb" + verb + "--errorWord" + errorWord);
    System.out.println("all error word is " + errorWord);
    dm.releaseConn();

    sss.writePredictNum(pw);
    pw.flush();
    pw.close();

  }

  public static void main(String[] args) throws IOException, SQLException {
    // QuantifierNode qn = ErrorPredictionProcess.getQN("���Bۂ�");
    // System.out.println(qn.strNumeral); // invoke the static method, wont
    // invoik its instructer
    System.out.println("ok,main");
    WordStatisticsOfLog ws = new WordStatisticsOfLog();
    // String SentencesTable="AllSentences";

    String SentencesTable = "SpeechSentencesStageTwo";
    String afile = "./Data/perplexity.txt";
    // ws.WordFindInLexicon2File(SentencesTable, afile);
    // WSRFILENAME
    // ws.WordOcurrenceAnalysisResultToFile(WSRFILENAME);
    // ws.WordErrorCompute2File(SentencesTable,afile);

    // ws.WordErrorCompute2File4BaseMethod(SentencesTable,afile);
    // ws.WordErrorCompute2File4BaseLine(SentencesTable,afile);

    ws.WordErrorCompute2File(SentencesTable, afile);

  }

}
