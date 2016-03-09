/**
 * Created on 2007/06/15
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

import jcall.config.Configuration;
import jcall.recognition.database.DataManager;
import jcall.recognition.database.TextSentenceDataMeta;
import jcall.recognition.database.WordErrDataMeta;

import org.apache.log4j.Logger;

public class OldCallLogParser {
  static Logger logger = Logger.getLogger(OldCallLogParser.class.getName());

  public final static String SentencesTable = "AllSentences";
  final static String RSentencesTable = "RightSentences";
  final static String ErrSentencesTable = "ErrSentences";

  public final static String WordTableName = "WordErrTable";
  public final static String RSentencesTable2 = "Sentences"; // same with
                                                             // "RightSentences"
  public final static String FirstOdbcName = "FirstExperimentODBC";

  int state;
  static final int STATE_NEW_SESSION = 0;
  static final int STATE_NEW_LESSON = 1;
  static final int STATE_NEXT_QUESTION = 2;
  static final int STATE_ERROR_DETAIL = 3;
  static final int STATE_ERRORSENTENCE = 4;
  static final int STATE_CORRETSENTENCE = 5;
  static final int STATE_QUESTION_END = 6;
  static final int STATE_ERROE_SUBSTITION = 7;

  static final int STATE_SENTENCE = 8; // for error table

  DataManager dm;
  BufferedReader br;
  PrintWriter pw;

  public OldCallLogParser() {
    init();
  }

  private void init() {
    dm = new DataManager();
  }

  /*
   * read all setences to AllSentences table of "history" db;
   */
  public void loadSentencesFromLogFiles() throws ClassNotFoundException, IOException {
    // create table "correctSentences"
    createTable(SentencesTable);
    // then get logfile path from configuration
    Configuration configuration = Configuration.getConfig();
    logger.debug("opened config file: " + "JCALLConfig.xml");
    String logfilepath = configuration.getItemValue("Data", "TrialLogs");
    logger.debug("get log file path: " + logfilepath);
    File filePath = new File(logfilepath);
    if (!filePath.exists()) {
      logger.debug("goes wrong,does not exist log file in path: " + logfilepath);
    } else {
      logger.debug(filePath.getName());
      // System.out.println(filePath.getName());
      File[] arrayFile = filePath.listFiles();
      for (int i = 0; i < arrayFile.length; i++) {
        File fileTemp = arrayFile[i];
        if (fileTemp.isFile()) {
          // load data one by one
          loadSentencesFromOneLogFile(fileTemp);
        }
      }
    }
  }

  /*
   * read all correct setences to RightSentences table to "history" db;
   */
  public void loadRSentencesFromLogFiles() throws ClassNotFoundException, IOException {
    // create table "correctSentences"
    createTable(RSentencesTable);
    // then get logfile path from configuration
    Configuration configuration = Configuration.getConfig();

    logger.debug("opened config file: " + "JCALLConfig.xml");
    String logfilepath = configuration.getItemValue("Data", "TrialLogs");
    logger.debug("get log file path: " + logfilepath);
    File filePath = new File(logfilepath);
    if (!filePath.exists()) {
      logger.debug("goes wrong,does not exist log file in path: " + logfilepath);
    } else {
      logger.debug(filePath.getName());
      // System.out.println(filePath.getName());
      File[] arrayFile = filePath.listFiles();
      for (int i = 0; i < arrayFile.length; i++) {
        File fileTemp = arrayFile[i];
        if (fileTemp.isFile()) {
          // load data one by one
          loadRSentencesFromOneLogFile(fileTemp);
        }
      }
    }
  }

  public void createTable(String tablename) throws ClassNotFoundException, IOException {
    // delete all the data and create a new table
    boolean bResult = false;
    dm.connectToAccess("history");
    String sql = "drop table " + tablename;
    bResult = dm.execute(sql);
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
      logger.debug("error, create no new table--" + tablename);
    } else {
      logger.debug("create a new table--" + tablename);
    }
    dm.releaseConn();
  }

  private void loadSentencesFromOneLogFile(File oneFile) throws IOException {
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
            // System.out.println("before add to our database,strValues is ["+strValues+"]");
            dm.insertRecord(SentencesTable, strFields, strValues);
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
            String strFields = "(StudentID,Lesson,Question,InputSentence,TargetSentence,SentenceLevelError)";
            String strValues = tsdm.toVALUES();
            // System.out.println("before add to our database,strValues is ["+strValues+"]");
            dm.insertRecord(SentencesTable, strFields, strValues);
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
    createTable(ErrSentencesTable);

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

  public void DataProcessTest() throws ClassNotFoundException, IOException {

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

      File fileTemp = arrayFile[0];
      if (fileTemp.isFile()) {
        wordErrFileAnalyze(fileTemp);
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

  public void loadErrWordFromLogfiles() throws ClassNotFoundException, IOException {
    boolean bResult = false;
    dm.connectToAccess("history");
    String sql = "drop table " + WordTableName;
    bResult = dm.execute(sql);
    if (bResult) {
      System.out.println("true, not drop a new table");
    } else {
      System.out.println("false, drop a new table");
    }
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

    // dm.truncateTable(strwordTableName, strwordTableName, strWordTableSetup);
    bResult = dm.createTable(WordTableName, strWordTableSetup);
    if (bResult) {
      System.out.println("not create a new table");
    } else {
      System.out.println("create a new table");
    }
    dm.releaseConn();

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
          wordErrFileAnalyze(fileTemp);
        }
      }
    }

  }

  private void wordErrFileAnalyze(File oneFile) throws IOException {
    state = 0;
    String name = oneFile.getName();
    name = name.substring(0, name.indexOf("-"));
    int intStudent = Integer.parseInt(name);

    WordErrDataMeta wedm = null;

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
            String strFields = "(StudentID,Lesson,Question,BaseGrammar,FullGrammar,ObservedWord,TargetWord,ObservedType,TargetType,SameType,Valid,SameConfusionGroup,Spell,EnglishStem,SameStem)";
            String strValues = wedm.toVALUES();
            // pw.println(wedm.toString());
            System.out.println("before add to our database,strValues is [" + strValues + "]");
            dm.insertRecord(WordTableName, strFields, strValues);

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
            String strFields = "(StudentID,Lesson,Question,InputSentence,TargetSentence,SentenceLevelError)";
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
      line = br.readLine();
    }
    dm.releaseConn();
    // System.out.println("intStudent ["+i+"] is" + intStudent);

  }

  public static void main(String args[]) throws IOException, Exception {
    OldCallLogParser lffoc = new OldCallLogParser();
    // sp.loadRSentencesFromLogFiles();
    // lffoc.loadErrSentenceFromlogFiles();
    lffoc.loadSentencesFromLogFiles();
  }

}
