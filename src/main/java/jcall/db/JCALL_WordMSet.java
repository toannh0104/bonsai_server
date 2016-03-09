/**
 * Created on 2007/10/25
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom2.Element;

public class JCALL_WordMSet {
  String strMType;
  String strScore;
  Vector<JCALL_WordMItem> vItem;

  // JCALL_WordMItem strEnglish;
  // JCALL_WordMItem strChinese;
  // JCALL_WordMItem strSynonym;
  // JCALL_WordMItem strViVT;
  // HashMap hm; //score,english,chinese,synonym

  public JCALL_WordMSet() {
    strMType = "default";
    // strEnglish=new JCALL_WordMItem();
    strScore = null;
    // strChinese=null;
    // strViVT=null;
    vItem = new Vector<JCALL_WordMItem>();
  }

  public JCALL_WordMSet(String strmtype) {
    strMType = strmtype;
    // strEnglish=new JCALL_WordMItem();
    strScore = null;
    // strChinese=null;
    // strViVT=null;
    vItem = new Vector<JCALL_WordMItem>();
  }

  public String getStrMType() {
    return strMType;
  }

  public void setStrMType(String strMType) {
    this.strMType = strMType;
  }

  public String getStrScore() {
    return strScore;
  }

  public void setStrScore(String strScore) {
    this.strScore = strScore;
  }

  public Element toElement() {
    Element elemRoot = new Element("set");
    if (strMType != null && strMType.length() > 0) {
      elemRoot.setAttribute("mtype", strMType);
    }
    if (strScore != null && strScore.length() > 0) {
      elemRoot.setAttribute("score", strScore);
    }
    if (vItem != null && vItem.size() > 0) {
      for (int i = 0; i < vItem.size(); i++) {
        JCALL_WordMItem wmi = vItem.elementAt(i);
        Element child = wmi.toElement();
        elemRoot.addContent(child);
      }
    }
    return elemRoot;
  }

  public void element2WordMeaning(Element e) {
    // JCALL_WordMeaning wm = new JCALL_WordMeaning();

    if (e.getAttribute("mtype") != null) {
      setStrMType(e.getAttributeValue("mtype"));
    }
    if (e.getAttribute("score") != null) {
      setStrScore(e.getAttributeValue("score"));
    }
    List content = e.getContent();
    Iterator iterator = content.iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      if (o instanceof Element) {
        Element child = (Element) o;
        JCALL_WordMItem wmi = new JCALL_WordMItem();
        wmi.elementToItem(child);
        this.addItem(wmi);
      }
    }
  }

  public Vector<JCALL_WordMItem> getVItem() {
    return vItem;
  }

  public void setVItem(Vector<JCALL_WordMItem> item) {
    vItem = item;
  }

  public void addItem(JCALL_WordMItem wmi) {
    vItem.add(wmi);
  }

}