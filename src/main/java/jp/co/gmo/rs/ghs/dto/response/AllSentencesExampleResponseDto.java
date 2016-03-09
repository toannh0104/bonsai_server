/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto.response;

import java.util.List;

import jp.co.gmo.rs.ghs.dto.SentenceDto;

/**
 * Response dto for all sentences
 * 
 * @author BaoTQ
 *
 */
public class AllSentencesExampleResponseDto extends BaseResponseDto  {

    private List<SentenceDto> result;

    /**
     * @return the result
     */
    public List<SentenceDto> getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(List<SentenceDto> result) {
        this.result = result;
    }
    
}
