package com.osteching.litemongo;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DaoFactoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGet() {
        DaoScanner.scan(PersonDao.class);
        PersonDao dao = DaoFactory.get(PersonDao.class);
        assertNotNull(dao);
    }

}
