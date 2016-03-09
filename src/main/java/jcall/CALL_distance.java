// A class that implement levenshtein distance
/////////////////////////////////////////////////////////

package jcall;

import org.apache.log4j.Logger;

public class CALL_distance {
  static Logger logger = Logger.getLogger(CALL_distance.class.getName());

  public CALL_distance() {
    // Do nothing - everything here is to be static
  }

  // The distance between to chars. Set to 1 currently, but may want to change
  // it based on character confusability
  static private int charDistance(char c1, char c2) {
    int distance = 1;
    return distance;
  }

  // Calculate the Levenshtein distance
  // ==============================
  static public double getDistance(String s1, String s2) {
    logger.debug("Enter getDistance;");

    if (s1 == null) {
      logger.debug("observedString(s1)is NULL");
    }
    if (s2 == null) {
      logger.debug("targetString(s2) is NULL");
      // s2="";
    }

    String str1, str2; // Strings in Romaji form
    int d[][]; // matrix
    int str1len; // length of s1
    int str2len; // length of s2
    double divLen; // The length to divide the return by (the smaller of the two
                   // strings)
    int i; // iterates through s1
    int j; // iterates through s2
    char c1; // Character from string 1
    char c2; // Character from string 2
    int cost; // cost

    // All processing with be done in romaji (to seperate vowell and consonant
    // errors)
    str1 = CALL_io.strKanaToRomaji(s1);
    str2 = CALL_io.strKanaToRomaji(s2);

    if (str1 == null) {
      // Use a blank string
      str1 = new String("");
    }

    if (str2 == null) {
      // Use a blank string
      str2 = new String("");
    }

    str1len = str1.length();
    str2len = str2.length();

    // If one of the strings is zero, the distance is the length of the other
    if (str1len == 0) {
      return str2len;
    }
    if (str2len == 0) {
      return str1len;
    }

    // This can't be 0 or less...safe
    divLen = (double) Math.min(str1len, str2len);

    // Create the distance matrix
    d = new int[str1len + 1][str2len + 1];

    // Form the distance matrix
    for (i = 0; i <= str1len; i++) {
      d[i][0] = i;
    }
    for (j = 0; j <= str2len; j++) {
      d[0][j] = j;
    }

    // Now perform matching, building the costs
    for (i = 1; i <= str1len; i++) {

      c1 = str1.charAt(i - 1);

      for (j = 1; j <= str2len; j++) {
        c2 = str2.charAt(j - 1);

        if (c1 == c2) {
          // Characters match - no cost
          cost = 0;
        } else {
          // Doesn't match. Cost of 1 (but may want to do more checking in case
          // of kana, Ka closer to Ku than chi for example)
          cost = charDistance(c1, c2);
        }

        // Update distance matrix based on this cost
        d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), d[i - 1][j - 1] + cost);

      }
    }

    // Return the distance at final stage
    return ((double) (d[str1len][str2len]) / divLen);
  }
}