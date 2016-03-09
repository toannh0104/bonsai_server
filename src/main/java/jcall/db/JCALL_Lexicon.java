/**
 * Created on 2007/06/04
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import jcall.CALL_formStruct;
import jcall.CALL_lexiconStruct;
import jcall.CALL_verbRulesStruct;
import jcall.CALL_wordStruct;
import jcall.CALL_wordWithFormStruct;
import jcall.config.ConfigInstant;
import jcall.config.FindConfig;
import jcall.recognition.util.CharacterUtil;
import jcall.util.XmlFileManager;
import jp.co.gmo.rs.ghs.model.DataConfig;

import org.apache.log4j.Logger;
import org.jdom2.Element;

public class JCALL_Lexicon implements JCALL_LexiconIF {
  private Properties property;
  static Logger logger = Logger.getLogger(JCALL_Lexicon.class.getName());

  final static int LEX_MAX_LEVELS = 5;

  // The types
  public static final int LEX_TYPE_UNSPECIFIED = 0;
  public static final int LEX_TYPE_NOUN = 1;
  public static final int LEX_TYPE_NOUN_PERSONNAME = 11;
  public static final int LEX_TYPE_NOUN_AREANAME = 12;
  public static final int LEX_TYPE_NOUN_DEFINITIVES = 13;
  public static final int LEX_TYPE_NOUN_SURU = 14;
  public static final int LEX_TYPE_NOUN_NUMERAL = 15;
  public static final int LEX_TYPE_NOUN_QUANTIFIER = 19;
  public static final int LEX_TYPE_NOUN_SUFFIX = 16;
  public static final int LEX_TYPE_NOUN_PREFIX = 17;
  public static final int LEX_TYPE_NOUN_TIME = 18;
  public static final int LEX_TYPE_NUMQUANT = 111;
  public static final int LEX_TYPE_NOUN_PRONOUN_PERSON = 112;
  // public static final int LEX_TYPE_NOUN_PRONOUN_THING = 113;

  // public static final int LEX_TYPE_NOUN_ADVERB = 19;

  public static final int LEX_TYPE_VERB = 2;
  // public static final int LEX_TYPE_VERB_VT = 21; //still no
  // public static final int LEX_TYPE_VERB_VI = 22; //still no
  public static final int LEX_TYPE_ADJECTIVE = 3;
  public static final int LEX_TYPE_ADJVERB = 4;
  public static final int LEX_TYPE_ADVERB = 5;

  public static final int LEX_TYPE_PARTICLE_AUXIL = 6; // auxiliary word
  public static final int LEX_TYPE_PARTICLE_AUXILV = 7;

  public static final int LEX_TYPE_RENTAI = 8;//
  public static final int LEX_TYPE_KANDOU = 9;//

  // public static final int[] LEX_TYPE_TRANSFORM
  // ={1,2,18,3,5,13,15,1,6,1,16,17};

  public static final int[] LEX_TYPE_INTTRANSFORM = { 0, 1, 11, 12, 13, 14, 15, 16, 17, 18, 19, 2, 3, 4, 5, 6, 7, 8, 9,
      111, 112 };

  // path of lexicon.xml
  private String filePath;

  // String definition of types
  static String[] TypeString = { "UNK", "NOUN", "PERSONNAME", "AREANAME", "DEFINITIVE", // PRONOUN_Thing
      "SURUNOUN", "NUMERAL", "SUFFIX", "PREFIX", "TIME", "QUANTIFIER", "VERB",
      // "VERB_VT",
      // "VERB_VI",
      "ADJECTIVE", "ADJVERB", "ADVERB", "PARTICLE", "PARTICLE_AUXILV", "RENTAI", "KANDOU", "NUMQUANT", "PRONOUN_PERSON" };

  private static JCALL_Lexicon lexicon;
  private static HashMap<Integer, JCALL_Word> hm = null;
  private static Vector<Integer> vKey = null;

  public static String typeInt2Name(int intType) {
    for (int i = 0; i < LEX_TYPE_INTTRANSFORM.length; i++) {
      int aType = LEX_TYPE_INTTRANSFORM[i];
      if (intType == aType) {
        return TypeString[i];
      }
    }
    // logger.error("find no respoding type name");
    return TypeString[0];
  }

  public static int typeName2Int(String strType) {
    for (int i = 0; i < TypeString.length; i++) {
      String aType = TypeString[i];
      if (strType.equalsIgnoreCase(aType)) {
        return LEX_TYPE_INTTRANSFORM[i];
      }
    }
    // logger.error("find no respoding type name");
    return 0;
  }

  private JCALL_Lexicon() {
    // TODO Auto-generated constructor stub
    init();
  }

  // synchronized, need not to use this for we make sure the initialization
  // will be in a single thread environment

  private void init() {

    property = new DataConfig().getProperty();

    filePath = property.getProperty("data.lexicon");
    XmlFileManager xf = new XmlFileManager();
    Element eRoot = xf.load2Element(filePath);
    logger.debug("root element: " + eRoot.getName());
    element2Lexicon(eRoot);

  }

  public static JCALL_Lexicon getInstance() {
    if (lexicon == null) {
      lexicon = new JCALL_Lexicon();
    }
    return lexicon;
  }

  // 2011.02.28 T.Tajima add for reload function
  public static JCALL_Lexicon getNewInstance() {
    lexicon = new JCALL_Lexicon();
    return lexicon;
  }

  public Element toElement() {
    Element eRoot = new Element("database");
    for (int i = 0; i < vKey.size(); i++) {
      int intKey = vKey.elementAt(i);
      JCALL_Word tmpWord = hm.get(intKey);
      Element child = tmpWord.toElement();
      eRoot.addContent(child);
    }
    return eRoot;
  }

  public void save2FileXml() {
    String file = FindConfig.getConfig().findStr(ConfigInstant.CONFIG_LEXICON_FILE);
    XmlFileManager xf = new XmlFileManager();
    xf.save2File(toElement(), file);
  }

  public void element2Lexicon(Element e) {
    hm = new HashMap<Integer, JCALL_Word>();
    vKey = new Vector<Integer>(); // not forget to initialize this vector;
    List content = e.getContent();
    Iterator iterator = content.iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      if (o instanceof Element) {
        Element child = (Element) o;
        JCALL_Word tmpWord = new JCALL_Word();
        tmpWord.element2Word(child);
        // System.out.println(tmpWord.getId());
        hm.put(tmpWord.getId(), tmpWord);
        // System.out.println(tmpWord.getId());
        vKey.add(tmpWord.getId());
      }
    }
    System.out.println("");
  }

  @Override
  public JCALL_Word getWord(int id) {
    // TODO Auto-generated method stub
    JCALL_Word word = hm.get(id);

    return word;
  }

  public Vector getAllWords() {
    // TODO Auto-generated method stub
    Vector<JCALL_Word> vResult = new Vector();
    for (int i = 0; i < vKey.size(); i++) {
      int intKey = vKey.elementAt(i);
      JCALL_Word tmpWord = hm.get(intKey);
      vResult.addElement(tmpWord);
    }
    return vResult;
  }

  public JCALL_Word getWordFmKanji(String kanji) {
    // TODO Auto-generated method stub
    for (int i = 0; i < vKey.size(); i++) {
      int intKey = vKey.elementAt(i);
      JCALL_Word tmpWord = hm.get(intKey);
      String tmpKanji = tmpWord.getStrKanji();
      if (kanji.equals(tmpKanji.trim())) {
        return tmpWord;
      }
    }
    return null;
  }

  public JCALL_Word getWordFmKanji(String kanji, int intType) {
    // TODO Auto-generated method stub
    for (int i = 0; i < vKey.size(); i++) {
      int intKey = vKey.elementAt(i);
      JCALL_Word tmpWord = hm.get(intKey);
      if (tmpWord.getIntType() == intType) {
        String tmpKanji = tmpWord.getStrKanji();
        if (kanji.equals(tmpKanji.trim())) {
          return tmpWord;
        }
      }
    }
    return null;
  }

  /*
   * It may return more words sharing the same kana
   */
  public Vector getVWordFmKana(String kana, int intType) {
    // TODO Auto-generated method stub
    Vector<JCALL_Word> vResult = null;
    for (int i = 0; i < vKey.size(); i++) {
      int intKey = vKey.elementAt(i);
      JCALL_Word tmpWord = hm.get(intKey);
      if (tmpWord.getIntType() == intType) {
        String tmpKana = tmpWord.getStrKana();
        if (tmpKana.equals(kana.trim())) {
          if (vResult == null) {
            vResult = new Vector<JCALL_Word>();
            vResult.add(tmpWord);
          }

        }
      }
    }
    return vResult;
  }

  public JCALL_Word getWordFmID(int intID) {
    // TODO Auto-generated method stub
    JCALL_Word result = null;
    result = hm.get(result);
    return result;
  }

  /*
   * str: could be kana or kanji(no romaji) return the first matching word
   */
  public JCALL_Word getWordFmStr(String str, int intType) {
    // TODO Auto-generated method stub
    JCALL_Word result = null;
    if (str == null || str.length() == 0) {
      return null;
    }
    for (int i = 0; i < vKey.size(); i++) {
      int intKey = vKey.elementAt(i);
      JCALL_Word tmpWord = hm.get(intKey);
      if (tmpWord.getIntType() == intType) {
        String tmpKana = tmpWord.getStrKana();
        String tmpKanji = tmpWord.getStrKanji();
        if (tmpKana.equals(str.trim()) || tmpKanji.equals(str.trim())) {
          result = tmpWord;
          break;
        }

      }
    }
    return result;
  }

  /*
   * str: could be kana or kanji(no romaji) return the first matching word
   */
  public JCALL_Word getWordFmStr(String str) {
    // TODO Auto-generated method stub
    JCALL_Word result = null;
    for (int i = 0; i < vKey.size(); i++) {
      int intKey = vKey.elementAt(i);
      JCALL_Word tmpWord = hm.get(intKey);
      String tmpKana = tmpWord.getStrKana();
      String tmpKanji = tmpWord.getStrKanji();
      if (tmpKana.equals(str.trim()) || tmpKanji.equals(str.trim())) {
        result = tmpWord;
        break;
      }

    }
    return result;
  }

  /*
   * str: the english label meaning. = the default english meaning return the
   * first matching word
   */
  public JCALL_Word getWordFmStrEnMeaning(String str) {
    // TODO Auto-generated method stub
    JCALL_Word result = null;
    if (str == null || str.length() == 0) {
      return null;
    }
    str = str.trim();
    for (int i = 0; i < vKey.size(); i++) {
      int intKey = vKey.elementAt(i);
      JCALL_Word tmpWord = hm.get(intKey);
      // String tmpKana = tmpWord.getStrKana();
      // String tmpKanji = tmpWord.getStrKanji();
      String tmpEnM = tmpWord.getDEngMeaning();

      if (tmpEnM != null) {

        if (tmpEnM.equals(str) || str.equalsIgnoreCase(tmpEnM.trim())) {
          result = tmpWord;
          break;
        }
      } else {
        // logger.error("tmpEnM is null, while word Kanji: "+tmpWord.getStrKanji()
        // +" kana: "+ tmpWord.getStrKana());
      }
    }
    return result;

  }

  /*
   * str: could be kana or kanji(no romaji) return the first matching word
   */
  public JCALL_Word getWordFmSurForm(String str) {
    // TODO Auto-generated method stub

    JCALL_Word result = null;
    for (int i = 0; i < vKey.size(); i++) {
      int intKey = vKey.elementAt(i);
      JCALL_Word tmpWord = hm.get(intKey);
      String tmpKana = tmpWord.getStrKana();
      String tmpKanji = tmpWord.getStrKanji();
      if (tmpKana.equals(str.trim()) || tmpKanji.equals(str.trim())) {
        result = tmpWord;
        break;
      } else { // No regular words, may be irregualar words = form
        // transformed
        if (tmpWord.intType == JCALL_Lexicon.LEX_TYPE_NOUN_PERSONNAME) {

          String tmpKanaNew = tmpKana + "ã�•ã‚“";
          String tmpKanjiNew = tmpKanji + "ã�•ã‚“";
          String tmpKanaNew2 = tmpKana + "ã��ã‚“";
          String tmpKanjiNew2 = tmpKanji + "ã��ã‚“";
          String tmpKanaNew3 = tmpKana + "ã�¡ã‚ƒã‚“";
          String tmpKanjiNew3 = tmpKanji + "ã�¡ã‚ƒã‚“";
          if (tmpKanaNew.equals(str.trim()) || tmpKanjiNew.equals(str.trim())) {
            result = tmpWord;
            break;
          } else if (tmpKanaNew2.equals(str.trim()) || tmpKanjiNew2.equals(str.trim())) {
            result = tmpWord;
            break;
          } else if (tmpKanaNew3.equals(str.trim()) || tmpKanjiNew3.equals(str.trim())) {
            result = tmpWord;
            break;
          }

        } else if (tmpWord.intType == JCALL_Lexicon.LEX_TYPE_NOUN) {
          String tmpKanaNew = "ã�Š" + tmpKana;
          String tmpKanjiNew = "ã�Š" + tmpKanji;
          String tmpKanaNew2 = "ã�Š" + tmpKana + "ã�•ã‚“";
          String tmpKanjiNew2 = "ã�Š" + tmpKanji + "ã�•ã‚“";
          String tmpKanaNew3 = tmpKana + "ã�•ã‚“";
          String tmpKanjiNew3 = tmpKanji + "ã�•ã‚“";
          String tmpKanaNew4 = CharacterUtil.wordHiragana2Katakana(tmpKana);
          // /????????????do sth for ãƒªãƒ³ã‚´ï¼›ã€€here

          if (tmpKanaNew.equals(str.trim()) || tmpKanjiNew.equals(str.trim())) {
            result = tmpWord;
            break;
          } else if (tmpKanaNew2.equals(str.trim()) || tmpKanjiNew2.equals(str.trim())) {
            result = tmpWord;
            break;
          } else if (tmpKanaNew3.equals(str.trim()) || tmpKanjiNew3.equals(str.trim())) {
            result = tmpWord;
            break;
          } else if (tmpKanaNew4.equals(str.trim())) {
            result = tmpWord;
            break;

          }

        } else if (tmpWord.intType == JCALL_Lexicon.LEX_TYPE_VERB) {
          // get 10 types of DFORM
          String verbRuleFileName = "./Data/verb-rules.txt";
          CALL_verbRulesStruct vrules = new CALL_verbRulesStruct();;
          // Load the verb rules
          boolean rc = vrules.loadRules(verbRuleFileName);
          if (rc == true) {
            Vector v = vrules.getVerbForms(tmpWord, null, -1, -1, CALL_formStruct.POLITE, -1);
            if (v != null) {
              for (int j = 0; j < v.size(); j++) {

                CALL_wordWithFormStruct wordwithForm = (CALL_wordWithFormStruct) v.get(j);
                String tmpKanaNew = wordwithForm.getSurfaceFormKana();
                String tmpKanjiNew = wordwithForm.getSurfaceFormKanji();
                if (tmpKanaNew.equals(str.trim()) || tmpKanjiNew.equals(str.trim())) {
                  result = tmpWord;
                  break;
                }

              }

            }
          } else {
            logger.error("Error: loading verb rules");
          }

        }// end elseif
      }// end IF

    }
    return result;
  }

  /*
   * str: could be kana or kanji or romaji & Katakana like "ã‚¤ãƒ�ã‚´"; return
   * the first matching word
   */
  public JCALL_Word getWordFmSurFormPRoma(String str) {
    // TODO Auto-generated method stub

    JCALL_Word result = null;
    for (int i = 0; i < vKey.size(); i++) {
      int intKey = vKey.elementAt(i);
      JCALL_Word tmpWord = hm.get(intKey);
      String tmpKana = tmpWord.getStrKana();
      String tmpKanji = tmpWord.getStrKanji();
      String tmpRomaji = CharacterUtil.wordKanaToRomaji(tmpKana);
      // if(CharacterUtil.checkWordClass(strSearchKana)==2){//hiragana
      // str = CharacterUtil.wordHiragana2Katakana(strSearchKana);
      if (tmpKana.equals(str.trim()) || tmpKanji.equals(str.trim()) || tmpRomaji.equals(str.trim())) {
        result = tmpWord;
        break;
      } else { // No regular words, may be irregualar words = form
        // transformed
        if (tmpWord.intType == JCALL_Lexicon.LEX_TYPE_NOUN_PERSONNAME) {

          String tmpKanaNew = tmpKana + "ã�•ã‚“";
          String tmpKanjiNew = tmpKanji + "ã�•ã‚“";
          String tmpRomajiNew = CharacterUtil.wordKanaToRomaji(tmpKanaNew);
          String tmpKanaNew2 = tmpKana + "ã��ã‚“";
          String tmpKanjiNew2 = tmpKanji + "ã��ã‚“";
          String tmpRomajiNew2 = CharacterUtil.wordKanaToRomaji(tmpKanaNew2);
          String tmpKanaNew3 = tmpKana + "ã�¡ã‚ƒã‚“";
          String tmpKanjiNew3 = tmpKanji + "ã�¡ã‚ƒã‚“";
          String tmpRomajiNew3 = CharacterUtil.wordKanaToRomaji(tmpKanaNew3);
          if (tmpKanaNew.equals(str.trim()) || tmpKanjiNew.equals(str.trim()) || tmpRomajiNew.equals(str.trim())) {
            result = tmpWord;
            break;
          } else if (tmpKanaNew2.equals(str.trim()) || tmpKanjiNew2.equals(str.trim())
              || tmpRomajiNew2.equals(str.trim())) {
            result = tmpWord;
            break;
          } else if (tmpKanaNew3.equals(str.trim()) || tmpKanjiNew3.equals(str.trim())
              || tmpRomajiNew3.equals(str.trim())) {
            result = tmpWord;
            break;
          }

        } else if (tmpWord.intType == JCALL_Lexicon.LEX_TYPE_NOUN) {
          String tmpKanaNew = "ã�Š" + tmpKana;
          String tmpKanjiNew = "ã�Š" + tmpKanji;
          String tmpKanaNew2 = "ã�Š" + tmpKana + "ã�•ã‚“";
          String tmpKanjiNew2 = "ã�Š" + tmpKanji + "ã�•ã‚“";
          String tmpKanaNew3 = tmpKana + "ã�•ã‚“";
          String tmpKanjiNew3 = tmpKanji + "ã�•ã‚“";

          String tmpRomajiNew = CharacterUtil.wordKanaToRomaji(tmpKanaNew);
          String tmpRomajiNew2 = CharacterUtil.wordKanaToRomaji(tmpKanaNew2);
          String tmpRomajiNew3 = CharacterUtil.wordKanaToRomaji(tmpKanaNew3);
          // Katakana
          String tmpKatakana = CharacterUtil.wordHiragana2Katakana(tmpKana);

          if (tmpKanaNew.equals(str.trim()) || tmpKanjiNew.equals(str.trim()) || tmpRomajiNew.equals(str.trim())) {
            result = tmpWord;
            break;
          } else if (tmpKanaNew2.equals(str.trim()) || tmpKanjiNew2.equals(str.trim())
              || tmpRomajiNew2.equals(str.trim())) {
            result = tmpWord;
            break;
          } else if (tmpKanaNew3.equals(str.trim()) || tmpKanjiNew3.equals(str.trim())
              || tmpRomajiNew3.equals(str.trim())) {
            result = tmpWord;
            break;
          } else if (tmpKatakana.equals(str.trim())) {
            result = tmpWord;
            break;
          }

        } else if (tmpWord.intType == JCALL_Lexicon.LEX_TYPE_VERB) {
          // get 10 types of DFORM
          String verbRuleFileName = "./Data/verb-rules.txt";
          CALL_verbRulesStruct vrules = new CALL_verbRulesStruct();;
          // Load the verb rules
          boolean rc = vrules.loadRules(verbRuleFileName);
          if (rc == true) {
            Vector v = vrules.getVerbForms(tmpWord, null, -1, -1, CALL_formStruct.POLITE, -1);
            if (v != null) {
              for (int j = 0; j < v.size(); j++) {

                CALL_wordWithFormStruct wordwithForm = (CALL_wordWithFormStruct) v.get(j);
                String tmpKanaNew = wordwithForm.getSurfaceFormKana();
                String tmpKanjiNew = wordwithForm.getSurfaceFormKanji();
                String tmpRomajiNew = CharacterUtil.wordKanaToRomaji(tmpKanaNew);
                if (tmpKanaNew.equals(str.trim()) || tmpKanjiNew.equals(str.trim()) || tmpRomajiNew.equals(str.trim())) {
                  result = tmpWord;
                  break;
                }

              }

            }
          } else {
            logger.error("Error: loading verb rules");
          }

        }// end elseif
      }// end IF

    }
    return result;
  }

  // no form restict

  public Vector getVWordFmStr(String str, int intType) {
    // TODO Auto-generated method stub
    Vector<JCALL_Word> vResult = null;
    for (int i = 0; i < vKey.size(); i++) {
      int intKey = vKey.elementAt(i);
      JCALL_Word tmpWord = hm.get(intKey);
      if (tmpWord.getIntType() == intType) {
        String tmpKana = tmpWord.getStrKana();
        String tmpKanji = tmpWord.getStrKanji();
        if (tmpKana.equals(str.trim()) || tmpKanji.equals(str.trim())) {
          if (vResult == null) {
            vResult = new Vector<JCALL_Word>();
            vResult.add(tmpWord);
          }

        }
      }
    }
    return vResult;
  }

  @Override
  synchronized public void save(JCALL_Word word) {
    // TODO Auto-generated method stub
    if (vKey.contains(word.getId())) {
      logger.error("The key is already exist");
    } else {
      vKey.add(word.getId());
      hm.put(word.getId(), word);
      save2FileXml();
    }
  }

  @Override
  synchronized public void update(JCALL_Word word) {
    // TODO Auto-generated method stub
    int intKey = word.getId();
    hm.put(intKey, word);
    save2FileXml();
  }

  public JCALL_Word searchNQKanji(String strNumber, String strQ) {
    // TODO Auto-generated method stub
    logger.debug("enter searchNQKanji");
    JCALL_Word result = null;
    for (int i = 0; i < vKey.size(); i++) {
      int intKey = vKey.elementAt(i);
      JCALL_Word tmpWord = hm.get(intKey);
      if (tmpWord.getIntType() == JCALL_Lexicon.LEX_TYPE_NUMQUANT) {
        String tmpNum = tmpWord.getStrNumber();
        String tmpQ = tmpWord.getStrQuantifier();
        if (tmpNum.equals(strNumber.trim()) && tmpQ.equals(strQ.trim())) {
          result = tmpWord;
          break;
        }

      }
    }
    return result;
  }

  public JCALL_Word searchNQ(JCALL_Word number, JCALL_Word Q) {
    // TODO Auto-generated method stub
    logger.debug("enter searchNQKanji");
    JCALL_Word result = null;
    for (int i = 0; i < vKey.size(); i++) {
      int intKey = vKey.elementAt(i);
      JCALL_Word tmpWord = hm.get(intKey);
      if (tmpWord.getIntType() == JCALL_Lexicon.LEX_TYPE_NUMQUANT) {
        String tmpNum = tmpWord.getStrNumber();
        String tmpQ = tmpWord.getStrQuantifier();
        if ((tmpNum.equals(number.getStrKanji().trim()) || tmpNum.equals(number.getStrKana().trim()))
            && (tmpQ.equals(Q.getStrKanji()) || tmpQ.equals(Q.getStrKanji()))) {
          result = tmpWord;
          break;
        }

      }
    }
    return result;
  }

  public static void main(String[] args) {
    // old lexicon
    CALL_lexiconStruct ls = new CALL_lexiconStruct(null);
    ls.load_lexicon("./Data/lexicon.txt");
    Vector vecOld = ls.getAllWords();

    JCALL_Lexicon l = JCALL_Lexicon.getInstance();
    Vector v = l.getAllWords();
    if (v != null) {
      System.out.println("lexicon size: " + v.size());
      for (int i = 0; i < v.size(); i++) {
        JCALL_Word word = (JCALL_Word) v.elementAt(i);
        String strKana = word.strKana;
        String strKanji = word.strKanji;
        CALL_wordStruct wordstruct = ls.getWordByKanji(strKanji);
        if (wordstruct != null) {
          boolean outmak = false;
          Vector cat = wordstruct.getCategories();
          Vector catpar = word.vCategories;
          for (int j = 0; j < cat.size(); j++) {
            String strCat = (String) cat.get(j);
            boolean mark = false;
            for (int m = 0; m < catpar.size(); m++) {
              if (strCat.equalsIgnoreCase((String) catpar.get(m))) {
                mark = true;
              }
            }
            if (!mark) {
              word.addCategory(strCat);
              outmak = true;
            }
          }
          l.update(word);
        }
      }

    }

  }

}
