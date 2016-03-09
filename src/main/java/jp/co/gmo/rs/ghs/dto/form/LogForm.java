/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto.form;


/**
 * log form
 *
 * @author ThuyTTT
 *
 */
public class LogForm {

    private String terminal;
    private String userNameLogin;
    private String userNameData;
    private String location;
    private String loginPeriodFrom;
    private String loginPeriodTo;
    private String modeLearning;
    private Integer lessonNo;
    private String course;
    private String modePracticeOrTest;
    private String modeSkill;
    private Integer currentPageData;
    private Integer currentPageLogin;

    /**
     * @return the terminal
     */
    public String getTerminal() {
        return terminal;
    }

    /**
     * @param terminal the terminal to set
     */
    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the loginPeriodFrom
     */
    public String getLoginPeriodFrom() {
        return loginPeriodFrom;
    }

    /**
     * @param loginPeriodFrom the loginPeriodFrom to set
     */
    public void setLoginPeriodFrom(String loginPeriodFrom) {
        this.loginPeriodFrom = loginPeriodFrom;
    }

    /**
     * @return the loginPeriodTo
     */
    public String getLoginPeriodTo() {
        return loginPeriodTo;
    }

    /**
     * @param loginPeriodTo the loginPeriodTo to set
     */
    public void setLoginPeriodTo(String loginPeriodTo) {
        this.loginPeriodTo = loginPeriodTo;
    }

    /**
     * @return the modeLearning
     */
    public String getModeLearning() {
        return modeLearning;
    }

    /**
     * @param modeLearning
     *            the modeLearning to set
     */
    public void setModeLearning(String modeLearning) {
        this.modeLearning = modeLearning;
    }

    /**
     * @return the lessonNo
     */
    public Integer getLessonNo() {
        return lessonNo;
    }

    /**
     * @param lessonNo
     *            the lessonNo to set
     */
    public void setLessonNo(Integer lessonNo) {
        this.lessonNo = lessonNo;
    }

    /**
     * @return the course
     */
    public String getCourse() {
        return course;
    }

    /**
     * @param course
     *            the course to set
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     * @return the modePracticeOrTest
     */
    public String getModePracticeOrTest() {
        return modePracticeOrTest;
    }

    /**
     * @param modePracticeOrTest
     *            the modePracticeOrTest to set
     */
    public void setModePracticeOrTest(String modePracticeOrTest) {
        this.modePracticeOrTest = modePracticeOrTest;
    }

    /**
     * @return the modeSkill
     */
    public String getModeSkill() {
        return modeSkill;
    }

    /**
     * @param modeSkill
     *            the modeSkill to set
     */
    public void setModeSkill(String modeSkill) {
        this.modeSkill = modeSkill;
    }

    /**
     * @return the userNameLogin
     */
    public String getUserNameLogin() {
        return userNameLogin;
    }

    /**
     * @param userNameLogin the userNameLogin to set
     */
    public void setUserNameLogin(String userNameLogin) {
        this.userNameLogin = userNameLogin;
    }

    /**
     * @return the userNameData
     */
    public String getUserNameData() {
        return userNameData;
    }

    /**
     * @param userNameData the userNameData to set
     */
    public void setUserNameData(String userNameData) {
        this.userNameData = userNameData;
    }

    /**
     * @return the currentPageData
     */
    public Integer getCurrentPageData() {
        return currentPageData;
    }

    /**
     * @param currentPageData the currentPageData to set
     */
    public void setCurrentPageData(Integer currentPageData) {
        this.currentPageData = currentPageData;
    }

    /**
     * @return the currentPageLogin
     */
    public Integer getCurrentPageLogin() {
        return currentPageLogin;
    }

    /**
     * @param currentPageLogin the currentPageLogin to set
     */
    public void setCurrentPageLogin(Integer currentPageLogin) {
        this.currentPageLogin = currentPageLogin;
    }

}
