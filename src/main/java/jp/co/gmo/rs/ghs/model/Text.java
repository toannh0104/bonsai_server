/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.model;

/**
 * create text class
 * 
 * @author ThuyTTT
 *
 */
public class Text {
    private String text;
    private String language;

    /**
     * Text
     * 
     * @param text is value translate
     * @param language is code translate input
     */
    public Text(String text, String language) {
        this.text = text;
        this.language = language;
    }

    /**
     * Text
     * 
     * @param language is code translate input
     */
    public Text(String language) {
        this.language = language;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

}
