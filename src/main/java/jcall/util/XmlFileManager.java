/**
 * Created on 2007/02/14
 * 
 * @author wang Copyrights @kawahara lab
 */
package jcall.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class XmlFileManager {

    private Document m_oDoc;
    private XMLOutputter m_oXMLOutputter;

    public XmlFileManager() {
    }

    public Document load2Doc(String strfileName) {
        try {
            SAXBuilder builder = new SAXBuilder();
            InputStream inputStream = new FileInputStream(new File(strfileName));
            Reader fileReader = new InputStreamReader(inputStream);
            if (fileReader != null) m_oDoc = builder.build(fileReader);
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return m_oDoc;
    }

    public Element load2Element(String strfileName) {
        try {
            SAXBuilder builder = new SAXBuilder();

            InputStream inputStream = new FileInputStream(strfileName);
            Reader fileReader = new InputStreamReader(inputStream, "UTF8");

            if (fileReader != null) m_oDoc = builder.build(fileReader);
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return m_oDoc.getRootElement();
    }

    public boolean save2File(Document oDoc, String strFileName) {
        try {
            FileOutputStream xfo = new FileOutputStream(strFileName);
            m_oXMLOutputter = new XMLOutputter();
            // m_oXMLOutputter.setEncoding("SJIS");
            m_oXMLOutputter.output(oDoc, xfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean save2File(Element oElement, String strFileName) {
        try {
            FileOutputStream xfo = new FileOutputStream(strFileName);
            m_oXMLOutputter = new XMLOutputter();
            // m_oXMLOutputter.setEncoding("SJIS");
            Document oDoc = new Document();
            oDoc.setRootElement(oElement);
            m_oXMLOutputter.output(oDoc, xfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}