/**
 * Created on 2007/11/08
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

import org.apache.log4j.Logger;
import org.jdom2.Element;

public abstract class JCALL_WordBase implements Comparable {
  static Logger logger = Logger.getLogger(JCALL_WordBase.class.getName());

  final int UNDEDINED = -999;

  int intID;
  int intType;
  String strKana;
  String strKanji;
  String strAltkana;

  // String strPronunciation; // finally delete this item
  public JCALL_WordBase() {
    init();
  }

  private void init() {
    this.intID = UNDEDINED;
    this.intType = UNDEDINED;
    this.strKanji = "";
    this.strKana = "";
    this.strAltkana = "";
    // strPronunciation="";
  }

  public JCALL_WordBase(int id, int intType, String strKana, String strKanji) {
    super();
    this.intID = id;
    this.intType = intType;
    this.strKana = strKana;
    this.strKanji = strKanji;
    // strPronunciation="";
  }

  // public JCALL_WordBase(int id, String strKana, String strKanji) {
  // super();
  // this.intID = id;
  // this.intType = -1;
  // this.strKana = strKana;
  // this.strKanji = strKanji;
  // // strPronunciation="";
  // }
  public JCALL_WordBase(int intType, String strKana, String strKanji) {
    super();
    this.intID = UNDEDINED;
    this.intType = intType;
    this.strKana = strKana;
    this.strKanji = strKanji;
    // strPronunciation="";
  }

  public int getId() {
    return intID;
  }

  public void setId(int id) {
    this.intID = id;
  }

  public int getIntType() {
    return intType;
  }

  public void setIntType(int intType) {
    this.intType = intType;
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

  public int compareTo(Object o) {

    if (o instanceof JCALL_WordBase) {
      JCALL_WordBase word = (JCALL_WordBase) o;
      if (this.strKanji.compareTo(word.getStrKanji()) == 0) {
        // logger.debug("return: 0");
        return 0;
      } else {
        // logger.debug("return: 1");
        return 1;
      }
    }
    // logger.debug("return: -1");
    return -1;
  }

  public String getStrAltkana() {
    return strAltkana;
  }

  public void setStrAltkana(String strAltkana) {
    this.strAltkana = strAltkana;
  }

  public Element toElement() {
    Element elemRoot = new Element("word");
    if (this.intID != UNDEDINED && this.intID != -1) {
      elemRoot.setAttribute("id", Integer.toString(this.getId()));
    }
    if (this.getIntType() >= -1) {
      elemRoot.setAttribute("type", Integer.toString(this.getIntType()));
    }
    if (this.getStrKana() != null && this.getStrKana().length() > 0) {
      elemRoot.setAttribute("kana", this.getStrKana());
    }
    if (this.getStrKanji() != null && this.getStrKanji().length() > 0) {
      elemRoot.setAttribute("kanji", this.getStrKanji());
    }
    if (this.getStrAltkana() != null && this.getStrAltkana().length() > 0) {
      elemRoot.setAttribute("altkana", this.getStrAltkana());
    }
    return elemRoot;
  }

  public void toObject(Element e) {
    if (e.getAttribute("id") != null) {
      this.setId(Integer.parseInt(e.getAttributeValue("id")));
    }
    if (e.getAttribute("type") != null) {
      this.setIntType(Integer.parseInt(e.getAttributeValue("type")));
    }
    if (e.getAttribute("kana") != null) {
      this.setStrKana(e.getAttributeValue("kana"));
    }
    if (e.getAttribute("kanji") != null) {
      this.setStrKanji(e.getAttributeValue("kanji"));
    }
    if (e.getAttribute("altkana") != null) {
      this.setStrAltkana(e.getAttributeValue("altkana"));
    }
  }

}
