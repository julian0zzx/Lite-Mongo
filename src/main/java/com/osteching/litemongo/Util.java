package com.osteching.litemongo;

import com.osteching.litemongo.annotation.Entity;

public final class Util {
    
    public static String getKey(Object obj) {
        Class<? extends Object> plazz = obj.getClass();
        Entity annoEntity = plazz.getAnnotation(Entity.class);
        if (null == annoEntity) {
            return null;
        }
        return annoEntity.collection() + "-" + plazz.getName();
    }
    
}
