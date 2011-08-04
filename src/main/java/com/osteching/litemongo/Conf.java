package com.osteching.litemongo;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Conf {

    private static final Logger logger = LoggerFactory.getLogger(Conf.class);

    private PropertiesConfiguration configuration = null;

    private static final String CONF_FILE = "lite-mongo.properties";
    
    private static final String HOST = "host";
    
    private static final String PORT = "port";
    
    private static final String DB = "db";
    
    private static final String USERNAME = "username";
    
    private static final String PASSWORD = "password";

    private Conf() {
        try {
            configuration = new PropertiesConfiguration(CONF_FILE);
        } catch (ConfigurationException e) {
            logger.error("---read configuration file failed---", e.getMessage());
        }
    }
    
    private static class Holder {
        private static final Conf instance = new Conf();
    }

    public static Conf getInstance() {
        return Holder.instance;
    }
    
    public String getHost() {
        return configuration.getString(HOST, "localhost");
    }
    
    public int getPort() {
        return configuration.getInt(PORT, 27017);
    }

    public String getDB() {
        return configuration.getString(DB, "local");
    }
    
    public String getUsername() {
        return configuration.getString(USERNAME);
    }
    
    public String getPassword() {
        return configuration.getString(PASSWORD);
    }

}
