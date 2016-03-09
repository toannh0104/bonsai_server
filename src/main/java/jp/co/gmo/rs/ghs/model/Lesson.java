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
 * create lesson mapping
 *
 * @author ThuyTTT
 *
 */
@Entity
@Table(name = "lesson")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Lesson implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    /* Init id */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /* Init lessonName */
    @Column(name = "lesson_name", length = 100, nullable = false)
    private String lessonName;

    /* Init lessonNameMlId */
    @Column(name = "lesson_name_ml_id")
    private Integer lessonNameMlId;

    /* Init lessonType */
    @Column(name = "lesson_type", length = 1, nullable = false)
    private Integer lessonType;

    /* Init japaneseLevel */
    @Column(name = "japanese_level", length = 1, nullable = false)
    private Integer japaneseLevel;

    /* Init percentComplete */
    @Column(name = "percent_complete")
    private Double percentComplete;

    /* Init averageScore */
    @Column(name = "average_score")
    private Double averageScore;

    /* Init lessonRange */
    @Column(name = "lesson_range", length = 100, nullable = false)
    private String lessonRange;

    /* Init lessonMethodRomaji */
    @Column(name = "lesson_method_romaji", length = 50, nullable = false)
    private String lessonMethodRomaji;

    /* Init lessonMethodHiragana */
    @Column(name = "lesson_method_hiragana", length = 50, nullable = false)
    private String lessonMethodHiragana;

    /* Init lessonMethodKanji */
    @Column(name = "lesson_method_kanji", length = 50, nullable = false)
    private String lessonMethodKanji;

    /* Init learningType */
    @Column(name = "learning_type", length = 1, nullable = false)
    private Integer learningType;

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
     * @return the lessonNameMlId
     */
    public Integer getLessonNameMlId() {
        return lessonNameMlId;
    }

    /**
     * @param lessonNameMlId the lessonNameMlId to set
     */
    public void setLessonNameMlId(Integer lessonNameMlId) {
        this.lessonNameMlId = lessonNameMlId;
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
     * @return the japaneseLevel
     */
    public Integer getJapaneseLevel() {
        return japaneseLevel;
    }

    /**
     * @param japaneseLevel the japaneseLevel to set
     */
    public void setJapaneseLevel(Integer japaneseLevel) {
        this.japaneseLevel = japaneseLevel;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
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

}
