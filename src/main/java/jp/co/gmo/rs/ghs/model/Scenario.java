/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * create scenario mapping
 *
 * @author ThuyTTT
 *
 */
@Entity
@Table(name = "scenario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Scenario implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    /* Init id */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /* Init lessonInfoId */
    @Column(name = "lesson_info_id", nullable = false)
    private Integer lessonInfoId;

    /* Init scenarioPart */
    @Column(name = "scenario_part", nullable = false)
    private Integer scenarioPart;

    /* Init scenarioOrder */
    @Column(name = "scenario_order", nullable = false)
    private Integer scenarioOrder;

    /* Init scenario */
    @Column(name = "scenario", length = 200, nullable = false)
    private String scenario;

    /* Init scenarioMlId */
    @Column(name = "scenario_ml_id")
    private Integer scenarioMlId;

    /* Init calljLessonNo */
    @Column(name = "callj_lesson_no")
    private Integer calljLessonNo;

    /* Init calljQuestionName */
    @Column(name = "callj_question_name", length = 50)
    private String calljQuestionName;

    /* Init calljConceptName */
    @Column(name = "callj_concept_name", length = 50)
    private String calljConceptName;

    /* Init calljWord */
    @Column(name = "callj_word", length = 200)
    private String calljWord;

    /* Init rubyWord */
    @Column(name = "ruby_word", length = 200)
    private String rubyWord;

    /* Init learningType */
    @Column(name = "learning_type", length = 1)
    private Integer learningType;

    /* Init categoryWord */
    @Column(name = "category_word", length = 200)
    private String categoryWord;

    /* Init createdAt */
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /* Init updatedAt */
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    /* Init deleteAt */
    @Column(name = "delete_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteAt;

    /* Init deleteFlg */
    @Column(name = "delete_flg", length = 1, nullable = false)
    private Integer deleteFlg;

    /* Init userInputFlg */
    @Column(name = "user_input_flg", length = 1)
    private Integer userInputFlg;

    
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
     * @return the scenarioMlId
     */
    public Integer getScenarioMlId() {
        return scenarioMlId;
    }

    /**
     * @param scenarioMlId the scenarioMlId to set
     */
    public void setScenarioMlId(Integer scenarioMlId) {
        this.scenarioMlId = scenarioMlId;
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
