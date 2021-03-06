///////////////////////////////////////////////////////////////////
// Lexicon Structure - holds the information about all the words
//
//

package jcall;

import java.io.PrintWriter;
import java.util.Vector;

import org.apache.log4j.Logger;

public class CALL_wordStruct {
  static Logger logger = Logger.getLogger(CALL_wordStruct.class.getName());

  // Reference to lexicon
  CALL_lexiconStruct parent;

  // Fields
  int internal_id; // This is generated by the system
  int id;
  int type;
  boolean active;
  String picture;

  // The primary word information
  String kana;
  String kanji;
  public String english;
  String genEnglish; // Without context, i.e. "Mother (Yours)" and
                     // "Mother (Other)" both equal "Mother"

  // Alternatives (Kana and Kanji will be the same length, English may vary)
  Vector altKana;
  Vector altKanji;
  Vector altEnglish;

  int level;
  int parameter1;
  int parameter2;

  Vector categories;
  Vector matches;

  public CALL_wordStruct(CALL_lexiconStruct p, int t, int iid) {
    // Reference parent
    parent = p;

    // Do some initialisation
    internal_id = iid;
    type = t;

    kana = null;
    kanji = null;
    english = null;
    genEnglish = null;

    altKana = new Vector();
    altKanji = new Vector();
    altEnglish = new Vector();

    categories = new Vector();
    matches = new Vector();

    active = false;
    id = -1;
    // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.DEBUG,
    // "New Word Created: Type " + type);
  }

  // Returns the type string related to the type integer passed in
  public static String getTypeString(int typeNum) {
    if ((typeNum < -1) || (typeNum >= (CALL_lexiconStruct.typeString.length - 1))) {
      typeNum = -1;
    }

    return CALL_lexiconStruct.typeString[typeNum + 1];
  }

  // /////////////////////////////////////////////////////////////////////////////
  // Writes the word to the lexicon - used mainly when changing lexicon format
  // /////////////////////////////////////////////////////////////////////////////
  public void write(PrintWriter outP) {
    String tempString1, tempString2;
    int i;

    outP.println("#");
    outP.println("-type " + type);
    outP.println("-id " + id);
    outP.println("-level " + level);
    outP.println("-parameter1 " + parameter1);
    outP.println("-parameter2 " + parameter2);
    outP.println("-picture " + picture);
    outP.println("-kana " + kana);
    outP.println("-kanji " + kanji);
    outP.println("-english " + english);
    outP.println("-genEnglish " + genEnglish);

    // Alternative Japanese
    for (i = 0; i < altKana.size(); i++) {
      tempString1 = (String) altKana.elementAt(i);
      if (tempString1 != null) {
        outP.println("-kana " + tempString1);
        outP.println("-kanji " + tempString1);
      }
    }

    // Alternate English
    for (i = 0; i < altEnglish.size(); i++) {
      tempString1 = (String) altEnglish.elementAt(i);
      if (tempString1 != null) {
        outP.println("-english " + tempString1);
      }
    }

    // Categories
    for (i = 0; i < categories.size(); i++) {
      tempString1 = (String) categories.elementAt(i);
      if (tempString1 != null) {
        outP.println("-category " + tempString1);
      }
    }

    // Matching Words
    for (i = 0; i < matches.size(); i++) {
      tempString1 = (String) matches.elementAt(i);
      if (tempString1 != null) {
        outP.println("-matches " + tempString1);
      }
    }
  }

  public boolean isInCategory(String category) {
    // logger.debug("enter isInCategory, category: "+ category);
    boolean rc = false;

    if (categories != null) {
      for (int i = 0; i < categories.size(); i++) {
        // logger.debug("word category["+i+"]: "+ (String)
        // categories.elementAt(i));

        if (category.equals((String) categories.elementAt(i))) {
          // We have found the group we're looking for!
          // logger.debug("We have found the group we're looking for");
          rc = true;
          break;
        }
      }
    }

    return rc;
  }

  public String getWordString(int format) {
    String rString = null;

    switch (format) {
      case CALL_io.kanji:
        rString = kanji;
        break;

      default:
        rString = kana;
        break;
    }

    if (format == CALL_io.romaji) {
      // Convert to romaji
      rString = CALL_io.strKanaToRomaji(rString);
    }

    return rString;
  }

  // get all aternative words
  // simple the word string like "tanaka", no changed to "tanaka-san"
  public Vector getWordStrings(int format) {
    Vector sourceVector = null;
    Vector rVector = new Vector();
    String rString;

    // First the default string
    // --------------------
    switch (format) {
      case CALL_io.kanji:
        rString = kanji;
        break;

      default:
        rString = kana;
        break;
    }

    if (rString != null) {
      if (format == CALL_io.romaji) {
        // Convert to romaji
        rString = CALL_io.strKanaToRomaji(rString);
      }

      // Add to vector
      if (rString != null) {
        rVector.addElement(rString);
      }
    }

    // Now all the alternative strings
    // --------------------------
    if (format == CALL_io.kanji) {
      sourceVector = altKanji;
    } else {
      sourceVector = altKana;
    }

    for (int i = 0; i < sourceVector.size(); i++) {
      rString = (String) sourceVector.elementAt(i);

      if (rString != null) {
        if (format == CALL_io.romaji) {
          // Convert to romaji
          rString = CALL_io.strKanaToRomaji(rString);
        }
        if (rString != null) {
          rVector.addElement(rString);
        }
      }
    }

    return rVector;
  }

  public void print_word_information() {
    // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO, "New Word");
    // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO, "ID: " +
    // id);
    // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO, "Type: " +
    // type);
    if (active) {
      // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO,
      // "Active: Yes");
    } else {
      // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO,
      // "Active: No");
    }
    if (picture != null) {
      // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO,
      // "Picture: " + picture);
    }
    // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO, "English: "
    // + english);
    // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO,
    // "General English: " + genEnglish);
    // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO, "Kana: " +
    // kana);
    // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO, "Kanji: " +
    // kanji);
  }

  public Vector getAltKana() {
    return altKana;
  }

  public void setAltKana(Vector altKana) {
    this.altKana = altKana;
  }

  public Vector getAltKanji() {
    return altKanji;
  }

  public void setAltKanji(Vector altKanji) {
    this.altKanji = altKanji;
  }

  public Vector getCategories() {
    return categories;
  }

  public void setCategories(Vector categories) {
    this.categories = categories;
  }

  public String getEnglish() {
    return english;
  }

  public void setEnglish(String english) {
    this.english = english;
  }

  public String getGenEnglish() {
    return genEnglish;
  }

  public void setGenEnglish(String genEnglish) {
    this.genEnglish = genEnglish;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getInternal_id() {
    return internal_id;
  }

  public void setInternal_id(int internal_id) {
    this.internal_id = internal_id;
  }

  public String getKana() {
    return kana;
  }

  public void setKana(String kana) {
    this.kana = kana;
  }

  public String getKanji() {
    return kanji;
  }

  public void setKanji(String kanji) {
    this.kanji = kanji;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public Vector getMatches() {
    return matches;
  }

  public void setMatches(Vector matches) {
    this.matches = matches;
  }

  public int getParameter1() {
    return parameter1;
  }

  public void setParameter1(int parameter1) {
    this.parameter1 = parameter1;
  }

  public int getParameter2() {
    return parameter2;
  }

  public void setParameter2(int parameter2) {
    this.parameter2 = parameter2;
  }

  public CALL_lexiconStruct getParent() {
    return parent;
  }

  public void setParent(CALL_lexiconStruct parent) {
    this.parent = parent;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Vector getAltEnglish() {
    return altEnglish;
  }

  public void setAltEnglish(Vector altEnglish) {
    this.altEnglish = altEnglish;
  }

}