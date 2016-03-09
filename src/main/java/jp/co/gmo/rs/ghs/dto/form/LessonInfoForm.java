/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto.form;

import java.util.List;

/**
 * lesson info form
 *
 * @author BaoTG
 *
 */
public class LessonInfoForm {

    /* Init id */
    private Integer id;

    /* Init lessonId */
    private Integer lessonId;

    /* Init lessonNo */
    private Integer lessonNo;

    /* Init practiceMemoryCondition */
    private Double practiceMemoryCondition;

    /* Init practiceWritingCondition */
    private Double practiceWritingCondition;

    /* Init practiceConversationCondition */
    private Double practiceConversationCondition;

    /* Init testMemoryCondition */
    private Double testMemoryCondition;

    /* Init testWritingCondition */
    private Double testWritingCondition;

    /* Init testConversationCondition */
    private Double testConversationCondition;

    /* Init scenarioName */
    private String scenarioName;

    /* Init scenarioNameEn */
    private String scenarioNameEn;

    /* Init scenarioNameLn */
    private String scenarioNameLn;

    /* Init scenarioSyntax */
    private String scenarioSyntax;

    /* Init practiceNo */
    private Integer practiceNo;

    /* Init practiceLimit */
    private Integer practiceLimit;

    /* Init list ScenarioDto */
    private List<ScenarioForm> scenarioForms;

    /* Init list VocabularyDto */
    private List<VocabularyForm> vocabularyForms;

    /* Init speakerName1 */
    private String speakerOneName;

    /* Init speakerName2 */
    private String speakerTwoName;

    /* Init modeLanguage */
    private Integer modeLanguage;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the lessonId
     */
    public Integer getLessonId() {
        return lessonId;
    }

    /**
     * @param lessonId the lessonId to set
     */
    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    /**
     * @return the lessonNo
     */
    public Integer getLessonNo() {
        return lessonNo;
    }

    /**
     * @param lessonNo the lessonNo to set
     */
    public void setLessonNo(Integer lessonNo) {
        this.lessonNo = lessonNo;
    }

    /**
     * @return the practiceMemoryCondition
     */
    public Double getPracticeMemoryCondition() {
        return practiceMemoryCondition;
    }

    /**
     * @param practiceMemoryCondition the practiceMemoryCondition to set
     */
    public void setPracticeMemoryCondition(Double practiceMemoryCondition) {
        this.practiceMemoryCondition = practiceMemoryCondition;
    }

    /**
     * @return the practiceWritingCondition
     */
    public Double getPracticeWritingCondition() {
        return practiceWritingCondition;
    }

    /**
     * @param practiceWritingCondition the practiceWritingCondition to set
     */
    public void setPracticeWritingCondition(Double practiceWritingCondition) {
        this.practiceWritingCondition = practiceWritingCondition;
    }

    /**
     * @return the practiceConversationCondition
     */
    public Double getPracticeConversationCondition() {
        return practiceConversationCondition;
    }

    /**
     * @param practiceConversationCondition the practiceConversationCondition to set
     */
    public void setPracticeConversationCondition(Double practiceConversationCondition) {
        this.practiceConversationCondition = practiceConversationCondition;
    }

    /**
     * @return the testMemoryCondition
     */
    public Double getTestMemoryCondition() {
        return testMemoryCondition;
    }

    /**
     * @param testMemoryCondition the testMemoryCondition to set
     */
    public void setTestMemoryCondition(Double testMemoryCondition) {
        this.testMemoryCondition = testMemoryCondition;
    }

    /**
     * @return the testWritingCondition
     */
    public Double getTestWritingCondition() {
        return testWritingCondition;
    }

    /**
     * @param testWritingCondition the testWritingCondition to set
     */
    public void setTestWritingCondition(Double testWritingCondition) {
        this.testWritingCondition = testWritingCondition;
    }

    /**
     * @return the testConversationCondition
     */
    public Double getTestConversationCondition() {
        return testConversationCondition;
    }

    /**
     * @param testConversationCondition the testConversationCondition to set
     */
    public void setTestConversationCondition(Double testConversationCondition) {
        this.testConversationCondition = testConversationCondition;
    }

    /**
     * @return the scenarioName
     */
    public String getScenarioName() {
        return scenarioName;
    }

    /**
     * @param scenarioName the scenarioName to set
     */
    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    /**
     * @return the scenarioNameEn
     */
    public String getScenarioNameEn() {
        return scenarioNameEn;
    }

    /**
     * @param scenarioNameEn the scenarioNameEn to set
     */
    public void setScenarioNameEn(String scenarioNameEn) {
        this.scenarioNameEn = scenarioNameEn;
    }

    /**
     * @return the scenarioNameLn
     */
    public String getScenarioNameLn() {
        return scenarioNameLn;
    }

    /**
     * @param scenarioNameLn the scenarioNameLn to set
     */
    public void setScenarioNameLn(String scenarioNameLn) {
        this.scenarioNameLn = scenarioNameLn;
    }

    /**
     * @return the scenarioSyntax
     */
    public String getScenarioSyntax() {
        return scenarioSyntax;
    }

    /**
     * @param scenarioSyntax the scenarioSyntax to set
     */
    public void setScenarioSyntax(String scenarioSyntax) {
        this.scenarioSyntax = scenarioSyntax;
    }

    /**
     * @return the practiceNo
     */
    public Integer getPracticeNo() {
        return practiceNo;
    }

    /**
     * @param practiceNo the practiceNo to set
     */
    public void setPracticeNo(Integer practiceNo) {
        this.practiceNo = practiceNo;
    }

    /**
     * @return the practiceLimit
     */
    public Integer getPracticeLimit() {
        return practiceLimit;
    }

    /**
     * @param practiceLimit the practiceLimit to set
     */
    public void setPracticeLimit(Integer practiceLimit) {
        this.practiceLimit = practiceLimit;
    }

    /**
     * @return the scenarioForms
     */
    public List<ScenarioForm> getScenarioForms() {
        return scenarioForms;
    }

    /**
     * @param scenarioForms the scenarioForms to set
     */
    public void setScenarioForms(List<ScenarioForm> scenarioForms) {
        this.scenarioForms = scenarioForms;
    }

    /**
     * @return the vocabularyForms
     */
    public List<VocabularyForm> getVocabularyForms() {
        return vocabularyForms;
    }

    /**
     * @param vocabularyForms the vocabularyForms to set
     */
    public void setVocabularyForms(List<VocabularyForm> vocabularyForms) {
        this.vocabularyForms = vocabularyForms;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the speakerOneName
     */
    public String getSpeakerOneName() {
        return speakerOneName;
    }

    /**
     * @param speakerOneName the speakerOneName to set
     */
    public void setSpeakerOneName(String speakerOneName) {
        this.speakerOneName = speakerOneName;
    }

    /**
     * @return the speakerTwoName
     */
    public String getSpeakerTwoName() {
        return speakerTwoName;
    }

    /**
     * @param speakerTwoName the speakerTwoName to set
     */
    public void setSpeakerTwoName(String speakerTwoName) {
        this.speakerTwoName = speakerTwoName;
    }

    /**
     * @return the modeLanguage
     */
    public Integer getModeLanguage() {
        return modeLanguage;
    }

    /**
     * @param modeLanguage the modeLanguage to set
     */
    public void setModeLanguage(Integer modeLanguage) {
        this.modeLanguage = modeLanguage;
    }

}
