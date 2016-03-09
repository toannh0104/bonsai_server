package jcall.recognition.languagemodel;

import java.util.StringTokenizer;
import java.util.Vector;

import jcall.CALL_formStruct;
import jcall.db.JCALL_NetworkSubNodeStruct;
import jcall.db.JCALL_Word;
import jcall.recognition.dataprocess.EPLeaf;
import jcall.recognition.dataprocess.LexiconWordMeta;
import jcall.recognition.util.CharacterUtil;

import org.apache.log4j.Logger;

public class FormatVerb {

  static Logger logger = Logger.getLogger(FormatVerb.class.getName());

  public static final int kanaLookupSize = 76;
  public static final String[] verbtail = { "く", "ぐ", "す", "つ", "ぬ", "ぶ", "む", "る", "う" };
  public static final String[] verbtail2 = { "い", "え", "き", "け", "し", "せ", "ち", "て", "に", "ね", "ひ", "へ", "み", "め", "り",
      "れ", "ぎ", "げ", "じ", "ぜ", "ぢ", "で", "び", "べ", "ぴ", "ぺ" };
  public static final String past = "た"; // past tense
  public static final String polite = "ます"; // polite
  public static final String negative = "ない"; // negative state
  // public static final String question = "た";
  public static final String te = "て";
  public static final String ing = "ている";
  public static final String completed = "てましう";
  // public static final String[][] da=
  // {{"だ","だった","ではない","ではなかった","","",""},{"です","でした","ではありません","ではありませんでした"}};
  public static final String[] masu = { "ます", "ました", "ません", "ませんでした" };
  public static final String[] arrayDa = { "だ", "ではない", "だった", "ではなかった", "です", "ではありません", "でした", "ではありませんでした" };
  public static final String[] nai = { "ない", "なかった", "ありません", "ませんでした" };

  public static final String[] StringToIndex = { "101010", "101001", "100110", "100101", "011010", "011001", "010110",
      "010101" };

  public FormatVerb() {

  }

  public static String strStripSpaces(String str) {
    String character;
    String outputStr;

    outputStr = new String("");

    for (int i = 0; i < str.length(); i++) {
      character = new String("" + str.charAt(i));
      if (character.compareTo(" ") != 0) {
        outputStr = outputStr + character;
      }
    }

    return outputStr;
  }

  /*
   * compare a character with a string[]
   */
  public static boolean strCompare(String str, String[] strarray) {
    boolean bResult = false;
    for (int i = 0; i < strarray.length; i++) {
      if (str.equalsIgnoreCase(strarray[i])) {
        bResult = true;
        break;
      }
    }
    return bResult;
  }

  /*
   * classify verbs wuduan(1) yiduan(2) kabian(3) sabian(4) panduan(5)
   */
  public String clasifyverb(String str) {
    String strResult = "";
    int i = str.length();

    if (str.equalsIgnoreCase("だ") || str.equalsIgnoreCase("です")) {
      strResult = "panduan";
    }
    if (i >= 2) {
      String tail2 = str.substring(i - 2, i - 1);
      String tail1 = str.substring(i - 1);
      if (!strCompare(tail1, verbtail)) {
        System.out.println("this is a wrong verb in Japanese,please check");
      } else {
        if (tail1.equalsIgnoreCase("る")) {
          if (strCompare(tail2, verbtail2)) {
            strResult = "yiduan";
          } else if (tail2.equalsIgnoreCase("く") && i == 2) {
            strResult = "kabian";
          } else if (tail2.equalsIgnoreCase("す")) {
            strResult = "sabian";
          } else {
            strResult = "wuduan";
          }
        } else {
          strResult = "wuduan";
        }
      }
    }
    return strResult;
  }

  /*
   * assume have knowing the verb type transform verb(except da) to the general
   * seven format result is a array and has serven elements --the sequence is as
   * ---weiran,lianyong,zhongzhi,lianti,jiading,mingling,tuiliang
   */

  public String[] verbFormat(String strVerb, String strType) {
    String[] result = new String[7];
    strVerb = strVerb.trim();
    int i = strVerb.length();
    String head1 = strVerb.substring(0, i - 1);
    String head2 = strVerb.substring(0, i - 2);
    if (strType.equals("panduan")) {
      System.out.println(" no verb Format");

    } else if (strType.equals("kabian")) {
      result[0] = "こ";
      result[1] = "き";
      result[2] = strVerb;
      result[3] = strVerb;
      result[4] = "くれ";
      result[5] = "こい";
      result[6] = "こよう";

    } else if (strType.equals("sabian")) {
      result[0] = head2 + "し";
      result[1] = head2 + "し";
      result[2] = head2 + "する";
      result[3] = head2 + "する";
      result[4] = head2 + "すれ";
      result[5] = head2 + "しろ/せよ";
      result[6] = head2 + "しよう";

    } else if (strType.equals("wuduan")) {

      String tail1 = strVerb.substring(i - 1);
      String[] row = CharacterUtil.charRow(tail1);
      if (row.length == 5) {
        result[0] = head1 + row[0];
        result[1] = head1 + row[1];
        result[2] = head1 + row[2];
        result[3] = head1 + row[2];
        result[4] = head1 + row[3];
        result[5] = head1 + row[3];
        result[6] = head1 + row[4];
      }

    } else if (strType.equals("yiduan")) {
      result[0] = head1;
      result[1] = head1;
      result[2] = strVerb;
      result[3] = strVerb;
      result[4] = head1 + "れ";
      result[6] = head1 + "よ/ろ";
      result[5] = head1 + "よう";
    }
    return result;
  }

  /*
   * strVerb must be kanji or must be a verb transform verb(except da) to the
   * general seven format result is a array and has serven elements --the
   * sequence is as
   * ---weiran,lianyong,zhongzhi,lianti,jiading,mingling,tuiliang,ta
   */
  public static String[] verbFormat(String strVerb) {

    String[] result = new String[8];
    String strResult = "";
    strVerb = strVerb.trim();
    int i = strVerb.length();
    String head1 = strVerb.substring(0, i - 1);
    String head2 = strVerb.substring(0, i - 2);
    if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
      System.out.println(" no verb Format");
    }
    if (i >= 2) {
      String tail2 = strVerb.substring(i - 2, i - 1);
      String tail1 = strVerb.substring(i - 1);
      if (!strCompare(tail1, verbtail)) {
        System.out.println("[" + strVerb + "] is a wrong/not base verb in Japanese,please check");
      } else {
        if (tail1.equalsIgnoreCase("る")) {
          if (strCompare(tail2, verbtail2)) {
            // strResult = "yiduan";
            result[0] = head1;
            result[1] = head1;
            result[2] = strVerb;
            result[3] = strVerb;
            result[4] = head1 + "れ";
            result[5] = head1 + "よ/ろ";
            result[6] = head1 + "よう";
            result[7] = head1 + "た";
          } else if (((tail2.equalsIgnoreCase("く")) || (tail2.equalsIgnoreCase("来"))) && i == 2) {
            // strResult = "kabian";
            result[0] = "こ";
            result[1] = "き";
            result[2] = strVerb;
            result[3] = strVerb;
            result[4] = "くれ";
            result[5] = "こい";
            result[6] = "こよう";
            result[7] = "来た";
          } else if (tail2.equalsIgnoreCase("す")) {
            // strResult = "sabian";
            result[0] = head2 + "し";
            result[1] = head2 + "し";
            result[2] = head2 + "する";
            result[3] = head2 + "する";
            result[4] = head2 + "すれ";
            result[5] = head2 + "しろ/せよ";
            result[6] = head2 + "しよう";
            result[7] = head2 + "した";;
          } else {
            strResult = "wuduan";

          }
        } else {
          strResult = "wuduan";

        }
      }
      if (strResult.equalsIgnoreCase("wuduan")) {
        String[] row = CharacterUtil.charRow(tail1);
        if (row.length == 5) {
          result[0] = head1 + row[0];
          result[1] = head1 + row[1];
          result[2] = head1 + row[2];
          result[3] = head1 + row[2];
          result[4] = head1 + row[3];
          result[5] = head1 + row[3];
          if (tail1.equals("う")) {
            result[6] = head1 + "おう";
          } else {
            result[6] = head1 + row[4] + "う";
          }
          // "く","ぐ","す","つ","ぬ","ぶ","む","る","う"
          // っ　　
          if (tail1.equals("す")) {
            result[7] = head1 + "し";
          } else if (tail1.equals("く")) {
            result[7] = head1 + "いた";
          } else if (tail1.equals("ぐ")) {
            result[7] = head1 + "いだ";
          } else if (tail1.equals("つ") || tail1.equals("る") || tail1.equals("う")) {
            result[7] = head1 + "った";
          } else if (tail1.equals("ぬ") || tail1.equals("ぶ") || tail1.equals("む")) {
            result[7] = head1 + "んだ";
          }

        }
      }
    }
    return result;
  }

  /*
   * strVerb must be kanji or must be a verb transform verb(except da) to the
   * general seven format result is a array and has serven elements --the
   * sequence is as
   * ---weiran,lianyong,zhongzhi,lianti,jiading,mingling,tuiliang,ta,te,se
   */
  public static String[] verbFormatAdwanced(String strVerb) {

    String[] result = new String[10];
    String strResult = "";
    strVerb = strVerb.trim();
    int i = strVerb.length();
    String head1 = strVerb.substring(0, i - 1);
    String head2 = strVerb.substring(0, i - 2);
    if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
      System.out.println(" no verb Format");
    }
    if (i >= 2) {
      head1 = strVerb.substring(0, i - 1);
      head2 = strVerb.substring(0, i - 2);
      String tail2 = strVerb.substring(i - 2, i - 1);
      String tail1 = strVerb.substring(i - 1);
      if (!strCompare(tail1, verbtail)) {
        System.out.println("[" + strVerb + "] is a wrong/not base verb in Japanese,please check");
      } else {
        if (tail1.equalsIgnoreCase("る")) {
          if (strCompare(tail2, verbtail2)) {
            // strResult = "yiduan";
            result[0] = head1;
            result[1] = head1;
            result[2] = strVerb;
            result[3] = strVerb;
            result[4] = head1 + "れ";
            result[5] = head1 + "よ/ろ";
            result[6] = head1 + "よう";
            result[7] = head1 + "た";
            result[8] = head1 + "て";
            result[9] = head1 + "させる";
          } else if (((tail2.equalsIgnoreCase("く")) || (tail2.equalsIgnoreCase("来"))) && i == 2) {
            // strResult = "kabian";
            result[0] = "こ";
            result[1] = "き";
            result[2] = strVerb;
            result[3] = strVerb;
            result[4] = "くれ";
            result[5] = "こい";
            result[6] = "こよう";
            result[7] = "きた";
            result[8] = "きて";
            result[9] = "こさせる";
          } else if (tail2.equalsIgnoreCase("す")) {
            // strResult = "sabian";
            result[0] = head2 + "し";
            result[1] = head2 + "し";
            result[2] = head2 + "する";
            result[3] = head2 + "する";
            result[4] = head2 + "すれ";
            result[5] = head2 + "しろ/せよ";
            result[6] = head2 + "しよう";
            result[7] = head2 + "した";
            result[8] = head2 + "して";
            result[9] = head2 + "させる";

          } else {
            strResult = "wuduan";

          }
        } else {
          strResult = "wuduan";

        }
      }
      if (strResult.equalsIgnoreCase("wuduan")) {
        String[] row = CharacterUtil.charRow(tail1);

        if (row.length == 5) {
          result[0] = head1 + row[0];
          result[1] = head1 + row[1];
          result[2] = head1 + row[2];
          result[3] = head1 + row[2];
          result[4] = head1 + row[3];
          result[5] = head1 + row[3];
          if (tail1.equals("う")) {
            result[6] = head1 + "おう";
          } else {
            result[6] = head1 + row[4] + "う";
          }
          // "く","ぐ","す","つ","ぬ","ぶ","む","る","う"
          // っ　
          if (strVerb.equalsIgnoreCase("いく") || strVerb.equalsIgnoreCase("行く")) {
            result[7] = head1 + "った";
            result[8] = head1 + "って";
          } else {
            if (tail1.equals("す")) {
              result[7] = head1 + "した";
              result[8] = head1 + "して";
            } else if (tail1.equals("く")) {
              result[7] = head1 + "いた";
              result[8] = head1 + "いて";
            } else if (tail1.equals("ぐ")) {
              result[7] = head1 + "いだ";
              result[8] = head1 + "いで";
            } else if (tail1.equals("つ") || tail1.equals("る") || tail1.equals("う")) {
              result[7] = head1 + "った";
              result[8] = head1 + "って";
            } else if (tail1.equals("ぬ") || tail1.equals("ぶ") || tail1.equals("む")) {
              result[7] = head1 + "んだ";
              result[8] = head1 + "んで";
            }
          }
          result[9] = head1 + row[0] + "せる";
        }

      }
    }
    return result;
  }

  /**
   * @param strVerb
   *          ust be kanji or must be a verb,transform verb(except da) to the
   *          general ten format
   * @param group
   * @return a array and has ten elements --the sequence is as
   *         ---weiran,lianyong
   *         ,zhongzhi,lianti,jiading,mingling,tuiliang,ta,te,se
   */
  public static String[] verbFormatAdwanced(String strVerb, String group) {

    String[] result = new String[10];
    String strResult = "";
    strVerb = strVerb.trim();
    int i = strVerb.length();
    String head1 = strVerb.substring(0, i - 1);
    String head2 = strVerb.substring(0, i - 2);
    if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
      logger.debug(" no verb Format");
    }
    if (i >= 2) {
      String tail1 = strVerb.substring(i - 1);
      if (group.equalsIgnoreCase("Group2") || group.equalsIgnoreCase("2")) {// strResult
                                                                            // =
                                                                            // "yiduan";
        result[0] = head1;
        result[1] = head1;
        result[2] = strVerb;
        result[3] = strVerb;
        result[4] = head1 + "れ";
        result[5] = head1 + "よ/ろ";
        result[6] = head1 + "よう";
        result[7] = head1 + "た";
        result[8] = head1 + "て";
        result[9] = head1 + "させる";
      } else if (group.equalsIgnoreCase("Group4") || group.equalsIgnoreCase("4")) {
        // strResult = "kabian";
        result[0] = "こ";
        result[1] = "き";
        result[2] = strVerb;
        result[3] = strVerb;
        result[4] = "くれ";
        result[5] = "こい";
        result[6] = "こよう";
        result[7] = "きた";
        result[8] = "きて";
        result[9] = "こさせる";
      } else if (group.equalsIgnoreCase("Group3") || group.equalsIgnoreCase("3")) {
        // strResult = "sabian";
        if (strVerb.equalsIgnoreCase("する")) {
          result[0] = "し";
          result[1] = "し";
          result[2] = "する";
          result[3] = "する";
          result[4] = "すれ";
          result[5] = "しろ/せよ";
          result[6] = "しよう";
          result[7] = "した";
          result[8] = "して";
          result[9] = "させる";
        } else {
          result[0] = head2 + "し";
          result[1] = head2 + "し";
          result[2] = head2 + "する";
          result[3] = head2 + "する";
          result[4] = head2 + "すれ";
          result[5] = head2 + "しろ/せよ";
          result[6] = head2 + "しよう";
          result[7] = head2 + "した";
          result[8] = head2 + "して";
          result[9] = head2 + "させる";
        }

      } else if (group.equalsIgnoreCase("Group1") || group.equalsIgnoreCase("1")) {// "wuduan"
        // String[] row = CharacterUtil.charRow(tail1);
        if (strVerb.equalsIgnoreCase("ある")) {
          result[0] = "";
          result[1] = "あり";
          result[2] = "ある";
          result[3] = "ある";
          result[4] = "あれ";
          result[5] = "あろ";
          result[6] = "あろう";
          result[7] = "あった";
          result[8] = "あって";
          result[9] = "あらせる";
        } else {
          String[] row = CharacterUtil.charRow(tail1);
          if (row.length == 5) {
            result[0] = head1 + row[0];
            result[1] = head1 + row[1];
            result[2] = head1 + row[2];
            result[3] = head1 + row[2];
            result[4] = head1 + row[3];
            result[5] = head1 + row[3];
            if (tail1.equals("う")) {
              result[6] = head1 + "おう";
            } else {
              result[6] = head1 + row[4] + "う";
            }
            // "く","ぐ","す","つ","ぬ","ぶ","む","る","う"
            // っ　
            if (strVerb.equalsIgnoreCase("いく") || strVerb.equalsIgnoreCase("行く")) {
              result[7] = head1 + "った";
              result[8] = head1 + "って";
            } else {
              if (tail1.equals("す")) {
                result[7] = head1 + "した";
                result[8] = head1 + "して";
              } else if (tail1.equals("く")) {
                result[7] = head1 + "いた";
                result[8] = head1 + "いて";
              } else if (tail1.equals("ぐ")) {
                result[7] = head1 + "いだ";
                result[8] = head1 + "いで";
              } else if (tail1.equals("つ") || tail1.equals("る") || tail1.equals("う")) {
                result[7] = head1 + "った";
                result[8] = head1 + "って";
              } else if (tail1.equals("ぬ") || tail1.equals("ぶ") || tail1.equals("む")) {
                result[7] = head1 + "んだ";
                result[8] = head1 + "んで";
              }
            }
            result[9] = head1 + row[0] + "せる";
          }
        }
      }
    }
    return result;
  }

  /*
   * transform verb to ましょう format(first is plain, second is polite)
   * {plain<0,1,2,3> ,polite<4,5,6,7>}{[present,past][positive,negative]}
   */

  public static String[] verbToInvitation(String strVerb, String group) {
    strVerb = strVerb.trim();
    String[] vResult = new String[2];
    if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
      vResult[0] = "だろう";
      vResult[1] = "でしょう";
    } else {
      String[] arrayFormat = verbFormatAdwanced(strVerb, group);

      vResult[0] = arrayFormat[6];
      vResult[1] = arrayFormat[1] + "ましょう";
    }
    return vResult;
  }

  /*
   * transform verb to ましょう format(no plain, just polite) {polite<4,5,6,7>}
   */

  public static String verbToInvitationPolite(String strVerb, String group) {
    strVerb = strVerb.trim();
    String strResult = null;
    if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
      strResult = "でしょう";
    } else {
      String[] arrayFormat = verbFormatAdwanced(strVerb, group);
      strResult = arrayFormat[1] + "ましょう";
    }
    return strResult;
  }

  public static JCALL_NetworkSubNodeStruct verbToInvitationPolite(JCALL_Word verb, boolean tw) {
    String kana = verb.getStrKana();
    String kanji = verb.getStrKanji();
    String strResult = null;
    String error = "";
    if (tw) {
      error = "TW_DFORM";
    } else {
      error = "DW_DFORM";
    }
    JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
    node.setBAccept(false);
    node.setBValid(true);
    node.setStrError(error);

    if (kana.equalsIgnoreCase("だ") || kana.equalsIgnoreCase("です")) {
      strResult = "でしょう";
      node.setStrKana(strResult);
      node.setStrKanji(strResult);
    } else {
      String[] arrayFormatKana = verbFormatAdwanced(kana, verb.getStrGroup());
      String[] arrayFormatKanji = verbFormatAdwanced(kana, verb.getStrGroup());

      strResult = arrayFormatKana[1] + "ましょう";
      node.setStrKana(strResult);
      strResult = arrayFormatKanji[1] + "ましょう";
      node.setStrKanji(strResult);
    }

    return node;
  }

  public static String verbToInvitationPolite(String strVerb, String group, String strErrType) {
    strVerb = strVerb.trim();
    String strResult = null;
    if (strErrType.equalsIgnoreCase("Masho")) {
      if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
        strResult = "でしょ";
      } else {
        String[] arrayFormat = verbFormatAdwanced(strVerb, group);
        strResult = arrayFormat[1] + "ましょ";
      }
    } else if (strErrType.equalsIgnoreCase("T1T2")) {
      if (group.equalsIgnoreCase("Group1") || group.equalsIgnoreCase("1")) {
        group = "Group2";
        String[] arrayFormat = verbFormatAdwanced(strVerb, group);
        strResult = arrayFormat[1] + "ましょう";
      } else if (group.equalsIgnoreCase("Group2") || group.equalsIgnoreCase("2")) {
        group = "Group1";
        String[] arrayFormat = verbFormatAdwanced(strVerb, group);
        strResult = arrayFormat[1] + "ましょう";
      }
    }
    return strResult;
  }

  /*
   * transform verb to てください format(no plain, second is polite)
   * {polite<4,5,6,7>}{[present,past][positive,negative]}
   */

  public static String verbToOrder(String strVerb) {
    strVerb = strVerb.trim();
    String strResult = null;
    if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
      logger.debug("wrong , have no Order format " + strVerb);
      return null;
    } else {
      String[] arrayFormat = verbFormatAdwanced(strVerb);
      strResult = arrayFormat[8] + "ください";
    }
    return strResult;

  }

  public static Vector verbToOrder(String strVerb, boolean negative, String group) {
    strVerb = strVerb.trim();
    Vector<String> vecResult = new Vector<String>();
    String strResult = null;
    if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
      logger.debug("wrong , have no Order format " + strVerb);
      return null;
    } else {
      String[] arrayFormat = verbFormatAdwanced(strVerb, group);
      strResult = arrayFormat[8] + "ください";
      vecResult.addElement(strResult);
      if (negative) {
        strResult = arrayFormat[0] + "ないでください";
        vecResult.addElement(strResult);
      }
    }
    return vecResult;
  }

  public static Vector verbToOrder(JCALL_Word verb, boolean negative, boolean tw) {
    String group = verb.getStrGroup();
    Vector vecResult = new Vector();
    String strVerb = verb.getStrKana();
    String strVerbkanji = verb.getStrKanji();
    String strResult = null;
    String error = "";
    if (tw) {
      error = "TW_DFORM";
    } else {
      error = "DW_DFORM";
    }
    if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
      logger.debug("wrong , have no Order format " + strVerb);
      return null;
    } else {
      String[] arrayFormatKana = verbFormatAdwanced(strVerb, group);
      String[] arrayFormatKanji = verbFormatAdwanced(strVerbkanji, group);

      strResult = arrayFormatKana[8] + "ください";
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setStrError(error);
      node.setStrKana(arrayFormatKana[8] + "ください");
      node.setStrKanji(arrayFormatKanji[8] + "ください");
      vecResult.addElement(node);

      if (negative) {
        JCALL_NetworkSubNodeStruct pnode = new JCALL_NetworkSubNodeStruct();
        pnode.setBAccept(false);
        pnode.setBValid(true);
        pnode.setStrError(error);
        pnode.setStrKana(arrayFormatKana[0] + "ないでください");
        pnode.setStrKanji(arrayFormatKanji[0] + "ないでください");
        vecResult.addElement(pnode);
      }
    }
    return vecResult;
  }

  public static Vector verbToOrder(LexiconWordMeta lwm, boolean negative, String group) {
    String strVerb = lwm.getStrKanji().trim();
    String strVerbKana = lwm.getStrKana().trim();
    Vector<EPLeaf> vecResult = new Vector();
    String strResult = null;
    String strResultKana = null;
    EPLeaf epl;
    if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
      logger.debug("wrong , have no Order format " + strVerb);
      return null;
    } else {
      String[] arrayFormat = verbFormatAdwanced(strVerb, group);
      String[] arrayFormatKana = verbFormatAdwanced(strVerbKana, group);
      strResult = arrayFormat[8] + "ください";
      strResultKana = arrayFormatKana[8] + "ください";
      epl = new EPLeaf();
      epl.setId(lwm.getId());
      epl.setIntType(lwm.getIntType());
      epl.setStrErrorType("VS_DFORM");
      epl.setStrKana(strResultKana);
      epl.setStrKanji(strResult);
      vecResult.addElement(epl);
      if (negative) {
        strResult = arrayFormat[0] + "ないでください";
        strResultKana = arrayFormatKana[0] + "ないでください";
        EPLeaf epl2 = new EPLeaf();
        epl2.setId(lwm.getId());
        epl2.setIntType(lwm.getIntType());
        epl2.setStrErrorType("VS_DFORM");
        epl2.setStrKana(strResultKana);
        epl2.setStrKanji(strResult);
        vecResult.addElement(epl2);
      }
    }
    return vecResult;
  }

  public static String verbToOrder(String strVerb, boolean negative, String group, String strErrType) {
    strVerb = strVerb.trim();
    Vector<String> vecResult = new Vector<String>();
    String strResult = "";
    if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
      logger.debug("wrong , have no Order format " + strVerb);
      return null;
    } else {
      if (strErrType.equalsIgnoreCase("TeDe")) {
        String[] arrayFormat = verbFormatAdwanced(strVerb, group);
        if (!negative) {
          String str = arrayFormat[8];
          if (str != null) {
            if (str.endsWith("て")) {
              strResult = arrayFormat[8].substring(0, str.length() - 1) + "でください";
            }
          }
        } else {
          System.out.println("wrong, in FormatVerb, arrayFormat[8] is null");
        }

      } else if (strErrType.equalsIgnoreCase("MasuTe")) {
        String[] arrayFormat = verbFormatAdwanced(strVerb, group);
        if (!negative) {
          strResult = arrayFormat[1] + "てください";
        }
      } else if (strErrType.equalsIgnoreCase("NaiTe")) {
        String[] arrayFormat = verbFormatAdwanced(strVerb, group);
        if (!negative) {
          strResult = arrayFormat[0] + "てください";
        }
      } else if (strErrType.equalsIgnoreCase("T1T2")) {
        if (strVerb.endsWith("る")) {
          if (group.equalsIgnoreCase("Group1") || group.equalsIgnoreCase("1")) {
            group = "Group2";
            String[] arrayFormat = verbFormatAdwanced(strVerb, group);
            if (negative) {
              strResult = arrayFormat[0] + "ないでください";
            } else {
              strResult = arrayFormat[8] + "ください";
            }
          } else if (group.equalsIgnoreCase("Group2") || group.equalsIgnoreCase("2")) {
            group = "Group1";
            String[] arrayFormat = verbFormatAdwanced(strVerb, group);
            if (negative) {
              strResult = arrayFormat[0] + "ないでください";
            } else {
              strResult = arrayFormat[8] + "ください";
            }
          }
        }

      }

    }
    return strResult;
  }

  public static String verbToSe(String strVerb, String group) {
    strVerb = strVerb.trim();
    String strResult = null;
    if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
      logger.debug("wrong , have no Order format " + strVerb);
      return null;
    } else {
      String[] arrayFormat = verbFormatAdwanced(strVerb, group);
      strResult = arrayFormat[9];
    }
    return strResult;
  }

  public static EPLeaf verbToSe(LexiconWordMeta lwm, String group) {
    // strVerb = strVerb.trim();
    String strVerb = lwm.getStrKanji().trim();
    String strVerbKana = lwm.getStrKana().trim();
    EPLeaf epl = null;
    String strResult = null;
    if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
      logger.debug("wrong , have no Order format " + strVerb);
      return null;
    } else {
      String[] arrayFormat = verbFormatAdwanced(strVerb, group);
      String[] arrayFormatKana = verbFormatAdwanced(strVerbKana, group);
      epl = new EPLeaf();
      epl.setId(lwm.getId());
      epl.setIntType(lwm.getComponentID());
      epl.setStrErrorType("VS_DFORM");
      epl.setStrKana(arrayFormatKana[9]);
      epl.setStrKanji(arrayFormat[9]);
      strResult = arrayFormat[9];
    }
    return epl;
  }

  public static String verbToSe(String strVerb, String group, String strErrType) {
    logger.debug("enter verbToSe, strErrType: " + strErrType);
    strVerb = strVerb.trim();
    String strResult = null;
    if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
      logger.debug("wrong , have no Order format " + strVerb);
      return null;
    } else {
      // deal with errorType
      if (strErrType.equalsIgnoreCase("SaseSe")) {
        String[] arrayFormat = verbFormatAdwanced(strVerb, group);
        String strTemp = arrayFormat[9];
        if (group.equalsIgnoreCase("Group1") || group.equalsIgnoreCase("1")) {
          strResult = strTemp.replace("せる", "させる");
          logger.debug("enter verbToSe");
        } else if (group.equalsIgnoreCase("Group2") || group.equalsIgnoreCase("2")) {
          strResult = strTemp.replace("させる", "せる");
        } else if (group.equalsIgnoreCase("Group3")) {
          strResult = strTemp.replace("させる", "せる");
        }
      } else if (strErrType.equalsIgnoreCase("T1T2")) {

        if (group.equalsIgnoreCase("Group1") || group.equalsIgnoreCase("1")) {
          group = "Group2";
          String[] arrayFormat = verbFormatAdwanced(strVerb, group);
          strResult = arrayFormat[9];
        } else if (group.equalsIgnoreCase("Group2") || group.equalsIgnoreCase("2")) {
          group = "Group1";
          String[] arrayFormat = verbFormatAdwanced(strVerb, group);
          strResult = arrayFormat[9];
        }
      }
    }
    logger.debug("strResult: " + strResult);
    return strResult;
  }

  /*
   * transform verb to four general format {[present,past][positive,negative]}
   */
  public static Vector verbToGeneralPolite(String strVerb, boolean tense, boolean negative, String group) {

    Vector<String> vecResult = null;;
    String[] vResult = verbToGeneral(strVerb, group);
    if (tense && negative) {
      vecResult = new Vector<String>();
      vecResult.addElement(vResult[4]);
      vecResult.addElement(vResult[5]);
      vecResult.addElement(vResult[6]);
      vecResult.addElement(vResult[7]);
    } else if (tense && (!negative)) {
      vecResult = new Vector<String>();
      vecResult.addElement(vResult[4]);
      vecResult.addElement(vResult[6]);
    } else if (negative && (!tense)) {
      vecResult = new Vector<String>();
      vecResult.addElement(vResult[4]);
      vecResult.addElement(vResult[5]);
    } else {
      vecResult = new Vector<String>();
      vecResult.addElement(vResult[4]);
    }
    return vecResult;
  }

  public static String verbToGeneralPoliteOne(String strVerb, boolean tense, boolean negative, String group) {
    Vector<String> vecResult = null;;
    String[] vResult = verbToGeneral(strVerb, group);
    if (tense && negative) {
      return vResult[5];
    } else if (tense && (!negative)) {
      return vResult[4];
    } else if (negative && (!tense)) {
      return vResult[6];
    } else {
      return vResult[7];
    }
  }

  // static final String str0 = "101010";
  // static final String str1 = "101001";
  // static final String str2 = "100110";
  // static final String str3 = "100101";
  // static final String str4 = "011010";
  // static final String str5 = "011001";
  // static final String str6 = "010110";
  // static final String str7 = "010101";
  //
  /*
   * transform verb to any needed format
   */

  public String verbFormatTransform(String strVerb, String strType, String strResultFormat) {
    String strResult = "";

    return strResult;
  }

  public String verbTransformFormat(String strVerb, String strType, String tense) {
    String strResult = "";

    return strResult;
  }

  // Takes string tokenizer, and returns remainder of string
  public static String strRemainder(StringTokenizer st) {
    String tempString, finalString;
    boolean firstpass = true;

    finalString = new String("");

    while (st.hasMoreTokens()) {
      tempString = new String(finalString);
      if (firstpass) {
        firstpass = false;
        finalString = new String(st.nextToken());
      } else {
        finalString = new String(tempString + " " + st.nextToken());
      }
    }

    return finalString;
  }

  public static String strReplace(String source, String pattern, String replace) {
    if (source != null) {
      final int len = pattern.length();
      StringBuffer sb = new StringBuffer();
      int found = -1;
      int start = 0;

      while ((found = source.indexOf(pattern, start)) != -1) {
        sb.append(source.substring(start, found));
        sb.append(replace);
        start = found + len;
      }

      sb.append(source.substring(start));

      return sb.toString();
    }

    else
      return "";
  }

  public static void main(String[] args) {
    // String str = "うごく";
    // int i = str.length();
    // for(int i = 0;i < str.length();i++){

    // System.out.println(str.substring(i-2, i-1)+"~"+str.substring(i-1));
    FormatVerb fv = new FormatVerb();
    // String str = FormatVerb.verbToInvitationPolite(strVerb);

    // String[] str = FormatVerb.verbFormatAdwanced(str, "Group1");
    String str = "いく";
    // Vector arrayGeneral = FormatVerb.verbToOrder(str, true,"Group1");
    String strTose = FormatVerb.verbToSe(str, "Group1", "SaseSe");

    System.out.println(strTose);

    // Vector arrayGeneral = FormatVerb.verbToGeneralPolite(str, true,
    // true,"Group1");
    // for(int i = 0; i<arrayGeneral.size();i++){
    // System.out.println(i + "----"+ arrayGeneral.get(i));
    // }

  }

  // ##############################
  // ##### new function for verb forms
  // #########################

  /*
   * transform verb to eight general format {plain<0,1,2,3>
   * ,polite<4,5,6,7>}{[present,past][positive,negative]}
   */
  public static String[] verbToGeneral(String strVerb, String group) {
    strVerb = strVerb.trim();
    String[] vResult = new String[8];
    if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
      System.out.println("だ ");
      return arrayDa;
    } else {
      String[] arrayFormat = verbFormatAdwanced(strVerb, group);
      vResult[0] = strVerb; // 0---- 101010
      vResult[1] = arrayFormat[0] + nai[0]; // 1-----101001
      vResult[2] = arrayFormat[7]; // 2-----100110
      vResult[3] = arrayFormat[0] + nai[1]; // 3-----100101
      vResult[4] = arrayFormat[1] + masu[0]; // 4-----011010
      vResult[5] = arrayFormat[1] + masu[2]; // 5---011001
      vResult[6] = arrayFormat[1] + masu[1]; // 6---010110
      vResult[7] = arrayFormat[1] + masu[3]; // 7---010101

    }
    return vResult;

  }

  public static Vector getSurfaceForms(JCALL_Word word, String strForm, boolean tw) {
    // TODO Auto-generated method stub
    Vector<JCALL_NetworkSubNodeStruct> vResult = new Vector<JCALL_NetworkSubNodeStruct>();
    CALL_formStruct form = null;
    form = new CALL_formStruct();
    form.setFromString(strForm);
    // CALL_formInstanceStruct forminstance = form.getFormInstance(form);

    boolean l_positive = form.isPositive();
    boolean l_negative = form.isNegative();
    boolean l_past = form.isPast();
    boolean l_present = form.isPresent();
    boolean l_plain = form.isPlain();
    boolean l_polite = form.isPolite();
    boolean l_question = form.isQuestion();
    boolean l_statement = form.isStatement();

    String error = "";
    if (tw) {
      error = "TW_DFORM";
    } else {
      error = "DW_DFORM";
    }
    String kana = word.getStrKana();
    String kanji = word.getStrKanji();
    String group = word.getStrGroup();

    String[] arrayFormKana = verbToGeneral(kana, group);
    String[] arrayFormKanji = verbToGeneral(kanji, group);

    if (l_plain && l_present && l_positive) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setIntType(word.getIntType());
      node.setStrKana(arrayFormKana[0]);
      node.setStrKanji(arrayFormKanji[0]);
      node.setStrError(error);
      vResult.addElement(node);
    }

    if (l_plain && l_present && l_negative) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setIntType(word.getIntType());
      node.setStrKana(arrayFormKana[1]);
      node.setStrKanji(arrayFormKanji[1]);
      node.setStrError(error);
      vResult.addElement(node);
    }

    if (l_plain && l_past && l_positive) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setIntType(word.getIntType());
      node.setStrKana(arrayFormKana[2]);
      node.setStrKanji(arrayFormKanji[2]);
      node.setStrError(error);
      vResult.addElement(node);
    }

    if (l_plain && l_past && l_negative) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setIntType(word.getIntType());
      node.setStrKana(arrayFormKana[3]);
      node.setStrKanji(arrayFormKanji[3]);
      node.setStrError(error);
      vResult.addElement(node);
    }

    if (l_polite && l_present && l_positive) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setIntType(word.getIntType());
      node.setStrKana(arrayFormKana[4]);
      node.setStrKanji(arrayFormKanji[4]);
      node.setStrError(error);
      vResult.addElement(node);
    }

    if (l_polite && l_present && l_negative) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setIntType(word.getIntType());
      node.setStrKana(arrayFormKana[5]);
      node.setStrKanji(arrayFormKanji[5]);
      node.setStrError(error);
      vResult.addElement(node);
    }

    if (l_polite && l_past && l_positive) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setIntType(word.getIntType());
      node.setStrKana(arrayFormKana[6]);
      node.setStrKanji(arrayFormKanji[6]);
      node.setStrError(error);
      vResult.addElement(node);
    }

    if (l_polite && l_past && l_negative) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setIntType(word.getIntType());
      node.setStrKana(arrayFormKana[7]);
      node.setStrKanji(arrayFormKanji[7]);
      node.setStrError(error);
      vResult.addElement(node);
    }
    Vector<JCALL_NetworkSubNodeStruct> v2 = new Vector();
    String ka = "か";
    if (vResult != null && vResult.size() > 0) {
      if (l_question && l_statement) {
        for (int i = 0; i < vResult.size(); i++) {
          JCALL_NetworkSubNodeStruct node = vResult.elementAt(i);
          JCALL_NetworkSubNodeStruct pnode = new JCALL_NetworkSubNodeStruct();
          pnode.setBAccept(node.isBAccept());
          pnode.setBValid(node.isBValid());
          pnode.setStrKana(node.getStrKana() + ka);
          pnode.setStrKanji(node.getStrKanji() + ka);
          pnode.setStrError(error);
          v2.addElement(pnode);
        }

      }

    }
    for (int i = 0; i < v2.size(); i++) {
      vResult.addElement((JCALL_NetworkSubNodeStruct) v2.elementAt(i));
    }
    return vResult;
  }

  public static Vector getSurfaceForms(String wordKana, String wordKanji, String strForm, String strgroup, boolean tw) {
    // TODO Auto-generated method stub
    logger.debug("enter getSurfaceForms, kana: " + wordKana + " kanji: " + wordKanji + " , tw: " + tw);
    Vector<JCALL_NetworkSubNodeStruct> vResult = new Vector<JCALL_NetworkSubNodeStruct>();
    CALL_formStruct form = null;
    form = new CALL_formStruct();
    form.setFromString(strForm);
    // CALL_formInstanceStruct forminstance = form.getFormInstance(form);

    boolean l_positive = form.isPositive();
    boolean l_negative = form.isNegative();
    boolean l_past = form.isPast();
    boolean l_present = form.isPresent();
    boolean l_plain = form.isPlain();
    boolean l_polite = form.isPolite();
    boolean l_question = form.isQuestion();
    boolean l_statement = form.isStatement();

    String error = "";
    if (tw) {
      error = "TW_DFORM";
    } else {
      error = "DW_DFORM";
    }
    String kana = wordKana;
    String kanji = wordKanji;
    String group = strgroup;

    String[] arrayFormKana = verbToGeneral(kana, group);
    String[] arrayFormKanji = verbToGeneral(kanji, group);

    if (l_plain && l_present && l_positive) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setStrKana(arrayFormKana[0]);
      node.setStrKanji(arrayFormKanji[0]);
      node.setStrError(error);
      vResult.addElement(node);
    }

    if (l_plain && l_present && l_negative) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setStrKana(arrayFormKana[1]);
      node.setStrKanji(arrayFormKanji[1]);
      node.setStrError(error);
      vResult.addElement(node);
    }

    if (l_plain && l_past && l_positive) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setStrKana(arrayFormKana[2]);
      node.setStrKanji(arrayFormKanji[2]);
      node.setStrError(error);
      vResult.addElement(node);
    }

    if (l_plain && l_past && l_negative) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setStrKana(arrayFormKana[3]);
      node.setStrKanji(arrayFormKanji[3]);
      node.setStrError(error);
      vResult.addElement(node);
    }

    if (l_polite && l_present && l_positive) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setStrKana(arrayFormKana[4]);
      node.setStrKanji(arrayFormKanji[4]);
      node.setStrError(error);
      vResult.addElement(node);
    }

    if (l_polite && l_present && l_negative) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setStrKana(arrayFormKana[5]);
      node.setStrKanji(arrayFormKanji[5]);
      node.setStrError(error);
      vResult.addElement(node);
    }

    if (l_polite && l_past && l_positive) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setStrKana(arrayFormKana[6]);
      node.setStrKanji(arrayFormKanji[6]);
      node.setStrError(error);
      vResult.addElement(node);
    }

    if (l_polite && l_past && l_negative) {
      JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
      node.setBAccept(false);
      node.setBValid(true);
      node.setStrKana(arrayFormKana[7]);
      node.setStrKanji(arrayFormKanji[7]);
      node.setStrError(error);
      vResult.addElement(node);
    }
    Vector<JCALL_NetworkSubNodeStruct> v2 = new Vector();
    String ka = "か";
    if (vResult != null && vResult.size() > 0) {
      if (l_question && l_statement) {
        for (int i = 0; i < vResult.size(); i++) {
          JCALL_NetworkSubNodeStruct node = vResult.elementAt(i);
          JCALL_NetworkSubNodeStruct pnode = new JCALL_NetworkSubNodeStruct();
          pnode.setBAccept(node.isBAccept());
          pnode.setBValid(node.isBValid());
          pnode.setStrKana(node.getStrKana() + ka);
          pnode.setStrKanji(node.getStrKanji() + ka);
          pnode.setStrError(error);
          v2.addElement(pnode);
        }

      }

    }
    for (int i = 0; i < v2.size(); i++) {
      vResult.addElement((JCALL_NetworkSubNodeStruct) v2.elementAt(i));
    }
    logger.debug("sufaceForms size: " + vResult.size());
    return vResult;
  }

  public static String[] verbFormat(JCALL_Word word, boolean kanji) {

    String[] result = new String[10];

    String strVerb = "";
    String group = word.getStrGroup();
    if (!kanji) {
      strVerb = word.getStrKana().trim();
    } else {
      strVerb = word.getStrKanji().trim();
    }

    int i = strVerb.length();
    String head1 = strVerb.substring(0, i - 1);
    String head2 = strVerb.substring(0, i - 2);
    if (strVerb.equalsIgnoreCase("だ") || strVerb.equalsIgnoreCase("です")) {
      logger.debug(" no verb Format");
    }
    if (i >= 2) {
      String tail1 = strVerb.substring(i - 1);
      if (group.equalsIgnoreCase("Group2") || group.equalsIgnoreCase("2")) {// strResult
                                                                            // =
                                                                            // "yiduan";
        result[0] = head1;
        result[1] = head1;
        result[2] = strVerb;
        result[3] = strVerb;
        result[4] = head1 + "れ";
        result[5] = head1 + "よ/ろ";
        result[6] = head1 + "よう";
        result[7] = head1 + "た";
        result[8] = head1 + "て";
        result[9] = head1 + "させる";
      } else if (group.equalsIgnoreCase("Group4") || group.equalsIgnoreCase("4")) {
        // strResult = "kabian";
        result[0] = "こ";
        result[1] = "き";
        result[2] = strVerb;
        result[3] = strVerb;
        result[4] = "くれ";
        result[5] = "こい";
        result[6] = "こよう";
        result[7] = "きた";
        result[8] = "きて";
        result[9] = "こさせる";
      } else if (group.equalsIgnoreCase("Group3") || group.equalsIgnoreCase("3")) {
        // strResult = "sabian";
        if (strVerb.equalsIgnoreCase("する")) {
          result[0] = "し";
          result[1] = "し";
          result[2] = "する";
          result[3] = "する";
          result[4] = "すれ";
          result[5] = "しろ/せよ";
          result[6] = "しよう";
          result[7] = "した";
          result[8] = "して";
          result[9] = "させる";
        } else {
          result[0] = head2 + "し";
          result[1] = head2 + "し";
          result[2] = head2 + "する";
          result[3] = head2 + "する";
          result[4] = head2 + "すれ";
          result[5] = head2 + "しろ/せよ";
          result[6] = head2 + "しよう";
          result[7] = head2 + "した";
          result[8] = head2 + "して";
          result[9] = head2 + "させる";
        }

      } else if (group.equalsIgnoreCase("Group1") || group.equalsIgnoreCase("1")) {// "wuduan"
        // String[] row = CharacterUtil.charRow(tail1);
        if (strVerb.equalsIgnoreCase("ある")) {
          result[0] = "";
          result[1] = "あり";
          result[2] = "ある";
          result[3] = "ある";
          result[4] = "あれ";
          result[5] = "あろ";
          result[6] = "あろう";
          result[7] = "あった";
          result[8] = "あって";
          result[9] = "あらせる";
        } else {
          String[] row = CharacterUtil.charRow(tail1);
          if (row.length == 5) {
            result[0] = head1 + row[0];
            result[1] = head1 + row[1];
            result[2] = head1 + row[2];
            result[3] = head1 + row[2];
            result[4] = head1 + row[3];
            result[5] = head1 + row[3];
            if (tail1.equals("う")) {
              result[6] = head1 + "おう";
            } else {
              result[6] = head1 + row[4] + "う";
            }
            // "く","ぐ","す","つ","ぬ","ぶ","む","る","う"
            // っ　
            if (strVerb.equalsIgnoreCase("いく") || strVerb.equalsIgnoreCase("行く")) {
              result[7] = head1 + "った";
              result[8] = head1 + "って";
            } else {
              if (tail1.equals("す")) {
                result[7] = head1 + "した";
                result[8] = head1 + "して";
              } else if (tail1.equals("く")) {
                result[7] = head1 + "いた";
                result[8] = head1 + "いて";
              } else if (tail1.equals("ぐ")) {
                result[7] = head1 + "いだ";
                result[8] = head1 + "いで";
              } else if (tail1.equals("つ") || tail1.equals("る") || tail1.equals("う")) {
                result[7] = head1 + "った";
                result[8] = head1 + "って";
              } else if (tail1.equals("ぬ") || tail1.equals("ぶ") || tail1.equals("む")) {
                result[7] = head1 + "んだ";
                result[8] = head1 + "んで";
              }
            }
            result[9] = head1 + row[0] + "せる";
          }
        }
      }
    }
    return result;
  }

}
