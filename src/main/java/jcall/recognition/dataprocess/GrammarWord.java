/**
 * Created on 2007/07/11
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

public class GrammarWord extends EPWord {

  String strLabel;// applicable to all words
  // for verb, it is used as an connector for the old CALLJ
  String strTransformRule; // such as "basic","mashou"
  // (rule name of verb-rules)
  // it may be the sentence form or the the verb forms such as "te" form or
  // others
  String strSentenceSetting; // include changable settings and fixed settings
                             // for this question or lesson
  String strSpecificSetting; // specific settings for sign, tense,
                             // question,politeness;
  // for the new constructure
  String action; // if it is verb, action is the position number of it in the
                 // sentence from right to left

  public GrammarWord() {
    super();
    init();
  }

  private void init() {
    strLabel = "";
    strTransformRule = "";
    action = "";
    strSpecificSetting = "";
    // strTransformRule="";
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getStrTransformRule() {
    return strTransformRule;
  }

  public void setStrTransformRule(String strTransformRule) {
    this.strTransformRule = strTransformRule;
  }

  public String getStrLabel() {
    return strLabel;
  }

  public void setStrLabel(String strLabel) {
    this.strLabel = strLabel;
  }

  public String getStrSpecificSetting() {
    return strSpecificSetting;
  }

  public void setStrSpecificSetting(String strSpecificSetting) {
    this.strSpecificSetting = strSpecificSetting;
  }

  public void setStrSpecificSetting(int sign, int tense, int politeness, int question) {

  }
}
