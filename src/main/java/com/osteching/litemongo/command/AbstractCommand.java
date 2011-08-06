package com.osteching.litemongo.command;

import java.lang.reflect.Method;

public abstract class AbstractCommand implements Command {

    protected Method method = null;
    protected Object[] args = null;

    public AbstractCommand(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

}
