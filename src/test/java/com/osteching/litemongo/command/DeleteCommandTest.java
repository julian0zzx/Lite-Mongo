package com.osteching.litemongo.command;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.osteching.litemongo.MongoDbConnection;
import com.osteching.litemongo.PersonDao;

public class DeleteCommandTest {

    private Command dc = null;

    private Method m = null;

    private Object[] args = null;

    @Before
    public void setUp() throws Exception {
        DB db = MongoDbConnection.getConn().getDb();
        DBCollection persons = db.getCollection("person");
        DBObject pp = new BasicDBObject();
        pp.put("name", "Jack");
        pp.put("age", 32);
        pp.put("_id", "2");
        persons.insert(pp);

        m = PersonDao.class.getMethod("delete", new Class[] { String.class });
        args = new Object[] { 2 };
        Command dm = new DeleteCommand(m);
        dm.setArgs(args);
        dc = dm;
    }

    @After
    public void tearDown() throws Exception {
        m = null;
        args = null;
        dc = null;
    }

    @Test
    public void testExecute() {
        boolean flag = (Boolean) dc.execute();
        assertTrue(flag);
    }

}
