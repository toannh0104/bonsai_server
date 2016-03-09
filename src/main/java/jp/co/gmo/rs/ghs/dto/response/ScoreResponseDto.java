/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto.response;

import java.util.List;

import jp.co.gmo.rs.ghs.dto.OutComeDto;

/**
 * Response dto for score
 * 
 * @author BaoTQ
 * 
 */
public class ScoreResponseDto extends BaseResponseDto {

    /* Init id */
    private Integer id;

    /* Init practiceMemoryScore */
    private Double practiceMemoryScore;

    /* Init practiceWritingScore */
    private Double practiceWritingScore;

    /* Init practiceConversationScore */
    private Double practiceConversationScore;

    /* Init testMemoryScore */
    private Double testMemoryScore;

    /* Init testWritingScore */
    private Double testWritingScore;

    /* Init testConversationScore */
    private Double testConversationScore;

    /* Init listOutCome */
    private List<OutComeDto> listOutCome;

    /* Init scoreScenario */
    private Double score;

    /* Init areaScore */
    private String areaScore;

    /* Init flagAvgScenario */
    private Boolean flagAvgScenario;

    /* Init flagAvgVocabulary */
    private Boolean flagAvgVocabulary;

    /* Init endTime */
    private String endTime;

    /* Init stringClass */
    private String stringClass;

    /* Init currentIdRecordScenario */
    private Integer currentIdRecordScenario;

    /* Init currentIdRecordVocabulary */
    private Integer currentIdRecordVocabulary;

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
     * @return the practiceMemoryScore
     */
    public Double getPracticeMemoryScore() {
        return practiceMemoryScore;
    }

    /**
     * @param practiceMemoryScore the practiceMemoryScore to set
     */
    public void setPracticeMemoryScore(Double practiceMemoryScore) {
        this.practiceMemoryScore = practiceMemoryScore;
    }

    /**
     * @return the practiceWritingScore
     */
    public Double getPracticeWritingScore() {
        return practiceWritingScore;
    }

    /**
     * @param practiceWritingScore the practiceWritingScore to set
     */
    public void setPracticeWritingScore(Double practiceWritingScore) {
        this.practiceWritingScore = practiceWritingScore;
    }

    /**
     * @return the practiceConversationScore
     */
    public Double getPracticeConversationScore() {
        return practiceConversationScore;
    }

    /**
     * @param practiceConversationScore the practiceConversationScore to set
     */
    public void setPracticeConversationScore(Double practiceConversationScore) {
        this.practiceConversationScore = practiceConversationScore;
    }

    /**
     * @return the testMemoryScore
     */
    public Double getTestMemoryScore() {
        return testMemoryScore;
    }

    /**
     * @param testMemoryScore the testMemoryScore to set
     */
    public void setTestMemoryScore(Double testMemoryScore) {
        this.testMemoryScore = testMemoryScore;
    }

    /**
     * @return the testWritingScore
     */
    public Double getTestWritingScore() {
        return testWritingScore;
    }

    /**
     * @param testWritingScore the testWritingScore to set
     */
    public void setTestWritingScore(Double testWritingScore) {
        this.testWritingScore = testWritingScore;
    }

    /**
     * @return the testConversationScore
     */
    public Double getTestConversationScore() {
        return testConversationScore;
    }

    /**
     * @param testConversationScore the testConversationScore to set
     */
    public void setTestConversationScore(Double testConversationScore) {
        this.testConversationScore = testConversationScore;
    }

    /**
     * @return the listOutCome
     */
    public List<OutComeDto> getListOutCome() {
        return listOutCome;
    }

    /**
     * @param listOutCome the listOutCome to set
     */
    public void setListOutCome(List<OutComeDto> listOutCome) {
        this.listOutCome = listOutCome;
    }

    /**
     * @return the score
     */
    public Double getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(Double score) {
        this.score = score;
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
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
