package com.osteching.litemongo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import com.osteching.litemongo.command.CommandFactory;

public final class DaoFactory {
    private DaoFactory() {
    }

    private static ConcurrentHashMap<Class<?>, Object> _pool = new ConcurrentHashMap<Class<?>, Object>();

    private static ConcurrentHashMap<String, Object> _methodPool = new ConcurrentHashMap<String, Object>();

    // MUST not be access for outside
    static <T> void put(Class<T> clazz) {
        _pool.putIfAbsent(clazz, Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                        new Class[] { clazz }, new InvocationHandler() {
                            @Override
                            public Object invoke(Object target, Method m, Object[] args)
                                            throws Throwable {
                                System.out.println("fucking");
                                
                                // command object to query MongoDB
                                
                                return CommandFactory.getCommand(m, args).execute();
                            }
                        }));
    }

    public static <T> T get(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        T obj = (T) _pool.get(clazz);
        return obj;
    }

    public synchronized static <T> T build(Class<T> clazz) {
        put(clazz);
        return get(clazz);
    }

}
