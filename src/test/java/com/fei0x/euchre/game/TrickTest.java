/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.game;

import com.fei0x.euchre.game.Card;
import com.fei0x.euchre.game.Play;
import com.fei0x.euchre.game.Rank;
import com.fei0x.euchre.game.Suit;
import com.fei0x.euchre.game.Trick;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author jsweetman
 */
public class TrickTest extends TestCase {
    
    public TrickTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(TrickTest.class);
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
     * Test of addPlay method, of class Trick.
     */
    public void testAddPlay() {
        System.out.println("addPlay");
        String player = "player1";
        Card card = new Card(Rank.TEN, Suit.CLUBS);
        Trick instance = new Trick(Suit.CLUBS, false);
        instance.addPlay(player, card);
        assertEquals(instance.getPlays().size(), 1);
        assertEquals(instance.getPlays().get(0).getCard().name(), card.name());
        assertEquals(instance.getPlays().get(0).getPlayer(), player);
    }

    /**
     * Test of addPlay method, of class Trick.
     */
    public void testAddPlay2() {
        System.out.println("addPlay2");
        Trick instance = new Trick(Suit.CLUBS, false);
        String player1 = "player1";
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.QUEEN, Suit.CLUBS);
        instance.addPlay(player2, card2);
        String player3 = "player3";
        Card card3 = new Card(Rank.JACK, Suit.CLUBS);
        instance.addPlay(player3, card3);
        String player4 = "player4";
        Card card4 = new Card(Rank.ACE, Suit.CLUBS);
        instance.addPlay(player4, card4);
        assertEquals(instance.getPlays().size(), 4);
        assertEquals(instance.getPlays().get(0).getCard().name(), card1.name());
        assertEquals(instance.getPlays().get(1).getPlayer(), player2);
        assertEquals(instance.getPlays().get(2).getCard().name(), card3.name());
        assertEquals(instance.getPlays().get(3).getPlayer(), player4);
    }

    /**
     * Test of addPlay method, of class Trick.
     */
    public void testAddPlay3() {
        System.out.println("addPlay3");
        Trick instance = new Trick(Suit.CLUBS, true);
        String player1 = "player1";
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.QUEEN, Suit.CLUBS);
        instance.addPlay(player2, card2);
        String player3 = "player3";
        Card card3 = new Card(Rank.JACK, Suit.CLUBS);
        instance.addPlay(player3, card3);
        String player4 = "player4";
        Card card4 = new Card(Rank.ACE, Suit.CLUBS);
        instance.addPlay(player4, card4);
        assertEquals(instance.getPlays().size(), 3);
        assertEquals(instance.getPlays().get(0).getCard().name(), card1.name());
        assertEquals(instance.getPlays().get(1).getPlayer(), player2);
        assertEquals(instance.getPlays().get(2).getCard().name(), card3.name());
        try{
            instance.getPlays().get(3);
            assertTrue(false);
        }catch(Exception e){}
    }

    /**
     * Test of isTrickEmpty method, of class Trick.
     */
    public void testIsTrickEmpty() {
        System.out.println("isTrickEmpty");
        Trick instance = new Trick(Suit.CLUBS, true);
        boolean expResult = true;
        boolean result = instance.trickEmpty();
        assertEquals(expResult, result);
    }

    /**
     * Test of isTrickEmpty method, of class Trick.
     */
    public void testIsTrickEmpty2() {
        System.out.println("isTrickEmpty2");
        Trick instance = new Trick(Suit.CLUBS, false);
        String player = "player1";
        Card card = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player, card);
        boolean expResult = false;
        boolean result = instance.trickEmpty();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLedPlay method, of class Trick.
     */
    public void testGetLedPlay() {
        System.out.println("getLedPlay");
        Trick instance = new Trick(Suit.CLUBS, false);
        String player = "player1";
        Card card = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player, card);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.CLUBS);
        instance.addPlay(player2, card2);
        Play result = instance.ledPlay();
        assertEquals(result.getCard().name(), card.name());
        assertEquals(result.getPlayer(), player);
    }

    /**
     * Test of getLedPlay method, of class Trick.
     */
    public void testGetLedPlay2() {
        System.out.println("getLedPlay2");
        Trick instance = new Trick(Suit.CLUBS, false);
        try{
            instance.ledPlay();
            assertTrue(false);
        }catch(Exception e){

        }
    }

    /**
     * Test of getLedCard method, of class Trick.
     */
    public void testGetLedCard() {
        System.out.println("getLedCard");
        Trick instance = new Trick(Suit.CLUBS, false);
        String player = "player1";
        Card card = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player, card);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.CLUBS);
        instance.addPlay(player2, card2);
        Card result = instance.ledCard();
        assertEquals(result.name(), card.name());
    }

    /**
     * Test of getLedCard method, of class Trick.
     */
    public void testGetLedCard2() {
        System.out.println("getLedCard2");
        Trick instance = new Trick(Suit.CLUBS, false);
        try{
            instance.ledCard();
            assertTrue(false);
        }catch(Exception e){

        }

    }

    /**
     * Test of getLedSuit method, of class Trick.
     */
    public void testGetLedSuit() {
        System.out.println("getLedSuit");
        Trick instance = new Trick(Suit.SPADES, false);
        String player = "player1";
        Card card = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player, card);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.SPADES);
        instance.addPlay(player2, card2);
        Suit result = instance.ledSuit(false);
        assertEquals(result, card.getSuit(Suit.SPADES));
    }

    /**
     * Test of getLedSuit method, of class Trick.
     */
    public void testGetLedSuit2() {
        System.out.println("getLedSuit2");
        Trick instance = new Trick(Suit.SPADES, false);
        try{
            instance.ledSuit(true);
            assertTrue(false);
        }catch(Exception e){

        }

    }

    /**
     * Test of getLedSuit method, of class Trick.
     */
    public void testGetLedSuit3() {
        System.out.println("getLedSuit3");
        Trick instance = new Trick(Suit.SPADES, false);
        String player = "player1";
        Card card = new Card(Rank.JACK, Suit.CLUBS);
        instance.addPlay(player, card);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.SPADES);
        instance.addPlay(player2, card2);
        Suit result = instance.ledSuit(true);
        assertEquals(result, Suit.SPADES);
    }

    /**
     * Test of getLedSuit method, of class Trick.
     */
    public void testGetLedSuit4() {
        System.out.println("getLedSuit4");
        Trick instance = new Trick(Suit.SPADES, false);
        String player = "player1";
        Card card = new Card(Rank.JACK, Suit.SPADES);
        instance.addPlay(player, card);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.SPADES);
        instance.addPlay(player2, card2);
        Suit result = instance.ledSuit(true);
        assertEquals(result, Suit.SPADES);
    }

    /**
     * Test of getPlays method, of class Trick.
     */
    public void testGetPlays() {
        System.out.println("getPlays");
        Trick instance = new Trick(Suit.CLUBS, false);
        String player1 = "player1";
        Card card1 = new Card(Rank.JACK, Suit.SPADES);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        instance.addPlay(player2, card2);
        String player3 = "player3";
        Card card3 = new Card(Rank.JACK, Suit.DIAMONDS);
        instance.addPlay(player3, card3);
        String player4 = "player4";
        Card card4 = new Card(Rank.ACE, Suit.CLUBS);
        instance.addPlay(player4, card4);
        assertEquals(instance.getPlays().size(), 4);
        assertEquals(instance.getPlays().get(0).getCard().name(), card1.name());
        assertEquals(instance.getPlays().get(1).getPlayer(), player2);
        assertEquals(instance.getPlays().get(2).getCard().name(), card3.name());
        assertEquals(instance.getPlays().get(3).getPlayer(), player4);
    }

    /**
     * Test of getTrickTaker method, of class Trick.
     */
    public void testGetTrickTaker() {
        System.out.println("getTrickTaker");
        Trick instance = new Trick(Suit.CLUBS, false);
        String player1 = "player1";
        Card card1 = new Card(Rank.JACK, Suit.SPADES);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        instance.addPlay(player2, card2);
        String player3 = "player3";
        Card card3 = new Card(Rank.JACK, Suit.DIAMONDS);
        instance.addPlay(player3, card3);
        String player4 = "player4";
        Card card4 = new Card(Rank.ACE, Suit.CLUBS);
        instance.addPlay(player4, card4);
        String result = instance.getTrickTaker();
        System.out.println("result: " + result);
        assertEquals(result, player1);
    }

    /**
     * Test of getTrickTaker method, of class Trick.
     */
    public void testGetTrickTaker2() {
        System.out.println("getTrickTaker2");
        Trick instance = new Trick(Suit.CLUBS, false);
        String player1 = "player1";
        Card card1 = new Card(Rank.JACK, Suit.CLUBS);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        instance.addPlay(player2, card2);
        String player3 = "player3";
        Card card3 = new Card(Rank.JACK, Suit.DIAMONDS);
        instance.addPlay(player3, card3);
        String player4 = "player4";
        Card card4 = new Card(Rank.ACE, Suit.CLUBS);
        instance.addPlay(player4, card4);
        String result = instance.getTrickTaker();
        System.out.println("result: " + result);
        assertEquals(result, player1);
    }

    /**
     * Test of getTrickTaker method, of class Trick.
     */
    public void testGetTrickTaker3() {
        System.out.println("getTrickTaker3");
        Trick instance = new Trick(Suit.CLUBS, false);
        String player1 = "player1";
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        instance.addPlay(player2, card2);
        String player3 = "player3";
        Card card3 = new Card(Rank.JACK, Suit.DIAMONDS);
        instance.addPlay(player3, card3);
        String player4 = "player4";
        Card card4 = new Card(Rank.ACE, Suit.CLUBS);
        instance.addPlay(player4, card4);
        String result = instance.getTrickTaker();
        System.out.println("result: " + result);
        assertEquals(result, player4);
    }

    /**
     * Test of getTrickTaker method, of class Trick.
     */
    public void testGetTrickTaker4() {
        System.out.println("getTrickTaker4");
        Trick instance = new Trick(Suit.SPADES, false);
        String player1 = "player1";
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        instance.addPlay(player2, card2);
        String player3 = "player3";
        Card card3 = new Card(Rank.JACK, Suit.DIAMONDS);
        instance.addPlay(player3, card3);
        String player4 = "player4";
        Card card4 = new Card(Rank.ACE, Suit.CLUBS);
        instance.addPlay(player4, card4);
        String result = instance.getTrickTaker();
        System.out.println("result: " + result);
        assertEquals(result, player4);
    }

    /**
     * Test of getTrickTaker method, of class Trick.
     */
    public void testGetTrickTaker5() {
        System.out.println("getTrickTaker5");
        Trick instance = new Trick(Suit.SPADES, true);
        String player1 = "player1";
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        instance.addPlay(player2, card2);
        String player3 = "player3";
        Card card3 = new Card(Rank.JACK, Suit.DIAMONDS);
        instance.addPlay(player3, card3);
        String result = instance.getTrickTaker();
        System.out.println("result: " + result);
        assertEquals(result, player1);
    }

    /**
     * Test of getTrickTaker method, of class Trick.
     */
    public void testGetTrickTaker6() {
        System.out.println("getTrickTaker6");
        Trick instance = new Trick(Suit.CLUBS, true);
        String player1 = "player1";
        Card card1 = new Card(Rank.JACK, Suit.SPADES);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        instance.addPlay(player2, card2);
        try{
            instance.getTrickTaker();
            assertTrue(false);
        }catch(Exception e){}
    }

    /**
     * Test of getTrickTaker method, of class Trick.
     */
    public void testGetTrickTaker7() {
        System.out.println("getTrickTaker7");
        Trick instance = new Trick(Suit.SPADES, false);
        String player1 = "player1";
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        instance.addPlay(player2, card2);
        String player3 = "player3";
        Card card3 = new Card(Rank.JACK, Suit.DIAMONDS);
        instance.addPlay(player3, card3);
        String player4 = "player4";
        Card card4 = new Card(Rank.ACE, Suit.DIAMONDS);
        instance.addPlay(player4, card4);
        String result = instance.getTrickTaker();
        System.out.println("result: " + result);
        assertEquals(result, player1);
    }

    /**
     * Test of getTrickTaker method, of class Trick.
     */
    public void testGetTrickTaker8() {
        System.out.println("getTrickTaker8");
        Trick instance = new Trick(Suit.CLUBS, false);
        String player1 = "player1";
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.JACK, Suit.SPADES);
        instance.addPlay(player2, card2);
        try{
            instance.getTrickTaker();
            assertTrue(false);
        }catch(Exception e){}
    }

    /**
     * Test of getTrickTaker method, of class Trick.
     */
    public void testGetTrickTaker9() {
        System.out.println("getTrickTaker9");
        Trick instance = new Trick(Suit.CLUBS, false);
        try{
            instance.getTrickTaker();
            assertTrue(false);
        }catch(Exception e){}
        
    }

    /**
     * Test of getTrickTaker method, of class Trick.
     */
    public void testGetTrickTaker10() {
        System.out.println("getTrickTaker10");
        Trick instance = new Trick(Suit.CLUBS, false);
        String player1 = "player1";
        Card card1 = new Card(Rank.NINE, Suit.CLUBS);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        instance.addPlay(player2, card2);
        String player3 = "player3";
        Card card3 = new Card(Rank.ACE, Suit.CLUBS);
        instance.addPlay(player3, card3);
        String player4 = "player4";
        Card card4 = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player4, card4);
        String result = instance.getTrickTaker();
        System.out.println("result: " + result);
        assertEquals(result, player3);
    }

    /**
     * Test of isTrickComplete method, of class Trick.
     */
    public void testIsTrickComplete() {
        System.out.println("isTrickComplete");
        Trick instance = new Trick(Suit.CLUBS, false);
        boolean expResult = false;
        boolean result = instance.trickComplete();
        assertEquals(expResult, result);
    }

    /**
     * Test of isTrickComplete method, of class Trick.
     */
    public void testIsTrickComplete1() {
        System.out.println("isTrickComplete1");
        Trick instance = new Trick(Suit.SPADES, false);
        String player1 = "player1";
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        instance.addPlay(player2, card2);
        String player3 = "player3";
        Card card3 = new Card(Rank.JACK, Suit.DIAMONDS);
        instance.addPlay(player3, card3);
        String player4 = "player4";
        Card card4 = new Card(Rank.ACE, Suit.DIAMONDS);
        instance.addPlay(player4, card4);
        boolean expResult = true;
        boolean result = instance.trickComplete();
        assertEquals(expResult, result);
    }

    /**
     * Test of isTrickComplete method, of class Trick.
     */
    public void testIsTrickComplete2() {
        System.out.println("isTrickComplete2");
        Trick instance = new Trick(Suit.SPADES, true);
        String player1 = "player1";
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        instance.addPlay(player2, card2);
        String player3 = "player3";
        Card card3 = new Card(Rank.JACK, Suit.DIAMONDS);
        instance.addPlay(player3, card3);
        boolean expResult = true;
        boolean result = instance.trickComplete();
        assertEquals(expResult, result);
    }

    /**
     * Test of hasCard method, of class Trick.
     */
    public void testHasCard() {
        System.out.println("hasCard");
        Card card = new Card(Rank.ACE, Suit.HEARTS);
        Trick instance = new Trick(Suit.SPADES, true);
        String player1 = "player1";
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        instance.addPlay(player2, card2);
        String player3 = "player3";
        Card card3 = new Card(Rank.JACK, Suit.DIAMONDS);
        instance.addPlay(player3, card3);
        boolean expResult = true;
        boolean result = instance.hasCard(card);
        assertEquals(expResult, result);
    }

    /**
     * Test of hasCard method, of class Trick.
     */
    public void testHasCard1() {
        System.out.println("hasCard1");
        Card card = new Card(Rank.NINE, Suit.HEARTS);
        Trick instance = new Trick(Suit.SPADES, true);
        String player1 = "player1";
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        instance.addPlay(player2, card2);
        String player3 = "player3";
        Card card3 = new Card(Rank.JACK, Suit.DIAMONDS);
        instance.addPlay(player3, card3);
        boolean expResult = false;
        boolean result = instance.hasCard(card);
        assertEquals(expResult, result);
    }


    /**
     * Test of getTrump method, of class Trick.
     */
    public void testGetTrump() {
        System.out.println("getTrump");
        Trick instance = new Trick(Suit.SPADES, true);
        Suit expResult = Suit.SPADES;
        Suit result = instance.getTrump();
        assertEquals(expResult, result);
    }

    /**
     * Test of clone method, of class Trick.
     */
    public void testClone() {
        System.out.println("clone");
        Trick instance = new Trick(Suit.SPADES, true);
        String player1 = "player1";
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        instance.addPlay(player1, card1);
        String player2 = "player2";
        Card card2 = new Card(Rank.ACE, Suit.HEARTS);
        instance.addPlay(player2, card2);
        String player3 = "player3";
        Card card3 = new Card(Rank.JACK, Suit.DIAMONDS);
        instance.addPlay(player3, card3);
        Trick expResult = instance;
        Trick result = instance.clone();
        assertNotSame(expResult, result);
    }

}
