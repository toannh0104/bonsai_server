/**
 * Created on 2007/05/03
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.database;

import java.io.Serializable;

public class WordErrDataMeta implements Serializable {
  public int intID;
  public int intStudentID;
  public String strLesson;
  public String strQuestion;
  public String strBaseGrammar;
  public String strFullGrammar;
  public String strObservedWord;
  public String strTargetWord;
  public String strObservedType;
  public String strTargetType;
  public boolean booSameType;
  public boolean booValid; // strTargetType is not UNKNOWN,for verb,it means we
                           // have such a transformation of the correct word;for
                           // others, it means we have such a word in our
                           // database;

  public boolean booSameConfusionGroup;
  public boolean booSpell; // Likely Spelling Mistake, with respect to our spell
                           // defination

  public boolean booEnglishStem; // target word is english based word

  // public boolean booWrongForm;//
  public boolean booSameStem; // the same stem means the two words have the same
                              // base word,
  // so the mistake could be wrongForm or invalidForm with v,adj,adjv,and n.
  public boolean booInvalidForm; // Invalid expansion. for verb it is the
                                 // tramsformation ;
  // for noun, it is the wrong honorific form or humility form
  public boolean booOneStepErrW; // that is SUBS5

  public String strGeneralType; // chosen from category list (GRAMMAR, CONCEPT,
                                // LEXICAL, INPUTetc)
  public String strSpecificType; // From list: WRONG_FORM, INVALID_FORM, SUBS1,
                                 // SUBS2, SUBS3, SUBS4, SUBS5, SUBS6, MULTIPLE,
                                 // NULL

  public String strFormMistakes; // list all the possible mistake forms
  public String strFormDistance; // String Edit Distance

  public WordErrDataMeta() {
    init();
  }

  private void init() {
    intID = -1;
    intStudentID = -1;
    strLesson = "";
    strQuestion = "";
    strBaseGrammar = "";
    strFullGrammar = "";
    strObservedWord = "";
    strTargetWord = "";
    strObservedType = "";
    strTargetType = "";
    booSameType = false;
    booValid = false;

    booSameConfusionGroup = false;
    booSpell = false;
    booEnglishStem = false;
    strFormMistakes = "";
    strFormDistance = "";
    booSameStem = false;
    strGeneralType = "";
    strSpecificType = "";
    booOneStepErrW = false;
    booInvalidForm = false;
  }

  public String toString() {
    String strResult = "";
    strResult = strResult + "intID is [" + intID + "]\t";
    strResult = strResult + "intStudentID is [" + intStudentID + "]\t";
    strResult = strResult + "strLesson is [" + strLesson + "]\t";
    strResult = strResult + "strQuestion is [" + strQuestion + "]\t";
    strResult = strResult + "strBaseGrammar is [" + strBaseGrammar + "]\t";
    strResult = strResult + "strFullGrammar is [" + strFullGrammar + "]\t";
    strResult = strResult + "strObservedWord is [" + strObservedWord + "]\t";
    strResult = strResult + "strTargetWord is [" + strTargetWord + "]\t";
    strResult = strResult + "strObservedType is [" + strObservedType + "]\t";
    strResult = strResult + "strTargetType is [" + strTargetType + "]\t";
    strResult = strResult + "booValid is [" + booValid + "]\t";
    strResult = strResult + "strGeneralType is [" + strGeneralType + "]\t";
    strResult = strResult + "strSpecificType is [" + strSpecificType + "]\t";
    strResult = strResult + "strFormMistakes is [" + strFormMistakes + "]\t";
    strResult = strResult + "strFormDistance is [" + strFormDistance + "]\t";
    return strResult;

  }

  public String toVALUES() {
    String strResult = "('";
    strResult = strResult + intStudentID + "','" + strLesson + "','" + strQuestion + "','" + strBaseGrammar + "','"
        + strFullGrammar + "','" + strObservedWord + "','" + strTargetWord + "','" + strObservedType + "','"
        + strTargetType + "'," + booSameType + "," + booValid + "," + booSameConfusionGroup + "," + booSpell + ","
        + booEnglishStem + "," + booSameStem + ")";

    return strResult;

  }

  /*
   * 
   * public String toVALUESAll(){ String strResult = "('"; strResult = strResult
   * + intStudentID + "','"+ strLesson + "','" + strQuestion + "','" +
   * strBaseGrammar + "','" + strFullGrammar + "','" + strObservedWord + "','" +
   * strTargetWord + "','" + strObservedType + "','" + strTargetType+ "','" +
   * booSameType + "','" + booValid + "','" + booSameConfusionGroup+ "','" +
   * booSpell+ "','" + booEnglishStem + "','" + booSameStem+ "','" +
   * booInvalidForm+ "','" + booOneStepErrW+ "','" + strGeneralType+ "','" +
   * strSpecificType + "','" + strFormMistakes + "','" + strFormDistance + "')";
   * 
   * return strResult;
   * 
   * }
   */
  public boolean isBooEnglishStem() {
    return booEnglishStem;
  }

  public void setBooEnglishStem(boolean booEnglishStem) {
    this.booEnglishStem = booEnglishStem;
  }

  public boolean isBooFormMistake() {
    return booInvalidForm;
  }

  public void setBooFormMistake(boolean booFormMistake) {
    this.booInvalidForm = booFormMistake;
  }

  public boolean isBooSameConfusionGroup() {
    return booSameConfusionGroup;
  }

  public void setBooSameConfusionGroup(boolean booSameConfusionGroup) {
    this.booSameConfusionGroup = booSameConfusionGroup;
  }

  public boolean isBooSameType() {
    return booSameType;
  }

  public void setBooSameType(boolean booSameType) {
    this.booSameType = booSameType;
  }

  public boolean isBooSpell() {
    return booSpell;
  }

  public void setBooSpell(boolean booSpell) {
    this.booSpell = booSpell;
  }

  public boolean isBooValid() {
    return booValid;
  }

  public void setBooValid(boolean booValid) {
    this.booValid = booValid;
  }

  public int getIntID() {
    return intID;
  }

  public void setIntID(int intID) {
    this.intID = intID;
  }

  public int getIntStudentID() {
    return intStudentID;
  }

  public void setIntStudentID(int intStudentID) {
    this.intStudentID = intStudentID;
  }

  public String getStrBaseGrammar() {
    return strBaseGrammar;
  }

  public void setStrBaseGrammar(String strBaseGrammar) {
    this.strBaseGrammar = strBaseGrammar;
  }

  public String getStrFormDistance() {
    return strFormDistance;
  }

  public void setStrFormDistance(String strFormDistance) {
    this.strFormDistance = strFormDistance;
  }

  public String getStrFormMistakes() {
    return strFormMistakes;
  }

  public void setStrFormMistakes(String strFormMistakes) {
    this.strFormMistakes = strFormMistakes;
  }

  public String getStrQuestion() {
    return strQuestion;
  }

  public void setStrQuestion(String strQuestion) {
    this.strQuestion = strQuestion;
  }

  public String getStrSpecificType() {
    return strSpecificType;
  }

  public void setStrSpecificType(String strSpecificType) {
    this.strSpecificType = strSpecificType;
  }

  public String getStrTargetType() {
    return strTargetType;
  }

  public void setStrTargetType(String strTargetType) {
    this.strTargetType = strTargetType;
  }

  public String getStrTargetWord() {
    return strTargetWord;
  }

  public void setStrTargetWord(String strTargetWord) {
    this.strTargetWord = strTargetWord;
  }

  public WordErrDataMeta(int intID, int intStudentID, String strLesson, String strQuestion, String strBaseGrammar,
      String strFullGrammar, String strObservedWord, String strTargetWord, String strObservedType,
      String strTargetType, boolean booSameType, boolean booValid, boolean booSameConfusionGroup, boolean booSpell,
      boolean booEnglishStem, String strFormMistakes, String strFormDistance, String strGeneralType,
      String strSpecificType, boolean booInvalidForm) {
    super();
    this.intID = intID;
    this.intStudentID = intStudentID;
    this.strLesson = strLesson;
    this.strQuestion = strQuestion;
    this.strBaseGrammar = strBaseGrammar;
    this.strFullGrammar = strFullGrammar;
    this.strObservedWord = strObservedWord;
    this.strTargetWord = strTargetWord;
    this.strObservedType = strObservedType;
    this.strTargetType = strTargetType;
    this.booSameType = booSameType;
    this.booValid = booValid;
    this.booSameConfusionGroup = booSameConfusionGroup;
    this.booSpell = booSpell;
    this.booEnglishStem = booEnglishStem;
    this.strFormMistakes = strFormMistakes;
    this.strFormDistance = strFormDistance;
    this.strGeneralType = strGeneralType;
    this.strSpecificType = strSpecificType;
    this.booInvalidForm = booInvalidForm;
  }

  public boolean isBooInvalidForm() {
    return booInvalidForm;
  }

  public void setBooInvalidForm(boolean booInvalidForm) {
    this.booInvalidForm = booInvalidForm;
  }

  public boolean isBooSameStem() {
    return booSameStem;
  }

  public void setBooSameStem(boolean booSameStem) {
    this.booSameStem = booSameStem;
  }

  public boolean isBooOneStepErrW() {
    return booOneStepErrW;
  }

  public void setBooOneStepErrW(boolean booOneStepErrW) {
    this.booOneStepErrW = booOneStepErrW;
  }

  public String getStrFullGrammar() {
    return strFullGrammar;
  }

  public void setStrFullGrammar(String strFullGrammar) {
    this.strFullGrammar = strFullGrammar;
  }

  public String getStrGeneralType() {
    return strGeneralType;
  }

  public void setStrGeneralType(String strGeneralType) {
    this.strGeneralType = strGeneralType;
  }

  public String getStrLesson() {
    return strLesson;
  }

  public void setStrLesson(String strLesson) {
    this.strLesson = strLesson;
  }

  public String getStrObservedType() {
    return strObservedType;
  }

  public void setStrObservedType(String strObservedType) {
    this.strObservedType = strObservedType;
  }

  public String getStrObservedWord() {
    return strObservedWord;
  }

  public void setStrObservedWord(String strObservedWord) {
    this.strObservedWord = strObservedWord;
  }

}
