package com.osteching.litemongo.annotation;


import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        assertTrue("person".equals(annoEntity.value()));
    }
    
    @Test
    public void testField() throws Exception {
        Class<? extends Person> plazz = p.getClass();
        java.lang.reflect.Field[] fs = plazz.getFields();
        for (java.lang.reflect.Field f : fs) {
            Field annoField = f.getAnnotation(Field.class);
            assertTrue("name".equals(annoField.value()) || "age".equals(annoField.value()));
        }
    }
    
}
