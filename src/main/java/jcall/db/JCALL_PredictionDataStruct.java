/**
 * Created on 2007/05/31
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

import java.io.Serializable;
import java.util.Vector;

import org.apache.log4j.Logger;

public class JCALL_PredictionDataStruct extends JCALL_WordBase implements Serializable {
  static Logger logger = Logger.getLogger(JCALL_PredictionDataStruct.class.getName());

  String strClass; // word or group
  // boolean booAltkana ; //if the original word in the lexicon has a altkana
  String strREF; // although only a few words have this field, so far we just
                 // add this extra word.
  String type;
  String strError;
  String strSlot;
  String strCondition;
  String strSubword;
  String strRule;
  String strCategory;
  boolean bAccept; // true, answer; false, not an alternative answer;
  boolean bValid; // true, in dictionary; false, out of dictionary;

  Vector vecLesson; // if lesson = -1, belong to any lesson.
  Vector<String> vecVerbForm; // each element is String :type, tense,negative etc.
  Vector<String> vecVerbInvalidFrom; // each element is String :T1T2 etc.
  Vector<String> vecSpecific; // each element is String,kanji or Kana

  public JCALL_PredictionDataStruct() {
    super();
    init();
  }

  void init() {
    strClass = "word";
    strREF = "";
    strError = "";
    strSlot = "";
    strCondition = "";
    strSubword = "";
    type = "";
    strRule = "";
    strCategory = "";
    bAccept = false;
    bValid = true;
    vecLesson = new Vector<String>();
    vecVerbForm = new Vector<String>();
    vecVerbInvalidFrom = new Vector<String>();
    vecSpecific = new Vector<String>();
  }

  public void addToVecLesson(String str) {
    vecLesson.addElement(str);
  }

  public void addToVecVerbForm(String str) {
    vecVerbForm.addElement(str);
  }

  public void addToVecVerbInvalidFrom(String str) {
    vecVerbInvalidFrom.addElement(str);
  }

  public void addToVecSpecific(String str) {
    vecSpecific.addElement(str);
  }

  /*
   * no use now
   * 
   * @see java.lang.Object#toString()
   */

  public String getStrRule() {
    return strRule;
  }

  public void setStrRule(String strRule) {
    this.strRule = strRule;
  }

  public String toString() {
    String strResult = "";
    // strResult += "<PredictionDataMeta>"
    strResult += "-strClass " + strClass + "\n";
    strResult += "-id " + this.getId() + "\n";
    strResult += "-type " + this.getIntType() + "\n";
    strResult += "-strKana " + this.getStrKana() + "\n";
    strResult += "-strKanji " + this.getStrKanji() + "\n";
    strResult += "-strREF " + strREF + "\n";
    strResult += "-strSubword " + strSubword + "\n";
    strResult += "-strError " + strError + "\n";
    strResult += "-vecSpecific ";
    for (int i = 0; i < vecSpecific.size(); i++) {

      strResult += " " + vecSpecific.get(i);
    }

    strResult += "\n-vecVerbForm ";
    for (int i = 0; i < vecVerbForm.size(); i++) {

      strResult += " " + vecVerbForm.get(i);
    }

    strResult += "\n-vecVerbInvalidFrom ";
    for (int i1 = 0; i1 < vecVerbInvalidFrom.size(); i1++) {

      strResult += vecVerbInvalidFrom.get(i1);
    }
    strResult += "\n-vecLesson ";
    for (int i2 = 0; i2 < vecLesson.size(); i2++) {
      strResult += " " + vecLesson.get(i2);
    }

    // strResult += "-vecHonorificWord-";
    return strResult;
  }

  public boolean isBAccept() {
    return bAccept;
  }

  public void setBAccept(boolean accept) {
    bAccept = accept;
  }

  public boolean isBValid() {
    return bValid;
  }

  public void setBValid(boolean valid) {
    bValid = valid;
  }

  public String getStrClass() {
    return strClass;
  }

  public void setStrClass(String strClass) {
    this.strClass = strClass;
  }

  public String getStrCondition() {
    return strCondition;
  }

  public void setStrCondition(String strCondition) {
    this.strCondition = strCondition;
  }

  public String getStrError() {
    return strError;
  }

  public void setStrError(String strError) {
    this.strError = strError;
  }

  public String getStrREF() {
    return strREF;
  }

  public void setStrREF(String strREF) {
    this.strREF = strREF;
  }

  public String getStrSlot() {
    return strSlot;
  }

  public void setStrSlot(String strSlot) {
    this.strSlot = strSlot;
  }

  public String getStrSubword() {
    return strSubword;
  }

  public void setStrSubword(String strSubword) {
    this.strSubword = strSubword;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Vector getVecLesson() {
    return vecLesson;
  }

  public void setVecLesson(Vector vecLesson) {
    this.vecLesson = vecLesson;
  }

  public Vector<String> getVecSpecific() {
    return vecSpecific;
  }

  public void setVecSpecific(Vector<String> vecSpecific) {
    this.vecSpecific = vecSpecific;
  }

  public Vector<String> getVecVerbForm() {
    return vecVerbForm;
  }

  public void setVecVerbForm(Vector<String> vecVerbForm) {
    this.vecVerbForm = vecVerbForm;
  }

  public Vector<String> getVecVerbInvalidFrom() {
    return vecVerbInvalidFrom;
  }

  public void setVecVerbInvalidFrom(Vector<String> vecVerbInvalidFrom) {
    this.vecVerbInvalidFrom = vecVerbInvalidFrom;
  }

  public String getStrWord() {
    return this.getStrKana();
  }

  public String getLessonlist() {
    String result = "";
    if (this.vecLesson != null) {
      for (int i = 0; i < vecLesson.size(); i++) {
        String str = (String) vecLesson.elementAt(i);
        result = result + " | " + str;
      }
    }

    return result;

  }

  public boolean isHasLesson(String lesson) {
    // logger.debug("enter isHasLesson");
    if (this.vecLesson != null) {
      for (int i = 0; i < vecLesson.size(); i++) {
        String str = (String) vecLesson.elementAt(i);
        if (str.equalsIgnoreCase(lesson) || str.equalsIgnoreCase("-1")) {
          // logger.debug("return: TRUE");
          return true;
        }
      }
    }
    // logger.debug("return: FALSE");

    return false;

  }

  public boolean isHasLesson(int lesson) {
    String slesson = new String("" + lesson);
    if (this.vecLesson != null) {
      for (int i = 0; i < vecLesson.size(); i++) {
        String str = (String) vecLesson.elementAt(i);
        if (str.equalsIgnoreCase(slesson) || str.equalsIgnoreCase("-1")) {
          return true;
        }
      }
    }

    return false;

  }

}
