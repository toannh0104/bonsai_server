/**
 * Created on 2007/06/05
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.util.Vector;

public class EPVerbSettingSet {
  Vector<EPVerbSetting> vecSetting;

  public EPVerbSettingSet() {
    init();
  }

  private void init() {
    vecSetting = new Vector<EPVerbSetting>();
  }

  public Vector<EPVerbSetting> getVecSetting() {
    return vecSetting;
  }

  public void setVecSetting(Vector<EPVerbSetting> vecSetting) {
    this.vecSetting = vecSetting;
  }

  public void add2VecSetting(EPVerbSetting epvs) {
    this.vecSetting.addElement(epvs);
  }
}
