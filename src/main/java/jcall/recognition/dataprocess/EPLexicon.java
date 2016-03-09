/**
 * Created on 2007/06/05
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.util.Vector;

public class EPLexicon {
  Vector<EPWord> vecWordLevelWord;

  public EPLexicon() {
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

  public void add2VecWordLevelWord(EPWord epw) {
    vecWordLevelWord.addElement(epw);
  }
}
