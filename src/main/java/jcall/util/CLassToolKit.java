/**
 * Created on 2007/11/08
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.util;

public class CLassToolKit {

  public static Class loadClass(String className) throws ClassNotFoundException {
    Class cls = null;
    try {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      cls = cl.loadClass(className);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // if present ClassLoader failed, use system ClassLoader
    if (cls == null) {
      cls = Class.forName(className);
    }
    return cls;
  }

}
