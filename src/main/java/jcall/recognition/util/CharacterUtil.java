/**
 * Created on 2007/10/31
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.util;

import jcall.db.JCALL_Lexicon;

import org.apache.log4j.Logger;

public class CharacterUtil {

  static Logger logger = Logger.getLogger(CharacterUtil.class.getName());

  // public static final int KANALOOKUPSIZE = 76;
  public static final int CHAR_TAB = 9;
  public static final String STR_TAB = new String("\t");
  public static final String STR_SPACE = new String(" ");

  public static final String STR_KA = new String("か");
  public static final String STR_DOUBLECONSONENT = new String("っ");
  // public static final String STR_KA = CharacterUtil.HIRAGANATABLE[0][1];
  // public static final String DOUBLECONSONENT
  // =CharacterUtil.HIRAGANATABLE[10][2];

  // *** type ****
  // 1 katakana / 2 hiragana / 3 romaji / 4 kanji(we guess it is KANJI) /other 5
  // wrong
  public static final int TYPE_KATAKANA = 1;
  public static final int TYPE_HIRAGANA = 2;
  public static final int TYPE_ROMAJI = 3;
  public static final int TYPE_KANJI = 4;
  public static final int TYPE_OTHERS = 5;

  public static final int[] VOICEDINDEX = { 11, 12, 13, 14, 15 };
  public static final int[] RESPONDUNVOICEDINDEX = { 1, 2, 3, 5, 5 };

  public static final int[] UNVOICEDINDEX = { 1, 2, 3, 5, 14 };
  public static final int[] RESPONDVOICEDINDEX = { 11, 12, 13, 15, 15 };

  public static final String[] ROMACONSONANTTABLE = { "a", "k", "s", "t", "n", "h", "m", "y", "r", "w", "N", "g", "z",
      "d", "p", "b" };

  public static final String[][] HIRAGANATABLE = { { "あ", "い", "う", "え", "お" }, { "か", "き", "く", "け", "こ" },
      { "さ", "し", "す", "せ", "そ" }, { "た", "ち", "つ", "て", "と" }, { "な", "に", "ぬ", "ね", "の" },
      { "は", "ひ", "ふ", "へ", "ほ" }, { "ま", "み", "む", "め", "も" }, { "や", "い", "ゆ", "え", "よ" },
      { "ら", "り", "る", "れ", "ろ" }, { "わ", "い", "う", "え", "を" }, { "ん", "ー", "っ", "", "" }, { "が", "ぎ", "ぐ", "げ", "ご" },
      { "ざ", "じ", "ず", "ぜ", "ぞ" }, { "だ", "ぢ", "づ", "で", "ど" }, { "ぱ", "ぴ", "ぷ", "ぺ", "ぽ" },
      { "ば", "び", "ぶ", "べ", "ぼ" }, { "ゃ", "ぃ", "ゅ", "ぇ", "ょ" }, { "ぁ", "ぃ", "ぅ", "ぇ", "ぉ" } };

  public static final String[][] KATAKANATABLE = { { "ア", "イ", "ウ", "エ", "オ" }, { "カ", "キ", "ク", "ケ", "コ" },
      { "サ", "シ", "ス", "セ", "ソ" }, { "タ", "チ", "ツ", "テ", "ト" }, { "ナ", "ニ", "ヌ", "ネ", "ノ" },
      { "ハ", "ヒ", "フ", "ヘ", "ホ" }, { "マ", "ミ", "ム", "メ", "モ" }, { "ヤ", "イ", "ユ", "エ", "ヨ" },
      { "ラ", "リ", "ル", "レ", "ロ" }, { "ワ", "イ", "ウ", "エ", "ヲ" }, { "ン", "ー", "ッ", "", "" }, { "ガ", "ギ", "グ", "ゲ", "ゴ" },
      { "ザ", "ジ", "ズ", "ゼ", "ゾ" }, { "ダ", "ヂ", "ヅ", "デ", "ド" }, { "パ", "ピ", "プ", "ペ", "ポ" },
      { "バ", "ビ", "ブ", "ベ", "ボ" }, { "ﾔ", "ｲ", "ﾕ", "ｴ", "ﾖ" }, { "ｱ", "ｲ", "ｳ", "ｴ", "ｵ" } };

  public static final String[][] ROMATABLE = { { "a", "i", "u", "e", "o" }, { "ka", "ki", "ku", "ke", "ko" },
      { "sa", "shi", "su", "se", "so" }, { "ta", "chi", "tsu", "te", "to" }, { "na", "ni", "nu", "ne", "no" },
      { "ha", "hi", "fu", "he", "ho" }, { "ma", "mi", "mu", "me", "mo" }, { "ya", "i", "yu", "e", "yo" },
      { "ra", "ri", "ru", "re", "ro" }, { "wa", "i", "u", "e", "wo" }, { "n", "-", "q", "", "" },
      { "ga", "gi", "gu", "ge", "go" }, { "za", "ji", "zu", "ze", "zo" }, { "da", "di", "zu", "de", "do" },
      { "pa", "pi", "pu", "pe", "po" }, { "ba", "bi", "bu", "be", "bo" }, { "ya", "i", "yu", "e", "yo" },
      { "a", "i", "u", "e", "o" }

  };

  // 16*5
  public static final String[][] JULIANTABLE = { { "a", "i", "u", "e", "o" }, { "k a", "k i", "k u", "k e", "k o" },
      { "s a", "sh i", "s u", "s e", "s o" }, { "t a", "ch i", "ts u", "t e", "t o" },
      { "n a", "n i", "n u", "n e", "n o" }, { "h a", "h i", "f u", "h e ", "h o" },
      { "m a", "m i", "m u", "m e", "m o" }, { "y a", "y i", "y u", "y e", "y o" },
      { "r a", "r i", "r u", "r e", "r o" }, { "w a", "i", "u", "e", "o" }, { "N", ":", "q", "", "" },
      { "g a", "g i", "g u", "g e", "g o" }, { "z a", "j i", "z u", "z e", "z o" },
      { "d a", "d i", "d u", "d e", "d o" }, { "p a", "p i", "p u", "p e", "p o" },
      { "b a", "b i", "b u", "b e", "b o" }, };

  public CharacterUtil() {

  }

  /*
   * ////////////////////////// functions for verb transformation
   */// ////////////////////////////
  public static String[] getRow(String strChar) {
    if (strChar != null && strChar.length() == 1) {
      for (int j = 1; j < HIRAGANATABLE.length; j++) {
        String strTemp = HIRAGANATABLE[j][2];
        if (strChar.equals(strTemp)) {
          return HIRAGANATABLE[j];
        }

      }
    }
    return null;
  }

  /*
   * ////////////////////////// JAPANESE word handling for julian grammar &
   * pronunciation of lexicon's words
   */// ////////////////////////////

  public static String wordKanaToJulian(String strWord, int intType) {
    String outputString = new String("");
    outputString = strWord + STR_TAB;
    outputString = outputString + wordKanaToPronunce(strWord, intType);
    return outputString;
  }

  public static String wordKanaToJulianVoca(String strWord, int intType) {

    String outputString = wordKanaToPronunce(strWord, intType);
    return outputString;
  }

  public static String strKanaToJulian(String strWord, int intType) {
    String outputString = new String("");
    outputString = strWord + STR_TAB;
    outputString = outputString + strKanaToPronunce(strWord, intType);
    return outputString;
  }

  /**
   * @param strWord
   * @param intType
   *          noun,verb,particle etc. all we need to check is particle は、へ; no
   *          include(verb ではありません、ではない)
   * @return
   */
  public static String wordKanaToPronunce(String strWord, int intType) {
    String outputString = new String("");
    String strChar;
    String strJulian;

    outputString = new String("");

    if (strWord != null) {
      strWord = strWord.trim();
      if (strWord.equals("は") && intType == JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL) {
        outputString = "w a";
      } else if (strWord.equals("では") && intType == JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL) {
        outputString = "d e w a";
      } else if (strWord.equals("へ") && intType == JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL) {
        outputString = "e";
      } else {

        for (int i = 0; i < strWord.length(); i++) {
          strChar = new String("" + strWord.charAt(i));
          if ((strChar.compareTo("ゃ") == 0) || (strChar.compareTo("ャ") == 0)) {
            if (i == 0) {
              logger.error("first char is [ゃ/ャ]--wrong word:" + strWord);
            } else if (i > 0 && outputString.length() > 2) {
              logger.debug("the char[-" + i + "-]" + "now the outputstring is[- " + outputString + "-]");
              outputString = outputString.substring(0, outputString.length() - 2);

              if ((outputString.endsWith("sh")) || (outputString.endsWith("ch")) || (outputString.endsWith("j"))) {
                // Special case: Change shi to sha etc
                outputString = outputString + STR_SPACE + "a";
              } else {

                outputString = outputString + "y" + STR_SPACE + "a";
              }
            }
          } else if ((strChar.compareTo("ゅ") == 0) || (strChar.compareTo("ュ") == 0)) {
            if (i > 0 && outputString.length() > 2) {
              logger.debug("the char[-" + i + "-]" + "now the outputstring is[- " + outputString.length() + "-]");
              outputString = outputString.substring(0, outputString.length() - 2);

              if ((outputString.endsWith("sh")) || (outputString.endsWith("ch")) || (outputString.endsWith("j"))) {
                // Special case: Change shi to shu etc
                outputString = outputString + STR_SPACE + "u";
              } else {

                outputString = outputString + "y" + STR_SPACE + "u";
              }
            }
          } else if ((strChar.compareTo("ょ") == 0) || (strChar.compareTo("ョ") == 0)) {
            if (i > 0 && outputString.length() > 2) {
              logger.debug("the char[-" + i + "-]" + "now the outputstring is[- " + outputString.length() + "-]");
              outputString = outputString.substring(0, outputString.length() - 2);

              if ((outputString.endsWith("sh")) || (outputString.endsWith("ch")) || (outputString.endsWith("j"))) {
                // Special case: Change shi to shu etc
                outputString = outputString + STR_SPACE + "o";
              } else {

                outputString = outputString + "y" + STR_SPACE + "o";
              }
            }
          } else if ((strChar.compareTo("ー") == 0)) {
            if (i > 0 && outputString.length() > 2) {
              logger.debug("the [-" + i + "-]" + "now the outputstring is[- " + outputString.length() + "-]");
              outputString = outputString + ":";

            }
          } else {
            // // Treat as normal kana
            boolean count = false;
            for (int j = 0; j < HIRAGANATABLE.length; j++) {
              if (!count) {
                for (int k = 0; k < HIRAGANATABLE[0].length; k++) {
                  String strTemp1 = HIRAGANATABLE[j][k];
                  String strTemp2 = KATAKANATABLE[j][k];
                  if (strChar.equals(strTemp1) || strChar.equals(strTemp2)) {
                    strJulian = JULIANTABLE[j][k];
                    outputString = outputString + STR_SPACE + strJulian;
                    // System.out.println("[j][k]" + j + k + outputString +
                    // "---end");
                    count = true;
                    break;
                  }
                }
              } else {
                break;
              }
            }
          }
        } // end of for
      } // end of else
      logger.debug("String conversion: In [" + strWord + "], Out: [" + outputString + "]");
    }// end if
    // Return the combine string
    return outputString;
  }

  /**
   * @param strWordComponent
   * @param intType
   *          noun,verb,particle etc. all we need to check is particle は、へ;
   *          including (verb ではありません、ではない)
   * @return
   */
  public static String strKanaToPronunce(String strWordComponent, int intType) {
    // logger.debug("word: "+ call_word.getStrKana());
    // String strWord = call_word.getStrKana();

    String strWord = strWordComponent.trim();
    String outputString = new String("");
    String strChar;
    String strJulian;
    String strPreChar = "";
    outputString = new String("");
    if (strWord != null) {
      if (strWord.equals("は") && intType == JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL) {
        outputString = "w a";
      } else if (strWord.equals("へ") && intType == JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL) {
        outputString = "e";
      } else {// in char level
        for (int i = 0; i < strWord.length(); i++) {
          strChar = new String("" + strWord.charAt(i));
          logger.debug("strChar: " + strChar + " strPreChar: " + strPreChar);
          if ((strChar.compareTo("は") == 0)) {
            if (strPreChar.compareTo("で") == 0) {
              outputString = outputString + STR_SPACE + "w a";
              logger.debug("find a dewa");
            } else {
              outputString = outputString + STR_SPACE + "h a";
            }
          }
          // delete as considering the wrong concept of long pronunce here
          // else if(checkLongChar(strPreChar,strChar)){
          // outputString = outputString + ":";
          // }
          else if ((strChar.compareTo("ゃ") == 0) || (strChar.compareTo("ャ") == 0)) {
            if (i == 0) {
              logger.debug("first char is [ゃ/ャ]--wrong word:" + strWord);
            } else if (i > 0 && outputString.length() > 2) {
              logger.debug("the [-" + i + "-]" + "now the outputstring is[- " + outputString.length() + "-]");
              outputString = outputString.substring(0, outputString.length() - 2);

              if ((outputString.endsWith("sh")) || (outputString.endsWith("ch")) || (outputString.endsWith("j"))) {
                // Special case: Change shi to sha etc
                outputString = outputString + STR_SPACE + "a";
              } else {

                outputString = outputString + "y" + STR_SPACE + "a";
              }
            }
          } else if ((strChar.compareTo("ゅ") == 0) || (strChar.compareTo("ュ") == 0)) {
            if (i > 0 && outputString.length() > 2) {
              logger.debug("the [-" + i + "-]" + "now the outputstring is[- " + outputString.length() + "-]");
              outputString = outputString.substring(0, outputString.length() - 2);

              if ((outputString.endsWith("sh")) || (outputString.endsWith("ch")) || (outputString.endsWith("j"))) {
                // Special case: Change shi to shu etc
                outputString = outputString + STR_SPACE + "u";
              } else {

                outputString = outputString + "y" + STR_SPACE + "u";
              }
            }
          } else if ((strChar.compareTo("ょ") == 0) || (strChar.compareTo("ョ") == 0)) {
            if (i > 0 && outputString.length() > 2) {
              logger.debug("the [-" + i + "-]" + "now the outputstring is[- " + outputString.length() + "-]");
              outputString = outputString.substring(0, outputString.length() - 2);

              if ((outputString.endsWith("sh")) || (outputString.endsWith("ch")) || (outputString.endsWith("j"))) {
                // Special case: Change shi to shu etc
                outputString = outputString + STR_SPACE + "o";
              } else {

                outputString = outputString + "y" + STR_SPACE + "o";
              }
            }
          } else if ((strChar.compareTo("ー") == 0)) {
            if (i > 0 && outputString.length() > 2) {
              logger.debug("the [-" + i + "-]" + "now the outputstring is[- " + outputString.length() + "-]");
              outputString = outputString + ":";

            }
          } else {
            // // Treat as normal kana
            boolean count = false;
            for (int j = 0; j < HIRAGANATABLE.length; j++) {
              if (!count) {
                for (int k = 0; k < HIRAGANATABLE[0].length; k++) {
                  String strTemp1 = HIRAGANATABLE[j][k];
                  String strTemp2 = KATAKANATABLE[j][k];
                  if (strChar.equals(strTemp1) || strChar.equals(strTemp2)) {
                    strJulian = JULIANTABLE[j][k];
                    outputString = outputString + STR_SPACE + strJulian;
                    count = true;
                    break;
                  }
                }
              } else {
                break;
              }
            }
          }

          strPreChar = strChar;
        }// end of for

      }
      outputString = outputString.trim();
      logger.debug("String conversion: In [" + strWord + "], Out: [" + outputString + "]");
    }// end if

    return outputString;
  }

  /*
   * ////////////////////////// JAPANESE character handling for JCALL typing
   * input
   */// ////////////////////////////

  public static String wordKanaToRomaji(String strWord) {
    String outputString = new String("");
    String character;
    boolean smalltsu = false;
    if (strWord != null) {
      strWord = strWord.trim();
      for (int i = 0; i < strWord.length(); i++) {
        character = new String("" + strWord.charAt(i));

        if ((character.compareTo("っ") == 0) || (character.compareTo("ッ") == 0)) {
          if (i == 0) {
            logger.debug("first char is [っ/ッ]--wrong word:" + strWord);
          } else if (i > 0 && outputString.length() > 0) {
            // System.out.println("the [-"+i+"-]"+"now the outputstring is[- "+outputString+"-]");
            smalltsu = true;
          }
        }// Is character a small one?
        else if ((character.compareTo("ゃ") == 0) || (character.compareTo("ャ") == 0)) {
          if (outputString.length() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);

            if ((outputString.endsWith("sh")) || (outputString.endsWith("ch"))) {
              // Special case: Change shi to sha etc
              outputString = outputString + "a";
            } else {
              // Standard case
              outputString = outputString + "ya";
            }
          }
        } else if ((character.compareTo("ゅ") == 0) || (character.compareTo("ュ") == 0)) {
          if (outputString.length() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);

            if ((outputString.endsWith("sh")) || (outputString.endsWith("ch"))) {
              // Special case: Change shi to shu etc
              outputString = outputString + "u";
            } else {
              // Standard case
              outputString = outputString + "yu";
            }
          }
        } else if ((character.compareTo("ょ") == 0) || (character.compareTo("ョ") == 0)) {
          if (outputString.length() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);

            if ((outputString.endsWith("sh")) || (outputString.endsWith("ch"))) {
              // Special case: Change shi to shu etc
              outputString = outputString + "o";
            } else {
              // Standard case
              outputString = outputString + "yo";
            }
          }
        } else if ((character.compareTo("ぁ") == 0) || (character.compareTo("ァ") == 0)) {
          if (outputString.length() > 1) {
            outputString = outputString.substring(0, outputString.length() - 1);
          }
          outputString = outputString + "a";
        } else if ((character.compareTo("ぃ") == 0) || (character.compareTo("ィ") == 0)) {
          if (outputString.length() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);
          }
          outputString = outputString + "i";
        } else if ((character.compareTo("ぅ") == 0) || (character.compareTo("ゥ") == 0)) {
          if (outputString.length() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);
          }
          outputString = outputString + "u";
        } else if ((character.compareTo("ぇ") == 0) || (character.compareTo("ェ") == 0)) {
          if (outputString.length() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);
          }
          outputString = outputString + "e";
        } else if ((character.compareTo("ぉ") == 0) || (character.compareTo("ォ") == 0)) {
          if (outputString.length() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);
          }
          outputString = outputString + "o";
        } else {
          // System.out.println("the word is a general word++++"+character);
          boolean count = false;
          // written = false;
          for (int j = 0; j < HIRAGANATABLE.length; j++) {
            if (!count) {
              for (int k = 0; k < HIRAGANATABLE[0].length; k++) {
                String strTemp1 = HIRAGANATABLE[j][k];
                String strTemp2 = KATAKANATABLE[j][k];
                if (character.equals(strTemp1) || character.equals(strTemp2)) {
                  if (smalltsu) {
                    // We want to have two of the first character
                    smalltsu = false;
                    outputString = outputString + (ROMATABLE[j][k]).charAt(0);
                  }
                  // strJulian = ROMATABLE[j][k];
                  outputString = outputString + ROMATABLE[j][k];
                  // System.out.println("[j][k]" + j + k + outputString +
                  // "---end");
                  count = true;
                  break;
                }
              }
            } else {
              break;
            }
          }
        } // end of else

      } // end of for
    } // end of is
    // Return the combine string
    return outputString;
  }

  /**
   * @param strWord
   *          hiragana word
   * @return
   */
  public static String wordHiragana2Katakana(String strWord) {
    String strResult = "";
    String strChar = "";
    String strPreChar;
    if (strWord != null) {
      strWord = strWord.trim();
      for (int i = 0; i < strWord.length(); i++) {
        strPreChar = strChar;
        strChar = new String("" + strWord.charAt(i));
        // System.out.println("charAt["+i+"]"+"is"+strChar);
        if (checkLongChar(strPreChar, strChar)) {
          // System.out.println("is a long char"+strChar);
          strResult = strResult + "ー";
        } else {
          String str = charHiragana2Katakana(strChar);
          if (str != null) {
            strResult += str;
            // System.out.println("charAt["+i+"]"+" its katakana char is"+str);
          }
        }
      }
    } // end of if
    // Return the combine string
    return strResult;
  }

  /**
   * @param strPreChar
   * @param strChar
   *          1 [あ]colummn+[あ];[い]colummn+[い];[う]colummn+[う];[え]colummn+[い]/[え];
   *          [お]colummn+[う]/[お]; 2 [ゃ]+[あ];[ゅ]+[う];[ょ]+[う]; 3 [ー] check if it
   *          is a long pronuncation if so, return its responding wrong word or
   *          else return null
   * @return if strChar is a long pronunce
   */
  private static boolean checkLongChar(String strPreChar, String strChar) {
    // System.out.println("enter checkLongChar strPreChar is "+strPreChar+"strChar is "+strChar);
    boolean booLongPronunce = false;
    if (strPreChar != null && strPreChar.length() > 0) {
      if (strChar.equals("ー")) {
        booLongPronunce = true;
      } else {
        // type 2
        if ((strPreChar.compareTo("ゃ") == 0 && strChar.equals("あ"))) {
          booLongPronunce = true;
        } else if ((strPreChar.compareTo("ャ") == 0) && strChar.equals("ア")) {
          booLongPronunce = true;
        } else if ((strPreChar.compareTo("ゅ") == 0) && strChar.equals("ウ")) {
          booLongPronunce = true;
        } else if ((strPreChar.compareTo("ュ") == 0) && strChar.equals("ウ")) {
          booLongPronunce = true;
        } else if ((strPreChar.compareTo("ょ") == 0) && strChar.equals("う")) {
          booLongPronunce = true;
        } else if ((strPreChar.compareTo("ョ") == 0) && strChar.equals("ウ")) {
          booLongPronunce = true;
        }// type 1
        else {
          String charColumnName1 = CharacterUtil.charColumnName(strPreChar);
          // String charColumnName2 =
          // CharacterUtil.characterColumnName(strChar);
          if (charColumnName1 != null) {
            if (charColumnName1.equals(strChar)) {
              booLongPronunce = true;
            } else if (charColumnName1.equals("え") && strChar.equals("い")) {
              booLongPronunce = true;
            } else if (charColumnName1.equals("エ") && strChar.equals("イ")) {
              booLongPronunce = true;
            } else if (charColumnName1.equals("お") && strChar.equals("う")) {
              booLongPronunce = true;
            } else if (charColumnName1.equals("オ") && strChar.equals("ウ")) {
              booLongPronunce = true;
            }
          }
        }
      }
    }
    return booLongPronunce;
  }

  /*
   * return the row name of the kana character at the first time if strWord is
   * "う"　return "a" row
   */
  public static String characterRowName(String strWord) {

    String strChar = strWord.trim();
    if (strChar != null && strChar.length() == 1) {

      for (int j = 0; j < HIRAGANATABLE.length; j++) {

        for (int k = 0; k < HIRAGANATABLE[0].length; k++) {
          String strTemp1 = HIRAGANATABLE[j][k];
          if (strChar.equals(strTemp1)) {
            return ROMATABLE[j][0];
          }

        }
      }
    }
    return null;
  }

  /**
   * @param strChar
   *          か、さ、た、は　－－が、ざ、だ、ぱ、ぱ
   * @return　　　
   */
  public static String changeVoicedToLight(String strChar) {
    String strResult = null;
    strChar = strChar.trim();
    if (strChar != null && strChar.length() == 1) {
      for (int i = 0; i < VOICEDINDEX.length; i++) {
        int j = VOICEDINDEX[i];
        int m = RESPONDUNVOICEDINDEX[i];
        for (int k = 0; k < 5; k++) {
          String strTemp1 = HIRAGANATABLE[j][k];
          String strTemp2 = KATAKANATABLE[j][k];
          if (strChar.equals(strTemp1)) {
            strResult = HIRAGANATABLE[m][k];
            return strResult;
          } else if (strChar.equals(strTemp2)) {
            strResult = KATAKANATABLE[m][k];
            return strResult;
          }
        }
      }
    }
    return strResult;
  }

  /**
   * @param strChar
   *          UNVOICED --> RESPONDVOICED katakana/hiragana
   *
   * @return　　　
   */
  public static String LightToVoiced(String strChar) {
    String strResult = null;
    strChar = strChar.trim();
    if (strChar != null && strChar.length() == 1) {
      for (int i = 0; i < UNVOICEDINDEX.length; i++) {
        int j = RESPONDVOICEDINDEX[i];
        int m = UNVOICEDINDEX[i];
        for (int k = 0; k < 5; k++) {
          String strTemp1 = HIRAGANATABLE[m][k];
          String strTemp2 = KATAKANATABLE[m][k];
          if (strChar.equals(strTemp1)) {
            strResult = HIRAGANATABLE[j][k];
            return strResult;
          } else if (strChar.equals(strTemp2)) {
            strResult = KATAKANATABLE[j][k];
            return strResult;
          }
        }
      }
    }
    return strResult;
  }

  /**
   * @param strChar
   *          か、さ、た、は、ぱ　－－が、ざ、だ、ば、ば
   * @return　　　
   */
  public static String changeLightToVoiced(String strChar) {
    String strResult = null;
    strChar = strChar.trim();
    if (strChar != null && strChar.length() == 1) {
      for (int i = 0; i < VOICEDINDEX.length; i++) {
        int j = VOICEDINDEX[i];
        int m = UNVOICEDINDEX[i];
        for (int k = 0; k < 5; k++) {
          String strTemp1 = HIRAGANATABLE[m][k];
          String strTemp2 = KATAKANATABLE[m][k];
          if (strChar.equals(strTemp1)) {
            strResult = HIRAGANATABLE[j][k];
            return strResult;
          } else if (strChar.equals(strTemp2)) {
            strResult = KATAKANATABLE[j][k];
            return strResult;
          }
        }
      }
    }
    return strResult;
  }

  /*
   * return the row of the kana character at the first time if strWord is
   * "う"　return "a" row
   */
  public static String[] characterRow(String strWord) {

    String strChar = strWord.trim();
    if (strChar != null && strChar.length() == 1) {
      for (int j = 0; j < HIRAGANATABLE.length; j++) {
        for (int k = 0; k < HIRAGANATABLE[0].length; k++) {
          String strTemp1 = HIRAGANATABLE[j][k];
          if (strChar.equals(strTemp1)) {
            return HIRAGANATABLE[j];
          }

        }
      }
    }
    return null;
  }

  public static String[] charRow(String strWord) {
    // TODO Auto-generated method stub
    return getRow(strWord);
  }

  /*
   * return the column name of the kana character if strWord is "う"　return "u"
   * columm
   */
  public static String charColumnName(String strChar) {
    String strResult = null;
    if (strChar != null && strChar.length() == 1) {
      for (int j = 0; j < HIRAGANATABLE.length; j++) {
        if (j == 10) {
          continue;
        } else {
          for (int k = 0; k < HIRAGANATABLE[0].length; k++) {
            String strTemp1 = HIRAGANATABLE[j][k];
            String strTemp2 = KATAKANATABLE[j][k];
            if (strChar.equals(strTemp1)) {
              strResult = HIRAGANATABLE[0][k];
              return strResult;
            } else if (strChar.equals(strTemp2)) {
              strResult = KATAKANATABLE[0][k];
              return strResult;
            }
          }
        }
      }
    }
    return strResult;
  }

  // charColumnNameHIRA
  /**
   * @param strChar
   * @return "ん、－、っ"　has no column name,here;
   */
  public static String charColumnNameHIRA(String strChar) {
    String strResult = null;
    if (strChar != null && strChar.length() == 1) {
      for (int j = 0; j < HIRAGANATABLE.length; j++) {
        if (j == 10) {
          continue;
        } else {
          for (int k = 0; k < HIRAGANATABLE[0].length; k++) {
            String strTemp1 = HIRAGANATABLE[j][k];
            String strTemp2 = KATAKANATABLE[j][k];
            if (strChar.equals(strTemp1) || strChar.equals(strTemp2)) {
              strResult = HIRAGANATABLE[0][k];
              return strResult;
            }
          }
        }
      }
    }
    return strResult;
  }

  public static String charHiragana2Katakana(String strChar) {
    // String strResult = null;
    if (strChar != null && strChar.length() == 1) {
      for (int j = 0; j < HIRAGANATABLE.length; j++) {
        for (int k = 0; k < HIRAGANATABLE[0].length; k++) {
          String strTemp1 = HIRAGANATABLE[j][k];
          String strTemp2 = KATAKANATABLE[j][k];
          if (strChar.equals(strTemp1)) {
            return strTemp2;
          }
        }
      }
    }
    return null;
  }

  /*
   * 
   * @param strWord predicate the kana word is katakana or hiragana
   * 
   * @return int 1 katakana / 2 hiragana / 3 romaji / 4 kanji(we guess it is
   * KANJI) /other 5 wrong when strWord is a correct word, other means "kanji"
   */
  // ///not proper,should be changed, but if we suppose the word's type is
  // consitent,then its ok;
  public static int checkWordClass(String strWord) {
    int intCharfirst;
    int intCharlast;
    int intResult = 5;
    if (strWord.length() > 0) {
      if (strWord.length() == 1) {
        String strCharfirst = new String("" + strWord.charAt(0));
        intResult = checkCharClass(strCharfirst);
      } else {
        // お兄さん ジョン様 道子さん
        for (int i = 0; i < strWord.length(); i++) {
          String strChar = new String("" + strWord.charAt(i));
          int intChar = checkCharClass(strChar);
          if (intChar == 4) {
            intResult = intChar;
            // logger.debug("strWord type: "+intResult);
            break;
          }
        }

        if (intResult == 5) {

          String strCharfirst = new String("" + strWord.charAt(0));
          String strCharlast = strWord.substring(strWord.length() - 1);
          intCharfirst = checkCharClass(strCharfirst);
          intCharlast = checkCharClass(strCharlast);

          // logger.debug("strCharfirst type:"+intCharfirst+" strCharlast type:"+intCharlast);

          if (intCharfirst == intCharlast) {
            intResult = intCharlast;
            // }else if(intCharfirst==1 && intCharlast==4){ //ジョン様
            // intResult = intCharlast;
            // }else if(intCharfirst==4 && intCharlast==2){//道子さん
            // intResult = 4;
            //
          } else if (intCharfirst == 1 && intCharlast == 2) { // ジョンさん
            // maybe a person's name+"san"(hiragana)
            intResult = intCharfirst;// intCharlast;

          } else {
            intResult = 5;
            logger.error("wrong, maybe use katakana and hiragana at the same time----" + strWord);
          }

        }
      }
    }
    return intResult;
  }

  /**
   * @param strChar
   * @return 1,2,3,4,5
   */
  public static int checkCharClass(String strChar) {
    // String strResult = new String("hoka");
    // String strChar = strSouceWord;
    for (int j = 0; j < HIRAGANATABLE.length; j++) {
      for (int k = 0; k < HIRAGANATABLE[0].length; k++) {
        String strTemp1 = HIRAGANATABLE[j][k];
        String strTemp2 = KATAKANATABLE[j][k];
        String strTemp3 = ROMATABLE[j][k];
        if (strChar.equals(strTemp2)) {
          return 1;
        } else if (strChar.equals(strTemp1)) {
          return 2;
        } else if (strChar.equals(strTemp3)) {
          return 3;
        } else if (strTemp3.contains(strChar)) {
          return 3;
        }
      }
    }
    return 4;
  }

  public static void main(String[] args) {

    // String str = "きょうと";
    // String strnew = (String)CharacterUtil.wordKanaToJulian(str);
    // String strBuf = CharacterUtil.strReplace(str, "th", "ww");
    // System.out.println(strBuf);
    // String str1 = str+STR_TAB+"x\tz";
    // System.out.println(str1);
    // System.out.println("a"+str1.charAt(15)+"b");
    // int[][] a = {{1,2,3},{4,5,6}};
    // System.out.println("chaged julian format----"+strnew);
    // String str1 = "く";
    // String[] strnew1 = CharacterUtil.characterRow(str1);
    // System.out.println("get the word vector----"+strnew1[0]+" two "+
    // strnew1[1]);
    // String str = CharacterUtil.wordHiragana2Katakana("いちご");//イイイチゴ??? (have
    // modified)
    // String str = CharacterUtil.wordKanaToRomaji("おさけ");
    // String str = CharacterUtil.wordKanaToRomaji("ビール");
    // int a = CharacterUtil.checkWordClass("コンピューター");
    // System.out.println("int 　"+a);

    // String str = JCALL_AlphabetUtil.wordKanaToJulianVoca("ワーズ",2);
    // System.out.println("result: " +str);

    String str = CharacterUtil.wordKanaToRomaji("テーブル");
    System.out.println("result: " + str);

  }

}
