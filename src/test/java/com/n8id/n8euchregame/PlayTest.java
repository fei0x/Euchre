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
public class PlayTest extends TestCase {
    
    public PlayTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(PlayTest.class);
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
     * Test of getCard method, of class Play.
     */
    public void testGetCard() {
        System.out.println("getCard");
        Card expResult = new Card(Rank.QUEEN, Suit.SPADES);
        Play instance = new Play("Player1", expResult);
        Card result = instance.getCard();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayer method, of class Play.
     */
    public void testGetPlayer() {
        System.out.println("getPlayer");
        Play instance = new Play("Player1", new Card(Rank.QUEEN, Suit.SPADES));
        String expResult = "Player1";
        String result = instance.getPlayer();
        assertEquals(expResult, result);
    }

    /**
     * Test of clone method, of class Play.
     */
    public void testClone() {
        System.out.println("clone");
        Play instance = new Play("Player1", new Card(Rank.QUEEN, Suit.SPADES));
        Play expResult = instance;
        Play result = instance.clone();
        assertNotSame(expResult, result);
    }

}
