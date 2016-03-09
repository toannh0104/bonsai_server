/**
 * Created on 2007/06/05
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

public class EPRule {
  EPLesson eplesson;
  EPLexicon epGeneralLexicon;

  public EPRule() {
    init();
  }

  private void init() {
    eplesson = new EPLesson();
    epGeneralLexicon = new EPLexicon();
  }

  public EPLexicon getEpGeneralLexicon() {
    return epGeneralLexicon;
  }

  public void setEpGeneralLexicon(EPLexicon epGeneralLexicon) {
    this.epGeneralLexicon = epGeneralLexicon;
  }

  public EPLesson getEplesson() {
    return eplesson;
  }

  public void setEplesson(EPLesson eplesson) {
    this.eplesson = eplesson;
  }

}
