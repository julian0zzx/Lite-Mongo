package com.osteching.litemongo.command;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osteching.litemongo.KeyUtil;
import com.osteching.litemongo.annotation.Dao;
import com.osteching.litemongo.annotation.crud.Delete;
import com.osteching.litemongo.annotation.crud.Insert;
import com.osteching.litemongo.annotation.crud.Query;
import com.osteching.litemongo.annotation.crud.Update;

public final class CommandFactory {

    private static final Logger logger = LoggerFactory.getLogger(CommandFactory.class);

    private static ConcurrentHashMap<String, Command> _cmdPool = new ConcurrentHashMap<String, Command>();

    private CommandFactory() {
    }

    public synchronized static Command getCommand(Method m) {
        Class<? extends Object> clazz = m.getDeclaringClass();
        logger.debug("get command according to " + clazz.getName() + "#" + m.getName());
        Dao daoAnn = clazz.getAnnotation(Dao.class);
        if (null == daoAnn) {
            logger.warn(clazz.getName() + "#" + m.getName()
                            + " is illegal for the interface without @Dao");
            throw new java.lang.IllegalArgumentException("---Illegal interface without @Dao---");
        }

        Command cmd = null;

        String key = KeyUtil.genMethodKey(m);
        if (_cmdPool.containsKey(key)) {
            cmd = _cmdPool.get(key);
            return cmd;
        } else {
            Annotation[] anns = m.getAnnotations();
            for (Annotation a : anns) {
                logger.debug("initialize " + clazz.getName() + "#" + m.getName());
                if (a instanceof Delete) {
                    cmd = new DeleteCommand(m);
                } else if (a instanceof Insert) {
                    cmd = new InsertCommand(m);
                } else if (a instanceof Query) {
                    cmd = new QueryCommand(m);
                } else if (a instanceof Update) {
                    cmd = new UpdateCommand(m);
                } else {
                    logger.debug(clazz.getName() + "#" + m.getName()
                                    + " without @Delete/@Insert/@Query/@Update");
                }
                if (null != cmd) break;
            }
            _cmdPool.put(key, cmd);
            return cmd;
        }

    }

}
