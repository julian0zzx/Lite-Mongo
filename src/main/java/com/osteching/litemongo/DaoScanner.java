package com.osteching.litemongo;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osteching.litemongo.annotation.Dao;

public class DaoScanner {

    private static final Logger logger = LoggerFactory.getLogger(DaoScanner.class);

    public static void scan(Collection<Class<? extends Object>> entities) {
        for (Class<? extends Object> e : entities) {
            scan(e);
        }
    }

    public static void scan(Class<? extends Object> clazz) {
        logger.debug("scan {}", clazz.getName());

        Dao daoAnn = clazz.getAnnotation(Dao.class);
        if (null != daoAnn) {
            DaoFactory.put(clazz);
            EntityPool.put(daoAnn.value());
        } else {
            logger.debug("invalid entity {}", clazz.getName());
        }
    }
}
