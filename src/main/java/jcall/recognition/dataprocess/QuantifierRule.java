/**
 * Created on 2007/06/02
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.Serializable;
import java.util.Vector;

import org.jdom2.Element;

public class QuantifierRule implements Serializable {
  String strName;
  Vector vecQC;

  public QuantifierRule() {
    vecQC = new Vector();

  }

  public Element QuantifierRule2Element() {
    QuantifierCategory qc;
    Element child;
    Element elemRoot = new Element("rule");
    elemRoot.setAttribute("name", this.getStrName());
    for (int i = 0; i < vecQC.size(); i++) {
      qc = (QuantifierCategory) vecQC.get(i);
      child = (Element) qc.toElement();
      elemRoot.addContent(child);
    }
    return elemRoot;
  }

  public Vector getVecQC() {
    return vecQC;
  }

  public void setVecQC(Vector vecQC) {
    this.vecQC = vecQC;
  }

  public String getStrName() {
    return strName;
  }

  public void setStrName(String strName) {
    this.strName = strName;
  }

}
