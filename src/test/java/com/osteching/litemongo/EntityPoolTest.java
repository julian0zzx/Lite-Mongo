package com.osteching.litemongo;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class EntityPoolTest {

    @Before
    public void setUp() throws Exception {
        EntityPool.put(Person.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetMethods() {
        Map<String, Method> mm = EntityPool.getMethods(Person.class);
        assertTrue(mm.containsKey("setName"));
        assertTrue(mm.containsKey("getAge"));
    }

}
