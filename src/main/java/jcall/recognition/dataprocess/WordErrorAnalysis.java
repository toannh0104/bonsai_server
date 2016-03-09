/**
 * Created on 2007/09/29
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import java.util.Vector;

import jcall.config.FindConfig;
import jcall.recognition.database.DataManager;
import jcall.recognition.database.WordDataMeta;

import org.apache.log4j.Logger;

public class WordErrorAnalysis {

  static Logger logger = Logger.getLogger(WordErrorAnalysis.class.getName());

  BufferedReader br;
  PrintWriter pw;
  int state;

  final static String strWordFields = "(StudentID,Lesson,Question,TargetWord,ObservedWord,RecognitionWord,ListenWord,TargetType,IsSameRecognitionWithObserved,IsSameObservedWithTarget,IsSameRecognitionWithListen,IsSameListenWithTarget,IsSameObservedWithListen,IsSameObservedWithRecognition,GeneralType,SpecificType,FormMistakes,strFormDistance)";
  final static String strWordFields_Listen = "(StudentID,Lesson,Question,TargetWord,ListenWord,TargetType,SpecificType,FormMistakes,strFormDistance)";
  // final static String strWordFields_Recognize =
  // "(StudentID,Lesson,Question,TargetWord,RecognitionWord,TargetType,SpecificType,FormMistakes,strFormDistance)";
  final static String strWordFields_Observed = "(StudentID,Lesson,Question,TargetWord,ObservedWord,TargetType,SpecificType,FormMistakes,strFormDistance)";

  final static String strWordFields_ListenRecognition = "(StudentID,Lesson,Question,TargetWord,ObservedWord,RecognitionWord,ListenWord,TargetType,SpecificType,FormMistakes,strFormDistance)";

  DataManager dm;

  final static String sentenceTablename_OneSpeech = "SOneSpeechTable";
  final static String sentenceTablename_TwoSpeech = "STwoSpeechTable";

  public WordErrorAnalysis() {
    dm = new DataManager();

  }

  /*
   * CHANGE THE WRONG TYPE
   */
  public Vector wordProcess(String strwordSpeakSenteceTableName) {

    String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
    dm.connectToAccess(strOdbc);

    Vector<WordDataMeta> v = new Vector<WordDataMeta>();
    int sameCount = 0;
    int WordErrCount = 0;
    int WordRecognitionErrCount = 0;

    // strwordTableName
    // read data into WordErrDataMeta one by one, change attribute on the basis
    // of its type
    try {
      Vector<String> vecTargetWord;
      Vector<String> vecObservedWord;
      Vector<String> vecRecognitionWord;
      Vector<String> vecListenWord;
      // int studentID;
      // String strTargetWord;
      // String strRecognitionWord;

      WordDataMeta objMeta;
      ResultSet tmpRs = dm.searchTable(strwordSpeakSenteceTableName, "");
      while (tmpRs.next()) {
        // objEDMeta.intID = tmpRs.getInt("ID");
        vecTargetWord = new Vector<String>();
        vecObservedWord = new Vector<String>();
        vecRecognitionWord = new Vector<String>();
        vecListenWord = new Vector<String>();

        int studentID = tmpRs.getInt("StudentID");
        String lesson = tmpRs.getString("Lesson");
        String strTargetWord = tmpRs.getString("strTargetAnswer");
        String strObservedWord = tmpRs.getString("strTextAnswer");
        String strRecognitionWord = tmpRs.getString("strSpeechAnswer");
        String strListenWord = tmpRs.getString("strListeningAnswer");

        logger.debug("Draw sentence: " + strTargetWord);

        StringTokenizer stTargetWord = new StringTokenizer(strTargetWord);
        StringTokenizer stObservedWord = new StringTokenizer(strObservedWord);
        StringTokenizer stRecognitionWord = new StringTokenizer(strRecognitionWord);
        StringTokenizer stListenWord = new StringTokenizer(strListenWord);

        while (stTargetWord.hasMoreElements()) {
          String str = (String) stTargetWord.nextElement();
          vecTargetWord.addElement(str);
        }
        while (stObservedWord.hasMoreElements()) {
          String str = (String) stObservedWord.nextElement();
          vecObservedWord.addElement(str);
        }
        while (stRecognitionWord.hasMoreElements()) {
          String str = (String) stRecognitionWord.nextElement();
          vecRecognitionWord.addElement(str);
        }
        while (stListenWord.hasMoreElements()) {
          String str = (String) stListenWord.nextElement();
          vecListenWord.addElement(str);
        }

        if (vecTargetWord.size() == vecListenWord.size() && vecListenWord.size() == vecRecognitionWord.size()
            && vecRecognitionWord.size() == vecObservedWord.size()) {

          for (int i = 0; i < vecTargetWord.size(); i++) {

            String strTarget = vecTargetWord.elementAt(i).trim();
            String strListen = vecListenWord.elementAt(i).trim();
            String strRecognition = vecRecognitionWord.elementAt(i).trim();
            String strObserved = vecObservedWord.elementAt(i).trim();

            if (strListen.equals(strTarget) && strRecognition.equals(strTarget) && strListen.equals(strObserved)) {
              sameCount++;
              // logger.debug("all is the same");
            } else {

              objMeta = new WordDataMeta();
              objMeta.intStudentID = studentID;
              objMeta.strLesson = lesson;
              objMeta.strObservedWord = strObserved;
              objMeta.strListenWord = strListen;
              objMeta.strRecognitionWord = strRecognition;
              objMeta.strTargetWord = strTarget;
              if (i == vecTargetWord.size() - 1) {
                objMeta.strTargetType = "VERB";
              } else if (i % 2 == 0) {
                objMeta.strTargetType = "NOUN";
              } else {
                objMeta.strTargetType = "PARTICLE";
              }
              if (strListen.equals(strTarget)) {
                objMeta.IsSameListenWithTarget = true;
              } else {
                objMeta.IsSameListenWithTarget = false;
              }

              if (strRecognition.equals(strListen)) {
                objMeta.IsSameRecognitionWithListen = true;
              } else {
                objMeta.IsSameRecognitionWithListen = false;
                WordRecognitionErrCount++;
              }

              if (strObserved.equals(strTarget)) {
                objMeta.IsSameObservedWithTarget = true;
              } else {
                objMeta.IsSameObservedWithTarget = false;
              }

              if (strListen.equals(strTarget)) {
                objMeta.IsSameListenWithTarget = true;
              } else {
                objMeta.IsSameListenWithTarget = false;
                WordErrCount++;
              }

              if (strListen.equals(strObserved)) {
                objMeta.IsSameObservedWithListen = true;
              } else {
                objMeta.IsSameObservedWithListen = false;
              }

              // if(strTarget.equals(strObserved)){
              // objMeta.IsSameObservedWithTarget= true;
              // }else{
              // objMeta.IsSameObservedWithTarget= false;
              // }

              v.addElement(objMeta);
              logger.debug("add one word: " + objMeta.toStringShort());
            }

          }

        } else {
          logger.error("Four sentences have different length; Target Sentence: " + strTargetWord);
        }
      }// end while

    } catch (Exception e) {
      System.out.println("Exception in wrongTypeProcess of CBPManager " + e.getMessage());
      e.printStackTrace();
    }

    dm.releaseConn();

    logger.debug("all same word count: " + sameCount + " different word size: " + v.size());
    logger.debug("WordErrCount: " + WordErrCount);
    logger.debug("WordRecognitionErrCount: " + WordRecognitionErrCount);
    return v;
  }

  public void wordProcess_ErrorRate(String strwordSpeakSenteceTableName) {

    String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
    dm.connectToAccess(strOdbc);

    int count = 0;
    int sameCount = 0;
    int WordUndetectedErrCount = 0;
    int WordDetectedErrCount = 0;
    int WordFalseAlarmCount = 0;
    int WER = 0;
    int UndetectedErrCount = 0;
    int FalseAlarmCount = 0;
    int WordNumber = 0;

    // strwordTableName
    // read data into WordErrDataMeta one by one, change attribute on the basis
    // of its type
    try {
      Vector<String> vecTargetWord;
      Vector<String> vecObservedWord;
      Vector<String> vecRecognitionWord;
      Vector<String> vecListenWord;
      // int studentID;
      // String strTargetWord;
      // String strRecognitionWord;

      WordDataMeta objMeta;
      ResultSet tmpRs = dm.searchTable(strwordSpeakSenteceTableName, "");
      while (tmpRs.next()) {
        // objEDMeta.intID = tmpRs.getInt("ID");
        vecTargetWord = new Vector<String>();
        vecObservedWord = new Vector<String>();
        vecRecognitionWord = new Vector<String>();
        vecListenWord = new Vector<String>();

        int studentID = tmpRs.getInt("StudentID");
        String lesson = tmpRs.getString("Lesson");
        String strTargetWord = tmpRs.getString("strTargetAnswer");
        String strObservedWord = tmpRs.getString("strTextAnswer");
        String strRecognitionWord = tmpRs.getString("strSpeechAnswer");
        String strListenWord = tmpRs.getString("strListeningAnswer");

        logger.debug("Draw sentence: " + strTargetWord);

        StringTokenizer stTargetWord = new StringTokenizer(strTargetWord);
        StringTokenizer stObservedWord = new StringTokenizer(strObservedWord);
        StringTokenizer stRecognitionWord = new StringTokenizer(strRecognitionWord);
        StringTokenizer stListenWord = new StringTokenizer(strListenWord);

        while (stTargetWord.hasMoreElements()) {
          String str = (String) stTargetWord.nextElement();
          vecTargetWord.addElement(str);
        }
        while (stObservedWord.hasMoreElements()) {
          String str = (String) stObservedWord.nextElement();
          vecObservedWord.addElement(str);
        }
        while (stRecognitionWord.hasMoreElements()) {
          String str = (String) stRecognitionWord.nextElement();
          vecRecognitionWord.addElement(str);
        }
        while (stListenWord.hasMoreElements()) {
          String str = (String) stListenWord.nextElement();
          vecListenWord.addElement(str);
        }

        if (vecTargetWord.size() == vecListenWord.size() && vecListenWord.size() == vecRecognitionWord.size()
            && vecRecognitionWord.size() == vecObservedWord.size()) {

          for (int i = 0; i < vecTargetWord.size(); i++) {

            String strTarget = vecTargetWord.elementAt(i).trim();
            String strListen = vecListenWord.elementAt(i).trim();
            String strRecognition = vecRecognitionWord.elementAt(i).trim();
            String strObserved = vecObservedWord.elementAt(i).trim();

            count++;

            if (strListen.equals(strTarget) && strRecognition.equals(strTarget)) {
              sameCount++;
              // logger.debug("all is the same");
            } else {

              if (strRecognition.equals(strListen)) {
                if (!strListen.equals(strTarget)) {
                  WordDetectedErrCount++;
                }
              }

              if (strListen.equals(strTarget)) {
                if (!strRecognition.equals(strListen)) {
                  WordFalseAlarmCount++;
                  // System.out.println("strListen==strTarget, strListen: "+strListen
                  // + " strTarget: "+strTarget + "strRecognition: "
                  // +strRecognition);
                }
              }

              if (strRecognition.equals(strTarget)) {
                if (!strRecognition.equals(strListen)) {
                  WordUndetectedErrCount++;
                }
              }

              if (!strRecognition.equals(strListen)) {
                if (strListen.equals(strTarget)) {
                  FalseAlarmCount++;
                } else {
                  UndetectedErrCount++;
                }

                WER++;
              }

            }

          }

        } else {
          logger.error("Four sentences have different length; Target Sentence: " + strTargetWord);
        }
      }// end while

    } catch (Exception e) {
      System.out.println("Exception in wrongTypeProcess of CBPManager " + e.getMessage());
      e.printStackTrace();
    }

    dm.releaseConn();

    // int WordUndetectedErrCount = 0;
    // int WordDetectedErrCount = 0;
    // int WordFalseAlarmCount = 0;
    //
    // logger.debug("all same word count: "+ sameCount);
    System.out.println("all word count: " + count);
    System.out.println("all same word count: " + sameCount);
    System.out.println("WordErrCount: " + WER);
    System.out
        .println("WordUndetectedErrCount: " + WordUndetectedErrCount + " another counting: " + UndetectedErrCount);
    System.out.println("WordDetectedErrCount: " + WordDetectedErrCount);
    System.out.println("WordFalseAlarmCount: " + WordFalseAlarmCount + " another counting: " + FalseAlarmCount);
    System.out.println("WordNumber: " + WordNumber);

  }

  public Vector wordProcess_Listen(String strwordSpeakSenteceTableName) {

    String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
    dm.connectToAccess(strOdbc);

    Vector<WordDataMeta> v = new Vector<WordDataMeta>();
    int sameCount = 0;
    int WordErrCount = 0;
    int WordRecognitionErrCount = 0;

    // strwordTableName
    // read data into WordErrDataMeta one by one, change attribute on the basis
    // of its type
    try {
      Vector<String> vecTargetWord;
      Vector<String> vecObservedWord;
      Vector<String> vecRecognitionWord;
      Vector<String> vecListenWord;
      // int studentID;
      // String strTargetWord;
      // String strRecognitionWord;

      WordDataMeta objMeta;
      ResultSet tmpRs = dm.searchTable(strwordSpeakSenteceTableName, "");
      while (tmpRs.next()) {
        // objEDMeta.intID = tmpRs.getInt("ID");
        vecTargetWord = new Vector<String>();
        vecObservedWord = new Vector<String>();
        vecRecognitionWord = new Vector<String>();
        vecListenWord = new Vector<String>();

        int studentID = tmpRs.getInt("StudentID");
        String lesson = tmpRs.getString("Lesson");
        String strTargetWord = tmpRs.getString("strTargetAnswer");
        String strObservedWord = tmpRs.getString("strTextAnswer");
        String strRecognitionWord = tmpRs.getString("strSpeechAnswer");
        String strListenWord = tmpRs.getString("strListeningAnswer");

        logger.debug("Draw sentence: " + strTargetWord);

        StringTokenizer stTargetWord = new StringTokenizer(strTargetWord);
        StringTokenizer stObservedWord = new StringTokenizer(strObservedWord);
        StringTokenizer stRecognitionWord = new StringTokenizer(strRecognitionWord);
        StringTokenizer stListenWord = new StringTokenizer(strListenWord);

        while (stTargetWord.hasMoreElements()) {
          String str = (String) stTargetWord.nextElement();
          vecTargetWord.addElement(str);
        }
        while (stObservedWord.hasMoreElements()) {
          String str = (String) stObservedWord.nextElement();
          vecObservedWord.addElement(str);
        }
        while (stRecognitionWord.hasMoreElements()) {
          String str = (String) stRecognitionWord.nextElement();
          vecRecognitionWord.addElement(str);
        }
        while (stListenWord.hasMoreElements()) {
          String str = (String) stListenWord.nextElement();
          vecListenWord.addElement(str);
        }

        if (vecTargetWord.size() == vecListenWord.size() && vecListenWord.size() == vecRecognitionWord.size()
            && vecRecognitionWord.size() == vecObservedWord.size()) {

          for (int i = 0; i < vecTargetWord.size(); i++) {

            String strTarget = vecTargetWord.elementAt(i).trim();
            String strListen = vecListenWord.elementAt(i).trim();
            String strRecognition = vecRecognitionWord.elementAt(i).trim();
            String strObserved = vecObservedWord.elementAt(i).trim();

            if (strListen.equals(strTarget)) {
              sameCount++;
              // logger.debug("all is the same");
            } else {

              objMeta = new WordDataMeta();
              objMeta.intStudentID = studentID;
              objMeta.strLesson = lesson;
              objMeta.strObservedWord = strObserved;
              objMeta.strListenWord = strListen;
              objMeta.strRecognitionWord = strRecognition;
              objMeta.strTargetWord = strTarget;
              if (i == vecTargetWord.size() - 1) {
                objMeta.strTargetType = "VERB";
              } else if (i % 2 == 0) {
                objMeta.strTargetType = "NOUN";
              } else {
                objMeta.strTargetType = "PARTICLE";
              }
              //
              v.addElement(objMeta);
              logger.debug("add one word: " + objMeta.toStringShort());
            }

          }

        } else {
          logger.error("Four sentences have different length; Target Sentence: " + strTargetWord);
        }
      }// end while

    } catch (Exception e) {
      System.out.println("Exception in wrongTypeProcess of CBPManager " + e.getMessage());
      e.printStackTrace();
    }

    dm.releaseConn();

    logger.debug("all same word(listen and target) count: " + sameCount + " different word size: " + v.size());
    // logger.debug("WordErrCount: "+WordErrCount);
    // logger.debug("WordRecognitionErrCount: "+WordRecognitionErrCount);
    return v;
  }

  public Vector wordProcess_ListenFull(String strwordSpeakSenteceTableName) {

    String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
    dm.connectToAccess(strOdbc);

    Vector<WordDataMeta> v = new Vector<WordDataMeta>();
    int sameCount = 0;
    int WordErrCount = 0;
    int WordRecognitionErrCount = 0;

    // strwordTableName
    // read data into WordErrDataMeta one by one, change attribute on the basis
    // of its type
    try {
      Vector<String> vecTargetWord;
      Vector<String> vecObservedWord;
      Vector<String> vecRecognitionWord;
      Vector<String> vecListenWord;
      // int studentID;
      // String strTargetWord;
      // String strRecognitionWord;

      WordDataMeta objMeta;
      ResultSet tmpRs = dm.searchTable(strwordSpeakSenteceTableName, "");
      while (tmpRs.next()) {
        // objEDMeta.intID = tmpRs.getInt("ID");
        vecTargetWord = new Vector<String>();
        vecObservedWord = new Vector<String>();
        vecRecognitionWord = new Vector<String>();
        vecListenWord = new Vector<String>();

        int studentID = tmpRs.getInt("StudentID");
        String lesson = tmpRs.getString("Lesson");
        String strTargetWord = tmpRs.getString("strTargetAnswer");
        String strObservedWord = tmpRs.getString("strTextAnswer");
        String strRecognitionWord = tmpRs.getString("strSpeechAnswer");
        String strListenWord = tmpRs.getString("strListeningAnswer");

        logger.debug("Draw sentence: " + strTargetWord);

        StringTokenizer stTargetWord = new StringTokenizer(strTargetWord);
        StringTokenizer stObservedWord = new StringTokenizer(strObservedWord);
        StringTokenizer stRecognitionWord = new StringTokenizer(strRecognitionWord);
        StringTokenizer stListenWord = new StringTokenizer(strListenWord);

        while (stTargetWord.hasMoreElements()) {
          String str = (String) stTargetWord.nextElement();
          vecTargetWord.addElement(str);
        }
        while (stObservedWord.hasMoreElements()) {
          String str = (String) stObservedWord.nextElement();
          vecObservedWord.addElement(str);
        }
        while (stRecognitionWord.hasMoreElements()) {
          String str = (String) stRecognitionWord.nextElement();
          vecRecognitionWord.addElement(str);
        }
        while (stListenWord.hasMoreElements()) {
          String str = (String) stListenWord.nextElement();
          vecListenWord.addElement(str);
        }

        if (vecTargetWord.size() == vecListenWord.size() && vecListenWord.size() == vecRecognitionWord.size()
            && vecRecognitionWord.size() == vecObservedWord.size()) {

          for (int i = 0; i < vecTargetWord.size(); i++) {

            String strTarget = vecTargetWord.elementAt(i).trim();
            String strListen = vecListenWord.elementAt(i).trim();
            String strRecognition = vecRecognitionWord.elementAt(i).trim();
            String strObserved = vecObservedWord.elementAt(i).trim();

            if (strListen.equals(strTarget)) {
              sameCount++;
              // logger.debug("all is the same");
            } else {

              objMeta = new WordDataMeta();
              objMeta.intStudentID = studentID;
              objMeta.strLesson = lesson;
              objMeta.strObservedWord = strObserved;
              objMeta.strListenWord = strListen;
              objMeta.strRecognitionWord = strRecognition;
              objMeta.strTargetWord = strTarget;
              if (i == vecTargetWord.size() - 1) {
                objMeta.strTargetType = "VERB";
              } else if (i % 2 == 0) {
                objMeta.strTargetType = "NOUN";
              } else {
                objMeta.strTargetType = "PARTICLE";
              }
              //
              if (strRecognition.equals(strListen)) {
                objMeta.IsSameRecognitionWithListen = true;
              } else {
                objMeta.IsSameRecognitionWithListen = false;
                WordRecognitionErrCount++;
              }

              if (strObserved.equals(strTarget)) {
                objMeta.IsSameObservedWithTarget = true;
              } else {
                objMeta.IsSameObservedWithTarget = false;
              }

              if (strListen.equals(strTarget)) {
                objMeta.IsSameListenWithTarget = true;
              } else {
                objMeta.IsSameListenWithTarget = false;
                WordErrCount++;
              }

              if (strListen.equals(strObserved)) {
                objMeta.IsSameObservedWithListen = true;
              } else {
                objMeta.IsSameObservedWithListen = false;
              }

              v.addElement(objMeta);
              logger.debug("add one word: " + objMeta.toStringShort());
            }

          }

        } else {
          logger.error("For sentences have different length; Target Sentence: " + strTargetWord);
        }
      }// end while

    } catch (Exception e) {
      System.out.println("Exception in wrongTypeProcess of CBPManager " + e.getMessage());
      e.printStackTrace();
    }

    dm.releaseConn();

    logger.debug("all same word(listen and target) count: " + sameCount + " different word size: " + v.size());
    // logger.debug("WordErrCount: "+WordErrCount);
    // logger.debug("WordRecognitionErrCount: "+WordRecognitionErrCount);
    return v;
  }

  public Vector wordProcess_ListenRecognition(String strwordSpeakSenteceTableName) {

    String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
    dm.connectToAccess(strOdbc);

    Vector<WordDataMeta> v = new Vector<WordDataMeta>();
    int sameCount = 0;
    int WordErrCount = 0;
    int WordRecognitionErrCount = 0;

    // strwordTableName
    // read data into WordErrDataMeta one by one, change attribute on the basis
    // of its type
    try {
      Vector<String> vecTargetWord;
      Vector<String> vecObservedWord;
      Vector<String> vecRecognitionWord;
      Vector<String> vecListenWord;
      // int studentID;
      // String strTargetWord;
      // String strRecognitionWord;

      WordDataMeta objMeta;
      ResultSet tmpRs = dm.searchTable(strwordSpeakSenteceTableName, "");
      while (tmpRs.next()) {
        // objEDMeta.intID = tmpRs.getInt("ID");
        vecTargetWord = new Vector<String>();
        vecObservedWord = new Vector<String>();
        vecRecognitionWord = new Vector<String>();
        vecListenWord = new Vector<String>();

        int studentID = tmpRs.getInt("StudentID");
        String lesson = tmpRs.getString("Lesson");
        String strTargetWord = tmpRs.getString("strTargetAnswer");
        String strObservedWord = tmpRs.getString("strTextAnswer");
        String strRecognitionWord = tmpRs.getString("strSpeechAnswer");
        String strListenWord = tmpRs.getString("strListeningAnswer");

        logger.debug("Draw sentence: " + strTargetWord);

        StringTokenizer stTargetWord = new StringTokenizer(strTargetWord);
        StringTokenizer stObservedWord = new StringTokenizer(strObservedWord);
        StringTokenizer stRecognitionWord = new StringTokenizer(strRecognitionWord);
        StringTokenizer stListenWord = new StringTokenizer(strListenWord);

        while (stTargetWord.hasMoreElements()) {
          String str = (String) stTargetWord.nextElement();
          vecTargetWord.addElement(str);
        }
        while (stObservedWord.hasMoreElements()) {
          String str = (String) stObservedWord.nextElement();
          vecObservedWord.addElement(str);
        }
        while (stRecognitionWord.hasMoreElements()) {
          String str = (String) stRecognitionWord.nextElement();
          vecRecognitionWord.addElement(str);
        }
        while (stListenWord.hasMoreElements()) {
          String str = (String) stListenWord.nextElement();
          vecListenWord.addElement(str);
        }

        if (vecTargetWord.size() == vecListenWord.size() && vecListenWord.size() == vecRecognitionWord.size()
            && vecRecognitionWord.size() == vecObservedWord.size()) {

          for (int i = 0; i < vecTargetWord.size(); i++) {

            String strTarget = vecTargetWord.elementAt(i).trim();
            String strListen = vecListenWord.elementAt(i).trim();
            String strRecognition = vecRecognitionWord.elementAt(i).trim();
            String strObserved = vecObservedWord.elementAt(i).trim();

            if (strListen.equals(strRecognition)) {
              sameCount++;
              // logger.debug("all is the same");
            } else {

              objMeta = new WordDataMeta();
              objMeta.intStudentID = studentID;
              objMeta.strLesson = lesson;
              objMeta.strObservedWord = strObserved;
              objMeta.strListenWord = strListen;
              objMeta.strRecognitionWord = strRecognition;
              objMeta.strTargetWord = strTarget;
              if (i == vecTargetWord.size() - 1) {
                objMeta.strTargetType = "VERB";
              } else if (i % 2 == 0) {
                objMeta.strTargetType = "NOUN";
              } else {
                objMeta.strTargetType = "PARTICLE";
              }
              //
              v.addElement(objMeta);
              logger.debug("add one word: " + objMeta.toStringShort());
            }

          }

        } else {
          logger.error("Four sentences have different length; Target Sentence: " + strTargetWord);
        }
      }// end while

    } catch (Exception e) {
      System.out.println("Exception in wrongTypeProcess of CBPManager " + e.getMessage());
      e.printStackTrace();
    }

    dm.releaseConn();

    logger.debug("all same word(listen and target) count: " + sameCount + " different word size: " + v.size());
    // logger.debug("WordErrCount: "+WordErrCount);
    // logger.debug("WordRecognitionErrCount: "+WordRecognitionErrCount);
    return v;
  }

  /*
   * WordTable contains all words that different between speechAnswer,
   * TargetAnswer, TextAnswer
   */

  public void createWordTable(String tablename) throws ClassNotFoundException, IOException {
    // delete all the data and create a new table
    boolean bResult = false;
    // connect to database
    String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
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
    strWordTableSetup += "ListenWord VarChar(50) NOT NULL, ";
    strWordTableSetup += "TargetType VarChar(10) , ";
    strWordTableSetup += "IsSameRecognitionWithObserved bit , ";
    strWordTableSetup += "IsSameObservedWithTarget bit , ";
    strWordTableSetup += "IsSameRecognitionWithListen bit, ";
    strWordTableSetup += "IsSameListenWithTarget bit, ";
    strWordTableSetup += "IsSameObservedWithListen bit, ";
    strWordTableSetup += "GeneralType VarChar(50), ";
    strWordTableSetup += "SpecificType VarChar(50), ";
    strWordTableSetup += "FormMistakes VarChar(50), ";
    strWordTableSetup += "strFormDistance VarChar(10)" + ")";
    String sqlnew = "CREATE TABLE " + tablename + " " + strWordTableSetup;
    bResult = dm.execute(sqlnew);
    if (bResult) {
      logger.error("error, create no new table--" + tablename);
    } else {
      logger.info("create a new table--" + tablename);
    }
    dm.releaseConn();
  }

  public void createWordTable_Listen(String tablename) throws ClassNotFoundException, IOException {
    // delete all the data and create a new table
    boolean bResult = false;
    // connect to database
    String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
    dm.connectToAccess(strOdbc);

    String sql = "drop table " + tablename;
    bResult = dm.execute(sql);
    // create new table
    String strWordTableSetup = "(" + "ID Counter PRIMARY KEY, ";
    strWordTableSetup += "StudentID int, ";
    strWordTableSetup += "Lesson VarChar(10), ";
    strWordTableSetup += "Question VarChar(10), ";
    strWordTableSetup += "TargetWord VarChar(50) NOT NULL, ";
    // strWordTableSetup += "ObservedWord VarChar(50) NOT NULL, ";
    // strWordTableSetup += "RecognitionWord VarChar(50) NOT NULL, ";
    strWordTableSetup += "ListenWord VarChar(50) NOT NULL, ";
    strWordTableSetup += "TargetType VarChar(10) , ";
    // strWordTableSetup += "IsSameRecognitionWithObserved bit , ";
    // strWordTableSetup += "IsSameObservedWithTarget bit , ";
    // strWordTableSetup += "IsSameRecognitionWithListen bit, ";
    // strWordTableSetup += "IsSameListenWithTarget bit, ";
    // strWordTableSetup += "IsSameObservedWithListen bit, ";
    // strWordTableSetup += "GeneralType VarChar(50), ";
    strWordTableSetup += "SpecificType VarChar(50), ";
    strWordTableSetup += "FormMistakes VarChar(50), ";
    strWordTableSetup += "strFormDistance VarChar(10)" + ")";
    String sqlnew = "CREATE TABLE " + tablename + " " + strWordTableSetup;
    bResult = dm.execute(sqlnew);
    if (bResult) {
      logger.error("error, create no new table--" + tablename);
    } else {
      logger.info("create a new table--" + tablename);
    }
    dm.releaseConn();
  }

  public void createWordTable_ListenFull(String tablename) throws ClassNotFoundException, IOException {
    // delete all the data and create a new table
    boolean bResult = false;
    // connect to database
    String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
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
    strWordTableSetup += "ListenWord VarChar(50) NOT NULL, ";
    strWordTableSetup += "TargetType VarChar(10) , ";
    strWordTableSetup += "IsSameRecognitionWithObserved bit , ";
    strWordTableSetup += "IsSameObservedWithTarget bit , ";
    strWordTableSetup += "IsSameRecognitionWithListen bit, ";
    strWordTableSetup += "IsSameListenWithTarget bit, ";
    strWordTableSetup += "IsSameObservedWithListen bit, ";
    // strWordTableSetup += "GeneralType VarChar(50), ";
    strWordTableSetup += "SpecificType VarChar(50), ";
    strWordTableSetup += "FormMistakes VarChar(50), ";
    strWordTableSetup += "strFormDistance VarChar(10)" + ")";
    String sqlnew = "CREATE TABLE " + tablename + " " + strWordTableSetup;
    bResult = dm.execute(sqlnew);
    if (bResult) {
      logger.error("error, create no new table--" + tablename);
    } else {
      logger.info("create a new table--" + tablename);
    }
    dm.releaseConn();
  }

  public void createWordTable_Observed(String tablename) throws ClassNotFoundException, IOException {
    // delete all the data and create a new table
    boolean bResult = false;
    // connect to database
    String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
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
    // strWordTableSetup += "RecognitionWord VarChar(50) NOT NULL, ";
    // strWordTableSetup += "ListenWord VarChar(50) NOT NULL, ";
    strWordTableSetup += "TargetType VarChar(10) , ";
    // strWordTableSetup += "IsSameRecognitionWithObserved bit , ";
    // strWordTableSetup += "IsSameObservedWithTarget bit , ";
    // strWordTableSetup += "IsSameRecognitionWithListen bit, ";
    // strWordTableSetup += "IsSameListenWithTarget bit, ";
    // strWordTableSetup += "IsSameObservedWithListen bit, ";
    // strWordTableSetup += "GeneralType VarChar(50), ";
    strWordTableSetup += "SpecificType VarChar(50), ";
    strWordTableSetup += "FormMistakes VarChar(50), ";
    strWordTableSetup += "strFormDistance VarChar(10)" + ")";
    String sqlnew = "CREATE TABLE " + tablename + " " + strWordTableSetup;
    bResult = dm.execute(sqlnew);
    if (bResult) {
      logger.error("error, create no new table--" + tablename);
    } else {
      logger.info("create a new table--" + tablename);
    }
    dm.releaseConn();
  }

  public void createWordTable_ListenRecognition(String tablename) throws ClassNotFoundException, IOException {
    // delete all the data and create a new table
    boolean bResult = false;
    // connect to database
    String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
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
    strWordTableSetup += "ListenWord VarChar(50) NOT NULL, ";
    strWordTableSetup += "TargetType VarChar(10) , ";
    // strWordTableSetup += "IsSameRecognitionWithObserved bit , ";
    // strWordTableSetup += "IsSameObservedWithTarget bit , ";
    // strWordTableSetup += "IsSameRecognitionWithListen bit, ";
    // strWordTableSetup += "IsSameListenWithTarget bit, ";
    // strWordTableSetup += "IsSameObservedWithListen bit, ";
    // strWordTableSetup += "GeneralType VarChar(50), ";
    strWordTableSetup += "SpecificType VarChar(50), ";
    strWordTableSetup += "FormMistakes VarChar(50), ";
    strWordTableSetup += "strFormDistance VarChar(10)" + ")";
    String sqlnew = "CREATE TABLE " + tablename + " " + strWordTableSetup;
    bResult = dm.execute(sqlnew);
    if (bResult) {
      logger.error("error, create no new table--" + tablename);
    } else {
      logger.info("create a new table--" + tablename);
    }
    dm.releaseConn();
  }

  public void loadWordToTable(String strwordSpeakSenteceTableName, String strTableName, String strFields)
      throws ClassNotFoundException, IOException {

    Vector v = wordProcess(strwordSpeakSenteceTableName);

    createWordTable(strTableName);

    if (v != null && v.size() > 0) {

      logger.info("total word size: " + v.size());

      String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
      dm.connectToAccess(strOdbc);

      for (int i = 0; i < v.size(); i++) {
        WordDataMeta objMeta = (WordDataMeta) v.elementAt(i);
        String strValues = objMeta.toVALUES();
        logger.debug("before add to our database,strValues is [" + strValues + "]");
        dm.insertRecord(strTableName, strFields, strValues);
      }

      dm.releaseConn();

    } else {
      logger.error("no input or vector size is zero");
    }

  }

  public void loadWordToTable_Listen(String strwordSpeakSenteceTableName, String strTableName, String strFields)
      throws ClassNotFoundException, IOException {

    Vector v = wordProcess_Listen(strwordSpeakSenteceTableName);

    createWordTable_Listen(strTableName);

    if (v != null && v.size() > 0) {

      logger.info("total word size: " + v.size());

      String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
      dm.connectToAccess(strOdbc);

      for (int i = 0; i < v.size(); i++) {
        WordDataMeta objMeta = (WordDataMeta) v.elementAt(i);
        // String strValues = objMeta.toVALUES();
        String strValues = objMeta.toVALUES_Listen();
        logger.debug("before add to our database,strValues is [" + strValues + "]");
        dm.insertRecord(strTableName, strFields, strValues);
      }

      dm.releaseConn();

    } else {
      logger.error("no input or vector size is zero");
    }

  }

  public void loadWordToTable_ListenFull(String strwordSpeakSenteceTableName, String strTableName, String strFields)
      throws ClassNotFoundException, IOException {

    Vector v = wordProcess_ListenFull(strwordSpeakSenteceTableName);

    createWordTable(strTableName);

    if (v != null && v.size() > 0) {

      logger.info("total word size: " + v.size());

      String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
      dm.connectToAccess(strOdbc);

      for (int i = 0; i < v.size(); i++) {
        WordDataMeta objMeta = (WordDataMeta) v.elementAt(i);
        // String strValues = objMeta.toVALUES();
        String strValues = objMeta.toVALUES();
        logger.debug("before add to our database,strValues is [" + strValues + "]");
        dm.insertRecord(strTableName, strFields, strValues);
      }

      dm.releaseConn();

    } else {
      logger.error("no input or vector size is zero");
    }

  }

  public void loadWordToTable_Observed(String strwordSpeakSenteceTableName, String strTableName, String strFields)
      throws ClassNotFoundException, IOException {

    Vector v = wordProcess(strwordSpeakSenteceTableName);

    createWordTable_Observed(strTableName);

    if (v != null && v.size() > 0) {

      logger.info("total word size: " + v.size());

      String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
      dm.connectToAccess(strOdbc);

      for (int i = 0; i < v.size(); i++) {
        WordDataMeta objMeta = (WordDataMeta) v.elementAt(i);
        // String strValues = objMeta.toVALUES();
        String strValues = objMeta.toVALUES_Observed();
        logger.debug("before add to our database,strValues is [" + strValues + "]");
        dm.insertRecord(strTableName, strFields, strValues);
      }

      dm.releaseConn();

    } else {
      logger.error("no input or vector size is zero");
    }

  }

  public void loadWordToTable_ListenRecognition(String strwordSpeakSenteceTableName, String strTableName,
      String strFields) throws ClassNotFoundException, IOException {

    Vector v = wordProcess_ListenRecognition(strwordSpeakSenteceTableName);

    createWordTable(strTableName);

    if (v != null && v.size() > 0) {

      logger.info("total word size: " + v.size());

      String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
      dm.connectToAccess(strOdbc);

      for (int i = 0; i < v.size(); i++) {
        WordDataMeta objMeta = (WordDataMeta) v.elementAt(i);
        // String strValues = objMeta.toVALUES();
        String strValues = objMeta.toVALUES();
        logger.debug("before add to our database,strValues is [" + strValues + "]");
        dm.insertRecord(strTableName, strFields, strValues);
      }

      dm.releaseConn();

    } else {
      logger.error("no input or vector size is zero");
    }

  }

  // ////////////////////////////
  // /////for the method comparison at sentence level
  // ////////////

  // final static String strSentenceFields =
  // "(StudentID,Lesson,Question,strTargetAnswer,strListeningAnswer,strSpeechAnswer,baseSpeechAnswer,oneSpeechAnswer,twoSpeechAnswer,threeSpeechAnswer,SentenceLevelError,booCorrect)";

  final static String strSentenceFields = "(StudentID,Lesson,Question,strTargetAnswer,strListeningAnswer,strSpeechAnswer)";

  public void createBasicSentenceTable(String tablename) throws ClassNotFoundException, IOException {
    // delete all the data and create a new table
    boolean bResult = false;
    // connect to database
    // String strOdbc = FindConfig.getConfig().findStr(OdbcName);
    String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
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
    // strTableSetup = strTableSetup+ "strSpeechAnswer VarChar(120) NOT NULL, ";
    // strTableSetup = strTableSetup+ "baseSpeechAnswer VarChar(120) , ";
    // strTableSetup = strTableSetup+ "oneSpeechAnswer VarChar(120) , ";
    // strTableSetup = strTableSetup+ "twoSpeechAnswer VarChar(120) , ";
    // strTableSetup = strTableSetup+ "threeSpeechAnswer VarChar(120) , ";
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

  // final static String strSentenceFields =
  // "(StudentID,Lesson,Question,strTargetAnswer,strListeningAnswer,strSpeechAnswer,baseSpeechAnswer,oneSpeechAnswer,twoSpeechAnswer,threeSpeechAnswer,SentenceLevelError,booCorrect)";

  // final static String strSentenceFields =
  // "(StudentID,Lesson,Question,strTargetAnswer,strListeningAnswer,strSpeechAnswer)";

  public void createSentenceTable(String tablename) throws ClassNotFoundException, IOException {
    // delete all the data and create a new table
    boolean bResult = false;
    // connect to database
    // String strOdbc = FindConfig.getConfig().findStr(OdbcName);
    String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
    dm.connectToAccess(strOdbc);

    String sql = "drop table " + tablename;
    bResult = dm.execute(sql);

    // create new table
    String strTableSetup = "(" + "ID Counter PRIMARY KEY, ";
    strTableSetup = strTableSetup + "StudentID int, ";
    strTableSetup = strTableSetup + "Lesson VarChar(50), ";
    strTableSetup = strTableSetup + "Question VarChar(50) , ";
    strTableSetup = strTableSetup + "strTargetAnswer VarChar(200) NOT NULL, ";
    // strTableSetup = strTableSetup+ "strTextAnswer VarChar(120) NOT NULL, ";
    // strTableSetup = strTableSetup+ "strSpeechAnswer VarChar(120) NOT NULL, ";
    strTableSetup = strTableSetup + "strListeningAnswer VarChar(120) NOT NULL, ";
    strTableSetup = strTableSetup + "strSpeechAnswer VarChar(120) NOT NULL, ";
    strTableSetup = strTableSetup + "baseSpeechAnswer VarChar(120) , ";
    strTableSetup = strTableSetup + "oneSpeechAnswer VarChar(120) , ";
    strTableSetup = strTableSetup + "twoSpeechAnswer VarChar(120) , ";
    strTableSetup = strTableSetup + "threeSpeechAnswer VarChar(120) , ";
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

  public Vector getSetenceFromTable(String tablename) {

    String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
    dm.connectToAccess(strOdbc);

    Vector<SentenceDataMeta> v = new Vector<SentenceDataMeta>();
    // Vector<SentenceDataMeta> v = new Vector<SentenceDataMeta>
    // read data into WordErrDataMeta one by one, change attribute on the basis
    // of its type
    try {

      SentenceDataMeta objMeta;
      ResultSet tmpRs = dm.searchTable(tablename, "");
      while (tmpRs.next()) {
        // objEDMeta.intID = tmpRs.getInt("ID");
        int iD = tmpRs.getInt("ID");
        int studentID = tmpRs.getInt("StudentID");
        String lesson = tmpRs.getString("Lesson");
        String strQuestion = tmpRs.getString("Question");
        String strTargetWord = tmpRs.getString("strTargetAnswer");
        String strRecognitionWord = tmpRs.getString("strSpeechAnswer");
        String strListenWord = tmpRs.getString("strListeningAnswer");
        // System.out.println("Question: "+ strQuestion);
        objMeta = new SentenceDataMeta(iD, studentID, lesson, strQuestion, strTargetWord, strListenWord,
            strRecognitionWord);

        logger.debug("Draw sentence: " + strTargetWord);
        v.addElement(objMeta);

        // System.out.println("iD: "+ iD );
        // v.add(iD-1, objMeta);

      }// end while

    } catch (Exception e) {
      System.out.println("Exception in wrongTypeProcess of CBPManager " + e.getMessage());
      e.printStackTrace();
    }
    dm.releaseConn();

    return v;
  }

  public Vector saveSetence2Table(String tablename, Vector v) {

    String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
    dm.connectToAccess(strOdbc);
    for (int i = 0; i < v.size(); i++) {
      SentenceDataMeta objMeta = (SentenceDataMeta) v.elementAt(i);
      String strValues = objMeta.toVALUES();
      dm.insertRecord(tablename, strSentenceFields, strValues);
    }
    dm.releaseConn();

    return v;
  }

  public static void main(String args[]) throws IOException, Exception {
    WordErrorAnalysis wea = new WordErrorAnalysis();

    wea.loadWordToTable(NewLogParser.SpeakSentencesTable3, "WordTable", strWordFields);

    // wea.loadWordToTable_Listen(NewLogParser.SpeakSentencesTable3,
    // "WordTable_Listen", strWordFields_Listen);

    // wea.loadWordToTable_Observed(NewLogParser.SpeakSentencesTable3,
    // "WordTable_Observed", strWordFields_Observed);

    // wea.loadWordToTable_ListenRecognition(NewLogParser.SpeakSentencesTable3,
    // "WordTable_ListenRecognition", strWordFields);

    // wea.loadWordToTable_ListenFull(NewLogParser.SpeakSentencesTable3,
    // "WordTable_ListenFull", strWordFields);

    // wea.wordProcess_ErrorRate(NewLogParser.SpeakSentencesTable3);

    // String sentenceTablename = "SOneSpeechTable";
    wea.createSentenceTable(sentenceTablename_OneSpeech);
    // wea.createSentenceTable(sentenceTablename_TwoSpeech);

    // Vector v = wea.getSetenceFromTable(NewLogParser.SpeakSentencesTable3);

    // wea.saveSetence2Table(sentenceTablename, v);

  }

}
