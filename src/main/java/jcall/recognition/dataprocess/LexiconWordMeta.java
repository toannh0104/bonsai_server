/**
 * Created on 2007/05/24
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.PrintWriter;
import java.util.Vector;

public class LexiconWordMeta implements Comparable {
  // Fields
  int id;
  int ComponentID;
  String strKanji;
  String strKana;
  String strAltkana;
  // String strEnglish;
  int intLevel;
  String strMainMeaning;
  String strOtherMeaning;
  int intType;
  // int intTypeSub;
  String strPicture;
  String strAttribute; // for verb,
                       // wuduan-Group1,yiduan-Group2,sabian-Group3,kabian-Group4,special-Group5
                       // like ��������
  String strCategory1; // for NQ , category1 is Numeral ;
  // for verb, category1 is
  // wuduan-Group1,yiduan-Group2,sabian-Group3,kabian-Group4,special-Group5 like
  // ��������
  String strCategory2;// for NQ , category2 is quantifier
  Vector categories;

  boolean active;

  public LexiconWordMeta() {

    this.id = -1;
    this.ComponentID = -1;
    this.strKanji = "";
    this.strKana = "";
    strAltkana = "";
    // this.strEnglish = "";
    this.intLevel = -1;
    this.strMainMeaning = "";
    strOtherMeaning = "";
    this.intType = -1;
    // this.intTypeSub = 0;
    this.strPicture = "";
    // this.strParameter="";
    this.strAttribute = "";
    this.strCategory1 = "";
    this.strCategory2 = "";
    categories = null;

    active = false;
  }

  public Vector getCategories() {
    return categories;
  }

  public void setCategories(Vector categories) {
    this.categories = categories;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIntLevel() {
    return intLevel;
  }

  public void setIntLevel(int intLevel) {
    this.intLevel = intLevel;
  }

  public int getIntType() {
    return intType;
  }

  public void setIntType(int intType) {
    this.intType = intType;
  }

  public String getPicture() {
    return strPicture;
  }

  public void setPicture(String picture) {
    this.strPicture = picture;
  }

  public String getStrCategory1() {
    return strCategory1;
  }

  public void setStrCategory1(String strCategory1) {
    this.strCategory1 = strCategory1;
  }

  public String getStrCategory2() {
    return strCategory2;
  }

  public void setStrCategory2(String strCategory2) {
    this.strCategory2 = strCategory2;
  }

  public String getStrMainMeaning() {
    return strMainMeaning;
  }

  public void setStrMainMeaning(String strGenMeaning) {
    this.strMainMeaning = strGenMeaning;
  }

  public String getStrOtherMeaning() {
    return strOtherMeaning;
  }

  public void setStrOtherMeaning(String strOtherMeaning) {
    this.strOtherMeaning = strOtherMeaning;
  }

  public String getStrKana() {
    return strKana;
  }

  public void setStrKana(String strKana) {
    this.strKana = strKana;
  }

  public String getStrKanji() {
    return strKanji;
  }

  public void setStrKanji(String strKanji) {
    this.strKanji = strKanji;
  }

  public String toString() {
    String strResult = "";
    strResult = "[" + strKanji + "]" + "[" + strKana + "]" + "[" + intLevel + "]" + "[" + strMainMeaning + "]" +

    "[" + strCategory1 + "]" + "[" + strCategory2 + "]";
    return strResult;
  }

  public String getStrAltkana() {
    return strAltkana;
  }

  public void setStrAltkana(String strAltkana) {
    this.strAltkana = strAltkana;
  }

  public String getStrPicture() {
    return strPicture;
  }

  public void setStrPicture(String strPicture) {
    this.strPicture = strPicture;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void toLexiconWord(QuantifierNode qn) {
    this.intLevel = 3;
    this.intType = Lexicon.LEX_TYPE_NUMQUANT;
    this.strKana = qn.getStrKana();
    this.strKanji = qn.getStrKanji();
    this.strCategory1 = qn.getStrNumeral();
    this.strCategory2 = qn.getStrQuantifier();
  }

  public String toVALUES() {
    String strResult = "(";
    strResult = strResult + intType + ",'" + strKanji + "','" + strKana + "','" + strMainMeaning + "','" + strPicture
        + "','" + strAltkana + "','" + strOtherMeaning + "','" + strCategory1 + "','" + strCategory2 + "')";
    return strResult;
  }

  public String toSubVALUES() {
    String strResult = "(";
    strResult = strResult + intType + ",'" + strKanji + "','" + strKana + "','" + strMainMeaning + "','" + strPicture
        + "','" + strAltkana + "','" + strOtherMeaning + "','" + strCategory1 + "','" + strCategory2 + "')";
    return strResult;

  }

  // String strLexiconFields =
  // "(Type,Level,Kanji,kana,MainMeaning,Picture,Altkana,OtherMeaning,Category1,Category2)";
  // /////////////////////////////////////////////////////////////////////////////
  // Writes the word to the file
  // /////////////////////////////////////////////////////////////////////////////
  public void write(PrintWriter outP) {

    outP.println("#");
    outP.println("-id " + id);
    outP.println("-componentID " + ComponentID);
    outP.println("-Type " + intType);
    outP.println("-Level " + intLevel);
    outP.println("-Kanji " + strKanji);
    outP.println("-Kana " + strKana);
    outP.println("-MainMeaning " + strMainMeaning);
    outP.println("-Picture " + strPicture);
    outP.println("-Altkana " + strAltkana);
    // outP.println("-English " + strEnglish);
    outP.println("-OtherMeaning " + strOtherMeaning);
    outP.println("-Attribute " + strAttribute);
    outP.println("-Category1 " + strCategory1);
    outP.println("-Category2 " + strCategory2);
  }

  public int compareTo(Object o) {

    if (o instanceof LexiconWordMeta) {
      LexiconWordMeta lwm = (LexiconWordMeta) o;
      if (this.strKana.compareTo(lwm.getStrKana()) == 0 && this.strKanji.compareTo(lwm.getStrKanji()) == 0
          && this.intType == lwm.getIntType()) {
        return 0;
      } else {
        return 1;
      }
    }
    return -1;
  }

  public int compareWith(LexiconWordMeta lwm) {

    if (this.id == lwm.id && this.strKana.compareTo(lwm.getStrKana()) == 0
        && this.strKanji.compareTo(lwm.getStrKanji()) == 0 && this.intType == lwm.getIntType()) {
      return 0;
    } else {
      return -1;
    }
  }

  public int getComponentID() {
    return ComponentID;
  }

  public void setComponentID(int componentID) {
    ComponentID = componentID;
  }

  public String getStrAttribute() {
    return strAttribute;
  }

  public void setStrAttribute(String strAttribute) {
    this.strAttribute = strAttribute;
  }

}