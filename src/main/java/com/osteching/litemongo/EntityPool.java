package com.osteching.litemongo;

import java.lang.reflect.Method;
import java.util.Collections;
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

    // private static Map<String, Field[]> _fieldPool = new ConcurrentHashMap<String, Field[]>();

    // private static Map<String, String> _collectionPool = new ConcurrentHashMap<String, String>();

    private static Map<Class<? extends Object>, Map<String, Method>> _methodPool = new ConcurrentHashMap<Class<? extends Object>, Map<String, Method>>();

    public static void put(Class<? extends Object> plazz) {
        Entity annoEntity = plazz.getAnnotation(Entity.class);
        if (null == annoEntity) {
            logger.warn("target entity({}) MUST be annotated by {}", plazz.getName(),
                            Entity.class.getName());
            throw new IllegalArgumentException("---target entity MUST be annotated by "
                            + Entity.class.getName() + "---");
        }
        String key = KeyUtil.genObjectKey(plazz);
        if (_pool.containsKey(key)) {
            logger.warn("duplicated entity by {} in the pool", key);
            throw new IllegalArgumentException("---duplicated entity - " + key + "---");
        }

        logger.debug("{} was pooled", key);
        _pool.put(key, plazz);
        _methodPool.put(plazz, extractMethods(plazz));
        // _fieldPool.put(key, plazz.getFields());
        // _collectionPool.put(key, annoEntity.value());
    }

    // public static Object get(String key) {
    // logger.debug("get bean by {}", key);
    // return _pool.get(key);
    // }

    // public static Field[] getFields(String key) {
    // logger.debug("get field by {}", key);
    // return _fieldPool.get(key);
    // }

    private static Map<String, Method> extractMethods(Class<? extends Object> clazz) {
        Method[] ms = clazz.getDeclaredMethods();
        if (null != ms && ms.length > 0) {
            Map<String, Method> mm = new ConcurrentHashMap<String, Method>();
            for (Method m : ms) {
                mm.put(m.getName(), m);
            }
            return mm;
        }
        return Collections.emptyMap();
    }

    public static Map<String, Method> getMethods(Class<? extends Object> clazz) {
        return _methodPool.get(clazz);
    }

    // public static String getCollection(String key) {
    // logger.debug("get collection by {}", key);
    // return _collectionPool.get(key);
    // }
}
