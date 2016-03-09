/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dao.impl;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.dao.LoginDao;
import jp.co.gmo.rs.ghs.model.User;
import jp.co.gmo.rs.ghs.model.UserLog;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * create LoginDaoImpl: insert, edit, update, delete
 *
 * @author ThuyTTT
 *
 */
@Repository
@Transactional
public class LoginDaoImpl implements LoginDao {

    /* Init sessionFactory is null */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * get user by username and password
     *
     * @param userName
     * @param password
     * @return user
     */
    @Override
    public User getUserLogin(String userName, String password) {

        Session session = this.sessionFactory.getCurrentSession();

        User user = null;
        try {
            // Query get lesson
            Query query = session.createQuery("from User where user_name =? and password =? and delete_flg =?");

            // Set parameter
            query.setParameter(0, userName);
            query.setParameter(1, password);
            query.setParameter(2, ConstValues.DELETE_FLAG_VALUES_OFF);
            user = (User) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * insert into user_log table
     *
     * @param userLog
     * @return true/false
     */
    @Override
    public Integer insertInUserLog(UserLog userLog) {

        Session session = this.sessionFactory.getCurrentSession();

        Integer id = null;

        try {
            id = (Integer) session.save(userLog);
        } catch (Exception e) {
            throw e;
        }
        return id;
    }

}