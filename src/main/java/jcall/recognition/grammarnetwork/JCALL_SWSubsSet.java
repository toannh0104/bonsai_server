/**
 * Created on 2007/11/24
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.grammarnetwork;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom2.Element;

public class JCALL_SWSubsSet {
  Vector<JCALL_SWSubs> vSubsWord;

  public JCALL_SWSubsSet() {
    // TODO Auto-generated constructor stub
  }

  public Vector<JCALL_SWSubs> getVSubsWord() {
    return vSubsWord;
  }

  public void setVSubsWord(Vector<JCALL_SWSubs> subsWord) {
    vSubsWord = subsWord;
  }

  public void addObj(JCALL_SWSubs obj) {
    if (vSubsWord == null) {
      vSubsWord = new Vector<JCALL_SWSubs>();
    }
    vSubsWord.add(obj);
  }

  public Element toElement() {
    Element child = new Element("subs");
    for (int i = 0; i < vSubsWord.size(); i++) {
      JCALL_SWSubs w = vSubsWord.elementAt(i);
      Element eTemp = w.toElement();
      if (eTemp != null) {
        child.addContent(eTemp);
      }
    }
    return child;
  }

  public void toObject(Element e) {
    List listcontent = e.getContent();
    Iterator it = listcontent.iterator();
    while (it.hasNext()) {
      Object aObj = it.next();
      if (aObj instanceof Element) {
        Element parentchild = (Element) aObj;
        JCALL_SWSubs w = new JCALL_SWSubs();
        w.toObject(parentchild);
        this.addObj(w);
      }
    }// inside while
  }

}
