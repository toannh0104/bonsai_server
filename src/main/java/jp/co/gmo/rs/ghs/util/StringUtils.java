/*
 * Copyright (C) 2015 by GMO Runsystem Company Global HR Service application
 */
package jp.co.gmo.rs.ghs.util;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * StringUtils
 * 
 * @author LongVNH
 * @version 1.0
 */
public class StringUtils {

    // kana lookup for convert kana to
    private static final int KANALOOKUPSIZE = 76;

    // Hiragana charaters
    private static final String[] HIRAGANATABLE = { "あ", "い", "う", "え", "お",
            "か", "き", "く", "け", "こ", "が", "ぎ", "ぐ", "げ", "ご", "は", "ひ", "ふ",
            "へ", "ほ", "ば", "び", "ぶ", "べ", "ぼ", "ぱ", "ぴ", "ぷ", "ぺ", "ぽ", "さ",
            "し", "す", "せ", "そ", "ざ", "じ", "ず", "ぜ", "ぞ", "た", "ち", "つ", "て",
            "と", "だ", "ぢ", "づ", "で", "ど", "ら", "り", "る", "れ", "ろ", "な", "に",
            "ぬ", "ね", "の", "ま", "み", "む", "め", "も", "や", "ゆ", "よ", "わ", "を",
            "ん", "ー", "？", "。", "、", "！"
    };

    // Katakana charaters
    private static final String[] KATAKANATABLE = { "ア", "イ", "ウ", "エ", "オ",
            "カ", "キ", "ク", "ケ", "コ", "ガ", "ギ", "グ", "ゲ", "ゴ", "ハ", "ヒ", "フ",
            "ヘ", "ホ", "バ", "ビ", "ブ", "ベ", "ボ", "パ", "ピ", "プ", "ペ", "ポ", "サ",
            "シ", "ス", "セ", "ソ", "ザ", "ジ", "ズ", "ゼ", "ゾ", "タ", "チ", "ツ", "テ",
            "ト", "ダ", "ヂ", "ヅ", "デ", "ド", "ラ", "リ", "ル", "レ", "ロ", "ナ", "ニ",
            "ヌ", "ネ", "ノ", "マ", "ミ", "ム", "メ", "モ", "ヤ", "ユ", "ヨ", "ワ", "ヲ",
            "ン", "ー", "？", "。", "、", "！"
    };

    // Romaji charaters
    private static final String[] ROMAJITABLE = { "a", "i", "u", "e", "o", "ka",
            "ki", "ku", "ke", "ko", "ga", "gi", "gu", "ge", "go", "ha", "hi",
            "fu", "he", "ho", "ba", "bi", "bu", "be", "bo", "pa", "pi", "pu",
            "pe", "po", "sa", "shi", "su", "se", "so", "za", "ji", "zu", "ze",
            "zo", "ta", "chi", "tsu", "te", "to", "da", "di", "zu", "de", "do",
            "ra", "ri", "ru", "re", "ro", "na", "ni", "nu", "ne", "no", "ma",
            "mi", "mu", "me", "mo", "ya", "yu", "yo", "wa", "wo", "n", "-",
            "?", ".", ",", "!"
    };

    /**
     * Constructor
     */
    protected StringUtils() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

    /**
     * Convert object to json string
     * 
     * @param obj
     *            is object
     * @return string
     */
    public static String convertObjToJson(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param str
     * @param separator
     * @return
     */
    public static String substringBefore(String str, String separator) {
        if ((isEmpty(str)) || (separator == null)) {
            return str;
        }
        if (separator.length() == 0) {
            return "";
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0) || (str == ""));
    }

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
                }

                // Is it a small tsu?
                else if ((character.compareTo("っ") == 0)
                        || (character.compareTo("ッ") == 0)) {
                    smalltsu = true;
                }

                // Is character a small one?
                else if ((character.compareTo("ゃ") == 0)
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
                    for (int j = 0; j < KANALOOKUPSIZE; j++) {
                        if ((character.compareTo(HIRAGANATABLE[j]) == 0)
                                || (character.compareTo(KATAKANATABLE[j]) == 0)) {
                            if (smalltsu) {
                                // We want to have two of the first character
                                smalltsu = false;
                                outputString = outputString
                                        + (ROMAJITABLE[j]).charAt(0);
                            }

                            outputString = outputString + ROMAJITABLE[j];
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
     * sort By Values
     * 
     * @param map map
     * @param ascending order
     * @return map
     */
    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map, int ascending) {
        Comparator<K> valueComparator = new Comparator<K>() {
            private int ascending;

            public int compare(K k1, K k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0) {
                    return 1;
                } else {
                    return ascending * compare;
                }
            }

            public Comparator<K> setParam(int ascending) {
                this.ascending = ascending;
                return this;
            }
        }.setParam(ascending);

        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

}
