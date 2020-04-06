/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.game;

import com.fei0x.euchre.game.AskGameImpl;
import com.fei0x.euchre.game.Card;
import com.fei0x.euchre.game.EuchreGame;
import com.fei0x.euchre.game.Suit;
import com.fei0x.euchre.game.Trick;
import com.fei0x.euchre.player_ai.PlayerAI;
import com.fei0x.euchre.game.Player;

import java.util.List;

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
        Player p1 = new Player("p1", new PlayerAIImpl());
        Player p2 = new Player("p2", new PlayerAIImpl());
        Player p3 = new Player("p3", new PlayerAIImpl());
        Player p4 = new Player("p4", new PlayerAIImpl());
        AskGameImpl instance = new AskGameImpl(new EuchreGame(p1,p2,p3,p4,1,System.out), p1);
        instance.speak(somethingToSay);
    }

    /**
     * Test of getPastTricks method, of class AskGameImpl.
     */
    public void testGetPastTricks() {
        System.out.println("getPastTricks");
        Player p1 = new Player("p1", new PlayerAIImpl());
        Player p2 = new Player("p2", new PlayerAIImpl());
        Player p3 = new Player("p3", new PlayerAIImpl());
        Player p4 = new Player("p4", new PlayerAIImpl());
        AskGameImpl instance = new AskGameImpl(new EuchreGame(p1,p2,p3,p4,1,System.out), p1);
        try{
            instance.pastTricks();
            assertTrue(false);
        }catch(Exception e){}
    }

    /**
     * Test of getMyTeamsScore method, of class AskGameImpl.
     */
    public void testGetMyTeamsScore() {
        System.out.println("getMyTeamsScore");
        Player p1 = new Player("p1", new PlayerAIImpl());
        Player p2 = new Player("p2", new PlayerAIImpl());
        Player p3 = new Player("p3", new PlayerAIImpl());
        Player p4 = new Player("p4", new PlayerAIImpl());
        EuchreGame game = new EuchreGame(p1,p2,p3,p4,1,System.out);
        game.getTeams().get(0).increaseScore(5);
        AskGameImpl instance = new AskGameImpl(game, p1);
        int expResult = 5;
        int result = instance.myTeamsScore();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOpponentsScore method, of class AskGameImpl.
     */
    public void testGetOpponentsScore() {
        System.out.println("getOpponentsScore");
        Player p1 = new Player("p1", new PlayerAIImpl());
        Player p2 = new Player("p2", new PlayerAIImpl());
        Player p3 = new Player("p3", new PlayerAIImpl());
        Player p4 = new Player("p4", new PlayerAIImpl());
        EuchreGame game = new EuchreGame(p1,p2,p3,p4,1,System.out);
        game.getTeams().get(1).increaseScore(8);
        AskGameImpl instance = new AskGameImpl(game, p1);
        int expResult = 8;
        int result = instance.opponentsScore();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPartnersName method, of class AskGameImpl.
     */
    public void testGetPartnersName() {
        System.out.println("getPartnersName");
        Player p1 = new Player("p1", new PlayerAIImpl());
        Player p2 = new Player("p2", new PlayerAIImpl());
        Player p3 = new Player("p3", new PlayerAIImpl());
        Player p4 = new Player("p4", new PlayerAIImpl());
        AskGameImpl instance = new AskGameImpl(new EuchreGame(p1,p2,p3,p4,1,System.out), p1);
        String expResult = "p2";
        String result = instance.partnersName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOpponents method, of class AskGameImpl.
     */
    public void testGetOpponents() {
        System.out.println("getOpponents");
        Player p1 = new Player("p1", new PlayerAIImpl());
        Player p2 = new Player("p2", new PlayerAIImpl());
        Player p3 = new Player("p3", new PlayerAIImpl());
        Player p4 = new Player("p4", new PlayerAIImpl());
        AskGameImpl instance = new AskGameImpl(new EuchreGame(p1,p2,p3,p4,1,System.out), p1);
        List<String> result = instance.opponentsNames();
        assertEquals("p3", result.get(0));
        assertEquals("p4", result.get(1));
    }


    public class PlayerAIImpl extends PlayerAI {

        public PlayerAIImpl() {
            super();
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
