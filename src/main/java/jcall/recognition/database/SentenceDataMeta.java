/**
 * Created on 2007/09/27
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.database;

public class SentenceDataMeta {
  int intID;
  int intStudentID;
  String strLesson;
  String strQuestion;

  String strTargetAnswer; // model answer in kanji
  String strSpeechAnswer; // speech input answer by automatic speech
                          // recognition;
  String strTextAnswer; // text input answer/ speech answer recognized by user
                        // himself
  String strListeningAnswer; // speech input answer by listening;

  boolean booCorrect; // All four answers are the same;
  String strSentenceLevelError;

  public SentenceDataMeta() {
    init();
  }

  private void init() {
    intID = -1;
    intStudentID = -1;
    strLesson = "";
    strQuestion = "";

    strTargetAnswer = "";
    strSpeechAnswer = "";
    strTextAnswer = "";
    strListeningAnswer = "";

    booCorrect = false;
    strSentenceLevelError = "";

  }

  public String toString() {
    String strResult = "";
    strResult = strResult + "intID is [" + intID + "]\n";
    strResult = strResult + "intStudentID is [" + intStudentID + "]\n";
    strResult = strResult + "strLesson is [" + strLesson + "]\n";
    strResult = strResult + "strQuestion is [" + strQuestion + "]\n";
    strResult = strResult + "strTargetAnswer is [" + strTargetAnswer + "]\n";
    strResult = strResult + "strTextAnswer is [" + strTextAnswer + "]\n";
    strResult = strResult + "strSpeechAnswer is [" + strSpeechAnswer + "]\n";
    strResult = strResult + "strListeningAnswer is [" + strListeningAnswer + "]\n";
    strResult = strResult + "booCorrect is [" + booCorrect + "]\n";

    return strResult;

  }

  public String toVALUES() {
    String strResult = "('";
    strResult = strResult + intStudentID + "','" + strLesson + "','" + strQuestion + "','" + strTargetAnswer + "','"
        + strTextAnswer + "','" + strSpeechAnswer + "','" + strListeningAnswer + "','" + strSentenceLevelError + "',"
        + booCorrect + ")";

    return strResult;

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

  public String getStrListeningAnswer() {
    return strListeningAnswer;
  }

  public void setStrListeningAnswer(String strListeningAnswer) {
    this.strListeningAnswer = strListeningAnswer;
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

  public String getStrSpeechAnswer() {
    return strSpeechAnswer;
  }

  public void setStrSpeechAnswer(String strSpeechAnswer) {
    this.strSpeechAnswer = strSpeechAnswer;
  }

  public String getStrTargetAnswer() {
    return strTargetAnswer;
  }

  public void setStrTargetAnswer(String strTargetAnswer) {
    this.strTargetAnswer = strTargetAnswer;
  }

  public String getStrTextAnswer() {
    return strTextAnswer;
  }

  public void setStrTextAnswer(String strTextAnswer) {
    this.strTextAnswer = strTextAnswer;
  }

  public boolean isBooCorrect() {
    return booCorrect;
  }

  public void setBooCorrect(boolean booCorrect) {
    this.booCorrect = booCorrect;
  }

}
