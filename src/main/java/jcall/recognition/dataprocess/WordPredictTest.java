/**
 * Created on 2007/06/18
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.IOException;
import java.util.Vector;

import org.apache.log4j.Logger;

public class WordPredictTest {

  static Logger logger = Logger.getLogger(PredictionProcess.class.getName());

  LexiconProcess lprocess;

  public WordPredictTest() throws IOException {
    super();
    lprocess = new LexiconProcess();
    // TODO Auto-generated constructor stub
  }

  public int getVerbError_VS_DFORM(LexiconWordMeta lwm, int lesson, SentenceStatisticStructure sss) throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    logger.debug("enter getVerbError_VS_DFORM; word [" + lwm.getStrKana() + "]");
    int changeSizeDFORM = 0;
    String word = lwm.getStrKana();
    if (lwm != null) {
      Vector vecDForm = lprocess.getVerbFormOfLessonDefNew(lwm, lesson, "kana");
      if (vecDForm != null && vecDForm.size() > 0) {
        changeSizeDFORM = vecDForm.size() - 1;
        logger.debug("VS_DFORM is : " + changeSizeDFORM + " ");
        sss.addVerb("VS_DFORM", changeSizeDFORM);
      }
    }

    return changeSizeDFORM;
  }

  /*
   * quantity1 = number type quantity2= number+Quantity wrong type, if it should
   * be special transform format return null,when no such type error; or else
   * return responding wrong words;
   */
  public static String getNQNUM(LexiconWordMeta lwm) {
    // Vector<String> vecResult = new Vector<String>();
    logger.debug("enter getNQNUM");
    if (lwm != null) { // it is a num+quantifier type;
      String strNumber = lwm.getStrCategory2();
      logger.debug("return " + strNumber);
      return strNumber;
    }
    return null;
  }

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    // TODO Auto-generated method stub
    WordPredictTest wpt = new WordPredictTest();
    /*
     * LexiconWordMeta lwm = new LexiconWordMeta(); lwm.setStrKana("�����҂�");
     * lwm.setStrKanji("��C") ; lwm.setStrCategory1("��");
     * lwm.setStrCategory2("�C"); wpt.getNQNUM(lwm);
     */
    SentenceStatisticStructure sss = new SentenceStatisticStructure();
    LexiconWordMeta lwm = new LexiconWordMeta();
    lwm.setStrKana("�����");
    lwm.setStrKanji("�����");
    lwm.setStrAttribute("Group2");
    wpt.getVerbError_VS_DFORM(lwm, 22, sss);

  }

}
