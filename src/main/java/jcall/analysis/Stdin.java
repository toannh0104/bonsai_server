package jcall.analysis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.NumberFormat;

public class Stdin {

  static InputStreamReader converter = new InputStreamReader(System.in);
  static BufferedReader in = new BufferedReader(converter);

  // Read a String from standard system input
  public static String getString() {
    try {
      return in.readLine();
    } catch (Exception e) {
      System.out.println("getString() exception, returning empty string");
      return "";
    }
  }

  // Read a char from standard system input
  public static char getChar() {
    String s = getString();
    if (s.length() >= 1)
      return s.charAt(0);
    else
      return '\n';
  }

  // Read a Number as a String from standard system input
  // Return the Number
  public static Number getNumber() {
    String numberString = getString();
    try {
      numberString = numberString.trim().toUpperCase();
      return NumberFormat.getInstance().parse(numberString);
    } catch (Exception e) {
      // if any exception occurs, just give a 0 back
      System.out.println("getNumber() exception, returning 0");
      return new Integer(0);
    }
  }

  // Read an integer from standard system input
  public static int getInt() {
    return getNumber().intValue();
  }

  // Read a float from standard system input
  public static float getFloat() {
    return getNumber().floatValue();
  }

  // Read a double from standard system input
  public static double getDouble() {
    return getNumber().doubleValue();
  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    // System.out.print("Hello World!");
    boolean boo = false;
    int i = 0;
    while (!boo) {
      String strLine = Stdin.getString();
      if (strLine != null && strLine.length() > 10) {
        strLine = strLine.trim();
        String substr = strLine.substring(0, 9);
        // System.out.println(substr);
        if (substr.equalsIgnoreCase("sentence1")) {
          System.out.println(strLine);
          boo = true;
        }
      } else {
        System.out.println("There is something wrong ,nothing correct is read");
      }
      i++;
      // System.out.println("the----"+i+"--- interation");

    }
    // System.out.print("Hello World!");
  }

}
