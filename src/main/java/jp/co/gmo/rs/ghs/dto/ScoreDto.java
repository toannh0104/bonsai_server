/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.io.Serializable;

/**
 * create score dto
 * 
 * @author BaoTQ
 *
 */
public class ScoreDto implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;
    
    /* Init id */
    private Integer id;

    /* Init practiceMemoryScore */
    private Double practiceMemoryScore;
    
    /* Init practiceWritingScore */
    private Double practiceWritingScore;
    
    /* Init practiceConversationScore */
    private Double practiceConversationScore;
    
    /* Init testMemoryScore */
    private Double testMemoryScore;
    
    /* Init testWritingScore */
    private Double testWritingScore;
    
    /* Init testConversationScore */
    private Double testConversationScore;
    
    /* Init score */
    private Double score;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the practiceMemoryScore
     */
    public Double getPracticeMemoryScore() {
        return practiceMemoryScore;
    }

    /**
     * 
     * @param practiceMemoryScore the practiceMemoryScore to set
     */
    public void setPracticeMemoryScore(Double practiceMemoryScore) {
        this.practiceMemoryScore = practiceMemoryScore;
    }

    /**
     * @return the practice_writing_score
     */
    public Double getPracticeWritingScore() {
        return practiceWritingScore;
    }

    /**
     * @param practiceWritingScore the practiceWritingScore to set
     */
    public void setPracticeWritingScore(Double practiceWritingScore) {
        this.practiceWritingScore = practiceWritingScore;
    }

    /**
     * @return the practiceConversationScore
     */
    public Double getPracticeConversationScore() {
        return practiceConversationScore;
    }

    /**
     * @param practiceConversationScore the practiceConversationScore to set
     */
    public void setPracticeConversationScore(Double practiceConversationScore) {
        this.practiceConversationScore = practiceConversationScore;
    }

    /**
     * @return the testMemoryScore
     */
    public Double getTestMemoryScore() {
        return testMemoryScore;
    }

    /**
     * @param testMemoryScore the testMemoryScore to set
     */
    public void setTestMemoryScore(Double testMemoryScore) {
        this.testMemoryScore = testMemoryScore;
    }

    /**
     * @return the testWritingScore
     */
    public Double getTestWritingScore() {
        return testWritingScore;
    }

    /**
     * @param testWritingScore the testWritingScore to set
     */
    public void setTestWritingScore(Double testWritingScore) {
        this.testWritingScore = testWritingScore;
    }

    /**
     * @return the testConversationScore
     */
    public Double getTestConversationScore() {
        return testConversationScore;
    }

    /**
     * @param testConversationScore the testConversationScore to set
     */
    public void setTestConversationScore(Double testConversationScore) {
        this.testConversationScore = testConversationScore;
    }

    /**
     * @return the score
     */
    public Double getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(Double score) {
        this.score = score;
    }

}
