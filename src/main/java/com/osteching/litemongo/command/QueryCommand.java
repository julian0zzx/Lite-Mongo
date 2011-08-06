package com.osteching.litemongo.command;

import java.lang.reflect.Method;


public class QueryCommand extends AbstractCommand {

    public QueryCommand(Method method, Object[] args) {
        super(method, args);
    }

    @Override
    public Object execute() {
        return null;
    }

}
