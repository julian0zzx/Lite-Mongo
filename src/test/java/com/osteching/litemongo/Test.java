package com.osteching.litemongo;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class Test {

    public static void main(String[] args) {
        PersonDao dao = (PersonDao) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                        new Class[] { PersonDao.class }, new InvocationHandler() {

                            @Override
                            public Object invoke(Object arg0, Method arg1, Object[] arg2)
                                            throws Throwable {
                                System.out.println("fucking");
                                return new Person();
                            }
                        });

        Person p = dao.get("123");
        System.out.println(p);

        Object ars = new String[] { "1", "2", "3" };
        System.out.println(ars.getClass());
        System.out.println(args.getClass().isArray());
        if (args.getClass().isArray()) {
            for (int i = 0, n = Array.getLength(ars); i < n; i++) {
                System.out.println(Array.get(ars, i));
            }
        }

        DB db = MongoDbConnection.getConn().getDb();
        DBCollection persons = db.getCollection("person");
        DBObject pp = new BasicDBObject();
        pp.put("name", "Jack");
        pp.put("age", 322);
        pp.put("_id", 2);
        WriteResult wr = persons.update(new BasicDBObject("_id", 2),pp);
        System.out.println(pp.get("_id") + "xxxxxxxxxxxxxxxxx");
        // WriteResult wr = persons.remove(new BasicDBObject("_id", 1));
        // System.out.println(wr.getError());
        // System.out.println(wr.getLastError());
        // System.out.println(wr.getLastError().ok());

        // DBCursor cbcr = persons.find(new BasicDBObject("_id",2));
        // while (cbcr.hasNext()) {
        // DBObject dbo = cbcr.next();
        // System.out.println(dbo.get("name"));
        // System.out.println(dbo.get("age"));
        // }

        // DBObject d = new BasicDBObject("_id", 2);
        // persons.remove(d);
    }
}
