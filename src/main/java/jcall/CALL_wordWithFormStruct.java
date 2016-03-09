///////////////////////////////////////////////////////////////////
// Lexicon Structure - holds the information about all the words
//
//

package jcall;

import jcall.db.JCALL_Word;

public class CALL_wordWithFormStruct {
  String surfaceFormKanji;
  String surfaceFormKana;
  String surfaceFormRomaji;

  CALL_wordStruct baseWord;

  JCALL_Word call_word;

  String transformationRule;
  int sign;
  int tense;
  int style;
  int type;

  boolean complete;

  public CALL_wordWithFormStruct() {
    init();
  }

  public CALL_wordWithFormStruct(String strJ, String strK, CALL_wordStruct w, String rule, int s, int t, int st, int ty) {
    init();

    if ((strJ != null) && (strK != null) && (w != null)) {
      surfaceFormKanji = strJ;
      surfaceFormKana = strK;
      surfaceFormRomaji = CALL_io.strKanaToRomaji(surfaceFormKana);

      baseWord = w;

      transformationRule = rule;
      sign = s;
      tense = t;
      style = st;
      type = ty;

      complete = true;
    }
  }

  public CALL_wordWithFormStruct(String strJ, String strK, JCALL_Word w, String rule, int s, int t, int st, int ty) {
    init();

    if ((strJ != null) && (strK != null) && (w != null)) {
      surfaceFormKanji = strJ;
      surfaceFormKana = strK;
      surfaceFormRomaji = CALL_io.strKanaToRomaji(surfaceFormKana);

      call_word = w;

      transformationRule = rule;
      sign = s;
      tense = t;
      style = st;
      type = ty;

      complete = true;
    }
  }

  public void init() {
    complete = false;
    surfaceFormKanji = null;
    surfaceFormKana = null;
    surfaceFormRomaji = null;

    baseWord = null;
    call_word = null;

    transformationRule = null;
    sign = -1;
    tense = -1;
    style = -1;
    type = -1;
  }

  public CALL_wordStruct getBaseWord() {
    return baseWord;
  }

  public void setBaseWord(CALL_wordStruct baseWord) {
    this.baseWord = baseWord;
  }

  public JCALL_Word getCall_word() {
    return call_word;
  }

  public void setCall_word(JCALL_Word call_word) {
    this.call_word = call_word;
  }

  public boolean isComplete() {
    return complete;
  }

  public void setComplete(boolean complete) {
    this.complete = complete;
  }

  public int getSign() {
    return sign;
  }

  public void setSign(int sign) {
    this.sign = sign;
  }

  public int getStyle() {
    return style;
  }

  public void setStyle(int style) {
    this.style = style;
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

  public int getTense() {
    return tense;
  }

  public void setTense(int tense) {
    this.tense = tense;
  }

  public String getTransformationRule() {
    return transformationRule;
  }

  public void setTransformationRule(String transformationRule) {
    this.transformationRule = transformationRule;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

}