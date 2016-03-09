/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service;

import jcall.CALL_configDataStruct;
import jcall.CALL_database;

/**
 * create interface BaseService
 *
 * @author LongVNH
 *
 */
public interface BaseService {
    CALL_database DB = new CALL_database();
    CALL_configDataStruct CONFIG_DSTRUCT = new CALL_configDataStruct();
    CALL_configDataStruct GCONFIG_DSTRUCT = new CALL_configDataStruct();

    /**
     * check logic
     */
    void checkLogic();
}
