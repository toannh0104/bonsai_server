/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

/**
 * ResultDto
 *
 * @author LongVNH
 *
 */
public class ResultDto {

    private Integer size;

    private String fileName;

    private String kana;

    private String kanji;

    private String romaji;

    /**
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the kana
     */
    public String getKana() {
        return kana;
    }

    /**
     * @param kana the kana to set
     */
    public void setKana(String kana) {
        this.kana = kana;
    }

    /**
     * @return the kanji
     */
    public String getKanji() {
        return kanji;
    }

    /**
     * @param kanji the kanji to set
     */
    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    /**
     * @return the romaji
     */
    public String getRomaji() {
        return romaji;
    }

    /**
     * @param romaji the romaji to set
     */
    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }


}
