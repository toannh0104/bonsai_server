/**
 * Created on 2007/09/27
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;

import jcall.config.Configuration;
import jcall.config.FindConfig;
import jcall.recognition.database.DataManager;
import jcall.recognition.database.SentenceDataMeta;
import jcall.recognition.database.TextSentenceDataMeta;
import jcall.recognition.database.WordErrDataMeta;

import org.apache.log4j.Logger;

public class NewLogParser {

  static Logger logger = Logger.getLogger(NewLogParser.class.getName());

  final static String SpeakSentencesTable1 = "SpeechSentencesStageOne";
  final static String SpeakSentencesTable2 = "SpeechSentencesStageTwo";
  public final static String SpeakSentencesTable3 = "SpeechSentencesStageTwoKana";

  final static String TextSentencesTable1 = "TextSentencesStageOne";
  final static String TextSentencesTable2 = "TextSentencesStageTwo";

  final static String TextWordErrorTable1 = "TextWordErrorStageOne";
  final static String TextWordErrorTable2 = "TextWordErrorStageTwo";

  final static String WordTable1 = "WordTableStageOne";
  final static String WordTable2 = "WordTableStageTwo";

  public final static String OdbcName = "ExperimentODBC";
  final static String logPath = "TrialLogs";

  final static String strSpeakLogSentenceFields = "(StudentID,Lesson,Question,strTargetAnswer,strTextAnswer,strSpeechAnswer,strListeningAnswer,SentenceLevelError,booCorrect)";

  final static String strTextLogSentenceFields = "(StudentID,Lesson,Question,InputSentence,TargetSentence,SentenceLevelError)";

  final static String strTextWordErrFields = "(StudentID,Lesson,Question,BaseGrammar,FullGrammar,ObservedWord,TargetWord,ObservedType,TargetType,SameType,Valid,SameConfusionGroup,Spell,EnglishStem,SameStem)";

  final static String RSentencesTable = "RightSentences";
  final static String ErrSentencesTable = "ErrSentences";
  final static String WordTableName = "WordErrTable";

  int state;
  static final int STATE_NEW_SESSION = 0;
  static final int STATE_NEW_LESSON = 1;
  static final int STATE_NEXT_QUESTION = 2;
  static final int STATE_ERROR_DETAIL = 3;
  static final int STATE_ERRORSENTENCE = 4;
  static final int STATE_CORRETSENTENCE = 5;
  static final int STATE_QUESTION_END = 6;
  static final int STATE_ERROE_SUBSTITION = 7;
  static final int STATE_QUESTION_DETAIL = 8;

  static final int STATE_SENTENCE = 8; // for error table

  DataManager dm;
  BufferedReader br;
  PrintWriter pw;

  public NewLogParser() {
    init();
  }

  private void init() {
    dm = new DataManager();
  }

  /**
   * read all setences to AllSentences table of "EXPERIMENT" db;
   * 
   * @param strTableName
   * @param strFileType
   *          "speak" or "text"
   * @throws ClassNotFoundException
   * @throws IOException
   */
  public void loadSentencesFromLogFiles(String strTableName, String strFileType, String strStage)
      throws ClassNotFoundException, IOException {

    strFileType = strFileType.trim();
    if (strFileType.equalsIgnoreCase("speak")) {
      loadSpeakSentencesFromLogFiles(strTableName, strStage);
    } else if (strFileType.equalsIgnoreCase("text")) {
      loadTextSentencesFromLogFiles(strTableName, strStage);
    }

  }

  public void loadTextSentencesFromLogFiles(String strTableName, String strStage) throws ClassNotFoundException,
      IOException {

    createTextSentenceTable(strTableName);

    // then get logfile path from configuration
    // Configuration configuration = new
    // Configuration("../../../JCALLConfig.xml");
    // logger.debug("opened config file: "+"JCALLConfig.xml");
    // String logfilepath = configuration.getItemValue("Data","TrialLogs");
    // logger.debug("get log file path: "+logfilepath);

    String logfilepath = FindConfig.getConfig().findStr(logPath);
    logger.debug("get log file path: " + logfilepath);

    File filePath = new File(logfilepath);
    if (!filePath.exists()) {
      logger.error("goes wrong,does not exist log file in path: " + logfilepath);
    } else {
      logger.debug(filePath.getName());

      File[] arrayFile = filePath.listFiles();
      for (int i = 0; i < arrayFile.length; i++) {
        File fileTemp = arrayFile[i];
        if (fileTemp.isDirectory()) {
          // load data one by one
          String oneFileName = "log_text_" + fileTemp.getName() + "_" + strStage.trim().toLowerCase() + ".txt";

          File oneFile = new File(fileTemp.getPath(), oneFileName);
          if (oneFile.exists()) {
            loadTextSentencesFromOneLogFile(strTableName, strTextLogSentenceFields, oneFile);
          } else {

            logger.error("No exist, file: " + oneFile.getAbsolutePath());
          }

        } else {
          logger.error("No exist such a directory: " + fileTemp.getAbsolutePath());
        }
      }
    }
  }

  /**
   * read all setences to AllSentences table of "EXPERIMENT" db;
   * 
   * @param strTableName
   * @param strFileType
   *          "speak" or "text"
   * @throws ClassNotFoundException
   * @throws IOException
   */
  public void loadSpeakSentencesFromLogFiles(String strTableName, String strStage) throws ClassNotFoundException,
      IOException {

    createSpeakSentenceTable(strTableName);

    // then get logfile path from configuration

    String logfilepath = FindConfig.getConfig().findStr(logPath);
    logger.debug("get log file path: " + logfilepath);

    File filePath = new File(logfilepath);
    if (!filePath.exists()) {
      logger.error("goes wrong,does not exist log file in path: " + logfilepath);
    } else {
      logger.debug(filePath.getName());

      File[] arrayFile = filePath.listFiles();
      for (int i = 0; i < arrayFile.length; i++) {
        File fileTemp = arrayFile[i];
        if (fileTemp.isDirectory()) {
          // load data one by one
          String oneFileName = "log_speak_" + fileTemp.getName() + "_" + strStage.trim().toLowerCase() + ".log";
          File oneFile = new File(fileTemp.getPath(), oneFileName);
          if (oneFile.exists()) {
            loadSpeakSentencesFromOneLogFile(strTableName, strSpeakLogSentenceFields, oneFile);
          } else {
            String oneFileName2 = "log_speak_" + fileTemp.getName() + "_" + strStage.trim().toLowerCase() + ".txt";
            File oneFile2 = new File(fileTemp.getPath(), oneFileName2);
            if (oneFile2.exists()) {
              loadSpeakSentencesFromOneLogFile(strTableName, strSpeakLogSentenceFields, oneFile2);
            } else {
              logger.error("No exist, file: " + oneFile.getAbsolutePath());
            }

          }

        } else {
          logger.error("Not exist such a directory: " + fileTemp.getAbsolutePath());
        }
      }
    }
  }

  /*
   * 
   * read all correct setences to RightSentences table to "history" db;
   */

  // public void loadRSentencesFromLogFiles() throws ClassNotFoundException,
  // IOException{
  // //create table "correctSentences"
  // createTable(RSentencesTable);
  // //then get logfile path from configuration
  // Configuration configuration = new
  // Configuration("../../../JCALLConfig.xml");
  // logger.debug("opened config file: "+"JCALLConfig.xml");
  // String logfilepath = configuration.getItemValue("Data","TrialLogs");
  // logger.debug("get log file path: "+logfilepath);
  // File filePath = new File(logfilepath);
  // if (!filePath.exists()) {
  // logger.debug("goes wrong,does not exist log file in path: "+logfilepath);
  // }else{
  // logger.debug(filePath.getName());
  // //System.out.println(filePath.getName());
  // File[] arrayFile = filePath.listFiles();
  // for (int i = 0; i < arrayFile.length; i++) {
  // File fileTemp = arrayFile[i];
  // if(fileTemp.isFile()){
  // // load data one by one
  // loadRSentencesFromOneLogFile(fileTemp);
  // }
  // }
  // }
  // }
  //
  public void createSpeakSentenceTable(String tablename) throws ClassNotFoundException, IOException {
    // delete all the data and create a new table
    boolean bResult = false;
    // connect to database
    String strOdbc = FindConfig.getConfig().findStr(OdbcName);
    dm.connectToAccess(strOdbc);

    String sql = "drop table " + tablename;
    bResult = dm.execute(sql);

    // create new table
    String strTableSetup = "(" + "ID Counter PRIMARY KEY, ";
    strTableSetup = strTableSetup + "StudentID int, ";
    strTableSetup = strTableSetup + "Lesson VarChar(50), ";
    strTableSetup = strTableSetup + "Question VarChar(50) , ";
    strTableSetup = strTableSetup + "strTargetAnswer VarChar(200) NOT NULL, ";
    strTableSetup = strTableSetup + "strTextAnswer VarChar(120) NOT NULL, ";
    strTableSetup = strTableSetup + "strSpeechAnswer VarChar(120) NOT NULL, ";
    strTableSetup = strTableSetup + "strListeningAnswer VarChar(120) NOT NULL, ";
    strTableSetup = strTableSetup + "SentenceLevelError VarChar(50), ";
    strTableSetup = strTableSetup + "booCorrect bit" + ")";
    String sqlnew = "CREATE TABLE " + tablename + " " + strTableSetup;
    bResult = dm.execute(sqlnew);
    if (bResult) {
      logger.error("error, create no new table--" + tablename);
    } else {
      logger.info("create a new table--" + tablename);
    }
    dm.releaseConn();
  }

  public void createTextSentenceTable(String tablename) throws ClassNotFoundException, IOException {
    // delete all the data and create a new table
    boolean bResult = false;
    // connect to database
    String strOdbc = FindConfig.getConfig().findStr(OdbcName);
    dm.connectToAccess(strOdbc);

    String sql = "drop table " + tablename;
    bResult = dm.execute(sql);
    // create new table
    String strTableSetup = "(" + "ID Counter PRIMARY KEY, ";
    strTableSetup = strTableSetup + "StudentID int, ";
    strTableSetup = strTableSetup + "Lesson VarChar(50), ";
    strTableSetup = strTableSetup + "Question VarChar(50) NOT NULL, ";
    strTableSetup = strTableSetup + "InputSentence VarChar(200) NOT NULL, ";
    strTableSetup = strTableSetup + "TargetSentence VarChar(200) NOT NULL, ";
    strTableSetup = strTableSetup + "SentenceLevelError VarChar(50)" + ")";
    String sqlnew = "CREATE TABLE " + tablename + " " + strTableSetup;
    bResult = dm.execute(sqlnew);
    if (bResult) {
      logger.error("error, create no new table--" + tablename);
    } else {
      logger.info("create a new table--" + tablename);
    }
    dm.releaseConn();
  }

  public void createTextWordTable(String tablename) throws ClassNotFoundException, IOException {
    // delete all the data and create a new table
    boolean bResult = false;
    // connect to database
    String strOdbc = FindConfig.getConfig().findStr(OdbcName);
    dm.connectToAccess(strOdbc);

    String sql = "drop table " + tablename;
    bResult = dm.execute(sql);
    // create new table
    String strWordTableSetup = "(" + "ID Counter PRIMARY KEY, ";
    strWordTableSetup += "StudentID int, ";
    strWordTableSetup += "Lesson VarChar(10), ";
    strWordTableSetup += "Question VarChar(10) NOT NULL, ";
    strWordTableSetup += "BaseGrammar VarChar(50) NOT NULL, ";
    strWordTableSetup += "FullGrammar VarChar(50) NOT NULL, ";
    strWordTableSetup += "ObservedWord  VarChar(50) NOT NULL, ";
    strWordTableSetup += "TargetWord VarChar(50) NOT NULL, ";
    strWordTableSetup += "ObservedType VarChar(10) NOT NULL, ";
    strWordTableSetup += "TargetType VarChar(10) NOT NULL, ";
    strWordTableSetup += "SameType bit , ";
    strWordTableSetup += "Valid bit , ";
    strWordTableSetup += "SameConfusionGroup bit, ";
    strWordTableSetup += "Spell bit, ";
    strWordTableSetup += "EnglishStem bit, ";
    strWordTableSetup += "SameStem bit, ";
    strWordTableSetup += "InvalidForm bit, ";
    strWordTableSetup += "OneStepErrW  bit,";
    strWordTableSetup += "GeneralType VarChar(50),";
    strWordTableSetup += "SpecificType VarChar(50),";
    strWordTableSetup += "FormMistakes VarChar(50), ";
    strWordTableSetup += "strFormDistance VarChar(10)" + ")";
    // System.out.println(strWordTableSetup);
    String sqlnew = "CREATE TABLE " + tablename + " " + strWordTableSetup;
    bResult = dm.execute(sqlnew);
    if (bResult) {
      logger.error("error, create no new table--" + tablename);
    } else {
      logger.info("create a new table--" + tablename);
    }
    dm.releaseConn();
  }

  /*
   * WordTable contains all words that different between speechAnswer,
   * TargetAnswer, TextAnswer
   */

  public void createWordTable(String tablename) throws ClassNotFoundException, IOException {
    // delete all the data and create a new table
    boolean bResult = false;
    // connect to database
    String strOdbc = FindConfig.getConfig().findStr(OdbcName);
    dm.connectToAccess(strOdbc);

    String sql = "drop table " + tablename;
    bResult = dm.execute(sql);
    // create new table
    String strWordTableSetup = "(" + "ID Counter PRIMARY KEY, ";
    strWordTableSetup += "StudentID int, ";
    strWordTableSetup += "Lesson VarChar(10), ";
    strWordTableSetup += "Question VarChar(10), ";
    strWordTableSetup += "TargetWord VarChar(50) NOT NULL, ";
    strWordTableSetup += "ObservedWord VarChar(50) NOT NULL, ";
    strWordTableSetup += "RecognitionWord VarChar(50) NOT NULL, ";
    strWordTableSetup += "ListenWord VarChar(10) NOT NULL, ";
    strWordTableSetup += "TargetType VarChar(10) NOT NULL, ";
    strWordTableSetup += "IsSameRecognitionWithObserved bit , ";
    strWordTableSetup += "IsSameObservedWithTarget bit , ";
    strWordTableSetup += "IsSameRecognitionWithListen bit, ";
    strWordTableSetup += "IsSameListenWithTarget bit, ";
    strWordTableSetup += "IsSameObservedWithListen bit, ";
    // strWordTableSetup += "SameStem bit, ";
    strWordTableSetup += "GeneralType VarChar(50),";
    strWordTableSetup += "SpecificType VarChar(50),";
    strWordTableSetup += "FormMistakes VarChar(50), ";
    strWordTableSetup += "strFormDistance VarChar(10)" + ")";
    // System.out.println(strWordTableSetup);
    String sqlnew = "CREATE TABLE " + tablename + " " + strWordTableSetup;
    bResult = dm.execute(sqlnew);
    if (bResult) {
      logger.error("error, create no new table--" + tablename);
    } else {
      logger.info("create a new table--" + tablename);
    }
    dm.releaseConn();
  }

  public void WordAnalysisToTable(String strSourceTableName, String strTargetTableName, String strTargetTableFields) {

  }

  /*
   * here oneFile is a File, no directory
   */

  private void loadSpeakSentencesFromOneLogFile(String strTableName, String strFields, File oneFile) throws IOException {
    state = 0;
    String name = oneFile.getName();
    StringTokenizer st = new StringTokenizer(name, "_");
    String str = "0";
    if (st.hasMoreElements()) {
      str = (String) st.nextElement();
      if (st.hasMoreElements()) {
        str = (String) st.nextElement();
      }
    }
    if (st.hasMoreElements()) {
      str = (String) st.nextElement();
    }

    // name = name.substring(0,name.indexOf("_"));
    int intStudent = Integer.parseInt(str);

    SentenceDataMeta sdm = null;
    br = new BufferedReader(new FileReader(oneFile));

    String strOdbc = FindConfig.getConfig().findStr(OdbcName);
    dm.connectToAccess(strOdbc);

    boolean token = false; // keep to remember if the file is end;
    int intTotalSentence = 0;
    String line;
    line = br.readLine();
    while (!token) {
      if (line == null) {
        continue;
      } else if (line.trim().equalsIgnoreCase("#eof")) {
        if (sdm != null && sdm.getStrSpeechAnswer().length() > 0) {
          // first, save to a file;

          // then, save to the database
          String strValues = sdm.toVALUES();
          logger.debug("before add to our database,strValues is [" + strValues + "]");
          dm.insertRecord(strTableName, strFields, strValues);
          intTotalSentence++;
        }
        token = true;
      } else {
        switch (state) {
          case STATE_NEW_SESSION:
            if (line.indexOf("[CALL_SentenceGrammar.java] : target sentence is") != -1) {
              state = STATE_NEXT_QUESTION;
            }
            break;
          case STATE_NEXT_QUESTION:
            sdm = new SentenceDataMeta();
            sdm.setIntStudentID(intStudent);
            if (line.indexOf("succeed creating dfafile:") != -1) {
              state = STATE_QUESTION_DETAIL;
            }
            break;
          case STATE_QUESTION_DETAIL:
            if (line.indexOf("[CALL_SentenceGrammar.java] : target sentence is") != -1) {
              if (sdm != null && sdm.getStrSpeechAnswer().length() > 0) {
                // first, save to a file;

                // then, save to the database
                String strValues = sdm.toVALUES();
                logger.debug("before add to our database,strValues is [" + strValues + "]");
                dm.insertRecord(strTableName, strFields, strValues);
                intTotalSentence++;
              }
              state = STATE_NEXT_QUESTION;
            } else if (line.indexOf("User Text Answer:") != -1) {
              String strSentence = line.substring(line.indexOf("Answer:") + 7);
              sdm.setStrTextAnswer(strSentence.trim());
              sdm.setStrListeningAnswer(strSentence.trim());
            }
            // else if(line.indexOf("Model Answer is:")!= -1){
            // String strSentence =
            // line.substring(line.indexOf("Answer is:")+10);
            // sdm.setStrTargetAnswer(strSentence.trim());
            // }
            else if (line.indexOf("Model Answer Kana is:") != -1) {
              String strSentence = line.substring(line.indexOf("Answer Kana is:") + 15);
              sdm.setStrTargetAnswer(strSentence.trim());
            } else if (line.indexOf("your Answer is:") != -1) {
              String strSentence = line.substring(line.indexOf("Answer is:") + 10);
              sdm.setStrSpeechAnswer(strSentence.trim());
            } else if (line.indexOf("#eof") != -1) {
              if (sdm != null && sdm.getStrSpeechAnswer().length() > 0) {
                // first, save to a file;

                // then, save to the database
                String strValues = sdm.toVALUES();
                logger.debug("before add to our database,strValues is [" + strValues + "]");
                dm.insertRecord(strTableName, strFields, strValues);
                intTotalSentence++;
              }
            }
            break;
        }

      }
      line = br.readLine();
    }
    dm.releaseConn();

    logger.debug("studentID: " + intStudent + "	sentence number: " + intTotalSentence);
  }

  private void loadTextSentencesFromOneLogFile(String strTableName, String strFields, File oneFile) throws IOException {
    state = 0;
    // String name = oneFile.getParent();
    // name = name.substring(0,name.indexOf("_"));
    String name = oneFile.getName();
    StringTokenizer st = new StringTokenizer(name, "_");
    String str = "0";
    if (st.hasMoreElements()) {
      str = (String) st.nextElement();
      if (st.hasMoreElements()) {
        str = (String) st.nextElement();
      }
    }
    if (st.hasMoreElements()) {
      str = (String) st.nextElement();
    }

    int intStudent = Integer.parseInt(str);

    TextSentenceDataMeta tsdm = null;
    br = new BufferedReader(new FileReader(oneFile));

    String strOdbc = FindConfig.getConfig().findStr(OdbcName);
    dm.connectToAccess(strOdbc);

    boolean token = false; // keep to remember if the sentence is wrong;
    int intTotalSentence = 0;
    String line;
    line = br.readLine();
    while (!token) {
      if (line == null) {
        continue;
      } else if (line.trim().equalsIgnoreCase("#eof")) {

        token = true;
      } else {
        switch (state) {
          case STATE_NEW_SESSION:
            if (line.indexOf("Flow: Usage: Selected lesson") != -1) {
              tsdm = new TextSentenceDataMeta();
              tsdm.setIntStudentID(intStudent);
              state = STATE_NEW_LESSON;
            }
            break;
          case STATE_NEW_LESSON:
            if (line.indexOf("Flow: Usage: Practice started, lesson") != -1) {

              String strLesson = line.substring(line.indexOf("lesson") + 7);
              strLesson = strLesson.trim();
              int intLesson = Integer.parseInt(strLesson) + 1;
              strLesson = Integer.toString(intLesson);
              tsdm.setStrLesson(strLesson);

            } else if (line.indexOf("=NEXT QUESTION") != -1) {
              state = STATE_NEXT_QUESTION;
            }
            break;
          case STATE_NEXT_QUESTION:
            if (line.indexOf("Flow: Usage: Lesson") != -1) {
              String strLesson = line.substring(line.indexOf("Lesson") + 6, line.indexOf(","));
              strLesson = strLesson.trim();
              int intLesson = Integer.parseInt(strLesson);
              strLesson = Integer.toString(intLesson);
              tsdm.setStrLesson(strLesson);

              String strQuestion = line.substring(line.indexOf("Question") + 9).trim();
              tsdm.setStrQuestion(strQuestion);
              state = STATE_ERROR_DETAIL;
            }
            break;
          case STATE_ERROR_DETAIL:
            if (line.indexOf("I: Errors:") != -1) {
              // this means sentence contain wrong;
              tsdm.setBooTextCorrect(false);
              state = STATE_ERRORSENTENCE;
              // token =true;
            } else if (line.indexOf("Flow: Usage: Lesson Ends") != -1 || line.indexOf("Flow: Usage: Lesson Quit") != -1) {
              state = STATE_NEW_LESSON;
            } else if (line.indexOf("Flow: Usage: Next Question") != -1) {
              state = STATE_QUESTION_END;
            } else if (line.indexOf("Usage: Given Answer:") != -1) {
              String strInputSentence = line.substring(line.indexOf("Answer:") + 7);
              strInputSentence = strInputSentence.trim();
              tsdm.setStrInputSentence(strInputSentence);
              tsdm.setBooTextCorrect(true);
              state = STATE_CORRETSENTENCE;
            } else if (line.indexOf("Flow: Usage: Example Skipped") != -1) {
              state = STATE_QUESTION_END;
            }
            break;
          case STATE_ERRORSENTENCE:
            if (line.indexOf("Res: Usage: Given Answer:") != -1) {
              String strInputSentence = line.substring(line.indexOf("Answer:") + 7);
              strInputSentence = strInputSentence.trim();
              tsdm.setStrInputSentence(strInputSentence);
            } else if (line.indexOf("Res: Usage: Matched Answer:") != -1) {
              String strTargetSentence = line.substring(line.indexOf("Answer:") + 7);
              strTargetSentence = strTargetSentence.trim();
              tsdm.setStrTargetSentence(strTargetSentence);
              // judge the tsdm or not?
              // pw.println(tsdm.toString()); // change these datas to DB

              // String strFields =
              // "(StudentID,Lesson,Question,InputSentence,TargetSentence,SentenceLevelError)";
              String strValues = tsdm.toVALUES();
              // System.out.println("before add to our database,strValues is ["+strValues+"]");
              dm.insertRecord(strTableName, strFields, strValues);
              intTotalSentence++;
            } else if (line.indexOf("Flow: Usage: Lesson Ends") != -1 || line.indexOf("Flow: Usage: Lesson Quit") != -1) {
              state = STATE_NEW_LESSON;
            } else if (line.indexOf("Flow: Usage: Next Question") != -1) {
              state = STATE_QUESTION_END;
            }
            break;
          case STATE_CORRETSENTENCE:
            if (line.indexOf("Res: Usage: Matched Answer:") != -1) {
              String strTargetSentence = line.substring(line.indexOf("Answer:") + 7);
              strTargetSentence = strTargetSentence.trim();
              tsdm.setStrTargetSentence(strTargetSentence);
              // change these datas to DB
              // String strFields =
              // "(StudentID,Lesson,Question,InputSentence,TargetSentence,SentenceLevelError)";
              String strValues = tsdm.toVALUES();
              // System.out.println("before add to our database,strValues is ["+strValues+"]");
              dm.insertRecord(strTableName, strFields, strValues);
              intTotalSentence++;
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
        } // end switch
      } // end else

      line = br.readLine();
    }
    dm.releaseConn();

    logger.debug("studentID: " + intStudent + "	sentence number: " + intTotalSentence);
  }

  private void loadRSentencesFromOneLogFile(File oneFile) throws IOException {
    state = 0;
    String name = oneFile.getName();
    name = name.substring(0, name.indexOf("-"));
    int intStudent = Integer.parseInt(name);

    TextSentenceDataMeta tsdm = null;
    br = new BufferedReader(new FileReader(oneFile));

    dm.connectToAccess("history");
    boolean token = false; // keep to remember if the sentence is wrong;
    int intTotalSentence = 0;
    String line;
    line = br.readLine();
    while (line != null) {
      switch (state) {
        case STATE_NEW_SESSION:
          if (line.indexOf("Flow: Usage: Selected lesson") != -1) {
            tsdm = new TextSentenceDataMeta();
            tsdm.setIntStudentID(intStudent);
            state = STATE_NEW_LESSON;
          }
          break;
        case STATE_NEW_LESSON:
          if (line.indexOf("Flow: Usage: Practice started, lesson") != -1) {
            // tsdm = new TextSentenceDataMeta();
            // tsdm.setIntStudentID(intStudent);
            String strLesson = line.substring(line.indexOf("lesson") + 7);
            strLesson = strLesson.trim();
            int intLesson = Integer.parseInt(strLesson) + 1;
            strLesson = Integer.toString(intLesson);
            tsdm.setStrLesson(strLesson);

          } else if (line.indexOf("=NEXT QUESTION") != -1) {
            state = STATE_NEXT_QUESTION;
          }
          break;
        case STATE_NEXT_QUESTION:
          if (line.indexOf("Flow: Usage: Lesson") != -1) {
            // String strLesson
            // =line.substring(line.indexOf("Lesson")+7,line.indexOf(","));
            // tsdm.setStrLesson(strLesson.trim());
            String strQuestion = line.substring(line.indexOf("Question") + 9).trim();
            tsdm.setStrQuestion(strQuestion);
            state = STATE_ERROR_DETAIL;
          }
          break;
        case STATE_ERROR_DETAIL:
          if (line.indexOf("I: Errors:") != -1) {
            // this means sentence contain wrong;
            tsdm.setBooTextCorrect(false);
            state = STATE_ERRORSENTENCE;
            // token =true;
          } else if (line.indexOf("Flow: Usage: Lesson Ends") != -1 || line.indexOf("Flow: Usage: Lesson Quit") != -1) {
            state = STATE_NEW_LESSON;
          } else if (line.indexOf("Flow: Usage: Next Question") != -1) {
            state = STATE_QUESTION_END;
          } else if (line.indexOf("Usage: Given Answer:") != -1) {
            String strInputSentence = line.substring(line.indexOf("Answer:") + 7);
            strInputSentence = strInputSentence.trim();
            tsdm.setStrInputSentence(strInputSentence);
            tsdm.setBooTextCorrect(true);
            state = STATE_CORRETSENTENCE;
          } else if (line.indexOf("Flow: Usage: Example Skipped") != -1) {
            state = STATE_QUESTION_END;
          }
          break;
        case STATE_ERRORSENTENCE:
          /*
           * skip it if(line.indexOf("Res: Usage: Given Answer:")!= -1){ String
           * strInputSentence = line.substring(line.indexOf("Answer:")+7);
           * strInputSentence =strInputSentence.trim();
           * tsdm.setStrInputSentence(strInputSentence); }else
           * if(line.indexOf("Res: Usage: Matched Answer:")!= -1){ String
           * strTargetSentence = line.substring(line.indexOf("Answer:")+7);
           * strTargetSentence =strTargetSentence.trim();
           * tsdm.setStrTargetSentence(strTargetSentence); //judge the tsdm or
           * not? //pw.println(tsdm.toString()); // change these datas to DB
           * String strFields =
           * "(StudentID,Lesson,Question,InputSentence,TargetSentence,SentenceLevelError)"
           * ; String strValues = tsdm.toVALUES(); //
           * System.out.println("before add to our database,strValues is ["
           * +strValues+"]"); dm.insertRecord(SentencesTable, strFields,
           * strValues); intTotalSentence++; }else
           */if (line.indexOf("Flow: Usage: Lesson Ends") != -1 || line.indexOf("Flow: Usage: Lesson Quit") != -1) {
            state = STATE_NEW_LESSON;
          } else if (line.indexOf("Flow: Usage: Next Question") != -1) {
            state = STATE_QUESTION_END;
          }
          break;
        case STATE_CORRETSENTENCE:
          if (line.indexOf("Res: Usage: Matched Answer:") != -1) {
            String strTargetSentence = line.substring(line.indexOf("Answer:") + 7);
            strTargetSentence = strTargetSentence.trim();
            tsdm.setStrTargetSentence(strTargetSentence);
            // change these datas to DB
            String strFields = "(StudentID,Lesson,Question,InputSentence,TargetSentence,SentenceLevelError)";
            String strValues = tsdm.toVALUES();
            // System.out.println("before add to our database,strValues is ["+strValues+"]");
            dm.insertRecord(RSentencesTable, strFields, strValues);
            intTotalSentence++;
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
    dm.releaseConn();

    logger.debug("studentID: " + intStudent + "	sentence number: " + intTotalSentence);
  }

  /*
   * for error sentence
   * 
   * @throws ClassNotFoundException
   * 
   * @throws IOException
   */
  public void loadErrSentenceFromlogFiles() throws ClassNotFoundException, IOException {
    // delete all the data and create such a new table
    boolean bResult = false;
    dm.connectToAccess("history");
    // String strOldTableName = "Sentences";
    createTextSentenceTable(ErrSentencesTable);

    Configuration configuration = Configuration.getConfig();

    System.out.println("after instantiate its own class");
    String logfilepath = configuration.getItemValue("Data", "TrialLogs");
    System.out.println(logfilepath);
    File filePath = new File(logfilepath);
    if (!filePath.exists()) {
      System.out.println("goes wrong,log file does not exist");
    } else {
      System.out.println(filePath.getName());
      File[] arrayFile = filePath.listFiles();
      for (int i = 0; i < arrayFile.length; i++) {
        File fileTemp = arrayFile[i];
        if (fileTemp.isFile()) {
          sentenceErrFileAnalyze(fileTemp);
        }
      }
    }

  }

  private void sentenceErrFileAnalyze(File oneFile) throws IOException {
    state = 0;
    String name = oneFile.getName();
    name = name.substring(0, name.indexOf("-"));
    int intStudent = Integer.parseInt(name);

    TextSentenceDataMeta tsdm = null;

    File tempFile = new File(".//Data//tempLogFile.txt");
    br = new BufferedReader(new FileReader(oneFile));
    pw = new PrintWriter(new FileOutputStream(tempFile), true);
    dm.connectToAccess("history");

    String line;
    line = br.readLine();
    while (line != null) {
      switch (state) {
        case STATE_NEW_SESSION:
          if (line.indexOf("Flow: Usage: Selected lesson") != -1) {
            tsdm = new TextSentenceDataMeta();
            tsdm.setIntStudentID(intStudent);
            state = STATE_NEW_LESSON;
          }
          break;
        case STATE_NEW_LESSON:
          if (line.indexOf("Flow: Usage: Practice started, lesson") != -1) {
            // tsdm = new TextSentenceDataMeta();
            // tsdm.setIntStudentID(intStudent);
            String strLesson = line.substring(line.indexOf("lesson") + 7);
            strLesson = strLesson.trim();
            int intLesson = Integer.parseInt(strLesson) + 1;
            strLesson = Integer.toString(intLesson);
            tsdm.setStrLesson(strLesson);

          } else if (line.indexOf("=NEXT QUESTION") != -1) {
            state = STATE_NEXT_QUESTION;
          }
          break;
        case STATE_NEXT_QUESTION:
          if (line.indexOf("Flow: Usage: Lesson") != -1) {
            // String strLesson
            // =line.substring(line.indexOf("Lesson")+7,line.indexOf(","));
            // tsdm.setStrLesson(strLesson.trim());
            String strQuestion = line.substring(line.indexOf("Question") + 9).trim();
            tsdm.setStrQuestion(strQuestion);
            state = STATE_ERROR_DETAIL;
          }
          break;
        case STATE_ERROR_DETAIL:
          if (line.indexOf("I: Errors:") != -1) {
            tsdm.setBooTextCorrect(false);
            state = STATE_SENTENCE;
          } else if (line.indexOf("Flow: Usage: Lesson Ends") != -1 || line.indexOf("Flow: Usage: Lesson Quit") != -1) {
            state = STATE_NEW_LESSON;
          } else if (line.indexOf("Flow: Usage: Next Question") != -1) {
            state = STATE_QUESTION_END;
          }
          break;
        case STATE_SENTENCE:

          if (line.indexOf("Res: Usage: Given Answer:") != -1) {
            String strInputSentence = line.substring(line.indexOf("Answer:") + 7);
            strInputSentence = strInputSentence.trim();
            tsdm.setStrInputSentence(strInputSentence);
          } else if (line.indexOf("Res: Usage: Matched Answer:") != -1) {
            String strTargetSentence = line.substring(line.indexOf("Answer:") + 7);
            strTargetSentence = strTargetSentence.trim();
            tsdm.setStrTargetSentence(strTargetSentence);
            // judge the tsdm or not?
            // pw.println(tsdm.toString()); // change these datas to DB
            String strFields = "(StudentID,Lesson,Question,InputSentence,TargetSentence,SentenceLevelError)";
            String strValues = tsdm.toVALUES();
            System.out.println("before add to our database,strValues is [" + strValues + "]");
            dm.insertRecord(ErrSentencesTable, strFields, strValues);

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
    dm.releaseConn();
    // System.out.println("intStudent ["+i+"] is" + intStudent);

  }

  public void loadErrWordFromLogfiles(String strTableName, String strStage) throws ClassNotFoundException, IOException {

    createTextWordTable(strTableName);

    String logfilepath = FindConfig.getConfig().findStr(logPath);
    logger.debug("get log file path: " + logfilepath);

    File filePath = new File(logfilepath);
    if (!filePath.exists()) {
      logger.error("goes wrong,does not exist log file in path: " + logfilepath);
    } else {
      logger.debug(filePath.getName());

      File[] arrayFile = filePath.listFiles();
      for (int i = 0; i < arrayFile.length; i++) {
        File fileTemp = arrayFile[i];
        if (fileTemp.isDirectory()) {
          // load data one by one
          String oneFileName = "log_text_" + fileTemp.getName() + "_" + strStage.trim().toLowerCase() + ".txt";
          File oneFile = new File(fileTemp.getPath(), oneFileName);
          if (oneFile.exists()) {
            wordErrFileAnalyze(strTableName, strTextWordErrFields, oneFile);
          } else {
            logger.error("No exist, file: " + oneFile.getAbsolutePath());

          }

        } else {
          logger.error("Not exist such a directory: " + fileTemp.getAbsolutePath());
        }
      }
    }

  }

  private void wordErrFileAnalyze(String strTableName, String strFields, File oneFile) throws IOException {
    state = 0;
    String name = oneFile.getName();
    StringTokenizer st = new StringTokenizer(name, "_");
    String str = "0";
    if (st.hasMoreElements()) {
      str = (String) st.nextElement();
      if (st.hasMoreElements()) {
        str = (String) st.nextElement();
      }
    }
    if (st.hasMoreElements()) {
      str = (String) st.nextElement();
    }
    int intStudent = Integer.parseInt(str);

    WordErrDataMeta wedm = null;

    File tempFile = new File(".//Data//tempLogFile.txt");
    br = new BufferedReader(new FileReader(oneFile));
    pw = new PrintWriter(new FileOutputStream(tempFile), true);

    String strOdbc = FindConfig.getConfig().findStr(OdbcName);
    dm.connectToAccess(strOdbc);

    boolean token = false; // keep to remember if the file is end;

    String line;
    line = br.readLine();
    while (!token) {
      if (line == null) {
        continue;
      } else if (line.trim().equalsIgnoreCase("#eof")) {
        token = true;
      } else {
        switch (state) {
          case STATE_NEW_SESSION:
            if (line.indexOf("Flow: Usage: Selected lesson") != -1) {
              wedm = new WordErrDataMeta();
              wedm.setIntStudentID(intStudent);
              state = STATE_NEW_LESSON;
            }
            break;
          case STATE_NEW_LESSON:
            if (line.indexOf("Flow: Usage: Practice started, lesson") != -1) {
              String strLesson = line.substring(line.indexOf("lesson") + 7).trim();
              int intLesson = Integer.parseInt(strLesson) + 1;
              strLesson = Integer.toString(intLesson);
              wedm.setStrLesson(strLesson);
            } else if (line.indexOf("=NEXT QUESTION") != -1) {
              state = STATE_NEXT_QUESTION;
            }
            break;
          case STATE_NEXT_QUESTION:
            if (line.indexOf("Flow: Usage: Lesson") != -1) {
              String strQuestion = line.substring(line.indexOf("Question") + 9).trim();
              wedm.setStrQuestion(strQuestion);
              state = STATE_ERROR_DETAIL;
            }
            break;
          case STATE_ERROR_DETAIL:
            if (line.indexOf("I: Errors:") != -1) {
              // tsdm.setBooTextCorrect(false);
              if (line.indexOf("I: Errors: SUBSTITUTION ERROR") != -1) {
                state = STATE_ERROE_SUBSTITION;
              }
              // state = STATE_SENTENCE;
            } else if (line.indexOf("Flow: Usage: Lesson Ends") != -1 || line.indexOf("Flow: Usage: Lesson Quit") != -1) {
              state = STATE_NEW_LESSON;
            } else if (line.indexOf("Flow: Usage: Next Question") != -1) {
              state = STATE_QUESTION_END;
            }
            break;
          case STATE_ERROE_SUBSTITION:
            if (line.indexOf("I: Errors: Target:") != -1) {
              String strTargetWord = line.substring(line.indexOf("Target:") + 7).trim();
              wedm.setStrTargetWord(strTargetWord);
            } else if (line.indexOf("I: Errors: Observed:") != -1) {
              String strObservedWord = line.substring(line.indexOf("Observed:") + 9).trim();
              wedm.setStrObservedWord(strObservedWord);
            } else if (line.indexOf("I: Errors: Full Rule") != -1) {
              String strFullGrammar = line.substring(line.indexOf("Rule:") + 5).trim();
              wedm.setStrFullGrammar(strFullGrammar);
            } else if (line.indexOf("I: Errors: Base Rule:") != -1) {
              String strBaseGrammar = line.substring(line.indexOf("Rule:") + 5).trim();
              wedm.setStrBaseGrammar(strBaseGrammar);
            } else if (line.indexOf("I: Errors: Type:") != -1) {
              String strTargetType = line.substring(line.indexOf("Type:") + 5).trim();
              wedm.setStrTargetType(strTargetType);
            } else if (line.indexOf("I: Errors: Same Type:") != -1) {
              String strType = line.substring(line.indexOf("Type: ") + 5).trim();
              if (strType.equalsIgnoreCase("True")) {
                wedm.setBooSameType(true);
                wedm.setStrObservedType(wedm.getStrTargetType());
                wedm.setBooValid(true);
              } else if (strType.startsWith("False")) {
                strType = strType.substring(strType.indexOf("False") + 5).trim();
                wedm.setBooSameType(false);
                wedm.setStrObservedType(strType);
                if (!strType.equalsIgnoreCase("UNK")) {
                  wedm.setBooValid(true);
                }
                // else{//determine the booInvalidform and booOneStepErrW
                // strFormMistakes strFormDistance

                // }
              }
            } else if (line.indexOf("I: Errors: Same Stem:") != -1) {
              String strSameStem = line.substring(line.indexOf("Stem:") + 5).trim();
              if (strSameStem.equalsIgnoreCase("True")) {
                wedm.setBooSameStem(true);
              }

            } else if (line.indexOf("I: Errors: Probably Spelling Error:") != -1) {
              // Likely,Unlikely
              String strSpell = line.substring(line.indexOf("Error:") + 6).trim();
              if (strSpell.equalsIgnoreCase("Likely")) {
                wedm.setBooSpell(true);
              }
              // else if(strSpell.equalsIgnoreCase("Unlikely")){
              // wedm.setBooSpell(false);
              // }
              // ---------save this error data ---------------------
              // String strFields =
              // "(StudentID,Lesson,Question,BaseGrammar,FullGrammar,ObservedWord,TargetWord,ObservedType,TargetType,SameType,Valid,SameConfusionGroup,Spell,EnglishStem,SameStem)";
              String strValues = wedm.toVALUES();
              pw.println(wedm.toString());
              System.out.println("strValues is [" + strValues + "]");
              dm.insertRecord(strTableName, strFields, strValues);

              state = STATE_ERROR_DETAIL;
            }// else if(line.indexOf("I: Errors: SUBSTITUTION ERROR")!= -1){
            break;

          // }
          case STATE_SENTENCE:

            if (line.indexOf("Res: Usage: Given Answer:") != -1) {
              String strInputSentence = line.substring(line.indexOf("Answer:") + 7);
              strInputSentence = strInputSentence.trim();
              // tsdm.setStrInputSentence(strInputSentence);
            } else if (line.indexOf("Res: Usage: Matched Answer:") != -1) {
              String strTargetSentence = line.substring(line.indexOf("Answer:") + 7);
              strTargetSentence = strTargetSentence.trim();
              // tsdm.setStrTargetSentence(strTargetSentence);
              // judge the tsdm or not?
              // pw.println(tsdm.toString()); // change these datas to DB
              // String strFields =
              // "(StudentID,Lesson,Question,InputSentence,TargetSentence,SentenceLevelError)";
              // String strValues = tsdm.toVALUES();
              // System.out.println("before add to our database,strValues is ["+strValues+"]");
              // dm.insertRecord("Sentences", strFields, strValues);

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
      }
      line = br.readLine();
    }
    dm.releaseConn();
    pw.close();
    // System.out.println("intStudent ["+i+"] is" + intStudent);

  }

  /**
   * @param strSourceTableName
   *          TextSentenceTableName
   * @param strTargetTableName
   *          SpeakSentenceTableName
   * @param strTargetTableFields
   *          SpeakSentenceTableFields Find Lesson number in TextSentenceTable
   *          and add to SpeakSentenceTable
   * @throws SQLException
   */
  public Vector collectLessonInfo(String strSourceTableName, String strTargetTableName) throws SQLException {
    // open the database
    String strOdbc = FindConfig.getConfig().findStr(OdbcName);
    dm.connectToAccess(strOdbc);
    Vector<String[]> vecSource = new Vector<String[]>();

    String sql = "SELECT * FROM " + strSourceTableName;
    ResultSet rs = dm.executeQuery(sql);
    while (rs.next()) {// still remains sentences
      int intId = rs.getInt("ID");
      int intStudentID = rs.getInt("StudentID");
      String strLesson = rs.getString("Lesson");
      String strQuestion = rs.getString("Question");

      String strObservedSentence = rs.getString("InputSentence");
      String strTargetSentence = rs.getString("TargetSentence");
      logger.debug(strTargetSentence);
      // String strObservedSentence = rs.getString("strTextAnswer");
      // String strTargetSentence= rs.getString("strTargetAnswer");

      String sqlTarget = "SELECT * FROM " + strTargetTableName + " WHERE StudentID=" + intStudentID
          + " AND strTextAnswer='" + strObservedSentence + "'";
      ResultSet rsTarget = dm.executeQuery(sqlTarget);
      if (rsTarget.next()) {
        String[] strArray = new String[3];
        int intID = rsTarget.getInt("ID");
        strArray[0] = Integer.toString(intID);
        strArray[1] = strLesson;
        strArray[2] = strQuestion;
        vecSource.addElement(strArray);
      } else {
        logger.error("no record: " + intStudentID + " strTextAnswer: " + strObservedSentence);
      }

      // String strSetValues =
      // "Lesson='"+strLesson+"'"+",Question='"+strQuestion+"'";
      // String strWhere = "WHERE (StudentID="+ intStudentID
      // +") AND (strTextAnswer='"+strObservedSentence+"')";
      // boolean rsTarget =
      // dm.modifyRecordNoRelease(strTargetTableName,strSetValues,strWhere);
      // //
      // if(rsTarget){
      //
      // logger.warn("update a record: "+
      // intStudentID+" strTextAnswer: "+strObservedSentence);
      // }

    }

    dm.releaseConn();
    return vecSource;
  }

  public void addLessonInfo(String strSourceTableName, String strTargetTableName) throws SQLException {

    String strOdbc = FindConfig.getConfig().findStr(OdbcName);

    Vector v = collectLessonInfo(strSourceTableName, strTargetTableName);

    if (v != null && v.size() > 0) {
      for (int i = 0; i < v.size(); i++) {
        String[] strArray = (String[]) v.elementAt(i);
        int id = Integer.parseInt(strArray[0]);
        String strSetValues = "Lesson='" + strArray[1] + "'" + ",Question='" + strArray[2] + "'";
        String strWhere = "WHERE ID=" + id;
        dm.modifyRecord(strOdbc, strTargetTableName, strSetValues, strWhere);
      }
    }
  }

  public static void main(String args[]) throws IOException, Exception {
    NewLogParser nlp = new NewLogParser();
    // sp.loadRSentencesFromLogFiles();
    // lffoc.loadErrSentenceFromlogFiles();
    // nlp.loadSentencesFromLogFiles(SpeakSentencesTable1,"speak","1");
    // nlp.loadSentencesFromLogFiles(SpeakSentencesTable2,"speak","2");

    // nlp.loadSentencesFromLogFiles(SpeakSentencesTable3,"speak","2");
    // nlp.loadSentencesFromLogFiles("SpeechSentencesStageTwo_BaseLine","speak","2");
    nlp.loadSentencesFromLogFiles("SpeechSentencesStageTwo_GeneralMethod", "speak", "2");
    // strTextWordErrFields
    // nlp.loadSentencesFromLogFiles(TextSentencesTable1,"text","1");
    // nlp.loadSentencesFromLogFiles(TextSentencesTable2,"text","2");

    // nlp.loadErrWordFromLogfiles(TextWordErrorTable1,"1");
    // nlp.loadErrWordFromLogfiles(TextWordErrorTable2,"2");
    // WordTable1 , WordTable2
    // nlp.addLessonInfo(TextSentencesTable2, SpeakSentencesTable2);

    // DataManager dm = new DataManager();
    // String strOdbc = FindConfig.getConfig().findStr(OdbcName);
    // dm.connectToAccess(strOdbc);

    // String strObservedSentence = "���o���� �� ���񂲂� �ł���";
    // //+" WHERE StudentID=10" + " AND strTextAnswer='"+strObservedSentence+"'"
    // String sqlTarget = "SELECT * FROM "+TextSentencesTable2;
    // ResultSet rsTarget = dm.executeQuery(sqlTarget);
    // if(rsTarget.next()){
    // String[] strArray = new String[3];
    // int intID = rsTarget.getInt("ID");
    // System.out.println(intID);
    // // strArray[0]= Integer.toString(intID);
    // // strArray[1]= strLesson;
    // // strArray[2]= strQuestion;
    // // vecSource.addElement(strArray);
    // }else{
    // logger.error("no strTextAnswer: "+strObservedSentence);
    // }
    //
    // dm.releaseConn();
    // }
  }

}
