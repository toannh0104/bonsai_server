/**
 * Created on 2007/11/11
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.util;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

public class IOUtil {

  public static int createFile(String path, String name) {

    File oneFile = new File(path, name);
    File filePath = new File(path);
    if (!filePath.exists()) {
      filePath.mkdir();
    }
    if (!oneFile.exists()) {
      try {
        oneFile.createNewFile();
        System.out.println("create new file---" + oneFile.getAbsolutePath());
      } catch (IOException e) {
        System.out.println("can not create a new file,--exception ---" + e.toString());
        return 0;
      }
      System.out.println("update all data,creat new file---" + oneFile.getAbsolutePath());
    }
    return 1;
  }

  /*
   * Remove all the trailing control characters get the string that the last
   * character is a string
   */
  public static String strStripCC(String str) {
    str = str.trim();
    String strResult;
    char ch;
    int index = -1;
    for (int i = str.length() - 1; i >= 0; i--) {
      ch = str.charAt(i);
      if (!Character.isISOControl(ch)) {
        index = i;
        break;
      }
    }
    strResult = str.substring(0, (index + 1));
    return strResult;
  }

  /*
   * Take string tokenizer return remainder of string,seperate with whitespace;
   */
  public static String strRemainder(StringTokenizer st) {
    String strPreReminder;
    boolean firstpass = true;

    String strResult = new String("");

    while (st.hasMoreTokens()) {
      strPreReminder = new String(strResult);
      if (firstpass) {
        firstpass = false;
        strResult = new String(st.nextToken());
      } else {
        strResult = new String(strPreReminder + " " + st.nextToken());
      }
    }
    return strResult;
  }

  public static String strRemainder(StringTokenizer st, String token) {
    String tempString, finalString;
    boolean firstpass = true;

    finalString = new String("");

    while (st.hasMoreTokens()) {
      tempString = new String(finalString);
      if (firstpass) {
        firstpass = false;
        finalString = new String(st.nextToken());
      } else {
        finalString = new String(tempString + token + st.nextToken());
      }
    }

    return finalString;
  }
}
