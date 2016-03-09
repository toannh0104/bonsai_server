/**
 * Created on 2007/06/02
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import jcall.util.XmlFileManager;

import org.jdom2.Document;
import org.jdom2.Element;

public class QuantifierRuleParser {

  XmlFileManager xfm;

  public QuantifierRuleParser() {
    xfm = new XmlFileManager();
  }

  public QuantifierRule loadFromFile(String strPath) {
    QuantifierRule qr = null;
    System.out.println("Open file, the Path is : " + strPath + ". start load........");
    Document oDoc = xfm.load2Doc(strPath);
    if (oDoc == null)
      return null;
    Element oRootElement = oDoc.getRootElement();
    if (oRootElement != null)
      qr = Element2QuantifierRule(oRootElement);
    else
      System.out.println("There is no rule in File: " + strPath);
    return qr;
  }

  public boolean saveToFile(QuantifierRule qr, String strPath) {
    Document oXmlDom = new Document();
    oXmlDom.setRootElement(QuantifierRule2Element(qr));
    return xfm.save2File(oXmlDom, strPath);
  }

  public Element QuantifierRule2Element(QuantifierRule qr) {
    QuantifierCategory qc;
    Element child;
    Element elemRoot = new Element("rule");
    elemRoot.setAttribute("name", qr.getStrName());
    Vector v = qr.getVecQC();
    for (int i = 0; i < v.size(); i++) {
      qc = (QuantifierCategory) v.get(i);
      child = (Element) qc.toElement();
      elemRoot.addContent(child);
    }
    return elemRoot;
  }

  public QuantifierRule Element2QuantifierRule(Element e) {
    QuantifierRule qr = new QuantifierRule();
    QuantifierCategory qc = new QuantifierCategory();
    Vector<QuantifierCategory> v = new Vector<QuantifierCategory>();
    qr.setStrName(e.getAttributeValue("name"));
    List content = e.getContent();
    Iterator iterator = content.iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      if (o instanceof Element) {
        Element child = (Element) o;
        qc = ElementtoQuantifierCategory(child);
        v.addElement(qc);
      }
    }
    qr.setVecQC(v);
    return qr;
  }

  public QuantifierCategory ElementtoQuantifierCategory(Element e) {
    QuantifierCategory qc = new QuantifierCategory();
    QuantifierNode qn = new QuantifierNode();
    Vector<QuantifierNode> v = new Vector<QuantifierNode>();
    qc.setStrName(e.getAttributeValue("name"));
    qc.setStrType(e.getAttributeValue("type"));
    List content = e.getContent();
    Iterator iterator = content.iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      if (o instanceof Element) {
        Element child = (Element) o;
        qn = ElementtoQuantifierNode(child);
        qn.setStrQuantifier(e.getAttributeValue("name"));
        v.addElement(qn);
      }
    }
    qc.setVecNode(v);
    return qc;
  }

  public QuantifierNode ElementtoQuantifierNode(Element e) {
    QuantifierNode qn = new QuantifierNode();
    qn.strKana = e.getAttributeValue("kana");
    qn.strKanji = e.getAttributeValue("kanji");
    qn.strNumeral = e.getAttributeValue("numeral");
    qn.strNumeralRomaji = e.getAttributeValue("numeralRomaji");
    if (e.getAttributeValue("altKana") != null) {
      qn.strAltKana = e.getAttributeValue("altKana");
    } else {
      qn.strAltKana = "";
    }
    return qn;
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
