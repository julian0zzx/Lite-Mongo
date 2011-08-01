package com.osteching.litemongo;

public interface Session<T> {
    
    boolean save(T t);

    boolean update(T t);

    boolean saveOrUpdate(T t);

    boolean delete(T t);
    
}
