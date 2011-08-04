package com.osteching.litemongo;

import java.lang.reflect.Method;

import com.osteching.litemongo.annotation.Entity;

public final class Util {
    
    public static String genObjectKey(Object obj) {
        Class<? extends Object> clazz = obj.getClass();
        Entity annoEntity = clazz.getAnnotation(Entity.class);
        if (null == annoEntity) {
            return null;
        }
        return annoEntity.value() + "-" + clazz.getName();
    }
    
    public static String genMethodKey(Method m) {
        return m.getClass().getName() + "-" + m.getName();
    }
    
}
