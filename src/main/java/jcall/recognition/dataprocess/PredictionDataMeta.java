/**
 * Created on 2007/05/31
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.Serializable;
import java.util.Vector;

public class PredictionDataMeta implements Cloneable, Serializable {
  int lesson; // if lesson = -1, belong to any lesson.
  String strClass; // grammar,concept,lexicon
  boolean booAltkana; // if the original word in the lexicon has a altkana
  int id; // the same with its lexicon
  int type;
  String strWord;
  String strKanji;
  String strREF; // although only a few words have this field, so far we just
                 // add this extra word.

  // String strSubWord; //if simple one subWord, then fill in
  // String strAltWord; //if simple one altWord, then fill in
  // String strHonorificWord;
  String strRestriction;
  // String strDataType; //simple
  int confusionCount; // total of all the changeable words
  Vector<String> vecAltWord; // each element is String,kanji or Kana
  Vector<String> vecVerbType; // each element is String :type, tense,negative
                              // etc.
  Vector<String> vecVerbInvalidFrom; // each element is String :T1T2 etc.
  Vector<String> vecSubWord; // each element is String,kanji or Kana
  Vector<String> vecHonorificWord; // each element is String,kanji or Kana
  Vector<String> vecWrongHonorificWord; // each element is String,kanji or Kana

  // Vector vecAllWord; //in this vector, all the type is word,subwords
  public PredictionDataMeta() {
    init();
  }

  public int getLesson() {
    return lesson;
  }

  public void setLesson(int lesson) {
    this.lesson = lesson;
  }

  public String getStrClass() {
    return strClass;
  }

  public void setStrClass(String strClass) {
    this.strClass = strClass;
  }

  void init() {
    lesson = -1;
    strClass = "Lexicon";
    strKanji = "";
    booAltkana = false;
    id = -1;
    type = -1;
    strWord = "";
    vecAltWord = new Vector<String>();
    vecSubWord = new Vector<String>();
    vecHonorificWord = new Vector<String>();
    vecWrongHonorificWord = new Vector<String>();
    confusionCount = 0;
    strREF = "";
    strRestriction = "";
    vecVerbType = new Vector<String>();
    vecVerbInvalidFrom = new Vector<String>();
  }

  public void addCount() {
    this.confusionCount++;
  }

  public void addCount(int i) {
    this.confusionCount += i;
  }

  public void clear() {
    booAltkana = false;
    id = -1;
    type = -1;
    strWord = "";
    strKanji = "";
    vecAltWord = new Vector<String>();
    vecSubWord = new Vector<String>();
    vecHonorificWord = new Vector<String>();
    vecWrongHonorificWord = new Vector<String>();
    confusionCount = 0;
    strREF = "";
    vecVerbType = new Vector<String>();
    vecVerbInvalidFrom = new Vector<String>();

  }

  public PredictionDataMeta doClone() {
    PredictionDataMeta o = null;
    try {
      o = (PredictionDataMeta) super.clone();
      Vector<String> v = new Vector<String>();
      for (int i = 0; i < getVecAltWord().size(); i++) {
        v.addElement((String) getVecAltWord().get(i));
      }
      o.setVecAltWord(v);
      Vector<String> vSub = new Vector<String>();
      for (int j = 0; j < getVecSubWord().size(); j++) {
        vSub.addElement((String) getVecSubWord().get(j));
      }
      o.setVecSubWord(vSub);

      Vector<String> vHonorific = new Vector<String>();
      for (int k = 0; k < getVecHonorificWord().size(); k++) {
        vHonorific.addElement((String) getVecHonorificWord().get(k));
      }
      o.setVecHonorificWord(vHonorific);

      Vector<String> vWrongHonorific = new Vector<String>();
      for (int m = 0; m < this.getVecWrongHonorificWord().size(); m++) {
        vWrongHonorific.addElement((String) this.getVecWrongHonorificWord().get(m));
      }
      o.setVecWrongHonorificWord(vWrongHonorific);

      Vector<String> vvecVerbType = new Vector<String>();
      for (int m = 0; m < getVecVerbType().size(); m++) {
        vvecVerbType.addElement((String) getVecVerbType().get(m));
      }
      o.setVecVerbType(vvecVerbType);

      Vector<String> vVerbInvalidFrom = new Vector<String>();
      for (int m = 0; m < getVecVerbInvalidFrom().size(); m++) {
        vVerbInvalidFrom.addElement((String) getVecVerbInvalidFrom().get(m));
      }
      o.setVecVerbInvalidFrom(vVerbInvalidFrom);
      // o.setStrREF(getStrREF());

    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return o;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getStrWord() {
    return strWord;
  }

  public void setStrWord(String strWord) {
    this.strWord = strWord;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public Vector getVecAltWord() {
    return vecAltWord;
  }

  public void setVecAltWord(Vector<String> vAltWord) {
    this.vecAltWord = vAltWord;
  }

  public Vector getVecHonorificWord() {
    return vecHonorificWord;
  }

  public void setVecHonorificWord(Vector<String> vHonorificWord) {
    this.vecHonorificWord = vHonorificWord;
  }

  public Vector getVecSubWord() {
    return vecSubWord;
  }

  public void setVecSubWord(Vector<String> vSubWord) {
    this.vecSubWord = vSubWord;
  }

  public Vector<String> getVecWrongHonorificWord() {
    return vecWrongHonorificWord;
  }

  public void setVecWrongHonorificWord(Vector<String> vecWrongHonorificWord) {
    this.vecWrongHonorificWord = vecWrongHonorificWord;
  }

  public int getConfusionCount() {
    return confusionCount;
  }

  public void setConfusionCount(int confusionCount) {
    this.confusionCount = confusionCount;
  }

  public String getStrRestriction() {
    return strRestriction;
  }

  public void setStrRestriction(String strRestriction) {
    this.strRestriction = strRestriction;
  }

  public boolean isBooAltkana() {
    return booAltkana;
  }

  public void setBooAltkana(boolean booAltkana) {
    this.booAltkana = booAltkana;
  }

  public void addToVecSubWord(String strSubWord) {
    vecSubWord.addElement(strSubWord);
  }

  public void addToHonorificWord(String strHonorificWord) {
    vecHonorificWord.addElement(strHonorificWord);
  }

  public void addToVecAltWord(String strAltWord) {
    vecAltWord.addElement(strAltWord);
  }

  public void addToWrongHonorificWord(String strWord) {
    vecWrongHonorificWord.addElement(strWord);
  }

  public String getStrKanji() {
    return strKanji;
  }

  public void setStrKanji(String strKanji) {
    this.strKanji = strKanji;
  }

  public String getStrREF() {
    return strREF;
  }

  public void setStrREF(String strREF) {
    this.strREF = strREF;
  }

  public Vector<String> getVecVerbType() {
    return vecVerbType;
  }

  public void setVecVerbType(Vector<String> vecVerbType) {
    this.vecVerbType = vecVerbType;
  }

  public void addToVecVerbType(String strVerbType) {
    vecVerbType.addElement(strVerbType);
  }

  public Vector<String> getVecVerbInvalidFrom() {
    return vecVerbInvalidFrom;
  }

  public void setVecVerbInvalidFrom(Vector<String> vecVerbInvalidFrom) {
    this.vecVerbInvalidFrom = vecVerbInvalidFrom;
  }

  public void addToVecVerbInvalidFrom(String strVerbInvalidFrom) {
    vecVerbInvalidFrom.addElement(strVerbInvalidFrom);
  }

  public String toString() {
    String strResult = "";
    // strResult += "<PredictionDataMeta>"
    strResult += "-lesson " + lesson + "\n";
    strResult += "-strClass " + strClass + "\n";
    strResult += "-id " + id + "\n";
    strResult += "-type " + type + "\n";
    strResult += "-strWord " + strWord + "\n";
    strResult += "-strKanji " + strKanji + "\n";
    strResult += "-strREF " + strREF + "\n";
    strResult += "-strRestriction " + strRestriction + "\n";
    strResult += "-vecAltWord";
    for (int i = 0; i < vecAltWord.size(); i++) {

      strResult += vecAltWord.get(i);
    }

    strResult += "\n-vecVerbType ";
    for (int i = 0; i < vecVerbType.size(); i++) {

      strResult += vecVerbType.get(i);
    }

    strResult += "\n-vecSubWord ";
    for (int i1 = 0; i1 < vecSubWord.size(); i1++) {

      strResult += vecSubWord.get(i1);
    }
    strResult += "\n-vecHonorificWord ";
    for (int i2 = 0; i2 < vecHonorificWord.size(); i2++) {
      strResult += vecHonorificWord.get(i2);
    }
    strResult += "\n-vecWrongHonorificWord ";
    for (int i3 = 0; i3 < vecWrongHonorificWord.size(); i3++) {
      strResult += vecWrongHonorificWord.get(i3);
    }
    // strResult += "-vecHonorificWord-";
    return strResult;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    // TODO Auto-generated method stub
    return super.clone();
  }

}
