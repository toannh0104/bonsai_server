/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author LongVNH
 *
 */
public class CategoryOrWord implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<String> category;
    private List<String> word;
    private boolean flag;

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getWord() {
        return word;
    }

    public void setWord(List<String> word) {
        this.word = word;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
