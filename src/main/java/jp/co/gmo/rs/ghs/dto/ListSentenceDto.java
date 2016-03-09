/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Create ListSentenceDto dto
 *
 * @author BaoTQ
 *
 */
public class ListSentenceDto implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

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
