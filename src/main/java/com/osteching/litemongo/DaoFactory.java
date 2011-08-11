package com.osteching.litemongo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.osteching.litemongo.command.Command;
import com.osteching.litemongo.command.CommandFactory;

public final class DaoFactory {
    private DaoFactory() {
    }

    private static Map<Class<?>, Object> _pool = new ConcurrentHashMap<Class<?>, Object>();

    // MUST not be access for outside
    static <T> void put(Class<T> clazz) {
        _pool.put(clazz, Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                        new Class[] { clazz }, new InvocationHandler() {
                            @Override
                            public Object invoke(Object target, Method m, Object[] args)
                                            throws Throwable {
                                Command cmd = CommandFactory.getCommand(m);
                                cmd.setArgs(args);
                                return CommandFactory.getCommand(m).execute();
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
