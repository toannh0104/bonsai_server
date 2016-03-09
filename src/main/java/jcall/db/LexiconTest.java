/**
 * Created on 2008/03/17
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

import jcall.CALL_lexiconStruct;
import jcall.CALL_wordStruct;

public class LexiconTest {

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    JCALL_Lexicon l = JCALL_Lexicon.getInstance();
    // JCALL_Word word = l.getWordFmStr("いける", JCALL_Lexicon.LEX_TYPE_VERB);
    JCALL_Word word = l.getWordFmStr("いける");

    if (word != null) {

      System.out.println("Find,Word: " + word.getStrKanji());
    }

    // from JCALL_Lexicon to CALL_LexiconStruct

    CALL_lexiconStruct ls = new CALL_lexiconStruct(null);
    if (ls.load_lexicon(l)) {

      CALL_wordStruct ws = ls.getWord("いける");
      if (ws != null) {

        System.out.println("Find in old lexicon,Word: " + ws.getKanji());
      } else {
        System.out.println("not Find");
      }

    }

  }

}
