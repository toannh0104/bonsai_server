/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.jcall.entity;

/**
 * MaintainForm
 *
 * @author LongVNH
 *
 */
public class MaintainForm {
    private String startTime;

    private String endTime;

    private boolean maintain;

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the maintain
     */
    public boolean isMaintain() {
        return maintain;
    }

    /**
     * @param maintain the maintain to set
     */
    public void setMaintain(boolean maintain) {
        this.maintain = maintain;
    }




}
