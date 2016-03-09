/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.io.Serializable;

/**
 * @author Dong
 *
 */
public class SentenceWordDto implements Serializable {
    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    /* Init lessonNo */
    private String lessonNo;

    /* Init index */
    private String index;

    /* Init grammarRule */
    private String grammarRule;

    /* Init componentName */
    private String componentName;

    /* Init useCounterSettings */
    private Boolean useCounterSettings;

    /* Init topWordStringKanji */
    private String topWordStringKanji;

    /* Init topWordStringKanna */
    private String topWordStringKanna;

    /* Init fullFormString */
    private String fullFormString;

    /* Init transformationRule */
    private String transformationRule;

    /* Init sign */
    private String sign;

    /* Init tense */
    private String tense;

    /* Init politenes */
    private String politenes;

    /* Init question */
    private String question;

    /* Init wordStruct */
    private WordStructDto wordStruct;

    /* Init sentence */
    private String sentence;

    /**
     * @return the index
     */
    public String getIndex() {
        return index;
    }

    /**
     * @param index
     *            the index to set
     */
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     * @return the grammarRule
     */
    public String getGrammarRule() {
        return grammarRule;
    }

    /**
     * @param grammarRule
     *            the grammarRule to set
     */
    public void setGrammarRule(String grammarRule) {
        this.grammarRule = grammarRule;
    }

    /**
     * @return the componentName
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * @param componentName
     *            the componentName to set
     */
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    /**
     * @return the useCounterSettings
     */
    public Boolean getUseCounterSettings() {
        return useCounterSettings;
    }

    /**
     * @param useCounterSettings
     *            the useCounterSettings to set
     */
    public void setUseCounterSettings(Boolean useCounterSettings) {
        this.useCounterSettings = useCounterSettings;
    }

    /**
     * @return the topWordStringKanji
     */
    public String getTopWordStringKanji() {
        return topWordStringKanji;
    }

    /**
     * @param topWordStringKanji
     *            the topWordStringKanji to set
     */
    public void setTopWordStringKanji(String topWordStringKanji) {
        this.topWordStringKanji = topWordStringKanji;
    }

    /**
     * @return the topWordStringKanna
     */
    public String getTopWordStringKanna() {
        return topWordStringKanna;
    }

    /**
     * @param topWordStringKanna
     *            the topWordStringKanna to set
     */
    public void setTopWordStringKanna(String topWordStringKanna) {
        this.topWordStringKanna = topWordStringKanna;
    }

    /**
     * @return the fullFormString
     */
    public String getFullFormString() {
        return fullFormString;
    }

    /**
     * @param fullFormString
     *            the fullFormString to set
     */
    public void setFullFormString(String fullFormString) {
        this.fullFormString = fullFormString;
    }

    /**
     * @return the transformationRule
     */
    public String getTransformationRule() {
        return transformationRule;
    }

    /**
     * @param transformationRule
     *            the transformationRule to set
     */
    public void setTransformationRule(String transformationRule) {
        this.transformationRule = transformationRule;
    }

    /**
     * @return the sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * @param sign
     *            the sign to set
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * @return the tense
     */
    public String getTense() {
        return tense;
    }

    /**
     * @param tense
     *            the tense to set
     */
    public void setTense(String tense) {
        this.tense = tense;
    }

    /**
     * @return the politenes
     */
    public String getPolitenes() {
        return politenes;
    }

    /**
     * @param politenes
     *            the politenes to set
     */
    public void setPolitenes(String politenes) {
        this.politenes = politenes;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question
     *            the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return the wordStruct
     */
    public WordStructDto getWordStruct() {
        return wordStruct;
    }

    /**
     * @param wordStruct
     *            the wordStruct to set
     */
    public void setWordStruct(WordStructDto wordStruct) {
        this.wordStruct = wordStruct;
    }

    /**
     * @return the sentence
     */
    public String getSentence() {
        return sentence;
    }

    /**
     * @return the lessonNo
     */
    public String getLessonNo() {
        return lessonNo;
    }

    /**
     * @param lessonNo
     *            the lessonNo to set
     */
    public void setLessonNo(String lessonNo) {
        this.lessonNo = lessonNo;
    }

    /**
     * @param sentence
     *            the sentence to set
     */
    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

}
