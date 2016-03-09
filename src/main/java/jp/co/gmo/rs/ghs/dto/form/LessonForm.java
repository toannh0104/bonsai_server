/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto.form;

import java.util.List;

/**
 * lesson form
 * 
 * @author BaoTG
 * 
 */
public class LessonForm {

    /* Init id */
    private Integer id;

    /* Init lessonName */
    private String lessonName;

    /* Init lessonNameEn */
    private String lessonNameEn;

    /* Init lessonNameLn */
    private String lessonNameLn;

    /* Init lessonType */
    private Integer lessonType;

    /* Init level */
    private Integer level;

    /* Init percentComplete */
    private Double percentComplete;

    /* Init averageScore */
    private Double averageScore;

    /* Init lessonRange */
    private String lessonRange;

    /* Init lessonMethodRomaji */
    private String lessonMethodRomaji;

    /* Init lessonMethodHiragana */
    private String lessonMethodHiragana;

    /* Init lessonMethodKanji */
    private String lessonMethodKanji;

    /* Init learningType */
    private Integer learningType;

    /* Init currentLessonInfoIndex */
    private Integer currentLessonInfoIndex;

    /* Init scenarioVocabulary */
    private String scenarioVocabulary;

    /* Init practiceTest */
    private String practiceTest;

    /* Init practiceTest */
    private String memoryWritingSpeaking;

    /* Init lessonInfoDtos */
    private List<LessonInfoForm> lessonInfoForms;

    private String languageId;

    /* Init practiceNo */
    private Integer practiceNo;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the lessonName
     */
    public String getLessonName() {
        return lessonName;
    }

    /**
     * @param lessonName the lessonName to set
     */
    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    /**
     * @return the lessonNameEn
     */
    public String getLessonNameEn() {
        return lessonNameEn;
    }

    /**
     * @param lessonNameEn the lessonNameEn to set
     */
    public void setLessonNameEn(String lessonNameEn) {
        this.lessonNameEn = lessonNameEn;
    }

    /**
     * @return the lessonNameLn
     */
    public String getLessonNameLn() {
        return lessonNameLn;
    }

    /**
     * @param lessonNameLn the lessonNameLn to set
     */
    public void setLessonNameLn(String lessonNameLn) {
        this.lessonNameLn = lessonNameLn;
    }

    /**
     * @return the lessonType
     */
    public Integer getLessonType() {
        return lessonType;
    }

    /**
     * @param lessonType the lessonType to set
     */
    public void setLessonType(Integer lessonType) {
        this.lessonType = lessonType;
    }

    /**
     * @return the level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * @return the percentComplete
     */
    public Double getPercentComplete() {
        return percentComplete;
    }

    /**
     * @param percentComplete the percentComplete to set
     */
    public void setPercentComplete(Double percentComplete) {
        this.percentComplete = percentComplete;
    }

    /**
     * @return the averageScore
     */
    public Double getAverageScore() {
        return averageScore;
    }

    /**
     * @param averageScore the averageScore to set
     */
    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    /**
     * @return the lessonRange
     */
    public String getLessonRange() {
        return lessonRange;
    }

    /**
     * @param lessonRange the lessonRange to set
     */
    public void setLessonRange(String lessonRange) {
        this.lessonRange = lessonRange;
    }

    /**
     * @return the lessonMethodRomaji
     */
    public String getLessonMethodRomaji() {
        return lessonMethodRomaji;
    }

    /**
     * @param lessonMethodRomaji the lessonMethodRomaji to set
     */
    public void setLessonMethodRomaji(String lessonMethodRomaji) {
        this.lessonMethodRomaji = lessonMethodRomaji;
    }

    /**
     * @return the lessonMethodHiragana
     */
    public String getLessonMethodHiragana() {
        return lessonMethodHiragana;
    }

    /**
     * @param lessonMethodHiragana the lessonMethodHiragana to set
     */
    public void setLessonMethodHiragana(String lessonMethodHiragana) {
        this.lessonMethodHiragana = lessonMethodHiragana;
    }

    /**
     * @return the lessonMethodKanji
     */
    public String getLessonMethodKanji() {
        return lessonMethodKanji;
    }

    /**
     * @param lessonMethodKanji the lessonMethodKanji to set
     */
    public void setLessonMethodKanji(String lessonMethodKanji) {
        this.lessonMethodKanji = lessonMethodKanji;
    }

    /**
     * @return the currentLessonInfoIndex
     */
    public Integer getCurrentLessonInfoIndex() {
        return currentLessonInfoIndex;
    }

    /**
     * @param currentLessonInfoIndex the currentLessonInfoIndex to set
     */
    public void setCurrentLessonInfoIndex(Integer currentLessonInfoIndex) {
        this.currentLessonInfoIndex = currentLessonInfoIndex;
    }

    /**
     * @return the scenarioVocabulary
     */
    public String getScenarioVocabulary() {
        return scenarioVocabulary;
    }

    /**
     * @param scenarioVocabulary the scenarioVocabulary to set
     */
    public void setScenarioVocabulary(String scenarioVocabulary) {
        this.scenarioVocabulary = scenarioVocabulary;
    }

    /**
     * @return the practiceTest
     */
    public String getPracticeTest() {
        return practiceTest;
    }

    /**
     * @param practiceTest the practiceTest to set
     */
    public void setPracticeTest(String practiceTest) {
        this.practiceTest = practiceTest;
    }

    /**
     * @return the memoryWritingSpeaking
     */
    public String getMemoryWritingSpeaking() {
        return memoryWritingSpeaking;
    }

    /**
     * @param memoryWritingSpeaking the memoryWritingSpeaking to set
     */
    public void setMemoryWritingSpeaking(String memoryWritingSpeaking) {
        this.memoryWritingSpeaking = memoryWritingSpeaking;
    }

    /**
     * @return the lessonInfoForms
     */
    public List<LessonInfoForm> getLessonInfoForms() {
        return lessonInfoForms;
    }

    /**
     * @param lessonInfoForms the lessonInfoForms to set
     */
    public void setLessonInfoForms(List<LessonInfoForm> lessonInfoForms) {
        this.lessonInfoForms = lessonInfoForms;
    }

    /**
     * @return the languageId
     */
    public String getLanguageId() {
        return languageId;
    }

    /**
     * @param languageId the languageId to set
     */
    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    /**
     * @return the learningType
     */
    public Integer getLearningType() {
        return learningType;
    }

    /**
     * @param learningType the learningType to set
     */
    public void setLearningType(Integer learningType) {
        this.learningType = learningType;
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

}
