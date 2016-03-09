/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.jcall.entity;

import java.util.List;

/**
 * GrammarFormGroup
 *
 * @author LongVNH
 */
public class GrammarFormGroup {
    private List<GrammarForm> grammarFormList;

    public List<GrammarForm> getGrammarFormList() {
        return grammarFormList;
    }

    public void setGrammarFormList(List<GrammarForm> grammarFormList) {
        this.grammarFormList = grammarFormList;
    }
}
