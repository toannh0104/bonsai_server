/**
 * Created on 2007/06/18
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import jcall.util.XmlFileManager;

import org.jdom2.Element;

public class EPRuleParser {

  XmlFileManager xfm;

  public EPRuleParser() {
    xfm = new XmlFileManager();
  }

  /*
   * public EPRule loadFromFile(String strPath) { EPRule epr = null;
   * System.out.println("Open file, the Path is : " + strPath +
   * ". start load........"); Document oDoc = xfm.load2Doc(strPath); if(oDoc ==
   * null) return null; Element oRootElement = oDoc.getRootElement();
   * if(oRootElement != null) epr = Element2EPRule(oRootElement); else
   * System.out.println("There is no rule in File: " + strPath); return epr; }
   * 
   * public boolean saveToFile(EPRule epr, String strPath) { Document oXmlDom =
   * new Document(); oXmlDom.setRootElement(EPRule2Element(epr)); return
   * xfm.save2File(oXmlDom, strPath); }
   * 
   * public Element QuantifierRule2Element(QuantifierRule qr) {
   * QuantifierCategory qc; Element child; Element elemRoot = new
   * Element("rule"); elemRoot.setAttribute("name",qr.getStrName()); Vector v =
   * qr.getVecQC(); for (int i = 0; i <v.size(); i++) { qc =
   * (QuantifierCategory)v.get(i); child=(Element)qc.toElement();
   * elemRoot.addContent(child); } return elemRoot; } public EPRule
   * EPLeaf2Element(Element e) { EPRule qr = new EPRule(); QuantifierCategory qc
   * = new QuantifierCategory(); Vector<QuantifierCategory> v = new
   * Vector<QuantifierCategory>(); qr.setStrName(e.getAttributeValue("name"));
   * List content = e.getContent(); Iterator iterator = content.iterator();
   * while (iterator.hasNext()) { Object o = iterator.next(); if (o instanceof
   * Element) { Element child = (Element)o; qc =
   * ElementtoQuantifierCategory(child); v.addElement(qc); } } qr.setVecQC(v);
   * return qr; }
   */

  public Element EPWord2Element(EPWord epw) {
    EPLeaf epChild;
    Element child;
    Element elemRoot = new Element("word");
    elemRoot.setAttribute("id", Integer.toString(epw.getId()));
    elemRoot.setAttribute("type", Integer.toString(epw.getIntType()));
    elemRoot.setAttribute("kana", epw.getStrKana());
    elemRoot.setAttribute("kanji", epw.getStrKanji());
    elemRoot.setAttribute("altKana", epw.getStrAltKana());
    elemRoot.setAttribute("engKana", epw.getStrEngKana());
    Vector v = epw.getVecSubsWord();
    for (int i = 0; i < v.size(); i++) {
      epChild = (EPLeaf) v.elementAt(i);
      child = EPLeaf2Element(epChild);
      elemRoot.addContent(child);
    }
    return elemRoot;
  }

  public EPWord Element2EPWord(Element e) {
    EPWord epr = new EPWord();
    EPLeaf epl = new EPLeaf();
    Vector<EPLeaf> v = new Vector<EPLeaf>();
    if (e.getAttribute("id") != null) {
      epr.setId(Integer.getInteger(e.getAttributeValue("id")));
    }
    if (e.getAttribute("type") != null) {
      epr.setIntType(Integer.getInteger(e.getAttributeValue("type")));
    }
    if (e.getAttribute("kana") != null) {
      epr.setStrKana(e.getAttributeValue("type"));
    }
    if (e.getAttribute("kanji") != null) {
      epr.setStrKanji(e.getAttributeValue("type"));
    }
    if (e.getAttribute("altKana") != null) {
      epr.setStrAltKana(e.getAttributeValue("altKana"));
    }
    if (e.getAttribute("altKana") != null) {
      epr.setStrEngKana(e.getAttributeValue("altKana"));
    }
    List content = e.getContent();
    Iterator iterator = content.iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      if (o instanceof Element) {
        Element child = (Element) o;
        epl = Element2EPLeaf(child);
        v.addElement(epl);
      }
    }
    epr.setVecSubsWord(v);
    return epr;
  }

  public EPLeaf Element2EPLeaf(Element e) {
    EPLeaf epl = new EPLeaf();
    if (e.getAttribute("id") != null) {
      epl.setId(Integer.getInteger(e.getAttributeValue("id")));
    }
    if (e.getAttribute("type") != null) {
      epl.setIntType(Integer.getInteger(e.getAttributeValue("type")));
    }
    if (e.getAttribute("kana") != null) {
      epl.setStrKana(e.getAttributeValue("type"));
    }
    if (e.getAttribute("kanji") != null) {
      epl.setStrKanji(e.getAttributeValue("type"));
    }
    if (e.getAttribute("errorType") != null) {
      epl.setStrErrorType(e.getAttributeValue("errorType"));
    }
    if (e.getAttribute("errorDescription") != null) {
      epl.setStrErrorDescription(e.getAttributeValue("errorDescription"));
    }
    return epl;
  }

  public Element EPLeaf2Element(EPLeaf epl) {

    Element elemRoot = new Element("leaf");
    elemRoot.setAttribute("id", Integer.toString(epl.getId()));
    elemRoot.setAttribute("type", Integer.toString(epl.getIntType()));
    elemRoot.setAttribute("kana", epl.getStrKana());
    elemRoot.setAttribute("kanji", epl.getStrKanji());
    elemRoot.setAttribute("errorType", epl.getStrErrorType());
    // elemRoot.setAttribute("restrict",epl.getStrRestrict());
    elemRoot.setAttribute("errorDescription", epl.getStrErrorDescription());

    return elemRoot;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    String strQuantifierRuleName = "./Data/QuantiferRules.xml";
    QuantifierRuleParser qrp = new QuantifierRuleParser();
    QuantifierRule qr = qrp.loadFromFile(strQuantifierRuleName);
    if (qr != null) {
      System.out.println(qr.getStrName());
      // QuantifierCategory qc;
      Vector v = qr.getVecQC();
      for (int i = 0; i < v.size(); i++) {
        QuantifierCategory qc = (QuantifierCategory) v.get(i);
        Vector v2 = qc.getVecNode();
        for (int j = 0; j < v2.size(); j++) {
          QuantifierNode qn = (QuantifierNode) v2.get(j);
          System.out.println("j=" + j + qn.getStrKana() + qn.getStrQuantifier());
        }
      }
    } else {
      System.out.println("wrong");
    }
  }

}
