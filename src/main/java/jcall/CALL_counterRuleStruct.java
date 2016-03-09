///////////////////////////////////////////////////////////////////
// Verb Rule Structures - holds the information about how to construct
//				  the various inflections for each verb group.
//				  All data is loaded from the verbRules.txt file.
//
///////////////////////////////////////////////////////////////////

package jcall;

import org.apache.log4j.Logger;

public class CALL_counterRuleStruct {
  static Logger logger = Logger.getLogger(CALL_counterRuleStruct.class.getName());

  // Defines
  // Kurita 2011/02/18
  // static final int MAX_NUMBER = 128;
  static final int MAX_NUMBER = 256;

  // Fields
  String category;
  String[] kana;
  String[] kanji;

  // Empty Constructor
  public CALL_counterRuleStruct() {
    init();
  }

  // Named Constructor
  public CALL_counterRuleStruct(String name) {
    init();
    category = name;
  }

  private void init() {
    category = null;
    kana = new String[MAX_NUMBER];
    kanji = new String[MAX_NUMBER];
    for (int i = 0; i < MAX_NUMBER; i++) {
      kana[i] = null;
      kanji[i] = null;
    }
  }

  public void addCounter(int number, String kanaS, String kanjiS) {
    if ((number >= 0) && (number < MAX_NUMBER)) {
      kana[number] = kanaS;
      kanji[number] = kanjiS;
    }
  }

  // Returns a vector of Strings, the acceptable counters for the word
  // NOTE - only dealing with numbers 1 to 10 right now!!!
  public String getCounter(int number, int outputType) {
    logger.debug("enter getCounter in rule; number: " + number + " outputType" + outputType);

    String returnString = null;

    if ((number >= 0) && (number < MAX_NUMBER)) {
      if (outputType == CALL_io.kanji) {
        returnString = kanji[number];
      } else {
        returnString = kana[number];
        if (outputType == CALL_io.romaji) {
          // Convert to romaji
          returnString = CALL_io.strKanaToRomaji(returnString);
        }
      }
    } else {
      logger.debug("number is out of boundary");

    }

    if (returnString != null) {
      logger.debug("Find word: " + returnString);
    }

    return returnString;
  }

  public String toString() {
    String str = "";
    str = str + "Counter Category: " + category;
    for (int i = 0; i < MAX_NUMBER; i++) {
      if ((kana[i] != null) && (kanji[i] != null)) {
        str = str + "Number " + i + " => " + kana[i] + " (" + kanji[i] + ")";
      }
    }
    return str;
  }

  public void print_debug() {
    // CALL_debug.printlog(CALL_debug.MOD_CRULES, CALL_debug.INFO,
    // "Counter Category: " + category);
    for (int i = 0; i < MAX_NUMBER; i++) {
      if ((kana[i] != null) && (kanji[i] != null)) {
        // CALL_debug.printlog(CALL_debug.MOD_CRULES, CALL_debug.INFO, "Number "
        // + i + " => " + kana[i] + " (" + kanji[i] + ")");
      }
    }
  }
}