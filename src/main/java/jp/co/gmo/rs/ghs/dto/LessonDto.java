/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Create lesson dto
 * 
 * @author ThuyTTT
 * 
 */
public class LessonDto implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

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

    /* Init deleteFlg */
    private Integer deleteFlg;

    /* Init lessonInfoDtos */
    private List<LessonInfoDto> lessonInfoDtos;

    /* Init createdAt */
    private Date createdAt;

    /* Init updatedAt */
    private Date updatedAt;

    /* Init deleteAt */
    private Date deleteAt;

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
     * @return the lessonInfoDtos
     */
    public List<LessonInfoDto> getLessonInfoDtos() {
        return lessonInfoDtos;
    }

    /**
     * @param lessonInfoDtos the lessonInfoDtos to set
     */
    public void setLessonInfoDtos(List<LessonInfoDto> lessonInfoDtos) {
        this.lessonInfoDtos = lessonInfoDtos;
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
