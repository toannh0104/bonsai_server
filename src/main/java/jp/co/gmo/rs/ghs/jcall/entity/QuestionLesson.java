/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.jcall.entity;

import java.util.List;

/**
 * QuestionLesson
 *
 * @author LongVNH
 *
 */
public class QuestionLesson {
    private String type;

    private double weight;

    private List<ConceptQuestion> conceptQuestionList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<ConceptQuestion> getConceptQuestionList() {
        return conceptQuestionList;
    }

    public void setConceptQuestionList(List<ConceptQuestion> conceptQuestionList) {
        this.conceptQuestionList = conceptQuestionList;
    }
}
