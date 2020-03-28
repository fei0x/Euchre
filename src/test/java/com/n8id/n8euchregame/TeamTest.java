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
public class TeamTest extends TestCase {
    
    public TeamTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(TeamTest.class);
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
     * Test of getPlayerOne method, of class Team.
     */
    public void testGetPlayerOne() {
        System.out.println("getPlayerOne");
        Team instance = new Team("player1", "player2");
        String expResult = "player1";
        String result = instance.getPlayerOne();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayerTwo method, of class Team.
     */
    public void testGetPlayerTwo() {
        System.out.println("getPlayerTwo");
        Team instance = new Team("player1", "player2");
        String expResult = "player2";
        String result = instance.getPlayerTwo();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNames method, of class Team.
     */
    public void testGetNames() {
        System.out.println("getNames");
        Team instance = new Team("player1", "player2");
        String expResult = "player1 & player2";
        String result = instance.getNames();
        assertEquals(expResult, result);
    }

    /**
     * Test of getScore method, of class Team.
     */
    public void testGetScore() {
        System.out.println("getScore");
        Team instance = new Team("player1", "player2");
        instance.increaseScore(3);
        int expResult = 3;
        int result = instance.getScore();
        assertEquals(expResult, result);
    }

    /**
     * Test of increaseScore method, of class Team.
     */
    public void testIncreaseScore() {
        System.out.println("increaseScore");
        int amount = 3;
        Team instance = new Team("player1", "player2");
        boolean expResult = false;
        boolean result = instance.increaseScore(amount);
        assertEquals(expResult, result);
    }

    /**
     * Test of increaseScore method, of class Team.
     */
    public void testIncreaseScore2() {
        System.out.println("increaseScore2");
        int amount = 10;
        Team instance = new Team("player1", "player2");
        boolean expResult = true;
        boolean result = instance.increaseScore(amount);
        assertEquals(expResult, result);
    }

    /**
     * Test of increaseScore method, of class Team.
     */
    public void testIncreaseScore3() {
        System.out.println("increaseScore3");
        int amount = 11;
        Team instance = new Team("player1", "player2");
        boolean expResult = true;
        boolean result = instance.increaseScore(amount);
        assertEquals(expResult, result);
    }

    /**
     * Test of hasMember method, of class Team.
     */
    public void testHasMember() {
        System.out.println("hasMember");
        String player = "player1";
        Team instance = new Team("player1", "player2");
        boolean expResult = true;
        boolean result = instance.hasMember(player);
        assertEquals(expResult, result);
    }

    /**
     * Test of hasMember method, of class Team.
     */
    public void testHasMember2() {
        System.out.println("hasMember2");
        String player = "player5";
        Team instance = new Team("player1", "player2");
        boolean expResult = false;
        boolean result = instance.hasMember(player);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTeammate method, of class Team.
     */
    public void testGetTeammate() throws Exception {
        System.out.println("getTeammate");
        String player = "player1";
        Team instance = new Team("player1", "player2");
        String expResult = "player2";
        String result = instance.getTeammate(player);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTeammate method, of class Team.
     */
    public void testGetTeammate2() throws Exception {
        System.out.println("getTeammate2");
        String player = "player2";
        Team instance = new Team("player1", "player2");
        String expResult = "player1";
        String result = instance.getTeammate(player);
        assertEquals(expResult, result);
    }

    /**
     * Test of clone method, of class Team.
     */
    public void testClone() {
        System.out.println("clone");
        Team instance = new Team("player1", "player2");
        Team expResult = instance;
        Team result = instance.clone();
        assertNotSame(expResult, result);
    }

}
