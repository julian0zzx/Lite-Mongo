package com.osteching.litemongo;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osteching.litemongo.annotation.Dao;

public class DaoScanner {

    private static final Logger logger = LoggerFactory.getLogger(DaoScanner.class);

    private Collection<Class<? extends Object>> entities = null;

    public DaoScanner() {
        entities = new ArrayList<Class<? extends Object>>();
    }

    public DaoScanner(Collection<Class<? extends Object>> c) {
        entities = new ArrayList<Class<? extends Object>>();
        entities.addAll(c);
    }

    public void scan() {
        for (Class<? extends Object> e : entities) {
            scan(e);
        }
    }

    public void scan(Class<? extends Object> clazz) {
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
