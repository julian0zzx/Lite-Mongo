package com.osteching.litemongo.command;

import java.lang.reflect.Method;

import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class DeleteCommand extends AbstractCommand {

    public DeleteCommand(Method method) {
        super(method);
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
