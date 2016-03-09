/**
 * Created on 2008/06/05
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

public class SentenceDataMeta {

  public int intID;
  public int intStudentID;
  public String strLesson;
  public String strQuestion; // be careful to keep consistent with the same
                             // dataset in sentence table
  // public String strBaseGrammar;
  // public String strFullGrammar;
  public String strObservedSentence;
  public String strTargetSentence;
  public String strRecognitionSentence;
  public String strListenSentence;
  public String strSentenceLevelError;

  public SentenceDataMeta(int intID, int intStudentID, String strLesson, String strQuestion, String strTargetSentence,
      String strRecognitionSentence, String strListenSentence) {
    super();
    this.intID = intID;
    this.intStudentID = intStudentID;
    this.strLesson = strLesson;
    this.strQuestion = strQuestion;
    this.strTargetSentence = strTargetSentence;
    this.strRecognitionSentence = strRecognitionSentence;
    this.strListenSentence = strListenSentence;
  }

  public SentenceDataMeta(int intID, int intStudentID, String strLesson, String strQuestion,
      String strObservedSentence, String strTargetSentence, String strRecognitionSentence, String strListenSentence,
      String strSentenceLevelError) {
    super();
    this.intID = intID;
    this.intStudentID = intStudentID;
    this.strLesson = strLesson;
    this.strQuestion = strQuestion;
    this.strObservedSentence = strObservedSentence;
    this.strTargetSentence = strTargetSentence;
    this.strRecognitionSentence = strRecognitionSentence;
    this.strListenSentence = strListenSentence;
    this.strSentenceLevelError = strSentenceLevelError;
  }

  public String getStrSentenceLevelError() {
    return strSentenceLevelError;
  }

  public void setStrSentenceLevelError(String strSentenceLevelError) {
    this.strSentenceLevelError = strSentenceLevelError;
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

  public String getStrLesson() {
    return strLesson;
  }

  public void setStrLesson(String strLesson) {
    this.strLesson = strLesson;
  }

  public String getStrListenSentence() {
    return strListenSentence;
  }

  public void setStrListenSentence(String strListenSentence) {
    this.strListenSentence = strListenSentence;
  }

  public String getStrObservedSentence() {
    return strObservedSentence;
  }

  public void setStrObservedSentence(String strObservedSentence) {
    this.strObservedSentence = strObservedSentence;
  }

  public String getStrQuestion() {
    return strQuestion;
  }

  public void setStrQuestion(String strQuestion) {
    this.strQuestion = strQuestion;
  }

  public String getStrRecognitionSentence() {
    return strRecognitionSentence;
  }

  public void setStrRecognitionSentence(String strRecognitionSentence) {
    this.strRecognitionSentence = strRecognitionSentence;
  }

  public String getStrTargetSentence() {
    return strTargetSentence;
  }

  public void setStrTargetSentence(String strTargetSentence) {
    this.strTargetSentence = strTargetSentence;
  }

  public String toVALUES() {
    String strResult = "('";
    strResult = strResult + intStudentID + "','" + strLesson + "','" + strQuestion + "','" + strTargetSentence + "','"
        + strListenSentence + "','" + strRecognitionSentence + "'" + ")";

    return strResult;

  }

}
