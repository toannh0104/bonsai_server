/**
 * Created on 2007/06/02
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.Serializable;

import org.jdom2.Element;

public class QuantifierNode implements Serializable {

  String strNumeral;
  String strNumeralRomaji;
  String strQuantifier;
  String strKanji;
  String strKana;
  String strAltKana;

  public QuantifierNode() {
    init();
  }

  private void init() {
    strNumeral = "";
    strNumeralRomaji = "";
    strQuantifier = "";
    strKanji = "";
    strKana = "";
    strAltKana = "";
  }

  public Element toElement() {
    Element element = new Element("node");
    element.setAttribute("numeral", this.strNumeral);
    element.setAttribute("numeralRomaji", this.strNumeralRomaji);
    element.setAttribute("quantifier", this.strQuantifier);
    element.setAttribute("kanji", this.strKanji);
    element.setAttribute("kana", this.strKana);
    element.setAttribute("altKana", this.strAltKana);
    return element;

  }

  public String getStrAltKana() {
    return strAltKana;
  }

  public void setStrAltKana(String strAltKana) {
    this.strAltKana = strAltKana;
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

  public String getStrNumeral() {
    return strNumeral;
  }

  public void setStrNumeral(String strNumeral) {
    this.strNumeral = strNumeral;
  }

  public String getStrNumeralRomaji() {
    return strNumeralRomaji;
  }

  public void setStrNumeralRomaji(String strNumeralRomaji) {
    this.strNumeralRomaji = strNumeralRomaji;
  }

  public String getStrQuantifier() {
    return strQuantifier;
  }

  public void setStrQuantifier(String strQuantifier) {
    this.strQuantifier = strQuantifier;
  }
}
