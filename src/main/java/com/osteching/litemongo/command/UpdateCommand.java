package com.osteching.litemongo.command;

import java.lang.reflect.Method;


public class UpdateCommand extends AbstractCommand {

    public UpdateCommand(Method method, Object[] args) {
        super(method, args);
    }

    @Override
    public Object execute() {
        return null;
    }

}
