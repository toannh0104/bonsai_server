/**
 * Created on 2007/11/18
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.grammarnetwork;

import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;

public class JCALL_SWNode {
  String strName;

  JCALL_SWComponent swcomponent;
  // Vector<JCALL_WSubs> vSubsWord;

  JCALL_SWSubsSet swsubs;

  public JCALL_SWNode() {
    // TODO Auto-generated constructor stub
  }

  public String getStrName() {
    return strName;
  }

  public void setStrName(String strName) {
    this.strName = strName;
  }

  public JCALL_SWComponent getSwcomponent() {
    return swcomponent;
  }

  public void setSwcomponent(JCALL_SWComponent swcomponent) {
    this.swcomponent = swcomponent;
  }

  public JCALL_SWSubsSet getSwsubs() {
    return swsubs;
  }

  public void setSwsubs(JCALL_SWSubsSet swsubs) {
    this.swsubs = swsubs;
  }

  public Element toElement() {
    Element elemRoot = new Element("node");
    if (this.strName != null) {
      elemRoot.setAttribute("name", strName);
    }
    if (swcomponent != null) {
      Element eTemp = swcomponent.toElement();
      if (eTemp != null) {
        elemRoot.addContent(eTemp);
      }
    }
    if (swsubs != null) {
      Element eTemp = swsubs.toElement();
      if (eTemp != null) {
        elemRoot.addContent(eTemp);
      }
    }
    return elemRoot;
  }

  public void toObject(Element e) {
    if (e.getAttribute("name") != null) {
      this.setStrName(e.getAttributeValue("name"));
    }
    List content = e.getContent();
    Iterator iterator = content.iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      if (o instanceof Element) {
        Element child = (Element) o;
        if (child.getName().equalsIgnoreCase("word")) {
          JCALL_SWComponent swcomponent = new JCALL_SWComponent();
          swcomponent.toObject(child);
          this.setSwcomponent(swcomponent);
        } else if (child.getName().equalsIgnoreCase("subs")) {
          JCALL_SWSubsSet sw = new JCALL_SWSubsSet();
          sw.toObject(child);
          this.setSwsubs(sw);
        }

      }
    }// outside while

  }

}
