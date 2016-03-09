/**
 * Created on 2007/06/05
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.util.Vector;

/*
 * this class is also not used now
 */
public class EPGrammar {

  Vector vecSentenceLevelModelSwap; // now haven not be used; will be used in the future

  Vector<EPWord> vecWordLevelWord;

  public EPGrammar() {
    init();
  }

  private void init() {
    vecWordLevelWord = new Vector<EPWord>();
  }

  public Vector getVecWordLevelWord() {
    return vecWordLevelWord;
  }

  public void setVecWordLevelWord(Vector vecWordLevelWord) {
    this.vecWordLevelWord = vecWordLevelWord;

  }

  public void removeFromVecWordLevelWord(EPWord epw) {
    vecWordLevelWord.remove(epw);
  }

  public void add2VecWordLevelWord(EPWord epw) {
    vecWordLevelWord.addElement(epw);
  }

}
