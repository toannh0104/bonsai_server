/**
 * Created on 2008/03/11
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

import java.util.Vector;

public class JCALL_NetworkSubNodeStruct extends JCALL_WordBase {

  String strClass; // grammar,concept,lexicon
  public String strError;
  public boolean bAccept; // true, answer; false, not an alternative answer;
  public boolean bValid; // true, in dictionary; false, out of dictionary;
  Vector<String> vecSpecific; // each element is String,kanji or Kana

  public JCALL_NetworkSubNodeStruct() {
    super();
    init();
  }

  void init() {
    strClass = "word";
    strError = "";
    bAccept = false;
    bValid = true;
    vecSpecific = new Vector<String>();
  }

  public void addToVecSpecific(String str) {
    vecSpecific.addElement(str);
  }

  public String toString() {
    String strResult = "";
    strResult += "-PNode" + "\n";
    // strResult += "<"+strClass+">"+"\n";
    // strResult += "-strClass "+strClass+"\n";
    // strResult += "-id "+this.getId()+"\n";
    // strResult += "-type "+this.getIntType()+"\n";
    strResult += "-strKana " + this.getStrKana() + "\n";
    strResult += "-strKanji " + this.getStrKanji() + "\n";
    strResult += "-strError " + strError + "\n";
    // strResult += "-vecSpecific";
    // for (int i = 0; i < vecSpecific.size(); i++) {
    //
    // strResult += vecSpecific.get(i);
    // }

    return strResult;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    // TODO Auto-generated method stub
    return super.clone();
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

  public String getStrError() {
    return strError;
  }

  public void setStrError(String strError) {
    this.strError = strError;
  }

  public Vector<String> getVecSpecific() {
    return vecSpecific;
  }

  public void setVecSpecific(Vector<String> vecSpecific) {
    this.vecSpecific = vecSpecific;
  }

  public boolean isHasErrSpecific(String specific) {
    if (vecSpecific != null) {
      for (int i = 0; i < vecSpecific.size(); i++) {
        if (specific.equalsIgnoreCase(vecSpecific.elementAt(i))) {
          return true;
        }

      }
    }
    return false;
  }

}
