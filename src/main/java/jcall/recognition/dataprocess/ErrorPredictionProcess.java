/**
 * Created on 2007/06/03
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import jcall.recognition.languagemodel.FormatVerb;
import jcall.recognition.util.CharacterUtil;

/**
 * @author kyoto-u
 *
 */
public class ErrorPredictionProcess {

  // static Logger logger = Logger.getLogger
  // (ErrorPredictionProcess.class.getName()) ;

  public ErrorPredictionParser epp;
  public QuantifierRuleParser qrp;
  public LexiconParser lp;

  public static QuantifierRule qr;
  public static ErrorPredictionStruct eps;
  public static Lexicon l;
  public static final String NQRULEFILE = "./Data/QuantiferRules.xml";
  public static final String PREDICTIONFILE = "./Data/LessonErrorPrediction.txt";
  public static final String LEXICONFILE = "./Data/newlexicon.txt";
  static int intTotal;
  public static final String[] VOICEDROW = { "��", "��", "��", "��", "��" };

  public ErrorPredictionProcess() throws IOException {
    init();

  }

  private void init() throws IOException {
    epp = new ErrorPredictionParser();
    qrp = new QuantifierRuleParser();
    lp = new LexiconParser();
    qr = qrp.loadFromFile(NQRULEFILE);
    eps = epp.loadDataFromFile(PREDICTIONFILE);
    l = lp.loadDataFromFile(LEXICONFILE);
    intTotal = 0;
  }

  public static int getWordPrediction(String strWord, int lesson, boolean booVerb, boolean booParticle)
      throws IOException {
    // logger.info("enter getWordPrediction");
    int intCount = 0;

    if (booVerb) {// type = verb
      intCount = getVerbErrorNumber(strWord, lesson);

    } else {
      if (booParticle) {
        LexiconWordMeta lwm = getLexiconWordParticle(strWord);
        if (lwm != null) {
          intCount = getParticleErrorNumber(lwm, lesson);
        }
      } else {// NQ + Noun
        LexiconWordMeta lwm = getLexiconWordNoun(strWord);
        if (lwm != null) { // type = noun/particle
          intCount = getNounErrorNumber(lwm, lesson);
        } else { // NQ
          QuantifierNode qn = getQN(strWord);
          if (qn != null) {// type = QN
            intCount = getNQErrorNumber(qn.getStrKana().trim(), qn, lesson);
          } else {

            System.out.println("sth wrong , the target word is not active or not exists in our data base ---["
                + strWord + "]");
          }
        }
      }
    }
    return intCount;
  }

  public static int getWord(String strWord, int lesson, boolean booVerb, boolean booParticle) throws IOException {
    // logger.info("enter getWordPrediction");
    int intCount = 0;

    if (booVerb) {// type = verb
      intCount = getVerbErrorNumber(strWord, lesson);

    } else {
      if (booParticle) {
        LexiconWordMeta lwm = getLexiconWordParticle(strWord);
        if (lwm != null) {
          intCount = getParticleErrorNumber(lwm, lesson);
        }
      } else {// NQ + Noun
        LexiconWordMeta lwm = getLexiconWordNoun(strWord);
        if (lwm != null) { // type = noun/particle
          intCount = getNounErrorNumber(lwm, lesson);
        } else { // NQ
          QuantifierNode qn = getQN(strWord);
          if (qn != null) {// type = QN
            intCount = getNQErrorNumber(qn.getStrKana().trim(), qn, lesson);
          } else {

            System.out.println("sth wrong , the target word is not active or not exists in our data base ---["
                + strWord + "]");
          }
        }
      }
    }
    return intCount;

  }

  public static int getWordPrediction(String strWord, int lesson, boolean booVerb, boolean booParticle,
      SentenceStatisticStructure ssstructure) throws IOException {
    // logger.info("enter getWordPrediction");
    int intCount = 0;

    if (booVerb) {// type = verb
      intCount = getVerbErrorNumber(strWord, lesson, ssstructure);

    } else {
      if (booParticle) {
        LexiconWordMeta lwm = getLexiconWordParticle(strWord);
        if (lwm != null) {
          intCount = getParticleErrorNumber(lwm, lesson, ssstructure);
        }
      } else {// NQ + Noun
        LexiconWordMeta lwm = getLexiconWordNoun(strWord);
        if (lwm != null) { // type = noun/particle
          intCount = getNounErrorNumber(lwm, lesson, ssstructure);
        } else { // NQ
          QuantifierNode qn = getQN(strWord);
          if (qn != null) {// type = QN
            intCount = getNQErrorNumber(qn.getStrKana().trim(), qn, lesson, ssstructure);
          } else {

            System.out.println("sth wrong , the target word is not active or not exists in our data base ---["
                + strWord + "]");
          }
        }
      }
    }
    return intCount;
  }

  public int getWordPrediction(String strWord, int lesson, boolean booVerb) throws IOException {
    int intCount = 0;

    if (booVerb) {// type = verb
      intCount = getVerbErrorNumber(strWord, lesson);

    } else {
      LexiconWordMeta lwm = getLexiconWordNounParticle(strWord);
      if (lwm != null) { // type = noun/particle
        if (lwm.getIntType() == Lexicon.LEX_TYPE_PARTICLE_AUXIL) {// type =
                                                                  // particle
          intCount = getParticleErrorNumber(lwm, lesson);
        } else {
          intCount = getNounErrorNumber(lwm, lesson);
        }
      } else {
        QuantifierNode qn = getQN(strWord);
        if (qn != null) {// type = QN
          intCount = getNQErrorNumber(qn.getStrKana().trim(), qn, lesson);
        } else {
          System.out.println("sth wrong , the target word is not active or not exists in our data base ---[" + strWord
              + "]");
        }
      }

    }
    return intCount;

  }

  /*
   * SubstitutionGroup = Aternative word() + Confusion word() strWord is the
   * target form of one word; verb and NQ has its transformation,should be dealt
   * specially
   */
  public static void getSubsGroup(String strWord, int Lesson) {

  }

  public static int getNounErrorNumber(String strWord, int lesson) throws IOException {
    System.out.println("int getNounErrorNumber; word [" + strWord + "]");
    intTotal = 0;
    PredictionDataMeta pdm;
    String strwordnew = null;
    // VDG + INVS_WFORM
    pdm = getNounSubsGroup(strWord, lesson);
    if (pdm != null && pdm.strWord.length() > 0) {
      intTotal += pdm.confusionCount;
      System.out.println("VDG + INVS_WFORM is [" + pdm.confusionCount + "]");
      // INVDG_PCE+INVDG_WFORM
      int num = getNounINVDG_PCEWFORM(pdm, lesson);
      intTotal += num;
      System.out.println("INVDG_PCE+INVDG_WFORM is [" + num + "]");
    }
    // INVS_PCE
    Vector vec = getNounPCE(strWord, true, false, true, true, true, true);
    if (vec != null && vec.size() > 0) {
      intTotal += vec.size();
      System.out.println("INVS_PCE is [" + vec.size() + "]");
    }
    System.out.println("all is [" + intTotal + "]");
    return intTotal;
  }

  public static int getNounErrorNumber(LexiconWordMeta lwm, int lesson) throws IOException {
    System.out.println("int getNounErrorNumber; word [" + lwm.strKana + "]");
    intTotal = 0; // original total number of all types errors
    // Vector<String> vecResult= new Vector();
    int uniqueTotal = 0;
    String strWord = lwm.getStrKana();
    Set<String> hs = new HashSet<String>();
    hs.add(strWord); // first add the original kana word, for prevent the
                     // accepted prediction words is equal to strWord;
    PredictionDataMeta pdm;
    String str = "";
    // INVS_PCE
    Vector vec = getNounPCE(strWord, true, false, true, true, true, true);
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        str = (String) vec.get(i);
        str = str.trim();
        if (!hs.contains(str)) {
          hs.add(str);
        }
      }
      intTotal += vec.size();
      System.out.println("INVS_PCE is [" + vec.size() + "]");
    }
    // VDG + INVS_WFORM
    pdm = getNounSubsGroup(lwm, lesson);
    if (pdm != null && pdm.strWord.length() > 0) {
      intTotal += pdm.confusionCount;
      System.out.println("VDG + INVS_WFORM is [" + pdm.confusionCount + "]");
      // add to set
      vec = getSubsGroup(pdm); // no include WFORM here
      if (vec != null && vec.size() > 0) {
        for (int i = 0; i < vec.size(); i++) {
          str = (String) vec.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
          }
        }
      }

      // INVDG_PCE+INVDG_WFORM
      Vector vecInv = getINVDG_PCEWFORM4Noun(pdm, lesson);
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

  public static int getNounErrorNumber(LexiconWordMeta lwm, int lesson, SentenceStatisticStructure sss)
      throws IOException {
    System.out.println("int getNounErrorNumber; word [" + lwm.strKana + "]");
    intTotal = 0; // original total number of all types errors
    // Vector<String> vecResult= new Vector();
    int uniqueTotal = 0;
    String strWord = lwm.getStrKana();
    Set<String> hs = new HashSet<String>();
    hs.add(strWord); // first add the original kana word, for prevent the
                     // accepted prediction words is equal to strWord;
    PredictionDataMeta pdm;
    String str = "";
    // INVS_PCE
    Vector vec = getNounPCE(strWord, true, false, true, true, true, true);
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

      System.out.println("INVS_PCE is [" + vec.size() + "]");
    }
    // VDG + INVS_WFORM
    pdm = getNounSubsGroup(lwm, lesson);
    if (pdm != null && pdm.strWord.length() > 0) {
      intTotal += pdm.confusionCount;
      // separte the number;
      Vector vecinvswform = pdm.getVecWrongHonorificWord();
      if (vecinvswform != null && vecinvswform.size() > 0) {
        sss.addNoun("INVS_WFORM", vecinvswform.size());
        sss.addNoun("VDG", pdm.confusionCount - vecinvswform.size());

      } else {
        sss.addNoun("VDG", pdm.confusionCount);
      }
      System.out.println("VDG + INVS_WFORM is [" + pdm.confusionCount + "]");
      // add to set
      vec = getSubsGroup(pdm); // no include WFORM here
      if (vec != null && vec.size() > 0) {
        for (int i = 0; i < vec.size(); i++) {
          str = (String) vec.get(i);
          str = str.trim();
          if (!hs.contains(str)) {
            hs.add(str);
          }
        }
      }

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

  /**
   * @param pdm
   * @return alt+sub+honorific
   */
  private static Vector getSubsGroup(PredictionDataMeta pdm) {
    Vector<String> vecResult = new Vector<String>();
    Vector vec;
    vec = pdm.getVecAltWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        vecResult.addElement(str);
      }
    }
    vec = pdm.getVecSubWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        vecResult.addElement(str);
      }
    }
    vec = pdm.getVecHonorificWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        vecResult.addElement(str);
      }
    }
    return vecResult;

  }

  public static int getParticleErrorNumber(LexiconWordMeta lwm, int lesson) throws IOException {
    System.out.println("int getParticleErrorNumber; word [" + lwm + "]");
    intTotal = 0;
    PredictionDataMeta pdm;
    // VDG
    pdm = getParticleSubsGroup(lwm, lesson);
    if (pdm != null && pdm.strWord.length() > 0) {
      intTotal += pdm.confusionCount;
      System.out.println("VDG is [" + pdm.confusionCount + "]");
    }
    System.out.println("all is [" + intTotal + "]");
    return intTotal;
  }

  public static int getParticleErrorNumber(LexiconWordMeta lwm, int lesson, SentenceStatisticStructure sss)
      throws IOException {
    String strWord = lwm.getStrKana();
    System.out.println("int getParticleErrorNumber; word [" + strWord + "]");
    intTotal = 0;
    PredictionDataMeta pdm;
    // VDG
    pdm = getParticleSubsGroup(lwm, lesson);
    if (pdm != null && pdm.strWord.length() > 0) {
      intTotal += pdm.confusionCount;
      sss.addParticle("VDG", pdm.confusionCount); // need to change;

      System.out.println("VDG is [" + pdm.confusionCount + "]");
    }
    System.out.println("all is [" + intTotal + "]");
    return intTotal;
  }

  public static int getNQErrorNumber(String strWord, QuantifierNode qn, int lesson) throws IOException {
    // QUANTITY,QUANTITY2,INVS_PCE,INVDG_PCE
    System.out.println("int getNQErrorNumber; word [" + strWord + "]");
    intTotal = 0;
    PredictionDataMeta pdm;
    // QuantifierNode qnode = qn;
    // VDG
    pdm = getNQSubsGroup(qn, lesson);
    if (pdm != null && pdm.strWord.length() > 0) {
      intTotal += pdm.confusionCount;
      System.out.println("VDG is [" + pdm.confusionCount + "]");

      // INVS_PCE
      Vector vec = getNQPCE(qn, true, false, true, true, true, true);
      if (vec != null && vec.size() > 0) {
        intTotal += vec.size();
        System.out.println("INVS_PCE is [" + vec.size() + "]");
      }
      // QUANTITY+QUANTITY2
      Vector vecQ = getNQQuantityError(qn, true, true);
      if (vecQ != null && vecQ.size() > 0) {
        intTotal += vecQ.size();
        System.out.println("QUANTITY+QUANTITY2 is [" + vecQ.size() + "]");
      }

      // INVDG_PCE
      int num = getNQINVDG_PCENumber(pdm, lesson);
      intTotal += num;
      System.out.println("INVDG_PCE is [" + num + "]");
    }
    System.out.println("all is [" + intTotal + "]");
    return intTotal;
  }

  public static int getNQErrorNumber(String strWord, QuantifierNode qn, int lesson, SentenceStatisticStructure sss)
      throws IOException {
    // QUANTITY,QUANTITY2,INVS_PCE,INVDG_PCE
    System.out.println("int getNQErrorNumber; word [" + strWord + "]");
    intTotal = 0;
    PredictionDataMeta pdm;
    // QuantifierNode qnode = qn;
    // VDG
    pdm = getNQSubsGroup(qn, lesson);
    if (pdm != null && pdm.strWord.length() > 0) {
      intTotal += pdm.confusionCount;
      sss.addDigit("VDG", pdm.confusionCount);
      System.out.println("VDG is [" + pdm.confusionCount + "]");

      // INVS_PCE
      Vector vec = getNQPCE(qn, true, false, true, true, true, true);
      if (vec != null && vec.size() > 0) {
        intTotal += vec.size();

        sss.addDigit("INVS_PCE", vec.size());

        System.out.println("INVS_PCE is [" + vec.size() + "]");
      }
      // QUANTITY+QUANTITY2
      Vector vecQ = getNQQuantityError(qn, true, true, sss);
      if (vecQ != null && vecQ.size() > 0) {
        intTotal += vecQ.size();
        System.out.println("QUANTITY+QUANTITY2 is [" + vecQ.size() + "]");
      }

      // INVDG_PCE
      int num = getNQINVDG_PCENumber(pdm, lesson);
      intTotal += num;

      sss.addDigit("INVDG_PCE", num);

      System.out.println("INVDG_PCE is [" + num + "]");
    }
    System.out.println("all is [" + intTotal + "]");
    return intTotal;
  }

  /**
   * @param strWord
   *          could be the base form(so far, this is for test) and one of the
   *          target form
   * @param lesson
   * @return
   * @throws IOException
   */
  public static int getVerbErrorNumber(String strWord, int lesson) throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    System.out.println("int getVerbErrorNumber; word [" + strWord + "]");
    intTotal = 0;
    int changeSizeDFORM = 0;
    int allForm = 1;
    int changeSizeREF = 0;
    PredictionDataMeta pdm;
    // VS_DFORM
    LexiconWordMeta lwm = getLexiconWord(strWord, lesson);
    if (lwm != null) {
      System.out.println("the base form of " + strWord + ":" + lwm.getStrKana());

      Vector vecDForm = getVerbFormOfLessonDef(lwm, lesson, "kana");
      if (vecDForm != null && vecDForm.size() > 0) {
        changeSizeDFORM = vecDForm.size() - 1;
        intTotal += vecDForm.size() - 1;
        System.out.println("VS_DFORM is [" + changeSizeDFORM + "]");
      }
    } else {
      System.out.println("wrong, no such verb in the lexicon");
    }

    PredictionDataMeta pdgVerb = getVerbPDM(lesson);
    if (pdgVerb != null) {
      // VS_DFORM, pdgverb is passed by the original one for the word "verb";
      /*
       * Vector vecDForm =(Vector)pdgVerb.getVecVerbType(); if(vecDForm!=null &&
       * vecDForm.size()>0 ){ for (int i = 0; i < vecDForm.size(); i++) {
       * allForm = allForm*2; } changeSizeDFORM = allForm-1; intTotal +=
       * allForm-1; System.out.println("VS_DFORM is ["+ changeSizeDFORM+"]"); }
       */

      // INVS_REF
      Vector vec = pdgVerb.getVecVerbInvalidFrom();
      if (vec != null && vec.size() > 0) {
        changeSizeREF = vec.size();
        intTotal += vec.size();
        System.out.println("INVS_REF is [" + changeSizeREF + "]");
      }

    } else {
      System.out.println("wrong in getVerbErrorNumber,lesson [" + lesson + "] do not have DFORM");
    }
    pdm = getVerbSubsGroup(strWord, lesson);
    if (pdm != null && pdm.strWord.length() > 0) {
      // VDG_SFORM
      intTotal += pdm.confusionCount;
      System.out.println("VDG_SFORM is [" + pdm.confusionCount + "]");
      // VDG_DFORM
      intTotal += (pdm.confusionCount) * changeSizeDFORM;
      System.out.println("VDG_SFORM is [" + (pdm.confusionCount) * changeSizeDFORM + "]");
      // INVDG_REF
      int num = (pdm.confusionCount) * changeSizeREF;
      intTotal += num;
      System.out.println("INVDG_REF is [" + num + "]");
    }

    System.out.println("all is [" + intTotal + "]");
    return intTotal;
  }

  public static int getVerbErrorNumber(String strWord, int lesson, SentenceStatisticStructure sss) throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    System.out.println("int getVerbErrorNumber; word [" + strWord + "]");
    intTotal = 0;
    int changeSizeDFORM = 0;
    int allForm = 1;
    int changeSizeREF = 0;
    PredictionDataMeta pdm;
    // int a[] = new int[];
    // VS_DFORM
    LexiconWordMeta lwm = getLexiconWord(strWord, lesson);
    if (lwm != null) {
      System.out.println("the base form of " + strWord + ":" + lwm.getStrKana());

      Vector vecDForm = getVerbFormOfLessonDef(lwm, lesson, "kana");
      if (vecDForm != null && vecDForm.size() > 0) {
        changeSizeDFORM = vecDForm.size() - 1;
        intTotal += vecDForm.size() - 1;
        sss.addVerb("VS_DFORM", changeSizeDFORM);
        System.out.println("VS_DFORM is [" + changeSizeDFORM + "]");
        // for (int i = 0; i < vecDForm.size(); i++) {
        // logger.info("Lesson"+lesson+"�@"+lwm.getStrKana()+"["+"VS_DFORM("+i+")]:�@"+vecDForm.get(i));
        // }
      }
    } else {
      System.out.println("wrong, no such verb in the lexicon");
    }

    PredictionDataMeta pdgVerb = getVerbPDM(lesson);
    if (pdgVerb != null) {
      // INVS_REF
      Vector vec = pdgVerb.getVecVerbInvalidFrom();
      if (vec != null && vec.size() > 0) {
        changeSizeREF = vec.size();
        intTotal += vec.size();
        sss.addVerb("INVS_REF", changeSizeREF);
        System.out.println("INVS_REF is [" + changeSizeREF + "]");
        // for (int i = 0; i < vec.size(); i++) {
        // logger.info("Lesson"+lesson+"�@"+lwm.getStrKana()+"["+"INVS_REF("+i+")]:�@"+vec.get(i));
        // }
      }

    } else {
      System.out.println("wrong in getVerbErrorNumber,lesson [" + lesson + "] do not have DFORM");
    }
    pdm = getVerbSubsGroup(strWord, lesson);
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

  /**
   * @param strWord
   *          could be the base form(so far, this is for test) and one of the
   *          target form
   * @param lesson
   * @return
   * @throws IOException
   */
  public static int getVerbErrorNumber(String strWord, int lesson, String errorPredicionPattern) throws IOException {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    System.out.println("int getVerbErrorNumber; word [" + strWord + "]");
    intTotal = 0;
    int changeSizeDFORM = 0;
    int allForm = 1;
    int changeSizeREF = 0;
    PredictionDataMeta pdm;
    if (errorPredicionPattern.equals("DifferentForm")) {
      // VS_DFORM
      LexiconWordMeta lwm = getLexiconWord(strWord, lesson);
      if (lwm != null) {
        System.out.println("the base form of " + strWord + ":" + lwm.getStrKana());

        Vector vecDForm = getVerbFormOfLessonDef(lwm, lesson, "kana");
        if (vecDForm != null && vecDForm.size() > 0) {
          changeSizeDFORM = vecDForm.size() - 1;
          intTotal += vecDForm.size() - 1;
          System.out.println("VS_DFORM is [" + changeSizeDFORM + "]");
        }
      } else {
        System.out.println("wrong, no such verb in the lexicon");
      }

    }
    if (errorPredicionPattern.equals("WrongForm")) {
      PredictionDataMeta pdgVerb = getVerbPDM(lesson);
      if (pdgVerb != null) {
        // INVS_REF
        Vector vec = pdgVerb.getVecVerbInvalidFrom();
        if (vec != null && vec.size() > 0) {
          changeSizeREF = vec.size();
          intTotal += vec.size();
          System.out.println("INVS_REF is [" + changeSizeREF + "]");
        }

      } else {
        System.out.println("wrong in getVerbErrorNumber,lesson [" + lesson + "] do not have DFORM");
      }

    }
    pdm = getVerbSubsGroup(strWord, lesson);
    if (errorPredicionPattern.equals("SemanticConfusion")) {

    }

    if (pdm != null && pdm.strWord.length() > 0) {
      // VDG_SFORM
      intTotal += pdm.confusionCount;
      System.out.println("VDG_SFORM is [" + pdm.confusionCount + "]");
      // VDG_DFORM
      intTotal += (pdm.confusionCount) * changeSizeDFORM;
      System.out.println("VDG_SFORM is [" + (pdm.confusionCount) * changeSizeDFORM + "]");
      // INVDG_REF
      int num = (pdm.confusionCount) * changeSizeREF;
      intTotal += num;
      System.out.println("INVDG_REF is [" + num + "]");
    }

    System.out.println("all is [" + intTotal + "]");
    return intTotal;
  }

  private static int getNQINVDG_PCENumber(PredictionDataMeta pdm, int lesson) throws IOException {
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
          Vector vecPCE = getNQPCE(qn, true, false, true, true, true, true);
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
          Vector vecPCE = getNQPCE(qn, true, false, true, true, true, true);
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

  private static Vector getNQINVDG_PCE(PredictionDataMeta pdm, int lesson) throws IOException {
    int intCount = 0;
    Vector<String> vecReuslt = new Vector();
    Vector vec;
    vec = pdm.getVecAltWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        // INVDG_PCE, watch out! the quantity1 &quantity1 now should be false
        QuantifierNode qn = searchQN(str);
        if (qn != null) {
          Vector vecPCE = getNQPCE(qn, true, false, true, true, true, true);
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
          Vector vecPCE = getNQPCE(qn, true, false, true, true, true, true);
          if (vecPCE != null && vecPCE.size() > 0) {
            for (int j = 0; j < vecPCE.size(); j++) {
              vecReuslt.addElement((String) vecPCE.get(j));
              intCount++;
            }

          }
        }
      }
    }
    return vecReuslt;
  }

  private static int getNounINVDG_PCEWFORM(PredictionDataMeta pdm, int lesson) throws IOException {
    int intCount = 0;
    Vector vec;
    vec = pdm.getVecAltWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {

        String str = ((String) vec.get(i)).trim();
        // INVDG_PCE
        Vector vecPCE = getNounPCE(str, true, false, true, true, true, true);
        if (vecPCE != null && vecPCE.size() > 0) {
          intCount += vecPCE.size();
        }
        // INVDG_WFORM
        PredictionDataMeta pdmTemp = getNounSubsGroup(str, lesson);
        if (pdmTemp != null && pdmTemp.strWord.length() > 0) {
          Vector vecINVDG = pdm.getVecWrongHonorificWord();
          if (vecINVDG != null && vec.size() > 0) {
            intCount += vecINVDG.size();
          }
        }
      }
    }
    vec = pdm.getVecSubWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        Vector vecPCE = getNounPCE(str, true, false, true, true, true, true);
        if (vecPCE != null && vecPCE.size() > 0) {
          intCount += vecPCE.size();
        }
        // INVDG_WFORM
        PredictionDataMeta pdmTemp = getNounSubsGroup(str, lesson);
        if (pdmTemp != null && pdmTemp.strWord.length() > 0) {
          Vector vecINVDG = pdm.getVecWrongHonorificWord();
          if (vecINVDG != null && vec.size() > 0) {
            intCount += vecINVDG.size();
          }
        }
      }
    }
    vec = pdm.getVecHonorificWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        Vector vecPCE = getNounPCE(str, true, false, true, true, true, true);
        if (vecPCE != null && vecPCE.size() > 0) {
          intCount += vecPCE.size();
        }
        // INVDG_WFORM
        PredictionDataMeta pdmTemp = getNounSubsGroup(str, lesson);
        if (pdmTemp != null && pdmTemp.strWord.length() > 0) {
          Vector vecINVDG = pdm.getVecWrongHonorificWord();
          if (vecINVDG != null && vec.size() > 0) {
            intCount += vecINVDG.size();
          }
        }
      }
    }
    return intCount;
  }

  private static Vector getINVDG_PCEWFORM4Noun(PredictionDataMeta pdm, int lesson) throws IOException {
    Vector<String> vecResult = new Vector<String>();
    int intCount = 0;
    Vector vec;
    vec = pdm.getVecAltWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        // INVDG_PCE
        Vector vecPCE = getNounPCE(str, true, false, true, true, true, true);
        if (vecPCE != null && vecPCE.size() > 0) {
          for (int j = 0; j < vecPCE.size(); j++) {
            vecResult.addElement((String) vecPCE.get(j));
          }
          intCount += vecPCE.size();
        }
        // INVDG_WFORM
        PredictionDataMeta pdmTemp = getNounSubsGroup(str, lesson);
        if (pdmTemp != null && pdmTemp.strWord.length() > 0) {
          Vector vecINVDG = pdm.getVecWrongHonorificWord();
          if (vecINVDG != null && vecINVDG.size() > 0) {
            for (int k = 0; k < vecINVDG.size(); k++) {
              vecResult.addElement((String) vecINVDG.get(k));
            }
            intCount += vecINVDG.size();
          }
        }
      }
    }
    vec = pdm.getVecSubWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        Vector vecPCE = getNounPCE(str, true, false, true, true, true, true);
        if (vecPCE != null && vecPCE.size() > 0) {
          for (int j = 0; j < vecPCE.size(); j++) {
            vecResult.addElement((String) vecPCE.get(j));
          }
          intCount += vecPCE.size();
        }
        // INVDG_WFORM
        PredictionDataMeta pdmTemp = getNounSubsGroup(str, lesson);
        if (pdmTemp != null && pdmTemp.strWord.length() > 0) {
          Vector vecINVDG = pdm.getVecWrongHonorificWord();
          if (vecINVDG != null && vec.size() > 0) {
            for (int k = 0; k < vecINVDG.size(); k++) {
              vecResult.addElement((String) vecINVDG.get(k));
            }
            intCount += vecINVDG.size();
          }
        }
      }
    }
    vec = pdm.getVecHonorificWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        Vector vecPCE = getNounPCE(str, true, false, true, true, true, true);
        if (vecPCE != null && vecPCE.size() > 0) {
          for (int j = 0; j < vecPCE.size(); j++) {
            vecResult.addElement((String) vecPCE.get(j));
          }
          intCount += vecPCE.size();
        }
        // INVDG_WFORM
        PredictionDataMeta pdmTemp = getNounSubsGroup(str, lesson);
        if (pdmTemp != null && pdmTemp.strWord.length() > 0) {
          Vector vecINVDG = pdm.getVecWrongHonorificWord();
          if (vecINVDG != null && vec.size() > 0) {
            for (int k = 0; k < vecINVDG.size(); k++) {
              vecResult.addElement((String) vecINVDG.get(k));
            }
            intCount += vecINVDG.size();
          }
        }
      }
    }
    return vecResult;
  }

  private static Vector getINVDG_PCEWFORM4Noun(PredictionDataMeta pdm, int lesson, SentenceStatisticStructure sss)
      throws IOException {
    Vector<String> vecResult = new Vector<String>();
    int intCount = 0;
    Vector vec;
    vec = pdm.getVecAltWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        // INVDG_PCE
        Vector vecPCE = getNounPCE(str, true, false, true, true, true, true);
        if (vecPCE != null && vecPCE.size() > 0) {
          for (int j = 0; j < vecPCE.size(); j++) {
            vecResult.addElement((String) vecPCE.get(j));
          }
          intCount += vecPCE.size();
          sss.addNoun("INVDG_PCE", vecPCE.size());
        }
        // INVDG_WFORM
        PredictionDataMeta pdmTemp = getNounSubsGroup(str, lesson);
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
    vec = pdm.getVecSubWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        Vector vecPCE = getNounPCE(str, true, false, true, true, true, true);
        if (vecPCE != null && vecPCE.size() > 0) {
          for (int j = 0; j < vecPCE.size(); j++) {
            vecResult.addElement((String) vecPCE.get(j));
          }
          intCount += vecPCE.size();
          sss.addNoun("INVDG_PCE", vecPCE.size());
        }
        // INVDG_WFORM
        PredictionDataMeta pdmTemp = getNounSubsGroup(str, lesson);
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
    vec = pdm.getVecHonorificWord();
    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        String str = ((String) vec.get(i)).trim();
        Vector vecPCE = getNounPCE(str, true, false, true, true, true, true);
        if (vecPCE != null && vecPCE.size() > 0) {
          for (int j = 0; j < vecPCE.size(); j++) {
            vecResult.addElement((String) vecPCE.get(j));
          }
          intCount += vecPCE.size();
          sss.addNoun("INVDG_PCE", vecPCE.size());
        }
        // INVDG_WFORM
        PredictionDataMeta pdmTemp = getNounSubsGroup(str, lesson);
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
    return vecResult;
  }

  // ///////////////////////////////////////
  // ///operation with the error predication data
  // ///////////
  // //////////////////////////////////
  /*
   * strWord is the target form of one general noun. no verb,no NQ;
   * SubstitutionGroup = Aternative word() + Confusion word(); these are stored
   * in an PredictionDataMeta
   */
  public static PredictionDataMeta getNounSubsGroup(String strWord, int lesson) {
    PredictionDataMeta pdmResult = null;
    PredictionDataMeta pdm = null;
    String strSearchKanji;
    String strSearchKana;
    String strSearchRomaji;
    int intWordType = CharacterUtil.checkWordClass(strWord);
    for (int i = 0; i < eps.vecLesson.size(); i++) {
      pdm = (PredictionDataMeta) eps.vecLesson.get(i);
      int intLesson = pdm.getLesson();
      if (intLesson == lesson) {// same lesson
        if (intWordType == 1 || intWordType == 2) {
          strSearchKana = pdm.getStrWord();
          if (strWord.equals(strSearchKana)) {
            pdmResult = pdm;
            break;
          }
        } else if (intWordType == 4) {
          strSearchKanji = pdm.getStrKanji();
          if (strWord.equals(strSearchKanji)) {
            pdmResult = pdm;
            break;
          }
        } else if (intWordType == 3) {
          strSearchRomaji = CharacterUtil.wordKanaToRomaji(pdm.getStrWord());
          if (strWord.equals(strSearchRomaji)) {
            pdmResult = pdm;
            break;
          }
        }

      }
    }// end of for
    if (pdmResult == null) {
      for (int i = 0; i < eps.vecGeneral.size(); i++) {
        pdm = (PredictionDataMeta) eps.vecGeneral.get(i);

        // no lesson feature
        if (intWordType == 1 || intWordType == 2) {
          strSearchKana = pdm.getStrWord();
          if (strWord.equals(strSearchKana)) {
            pdmResult = pdm;
            break;
          }
        } else if (intWordType == 4) {
          strSearchKanji = pdm.getStrKanji();
          if (strWord.equals(strSearchKanji)) {
            pdmResult = pdm;
            break;
          }
        } else if (intWordType == 3) {
          strSearchRomaji = CharacterUtil.wordKanaToRomaji(pdm.getStrWord());
          if (strWord.equals(strSearchRomaji)) {
            pdmResult = pdm;
            break;
          }
        }

      } // end of for
    }// end of if
    return pdmResult;
  }

  public static PredictionDataMeta getNounSubsGroup(LexiconWordMeta lwm, int lesson) {
    PredictionDataMeta pdmResult = null;
    PredictionDataMeta pdm = null;
    String strSearchKana;
    String strWord = lwm.getStrKana();
    for (int i = 0; i < eps.vecLesson.size(); i++) {
      pdm = (PredictionDataMeta) eps.vecLesson.get(i);
      int intLesson = pdm.getLesson();
      if (intLesson == lesson) {// same lesson
        strSearchKana = pdm.getStrWord();
        if (strWord.equals(strSearchKana)) {
          pdmResult = pdm;
          System.out.println("get one NounSubsGroup which kana is" + strSearchKana);
          break;
        }
      }
    }// end of for
    if (pdmResult == null) {
      for (int i = 0; i < eps.vecGeneral.size(); i++) {
        pdm = (PredictionDataMeta) eps.vecGeneral.get(i);
        // no lesson feature
        strSearchKana = pdm.getStrWord();
        if (strWord.equals(strSearchKana)) {
          pdmResult = pdm;
          System.out
              .println("get one NounSubsGroup which kana is" + strSearchKana + " all VDG is" + pdm.confusionCount);
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

  /*
   * strWord is kana form of one general noun. no verb,no NQ;
   */
  public static Vector getNounHonorifics(String strWord) {
    Vector vecResult = null;
    PredictionDataMeta pdm = null;
    String strSearchKana;
    Vector v = null;
    for (int i = 0; i < eps.vecGeneral.size(); i++) {
      pdm = (PredictionDataMeta) eps.vecGeneral.get(i);
      strSearchKana = pdm.getStrWord();
      if (strWord.equals(strSearchKana)) {
        v = pdm.getVecHonorificWord();
        if (v != null && v.size() > 0) {
          vecResult = v;

        }
      }
    } // end of for
    return vecResult;
  }

  /*
   * strWord is the target form of one general Particle. no verb,no NQ,no Noun;
   * SubstitutionGroup = Aternative word() + Confusion word(); these are stored
   * in an PredictionDataMeta
   */
  public static PredictionDataMeta getParticleSubsGroup(String strWord, int lesson) {
    PredictionDataMeta pdmResult = null;
    PredictionDataMeta pdm = null;
    String strSearchKanji;
    String strSearchKana;
    String strSearchRomaji;
    int intWordType = CharacterUtil.checkWordClass(strWord);
    for (int i = 0; i < eps.vecLesson.size(); i++) {
      pdm = (PredictionDataMeta) eps.vecLesson.get(i);
      int intLesson = pdm.getLesson();
      if (intLesson == lesson) {// same lesson
        if (intWordType == 1 || intWordType == 2) {
          strSearchKana = pdm.getStrWord();
          if (strWord.equals(strSearchKana)) {
            pdmResult = pdm;
            break;
          }
        } else if (intWordType == 4) {
          strSearchKanji = pdm.getStrKanji();
          if (strWord.equals(strSearchKanji)) {
            pdmResult = pdm;
            break;
          }
        } else if (intWordType == 3) {
          strSearchRomaji = CharacterUtil.wordKanaToRomaji(pdm.getStrWord());
          if (strWord.equals(strSearchRomaji)) {
            pdmResult = pdm;
            break;
          }
        }

      }
    }
    return pdmResult;
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

  /*
   * strWord is
   * 
   * the strWord the PredictionDataMeta is the the form of kana,kanji
   */

  /**
   * @param strWord
   *          the target form of one general Verb. no Particle,no NQ,no Noun;
   * @param lesson
   * @return SubstitutionGroup = Aternative word() + Confusion word();these are
   *         stored in an PredictionDataMeta
   * @return the PredictionDataMeta of strWord is the the form of kana and kanji
   */
  public static PredictionDataMeta getVerbSubsGroup(String strWord, int lesson) {
    // PredictionDataMeta pdmResult = null;
    PredictionDataMeta pdm = null;
    for (int i = 0; i < eps.vecLesson.size(); i++) {
      pdm = (PredictionDataMeta) eps.vecLesson.get(i);
      int intLesson = pdm.getLesson();
      if (intLesson == lesson && pdm.getType() == 2) {// same lesson and is a
                                                      // verb
        if (isEquality(strWord, pdm, lesson)) {
          return pdm;
        }
      }
    }
    return null;
  }

  /**
   * @param strWord
   *          kana or kanji, base form
   * @param lesson
   * @return this word's all possible form defined by the lesson ; kana format
   */
  private static Vector getVerbFormOfLessonDef(LexiconWordMeta lwm, int lesson, String kanaKanji) {
    Vector<String> vecResult = new Vector<String>();
    String strWord = "";
    if (lwm.getStrKana().equalsIgnoreCase("��������") || lwm.getStrKana().equalsIgnoreCase("���˂������܂�")) {
      strWord = lwm.getStrKana();
      vecResult.addElement(strWord);
      return vecResult;
    } else {
      if (kanaKanji.equalsIgnoreCase("kana")) {
        strWord = lwm.getStrKana();
      } else if (kanaKanji.equalsIgnoreCase("kanji")) {
        strWord = lwm.getStrKanji();
      } else {
        System.out.println("wrong, in getVerbFormOfLessonDef ;pramameter kanaKanji is not IN(kana,kanji)");
        return vecResult;
      }
      // String group = lwm.getStrCategory1();
      String group = lwm.getStrAttribute();

      boolean booType = false;
      boolean booNegative = false;
      boolean booTense = false;
      // determine sentence form
      PredictionDataMeta pdmVerb = getVerbPDM(lesson);
      Vector vecType = pdmVerb.getVecVerbType();
      for (int i = 0; i < vecType.size(); i++) {
        String strTemp = (String) vecType.get(i);
        if (strTemp.equals("type")) {
          booType = true;
        } else if (strTemp.equals("negative")) {
          booNegative = true;
        } else if (strTemp.equals("tense")) {
          booTense = true;
        }
      }
      // verb's special form
      String strRef = pdmVerb.getStrREF();// TETA,SE,Masyou
      strRef = strRef.trim();
      String strSpecialTransformWord = null;
      String strSpecialTransformWordMasyou = null;
      Vector vSpecialTransformWord = null;
      boolean booV = false;
      if (strRef.length() > 0) {
        if (strRef.equals("TETA")) {
          vSpecialTransformWord = FormatVerb.verbToOrder(strWord, booNegative, group);
          booV = true;
        } else if (strRef.equals("SE")) {
          strSpecialTransformWord = FormatVerb.verbToSe(strWord, group);
          booV = false;
          // System.out.println(strSpecialTransformWord
          // +"is the se format of ");
        } else if (strRef.equals("Masyou")) {
          strSpecialTransformWordMasyou = FormatVerb.verbToInvitationPolite(strWord, group);
          booV = true;
        }
      }
      Vector v = null;
      /*
       * still need to modify, for the TETA can't have tense changing, but here
       * we just known it won't
       */
      if (strSpecialTransformWord != null) {
        v = FormatVerb.verbToGeneralPolite(strSpecialTransformWord, booTense, booNegative, group);

      } else if (vSpecialTransformWord != null) {
        vecResult = vSpecialTransformWord;
        return vecResult;

      } else if (strSpecialTransformWordMasyou != null) {
        v = new Vector();
        v.addElement(strSpecialTransformWordMasyou);
      } else {
        v = FormatVerb.verbToGeneralPolite(strWord, booTense, booNegative, "Group2");
      }

      if (v != null && v.size() > 0) {
        for (int i = 0; i < v.size(); i++) {
          String str = (String) v.get(i);
          vecResult.addElement(str);
          if (booType) {
            vecResult.addElement(str + "��");
          }
        }
      }
    }
    return vecResult;
  }

  public static Vector getVerbFormOfLessonDefNew(LexiconWordMeta lwm, int lesson, String kanaKanji) {
    Vector<String> vecResult = new Vector<String>();
    // logger.debug("enter getVerbFormOfLessonDefAndFormBase;parameter type is: "+
    // kanaKanji);
    String strWord = "";
    if (kanaKanji.equalsIgnoreCase("kana")) {
      strWord = lwm.getStrKana();
    } else if (kanaKanji.equalsIgnoreCase("kanji")) {
      strWord = lwm.getStrKanji();
    } else {
      System.out.println("wrong, in getVerbFormOfLessonDef ;pramameter kanaKanji is not IN(kana,kanji)");
      return vecResult;
    }

    // String group = lwm.getStrCategory1();
    String group = lwm.getStrAttribute();
    // logger.debug("verb group: "+group);

    boolean booType = false;
    boolean booNegative = false;
    boolean booTense = false;

    // determine sentence form
    PredictionDataMeta pdmVerb = getVerbPDM(lesson);
    if (pdmVerb != null) {
      // logger.debug("find pdm");
      Vector vecType = pdmVerb.getVecVerbType();
      for (int i = 0; i < vecType.size(); i++) {
        String strTemp = (String) vecType.get(i);
        if (strTemp.equals("type")) {
          booType = true;
          // logger.debug("booType: "+true);
        } else if (strTemp.equals("negative")) {
          booNegative = true;
          // logger.debug("booNegative: "+true);
        } else if (strTemp.equals("tense")) {
          booTense = true;
          // logger.debug("booTense: "+true);
        }
      }
      // verb's special form
      String strRef = pdmVerb.getStrREF();// TETA,SE,Masyou
      strRef = strRef.trim();
      String strSpecialTransformWord = null;
      String strSpecialTransformWordMasyou = null;
      Vector vSpecialTransformWord = null;
      Vector v = null;
      boolean booV = false;
      if (strRef.length() > 0) {
        // logger.debug("a special form,strRef.length()>0: "+strRef);
        if (strRef.equals("TETA")) {
          vSpecialTransformWord = FormatVerb.verbToOrder(strWord, booNegative, group);
          if (vSpecialTransformWord != null) {
            vecResult = vSpecialTransformWord;
            return vecResult;

          }
          // logger.debug("SE: "+strSpecialTransformWord);
          return vecResult;
        } else if (strRef.equals("SE")) {
          strSpecialTransformWord = FormatVerb.verbToSe(strWord, group);
          // logger.debug("SE: "+strSpecialTransformWord);
          if (strSpecialTransformWord != null) {
            v = FormatVerb.verbToGeneralPolite(strSpecialTransformWord, booTense, booNegative, "Group2");
          }
          // System.out.println(strSpecialTransformWord
          // +"is the se format of ");
        } else if (strRef.equals("Masyou")) {
          strSpecialTransformWordMasyou = FormatVerb.verbToInvitationPolite(strWord, group);
          if (strSpecialTransformWordMasyou != null) {
            // logger.debug("Masyou: "+strSpecialTransformWordMasyou);
            v = new Vector();
            v.addElement(strSpecialTransformWordMasyou);
          }
        }
      } else {
        // general verb;
        v = FormatVerb.verbToGeneralPolite(strWord, booTense, booNegative, group);

      }

      /*
       * still need to modify, for the TETA can't have tense changing, but here
       * we just known it won't
       */
      if (v != null && v.size() > 0) {
        for (int i = 0; i < v.size(); i++) {
          String str = (String) v.get(i);
          vecResult.addElement(str);
          if (booType) {
            vecResult.addElement(str + "��");
          }
        }
      }
      // add the base tranformation form of this word defined in this lesson
      // like ���ׂ�����(L28) or ���؂�@(L2)

      return vecResult;

    } else {
      return null;
    }

  }

  private static Vector getVerbFormOfLessonDefAndFormBase(LexiconWordMeta lwm, int lesson, String kanaKanji) {
    Vector<String> vecResult = new Vector<String>();
    // logger.debug("enter getVerbFormOfLessonDefAndFormBase;parameter type is: "+
    // kanaKanji);
    String strWord = "";
    if (kanaKanji.equalsIgnoreCase("kana")) {
      strWord = lwm.getStrKana();
    } else if (kanaKanji.equalsIgnoreCase("kanji")) {
      strWord = lwm.getStrKanji();
    } else {
      System.out.println("wrong, in getVerbFormOfLessonDef ;pramameter kanaKanji is not IN(kana,kanji)");
      return vecResult;
    }

    // String group = lwm.getStrCategory1();
    String group = lwm.getStrAttribute();
    // logger.debug("verb group: "+group);

    boolean booType = false;
    boolean booNegative = false;
    boolean booTense = false;

    // determine sentence form
    PredictionDataMeta pdmVerb = getVerbPDM(lesson);
    if (pdmVerb != null) {
      // logger.debug("find pdm");
      Vector vecType = pdmVerb.getVecVerbType();
      for (int i = 0; i < vecType.size(); i++) {
        String strTemp = (String) vecType.get(i);
        if (strTemp.equals("type")) {
          booType = true;
          // logger.debug("booType: "+true);
        } else if (strTemp.equals("negative")) {
          booNegative = true;
          // logger.debug("booNegative: "+true);
        } else if (strTemp.equals("tense")) {
          booTense = true;
          // logger.debug("booTense: "+true);
        }
      }
      // verb's special form
      String strRef = pdmVerb.getStrREF();// TETA,SE,Masyou
      strRef = strRef.trim();
      String strSpecialTransformWord = null;
      String strSpecialTransformWordMasyou = null;
      Vector vSpecialTransformWord = null;
      Vector v = null;
      boolean booV = false;
      if (strRef.length() > 0) {
        // logger.debug("a special form,strRef.length()>0: "+strRef);
        if (strRef.equals("TETA")) {
          vSpecialTransformWord = FormatVerb.verbToOrder(strWord, booNegative, group);
          if (vSpecialTransformWord != null) {
            vecResult = vSpecialTransformWord;
            return vecResult;

          }
          // logger.debug("SE: "+strSpecialTransformWord);
          return vecResult;
        } else if (strRef.equals("SE")) {
          strSpecialTransformWord = FormatVerb.verbToSe(strWord, group);
          // logger.debug("SE: "+strSpecialTransformWord);
          if (strSpecialTransformWord != null) {
            v = FormatVerb.verbToGeneralPolite(strSpecialTransformWord, booTense, booNegative, "Group2");
          }
          // System.out.println(strSpecialTransformWord
          // +"is the se format of ");
        } else if (strRef.equals("Masyou")) {
          strSpecialTransformWordMasyou = FormatVerb.verbToInvitationPolite(strWord, group);
          if (strSpecialTransformWordMasyou != null) {
            // logger.debug("Masyou: "+strSpecialTransformWordMasyou);
            v = new Vector();
            v.addElement(strSpecialTransformWordMasyou);
          }
        }
      } else {
        // general verb;
        v = FormatVerb.verbToGeneralPolite(strWord, booTense, booNegative, group);

      }

      /*
       * still need to modify, for the TETA can't have tense changing, but here
       * we just known it won't
       */
      if (v != null && v.size() > 0) {
        for (int i = 0; i < v.size(); i++) {
          String str = (String) v.get(i);
          vecResult.addElement(str);
          if (booType) {
            vecResult.addElement(str + "��");
          }
        }
      }
      // add the base tranformation form of this word defined in this lesson
      // like ���ׂ�����(L28) or ���؂�@(L2)
      if (strSpecialTransformWord != null) {
        vecResult.addElement(strSpecialTransformWord);
        if (booType) {
          vecResult.addElement(strSpecialTransformWord + "��");
        }
      } else {
        vecResult.addElement(strWord);
      }

      return vecResult;

    } else {
      return null;
    }

  }

  /**
   * for IsEquality
   * 
   * @param LexiconWordMeta
   *          lwm make sure a verb
   * @param lesson
   * @return this word's all possible form defined by the lesson + its form base
   */
  private static Vector getVerbFormOfLessonDefAndFormBase(String strWord, int lesson) {

    return null;

  }

  /*
   * get verb's PredictionDataMeta of this lesson wrong form number
   */
  public static PredictionDataMeta getVerbPDM(int lesson) {
    PredictionDataMeta pdmResult = new PredictionDataMeta();
    PredictionDataMeta pdm = null;
    for (int i = 0; i < eps.vecLesson.size(); i++) {
      pdm = (PredictionDataMeta) eps.vecLesson.get(i);
      int intLesson = pdm.getLesson();
      // System.out.println(intLesson);
      if (intLesson == lesson) {// same lesson
        if ((pdm.getStrWord()).trim().equals("verb")) {
          // System.out.println(pdm.toString());
          return pdm;
        }
      }
    }// end of for
    return pdmResult;
  }

  /*
   * get verb VS_DFORM's wrong form number
   */
  public static int getVerbVS_DFORMNumber(int lesson) {

    PredictionDataMeta pdm = null;
    for (int i = 0; i < eps.vecLesson.size(); i++) {
      pdm = (PredictionDataMeta) eps.vecLesson.get(i);
      int intLesson = pdm.getLesson();
      if (intLesson == lesson) {// same lesson
        if (pdm.getStrWord().equals("verb")) {
          return pdm.confusionCount;
        }

      }
    }// end of for
    return -1;
  }

  private static LexiconWordMeta getVerbBase(String strWord, int lesson) {

    return null;
  }

  /*
   * strWord is the basic form of the NQ. no noun, no verb; we know this word
   * belongs to the lesson,but not sure this word has the lesson-specific
   * prediction error; SubstitutionGroup = Aternative word() + Confusion word();
   * these are stored in an PredictionDataMeta which is constructed from an
   * QuantifierNode error Type is VDG
   */
  public static PredictionDataMeta getNQSubsGroup(String strWord, int Lesson) throws IOException {
    PredictionDataMeta pdmResult = null;
    PredictionDataMeta pdm = null;
    Vector vec = null;
    LexiconWordMeta lwm;
    String strNumber;
    String strQuantifier;
    QuantifierNode qn = searchQN(strWord);
    if (qn != null) { // it is a num+quantifier type;
      strNumber = qn.getStrNumeral();
      strQuantifier = qn.getStrQuantifier();
      // first deal with Quantifier,check if it has some subs confusion words
      pdm = getNounSubsGroup(strQuantifier, Lesson);
      if (pdm != null) {
        pdmResult = new PredictionDataMeta();
        pdmResult.setLesson(Lesson);
        pdmResult.setStrWord(qn.getStrKana());
        pdmResult.setStrKanji(qn.getStrKanji());

        Vector vecAlt = pdm.getVecAltWord();
        Vector vecSub = pdm.getVecSubWord();
        if (vecAlt != null && vecAlt.size() > 0) {
          for (int i = 0; i < vecAlt.size(); i++) {
            String strAlt = (String) vecAlt.get(i);
            lwm = searchLexiconWord(strAlt);
            if (lwm != null) {
              String str = searchQNKana(strNumber, lwm);
              if (str != null) { // get the alternative QN name
                pdmResult.addToVecAltWord(str);
                pdmResult.addCount();
              }
            } else {
              System.out.println("wrong word's alt in the NQRULEFILE,or no exist in the system");
            }

          }
        }
        if (vecSub != null && vecSub.size() > 0) {
          for (int i = 0; i < vecSub.size(); i++) {
            String strSub = (String) vecSub.get(i);
            lwm = searchLexiconWord(strSub);
            if (lwm != null) {
              String str = searchQNKana(strNumber, lwm);
              if (str != null) { // get the alternative QN name
                pdmResult.addToVecSubWord(str);
                pdmResult.addCount();
              }
            } else {
              System.out.println("wrong word's sub in the NQRULEFILE,or no exist in the system");
            }

          }
        }
      }
    }
    return pdmResult;
  }

  /*
   * strWord is the basic form of the NQ. no noun, no verb; we know this word
   * belongs to the lesson,but not sure this word has the lesson-specific
   * prediction error; SubstitutionGroup = Aternative word() + Confusion word();
   * these are stored in an PredictionDataMeta which is constructed from an
   * QuantifierNode error Type is VDG Construct new PredictionDataMeta and count
   * its subsGrop's number
   */
  public static PredictionDataMeta getNQSubsGroup(QuantifierNode qn, int Lesson) throws IOException {
    PredictionDataMeta pdmResult = null;
    PredictionDataMeta pdm = null;
    Vector vec = null;
    LexiconWordMeta lwm;
    String strNumber;
    String strQuantifier;
    // QuantifierNode qn = searchQN(strWord);
    if (qn != null) { // it is a num+quantifier type;
      strNumber = qn.getStrNumeral();
      strQuantifier = qn.getStrQuantifier();
      // first deal with Quantifier,check if it has some subs confusion words
      pdm = getNounSubsGroup(strQuantifier, Lesson);
      if (pdm != null) {// has subs
        pdmResult = new PredictionDataMeta();
        pdmResult.setLesson(Lesson);
        pdmResult.setStrWord(qn.getStrKana());
        pdmResult.setStrKanji(qn.getStrKanji());
        Vector vecAlt = pdm.getVecAltWord();
        Vector vecSub = pdm.getVecSubWord();
        if (vecAlt != null && vecAlt.size() > 0) {
          for (int i = 0; i < vecAlt.size(); i++) { // each alt word, search the
                                                    // word in the lexicon and
                                                    // the get its correct QN
                                                    // form;
            String strAlt = (String) vecAlt.get(i);
            lwm = searchLexiconWord(strAlt);
            if (lwm != null) {
              String str = searchQNKana(strNumber, lwm);
              if (str != null) { // get the alternative QN name
                pdmResult.addToVecAltWord(str);
                System.out.println(str);
                pdmResult.addCount();
              }
            } else {
              System.out.println("wrong word's alt in the NQRULEFILE,or no exist in the system");
            }

          }
        }
        if (vecSub != null && vecSub.size() > 0) {
          for (int i = 0; i < vecSub.size(); i++) {
            String strSub = (String) vecSub.get(i);
            lwm = searchLexiconWord(strSub);
            if (lwm != null) {
              String str = searchQNKana(strNumber, lwm);
              if (str != null) { // get the subsitution QN name
                pdmResult.addToVecSubWord(str);
                pdmResult.addCount();
                System.out.println(str);
              }
            } else {
              System.out.println("wrong word's sub in the NQRULEFILE,or no exist in the system");
            }

          }
        }
      }
    }
    return pdmResult;
  }

  /*
   * 
   * AddShort: always false,included the quantifier AddVoiced: only checked by
   * the first pronunciation is �� ,and confined to the japanese word(no
   * english-based words) but for quantifier,if the quantifier's first character
   * is "ha/sa/ka" row, and mainly is "ha" row. AddLong: is detected,only the
   * last character could add "��" OmitVoiced : means g-k,d-t //light weight
   * continous words(like ������) except quantifier. for all the noun, included
   * numeral+quantifier,PE means prediction errors quantity1 = numeral
   * quantity2= numeral+quantifier directly without changing the form
   */
  public static Vector getNounINVS_PCE(String strWord, boolean OmitShort, boolean AddShort, boolean OmitLong,
      boolean AddLong, boolean OmitVoiced, boolean AddVoiced, boolean quantity1, boolean quantity2) throws IOException {
    Vector<String> vecResult = new Vector<String>();
    String strWrongWord;
    String strChar = null;
    String strPreChar;
    if (strWord != null) {
      strWord = strWord.trim();
      for (int i = 0; i < strWord.length(); i++) {
        strPreChar = strChar;
        strChar = new String("" + strWord.charAt(i));
        if (i == 0) {
          if (AddVoiced && strChar.equals("��")) {
            strWrongWord = strWord.replace("��", "��");
            vecResult.addElement(strWrongWord);
          }

        } else if (0 < i && i < strWord.length() - 1) {
          if (OmitShort) {
            if (strChar.compareTo("��") == 0) {
              strWrongWord = strWord.replace("��", "");
              vecResult.addElement(strWrongWord);
            }
          }
          if (OmitVoiced) {
            strWrongWord = getOmitVoicedWord4Noun(strChar, strWord, i);
            if (strWrongWord != null && strWrongWord.length() > 0) {
              vecResult.addElement(strWrongWord);
            }
          }
          if (OmitLong) {
            strWrongWord = getOmitLongWord(strPreChar, strChar, strWord, i);
            if (strWrongWord != null && strWrongWord.length() > 0) {
              System.out.println(strWrongWord + "OmitLong");
              vecResult.addElement(strWrongWord);
            }
          }
          QuantifierNode qn = searchQN(strWord);
          if (qn != null) { // it is a num+quantifier;
            if (AddVoiced) { // special forms of num+quantifier
              strWrongWord = getAddVoicedWord4NQ(qn);
              if (strWrongWord != null || strWrongWord.length() > 0) {
                vecResult.addElement(strWrongWord);
              }
              Vector vec = getNQQuantityError(qn, quantity1, quantity2);
              if (vec != null) {
                for (int j = 0; j < vec.size(); j++) {
                  vecResult.addElement((String) vec.get(j));
                }
              }
            }

          }
        } else if (i == strWord.length() - 1) {
          if (AddLong) {
            strWrongWord = getAddLongWord(strChar, strWord);
            if (strWrongWord != null && strWrongWord.length() > 0) {
              System.out.println(strWrongWord + "AddLong");
              vecResult.addElement(strWrongWord);
            }
          }
        }
      }// end of for(int i = 0; i < strWord.length(); i++)
    }// end of if(strWord != null)

    return vecResult;

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
  public static Vector getNounPCE(String strWord, boolean OmitShort, boolean AddShort, boolean OmitLong,
      boolean AddLong, boolean OmitVoiced, boolean AddVoiced) throws IOException {
    Vector<String> vecResult = new Vector<String>();
    String strWrongWord;
    String strChar = null;
    String strPreChar;
    if (strWord != null) {
      strWord = strWord.trim();
      for (int i = 0; i < strWord.length(); i++) {
        strPreChar = strChar;
        strChar = new String("" + strWord.charAt(i));
        if (OmitVoiced) {// no care about the char position
          strWrongWord = getOmitVoicedWord4Noun(strChar, strWord, i);
          if (strWrongWord != null && strWrongWord.length() > 0) {
            vecResult.addElement(strWrongWord);
            System.out.println("OmitVoiced " + strWrongWord);
          }
        }
        if (i == 0) {
          if (AddVoiced && strChar.equals("��")) {
            LexiconWordMeta lwm = getLexiconWordNoun(strWord);
            if (lwm != null) {
              if (lwm.getStrCategory1().equals("personname") || lwm.getStrCategory2().equals("personname")) {
                // do nothing
              } else if (lwm.getStrCategory1().equals("family") || lwm.getStrCategory2().equals("family")) {
                // do nothing
              } else {
                strWrongWord = strWord.replaceFirst("��", "��");
                vecResult.addElement(strWrongWord);
                System.out.println("AddVoiced " + strWrongWord);
              }
            }

          }
        } else {
          if (OmitLong) {// happens from the second position
            strWrongWord = getOmitLongWord(strPreChar, strChar, strWord, i);
            if (strWrongWord != null && strWrongWord.length() > 0) {
              vecResult.addElement(strWrongWord);
              System.out.println("OmitLong " + strWrongWord);
            }
          }
          if (0 < i && i < strWord.length() - 1) {
            if (OmitShort) {
              if (strChar.compareTo("��") == 0) {
                strWrongWord = strWord.replace("��", "");
                vecResult.addElement(strWrongWord);
                System.out.println("OmitShort " + strWrongWord);
              }
            }
          } else if (i == strWord.length() - 1) {
            if (AddLong) {
              strWrongWord = getAddLongWord(strChar, strWord);
              if (strWrongWord != null && strWrongWord.length() > 0) {
                System.out.println("AddLong" + strWrongWord);
                vecResult.addElement(strWrongWord);
              }
            }
          }
        }
      }// end of for(int i = 0; i < strWord.length(); i++)
    }// end of if(strWord != null)

    return vecResult;

  }

  /*
   * check if this word is a numeral+quantifier if so, and if the first
   * character of its quantifier is "ha,sa,ka" row, it will appear AddVoiced
   * wrong; strWord is kana word. AddVoiced,,if the quantifier's first character
   * is "ha/sa/ka" row, and mainly is "ha" row. the same with the getNounPCE
   * included numeral+quantifier
   */
  public static Vector getNQPCE(QuantifierNode qn, boolean OmitShort, boolean AddShort, boolean OmitLong,
      boolean AddLong, boolean OmitVoiced, boolean AddVoiced) throws IOException {
    Vector<String> vecResult = new Vector<String>();
    String strWrongWord;
    String strChar = null;
    String strPreChar;
    String strWord = qn.getStrKana().trim();
    // OmitVoiced
    if (OmitVoiced) {// no care about position
      strWrongWord = getOmitVoicedWord4NQ(qn);
      if (strWrongWord != null && strWrongWord.length() > 0) {
        vecResult.addElement(strWrongWord);
        System.out.println(strWrongWord + "OmitVoiced");
      }
    }
    if (AddVoiced) {// it is a num+quantifier; no care about position
      strWrongWord = getAddVoicedWord4NQ(qn);
      if (strWrongWord != null && strWrongWord.length() > 0) {
        System.out.println(strWrongWord + "AddVoiced");
        vecResult.addElement(strWrongWord);
      }

    }
    // othet type errors
    for (int i = 0; i < strWord.length(); i++) {
      strPreChar = strChar;
      strChar = new String("" + strWord.charAt(i));
      if (0 < i && i < strWord.length() - 1) {
        if (OmitShort) {
          if (strChar.compareTo("��") == 0) {
            strWrongWord = getReplaceWord("", strWord, i);
            System.out.println(strWrongWord + "OmitShort");
            vecResult.addElement(strWrongWord);
          }
        }
        if (OmitLong) {
          strWrongWord = getOmitLongWord(strPreChar, strChar, strWord, i);
          if (strWrongWord != null && strWrongWord.length() > 0) {
            System.out.println(strWrongWord + "OmitLong");
            vecResult.addElement(strWrongWord);
          }
        }
      } else if (i == strWord.length() - 1) {
        if (AddLong) {
          strWrongWord = getAddLongWord(strChar, strWord);
          if (strWrongWord != null && strWrongWord.length() > 0) {
            System.out.println(strWrongWord + "AddLong");
            vecResult.addElement(strWrongWord);
          }
        }
      }
    }// end of for(int i = 0; i < strWord.length(); i++)

    return vecResult;
  }

  /*
   * quantity1 = number type quantity2= number+Quantity wrong type, if it should
   * be special transform format return null,when no such type error; or else
   * return responding wrong words;
   */
  public static Vector getNQQuantityError(String strWord, boolean quantity1, boolean quantity2) throws IOException {
    Vector<String> vecResult = new Vector<String>();
    QuantifierNode qn = searchQN(strWord);
    if (qn != null) { // it is a num+quantifier type;
      String strKanji = qn.getStrKanji().trim();
      String strKana = qn.getStrKana().trim();
      String strNumber = "";
      String strQuantifier = "";
      if (strKanji.length() == 1) {
        strNumber = "���イ";
        strQuantifier = "��";
      } else if (strKanji.length() > 1) {
        strNumber = strKanji.substring(0, 1);
        strQuantifier = strKanji.substring(1);
      }
      LexiconWordMeta numberWord = searchLexiconWord(strNumber);
      LexiconWordMeta quantifierWord = searchLexiconWord(strQuantifier);
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
            vecResult = null;
          } else {
            if (strWrongWord2 == null) {
              vecResult.addElement(strWrongWord1);
            } else {
              if (strKana.equals(strWrongWord2)) {
                vecResult = null;
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

  /*
   * quantity1 = number type quantity2= number+Quantity wrong type, if it should
   * be special transform format return null,when no such type error; or else
   * return responding wrong words;
   */
  public static Vector getNQQuantityError(QuantifierNode qn, boolean quantity1, boolean quantity2) throws IOException {
    Vector<String> vecResult = new Vector<String>();
    LexiconParser l = new LexiconParser();
    // QuantifierNode qn = Lexicon.checkQuantifier(strWord);
    if (qn != null) { // it is a num+quantifier type;
      String strKanji = qn.getStrKanji().trim();
      String strKana = qn.getStrKana().trim();
      String strNumber = qn.getStrNumeral();
      String strQuantifier = qn.getStrQuantifier();
      LexiconWordMeta numberWord = searchLexiconWord(strNumber);
      LexiconWordMeta quantifierWord = searchLexiconWord(strQuantifier);
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

  public static Vector getNQQuantityError(QuantifierNode qn, boolean quantity1, boolean quantity2,
      SentenceStatisticStructure sss) throws IOException {
    Vector<String> vecResult = new Vector<String>();
    LexiconParser l = new LexiconParser();
    // QuantifierNode qn = Lexicon.checkQuantifier(strWord);
    if (qn != null) { // it is a num+quantifier type;
      String strKanji = qn.getStrKanji().trim();
      String strKana = qn.getStrKana().trim();
      String strNumber = qn.getStrNumeral();
      String strQuantifier = qn.getStrQuantifier();
      LexiconWordMeta numberWord = searchLexiconWord(strNumber);
      LexiconWordMeta quantifierWord = searchLexiconWord(strQuantifier);
      if (numberWord != null && quantifierWord != null) {
        if (quantity1) {// number only
          vecResult.addElement(numberWord.getStrKana());
          sss.addDigit("QUANTITY", 1);
          if (numberWord.getStrAltkana().length() > 0) {
            vecResult.addElement(numberWord.getStrAltkana());
            sss.addDigit("QUANTITY", 1);

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
              sss.addDigit("QUANTITY2", 1);
            } else {
              if (strKana.equals(strWrongWord2)) {
                System.out.println("no quantity2");
              } else {
                vecResult.addElement(strWrongWord2);
                vecResult.addElement(strWrongWord1);

                sss.addDigit("QUANTITY2", 2);
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
    String strResult = null;
    int i = 11;
    if (strChar.equals("��") || strChar.equals("�W")) {
      strResult = null;
    } else {
      while (i <= 13) {
        for (int j = 0; j < 5; j++) {
          String strTemp1 = CharacterUtil.HIRAGANATABLE[i][j];
          String strTemp2 = CharacterUtil.KATAKANATABLE[i][j];
          if (strChar.equals(strTemp1)) {
            strResult = CharacterUtil.HIRAGANATABLE[i - 10][j];
            break;
          } else if (strChar.equals(strTemp2)) {
            strResult = CharacterUtil.KATAKANATABLE[i - 10][j];
            break;
          }
        }
        i++;
      }
    }
    return strResult;
  }

  /**
   * @param strChar
   * @param strWord
   * @return its responding wrong word or else return null,
   */
  private static String getOmitVoicedWord4Noun(String strChar, String strWord, int index) {
    String strResult = null;
    String strVoiced = getOmitVoicedChar4Noun(strChar);
    if (strVoiced != null) {
      strResult = getReplaceWord(strVoiced, strWord, index);
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
   * @param strWord
   *          the only voiced pronounce we detect is the quantifier part
   * @param index
   * @return
   */
  private static String getOmitVoicedWord4NQ(String strWord) {
    String strResult = null;
    QuantifierNode qn = searchQN(strWord);
    String strNumber;
    String strQuantifier;
    String strKana;
    if (qn != null) {
      strQuantifier = qn.getStrQuantifier();
      strKana = qn.getStrKana();
      LexiconWordMeta lwmTemp = searchLexiconWord(strQuantifier);
      if (lwmTemp != null) {
        String strQuantifierKana = lwmTemp.getStrKana();
        strQuantifierKana = strQuantifierKana.trim();
        // String strChar = strQuantifierKana.substring(0, 1);
        int intLength = strQuantifierKana.length();
        int changeCharIndex = strKana.length() - intLength;
        String strCharSource = new String("" + strKana.charAt(changeCharIndex));
        String strCharTarget = CharacterUtil.changeVoicedToLight(strCharSource);
        if (strCharTarget != null) { // if it is an voicedLight
          strResult = getReplaceWord(strCharTarget, strWord, changeCharIndex);
          System.out.println("getOmitVoicedWord4NQ---" + strResult);
        }
      } else {
        System.out.println("wrong Quantifier! [" + strQuantifier + "]");
      }

    }
    return strResult;
  }

  /**
   * @param strWord
   *          the only voiced pronounce we detect is the quantifier part
   * @return
   */
  private static String getOmitVoicedWord4NQ(QuantifierNode qn) {
    String strResult = null;
    // QuantifierNode qn = searchQN(strWord);
    String strNumber;
    String strQuantifier;
    String strKana;
    if (qn != null) {
      strQuantifier = qn.getStrQuantifier();
      strKana = qn.getStrKana();
      LexiconWordMeta lwmTemp = searchLexiconWord(strQuantifier);
      if (lwmTemp != null) {
        String strQuantifierKana = lwmTemp.getStrKana();
        strQuantifierKana = strQuantifierKana.trim();
        // String strChar = strQuantifierKana.substring(0, 1);
        int intLength = strQuantifierKana.length();
        int changeCharIndex = strKana.length() - intLength;
        String strCharSource = new String("" + strKana.charAt(changeCharIndex));
        String strCharTarget = CharacterUtil.changeVoicedToLight(strCharSource);
        if (strCharTarget != null) { // if it is an voicedLight
          strResult = getReplaceWord(strCharTarget, strKana, changeCharIndex);
          // System.out.println("getOmitVoicedWord4NQ---"+strResult);
        }
      } else {
        System.out.println("wrong Quantifier! [" + strQuantifier + "]");
      }

    }
    return strResult;
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
    } else {
      strResult = null;
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
   * check if it could connect [��] and become a long pronunciation
   * [��]colummn,[��]colummn,[��],[��]
   */
  private static boolean checkAddLongWord(String strChar) {
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

  /*
   * for noun check if it could connect [��] and become a long pronunciation
   * [��]colummn,[��]colummn,[��],[��],except the first row chars *
   */
  private static String getAddLongWordGeneral(String strChar, String strWord) {
    String strResult = null;
    if ((strChar.compareTo("��") == 0) || (strChar.compareTo("��") == 0)) {
      strResult = strWord.concat("��");
    } else if ((strChar.compareTo("��") == 0) || (strChar.compareTo("��") == 0)) {
      strResult = strWord.concat("�[");

    }// type 1
    else {
      String charColumnName2 = CharacterUtil.charColumnName(strChar);
      if (charColumnName2 != null) {
        if (charColumnName2.equals("��") || charColumnName2.equals("��")) {
          strResult = strWord.concat("��");
        } else if (charColumnName2.equals("�E") || charColumnName2.equals("�I")) {
          strResult = strWord.concat("�E");
        }
      }
    }
    return strResult;
  }

  /*
   * for noun check if it could connect [��] and become a long pronunciation
   * [��]colummn,[��]colummn,[��],[��],except [��] For katakana words it happens
   * all the time except "�["/"��"
   */
  private static String getAddLongWord(String strChar, String strWord) {
    String strResult = null;
    int i = CharacterUtil.checkCharClass(strChar);
    if (i == 1) {
      if (strChar.compareTo("�[") == 0 || strChar.compareTo("��") == 0) {
        // do nothing
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
   * @strWord, kana word,like �����҂� Given a NQ, return the addvoiced wrong
   * word of this word
   */
  public static String getAddVoicedWord4NQ(QuantifierNode qn) throws IOException {
    // LexiconParser l = new LexiconParser();
    String strKanji = qn.getStrKanji().trim();
    String strKana = qn.getStrKana().trim();
    String strNumber = "";
    String strResult = null;
    String strQuantifierLeft = "";
    if (strKanji.length() > 1) {
      String strQuantifier = strKanji.substring(1);
      LexiconWordMeta lwmTemp = searchLexiconWord(strQuantifier);
      if (lwmTemp != null) {
        String strQuantifierKana = lwmTemp.getStrKana();
        strQuantifierKana = strQuantifierKana.trim();
        String strChar = strQuantifierKana.substring(0, 1);
        int intLength = strQuantifierKana.length();
        strNumber = strKana.substring(0, strKana.length() - intLength); // the
        // string
        // without
        // quantifier;
        if (intLength > 1) {
          strQuantifierLeft = strQuantifierKana.substring(1);
        }
        if (strChar != null && strChar.length() == 1) {
          for (int j = 0; j < CharacterUtil.HIRAGANATABLE.length; j++) {
            for (int k = 0; k < CharacterUtil.HIRAGANATABLE[0].length; k++) {
              String strTemp1 = CharacterUtil.HIRAGANATABLE[j][k];
              // String strTemp2 = KATAKANATABLE[j][k];
              // ||strChar.equals(strTemp2)
              if (strChar.equals(strTemp1)) {
                String strRomaji = CharacterUtil.ROMATABLE[j][k];
                String rowName = CharacterUtil.ROMATABLE[j][0];
                if (rowName.equals("ha") || rowName.equals("sa") || rowName.equals("ta")) {
                  strResult = strNumber + CharacterUtil.HIRAGANATABLE[j + 10][k] + strQuantifierLeft;
                  break;
                }
              }
            }
          }
        }

      }
    }
    return strResult;
  }

  // //////////////////////////////////////////////////////////////
  // ////////////////////////
  // /QN processing
  // ///////////////////////
  /*
   * search QN by number + Quantifier
   */
  private static QuantifierNode searchQN(String number, LexiconWordMeta lwmQuantifier) {
    QuantifierNode qnResult = null;
    Vector vecCategory = qr.getVecQC();
    for (int i = 0; i < vecCategory.size(); i++) {
      QuantifierCategory qc = (QuantifierCategory) vecCategory.get(i);
      if (qc.getStrName().equals(lwmQuantifier.getStrKana()) || qc.getStrName().equals(lwmQuantifier.getStrKanji())) {
        Vector vecNode = qc.getVecNode();
        for (int j = 0; j < vecNode.size(); j++) {
          QuantifierNode qnTemp = (QuantifierNode) vecNode.get(j);
          if (qnTemp.getStrNumeral().equals(number)) {
            qnResult = qnTemp;
            return qnResult;
          }
        }
      }
    }
    return qnResult;

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
   * strWord = QN , maybe romaji word, correct
   */
  public static QuantifierNode getQN(String strWord) {

    QuantifierNode qnResult = null;
    QuantifierNode qn = null;
    String strSearchKanji;
    String strSearchKana;
    String strSearchRomaji;
    String strSearchAltKana;
    int intWordType = CharacterUtil.checkWordClass(strWord);
    Vector vecCategory = qr.getVecQC();
    for (int i = 0; i < vecCategory.size(); i++) {
      QuantifierCategory qc = (QuantifierCategory) vecCategory.get(i);
      Vector vecNode = qc.getVecNode();
      for (int j = 0; j < vecNode.size(); j++) {
        qn = (QuantifierNode) vecNode.get(j);
        strSearchKana = qn.getStrKana();
        strSearchAltKana = qn.getStrAltKana();
        if (intWordType == 1 || intWordType == 2) {

          if (strWord.equals(strSearchKana)) {
            qnResult = qn;
            break;
          } else {
            if (strSearchAltKana.length() > 0) {
              if (strWord.equals(strSearchKana)) {
                qn.setStrKana(strSearchAltKana);
                qn.setStrAltKana(strSearchKana);
                qn.setStrKanji(strSearchAltKana);
                qnResult = qn;
                break;
              }
            }
          }
        } else if (intWordType == 4) {
          strSearchKanji = qn.getStrKanji();
          if (strWord.equals(strSearchKanji)) {
            qnResult = qn;
            break;
          }
        } else if (intWordType == 3) {
          strSearchRomaji = CharacterUtil.wordKanaToRomaji(qn.getStrKana());
          if (strWord.equals(strSearchRomaji)) {
            qnResult = qn;
            break;
          } else {
            if (strSearchAltKana.length() > 0) {
              strSearchRomaji = CharacterUtil.wordKanaToRomaji(qn.getStrAltKana());
              if (strWord.equals(strSearchRomaji)) {
                qn.setStrKana(strSearchAltKana);
                qn.setStrAltKana(strSearchKana);
                qn.setStrKanji(strSearchAltKana);
                qnResult = qn;
                break;
              }
            }
          }
        }

      }
    }// end of for

    return qnResult;
  }

  // //////////////////////////////////////////////////////////////
  // ////////////////////////
  // /lexicon processing
  // ///////////////////////
  /**
   * @param strWord
   *          maybe romaji word, correct,the base form, no verb
   * @param lesson
   * @return get LexiconWordMeta of this word and make sure the strKana of the
   *         result is strWord ;
   */
  public static LexiconWordMeta getLexiconWord(String strWord) {
    LexiconWordMeta wmResult = null;
    LexiconWordMeta wmTemp;
    int intWordType = -1;
    String strSearchKanji;
    String strSearchKana;
    String strSearchRomaji;
    String strSearchAltKana;

    int lwmType = -1;
    if (strWord != null) {
      strWord = strWord.trim();
      intWordType = CharacterUtil.checkWordClass(strWord);
      for (int i = 0; i < l.vecAllWord.size(); i++) {
        wmTemp = l.vecAllWord.get(i);
        strSearchKana = wmTemp.getStrKana();
        strSearchAltKana = wmTemp.getStrAltkana(); // suppose verb has now
                                                   // altkana in the
                                                   // lexicon,actually so for it
                                                   // indeed no
        lwmType = wmTemp.getIntType();
        if (lwmType == 2) { // verb , special deal
          // no deal with now
        } else {
          if (intWordType == 1 || intWordType == 2) {
            if (strWord.equals(strSearchKana)) {
              wmResult = wmTemp;
              break;
            } else {
              if (strSearchAltKana.length() > 0) {
                if (strWord.equals(strSearchKana)) {
                  wmTemp.setStrKana(strSearchAltKana);
                  wmTemp.setStrAltkana(strSearchKana);
                  wmTemp.setStrKanji(strSearchAltKana);
                  wmResult = wmTemp;
                  break;
                }
              }
            }
          } else if (intWordType == 4) {
            strSearchKanji = wmTemp.getStrKanji();
            if (strWord.equals(strSearchKanji)) {
              wmResult = wmTemp;
              break;
            }
          } else if (intWordType == 3) {
            strSearchRomaji = CharacterUtil.wordKanaToRomaji(wmTemp.getStrKana());
            if (strWord.equals(strSearchRomaji)) {
              wmResult = wmTemp;
              break;
            } else {
              if (strSearchAltKana.length() > 0) {
                strSearchRomaji = CharacterUtil.wordKanaToRomaji(strSearchAltKana);
                if (strWord.equals(strSearchRomaji)) {
                  wmTemp.setStrKana(strSearchAltKana);
                  wmTemp.setStrAltkana(strSearchKana);
                  wmTemp.setStrKanji(strSearchAltKana);
                  wmResult = wmTemp;
                  break;
                }
              }

            }
          }
        }
      } // end of for
    }// end of if
    return wmResult;
  }

  /**
   * @param strWord
   *          maybe romaji word, correct,the base form, no verb ,no particle
   * @param lesson
   * @return get LexiconWordMeta of this word and make sure the strKana of the
   *         result is strWord ;
   */
  public static LexiconWordMeta getLexiconWordNoun(String strWord) {
    System.out.println("getLexiconWordNoun");
    LexiconWordMeta wmResult = null;
    LexiconWordMeta wmTemp;
    int intWordType = -1;
    String strSearchKanji;
    String strSearchKana;
    String strSearchRomaji;
    String strSearchAltKana;
    int lwmType = -1;
    if (strWord != null) {
      strWord = strWord.trim();
      intWordType = CharacterUtil.checkWordClass(strWord);
      System.out.println("intWordType is " + intWordType);
      for (int i = 0; i < l.vecAllWord.size(); i++) {
        wmTemp = l.vecAllWord.get(i);
        lwmType = wmTemp.getIntType();
        if (lwmType == Lexicon.LEX_TYPE_PARTICLE_AUXIL || lwmType == Lexicon.LEX_TYPE_VERB) {
          // verb or particle, not to handle here
        } else {
          strSearchKana = wmTemp.getStrKana();
          strSearchAltKana = wmTemp.getStrAltkana(); // suppose verb has now
                                                     // altkana in the
                                                     // lexicon,actually so for
                                                     // it indeed no
          if (intWordType == 1) {// katakana
            String str;
            if (CharacterUtil.checkWordClass(strSearchKana) == 2) {// hiragana
              // System.out.println("lwmType=2");
              str = CharacterUtil.wordHiragana2Katakana(strSearchKana);
            } else {
              str = strSearchKana;
            }
            if (strWord.equals(str)) {
              wmResult = wmTemp;
              break;
            } else {
              if (strSearchAltKana.length() > 0) {
                if (CharacterUtil.checkWordClass(strSearchAltKana) == 2) {// hiragana
                  // System.out.println("lwmType=2");
                  str = CharacterUtil.wordHiragana2Katakana(strSearchAltKana);
                } else {
                  str = strSearchAltKana;
                }
                if (strWord.equals(str)) {
                  wmTemp.setStrKana(strSearchAltKana);
                  wmTemp.setStrAltkana(strSearchKana);
                  wmTemp.setStrKanji(strSearchAltKana);
                  wmResult = wmTemp;
                  break;
                }
              }
              Vector vec = getNounHonorifics(strSearchKana);
              if (vec != null && vec.size() > 0) {
                for (int j = 0; j < vec.size(); j++) {
                  String strHonorifics = (String) vec.get(j);// here we suppose
                                                             // horific form is
                                                             // hiraganna,it is
                                                             // true;
                  // in fact,if the correct form is Katakana,and it also have
                  // the horifics, it will be the name like"�T������"�@form.
                  /*
                   * str = CharacterUtil.wordHiragana2Katakana(strHonorifics);
                   * if(strWord.equals(str)){ wmResult = wmTemp; break; }else{
                   * String str1 =
                   * CharacterUtil.wordHiragana2Katakana(strSearchKana); str1 =
                   * strHonorifics.replaceFirst(strSearchKana,str1);
                   * if(str1!=null&&strWord.equals(str1)){ wmResult = wmTemp;
                   * break; } }
                   */
                  if (strWord.equals(strHonorifics)) {
                    wmResult = wmTemp;
                    break;
                  }
                }
              }
            }
          } else if (intWordType == 2) {// hiragana
            if (strWord.equals(strSearchKana)) {
              wmResult = wmTemp;
              break;
            } else {
              if (strSearchAltKana.length() > 0) {
                if (strWord.equals(strSearchKana)) {
                  wmTemp.setStrKana(strSearchAltKana);
                  wmTemp.setStrAltkana(strSearchKana);
                  wmTemp.setStrKanji(strSearchAltKana);
                  wmResult = wmTemp;
                  break;
                }
              }
              Vector vec = getNounHonorifics(wmTemp.getStrKana());
              if (vec != null && vec.size() > 0) {
                for (int j = 0; j < vec.size(); j++) {
                  String str = (String) vec.get(j);// here we suppose horific
                                                   // form is kanna
                  // System.out.println("word "+
                  // wmTemp.getStrKana()+"NounHorifics is "+ str );
                  if (strWord.equals(str)) {
                    wmResult = wmTemp;
                    break;
                  }

                }
              }
            }
          } else if (intWordType == 4) {
            strSearchKanji = wmTemp.getStrKanji();
            if (strWord.equals(strSearchKanji)) {
              wmResult = wmTemp;
              break;
            }
          } else if (intWordType == 3) {// romaji
            strSearchRomaji = CharacterUtil.wordKanaToRomaji(wmTemp.getStrKana());
            if (strWord.equals(strSearchRomaji)) {
              wmResult = wmTemp;
              break;
            } else {
              if (strSearchAltKana.length() > 0) {
                strSearchRomaji = CharacterUtil.wordKanaToRomaji(strSearchAltKana);
                if (strWord.equals(strSearchRomaji)) {
                  wmTemp.setStrKana(strSearchAltKana);
                  wmTemp.setStrAltkana(strSearchKana);
                  wmTemp.setStrKanji(strSearchAltKana);
                  wmResult = wmTemp;
                  break;
                }
              }
              // for the honorific types
              Vector vec = getNounHonorifics(strSearchKana);
              if (vec != null && vec.size() > 0) {
                for (int j = 0; j < vec.size(); j++) {
                  String str = (String) vec.get(j);// here we suppose horific
                                                   // form is kanna,acturally it
                                                   // is;
                  strSearchRomaji = CharacterUtil.wordKanaToRomaji(str);
                  if (strWord.equals(strSearchRomaji)) {
                    wmResult = wmTemp;
                    break;
                  }

                }
              }
            }
          }
        } // end of else
      } // end of for
    }// end of if
    return wmResult;
  }

  /**
   * @param strWord
   *          maybe romaji word, correct,the base form, no verb
   * @param lesson
   * @return get LexiconWordMeta of this word and make sure the strKana of the
   *         result is strWord ; different with getLexiconWordNoun because it
   *         search in the active vector; and no typeconfuse of "��"�@and "��"
   */
  public static LexiconWordMeta getLexiconWordNounParticle(String strWord) {
    System.out.println("getLexiconWordNounParticle");
    LexiconWordMeta wmResult = null;
    LexiconWordMeta wmTemp;
    int intWordType = -1;
    String strSearchKanji;
    String strSearchKana;
    String strSearchRomaji;
    String strSearchAltKana;

    int lwmType = -1;
    if (strWord != null) {
      strWord = strWord.trim();
      intWordType = CharacterUtil.checkWordClass(strWord);
      System.out.println("intWordType is " + intWordType);
      for (int i = 0; i < l.vecActiveWord.size(); i++) {
        wmTemp = l.vecActiveWord.get(i);
        strSearchKana = wmTemp.getStrKana();
        strSearchAltKana = wmTemp.getStrAltkana(); // suppose verb has now
                                                   // altkana in the
                                                   // lexicon,actually so for it
                                                   // indeed no
        lwmType = wmTemp.getIntType();
        if (lwmType == 2) { // verb , special deal
          // no deal with now
        } else {
          if (intWordType == 1) {// katakana
            String str = strSearchKana;
            if (CharacterUtil.checkCharClass(strSearchKana) == 2) {
              str = CharacterUtil.wordHiragana2Katakana(strSearchKana);
            }
            if (strWord.equals(str)) {
              wmResult = wmTemp;
              break;
            }
          } else if (intWordType == 2) {// hiragana
            if (strWord.equals(strSearchKana)) {
              wmResult = wmTemp;
              break;
            } else {
              if (strSearchAltKana.length() > 0) {
                if (strWord.equals(strSearchKana)) {
                  wmTemp.setStrKana(strSearchAltKana);
                  wmTemp.setStrAltkana(strSearchKana);
                  wmTemp.setStrKanji(strSearchAltKana);
                  wmResult = wmTemp;
                  break;
                }
              }
              Vector vec = getNounHonorifics(wmTemp.getStrKana());
              if (vec != null && vec.size() > 0) {
                for (int j = 0; j < vec.size(); j++) {
                  String str = (String) vec.get(j);// here we suppose horific
                                                   // form is kanna
                  // System.out.println("word "+
                  // wmTemp.getStrKana()+"NounHorifics is "+ str );
                  if (strWord.equals(str)) {
                    wmResult = wmTemp;
                    break;
                  }

                }
              }
            }
          } else if (intWordType == 4) {
            strSearchKanji = wmTemp.getStrKanji();
            if (strWord.equals(strSearchKanji)) {
              wmResult = wmTemp;
              break;
            }
          } else if (intWordType == 3) {
            strSearchRomaji = CharacterUtil.wordKanaToRomaji(wmTemp.getStrKana());
            if (strWord.equals(strSearchRomaji)) {
              wmResult = wmTemp;
              break;
            } else {
              if (strSearchAltKana.length() > 0) {
                strSearchRomaji = CharacterUtil.wordKanaToRomaji(strSearchAltKana);
                if (strWord.equals(strSearchRomaji)) {
                  wmTemp.setStrKana(strSearchAltKana);
                  wmTemp.setStrAltkana(strSearchKana);
                  wmTemp.setStrKanji(strSearchAltKana);
                  wmResult = wmTemp;
                  break;
                }
              }
              Vector vec = getNounHonorifics(wmTemp.getStrKana());
              if (vec != null && vec.size() > 0) {
                for (int j = 0; j < vec.size(); j++) {
                  String str = (String) vec.get(j);// here we suppose horific
                                                   // form is kanna
                  strSearchRomaji = CharacterUtil.wordKanaToRomaji(str);
                  if (strWord.equals(str)) {
                    wmResult = wmTemp;
                    break;
                  }

                }
              }

            }
          }
        }
      } // end of for

    }// end of if
    return wmResult;
  }

  /**
   * @param strWord
   *          maybe romaji word, correct,the base form, just particle
   * @param lesson
   * @return get LexiconWordMeta of this word and make sure the strKana of the
   *         result is strWord ; different with getLexiconWordNoun because it
   *         search in the active vector; and no typeconfuse of "��"�@and "��"
   */
  public static LexiconWordMeta getLexiconWordParticle(String strWord) {
    System.out.println("getLexiconWordParticle");
    LexiconWordMeta wmResult = null;
    LexiconWordMeta wmTemp;
    int intWordType = -1;
    String strSearchKanji;
    String strSearchKana;
    String strSearchRomaji;
    String strSearchAltKana;

    int lwmType = -1;
    if (strWord != null) {
      strWord = strWord.trim();
      intWordType = CharacterUtil.checkWordClass(strWord);
      System.out.println("intWordType is " + intWordType);
      for (int i = 0; i < l.vecAllWord.size(); i++) {
        wmTemp = l.vecAllWord.get(i);
        if (wmTemp.getIntType() == Lexicon.LEX_TYPE_PARTICLE_AUXIL) {
          strSearchKana = wmTemp.getStrKana();
          strSearchAltKana = wmTemp.getStrAltkana(); // suppose verb has now
                                                     // altkana in the
                                                     // lexicon,actually so for
                                                     // it indeed no
          lwmType = wmTemp.getIntType();
          if (lwmType == 2) { // verb , special deal
            // no deal with now
          } else {
            if (intWordType == 1) {// katakana
              String str = strSearchKana;
              if (CharacterUtil.checkCharClass(strSearchKana) == 2) {
                str = CharacterUtil.wordHiragana2Katakana(strSearchKana);
              }
              if (strWord.equals(str)) {
                wmResult = wmTemp;
                break;
              }
            } else if (intWordType == 2) {// hiragana
              if (strWord.equals(strSearchKana)) {
                wmResult = wmTemp;
                break;
              } else {
                if (strSearchAltKana.length() > 0) {
                  if (strWord.equals(strSearchKana)) {
                    wmTemp.setStrKana(strSearchAltKana);
                    wmTemp.setStrAltkana(strSearchKana);
                    wmTemp.setStrKanji(strSearchAltKana);
                    wmResult = wmTemp;
                    break;
                  }
                }
                Vector vec = getNounHonorifics(wmTemp.getStrKana());
                if (vec != null && vec.size() > 0) {
                  for (int j = 0; j < vec.size(); j++) {
                    String str = (String) vec.get(j);// here we suppose horific
                                                     // form is kanna
                    // System.out.println("word "+
                    // wmTemp.getStrKana()+"NounHorifics is "+ str );
                    if (strWord.equals(str)) {
                      wmResult = wmTemp;
                      break;
                    }

                  }
                }
              }
            } else if (intWordType == 4) {
              strSearchKanji = wmTemp.getStrKanji();
              if (strWord.equals(strSearchKanji)) {
                wmResult = wmTemp;
                break;
              }
            } else if (intWordType == 3) {
              strSearchRomaji = CharacterUtil.wordKanaToRomaji(wmTemp.getStrKana());
              if (strWord.equals(strSearchRomaji)) {
                wmResult = wmTemp;
                break;
              } else {
                if (strSearchAltKana.length() > 0) {
                  strSearchRomaji = CharacterUtil.wordKanaToRomaji(strSearchAltKana);
                  if (strWord.equals(strSearchRomaji)) {
                    wmTemp.setStrKana(strSearchAltKana);
                    wmTemp.setStrAltkana(strSearchKana);
                    wmTemp.setStrKanji(strSearchAltKana);
                    wmResult = wmTemp;
                    break;
                  }
                }
                // no honorifics
              }
            }
          }

        }// end of if
      } // end of for

    }// end of if
    return wmResult;
  }

  // given we have known its a noun; search its horific form from pediction
  // rules

  /**
   * @param strWord
   *          maybe romaji word, correct,the base form, included verb
   * @param lesson
   * @return get LexiconWordMeta of this word and make sure the strKana of the
   *         result is strWord ;
   */
  public static LexiconWordMeta getLexiconWord(String strWord, int lesson) {
    LexiconWordMeta wmResult = null;
    LexiconWordMeta wmTemp;
    int intWordType = -1;
    String strSearchKanji;
    String strSearchKana;
    String strSearchRomaji;
    String strSearchAltKana;

    int lwmType = -1;
    if (strWord != null) {
      strWord = strWord.trim();
      intWordType = CharacterUtil.checkWordClass(strWord);
      for (int i = 0; i < l.vecAllWord.size(); i++) {
        wmTemp = l.vecAllWord.get(i);
        strSearchKana = wmTemp.getStrKana();
        strSearchAltKana = wmTemp.getStrAltkana(); // suppose verb has now
                                                   // altkana in the
                                                   // lexicon,actually so for it
                                                   // indeed no
        lwmType = wmTemp.getIntType();
        if (lwmType == 2) { // verb , special deal
          System.out.println("enter verb handle");
          if (isEquality(strWord, wmTemp, lesson)) {
            wmResult = wmTemp;
            break;
          }
        } else { // for other types
          if (intWordType == 1 || intWordType == 2) {
            if (strWord.equals(strSearchKana)) {
              wmResult = wmTemp;
              break;
            } else {
              if (strSearchAltKana.length() > 0) {
                if (strWord.equals(strSearchKana)) {
                  wmTemp.setStrKana(strSearchAltKana);
                  wmTemp.setStrAltkana(strSearchKana);
                  wmTemp.setStrKanji(strSearchAltKana);
                  wmResult = wmTemp;
                  break;
                }
              }
            }
          } else if (intWordType == 4) {
            strSearchKanji = wmTemp.getStrKanji();
            if (strWord.equals(strSearchKanji)) {
              wmResult = wmTemp;
              break;
            }
          } else if (intWordType == 3) {
            strSearchRomaji = CharacterUtil.wordKanaToRomaji(wmTemp.getStrKana());
            if (strWord.equals(strSearchRomaji)) {
              wmResult = wmTemp;
              break;
            } else {
              if (strSearchAltKana.length() > 0) {
                strSearchRomaji = CharacterUtil.wordKanaToRomaji(strSearchAltKana);
                if (strWord.equals(strSearchRomaji)) {
                  wmTemp.setStrKana(strSearchAltKana);
                  wmTemp.setStrAltkana(strSearchKana);
                  wmTemp.setStrKanji(strSearchAltKana);
                  wmResult = wmTemp;
                  break;
                }
              }

            }
          }
        }
      } // end of for
    }// end of if
    return wmResult;
  }

  /**
   * @param strWord
   *          maybe romaji word, correct,the base form, included verb
   * @param lesson
   * @return get LexiconWordMeta of this word and make sure the strKana of the
   *         result is strWord ;
   */
  public static LexiconWordMeta getLexiconWordVerb(String strWord, int lesson) {
    LexiconWordMeta wmResult = null;
    LexiconWordMeta wmTemp;
    int intWordType = -1;
    String strSearchKanji;
    String strSearchKana;
    String strSearchRomaji;
    String strSearchAltKana;

    int lwmType = -1;
    if (strWord != null) {
      strWord = strWord.trim();
      intWordType = CharacterUtil.checkWordClass(strWord);
      for (int i = 0; i < l.vecAllWord.size(); i++) {
        wmTemp = l.vecAllWord.get(i);
        strSearchKana = wmTemp.getStrKana();
        strSearchAltKana = wmTemp.getStrAltkana(); // suppose verb has now
                                                   // altkana in the
                                                   // lexicon,actually so for it
                                                   // indeed no
        lwmType = wmTemp.getIntType();
        if (lwmType == 2) { // verb , special deal
          System.out.println("enter verb handle");
          if (isEquality(strWord, wmTemp, lesson)) {
            wmResult = wmTemp;
            break;
          }
        }
      } // end of for
    }// end of if
    return wmResult;
  }

  private static boolean isEquality(String strWord, PredictionDataMeta pdm, int lesson) {
    int intWordType = CharacterUtil.checkWordClass(strWord);
    boolean booResult = false;
    String strSearchKanji;
    String strSearchKana;
    String strSearchRomaji;
    Vector vec;
    if (intWordType == 1 || intWordType == 2) {
      strSearchKana = pdm.getStrWord();
      // first check if strWord(target form)is the base given for test;
      if (strWord.equals(strSearchKana)) {
        booResult = true;
      }
      // secondly check if it is one of its transformation;
      vec = getVerbFormOfLessonDefAndFormBase(strSearchKana, lesson); // given
                                                                      // an kana
                                                                      // verb,
                                                                      // change
                                                                      // its
                                                                      // transform
      if (vec != null && vec.size() > 0) {
        for (int j = 0; j < vec.size(); j++) {
          if (strWord.equals((String) vec.get(j))) {
            booResult = true;
          }
        }
      }
    } else if (intWordType == 4) {
      strSearchKanji = pdm.getStrKanji();
      if (strWord.equals(strSearchKanji)) {
        booResult = true;
      }
      vec = getVerbFormOfLessonDefAndFormBase(strSearchKanji, lesson);
      if (vec != null && vec.size() > 0) {
        for (int j = 0; j < vec.size(); j++) {
          if (strWord.equals(((String) vec.get(j)).trim())) {
            booResult = true;
            break;
          }
        }
      }
    } else if (intWordType == 3) {
      strSearchKana = pdm.getStrWord();
      String strRomaji = CharacterUtil.wordKanaToRomaji(strSearchKana.trim());
      if (strWord.equals(strRomaji)) {
        booResult = true;
      }
      vec = getVerbFormOfLessonDefAndFormBase(strSearchKana, lesson); // given
                                                                      // an kana
                                                                      // verb,
                                                                      // change
                                                                      // its
                                                                      // transform
      if (vec != null && vec.size() > 0) {
        for (int j = 0; j < vec.size(); j++) {
          strSearchRomaji = CharacterUtil.wordKanaToRomaji(((String) vec.get(j)).trim());
          if (strWord.equals(strSearchRomaji)) {
            booResult = true;
            break;
          }
        }
      }
    }
    return booResult;

  }

  /**
   * @param strWord
   *          is a verb
   * @param lwm
   * @param lesson
   * @return
   */
  private static boolean isEquality(String strWord, LexiconWordMeta lwm, int lesson) {
    // logger.debug("enter isEquality");
    int intWordType = CharacterUtil.checkWordClass(strWord);
    String strSearchKanji;
    String strSearchKana;
    String strSearchRomaji;
    boolean booResult = false;
    Vector vec;
    if (intWordType == 1 || intWordType == 2) {
      // logger.debug("type is kana");
      strSearchKana = lwm.getStrKana();
      // first check if strWord(target form)is the base given for test;
      if (strWord.equals(strSearchKana)) {
        booResult = true;
      }
      // secondly check if it is one of its transformation;
      vec = getVerbFormOfLessonDefAndFormBase(lwm, lesson, "kana"); // given an
                                                                    // kana
                                                                    // verb,
                                                                    // change
                                                                    // its
                                                                    // transform
      if (vec != null && vec.size() > 0) {
        for (int j = 0; j < vec.size(); j++) {
          if (strWord.equals((String) vec.get(j))) {
            booResult = true;
          }
        }
      }
    } else if (intWordType == 4) {
      strSearchKanji = lwm.getStrKanji();
      if (strWord.equals(strSearchKanji)) {
        booResult = true;
      }
      vec = getVerbFormOfLessonDefAndFormBase(lwm, lesson, "kanji");
      if (vec != null && vec.size() > 0) {
        for (int j = 0; j < vec.size(); j++) {
          if (strWord.equals(((String) vec.get(j)).trim())) {
            booResult = true;
          }
        }
      }
    } else if (intWordType == 3) {
      strSearchKana = lwm.getStrKana();
      String strRomaji = CharacterUtil.wordKanaToRomaji(strSearchKana.trim());
      if (strWord.equals(strRomaji)) {
        booResult = true;
      }
      vec = getVerbFormOfLessonDefAndFormBase(lwm, lesson, "kana"); // given an
                                                                    // kana
                                                                    // verb,
                                                                    // change
                                                                    // its
                                                                    // transform
      if (vec != null && vec.size() > 0) {
        for (int j = 0; j < vec.size(); j++) {
          strSearchRomaji = CharacterUtil.wordKanaToRomaji(((String) vec.get(j)).trim());
          if (strWord.equals(strSearchRomaji)) {
            booResult = true;
          }
        }
      }
    }

    return booResult;
  }

  /*
   * assume strWord is kana/kanji word
   */
  public static LexiconWordMeta searchLexiconWord(String strWord) {
    LexiconWordMeta wmResult = null;
    LexiconWordMeta wmTemp;
    if (strWord != null) {
      for (int i = 0; i < l.vecAllWord.size(); i++) {
        wmTemp = l.vecAllWord.get(i);
        String strKana = wmTemp.getStrKana();
        String strKanji = wmTemp.getStrKanji();
        String strAltKana = wmTemp.getStrAltkana();
        if (strWord.equals(strKana) || strWord.equals(strKanji)) {
          wmResult = wmTemp;
          break;
        } else if (strAltKana != null && strAltKana.length() > 0 && strWord.equals(strAltKana)) {
          wmResult = wmTemp;
          break;
        }
      }
    }
    return wmResult;
  }

  /*
   * assume strWord is kana/kanji word
   */
  public static LexiconWordMeta searchLexiconWord(String strWord, Lexicon lex) {
    LexiconWordMeta wmResult = null;
    LexiconWordMeta wmTemp;
    if (strWord != null) {
      for (int i = 0; i < lex.vecAllWord.size(); i++) {
        wmTemp = lex.vecAllWord.get(i);
        String strKana = wmTemp.getStrKana();
        String strKanji = wmTemp.getStrKanji();
        String strAltKana = wmTemp.getStrAltkana();
        if (strWord.equals(strKana) || strWord.equals(strKanji)) {
          wmResult = wmTemp;
          break;
        } else if (strAltKana != null && strAltKana.length() > 0 && strWord.equals(strAltKana)) {
          wmResult = wmTemp;
          break;
        }
      }
    }
    return wmResult;
  }

  public static Vector searchWords(String strCategory, Lexicon lex) {
    // first search the altkana vetor
    Vector<LexiconWordMeta> vetResult = new Vector<LexiconWordMeta>();
    LexiconWordMeta wmTemp;
    // System.out.println("all words is "+vecAllWord.size());
    for (int i = 0; i < lex.vecAllWord.size(); i++) {
      wmTemp = lex.vecAllWord.get(i);
      String strCategory1 = wmTemp.getStrCategory1();
      String strCategory2 = wmTemp.getStrCategory2();
      if (strCategory1.equals(strCategory) || strCategory2.equals(strCategory)) {
        vetResult.addElement(wmTemp);
        // System.out.println("i---"+i+"wordid ---"+wmTemp.getId()+"word--"+wmTemp.getStrKana());
      }
    }
    return vetResult;
  }

  public Vector searchWords(String strCategory) {
    // first search the altkana vetor
    Vector<LexiconWordMeta> vetResult = new Vector<LexiconWordMeta>();
    LexiconWordMeta wmTemp;
    // System.out.println("all words is "+vecAllWord.size());
    for (int i = 0; i < l.vecAllWord.size(); i++) {
      wmTemp = l.vecAllWord.get(i);
      String strCategory1 = wmTemp.getStrCategory1();
      String strCategory2 = wmTemp.getStrCategory2();
      if (strCategory1.equals(strCategory) || strCategory2.equals(strCategory)) {
        vetResult.addElement(wmTemp);
        // System.out.println("i---"+i+"wordid ---"+wmTemp.getId()+"word--"+wmTemp.getStrKana());
      }
    }
    return vetResult;
  }

  public static Lexicon getL() {
    return l;
  }

  public static void setL(Lexicon l) {
    ErrorPredictionProcess.l = l;
  }

  public static ErrorPredictionStruct getEps() {
    return eps;
  }

  public static void setEps(ErrorPredictionStruct eps) {
    ErrorPredictionProcess.eps = eps;
  }

  public static QuantifierRule getQr() {
    return qr;
  }

  public static void setQr(QuantifierRule qr) {
    ErrorPredictionProcess.qr = qr;
  }

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    // TODO Auto-generated method stub
    ErrorPredictionProcess epp = new ErrorPredictionProcess();
    // epp.getWordPrediction("�����܂��傤", 6, true);// when test this, the result
    // is wrong, because we have no such word like "�H�ׂ�����"�@in the lexicon

    // PredictionDataMeta pdm = epp.getVerbPDM(22);
    // System.out.println(pdm.toString());
    // epp.getWordPrediction("�ɂ܂�", 21, false);
    // epp.getWordPrediction("��", 28, false); //if has two particle "��" in the
    // prediction rule , it just search the first one;
    // getVerbFormOfLessonDefAndFormBase
    // Vector v = epp.getVerbFormOfLessonDef("���ׂ�",28);
    // for (int i = 0; i < v.size(); i++) {
    // System.out.println(v.get(i));
    // }
    // epp.getWordPrediction("�����˂���", 1,false);
    // epp.getWordPrediction("������", 1,false);
    // �s�U
    // epp.getWordPrediction("�s�U", 1, false);

    // epp.getWordPrediction("ojisan", 1,false);//VDG + INVS_WFORM is [1];
    // OmitLong ��������(this is the ����������'s omitlong error,actually it is
    // the right source word);so the following not existINVDG_PCE+INVDG_WFORM is
    // [1]; all is [2]
    // have been handled;
    // epp.getWordPrediction("�����S", 1,false,false);// add
    // CharacterUtil.wordHiragana2Katakana("���")

    // String str = CharacterUtil.wordHiragana2Katakana("���");
    // System.out.println(str);
    // epp.getWordPrediction("�T��", 1,false);
    /*
     * addlong for english-based word is different with the nomal japanese word;
     * like �T������--�T���[����; �R�[��--�R�[���[ doitsujin,4 in our
     * approach,it also could create AddLong error ,also not proper
     */
    // epp.getWordPrediction("to", 6,false);
    // epp.getWordPrediction("kabin", 3,false);
    // epp.getWordPrediction("�x�b�h", 3,false);
    /*
     * does it neccesary to add rule about sub with ����/�N
     */
    // epp.getWordPrediction("���Ȃ�����", 3,false,false);
    // epp.getWordPrediction("�T������", 3,false,false);
    // String str =
    // CharacterUtil.wordHiragana2Katakana("������");//�C�C�C�`�S???---modified
    // System.out.println(str);
    // epp.getWordPrediction("bi-ru", 3,false,false);//acturally no such romaji
    // exists,but we should handle it for analyzing the data;
    // �ڂ���,������,��,��,�낤����,�����Ԃ�,�G�b�Z�C; ---������- have modified--
    // epp.getWordPrediction("�낤����",28,false,false);
    epp.getWordPrediction("�����҂�", 21, false, false);
  }

}
