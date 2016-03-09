package jcall.portal.appmgr;

import java.io.Serializable;
import java.util.List;

import org.jdom2.Element;

public class CAppSpace extends CSpaceBase implements Serializable {

  // private String m_strOwner;
  public CSpaceDirectory m_oSpaceDir;

  // private Vector m_vSpaceDir;

  public CAppSpace() {
    init();
  }

  private void init() {
    // m_strOwner = "";
    m_oSpaceDir = new CSpaceDirectory();
    // m_oSpaceDir.setCreateDate((new Date()).toLocaleString());
    m_oSpaceDir.setID("dir0");
    m_oSpaceDir.setLevel("0");
    String s1 = m_oSpaceDir.getID();
    m_oSpaceDir.setPath(s1);
    m_strType = "appspace";
  }

  public CAppSpace(Element element) {
    init();
    List list = element.getChildren();
    int i = list.size();
    for (int j = 0; j < i; j++) {
      Element element1 = (Element) list.get(j);
      // String s = Null2Str(element1.getText());
      // if(element1.getName().equalsIgnoreCase("owner"))
      // setOwner(s);
      if (element1.getName() != null && element1.getName().equalsIgnoreCase("SpaceDirectory")) {
        CSpaceDirectory cspacedirectory = new CSpaceDirectory(element1);
        cspacedirectory.setID("dir0");
        cspacedirectory.setLevel("0");
        String s1 = cspacedirectory.getID();
        cspacedirectory.setPath(s1);
        setSpaceDir(cspacedirectory);
      }
    }

    m_strLabel = "AppSpace";
  }

  /*
   * public void setOwner(String s) { m_strOwner = s; }
   * 
   * public String getOwner() { return m_strOwner; }
   */
  public void setSpaceDir(CSpaceDirectory cspacedirectory) {
    m_oSpaceDir = cspacedirectory;
  }

  public CSpaceDirectory getSpaceDir() {
    return m_oSpaceDir;
  }

  public Element toXML() {
    Element element = new Element("AppSpace");
    // Element element1 = new Element("Owner");
    // element1.setText(getOwner());
    // element.addContent(element1);
    if (getSpaceDir() != null) {
      CSpaceDirectory cspacedirectory = getSpaceDir();
      Element element2 = cspacedirectory.toXML();
      element.addContent(element2);
    }
    return element;
  }
}
