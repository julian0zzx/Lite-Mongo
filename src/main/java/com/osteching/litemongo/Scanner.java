package com.osteching.litemongo;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osteching.litemongo.annotation.Entity;

public class Scanner {
    private static final Logger logger = LoggerFactory.getLogger(Scanner.class);

    private List entities = null;
    
    public Scanner() {
        entities = new ArrayList();
    }

    public Scanner(final int initSize) {
        entities = new ArrayList(initSize);
    }

    public Scanner(Collection c) {
        entities = new ArrayList();
        entities.addAll(c);
    }
    
    public void scan() {
        for (Object e : entities) {
            scan(e);
        }
    }
    
    public void scan(Object entity) {
        Class plazz = entity.getClass();
        logger.debug("scan " + plazz.getName());
        
        Annotation annoEntity = plazz.getAnnotation(Entity.class);
        if (null != annoEntity && annoEntity instanceof Entity) {
            EntityPool.put(entity);
        } else {
            logger.debug("invalid entity " + plazz.getName());
        }
    }
}
