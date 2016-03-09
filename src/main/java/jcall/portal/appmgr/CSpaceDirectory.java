package jcall.portal.appmgr;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom2.Element;

/*
 * 
 * @author WHC
 */

public class CSpaceDirectory extends CSpaceBase implements java.io.Serializable {

  String m_strID;
  String m_strTitle;// label
  String m_strSpaceDirPath;
  String m_strLevel;
  String m_strHref;
  String m_strTarget;

  Vector m_vSpaceDir;
  Vector m_vAppItem;
  Vector m_vServiceItem;

  // String m_strCreateDate;
  // String m_strModifyDate;
  // String m_strDescription;
  // String m_strStatus;

  public CSpaceDirectory() {
    init();
  }

  private void init() {
    m_strID = "";
    m_strTitle = "";
    m_vSpaceDir = new Vector();
    m_vAppItem = new Vector();
    m_vServiceItem = new Vector();
    this.m_strType = "spacedir";
    m_strSpaceDirPath = "";

    m_strLevel = "";
    m_strHref = "";
    m_strTarget = "";

  }

  public CSpaceDirectory(Element oElement) {

    init();
    List oChildren = oElement.getChildren();
    int nSize = oChildren.size();
    for (int i = 0; i < nSize; i++) {
      Element oTempElem = (Element) oChildren.get(i);
      String strText = Null2Str(oTempElem.getText());

      if (oTempElem.getName().equalsIgnoreCase("id")) {
        this.setID(strText);
      }
      if (oTempElem.getName().equalsIgnoreCase("title")) {
        this.setTitle(strText);
      }

      if (oTempElem.getName().equalsIgnoreCase("level")) {
        this.setLevel(strText);
      }
      if (oTempElem.getName().equalsIgnoreCase("href")) {
        this.setHref(strText);
      }
      if (oTempElem.getName().equalsIgnoreCase("target")) {
        this.setTarget(strText);
      }

      if (oTempElem.getName().equalsIgnoreCase("path")) {
        this.setPath(strText);
      }
      if (oTempElem.getName().equalsIgnoreCase("SpaceDirectory")) {
        CSpaceDirectory oDir = new CSpaceDirectory(oTempElem);
        m_vSpaceDir.add(oDir);
      }

      if (oTempElem.getName().equalsIgnoreCase("AppItem")) {
        List children = oTempElem.getChildren("AppUnit");
        Iterator it = children.iterator();
        while (it.hasNext()) {
          Element element = (Element) it.next();
          CAppItem oAppItem = new CAppItem(element);
          m_vAppItem.add(oAppItem);
        }
      }

      if (oTempElem.getName().equalsIgnoreCase("ServiceItem")) {
        List children = oTempElem.getChildren("ServiceUnit");
        Iterator it = children.iterator();
        while (it.hasNext()) {
          Element element = (Element) it.next();
          CServiceItem csi = new CServiceItem(element);
          m_vServiceItem.add(csi);
        }
      }
    }

    System.out.println("Title is:" + this.getTitle());

    this.m_strLabel = this.getTitle();
  }

  public void setPath(String path) {
    m_strSpaceDirPath = path;
  }

  public String getPath() {
    return m_strSpaceDirPath;
  }

  public void setID(String strID) {
    m_strID = strID;

  }

  public String getID() {
    return m_strID;
  }

  public void setTitle(String strTitle) {
    m_strTitle = strTitle;
  }

  public String getTitle() {
    return m_strTitle;
  }

  public void addSpaceDir(CSpaceDirectory obj) {
    m_vSpaceDir.add(obj);
  }

  public Vector getSpaceDirs() {
    return m_vSpaceDir;
  }

  public void setSpaceDirs(Vector spaceDir) {
    m_vSpaceDir = new Vector();
    for (int i = 0; i < spaceDir.size(); i++) {
      m_vSpaceDir.add((CSpaceDirectory) spaceDir.get(i));
    }
  }

  public void delSpaceDir(Object obj) {
    m_vSpaceDir.remove(obj);
  }

  public void addAppItem(CAppItem obj) {
    CAppItem oApp = (CAppItem) obj;
    m_vAppItem.add(oApp);
  }

  public Vector getAppItems() {
    return m_vAppItem;
  }

  public void setAppItems(Vector appItem) {
    m_vAppItem = new Vector();
    for (int i = 0; i < appItem.size(); i++) {
      m_vAppItem.add((CAppItem) appItem.get(i));
    }

  }

  public void delAppItem(CAppItem obj) {
    m_vAppItem.remove(obj);
  }

  public void addServiceItem(CServiceItem obj) {
    CServiceItem si = (CServiceItem) obj;
    m_vServiceItem.add(si);
  }

  public Vector getServiceItems() {
    return m_vServiceItem;
  }

  public void setServiceItems(Vector serviceItems) {
    m_vServiceItem = new Vector();
    for (int i = 0; i < serviceItems.size(); i++) {
      m_vServiceItem.add((CServiceItem) serviceItems.get(i));
    }
  }

  public void delServiceItem(CServiceItem obj) {
    m_vServiceItem.remove(obj);
  }

  public void setLevel(String strLevel) {
    m_strLevel = strLevel;
  }

  public String getLevel() {
    return m_strLevel;
  }

  public void setHref(String strHref) {
    m_strHref = strHref;
  }

  public String getHref() {
    return m_strHref;
  }

  public void setTarget(String strTarget) {
    m_strTarget = strTarget;
  }

  public String getTarget() {
    return m_strTarget;
  }

  public Element toXML() {
    Element oElement = new Element("SpaceDirectory");

    Element oIDElem = new Element("id");
    oIDElem.setText(this.getID());
    oElement.addContent(oIDElem);

    Element oTitleElem = new Element("title");
    oTitleElem.setText(this.getTitle());
    oElement.addContent(oTitleElem);

    Element oPathElem = new Element("path");
    oPathElem.setText(this.getPath());
    oElement.addContent(oPathElem);

    Element oLevelElem = new Element("level");
    oLevelElem.setText(this.getLevel());
    oElement.addContent(oLevelElem);

    Element oTargetElem = new Element("target");
    oTargetElem.setText(this.getTarget());
    oElement.addContent(oTargetElem);

    Element oHrefElem = new Element("href");
    oHrefElem.setText(this.getHref());
    oElement.addContent(oHrefElem);

    int nSize1 = m_vAppItem.size();
    if (nSize1 > 0) {
      Element oAppItemElem = new Element("AppItem");
      for (int j = 0; j < nSize1; j++) {
        CAppItem oAppItem = (CAppItem) m_vAppItem.get(j);
        oAppItemElem.addContent(oAppItem.toXML());
      }
      oElement.addContent(oAppItemElem);
    }

    int nSize2 = m_vServiceItem.size();
    if (nSize2 > 0) {
      Element oServiceItemElem = new Element("ServiceItem");
      for (int l = 0; l < nSize2; l++) {
        CServiceItem oServiceItem = (CServiceItem) m_vServiceItem.get(l);
        oServiceItemElem.addContent(oServiceItem.toXML());
      }
      oElement.addContent(oServiceItemElem);
    }

    int nSize3 = m_vSpaceDir.size();
    for (int i = 0; i < nSize3; i++) {
      CSpaceDirectory oSpaceDir = (CSpaceDirectory) m_vSpaceDir.get(i);
      String s = "dir" + getLevel() + (new Integer(i + 1)).toString();
      oSpaceDir.setLevel("0" + getLevel());
      oSpaceDir.setID(s);
      String strPath = getPath() + "_" + oSpaceDir.getID();
      oSpaceDir.setPath(strPath);
      oElement.addContent(oSpaceDir.toXML());
    }
    return oElement;
  }

}
