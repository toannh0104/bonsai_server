/**
 * Created on 2008/10/16
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.portal.presentation;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.servlet.jsp.JspWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import jcall.config.FindConfig;
import jcall.portal.appmgr.CAppSpace;
import jcall.portal.appmgr.CAppSpaceAccess;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.DOMBuilder;
import org.jdom2.input.SAXBuilder;
import org.jdom2.transform.JDOMSource;

//Referenced classes of package portal.presentation:

public class UITransform {

  public UITransform() {
  }

  public Document Object2Document(CAppSpace obj) {
    org.jdom2.Element element = obj.toXML();

    Document document = new Document();
    try {
      document.setRootElement(element);
    } catch (Exception exception) {
      System.out.println(exception.toString());
      exception.printStackTrace();
    }
    return document;
  }

  public Document BuildXMLDocFromString(String s) {
    Document document = null;
    s = replace(s, "&lt;", "<");
    s = replace(s, "&gt;", ">");
    StringReader stringreader = new StringReader(s);
    SAXBuilder saxbuilder = new SAXBuilder();
    try {
      document = saxbuilder.build(stringreader);
    } catch (JDOMException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return document;

  }

  public Document BuildXMLDocFromFile(String s) {
    Document document = new Document();
    try {
      File file = new File(s);
      DOMBuilder dombuilder = new DOMBuilder();
      document = dombuilder.build((org.w3c.dom.Document) file);
    } catch (Exception exception) {
      System.out.println(exception.toString());
      exception.printStackTrace();
    }
    return document;
  }

  private int Str2Int(String s) {
    if (s == null)
      return 0;
    if (s.equalsIgnoreCase(""))
      return 0;
    else
      return Integer.parseInt(s);
  }

  private String Null2Str(String s) {
    if (s == null || s == "")
      s = "";
    return s;
  }

  public void Xml2Html(Document document, String s, PrintWriter printwriter) {
    String s1 = "";
    if (!s.equalsIgnoreCase(""))
      s1 = FindConfig.getConfig().findStr(s);
    else
      s1 = FindConfig.getConfig().findStr("XML2HTML_Transform");
    try {
      TransformerFactory transformerfactory = TransformerFactory.newInstance();
      Transformer transformer = transformerfactory.newTransformer(new StreamSource(s1));
      transformer.setOutputProperty("encoding", "UTF-8");
      String s2 = new String();
      transformer.transform(new JDOMSource(document), new StreamResult(printwriter));
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public void Xml2Html(Document document, String s, JspWriter jspwriter) {
    String s1 = "";
    if (!s.equalsIgnoreCase("") && s != null)
      s1 = FindConfig.getConfig().findStr(s);
    else
      s1 = FindConfig.getConfig().findStr("XML2HTML_Transform");
    try {
      TransformerFactory transformerfactory = TransformerFactory.newInstance();
      Transformer transformer = transformerfactory.newTransformer(new StreamSource(s1));
      transformer.setOutputProperty("encoding", "UTF-8");
      String s2 = new String();
      transformer.transform(new JDOMSource(document), new StreamResult(jspwriter));
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public static final void main(String args[]) {
    try {
      UITransform uitransform = new UITransform();
      CAppSpaceAccess appaccess = new CAppSpaceAccess();
      CAppSpace cappspace = appaccess.getAppSpace(".//xml//dirs.xml");

      Document document = uitransform.Object2Document(cappspace);
      System.out.println(document.toString());
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      exception.printStackTrace();
    }
  }

  public static String replace(String s, String s1, String s2) {
    String s3 = "";
    int i = s1.length();
    int j;
    while ((j = s.indexOf(s1)) != -1) {
      s3 = s3 + s.substring(0, j);
      s3 = s3 + s2;
      s = s.substring(j + i);
    }
    s3 = s3 + s;
    return s3;
  }

}
