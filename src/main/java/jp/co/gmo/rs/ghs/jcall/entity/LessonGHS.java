/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.jcall.entity;

import java.io.Serializable;
import java.util.List;

/**
 * controller in admin page
 *
 * @author BaoTQ
 *
 */
public class LessonGHS implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<List<WordGHS>> wordList;
    private List<String> nameConceptList;
    private List<String> nameQuestionList;
    private List<Integer> lessonIdList;

    /**
     * @return the wordList
     */
    public List<List<WordGHS>> getWordList() {
        return wordList;
    }

    /**
     * @param wordList
     *            the wordList to set
     */
    public void setWordList(List<List<WordGHS>> wordList) {
        this.wordList = wordList;
    }

    /**
     * @return the nameConceptList
     */
    public List<String> getNameConceptList() {
        return nameConceptList;
    }

    /**
     * @param nameConceptList
     *            the nameConceptList to set
     */
    public void setNameConceptList(List<String> nameConceptList) {
        this.nameConceptList = nameConceptList;
    }

    /**
     * @return the nameQuestionList
     */
    public List<String> getNameQuestionList() {
        return nameQuestionList;
    }

    /**
     * @param nameQuestionList
     *            the nameQuestionList to set
     */
    public void setNameQuestionList(List<String> nameQuestionList) {
        this.nameQuestionList = nameQuestionList;
    }

    /**
     * @return the lessonIdList
     */
    public List<Integer> getLessonIdList() {
        return lessonIdList;
    }

    /**
     * @param lessonIdList the lessonIdList to set
     */
    public void setLessonIdList(List<Integer> lessonIdList) {
        this.lessonIdList = lessonIdList;
    }

}
