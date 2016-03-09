/**
 * Created on 2007/06/05
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.Serializable;
import java.util.Vector;

import jcall.CALL_wordStruct;

public class EPWord extends JWordBase implements Cloneable, Serializable {

  // String strLabel;
  String StrSetting;

  JWordBase prefix;
  JWordBase suffix;
  Vector<EPLeaf> vecSubsWord;

  public EPWord() {
    super();

    init();
  }

  private void init() {
    vecSubsWord = new Vector<EPLeaf>();

    // strTransformRule="";
  }

  public Vector<EPLeaf> getVecSubsWord() {
    return vecSubsWord;
  }

  public void setVecSubsWord(Vector<EPLeaf> v) {
    this.vecSubsWord = v;
  }

  public void addSubsWord(EPLeaf epl) {
    this.vecSubsWord.addElement(epl);
  }

  public void toEPWord(CALL_wordStruct word) {
    // this.setIntLevel(word.getLevel());
    // this.setIntType(LexiconParser.LEX_TYPE_TRANSFORM[word.getType()]);
    this.setIntType(word.getType());
    this.setStrKana(word.getKana());
    this.setStrKanji(word.getKanji());

    // search id in the new lexicon
  }

  public Object clone() {
    EPWord o = null;
    o = (EPWord) super.clone();
    Vector<EPLeaf> v = new Vector<EPLeaf>();
    for (int i = 0; i < getVecSubsWord().size(); i++) {
      EPLeaf jwb = getVecSubsWord().get(i);
      v.addElement((EPLeaf) jwb.clone());
    }
    o.setVecSubsWord(v);
    return o;
  }

  public String getStrSetting() {
    return StrSetting;
  }

  public void setStrSetting(String strSetting) {
    StrSetting = strSetting;
  }

}
