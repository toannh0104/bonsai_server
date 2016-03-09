///////////////////////////////////////////////////////////////////
// Lexicon Structure - holds the information about all the words
//
//

package jcall;

import java.util.Vector;

public class CALL_lessonExampleStruct {
  String imageStr;
  String answerJ;
  String answerK;
  String english;

  String formHelp;
  String gfxHelp;
  String diagHelp;

  String questionType;

  Vector forms; // A vector of CALL_stringPairsStruct - lists of lists of pairs
                // (eg. "Statment", "<off>")

  // More fields, such as information overlay etc?

  public CALL_lessonExampleStruct() {
    imageStr = null;
    answerJ = null;
    answerK = null;
    english = null;
    formHelp = null;
    gfxHelp = null;
    questionType = null;

    forms = new Vector();
  }

  /**
 * @return the answerJ
 */
public String getAnswerJ() {
    return answerJ;
}

/**
 * @param answerJ the answerJ to set
 */
public void setAnswerJ(String answerJ) {
    this.answerJ = answerJ;
}

/**
 * @return the answerK
 */
public String getAnswerK() {
    return answerK;
}

/**
 * @param answerK the answerK to set
 */
public void setAnswerK(String answerK) {
    this.answerK = answerK;
}

public void print_debug() {
  }
}