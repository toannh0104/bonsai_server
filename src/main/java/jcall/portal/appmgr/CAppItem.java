package jcall.portal.appmgr;

import java.io.Serializable;
import java.util.List;

import org.jdom2.Element;

/*
 * 
 * @author WHC
 * application Item which user or expert build
 */

public class CAppItem extends CSpaceBase implements Serializable {

  String m_strID;
  String m_strTitle;// label
  String m_strURL;
  String m_strCreator;// ����ת��ΪCBPMeta������ʧ�?��
  String m_strCreateDate;
  String m_strModifyDate;
  String m_strDescription;

  // Vector m_vSpaceDir;
  // String m_strParent;
  // Object m_oAppObj;

  public CAppItem() {
    init();
  }

  private void init() {
    m_strID = "";
    m_strURL = "";
    m_strTitle = "";// ��label;
    m_strCreateDate = "";
    m_strModifyDate = "";
    m_strDescription = "";
    m_strType = "appunit";
    m_strCreator = "";

  }

  public CAppItem(Element element) {
    init();
    List list = element.getChildren();
    int i = list.size();
    for (int j = 0; j < i; j++) {
      Element element1 = (Element) list.get(j);
      String s = element1.getText();
      if (element1.getName().equalsIgnoreCase("id"))
        setID(s);
      if (element1.getName().equalsIgnoreCase("title"))
        setTitle(s);
      if (element1.getName().equalsIgnoreCase("creator"))
        setCreator(s);
      if (element1.getName().equalsIgnoreCase("url"))
        setURL(s);
      if (element1.getName().equalsIgnoreCase("createdate"))
        setCreateDate(s);
      if (element1.getName().equalsIgnoreCase("updatedate"))
        setModifyDate(s);
      if (element1.getName().equalsIgnoreCase("description"))
        setDescription(s);

    }

    m_strLabel = getTitle();
  }

  public void setID(String s) {
    m_strID = s;
  }

  public String getID() {
    return m_strID;
  }

  public void setTitle(String s) {
    m_strTitle = s;
  }

  public String getTitle() {
    return m_strTitle;
  }

  public void setURL(String s) {
    m_strURL = s;
  }

  public String getURL() {
    return m_strURL;
  }

  public void setCreateDate(String s) {
    m_strCreateDate = s;
  }

  public String getCreateDate() {
    return m_strCreateDate;
  }

  public void setModifyDate(String s) {
    m_strModifyDate = s;
  }

  public String getModifyDate() {
    return m_strModifyDate;
  }

  public void setDescription(String s) {
    m_strDescription = s;
  }

  public String getDescription() {
    return m_strDescription;
  }

  /**
   * @return Returns the m_strCreator.
   */
  public String getCreator() {
    return m_strCreator;
  }

  /**
   * @param creator
   *          The m_strCreator to set.
   */
  public void setCreator(String creator) {
    m_strCreator = creator;
  }

  public Element toXML() {
    Element element = new Element("AppUnit");
    Element element1 = new Element("id");
    element1.setText(getID());
    element.addContent(element1);
    Element element3 = new Element("title");
    element3.setText(getTitle());
    element.addContent(element3);
    Element element2 = new Element("creator");
    element2.setText(getCreator());
    element.addContent(element2);
    Element element4 = new Element("url");
    element4.setText(getURL());
    element.addContent(element4);
    Element element5 = new Element("createdate");
    element5.setText(getCreateDate());
    element.addContent(element5);
    Element element6 = new Element("updatedate");
    element6.setText(getModifyDate());
    element.addContent(element6);
    Element element7 = new Element("description");
    element7.setText(getDescription());
    element.addContent(element7);
    return element;
  }

}
