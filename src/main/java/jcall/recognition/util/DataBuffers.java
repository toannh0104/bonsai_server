/**
 * Created on 2007/08/10
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.util;

import java.util.Stack;
import java.util.Vector;

import org.apache.log4j.Logger;

public class DataBuffers {

  static Stack<String> resultStack; // = new Stack<String>();

  static Logger logger = Logger.getLogger(DataBuffers.class.getName());

  private static Object synchronizeObj = new Object();

  public DataBuffers() {
    resultStack = new Stack<String>();
  }

  /*
   * save data to stack
   */
  public void saveData(Stack<String> stack, String str) {
    stack.push(str);
  }

  /*
   * save data to static stack
   */
  public synchronized void saveData(String str) {
    if (resultStack != null) {
      resultStack.push(str);
    }
  }

  public synchronized String popData() {
    String str = "";
    if (!resultStack.empty()) {
      str = (String) resultStack.pop();
      resultStack.clear();
    } else {
      System.out.println("the resultStack is empty");
    }
    return str;
  }

  public synchronized Vector readData() {
    Vector str = null;
    if (!resultStack.empty()) {
      str = new Vector();
      while (!resultStack.empty()) {
        String strTemp = (String) resultStack.pop();
        if (strTemp != null && strTemp.length() > 0) {
          str.addElement(strTemp);
        }
      }

    } else {
      System.out.println("the resultStack is empty");
    }
    return str;
  }

  // synchronized(synchronizeObj)

  public static void saveData2Stack(String str) {

    synchronized (synchronizeObj) {

      if (resultStack != null) {
        resultStack.push(str);
      }

    }
  }

  public static String popDataFromStack() {
    String str = "";
    synchronized (synchronizeObj) {
      if (!resultStack.empty()) {

        str = (String) resultStack.pop();
        resultStack.clear();

      } else {
        System.out.println("the resultStack is empty");
      }
    }
    return str;
  }

  public static Vector readDataFromStack() {
    Vector str = null;
    synchronized (synchronizeObj) {
      if (!resultStack.empty()) {
        str = new Vector();
        while (!resultStack.empty()) {
          String strTemp = (String) resultStack.pop();
          if (strTemp != null && strTemp.length() > 0) {
            str.addElement(strTemp);
          }
        }
      } else {
        System.out.println("the resultStack is empty");
        str = new Vector();
      }
    }
    return str;
  }

  public static String[] readDataInSequenceFromStack() {
    String[] str = new String[10];
    int i = 0;
    synchronized (synchronizeObj) {
      if (!resultStack.empty()) {
        while (!resultStack.empty()) {
          String strTemp = (String) resultStack.pop();
          if (strTemp != null && strTemp.length() > 0) {
            str[i] = strTemp;
            i++;
          }
        }
      } else {
        System.out.println("the resultStack is empty");
      }
    }

    return str;
  }

}
