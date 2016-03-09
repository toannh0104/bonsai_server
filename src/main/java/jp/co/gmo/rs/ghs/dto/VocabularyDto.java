/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * create vocabulary dto
 *
 * @author ThuyTTT
 *
 */
public class VocabularyDto implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    /* Init id */
    private Integer id;

    /* Init lessonInfoId */
    private Integer lessonInfoId;

    /* Init vocabularyOrder */
    private Integer vocabularyOrder;

    /* Init vocabulary */
    private String vocabulary;

    /* Init vocabularyEn */
    private String vocabularyEn;

    /* Init vocabularyLn */
    private String vocabularyLn;

    /* Init rubyWord */
    private String rubyWord;

    /* Init learningType */
    private Integer learningType;

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

    /* Init vocabularyEnglishName */
    private String vocabularyEnglishName;

    /* Init vocabularyKanaName */
    private String vocabularyKanaName;

    /* Init vocabularyCategories */
    private String vocabularyCategories;

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
     * @return the vocabularyOrder
     */
    public Integer getVocabularyOrder() {
        return vocabularyOrder;
    }

    /**
     * @param vocabularyOrder the vocabularyOrder to set
     */
    public void setVocabularyOrder(Integer vocabularyOrder) {
        this.vocabularyOrder = vocabularyOrder;
    }

    /**
     * @return the vocabulary
     */
    public String getVocabulary() {
        return vocabulary;
    }

    /**
     * @param vocabulary the vocabulary to set
     */
    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    /**
     * @return the vocabularyEn
     */
    public String getVocabularyEn() {
        return vocabularyEn;
    }

    /**
     * @param vocabularyEn the vocabularyEn to set
     */
    public void setVocabularyEn(String vocabularyEn) {
        this.vocabularyEn = vocabularyEn;
    }

    /**
     * @return the vocabularyLn
     */
    public String getVocabularyLn() {
        return vocabularyLn;
    }

    /**
     * @param vocabularyLn the vocabularyLn to set
     */
    public void setVocabularyLn(String vocabularyLn) {
        this.vocabularyLn = vocabularyLn;
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
     * @return the vocabularyEnglishName
     */
    public String getVocabularyEnglishName() {
        return vocabularyEnglishName;
    }

    /**
     * @param vocabularyEnglishName the vocabularyEnglishName to set
     */
    public void setVocabularyEnglishName(String vocabularyEnglishName) {
        this.vocabularyEnglishName = vocabularyEnglishName;
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
     * @return the vocabularyKanaName
     */
    public String getVocabularyKanaName() {
        return vocabularyKanaName;
    }

    /**
     * @param vocabularyKanaName the vocabularyKanaName to set
     */
    public void setVocabularyKanaName(String vocabularyKanaName) {
        this.vocabularyKanaName = vocabularyKanaName;
    }

    /**
     * @return the vocabularyCategories
     */
    public String getVocabularyCategories() {
        return vocabularyCategories;
    }

    /**
     * @param vocabularyCategories the vocabularyCategories to set
     */
    public void setVocabularyCategories(String vocabularyCategories) {
        this.vocabularyCategories = vocabularyCategories;
    }

}
