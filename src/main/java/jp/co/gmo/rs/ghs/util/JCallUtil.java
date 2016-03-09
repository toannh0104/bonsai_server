/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.util;


/**
 * JCall_Util
 *
 * @author LongVNH
 * @version 1.0
 */
public class JCallUtil {

    /**
     * Constructor
     */
    protected JCallUtil() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

    public static final int KANA_LOOKUP_SIZE = 76;
    public static final int[] LEX_TYPE_INTTRANSFORM = { 0, 1, 11, 12, 13, 14,
        15, 16, 17, 18, 19, 2, 3, 4, 5, 6, 7, 8, 9, 111, 112 };

    // String definition of types
    static String[] typeString = { "UNK", "NOUN",
        "PERSONNAME",
        "AREANAME",
        "DEFINITIVES", // PRONOUN_Thing
        "SURUNOUN", "NUMERAL", "SUFFIX", "PREFIX", "TIME",
        "QUANTIFIER",
        "VERB",
        // "VERB_VT",
        // "VERB_VI",
        "ADJECTIVE", "ADJVERB", "ADVERB", "PARTICLE_AUXIL",
        "PARTICLE_AUXILV", "RENTAI", "KANDOU", "NUMQUANT", "PRONOUN_PERSON" };

    static final String[] TYPE_ICON_STRINGS = {
        "Misc/UNKicon.gif",
        "Misc/NOUNicon.gif",
        "Misc/NOUNicon.gif",
        "Misc/NOUNicon.gif",
        "Misc/DEFINITIVEicon.gif",
        "Misc/NOUNicon.gif",
        "Misc/DIGITicon.gif", // numeral;
        "Misc/SUFFIXicon.gif",
        "Misc/PREFIXicon.gif",
        "Misc/TIMEicon.gif",
        "Misc/DIGITicon.gif", // quantifier;
        "Misc/VERBicon.gif", "Misc/ADJECTIVEicon.gif",
        "Misc/ADJECTIVEicon.gif", "Misc/ADVERBicon.gif",
        "Misc/PARTICLEicon.gif", "Misc/PARTICLEicon.gif",
        "Misc/MISCicon.gif", // rentai
        "Misc/MISCicon.gif", // kandou
        "Misc/DIGITicon.gif", // nq;
        "Misc/DEFINITIVEicon.gif" };

    static final String[] PREFIX = { "さん", "氏", "君", "くん", "ちゃん", "様", "さま",
        "殿", "どの", "お宅", "おたく" };

    public static final String[] HIRAGANA_TABLE = { "あ", "い", "う", "え", "お",
        "か", "き", "く", "け", "こ", "が", "ぎ", "ぐ", "げ", "ご", "は", "ひ", "ふ",
        "へ", "ほ", "ば", "び", "ぶ", "べ", "ぼ", "ぱ", "ぴ", "ぷ", "ぺ", "ぽ", "さ",
        "し", "す", "せ", "そ", "ざ", "じ", "ず", "ぜ", "ぞ", "た", "ち", "つ", "て",
        "と", "だ", "ぢ", "づ", "で", "ど", "ら", "り", "る", "れ", "ろ", "な", "に",
        "ぬ", "ね", "の", "ま", "み", "む", "め", "も", "や", "ゆ", "よ", "わ", "を",
        "ん", "ー", "？", "。", "、", "！" };

    public static final String[] KATAKANA_TABLE = { "ア", "イ", "ウ", "エ", "オ",
        "カ", "キ", "ク", "ケ", "コ", "ガ", "ギ", "グ", "ゲ", "ゴ", "ハ", "ヒ", "フ",
        "ヘ", "ホ", "バ", "ビ", "ブ", "ベ", "ボ", "パ", "ピ", "プ", "ペ", "ポ", "サ",
        "シ", "ス", "セ", "ソ", "ザ", "ジ", "ズ", "ゼ", "ゾ", "タ", "チ", "ツ", "テ",
        "ト", "ダ", "ヂ", "ヅ", "デ", "ド", "ラ", "リ", "ル", "レ", "ロ", "ナ", "ニ",
        "ヌ", "ネ", "ノ", "マ", "ミ", "ム", "メ", "モ", "ヤ", "ユ", "ヨ", "ワ", "ヲ",
        "ン", "ー", "？", "。", "、", "！"

    };
    public static final String[] ROMAJI_TABLE = { "a", "i", "u", "e", "o", "ka",
        "ki", "ku", "ke", "ko", "ga", "gi", "gu", "ge", "go", "ha", "hi",
        "fu", "he", "ho", "ba", "bi", "bu", "be", "bo", "pa", "pi", "pu",
        "pe", "po", "sa", "shi", "su", "se", "so", "za", "ji", "zu", "ze",
        "zo", "ta", "chi", "tsu", "te", "to", "da", "di", "zu", "de", "do",
        "ra", "ri", "ru", "re", "ro", "na", "ni", "nu", "ne", "no", "ma",
        "mi", "mu", "me", "mo", "ya", "yu", "yo", "wa", "wo", "n", "-",
        "?", ".", ",", "!" };

    /**
     * convert kana to romaji
     * 
     * @param str is kana
     * @return romaji
     */
    public static String strKanaToRomaji(String str) {
        String outputString = null;
        String character;
        boolean smalltsu;
        boolean written;

        outputString = new String("");
        smalltsu = false;

        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                character = new String("" + str.charAt(i));

                // Is it a space
                if (character.compareTo(" ") == 0) {
                    outputString = outputString + " ";

                 // Is it a small tsu?
                } else if ((character.compareTo("っ") == 0)
                        || (character.compareTo("ッ") == 0)) {
                    smalltsu = true;

                 // Is character a small one?
                } else if ((character.compareTo("ゃ") == 0)
                        || (character.compareTo("ャ") == 0)) {
                    if (outputString.length() > 0) {
                        outputString = outputString.substring(0,
                                outputString.length() - 1);

                        if ((outputString.endsWith("sh"))
                                || (outputString.endsWith("ch"))) {
                            // Special case: Change shi to sha etc
                            outputString = outputString + "a";
                        } else {
                            // Standard case
                            outputString = outputString + "ya";
                        }
                    }
                } else if ((character.compareTo("ゅ") == 0)
                        || (character.compareTo("ュ") == 0)) {
                    if (outputString.length() > 0) {
                        outputString = outputString.substring(0,
                                outputString.length() - 1);

                        if ((outputString.endsWith("sh"))
                                || (outputString.endsWith("ch"))) {
                            // Special case: Change shi to shu etc
                            outputString = outputString + "u";
                        } else {
                            // Standard case
                            outputString = outputString + "yu";
                        }
                    }
                } else if ((character.compareTo("ょ") == 0)
                        || (character.compareTo("ョ") == 0)) {
                    if (outputString.length() > 0) {
                        outputString = outputString.substring(0,
                                outputString.length() - 1);

                        if ((outputString.endsWith("sh"))
                                || (outputString.endsWith("ch"))) {
                            // Special case: Change shi to shu etc
                            outputString = outputString + "o";
                        } else {
                            // Standard case
                            outputString = outputString + "yo";
                        }
                    }
                } else if ((character.compareTo("ぁ") == 0)
                        || (character.compareTo("ァ") == 0)) {
                    if (outputString.length() > 0) {
                        outputString = outputString.substring(0,
                                outputString.length() - 1);
                    }
                    outputString = outputString + "a";
                } else if ((character.compareTo("ぃ") == 0)
                        || (character.compareTo("ィ") == 0)) {
                    if (outputString.length() > 0) {
                        outputString = outputString.substring(0,
                                outputString.length() - 1);
                    }
                    outputString = outputString + "i";
                } else if ((character.compareTo("ぅ") == 0)
                        || (character.compareTo("ゥ") == 0)) {
                    if (outputString.length() > 0) {
                        outputString = outputString.substring(0,
                                outputString.length() - 1);
                    }
                    outputString = outputString + "u";
                } else if ((character.compareTo("ぇ") == 0)
                        || (character.compareTo("ェ") == 0)) {
                    if (outputString.length() > 0) {
                        outputString = outputString.substring(0,
                                outputString.length() - 1);
                    }
                    outputString = outputString + "e";
                } else if ((character.compareTo("ぉ") == 0)
                        || (character.compareTo("ォ") == 0)) {
                    if (outputString.length() > 0) {
                        outputString = outputString.substring(0,
                                outputString.length() - 1);
                    }
                    outputString = outputString + "o";
                } else {
                    written = false;

                    // Treat as normal kana
                    for (int j = 0; j < KANA_LOOKUP_SIZE; j++) {
                        if ((character.compareTo(HIRAGANA_TABLE[j]) == 0)
                                || (character.compareTo(KATAKANA_TABLE[j]) == 0)) {
                            if (smalltsu) {
                                // We want to have two of the first character
                                smalltsu = false;
                                outputString = outputString
                                        + (ROMAJI_TABLE[j]).charAt(0);
                            }

                            outputString = outputString + ROMAJI_TABLE[j];
                            written = true;
                        }
                    }

                    if (!written) {
                        // Just write the character as is
                        outputString = outputString + character;
                    }
                }
            }
        }
        return outputString;
    }

    /**
     * get type
     * 
     * @param intType is lexicon type
     * @return type
     */
    public static String getTypeStr(int intType) {
        for (int i = 0; i < LEX_TYPE_INTTRANSFORM.length; i++) {
            int aType = LEX_TYPE_INTTRANSFORM[i];
            if (intType == aType) {
                return typeString[i];
            }
        }
        return typeString[0];
    }

    /**
     * get type icon
     * 
     * @param intType is lexicon type
     * @return TypeIcon
     */
    public static String getTypeIconStr(int intType) {
        for (int i = 0; i < LEX_TYPE_INTTRANSFORM.length; i++) {
            int aType = LEX_TYPE_INTTRANSFORM[i];
            if (intType == aType) {
                return TYPE_ICON_STRINGS[i];
            }
        }
        return TYPE_ICON_STRINGS[0];
    }

}
