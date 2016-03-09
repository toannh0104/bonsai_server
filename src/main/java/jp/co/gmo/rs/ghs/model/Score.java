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
 * create core mapping
 * 
 * @author BaoTQ
 *
 */
@Entity
@Table(name = "score")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Score implements Serializable {

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

    /* Init lessonInfoId */
    @Column(name = "lesson_info_id", nullable = false)
    private Integer lessonInfoId;

    /* Init userId */
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    /* Init practiceMemoryScore */
    @Column(name = "practice_memory_score", nullable = true)
    private Double practiceMemoryScore;
    
    /* Init practiceWritingScore */
    @Column(name = "practice_writing_score", nullable = true)
    private Double practiceWritingScore;
    
    /* Init practiceConversationScore */
    @Column(name = "practice_conversation_score", nullable = true)
    private Double practiceConversationScore;
    
    /* Init testMemoryScore */
    @Column(name = "test_memory_score", nullable = true)
    private Double testMemoryScore;
    
    /* Init testWritingScore */
    @Column(name = "test_writing_score", nullable = true)
    private Double testWritingScore;
    
    /* Init testConversationScore */
    @Column(name = "test_conversation_score", nullable = true)
    private Double testConversationScore;

    /* Init createdAt */
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /* Init updatedAt */
    @Column(name = "updated_at", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    /* Init deleteAt */
    @Column(name = "delete_at", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteAt;

    /* Init deleteFlg */
    @Column(name = "delete_flg", length = 1, nullable = false)
    private Integer deleteFlg;

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
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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

}
