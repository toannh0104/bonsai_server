/*
 * Copyright (C) 2015 by GMO Runsystem Company Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.util.ArrayList;
import java.util.List;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.dto.response.BaseResponseDto;

/**
 * PracticeTestDto
 * 
 * @author LongVNH
 * 
 */
public class PracticeTestDto extends BaseResponseDto {

    /* Init pageLimit */
    private Integer pageLimit;

    /* Init sizePerPage */
    private Integer sizePerPage;

    /* Init scenarioList */
    private List<List<String>> scenarioList;

    private List<List<String>> element;

    /* Init scenarioShowList */
    private List<List<String>> scenarioShowList;

    /* Init scenarioRubyList */
    private List<List<String>> scenarioRubyList;

    /* Init scenarioJSonList */
    private List<List<String>> scenarioJSonList;

    /* Init vocabularyList */
    private List<List<String>> vocabularyList;

    /* Init vocabularyShowList */
    private List<List<String>> vocabularyShowList;

    /* Init vocabularyRubyList */
    private List<List<String>> vocabularyRubyList;

    /* Init vocabularyJSonList */
    private List<List<String>> vocabularyJSonList;

    /* Init memoryScenarioAnswerList */
    private List<List<List<String>>> memoryScenarioAnswerList;

    /* Init memoryVocabularyAnswerList */
    private List<List<List<String>>> memoryVocabularyAnswerList;

    /* Init practiceTest */
    private String practiceTest;

    /* Init memoryWritingConversation */
    private String memoryWritingConversation;

    /* Init pageIndex */
    private Integer pageIndex;

    /* Init pageIndex */
    private Integer lessonType;

    /* Init pageIndexPracticeMemory */
    private Integer pageIndexPracticeMemory;

    /* Init pageIndexPracticeWriting */
    private Integer pageIndexPracticeWriting;

    /* Init pageIndexPracticeConversation */
    private Integer pageIndexPracticeConversation;

    /* Init pageIndexPracticeMemoryVocabulary */
    private Integer pageIndexPracticeMemoryVocabulary;

    /* Init pageIndexPracticeWritingVocabulary */
    private Integer pageIndexPracticeWritingVocabulary;

    /* Init pageIndexPracticeConversationVocabulary */
    private Integer pageIndexPracticeConversationVocabulary;

    /* Init start time */
    private String startTime;

    /**
     * PracticeTestDto constructor
     */
    public PracticeTestDto() {
        sizePerPage = ConstValues.PageDefault.SIZE_PER_PAGE;
        pageLimit = ConstValues.PageDefault.PAGE_START;
        scenarioList = new ArrayList<List<String>>();
        scenarioShowList = new ArrayList<List<String>>();
        scenarioRubyList = new ArrayList<List<String>>();
        scenarioJSonList = new ArrayList<List<String>>();
        vocabularyList = new ArrayList<List<String>>();
        vocabularyShowList = new ArrayList<List<String>>();
        vocabularyRubyList = new ArrayList<List<String>>();
        vocabularyJSonList = new ArrayList<List<String>>();
        memoryScenarioAnswerList = new ArrayList<List<List<String>>>();
        memoryVocabularyAnswerList = new ArrayList<List<List<String>>>();
        element = new ArrayList<List<String>>();
    }

    /**
     * @return the pageLimit
     */
    public Integer getPageLimit() {
        return pageLimit;
    }

    /**
     * @param pageLimit
     *            the pageLimit to set
     */
    public void setPageLimit(Integer pageLimit) {
        this.pageLimit = pageLimit;
    }

    /**
     * @return the sizePerPage
     */
    public Integer getSizePerPage() {
        return sizePerPage;
    }

    /**
     * @param sizePerPage
     *            the sizePerPage to set
     */
    public void setSizePerPage(Integer sizePerPage) {
        this.sizePerPage = sizePerPage;
    }

    /**
     * @return the scenarioList
     */
    public List<List<String>> getScenarioList() {
        return scenarioList;
    }

    /**
     * @param scenarioList
     *            the scenarioList to set
     */
    public void setScenarioList(List<List<String>> scenarioList) {
        this.scenarioList = scenarioList;
    }

    /**
     * @return the scenarioShowList
     */
    public List<List<String>> getScenarioShowList() {
        return scenarioShowList;
    }

    /**
     * @param scenarioShowList
     *            the scenarioShowList to set
     */
    public void setScenarioShowList(List<List<String>> scenarioShowList) {
        this.scenarioShowList = scenarioShowList;
    }

    /**
     * @return the scenarioRubyList
     */
    public List<List<String>> getScenarioRubyList() {
        return scenarioRubyList;
    }

    /**
     * @param scenarioRubyList
     *            the scenarioRubyList to set
     */
    public void setScenarioRubyList(List<List<String>> scenarioRubyList) {
        this.scenarioRubyList = scenarioRubyList;
    }

    /**
     * @return the scenarioJSonList
     */
    public List<List<String>> getScenarioJSonList() {
        return scenarioJSonList;
    }

    /**
     * @param scenarioJSonList
     *            the scenarioJSonList to set
     */
    public void setScenarioJSonList(List<List<String>> scenarioJSonList) {
        this.scenarioJSonList = scenarioJSonList;
    }

    /**
     * @return the vocabularyList
     */
    public List<List<String>> getVocabularyList() {
        return vocabularyList;
    }

    /**
     * @param vocabularyList
     *            the vocabularyList to set
     */
    public void setVocabularyList(List<List<String>> vocabularyList) {
        this.vocabularyList = vocabularyList;
    }

    /**
     * @return the vocabularyShowList
     */
    public List<List<String>> getVocabularyShowList() {
        return vocabularyShowList;
    }

    /**
     * @param vocabularyShowList
     *            the vocabularyShowList to set
     */
    public void setVocabularyShowList(List<List<String>> vocabularyShowList) {
        this.vocabularyShowList = vocabularyShowList;
    }

    /**
     * @return the vocabularyRubyList
     */
    public List<List<String>> getVocabularyRubyList() {
        return vocabularyRubyList;
    }

    /**
     * @param vocabularyRubyList
     *            the vocabularyRubyList to set
     */
    public void setVocabularyRubyList(List<List<String>> vocabularyRubyList) {
        this.vocabularyRubyList = vocabularyRubyList;
    }

    /**
     * @return the vocabularyJSonList
     */
    public List<List<String>> getVocabularyJSonList() {
        return vocabularyJSonList;
    }

    /**
     * @param vocabularyJSonList
     *            the vocabularyJSonList to set
     */
    public void setVocabularyJSonList(List<List<String>> vocabularyJSonList) {
        this.vocabularyJSonList = vocabularyJSonList;
    }

    /**
     * @return the memoryScenarioAnswerList
     */
    public List<List<List<String>>> getMemoryScenarioAnswerList() {
        return memoryScenarioAnswerList;
    }

    /**
     * @param memoryScenarioAnswerList
     *            the memoryScenarioAnswerList to set
     */
    public void setMemoryScenarioAnswerList(List<List<List<String>>> memoryScenarioAnswerList) {
        this.memoryScenarioAnswerList = memoryScenarioAnswerList;
    }

    /**
     * @return the memoryVocabularyAnswerList
     */
    public List<List<List<String>>> getMemoryVocabularyAnswerList() {
        return memoryVocabularyAnswerList;
    }

    /**
     * @param memoryVocabularyAnswerList
     *            the memoryVocabularyAnswerList to set
     */
    public void setMemoryVocabularyAnswerList(List<List<List<String>>> memoryVocabularyAnswerList) {
        this.memoryVocabularyAnswerList = memoryVocabularyAnswerList;
    }

    /**
     * @return the element
     */
    public List<List<String>> getElement() {
        return element;
    }

    /**
     * @param element
     *            the element to set
     */
    public void setElement(List<List<String>> element) {
        this.element = element;
    }

    public String getPracticeTest() {
        return practiceTest;
    }

    public void setPracticeTest(String practiceTest) {
        this.practiceTest = practiceTest;
    }

    public String getMemoryWritingConversation() {
        return memoryWritingConversation;
    }

    public void setMemoryWritingConversation(String memoryWritingConversation) {
        this.memoryWritingConversation = memoryWritingConversation;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getLessonType() {
        return lessonType;
    }

    public void setLessonType(Integer lessonType) {
        this.lessonType = lessonType;
    }

    /**
     * @return the pageIndexPracticeMemory
     */
    public Integer getPageIndexPracticeMemory() {
        return pageIndexPracticeMemory;
    }

    /**
     * @param pageIndexPracticeMemory
     *            the pageIndexPracticeMemory to set
     */
    public void setPageIndexPracticeMemory(Integer pageIndexPracticeMemory) {
        this.pageIndexPracticeMemory = pageIndexPracticeMemory;
    }

    /**
     * @return the pageIndexPracticeWriting
     */
    public Integer getPageIndexPracticeWriting() {
        return pageIndexPracticeWriting;
    }

    /**
     * @param pageIndexPracticeWriting
     *            the pageIndexPracticeWriting to set
     */
    public void setPageIndexPracticeWriting(Integer pageIndexPracticeWriting) {
        this.pageIndexPracticeWriting = pageIndexPracticeWriting;
    }

    /**
     * @return the pageIndexPracticeConversation
     */
    public Integer getPageIndexPracticeConversation() {
        return pageIndexPracticeConversation;
    }

    /**
     * @param pageIndexPracticeConversation
     *            the pageIndexPracticeConversation to set
     */
    public void setPageIndexPracticeConversation(Integer pageIndexPracticeConversation) {
        this.pageIndexPracticeConversation = pageIndexPracticeConversation;
    }

    /**
     * @return the pageIndexPracticeMemoryVocabulary
     */
    public Integer getPageIndexPracticeMemoryVocabulary() {
        return pageIndexPracticeMemoryVocabulary;
    }

    /**
     * @param pageIndexPracticeMemoryVocabulary
     *            the pageIndexPracticeMemoryVocabulary to set
     */
    public void setPageIndexPracticeMemoryVocabulary(Integer pageIndexPracticeMemoryVocabulary) {
        this.pageIndexPracticeMemoryVocabulary = pageIndexPracticeMemoryVocabulary;
    }

    /**
     * @return the pageIndexPracticeWritingVocabulary
     */
    public Integer getPageIndexPracticeWritingVocabulary() {
        return pageIndexPracticeWritingVocabulary;
    }

    /**
     * @param pageIndexPracticeWritingVocabulary
     *            the pageIndexPracticeWritingVocabulary to set
     */
    public void setPageIndexPracticeWritingVocabulary(Integer pageIndexPracticeWritingVocabulary) {
        this.pageIndexPracticeWritingVocabulary = pageIndexPracticeWritingVocabulary;
    }

    /**
     * @return the pageIndexPracticeConversationVocabulary
     */
    public Integer getPageIndexPracticeConversationVocabulary() {
        return pageIndexPracticeConversationVocabulary;
    }

    /**
     * @param pageIndexPracticeConversationVocabulary
     *            the pageIndexPracticeConversationVocabulary to set
     */
    public void setPageIndexPracticeConversationVocabulary(Integer pageIndexPracticeConversationVocabulary) {
        this.pageIndexPracticeConversationVocabulary = pageIndexPracticeConversationVocabulary;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

}
