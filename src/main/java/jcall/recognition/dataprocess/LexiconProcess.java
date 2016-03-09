/**
 * Created on 2007/06/15
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.IOException;
import java.util.Vector;

import jcall.recognition.languagemodel.FormatVerb;
import jcall.recognition.util.CharacterUtil;

import org.apache.log4j.Logger;

public class LexiconProcess {

  static Logger logger = Logger.getLogger(LexiconProcess.class.getName());

  public ErrorPredictionParser epp;

  public QuantifierRuleParser qrp;
  public LexiconParser lp;

  // static QuantifierRule qr;
  static ErrorPredictionStruct eps;
  public static Lexicon l;
  static final String NQRULEFILE = "./Data/QuantiferRules.xml";
  static final String PREDICTIONFILE = "./Data/LessonErrorPrediction.txt";
  static final String LEXICONFILE = "./Data/newlexicon.txt";
  static int intTotal;
  public static final String[] VOICEDROW = { "��", "��", "��", "��", "��" };

  public LexiconProcess() throws IOException {
    init();

  }

  private void init() throws IOException {
    epp = new ErrorPredictionParser();
    // qrp = new QuantifierRuleParser();
    lp = new LexiconParser();
    // qr = qrp.loadFromFile(NQRULEFILE);
    eps = epp.loadDataFromFile(PREDICTIONFILE);
    l = lp.loadData(LEXICONFILE);
    intTotal = 0;
  }

  /**
   * @param strWord
   *          maybe romaji word, correct,the base form, only verb
   * @param lesson
   * @return get LexiconWordMeta of this word and make sure the strKana of the
   *         result is strWord ;
   */
  public LexiconWordMeta getLexiconWordVerb(String strWord, int lesson) {
    // logger.debug("enter getLexiconWordVerb");
    LexiconWordMeta lwm;
    if (strWord != null) {
      strWord = strWord.trim();
      for (int i = 0; i < l.vecVerb.size(); i++) {
        lwm = l.vecVerb.get(i);
        // strSearchAltKana = wsmTemp.getStrAltkana(); //suppose verb has now
        // altkana in the lexicon,actually so for it indeed no
        logger.debug("pick up a verb:" + lwm.getStrKana());

        if (isEquality(strWord, lwm, lesson)) {
          return lwm;
        }
      } // end of for
    }// end of if

    return null;
  }

  /**
   * @param strWord
   *          is a verb
   * @param lwm
   * @param lesson
   * @return
   */
  private static boolean isEquality(String strWord, LexiconWordMeta lwm, int lesson) {
    logger.debug("enter isEquality");
    int intWordType = CharacterUtil.checkWordClass(strWord);
    String strSearchKanji;
    String strSearchKana;
    String strSearchRomaji;
    boolean booResult = false;
    Vector vec;
    if (intWordType == 1 || intWordType == 2) {
      logger.debug("type is kana");
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
          logger.debug("word:" + lwm.strKana + " all format:" + (String) vec.get(j));
          if (strWord.equals((String) vec.get(j))) {
            booResult = true;
            break;
          }
        }
      }
    } else if (intWordType == 4) { // kanji
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
    } else if (intWordType == 3) {// romaji
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

  /**
   * @param strWord
   *          kana or kanji, base form
   * @param lesson
   * @return this word's all possible form defined by the lesson ; kana format
   */
  private Vector getVerbFormOfLessonDef(LexiconWordMeta lwm, int lesson, String kanaKanji) {
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
      logger.error("wrong, in getVerbFormOfLessonDef ;pramameter kanaKanji is not IN(kana,kanji)");
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

  public static Vector getErrVerbFormOfLessonDefNew(LexiconWordMeta lwm, int lesson, String kanaKanji,
      boolean booTense, boolean booNegative) {
    Vector<String> vecResult = new Vector<String>();
    logger.debug("enter getErrVerbFormOfLessonDefNew;");
    logger.debug("parameter type is: " + kanaKanji + " booTense: " + booTense + " booNegative: " + booNegative);
    String strWord = "";
    Vector<String> vTemp = new Vector<String>();
    if (kanaKanji.equalsIgnoreCase("kana")) {
      strWord = lwm.getStrKana();
    } else if (kanaKanji.equalsIgnoreCase("kanji")) {
      strWord = lwm.getStrKanji();
    } else {
      logger.error("wrong, in getErrVerbFormOfLessonDefNew ;pramameter kanaKanji is not IN(kana,kanji)");
      return vecResult;
    }

    String group = lwm.getStrAttribute();
    logger.debug("verb group: " + group);

    boolean booType = false;
    boolean booTenseChange = false;
    // determine sentence form
    PredictionDataMeta pdmVerb = getVerbPDM(lesson);
    if (pdmVerb != null) {
      // logger.debug("find pdm");
      Vector vecType = pdmVerb.getVecVerbType();
      for (int i = 0; i < vecType.size(); i++) {
        String strTemp = (String) vecType.get(i);
        if (strTemp.equals("type")) {
          booType = true;
          logger.debug("booType: " + true);
        }
        if (strTemp.equals("tense")) {
          booTenseChange = true;
          logger.debug("booTenseChange: " + true);
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
        logger.debug("a special form,strRef.length()>0: " + strRef);
        if (strRef.equals("TETA")) {
          Vector vec = pdmVerb.getVecVerbInvalidFrom();
          if (vec != null && vec.size() > 0) {
            // deal with different kinds of errors of these
            for (int i = 0; i < vec.size(); i++) {
              String str = (String) vec.get(i);
              String str2 = FormatVerb.verbToOrder(strWord, booNegative, group, str);
              if (str2 != null && str2.length() > 0) {
                vTemp.addElement(str2);
              }

            }
          }
          return vTemp;
        } else if (strRef.equals("SE")) {
          Vector vec = pdmVerb.getVecVerbInvalidFrom();
          if (vec != null && vec.size() > 0) {
            // deal with different kinds of errors of these
            for (int i = 0; i < vec.size(); i++) {
              String str = (String) vec.get(i);
              if (str.equalsIgnoreCase("SaseSe")) {
                strSpecialTransformWord = FormatVerb.verbToSe(strWord, group, "SaseSe");
                if (strSpecialTransformWord != null) {
                  logger.debug("SE: " + strSpecialTransformWord);
                  // String stri =
                  // FormatVerb.verbToGeneralPoliteOne(strSpecialTransformWord,
                  // booTense, booNegative,"Group2");
                  Vector vecSe = FormatVerb.verbToGeneralPolite(strSpecialTransformWord, booTenseChange, booNegative,
                      "Group2");
                  if (vecSe != null && vecSe.size() > 0) {
                    for (int j = 0; j < vecSe.size(); j++) {
                      String stri = (String) vecSe.get(j);
                      vTemp.addElement(stri);
                    }
                  }
                  // vTemp.addElement(stri);
                }
              }
              // else if (str.equalsIgnoreCase("T1T2")){
              // strSpecialTransformWord =
              // FormatVerb.verbToSe(strWord,group,"T1T2");
              // if(strSpecialTransformWord != null){
              // logger.debug("SE: "+strSpecialTransformWord);
              // String stri =
              // FormatVerb.verbToGeneralPoliteOne(strSpecialTransformWord,
              // booTense, booNegative,"Group2");
              // vTemp.addElement(stri);
              // }
              // }
            }
          }
          // System.out.println(strSpecialTransformWord
          // +"is the se format of ");
        } else if (strRef.equals("Masyou")) {
          Vector vec = pdmVerb.getVecVerbInvalidFrom();
          if (vec != null && vec.size() > 0) {
            // deal with different kinds of errors of these
            for (int i = 0; i < vec.size(); i++) {
              String str = (String) vec.get(i);
              if (str.equalsIgnoreCase("Masho")) {
                strSpecialTransformWordMasyou = FormatVerb.verbToInvitationPolite(strWord, group, "Masho");
                if (strSpecialTransformWordMasyou != null) {
                  // logger.debug("Masyou: "+strSpecialTransformWordMasyou);
                  vTemp.addElement(strSpecialTransformWordMasyou);
                }
              } else if (str.equalsIgnoreCase("T1T2")) {
                strSpecialTransformWordMasyou = FormatVerb.verbToInvitationPolite(strWord, group, "T1T2");
                if (strSpecialTransformWordMasyou != null) {
                  // logger.debug("Masyou: "+strSpecialTransformWordMasyou);
                  vTemp.addElement(strSpecialTransformWordMasyou);
                }
              }

            }
          }
        }// end else if(strRef.equals("Masyou"))
      } // end if(strRef.length()>0){

      /*
       * still need to modify, for the TETA can't have tense changing, but here
       * we just known it won't
       */
      if (vTemp != null && vTemp.size() > 0) {
        for (int i = 0; i < vTemp.size(); i++) {
          String str = (String) vTemp.get(i);
          vecResult.addElement(str);
          if (booType) {
            vecResult.addElement(str + "��");
          }
        }
      }
    }
    // add the base tranformation form of this word defined in this lesson like
    // ���ׂ�����(L28) or ���؂�@(L2)

    return vecResult;

  }

  /**
   * for IsEquality
   * 
   * @param LexiconWordMeta
   *          lwm make sure a verb
   * @param lesson
   * @return this word's all possible form defined by the lesson + its form base
   */
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
   * @param lesson
   * @return pdm
   */
  public static PredictionDataMeta getVerbPDM(int lesson) {
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
    return null;
  }

  /**
   * @param strWord
   *          maybe romaji word, correct,the base form, just particle
   * @param lesson
   * @return get LexiconWordMeta of this word and make sure the strKana of the
   *         result is strWord ; different with getLexiconWordNoun, escape from
   *         typeconfuse of "��"�@and "��"
   */
  public LexiconWordMeta getLexiconWordParticle(String strWord) {
    // logger.debug("enter getLexiconWordParticle:"+strWord);
    LexiconWordMeta wsmResult = null;
    LexiconWordMeta wsmTemp = null;
    if (strWord != null) {
      strWord = strWord.trim();
      int intWordType = CharacterUtil.checkWordClass(strWord);
      for (int i = 0; i < l.vecParticle.size(); i++) {
        wsmTemp = l.vecParticle.get(i);
        if (IsSameWord(wsmTemp, strWord, intWordType)) {
          wsmResult = wsmTemp;
        }
      } // end of for
    }// end of if
    return wsmResult;
  }

  /**
   * @param strWord
   *          maybe romaji word, correct,the base form, no verb ,no
   *          particle,noNQ included pronoun,and any one which type between
   *          11-18
   * @return get LexiconWordMeta of this word and make sure the strKana of the
   *         result is strWord ;
   */
  public LexiconWordMeta getLexiconWordNoun(String strWord) {
    logger.debug("enter getLexiconWordNoun: " + strWord);
    LexiconWordMeta wsmResult = null;
    LexiconWordMeta wsmTemp = null;
    if (strWord != null) {
      strWord = strWord.trim();
      int intWordType = CharacterUtil.checkWordClass(strWord);
      logger.debug("target word WordType: " + intWordType);
      if (strWord.equalsIgnoreCase("�����Ⴓ��") || strWord.equalsIgnoreCase("�������Ⴓ��")) {
        logger.debug("strWord, base form: ������");
        strWord = "������";
      } else if (strWord.equalsIgnoreCase("��҂���")) {
        logger.debug("strWord, base form: ������");
        strWord = "���";
      } else if (strWord.equalsIgnoreCase("���񂲂ӂ���")) {
        strWord = "���񂲂�";
        logger.debug("strWord, base form: ���񂲂�");
      } else if (strWord.equalsIgnoreCase("�Ō�w����")) {
        strWord = "�Ō�w";
        logger.debug("strWord, base form: ���񂲂�");
      } else if (strWord.equals("�͂����Ⴓ��")) {
        logger.debug("strWord, base form: �͂�����");
        strWord = "�͂�����";
      } else if (strWord.equalsIgnoreCase("����҂���")) {
        logger.debug("strWord, base form: �͂�����");
        strWord = "�����";
      } else if (strWord.equalsIgnoreCase("haishasan")) {
        logger.debug("strWord, base form: �͂�����");
        strWord = "haisha";
      }
      logger.debug("no special word used");
      // noun_personname
      for (int i = 0; i < l.getVecPersonName().size(); i++) {
        wsmTemp = (LexiconWordMeta) l.getVecPersonName().get(i);
        logger.debug("personname group: " + wsmTemp.getStrKana());
        if (IsSamePersonName(wsmTemp, strWord, intWordType)) {
          return wsmTemp;
        }

      } // end of for

      if (wsmResult == null) {
        // prononoun
        logger.debug("not a personname,enter into prononoun");;
        for (int i = 0; i < l.getVecPronoun().size(); i++) {
          wsmTemp = (LexiconWordMeta) l.getVecPronoun().get(i);
          // System.out.println("pronoun "+wsmTemp.strKana +"strWord "+strWord);
          if (IsSameWord(wsmTemp, strWord, intWordType)) {
            // System.out.println("got one "+wsmTemp.strKana);
            wsmResult = wsmTemp;
            break;
          }
        } // end of for
      }

      if (wsmResult == null) {
        logger.debug("not a prononoun,check general noun");
        // general noun
        for (int i = 0; i < l.getVecNoun().size(); i++) {
          wsmTemp = (LexiconWordMeta) l.getVecNoun().get(i);
          if (IsSameWord(wsmTemp, strWord, intWordType)) {
            wsmResult = wsmTemp;
            break;
          }
        } // end of for
      }

      if (wsmResult == null) {
        logger.debug("evev not a general noun,check o+noun");
        // general noun
        for (int i = 0; i < l.getVecNoun().size(); i++) {
          wsmTemp = (LexiconWordMeta) l.getVecNoun().get(i);
          if (IsSameStem(wsmTemp, strWord, intWordType)) {
            wsmResult = wsmTemp;
            logger.debug("find same stem of o+noun: " + wsmTemp.strKana);
            break;
          }
        } // end of for
      }

    }// end of if
    return wsmResult;
  }

  /**
   * for CALL_SentenceGrammar
   * 
   * @param strWord
   *          maybe romaji word, correct,the base form, no verb ,no
   *          particle,noNQ included pronoun,and any one which type between
   *          11-18
   * @return get LexiconWordMeta of this word and make sure the strKana of the
   *         result is strWord ;
   *
   */
  public LexiconWordMeta getLexiconWordNounFull(String strWord) {
    logger.debug("enter getLexiconWordNoun: " + strWord);
    LexiconWordMeta wsmResult = null;
    LexiconWordMeta wsmTemp = null;
    if (strWord != null) {
      strWord = strWord.trim();
      int intWordType = CharacterUtil.checkWordClass(strWord);
      logger.debug("target word WordType: " + intWordType);
      if (strWord.equalsIgnoreCase("�����Ⴓ��") || strWord.equalsIgnoreCase("�������Ⴓ��")) {
        logger.debug("strWord, base form: ������");
        strWord = "������";
      } else if (strWord.equalsIgnoreCase("��҂���")) {
        logger.debug("strWord, base form: ������");
        strWord = "���";
      } else if (strWord.equalsIgnoreCase("���񂲂ӂ���")) {
        strWord = "���񂲂�";
        logger.debug("strWord, base form: ���񂲂�");
      } else if (strWord.equalsIgnoreCase("�Ō�w����")) {
        strWord = "�Ō�w";
        logger.debug("strWord, base form: ���񂲂�");
      } else if (strWord.equals("�͂����Ⴓ��")) {
        logger.debug("strWord, base form: �͂�����");
        strWord = "�͂�����";
      } else if (strWord.equalsIgnoreCase("����҂���")) {
        logger.debug("strWord, base form: �͂�����");
        strWord = "�����";
      } else if (strWord.equalsIgnoreCase("haishasan")) {
        logger.debug("strWord, base form: �͂�����");
        strWord = "haisha";
      }
      logger.debug("no special word used");
      // noun_personname
      for (int i = 0; i < l.getVecPersonName().size(); i++) {
        wsmTemp = (LexiconWordMeta) l.getVecPersonName().get(i);
        logger.debug("personname group: " + wsmTemp.getStrKana());
        wsmResult = getSamePersonName(wsmTemp, strWord, intWordType);
        if (wsmResult != null) {

          return wsmResult;
        }

      } // end of for

      if (wsmResult == null) {
        // prononoun
        logger.debug("not a personname,enter into prononoun");;
        for (int i = 0; i < l.getVecPronoun().size(); i++) {
          wsmTemp = (LexiconWordMeta) l.getVecPronoun().get(i);
          // System.out.println("pronoun "+wsmTemp.strKana +"strWord "+strWord);
          if (IsSameWord(wsmTemp, strWord, intWordType)) {
            // System.out.println("got one "+wsmTemp.strKana);
            wsmResult = wsmTemp;
            break;
          }
        } // end of for
      }

      if (wsmResult == null) {
        logger.debug("not a prononoun,check general noun");
        // general noun
        for (int i = 0; i < l.getVecNoun().size(); i++) {
          wsmTemp = (LexiconWordMeta) l.getVecNoun().get(i);
          if (IsSameWord(wsmTemp, strWord, intWordType)) {
            wsmResult = wsmTemp;
            break;
          }
        } // end of for
      }

      if (wsmResult == null) {
        logger.debug("evev not a general noun,check o+noun");
        // general noun
        for (int i = 0; i < l.getVecNoun().size(); i++) {
          wsmTemp = (LexiconWordMeta) l.getVecNoun().get(i);
          if (IsSameStem(wsmTemp, strWord, intWordType)) {

            wsmResult = new LexiconWordMeta();
            wsmResult.setId(wsmTemp.getId());
            wsmResult.setComponentID(wsmTemp.getComponentID());
            wsmResult.setIntType(wsmTemp.getIntType());
            wsmResult.setStrAttribute(wsmTemp.getStrAttribute());
            wsmResult.setStrCategory1(wsmTemp.getStrCategory1());
            wsmResult.setStrCategory1(wsmTemp.getStrCategory2());
            wsmResult.setStrKana("��" + wsmTemp.getStrKana().trim());
            wsmResult.setStrKanji("��" + wsmTemp.getStrKanji().trim());

            logger.debug("find same stem of o+noun: " + wsmTemp.strKana);
            break;
          }
        } // end of for
      }

    }// end of if
    return wsmResult;
  }

  private static LexiconWordMeta getSamePersonName(LexiconWordMeta lwm, String strWord, int intWordType) {
    logger.debug("enter getSamePersonName ");
    LexiconWordMeta lwmResult = null;
    boolean booResult = false;
    String strSearchRomaji;
    String strSearchKanji;
    String strSuffix;
    String strKana = "";
    String strKanji = "";
    String strSearchKana = lwm.getStrKana();
    // suppose personname has no altkana in the lexicon,actually so for it
    // indeed no
    if (intWordType == 1) {// katakana
      String str;
      if (CharacterUtil.checkWordClass(strSearchKana) == 2) {// hiragana
        logger.debug("dic word type: " + "2");
        str = CharacterUtil.wordHiragana2Katakana(strSearchKana);
      } else {
        str = strSearchKana;
      }
      if (strWord.equals(str)) {
        logger.debug("find name: " + str);
        booResult = true;
      } else { // if strWord is personname+suffix"����"+etc
        if (strWord.startsWith(str)) {
          logger.debug("find name: " + str);
          String suffix = strWord.replace(str, "").trim();
          strKana = lwm.getStrKana() + suffix;

          strKanji = lwm.getStrKanji() + suffix;
          lwmResult = lwm;
          lwmResult.setStrKana(strKana);
          lwmResult.setStrKanji(strKanji);
          booResult = true;
        }
      }
    } else if (intWordType == 2) {// hiragana
      logger.debug("hiragana");
      if (strWord.equals(strSearchKana)) {
        booResult = true;
      } else { // if strWord is personname+suffix"����"+etc
        if (strWord.startsWith(strSearchKana)) {
          logger.debug("find name: " + strSearchKana);
          String suffix = strWord.replace(strSearchKana, "").trim();
          strKana = lwm.getStrKana() + suffix;

          strKanji = lwm.getStrKanji() + suffix;
          lwmResult = lwm;
          lwmResult.setStrKana(strKana);
          lwmResult.setStrKanji(strKanji);
          booResult = true;

          booResult = true;
        }
      }
    } else if (intWordType == 4) { // kanji
      strSearchKanji = lwm.getStrKanji();
      if (strWord.equals(strSearchKanji)) {
        booResult = true;
      } else { // if strWord is personname+suffix"����"+etc
        if (strWord.startsWith(strSearchKana)) {
          String suffix = strWord.replace(strSearchKana, "").trim();
          strKana = lwm.getStrKana() + suffix;

          strKanji = lwm.getStrKanji() + suffix;
          lwmResult = lwm;
          lwmResult.setStrKana(strKana);
          lwmResult.setStrKanji(strKanji);
          booResult = true;
        }
      }
    } else if (intWordType == 3) {// romaji
      strSearchRomaji = CharacterUtil.wordKanaToRomaji(lwm.getStrKana());
      if (strWord.equals(strSearchRomaji)) {
        booResult = true;
      } else { // if strWord is personname+suffix"����"+etc
        if (strWord.startsWith(strSearchRomaji)) {
          booResult = true;
        }
      }
    } // end of else
    return lwmResult;

  }

  /**
   * for noun, like o+noun[dic]
   * 
   * @param wsmTemp
   *          base form as in the lexicon, maybe be written in romaji, correct
   * @param strWord
   *          searching word,base form as in the lexicon, maybe be written in
   *          romaji, correct
   * @return if success,true; else false.
   */
  private static boolean IsSameStem(LexiconWordMeta wsmTemp, String strWord, int intWordType) {
    // logger.debug("enter IsSameStem: "+wsmTemp.getStrKana());
    boolean booResult = false;
    String strSearchRomaji;
    String strSearchKanji;
    String strSearchKana = wsmTemp.getStrKana();
    // String strSearchAltKana = wsmTemp.getStrAltkana();
    // int intWordType = CharacterUtil.checkWordClass(strWord);
    // katakana, no english_stem word will plus prefix, so do nothing here
    // if(intWordType==1 ){
    // }else
    if (intWordType == 2) {// hiragana
      if (strWord.startsWith("��")) {
        String str = strWord.substring(1);
        if (str.equals(strSearchKana)) {
          booResult = true;
        }
        // else{//in our case,not necessary to check strSearchAltKana
      }

    } else if (intWordType == 4) {// kanji
      strSearchKanji = wsmTemp.getStrKanji();
      if (strWord.startsWith("��")) {
        String str = strWord.substring(1);
        if (str.equals(strSearchKanji)) {
          booResult = true;
        }
      }

    } else if (intWordType == 3) {// romaji

      if (strWord.startsWith("o")) {
        strSearchRomaji = CharacterUtil.wordKanaToRomaji(wsmTemp.getStrKana());
        String str = strWord.substring(1);
        if (str.equals(strSearchRomaji)) {
          booResult = true;
        }
      }
    } // end of else
    return booResult;
  }

  /**
   * @param strWord
   *          = NQ , maybe romaji word, correct
   * @return get LexiconWordMeta of this word and make sure the strKana of the
   *         result is strWord ;
   */

  public LexiconWordMeta getNQ(String strWord) {
    // LexiconWordMeta lwmResult =null;
    LexiconWordMeta lwm = null;
    int intWordType = CharacterUtil.checkWordClass(strWord);
    // logger.debug("enter getNQ, target word type: "+strWord);
    for (int i = 0; i < l.getVecNQ().size(); i++) {
      lwm = (LexiconWordMeta) l.getVecNQ().get(i);
      if (IsSameWord(lwm, strWord, intWordType)) {
        return lwm;
      }
    }// end of for

    return null;
  }

  /**
   * @param wsmTemp
   *          base form as in the lexicon, maybe be written in romaji, correct
   * @param strWord
   *          searching word,base form as in the lexicon, maybe be written in
   *          romaji, correct
   * @return if success,true; else false.
   */
  private static boolean IsSameWord(LexiconWordMeta wsmTemp, String strWord, int intWordType) {
    boolean booResult = false;
    String strSearchRomaji;
    String strSearchKanji;
    String strSearchKana = wsmTemp.getStrKana();
    String strSearchAltKana = wsmTemp.getStrAltkana(); // suppose verb has now
                                                       // altkana in the
                                                       // lexicon,actually so
                                                       // for it indeed no
    // logger.debug("enter IsSameWord: searchwordKana ["+strSearchKana+"] searchword altkana ["+strSearchAltKana+"]");
    // int intWordType = CharacterUtil.checkWordClass(strWord);
    // System.out.println("intWordType is "+intWordType);
    // logger.debug("intWordType is "+intWordType);

    if (intWordType == 1) {// katakana
    // logger.debug("intWordType�@"+1);
      String str;
      if (CharacterUtil.checkWordClass(strSearchKana) == 2) {// hiragana
        str = CharacterUtil.wordHiragana2Katakana(strSearchKana);
      } else {
        str = strSearchKana;
      }
      // logger.debug("compare standard of strKana -str�@"+str);
      if (strWord.equals(str)) {
        booResult = true;
      } else { // deal with its altKana
      // logger.debug("not strKana�@");
        if (strSearchAltKana.length() > 0) {
          if (CharacterUtil.checkWordClass(strSearchAltKana) == 2) {// hiragana
            str = CharacterUtil.wordHiragana2Katakana(strSearchAltKana);
          } else {
            str = strSearchAltKana;
          }
          // logger.debug("compare standard of strSearchAltKana -str�@"+str);
          if (strWord.equals(str)) {
            // logger.debug("equal with strSearchAltKana");
            // wsmTemp.setStrKana(strSearchAltKana);
            // wsmTemp.setStrAltkana(strSearchKana);
            // wsmTemp.setStrKanji(strSearchAltKana);
            return true;
          }
        }
      }
    } else if (intWordType == 2) {// hiragana
      if (strWord.equals(strSearchKana)) {
        booResult = true;
      } else {
        if (strSearchAltKana.length() > 0) {
          if (strWord.equals(strSearchAltKana)) {
            // wsmTemp.setStrKana(strSearchAltKana);
            // wsmTemp.setStrAltkana(strSearchKana);
            // wsmTemp.setStrKanji(strSearchAltKana);
            return true;
          }
        }
      }
    } else if (intWordType == 4) {// kanji
      strSearchKanji = wsmTemp.getStrKanji();
      if (strWord.equals(strSearchKanji)) {
        return true;
      }
    } else if (intWordType == 3) {// romaji
      strSearchRomaji = CharacterUtil.wordKanaToRomaji(wsmTemp.getStrKana());
      if (strWord.equals(strSearchRomaji)) {
        booResult = true;
      } else {
        if (strSearchAltKana.length() > 0) {
          strSearchRomaji = CharacterUtil.wordKanaToRomaji(strSearchAltKana);
          if (strWord.equals(strSearchRomaji)) {
            // wsmTemp.setStrKana(strSearchAltKana);
            // wsmTemp.setStrAltkana(strSearchKana);
            // wsmTemp.setStrKanji(strSearchAltKana);
            return true;
          }
        }
      }
    } // end of else
    return booResult;
  }

  /**
   * @param lwm
   *          base form as in the lexicon, maybe be written in romaji, correct
   * @param strWord
   *          searching word: base form or derive from lexicon word by adding
   *          suffix name form, maybe be written in romaji, correct
   * @return if success,true; else false.
   */
  private static boolean IsSamePersonName(LexiconWordMeta lwm, String strWord, int intWordType) {
    logger.debug("enter IsSamePersonName ");
    boolean booResult = false;
    String strSearchRomaji;
    String strSearchKanji;
    String strSuffix;
    String strSearchKana = lwm.getStrKana();
    // suppose personname has no altkana in the lexicon,actually so for it
    // indeed no
    if (intWordType == 1) {// katakana
      String str;
      if (CharacterUtil.checkWordClass(strSearchKana) == 2) {// hiragana
        logger.debug("dic word type: " + "2");
        str = CharacterUtil.wordHiragana2Katakana(strSearchKana);
      } else {
        str = strSearchKana;
      }
      if (strWord.equals(str)) {
        logger.debug("find name: " + str);
        booResult = true;
      } else { // if strWord is personname+suffix"����"+etc
        if (strWord.startsWith(str)) {
          logger.debug("find name: " + str);
          booResult = true;
        }
      }
    } else if (intWordType == 2) {// hiragana
      // logger.debug("hiragana");
      if (strWord.equals(strSearchKana)) {
        booResult = true;
      } else { // if strWord is personname+suffix"����"+etc
        if (strWord.startsWith(strSearchKana)) {
          booResult = true;
        }
      }
    } else if (intWordType == 4) { // kanji
      strSearchKanji = lwm.getStrKanji();
      if (strWord.equals(strSearchKanji)) {
        booResult = true;
      } else { // if strWord is personname+suffix"����"+etc
        if (strWord.startsWith(strSearchKana)) {
          booResult = true;
        }
      }
    } else if (intWordType == 3) {// romaji
      strSearchRomaji = CharacterUtil.wordKanaToRomaji(lwm.getStrKana());
      if (strWord.equals(strSearchRomaji)) {
        booResult = true;
      } else { // if strWord is personname+suffix"����"+etc
        if (strWord.startsWith(strSearchRomaji)) {
          booResult = true;
        }
      }
    } // end of else
    return booResult;

  }

  // ////////////////////////////////////////////////////////////////////////
  // ///////
  // ///////search word for prediction
  // ////// needn't guess its "kana","romaji" or "kanji"
  // //////
  // ////////////////////////////////////////////////////////////////////////////////
  public LexiconWordMeta FindQuantifierInLexicon(String strWord) {
    // logger.debug("enter FindQuantifierInLexicon:"+strWord);
    LexiconWordMeta wsmTemp = null;
    if (strWord != null) {
      strWord = strWord.trim();
      for (int i = 0; i < l.vecQantifier.size(); i++) {
        wsmTemp = l.vecQantifier.get(i);
        if (IsSameWord(wsmTemp, strWord)) {
          return wsmTemp;
        }
      } // end of for
    }// end of if
    return null;

  }

  /*
   * both kanji
   */
  public LexiconWordMeta searchNQKana(String strNumber, String strQuantifier) {
    // LexiconWordMeta lwmResult =null;
    LexiconWordMeta lwm = null;
    // logger.debug("enter searchNQKana: "+strNumber+" "+strQuantifier);
    for (int i = 0; i < l.getVecNQ().size(); i++) {
      lwm = (LexiconWordMeta) l.getVecNQ().get(i);
      if (lwm.getStrCategory1().equalsIgnoreCase(strNumber) && lwm.getStrCategory2().equalsIgnoreCase(strQuantifier)) {
        return lwm;
      }
    }// end of for
    return null;
  }

  public LexiconWordMeta FindNumeralInLexicon(String strWord) {
    // logger.debug("enter FindNumeralInLexicon:"+strWord);
    LexiconWordMeta wsmTemp = null;
    if (strWord != null) {
      strWord = strWord.trim();
      for (int i = 0; i < l.vecNumber.size(); i++) {
        wsmTemp = l.vecNumber.elementAt(i);
        if (IsSameWord(wsmTemp, strWord)) {
          return wsmTemp;
        }
      } // end of for
    }// end of if
    return null;

  }

  /**
   * @param wsmTemp
   * @param strWord
   *          searching word,base form as in the lexicon,written in kanji/kana,
   *          correct
   * @return if success,true; else false.
   */
  private boolean IsSameWord(LexiconWordMeta wsmTemp, String strWord) {
    boolean booResult = false;
    String strSearchKanji = wsmTemp.getStrKanji();
    String strSearchKana = wsmTemp.getStrKana();
    String strSearchAltKana = wsmTemp.getStrAltkana(); // suppose verb has now
                                                       // altkana in the
                                                       // lexicon,actually so
                                                       // for it indeed no
    // logger.debug("enter IsSameWord: searchwordKana ["+strSearchKana+"] searchword altkana ["+strSearchAltKana+"]");
    if (strWord.equals(strSearchKanji) || strWord.equals(strSearchKana)) {
      booResult = true;
      return true;
    } else {
      if (strSearchAltKana.length() > 0) {
        if (strWord.equals(strSearchAltKana)) {
          booResult = true;
          return true;
        }
      }
    }
    return booResult;
  }

  public LexiconWordMeta getWordNoun(String strWord) {
    // logger.debug("enter getWordNoun: "+strWord);
    LexiconWordMeta wsmResult = null;
    LexiconWordMeta wsmTemp = null;
    if (strWord != null) {
      strWord = strWord.trim();
      int intWordType = CharacterUtil.checkWordClass(strWord);
      // logger.debug("no special word used");
      // noun_personname
      for (int i = 0; i < l.getVecPersonName().size(); i++) {
        wsmTemp = (LexiconWordMeta) l.getVecPersonName().get(i);
        // logger.debug("personname group: "+wsmTemp.getStrKana());
        if (IsSamePersonName(wsmTemp, strWord, intWordType)) {
          return wsmTemp;
        }
      } // end of for

      if (wsmResult == null) {
        // logger.debug("not a person name,check general noun");
        // general noun
        for (int i = 0; i < l.getVecNoun().size(); i++) {
          wsmTemp = (LexiconWordMeta) l.getVecNoun().get(i);
          if (IsSameWord(wsmTemp, strWord, intWordType)) {
            wsmResult = wsmTemp;
            break;
          }
        } // end of for
      }

    }// end of if
    return wsmResult;
  }

  // ////////////////////////////////////////////////////
  // //////search word for grammar network; old word structure to new word
  // format
  // ///// needn't guess its "kana","romaji" or "kanji"
  // ///////////////////////////////////////////////////////

  public LexiconWordMeta getWord(int type, String kanji, String kana) {
    logger.debug("enter getWord: " + kanji);
    LexiconWordMeta wsmResult = null;
    LexiconWordMeta wsmTemp = null;
    if (kanji != null) {
      kanji = kanji.trim();
      for (int i = 0; i < l.getVecAllWord().size(); i++) {
        wsmTemp = (LexiconWordMeta) l.getVecAllWord().get(i);
        if (IsSameWord(wsmTemp, type, kanji)) {
          return wsmTemp;
        }

      } // end of for

      if (wsmResult == null) {
        logger.debug("not found the kanji: " + kanji + " start matching the kana: " + kana);
        for (int i = 0; i < l.getVecAllWord().size(); i++) {
          wsmTemp = (LexiconWordMeta) l.getVecAllWord().get(i);

          if (IsSameWord(wsmTemp, type, kanji, kana)) {
            return wsmTemp;
          }

        } // end of for

        // end of for
      }

      if (wsmResult == null) {
        logger.error("finally not found the word: " + kanji);
      }

    }// end of if
    return wsmResult;
  }

  public LexiconWordMeta getWord(String kanji, String kana) {
    logger.debug("enter getWord: " + kanji);
    LexiconWordMeta wsmResult = null;
    LexiconWordMeta wsmTemp = null;
    if (kanji != null && kanji.length() > 0) {
      kanji = kanji.trim();
      for (int i = 0; i < l.getVecAllWord().size(); i++) {
        wsmTemp = (LexiconWordMeta) l.getVecAllWord().get(i);

        if (IsSameWordInKanji(wsmTemp, kanji)) {
          return wsmTemp;
        }
      } // end of for

      if (wsmResult == null) {
        logger.info("not found the kanji: " + kanji + " start matching the kana: " + kana);
        if (kana != null && kana.length() > 0) {
          kana = kana.trim();
          for (int i = 0; i < l.getVecAllWord().size(); i++) {
            wsmTemp = (LexiconWordMeta) l.getVecAllWord().get(i);
            if (IsSameWordInKana(wsmTemp, kanji, kana)) {
              return wsmTemp;
            }

          } // end of for
        }
      }

      if (wsmResult == null) {
        logger.error("finally not found the word: " + kanji);

      }

    }// end of if
    return wsmResult;
  }

  public LexiconWordMeta getWord(String kanji, String kana, String strEngKana) {
    logger.debug("enter getWord: " + kanji);
    LexiconWordMeta wsmResult = null;
    LexiconWordMeta wsmTemp = null;
    if (kanji != null && kanji.length() > 0) {
      kanji = kanji.trim();
      for (int i = 0; i < l.getVecAllWord().size(); i++) {
        wsmTemp = (LexiconWordMeta) l.getVecAllWord().get(i);

        if (IsSameWordInKanji(wsmTemp, kanji)) {
          return wsmTemp;
        }
      } // end of for

      if (wsmResult == null) {
        logger.info("even not found the kanji: " + kanji + "; start matching the strEngKana: " + strEngKana);
        if (strEngKana != null && strEngKana.length() > 0) {
          strEngKana = strEngKana.trim();
          for (int i = 0; i < l.getVecAllWord().size(); i++) {
            wsmTemp = (LexiconWordMeta) l.getVecAllWord().get(i);
            if (IsSameWordInAltEngKana(wsmTemp, strEngKana)) {
              return wsmTemp;
            }
          } // end of for
        }

      }

      if (wsmResult == null) {
        logger.info("even not found the strEngKana: " + strEngKana + "; start matching the kana: " + kana);
        if (kana != null && kana.length() > 0) {
          kana = kana.trim();
          for (int i = 0; i < l.getVecAllWord().size(); i++) {
            wsmTemp = (LexiconWordMeta) l.getVecAllWord().get(i);
            if (IsSameWordInKana(wsmTemp, kanji, kana)) {
              return wsmTemp;
            }
          } // end of for
        }
      }

      if (wsmResult == null) {
        logger.error("finally not found the word: " + kanji);

      }

    }// end of if
    return wsmResult;
  }

  private boolean IsSameWordInKanji(LexiconWordMeta wsmTemp, String kanji) {
    boolean booResult = false;

    String strSearchKanji = wsmTemp.getStrKanji();
    String strKanji = "��" + strSearchKanji.trim();
    String strKanji2 = strSearchKanji.trim() + "����";
    String strKanji3 = "��" + strSearchKanji.trim() + "����";
    int intType = wsmTemp.getIntType();
    // logger.debug("enter IsSameWord: strSearchKanji ["+strSearchKanji+"]");
    if (kanji.equals(strSearchKanji)) {
      booResult = true;
    } else if (kanji.equals(strKanji)) { // "��"+kanji
      booResult = true;
    } else if (kanji.equals(strKanji2)) {
      booResult = true;
    } else if (kanji.equals(strKanji3)) {
      booResult = true;
    }
    return booResult;
  }

  private boolean IsSameWord(LexiconWordMeta wsmTemp, int type, String kanji) {
    boolean booResult = false;
    String strSearchKanji = wsmTemp.getStrKanji();
    String strKanji = "��" + strSearchKanji.trim();
    String strKanji2 = strSearchKanji.trim() + "����";
    String strKanji3 = "��" + strSearchKanji.trim() + "����";
    int intType = wsmTemp.getIntType();
    logger.debug("enter IsSameWord: strSearchKanji [" + strSearchKanji + "]");
    if (intType == type) {
      if (kanji.equals(strSearchKanji)) {
        booResult = true;
      } else if (kanji.equals(strKanji)) { // "��"+kanji
        booResult = true;
      } else if (kanji.equals(strKanji2)) {
        booResult = true;
      } else if (kanji.equals(strKanji3)) {
        booResult = true;
      }
    }
    return booResult;
  }

  /*
   * even include the case "if kanji equals strSearchKana"
   */

  private boolean IsSameWordInKana(LexiconWordMeta wsmTemp, String kanji, String kana) {
    boolean booResult = false;
    String strSearchKanji = wsmTemp.getStrKanji();
    String strSearchKana = wsmTemp.getStrKana();
    int intType = wsmTemp.getIntType();
    // logger.debug("enter IsSameWordInKana: strSearchKanji ["+strSearchKanji+"]");
    if (kanji.equals(strSearchKana) || kana.equals(strSearchKana)) {
      booResult = true;
    }
    return booResult;
  }

  private boolean IsSameWordInAltEngKana(LexiconWordMeta wsmTemp, String altEngKana) {
    boolean booResult = false;
    String strSearchAltKana = wsmTemp.getStrAltkana();

    int intType = wsmTemp.getIntType();
    // logger.debug("enter IsSameWord: strSearchKanji ["+strSearchKanji+"]");
    if (altEngKana.equals(strSearchAltKana)) {
      booResult = true;
    }
    return booResult;
  }

  private boolean IsSameWord(LexiconWordMeta wsmTemp, int type, String kanji, String kana) {
    boolean booResult = false;
    String strSearchKanji = wsmTemp.getStrKanji();
    String strSearchKana = wsmTemp.getStrKana();
    int intType = wsmTemp.getIntType();
    // logger.debug("enter IsSameWord: strSearchKanji ["+strSearchKanji+"]");
    if (intType == type) {
      if (kanji.equals(strSearchKanji) || kana.equals(strSearchKana)) {
        booResult = true;
      }
    }
    if (!booResult) {
      if (kanji.equals(strSearchKanji) || kana.equals(strSearchKana)) {
        booResult = true;
      }
    }
    return booResult;
  }

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {

    LexiconProcess lp = new LexiconProcess();
    // LexiconWordMeta lwm = lp.getLexiconWordParticle("��");
    // ����������;���݂�;�R���s���[�^�[;�P�[�e�B����;
    // System.out.println("start main");
    // LexiconWordMeta lwm = lp.getLexiconWordNoun("haishasan");
    // �������܂�����,28;��݂܂�����,2;���܂�,3;
    // LexiconWordMeta lwm = lp.getLexiconWordVerb("���ׂ����܂���",28);
    // LexiconWordMeta lwm = lp.getWord("�Ƃ���", "�Ƃ���");
    // LexiconWordMeta lwm = lp.getLexiconWordNounFull("���Z����");

    // LexiconWordMeta lwm = lp.getLexiconWordVerb("��܂��܂���",22);
    // if(lwm!=null){
    // System.out.println("find word "+lwm.getId());
    // }else{
    // System.out.println("not found");
    // }

    // Vector v = lp.getErrVerbFormOfLessonDefNew

  }

}
