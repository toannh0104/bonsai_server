/**
 * Created on 2007/06/05
 * @author wang
 * Copyrights @kawahara lab
 */

package jcall.recognition.dataprocess;

import java.io.Serializable;

public class EPLeaf extends JWordBase implements Cloneable, Serializable {

  String strRestrict;
  String strErrorType;
  String strErrorDescription;
  JWordBase prefix;
  JWordBase suffix;

  public EPLeaf() {
    super(-1, "", "");
    strRestrict = "";
    strErrorType = "";
    strErrorDescription = "";
  }

  public String getStrRestrict() {
    return strRestrict;
  }

  public void setStrRestrict(String strRestrict) {
    this.strRestrict = strRestrict;
  }

  public String getStrErrorDescription() {
    return strErrorDescription;
  }

  public void setStrErrorDescription(String strErrorDescription) {
    this.strErrorDescription = strErrorDescription;
  }

  public String getStrErrorType() {
    return strErrorType;
  }

  public void setStrErrorType(String strErrorType) {
    this.strErrorType = strErrorType;
  }

  public Object clone() {
    EPLeaf o = null;
    o = (EPLeaf) super.clone();
    o.setStrRestrict(getStrRestrict());
    o.setStrErrorType(getStrErrorType());
    o.setStrErrorType(getStrErrorDescription());
    return o;
  }
}
