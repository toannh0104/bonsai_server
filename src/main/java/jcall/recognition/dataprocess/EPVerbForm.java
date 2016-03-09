/**
 * Created on 2007/06/05
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.Serializable;

public class EPVerbForm implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  // private static final long serialVersionUID = 1L;
  String strName;
  String strAction;
  String strReferto;

  public EPVerbForm() {
    init();
  }

  private void init() {
    strName = "";
    strAction = "";
    strReferto = "";
  }

  public String getStrAction() {
    return strAction;
  }

  public void setStrAction(String strAction) {
    this.strAction = strAction;
  }

  public String getStrName() {
    return strName;
  }

  public void setStrName(String strName) {
    this.strName = strName;
  }

  public String getStrReferto() {
    return strReferto;
  }

  public void setStrReferto(String strReferto) {
    this.strReferto = strReferto;
  }

}
