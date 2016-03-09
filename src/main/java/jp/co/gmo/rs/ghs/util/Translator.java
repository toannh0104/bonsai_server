/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.gmo.rs.ghs.model.Text;
import jp.co.gmo.rs.ghs.model.TextTranslate;
import jp.co.gmo.rs.ghs.service.Parse;
import jp.co.gmo.rs.ghs.service.impl.ParseTextTranslate;

/**
 * create translator class
 *
 * @author ThuyTTT
 *
 */
public class Translator {

    private static Translator translator;

    /**
     * getInstance
     *
     * @return translator
     */
    public static synchronized Translator getInstance() {

        if (translator == null) {
            translator = new Translator();
        }
        return translator;

    }

    /**
     * translate
     *
     * @param text is value translate
     * @param languageInput is code language input
     * @param languageOutput is code language output
     * @return result
     */
    public Map<String, String> translate(String text, String languageInput,
            List<String> languageOutput) {

        Text input = new Text(text, languageInput);
        Map<String, String> result = new HashMap<String, String>();
        try {
            for (String output : languageOutput) {
                TextTranslate textTranslate = new TextTranslate(input, output);
                Parse parse = new ParseTextTranslate(textTranslate);
                parse.parse(text);
                String value = textTranslate.getOutput().getText();
                result.put(output, value);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return result;
    }

    /**
     * translate
     *
     * @param text is value translate
     * @param languageInput is code language input
     * @param languageOutput is code language output
     * @return result
     */
    public String translate(String text, String languageInput,
            String languageOutput) {
        Text input = new Text(text, languageInput);
        TextTranslate textTranslate = new TextTranslate(input, languageOutput);
        Parse parse = new ParseTextTranslate(textTranslate);
        parse.parse(text);
        return textTranslate.getOutput().getText();
    }
}
