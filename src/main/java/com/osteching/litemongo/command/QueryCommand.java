package com.osteching.litemongo.command;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


public class QueryCommand extends AbstractCommand {

    public QueryCommand(Method method) {
        super(method);
    }

    @Override
    public Object execute() {
        assert method != null && null != args;
        
        return query(buildDbObject());
    }

    @Override
    protected DBObject buildDbObject() {
        BasicDBObject dbo = new BasicDBObject();
        Annotation[][] as = method.getParameterAnnotations();
        
        return dbo;
    }
    
    private Collection<? extends Object> query(DBObject criteria) {
        DBCursor cr = super.getDbCollection().find(criteria);
        return null;
    }
}
