///////////////////////////////////////////////////////////////////
// Lexicon Structure - holds the information about all the words
//
//

package jcall;

import java.util.Vector;

public class CALL_lexiconCategoryStruct {
  // Category Information
  String name;

  // Main Data Storage Arrays (CALL_wordStruct)
  Vector words;

  public CALL_lexiconCategoryStruct() {
    // Does nothing at present
    name = null;
    words = new Vector();
  }

  public CALL_lexiconCategoryStruct(String cname) {
    // Alternative constructor
    name = new String(cname);
    words = new Vector();
  }

  // Adds a word
  public void add_word(CALL_wordStruct word) {
    // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.DEBUG,
    // "Adding word [" + word.kana + "] to category [" + name + "]");
    words.addElement(word);
  }
}