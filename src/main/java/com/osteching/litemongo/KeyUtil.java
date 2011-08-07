package com.osteching.litemongo;

import java.lang.reflect.Method;

public final class KeyUtil {

    public static String genObjectKey(Class<? extends Object> clazz) {
        return clazz.getName();
    }

    public static String genMethodKey(Method m) {
        return m.getDeclaringClass().getName() + "#" + m.getName();
    }

}
