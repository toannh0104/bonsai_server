/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.io.Serializable;

/**
 * WordStructDto
 *
 * @author LongVNH
 *
 */
public class WordStructDto implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    /* Init kanji */
    private String kanji;

    /* Init type */
    private String type;

    /**
     * @return the kanji
     */
    public String getKanji() {
        return kanji;
    }

    /**
     * @param kanji
     *            the kanji to set
     */
    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
