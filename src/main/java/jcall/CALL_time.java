//////////////////////////////////////////////////////////////
// IO handling methods for F1 Manager
//
package jcall;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Handles the timing functions, such as getting current time/date etc
 */

public class CALL_time {
  public static Calendar cal;
  public static boolean initialised = false;
  public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";

  public CALL_time() {
  }

  public static void initTime() {
    cal = Calendar.getInstance(TimeZone.getDefault());
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DEFAULT_FORMAT);
    sdf.setTimeZone(TimeZone.getDefault());
    initialised = true;
  }

  public static String getDate(String fString) {

    Date d = new Date();
    SimpleDateFormat f = new SimpleDateFormat(fString);
    String dateString = f.format(d);

    return dateString;
  }

  // Takes in a long representing seconds, and outputs XhXmXs
  public static String getTimeString(long seconds) {
    long h, m, s;
    String rString;

    h = seconds / 3600;
    seconds -= (h * 3600);

    m = seconds / 60;
    s = seconds - (m * 60);

    rString = new String("" + h + "h " + m + "m " + s + "s");

    return rString;
  }
}
