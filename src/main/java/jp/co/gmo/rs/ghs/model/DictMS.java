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
 * create DictMS mapping
 * 
 * @author ThuyTTT
 *
 */
@Entity
@Table(name = "DICT_MS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class DictMS implements Serializable, DictBase {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    /* Init id */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /* Init dictJaId */
    @Column(name = "dict_ja_id", nullable = false)
    private Integer dictJaId;

    /* Init word1 */
    @Column(name = "word_1", length = 300, nullable = false)
    private String word1;

    /* Init word2 */
    @Column(name = "word_2", length = 300)
    private String word2;

    /* Init word3 */
    @Column(name = "word_3", length = 300)
    private String word3;

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
     * @return the dictJaId
     */
    public Integer getDictJaId() {
        return dictJaId;
    }

    /**
     * @param dictJaId the dictJaId to set
     */
    public void setDictJaId(Integer dictJaId) {
        this.dictJaId = dictJaId;
    }

    /**
     * @return the word1
     */
    public String getWord1() {
        return word1;
    }

    /**
     * @param word1 the word1 to set
     */
    public void setWord1(String word1) {
        this.word1 = word1;
    }

    /**
     * @return the word2
     */
    public String getWord2() {
        return word2;
    }

    /**
     * @param word2 the word2 to set
     */
    public void setWord2(String word2) {
        this.word2 = word2;
    }

    /**
     * @return the word3
     */
    public String getWord3() {
        return word3;
    }

    /**
     * @param word3 the word3 to set
     */
    public void setWord3(String word3) {
        this.word3 = word3;
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
