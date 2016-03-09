/**
 * Created on 2008/09/02
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.analysis;

public class SenPerplexityStruct {

  int ISentenceNum; // how many sentences in db
  int ITotalWordNum;// how many total sentences in db
  // int[] ISentenceAvePerp;
  int[] sentenceWordNum;
  int[] sentenceTotolPerp;

  final int sentenceMaxNum = 1000;

  // final int wordMaxNum = 7000;

  public SenPerplexityStruct() {
    ISentenceNum = 0;
    ITotalWordNum = 0;
    sentenceWordNum = new int[sentenceMaxNum];
    sentenceTotolPerp = new int[sentenceMaxNum];
  }

  // public double

  public int getISentenceNum() {
    return ISentenceNum;
  }

  public void setISentenceNum(int sentenceNum) {
    ISentenceNum = sentenceNum;
  }

  public int getITotalWordNum() {
    return ITotalWordNum;
  }

  public void setITotalWordNum(int totalWordNum) {
    ITotalWordNum = totalWordNum;
  }

  public int getSentenceMaxNum() {
    return sentenceMaxNum;
  }

  public int[] getSentenceTotolPerp() {
    return sentenceTotolPerp;
  }

  public void setSentenceTotolPerp(int[] sentenceTotolPerp) {
    this.sentenceTotolPerp = sentenceTotolPerp;
  }

  public int[] getSentenceWordNum() {
    return sentenceWordNum;
  }

  public void setSentenceWordNum(int[] sentenceWordNum) {
    this.sentenceWordNum = sentenceWordNum;
  }

  public String toString() {

    String str = "";
    str = str + " ISentenceNum: " + ISentenceNum + "\n";
    str = str + " ITotalWordNum: " + ITotalWordNum + "\n";
    int total = 0;

    // System.out.println("sentenceTotolPerp array size: "+
    // sentenceTotolPerp.length); //1000 = initial value

    for (int i = 0; i < sentenceTotolPerp.length; i++) {

      int tper = sentenceTotolPerp[i];
      total = total + tper;
    }

    str = str + "all sentenceTotolPerp: " + total + "\n";

    str = str + "new perplexity: " + (double) total / ITotalWordNum + "\n";

    double Oldper = 0.0;
    if (sentenceTotolPerp.length == sentenceWordNum.length) {
      for (int i = 0; i < sentenceTotolPerp.length; i++) {

        int tper = sentenceTotolPerp[i];
        int swnum = sentenceWordNum[i];
        if (swnum != 0 && tper != 0) {
          double oneSenOldper = (double) tper / sentenceWordNum[i];
          System.out.println("[" + i + "], one sentenceTotolPerp: " + sentenceTotolPerp[i] + " sentenceWordNum: "
              + sentenceWordNum[i]);
          Oldper = Oldper + oneSenOldper;
        }
      }
    }

    str = str + "old perplexity: " + Oldper + "\n";

    return str;
  }

}
