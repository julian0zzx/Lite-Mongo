package com.osteching.litemongo.command;

import java.lang.reflect.Method;

public final class CommandFactory {
    private CommandFactory() {
    }
    
    public static Command getCommand(Method m, Object[] args) {
        return null;
    }
}
