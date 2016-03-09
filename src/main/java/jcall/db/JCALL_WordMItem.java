/**
 * Created on 2007/11/09
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

//import java.io.StringReader;

//import org.jdom.Document;
import org.jdom2.Element;
//import org.jdom.JDOMException;
//import org.jdom.input.SAXBuilder;

public class JCALL_WordMItem {

  String name;
  String value;
  String description;

  public JCALL_WordMItem(String name, String value) {
    super();
    this.name = name;
    this.value = value;
  }

  public JCALL_WordMItem() {
    // TODO Auto-generated constructor stub
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String toXMLString() {
    String xmlString = "<Item name=\"" + name + "\" value=\"" + value + "\" description=\"" + description + "\">";
    xmlString += "</Item>";
    return xmlString;

  }

  public Element toElement() {
    Element e = new Element("Item");
    if (this.name != null && this.name.length() > 0) {
      e.setAttribute("name", this.name);
    }
    if (this.value != null && this.value.length() > 0) {
      e.setAttribute("value", this.value);
    }
    if (this.description != null && this.description.length() > 0) {
      e.setAttribute("description", this.description);
    }
    return e;
  }

  public void elementToItem(Element e) {
    // JCALL_WordMItem item = new JCALL_WordMItem();
    if (e.getAttribute("name") != null) {
      this.setName(e.getAttributeValue("name"));
    }
    if (e.getAttribute("value") != null) {
      this.setValue(e.getAttributeValue("value"));
    }
    if (e.getAttribute("description") != null) {
      this.setDescription(e.getAttributeValue("description"));
    }
    // return item;
  }

}
