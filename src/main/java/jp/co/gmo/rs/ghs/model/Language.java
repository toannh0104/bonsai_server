/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.model;

import java.util.HashMap;

/**
 * create list language
 *
 * @author ThuyTTT
 *
 */
public final class Language {
    public static final String AFRIKAANS = "af";
    public static final String ALBANIAN = "sq";
    public static final String ARABIC = "ar";
    public static final String ARMENIAN = "hy";
    public static final String AZERBAIJANI = "az";
    public static final String BASQUE = "eu";
    public static final String BELARUSIAN = "be";
    public static final String BENGALI = "bn";
    public static final String BULGARIAN = "bg";
    public static final String CATALAN = "ca";
    public static final String CROATIAN = "hr";
    public static final String CZECH = "cs";
    public static final String DANISH = "da";
    public static final String DUTCH = "nl";
    public static final String ENGLISH = "en";
    public static final String ESTONIAN = "et";
    public static final String FILIPINO = "tl";
    public static final String FINNISH = "fi";
    public static final String FRENCH = "fr";
    public static final String GALICIAN = "gl";
    public static final String GEORGIAN = "ka";
    public static final String GERMAN = "de";
    public static final String GREEK = "el";
    public static final String GUJARATI = "gu";
    public static final String HAITIAN_CREOLE = "ht";
    public static final String HEBREW = "iw";
    public static final String HINDI = "hi";
    public static final String HUNGARIAN = "hu";
    public static final String ICELANDIC = "is";
    public static final String INDONESIAN = "id";
    public static final String IRISH = "ga";
    public static final String ITALIAN = "it";
    public static final String JAPANESE = "ja";
    public static final String KANNADA = "kn";
    public static final String KOREAN = "ko";
    public static final String LATIN = "la";
    public static final String LATVIAN = "lv";
    public static final String LITHUANIAN = "ln";
    public static final String MACEDONIAN = "mk";
    public static final String MALAY = "ms";
    public static final String MALTESE = "mt";
    public static final String NORWEGIAN = "no";
    public static final String PERSIAN = "fa";
    public static final String POLISH = "pl";
    public static final String PORTUGUESE = "pt";
    public static final String ROMANIAN = "ro";
    public static final String RUSSIAN = "ru";
    public static final String SERBIAN = "sr";
    public static final String SLOVAK = "sk";
    public static final String SLOVENIAN = "sl";
    public static final String SPANISH = "es";
    public static final String SWAHILI = "sw";
    public static final String SWEDISH = "sv";
    public static final String TAMIL = "ta";
    public static final String TELUGU = "te";
    public static final String THAI = "th";
    public static final String TURKISH = "tr";
    public static final String UKRAINIAN = "uk";
    public static final String URDU = "ur";
    public static final String VIETNAMESE = "vi";
    public static final String WELSH = "cy";
    public static final String YIDDISH = "yi";
    public static final String CHINESE_SIMPLIFIED = "zh_CN";
    public static final String CHINESE_TRADITIONAL = "zh_TW";
    private static Language language;
    private HashMap<String, String> hashLanguage;

    private Language() {
        hashLanguage = new HashMap<String, String>();
        init();
    }

    /**
     * getInstance
     * 
     * @return language
     */
    public static synchronized Language getInstance() {
        if (language == null) {
            language = new Language();
        }
        return language;
    }

    /**
     * init
     */
    private void init() {
        //hashLanguage.put("AF", "AFRIKAANS");
        //hashLanguage.put("SQ", "ALBANIAN");
        hashLanguage.put("AR", "ARABIC");
        //hashLanguage.put("HY", "ARMENIAN");
        //hashLanguage.put("AZ", "AZERBAIJANI");
        //hashLanguage.put("EU", "BASQUE");
        //hashLanguage.put("BE", "BELARUSIAN");
        //hashLanguage.put("BN", "BENGALI");
        hashLanguage.put("BG", "BULGARIAN");
        hashLanguage.put("CA", "CATALAN");
        //hashLanguage.put("HR", "CROATIAN");
        hashLanguage.put("CS", "CZECH");
        hashLanguage.put("DA", "DANISH");
        hashLanguage.put("NL", "DUTCH");
        hashLanguage.put("EN", "ENGLISH");
        hashLanguage.put("ET", "ESTONIAN");
        //hashLanguage.put("TL", "FILIPINO");
        hashLanguage.put("FI", "FINNISH");
        hashLanguage.put("FR", "FRENCH");
        //hashLanguage.put("GL", "GALICIAN");
        //hashLanguage.put("KA", "GEORGIAN");
        hashLanguage.put("DE", "GERMAN");
        hashLanguage.put("EL", "GREEK");
        //hashLanguage.put("GU", "GUJARATI");
        hashLanguage.put("HT", "HAITIAN_CREOLE");
        //hashLanguage.put("IW", "HEBREW");
        hashLanguage.put("HI", "HINDI");
        hashLanguage.put("HU", "HUNGARIAN");
        //hashLanguage.put("IS", "ICELANDIC");
        hashLanguage.put("ID", "INDONESIAN");
        //hashLanguage.put("GA", "IRISH");
        hashLanguage.put("IT", "ITALIAN");
        //hashLanguage.put("KN", "KANNADA");
        hashLanguage.put("KO", "KOREAN");
        //hashLanguage.put("LA", "LATIN");
        hashLanguage.put("LV", "LATVIAN");
        hashLanguage.put("LN", "LITHUANIAN");
        //hashLanguage.put("MK", "MACEDONIAN");
        hashLanguage.put("MS", "MALAY");
        //hashLanguage.put("MT", "MALTESE");
        hashLanguage.put("NO", "NORWEGIAN");
        hashLanguage.put("FA", "PERSIAN");
        hashLanguage.put("PL", "POLISH");
        hashLanguage.put("PT", "PORTUGUESE");
        hashLanguage.put("RO", "ROMANIAN");
        hashLanguage.put("RU", "RUSSIAN");
        //hashLanguage.put("SR", "SERBIAN");
        hashLanguage.put("SK", "SLOVAK");
        hashLanguage.put("SL", "SLOVENIAN");
        hashLanguage.put("ES", "SPANISH");
        //hashLanguage.put("SW", "SWAHILI");
        hashLanguage.put("SV", "SWEDISH");
        //hashLanguage.put("TA", "TAMIL");
        //hashLanguage.put("TE", "TELUGU");
        hashLanguage.put("TH", "THAI");
        hashLanguage.put("TR", "TURKISH");
        hashLanguage.put("UK", "UKRAINIAN");
        hashLanguage.put("UR", "URDU");
        hashLanguage.put("VI", "VIETNAMESE");
        //hashLanguage.put("CY", "WELSH");
        //hashLanguage.put("YI", "YIDDISH");
        //hashLanguage.put("zh_CN", "CHINESE_SIMPLIFIED");
        //hashLanguage.put("zh_TW", "CHINESE_TRADITIONAL");
        
    }

    /**
     * @return the hashLanguage
     */
    public HashMap<String, String> getHashLanguage() {
        return hashLanguage;
    }

    /**
     * @param hashLanguage the hashLanguage to set
     */
    public void setHashLanguage(HashMap<String, String> hashLanguage) {
        this.hashLanguage = hashLanguage;
    }

}
