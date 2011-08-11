package com.osteching.litemongo.command;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.osteching.litemongo.PersonDao;

public class CommandFactoryTest {

    private Command dc = null;

    private Method m = null;

    private Object[] args = null;
    
    @Before
    public void setUp() throws Exception {
        m = PersonDao.class.getMethod("delete", new Class[] { String.class });
        args = new Object[] { String.valueOf(1) };
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
    public void testGetCommand() {
        Command dcm = (DeleteCommand)CommandFactory.getCommand(m);
        dcm.setArgs(args);
        assertEquals(dc, dcm);
    }

}
