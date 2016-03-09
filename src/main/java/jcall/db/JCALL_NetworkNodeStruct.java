/**
 * Created on 2007/07/11
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

import java.util.Vector;

import jcall.CALL_sentenceWordStruct;

public class JCALL_NetworkNodeStruct {

  // (rule name of verb-rules)
  // it may be the sentence form or the the verb forms such as "te" form or
  // others
  String strSentenceSetting; // include changable settings and fixed settings
                             // for this question or lesson
  // String strSpecificSetting; // specific settings for sign, tense,
  // question,politeness;
  // for the new constructure
  // String action; // if it is verb, action is the position number of it in the
  // sentence from right to left

  String strLabel;
  CALL_sentenceWordStruct sword;

  JCALL_Word call_word; // for new version
  // surface form
  String surfaceFormKanji;
  String surfaceFormKana;
  String surfaceFormRomaji;

  Vector<JCALL_NetworkSubNodeStruct> pnodes;

  // public GrammarWord( CALL_sentenceWordStruct word){
  // pnodes = new Vector();
  // sign = word.getSign();
  // tense = word.getTense();
  // politeness = word.getPoliteness();
  // question = word.getQuestion();
  // transformationRule = word.getTransformationRule();
  // fullGrammarRule = word.getFullGrammarRule();
  // grammarRule = word.getGrammarRule();
  // componentName
  //
  //
  //
  // }

  public void addSubs(Vector v) {

    if (pnodes == null) {
      pnodes = new Vector<JCALL_NetworkSubNodeStruct>();
    }
    if (v != null) {
      for (int i = 0; i < v.size(); i++) {
        pnodes.addElement((JCALL_NetworkSubNodeStruct) v.get(i));
      }
    }

  }

  public void addPNode(JCALL_NetworkSubNodeStruct node) {

    if (pnodes == null) {
      pnodes = new Vector<JCALL_NetworkSubNodeStruct>();
    }
    pnodes.addElement(node);
  }

  public JCALL_NetworkNodeStruct() {
    super();
    init();
  }

  public JCALL_NetworkNodeStruct(CALL_sentenceWordStruct sword) {
    super();
    this.sword = sword;
  }

  private void init() {
    strSentenceSetting = "";
    sword = null;
    surfaceFormKanji = "";
    surfaceFormKana = "";
    surfaceFormRomaji = "";
    pnodes = null;
    // strTransformRule="";
  }

  public Vector<JCALL_NetworkSubNodeStruct> getVecSubsWord() {
    return pnodes;
  }

  public Vector<JCALL_NetworkSubNodeStruct> getPnodes() {
    return pnodes;
  }

  public void setPnodes(Vector<JCALL_NetworkSubNodeStruct> pnodes) {
    this.pnodes = pnodes;
  }

  public String getSurfaceFormKana() {
    return surfaceFormKana;
  }

  public void setSurfaceFormKana(String surfaceFormKana) {
    this.surfaceFormKana = surfaceFormKana;
  }

  public String getSurfaceFormKanji() {
    return surfaceFormKanji;
  }

  public void setSurfaceFormKanji(String surfaceFormKanji) {
    this.surfaceFormKanji = surfaceFormKanji;
  }

  public String getSurfaceFormRomaji() {
    return surfaceFormRomaji;
  }

  public void setSurfaceFormRomaji(String surfaceFormRomaji) {
    this.surfaceFormRomaji = surfaceFormRomaji;
  }

  public CALL_sentenceWordStruct getSword() {
    return sword;
  }

  public void setSword(CALL_sentenceWordStruct sword) {
    this.sword = sword;
  }

  public void setStrSpecificSetting(int sign, int tense, int politeness, int question) {

  }

  public String getStrSentenceSetting() {
    return strSentenceSetting;
  }

  public void setStrSentenceSetting(String strSentenceSetting) {
    this.strSentenceSetting = strSentenceSetting;
  }

  // public void setWord(CALL_wordStruct word){
  // //this.setIntLevel(word.getLevel());
  // // this.setIntType(LexiconParser.LEX_TYPE_TRANSFORM[word.getType()]);
  // this.setId(word.getId());
  // this.setIntType(word.getType());
  // this.setStrKana(word.getKana());
  // this.setStrKanji(word.getKanji());
  //
  // //search id in the new lexicon
  // }
  //
  // public void setWord(JCALL_Word word){
  // //this.setIntLevel(word.getLevel());
  // // this.setIntType(LexiconParser.LEX_TYPE_TRANSFORM[word.getType()]);
  // this.setId(word.getId());
  // this.setIntType(word.getIntType());
  // this.setStrKana(word.getStrKana());
  // this.setStrKanji(word.getStrKanji());
  // this.setStrAltkana(word.getStrAltkana());
  // // this.setStrGroup(word.ge)
  // //search id in the new lexicon
  // }

  public JCALL_Word getCall_word() {
    return call_word;
  }

  public void setCall_word(JCALL_Word call_word) {
    this.call_word = call_word;
  }

  public String getStrLabel() {
    return strLabel;
  }

  public void setStrLabel(String strLabel) {
    this.strLabel = strLabel;
  }

}
