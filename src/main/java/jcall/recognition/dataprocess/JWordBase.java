/**
 * Created on 2007/06/05
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.Serializable;

public class JWordBase implements Cloneable, Serializable {

  int id;
  int intType;
  String strKana;
  String strKanji;
  String strAltKana;
  String strEngKana;

  public JWordBase() {
    init();
  }

  private void init() {

    this.id = -1;
    this.intType = -1;
    this.strKanji = "";
    this.strKana = "";
    this.strAltKana = "";
    this.strEngKana = "";
  }

  public JWordBase(int id, String strKana, String strKanji) {
    super();
    this.id = id;
    this.strKana = strKana;
    this.strKanji = strKanji;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIntType() {
    return intType;
  }

  public void setIntType(int intType) {
    this.intType = intType;
  }

  public String getStrAltKana() {
    return strAltKana;
  }

  public void setStrAltKana(String strAltKana) {
    this.strAltKana = strAltKana;
  }

  public String getStrEngKana() {
    return strEngKana;
  }

  public void setStrEngKana(String strEngKana) {
    this.strEngKana = strEngKana;
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

  public Object clone() {
    JWordBase o = null;
    try {
      o = (JWordBase) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return o;
  }

}
