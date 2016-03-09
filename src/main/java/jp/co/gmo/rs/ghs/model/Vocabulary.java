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
 * create vocabulary mapping
 *
 * @author ThuyTTT
 *
 */
@Entity
@Table(name = "vocabulary")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Vocabulary implements Serializable {

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

    /* Init vocabularyOrder */
    @Column(name = "vocabulary_order", nullable = false)
    private Integer vocabularyOrder;

    /* Init vocabulary */
    @Column(name = "vocabulary", length = 200, nullable = false)
    private String vocabulary;

    /* Init vocabularyMlId */
    @Column(name = "vocabulary_ml_id")
    private Integer vocabularyMlId;

    /* Init vocabulary */
    @Column(name = "vocabulary_english_name", length = 50)
    private String vocabularyEnglishName;

    /* Init vocabularyKanaName */
    @Column(name = "vocabulary_kana_name", length = 100)
    private String vocabularyKanaName;

    /* Init vocabularyCategories */
    @Column(name = "vocabulary_categories", length = 200)
    private String vocabularyCategories;

    /* Init rubyWord */
    @Column(name = "ruby_word", length = 200)
    private String rubyWord;

    /* Init learningType */
    @Column(name = "learning_type", length = 1)
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

    /* Init deleteFlg*/
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
     * @return the vocabularyMlId
     */
    public Integer getVocabularyMlId() {
        return vocabularyMlId;
    }

    /**
     * @param vocabularyMlId the vocabularyMlId to set
     */
    public void setVocabularyMlId(Integer vocabularyMlId) {
        this.vocabularyMlId = vocabularyMlId;
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
