/**
 * Created on 2007/08/13
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.event;

public class Answer {

  int intComponentID = 0;
  String strAnswer = "";

  public Answer() {

  }

  public Answer(int intComponentID) {
    super();
    this.intComponentID = intComponentID;
  }

  public Answer(int intComponentID, String strAnswer) {
    super();
    this.intComponentID = intComponentID;
    this.strAnswer = strAnswer;
  }

  public int getIntComponentID() {
    return intComponentID;
  }

  public void setIntComponentID(int intComponentID) {
    this.intComponentID = intComponentID;
  }

  public String getStrAnswer() {
    return strAnswer;
  }

  public void setStrAnswer(String strAnswer) {
    this.strAnswer = strAnswer;
  }

}
