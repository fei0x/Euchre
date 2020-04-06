/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchreplayers;

import com.n8id.n8euchregame.AskGameImpl;
import com.n8id.n8euchregame.Card;
import com.n8id.n8euchregame.EuchreGame;
import com.n8id.n8euchregame.PlayerHand;
import com.n8id.n8euchregame.Suit;
import com.n8id.n8euchregame.Trick;
import junit.framework.TestCase;

/**
 *
 * @author jsweetman
 */
public class PlayerTest extends TestCase {
    
    public PlayerTest(String testName) {
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
     * Test of getName method, of class Player.
     */
    public void testGetName() {
        System.out.println("getName");
        Player instance = new PlayerImpl("name");
        String expResult = "name";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setHand method, of class Player.
     */
    public void testSetHand() {
        System.out.println("setHand");
        PlayerHand handCopy = new PlayerHand("name", null);
        Player instance = new PlayerImpl("name");
        instance.setHand(handCopy);
    }

    /**
     * Test of setAskGame method, of class Player.
     */
    public void testSetAskGame() {
        System.out.println("setAskGame");
        Player p1 = new PlayerImpl("name1");
        Player p2 = new PlayerImpl("name2");
        Player p3 = new PlayerImpl("name3");
        Player p4 = new PlayerImpl("name4");
        AskGameImpl askGame = new AskGameImpl(new EuchreGame(p1,p2,p3,p4,1,System.out),p1.getName());
        p1.setAskGame(askGame);
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
