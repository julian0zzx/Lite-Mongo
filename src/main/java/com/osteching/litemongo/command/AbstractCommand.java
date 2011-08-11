package com.osteching.litemongo.command;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.osteching.litemongo.MongoDbConnection;
import com.osteching.litemongo.Operator;
import com.osteching.litemongo.annotation.Dao;
import com.osteching.litemongo.annotation.Entity;
import com.osteching.litemongo.annotation.Field;
import com.osteching.litemongo.annotation.Param;
import com.osteching.litemongo.annotation.crud.Insert;
import com.osteching.litemongo.annotation.crud.Update;

public abstract class AbstractCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(AbstractCommand.class);

    protected Method method = null;

    protected Object[] args = null;

    public AbstractCommand(Method method) {
        this.method = method;
    }
    
    public void setArgs(Object[] args) {
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
            logger.warn(method.getDeclaringClass().getName() + "#" + method.getName()
                            + ", parameters should all be annotated");
            throw new IllegalStateException("---Parameters should all be annotated---");
        }
        for (int i = 0, n = args.length; i < n; i++) {
            if (0 != as[i].length) {
                for (int j = 0, m = as[i].length; j < m; j++) {
                    if (Param.class == as[i][j].annotationType()) {
                        Param p = Param.class.cast(as[i][j]);
                        dbo.put(p.value(), Operator.EQ.equals(p.opr()) ? args[i]
                                        : new BasicDBObject(p.opr(), args[i]));
                        break;
                    }
                }
            } else { // MUST be insert and update
                Insert in = method.getAnnotation(Insert.class);
                Update up = method.getAnnotation(Update.class);
                if (null == in && null == up) {
                    logger.warn("if parameters with @Param it should be Insert/Update, but it is not");
                    throw new IllegalStateException("---only Insert/Update can ignore @Param---");
                }
                Object obj = args[0];
                java.lang.reflect.Field[] fs = obj.getClass().getDeclaredFields();
                for (java.lang.reflect.Field f : fs) {
                    Field af = f.getAnnotation(Field.class);
                    try {
                        String fgr = "get" + Character.toUpperCase(f.getName().charAt(0))
                                        + f.getName().substring(1);
                        Method mr = obj.getClass().getMethod(fgr, new Class[0]);
                        dbo.put(af.value(), mr.invoke(obj, new Object[0]));
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
