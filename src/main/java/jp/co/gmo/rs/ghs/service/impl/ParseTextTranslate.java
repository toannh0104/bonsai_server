/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service.impl;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.model.Text;
import jp.co.gmo.rs.ghs.model.TextTranslate;
import jp.co.gmo.rs.ghs.service.Parse;
import jp.co.gmo.rs.ghs.util.WebUtils;

/**
 * create ParseTextTranslate class
 *
 * @author ThuyTTT
 *
 */
public class ParseTextTranslate implements Parse {

    private TextTranslate textTranslate;

    private StringBuilder url;

    /**
     * ParseTextTranslate
     *
     * @param textTranslate is TextTranslate
     */
    public ParseTextTranslate(TextTranslate textTranslate) {
        this.textTranslate = textTranslate;
    }

    /**
     * parse
     *
     * @param initValue is original value
     */
    @Override
    public void parse(String initValue) {
        appendURL();
        String result = WebUtils.source(url.toString());
        String[] split = result.replace("[", "").replace("]", "").replace("\"", "").split(",");
        Text output = textTranslate.getOutput();

        String[] inputs = initValue.replace(".", ".@").split("@");
        String value = "";
        int a = 0;
        for (String input : inputs) {
            for (int i = a; i < split.length; i++) {
                if (split[i].equals(input.trim())) {
                    a = i + ConstValues.NEXT_ELEMENT;
                    break;
                }
                if (!value.contains(split[i])) {
                    value += split[i];
                }
            }
        }
        if (value.equals("")) {
            value = initValue;
        }
        output.setText(value);
    }

    /**
     * @param textTranslate the textTranslate to set
     */
    public void setTextTranslate(TextTranslate textTranslate) {
        this.textTranslate = textTranslate;
    }

    @Override
    public void appendURL() {
        Text input = textTranslate.getInput();
        Text output = textTranslate.getOutput();
        url = new StringBuilder(ConstValues.GOOGLE_TRANSLATE_TEXT);

        url.append("client=t&sl=auto&tl=" + output.getLanguage()
                + "&hl=" + input.getLanguage()
                + "&dt=bd&dt=ex&dt=ld&dt=md&dt=qc&dt=rw&dt=rm&dt=ss&dt=t&dt=at&ie=UTF-8&oe=UTF-8"
                + "&otf=1&rom=1&ssel=0&tsel=3&kc=1&tk=620730|996163"
                + "&q=" + input.getText().replace(" ", "%20"));

    }
}
