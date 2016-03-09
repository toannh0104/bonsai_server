/**
 * Created on 2007/06/05
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.Serializable;
import java.util.Vector;

public class EPVerbSetting implements Serializable {

  private static final long serialVersionUID = 1L;
  String strName;
  String strQuestion;
  Vector<EPVerbForm> vecForm;

  public EPVerbSetting() {
    init();
  }

  private void init() {
    strName = "";
    strQuestion = "";
    vecForm = new Vector<EPVerbForm>();
  }

  public String getStrName() {
    return strName;
  }

  public void setStrName(String strName) {
    this.strName = strName;
  }

  public Vector<EPVerbForm> getVecForm() {
    return vecForm;
  }

  public void setVecForm(Vector<EPVerbForm> vecForm) {
    this.vecForm = vecForm;
  }

  public void add2VecForm(EPVerbForm epsf) {
    this.vecForm.addElement(epsf);
  }

  public String getStrQuestion() {
    return strQuestion;
  }

  public void setStrQuestion(String strQuestion) {
    this.strQuestion = strQuestion;
  }

}
