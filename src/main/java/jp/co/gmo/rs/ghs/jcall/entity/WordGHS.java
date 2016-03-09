/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.jcall.entity;

import java.io.Serializable;
import java.util.List;

import jp.co.gmo.rs.ghs.dto.CategoryOrWord;
import jp.co.gmo.rs.ghs.dto.SentenceWordDto;

/**
 * Word
 *
 * @author ThuyTTT
 *
 */
public class WordGHS implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String kana;

    private String kanji;

    private String romaji;

    private String baseKana;

    private String baseKanji;

    private String wordType;

    private String english;

    private List<String> slotName;

    private SentenceWordDto sentenceWordDto;

    private CategoryOrWord categoryOrWord;

    private String typeSlot;

    public String getKana() {
        return kana;
    }

    public void setKana(String kana) {
        this.kana = kana;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    public String getBaseKana() {
        return baseKana;
    }

    public void setBaseKana(String baseKana) {
        this.baseKana = baseKana;
    }

    public String getBaseKanji() {
        return baseKanji;
    }

    public void setBaseKanji(String baseKanji) {
        this.baseKanji = baseKanji;
    }

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    public SentenceWordDto getSentenceWordDto() {
        return sentenceWordDto;
    }

    public void setSentenceWordDto(SentenceWordDto sentenceWordDto) {
        this.sentenceWordDto = sentenceWordDto;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public List<String> getSlotName() {
        return slotName;
    }

    public void setSlotName(List<String> slotName) {
        this.slotName = slotName;
    }

    public CategoryOrWord getCategoryOrWord() {
        return categoryOrWord;
    }

    public void setCategoryOrWord(CategoryOrWord categoryOrWord) {
        this.categoryOrWord = categoryOrWord;
    }

    /**
     * @return the typeSlot
     */
    public String getTypeSlot() {
        return typeSlot;
    }

    /**
     * @param typeSlot the typeSlot to set
     */
    public void setTypeSlot(String typeSlot) {
        this.typeSlot = typeSlot;
    }

}
