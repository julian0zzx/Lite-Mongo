package com.osteching.litemongo;

import static org.junit.Assert.*;

import org.junit.Test;

public class KeyUtilTest {

    @Test
    public void testGenObjectKey() throws Exception {
        assertEquals("com.osteching.litemongo.Person", KeyUtil.genObjectKey(Person.class));
    }

    // use method as key directly 
    // @Test
    public void testGenMethodKey() throws Exception {
        assertEquals("com.osteching.litemongo.PersonDao#delete",
                        KeyUtil.genMethodKey(PersonDao.class.getDeclaredMethod("delete",
                                        new Class[] { String.class })));
    }

}
