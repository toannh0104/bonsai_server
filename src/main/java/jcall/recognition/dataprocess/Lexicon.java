/**
 * Created on 2007/06/04
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.util.Vector;

public class Lexicon {
  // final static int LEX_NUM_TYPES = 20;
  final static int LEX_MAX_LEVELS = 5;
  // The types
  public static final int LEX_TYPE_UNSPECIFIED = 0;
  public static final int LEX_TYPE_NOUN = 1;
  public static final int LEX_TYPE_NOUN_PERSONNAME = 11;
  public static final int LEX_TYPE_NOUN_AREANAME = 12;
  public static final int LEX_TYPE_NOUN_PRONOUN = 13;
  public static final int LEX_TYPE_NOUN_SURU = 14;
  public static final int LEX_TYPE_NOUN_NUMERAL = 15;
  public static final int LEX_TYPE_NOUN_QUANTIFIER = 19;
  public static final int LEX_TYPE_NOUN_SUFFIX = 16;
  public static final int LEX_TYPE_NOUN_PREFIX = 17;
  public static final int LEX_TYPE_NOUN_TIME = 18;
  public static final int LEX_TYPE_NUMQUANT = 111;
  // final static int LEX_TYPE_NOUN_ADVERB = 19;
  public static final int LEX_TYPE_VERB = 2;
  public static final int LEX_TYPE_VERB_VT = 21;
  public static final int LEX_TYPE_VERB_VI = 22;
  public static final int LEX_TYPE_ADJECTIVE = 3;
  public static final int LEX_TYPE_ADJVERB = 4;
  public static final int LEX_TYPE_ADVERB = 5;

  public static final int LEX_TYPE_PARTICLE_AUXIL = 6; // auxiliary word
  public static final int LEX_TYPE_PARTICLE_AUXILV = 7;
  public static final int LEX_TYPE_RENTAI = 8;
  public static final int LEX_TYPE_KANDOU = 9;

  public static final int[] LEX_TYPE_TRANSFORM = { 1, 2, 18, 3, 5, 13, 15, 1, 6, 1, 16, 17 };
  public static final int[] LEX_TYPE_INTTRANSFORM = { 0, 1, 11, 12, 13, 14, 15, 16, 17, 18, 19, 2, 21, 22, 3, 4, 5, 6,
      7, 8, 9, 111 };

  // String definition of types
  static String[] typeString = { "UNK", "NOUN", "NOUN_PERSONNAME", "NOUN_AREANAME", "NOUN_PRONOUN", "NOUN_SURU",
      "NOUN_NUMERAL", "NOUN_SUFFIX", "NOUN_PREFIX", "NOUN_TIME", "NOUN_QUANTIFIER", "VERB", "VERB_VT", "VERB_VI",
      "ADJECTIVE", "ADJVERB", "ADVERB", "PARTICLE_AUXIL", "PARTICLE_AUXILV", "RENTAI", "KANDOU", "NUMQUANT" };

  Vector<LexiconWordMeta> vecAllWord; // 887
  Vector<LexiconWordMeta> vecActiveWord; // 324
  Vector<LexiconWordMeta> vecAltKanaActiveWord; // the one that has altKana and
                                                // active;19
  Vector<LexiconWordMeta> vecWord; // 324
  Vector<LexiconWordMeta> vecNoun;
  Vector<LexiconWordMeta> vecPersonName;
  Vector<LexiconWordMeta> vecPronoun;
  Vector<LexiconWordMeta> vecNumber;
  Vector<LexiconWordMeta> vecQantifier;
  Vector<LexiconWordMeta> vecNQ;
  Vector<LexiconWordMeta> vecSuffix;
  Vector<LexiconWordMeta> vecprefix;
  Vector<LexiconWordMeta> vecTime;

  Vector<LexiconWordMeta> vecVerb;

  Vector<LexiconWordMeta> vecParticle;

  Vector<LexiconWordMeta> vecRentai;

  Vector<LexiconWordMeta> vecAdjective;
  Vector<LexiconWordMeta> vecAdjVerb;

  Vector<LexiconWordMeta> vecAdverb;

  // Vector vecAltKanaWord; //the one that has altKana and active;19

  public Lexicon() {
    init();
  }

  private void init() {
    vecAllWord = new Vector<LexiconWordMeta>();
    vecAltKanaActiveWord = new Vector<LexiconWordMeta>();
    vecWord = new Vector<LexiconWordMeta>();
    vecNoun = new Vector<LexiconWordMeta>();
    vecVerb = new Vector<LexiconWordMeta>();
    vecQantifier = new Vector<LexiconWordMeta>();
    vecParticle = new Vector<LexiconWordMeta>();
    vecNumber = new Vector<LexiconWordMeta>();
    vecprefix = new Vector<LexiconWordMeta>();
    vecSuffix = new Vector<LexiconWordMeta>();
    vecRentai = new Vector<LexiconWordMeta>();
    vecPersonName = new Vector<LexiconWordMeta>();
    vecPronoun = new Vector<LexiconWordMeta>();
    vecNQ = new Vector<LexiconWordMeta>();
    vecAdverb = new Vector<LexiconWordMeta>();
    vecAdjVerb = new Vector<LexiconWordMeta>();
    vecAdjective = new Vector<LexiconWordMeta>();
    vecTime = new Vector<LexiconWordMeta>();
  }

  public Vector<LexiconWordMeta> getVecActiveWord() {
    return vecActiveWord;
  }

  public void setVecActiveWord(Vector<LexiconWordMeta> vecActiveWord) {
    this.vecActiveWord = vecActiveWord;
  }

  public Vector<LexiconWordMeta> getVecAltKanaActiveWord() {
    return vecAltKanaActiveWord;
  }

  public void setVecAltKanaActiveWord(Vector<LexiconWordMeta> vecAltKanaActiveWord) {
    this.vecAltKanaActiveWord = vecAltKanaActiveWord;
  }

  public Vector<LexiconWordMeta> getVecNQ() {
    return vecNQ;
  }

  public void setVecNQ(Vector<LexiconWordMeta> vecNQ) {
    this.vecNQ = vecNQ;
  }

  public void addToVecNQ(LexiconWordMeta wsm) {
    this.vecNQ.addElement(wsm);
  }

  public void setVecQantifier(Vector<LexiconWordMeta> vecCounter) {
    this.vecQantifier = vecCounter;
  }

  public Vector<LexiconWordMeta> getVecQantifier() {
    return vecQantifier;
  }

  public void addToVecQantifier(LexiconWordMeta wsm) {
    this.vecQantifier.addElement(wsm);
  }

  public Vector<LexiconWordMeta> getVecNoun() {
    return vecNoun;
  }

  public void setVecNoun(Vector<LexiconWordMeta> vecNoun) {
    this.vecNoun = vecNoun;
  }

  public void addToVecNoun(LexiconWordMeta wsm) {
    this.vecNoun.addElement(wsm);
  }

  public Vector<LexiconWordMeta> getVecNumber() {
    return vecNumber;
  }

  public void setVecNumber(Vector<LexiconWordMeta> vecNumber) {
    this.vecNumber = vecNumber;
  }

  public void addToVecNumber(LexiconWordMeta wsm) {
    this.vecNumber.addElement(wsm);
  }

  public Vector<LexiconWordMeta> getVecParticle() {
    return vecParticle;
  }

  public void setVecParticle(Vector<LexiconWordMeta> vecParticle) {
    this.vecParticle = vecParticle;
  }

  public void addToVecParticle(LexiconWordMeta wsm) {
    this.vecParticle.addElement(wsm);
  }

  public Vector<LexiconWordMeta> getVecVerb() {
    return vecVerb;
  }

  public void setVecVerb(Vector<LexiconWordMeta> vecVerb) {
    this.vecVerb = vecVerb;
  }

  public void addToVecVerb(LexiconWordMeta wsm) {
    this.vecVerb.addElement(wsm);
  }

  public Vector<LexiconWordMeta> getVecWord() {
    return vecWord;
  }

  public void setVecWord(Vector<LexiconWordMeta> vecWord) {
    this.vecWord = vecWord;
  }

  public void addToVecWord(LexiconWordMeta wsm) {
    this.vecWord.addElement(wsm);
  }

  public Vector<LexiconWordMeta> getVecAllWord() {
    return vecAllWord;
  }

  public void setVecAllWord(Vector<LexiconWordMeta> vecAllWord) {
    this.vecAllWord = vecAllWord;
  }

  public void addToVecAllWord(LexiconWordMeta wsm) {
    this.vecAllWord.addElement(wsm);
  }

  public void addWord(LexiconWordMeta lwm) {
    this.vecAllWord.addElement(lwm);
    int type = lwm.getIntType();
    if (type == Lexicon.LEX_TYPE_VERB) {
      this.vecVerb.addElement(lwm);
    } else if (type == Lexicon.LEX_TYPE_NOUN) {
      this.vecNoun.addElement(lwm);
    } else if (type == Lexicon.LEX_TYPE_NOUN_NUMERAL) {
      this.addToVecNumber(lwm);
    } else if (type == Lexicon.LEX_TYPE_NOUN_QUANTIFIER) {
      this.addToVecQantifier(lwm);
    } else if (type == Lexicon.LEX_TYPE_NOUN_PREFIX) {
      this.vecprefix.addElement(lwm);
    } else if (type == Lexicon.LEX_TYPE_NOUN_SUFFIX) {
      this.vecSuffix.addElement(lwm);
    } else if (type == Lexicon.LEX_TYPE_PARTICLE_AUXIL) {
      this.addToVecParticle(lwm);
    } else if (type == Lexicon.LEX_TYPE_NOUN_PRONOUN) {
      this.vecPronoun.addElement(lwm);
    } else if (type == Lexicon.LEX_TYPE_RENTAI) {
      this.vecRentai.addElement(lwm);
    } else if (type == Lexicon.LEX_TYPE_NOUN_PERSONNAME) {
      this.vecPersonName.addElement(lwm);
    } else if (type == Lexicon.LEX_TYPE_NUMQUANT) {
      this.vecNQ.addElement(lwm);
    } else if (type == Lexicon.LEX_TYPE_ADJECTIVE) {
      this.vecAdjective.addElement(lwm);
    } else if (type == Lexicon.LEX_TYPE_ADJVERB) {
      this.vecAdjVerb.addElement(lwm);
    } else if (type == Lexicon.LEX_TYPE_ADVERB) {
      this.vecAdverb.addElement(lwm);
    } else if (type == Lexicon.LEX_TYPE_NOUN_TIME) {
      this.vecTime.addElement(lwm);
    }
  }

  /**
   * @param lwm
   *          add lwm , give new id && component id
   */
  public void addWordUnique(LexiconWordMeta lwm) {
    int index;
    if (!contains(lwm)) {
      index = this.vecAllWord.size();
      lwm.setId(index + 1);
      int type = lwm.getIntType();
      if (type == Lexicon.LEX_TYPE_VERB) {
        index = this.vecVerb.size();
        lwm.setComponentID(index + 1);
        // String str = lwm.getStrCategory1();
        // lwm.setStrAttribute(str);
        // lwm.setStrCategory1("");
        this.vecVerb.addElement(lwm);
      } else if (type == Lexicon.LEX_TYPE_NOUN) {
        index = this.vecNoun.size();
        lwm.setComponentID(index + 1);
        this.vecNoun.addElement(lwm);
      } else if (type == Lexicon.LEX_TYPE_NOUN_NUMERAL) {
        index = this.vecNumber.size();
        lwm.setComponentID(index + 1);
        this.addToVecNumber(lwm);
      } else if (type == Lexicon.LEX_TYPE_NOUN_QUANTIFIER) {
        index = this.vecQantifier.size();
        lwm.setComponentID(index + 1);
        this.addToVecQantifier(lwm);
      } else if (type == Lexicon.LEX_TYPE_NOUN_PREFIX) {
        index = this.vecprefix.size();
        lwm.setComponentID(index + 1);
        this.vecprefix.addElement(lwm);
      } else if (type == Lexicon.LEX_TYPE_NOUN_SUFFIX) {
        index = this.vecSuffix.size();
        lwm.setComponentID(index + 1);
        this.vecSuffix.addElement(lwm);
      } else if (type == Lexicon.LEX_TYPE_PARTICLE_AUXIL) {
        index = this.vecParticle.size();
        lwm.setComponentID(index + 1);
        this.addToVecParticle(lwm);
      } else if (type == Lexicon.LEX_TYPE_NOUN_PRONOUN) {
        index = this.vecPronoun.size();
        lwm.setComponentID(index + 1);
        this.vecPronoun.addElement(lwm);
      } else if (type == Lexicon.LEX_TYPE_RENTAI) {
        index = this.vecRentai.size();
        lwm.setComponentID(index + 1);
        this.vecRentai.addElement(lwm);
      } else if (type == Lexicon.LEX_TYPE_NOUN_PERSONNAME) {
        index = this.vecPersonName.size();
        lwm.setComponentID(index + 1);
        this.vecPersonName.addElement(lwm);
      } else if (type == Lexicon.LEX_TYPE_NUMQUANT) {
        index = this.vecNQ.size();
        lwm.setComponentID(index + 1);
        this.vecNQ.addElement(lwm);
      } else if (type == Lexicon.LEX_TYPE_ADJECTIVE) {
        index = this.vecAdjective.size();
        lwm.setComponentID(index + 1);
        this.vecAdjective.addElement(lwm);
      } else if (type == Lexicon.LEX_TYPE_ADJVERB) {
        index = this.vecAdjVerb.size();
        lwm.setComponentID(index + 1);
        this.vecAdjVerb.addElement(lwm);
      } else if (type == Lexicon.LEX_TYPE_ADVERB) {
        index = this.vecAdverb.size();
        lwm.setComponentID(index + 1);
        this.vecAdverb.addElement(lwm);
      } else if (type == Lexicon.LEX_TYPE_NOUN_TIME) {
        index = this.vecTime.size();
        lwm.setComponentID(index + 1);
        this.vecTime.addElement(lwm);
      }
      this.vecAllWord.addElement(lwm);
    }
  }

  private boolean contains(LexiconWordMeta lwm) {
    for (int i = 0; i < this.vecAllWord.size(); i++) {
      LexiconWordMeta lwmTemp = vecAllWord.elementAt(i);
      if (lwm.compareTo(lwmTemp) == 0) {
        return true;
      }
    }
    return false;
  }

  public Vector<LexiconWordMeta> getVecPersonName() {
    return vecPersonName;
  }

  public void setVecPersonName(Vector<LexiconWordMeta> vecPersonName) {
    this.vecPersonName = vecPersonName;
  }

  public Vector<LexiconWordMeta> getVecprefix() {
    return vecprefix;
  }

  public void setVecprefix(Vector<LexiconWordMeta> vecprefix) {
    this.vecprefix = vecprefix;
  }

  public Vector<LexiconWordMeta> getVecPronoun() {
    return vecPronoun;
  }

  public void setVecPronoun(Vector<LexiconWordMeta> vecPronoun) {
    this.vecPronoun = vecPronoun;
  }

  public Vector<LexiconWordMeta> getVecRentai() {
    return vecRentai;
  }

  public void setVecRentai(Vector<LexiconWordMeta> vecRentai) {
    this.vecRentai = vecRentai;
  }

  public Vector<LexiconWordMeta> getVecSuffix() {
    return vecSuffix;
  }

  public void setVecSuffix(Vector<LexiconWordMeta> vecSuffix) {
    this.vecSuffix = vecSuffix;
  }

  public static String[] getTypeString() {
    return typeString;
  }

  public static void setTypeString(String[] typeString) {
    Lexicon.typeString = typeString;
  }

  public Vector<LexiconWordMeta> getVecAdjective() {
    return vecAdjective;
  }

  public void setVecAdjective(Vector<LexiconWordMeta> vecAdjective) {
    this.vecAdjective = vecAdjective;
  }

  public Vector<LexiconWordMeta> getVecAdjVerb() {
    return vecAdjVerb;
  }

  public void setVecAdjVerb(Vector<LexiconWordMeta> vecAdjVerb) {
    this.vecAdjVerb = vecAdjVerb;
  }

  public Vector<LexiconWordMeta> getVecAdverb() {
    return vecAdverb;
  }

  public void setVecAdverb(Vector<LexiconWordMeta> vecAdverb) {
    this.vecAdverb = vecAdverb;
  }

  public Vector<LexiconWordMeta> getVecTime() {
    return vecTime;
  }

  public void setVecTime(Vector<LexiconWordMeta> vecTime) {
    this.vecTime = vecTime;
  }

}
