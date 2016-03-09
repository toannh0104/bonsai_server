/*
 * Copyright (C) 2015 by GMO Runsystem Company Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto.form;

import java.io.Serializable;

/**
 * PracticeTestDto
 *
 * @author LongVNH
 *
 */
public class PracticeTestRequestForm implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    /* Init practiceTest */
    private String languageId;

    private Integer lessonNo;

    private Integer lessonType;

    private String practiceTest;

    private Integer lessonInfoId;

    private String memoryWritingConversation;

    private Integer modeLanguage;

    private Integer pageIndexPracticeMemory;

    private Integer pageIndexPracticeWriting;

    private Integer pageIndexPracticeConversation;

    private Integer pageIndexPracticeMemoryVocabulary;

    private Integer pageIndexPracticeWritingVocabulary;

    private Integer pageIndexPracticeConversationVocabulary;

    /**
     * PracticeTestDto constructor
     */
    public PracticeTestRequestForm() {

    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public Integer getLessonNo() {
        return lessonNo;
    }

    public void setLessonNo(Integer lessonNo) {
        this.lessonNo = lessonNo;
    }

    public Integer getLessonType() {
        return lessonType;
    }

    public void setLessonType(Integer lessonType) {
        this.lessonType = lessonType;
    }

    public String getPracticeTest() {
        return practiceTest;
    }

    public void setPracticeTest(String practiceTest) {
        this.practiceTest = practiceTest;
    }

    public Integer getLessonInfoId() {
        return lessonInfoId;
    }

    public void setLessonInfoId(Integer lessonInfoId) {
        this.lessonInfoId = lessonInfoId;
    }

    public String getMemoryWritingConversation() {
        return memoryWritingConversation;
    }

    public void setMemoryWritingConversation(String memoryWritingConversation) {
        this.memoryWritingConversation = memoryWritingConversation;
    }

    public Integer getModeLanguage() {
        return modeLanguage;
    }

    public void setModeLanguage(Integer modeLanguage) {
        this.modeLanguage = modeLanguage;
    }

    /**
     * @return the pageIndexPracticeMemory
     */
    public Integer getPageIndexPracticeMemory() {
        return pageIndexPracticeMemory;
    }

    /**
     * @param pageIndexPracticeMemory the pageIndexPracticeMemory to set
     */
    public void setPageIndexPracticeMemory(Integer pageIndexPracticeMemory) {
        this.pageIndexPracticeMemory = pageIndexPracticeMemory;
    }

    /**
     * @return the pageIndexPracticeWriting
     */
    public Integer getPageIndexPracticeWriting() {
        return pageIndexPracticeWriting;
    }

    /**
     * @param pageIndexPracticeWriting the pageIndexPracticeWriting to set
     */
    public void setPageIndexPracticeWriting(Integer pageIndexPracticeWriting) {
        this.pageIndexPracticeWriting = pageIndexPracticeWriting;
    }

    /**
     * @return the pageIndexPracticeConversation
     */
    public Integer getPageIndexPracticeConversation() {
        return pageIndexPracticeConversation;
    }

    /**
     * @param pageIndexPracticeConversation the pageIndexPracticeConversation to set
     */
    public void setPageIndexPracticeConversation(Integer pageIndexPracticeConversation) {
        this.pageIndexPracticeConversation = pageIndexPracticeConversation;
    }

    /**
     * @return the pageIndexPracticeMemoryVocabulary
     */
    public Integer getPageIndexPracticeMemoryVocabulary() {
        return pageIndexPracticeMemoryVocabulary;
    }

    /**
     * @param pageIndexPracticeMemoryVocabulary the pageIndexPracticeMemoryVocabulary to set
     */
    public void setPageIndexPracticeMemoryVocabulary(Integer pageIndexPracticeMemoryVocabulary) {
        this.pageIndexPracticeMemoryVocabulary = pageIndexPracticeMemoryVocabulary;
    }

    /**
     * @return the pageIndexPracticeWritingVocabulary
     */
    public Integer getPageIndexPracticeWritingVocabulary() {
        return pageIndexPracticeWritingVocabulary;
    }

    /**
     * @param pageIndexPracticeWritingVocabulary the pageIndexPracticeWritingVocabulary to set
     */
    public void setPageIndexPracticeWritingVocabulary(Integer pageIndexPracticeWritingVocabulary) {
        this.pageIndexPracticeWritingVocabulary = pageIndexPracticeWritingVocabulary;
    }

    /**
     * @return the pageIndexPracticeConversationVocabulary
     */
    public Integer getPageIndexPracticeConversationVocabulary() {
        return pageIndexPracticeConversationVocabulary;
    }

    /**
     * @param pageIndexPracticeConversationVocabulary the pageIndexPracticeConversationVocabulary to set
     */
    public void setPageIndexPracticeConversationVocabulary(Integer pageIndexPracticeConversationVocabulary) {
        this.pageIndexPracticeConversationVocabulary = pageIndexPracticeConversationVocabulary;
    }

}
