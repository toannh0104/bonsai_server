/**
 * Created on 2007/06/02
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.Serializable;
import java.util.Vector;

import org.jdom2.Element;

public class QuantifierCategory implements Serializable {
  String strName;
  String strType;
  Vector vecNode;

  public QuantifierCategory() {
    init();
  }

  private void init() {
    strName = "";
    strType = "";
    vecNode = new Vector();
  }

  public Element toElement() {
    QuantifierNode qn;
    Element child;
    Element elemCategory = new Element("category");
    elemCategory.setAttribute("name", this.getStrName());
    elemCategory.setAttribute("type", this.getStrType());
    for (int i = 0; i < vecNode.size(); i++) {
      qn = (QuantifierNode) vecNode.get(i);
      child = (Element) qn.toElement();
      elemCategory.addContent(child);
    }
    return elemCategory;

  }

  public String getStrType() {
    return strType;
  }

  public void setStrType(String strType) {
    this.strType = strType;
  }

  public String getStrName() {
    return strName;
  }

  public void setStrName(String strName) {
    this.strName = strName;
  }

  public Vector getVecNode() {
    return vecNode;
  }

  public void setVecNode(Vector vecNode) {
    this.vecNode = vecNode;
  }

}
