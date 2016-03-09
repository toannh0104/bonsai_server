/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto.form;

import java.util.List;

/**
 * Scenario Question form
 * 
 * @author Thuy
 * 
 */
public class QuestionAnswerForm {

    /* Init lessonType */
    private Integer lessonType;

    /* Init question */
    private List<String> questions;

    /* Init answer */
    private List<String> answers;

    /* Init question id */
    private List<String> questionIds;

    /* Init vocabularyQuestions */
    private List<String> vocabularyQuestions;

    /* Init vocabularyAnswers */
    private List<String> vocabularyAnswers;
    
    /* Init lessonId */
    private Integer lessonId;
    
    /* Init lessonInfo */
    private Integer lessonInfo;
    
    /* Init scenarioVocabulary */
    private String scenarioVocabulary;
    
    /* Init practiceTest */
    private String practiceTest;
    
    /* Init memoryWriteSpeech */
    private String memoryWriteSpeech;
    
    /* Init areaScore */
    private String areaScore;
    
    /* Init saveScoreScenario */
    private Double saveScoreScenario;
    
    /* Init saveScoreVocabulary */
    private Double saveScoreVocabulary;
    
    /* Init flagAvgScenario */
    private Boolean flagAvgScenario;
    
    /* Init flagAvgVocabulary */
    private Boolean flagAvgVocabulary;
    
    /* Init practiceTime */
    private String practiceTime;
    
    /* Init lessonNo */
    private Integer lessonNo;
    
    /* Init lessonName */
    private String lessonName;
    
    /* Init startTime */
    private String startTime;

    /* Init stringClass */
    private String stringClass;

    /* Init currentIdRecordScenario */
    private Integer currentIdRecordScenario;

    /* Init currentIdRecordScenario */
    private Integer currentIdRecordVocabulary;

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
     * @return the questions
     */
    public List<String> getQuestions() {
        return questions;
    }

    /**
     * @param questions the questions to set
     */
    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    /**
     * @return the answers
     */
    public List<String> getAnswers() {
        return answers;
    }

    /**
     * @param answers the answers to set
     */
    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    /**
     * @return the vocabularyQuestions
     */
    public List<String> getVocabularyQuestions() {
        return vocabularyQuestions;
    }

    /**
     * @param vocabularyQuestions the vocabularyQuestions to set
     */
    public void setVocabularyQuestions(List<String> vocabularyQuestions) {
        this.vocabularyQuestions = vocabularyQuestions;
    }

    /**
     * @return the vocabularyAnswers
     */
    public List<String> getVocabularyAnswers() {
        return vocabularyAnswers;
    }

    /**
     * @param vocabularyAnswers the vocabularyAnswers to set
     */
    public void setVocabularyAnswers(List<String> vocabularyAnswers) {
        this.vocabularyAnswers = vocabularyAnswers;
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
     * @return the lessonInfo
     */
    public Integer getLessonInfo() {
        return lessonInfo;
    }

    /**
     * @param lessonInfo the lessonInfo to set
     */
    public void setLessonInfo(Integer lessonInfo) {
        this.lessonInfo = lessonInfo;
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
     * @return the memoryWriteSpeech
     */
    public String getMemoryWriteSpeech() {
        return memoryWriteSpeech;
    }

    /**
     * @param memoryWriteSpeech the memoryWriteSpeech to set
     */
    public void setMemoryWriteSpeech(String memoryWriteSpeech) {
        this.memoryWriteSpeech = memoryWriteSpeech;
    }

    /**
     * @return the areaScore
     */
    public String getAreaScore() {
        return areaScore;
    }

    /**
     * @param areaScore the areaScore to set
     */
    public void setAreaScore(String areaScore) {
        this.areaScore = areaScore;
    }

    /**
     * @return the saveScoreScenario
     */
    public Double getSaveScoreScenario() {
        return saveScoreScenario;
    }

    /**
     * @param saveScoreScenario the saveScoreScenario to set
     */
    public void setSaveScoreScenario(Double saveScoreScenario) {
        this.saveScoreScenario = saveScoreScenario;
    }

    /**
     * @return the saveScoreVocabulary
     */
    public Double getSaveScoreVocabulary() {
        return saveScoreVocabulary;
    }

    /**
     * @param saveScoreVocabulary the saveScoreVocabulary to set
     */
    public void setSaveScoreVocabulary(Double saveScoreVocabulary) {
        this.saveScoreVocabulary = saveScoreVocabulary;
    }

    /**
     * @return the questionIds
     */
    public List<String> getQuestionIds() {
        return questionIds;
    }

    /**
     * @param questionIds the questionIds to set
     */
    public void setQuestionIds(List<String> questionIds) {
        this.questionIds = questionIds;
    }

    /**
     * @return the flagAvgScenario
     */
    public Boolean getFlagAvgScenario() {
        return flagAvgScenario;
    }

    /**
     * @param flagAvgScenario the flagAvgScenario to set
     */
    public void setFlagAvgScenario(Boolean flagAvgScenario) {
        this.flagAvgScenario = flagAvgScenario;
    }

    /**
     * @return the flagAvgVocabulary
     */
    public Boolean getFlagAvgVocabulary() {
        return flagAvgVocabulary;
    }

    /**
     * @param flagAvgVocabulary the flagAvgVocabulary to set
     */
    public void setFlagAvgVocabulary(Boolean flagAvgVocabulary) {
        this.flagAvgVocabulary = flagAvgVocabulary;
    }

    /**
     * @return the practiceTime
     */
    public String getPracticeTime() {
        return practiceTime;
    }

    /**
     * @param practiceTime the practiceTime to set
     */
    public void setPracticeTime(String practiceTime) {
        this.practiceTime = practiceTime;
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
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the stringClass
     */
    public String getStringClass() {
        return stringClass;
    }

    /**
     * @param stringClass the stringClass to set
     */
    public void setStringClass(String stringClass) {
        this.stringClass = stringClass;
    }

    /**
     * @return the currentIdRecordScenario
     */
    public Integer getCurrentIdRecordScenario() {
        return currentIdRecordScenario;
    }

    /**
     * @param currentIdRecordScenario the currentIdRecordScenario to set
     */
    public void setCurrentIdRecordScenario(Integer currentIdRecordScenario) {
        this.currentIdRecordScenario = currentIdRecordScenario;
    }

    /**
     * @return the currentIdRecordVocabulary
     */
    public Integer getCurrentIdRecordVocabulary() {
        return currentIdRecordVocabulary;
    }

    /**
     * @param currentIdRecordVocabulary the currentIdRecordVocabulary to set
     */
    public void setCurrentIdRecordVocabulary(Integer currentIdRecordVocabulary) {
        this.currentIdRecordVocabulary = currentIdRecordVocabulary;
    }

}
