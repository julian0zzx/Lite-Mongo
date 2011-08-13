package com.osteching.litemongo;

import java.lang.reflect.Method;

public final class KeyUtil {

    public static String genObjectKey(Class<? extends Object> clazz) {
        return clazz.getName();
    }

    public static String genMethodKey(Method m) {
        Class<? extends Object>[] paramTypes = m.getParameterTypes();
        StringBuilder sb = new StringBuilder();
        for (Class<? extends Object> t : paramTypes) {
            sb.append(t.getName()).append(",");
        }
        return m.getDeclaringClass().getName() + "#" + m.getName() + "#" + sb.toString();
    }

}
