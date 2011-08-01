package com.osteching.litemongo.annotation;


import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.osteching.litemongo.MongoDbConnection;
import com.osteching.litemongo.Person;

public class EntityAnnotationTest {
    
    private Person p = null;
    
    @Before
    public void setUp() throws Exception {
        p = new Person();
    }

    @After
    public void tearDown() throws Exception {
        p = null;
    }

    @Test
    public void testEntity() throws Exception {
        Class<? extends Person> plazz = p.getClass();
        Entity annoEntity = plazz.getAnnotation(Entity.class);
        assertTrue("person".equals(annoEntity.collection()));
    }
    
    @Test
    public void testField() throws Exception {
        Class<? extends Person> plazz = p.getClass();
        java.lang.reflect.Field[] fs = plazz.getFields();
        for (java.lang.reflect.Field f : fs) {
            Field annoField = f.getAnnotation(Field.class);
            assertTrue("name".equals(annoField.key()) || "age".equals(annoField.key()));
        }
    }
    
    @Test
    public void testInsert() throws Exception {
        DB db = MongoDbConnection.getConn().getDb();
        DBCollection persons = db.getCollection("person");
        DBObject p = new BasicDBObject();
        p.put("name", "Jack");
        p.put("age", 32);
        assertTrue(persons.count() > 0);
    }
    
}
