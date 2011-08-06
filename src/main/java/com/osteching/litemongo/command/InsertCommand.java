package com.osteching.litemongo.command;

import java.lang.reflect.Method;


public class InsertCommand extends AbstractCommand {

    public InsertCommand(Method method, Object[] args) {
        super(method, args);
    }

    @Override
    public Object execute() {
        return null;
    }

}
