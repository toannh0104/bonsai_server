/**
 * Created on 2007/11/02
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.grammarnetwork;

import java.util.StringTokenizer;
import java.util.Vector;

import jcall.db.JCALL_WordBase;

import org.jdom2.Element;

public class JCALL_SWSubs extends JCALL_WordBase {

  String errPattern; // final error type
  Boolean bCorrect;
  Boolean bValid; // valid: word in vocabulary;else, out of vocabulary
  String errSpecific; // for example: samemeaning,picture,sorekore,
  String strRestrict;
  String strErrorDescription;

  static final String[] NOUN_PREDICTTYPE = { "DW", "TW_NTD", // should be
                                                             // accepted
      "TW_PCE", "DW_NTD", "DW_PCE" };// 5

  static final String[] VERB_PREDICTTYPE = { "TW_DFORM", "DW_SFORM", "DW_DFORM", "TW_WIF" };// 8
  // "VOUT",
  // "INVDOUT"
  static final String[] PARTICAL_PREDICTTYPE = { "DW", "VDOUT" };// 2
  static final String[] DIGIT_PREDICTTYPE = { "DQ_SNum", "NQ_SNum", "TW_WForm", // wrong form means Number + Quantifier directly
      "TW_PCE", "DQ_PCE" };// 5

  static final String[] PCE_PREDICTTYPE = { "OmitShort", "OmitLong", "OmitVoiced",
      // "AddShort", //not predicted
      "AddLong", "AddVoiced" };// 5

  public JCALL_SWSubs() {
    super();
    errPattern = "";
    bCorrect = false;
  }

  public JCALL_SWSubs(int intType, String strKana, String strKanji, String strErrorPattern) {
    super(intType, strKana, strKanji);
    errPattern = strErrorPattern;
    bCorrect = false;
  }

  public JCALL_SWSubs(int intType, String strKana, String strKanji) {
    super(intType, strKana, strKanji);
    errPattern = "";
    bCorrect = false;

  }

  public String getErrorPattern() {
    return errPattern;

  }

  public void setErrorPattern(String errorPattern) {
    this.errPattern = errorPattern;
  }

  public Boolean getBCorrect() {
    return bCorrect;
  }

  public void setBCorrect(Boolean correct) {
    bCorrect = correct;
  }

  public String getStrErrorDescription() {
    return strErrorDescription;
  }

  public void setStrErrorDescription(String strErrorDescription) {
    this.strErrorDescription = strErrorDescription;
  }

  public String getStrRestrict() {
    return strRestrict;
  }

  public void setStrRestrict(String strRestrict) {
    this.strRestrict = strRestrict;
  }

  public Element toElement() {
    Element elemRoot = new Element("word");
    if (this.getId() >= -1) {
      elemRoot.setAttribute("id", Integer.toString(this.getId()));
    }
    if (this.getIntType() >= -1) {
      elemRoot.setAttribute("type", Integer.toString(this.getIntType()));
    }
    if (this.getStrKana() != null && this.getStrKana().length() > 0) {
      elemRoot.setAttribute("kana", this.getStrKana());
    }
    if (this.getStrKanji() != null && this.getStrKanji().length() > 0) {
      elemRoot.setAttribute("kanji", this.getStrKanji());
    }

    if (this.getStrAltkana() != null && this.getStrAltkana().length() > 0) {
      elemRoot.setAttribute("altkana", this.getStrAltkana());
    }
    if (this.getErrorPattern() != null && this.getErrorPattern().length() > 0) {
      elemRoot.setAttribute("subspattern", getPatternValue());
    }
    if (this.getStrRestrict() != null && this.getStrRestrict().length() > 0) {
      elemRoot.setAttribute("restrict", this.getStrRestrict());
    }
    if (this.getStrErrorDescription() != null && this.getStrErrorDescription().length() > 0) {
      elemRoot.setAttribute("errdesc", this.getStrErrorDescription());
    }
    // if(this.bCorrect){
    // elemRoot.setAttribute("altanswer","T");
    // }else{
    // elemRoot.setAttribute("altanswer","F");
    // }
    return elemRoot;
  }

  public void toObject(Element e) {
    if (e.getAttribute("id") != null) {
      this.setId(Integer.parseInt(e.getAttributeValue("id")));
    }
    if (e.getAttribute("type") != null) {
      this.setIntType(Integer.parseInt(e.getAttributeValue("type")));
    }
    if (e.getAttribute("kana") != null) {
      this.setStrKana(e.getAttributeValue("kana"));
    }
    if (e.getAttribute("kanji") != null) {
      this.setStrKanji(e.getAttributeValue("kanji"));
    }
    if (e.getAttribute("altkana") != null) {
      this.setStrAltkana(e.getAttributeValue("altkana"));
    }

    if (e.getAttribute("subspattern") != null || e.getAttribute("errorType") != null) {
      if (e.getAttribute("subspattern") != null) {
        String eAtribute = e.getAttributeValue("subspattern");
        setPatternValue(eAtribute);

      } else if (e.getAttribute("errorType") != null) {
        String eAtribute = e.getAttributeValue("errorType");
        setPatternValue(eAtribute);

      }

    }
    if (e.getAttribute("restrict") != null) {
      this.setStrRestrict(e.getAttributeValue("restrict"));
    }
    if (e.getAttribute("errdesc") != null) {
      this.setStrErrorDescription(e.getAttributeValue("errdesc"));
    }
    if (e.getAttribute("altanswer") != null) {
      String str = e.getAttributeValue("altanswer").trim();
      if (str.equalsIgnoreCase("T")) {
        this.setBCorrect(true);
      } else if (str.equalsIgnoreCase("F")) {
        this.setBCorrect(false);
      }
    }
  }

  private void setPatternValue(String pattern) {
    // TODO Auto-generated method stub
    // "DW.ALT2.KeyPoint.invalid&wrong"
    // "DW.Related.invalid&wrong"
    Vector<String> vPat = new Vector();
    Vector<String> vValid = new Vector();
    StringTokenizer st = new StringTokenizer(pattern, ".");
    if (st.hasMoreElements()) {
      String str = st.nextToken();
      if (str != null && str.length() > 0) {
        // this.errPattern = str;
        vPat.addElement(str);
      }
    }
    int intsize = vPat.size();
    if (vPat != null && intsize > 0) {
      this.errPattern = (String) vPat.elementAt(0);

      // process the last
      if (intsize > 1) {
        String valid = (String) vPat.elementAt(intsize - 1);
        StringTokenizer st2 = new StringTokenizer(valid, "&");
        if (st2.hasMoreElements()) {
          String str2 = st2.nextToken();
          if (str2 != null && str2.length() > 0) {
            // this.errPattern = str;
            vValid.addElement(str2);
          }
        }

        if (vValid != null && vValid.size() == 2) {
          String s1 = vValid.get(0).trim();
          String s2 = vValid.get(1).trim();
          if (s1.equalsIgnoreCase("valid")) {
            this.bValid = true;
          } else if (s1.equalsIgnoreCase("invalid")) {
            this.bValid = false;
          }

          if (s1.equalsIgnoreCase("correct")) {
            this.bCorrect = true;
          } else if (s1.equalsIgnoreCase("wrong")) {
            this.bCorrect = false;
          }

        } else {
          System.out.println("ERR: no such kind of data like---invalid&wrong");
        }

      }
      // process the middle data

      if (intsize > 2) {
        String strMid = "";
        for (int i = 1; i < intsize - 1; i++) {
          strMid = strMid + "." + vPat.elementAt(i);
        }
        if (strMid.length() > 0) {
          this.errSpecific = strMid.substring(1);

        }

      }

    }

  }

  private String getPatternValue() {

    String strResult = "";
    if (this.errPattern != null && errPattern.length() > 0) {
      strResult = strResult + errPattern;

    }

    if (this.errSpecific != null && errSpecific.length() > 0) {
      strResult = strResult + "." + errSpecific;

    }

    if (this.bValid) {
      strResult = strResult + ".valid";

    } else {
      strResult = strResult + ".invalid";
    }

    if (this.bCorrect) {
      strResult = strResult + "&correct";
    } else {
      strResult = strResult + "&wrong";
    }

    return errPattern;

  }
}
