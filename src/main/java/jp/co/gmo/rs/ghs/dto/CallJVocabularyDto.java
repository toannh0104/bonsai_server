/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.io.Serializable;

/**
 * create callj vocabulary dto for client practice/test
 *
 * @author DongNSH
 *
 */
public class CallJVocabularyDto implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    /* Init vocabulary */
    private String vocabulary;

    /* Init vocabularyEnglishName */
    private String vocabularyEnglishName;

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

}
