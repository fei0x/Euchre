/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchregame;

import java.util.ArrayList;
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
     * Test of isStrongerThan method, of class Card.
     */
    public void testIsStrongerThan() {
        System.out.println("isStrongerThan");
        Card other = new Card(Rank.JACK, Suit.SPADES);
        Suit trump = Suit.CLUBS;
        Card instance = new Card(Rank.JACK, Suit.CLUBS);
        boolean expResult = true;
        boolean result = instance.isStrongerThan(other, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isStrongerThan method, of class Card.
     */
    public void testIsStrongerThan2() {
        System.out.println("isStrongerThan2");
        Card other = new Card(Rank.JACK, Suit.SPADES);
        Suit trump = Suit.SPADES;
        Card instance = new Card(Rank.JACK, Suit.CLUBS);
        boolean expResult = false;
        boolean result = instance.isStrongerThan(other, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isStrongerThan method, of class Card.
     */
    public void testIsStrongerThan3() {
        System.out.println("isStrongerThan3");
        Card other = new Card(Rank.ACE, Suit.SPADES);
        Suit trump = Suit.SPADES;
        Card instance = new Card(Rank.JACK, Suit.CLUBS);
        boolean expResult = true;
        boolean result = instance.isStrongerThan(other, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isStrongerThan method, of class Card.
     */
    public void testIsStrongerThan4() {
        System.out.println("isStrongerThan4");
        Card other = new Card(Rank.ACE, Suit.SPADES);
        Suit trump = null;
        Card instance = new Card(Rank.JACK, Suit.CLUBS);
        boolean expResult = false;
        boolean result = instance.isStrongerThan(other, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isStrongerThan method, of class Card.
     */
    public void testIsStrongerThan5() {
        System.out.println("isStrongerThan5");
        Card other = new Card(Rank.TEN, Suit.HEARTS);
        Suit trump = null;
        Card instance = new Card(Rank.TEN, Suit.SPADES);
        boolean expResult = false;
        boolean result = instance.isStrongerThan(other, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isStrongerThan method, of class Card.
     */
    public void testIsStrongerThan6() {
        System.out.println("isStrongerThan6");
        Card other = new Card(Rank.TEN, Suit.HEARTS);
        Suit trump = Suit.CLUBS;
        Card instance = new Card(Rank.TEN, Suit.SPADES);
        boolean expResult = false;
        boolean result = instance.isStrongerThan(other, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of isStrongerThan method, of class Card.
     */
    public void testIsStrongerThan7() {
        System.out.println("isStrongerThan7");
        Card other = new Card(Rank.KING, Suit.HEARTS);
        Suit trump = Suit.SPADES;
        Card instance = new Card(Rank.NINE, Suit.SPADES);
        boolean expResult = true;
        boolean result = instance.isStrongerThan(other, trump);
        assertEquals(expResult, result);
    }

    /**
     * Test of findStrongestCards method, of class Card.
     */
    public void testFindStrongestCards() {
        System.out.println("findStrongestCards");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.NINE, Suit.HEARTS));
        cards.add(new Card(Rank.NINE, Suit.DIAMONDS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        Suit trump = null;
        ArrayList expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.SPADES));
        expResult.add(new Card(Rank.JACK, Suit.DIAMONDS));
        ArrayList result = Card.findStrongestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findStrongestCards method, of class Card.
     */
    public void testFindStrongestCards2() {
        System.out.println("findStrongestCards2");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.NINE, Suit.HEARTS));
        cards.add(new Card(Rank.NINE, Suit.DIAMONDS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        Suit trump = Suit.DIAMONDS;
        ArrayList expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.DIAMONDS));
        ArrayList result = Card.findStrongestCards(cards, trump);
        assertCardArray(expResult, result);
    }


    /**
     * Test of findStrongestCards method, of class Card.
     */
    public void testFindStrongestCards3() {
        System.out.println("findStrongestCards3");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.ACE, Suit.HEARTS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        Suit trump = null;
        ArrayList expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.ACE, Suit.HEARTS));
        ArrayList result = Card.findStrongestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findStrongestCards method, of class Card.
     */
    public void testFindStrongestCards4() {
        System.out.println("findStrongestCards4");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.ACE, Suit.HEARTS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        Suit trump = Suit.DIAMONDS;
        ArrayList expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.ACE, Suit.HEARTS));
        ArrayList result = Card.findStrongestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findStrongestCards method, of class Card.
     */
    public void testFindStrongestCards5() {
        System.out.println("findStrongestCards5");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.ACE, Suit.HEARTS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        Suit trump = Suit.CLUBS;
        ArrayList expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.SPADES));
        ArrayList result = Card.findStrongestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findStrongestCards method, of class Card.
     */
    public void testFindStrongestCards6() {
        System.out.println("findStrongestCards6");
        ArrayList<Card> cards = new ArrayList<Card>();
        Suit trump = Suit.CLUBS;
        ArrayList expResult = new ArrayList<Card>();
        ArrayList result = Card.findStrongestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards() {
        System.out.println("findWeakestCards");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.ACE, Suit.CLUBS));
        cards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        cards.add(new Card(Rank.QUEEN, Suit.SPADES));
        cards.add(new Card(Rank.JACK, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        Suit trump = null;
        ArrayList expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.CLUBS));
        expResult.add(new Card(Rank.JACK, Suit.DIAMONDS));
        ArrayList result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards2() {
        System.out.println("findWeakestCards2");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.JACK, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        cards.add(new Card(Rank.ACE, Suit.CLUBS));
        cards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        cards.add(new Card(Rank.QUEEN, Suit.SPADES));
        Suit trump = Suit.SPADES;
        ArrayList<Card> expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.DIAMONDS));
        ArrayList result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards3o1() {
        System.out.println("findWeakestCards3o1");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.ACE, Suit.CLUBS));
        cards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        Suit trump = Suit.HEARTS;
        ArrayList expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.ACE, Suit.CLUBS));
        expResult.add(new Card(Rank.ACE, Suit.DIAMONDS));
        ArrayList result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }
    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards3() {
        System.out.println("findWeakestCards3");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.ACE, Suit.CLUBS));
        cards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        cards.add(new Card(Rank.QUEEN, Suit.SPADES));
        cards.add(new Card(Rank.JACK, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        Suit trump = Suit.HEARTS;
        ArrayList expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.CLUBS));
        expResult.add(new Card(Rank.JACK, Suit.SPADES));
        ArrayList result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards4() {
        System.out.println("findWeakestCards4");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        Suit trump = null;
        ArrayList expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.TEN, Suit.CLUBS));
        ArrayList result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards5() {
        System.out.println("findWeakestCards5");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.JACK, Suit.DIAMONDS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        Suit trump = Suit.CLUBS;
        ArrayList expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.DIAMONDS));
        ArrayList result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards6() {
        System.out.println("findWeakestCards6");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.JACK, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.SPADES));
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        Suit trump = Suit.CLUBS;
        ArrayList expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.TEN, Suit.CLUBS));
        ArrayList result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards7() {
        System.out.println("findWeakestCards7");
        ArrayList<Card> cards = new ArrayList<Card>();
        Suit trump = Suit.CLUBS;
        ArrayList expResult = new ArrayList<Card>();
        ArrayList result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }

    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards8() {
        System.out.println("findWeakestCards8");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.TEN, Suit.SPADES));
        cards.add(new Card(Rank.TEN, Suit.HEARTS));
        Suit trump = Suit.DIAMONDS;
        ArrayList expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.TEN, Suit.CLUBS));
        expResult.add(new Card(Rank.TEN, Suit.SPADES));
        expResult.add(new Card(Rank.TEN, Suit.HEARTS));
        ArrayList result = Card.findWeakestCards(cards, trump);
        assertCardArray(expResult, result);
    }


    /**
     * Test of findWeakestCards method, of class Card.
     */
    public void testFindWeakestCards9() {
        System.out.println("findWeakestCards9");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.JACK, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.HEARTS));
        Suit trump = Suit.DIAMONDS;
        ArrayList expResult = new ArrayList<Card>();
        expResult.add(new Card(Rank.JACK, Suit.CLUBS));
        ArrayList result = Card.findWeakestCards(cards, trump);
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




    private void assertCardArray(ArrayList<Card> expResult, ArrayList<Card> result){
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
}
