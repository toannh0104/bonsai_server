/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.util.Date;

/**
 * Create UserLogDto dto
 *
 * @author BaoTQ
 *
 */
public class UserLogDto {

    private Integer id;
    private Integer userId;
    private String userName;
    private String deviceId;
    private String deviceName;
    private String deviceVersion;
    private String location;
    private String startTime;
    private String endTime;
    private String modeLearning;
    private String lessonNo;
    private String modePracticeOrTest;
    private String course;
    private String scenarioOrWord;
    private String practiceTime;
    private String answer;
    private Double score;
    private Integer logFlg;
    private String token;
    private Date createdAt;
    private Date updatedAt;
    private Date deleteAt;
    private Integer deleteFlg;
    private String modeSkill;
    private String time;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId
     *            the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the deviceName
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * @param deviceName
     *            the deviceName to set
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * @return the deviceVersion
     */
    public String getDeviceVersion() {
        return deviceVersion;
    }

    /**
     * @param deviceVersion
     *            the deviceVersion to set
     */
    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
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
    public String getLessonNo() {
        return lessonNo;
    }

    /**
     * @param lessonNo
     *            the lessonNo to set
     */
    public void setLessonNo(String lessonNo) {
        this.lessonNo = lessonNo;
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
     * @return the scenarioOrWord
     */
    public String getScenarioOrWord() {
        return scenarioOrWord;
    }

    /**
     * @param scenarioOrWord
     *            the scenarioOrWord to set
     */
    public void setScenarioOrWord(String scenarioOrWord) {
        this.scenarioOrWord = scenarioOrWord;
    }

    /**
     * @return the practiceTime
     */
    public String getPracticeTime() {
        return practiceTime;
    }

    /**
     * @param practiceTime
     *            the practiceTime to set
     */
    public void setPracticeTime(String practiceTime) {
        this.practiceTime = practiceTime;
    }

    /**
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer
     *            the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return the score
     */
    public Double getScore() {
        return score;
    }

    /**
     * @param score
     *            the score to set
     */
    public void setScore(Double score) {
        this.score = score;
    }

    /**
     * @return the logFlg
     */
    public Integer getLogFlg() {
        return logFlg;
    }

    /**
     * @param logFlg
     *            the logFlg to set
     */
    public void setLogFlg(Integer logFlg) {
        this.logFlg = logFlg;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     *            the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the updatedAt
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     *            the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return the deleteAt
     */
    public Date getDeleteAt() {
        return deleteAt;
    }

    /**
     * @param deleteAt
     *            the deleteAt to set
     */
    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
    }

    /**
     * @return the deleteFlg
     */
    public Integer getDeleteFlg() {
        return deleteFlg;
    }

    /**
     * @param deleteFlg
     *            the deleteFlg to set
     */
    public void setDeleteFlg(Integer deleteFlg) {
        this.deleteFlg = deleteFlg;
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
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

}
