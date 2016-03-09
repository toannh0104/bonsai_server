/**
 * Created on 2008/04/23
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall;

public class CALL_Form {

  // Sign: The verb sign. If -1, use all signs.
  // Tense: The verb tense. If -1, use all tenses.
  // Style: The verb politeness. If -1, use all styles.
  // Question: Whether a question or not. If -1, use both options
  int sign; // positive negative
  int tense; // past non_past
  int style; // plain polite
  int type; // question or statement

  public CALL_Form() {

  }

  public CALL_Form(int sign, int tense, int style, int type) {
    super();
    this.sign = sign;
    this.tense = tense;
    this.style = style;
    this.type = type;
  }

  public CALL_Form(CALL_formStruct formstruct) {

    if (formstruct.positive && formstruct.negative) {
      sign = -1;
    } else if (formstruct.positive) {
      sign = CALL_formStruct.POSITIVE;
    } else {
      sign = CALL_formStruct.NEGATIVE;
    }

    if (formstruct.present && formstruct.past) {
      tense = -1;
    } else if (formstruct.present) {
      tense = CALL_formStruct.PRESENT;
    } else {
      tense = CALL_formStruct.PAST;
    }

    if (formstruct.polite && formstruct.plain) {
      style = -1;
    } else if (formstruct.plain) {
      style = CALL_formStruct.PLAIN;
    } else {
      style = CALL_formStruct.POLITE;
    }

    if (formstruct.question && formstruct.statement) {
      type = -1;
    } else if (formstruct.question) {
      type = CALL_formStruct.QUESTION;
    } else {
      type = CALL_formStruct.STATEMENT;
    }

  }

  public int getSign() {
    return sign;
  }

  public void setSign(int sign) {
    this.sign = sign;
  }

  public int getStyle() {
    return style;
  }

  public void setStyle(int style) {
    this.style = style;
  }

  public int getTense() {
    return tense;
  }

  public void setTense(int tense) {
    this.tense = tense;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

}
