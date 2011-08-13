package com.osteching.litemongo.command;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.DBObject;
import com.osteching.litemongo.PersonDao;

public class QueryCommandTest {

    private QueryCommand qc = null;

    @Before
    public void setUp() throws Exception {
        Method qm = PersonDao.class.getMethod("get", new Class[] { String.class, int.class });
        Object[] args = new Object[] { "tomm", 123 };
        QueryCommand dm = new QueryCommand(qm);
        dm.setArgs(args);
        qc = dm;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testBuildDbObject() {
        DBObject dbo = qc.buildDbObject();
        assertEquals("tomm", dbo.get("name"));
        assertEquals(123, dbo.get("age"));
    }

}
