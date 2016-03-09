/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dao;

import jp.co.gmo.rs.ghs.model.User;
import jp.co.gmo.rs.ghs.model.UserLog;

/**
 * create LoginDao: insert, edit, update, delete
 *
 * @author ThuyTTT
 *
 */
public interface LoginDao {

    /**
     * get user by username and password
     * 
     * @param userName
     * @param password
     * @return user
     */
    User getUserLogin(String userName, String password);

    /**
     * insert into user_log table
     * 
     * @param userLog
     * @return true/false
     */
    Integer insertInUserLog(UserLog userLog);
}
