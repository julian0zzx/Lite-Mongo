package com.osteching.litemongo;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osteching.litemongo.annotation.Entity;

public final class EntityPool {
    private EntityPool() {
    }

    private static final Logger logger = LoggerFactory.getLogger(EntityPool.class);

    private static Map<String, Object> _pool = new ConcurrentHashMap<String, Object>();

    private static Map<String, Field[]> _fieldPool = new ConcurrentHashMap<String, Field[]>();

    private static Map<String, String> _collectionPool = new ConcurrentHashMap<String, String>();
    
    public static void put(Class<? extends Object> plazz) {
        Entity annoEntity = plazz.getAnnotation(Entity.class);
        if (null == annoEntity) {
            throw new IllegalArgumentException("---target entity MUST be annotated by "
                            + Entity.class.getName() + "---");
        }
        String key = KeyUtil.genObjectKey(plazz);
        if (_pool.containsKey(key)) {
            throw new IllegalArgumentException("---duplicated entity - " + key + "---");
        }

        logger.debug(key + " was pooled");
        _pool.put(key, plazz);
        _fieldPool.put(key, plazz.getFields());
        _collectionPool.put(key, annoEntity.value());
    }

    public static Object get(String key) {
        logger.debug("get " + key);
        return _pool.get(key);
    }

    public static Field[] getFields(String key) {
        logger.debug("get " + key);
        return _fieldPool.get(key);
    }

    public static String getCollection(String key) {
        logger.debug("get " + key);
        return _collectionPool.get(key);
    }
}
