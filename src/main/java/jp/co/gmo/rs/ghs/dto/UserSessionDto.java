/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto;

import java.io.Serializable;

/**
 * create UserSessionDto
 *
 * @author LongVNH
 *
 */
public class UserSessionDto implements Serializable {

    /* Init serialVersionUID is 1L */
    private static final long serialVersionUID = 1L;

    /* Init userId */
    private Integer userId = null;

    /* Init userName is null */
    private String userName = null;

    /* Init nativeLanguage is null */
    private String nativeLanguage = null;

    /* Init uuid is null */
    private String uuid = null;

    /* Init idLog in table UserLog */
    private Integer idLog = null;

    /* Init deviceName is null */
    private String deviceName = null;

    /* Init deviceVersion is null */
    private String deviceVersion = null;

    /* Init location is null */
    private String location = null;

    /* Init token is null */
    private String token = null;

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
     * @return the nativeLanguage
     */
    public String getNativeLanguage() {
        return nativeLanguage;
    }

    /**
     * @param nativeLanguage the nativeLanguage to set
     */
    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the idLog
     */
    public Integer getIdLog() {
        return idLog;
    }

    /**
     * @param idLog the idLog to set
     */
    public void setIdLog(Integer idLog) {
        this.idLog = idLog;
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

}
