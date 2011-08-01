package com.osteching.litemongo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public final class MongoDbConnection {
    
    private Mongo mongo = null;

    private DB db = null;

    private MongoDbConnection() {
        try {
            mongo = new Mongo(Conf.getInstance().getHost(), Conf.getInstance().getPort());
            db = mongo.getDB(Conf.getInstance().getDB());
            if (null!=Conf.getInstance().getUsername() 
                    && 0 != Conf.getInstance().getUsername().trim().length() 
                    && null != Conf.getInstance().getPassword() 
                    && 0 != Conf.getInstance().getPassword().trim().length()) {
                boolean authed = db.authenticate(Conf.getInstance().getUsername(), Conf.getInstance().getPassword().toCharArray());
                if (!authed) {
                    throw new IllegalArgumentException("---authentication with given username and password failed---");
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
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
