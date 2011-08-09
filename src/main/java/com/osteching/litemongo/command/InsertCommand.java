package com.osteching.litemongo.command;

import java.lang.reflect.Method;

import com.mongodb.DBObject;


public class InsertCommand extends AbstractCommand {

    public InsertCommand(Method method, Object[] args) {
        super(method, args);
    }

    @Override
    public Object execute() {
        assert method != null && null != args;

        return insert(buildDbObject());
    }

    private Object insert(DBObject dbo) {
        getDbCollection().insert(dbo);
        return dbo.get("_id"); // always return _id here   
    }
}
