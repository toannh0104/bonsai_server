/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.model;

/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
import java.util.Date;

/**
 * create DictAF mapping
 *
 * @author ThuyTTT
 *
 */
public interface DictBase {

    /**
     * @return the id
     */
    Integer getId();

    /**
     * @param id the id to set
     */
    void setId(Integer id);

    /**
     * @return the dictJaId
     */
    Integer getDictJaId();

    /**
     * @param dictJaId the dictJaId to set
     */
    void setDictJaId(Integer dictJaId);
    /**
     * @return the word1
     */
    String getWord1();

    /**
     * @param word1 the word1 to set
     */
    void setWord1(String word1);

    /**
     * @return the word2
     */
    String getWord2();

    /**
     * @param word2 the word2 to set
     */
    void setWord2(String word2);

    /**
     * @return the word3
     */
    String getWord3();

    /**
     * @param word3 the word3 to set
     */
    void setWord3(String word3);

    /**
     * @return the createdAt
     */
    Date getCreatedAt();

    /**
     * @param createdAt the createdAt to set
     */
    void setCreatedAt(Date createdAt);

    /**
     * @return the updatedAt
     */
    Date getUpdatedAt();

    /**
     * @param updatedAt the updatedAt to set
     */
    void setUpdatedAt(Date updatedAt);

    /**
     * @return the deleteAt
     */
    Date getDeleteAt();

    /**
     * @param deleteAt the deleteAt to set
     */
    void setDeleteAt(Date deleteAt);

    /**
     * @return the deleteFlg
     */
    Integer getDeleteFlg();

    /**
     * @param deleteFlg the deleteFlg to set
     */
    void setDeleteFlg(Integer deleteFlg);

}
