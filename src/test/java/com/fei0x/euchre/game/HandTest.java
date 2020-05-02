/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.game;

import java.util.ArrayList;
import java.util.List;

import com.fei0x.euchre.exceptions.IllegalPlay;
import com.fei0x.euchre.game.Card;
import com.fei0x.euchre.game.Hand;
import com.fei0x.euchre.game.Rank;
import com.fei0x.euchre.game.Suit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author jsweetman
 */
public class HandTest extends TestCase {
    
    public HandTest() {
        super();
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(HandTest.class);
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
     * Test of getHand method, of class PlayerHand.
     */
    public void testGetHand() {
        System.out.println("getHand");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.KING, Suit.CLUBS));
        Hand instance = new Hand(cards);
        List<Card> expResult = cards;
        List<Card> result = instance.getCards();
        assertEquals(expResult, result);
    }

    /**
     * Test of hasCard method, of class PlayerHand.
     */
    public void testHasCard() {
        System.out.println("hasCard");
        Card other = new Card(Rank.QUEEN, Suit.CLUBS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.KING, Suit.CLUBS));
        Hand instance = new Hand(cards);
        boolean expResult = true;
        boolean result = instance.hasCard(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of hasCard method, of class PlayerHand.
     */
    public void testHasCard2() {
        System.out.println("hasCard2");
        Card other = new Card(Rank.QUEEN, Suit.SPADES);
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.KING, Suit.CLUBS));
        Hand instance = new Hand(cards);
        boolean expResult = false;
        boolean result = instance.hasCard(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of pullCard method, of class PlayerHand.
     */
    public void testPullCard() throws Exception {
        System.out.println("pullCard");
        Card topull = new Card(Rank.TEN, Suit.CLUBS);
        Card testPull = new Card(Rank.TEN, Suit.CLUBS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.KING, Suit.CLUBS));
        Hand instance = new Hand(cards);
        Card expResult = testPull;
        Card result = instance.pullCard(topull);
        assertEquals(expResult.name(), result.name());
    }

    /**
     * Test of pullCard method, of class PlayerHand.
     */
    public void testPullCard2() throws Exception {
        System.out.println("pullCard2");
        Card topull = new Card(Rank.TEN, Suit.SPADES);
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.KING, Suit.CLUBS));
        Hand instance = new Hand(cards);
        try{
            instance.pullCard(topull);
            assertTrue(false);
        }catch(IllegalPlay ip){
        }
    }

    /**
     * Test of swapWithFaceUpCard method, of class PlayerHand.
     */
    public void testSwapWithFaceUpCard() throws Exception {
        System.out.println("swapWithFaceUpCard");
        Card toAdd = new Card(Rank.TEN, Suit.SPADES);
        Card topull = new Card(Rank.KING, Suit.HEARTS);
        Card resultCard = new Card(Rank.KING, Suit.HEARTS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(resultCard);
        Hand instance = new Hand(cards);
        Card expResult = resultCard;
        Card result = instance.swapCard(toAdd, topull);
        System.out.println("result: " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of swapWithFaceUpCard method, of class PlayerHand.
     */
    public void testSwapWithFaceUpCard2() throws Exception {
        System.out.println("swapWithFaceUpCard2");
        Card toAdd = new Card(Rank.TEN, Suit.SPADES);
        Card topull = new Card(Rank.KING, Suit.HEARTS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        Hand instance = new Hand(cards);
        try{
            instance.swapCard(toAdd, topull);
            assertTrue(false);
        }catch(IllegalPlay ip){
        }
    }

    /**
     * Test of showHand method, of class PlayerHand.
     */
    public void testShowHand() {
        System.out.println("getHand");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.KING, Suit.CLUBS));
        Hand instance = new Hand(cards);
        List<Card> expResult = cards;
        List<Card> result = instance.getCards();
        assertCardArray(expResult, result);
    }

    /**
     * Test of hasSuit method, of class PlayerHand.
     */
    public void testHasSuit() {
        System.out.println("hasSuit");
        Suit suit = Suit.CLUBS;
        Suit trump = Suit.CLUBS;
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.JACK, Suit.HEARTS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        Hand instance = new Hand(cards);
        boolean expResult = true;
        boolean result = instance.hasSuit(suit, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of hasSuit method, of class PlayerHand.
     */
    public void testHasSuit2() {
        System.out.println("hasSuit2");
        Suit suit = Suit.HEARTS;
        Suit trump = Suit.HEARTS;
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.JACK, Suit.HEARTS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        Hand instance = new Hand(cards);
        boolean expResult = true;
        boolean result = instance.hasSuit(suit, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of hasSuit method, of class PlayerHand.
     */
    public void testHasSuit3() {
        System.out.println("hasSuit3");
        Suit suit = Suit.DIAMONDS;
        Suit trump = Suit.DIAMONDS;
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.JACK, Suit.HEARTS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        Hand instance = new Hand(cards);
        boolean expResult = true;
        boolean result = instance.hasSuit(suit, trump);
        assertEquals(expResult, result);
    }


    /**
     * Test of hasSuit method, of class PlayerHand.
     */
    public void testHasSuit4() {
        System.out.println("hasSuit4");
        Suit suit = Suit.SPADES;
        Suit trump = Suit.HEARTS;
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.JACK, Suit.HEARTS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        Hand instance = new Hand(cards);
        boolean expResult = false;
        boolean result = instance.hasSuit(suit, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class PlayerHand.
     */
    public void testToString() {
        System.out.println("toString");
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.JACK, Suit.HEARTS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        Hand instance = new Hand(cards);
        String expResult = "TEN of CLUBS, JACK of HEARTS";
        String result = instance.toString();
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of clone method, of class PlayerHand.
     */
    public void testClone() {
        System.out.println("clone");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.HEARTS));
        Hand instance = new Hand(cards);
        Hand expResult = instance;
        Hand result = instance.clone();
        assertNotSame(expResult, result);
    }

    private void assertCardArray(List<Card> expResult, List<Card> result){
        System.out.println("Expected");
        for(Card card :expResult){
            System.out.println(card.name());
        }
        System.out.println("Result");
        for(Card card :result){
            System.out.println(card.name());
        }
        assertEquals(expResult.size(), result.size());
        for(int i = 0; i < expResult.size(); i++){
            assertEquals(expResult.get(i).name(), result.get(i).name());
        }
    }
}
