/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.io.Serializable;
import java.util.Vector;

import jcall.CALL_formInstanceStruct;
import jcall.CALL_formStruct;

/**
 * @author LongVNH
 *
 */
public class FormSettingGrammarDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private transient Vector<CALL_formStruct> formSettingStructs;
    private Vector<CALL_formInstanceStruct> formSettings;
    private Vector<String> formNames;

    public Vector<CALL_formStruct> getFormSettingStructs() {
        return formSettingStructs;
    }

    public void setFormSettingStructs(Vector<CALL_formStruct> formSettingStructs) {
        this.formSettingStructs = formSettingStructs;
    }

    public Vector<CALL_formInstanceStruct> getFormSettings() {
        return formSettings;
    }

    public void setFormSettings(Vector<CALL_formInstanceStruct> formSettings) {
        this.formSettings = formSettings;
    }

    public Vector<String> getFormNames() {
        return formNames;
    }

    public void setFormNames(Vector<String> formNames) {
        this.formNames = formNames;
    }

}
