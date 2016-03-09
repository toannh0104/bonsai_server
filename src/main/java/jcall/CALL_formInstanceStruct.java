///////////////////////////////////////////////////////////////////
// Holds the information about the form specification for a verb / adjective
//
//

package jcall;

public class CALL_formInstanceStruct {
  int tense;
  int sign;
  int style;
  int type;

  public CALL_formInstanceStruct() {
    tense = -1;
    sign = -1;
    style = -1;
    type = -1;
  }

  public CALL_formInstanceStruct(int i) {
    tense = i;
    sign = i;
    style = i;
    type = i;
  }

  public String toString() {
    String str = "";
    str = str + " " + "Sign: " + sign;
    str = str + " " + "Tense: " + tense;
    str = str + " " + "Style: " + style;
    str = str + " " + "Type: " + type;
    return str.trim();
  }

  public void print_debug() {
    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG, "Sign: " +
    // sign);
    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG, "Tense: "
    // + tense);
    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG, "Style: "
    // + style);
    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG, "Type: " +
    // type);
  }
}