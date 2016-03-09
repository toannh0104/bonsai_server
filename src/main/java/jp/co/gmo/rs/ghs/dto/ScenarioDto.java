/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * create scenario dto
 *
 * @author ThuyTTT
 *
 */
public class ScenarioDto implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    /* Init id */
    private Integer id;

    /* Init lessonInfoId */
    private Integer lessonInfoId;

    /* Init scenarioPart */
    private Integer scenarioPart;

    /* Init scenarioOrder */
    private Integer scenarioOrder;

    /* Init scenario */
    private String scenario;

    /* Init scenarioEn */
    private String scenarioEn;

    /* Init scenarioLn */
    private String scenarioLn;

    /* Init rubyWord */
    private String rubyWord;

    /* Init learningType */
    private Integer learningType;

    /* Init categoryWord */
    private String categoryWord;

    /* Init deleteFlg */
    private Integer deleteFlg;

    /* Init createdAt */
    private Date createdAt;

    /* Init updatedAt */
    private Date updatedAt;

    /* Init deleteAt */
    private Date deleteAt;

    /* Init userInputFlg */
    private Integer userInputFlg;

    /* Init calljLessonNo */
    private Integer calljLessonNo;

    /* Init calljQuestionName */
    private String calljQuestionName;

    /* Init calljConceptName */
    private String calljConceptName;

    /* Init calljWord */
    private String calljWord;

    /**
     * @return the lessonInfoId
     */
    public Integer getLessonInfoId() {
        return lessonInfoId;
    }

    /**
     * @param lessonInfoId the lessonInfoId to set
     */
    public void setLessonInfoId(Integer lessonInfoId) {
        this.lessonInfoId = lessonInfoId;
    }

    /**
     * @return the scenarioPart
     */
    public Integer getScenarioPart() {
        return scenarioPart;
    }

    /**
     * @param scenarioPart the scenarioPart to set
     */
    public void setScenarioPart(Integer scenarioPart) {
        this.scenarioPart = scenarioPart;
    }

    /**
     * @return the scenarioOrder
     */
    public Integer getScenarioOrder() {
        return scenarioOrder;
    }

    /**
     * @param scenarioOrder the scenarioOrder to set
     */
    public void setScenarioOrder(Integer scenarioOrder) {
        this.scenarioOrder = scenarioOrder;
    }

    /**
     * @return the scenario
     */
    public String getScenario() {
        return scenario;
    }

    /**
     * @param scenario the scenario to set
     */
    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    /**
     * @return the scenarioEn
     */
    public String getScenarioEn() {
        return scenarioEn;
    }

    /**
     * @param scenarioEn the scenarioEn to set
     */
    public void setScenarioEn(String scenarioEn) {
        this.scenarioEn = scenarioEn;
    }

    /**
     * @return the scenarioLn
     */
    public String getScenarioLn() {
        return scenarioLn;
    }

    /**
     * @param scenarioLn the scenarioLn to set
     */
    public void setScenarioLn(String scenarioLn) {
        this.scenarioLn = scenarioLn;
    }

    /**
     * @return the deleteFlg
     */
    public Integer getDeleteFlg() {
        return deleteFlg;
    }

    /**
     * @param deleteFlg the deleteFlg to set
     */
    public void setDeleteFlg(Integer deleteFlg) {
        this.deleteFlg = deleteFlg;
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
     * @return the userInputFlg
     */
    public Integer getUserInputFlg() {
        return userInputFlg;
    }

    /**
     * @param userInputFlg the userInputFlg to set
     */
    public void setUserInputFlg(Integer userInputFlg) {
        this.userInputFlg = userInputFlg;
    }

    /**
     * @return the calljLessonNo
     */
    public Integer getCalljLessonNo() {
        return calljLessonNo;
    }

    /**
     * @param calljLessonNo the calljLessonNo to set
     */
    public void setCalljLessonNo(Integer calljLessonNo) {
        this.calljLessonNo = calljLessonNo;
    }

    /**
     * @return the calljQuestionName
     */
    public String getCalljQuestionName() {
        return calljQuestionName;
    }

    /**
     * @param calljQuestionName the calljQuestionName to set
     */
    public void setCalljQuestionName(String calljQuestionName) {
        this.calljQuestionName = calljQuestionName;
    }

    /**
     * @return the calljConceptName
     */
    public String getCalljConceptName() {
        return calljConceptName;
    }

    /**
     * @param calljConceptName the calljConceptName to set
     */
    public void setCalljConceptName(String calljConceptName) {
        this.calljConceptName = calljConceptName;
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

    /**
     * @return the rubyWord
     */
    public String getRubyWord() {
        return rubyWord;
    }

    /**
     * @param rubyWord the rubyWord to set
     */
    public void setRubyWord(String rubyWord) {
        this.rubyWord = rubyWord;
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

}
