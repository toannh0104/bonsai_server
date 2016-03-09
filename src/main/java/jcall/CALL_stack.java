//////////////////////////////////////////////////////////////
// IO handling methods for F1 Manager
//
package jcall;

import java.util.Vector;

/**
 * Handles stack functionality
 */

public class CALL_stack {
  Vector data;

  public CALL_stack() {
    data = new Vector();
  }

  public void push(Object obj) {
    data.addElement(obj);
    // CALL_debug.printlog(CALL_debug.MOD_STACK, CALL_debug.DEBUG, "Push: [" +
    // obj.toString() + "]");
  }

  public Object pop() {
    Object obj = null;
    int i = data.size();

    if (i > 0) {
      obj = data.lastElement();
      data.removeElementAt(i - 1);
    }
    // CALL_debug.printlog(CALL_debug.MOD_STACK, CALL_debug.DEBUG, "Pop: [" +
    // obj.toString() + "]");

    return obj;
  }

  public int size() {
    return data.size();
  }
}
