/**
 * Created on 2008/03/12
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

import java.util.Vector;

public class JCALL_NetworkStruct {

  Vector<JCALL_NetworkNodeStruct> vGW;

  public Vector getVGW() {
    return vGW;
  }

  public void setVGW(Vector<JCALL_NetworkNodeStruct> vgw) {
    vGW = vgw;
  }

  public void addGW(JCALL_NetworkNodeStruct gw) {
    if (vGW == null) {
      vGW = new Vector<JCALL_NetworkNodeStruct>();
    }
    vGW.addElement(gw);
  }

}
