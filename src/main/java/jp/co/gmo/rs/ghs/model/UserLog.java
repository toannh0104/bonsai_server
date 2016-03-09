/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * create user mapping
 *
 * @author ThuyTTT
 *
 */
@Entity
@Table(name = "USER_LOG")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserLog implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    /* Init id */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /* Init userId */
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    /* Init userName */
    @Column(name = "user_name", length = 20, nullable = false)
    private String userName;

    /* Init deviceId */
    @Column(name = "device_id", length = 200, nullable = false)
    private String deviceId;

    /* Init deviceName */
    @Column(name = "device_name", length = 50, nullable = false)
    private String deviceName;

    /* Init deviceVersion */
    @Column(name = "device_version", length = 50, nullable = false)
    private String deviceVersion;

    /* Init location */
    @Column(name = "location", length = 100, nullable = false)
    private String location;

    /* Init startTime */
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    /* Init endTime */
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    /* Init modeLearning */
    @Column(name = "mode_learning", length = 50)
    private String modeLearning;

    /* Init lessonNo */
    @Column(name = "lesson_no", length = 10)
    private String lessonNo;

    /* Init modePracticeOrTest */
    @Column(name = "mode_practice_or_test", length = 20)
    private String modePracticeOrTest;

    /* Init modeSkill */
    @Column(name = "mode_skill", length = 20)
    private String modeSkill;

    /* Init course */
    @Column(name = "course", length = 200)
    private String course;

    /* Init scenarioOrWord */
    @Column(name = "scenario_or_word", length = 200)
    private String scenarioOrWord;

    /* Init practiceTimes */
    @Column(name = "practice_times", length = 10)
    private String practiceTimes;

    /* Init answer */
    @Column(name = "answer", length = 500)
    private String answer;

    /* Init score */
    @Column(name = "score")
    private Double score;

    /* Init logFlg */
    @Column(name = "log_flg", length = 1)
    private Integer logFlg;

    /* Init token */
    @Column(name = "token", length = 500)
    private String token;

    /* Init createdAt */
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /* Init updatedAt */
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    /* Init deleteAt */
    @Column(name = "delete_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteAt;

    /* Init deleteFlg */
    @Column(name = "delete_flg", length = 1, nullable = false)
    private Integer deleteFlg;

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
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
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
     * @param userName the userName to set
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
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
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
     * @param updatedAt the updatedAt to set
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
     * @param deleteAt the deleteAt to set
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
     * @param deleteFlg the deleteFlg to set
     */
    public void setDeleteFlg(Integer deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    /**
     * @return the deviceName
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * @param deviceName the deviceName to set
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
     * @param deviceVersion the deviceVersion to set
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
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the modeLearning
     */
    public String getModeLearning() {
        return modeLearning;
    }

    /**
     * @param modeLearning the modeLearning to set
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
     * @param lessonNo the lessonNo to set
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
     * @param modePracticeOrTest the modePracticeOrTest to set
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
     * @param modeSkill the modeSkill to set
     */
    public void setModeSkill(String modeSkill) {
        this.modeSkill = modeSkill;
    }

    /**
     * @return the course
     */
    public String getCourse() {
        return course;
    }

    /**
     * @param course the course to set
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
     * @param scenarioOrWord the scenarioOrWord to set
     */
    public void setScenarioOrWord(String scenarioOrWord) {
        this.scenarioOrWord = scenarioOrWord;
    }

    /**
     * @return the practiceTimes
     */
    public String getPracticeTimes() {
        return practiceTimes;
    }

    /**
     * @param practiceTimes the practiceTimes to set
     */
    public void setPracticeTimes(String practiceTimes) {
        this.practiceTimes = practiceTimes;
    }

    /**
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
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
     * @param score the score to set
     */
    public void setScore(Double score) {
        this.score = score;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the logFlg
     */
    public Integer getLogFlg() {
        return logFlg;
    }

    /**
     * @param logFlg the logFlg to set
     */
    public void setLogFlg(Integer logFlg) {
        this.logFlg = logFlg;
    }
}
