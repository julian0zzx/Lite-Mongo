package com.osteching.litemongo.command;

import java.lang.reflect.Method;

import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class DeleteCommand extends AbstractCommand {

    public DeleteCommand(Method method, Object[] args) {
        super(method, args);
    }

    @Override
    public Object execute() {
        assert method != null && null != args;

        return delete(buildDbObject());
    }

    private boolean delete(DBObject arg) {
        WriteResult wr = getDbCollection().remove(arg);
        return null == wr.getError();
    }

}
