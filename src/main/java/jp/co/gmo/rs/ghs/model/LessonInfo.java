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
 * create lesson info mapping
 *
 * @author ThuyTTT
 *
 */
@Entity
@Table(name = "lesson_info")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LessonInfo implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    /* Init id */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /* Init lessonId */
    @Column(name = "lesson_id", nullable = false)
    private Integer lessonId;

    /* Init lessonNo */
    @Column(name = "lesson_no", nullable = false)
    private Integer lessonNo;

    /* Init practiceMemoryCondition */
    @Column(name = "practice_memory_condition", nullable = false)
    private Double practiceMemoryCondition;

    /* Init practiceWritingCondition */
    @Column(name = "practice_writing_condition", nullable = false)
    private Double practiceWritingCondition;

    /* Init practiceConversationCondition */
    @Column(name = "practice_conversation_condition", nullable = false)
    private Double practiceConversationCondition;

    /* Init testMemoryCondition */
    @Column(name = "test_memory_condition", nullable = false)
    private Double testMemoryCondition;

    /* Init testWritingCondition */
    @Column(name = "test_writing_condition", nullable = false)
    private Double testWritingCondition;

    /* Init testConversationCondition */
    @Column(name = "test_conversation_condition", nullable = false)
    private Double testConversationCondition;

    /* Init scenarioName */
    @Column(name = "scenario_name", length = 100, nullable = false)
    private String scenarioName;

    /* Init scenarioNameMlId */
    @Column(name = "scenario_name_ml_id")
    private Integer scenarioNameMlId;

    /* Init scenarioSyntax */
    @Column(name = "scenario_syntax", length = 100, nullable = false)
    private String scenarioSyntax;

    /* Init practiceNo */
    @Column(name = "practice_no")
    private Integer practiceNo;

    /* Init practiceLimit */
    @Column(name = "practice_limit", nullable = false)
    private Integer practiceLimit;

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

    /* Init speakerName1 */
    @Column(name = "speaker_1_name", length = 100)
    private String speakerOneName;

    /* Init speakerName2 */
    @Column(name = "speaker_2_name", length = 100)
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
     * @return the scenarioNameMlId
     */
    public Integer getScenarioNameMlId() {
        return scenarioNameMlId;
    }

    /**
     * @param scenarioNameMlId
     *            the scenarioNameMlId to set
     */
    public void setScenarioNameMlId(Integer scenarioNameMlId) {
        this.scenarioNameMlId = scenarioNameMlId;
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
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     *            the createdAt to set
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
     * @param updatedAt
     *            the updatedAt to set
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
     * @param deleteAt
     *            the deleteAt to set
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
     * @return the practiceNo
     */
    public Integer getPracticeNo() {
        return practiceNo;
    }

    /**
     * @param practiceNo
     *            the practiceNo to set
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
     * @param practiceLimit
     *            the practiceLimit to set
     */
    public void setPracticeLimit(Integer practiceLimit) {
        this.practiceLimit = practiceLimit;
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
