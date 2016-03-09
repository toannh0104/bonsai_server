/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.io.Serializable;
import java.util.List;

/**
 * create out come dto
 * 
 * @author BaoTQ
 *
 */
public class OutComeDto implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    /* Init question */
    private String question;

    /* Init answer */
    private String answer;

    /* Init listIndexError */
    private List<Integer> listIndexError;

    /* Init questionId */
    private String questionId;

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
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return the listIndexError
     */
    public List<Integer> getListIndexError() {
        return listIndexError;
    }

    /**
     * @param listIndexError the listIndexError to set
     */
    public void setListIndexError(List<Integer> listIndexError) {
        this.listIndexError = listIndexError;
    }

    /**
     * @return the questionId
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId the questionId to set
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

}
