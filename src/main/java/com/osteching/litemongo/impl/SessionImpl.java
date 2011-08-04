package com.osteching.litemongo.impl;

import java.lang.reflect.Field;

import com.osteching.litemongo.EntityPool;
import com.osteching.litemongo.Session;
import com.osteching.litemongo.Util;

public class SessionImpl<T> implements Session<T> {

    public SessionImpl() {
    }
    
    @Override
    public boolean save(T t) {
        String key = Util.genObjectKey(t);
        String collection = EntityPool.getCollection(key);
        Field[] fields = EntityPool.getFields(key);
        return false;
    }

    @Override
    public boolean update(T t) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(T t) {
        return false;
    }

    @Override
    public boolean delete(T t) {
        return false;
    }
    
}
