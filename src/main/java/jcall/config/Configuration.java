/**
 * Created on 2007/02/14
 *
 * @author wang Copyrights @kawahara lab
 */
package jcall.config;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import jcall.util.XmlFileManager;
import jp.co.gmo.rs.ghs.model.DataConfig;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Configuration {
    static Logger logger = Logger.getLogger(Configuration.class.getName());
    private static String ConfigUrl = DataConfig.getProperty().getProperty("data.config");
    private static Element elemConfiguration;
    // private static String a = null;
    private static HashMap<String, String> mapConfigType = new HashMap<String, String>();
    private static Hashtable<String, String> mapConfigItem = new Hashtable<String, String>();
    private static Object synchronizeObj = new Object();
    private static XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

    private static Configuration config;

    private Configuration() {
        XmlFileManager xfu = new XmlFileManager();
        Document document = xfu.load2Doc(ConfigUrl);
        elemConfiguration = document.getRootElement();
        List list = elemConfiguration.getChildren();
        for (int i = 0; i < list.size(); i++) {
            Element element = (Element) list.get(i);
            String s = element.getAttributeValue("name");
            // logger.debug("session:" + s);
            List list1 = element.getChildren();
            for (int j = 0; j < list1.size(); j++) {
                Element element1 = (Element) list1.get(j);
                String s1 = element1.getAttributeValue("name");
                String s2 = element1.getAttributeValue("value");
                // logger.debug("name:" + s1);
                mapConfigItem.put(s + "&" + s1, s2);
            }
            mapConfigType.put(s, s);
        }
    }

    public static Configuration getConfig() {
        if (config == null) {
            config = new Configuration();
        }
        return config;
    }

    public void addConfigType(String strType) {
        // logger.debug("Enter addConfigType:");
        // boolean booResult = true;
        synchronized (synchronizeObj) {
            if (mapConfigType.containsKey(strType)) {
                return;
            } else {
                mapConfigType.put(strType, strType);
                elemConfiguration.addContent(configTypeToElement(strType));
                try {
                    outputter.output(elemConfiguration, new FileOutputStream(ConfigUrl));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * element is ConfigType Element or the root
     */
    private Element getElementByNameAttr(Element element, String s) {
        List list = element.getChildren();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Element element1 = (Element) iterator.next();
            if (element1.getAttributeValue("name").equals(s)) return element1;
        }
        return null;
    }

    private Element configTypeToElement(String strName) {
        Element element = new Element("session");
        if (strName != null) {
            element.setAttribute("name", strName);
        }
        return element;
    }

    private Element configItemToElement(String s, String s1) {
        Element element = new Element("entry");
        element.setAttribute("name", s);
        element.setAttribute("value", s1);
        return element;
    }

    public boolean updateConfigType(String strConfigTypeName, String strnewName) throws FileNotFoundException,
            IOException {
        // logger.debug("Enter updateConfigType:" + strConfigTypeName + "|" +
        // strnewName );

        if (!mapConfigType.containsKey(strConfigTypeName)) {
            logger.info("no ConfigType: " + strConfigTypeName);
            return false;
        } else {
            synchronized (synchronizeObj) {
                if (!strConfigTypeName.equals(strnewName)) {
                    mapConfigType.remove(strConfigTypeName);
                    mapConfigType.put(strnewName, strnewName);
                    Iterator it = mapConfigItem.keySet().iterator();
                    while (it.hasNext()) {
                        String ss = (String) (it.next());
                        if (ss.startsWith(strConfigTypeName + "&")) {
                            String strTemp1 = mapConfigItem.get(ss);
                            String strTemp2 = ss.replaceFirst(strConfigTypeName + "&", strnewName + "&");
                            mapConfigItem.remove(ss);
                            mapConfigItem.put(strTemp2, strTemp1);
                        }
                    }
                }
                Element element = getElementByNameAttr(elemConfiguration, strConfigTypeName);
                element.setAttribute("name", strnewName);
                outputter.output(elemConfiguration, new FileOutputStream(ConfigUrl));
            }
        }
        // logger.debug("success in updateConfigType:");
        return true;
    }

    public boolean delConfigType(String strConfigTypeName) {
        // logger.debug("Enter delConfigType:" + strConfigTypeName);
        if (!mapConfigType.containsKey(strConfigTypeName)) return false;
        synchronized (synchronizeObj) {
            mapConfigType.remove(strConfigTypeName);
            Iterator it = mapConfigItem.keySet().iterator();
            while (it.hasNext()) {
                String ss = (String) (it.next());
                if (ss.startsWith(strConfigTypeName + "&")) {
                    mapConfigItem.remove(ss);
                }
            }
            Element element = getElementByNameAttr(elemConfiguration, strConfigTypeName);
            elemConfiguration.removeContent(element);
            try {
                outputter.output(elemConfiguration, new FileOutputStream(ConfigUrl));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // logger.debug("success in delConfigType:" + strConfigTypeName);
        return true;
    }

    public boolean addItem(String strConfigTypeName, String strConfigItemName, String strConfigItemValue) {
        if (!mapConfigType.containsKey(strConfigTypeName)
                || mapConfigItem.containsKey(strConfigTypeName + "&" + strConfigItemName)) return false;
        synchronized (synchronizeObj) {
            mapConfigItem.put(strConfigTypeName + "&" + strConfigItemName, strConfigItemValue);
            Element element = getElementByNameAttr(elemConfiguration, strConfigTypeName);
            element.addContent(configItemToElement(strConfigItemName, strConfigItemValue));
            try {
                outputter.output(elemConfiguration, new FileOutputStream(ConfigUrl));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // logger.debug("success in addItem" );
        return true;
    }

    public boolean updateItem(String strConfigTypeName, String strConfigItemName, String strConfigItemValue) {
        // logger.debug("Enter updateItem:" );
        if (!mapConfigType.containsKey(strConfigTypeName)
                || !mapConfigItem.containsKey(strConfigTypeName + "&" + strConfigItemName)) return false;
        synchronized (synchronizeObj) {
            mapConfigItem.remove(strConfigTypeName + "&" + strConfigItemName);
            mapConfigItem.put(strConfigTypeName + "&" + strConfigItemName, strConfigItemValue);

            Element element = getElementByNameAttr(elemConfiguration, strConfigTypeName);
            Element element1 = getElementByNameAttr(element, strConfigItemName);
            element.removeContent(element1);
            element.addContent(configItemToElement(strConfigItemName, strConfigItemValue));

            try {
                outputter.output(elemConfiguration, new FileOutputStream(ConfigUrl));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // logger.debug("success in updateItem:");
        return true;
    }

    public boolean delItem(String strConfigTypeName, String strConfigItemName) {
        // logger.debug("Enter delItem:" );
        if (!mapConfigType.containsKey(strConfigTypeName)
                || !mapConfigItem.containsKey(strConfigTypeName + "&" + strConfigItemName)) return false;
        synchronized (synchronizeObj) {
            mapConfigItem.remove(strConfigTypeName + "&" + strConfigItemName);
            Element element = getElementByNameAttr(elemConfiguration, strConfigTypeName);
            Element element1 = getElementByNameAttr(element, strConfigItemName);
            element.removeContent(element1);
            try {
                outputter.output(elemConfiguration, new FileOutputStream(ConfigUrl));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // logger.debug("success in delItem:");
        return true;
    }

    public String getItemValue(String strConfigTypeName, String strConfigItemName) {
        String strResult = null;
        Element element1;
        if (!mapConfigItem.containsKey(strConfigTypeName + "&" + strConfigItemName)) {
            System.out.println("error in getItemValue");
        } else {
            // Element element = getElementByNameAttr(elemConfiguration,
            // strConfigTypeName);
            // if(element!=null){
            // element1 = getElementByNameAttr(element, strConfigItemName);
            // if(element1!=null){
            // strResult = element1.getAttributeValue("value");
            // }else{
            // System.out.println("element1 null");
            // }
            // }else{
            // System.out.println("element null");
            // }

            strResult = mapConfigItem.get(strConfigTypeName + "&" + strConfigItemName);
        }
        return strResult;

    }

    public Vector getConfigTypes() {
        Vector<ConfigType> vector = new Vector<ConfigType>();
        synchronized (synchronizeObj) {
            List list = elemConfiguration.getChildren();
            String s;
            for (Iterator iterator = list.iterator(); iterator.hasNext(); vector.add(new ConfigType(s))) {
                Element element = (Element) iterator.next();
                s = element.getAttributeValue("name");
            }

        }
        return vector;
    }

    public Vector getConfigItems(String s) {
        Vector<ConfigItem> vector = new Vector<ConfigItem>();
        synchronized (synchronizeObj) {
            Element element = getElementByNameAttr(elemConfiguration, s);
            if (element != null) {
                List list = element.getChildren();
                String s1;
                String s2;

                for (Iterator iterator = list.iterator(); iterator.hasNext(); vector.add(new ConfigItem(s1, s2))) {
                    Element element1 = (Element) iterator.next();
                    s1 = element1.getAttributeValue("name");
                    s2 = element1.getAttributeValue("value");
                }

            }
        }
        return vector;
    }

    public void addPersonalConfig(String s) {

        // Configuration configuration = Configuration.getConfig();

        String sName1 = "langform";
        String sValue1 = "kana";

        String sName2 = "label";
        String sValue2 = "eng";

        String sName3 = "weightoption";
        String sValue3 = "grammar";

        String sName4 = "input";
        String sValue4 = "typing";

        String sessionName = s;

        // configuration.addConfigType(sessionName);
        // configuration.addItem(sessionName, sName1, sValue1);
        // configuration.addItem(sessionName, sName2, sValue2);
        // configuration.addItem(sessionName, sName3, sValue3);
        // configuration.addItem(sessionName, sName4, sValue4);

        addConfigType(sessionName);
        addItem(sessionName, sName1, sValue1);
        addItem(sessionName, sName2, sValue2);
        addItem(sessionName, sName3, sValue3);
        addItem(sessionName, sName4, sValue4);

    }

    public void addPersonalConfig(String name, String pwd) {

        // Configuration configuration = Configuration.getConfig();

        String sName0 = name;
        String sValue0 = pwd;

        String sName1 = "langform";
        String sValue1 = "kana";

        String sName2 = "label";
        String sValue2 = "eng";

        String sName3 = "weightoption";
        String sValue3 = "grammar";

        String sName4 = "input";
        String sValue4 = "typing";

        String sessionName = name;

        addConfigType(sessionName);
        addItem(sessionName, sName0, sValue0);
        addItem(sessionName, sName1, sValue1);
        addItem(sessionName, sName2, sValue2);
        addItem(sessionName, sName3, sValue3);
        addItem(sessionName, sName4, sValue4);

    }

    // static Class loadClass(String s) throws ClassNotFoundException
    // {
    // Class result = Class.forName(s);
    // return result;
    // }

    public static void main(String args[]) throws IOException, Exception {
        Configuration configuration = Configuration.getConfig();

        System.out.println("after instantiate its own class");

        String s1 = "abc";
        configuration.addConfigType(s1);
        //
        // String s4 = "abc";
        // String s9 = "iloveyou";
        // String s13 = "value";
        // String s15 = "description";
        // configuration.addItem(s4, s9, s13);
        //
        // Configuration configurationNew = Configuration.getConfig();
        // System.out.println(configurationNew.getItemValue("abc","iloveyou"));

        System.out.println(configuration.getItemValue("systeminfo", "laststudent"));

    }

}
