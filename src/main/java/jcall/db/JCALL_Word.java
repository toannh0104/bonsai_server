/**
 * Created on 2007/05/24
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.jdom2.Element;

public class JCALL_Word extends JCALL_WordBase {
  // Fields
  private static final long serialVersionUID = 1L;

  int ComponentID;

  String strLevel;
  // String strKatakana;
  // String strPronunciation; //pronunciation format for julian
  String strPicture;
  // String strCategories;

  Vector<String> vCategories;
  // String strMainMeaning;
  // String strAltEng;
  // String strAltkana;

  String strGroup; // for verb,
                   // wuduan-Group1,yiduan-Group2,sabian-Group3,kabian-Group4,special-Group5
  String strClass; // for verb, transient, intransient
  //
  String strNumber;
  String strQuantifier;
  //
  // String strCategory1; //for NQ , category1 is Numeral ;
  // for verb, category1 is
  // wuduan-Group1,yiduan-Group2,sabian-Group3,kabian-Group4,special-Group5
  // String strCategory2;//for NQ , category2 is quantifier
  // for verb, transient, intransient
  // Vector vCategories;
  Vector<JCALL_WordMSet> vMeaning;

  boolean active;

  public JCALL_Word() {
    super();

    this.ComponentID = -1;
    // this.strKatakana = "";
    this.strLevel = "4";
    // this.intType = -1;
    // this.strPronunciation="";
    this.strPicture = "";
    // // this.strCategories = "";
    vCategories = new Vector<String>();

    // this.strMainMeaning = "";
    // this.strAltEng="";
    // this.strAltkana="";
    this.vMeaning = new Vector<JCALL_WordMSet>();
    active = false;
    this.strGroup = null;
    this.strClass = null;
    strNumber = null;
    strQuantifier = null;

    // this.strAltEng="";
    // this.strAltkana="";

    // this.strCategory1="";
    // this.strCategory2="";
  }

  public String toString() {
    String strResult = "";
    strResult = "[" + intID + "] " + " [" + strKana + "] " + " [" + strKanji + "] " + " [" + this.getDEngMeaning()
        + "] " + " [" + this.getDChMeaning() + "] ";
    return strResult;
  }

  // String strLexiconFields =
  // "(Type,Level,Kanji,kana,MainMeaning,Picture,Altkana,OtherMeaning,Category1,Category2)";

  /**
   * @param outP
   *          write to file
   */
  public void write(PrintWriter outP) {
    outP.println("#");
    outP.println("-id " + intID);
    outP.println("-componentID " + ComponentID);
    outP.println("-type " + intType);
    outP.println("-level " + strLevel);
    outP.println("-kanji " + strKanji);
    outP.println("-kana " + strKana);
    // outP.println("-pronunciation " + strPronunciation);
    outP.println("-engMeaning " + this.getDEngMeaning());
    if (strPicture != null && strPicture.trim().length() > 0) {
      outP.println("-picture " + strPicture);
    }

    if (strAltkana != null && strAltkana.trim().length() > 0) {
      outP.println("-altkana " + strAltkana);
    }
    if (getDSynonymValue() != null && getDSynonymValue().trim().length() > 0) {
      outP.println("-synonym " + getDSynonymValue().trim());
    }
    if (strGroup != null && strGroup.trim().length() > 0) {
      outP.println("-group " + strGroup);
    }

    if (strClass != null && strClass.trim().length() > 0) {
      outP.println("-class " + strClass);
    }

    if (strNumber != null && strNumber.trim().length() > 0) {
      outP.println("-number " + strNumber);
    }
    if (strQuantifier != null && strQuantifier.trim().length() > 0) {
      outP.println("-quantifier " + strQuantifier);
    }

    if (vCategories != null && vCategories.size() > 0) {
      outP.println("-categories " + this.getCategories());
    }

  }

  public int compareTo(Object o) {

    if (o instanceof JCALL_Word) {
      JCALL_Word lwm = (JCALL_Word) o;
      if (this.strKanji.compareTo(lwm.getStrKanji()) == 0 && this.intType == lwm.getIntType()) {
        return 0;
      } else {
        return 1;
      }
    }
    return -1;
  }

  public void addMeaning(JCALL_WordMSet meaning) {
    if (vMeaning == null) {
      vMeaning = new Vector<JCALL_WordMSet>();
    }
    vMeaning.add(meaning);
  }

  public void addCategory(String cate) {
    if (vCategories == null) {
      vCategories = new Vector<String>();
    }
    vCategories.add(cate);
  }

  public boolean IsHasCategory(String cate) {
    cate = cate.trim();
    if (vCategories != null) {
      for (int i = 0; i < vCategories.size(); i++) {
        String str = vCategories.elementAt(i);
        if (str.equalsIgnoreCase(cate)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Vector<JCALL_WordMSet> getVMeaning() {
    return vMeaning;
  }

  public void setVMeaning(Vector<JCALL_WordMSet> meaning) {
    vMeaning = meaning;
  }

  /*
   * interface for mainmeaning
   */
  public String getItemValue(String strMType, String strItemName) {
    String strResult = null;
    if (vMeaning != null && vMeaning.size() > 0) {
      for (int i = 0; i < vMeaning.size(); i++) {
        JCALL_WordMSet wordmeaning = vMeaning.elementAt(i);
        if (wordmeaning != null && wordmeaning.getStrMType().equalsIgnoreCase(strMType)) {
          Vector v = wordmeaning.getVItem();
          if (v != null && v.size() > 0) {
            for (int j = 0; j < v.size(); j++) {
              JCALL_WordMItem wmi = (JCALL_WordMItem) v.elementAt(j);
              if (wmi.getName().trim().equalsIgnoreCase(strItemName)) {
                if (wmi.getValue() != null) {
                  return wmi.getValue();
                }
              }
            }

          }
        }

      }
    }
    return strResult;
  }

  public JCALL_WordMItem getItem(String strMType, String strItemName) {
    if (vMeaning != null && vMeaning.size() > 0) {
      for (int i = 0; i < vMeaning.size(); i++) {
        JCALL_WordMSet wordmeaning = vMeaning.elementAt(i);
        if (wordmeaning != null && wordmeaning.getStrMType().equalsIgnoreCase(strMType)) {
          Vector v = wordmeaning.getVItem();
          if (v != null && v.size() > 0) {
            for (int j = 0; j < v.size(); j++) {
              JCALL_WordMItem wmi = (JCALL_WordMItem) v.elementAt(j);
              if (wmi.getName().trim().equalsIgnoreCase(strItemName)) {
                return wmi;

              }
            }

          }
        }

      }
    }
    return null;
  }

  public void setItem(String strMType, String strItemName, String strItemValue) {

    if (vMeaning == null) {
      vMeaning = new Vector<JCALL_WordMSet>();
    }
    if (vMeaning.size() == 0) {
      JCALL_WordMSet wordmeaning = new JCALL_WordMSet(strMType);
      JCALL_WordMItem wmi = new JCALL_WordMItem(strItemName, strItemValue);
      wordmeaning.addItem(wmi);
      this.addMeaning(wordmeaning);
      return;
    }
    if (vMeaning.size() > 0) {
      for (int i = 0; i < vMeaning.size(); i++) {
        JCALL_WordMSet meaning = vMeaning.elementAt(i);
        if (meaning != null && meaning.getStrMType().equalsIgnoreCase(strMType)) {
          Vector v = meaning.getVItem();
          if (v != null && v.size() > 0) {
            for (int j = 0; j < v.size(); j++) {
              JCALL_WordMItem wmi = (JCALL_WordMItem) v.elementAt(j);
              if (wmi.getName().trim().equalsIgnoreCase(strItemName)) {
                wmi.setValue(strItemValue);
                return;
              }
            }
          }
          // not such an Item
          JCALL_WordMItem wmiNew = new JCALL_WordMItem(strItemName, strItemValue);
          meaning.addItem(wmiNew);
          return;
        }

      }
      JCALL_WordMSet wordmeaning = new JCALL_WordMSet(strMType);
      JCALL_WordMItem wmi = new JCALL_WordMItem(strItemName, strItemValue);
      wordmeaning.addItem(wmi);
      this.addMeaning(wordmeaning);
      return;
    }

  }

  public String getDEngMeaning() {
    return getItemValue("default", "english");
  }

  public String getDChMeaning() {
    return getItemValue("default", "chinese");
  }

  public String getDSynonymValue() {
    return getItemValue("default", "synonym");
  }

  public JCALL_WordMItem getDSynonymItem() {
    return getItem("default", "synonym");
  }

  public JCALL_WordMItem getViVtItem() {
    return getItem("default", "vivt");
  }

  public String getStrClass() {
    return strClass;
  }

  public void setStrClass(String strClass) {
    this.strClass = strClass;
  }

  public String getStrGroup() {
    return strGroup;
  }

  public void setStrGroup(String strGroup) {
    this.strGroup = strGroup;
  }

  public String getStrLevel() {
    return strLevel;
  }

  public void setStrLevel(String strLevel) {
    this.strLevel = strLevel;
  }

  public String getStrNumber() {
    return strNumber;
  }

  public void setStrNumber(String strNumber) {
    this.strNumber = strNumber;
  }

  public String getStrPicture() {
    return strPicture;
  }

  public void setStrPicture(String strPicture) {
    this.strPicture = strPicture;
  }

  public String getStrQuantifier() {
    return strQuantifier;
  }

  public void setStrQuantifier(String strQuantifier) {
    this.strQuantifier = strQuantifier;
  }

  public Vector<String> getVCategories() {
    return vCategories;
  }

  public void setVCategories(Vector<String> categories) {
    vCategories = categories;
  }

  public void setVCategories(String categories) {
    if (vCategories == null) {
      vCategories = new Vector<String>();
    }
    StringTokenizer st = new StringTokenizer(categories, InstantData.LEX_FILE_ITEM_SEPERATION);
    while (st.hasMoreElements()) {
      this.vCategories.add((String) st.nextElement());
    }
  }

  public String getCategories() {
    String strCate = "";
    for (int i = 0; i < vCategories.size(); i++) {
      strCate = strCate + vCategories.get(i);
      strCate = strCate.trim() + InstantData.LEX_FILE_ITEM_SEPERATION;
    }
    strCate = strCate.substring(0, strCate.length() - 1);
    return strCate;
  }

  public int getComponentID() {
    return ComponentID;
  }

  public void setComponentID(int componentID) {
    ComponentID = componentID;
  }

  public Element toElement() {
    Element elemRoot = new Element("word");
    elemRoot.setAttribute("id", Integer.toString(this.getId()));
    elemRoot.setAttribute("componentID", Integer.toString(this.getComponentID()));
    if (strLevel != null && strLevel.length() > 0) {
      Element child = new Element("prop");
      child.setAttribute("name", "level");
      child.setAttribute("value", strLevel);
      elemRoot.addContent(child);
    }
    if (this.strKana != null && strKana.length() > 0) {
      Element child = new Element("prop");
      child.setAttribute("name", "kana");
      child.setAttribute("value", strKana);
      elemRoot.addContent(child);
    }
    if (this.strKanji != null && strKanji.length() > 0) {
      Element child = new Element("prop");
      child.setAttribute("name", "kanji");
      child.setAttribute("value", strKanji);
      elemRoot.addContent(child);
    }
    if (this.intType >= -1) {
      Element child = new Element("prop");
      child.setAttribute("name", "type");
      child.setAttribute("value", Integer.toString(intType));
      elemRoot.addContent(child);
    }
    // if(this.strPronunciation!=null && strPronunciation.length()>0){
    // Element child = new Element("prop");
    // child.setAttribute("name","pronunciation");
    // child.setAttribute("value",strPronunciation);
    // elemRoot.addContent(child);
    // }
    if (this.strPicture != null && strPicture.length() > 0) {
      Element child = new Element("prop");
      child.setAttribute("name", "picture");
      child.setAttribute("value", strPicture);
      elemRoot.addContent(child);
    }
    if (this.strAltkana != null && strAltkana.length() > 0) {
      Element child = new Element("prop");
      child.setAttribute("name", "altkana");
      child.setAttribute("value", strAltkana);
      elemRoot.addContent(child);
    }

    if (this.strClass != null && strClass.length() > 0) {
      Element child = new Element("prop");
      child.setAttribute("name", "class");
      child.setAttribute("value", strClass);
      elemRoot.addContent(child);
    }
    if (this.strGroup != null && strGroup.length() > 0) {
      Element child = new Element("prop");
      child.setAttribute("name", "group");
      child.setAttribute("value", strGroup);
      elemRoot.addContent(child);
    }
    if (this.strNumber != null && strNumber.length() > 0) {
      Element child = new Element("prop");
      child.setAttribute("name", "number");
      child.setAttribute("value", strNumber);
      elemRoot.addContent(child);
    }
    if (this.strQuantifier != null && strQuantifier.length() > 0) {
      Element child = new Element("prop");
      child.setAttribute("name", "quantifier");
      child.setAttribute("value", strQuantifier);
      elemRoot.addContent(child);
    }
    if (this.vCategories != null && vCategories.size() > 0) {
      Element child = new Element("prop");
      child.setAttribute("name", "categories");
      child.setAttribute("value", this.getCategories());
      elemRoot.addContent(child);
    }

    if (this.vMeaning != null && vMeaning.size() > 0) {
      Element child = new Element("prop");
      child.setAttribute("name", "meaning");
      for (int i = 0; i < vMeaning.size(); i++) {
        JCALL_WordMSet wm = vMeaning.elementAt(i);
        Element eTemp = wm.toElement();
        if (eTemp != null) {
          child.addContent(eTemp);
        }
      }
      elemRoot.addContent(child);
    }
    return elemRoot;
  }

  public void element2Word(Element e) {
    // JCALL_WordMeaning wm = new JCALL_WordMeaning();
    if (e.getAttribute("id") != null) {
      this.setId(Integer.parseInt(e.getAttributeValue("id")));
      // System.out.println("word id: "+ this.getId());
    } else {
      this.setId(-1);
      System.out.println("no id: ");
    }
    if (e.getAttribute("componentID") != null) {
      this.setComponentID(Integer.parseInt(e.getAttributeValue("componentID")));
    }
    List content = e.getContent();
    Iterator iterator = content.iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      if (o instanceof Element) {
        Element child = (Element) o;
        if (child.getAttribute("name") != null) {
          String name = child.getAttributeValue("name");
          if (name.trim().equalsIgnoreCase("level")) {
            if (child.getAttribute("value") != null) {
              this.setStrLevel(child.getAttributeValue("value"));
            }
          } else if (name.trim().equalsIgnoreCase("kana")) {
            if (child.getAttribute("value") != null) {
              this.setStrKana(child.getAttributeValue("value"));
            }
          } else if (name.trim().equalsIgnoreCase("kanji")) {
            if (child.getAttribute("value") != null) {
              this.setStrKanji(child.getAttributeValue("value"));
            }
          } else if (name.trim().equalsIgnoreCase("type")) {
            if (child.getAttribute("value") != null) {
              this.setIntType(Integer.parseInt(child.getAttributeValue("value")));
            }
          }

          // else if(name.trim().equalsIgnoreCase("pronunciation")){
          // if(child.getAttribute("value")!=null){
          // this.setStrPronunciation(child.getAttributeValue("value"));
          // }
          // }

          else if (name.trim().equalsIgnoreCase("picture")) {
            if (child.getAttribute("value") != null) {
              this.setStrPicture(child.getAttributeValue("value"));
            }
          } else if (name.trim().equalsIgnoreCase("altkana")) {
            if (child.getAttribute("value") != null) {
              this.setStrAltkana(child.getAttributeValue("value"));
            }
          } else if (name.trim().equalsIgnoreCase("class")) {
            if (child.getAttribute("value") != null) {
              this.setStrClass(child.getAttributeValue("value"));
            }
          } else if (name.trim().equalsIgnoreCase("group")) {
            if (child.getAttribute("value") != null) {
              // String gr = "Group"+ child.getAttributeValue("value");
              // this.setStrGroup(gr);
              this.setStrGroup(child.getAttributeValue("value"));
            }
          } else if (name.trim().equalsIgnoreCase("number")) {
            if (child.getAttribute("value") != null) {
              this.setStrNumber(child.getAttributeValue("value"));
            }
          } else if (name.trim().equalsIgnoreCase("quantifier")) {
            if (child.getAttribute("value") != null) {
              this.setStrQuantifier(child.getAttributeValue("value"));
            }
          } else if (name.trim().equalsIgnoreCase("categories")) {
            if (child.getAttribute("value") != null) {
              String strCate = child.getAttributeValue("value");
              this.setVCategories(strCate);
            }
          } else if (name.trim().equalsIgnoreCase("meaning")) {
            List listcontent = child.getContent();
            Iterator it = listcontent.iterator();
            while (it.hasNext()) {
              // System.out.println("element: "+it);
              Object aObj = it.next();
              if (aObj instanceof Element) {
                Element parentchild = (Element) aObj;
                JCALL_WordMSet wm = new JCALL_WordMSet();
                wm.element2WordMeaning(parentchild);
                this.vMeaning.add(wm);
              }
            }

          }

        }
      }
    }

  }

}