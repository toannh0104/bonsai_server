package jcall.portal.appmgr;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import jcall.util.XmlFileManager;

import org.jdom2.Element;

/*
 * @author WHC
 * get appspace from a file or save into a file
 */

public class CAppSpaceAccess implements Serializable {

  private String m_strXmlFile;

  public CAppSpaceAccess(String s) {
    m_strXmlFile = s;
  }

  public CAppSpaceAccess() {

  }

  public CAppSpace getAppSpace(String s) {
    m_strXmlFile = s;
    System.out.println("In Space Access:" + m_strXmlFile);
    XmlFileManager cconversion = new XmlFileManager();

    Element element = cconversion.load2Element(m_strXmlFile);
    CAppSpace cappspace = new CAppSpace(element);
    return cappspace;
  }

  public CAppSpace createAppSpace() {
    CAppSpace cappspace = new CAppSpace();
    // cappspace.setOwner(s);
    cappspace.getSpaceDir().setTitle("My Desktop");
    return cappspace;
  }

  public void saveAppSpace(CAppSpace cappspace, String s) {
    Element element = cappspace.toXML();
    XmlFileManager cconversion = new XmlFileManager();

    cconversion.save2File(element, s);
  }

  private Element getElement(Vector vector, Element element) {
    Element element1 = null;
    List list = element.getChildren();
    for (int i = 0; i < list.size(); i++) {
      Element element2 = (Element) list.get(i);
      if (((String) vector.get(0)).equalsIgnoreCase(element1.getName())) {
        vector.remove(0);
        element1 = getElement(vector, element2);
      }
    }

    return element1;
  }

  public static final void main(String args[]) {
    // CAppSpaceAccess cappspaceaccess = new CAppSpaceAccess("AppMgr.xml");

  }

}
