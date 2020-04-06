/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.game;

import java.util.ArrayList;
import java.util.List;

import com.fei0x.euchre.game.Card;
import com.fei0x.euchre.game.Rank;
import com.fei0x.euchre.game.Suit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author jsweetman
 */
public class CardTest extends TestCase {
    
    public CardTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(CardTest.class);
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
     * Test of HigherThan method, of class Card.
     */
    public void testHigherThan() {
        System.out.println("HigherThan");
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.ACE, Suit.CLUBS);
        boolean expResult = false;
        boolean result = c1.higherThan(c2);
        assertEquals(expResult, result);
    }

    /**
     * Test of HigherThan method, of class Card.
     */
    public void testHigherThan2() {
        System.out.println("HigherThan2");
        Card c1 = new Card(Rank.ACE, Suit.HEARTS);
        Card c2 = new Card(Rank.JACK, Suit.CLUBS);
        boolean expResult = true;
        boolean result = c1.higherThan(c2);
        assertEquals(expResult, result);
    }

    /**
     * Test of HigherThan method, of class Card.
     */
    public void testHigherThan3() {
        System.out.println("HigherThan3");
        Card c1 = new Card(Rank.KING, Suit.DIAMONDS);
        Card c2 = new Card(Rank.KING, Suit.CLUBS);
        boolean expResult = false;
        boolean result = c1.higherThan(c2);
        assertEquals(expResult, result);
    }

    /**
     * Test of SameSuit method, of class Card.
     */
    public void testSameSuit() {
        System.out.println("SameSuit");
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.ACE, Suit.CLUBS);
        boolean expResult = true;
        boolean result = c1.sameSuit(c2,Suit.HEARTS);
        assertEquals(expResult, result);
    }


    /**
     * Test of SameSuit method, of class Card.
     */
    public void testSameSuit2() {
        System.out.println("SameSuit2");
        Card c1 = new Card(Rank.NINE, Suit.CLUBS);
        Card c2 = new Card(Rank.QUEEN, Suit.DIAMONDS);
        boolean expResult = false;
        boolean result = c1.sameSuit(c2,Suit.HEARTS);
        assertEquals(expResult, result);
    }


    /**
     * Test of SameSuit method, of class Card.
     */
    public void testSameSuit3() {
        System.out.println("SameSuit3");
        Card c1 = new Card(Rank.JACK, Suit.CLUBS);
        Card c2 = new Card(Rank.JACK, Suit.DIAMONDS);
        boolean expResult = false;
        boolean result = c1.sameSuit(c2,Suit.HEARTS);
        assertEquals(expResult, result);
    }


    /**
     * Test of SameSuit method, of class Card.
     */
    public void testSameSuit4() {
        System.out.println("SameSuit4");
        Card c1 = new Card(Rank.JACK, Suit.HEARTS);
        Card c2 = new Card(Rank.JACK, Suit.DIAMONDS);
        boolean expResult = true;
        boolean result = c1.sameSuit(c2,Suit.HEARTS);
        assertEquals(expResult, result);
    }


    /**
     * Test of SameSuit method, of class Card.
     */
    public void testSameSuit5() {
        System.out.println("SameSuit5");
        Card c1 = new Card(Rank.NINE, Suit.HEARTS);
        Card c2 = new Card(Rank.JACK, Suit.DIAMONDS);
        boolean expResult = true;
        boolean result = c1.sameSuit(c2,Suit.HEARTS);
        assertEquals(expResult, result);
    }


    /**
     * Test of SameSuit method, of class Card.
     */
    public void testSameSuit6() {
        System.out.println("SameSuit6");
        Card c1 = new Card(Rank.JACK, Suit.HEARTS);
        Card c2 = new Card(Rank.JACK, Suit.CLUBS);
        boolean expResult = false;
        boolean result = c1.sameSuit(c2,Suit.HEARTS);
        assertEquals(expResult, result);
    }


    /**
     * Test of SameSuit method, of class Card.
     */
    public void testSameSuit7() {
        System.out.println("SameSuit7");
        Card c1 = new Card(Rank.JACK, Suit.DIAMONDS);
        Card c2 = new Card(Rank.TEN, Suit.DIAMONDS);
        boolean expResult = false;
        boolean result = c1.sameSuit(c2,Suit.HEARTS);
        assertEquals(expResult, result);
    }

    
    /**
     * Test of getRank method, of class Card.
     */
    public void testGetRank() {
        System.out.println("getRank");
        Card instance = new Card(Rank.JACK, Suit.CLUBS);
        Rank expResult = Rank.JACK;
        Rank result = instance.getRank();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRank method, of class Card.
     */
    public void testGetRank2() {
        System.out.println("getRank2");
        Card instance = new Card(Rank.ACE, Suit.CLUBS);
        Rank expResult = Rank.ACE;
        Rank result = instance.getRank();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSuit method, of class Card.
     */
    public void testGetSuit() {
        System.out.println("getSuit");
        Suit trump = null;
        Card instance = new Card(Rank.ACE, Suit.DIAMONDS);
        Suit expResult = Suit.DIAMONDS;
        Suit result = instance.getSuit(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSuit method, of class Card.
     */
    public void testGetSuit2() {
        System.out.println("getSuit2");
        Suit trump = Suit.HEARTS;
        Card instance = new Card(Rank.JACK, Suit.HEARTS);
        Suit expResult = Suit.HEARTS;
        Suit result = instance.getSuit(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSuit method, of class Card.
     */
    public void testGetSuit3() {
        System.out.println("getSuit3");
        Suit trump = Suit.DIAMONDS;
        Card instance = new Card(Rank.JACK, Suit.HEARTS);
        Suit expResult = Suit.DIAMONDS;
        Suit result = instance.getSuit(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSuit method, of class Card.
     */
    public void testGetSuit4() {
        System.out.println("getSuit4");
        Suit trump = Suit.SPADES;
        Card instance = new Card(Rank.JACK, Suit.HEARTS);
        Suit expResult = Suit.HEARTS;
        Suit result = instance.getSuit(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSuit method, of class Card.
     */
    public void testGetSuit5() {
        System.out.println("getSuit5");
        Suit trump = Suit.SPADES;
        Card instance = new Card(Rank.TEN, Suit.SPADES);
        Suit expResult = Suit.SPADES;
        Suit result = instance.getSuit(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSuit method, of class Card.
     */
    public void testGetSuit6() {
        System.out.println("getSuit6");
        Suit trump = Suit.HEARTS;
        Card instance = new Card(Rank.KING, Suit.CLUBS);
        Suit expResult = Suit.CLUBS;
        Suit result = instance.getSuit(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSuit method, of class Card.
     */
    public void testGetSuit7() {
        System.out.println("getSuit7");
        Suit trump = null;
        Card instance = new Card(Rank.JACK, Suit.HEARTS);
        Suit expResult = Suit.HEARTS;
        Suit result = instance.getSuit(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isRightBower method, of class Card.
     */
    public void testIsRightBower() {
        System.out.println("isRightBower");
        Suit trump = Suit.CLUBS;
        Card instance = new Card(Rank.JACK, Suit.CLUBS);
        boolean expResult = true;
        boolean result = instance.isRightBower(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isRightBower method, of class Card.
     */
    public void testIsRightBower2() {
        System.out.println("isRightBower2");
        Suit trump = Suit.SPADES;
        Card instance = new Card(Rank.JACK, Suit.CLUBS);
        boolean expResult = false;
        boolean result = instance.isRightBower(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isRightBower method, of class Card.
     */
    public void testIsRightBower3() {
        System.out.println("isRightBower2");
        Suit trump = Suit.CLUBS;
        Card instance = new Card(Rank.ACE, Suit.CLUBS);
        boolean expResult = false;
        boolean result = instance.isRightBower(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isLeftBower method, of class Card.
     */
    public void testIsLeftBower() {
        System.out.println("isLeftBower");
        Suit trump = Suit.HEARTS;
        Card instance = new Card(Rank.ACE, Suit.HEARTS);
        boolean expResult = false;
        boolean result = instance.isLeftBower(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isLeftBower method, of class Card.
     */
    public void testIsLeftBower2() {
        System.out.println("isLeftBower2");
        Suit trump = Suit.HEARTS;
        Card instance = new Card(Rank.JACK, Suit.HEARTS);
        boolean expResult = false;
        boolean result = instance.isLeftBower(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isLeftBower method, of class Card.
     */
    public void testIsLeftBower3() {
        System.out.println("isLeftBower3");
        Suit trump = Suit.DIAMONDS;
        Card instance = new Card(Rank.JACK, Suit.HEARTS);
        boolean expResult = true;
        boolean result = instance.isLeftBower(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of sameSuit method, of class Card.
     */
    public void sameSuit() {
        System.out.println("sameSuit");
        Suit trump = Suit.SPADES;
        Card instance = new Card(Rank.JACK, Suit.SPADES);
        Card that = new Card(Rank.JACK, Suit.CLUBS);
        boolean expResult = true;
        boolean result = instance.sameSuit(that, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of sameSuit method, of class Card.
     */
    public void sameSuit2() {
        System.out.println("sameSuit2");
        Suit trump = Suit.SPADES;
        Card instance = new Card(Rank.JACK, Suit.CLUBS);
        Card that = new Card(Rank.JACK, Suit.HEARTS);
        boolean expResult = false;
        boolean result = instance.sameSuit(that, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of sameSuit method, of class Card.
     */
    public void sameSuit3() {
        System.out.println("sameSuit3");
        Suit trump = Suit.DIAMONDS;
        Card instance = new Card(Rank.JACK, Suit.SPADES);
        Card that = new Card(Rank.TEN, Suit.CLUBS);
        boolean expResult = false;
        boolean result = instance.sameSuit(that, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of sameSuit method, of class Card.
     */
    public void sameSuit4() {
        System.out.println("sameSuit4");
        Suit trump = Suit.DIAMONDS;
        Card instance = new Card(Rank.ACE, Suit.SPADES);
        Card that = new Card(Rank.TEN, Suit.SPADES);
        boolean expResult = true;
        boolean result = instance.sameSuit(that, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of sameSuit method, of class Card.
     */
    public void sameSuit5() {
        System.out.println("sameSuit5");
        Suit trump = Suit.DIAMONDS;
        Card instance = new Card(Rank.ACE, Suit.DIAMONDS);
        Card that = new Card(Rank.JACK, Suit.SPADES);
        boolean expResult = false;
        boolean result = instance.sameSuit(that, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isTrump method, of class Card.
     */
    public void testIsTrump() {
        System.out.println("isTrump");
        Suit trump = Suit.SPADES;
        Card instance = new Card(Rank.JACK, Suit.SPADES);
        boolean expResult = true;
        boolean result = instance.isTrump(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isTrump method, of class Card.
     */
    public void testIsTrump2() {
        System.out.println("isTrump2");
        Suit trump = Suit.SPADES;
        Card instance = new Card(Rank.JACK, Suit.CLUBS);
        boolean expResult = true;
        boolean result = instance.isTrump(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isTrump method, of class Card.
     */
    public void testIsTrump3() {
        System.out.println("isTrump3");
        Suit trump = Suit.DIAMONDS;
        Card instance = new Card(Rank.JACK, Suit.CLUBS);
        boolean expResult = false;
        boolean result = instance.isTrump(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isTrump method, of class Card.
     */
    public void testIsTrump4() {
        System.out.println("isTrump4");
        Suit trump = Suit.DIAMONDS;
        Card instance = new Card(Rank.NINE, Suit.SPADES);
        boolean expResult = false;
        boolean result = instance.isTrump(trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of IsTrump method, of class Card.
     */
    public void testIsTrump5() {
        System.out.println("IsTrump5");
        Card c1 = new Card(Rank.JACK, Suit.DIAMONDS);
        boolean expResult = true;
        boolean result = c1.isTrump(Suit.HEARTS);
        assertEquals(expResult, result);
    }
    /**
     * Test of beats method, of class Card.
     */
    public void testBeats() {
        System.out.println("beats");
        Card other = new Card(Rank.JACK, Suit.SPADES);
        Suit trump = Suit.CLUBS;
        Card instance = new Card(Rank.JACK, Suit.CLUBS);
        boolean expResult = true;
        boolean result = instance.beats(other, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of beats method, of class Card.
     */
    public void testBeats2() {
        System.out.println("beats2");
        Card other = new Card(Rank.JACK, Suit.SPADES);
        Suit trump = Suit.SPADES;
        Card instance = new Card(Rank.JACK, Suit.CLUBS);
        boolean expResult = false;
        boolean result = instance.beats(other, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of beats method, of class Card.
     */
    public void testBeats3() {
        System.out.println("beats3");
        Card other = new Card(Rank.ACE, Suit.SPADES);
        Suit trump = Suit.SPADES;
        Card instance = new Card(Rank.JACK, Suit.CLUBS);
        boolean expResult = true;
        boolean result = instance.beats(other, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of beats method, of class Card.
     */
    public void testBeats4() {
        System.out.println("beats4");
        Card other = new Card(Rank.ACE, Suit.SPADES);
        Suit trump = null;
        Card instance = new Card(Rank.JACK, Suit.CLUBS);
        boolean expResult = false;
        boolean result = instance.beats(other, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of beats method, of class Card.
     */
    public void testBeats5() {
        System.out.println("beats5");
        Card other = new Card(Rank.TEN, Suit.HEARTS);
        Suit trump = null;
        Card instance = new Card(Rank.TEN, Suit.SPADES);
        boolean expResult = false;
        boolean result = instance.beats(other, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of beats method, of class Card.
     */
    public void testBeats6() {
        System.out.println("beats6");
        Card other = new Card(Rank.TEN, Suit.HEARTS);
        Suit trump = Suit.CLUBS;
        Card instance = new Card(Rank.TEN, Suit.SPADES);
        boolean expResult = false;
        boolean result = instance.beats(other, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of beats method, of class Card.
     */
    public void testBeats7() {
        System.out.println("beats7");
        Card other = new Card(Rank.KING, Suit.HEARTS);
        Suit trump = Suit.SPADES;
        Card instance = new Card(Rank.NINE, Suit.SPADES);
        boolean expResult = true;
        boolean result = instance.beats(other, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of findStrongestCards method, of class Card.
     */
    public void testFindStrongestCards() {
        System.out.println("findStrongestCards");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.NINE, Suit.HEARTS));
        cards.add(new Card(Rank.NINE, Suit.DIAMONDS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        Suit trump = null;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.SPADES));
        expResult.add(new Card(Rank.JACK, Suit.DIAMONDS));
        List<Card> result = Card.findStrongestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findStrongestCards method, of class Card.
     */
    public void testFindStrongestCards2() {
        System.out.println("findStrongestCards2");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.NINE, Suit.HEARTS));
        cards.add(new Card(Rank.NINE, Suit.DIAMONDS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        Suit trump = Suit.DIAMONDS;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.DIAMONDS));
        List<Card> result = Card.findStrongestCards(cards, trump);
        assertCardArray(expResult, result);
    }


    /**
     * Test of findStrongestCards method, of class Card.
     */
    public void testFindStrongestCards3() {
        System.out.println("findStrongestCards3");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.ACE, Suit.HEARTS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        Suit trump = null;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.ACE, Suit.HEARTS));
        List<Card> result = Card.findStrongestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findStrongestCards method, of class Card.
     */
    public void testFindStrongestCards4() {
        System.out.println("findStrongestCards4");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.ACE, Suit.HEARTS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        Suit trump = Suit.DIAMONDS;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.ACE, Suit.HEARTS));
        List<Card> result = Card.findStrongestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findStrongestCards method, of class Card.
     */
    public void testFindStrongestCards5() {
        System.out.println("findStrongestCards5");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.ACE, Suit.HEARTS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        Suit trump = Suit.CLUBS;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.SPADES));
        List<Card> result = Card.findStrongestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findStrongestCards method, of class Card.
     */
    public void testFindStrongestCards6() {
        System.out.println("findStrongestCards6");
        List<Card> cards = new ArrayList<Card>();
        Suit trump = Suit.CLUBS;
        List<Card> expResult = new ArrayList<Card>();
        List<Card> result = Card.findStrongestCards(cards, trump);
        assertCardArray(expResult, result);
    }


    /**
     * Test of findStrongestCards method, of class Card.
     */
    public void testFindStrongestCards7() {
        System.out.println("findStrongestCards7");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.NINE, Suit.HEARTS));
        cards.add(new Card(Rank.JACK, Suit.HEARTS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        Suit trump = Suit.DIAMONDS;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.DIAMONDS));
        List<Card> result = Card.findStrongestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findStrongestCards method, of class Card.
     */
    public void testFindStrongestCards8() {
        System.out.println("findStrongestCards8");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.NINE, Suit.HEARTS));
        cards.add(new Card(Rank.JACK, Suit.HEARTS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        Suit trump = Suit.HEARTS;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.HEARTS));
        List<Card> result = Card.findStrongestCards(cards, trump);
        assertCardArray(expResult, result);
    }


    /**
     * Test of findStrongestCards method, of class Card.
     */
    public void testFindStrongestCards9() {
        System.out.println("findStrongestCards9");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.NINE, Suit.HEARTS));
        cards.add(new Card(Rank.JACK, Suit.HEARTS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        Suit trump = null;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.HEARTS));
        expResult.add(new Card(Rank.JACK, Suit.SPADES));
        expResult.add(new Card(Rank.JACK, Suit.DIAMONDS));
        List<Card> result = Card.findStrongestCards(cards, trump);
        assertCardArray(expResult, result);
    }
    
    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards() {
        System.out.println("findWeakestCards");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.ACE, Suit.CLUBS));
        cards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        cards.add(new Card(Rank.QUEEN, Suit.SPADES));
        cards.add(new Card(Rank.JACK, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        Suit trump = null;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.CLUBS));
        expResult.add(new Card(Rank.JACK, Suit.DIAMONDS));
        List<Card> result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards2() {
        System.out.println("findWeakestCards2");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.JACK, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        cards.add(new Card(Rank.ACE, Suit.CLUBS));
        cards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        cards.add(new Card(Rank.QUEEN, Suit.SPADES));
        Suit trump = Suit.SPADES;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.DIAMONDS));
        List<Card> result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards3o1() {
        System.out.println("findWeakestCards3o1");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.ACE, Suit.CLUBS));
        cards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        Suit trump = Suit.HEARTS;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.ACE, Suit.CLUBS));
        expResult.add(new Card(Rank.ACE, Suit.DIAMONDS));
        List<Card> result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }
    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards3() {
        System.out.println("findWeakestCards3");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.ACE, Suit.CLUBS));
        cards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        cards.add(new Card(Rank.QUEEN, Suit.SPADES));
        cards.add(new Card(Rank.JACK, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        Suit trump = Suit.HEARTS;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.CLUBS));
        expResult.add(new Card(Rank.JACK, Suit.SPADES));
        List<Card> result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards4() {
        System.out.println("findWeakestCards4");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        Suit trump = null;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.TEN, Suit.CLUBS));
        List<Card> result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards5() {
        System.out.println("findWeakestCards5");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        Suit trump = Suit.CLUBS;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.DIAMONDS));
        List<Card> result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards6() {
        System.out.println("findWeakestCards6");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.JACK, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        Suit trump = Suit.CLUBS;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.TEN, Suit.CLUBS));
        List<Card> result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards7() {
        System.out.println("findWeakestCards7");
        List<Card> cards = new ArrayList<Card>();
        Suit trump = Suit.CLUBS;
        List<Card> expResult = new ArrayList<Card>();
        List<Card> result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards8() {
        System.out.println("findWeakestCards8");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.TEN, Suit.SPADES));
        cards.add(new Card(Rank.TEN, Suit.HEARTS));
        Suit trump = Suit.DIAMONDS;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.TEN, Suit.CLUBS));
        expResult.add(new Card(Rank.TEN, Suit.SPADES));
        expResult.add(new Card(Rank.TEN, Suit.HEARTS));
        List<Card> result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }


    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards9() {
        System.out.println("findWeakestCards9");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.JACK, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.HEARTS));
        Suit trump = Suit.DIAMONDS;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.CLUBS));
        List<Card> result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }


    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards10() {
        System.out.println("findWeakestCards10");
        List<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.JACK, Suit.CLUBS));
        cards.add(new Card(Rank.TEN, Suit.HEARTS));
        cards.add(new Card(Rank.QUEEN, Suit.SPADES));
        cards.add(new Card(Rank.TEN, Suit.DIAMONDS));
        Suit trump = null;
        List<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.TEN, Suit.HEARTS));
        expResult.add(new Card(Rank.TEN, Suit.DIAMONDS));
        List<Card> result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of equals method, of class Card.
     */
    public void testEquals() {
        System.out.println("equals");
        Card other = new Card(Rank.JACK, Suit.SPADES);
        Card instance = new Card(Rank.JACK, Suit.SPADES);
        boolean expResult = true;
        boolean result = instance.equals(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Card.
     */
    public void testEquals2() {
        System.out.println("equals2");
        Card other = new Card(Rank.JACK, Suit.SPADES);
        Card instance = new Card(Rank.QUEEN, Suit.SPADES);
        boolean expResult = false;
        boolean result = instance.equals(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Card.
     */
    public void testToString() {
        System.out.println("toString");
        Card instance = new Card(Rank.QUEEN, Suit.SPADES);
        String expResult = "QUEEN of SPADES";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class Card.
     */
    public void testGetName() {
        System.out.println("getName");
        Card instance = new Card(Rank.QUEEN, Suit.SPADES);
        String expResult = "QUEEN of SPADES";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of clone method, of class Card.
     */
    public void testClone() {
        System.out.println("clone");
        Card instance = new Card(Rank.QUEEN, Suit.SPADES);
        Card expResult = instance;
        Card result = instance.clone();
        assertNotSame(expResult, result);
    }




    private void assertCardArray(List<Card> expResult, List<Card> result){
        System.out.println("Expected");
        for(Card card :expResult){
            System.out.println(card.getName());
        }
        System.out.println("Result");
        for(Card card :result){
            System.out.println(card.getName());
        }
        assertEquals(expResult.size(), result.size());
        for(int i = 0; i < expResult.size(); i++){
            assertEquals(expResult.get(i).getName(), result.get(i).getName());
        }
    }
    

    /**
     * Test of showLegalPlays method, of class PlayerHand.
     */
    public void testShowLegalPlays() {
        System.out.println("showLegalPlays");
        Card led = new Card(Rank.JACK, Suit.DIAMONDS);
        Suit trump = Suit.HEARTS;
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.QUEEN, Suit.CLUBS);
        Card c3 = new Card(Rank.KING, Suit.CLUBS);
        Card c4 = new Card(Rank.JACK, Suit.HEARTS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        List<Card> expResult =  new ArrayList<Card>();
        expResult.add(c4);
        List<Card> result = Card.findLegalCards(cards, led, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of showLegalPlays method, of class PlayerHand.
     */
    public void testShowLegalPlays2() {
        System.out.println("showLegalPlays2");
        Card led = new Card(Rank.JACK, Suit.HEARTS);
        Suit trump = Suit.HEARTS;
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.QUEEN, Suit.CLUBS);
        Card c3 = new Card(Rank.KING, Suit.CLUBS);
        Card c4 = new Card(Rank.JACK, Suit.DIAMONDS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        List<Card> expResult =  new ArrayList<Card>();
        expResult.add(c4);
        List<Card> result = Card.findLegalCards(cards, led, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of showLegalPlays method, of class PlayerHand.
     */
    public void testShowLegalPlays3() {
        System.out.println("showLegalPlays3");
        Card led = new Card(Rank.JACK, Suit.CLUBS);
        Suit trump = Suit.DIAMONDS;
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.QUEEN, Suit.CLUBS);
        Card c3 = new Card(Rank.KING, Suit.CLUBS);
        Card c4 = new Card(Rank.JACK, Suit.DIAMONDS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        List<Card> expResult =  new ArrayList<Card>();
        expResult.add(c1);
        expResult.add(c2);
        expResult.add(c3);
        List<Card> result = Card.findLegalCards(cards, led, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of showLegalPlays method, of class PlayerHand.
     */
    public void testShowLegalPlays4() {
        System.out.println("showLegalPlays4");
        Card led = new Card(Rank.JACK, Suit.SPADES);
        Suit trump = Suit.SPADES;
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.QUEEN, Suit.CLUBS);
        Card c3 = new Card(Rank.KING, Suit.CLUBS);
        Card c4 = new Card(Rank.JACK, Suit.DIAMONDS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        List<Card> expResult =  new ArrayList<Card>();
        expResult.add(c1);
        expResult.add(c2);
        expResult.add(c3);
        expResult.add(c4);
        List<Card> result = Card.findLegalCards(cards, led, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of showLegalPlays method, of class PlayerHand.
     */
    public void testShowLegalPlays5() {
        System.out.println("showLegalPlays5");
        Card led = new Card(Rank.TEN, Suit.DIAMONDS);
        Suit trump = Suit.DIAMONDS;
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.QUEEN, Suit.DIAMONDS);
        Card c3 = new Card(Rank.JACK, Suit.HEARTS);
        Card c4 = new Card(Rank.JACK, Suit.DIAMONDS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        List<Card> expResult =  new ArrayList<Card>();
        expResult.add(c2);
        expResult.add(c3);
        expResult.add(c4);
        List<Card> result = Card.findLegalCards(cards, led, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of showLegalPlays method, of class PlayerHand.
     */
    public void testShowLegalPlays6() {
        System.out.println("showLegalPlays6");
        Card led = new Card(Rank.TEN, Suit.SPADES);
        Suit trump = Suit.SPADES;
        Card c1 = new Card(Rank.JACK, Suit.CLUBS);
        Card c2 = new Card(Rank.JACK, Suit.HEARTS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        List<Card> expResult =  new ArrayList<Card>();
        expResult.add(c1);
        List<Card> result = Card.findLegalCards(cards, led, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of showTrumpCards method, of class PlayerHand.
     */
    public void testShowTrumpCards() {
        System.out.println("showTrumpCards");
        Suit trump = Suit.DIAMONDS;
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.QUEEN, Suit.DIAMONDS);
        Card c3 = new Card(Rank.JACK, Suit.HEARTS);
        Card c4 = new Card(Rank.JACK, Suit.DIAMONDS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        List<Card> expResult =  new ArrayList<Card>();
        expResult.add(c2);
        expResult.add(c3);
        expResult.add(c4);
        List<Card> result = Card.findTrumpCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of showTrumpCards method, of class PlayerHand.
     */
    public void testShowTrumpCards2() {
        System.out.println("showTrumpCards2");
        Suit trump = Suit.SPADES;
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.QUEEN, Suit.DIAMONDS);
        Card c3 = new Card(Rank.JACK, Suit.HEARTS);
        Card c4 = new Card(Rank.JACK, Suit.DIAMONDS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        List<Card> expResult =  new ArrayList<Card>();
        List<Card> result = Card.findTrumpCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of showTrumpCards method, of class PlayerHand.
     */
    public void testShowTrumpCards3() {
        System.out.println("showTrumpCards3");
        Suit trump = Suit.HEARTS;
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.QUEEN, Suit.DIAMONDS);
        Card c3 = new Card(Rank.JACK, Suit.HEARTS);
        Card c4 = new Card(Rank.JACK, Suit.DIAMONDS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        List<Card> expResult =  new ArrayList<Card>();
        expResult.add(c3);
        expResult.add(c4);
        List<Card> result = Card.findTrumpCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of showTrumpCards method, of class PlayerHand.
     */
    public void testShowTrumpCards4() {
        System.out.println("showTrumpCards4");
        Suit trump = Suit.CLUBS;
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.QUEEN, Suit.DIAMONDS);
        Card c3 = new Card(Rank.JACK, Suit.HEARTS);
        Card c4 = new Card(Rank.JACK, Suit.DIAMONDS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        List<Card> expResult =  new ArrayList<Card>();
        expResult.add(c1);
        List<Card> result = Card.findTrumpCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of showTrumpCards method, of class PlayerHand.
     */
    public void testShowNonTrumpCards() {
        System.out.println("showNonTrumpCards");
        Suit trump = Suit.CLUBS;
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.QUEEN, Suit.DIAMONDS);
        Card c3 = new Card(Rank.JACK, Suit.HEARTS);
        Card c4 = new Card(Rank.JACK, Suit.DIAMONDS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        List<Card> expResult =  new ArrayList<Card>();
        expResult.add(c2);
        expResult.add(c3);
        expResult.add(c4);
        List<Card> result = Card.findNotTrumpCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of showTrumpCards method, of class PlayerHand.
     */
    public void testShowNonTrumpCards2() {
        System.out.println("showNonTrumpCards2");
        Suit trump = Suit.HEARTS;
        Card c1 = new Card(Rank.TEN, Suit.CLUBS);
        Card c2 = new Card(Rank.QUEEN, Suit.DIAMONDS);
        Card c3 = new Card(Rank.JACK, Suit.HEARTS);
        Card c4 = new Card(Rank.JACK, Suit.DIAMONDS);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        List<Card> expResult =  new ArrayList<Card>();
        expResult.add(c1);
        expResult.add(c2);
        List<Card> result = Card.findNotTrumpCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of showTrumpCards method, of class PlayerHand.
     */
    public void testShowNonTrumpCards3() {
        System.out.println("showNonTrumpCards3");
        Suit trump = Suit.SPADES;
        Card c1 = new Card(Rank.TEN, Suit.SPADES);
        Card c2 = new Card(Rank.QUEEN, Suit.SPADES);
        Card c3 = new Card(Rank.JACK, Suit.SPADES);
        Card c4 = new Card(Rank.NINE, Suit.SPADES);
        List<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        List<Card> expResult =  new ArrayList<Card>();
        List<Card> result = Card.findNotTrumpCards(cards, trump);
        assertCardArray(expResult, result);
    }


}
