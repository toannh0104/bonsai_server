/**
 * Created on 2007/06/05
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

public class EPLesson {

  EPLexicon eplexicon;
  // EPGrammar epgrammar;
  EPVerbSettingSet epVerbSettingSet;

  public EPLesson() {
    init();

  }

  private void init() {
    eplexicon = new EPLexicon();
    // epgrammar = new EPGrammar();
    epVerbSettingSet = new EPVerbSettingSet();
  }

  public EPVerbSettingSet getEpconceptverb() {
    return epVerbSettingSet;
  }

  public void setEpconceptverb(EPVerbSettingSet epconceptverb) {
    this.epVerbSettingSet = epconceptverb;
  }

  public EPLexicon getEplexicon() {
    return eplexicon;
  }

  public void setEplexicon(EPLexicon eplexicon) {
    this.eplexicon = eplexicon;
  }

  public EPVerbSettingSet getEpVerbSettingSet() {
    return epVerbSettingSet;
  }

  public void setEpVerbSettingSet(EPVerbSettingSet epVerbSettingSet) {
    this.epVerbSettingSet = epVerbSettingSet;
  }

}
