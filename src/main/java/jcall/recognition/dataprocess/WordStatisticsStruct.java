/**
 * Created on 2007/06/17
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.PrintWriter;
import java.util.Vector;

import org.apache.log4j.Logger;

public class WordStatisticsStruct {

  static Logger logger = Logger.getLogger(WordStatisticsStruct.class.getName());
  static Vector<WordStatisticsDataMeta> vecWord; // all words that ever appears
                                                 // in the logs

  public WordStatisticsStruct() {
    init();
  }

  private void init() {

    vecWord = new Vector<WordStatisticsDataMeta>();
  }

  public Vector<WordStatisticsDataMeta> getVecWord() {
    return vecWord;
  }

  public void setVecWord(Vector<WordStatisticsDataMeta> vecWord) {
    WordStatisticsStruct.vecWord = vecWord;
  }

  public void addToVecWord(WordStatisticsDataMeta wsdm) {
    WordStatisticsStruct.vecWord.addElement(wsdm);
  }

  public void addWord(LexiconWordMeta lwm) {
    logger.info("enter addWord");
    WordStatisticsDataMeta wsdm;
    int intTime;
    for (int i = 0; i < vecWord.size(); i++) {
      // LexiconWordMeta lwmTemp = vecWord.elementAt(i);
      LexiconWordMeta lwmTemp = vecWord.elementAt(i);
      if (lwmTemp.compareWith(lwm) == 0) {
        // already exist this word
        wsdm = vecWord.elementAt(i);
        intTime = wsdm.getIntOccureceTime();
        wsdm.setIntOccureceTime(intTime + 1);
        logger.info("already exist: " + lwm.getStrKana() + " now time: " + (intTime + 1));
        return;
      } else {
        logger.debug("[" + lwm.getStrKana() + "] is not equal with [" + lwmTemp.getStrKana() + "]");
      }
    }
    // no found add new element
    logger.info("not exist,add new Word: " + lwm.getStrKana());
    wsdm = new WordStatisticsDataMeta(1);
    wsdm.id = lwm.id;
    wsdm.ComponentID = lwm.ComponentID;
    wsdm.intLevel = lwm.intLevel;
    wsdm.intType = lwm.intType;
    wsdm.strAltkana = lwm.strAltkana;
    wsdm.strAttribute = lwm.strAttribute;
    wsdm.strCategory1 = lwm.strCategory1;
    wsdm.strCategory2 = lwm.strCategory2;
    wsdm.strKana = lwm.strKana;
    wsdm.strKanji = lwm.strKanji;
    wsdm.strMainMeaning = lwm.strMainMeaning;
    wsdm.strOtherMeaning = lwm.strOtherMeaning;
    wsdm.strPicture = lwm.strPicture;
    WordStatisticsStruct.vecWord.addElement(wsdm);
    logger.info("Now all elements of WordStatisticsStruct: " + WordStatisticsStruct.vecWord.size());

  }

  public void write(PrintWriter outP) {
    for (int i = 0; i < vecWord.size(); i++) {
      WordStatisticsDataMeta wsdm = (WordStatisticsDataMeta) vecWord.elementAt(i);
      wsdm.write(outP);
    }
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
