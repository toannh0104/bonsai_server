/**
 * Created on 2007/05/07
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.database;

import java.io.Serializable;

public class TextSentenceDataMeta implements Serializable {

  int intID;
  int intStudentID;
  String strLesson;
  String strQuestion;
  String strInputSentence;
  String strTargetSentence;
  boolean booTextCorrect;
  String strSentenceLevelError;

  public TextSentenceDataMeta() {
    init();
  }

  private void init() {
    intID = -1;
    intStudentID = -1;
    strLesson = "";
    strQuestion = "";

    strInputSentence = "";
    strTargetSentence = "";

    booTextCorrect = false;
    strSentenceLevelError = "";

  }

  public boolean isBooTextCorrect() {
    return booTextCorrect;
  }

  public void setBooTextCorrect(boolean booTextCorrect) {
    this.booTextCorrect = booTextCorrect;
  }

  public int getIntID() {
    return intID;
  }

  public void setIntID(int intID) {
    this.intID = intID;
  }

  public int getIntStudentID() {
    return intStudentID;
  }

  public void setIntStudentID(int intStudentID) {
    this.intStudentID = intStudentID;
  }

  public String getStrInputSentence() {
    return strInputSentence;
  }

  public void setStrInputSentence(String strInputSentence) {
    this.strInputSentence = strInputSentence;
  }

  public String getStrLesson() {
    return strLesson;
  }

  public void setStrLesson(String strLesson) {
    this.strLesson = strLesson;
  }

  public String getStrQuestion() {
    return strQuestion;
  }

  public void setStrQuestion(String strQuestion) {
    this.strQuestion = strQuestion;
  }

  public String getStrSentenceLevelError() {
    return strSentenceLevelError;
  }

  public void setStrSentenceLevelError(String strSentenceLevelError) {
    this.strSentenceLevelError = strSentenceLevelError;
  }

  public String getStrTargetSentence() {
    return strTargetSentence;
  }

  public void setStrTargetSentence(String strTargetSentence) {
    this.strTargetSentence = strTargetSentence;
  }

  public String toString() {
    String strResult = "";
    strResult = strResult + "intID is [" + intID + "]\t";
    strResult = strResult + "intStudentID is [" + intStudentID + "]\t";
    strResult = strResult + "strLesson is [" + strLesson + "]\t";
    strResult = strResult + "strQuestion is [" + strQuestion + "]\t";
    strResult = strResult + "strInputSentence is [" + strInputSentence + "]\t";
    strResult = strResult + "strTargetSentence is [" + strTargetSentence + "]\t";
    strResult = strResult + "strSentenceLevelError is [" + strSentenceLevelError + "]\t";

    return strResult;

  }

  public String toVALUES() {
    String strResult = "('";
    strResult = strResult + intStudentID + "','" + strLesson + "','" + strQuestion + "','" + strInputSentence + "','"
        + strTargetSentence + "','" + strSentenceLevelError + "')";

    return strResult;

  }

}
