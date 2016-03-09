/**
 * Created on 2007/09/27
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.database;

public class WordDataMeta {
  public int intID;
  public int intStudentID;
  public String strLesson;
  public String strQuestion; // be careful to keep consistent with the same
                             // dataset in sentence table
  // public String strBaseGrammar;
  // public String strFullGrammar;
  public String strObservedWord;
  public String strTargetWord;

  public String strRecognitionWord;
  public String strListenWord;

  public String strObservedType;
  public String strTargetType;
  public String strListenType;

  public boolean IsSameRecognitionWithObserved;
  public boolean IsSameObservedWithTarget;
  public boolean IsSameObservedWithListen;

  public boolean IsSameRecognitionWithListen;
  public boolean IsSameListenWithTarget;
  public boolean IsSameRecognitionWithTarget;

  // public boolean booSameType;
  // public boolean booValid; //strTargetType is not UNKNOWN,for verb,it means
  // we have such a transformation of the correct word;for others, it means we
  // have such a word in our database;
  // public boolean booSameConfusionGroup;
  // public boolean booSpell; //Likely Spelling Mistake, with respect to our
  // spell defination
  // public boolean booEnglishStem; //target word is english based word
  // public boolean booSameStem; //the same stem means the two words have the
  // same base word,
  // so the mistake could be wrongForm v,adj,adjv.

  // public boolean booInvalidForm; //Invalid expansion. for verb it is the
  // tramsformation ;
  // for noun, it is the wrong honorific form or humility form
  // public boolean booOneStepErrW; // that is SUBS5
  // public boolean booRecog; // Likely Recognition Mistake,

  // public boolean booSystem; // Likely System Mistake(text answer exist in the
  // recognition files, but something was wrong when runing the system)

  public String strGeneralType; // chosen from category list (GRAMMAR, CONCEPT,
                                // LEXICAL, INPUTetc)
  public String strSpecificType; // From list: WRONG_FORM, INVALID_FORM, SUBS1,
                                 // SUBS2, SUBS3, SUBS4, SUBS5, SUBS6, MULTIPLE,
                                 // NULL

  public String strFormMistakes; // list all the possible mistake forms
  public String strFormDistance; // String Edit Distance

  public WordDataMeta() {
    init();
  }

  private void init() {
    intID = -1;
    intStudentID = -1;
    strLesson = "";
    strQuestion = "";
    strObservedWord = "";
    strTargetWord = "";
    strRecognitionWord = "";
    strListenWord = "";

    strObservedType = "";
    strTargetType = "";
    strListenType = "";

    IsSameRecognitionWithObserved = false;
    IsSameObservedWithTarget = false;
    IsSameRecognitionWithListen = false;
    IsSameListenWithTarget = false;
    IsSameObservedWithListen = false;

    strFormMistakes = "";
    strFormDistance = "";
    strGeneralType = "";
    strSpecificType = "";
    // booSystem = false;
  }

  public String toString() {
    String strResult = "";
    strResult = strResult + "intID is [" + intID + "]\t";
    strResult = strResult + "intStudentID is [" + intStudentID + "]\t";
    strResult = strResult + "strLesson is [" + strLesson + "]\t";
    strResult = strResult + "strQuestion is [" + strQuestion + "]\t";
    strResult = strResult + "strTargetWord is [" + strTargetWord + "]\t";
    strResult = strResult + "strObservedWord is [" + strObservedWord + "]\t";
    strResult = strResult + "strRecognitionWord is [" + strRecognitionWord + "]\t";
    strResult = strResult + "strListenWord is [" + strListenWord + "]\t";
    strResult = strResult + "strObservedType is [" + strObservedType + "]\t";
    strResult = strResult + "strTargetType is [" + strTargetType + "]\t";
    strResult = strResult + "strListenType is [" + strListenType + "]\t";
    strResult = strResult + "IsSameRecognitionWithObserved is [" + IsSameRecognitionWithObserved + "]\t";
    strResult = strResult + "strGeneralType is [" + strGeneralType + "]\t";
    strResult = strResult + "strSpecificType is [" + strSpecificType + "]\t";
    strResult = strResult + "strFormMistakes is [" + strFormMistakes + "]\t";
    strResult = strResult + "strFormDistance is [" + strFormDistance + "]\t";
    return strResult;

  }

  public String toString4print() {
    String strResult = "";
    strResult = strResult + "intID is [" + intID + "]\t";
    strResult = strResult + "intStudentID is [" + intStudentID + "]\t";
    strResult = strResult + "strLesson is [" + strLesson + "]\t";
    strResult = strResult + "strQuestion is [" + strQuestion + "]\t";
    strResult = strResult + "strTargetWord is [" + strTargetWord + "]\t";
    strResult = strResult + "strSpecificType is [" + strSpecificType + "]\t";
    return strResult;

  }

  public String toStringShort() {
    String strResult = "";
    // strResult = strResult + "intID is ["+intID+"]\t";
    strResult = strResult + "intStudentID is [" + intStudentID + "]\t";
    strResult = strResult + "strLesson is [" + strLesson + "]\t";
    strResult = strResult + "strTargetWord is [" + strTargetWord + "]\t";
    strResult = strResult + "strObservedWord is [" + strObservedWord + "]\t";
    strResult = strResult + "strRecognitionWord is [" + strRecognitionWord + "]\t";
    strResult = strResult + "strListenWord is [" + strListenWord + "]\t";
    return strResult;

  }

  public String toVALUES() {
    String strResult = "('";
    strResult = strResult + intStudentID + "','" + strLesson + "','" + strQuestion + "','" + strTargetWord + "','"
        + strObservedWord + "','" + strRecognitionWord + "','" + strListenWord + "','" + strTargetType + "',"
        + IsSameRecognitionWithObserved + "," + IsSameObservedWithTarget + "," + IsSameRecognitionWithListen + ","
        + IsSameListenWithTarget + "," + IsSameObservedWithListen + ",'" + strGeneralType + "','" + strSpecificType
        + "','" + strFormMistakes + "','" + strFormDistance + "')";
    // + ")";
    return strResult;
  }

  public String toVALUES_Listen() {
    String strResult = "('";
    strResult = strResult + intStudentID + "','" + strLesson + "','" + strQuestion + "','" + strTargetWord + "','"
        + strListenWord + "','" + strTargetType + "','" + strSpecificType + "','" + strFormMistakes + "','"
        + strFormDistance + "')";

    return strResult;
  }

  public String toVALUES_ListenRecogntion() {
    String strResult = "('";
    strResult = strResult + intStudentID + "','" + strLesson + "','" + strQuestion + "','" + strTargetWord + "','"
        + strRecognitionWord + "','" + strListenWord + "','" + strTargetType + "','" + strSpecificType + "','"
        + strFormMistakes + "','" + strFormDistance + "')";

    return strResult;
  }

  public String toVALUES_Observed() {
    String strResult = "('";
    strResult = strResult + intStudentID + "','" + strLesson + "','" + strQuestion + "','" + strTargetWord + "','"
        + strObservedWord + "','" + strTargetType + "','" + strSpecificType + "','" + strFormMistakes + "','"
        + strFormDistance + "')";

    return strResult;
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

  public boolean isSameListenWithTarget() {
    return IsSameListenWithTarget;
  }

  public void setSameListenWithTarget(boolean isSameListenWithTarget) {
    IsSameListenWithTarget = isSameListenWithTarget;
  }

  public boolean isSameObservedWithListen() {
    return IsSameObservedWithListen;
  }

  public void setSameObservedWithListen(boolean isSameObservedWithListen) {
    IsSameObservedWithListen = isSameObservedWithListen;
  }

  public boolean isSameObservedWithTarget() {
    return IsSameObservedWithTarget;
  }

  public void setSameObservedWithTarget(boolean isSameObservedWithTarget) {
    IsSameObservedWithTarget = isSameObservedWithTarget;
  }

  public boolean isSameRecognitionWithListen() {
    return IsSameRecognitionWithListen;
  }

  public void setSameRecognitionWithListen(boolean isSameRecognitionWithListen) {
    IsSameRecognitionWithListen = isSameRecognitionWithListen;
  }

  public boolean isSameRecognitionWithObserved() {
    return IsSameRecognitionWithObserved;
  }

  public void setSameRecognitionWithObserved(boolean isSameRecognitionWithObserved) {
    IsSameRecognitionWithObserved = isSameRecognitionWithObserved;
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

  public String getStrListenType() {
    return strListenType;
  }

  public void setStrListenType(String strListenType) {
    this.strListenType = strListenType;
  }

  public String getStrListenWord() {
    return strListenWord;
  }

  public void setStrListenWord(String strListenWord) {
    this.strListenWord = strListenWord;
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

  public String getStrQuestion() {
    return strQuestion;
  }

  public void setStrQuestion(String strQuestion) {
    this.strQuestion = strQuestion;
  }

  public String getStrRecognitionWord() {
    return strRecognitionWord;
  }

  public void setStrRecognitionWord(String strRecognitionWord) {
    this.strRecognitionWord = strRecognitionWord;
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

  /*
   * (non-Javadoc) for HashTable
   */
  public boolean equals(Object anObject) {

    // need check three input modes

    if (anObject instanceof WordDataMeta) {

      WordDataMeta wdm = (WordDataMeta) (anObject);
      String strT = wdm.getStrTargetWord();
      String strO = wdm.getStrObservedWord();
      if (strT.equals(this.strTargetWord) && strO.equals(this.strObservedWord)) {
        return true;
      }

    }

    return false;
  }

  /*
   * The same data, different studentID;
   */
  public boolean equalData(Object anObject) {

    // need check three input modes

    if (anObject instanceof WordDataMeta) {

      WordDataMeta wdm = (WordDataMeta) (anObject);
      int studentID = wdm.intStudentID;
      String strT = wdm.getStrTargetWord();
      String strO = wdm.getStrObservedWord();
      if (strT.equals(this.strTargetWord) && strO.equals(this.strObservedWord) && studentID != this.intStudentID) {
        return true;
      }

    }

    return false;
  }

}
