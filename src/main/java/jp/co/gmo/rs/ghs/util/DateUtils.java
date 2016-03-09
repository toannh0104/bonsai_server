/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * DateUtils
 *
 * @author LongVNH
 * @version 1.0
 */
public class DateUtils {

    /**
     * Constructor
     */
    protected DateUtils() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

    /**
     * Get current time
     *
     * @return date
     */
    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    /**
     * format Date
     * 
     * @param date
     * @return date
     */
    public static String formatDate(Date date) {
        String d = null;
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            d = sdf.format(date);
        }
        return d;
    }

    /**
     * get time between d1 and d2
     * 
     * @param d1
     * @param d2
     * @return time
     */
    public static String getTime(Date d1, Date d2) {
        long time = 0;
        if (d1 != null && d2 != null) {
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            if (diffDays > 0) {
                time += (diffDays * 24 * 60);
            }

            if (diffHours > 0) {
                time += (diffHours * 60);
            }

            if (diffMinutes > 0) {
                time += diffMinutes;
                
            }

            if (diffSeconds > 0) {
                time += 1;
            }
        }
        if (time > 0) {
            return time + "";
        }
        return null;
    }

}
