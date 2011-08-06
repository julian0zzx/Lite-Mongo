package com.osteching.litemongo.command;

import java.lang.reflect.Method;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.osteching.litemongo.MongoDbConnection;
import com.osteching.litemongo.Operator;
import com.osteching.litemongo.annotation.Dao;
import com.osteching.litemongo.annotation.Entity;
import com.osteching.litemongo.annotation.Param;

public class DeleteCommand extends AbstractCommand {

    public DeleteCommand(Method method, Object[] args) {
        super(method, args);
    }

    @Override
    public Object execute() {
        assert null != args;

        return delete(buildDbObject(args));
    }

    private DBObject buildDbObject(Object[] args) {
        BasicDBObject dbo = new BasicDBObject();
        for (Object o : args) {
            Param pAnn = o.getClass().getAnnotation(Param.class);
            dbo.append(pAnn.value(), Operator.EQ != pAnn.opr() ? new BasicDBObject(pAnn.opr(), o)
                            : o);
        }
        return dbo;
    }

    private boolean delete(DBObject arg) {
        Dao daoAnn = method.getClass().getAnnotation(Dao.class);
        Entity entAnn = daoAnn.value().getAnnotation(Entity.class);

        String collection = entAnn.value();
        DBCollection coll = MongoDbConnection.getConn().getDb().getCollection(collection);

        WriteResult wr = coll.remove(arg);
        return null == wr.getError();
    }

}
