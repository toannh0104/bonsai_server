/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto.response;

import java.util.List;

import jp.co.gmo.rs.ghs.jcall.entity.WordGHS;

/**
 * Response dto for all sentences
 *
 * @author VinhNgh
 *
 */
public class AllSentencesResponseDto extends BaseResponseDto  {

    private List<List<WordGHS>> result;
    private List<String> nameConceptList;
    private List<String> nameQuestionList;
    private List<Integer> lessonIdList;

    /**
     * Get result
     * @return the result
     */
    public List<List<WordGHS>> getResult() {
        return result;
    }

    /**
     * Set result
     * @param result the result to set
     */
    public void setResult(List<List<WordGHS>> result) {
        this.result = result;
    }

    /**
     * @return the nameConceptList
     */
    public List<String> getNameConceptList() {
        return nameConceptList;
    }

    /**
     * @param nameConceptList the nameConceptList to set
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
     * @param nameQuestionList the nameQuestionList to set
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
