/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.io.Serializable;

/**
 * create callj scenario dto for client practice/test
 *
 * @author DongNSH
 *
 */
public class CallJScenarioDto implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    /* Init calljLessonNo */
    private Integer calljLessonNo;

    /* Init calljQuestionName */
    private String calljQuestionName;

    /* Init calljConceptName */
    private String calljConceptName;

    /**
     * @return the calljLessonNo
     */
    public Integer getCalljLessonNo() {
        return calljLessonNo;
    }

    /**
     * @param calljLessonNo the calljLessonNo to set
     */
    public void setCalljLessonNo(Integer calljLessonNo) {
        this.calljLessonNo = calljLessonNo;
    }

    /**
     * @return the calljQuestionName
     */
    public String getCalljQuestionName() {
        return calljQuestionName;
    }

    /**
     * @param calljQuestionName the calljQuestionName to set
     */
    public void setCalljQuestionName(String calljQuestionName) {
        this.calljQuestionName = calljQuestionName;
    }

    /**
     * @return the calljConceptName
     */
    public String getCalljConceptName() {
        return calljConceptName;
    }

    /**
     * @param calljConceptName the calljConceptName to set
     */
    public void setCalljConceptName(String calljConceptName) {
        this.calljConceptName = calljConceptName;
    }

}
