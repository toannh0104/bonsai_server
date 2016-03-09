/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * create lesson info dto
 *
 * @author ThuyTTT
 *
 */
public class LessonInfoDto implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    /* Init id */
    private Integer id;

    /* Init lessonId */
    private Integer lessonId;

    /* Init lessonNo */
    private Integer lessonNo;

    /* Init practiceMemoryCondition */
    private Double practiceMemoryCondition;
    
    /* Init practiceMemoryScore */
    private Double practiceMemoryScore;

    /* Init practiceWritingCondition */
    private Double practiceWritingCondition;

    /* Init practiceWritingScore */
    private Double practiceWritingScore;
    
    /* Init practiceConversationCondition */
    private Double practiceConversationCondition;
    
    /* Init practiceConversationScore */
    private Double practiceConversationScore;

    /* Init testMemoryCondition */
    private Double testMemoryCondition;
    
    /* Init testMemoryScore */
    private Double testMemoryScore;

    /* Init testWritingCondition */
    private Double testWritingCondition;

    /* Init testWritingScore */
    private Double testWritingScore;
    
    /* Init testConversationCondition */
    private Double testConversationCondition;
    
    /* Init testConversationScore */
    private Double testConversationScore;

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

    /* Init deleteFlg is null */
    private Integer deleteFlg;

    /* Init list ScenarioDto */
    private List<ScenarioDto> scenarioDtos;

    /* Init list VocabularyDto */
    private List<VocabularyDto> vocabularyDtos;

    /* Init createdAt */
    private Date createdAt;

    /* Init updatedAt */
    private Date updatedAt;

    /* Init deleteAt */
    private Date deleteAt;

    /* Init speakerName1 */
    private String speakerOneName;

    /* Init speakerName2 */
    private String speakerTwoName;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return the lessonNo
     */
    public Integer getLessonNo() {
        return lessonNo;
    }

    /**
     * @param lessonNo
     *            the lessonNo to set
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
     * @param practiceMemoryCondition
     *            the practiceMemoryCondition to set
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
     * @param practiceWritingCondition
     *            the practiceWritingCondition to set
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
     * @param practiceConversationCondition
     *            the practiceConversationCondition to set
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
     * @param testMemoryCondition
     *            the testMemoryCondition to set
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
     * @param testWritingCondition
     *            the testWritingCondition to set
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
     * @param testConversationCondition
     *            the testConversationCondition to set
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
     * @param scenarioName
     *            the scenarioName to set
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
     * @param scenarioSyntax
     *            the scenarioSyntax to set
     */
    public void setScenarioSyntax(String scenarioSyntax) {
        this.scenarioSyntax = scenarioSyntax;
    }

    /**
     * @return the deleteFlg
     */
    public Integer getDeleteFlg() {
        return deleteFlg;
    }

    /**
     * @param deleteFlg
     *            the deleteFlg to set
     */
    public void setDeleteFlg(Integer deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    /**
     * @return the scenarioDtos
     */
    public List<ScenarioDto> getScenarioDtos() {
        return scenarioDtos;
    }

    /**
     * @param scenarioDtos
     *            the scenarioDtos to set
     */
    public void setScenarioDtos(List<ScenarioDto> scenarioDtos) {
        this.scenarioDtos = scenarioDtos;
    }

    /**
     * @return the vocabularyDtos
     */
    public List<VocabularyDto> getVocabularyDtos() {
        return vocabularyDtos;
    }

    /**
     * @param vocabularyDtos
     *            the vocabularyDtos to set
     */
    public void setVocabularyDtos(List<VocabularyDto> vocabularyDtos) {
        this.vocabularyDtos = vocabularyDtos;
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
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the updatedAt
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return the deleteAt
     */
    public Date getDeleteAt() {
        return deleteAt;
    }

    /**
     * @param deleteAt the deleteAt to set
     */
    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
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
}
