package com.osteching.litemongo.command;

import java.lang.reflect.Method;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


public class UpdateCommand extends AbstractCommand {

    public UpdateCommand(Method method) {
        super(method);
    }

    @Override
    public Object execute() {
        assert method != null && null != args;

        DBObject newOne = super.buildDbObject();
        return update(new BasicDBObject("_id", newOne.get("_id")), newOne);
    }
    
    private Object update(DBObject id, DBObject newOne) {
        super.getDbCollection().update(id, newOne);
        return id.get("_id"); // simple return _id
    }
}
