/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchregame;

import com.n8id.n8euchreplayers.Player;
import java.util.ArrayList;
import junit.framework.TestCase;

/**
 *
 * @author jsweetman
 */
public class AskGameTest extends TestCase {
    
    public AskGameTest(String testName) {
        super(testName);
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
     * Test of speak method, of class AskGameImpl.
     */
    public void testSpeak() {
        System.out.println("speak");
        String somethingToSay = "!!!WRITE-THIS-TO-STANDARD-OUT!!!";
        Player p1 = new PlayerImpl("p1");
        Player p2 = new PlayerImpl("p2");
        Player p3 = new PlayerImpl("p3");
        Player p4 = new PlayerImpl("p4");
        AskGameImpl instance = new AskGameImpl(new EuchreGame(p1,p2,p3,p4,1,System.out), p1.getName());
        instance.speak(somethingToSay);
    }

    /**
     * Test of getPastTricks method, of class AskGameImpl.
     */
    public void testGetPastTricks() {
        System.out.println("getPastTricks");
        Player p1 = new PlayerImpl("p1");
        Player p2 = new PlayerImpl("p2");
        Player p3 = new PlayerImpl("p3");
        Player p4 = new PlayerImpl("p4");
        AskGameImpl instance = new AskGameImpl(new EuchreGame(p1,p2,p3,p4,1,System.out), p1.getName());
        try{
            ArrayList result = instance.getPastTricks();
            assertTrue(false);
        }catch(Exception e){}
    }

    /**
     * Test of getMyTeamsScore method, of class AskGameImpl.
     */
    public void testGetMyTeamsScore() {
        System.out.println("getMyTeamsScore");
        Player p1 = new PlayerImpl("p1");
        Player p2 = new PlayerImpl("p2");
        Player p3 = new PlayerImpl("p3");
        Player p4 = new PlayerImpl("p4");
        EuchreGame game = new EuchreGame(p1,p2,p3,p4,1,System.out);
        game.getTeams().get(0).increaseScore(5);
        AskGameImpl instance = new AskGameImpl(game, p1.getName());
        int expResult = 5;
        int result = instance.getMyTeamsScore();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOpponentsScore method, of class AskGameImpl.
     */
    public void testGetOpponentsScore() {
        System.out.println("getOpponentsScore");
        Player p1 = new PlayerImpl("p1");
        Player p2 = new PlayerImpl("p2");
        Player p3 = new PlayerImpl("p3");
        Player p4 = new PlayerImpl("p4");
        EuchreGame game = new EuchreGame(p1,p2,p3,p4,1,System.out);
        game.getTeams().get(1).increaseScore(8);
        AskGameImpl instance = new AskGameImpl(game, p1.getName());
        int expResult = 8;
        int result = instance.getOpponentsScore();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPartnersName method, of class AskGameImpl.
     */
    public void testGetPartnersName() {
        System.out.println("getPartnersName");
        Player p1 = new PlayerImpl("p1");
        Player p2 = new PlayerImpl("p2");
        Player p3 = new PlayerImpl("p3");
        Player p4 = new PlayerImpl("p4");
        AskGameImpl instance = new AskGameImpl(new EuchreGame(p1,p2,p3,p4,1,System.out), p1.getName());
        String expResult = "p2";
        String result = instance.getPartnersName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOpponents method, of class AskGameImpl.
     */
    public void testGetOpponents() {
        System.out.println("getOpponents");
        Player p1 = new PlayerImpl("p1");
        Player p2 = new PlayerImpl("p2");
        Player p3 = new PlayerImpl("p3");
        Player p4 = new PlayerImpl("p4");
        AskGameImpl instance = new AskGameImpl(new EuchreGame(p1,p2,p3,p4,1,System.out), p1.getName());
        ArrayList<String> result = instance.getOpponents();
        assertEquals("p3", result.get(0));
        assertEquals("p4", result.get(1));
    }


    public class PlayerImpl extends Player {

        public PlayerImpl(String name) {
            super(name);
        }

        public boolean callItUp(Card faceUpCard) {
            return false;
        }

        public Card swapWithFaceUpCard(Card faceUpCard) {
            return null;
        }

        public Suit callSuit(Card turnedDownCard) {
            return null;
        }

        public Suit stickTheDealer(Card turnedDownCard) {
            return null;
        }

        public boolean playAlone() {
            return false;
        }

        public Card playCard(Trick currentTrick) {
            return null;
        }
    }
}
