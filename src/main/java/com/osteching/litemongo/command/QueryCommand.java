package com.osteching.litemongo.command;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.osteching.litemongo.annotation.Dao;
import com.osteching.litemongo.annotation.Field;

public class QueryCommand extends AbstractCommand {

    private static final Logger logger = LoggerFactory.getLogger(QueryCommand.class);

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

    private Object query(DBObject criteria) {
        DBCursor cr = super.getDbCollection().find(criteria);
        Class<? extends Object> clazz = method.getReturnType();
        if (!clazz.isArray()) { // return single object
            if (1 != cr.size()) {
                logger.warn("{}#{} returns one object", method.getDeclaringClass().getName(),
                                method.getName());
                throw new IllegalStateException("---should return one but return more---");
            } else {
                return getObjectFromDbCursor(cr.next());
            }
        } else { // return collection
            Collection<Object> objs = new ArrayList<Object>(cr.size());
            while (cr.hasNext()) {
                objs.add(getObjectFromDbCursor(cr.next()));
            }
            return objs;
        }
    }

    private Object getObjectFromDbCursor(DBObject dbo) {
        Class<? extends Object> bean = method.getDeclaringClass().getAnnotation(Dao.class).value();
        Object rtn = null;
        try {
            rtn = bean.newInstance();
            java.lang.reflect.Field[] fs = bean.getDeclaredFields();
            for (java.lang.reflect.Field f : fs) {
                Field af = f.getAnnotation(Field.class);
                try {
                    String fgr = "set" + Character.toUpperCase(f.getName().charAt(0))
                                    + f.getName().substring(1);
                    Method mr = bean.getMethod(fgr, new Class[] {}); // TODO set what?
                    mr.invoke(rtn,dbo.get(af.value()));
                } catch (IllegalArgumentException e) {
                    logger.warn(e.getMessage());
                } catch (IllegalAccessException e) {
                    logger.warn(e.getMessage());
                } catch (SecurityException e) {
                    logger.warn(e.getMessage());
                } catch (NoSuchMethodException e) {
                    logger.warn(e.getMessage());
                } catch (InvocationTargetException e) {
                    logger.warn(e.getMessage());
                }
            }
        } catch (InstantiationException e) {
            logger.warn(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.warn(e.getMessage());
        }
        return rtn;
    }
}
