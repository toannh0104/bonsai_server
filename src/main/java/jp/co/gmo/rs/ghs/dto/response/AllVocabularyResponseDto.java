/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto.response;

import java.util.List;

import jp.co.gmo.rs.ghs.jcall.entity.Word;

/**
 * Response dto for all vocabulary
 * 
 * @author VinhNgh
 *
 */
public class AllVocabularyResponseDto extends BaseResponseDto {

    private List<Word> result;
    private List<String> listCategory;

    /**
     * Get result
     * @return the result
     */
    public List<Word> getResult() {
        return result;
    }

    /**
     * Set result
     * @param result the result to set
     */
    public void setResult(List<Word> result) {
        this.result = result;
    }

    /**
     * @return the listCategory
     */
    public List<String> getListCategory() {
        return listCategory;
    }

    /**
     * @param listCategory the listCategory to set
     */
    public void setListCategory(List<String> listCategory) {
        this.listCategory = listCategory;
    }

}
