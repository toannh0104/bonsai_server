/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.io.Serializable;
import java.util.List;

import jp.co.gmo.rs.ghs.jcall.entity.WordGHS;

/**
 * Create SentenceDto dto
 *
 * @author BaoTQ
 *
 */
public class SentenceDto implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    private List<WordGHS> sentence;
    private String nameConcept;
    private String nameQuestion;
    private Integer lessonId;
    private String fullSentence;
    private List<String> listSlotName;
    private String rubyWords;
    private String categoryWord;
    private String calljWord;

    /**
     * @return the rubyWords
     */
    public String getRubyWords() {
        return rubyWords;
    }
    /**
     * @param rubyWords the rubyWords to set
     */
    public void setRubyWords(String rubyWords) {
        this.rubyWords = rubyWords;
    }
    /**
     * @return the fullSentence
     */
    public String getFullSentence() {
        return fullSentence;
    }
    /**
     * @param fullSentence the fullSentence to set
     */
    public void setFullSentence(String fullSentence) {
        this.fullSentence = fullSentence;
    }
    /**
     * @return the sentence
     */
    public List<WordGHS> getSentence() {
        return sentence;
    }

    /**
     * @param sentence
     *            the sentence to set
     */
    public void setSentence(List<WordGHS> sentence) {
        this.sentence = sentence;
    }

    /**
     * @return the nameConcept
     */
    public String getNameConcept() {
        return nameConcept;
    }

    /**
     * @param nameConcept
     *            the nameConcept to set
     */
    public void setNameConcept(String nameConcept) {
        this.nameConcept = nameConcept;
    }

    /**
     * @return the nameQuestion
     */
    public String getNameQuestion() {
        return nameQuestion;
    }

    /**
     * @param nameQuestion
     *            the nameQuestion to set
     */
    public void setNameQuestion(String nameQuestion) {
        this.nameQuestion = nameQuestion;
    }

    /**
     * @return the lessonId
     */
    public Integer getLessonId() {
        return lessonId;
    }

    /**
     * @param lessonId
     *            the lessonId to set
     */
    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    /**
     * @return the listSlotName
     */
    public List<String> getListSlotName() {
        return listSlotName;
    }

    /**
     * @param listSlotName
     *            the listSlotName to set
     */
    public void setListSlotName(List<String> listSlotName) {
        this.listSlotName = listSlotName;
    }
    /**
     * @return the categoryWord
     */
    public String getCategoryWord() {
        return categoryWord;
    }
    /**
     * @param categoryWord the categoryWord to set
     */
    public void setCategoryWord(String categoryWord) {
        this.categoryWord = categoryWord;
    }
    /**
     * @return the calljWord
     */
    public String getCalljWord() {
        return calljWord;
    }
    /**
     * @param calljWord the calljWord to set
     */
    public void setCalljWord(String calljWord) {
        this.calljWord = calljWord;
    }

}
