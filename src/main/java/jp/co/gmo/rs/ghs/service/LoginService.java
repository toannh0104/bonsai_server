/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service;

import java.util.Date;

import jp.co.gmo.rs.ghs.dto.UserDto;

/**
 * create login service
 *
 * @author ThuyTTT
 *
 */
public interface LoginService {

    /**
     * get user by username and password
     *
     * @param userName
     * @param password
     * @return userDto
     */
    UserDto getUserLogin(String userName, String password);

    /**
     * insert into user_log table
     *
     * @param userDto
     * @return true/false
     */
    Integer insertInUserLog(UserDto userDto, String uuid, String token, String deviceName, 
            String deviceVersion, String location, Date startTime);

}
