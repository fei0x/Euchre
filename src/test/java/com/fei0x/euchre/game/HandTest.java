/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchregame;

import com.n8id.n8euchreexceptions.IllegalPlay;
import java.util.ArrayList;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author jsweetman
 */
public class PlayerHandTest extends TestCase {
    
    public PlayerHandTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(PlayerHandTest.class);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.KING, Suit.CLUBS));
        PlayerHand instance = new PlayerHand("player1",cards);
        ArrayList<Card> expResult = cards;
        ArrayList<Card> result = instance.getHand();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayerName method, of class PlayerHand.
     */
    public void testGetPlayerName() {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.KING, Suit.CLUBS));
        PlayerHand instance = new PlayerHand("player1",cards);
        String expResult = "player1";
        String result = instance.getPlayerName();
        assertEquals(expResult, result);
    }

    /**
     * Test of hasCard method, of class PlayerHand.
     */
    public void testHasCard() {
        System.out.println("hasCard");
        Card other = new Card(Rank.QUEEN, Suit.CLUBS);
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.KING, Suit.CLUBS));
        PlayerHand instance = new PlayerHand("player1",cards);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.KING, Suit.CLUBS));
        PlayerHand instance = new PlayerHand("player1",cards);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.KING, Suit.CLUBS));
        PlayerHand instance = new PlayerHand("player1",cards);
        Card expResult = testPull;
        Card result = instance.pullCard(topull);
        assertEquals(expResult.getName(), result.getName());
    }

    /**
     * Test of pullCard method, of class PlayerHand.
     */
    public void testPullCard2() throws Exception {
        System.out.println("pullCard2");
        Card topull = new Card(Rank.TEN, Suit.SPADES);
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.KING, Suit.CLUBS));
        PlayerHand instance = new PlayerHand("player1",cards);
        try{
            Card result = instance.pullCard(topull);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(resultCard);
        PlayerHand instance = new PlayerHand("player1",cards);
        Card expResult = resultCard;
        Card result = instance.swapWithFaceUpCard(toAdd, topull);
        assertEquals(expResult, result);
    }

    /**
     * Test of swapWithFaceUpCard method, of class PlayerHand.
     */
    public void testSwapWithFaceUpCard2() throws Exception {
        System.out.println("swapWithFaceUpCard2");
        Card toAdd = new Card(Rank.TEN, Suit.SPADES);
        Card topull = new Card(Rank.KING, Suit.HEARTS);
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        PlayerHand instance = new PlayerHand("player1",cards);
        try{
            Card result = instance.swapWithFaceUpCard(toAdd, topull);
            assertTrue(false);
        }catch(IllegalPlay ip){
        }
    }

    /**
     * Test of showHand method, of class PlayerHand.
     */
    public void testShowHand() {
        System.out.println("getHand");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.CLUBS));
        cards.add(new Card(Rank.KING, Suit.CLUBS));
        PlayerHand instance = new PlayerHand("player1",cards);
        ArrayList<Card> expResult = cards;
        ArrayList<Card> result = instance.showHand();
        assertCardArray(expResult, result);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        PlayerHand instance = new PlayerHand("player1",cards);
        ArrayList<Card> expResult =  new ArrayList<Card>();
        expResult.add(c4);
        ArrayList<Card> result = instance.showLegalPlays(led, trump);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        PlayerHand instance = new PlayerHand("player1",cards);
        ArrayList<Card> expResult =  new ArrayList<Card>();
        expResult.add(c4);
        ArrayList<Card> result = instance.showLegalPlays(led, trump);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        PlayerHand instance = new PlayerHand("player1",cards);
        ArrayList<Card> expResult =  new ArrayList<Card>();
        expResult.add(c1);
        expResult.add(c2);
        expResult.add(c3);
        ArrayList<Card> result = instance.showLegalPlays(led, trump);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        PlayerHand instance = new PlayerHand("player1",cards);
        ArrayList<Card> expResult =  new ArrayList<Card>();
        expResult.add(c1);
        expResult.add(c2);
        expResult.add(c3);
        expResult.add(c4);
        ArrayList<Card> result = instance.showLegalPlays(led, trump);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        PlayerHand instance = new PlayerHand("player1",cards);
        ArrayList<Card> expResult =  new ArrayList<Card>();
        expResult.add(c2);
        expResult.add(c3);
        expResult.add(c4);
        ArrayList<Card> result = instance.showLegalPlays(led, trump);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        PlayerHand instance = new PlayerHand("player1",cards);
        ArrayList<Card> expResult =  new ArrayList<Card>();
        expResult.add(c1);
        ArrayList<Card> result = instance.showLegalPlays(led, trump);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        PlayerHand instance = new PlayerHand("player1",cards);
        ArrayList<Card> expResult =  new ArrayList<Card>();
        expResult.add(c2);
        expResult.add(c3);
        expResult.add(c4);
        ArrayList<Card> result = instance.showTrumpCards(trump);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        PlayerHand instance = new PlayerHand("player1",cards);
        ArrayList<Card> expResult =  new ArrayList<Card>();
        ArrayList<Card> result = instance.showTrumpCards(trump);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        PlayerHand instance = new PlayerHand("player1",cards);
        ArrayList<Card> expResult =  new ArrayList<Card>();
        expResult.add(c3);
        expResult.add(c4);
        ArrayList<Card> result = instance.showTrumpCards(trump);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        PlayerHand instance = new PlayerHand("player1",cards);
        ArrayList<Card> expResult =  new ArrayList<Card>();
        expResult.add(c1);
        ArrayList<Card> result = instance.showTrumpCards(trump);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        PlayerHand instance = new PlayerHand("player1",cards);
        ArrayList<Card> expResult =  new ArrayList<Card>();
        expResult.add(c2);
        expResult.add(c3);
        expResult.add(c4);
        ArrayList<Card> result = instance.showNonTrumpCards(trump);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        PlayerHand instance = new PlayerHand("player1",cards);
        ArrayList<Card> expResult =  new ArrayList<Card>();
        expResult.add(c1);
        expResult.add(c2);
        ArrayList<Card> result = instance.showNonTrumpCards(trump);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        PlayerHand instance = new PlayerHand("player1",cards);
        ArrayList<Card> expResult =  new ArrayList<Card>();
        ArrayList<Card> result = instance.showNonTrumpCards(trump);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        PlayerHand instance = new PlayerHand("player1",cards);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        PlayerHand instance = new PlayerHand("player1",cards);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        PlayerHand instance = new PlayerHand("player1",cards);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        PlayerHand instance = new PlayerHand("player1",cards);
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
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        PlayerHand instance = new PlayerHand("player1",cards);
        String expResult = "TEN of CLUBS, JACK of HEARTS, ";
        String result = instance.toString();
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of clone method, of class PlayerHand.
     */
    public void testClone() {
        System.out.println("clone");
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        cards.add(new Card(Rank.JACK, Suit.HEARTS));
        PlayerHand instance = new PlayerHand("player1",cards);
        PlayerHand expResult = instance;
        PlayerHand result = instance.clone();
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
