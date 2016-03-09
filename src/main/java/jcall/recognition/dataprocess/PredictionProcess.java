/**
 * Created on 2007/06/12
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import jcall.recognition.util.CharacterUtil;

import org.apache.log4j.Logger;

public class PredictionProcess {

  static Logger logger = Logger.getLogger(PredictionProcess.class.getName());

  static final String DOUBLECONSONENT = CharacterUtil.STR_DOUBLECONSONENT;

  String NQRULEFILE = "./Data/QuantiferRules.xml";
  String PREDICTIONFILE = "./Data/LessonErrorPrediction.txt";
  // String LEXICONFILE = "./Data/newlexicon.txt";

  public static LexiconProcess lprocess;

  public ErrorPredictionParser epp;
  public QuantifierRuleParser qrp;
  // public LexiconParser lp;
  public static QuantifierRule qr;
  public static ErrorPredictionStruct eps;

  // static Lexicon l;
  static int intTotal;

  // static int intTotal ;
  public static final String[] VOICEDROW = { "��", "��", "��", "��", "��" };

  public PredictionProcess() throws IOException {
    init();

  }

  private void init() throws IOException {
    lprocess = new LexiconProcess();

    epp = new ErrorPredictionParser();
    qrp = new QuantifierRuleParser();
    // lp = new LexiconParser();
    qr = qrp.loadFromFile(NQRULEFILE);
    eps = epp.loadDataFromFile(PREDICTIONFILE);
    // l = lp.loadDataFromFile(LEXICONFILE) ;
  }

  public int getNounErrorNumber(LexiconWordMeta lwm, int lesson, SentenceStatisticStructure sss) throws IOException {
    logger.debug("enter getNounErrorNumber; word [" + lwm.strKana + "]");
    intTotal = 0; // original total number of all types errors
    // Vector<String> vecResult= new Vector();
    int uniqueTotal = 0;
    String strWord = lwm.getStrKana();
    Set<String> hs = new HashSet<String>();
    hs.add(strWord); // first add the original kana word, for prevent the
                     // accepted prediction words is equal to strWord;
    PredictionDataMeta pdm;
    String str = "";
    Vector vec;
    // INVS_PCE
    vec = getNounPCE(lwm, true, false, true, true, true, true, sss);
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        str = (String) vec.get(i);
        str = str.trim();
        if (!hs.contains(str)) {
          hs.add(str);
        }
      }
      intTotal += vec.size();
      sss.addNoun("INVS_PCE", vec.size());
      logger.debug("INVS_PCE is [" + vec.size() + "]");
    } else {
      logger.debug("Has not INVS_PCE ");
    }
    // VDG
    pdm = getNounSubsGroup(lwm, lesson);
    if (pdm != null && pdm.strWord.length() > 0) {
      vec = pdm.getVecAltWord();
      if (vec != null && vec.size() > 0) {
        for (int i = 0; i < vec.size(); i++) {
          str = (String) vec.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
          }
        }
        sss.addNoun("VDGALT", vec.size());
      }
      vec = pdm.getVecHonorificWord();
      if (vec != null && vec.size() > 0) {
        for (int i = 0; i < vec.size(); i++) {
          str = (String) vec.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
          }
        }
        sss.addNoun("VDGALT", vec.size());
      }

      vec = pdm.getVecHonorificWord();
      if (vec != null && vec.size() > 0) {
        for (int i = 0; i < vec.size(); i++) {
          str = (String) vec.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
          }
        }
        sss.addNoun("VDGALT", vec.size());
      }

      vec = pdm.getVecSubWord();
      if (vec != null && vec.size() > 0) {
        for (int i = 0; i < vec.size(); i++) {
          str = (String) vec.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
          }
        }
        String strTemp = pdm.getStrRestriction();
        if (strTemp != null && strTemp.length() > 0) {
          if (strTemp.equalsIgnoreCase("coverage")) {
            sss.addNoun("VDGCOVER", vec.size());
          } else if (strTemp.equalsIgnoreCase("picture")) {
            sss.addNoun("VDGPIC", vec.size());
          } else if (strTemp.equalsIgnoreCase("family")) {
            sss.addNoun("VDGFAMILY", vec.size());
          }
        }
      }

      // INVS_WFORM
      intTotal += pdm.confusionCount;
      // separte the number;
      Vector vecinvswform = pdm.getVecWrongHonorificWord();
      if (vecinvswform != null && vecinvswform.size() > 0) {
        sss.addNoun("INVS_WFORM", vecinvswform.size());
        // sss.addNoun("VDG", pdm.confusionCount-vecinvswform.size());

      }
      logger.debug("VDG + INVS_WFORM is [" + pdm.confusionCount + "]");

      // INVDG_PCE+INVDG_WFORM
      Vector vecInv = getINVDG_PCEWFORM4Noun(pdm, lesson, sss);
      if (vecInv != null && vecInv.size() > 0) {
        intTotal += vecInv.size();
        System.out.println("INVDG_PCE+INVDG_WFORM is [" + vecInv.size() + "]");
        for (int i = 0; i < vecInv.size(); i++) {
          str = (String) vecInv.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
          }
        }
      }
    }
    // repeated words could exist in the (strWord+VDG)(INVS_PCE +
    // INVS_WFORM)(INVDG_PCE+INVDG_WFORM)
    // INVS_PCE could come out correct word like "obaasaN"->"obasaN"
    // like above errors how could we divide its a Valid sustituation word or an
    // Invalid PCE errors(the standard????)

    uniqueTotal = hs.size() - 1; // subtract the correct original word;
    System.out.println("all is [" + uniqueTotal + "]");

    return uniqueTotal;
  }

  public Vector getNounError(LexiconWordMeta lwm, int lesson) throws IOException {
    logger.debug("enter getNounErrorNumber; word [" + lwm.strKana + "]");
    intTotal = 0; // original total number of all types errors
    Vector<String> vecResult = new Vector();
    int uniqueTotal = 0;
    String strWord = lwm.getStrKana();
    Set<String> hs = new HashSet<String>();
    hs.add(strWord); // first add the original kana word, for prevent the
                     // accepted prediction words is equal to strWord;
    vecResult.addElement(strWord);

    // first add the original kana word, for prevent the accepted prediction
    // words is equal to strWord;

    PredictionDataMeta pdm;
    String str = "";
    Vector vec;
    // INVS_PCE
    vec = getNounPCE(lwm, true, false, true, true, true, true);
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        str = (String) vec.get(i);
        str = str.trim();
        if (!hs.contains(str)) {
          hs.add(str);
          vecResult.addElement(str);
        }
      }
      intTotal += vec.size();
      // sss.addNoun("INVS_PCE", vec.size());
      logger.debug("INVS_PCE is [" + vec.size() + "]");
    } else {
      logger.debug("Has not INVS_PCE ");
    }
    // VDG
    pdm = getNounSubsGroup(lwm, lesson);
    if (pdm != null && pdm.strWord.length() > 0) {
      vec = pdm.getVecAltWord();
      if (vec != null && vec.size() > 0) {
        for (int i = 0; i < vec.size(); i++) {
          str = (String) vec.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
            vecResult.addElement(str);
          }
        }
        // sss.addNoun("VDGALT", vec.size());
      }

      vec = pdm.getVecHonorificWord();
      if (vec != null && vec.size() > 0) {
        for (int i = 0; i < vec.size(); i++) {
          str = (String) vec.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
            vecResult.addElement(str);
          }
        }
        // sss.addNoun("VDGALT", vec.size());
      }

      vec = pdm.getVecSubWord();
      if (vec != null && vec.size() > 0) {
        for (int i = 0; i < vec.size(); i++) {
          str = (String) vec.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
            vecResult.addElement(str);
          }
        }

      }

      // INVS_WFORM
      intTotal += pdm.confusionCount;
      // separte the number;
      Vector vecinvswform = pdm.getVecWrongHonorificWord();
      if (vecinvswform != null && vecinvswform.size() > 0) {
        for (int i = 0; i < vecinvswform.size(); i++) {
          str = (String) vecinvswform.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
            vecResult.addElement(str);
          }

        }

      }
      logger.debug("VDG + INVS_WFORM is [" + pdm.confusionCount + "]");

      // INVDG_PCE+INVDG_WFORM
      Vector vecInv = getINVDG_PCEWFORM4Noun(pdm, lesson);
      if (vecInv != null && vecInv.size() > 0) {

        intTotal += vecInv.size();
        logger.debug("INVDG_PCE+INVDG_WFORM is [" + vecInv.size() + "]");
        for (int i = 0; i < vecInv.size(); i++) {
          str = (String) vecInv.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
            vecResult.addElement(str);
          }
        }
      }
    }

    // confirm contained the altKana
    // String strAltkana = lwm.getStrAltkana();
    // if(strAltkana!=null && strAltkana.length()>0){
    // logger.info("has strAltkana: "+ strAltkana);
    // if(!hs.contains(strAltkana)){
    // logger.debug("no strAltkana, add : "+ strAltkana);
    // hs.add(strAltkana);
    // vecResult.addElement(strAltkana);
    // }
    // }
    //
    // repeated words could exist in the (strWord+VDG)(INVS_PCE +
    // INVS_WFORM)(INVDG_PCE+INVDG_WFORM)
    // INVS_PCE could come out correct word like "obaasaN"->"obasaN"
    // like above errors how could we divide its a Valid sustituation word or an
    // Invalid PCE errors(the standard????)

    uniqueTotal = hs.size() - 1; // subtract the correct original word;
    logger.debug("all is [" + uniqueTotal + "]");

    // return (String[]) hs.toArray();
    return vecResult;
  }

  public Vector getNounErrors(LexiconWordMeta lwm, int lesson) throws IOException {
    logger.debug("enter getNounErrorNumber; word [" + lwm.strKana + "]");
    intTotal = 0; // original total number of all types errors
    Vector<EPLeaf> vecResult = new Vector();
    int uniqueTotal = 0;
    String strWord = lwm.getStrKana();
    Set<String> hs = new HashSet<String>();
    hs.add(strWord); // first add the original kana word, for prevent the
                     // accepted prediction words is equal to strWord;
    EPLeaf epl = null;
    epl = new EPLeaf();
    epl.setId(lwm.getId());
    epl.setIntType(lwm.getIntType());
    epl.setStrErrorType("TW");
    epl.setStrKana(lwm.getStrKana());
    epl.setStrKanji(lwm.getStrKanji());
    vecResult.addElement(epl);
    // vecResult.addElement(strWord);

    // first add the original kana word, for prevent the accepted prediction
    // words is equal to strWord;

    PredictionDataMeta pdm;
    String str = "";
    Vector vec;
    // INVS_PCE
    vec = getNounPCE(lwm, true, false, true, true, true, true);
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        str = (String) vec.get(i);
        str = str.trim();
        if (!hs.contains(str)) {
          hs.add(str);
          epl = new EPLeaf();
          epl.setId(lwm.getId());
          epl.setIntType(lwm.getIntType());
          epl.setStrErrorType("INVS_PCE");
          epl.setStrKana(str);
          epl.setStrKanji(str);
          vecResult.addElement(epl);
          // vecResult.addElement(str);
        }
      }
      intTotal += vec.size();
      // sss.addNoun("INVS_PCE", vec.size());
      logger.debug("INVS_PCE is [" + vec.size() + "]");
    } else {
      logger.debug("Has not INVS_PCE ");
    }
    // VDG
    pdm = getNounSubsGroup(lwm, lesson);
    if (pdm != null && pdm.strWord.length() > 0) {
      vec = pdm.getVecAltWord();
      if (vec != null && vec.size() > 0) {
        for (int i = 0; i < vec.size(); i++) {
          str = (String) vec.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
            epl = new EPLeaf();
            epl.setId(lwm.getId());
            epl.setIntType(lwm.getIntType());
            epl.setStrErrorType("VDGALT");
            epl.setStrKana(str);
            epl.setStrKanji(str);
            vecResult.addElement(epl);
            // vecResult.addElement(str);
          }
        }
        // sss.addNoun("VDGALT", vec.size());
      }

      vec = pdm.getVecHonorificWord();
      if (vec != null && vec.size() > 0) {
        for (int i = 0; i < vec.size(); i++) {
          str = (String) vec.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
            epl = new EPLeaf();
            epl.setId(lwm.getId());
            epl.setIntType(lwm.getIntType());
            epl.setStrErrorType("VDGALT");
            epl.setStrKana(str);
            epl.setStrKanji(str);
            vecResult.addElement(epl);
            // vecResult.addElement(str);
          }
        }
        // sss.addNoun("VDGALT", vec.size());
      }

      vec = pdm.getVecSubWord();
      if (vec != null && vec.size() > 0) {
        for (int i = 0; i < vec.size(); i++) {
          str = (String) vec.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
            epl = new EPLeaf();
            epl.setId(lwm.getId());
            epl.setIntType(lwm.getIntType());
            epl.setStrErrorType("VDG");
            epl.setStrKana(str);
            epl.setStrKanji(str);
            vecResult.addElement(epl);
            // vecResult.addElement(str);
          }
        }

      }

      // INVS_WFORM
      intTotal += pdm.confusionCount;
      // separte the number;
      Vector vecinvswform = pdm.getVecWrongHonorificWord();
      if (vecinvswform != null && vecinvswform.size() > 0) {
        for (int i = 0; i < vecinvswform.size(); i++) {
          str = (String) vecinvswform.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
            epl = new EPLeaf();
            epl.setId(lwm.getId());
            epl.setIntType(lwm.getIntType());
            epl.setStrErrorType("INVS_WFORM");
            epl.setStrKana(str);
            epl.setStrKanji(str);
            vecResult.addElement(epl);
            // vecResult.addElement(str);
          }

        }

      }
      logger.debug("VDG + INVS_WFORM is [" + pdm.confusionCount + "]");

      // INVDG_PCE+INVDG_WFORM
      Vector vecInv = getINVDG_PCEWFORM4Nouns(pdm, lesson);
      if (vecInv != null && vecInv.size() > 0) {
        intTotal += vecInv.size();
        logger.debug("INVDG_PCE+INVDG_WFORM is [" + vecInv.size() + "]");
        for (int i = 0; i < vecInv.size(); i++) {
          epl = (EPLeaf) vecInv.get(i);
          str = epl.getStrKana().trim();
          if (!hs.contains(str)) {
            hs.add(str);
            vecResult.addElement(epl);
          }
        }
      }
    }

    // confirm contained the altKana
    // String strAltkana = lwm.getStrAltkana();
    // if(strAltkana!=null && strAltkana.length()>0){
    // logger.info("has strAltkana: "+ strAltkana);
    // if(!hs.contains(strAltkana)){
    // logger.debug("no strAltkana, add : "+ strAltkana);
    // hs.add(strAltkana);
    // vecResult.addElement(strAltkana);
    // }
    // }
    //
    // repeated words could exist in the (strWord+VDG)(INVS_PCE +
    // INVS_WFORM)(INVDG_PCE+INVDG_WFORM)
    // INVS_PCE could come out correct word like "obaasaN"->"obasaN"
    // like above errors how could we divide its a Valid sustituation word or an
    // Invalid PCE errors(the standard????)

    uniqueTotal = hs.size() - 1; // subtract the correct original word;
    logger.debug("all is [" + uniqueTotal + "]");

    // return (String[]) hs.toArray();
    return vecResult;
  }

  private Vector getINVDG_PCEWFORM4Noun(PredictionDataMeta pdm, int lesson, SentenceStatisticStructure sss)
      throws IOException {
    logger.debug("enter getINVDG_PCEWFORM4Noun");
    Vector<String> vecResult = new Vector<String>();
    int intCount = 0;
    Vector vec;
    vec = pdm.getVecAltWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        // INVDG_PCE
        LexiconWordMeta lwm = lprocess.getWordNoun(str);
        if (lwm != null) {
          Vector vecPCE = getNounPCE(lwm, true, false, true, true, true, true);
          if (vecPCE != null && vecPCE.size() > 0) {
            for (int j = 0; j < vecPCE.size(); j++) {
              vecResult.addElement((String) vecPCE.get(j));
            }
            intCount += vecPCE.size();
            sss.addNoun("INVDG_PCE", vecPCE.size());
          }
          // INVDG_WFORM
          PredictionDataMeta pdmTemp = getNounSubsGroup(lwm, lesson);
          if (pdmTemp != null && pdmTemp.strWord.length() > 0) {
            Vector vecINVDG = pdm.getVecWrongHonorificWord();
            if (vecINVDG != null && vecINVDG.size() > 0) {
              for (int k = 0; k < vecINVDG.size(); k++) {
                vecResult.addElement((String) vecINVDG.get(k));
              }
              intCount += vecINVDG.size();
              sss.addNoun("INVDG_WFORM", vecINVDG.size());
            }
          }
        }
      }
    }

    vec = pdm.getVecSubWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        LexiconWordMeta lwm = lprocess.getWordNoun(str);
        if (lwm != null) {
          Vector vecPCE = getNounPCE(lwm, true, false, true, true, true, true);
          if (vecPCE != null && vecPCE.size() > 0) {
            for (int j = 0; j < vecPCE.size(); j++) {
              vecResult.addElement((String) vecPCE.get(j));
            }
            intCount += vecPCE.size();
            sss.addNoun("INVDG_PCE", vecPCE.size());
          }
          // INVDG_WFORM
          PredictionDataMeta pdmTemp = getNounSubsGroup(lwm, lesson);
          if (pdmTemp != null && pdmTemp.strWord.length() > 0) {
            Vector vecINVDG = pdm.getVecWrongHonorificWord();
            if (vecINVDG != null && vec.size() > 0) {
              for (int k = 0; k < vecINVDG.size(); k++) {
                vecResult.addElement((String) vecINVDG.get(k));
              }
              intCount += vecINVDG.size();
              sss.addNoun("INVDG_WFORM", vecINVDG.size());
            }
          }
        }
      }
    }
    vec = pdm.getVecHonorificWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        LexiconWordMeta lwm = lprocess.getWordNoun(str);
        if (lwm != null) {
          Vector vecPCE = getNounPCE(lwm, true, false, true, true, true, true);
          if (vecPCE != null && vecPCE.size() > 0) {
            for (int j = 0; j < vecPCE.size(); j++) {
              vecResult.addElement((String) vecPCE.get(j));
            }
            intCount += vecPCE.size();
            sss.addNoun("INVDG_PCE", vecPCE.size());
          }
          // INVDG_WFORM
          PredictionDataMeta pdmTemp = getNounSubsGroup(lwm, lesson);
          if (pdmTemp != null && pdmTemp.strWord.length() > 0) {
            Vector vecINVDG = pdm.getVecWrongHonorificWord();
            if (vecINVDG != null && vec.size() > 0) {
              for (int k = 0; k < vecINVDG.size(); k++) {
                vecResult.addElement((String) vecINVDG.get(k));
                sss.addNoun("INVDG_WFORM", vecINVDG.size());
              }
              intCount += vecINVDG.size();
            }
          }
        }

      }
    }
    return vecResult;
  }

  private Vector getINVDG_PCEWFORM4Noun(PredictionDataMeta pdm, int lesson) throws IOException {
    logger.debug("enter getINVDG_PCEWFORM4Noun");
    Vector<String> vecResult = new Vector<String>();
    int intCount = 0;
    Vector vec;
    vec = pdm.getVecAltWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        // INVDG_PCE
        LexiconWordMeta lwm = lprocess.getWordNoun(str);
        if (lwm != null) {
          Vector vecPCE = getNounPCE(lwm, true, false, true, true, true, true);
          if (vecPCE != null && vecPCE.size() > 0) {
            for (int j = 0; j < vecPCE.size(); j++) {
              vecResult.addElement((String) vecPCE.get(j));
            }
            intCount += vecPCE.size();
            // sss.addNoun("INVDG_PCE", vecPCE.size());
          }
          // INVDG_WFORM
          PredictionDataMeta pdmTemp = getNounSubsGroup(lwm, lesson);
          if (pdmTemp != null && pdmTemp.strWord.length() > 0) {
            Vector vecINVDG = pdm.getVecWrongHonorificWord();
            if (vecINVDG != null && vecINVDG.size() > 0) {
              for (int k = 0; k < vecINVDG.size(); k++) {
                vecResult.addElement((String) vecINVDG.get(k));
              }
              intCount += vecINVDG.size();
              // sss.addNoun("INVDG_WFORM", vecINVDG.size());
            }
          }
        }
      }
    }

    vec = pdm.getVecSubWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        LexiconWordMeta lwm = lprocess.getWordNoun(str);
        if (lwm != null) {
          Vector vecPCE = getNounPCE(lwm, true, false, true, true, true, true);
          if (vecPCE != null && vecPCE.size() > 0) {
            for (int j = 0; j < vecPCE.size(); j++) {
              vecResult.addElement((String) vecPCE.get(j));
            }
            intCount += vecPCE.size();
            // sss.addNoun("INVDG_PCE", vecPCE.size());
          }
          // INVDG_WFORM
          PredictionDataMeta pdmTemp = getNounSubsGroup(lwm, lesson);
          if (pdmTemp != null && pdmTemp.strWord.length() > 0) {
            Vector vecINVDG = pdm.getVecWrongHonorificWord();
            if (vecINVDG != null && vec.size() > 0) {
              for (int k = 0; k < vecINVDG.size(); k++) {
                vecResult.addElement((String) vecINVDG.get(k));
              }
              intCount += vecINVDG.size();
              // sss.addNoun("INVDG_WFORM", vecINVDG.size());
            }
          }
        }
      }
    }
    vec = pdm.getVecHonorificWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        LexiconWordMeta lwm = lprocess.getWordNoun(str);
        if (lwm != null) {
          Vector vecPCE = getNounPCE(lwm, true, false, true, true, true, true);
          if (vecPCE != null && vecPCE.size() > 0) {
            for (int j = 0; j < vecPCE.size(); j++) {
              vecResult.addElement((String) vecPCE.get(j));
            }
            intCount += vecPCE.size();
            // sss.addNoun("INVDG_PCE", vecPCE.size());
          }
          // INVDG_WFORM
          PredictionDataMeta pdmTemp = getNounSubsGroup(lwm, lesson);
          if (pdmTemp != null && pdmTemp.strWord.length() > 0) {
            Vector vecINVDG = pdm.getVecWrongHonorificWord();
            if (vecINVDG != null && vec.size() > 0) {
              for (int k = 0; k < vecINVDG.size(); k++) {
                vecResult.addElement((String) vecINVDG.get(k));
                // sss.addNoun("INVDG_WFORM", vecINVDG.size());
              }
              intCount += vecINVDG.size();
            }
          }
        }

      }
    }
    return vecResult;
  }

  /*
   * revised to EPLeaf
   */
  private Vector getINVDG_PCEWFORM4Nouns(PredictionDataMeta pdm, int lesson) throws IOException {
    logger.debug("enter getINVDG_PCEWFORM4Noun");
    Vector vecResult = new Vector();
    int intCount = 0;
    Vector vec;
    EPLeaf epl = null;
    vec = pdm.getVecAltWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        // INVDG_PCE
        LexiconWordMeta lwm = lprocess.getWordNoun(str);
        if (lwm != null) {
          Vector vecPCE = getNounPCE(lwm, true, false, true, true, true, true);
          if (vecPCE != null && vecPCE.size() > 0) {
            for (int j = 0; j < vecPCE.size(); j++) {
              // vecResult.addElement((String) vecPCE.get(j));
              String strTemp = (String) vecPCE.get(j);
              epl = new EPLeaf();
              epl.setId(lwm.getId());
              epl.setIntType(lwm.getIntType());
              epl.setStrErrorType("INVDG_PCE");
              epl.setStrKana(strTemp);
              epl.setStrKanji(strTemp);
              vecResult.addElement(epl);
            }
            intCount += vecPCE.size();
            // sss.addNoun("INVDG_PCE", vecPCE.size());
          }
          // INVDG_WFORM
          PredictionDataMeta pdmTemp = getNounSubsGroup(lwm, lesson);
          if (pdmTemp != null && pdmTemp.strWord.length() > 0) {
            Vector vecINVDG = pdm.getVecWrongHonorificWord();
            if (vecINVDG != null && vecINVDG.size() > 0) {
              for (int k = 0; k < vecINVDG.size(); k++) {
                // vecResult.addElement((String) vecINVDG.get(k));
                String strTemp = (String) vecINVDG.get(k);
                epl = new EPLeaf();
                epl.setId(lwm.getId());
                epl.setIntType(lwm.getIntType());
                epl.setStrErrorType("INVDG_WFORM");
                epl.setStrKana(strTemp);
                epl.setStrKanji(strTemp);
                vecResult.addElement(epl);
              }
              intCount += vecINVDG.size();
              // sss.addNoun("INVDG_WFORM", vecINVDG.size());
            }
          }
        }
      }
    }

    vec = pdm.getVecSubWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        LexiconWordMeta lwm = lprocess.getWordNoun(str);
        if (lwm != null) {
          Vector vecPCE = getNounPCE(lwm, true, false, true, true, true, true);
          if (vecPCE != null && vecPCE.size() > 0) {
            for (int j = 0; j < vecPCE.size(); j++) {
              // vecResult.addElement((String) vecPCE.get(j));
              String strTemp = (String) vecPCE.get(j);
              epl = new EPLeaf();
              epl.setId(lwm.getId());
              epl.setIntType(lwm.getIntType());
              epl.setStrErrorType("INVDG_PCE");
              epl.setStrKana(strTemp);
              epl.setStrKanji(strTemp);
              vecResult.addElement(epl);
            }
            intCount += vecPCE.size();
            // sss.addNoun("INVDG_PCE", vecPCE.size());
          }
          // INVDG_WFORM
          PredictionDataMeta pdmTemp = getNounSubsGroup(lwm, lesson);
          if (pdmTemp != null && pdmTemp.strWord.length() > 0) {
            Vector vecINVDG = pdm.getVecWrongHonorificWord();
            if (vecINVDG != null && vec.size() > 0) {
              for (int k = 0; k < vecINVDG.size(); k++) {
                // vecResult.addElement((String) vecINVDG.get(k));
                String strTemp = (String) vecINVDG.get(k);
                epl = new EPLeaf();
                epl.setId(lwm.getId());
                epl.setIntType(lwm.getIntType());
                epl.setStrErrorType("INVDG_WFORM");
                epl.setStrKana(strTemp);
                epl.setStrKanji(strTemp);
                vecResult.addElement(epl);
              }
              intCount += vecINVDG.size();
              // sss.addNoun("INVDG_WFORM", vecINVDG.size());
            }
          }
        }
      }
    }
    vec = pdm.getVecHonorificWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        LexiconWordMeta lwm = lprocess.getWordNoun(str);
        if (lwm != null) {
          Vector vecPCE = getNounPCE(lwm, true, false, true, true, true, true);
          if (vecPCE != null && vecPCE.size() > 0) {
            for (int j = 0; j < vecPCE.size(); j++) {
              // vecResult.addElement((String) vecPCE.get(j));
              String strTemp = (String) vecPCE.get(j);
              epl = new EPLeaf();
              epl.setId(lwm.getId());
              epl.setIntType(lwm.getIntType());
              epl.setStrErrorType("INVDG_PCE");
              epl.setStrKana(strTemp);
              epl.setStrKanji(strTemp);
              vecResult.addElement(epl);
            }
            intCount += vecPCE.size();
            // sss.addNoun("INVDG_PCE", vecPCE.size());
          }
          // INVDG_WFORM
          PredictionDataMeta pdmTemp = getNounSubsGroup(lwm, lesson);
          if (pdmTemp != null && pdmTemp.strWord.length() > 0) {
            Vector vecINVDG = pdm.getVecWrongHonorificWord();
            if (vecINVDG != null && vec.size() > 0) {
              for (int k = 0; k < vecINVDG.size(); k++) {
                // vecResult.addElement((String) vecINVDG.get(k));
                String strTemp = (String) vecINVDG.get(k);
                epl = new EPLeaf();
                epl.setId(lwm.getId());
                epl.setIntType(lwm.getIntType());
                epl.setStrErrorType("INVDG_WFORM");
                epl.setStrKana(strTemp);
                epl.setStrKanji(strTemp);
                vecResult.addElement(epl);
                // sss.addNoun("INVDG_WFORM", vecINVDG.size());
              }
              intCount += vecINVDG.size();
            }
          }
        }

      }
    }
    return vecResult;
  }

  public static PredictionDataMeta getNounSubsGroup(LexiconWordMeta lwm, int lesson) {
    logger.debug("enter getNounSubsGroup! " + lwm.getStrKana());
    PredictionDataMeta pdmResult = null;
    PredictionDataMeta pdm = null;
    String strSearchKana;
    String strWord = lwm.getStrKana();
    String strKanjiWord = lwm.strKanji;
    for (int i = 0; i < eps.vecLesson.size(); i++) {
      pdm = (PredictionDataMeta) eps.vecLesson.get(i);
      int intLesson = pdm.getLesson();
      if (intLesson == lesson) {// same lesson
        strSearchKana = pdm.getStrWord();
        if (strWord.equals(strSearchKana) || strSearchKana.equals(strKanjiWord)) {
          pdmResult = pdm;
          logger.debug("get one NounSubsGroup in lesson which kana is" + pdm.getStrWord());
          break;
        }
      }
    }// end of for
    if (pdmResult == null) {
      for (int i = 0; i < eps.vecGeneral.size(); i++) {
        pdm = (PredictionDataMeta) eps.vecGeneral.get(i);
        // no lesson feature
        strSearchKana = pdm.getStrWord();
        if (strWord.equals(strSearchKana) || strSearchKana.equals(strKanjiWord)) {
          pdmResult = pdm;
          logger.debug("get one NounSubsGroup in general which kana is" + strSearchKana + " all VDG is"
              + pdm.confusionCount);
          // Vector vec= pdm.getVecWrongHonorificWord();
          // if(vec!=null&&vec.size()>0){
          // System.out.println("contain wrong honorific word "+vec.size());
          // }

          break;
        }
      } // end of for
    }// end of if
    return pdmResult;
  }

  /**
   * @param strWord
   *          only noun-kana format, no NQ,no Verb,no Particle
   * @param OmitShort
   * @param AddShort
   *          is always false.
   * @param OmitLong
   * @param AddLong
   *          is detected,only the last character could add "��" OmitVoiced
   *          means g-k,d-t ,except quantifier; or (light weight continous
   *          words(like ������) ,but havent done this part
   * @param OmitVoiced
   * @param AddVoiced
   *          :is only checked by the first pronunciation is �� ,and confined to
   *          the japanese word(no english-based words)
   * @return
   * @throws IOException
   */

  public Vector getNounPCE(LexiconWordMeta lwm, boolean OmitShort, boolean AddShort, boolean OmitLong, boolean AddLong,
      boolean OmitVoiced, boolean AddVoiced) throws IOException {
    Vector<String> vecResult = new Vector<String>();
    String strWrongWord;
    // String strChar = null;
    Vector v;
    String strWord = lwm.getStrKana().trim();
    logger.debug("int getNounPCE; word [" + strWord + "]");

    if (lwm.getStrCategory1().equalsIgnoreCase("family") || lwm.getStrCategory2().equalsIgnoreCase("family")) {
      logger.debug("It is a family name, no PCE");
    } else {

      if (AddVoiced) {
        // logger.debug("AddVoiced "+AddVoiced);
        strWrongWord = getAddVoicedWord4Noun(lwm);
        if (strWrongWord != null && strWrongWord.length() > 0) {
          vecResult.addElement(strWrongWord);
          // sss.addNoun("AddVoiced", 1);
        } else {
          logger.debug("not prediction: AddVoiced ");
        }
      }
      if (AddLong) {
        // logger.debug("AddLong "+AddLong);
        strWrongWord = getAddLongWord(strWord);
        if (strWrongWord != null && strWrongWord.length() > 0) {
          logger.debug("AddLong: " + strWrongWord);
          vecResult.addElement(strWrongWord);
          // sss.addNoun("AddLong", 1);
        } else {
          logger.debug("not prediction: AddLong");
        }
      }

      if (OmitVoiced) {// no care about the char position
        v = getOmitVoicedWords4Noun(strWord);
        if (v != null && v.size() > 0) {
          for (int i = 0; i < v.size(); i++) {
            vecResult.addElement((String) v.elementAt(i));
            // sss.addNoun("OmitVoiced", 1);
            // logger.debug("OmitVoiced: "+(String) v.elementAt(i) );
          }
          // System.out.println("OmitVoiced "+strWrongWord);
        } else {
          logger.debug("not prediction: OmitVoiced");
        }
      }
      if (OmitLong) {// happens from the second position
        v = getOmitLongWords(strWord);
        if (v != null && v.size() > 0) {
          for (int i = 0; i < v.size(); i++) {
            vecResult.addElement((String) v.elementAt(i));
            // sss.addNoun("OmitLong", 1);
            logger.debug("OmitLong: " + (String) v.elementAt(i));

          }
        } else {
          logger.debug("not prediction: OmitLong");
        }
      }
      if (OmitShort) {
        v = getOmitDoubleConsonentWords(strWord);
        if (v != null && v.size() > 0) {
          for (int i = 0; i < v.size(); i++) {
            vecResult.addElement((String) v.elementAt(i));
            // sss.addNoun("OmitShort", 1);
            logger.debug("OmitShort: " + (String) v.elementAt(i));
          }
        } else {
          logger.debug("not prediction: OmitShort");
        }
      }
    }
    return vecResult;

  }

  public Vector getNounPCE(LexiconWordMeta lwm, boolean OmitShort, boolean AddShort, boolean OmitLong, boolean AddLong,
      boolean OmitVoiced, boolean AddVoiced, SentenceStatisticStructure sss) throws IOException {
    Vector<String> vecResult = new Vector<String>();

    String strWrongWord;
    // String strChar = null;
    Vector v;
    String strWord = lwm.getStrKana().trim();
    logger.debug("int getNounPCE; word [" + strWord + "]");
    if (lwm.getStrCategory1().equalsIgnoreCase("family") || lwm.getStrCategory2().equalsIgnoreCase("family")) {
      logger.debug("It is a family name, no PCE");
    } else {

      if (AddVoiced) {
        // logger.debug("AddVoiced "+AddVoiced);
        strWrongWord = getAddVoicedWord4Noun(lwm);
        if (strWrongWord != null && strWrongWord.length() > 0) {
          vecResult.addElement(strWrongWord);
          sss.addNoun("AddVoiced", 1);
        } else {
          logger.debug("not prediction: AddVoiced ");
        }
      }
      if (AddLong) {
        // logger.debug("AddLong "+AddLong);
        strWrongWord = getAddLongWord(strWord);
        if (strWrongWord != null && strWrongWord.length() > 0) {
          logger.debug("AddLong: " + strWrongWord);
          vecResult.addElement(strWrongWord);
          sss.addNoun("AddLong", 1);
        } else {
          logger.debug("not prediction: AddLong");
        }
      }

      if (OmitVoiced) {// no care about the char position
        v = getOmitVoicedWords4Noun(strWord);
        if (v != null && v.size() > 0) {
          for (int i = 0; i < v.size(); i++) {
            vecResult.addElement((String) v.elementAt(i));
            sss.addNoun("OmitVoiced", 1);
            logger.debug("OmitVoiced: " + (String) v.elementAt(i));
          }
          // System.out.println("OmitVoiced "+strWrongWord);
        } else {
          logger.debug("not prediction: OmitVoiced");
        }
      }
      if (OmitLong) {// happens from the second position
        v = getOmitLongWords(strWord);
        if (v != null && v.size() > 0) {
          for (int i = 0; i < v.size(); i++) {
            vecResult.addElement((String) v.elementAt(i));
            sss.addNoun("OmitLong", 1);
            logger.debug("OmitLong: " + (String) v.elementAt(i));

          }
        } else {
          logger.debug("not prediction: OmitLong");
        }
      }
      if (OmitShort) {
        v = getOmitDoubleConsonentWords(strWord);
        if (v != null && v.size() > 0) {
          for (int i = 0; i < v.size(); i++) {
            vecResult.addElement((String) v.elementAt(i));
            sss.addNoun("OmitShort", 1);
            logger.debug("OmitShort: " + (String) v.elementAt(i));
          }
        } else {
          logger.debug("not prediction: OmitShort");
        }
      }
    }
    return vecResult;

  }

  public Vector getVerbError(LexiconWordMeta lwm, int lesson, boolean booTense, boolean booNegative) throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    intTotal = 0;
    // to prevent repetiveness
    Set<String> hs = new HashSet<String>();
    String strWord = lwm.getStrKana();
    // hs.add(strWord); //first add the original kana word, for prevent the
    // accepted prediction words is equal to strWord;
    // first add the original kana word, for prevent the accepted prediction
    // words is equal to strWord;
    Vector vec;
    String str;
    int intNum = 0;
    vec = getVerbError_VS_DFORM(lwm, lesson); // includes all different form of
                                              // it;
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        str = (String) vec.get(i);
        str = str.trim();
        if (!hs.contains(str)) {
          hs.add(str);
        }
      }
      intTotal += intNum;
      // System.out.println(intNum);
    }

    vec = getVerbError_VDG_AllForms(lwm, lesson, "both");
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        str = (String) vec.get(i);
        str = str.trim();
        if (!hs.contains(str)) {
          hs.add(str);
        }
      }
      intTotal += intNum;
      // System.out.println(intNum);
    }

    vec = getVerbError_INVS_REF(lwm, lesson, booTense, booNegative);

    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        str = (String) vec.get(i);
        str = str.trim();
        if (!hs.contains(str)) {
          hs.add(str);
        }
      }
      intTotal += intNum;
      // System.out.println(intNum);
    }

    vec = getVerbError_INVDG_REF(lwm, lesson, "both", booTense, booNegative);
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        str = (String) vec.get(i);
        str = str.trim();
        if (!hs.contains(str)) {
          hs.add(str);
        }
      }
      intTotal += intNum;
      // System.out.println(intNum);
    }
    Vector vResult = new Vector();
    Iterator<String> it = hs.iterator();
    while (it.hasNext()) {
      vResult.addElement(it.next());
    }
    return vResult;

    // return (String[])hs.toArray();
  }

  /*
   * for CALL_sentenceGrammar class
   */
  public Vector getVerbErrors(LexiconWordMeta lwm, int lesson, boolean booTense, boolean booNegative)
      throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    logger.debug("enter getVerbErrors");
    intTotal = 0;
    Vector<EPLeaf> vecResult = new Vector();
    // to prevent repetiveness
    Set<String> hs = new HashSet<String>();
    String strWord = lwm.getStrKana();
    // hs.add(strWord); //first add the original kana word, for prevent the
    // accepted prediction words is equal to strWord;
    // first add the original kana word, for prevent the accepted prediction
    // words is equal to strWord;
    Vector vec;
    String str;
    EPLeaf epl;
    int intNum = 0;
    vec = getVerbErrors_VS_DFORM(lwm, lesson); // includes all different form of
                                               // it;
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        str = (String) vec.get(i);
        str = str.trim();
        if (!hs.contains(str)) {
          hs.add(str);
          epl = new EPLeaf();
          epl.setId(lwm.getId());
          epl.setIntType(lwm.getIntType());
          epl.setStrErrorType("VS_DFORM");
          epl.setStrKana(str);
          epl.setStrKanji(str); // need to revise, here kanji is kana;
          vecResult.addElement(epl);
        }
      }
      intTotal += intNum;
      // System.out.println(intNum);
    }

    vec = getVerbError_VDG_AllForms(lwm, lesson, "both");
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        str = (String) vec.get(i);
        str = str.trim();
        if (!hs.contains(str)) {
          hs.add(str);
          epl = new EPLeaf();
          epl.setId(lwm.getId());
          epl.setIntType(lwm.getIntType());
          epl.setStrErrorType("VDG");
          epl.setStrKana(str);
          epl.setStrKanji(str); // need to revise, here kanji is kana;
          vecResult.addElement(epl);
        }
      }
      intTotal += intNum;
      // System.out.println(intNum);
    }

    vec = getVerbError_INVS_REF(lwm, lesson, booTense, booNegative);

    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        str = (String) vec.get(i);
        str = str.trim();
        if (!hs.contains(str)) {
          hs.add(str);
          epl = new EPLeaf();
          epl.setId(lwm.getId());
          epl.setIntType(lwm.getIntType());
          epl.setStrErrorType("INVS_REF");
          epl.setStrKana(str);
          epl.setStrKanji(str); // need to revise, here kanji is kana;
          vecResult.addElement(epl);
        }
      }
      intTotal += intNum;
      // System.out.println(intNum);
    }

    vec = getVerbError_INVDG_REF(lwm, lesson, "both", booTense, booNegative);
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        str = (String) vec.get(i);
        str = str.trim();
        if (!hs.contains(str)) {
          hs.add(str);
          epl = new EPLeaf();
          epl.setId(lwm.getId());
          epl.setIntType(lwm.getIntType());
          epl.setStrErrorType("INVDG_REF");
          epl.setStrKana(str);
          epl.setStrKanji(str); // need to revise, here kanji is kana;
          vecResult.addElement(epl);
        }
      }
      intTotal += intNum;
      // System.out.println(intNum);
    }

    // Vector vResult=new Vector();
    // Iterator<String> it = hs.iterator();
    // while(it.hasNext()){
    // vResult.addElement(it.next());
    // }

    return vecResult;

  }

  public int getVerbErrorNumber(LexiconWordMeta lwm, int lesson, SentenceStatisticStructure sss) throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    // System.out.println("int getVerbErrorNumber; word ["+strWord+"]");
    intTotal = 0;
    // int changeSizeDFORM = 0;
    // int allForm = 1;
    // int changeSizeREF = 0;
    // PredictionDataMeta pdm;
    // int a[] = new int[];
    // VS_DFORM
    int intNum = 0;
    intNum = getVerbError_VS_DFORM(lwm, lesson, sss);
    intTotal += intNum;
    System.out.println(intNum);

    intNum = getVerbError_VDG_SFORM(lwm, lesson, sss, "both");
    System.out.println(intNum);
    intTotal += intNum;

    intNum = getVerbError_VDG_DFORM(lwm, lesson, sss, "both");
    System.out.println(intNum);
    intTotal += intNum;

    intNum = getVerbError_INVS_REF(lwm, lesson, sss);
    System.out.println(intNum);
    intTotal += intNum;

    intNum = getVerbError_INVDG_REF(lwm, lesson, sss, "both");
    System.out.println(intNum);
    intTotal += intNum;

    return intTotal;
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
        sss.addVerb("VS_DFORM", changeSizeDFORM);
      }
    }

    return changeSizeDFORM;
  }

  public Vector getVerbError_VS_DFORM(LexiconWordMeta lwm, int lesson) throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    logger.debug("enter getVerbError_VS_DFORM; word [" + lwm.getStrKana() + "]");
    int changeSizeDFORM = 0;
    String word = lwm.getStrKana();
    if (lwm != null) {
      Vector vecDForm = lprocess.getVerbFormOfLessonDefNew(lwm, lesson, "kana");
      if (vecDForm != null && vecDForm.size() > 0) {
        return vecDForm;
      }
    }

    return null;
  }

  /*
   * 
   * for CALL_sentenceGrammar class
   */
  public Vector getVerbErrors_VS_DFORM(LexiconWordMeta lwm, int lesson) throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    logger.debug("enter getVerbError_VS_DFORM; word [" + lwm.getStrKana() + "]");
    int changeSizeDFORM = 0;
    String word = lwm.getStrKana();
    if (lwm != null) {
      Vector vecDForm = lprocess.getVerbFormOfLessonDefNew(lwm, lesson, "kana");
      if (vecDForm != null && vecDForm.size() > 0) {
        return vecDForm;
      }
    }

    return null;
  }

  public int getVerbError_VDG_SFORM(LexiconWordMeta lwm, int lesson, SentenceStatisticStructure sss, String strC)
      throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    logger.debug("enter getVerbError_VDG_SFORM; word [" + lwm.getStrKana() + "]");
    PredictionDataMeta pdm;
    String strWord = lwm.getStrKana();

    pdm = getVerbSubsGroup(strWord, lesson, strC);
    if (pdm != null && pdm.strWord.length() > 0) {
      // VDG_SFORM
      sss.addVerb("VDG_SFORM", pdm.confusionCount);
      return pdm.confusionCount;
    } else {
      return 0;
    }
  }

  public Vector getVerbError_VDG(LexiconWordMeta lwm, int lesson, String strC) throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    logger.debug("enter getVerbError_VDG; word [" + lwm.getStrKana() + "]");
    PredictionDataMeta pdm;
    Vector<String> vResult = null;
    String strWord = lwm.getStrKana();

    pdm = getVerbSubsGroup(strWord, lesson, strC);
    if (pdm != null && pdm.strWord.length() > 0) {
      // VDG_SFORM
      Vector alt = pdm.getVecAltWord();
      if (alt != null & alt.size() > 0) {
        for (int i = 0; i < alt.size(); i++) {
          if (vResult == null) {
            vResult = new Vector();
          }
          vResult.addElement((String) alt.elementAt(i));

        }
      }
      alt = pdm.getVecSubWord();
      if (alt != null & alt.size() > 0) {
        for (int i = 0; i < alt.size(); i++) {
          if (vResult == null) {
            vResult = new Vector();
          }
          vResult.addElement((String) alt.elementAt(i));
        }
      }
    }
    return vResult;
  }

  public Vector getVerbError_VDG_AllForms(LexiconWordMeta lwm, int lesson, String strC) throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    logger.debug("enter getVerbError_VDG_SFORM; word [" + lwm.getStrKana() + "]");
    PredictionDataMeta pdm;
    Vector<String> vecResult = new Vector();
    Vector vVDG = getVerbError_VDG(lwm, lesson, strC);
    if (vVDG != null && vVDG.size() > 0) {
      for (int i = 0; i < vVDG.size(); i++) {
        String strWord = (String) vVDG.get(i);
        LexiconWordMeta lwmSubs = lprocess.getLexiconWordVerb(strWord, lesson);
        if (lwmSubs != null && lwmSubs.strKana.length() > 0) {
          Vector vecTemp = getVerbError_VS_DFORM(lwmSubs, lesson);
          if (vecTemp != null && vecTemp.size() > 0) {
            for (int j = 0; j < vecTemp.size(); j++) {
              vecResult.addElement((String) vecTemp.elementAt(j));
            }
          }
        } else {
          System.out.println("wrong, word [" + lwm.getStrKana() + "], its VDG: " + strWord
              + " can not find in the lexicon");
        }
      }

    }
    return vecResult;
  }

  public int getVerbError_VDG_DFORM(LexiconWordMeta lwm, int lesson, SentenceStatisticStructure sss, String strC)
      throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    logger.debug("enter getVerbError_VDG_SFORM; word [" + lwm.getStrKana() + "]");
    PredictionDataMeta pdm;
    int intResult = 0;
    int changeSizeDFORM = 0;
    String strWord = lwm.getStrKana();
    System.out.println("int getVerbErrorNumber; word [" + lwm.getStrKana() + "]");
    if (lwm != null) {
      Vector vecDForm = lprocess.getVerbFormOfLessonDefNew(lwm, lesson, "kana");
      if (vecDForm != null && vecDForm.size() > 0) {
        changeSizeDFORM = vecDForm.size() - 1;
      }
      pdm = getVerbSubsGroup(strWord, lesson, strC);
      if (pdm != null && pdm.strWord.length() > 0) {
        // VDG_DFORM
        intResult = (pdm.confusionCount) * changeSizeDFORM;

      }
    }
    sss.addVerb("VDG_DFORM", intResult);
    return intResult;
  }

  public Vector getVerbError_INVS_REF(LexiconWordMeta lwm, int lesson, boolean booTense, boolean booNegative)
      throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    logger.debug("enter getVerbError_INVS_REF; word [" + lwm.getStrKanji() + "]");
    Vector v = lprocess.getErrVerbFormOfLessonDefNew(lwm, lesson, "kana", booTense, booNegative);
    return v;

  }

  public Vector getVerbError_INVDG_REF(LexiconWordMeta lwm, int lesson, String strC, boolean booTense,
      boolean booNegative) throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    logger.debug("enter getVerbError_INVDG_REF; word []");
    Vector v = getVerbError_VDG(lwm, lesson, strC);
    Vector vecResult = new Vector();
    if (v != null && v.size() > 0) {
      for (int i = 0; i < v.size(); i++) {
        String strWord = (String) v.get(i);
        LexiconWordMeta lwmSubs = lprocess.getLexiconWordVerb(strWord, lesson);
        if (lwmSubs != null && lwmSubs.strKana.length() > 0) {
          Vector vecTemp = lprocess.getErrVerbFormOfLessonDefNew(lwmSubs, lesson, "kana", booTense, booNegative);
          if (vecTemp != null && vecTemp.size() > 0) {
            for (int j = 0; j < vecTemp.size(); j++) {
              vecResult.addElement(vecTemp.elementAt(j));
            }
          }
        } else {
          System.out.println("wrong, word [" + lwm.getStrKana() + "], its VDG: " + strWord
              + " can not find in the lexicon");
        }
      }
    }
    return vecResult;
  }

  public int getVerbError_INVS_REF(LexiconWordMeta lwm, int lesson, SentenceStatisticStructure sss) throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    logger.debug("enter getVerbErrorNumber; word [" + lwm.getStrKana() + "]");
    // PredictionDataMeta pdm;
    int intResult = 0;
    int changeSizeREF = 0;
    PredictionDataMeta pdgVerb = lprocess.getVerbPDM(lesson);
    if (pdgVerb != null) {
      // INVS_REF
      Vector vec = pdgVerb.getVecVerbInvalidFrom();
      if (vec != null && vec.size() > 0) {
        // deal with different kinds of errors of these
        for (int i = 0; i < vec.size(); i++) {
          String str = (String) vec.get(i);

        }
        intResult = vec.size();
        sss.addVerb("INVS_REF", intResult);
      }
    }
    return intResult;
  }

  public int getVerbError_INVDG_REF(LexiconWordMeta lwm, int lesson, SentenceStatisticStructure sss, String strC)
      throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    logger.debug("enter getVerbErrorNumber; word [" + lwm.getStrKana() + "]");
    // PredictionDataMeta pdm;
    int intResult = 0;
    int changeSizeREF = 0;
    PredictionDataMeta pdm = null;
    String strWord = lwm.getStrKana();
    pdm = getVerbSubsGroup(strWord, lesson, strC);
    if (pdm != null && pdm.strWord.length() > 0) {
      // INVS_REF
      PredictionDataMeta pdgVerb = lprocess.getVerbPDM(lesson);
      if (pdgVerb != null) {
        // INVS_REF
        Vector vec = pdgVerb.getVecVerbInvalidFrom();
        if (vec != null && vec.size() > 0) {
          changeSizeREF = vec.size();

          // INVDG_REF
          intResult = (pdm.confusionCount) * changeSizeREF;
        }
        sss.addVerb("INVDG_REF", intResult);
      }
    }
    return intResult;
  }

  /**
   * @param strWord
   *          the target form of one general Verb. no Particle,no NQ,no Noun;
   * @param lesson
   * @return SubstitutionGroup = Aternative word() + Confusion word();these are
   *         stored in an PredictionDataMeta
   * @return the PredictionDataMeta of strWord is the the form of kana and kanji
   */
  public static PredictionDataMeta getVerbSubsGroupLesson(String strWord, int lesson) {
    // PredictionDataMeta pdmResult = null;
    PredictionDataMeta pdm = null;
    for (int i = 0; i < eps.vecLesson.size(); i++) {
      pdm = (PredictionDataMeta) eps.vecLesson.get(i);
      int intLesson = pdm.getLesson();
      if (intLesson == lesson && pdm.getType() == 2) {// same lesson and is a
                                                      // verb
        if (strWord.equals(pdm.getStrWord())) {
          return pdm;
        }
      }
    }
    return null;
  }

  public static PredictionDataMeta getVerbSubsGroupOther(String strWord, int lesson) {
    // PredictionDataMeta pdmResult = null;
    logger.debug(" enter getVerbSubsGroupOther: " + strWord);
    PredictionDataMeta pdm = null;
    for (int i = 0; i < eps.vecGeneral.size(); i++) {
      pdm = (PredictionDataMeta) eps.vecGeneral.get(i);
      logger.debug("vecGeneral[" + i + "]  : " + pdm.getStrWord());
      // int intLesson = pdm.getLesson();
      if (pdm.getType() == 2) {// same lesson and is a verb
        if (strWord.equals(pdm.getStrWord())) {
          logger.debug("findIn one : " + pdm.getStrKanji());
          return pdm;
        }
      }
    }
    return null;
  }

  public static PredictionDataMeta getVerbSubsGroup(String strWord, int lesson, String strCategory) {
    // PredictionDataMeta pdmResult = null;
    logger.debug("enter getVerbSubsGroup, type: " + strCategory + " lesson: " + lesson);
    PredictionDataMeta pdm = null;
    if (strCategory.equals("lesson")) {
      pdm = getVerbSubsGroupLesson(strWord, lesson);
    } else if (strCategory.equals("outoflesson")) {
      logger.debug("outoflesson: ");
      pdm = getVerbSubsGroupOther(strWord, lesson);
    } else if (strCategory.equals("both")) {
      logger.debug("both");
      pdm = getVerbSubsGroupLesson(strWord, lesson);
      if (pdm == null) {
        pdm = getVerbSubsGroupOther(strWord, lesson);
      }
    }
    return pdm;
  }

  public static int getVerbErrorNumber(LexiconWordMeta lwm, int lesson, SentenceStatisticStructure sss, String strC)
      throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    System.out.println("int getVerbErrorNumber; word [" + lwm.getStrKana() + "]");
    intTotal = 0;
    int changeSizeDFORM = 0;
    int allForm = 1;
    int changeSizeREF = 0;
    PredictionDataMeta pdm;
    String word = lwm.getStrKana();
    String strWord = lwm.getStrKana();
    if (lwm != null) {
      Vector vecDForm = lprocess.getVerbFormOfLessonDefNew(lwm, lesson, "kana");
      if (vecDForm != null && vecDForm.size() > 0) {
        changeSizeDFORM = vecDForm.size() - 1;
        intTotal += vecDForm.size() - 1;
        sss.addVerb("VS_DFORM", changeSizeDFORM);
        // System.out.println("VS_DFORM is ["+ changeSizeDFORM+"]");
        for (int i = 0; i < vecDForm.size(); i++) {
          logger.info("Lesson" + lesson + "�@" + lwm.getStrKana() + "[" + "VS_DFORM(" + i + ")]:�@" + vecDForm.get(i));
        }
      }
    } else {
      System.out.println("wrong, no such verb in the lexicon");
    }
    PredictionDataMeta pdgVerb = lprocess.getVerbPDM(lesson);
    if (pdgVerb != null) {
      // INVS_REF
      Vector vec = pdgVerb.getVecVerbInvalidFrom();
      if (vec != null && vec.size() > 0) {
        changeSizeREF = vec.size();
        intTotal += vec.size();
        sss.addVerb("INVS_REF", changeSizeREF);
        // System.out.println("INVS_REF is ["+ changeSizeREF+"]");
        for (int i = 0; i < vec.size(); i++) {
          logger.info("Lesson" + lesson + "�@" + lwm.getStrKana() + "[" + "INVS_REF(" + i + ")]:�@" + vec.get(i));
        }
      }

    } else {
      System.out.println("wrong in getVerbErrorNumber,lesson [" + lesson + "] do not have DFORM");
    }
    pdm = getVerbSubsGroup(strWord, lesson, strC);
    if (pdm != null && pdm.strWord.length() > 0) {
      // VDG_SFORM
      intTotal += pdm.confusionCount;
      sss.addVerb("VDG_SFORM", pdm.confusionCount);
      System.out.println("VDG_SFORM is [" + pdm.confusionCount + "]");
      // VDG_SFORM member .....???? need to consider the specific situation
      // VDG_DFORM
      intTotal += (pdm.confusionCount) * changeSizeDFORM;
      sss.addVerb("VDG_DFORM", (pdm.confusionCount) * changeSizeDFORM);
      System.out.println("VDG_SFORM is [" + (pdm.confusionCount) * changeSizeDFORM + "]");
      // INVDG_REF
      int num = (pdm.confusionCount) * changeSizeREF;
      intTotal += num;
      sss.addVerb("INVDG_REF", num);
      System.out.println("INVDG_REF is [" + num + "]");
    }

    System.out.println("all is [" + intTotal + "]");
    return intTotal;
  }

  public static int getParticleErrorNumber(LexiconWordMeta lwm, int lesson, SentenceStatisticStructure sss)
      throws IOException {
    String strWord = lwm.getStrKana();
    System.out.println("int getParticleErrorNumber; word [" + strWord + "]");
    // intTotal = 0 ;
    int intResult = 0;
    PredictionDataMeta pdm;
    // VDG
    pdm = getParticleSubsGroup(lwm, lesson);
    if (pdm != null && pdm.strWord.length() > 0) {
      intResult = pdm.confusionCount;
      sss.addParticle("VDG", pdm.confusionCount); // need to change;

      System.out.println("VDG is [" + pdm.confusionCount + "]");
    }
    System.out.println("all is [" + intTotal + "]");
    return intResult;
  }

  public static Vector getParticleError(LexiconWordMeta lwm, int lesson) throws IOException {
    String strWord = lwm.getStrKana();
    System.out.println("int getParticleError; word [" + strWord + "]");
    // intTotal = 0 ;
    Vector vResult = new Vector();
    PredictionDataMeta pdm;
    // VDG
    pdm = getParticleSubsGroup(lwm, lesson);
    Set<String> hs = new HashSet<String>();
    hs.add(strWord);
    vResult.addElement(strWord);

    if (pdm != null && pdm.strWord.length() > 0) {
      if (pdm != null && pdm.strWord.length() > 0) {
        // VDG_SFORM
        Vector alt = pdm.getVecAltWord();
        if (alt != null && alt.size() > 0) {
          for (int i = 0; i < alt.size(); i++) {
            if (!hs.contains((String) alt.elementAt(i))) {
              hs.add((String) alt.elementAt(i));
              vResult.addElement((String) alt.elementAt(i));
            }

          }
        }
        alt = pdm.getVecSubWord();
        if (alt != null && alt.size() > 0) {
          for (int i = 0; i < alt.size(); i++) {
            if (!hs.contains((String) alt.elementAt(i))) {
              hs.add((String) alt.elementAt(i));
              vResult.addElement((String) alt.elementAt(i));
            }
          }
        }
      }
    }
    // return (String[]) hs.toArray();
    return vResult;
  }

  public Vector getParticleErrors(LexiconWordMeta lwm, int lesson) throws IOException {
    String strWord = lwm.getStrKana();
    logger.debug("int getParticleErrors; word [" + strWord + "]");
    // intTotal = 0 ;
    Vector<EPLeaf> vecResult = new Vector();
    PredictionDataMeta pdm;
    EPLeaf epl = null;
    // VDG
    pdm = getParticleSubsGroup(lwm, lesson);
    Set<String> hs = new HashSet<String>();
    hs.add(strWord);
    epl = new EPLeaf();
    epl.setId(lwm.getId());
    epl.setIntType(lwm.getIntType());
    epl.setStrErrorType("TW");
    epl.setStrKana(lwm.getStrKana());
    epl.setStrKanji(lwm.getStrKanji());
    vecResult.addElement(epl);
    // vResult.addElement(strWord);

    if (pdm != null && pdm.strWord.length() > 0) {
      if (pdm != null && pdm.strWord.length() > 0) {
        // VDG_SFORM
        Vector alt = pdm.getVecAltWord();
        if (alt != null && alt.size() > 0) {
          for (int i = 0; i < alt.size(); i++) {
            if (!hs.contains((String) alt.elementAt(i))) {
              hs.add((String) alt.elementAt(i));
              String str = (String) alt.elementAt(i);
              epl = new EPLeaf();
              epl.setId(lwm.getId());
              epl.setIntType(lwm.getIntType());
              epl.setStrErrorType("VDG_SFORM");
              epl.setStrKana(str);
              epl.setStrKanji(str); // need to revise, here kanji is kana;
              vecResult.addElement(epl);
              // vResult.addElement((String) alt.elementAt(i));
            }

          }
        }
        alt = pdm.getVecSubWord();
        if (alt != null && alt.size() > 0) {
          for (int i = 0; i < alt.size(); i++) {
            if (!hs.contains((String) alt.elementAt(i))) {
              hs.add((String) alt.elementAt(i));
              String str = (String) alt.elementAt(i);
              epl = new EPLeaf();
              epl.setId(lwm.getId());
              epl.setIntType(lwm.getIntType());
              epl.setStrErrorType("VDG");
              epl.setStrKana(str);
              epl.setStrKanji(str); // need to revise, here kanji is kana;
              vecResult.addElement(epl);
              // vResult.addElement((String) alt.elementAt(i));
            }
          }
        }
      }
    }
    // return (String[]) hs.toArray();
    return vecResult;
  }

  /*
   * strWord is the target form of one general Particle. no verb,no NQ,no Noun;
   * SubstitutionGroup = Aternative word() + Confusion word(); these are stored
   * in an PredictionDataMeta
   */
  public static PredictionDataMeta getParticleSubsGroup(LexiconWordMeta lwm, int lesson) {
    String strWord = lwm.getStrKana();
    PredictionDataMeta pdmResult = null;
    PredictionDataMeta pdm = null;
    String strSearchKana;
    for (int i = 0; i < eps.vecLesson.size(); i++) {
      pdm = (PredictionDataMeta) eps.vecLesson.get(i);
      int intLesson = pdm.getLesson();
      if (intLesson == lesson) {// same lesson
        strSearchKana = pdm.getStrWord();
        if (strWord.equals(strSearchKana)) {
          pdmResult = pdm;
          break;
        }
      }
    }
    return pdmResult;
  }

  public Vector getNQError(LexiconWordMeta lwm, int lesson) throws IOException {
    // QUANTITY,QUANTITY2,INVS_PCE,INVDG_PCE
    logger.debug("int getNQError; word [" + lwm.getStrKana() + "]");
    intTotal = 0;
    Vector vResult = new Vector();
    Set<String> hs = new HashSet<String>();
    hs.add(lwm.getStrKana());
    vResult.addElement(lwm.getStrKana());

    PredictionDataMeta pdm;
    // INVS_PCE
    Vector vec = getNQPCE(lwm, true, false, false, true, true, true);
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        if (!hs.contains((String) vec.elementAt(i))) {
          hs.add((String) vec.elementAt(i));
          vResult.addElement((String) vec.elementAt(i));
        }
      }
      intTotal += vec.size();
      // sss.addDigit("INVS_PCE", vec.size());
      System.out.println("INVS_PCE is [" + vec.size() + "]");
    }

    // VDG
    Vector v = getNQSubsGroup(lwm, lesson);
    if (v != null && v.size() > 0) {
      for (int i = 0; i < v.size(); i++) {
        if (!hs.contains((String) v.elementAt(i))) {
          hs.add((String) v.elementAt(i));
          vResult.addElement((String) v.elementAt(i));
        }
      }

      intTotal += v.size();
      // sss.addDigit("VDG", v.size());
      System.out.println("VDG is [" + v.size() + "]");

      // INVDG_PCE
      Vector vVdg = getNQINVDG_PCE(v, lesson);
      for (int j = 0; j < vVdg.size(); j++) {
        if (!hs.contains((String) vVdg.elementAt(j))) {
          hs.add((String) vVdg.elementAt(j));
          vResult.addElement((String) vVdg.elementAt(j));
        }
      }

      intTotal += vVdg.size();
      // sss.addDigit("INVDG_PCE", num);
      System.out.println("INVDG_PCE is [" + vVdg.size() + "]");
    }

    // QUANTITY+QUANTITY2
    String Q1 = getNQNUM(lwm);
    if (Q1 != null && Q1.length() > 0) {
      if (!hs.contains(Q1)) {
        hs.add(Q1);
        vResult.addElement(Q1);
      }
      // sss.addDigit("QUANTITY", 1);

    }

    Vector vecQ = getNQ_NPlusQ(lwm);
    if (vecQ != null && vecQ.size() > 0) {
      for (int j = 0; j < vecQ.size(); j++) {
        if (!hs.contains((String) vecQ.elementAt(j))) {
          hs.add((String) vecQ.elementAt(j));
          vResult.addElement((String) vecQ.elementAt(j));
        }
      }
      intTotal += vecQ.size();
      // sss.addDigit("QUANTITY2", vecQ.size());
      System.out.println("QUANTITY+QUANTITY2 is [" + vecQ.size() + "]");
    }

    // not predict this
    // int diffnum = 9;
    // sss.addDigit("VDOUT_WNUM", diffnum);
    // intTotal += diffnum;

    System.out.println("all is [" + intTotal + "]");
    return vResult;
  }

  public Vector getNQErrors(LexiconWordMeta lwm, int lesson) throws IOException {
    // QUANTITY,QUANTITY2,INVS_PCE,INVDG_PCE
    logger.info("int getNQErrors; word [" + lwm.getStrKana() + "]");
    intTotal = 0;
    EPLeaf epl = null;
    Vector<EPLeaf> vecResult = new Vector();
    Set<String> hs = new HashSet<String>();
    hs.add(lwm.getStrKana());
    epl = new EPLeaf();
    epl.setId(lwm.getId());
    epl.setIntType(lwm.getIntType());
    epl.setStrErrorType("TW");
    epl.setStrKana(lwm.getStrKana());
    epl.setStrKanji(lwm.getStrKanji());
    vecResult.addElement(epl);

    PredictionDataMeta pdm;
    // INVS_PCE
    Vector vec = getNQPCE(lwm, true, false, false, true, true, true);
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        if (!hs.contains((String) vec.elementAt(i))) {
          hs.add((String) vec.elementAt(i));
          String str = (String) vec.elementAt(i);
          epl = new EPLeaf();
          epl.setId(lwm.getId());
          // epl.setIntType(lwm.getIntType());
          epl.setStrErrorType("INVS_PCE");
          epl.setStrKana(str);
          epl.setStrKanji(str); // need to revise, here kanji is kana;
          vecResult.addElement(epl);
          // vResult.addElement((String) vec.elementAt(i));
        }
      }
      intTotal += vec.size();
      // sss.addDigit("INVS_PCE", vec.size());
      logger.debug("INVS_PCE is [" + vec.size() + "]");
    }

    // VDG
    Vector v = getNQSubsGroup(lwm, lesson);
    if (v != null && v.size() > 0) {
      for (int i = 0; i < v.size(); i++) {
        if (!hs.contains((String) v.elementAt(i))) {
          hs.add((String) v.elementAt(i));
          String str = (String) v.elementAt(i);
          epl = new EPLeaf();
          epl.setId(lwm.getId());
          epl.setIntType(lwm.getIntType());
          epl.setStrErrorType("VDG");
          epl.setStrKana(str);
          epl.setStrKanji(str); // need to revise, here kanji is kana;
          vecResult.addElement(epl);
          // vResult.addElement((String) v.elementAt(i));
        }
      }

      intTotal += v.size();
      // sss.addDigit("VDG", v.size());
      logger.debug("VDG is [" + v.size() + "]");

      // INVDG_PCE
      Vector vVdg = getNQINVDG_PCE(v, lesson);
      for (int j = 0; j < vVdg.size(); j++) {
        if (!hs.contains((String) vVdg.elementAt(j))) {
          hs.add((String) vVdg.elementAt(j));
          String str = (String) vVdg.elementAt(j);
          epl = new EPLeaf();
          epl.setId(lwm.getId());
          epl.setIntType(lwm.getIntType());
          epl.setStrErrorType("INVDG_PCE");
          epl.setStrKana(str);
          epl.setStrKanji(str); // need to revise, here kanji is kana;
          vecResult.addElement(epl);

          // vResult.addElement((String) vVdg.elementAt(j));
        }
      }

      intTotal += vVdg.size();
      // sss.addDigit("INVDG_PCE", num);
      logger.debug("INVDG_PCE is [" + vVdg.size() + "]");
    }

    // QUANTITY+QUANTITY2
    String Q1 = getNQNUM(lwm);
    if (Q1 != null && Q1.length() > 0) {
      if (!hs.contains(Q1)) {
        hs.add(Q1);
        String str = Q1;
        epl = new EPLeaf();
        epl.setId(lwm.getId());
        epl.setIntType(lwm.getIntType());
        epl.setStrErrorType("QUANTITY");
        epl.setStrKana(str);
        epl.setStrKanji(str); // need to revise, here kanji is kana;
        vecResult.addElement(epl);
        // vResult.addElement(Q1);
      }
      // sss.addDigit("QUANTITY", 1);

    }

    Vector vecQ = getNQ_NPlusQ(lwm);
    if (vecQ != null && vecQ.size() > 0) {
      for (int j = 0; j < vecQ.size(); j++) {
        if (!hs.contains((String) vecQ.elementAt(j))) {
          hs.add((String) vecQ.elementAt(j));
          String str = (String) vecQ.elementAt(j);
          epl = new EPLeaf();
          epl.setId(lwm.getId());
          epl.setIntType(lwm.getIntType());
          epl.setStrErrorType("QUANTITY2");
          epl.setStrKana(str);
          epl.setStrKanji(str); // need to revise, here kanji is kana;
          vecResult.addElement(epl);
          // vResult.addElement((String) vecQ.elementAt(j));
        }
      }
      intTotal += vecQ.size();
      // sss.addDigit("QUANTITY2", vecQ.size());
      logger.debug("QUANTITY+QUANTITY2 is [" + vecQ.size() + "]");
    }

    // not predict this
    // int diffnum = 9;
    // sss.addDigit("VDOUT_WNUM", diffnum);
    // intTotal += diffnum;

    logger.debug("all is [" + intTotal + "]");
    return vecResult;
  }

  public int getNQErrorNumber(LexiconWordMeta lwm, int lesson, SentenceStatisticStructure sss) throws IOException {
    // QUANTITY,QUANTITY2,INVS_PCE,INVDG_PCE
    logger.debug("int getNQErrorNumber; word [" + lwm.getStrKana() + "]");
    intTotal = 0;
    PredictionDataMeta pdm;
    // INVS_PCE
    Vector vec = getNQPCE(lwm, true, false, false, true, true, true, sss);
    if (vec != null && vec.size() > 0) {
      intTotal += vec.size();
      sss.addDigit("INVS_PCE", vec.size());
      System.out.println("INVS_PCE is [" + vec.size() + "]");
    }

    // VDG
    Vector v = getNQSubsGroup(lwm, lesson);
    if (v != null && v.size() > 0) {
      intTotal += v.size();
      sss.addDigit("VDG", v.size());
      System.out.println("VDG is [" + v.size() + "]");

      // INVDG_PCE
      int num = getNQINVDG_PCENumber(v, lesson);
      intTotal += num;
      sss.addDigit("INVDG_PCE", num);
      System.out.println("INVDG_PCE is [" + num + "]");
    }

    // QUANTITY+QUANTITY2
    String Q1 = getNQNUM(lwm);
    if (Q1 != null && Q1.length() > 0) {
      sss.addDigit("QUANTITY", 1);
      System.out.println("QUANTITY is [1]");
    }
    Vector vecQ = getNQ_NPlusQ(lwm);
    if (vecQ != null && vecQ.size() > 0) {
      intTotal += vecQ.size();
      sss.addDigit("QUANTITY2", vecQ.size());
      System.out.println("QUANTITY2 is [" + vecQ.size() + "]");
    }

    // int diffnum = 9;
    // sss.addDigit("VDOUT_WNUM", diffnum);
    // intTotal += diffnum;

    System.out.println("all is [" + intTotal + "]");
    return intTotal;
  }

  private int getNQINVDG_PCENumber(PredictionDataMeta pdm, int lesson) throws IOException {
    int intCount = 0;
    Vector vecReuslt = new Vector();
    Vector vec;
    vec = pdm.getVecAltWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        // INVDG_PCE, watch out! the quantity1 &quantity1 now should be false
        QuantifierNode qn = searchQN(str);
        if (qn != null) {
          LexiconWordMeta lwmTemp = new LexiconWordMeta();
          lwmTemp.toLexiconWord(qn);
          Vector vecPCE = getNQPCE(lwmTemp, true, false, true, true, true, true);
          if (vecPCE != null && vecPCE.size() > 0) {
            for (int j = 0; j < vecPCE.size(); j++) {
              vecReuslt.addElement((String) vecPCE.get(j));
              intCount++;
            }

          }
        }
      }
    }
    vec = pdm.getVecSubWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        // INVDG_PCE, watch out! the quantity1 &quantity1 now should be false
        QuantifierNode qn = searchQN(str);
        if (qn != null) {
          LexiconWordMeta lwmTemp = new LexiconWordMeta();
          lwmTemp.toLexiconWord(qn);
          Vector vecPCE = getNQPCE(lwmTemp, true, false, true, true, true, true);
          if (vecPCE != null && vecPCE.size() > 0) {
            for (int j = 0; j < vecPCE.size(); j++) {
              vecReuslt.addElement((String) vecPCE.get(j));
              intCount++;
            }

          }
        }
      }
    }
    return intCount;
  }

  private Vector getNQINVDG_PCE(Vector v, int lesson) throws IOException {
    int intCount = 0;
    Vector vecReuslt = new Vector();
    Vector vec;
    LexiconWordMeta lwm;
    if (v != null && v.size() > 0) {
      for (int i = 0; i < v.size(); i++) {
        lwm = ((LexiconWordMeta) v.get(i));
        // INVDG_PCE, watch out! the quantity1 &quantity1 now should be false
        Vector vecPCE = getNQPCE(lwm, true, false, false, true, true, true);
        if (vecPCE != null && vecPCE.size() > 0) {
          for (int j = 0; j < vecPCE.size(); j++) {
            vecReuslt.addElement((String) vecPCE.get(j));
            intCount++;
          }
        }
      }
    }
    return vecReuslt;
  }

  private int getNQINVDG_PCENumber(Vector v, int lesson) throws IOException {
    int intCount = 0;
    Vector vecReuslt = new Vector();
    Vector vec;
    LexiconWordMeta lwm;
    if (v != null && v.size() > 0) {
      for (int i = 0; i < v.size(); i++) {
        lwm = ((LexiconWordMeta) v.get(i));
        // INVDG_PCE, watch out! the quantity1 &quantity1 now should be false
        Vector vecPCE = getNQPCE(lwm, true, false, false, true, true, true);
        if (vecPCE != null && vecPCE.size() > 0) {
          for (int j = 0; j < vecPCE.size(); j++) {
            vecReuslt.addElement((String) vecPCE.get(j));
            intCount++;
          }

        }
      }
    }
    return intCount;
  }

  /*
   * strWord = QN
   */
  public static QuantifierNode searchQN(String strWord) {
    QuantifierNode qnResult = null;
    int id = 1;
    Vector vecCategory = qr.getVecQC();
    for (int i = 0; i < vecCategory.size(); i++) {
      QuantifierCategory qc = (QuantifierCategory) vecCategory.get(i);
      Vector vecNode = qc.getVecNode();
      for (int j = 0; j < vecNode.size(); j++) {
        QuantifierNode qn = (QuantifierNode) vecNode.get(j);
        if (qn.getStrKana().equals(strWord) || qn.strKanji.equals(strWord)) {
          qnResult = qn;
          break;
        }
      }
    }

    return qnResult;
  }

  /*
   * strWord is the basic form of the NQ. no noun, no verb; we know this word
   * belongs to the lesson,but not sure this word has the lesson-specific
   * prediction error; SubstitutionGroup = Aternative word() + Confusion word();
   * these are stored in an PredictionDataMeta which is constructed from an
   * QuantifierNode error Type is VDG Construct new PredictionDataMeta and count
   * its subsGrop's number
   */
  public static Vector getNQSubsGroup(LexiconWordMeta lwm, int Lesson) throws IOException {
    logger.info("enter getNQSubsGroup");
    Vector<LexiconWordMeta> vResult = new Vector<LexiconWordMeta>();
    PredictionDataMeta pdm = null;
    String strNumber = lwm.getStrCategory1();
    String strQuantifier = lwm.getStrCategory2();
    logger.info("lwm.getStrCategory1()---" + strNumber + " lwm.getStrCategory2()--" + strQuantifier);

    LexiconWordMeta lwmTempQ;
    LexiconWordMeta lwmTempR;

    LexiconWordMeta lwmTemp1 = lprocess.FindQuantifierInLexicon(strQuantifier);

    if (lwmTemp1 != null) {
      logger.info("find the strQuantifier---" + lwmTemp1.strKana);
      String str = lwmTemp1.getStrKana();
      pdm = getQSubsGroup(str, Lesson);
      if (pdm != null) {// has subs
        logger.info("this strQuantifier has subs group");
        Vector vecAlt = pdm.getVecAltWord();
        Vector vecSub = pdm.getVecSubWord();
        if (vecAlt != null && vecAlt.size() > 0) {
          for (int i = 0; i < vecAlt.size(); i++) { // each alt word, search the
                                                    // word in the lexicon and
                                                    // the get its correct QN
                                                    // form;
            String strAlt = (String) vecAlt.get(i);
            lwmTempQ = lprocess.FindQuantifierInLexicon(strAlt);
            if (lwmTempQ != null) {
              lwmTempR = lprocess.searchNQKana(strNumber, lwmTempQ.getStrKanji());
              // String str = searchQNKana(strNumber,lwm);
              if (lwmTempR != null) { // get the alternative QN name
                vResult.addElement(lwmTempR);
              }
            } else {
              System.out.println("wrong word's alt in the NQRULEFILE,or no exist in the system");
            }

          }
        }
        if (vecSub != null && vecSub.size() > 0) {
          for (int i = 0; i < vecSub.size(); i++) {
            String strSub = (String) vecSub.get(i);
            lwmTempQ = lprocess.FindQuantifierInLexicon(strSub);
            if (lwmTempQ != null) {
              lwmTempR = lprocess.searchNQKana(strNumber, lwmTempQ.getStrKanji());
              // String str = searchQNKana(strNumber,lwm);
              if (lwmTempR != null) { // get the alternative QN name
                vResult.addElement(lwmTempR);
              }
            } else {
              System.out.println("wrong word's alt in the NQRULEFILE,or no exist in the system");
            }

          }
        }
      }
    } else {
      logger.error("wrong word , not find qantifier:+" + strQuantifier);

    }
    return vResult;
  }

  /*
   * search QN'kana by number + Quantifier
   */
  private static String searchQNKana(String number, LexiconWordMeta lwmQuantifier) {
    String strResult = null;
    Vector vecCategory = qr.getVecQC();
    for (int i = 0; i < vecCategory.size(); i++) {
      QuantifierCategory qc = (QuantifierCategory) vecCategory.get(i);
      if (qc.getStrName().equals(lwmQuantifier.getStrKana()) || qc.getStrName().equals(lwmQuantifier.getStrKanji())) {
        Vector vecNode = qc.getVecNode();
        for (int j = 0; j < vecNode.size(); j++) {
          QuantifierNode qnTemp = (QuantifierNode) vecNode.get(j);
          if (qnTemp.getStrNumeral().equals(number)) {
            return qnTemp.getStrKana();
          }
        }
      }
    }
    return strResult;
  }

  public static PredictionDataMeta getNounSubsGroup(String strWord, int lesson) {
    logger.debug("enter getNounSubsGroup:" + strWord);
    // String strWord = lwm.getStrKana();
    PredictionDataMeta pdmResult = null;
    PredictionDataMeta pdm = null;
    String strSearchKana;
    for (int i = 0; i < eps.vecLesson.size(); i++) {
      pdm = (PredictionDataMeta) eps.vecLesson.get(i);
      int intLesson = pdm.getLesson();
      if (intLesson == lesson) {// same lesson
        logger.debug("same lesson: " + lesson + " pdm word is" + pdm.getStrWord());
        strSearchKana = pdm.getStrWord();
        if (strWord.equals(strSearchKana)) {
          pdmResult = pdm;
          logger.debug("find one");
          break;
        }
      }
    }
    return pdmResult;
  }

  public static PredictionDataMeta getQSubsGroup(String strWord, int lesson) {
    logger.debug("enter getQSubsGroup:" + strWord);
    // String strWord = lwm.getStrKana();
    PredictionDataMeta pdmResult = null;
    PredictionDataMeta pdm = null;
    String strSearchKana;
    for (int i = 0; i < eps.vecLesson.size(); i++) {
      pdm = (PredictionDataMeta) eps.vecLesson.get(i);
      int intLesson = pdm.getLesson();
      if (intLesson == lesson) {// same lesson
        logger.debug("same lesson: " + lesson + " pdm word is" + pdm.getStrWord());
        strSearchKana = pdm.getStrWord();
        if (strWord.equals(strSearchKana)) {
          pdmResult = pdm;
          logger.debug("find one");
          break;
        }
      }
    }
    if (pdmResult == null) {
      for (int i = 0; i < eps.vecGeneral.size(); i++) {
        pdm = (PredictionDataMeta) eps.vecGeneral.get(i);
        int intLesson = pdm.getLesson();
        // logger.debug(" pdm word is"+ pdm.getStrWord());
        strSearchKana = pdm.getStrWord();
        if (strWord.equals(strSearchKana)) {
          pdmResult = pdm;
          logger.debug("find one in general");
          break;
        }
      }
    }
    return pdmResult;
  }

  public static String getSOREKORE(LexiconWordMeta lwm) {

    if (lwm.strKana.equalsIgnoreCase("����")) {
      String str = "����";
    } else if (lwm.strKana.equalsIgnoreCase("����")) {
      String str = "����";
    }
    return null;
  }

  public static String getOPlusNoun(LexiconWordMeta lwm) {

    if (lwm.intType == Lexicon.LEX_TYPE_NOUN) {
      if (lwm.strCategory1.equalsIgnoreCase("family") || lwm.strCategory2.equalsIgnoreCase("family")) {
        return null;
      } else {
        if (lwm.strCategory1.equalsIgnoreCase("job") && lwm.strCategory2.equalsIgnoreCase("job")) {
          return null;
        } else {
          String str = "��" + lwm.getStrKana();
          return str;
        }
      }
    }
    return null;
  }

  /*
   * 
   * for all the noun,included numeral+quantifier;
   */

  /**
   * @param strWord
   *          only noun-kana format, no NQ,no Verb,no Particle
   * @param OmitShort
   * @param AddShort
   *          is always false.
   * @param OmitLong
   * @param AddLong
   *          is detected,only the last character could add "��" OmitVoiced
   *          means g-k,d-t ,except quantifier; or (light weight continous
   *          words(like ������) ,but havent done this part
   * @param OmitVoiced
   * @param AddVoiced
   *          :is only checked by the first pronunciation is �� ,and confined to
   *          the japanese word(no english-based words)
   * @return
   * @throws IOException
   */
  public Vector getNQPCE(LexiconWordMeta lwm, boolean OmitShort, boolean AddShort, boolean OmitLong, boolean AddLong,
      boolean OmitVoiced, boolean AddVoiced) throws IOException {
    Vector<String> vecResult = new Vector<String>();
    String strWrongWord;
    // String strChar = null;
    Vector v;
    String strWord = lwm.getStrKana().trim();
    logger.debug("int getNQPCE; word [" + strWord + "]");
    if (AddLong) {
      strWrongWord = getAddLongWord(strWord);
      if (strWrongWord != null && strWrongWord.length() > 0) {
        logger.debug("AddLong" + strWrongWord);
        vecResult.addElement(strWrongWord);
      }
    }
    if (AddVoiced) {
      strWrongWord = getAddVoicedWord4NQ(lwm);
      if (strWrongWord != null && strWrongWord.length() > 0) {
        vecResult.addElement(strWrongWord);
      }
    }
    if (OmitVoiced) {// no care about the char position
      strWrongWord = getOmitVoicedWord4NQ(lwm);
      if (strWrongWord != null && strWrongWord.length() > 0) {
        vecResult.addElement(strWrongWord);
      }
    }
    if (OmitLong) {// happens from the second position
      v = getOmitLongWords(strWord);
      if (v != null && v.size() > 0) {
        for (int i = 0; i < v.size(); i++) {
          vecResult.addElement((String) v.elementAt(i));
        }
      }
    }
    if (OmitShort) {
      v = getOmitDoubleConsonentWords(strWord);
      if (v != null && v.size() > 0) {
        for (int i = 0; i < v.size(); i++) {
          vecResult.addElement((String) v.elementAt(i));
        }
      }
    }

    return vecResult;

  }

  public Vector getNQPCE(LexiconWordMeta lwm, boolean OmitShort, boolean AddShort, boolean OmitLong, boolean AddLong,
      boolean OmitVoiced, boolean AddVoiced, SentenceStatisticStructure sss) throws IOException {
    Vector<String> vecResult = new Vector<String>();
    String strWrongWord;
    // String strChar = null;
    Vector v;
    String strWord = lwm.getStrKana().trim();
    logger.debug("int getNQPCE; word [" + strWord + "]");

    if (AddVoiced) {
      logger.debug("AddVoiced " + AddVoiced);
      strWrongWord = getAddVoicedWord4NQ(lwm);
      if (strWrongWord != null && strWrongWord.length() > 0) {
        sss.addDigit("AddVoiced", 1);
        vecResult.addElement(strWrongWord);
      }
    }

    if (AddLong) {
      logger.debug("AddLong: " + AddLong);
      strWrongWord = getAddLongWord(strWord);
      if (strWrongWord != null && strWrongWord.length() > 0) {
        logger.debug("AddLong" + strWrongWord);
        sss.addDigit("AddLong", 1);
        vecResult.addElement(strWrongWord);
      } else {
        logger.debug("not AddLong prediction" + AddLong);
      }
    }

    if (OmitVoiced) {// no care about the char position
      strWrongWord = getOmitVoicedWord4NQ(lwm);
      if (strWrongWord != null && strWrongWord.length() > 0) {
        System.out.println("OmitVoiced" + strWrongWord);
        sss.addDigit("OmitVoiced", 1);
        vecResult.addElement(strWrongWord);
      } else {
        logger.debug("not OmitVoiced prediction" + AddLong);
      }
    }
    if (OmitLong) {// happens from the second position
      v = getOmitLongWords(strWord);
      if (v != null && v.size() > 0) {
        for (int i = 0; i < v.size(); i++) {
          vecResult.addElement((String) v.elementAt(i));
          sss.addDigit("OmitLong", 1);
        }
      } else {
        logger.debug("not OmitLong prediction" + AddLong);
      }
    }
    if (OmitShort) {
      v = getOmitDoubleConsonentWords(strWord);
      if (v != null && v.size() > 0) {
        for (int i = 0; i < v.size(); i++) {
          vecResult.addElement((String) v.elementAt(i));
          sss.addDigit("OmitShort", 1);
        }
      }
    }

    return vecResult;

  }

  /*
   * AddVoiced :is only checked by the first pronunciation is �� ,and confined
   * to the japanese word(no english-based words)
   */
  public static String getAddVoicedWord4Noun(LexiconWordMeta lwm, String strWord) {
    // String strResult = null;
    String strWrongWord;
    String strChar = new String("" + strWord.charAt(0));
    if (strChar.equals("��")) {
      if (lwm.getIntType() == Lexicon.LEX_TYPE_NOUN) {
        strWrongWord = strWord.replaceFirst("��", "��");
        logger.debug("AddVoiced: " + strWrongWord);
        return strWrongWord;
      }
    }
    return null;
  }

  /*
   * AddVoiced :is only checked by the first pronunciation is �� ,and confined
   * to the japanese word(no english-based words)
   */
  public static String getAddVoicedWord4Noun(LexiconWordMeta lwm) {
    // String strResult = null;
    logger.debug("getAddVoicedWord4Noun");
    String strWrongWord = null;
    String strWord = lwm.getStrKana();
    String strChar = new String("" + strWord.charAt(0));
    if (strChar.equals("��")) {
      if (lwm.getIntType() == Lexicon.LEX_TYPE_NOUN) {
        strWrongWord = getReplaceWord("��", strWord, 0);
        // strWrongWord = strWord.replaceFirst("��", "��");
        logger.debug("AddVoiced: " + strWrongWord);
        return strWrongWord;
      }
    }
    return null;
  }

  public static Vector getOmitVoicedWords4Noun(String strWord) {
    logger.debug("enter getOmitVoicedWords4Noun;");
    Vector<String> vecResult = new Vector<String>();
    String strWrongWord;
    String strChar = null;
    for (int i = 0; i < strWord.length(); i++) {
      strChar = new String("" + strWord.charAt(i));
      strWrongWord = getOmitVoicedWord4Noun(strChar, strWord, i);
      if (strWrongWord != null && strWrongWord.length() > 0) {
        logger.debug("OmitVoiced: " + strWrongWord);
        vecResult.addElement(strWrongWord);
      }
    }
    return vecResult;

  }

  /**
   * @param strChar
   * @param strWord
   * @return its responding wrong word or else return null,
   */
  private static String getOmitVoicedWord4Noun(String strChar, String strWord, int index) {
    logger.debug("enter getOmitVoicedWord4Noun;");
    String strResult = null;
    String strVoiced = getOmitVoicedChar4Noun(strChar);
    if (strVoiced != null) {
      strResult = getReplaceWord(strVoiced, strWord, index);
      logger.debug("is a voiced char");
    }
    return strResult;
  }

  /**
   * In all of the data,eng_based word only have two such error.They are
   * piza-pisa, beddo-betto; (pa-ba colomn,ha column),(ji,si)no pronunce
   * confusion for their big difference at least for pure japanese word; no
   * appears in our existing db
   * 
   * @param strChar
   *          check the voiced pronuncation if it is da/ga/za colummn(exclude
   *          ji)
   * @return its responding wrong char or else return null za-sa ga-ka
   *         suzuki-susuki tomodati-tomotati codomo-cotomo ji-chi tegame-tekame
   *         ginnko-kinnco
   */
  private static String getOmitVoicedChar4Noun(String strChar) {
    logger.debug("getOmitVoicedChar4Noun");
    String strResult = null;
    int i = 11;
    if (strChar.equals("��") || strChar.equals("�W")) {
      return null;
    } else {
      while (i <= 13) {
        for (int j = 0; j < 5; j++) {
          String strTemp1 = CharacterUtil.HIRAGANATABLE[i][j];
          String strTemp2 = CharacterUtil.KATAKANATABLE[i][j];
          if (strChar.equals(strTemp1)) {
            strResult = CharacterUtil.HIRAGANATABLE[i - 10][j];
            return strResult;
          } else if (strChar.equals(strTemp2)) {
            strResult = CharacterUtil.KATAKANATABLE[i - 10][j];
            return strResult;
          }
        }
        i++;
      }
    }
    return null;
  }

  public static Vector getOmitLongWords(String strWord) {
    logger.debug("getOmitLongWords");
    Vector<String> vecResult = new Vector<String>();
    String strWrongWord;
    String strChar = null;
    String strPreChar;

    // String strWord = lwm.getStrKana().trim(); //kana

    for (int i = 0; i < strWord.length(); i++) {
      strPreChar = strChar;
      strChar = new String("" + strWord.charAt(i));
      if (0 < i) {
        strWrongWord = getOmitLongWord(strPreChar, strChar, strWord, i);
        if (strWrongWord != null && strWrongWord.length() > 0) {
          logger.debug("OmitLong: " + strWrongWord);
          vecResult.addElement(strWrongWord);
        }
      }
    }
    return vecResult;

  }

  /*
   * 1
   * [��]colummn+[��];[��]colummn+[��];[��]colummn+[��];[��]colummn+[��]/[��];[
   * ��]colummn+[��]/[��]; 2 [��]+[��];[��]+[��];[��]+[��]; 3 [�[] check if it
   * is a long pronuncation if so, return its responding wrong word or else
   * return null
   */
  private static String getOmitLongWord(String strPreChar, String strChar, String strWord, int index) {
    // System.out.println("enter getOmitLongWord strPreChar is "+strPreChar+"strChar is "+strChar+"strWord is"+strWord
    // );
    String strResult = null;
    if (checkLongChar(strPreChar, strChar)) {
      strResult = getReplaceWord("", strWord, index);
    }
    return strResult;
  }

  /**
   * @param strPreChar
   * @param strChar
   *          1 [��]colummn+[��];[��]colummn+[��];[��]colummn+[��];[��]colummn+[
   *          ��]/[��];[��]colummn+[��]/[��]; 2 [��]+[��];[��]+[��];[��]+[��]; 3
   *          [�[] check if it is a long pronuncation if so, return its
   *          responding wrong word or else return null
   * @return if strChar is a long pronunce
   */
  private static boolean checkLongChar(String strPreChar, String strChar) {
    // System.out.println("enter checkLongChar strPreChar is "+strPreChar+"strChar is "+strChar);
    String strResult = null;
    boolean booLongPronunce = false;
    if (strPreChar != null && strPreChar.length() > 0) {
      if (strChar.equals("�[")) {
        booLongPronunce = true;

      } else {
        // type 2
        if ((strPreChar.compareTo("��") == 0 && strChar.equals("��"))) {
          booLongPronunce = true;
        } else if ((strPreChar.compareTo("��") == 0) && strChar.equals("�A")) {
          booLongPronunce = true;
        } else if ((strPreChar.compareTo("��") == 0) && strChar.equals("�E")) {
          booLongPronunce = true;
        } else if ((strPreChar.compareTo("��") == 0) && strChar.equals("�E")) {
          booLongPronunce = true;
        } else if ((strPreChar.compareTo("��") == 0) && strChar.equals("��")) {
          booLongPronunce = true;
        } else if ((strPreChar.compareTo("��") == 0) && strChar.equals("�E")) {
          booLongPronunce = true;
        }// type 1
        else {
          String charColumnName1 = CharacterUtil.charColumnName(strPreChar);
          // String charColumnName2 =
          // CharacterUtil.characterColumnName(strChar);
          if (charColumnName1 != null) {
            if (charColumnName1.equals(strChar)) {
              booLongPronunce = true;
            } else if (charColumnName1.equals("��") && strChar.equals("��")) {
              booLongPronunce = true;
            } else if (charColumnName1.equals("�G") && strChar.equals("�C")) {
              booLongPronunce = true;
            } else if (charColumnName1.equals("��") && strChar.equals("��")) {
              booLongPronunce = true;
            } else if (charColumnName1.equals("�I") && strChar.equals("�E")) {
              booLongPronunce = true;
            }
          }
        }
      }
    }
    return booLongPronunce;
  }

  /*
   * for noun check if it could connect [��] and become a long pronunciation or
   * we could say AddLong is detected,only the last character could add "��"
   * [��]colummn,[��]colummn,[��],[��],except [��] For katakana words it happens
   * all the time except "�["/"��"
   */
  private String getAddLongWord(String strWord) {
    String strChar = strWord.substring(strWord.length() - 1);
    logger.debug("the last char is: " + strChar);
    String strResult = null;
    int i = CharacterUtil.checkCharClass(strChar);
    logger.debug("class is " + i);
    if (i == 1) {
      if (strChar.compareTo("�[") == 0 || strChar.compareTo("��") == 0) {
        return null;
      } else {
        strResult = strWord.concat("�[");
      }
    } else {
      if ((strChar.compareTo("��") == 0) || (strChar.compareTo("��") == 0)) {
        strResult = strWord.concat("��");
      } else {
        String charColumnName2 = CharacterUtil.charColumnName(strChar);
        if (charColumnName2 != null) {
          if (charColumnName2.equals("��") && !strChar.equals("��")) {
            strResult = strWord.concat("��");
          } else if (charColumnName2.equals("��")) {
            strResult = strWord.concat("��");
          }
        }
      }
    }
    return strResult;
  }

  /*
   * check if it could connect [��] and become a long pronunciation
   * [��]colummn,[��]colummn,[��],[��]
   */
  private static boolean checkAddLongChar(String strChar) {
    boolean booLongPronunce = false;
    if ((strChar.compareTo("��") == 0) || (strChar.compareTo("��") == 0)) {
      booLongPronunce = true;
    } else if ((strChar.compareTo("��") == 0) || (strChar.compareTo("��") == 0)) {
      booLongPronunce = true;

    }// type 1
    else {
      String charColumnName2 = CharacterUtil.charColumnNameHIRA(strChar);
      if (charColumnName2.equals("��") || charColumnName2.equals("��")) {
        booLongPronunce = true;
      }
    }
    return booLongPronunce;
  }

  /**
   * @param lwm
   * @return Vector if get OmitDoubleConsonent Words its size>0;else size=0;
   */
  public static Vector getOmitDoubleConsonentWords(String strWord) {
    logger.debug("the getOmitDoubleConsonentWords: " + strWord);
    Vector<String> vecResult = new Vector<String>();
    String strWrongWord;
    // String strKana = lwm.getStrKana().trim();
    int index;
    int i = 0;
    while (i < strWord.length()) {
      index = strWord.indexOf(DOUBLECONSONENT, i + 1);
      logger.debug("index is " + index);
      if (index != -1) {
        strWrongWord = getReplaceWord("", strWord, index);
        logger.debug("have one," + strWrongWord);
        vecResult.addElement(strWrongWord);
        i = index;
      } else {
        logger.debug("have no more OmitDoubleConsonent");
        i = strWord.length() + 1;
      }

    }
    return vecResult;
  }

  /**
   * @param lwm
   *          POS=NQ,like ���B҂�, use its kana format
   * @return null,if no such type error; or else return responding wrong word of
   *         this wsdm;
   */
  private static String getOmitVoicedWord4NQ(LexiconWordMeta lwm) {
    String strResult = null;
    String strKanji = lwm.getStrKanji().trim();
    String strKana = lwm.getStrKana().trim();
    String strQuantifier = lwm.getStrCategory2().trim();

    logger.debug("enter  getOmitVoicedWord4NQ:" + strKana);

    if (strKanji.length() > 1) {
      LexiconWordMeta lwmTemp = lprocess.FindQuantifierInLexicon(strQuantifier);
      if (lwmTemp != null) {
        String strQuantifierKana = lwmTemp.getStrKana().trim();
        int intLength = strQuantifierKana.length();
        int changeCharIndex = strKana.length() - intLength;
        String strCharSource = new String("" + strKana.charAt(changeCharIndex));
        String strCharTarget = CharacterUtil.changeVoicedToLight(strCharSource);
        if (strCharTarget != null) { // if it is an voicedLight
          strResult = getReplaceWord(strCharTarget, strKana, changeCharIndex);
          logger.debug("one OmitVoicedWord4NQ: " + strResult);
        }
      } else {
        System.out.println("wrong Quantifier! [" + strQuantifier + "]");
      }
    }
    return strResult;
  }

  private static String getReplaceWord(String targetChar, String strWord, int i) {
    String strResult = "";
    String strHead = strWord.substring(0, i);
    String strTail = strWord.substring(i + 1);
    strResult = strHead + targetChar + strTail;
    return strResult;
  }

  /**
   * Feature: add voiced pronunciation to the first character of its responding
   * quantifier, only if the first charcter is "ka/sa/ta/ha/pa" row.
   * 
   * @param lwm
   *          POS=NQ,like ���B҂�, use its kana format *
   * @return null,if no such type error; or else return responding addvoiced
   *         wrong word of this wsdm;
   */

  public static String getAddVoicedWord4NQ(LexiconWordMeta lwm) throws IOException {
    String strResult = null;
    String strKanji = lwm.getStrKanji().trim();
    String strKana = lwm.getStrKana().trim();
    String strNumber; // number part by cutting quantifier
    String strQuantifier = lwm.getStrCategory2().trim();
    String strQuantifierLeft = "";
    if (strKanji.length() > 1) {
      LexiconWordMeta lwmTemp = lprocess.FindQuantifierInLexicon(strQuantifier);
      if (lwmTemp != null) {
        String strQuantifierKana = lwmTemp.getStrKana().trim();
        String strChar = strQuantifierKana.substring(0, 1);
        int intLength = strQuantifierKana.length();
        strNumber = strKana.substring(0, strKana.length() - intLength); // the
                                                                        // string
                                                                        // without
                                                                        // quantifier;
        if (intLength > 1) {
          strQuantifierLeft = strQuantifierKana.substring(1);
        }
        String strCharTemp = CharacterUtil.LightToVoiced(strChar);
        if (strCharTemp != null && strCharTemp.length() > 0) {
          strResult = strNumber + strCharTemp + strQuantifierLeft;
        }
      }
    }
    return strResult;
  }

  /*
   * quantity1 = number type quantity2= number+Quantity wrong type, if it should
   * be special transform format return null,when no such type error; or else
   * return responding wrong words;
   */
  public static String getNQNUM(LexiconWordMeta lwm) {
    // Vector<String> vecResult = new Vector<String>();
    if (lwm != null) { // it is a num+quantifier type;
      String strNumber = lwm.getStrCategory1();
      return strNumber;
    }
    return null;
  }

  /*
   * quantity1 = number type quantity2= number+Quantity wrong type, if it should
   * be special transform format return null,when no such type error; or else
   * return responding wrong words;
   */
  public static Vector getNQ_NPlusQ(LexiconWordMeta lwm) throws IOException {
    logger.debug("enter getNQ_NPlusQ");
    Vector<String> vecResult = new Vector<String>();
    LexiconParser l = new LexiconParser();
    // QuantifierNode qn = Lexicon.checkQuantifier(strWord);
    if (lwm != null) { // it is a num+quantifier type;
      String strKanji = lwm.getStrKanji().trim();
      String strKana = lwm.getStrKana().trim();
      String strNumber = lwm.getStrCategory1();
      String strQuantifier = lwm.getStrCategory2();

      LexiconWordMeta numberWord = lprocess.FindNumeralInLexicon(strNumber);
      LexiconWordMeta quantifierWord = lprocess.FindQuantifierInLexicon(strQuantifier);
      if (numberWord != null && quantifierWord != null) {
        String strWrongWord1 = numberWord.getStrKana() + quantifierWord.getStrKana();
        String strWrongWord2 = null;
        if (numberWord.getStrAltkana().length() > 0) {
          strWrongWord2 = numberWord.getStrAltkana() + quantifierWord.getStrKana();
        }
        if (strKana.equals(strWrongWord1)) {
          return null;
        } else {
          if (strWrongWord2 == null) {
            vecResult.addElement(strWrongWord1);
          } else {
            if (strKana.equals(strWrongWord2)) {
              return null;
            } else {
              vecResult.addElement(strWrongWord2);
              vecResult.addElement(strWrongWord1);
            }
          }
        }

      } else {
        logger.debug("number [" + strNumber + "]" + " or quantifier [" + strQuantifier
            + "] is not found in the lexicon");
      }
    }
    return vecResult;
  }

  /*
   * quantity1 = number type quantity2= number+Quantity wrong type, if it should
   * be special transform format return null,when no such type error; or else
   * return responding wrong words;
   */
  public static Vector getNQQuantityError(LexiconWordMeta lwm, boolean quantity1, boolean quantity2) throws IOException {
    Vector<String> vecResult = new Vector<String>();
    LexiconParser l = new LexiconParser();
    // QuantifierNode qn = Lexicon.checkQuantifier(strWord);
    if (lwm != null) { // it is a num+quantifier type;
      String strKanji = lwm.getStrKanji().trim();
      String strKana = lwm.getStrKana().trim();
      String strNumber = lwm.getStrCategory1();
      String strQuantifier = lwm.getStrCategory2();
      LexiconWordMeta numberWord = lprocess.FindNumeralInLexicon(strQuantifier);
      LexiconWordMeta quantifierWord = lprocess.FindQuantifierInLexicon(strQuantifier);

      if (numberWord != null && quantifierWord != null) {
        if (quantity1) {
          vecResult.addElement(numberWord.getStrKana());
          if (numberWord.getStrAltkana().length() > 0) {
            vecResult.addElement(numberWord.getStrAltkana());
          }
        }
        if (quantity2) {
          String strWrongWord1 = numberWord.getStrKana() + quantifierWord.getStrKana();
          String strWrongWord2 = null;
          if (numberWord.getStrAltkana().length() > 0) {
            strWrongWord2 = numberWord.getStrAltkana() + quantifierWord.getStrKana();
          }
          if (strKana.equals(strWrongWord1)) {
            System.out.println("no quantity2");
          } else {
            if (strWrongWord2 == null) {
              vecResult.addElement(strWrongWord1);
            } else {
              if (strKana.equals(strWrongWord2)) {
                System.out.println("no quantity2");
              } else {
                vecResult.addElement(strWrongWord2);
                vecResult.addElement(strWrongWord1);
              }
            }
          }

        } // end of if(quantity2){
      } else {
        System.out.println("number [" + strNumber + "]" + " or quantifier [" + strQuantifier
            + "] is not found in the lexicon");
      }
    }
    return vecResult;
  }

  // ////////////////////
  // ////////
  // ////////
  // ////////////////////////////////////////////

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    PredictionProcess pp = new PredictionProcess();
    LexiconWordMeta lwm = new LexiconWordMeta();
    SentenceStatisticStructure sss = new SentenceStatisticStructure();
    // lwm.setStrKana("���B҂�");
    // lwm.setStrKanji("��C") ;
    // lwm.setStrCategory1("��");
    // lwm.setStrCategory2("�C");
    /*
     * 
     * lwm.setStrKana("���Bς�"); lwm.setStrKanji("��t") ;
     * lwm.setStrCategory1("��"); lwm.setStrCategory2("�t"); Vector v =
     * pp.getNQError(lwm,21, sss); for (int i = 0; i < v.size(); i++) {
     * System.out.println("getVerbError ["+i+"] "+v.get(i)); } int a =
     * pp.getNQErrorNumber(lwm, 21, sss ); System.out.println(a);
     */
    /*
     * lwm.setStrKana("�ق�₭����"); lwm.setStrKanji("�|�󂷂�");
     * lwm.setStrAttribute("Group3");
     */
    // pp.getVerbError_VS_DFORM(lwm, 2, sss);
    // int a = pp.getVerbError_VDG_SFORM(lwm, 2, sss, "outlesson");

    /*
     * 
     * noun test code
     */
    // lwm.setStrKana("���イ�ɂイ");
    // lwm.setStrKanji("����") ;
    // lwm.setStrAltkana("�~���N");
    // Vector v = pp.getNounError(lwm,1);
    // for (int i = 0; i < v.size(); i++) {
    // System.out.println("getVerbError ["+i+"] "+v.get(i));
    // }
    // int a = pp.getNounErrorNumber(lwm, 2, sss);
    // System.out.println(a);

    /*
     * verb test code
     */
    // "��炤",22;
    lwm.setStrKana("����");
    lwm.setStrKanji("�s��");
    lwm.setStrAttribute("Group1");
    // String[] v = pp.getVerbErrors(lwm, 7, true, false);
    Vector v = pp.getVerbErrors(lwm, 28, true, false);
    for (int i = 0; i < v.size(); i++) {
      EPLeaf epl = (EPLeaf) v.elementAt(i);
      System.out.println("getVerbError [" + i + "]: " + epl.getStrKana());
    }

    // int a = pp.getVerbErrorNumber(lwm, 22, sss, "outlesson");
    // System.out.println(a);

  }
}
