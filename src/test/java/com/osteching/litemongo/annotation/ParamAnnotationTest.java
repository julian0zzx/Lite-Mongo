package com.osteching.litemongo.annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Test;

import com.osteching.litemongo.PersonDao;
import com.osteching.litemongo.annotation.crud.Delete;

public class ParamAnnotationTest {

    @Test
    public void testDelete() throws Exception {

        Method d = PersonDao.class.getDeclaredMethod("delete", new Class[] { String.class });
        Annotation dAnn = d.getAnnotation(Delete.class);
        assertNotNull(dAnn);

        Annotation[][] as = d.getParameterAnnotations();
        assertEquals(1, as.length);
        assertEquals(1, as[0].length);
        assertEquals(Param.class, as[0][0].annotationType());

    }

    @Test
    public void testParam() throws Exception {

        Method d = PersonDao.class.getDeclaredMethod("delete", new Class[] { String.class });
        Annotation[][] as = d.getParameterAnnotations();
        assertEquals(1, as.length);
        assertEquals(1, as[0].length);
        assertEquals(Param.class, as[0][0].annotationType());
        if (Param.class == as[0][0].annotationType()) {
            Param p = Param.class.cast(as[0][0]);
            assertEquals("_id", p.value());
        }
    }
    
    
    @Test
    public void testParam2() throws Exception {

        Method d = PersonDao.class.getDeclaredMethod("delete", new Class[] { String.class });
        Annotation[][] as = d.getParameterAnnotations();
        assertEquals(1, as.length);
        assertEquals(1, as[0].length);
        assertEquals(Param.class, as[0][0].annotationType());
        if (Param.class == as[0][0].annotationType()) {
            Param p = Param.class.cast(as[0][0]);
            assertEquals("_id", p.value());
        }
    }

}
