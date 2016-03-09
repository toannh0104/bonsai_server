/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * create DataConfig
 *
 * @author ThuyTTT
 *
 */
public class DataConfig {
    private static Properties property;

    /**
     * init unit
     */
    public DataConfig() {
        unit();
    }
    /**
     * check operator system and get file properties
     */
    private void unit() {
        InputStream inputStream = getClass().getResourceAsStream("/app.properties");
        try {
            property = new Properties();
            property.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @return the property
     */
    public static Properties getProperty() {
        return property;
    }

    /**
     * @param property the property to set
     */
    public static void setProperty(Properties property) {
        DataConfig.property = property;
    }

}
