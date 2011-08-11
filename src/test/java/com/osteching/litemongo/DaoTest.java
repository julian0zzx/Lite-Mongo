package com.osteching.litemongo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class DaoTest {

    private PersonDao dao = null;

    @BeforeClass
    public static void before() throws Exception {
        new DaoScanner().scan(PersonDao.class);
    }

    @Before
    public void setUp() throws Exception {
        dao = DaoFactory.get(PersonDao.class);
    }

    @After
    public void tearDown() throws Exception {
        dao = null;
    }

    @Test
    public void testDelete() {
        DB db = MongoDbConnection.getConn().getDb();
        DBCollection persons = db.getCollection("person");
        DBObject pp = new BasicDBObject();
        pp.put("name", "Jack");
        pp.put("age", 32);
        pp.put("_id", "2222");
        persons.insert(pp);

        dao.delete("2222");
        DBCursor cr = persons.find(new BasicDBObject("_id", "2222"));
        assertFalse(cr.hasNext());
    }

    @Test
    public void testInsert() {
        dao.insert(new Person("tomm", 123));

        DB db = MongoDbConnection.getConn().getDb();
        DBCollection persons = db.getCollection("person");
        DBObject pp = new BasicDBObject();
        pp.put("name", "tomm");
        pp.put("age", 123);

        DBCursor cr = persons.find(pp);
        assertTrue(cr.hasNext());
    }

    @Test
    public void testUpdate() {
        Person np = new Person("ttttt", 11);
        np.setId("12345678");
        dao.insert(np);
        

    }

}
