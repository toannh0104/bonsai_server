/**
 * Created on 2007/06/11
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.PrintWriter;

public class SentenceStatisticStructure {

  static final String[] NOUN_PREDICTTYPE = { "VDG", "VDOUT", "VOTHER", "INVS_WFORM", "INVS_PCE", "INVOUT", "INVDG_PCE",
      "INVDG_WFORM", "OmitShort", "AddShort", "OmitLong", "AddLong", "OmitVoiced", "AddVoiced", "VDGALT", "VDGFAMILY",
      "VDGCOVER", "VDGPIC" };// 8+6+4
  static final String[] VERB_PREDICTTYPE = { "VS_DFORM", "VDG_SFORM", "VDG_DFORM", "VDOUT", "VOTHER", "INVS_REF",
      "INVDOUT", "INVDG_REF" };// 8
  static final String[] PARTICAL_PREDICTTYPE = { "VDG", "VDOUT" };// 2
  static final String[] DIGIT_PREDICTTYPE = { "QUANTITY", "QUANTITY2", "VDG", "INVDG_PCE", "INVS_PCE", "VDOUT_WNUM",
      "INVOUT", "OmitShort", "AddShort", "OmitLong", "AddLong", "OmitVoiced", "AddVoiced" };// 7+6

  // add word number,total
  int[] noun;
  int[] verb;
  int[] particle;
  int[] digit;
  int total;
  // error type number ;
  int[] nounErr;
  int[] verbErr;
  int[] particleErr;
  int[] digitErr;
  int totalErr;

  public SentenceStatisticStructure() {
    init();
  }

  private void init() {
    noun = new int[18];
    verb = new int[8];
    particle = new int[2];
    digit = new int[13];
    total = 0;
    nounErr = new int[12];
    verbErr = new int[8];
    particleErr = new int[4];
    digitErr = new int[7];
  }

  public void writeout(PrintWriter pw) {
    pw.println("NOUN");
    for (int i = 0; i < NOUN_PREDICTTYPE.length; i++) {
      pw.println(NOUN_PREDICTTYPE[i] + "-" + nounErr[i] + "-" + this.noun[i]);
    }
    pw.println("VERB");
    for (int i = 0; i < VERB_PREDICTTYPE.length; i++) {
      pw.println(VERB_PREDICTTYPE[i] + "-" + verbErr[i] + "-" + this.verb[i]);
    }
    pw.println("COUNTER");
    for (int i = 0; i < DIGIT_PREDICTTYPE.length; i++) {
      pw.println(DIGIT_PREDICTTYPE[i] + "-" + digitErr[i] + "-" + this.digit[i]);
    }
    pw.println("PARTICLE");
    for (int i = 0; i < PARTICAL_PREDICTTYPE.length; i++) {
      pw.println(PARTICAL_PREDICTTYPE[i] + "-" + particleErr[i] + "-" + this.particle[i]);
    }
  }

  public void writePredictNum(PrintWriter pw) {
    pw.println();// total word is 2429
    pw.println("#NOUN");// 965
    for (int i = 0; i < NOUN_PREDICTTYPE.length; i++) {
      pw.println(NOUN_PREDICTTYPE[i] + "-" + this.noun[i]);
    }
    pw.println("#VERB");// 475
    for (int i = 0; i < VERB_PREDICTTYPE.length; i++) {
      pw.println(VERB_PREDICTTYPE[i] + "-" + this.verb[i]);
    }
    pw.println("#COUNTER");// 80
    for (int i = 0; i < DIGIT_PREDICTTYPE.length; i++) {
      pw.println(DIGIT_PREDICTTYPE[i] + "-" + this.digit[i]);
    }
    pw.println("#PARTICLE");// 909
    for (int i = 0; i < PARTICAL_PREDICTTYPE.length; i++) {
      pw.println(PARTICAL_PREDICTTYPE[i] + "-" + this.particle[i]);
    }
  }

  public static String[] getDIGIT_PREDICTTYPE() {
    return DIGIT_PREDICTTYPE;
  }

  public static String[] getNOUN_PREDICTTYPE() {
    return NOUN_PREDICTTYPE;
  }

  public static String[] getPARTICAL_PREDICTTYPE() {
    return PARTICAL_PREDICTTYPE;
  }

  public static String[] getVERB_PREDICTTYPE() {
    return VERB_PREDICTTYPE;
  }

  public int[] getDigit() {
    return digit;
  }

  public void setDigit(int[] digit) {
    this.digit = digit;
  }

  public void addDigit(String errType, int number) {
    for (int i = 0; i < DIGIT_PREDICTTYPE.length; i++) {
      if (errType.equalsIgnoreCase(DIGIT_PREDICTTYPE[i])) {
        this.digit[i] += number;
      }
    }
  }

  public int[] getDigitErr() {
    return digitErr;
  }

  public void setDigitErr(int[] digitErr) {
    this.digitErr = digitErr;
  }

  public void addDigitErr(int index, int number) {
    this.digit[index] += number;
  }

  public int[] getNoun() {
    return noun;
  }

  public void setNoun(int[] noun) {
    this.noun = noun;
  }

  public void addNoun(String errType, int number) {
    for (int i = 0; i < NOUN_PREDICTTYPE.length; i++) {
      if (errType.equalsIgnoreCase(NOUN_PREDICTTYPE[i])) {
        this.noun[i] += number;
      }
    }
  }

  public int[] getNounErr() {
    return nounErr;
  }

  public void setNounErr(int[] nounErr) {
    this.nounErr = nounErr;
  }

  public void addNounErr(int index, int number) {
    this.nounErr[index] += number;
  }

  public int[] getParticle() {
    return particle;
  }

  public void setParticle(int[] particle) {
    this.particle = particle;
  }

  public void addParticle(String errType, int number) {
    for (int i = 0; i < PARTICAL_PREDICTTYPE.length; i++) {
      if (errType.equalsIgnoreCase(PARTICAL_PREDICTTYPE[i])) {
        this.particle[i] += number;
      }
    }
  }

  public int[] getParticleErr() {
    return particleErr;
  }

  public void setParticleErr(int[] particleErr) {
    this.particleErr = particleErr;
  }

  public void addParticleErr(int index, int number) {
    this.particleErr[index] += number;

  }

  public int getTotal() {
    return total;
  }

  public int computeTotal() {
    int intTotal = 0;
    for (int i = 0; i < digit.length; i++) {
      intTotal += digit[i];
    }
    for (int i = 0; i < noun.length; i++) {
      intTotal += noun[i];
    }
    for (int i = 0; i < particle.length; i++) {
      intTotal += particle[i];
    }
    for (int i = 0; i < verb.length; i++) {
      intTotal += verb[i];
    }
    return intTotal;

  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getTotalErr() {
    return totalErr;
  }

  public void setTotalErr(int totalErr) {
    this.totalErr = totalErr;
  }

  public int[] getVerb() {
    return verb;
  }

  public void setVerb(int[] verb) {
    this.verb = verb;
  }

  public void addVerb(String errType, int number) {
    for (int i = 0; i < VERB_PREDICTTYPE.length; i++) {
      if (errType.equalsIgnoreCase(VERB_PREDICTTYPE[i])) {
        this.verb[i] += number;
      }
    }
  }

  public int[] getVerbErr() {
    return verbErr;
  }

  public void setVerbErr(int[] verbErr) {
    this.verbErr = verbErr;
  }

  public void addVerbErr(int index, int number) {
    this.verb[index] += number;

  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
