package com.osteching.litemongo;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public final class MongoDbConnection {

    private static final Logger logger = LoggerFactory.getLogger(MongoDbConnection.class);

    private Mongo mongo = null;

    private DB db = null;

    private MongoDbConnection() {
        try {
            logger.debug("get MongoDB connection");
            mongo = new Mongo(Conf.getInstance().getHost(), Conf.getInstance().getPort());
            db = mongo.getDB(Conf.getInstance().getDB());
            if (null != Conf.getInstance().getUsername()
                            && 0 != Conf.getInstance().getUsername().trim().length()
                            && null != Conf.getInstance().getPassword()
                            && 0 != Conf.getInstance().getPassword().trim().length()) {
                boolean authed = db.authenticate(Conf.getInstance().getUsername(), Conf
                                .getInstance().getPassword().toCharArray());
                if (!authed) {
                    logger.warn("authentication failed");
                    throw new IllegalArgumentException(
                                    "---authentication with given username and password failed---");
                }
            }
        } catch (UnknownHostException e) {
            logger.warn(e.getMessage());
        } catch (MongoException e) {
            logger.warn(e.getMessage());
        }
    }

    private static final class Holder {
        private static final MongoDbConnection _conn = new MongoDbConnection();
    }

    public static MongoDbConnection getConn() {
        return Holder._conn;
    }

    public DB getDb() {
        return db;
    }

    public void close() {
        if (null != db) {
            db.cleanCursors(true);
        }
        if (null != mongo) {
            mongo.close();
        }
    }

    // TODO other util method in Mongo

}
