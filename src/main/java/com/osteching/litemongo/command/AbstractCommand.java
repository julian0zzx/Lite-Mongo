package com.osteching.litemongo.command;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.osteching.litemongo.MongoDbConnection;
import com.osteching.litemongo.Operator;
import com.osteching.litemongo.annotation.Dao;
import com.osteching.litemongo.annotation.Entity;
import com.osteching.litemongo.annotation.Param;

public abstract class AbstractCommand implements Command {

    protected Method method = null;

    protected Object[] args = null;

    public AbstractCommand(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    protected DBCollection getDbCollection() {
        Dao daoAnn = method.getDeclaringClass().getAnnotation(Dao.class);
        Entity entAnn = daoAnn.value().getAnnotation(Entity.class);
        String collection = entAnn.value();
        return MongoDbConnection.getConn().getDb().getCollection(collection);
    }

    protected DBObject buildDbObject() {
        BasicDBObject dbo = new BasicDBObject();
        Annotation[][] as = method.getParameterAnnotations();
        if (as.length != args.length) {
            throw new IllegalStateException("---Parameters should be annotated---");
        }
        for (int i = 0, n = args.length; i < n; i++) {
            for (int j = 0, m = as[i].length; j < m; j++) {
                if (Param.class == as[i][j].annotationType()) {
                    Param p = Param.class.cast(as[i][j]);
                    dbo.put(p.value(),
                                    Operator.EQ.equals(p.opr()) ? args[i] : new BasicDBObject(p
                                                    .opr(), args[i]));
                    break;
                }
            }
        }
        return dbo;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractCommand)) {
            return false;
        }

        AbstractCommand that = (AbstractCommand) obj;
        return this.method.equals(that.method) && Arrays.deepEquals(this.args, that.args);
    }

    @Override
    public int hashCode() {
        return method.hashCode() * 37 + args.hashCode() * 107;
    }
}
