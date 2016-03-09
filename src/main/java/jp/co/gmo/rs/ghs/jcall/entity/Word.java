/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.jcall.entity;

import java.io.Serializable;
import java.util.List;

import jp.co.gmo.rs.ghs.util.StringUtils;

/**
 * Word
 *
 * @author ThuyTTT
 *
 */
@SuppressWarnings("rawtypes")
public class Word implements Comparable, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<String> showRomanj;

    private String wordshow;

    private String wordHint;

    private Integer id;

    private Integer level;

    private Integer groups;

    private String kana;

    private String kanji;

    private String romaji;

    private String baseKana;

    private String baseKanji;

    private String baseRomaji;

    private Integer type; // loai tu

    private String typeName;

    private String picture;

    private String english;

    private String chinese;

    private String vietnamese;

    private String name;

    private String wordType;

    private String answer;

    private String correctAnswer;

    private String errorType;

    private String iconType;

    private String iconComponent;

    private String categories;

    private String quantifier;

    private String number;

    private String synonym;

    private List<String> categoryList;

    private List<Category> catesList;

    private String grammarRule;

    private String componentName;

    private Boolean useCounterSettings;

    private String topWordStringKanji;

    private String topWordStringKanna;

    private String rubyWords;

    /**
     * @return the topWordStringKanji
     */
    public String getTopWordStringKanji() {
        return topWordStringKanji;
    }

    /**
     * @param topWordStringKanji the topWordStringKanji to set
     */
    public void setTopWordStringKanji(String topWordStringKanji) {
        this.topWordStringKanji = topWordStringKanji;
    }

    /**
     * @return the topWordStringKanna
     */
    public String getTopWordStringKanna() {
        return topWordStringKanna;
    }

    /**
     * @param topWordStringKanna the topWordStringKanna to set
     */
    public void setTopWordStringKanna(String topWordStringKanna) {
        this.topWordStringKanna = topWordStringKanna;
    }

    private String fullFormString;

    private String transformationRule;

    private String sign;

    private String tense;

    private String politeness;

    private String question;


    /**
     * @return the useCounterSettings
     */
    public Boolean getUseCounterSettings() {
        return useCounterSettings;
    }

    /**
     * @param useCounterSettings the useCounterSettings to set
     */
    public void setUseCounterSettings(Boolean useCounterSettings) {
        this.useCounterSettings = useCounterSettings;
    }

    /**
     * @return the fullFormString
     */
    public String getFullFormString() {
        return fullFormString;
    }

    /**
     * @param fullFormString the fullFormString to set
     */
    public void setFullFormString(String fullFormString) {
        this.fullFormString = fullFormString;
    }

    /**
     * @return the transformationRule
     */
    public String getTransformationRule() {
        return transformationRule;
    }

    /**
     * @param transformationRule the transformationRule to set
     */
    public void setTransformationRule(String transformationRule) {
        this.transformationRule = transformationRule;
    }

    /**
     * @return the sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * @param sign the sign to set
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * @return the tense
     */
    public String getTense() {
        return tense;
    }

    /**
     * @param tense the tense to set
     */
    public void setTense(String tense) {
        this.tense = tense;
    }

    /**
     * @return the politeness
     */
    public String getPoliteness() {
        return politeness;
    }

    /**
     * @param politeness the politeness to set
     */
    public void setPoliteness(String politeness) {
        this.politeness = politeness;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return the componentName
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * @param componentName the componentName to set
     */
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    /**
     * @return the grammarRule
     */
    public String getGrammarRule() {
        return grammarRule;
    }

    /**
     * @param grammarRule the grammarRule to set
     */
    public void setGrammarRule(String grammarRule) {
        this.grammarRule = grammarRule;
    }

    /**
     * @return the showRomanj
     */
    public List<String> getShowRomanj() {
        return showRomanj;
    }

    /**
     * @param showRomanj
     *            the showRomanj to set
     */
    public void setShowRomanj(List<String> showRomanj) {
        this.showRomanj = showRomanj;
    }

    /**
     * @return the wordshow
     */
    public String getWordshow() {
        return wordshow;
    }

    /**
     * @param wordshow
     *            the wordshow to set
     */
    public void setWordshow(String wordshow) {
        this.wordshow = wordshow;
    }

    /**
     * @return the wordHint
     */
    public String getWordHint() {
        return wordHint;
    }

    /**
     * @param wordHint
     *            the wordHint to set
     */
    public void setWordHint(String wordHint) {
        this.wordHint = wordHint;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level
     *            the level to set
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * @return the groups
     */
    public Integer getGroups() {
        return groups;
    }

    /**
     * @param groups
     *            the groups to set
     */
    public void setGroups(Integer groups) {
        this.groups = groups;
    }

    /**
     * @return the kana
     */
    public String getKana() {
        return kana;
    }

    /**
     * @param kana
     *            the kana to set
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
     * @param kanji
     *            the kanji to set
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
     * @param romaji
     *            the romaji to set
     */
    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    /**
     * @return the baseKana
     */
    public String getBaseKana() {
        return baseKana;
    }

    /**
     * @param baseKana
     *            the baseKana to set
     */
    public void setBaseKana(String baseKana) {
        this.baseKana = baseKana;
    }

    /**
     * @return the baseKanji
     */
    public String getBaseKanji() {
        return baseKanji;
    }

    /**
     * @param baseKanji
     *            the baseKanji to set
     */
    public void setBaseKanji(String baseKanji) {
        this.baseKanji = baseKanji;
    }

    /**
     * @return the baseRomaji
     */
    public String getBaseRomaji() {
        return baseRomaji;
    }

    /**
     * @param baseRomaji
     *            the baseRomaji to set
     */
    public void setBaseRomaji(String baseRomaji) {
        this.baseRomaji = baseRomaji;
    }

    /**
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return the typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param typeName
     *            the typeName to set
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * @return the picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * @param picture
     *            the picture to set
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * @return the english
     */
    public String getEnglish() {
        return english;
    }

    /**
     * @param english
     *            the english to set
     */
    public void setEnglish(String english) {
        this.english = english;
    }

    /**
     * @return the chinese
     */
    public String getChinese() {
        return chinese;
    }

    /**
     * @param chinese
     *            the chinese to set
     */
    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    /**
     * @return the vietnamese
     */
    public String getVietnamese() {
        return vietnamese;
    }

    /**
     * @param vietnamese
     *            the vietnamese to set
     */
    public void setVietnamese(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the wordType
     */
    public String getWordType() {
        return wordType;
    }

    /**
     * @param wordType
     *            the wordType to set
     */
    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    /**
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer
     *            the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return the correctAnswer
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * @param correctAnswer
     *            the correctAnswer to set
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * @return the errorType
     */
    public String getErrorType() {
        return errorType;
    }

    /**
     * @param errorType
     *            the errorType to set
     */
    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    /**
     * @return the iconType
     */
    public String getIconType() {
        return iconType;
    }

    /**
     * @param iconType
     *            the iconType to set
     */
    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    /**
     * @return the iconComponent
     */
    public String getIconComponent() {
        return iconComponent;
    }

    /**
     * @param iconComponent
     *            the iconComponent to set
     */
    public void setIconComponent(String iconComponent) {
        this.iconComponent = iconComponent;
    }

    /**
     * @return the categories
     */
    public String getCategories() {
        return categories;
    }

    /**
     * @param categories
     *            the categories to set
     */
    public void setCategories(String categories) {
        this.categories = categories;
    }

    /**
     * @return the quantifier
     */
    public String getQuantifier() {
        return quantifier;
    }

    /**
     * @param quantifier
     *            the quantifier to set
     */
    public void setQuantifier(String quantifier) {
        this.quantifier = quantifier;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number
     *            the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the synonym
     */
    public String getSynonym() {
        return synonym;
    }

    /**
     * @param synonym
     *            the synonym to set
     */
    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    /**
     * @return the categoryList
     */
    public List<String> getCategoryList() {
        return categoryList;
    }

    /**
     * @param categoryList
     *            the categoryList to set
     */
    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    /**
     * @return the catesList
     */
    public List<Category> getCatesList() {
        return catesList;
    }

    /**
     * @param catesList
     *            the catesList to set
     */
    public void setCatesList(List<Category> catesList) {
        this.catesList = catesList;
    }

    /**
     * convert list category to string
     *
     * @return the result
     */
    public String categorytoString() {
        String result = "";
        if (categoryList != null) {
            for (String str : categoryList) {
                if (!StringUtils.isEmpty(str)) {
                    result += str + ";";
                }
            }
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    /**
     *
     */
    @Override
    public int compareTo(Object arg0) {
        Word word = (Word) arg0;
        return (this.getId() < word.getId()) ? -1 : 0;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Word [kana=" + kana + ", kanji=" + kanji + ", romaji=" + romaji + ", english=" + english + "]";
    }

    /**
     * @return the rubyWords
     */
    public String getRubyWords() {
        return rubyWords;
    }

    /**
     * @param rubyWords the rubyWords to set
     */
    public void setRubyWords(String rubyWords) {
        this.rubyWords = rubyWords;
    }

}
