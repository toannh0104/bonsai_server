/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service.impl;

import java.util.Date;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.dao.LoginDao;
import jp.co.gmo.rs.ghs.dto.UserDto;
import jp.co.gmo.rs.ghs.model.User;
import jp.co.gmo.rs.ghs.model.UserLog;
import jp.co.gmo.rs.ghs.service.LoginService;
import jp.co.gmo.rs.ghs.util.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * create login service Implement
 *
 * @author ThuyTTT
 *
 */
@Service
public class LoginServiceImpl implements LoginService, UserDetailsService {

    /* Init AdminDao */
    @Autowired
    private LoginDao loginDao;

    /**
     * get user by username and password
     *
     * @param userName
     * @param password
     * @return userDto
     */
    @Override
    public UserDto getUserLogin(String userName, String password) {
        UserDto userResult = null;
        User user = loginDao.getUserLogin(userName, password);
        if (user != null) {
            userResult = new UserDto();
            userResult.setId(user.getId());
            userResult.setUserName(user.getUserName());
            userResult.setPassword(user.getPassword());
            userResult.setDisplayName(user.getDisplayName());
            userResult.setRole(user.getRole());
        }
        return userResult;
    }

    /**
     * insert into user_log table
     *
     * @param userDto
     * @return true/false
     */
    @Override
    public Integer insertInUserLog(UserDto userDto, String uuid, String token, 
            String deviceName, String deviceVersion, String location, Date startTime) {
        UserLog userLog = new UserLog();
        if (userDto.getUserName() != null) {
            userLog.setUserId(userDto.getId());
            userLog.setUserName(userDto.getUserName());
            userLog.setDeviceId(uuid);
            userLog.setToken(token);
            userLog.setCreatedAt(DateUtils.now());
            userLog.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
            userLog.setLogFlg(ConstValues.MODE_LOG_LOGIN);
            userLog.setStartTime(startTime);
            userLog.setDeviceName(deviceName);
            userLog.setDeviceVersion(deviceVersion);
            userLog.setLocation(location);
        }
        return loginDao.insertInUserLog(userLog);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

}
