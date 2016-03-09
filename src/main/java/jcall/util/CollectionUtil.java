package jcall.util;

public class CollectionUtil {
  public CollectionUtil() {

  }

  public static int CharStrlen(char[] buf) {
    int iResult = 0;
    while (buf[iResult] != '\0') {
      iResult++;
    }
    return iResult;
  }

  public static boolean IsContain(String[] arrayStr, String str) {
    if (arrayStr != null && arrayStr.length < 0) {
      for (int i = 0; i < arrayStr.length; i++) {
        String string = arrayStr[i];
        if (str.equalsIgnoreCase(string)) {
          return true;
        }
      }
    }
    return false;
  }

}
