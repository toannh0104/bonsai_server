/*
 * Created on 2005-6-22 
 */
package jcall.portal.appmgr;

import java.io.Serializable;
import java.util.List;

import org.jdom2.Element;

/*
 * @author WHC
 * service Item which read from cafise database
 */
public class CServiceItem extends CSpaceBase implements Serializable {
  String m_strID;
  String m_strTitle;
  String m_strURL;
  String m_strDescription;

  // String m_strCreateDate;
  // String m_strModifyDate;

  public CServiceItem() {
    init();
  }

  private void init() {
    m_strID = "";
    m_strTitle = "";// ��label;
    // m_strCreateDate = "";
    m_strURL = "";
    m_strDescription = "";
    m_strType = "serviceunit";

  }

  public CServiceItem(Element element) {
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
      if (element1.getName().equalsIgnoreCase("url"))
        setURL(s);
      if (element1.getName().equalsIgnoreCase("description"))
        setDescription(s);

    }

    m_strLabel = getTitle();
  }

  public void setDescription(String s) {
    m_strDescription = s;
  }

  public String getDescription() {
    return m_strDescription;
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

  public Element toXML() {
    Element element = new Element("ServiceUnit");
    Element element1 = new Element("id");
    element1.setText(getID());
    element.addContent(element1);
    Element element3 = new Element("title");
    element3.setText(getTitle());
    element.addContent(element3);
    Element element4 = new Element("url");
    element4.setText(getURL());
    element.addContent(element4);

    Element element7 = new Element("description");
    element7.setText(getDescription());
    element.addContent(element7);
    return element;
  }

}
