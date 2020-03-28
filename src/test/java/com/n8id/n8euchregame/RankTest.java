/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchregame;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author jsweetman
 */
public class RankTest extends TestCase {
    
    public RankTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(RankTest.class);
        return suite;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getName method, of class Rank.
     */
    public void testGetName() {
        System.out.println("getName");
        Rank instance = Rank.ACE;
        String expResult = "ACE";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class Rank.
     */
    public void testGetValue() {
        System.out.println("getValue");
        Rank instance = Rank.NINE;
        int expResult = 1;
        int result = instance.getValue();
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class Rank.
     */
    public void testGetValue2() {
        System.out.println("getValue2");
        Rank instance = Rank.JACK;
        int expResult = 3;
        int result = instance.getValue();
        assertEquals(expResult, result);
    }
}
