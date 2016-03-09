/**
 * Created on 2008/03/11
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db.prediction;

import java.util.Vector;

import jcall.db.JCALL_NetworkSubNodeStruct;

public class PNodes {

  Vector pnodes;

  public Vector getPnodes() {
    return pnodes;
  }

  public void setPnodes(Vector pnodes) {
    this.pnodes = pnodes;
  }

  public void addPnode(JCALL_NetworkSubNodeStruct pnode) {
    if (this.pnodes == null) {
      pnodes = new Vector();
    }
    pnodes.addElement(pnode);
  }
}
