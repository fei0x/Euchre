/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchreplayers;

import com.n8id.n8euchregame.Card;
import com.n8id.n8euchregame.Suit;
import com.n8id.n8euchregame.Trick;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This is an example contender submission for the n8 Euchre Challenge. A contender needs to only, at minimum, complete this class and functions as shown.
 * This example player, plays completely randomly, and makes completely legal movess.
 * For function descriptions please see the Player class.
 * Tip: don't play euchre randomly... you probably won't win.
 * @author jsweetman
 */
public class ExamplePlayerRandom extends Player {

    Random rdm = new Random(); //a random generator for this random player.

    public ExamplePlayerRandom(String playerName){
        super(playerName);
        //the name of your player goes to the super constructor. like so.
        //Remember: super must be the first line in this function
    }

    @Override
    public boolean callItUp(Card faceUpCard) {
        //the following checks your hand to see if you have a card that shares suit with the face-up card.
        //you will notice that nulls are being passed as parameters to trump fields,
        //  this is ok, as this indicates that trump is not considered when evaluating suit.
        //  (in other words jacks will all 'report' their original suit, and not whatever suit they would take on as the left bower.)
        if(hand.hasSuit(faceUpCard.getSuit(null), null)){
            return rdm.nextBoolean(); //you can legally tell the dealer to pick up the card, so decide randomly.
        }
        return false; //you can not legally tell the dealer to pick up the card, so don't
    }

    @Override
    public Card swapWithFaceUpCard(Card faceUpCard) {
        //any card can be swapped so chose one randomly
        //first get all the cards in your hand.
        ArrayList<Card> cards = hand.showHand();
        //shuffle your hand
        Collections.shuffle(cards);
        //Tip: don't worry too much about messing up your hand, the game will automatically re-send you your correct hand, after every time your hand changes.
        //return the top (random) card
        return cards.get(0); //hopefully it wasn't trump...
    }

    @Override
    public Suit callSuit(Card turnedDownCard) {
        //decide a random suit by drawing a card at random
        //first get all the cards in your hand.
        ArrayList<Card> cards = hand.showHand();
        //shuffle your hand
        Collections.shuffle(cards);
        //draw the top (random) card
        Card card = cards.get(0);
        //check to see if it's trump is the same as the invalid suit (suit of the card turned down). if so pass (return null) if not call that suit.
        if(card.getSuit(null) == turnedDownCard.getSuit(null)){
            return null; //pass calling suit
        }else{
            return card.getSuit(null); //call the random suit.
        }

    }

    @Override
    public Suit stickTheDealer(Card turnedDownCard) {
        //decide a random suit by drawing a card at random
        //first get all the cards in your hand.
        ArrayList<Card> cards = hand.showHand();
        //shuffle your hand
        Collections.shuffle(cards);
        //draw the top (random) card
        Card card = cards.get(0);
        //because the dealer MUST select a suit, and it must be valid, we'll iterate through the cards until we find the first valid suit to call.
        while(card.getSuit(null) == turnedDownCard.getSuit(null)){//check to see if this suit is the invalid suit.
            //remove that card draw the next.
            cards.remove(0);
            card = cards.get(0); //Note: if all this player has is the suit that is turned down. this will throw an exception...
                                 //Otherwise any move we make will make an illegal move, and all routes will lead to automatically losing the game.
                                 //So to avoid this scenario draw the face-up card if you have exclusively that suit.
                                 //For now I will let this exception throw and the game can deal with the exception and make this player's team lose.
        }
        return card.getSuit(null); //call the random suit.
    }

    @Override
    public boolean playAlone() {
        //easy boolean choice, remember to use askGame to ask the game about the current round and score, before making your decision.
        return rdm.nextBoolean();
    }

    @Override
    public Card playCard(Trick currentTrick) {
        ArrayList<Card> cards;
        //first see if you are the lead player this trick
        if(currentTrick.isTrickEmpty()){
            //you are the trick leader all cards are valid.
            cards = hand.showHand();
        }else{
            //you are not the trick leader, find all the legal plays in your hand. (to follow suit)
            cards = hand.showLegalPlays(currentTrick.getLedCard(),currentTrick.getTrump());
//            System.out.println(getName() + "'s hand: " + hand.showHand());
//            System.out.println(getName() + "'s legalCards: " + (new PlayerHand(getName(),cards)).toString());
        }
        //Note: the current trick can tell you who the current leader is, what card led, the suit that led, who played each card and the order the cards where played in.
        //randomly shuffle the cards that can legally be played.
        Collections.shuffle(cards);
        //draw the top (random) card
        Card card = cards.get(0);
        //and play it.
        return card;
        //Note: again you don't need to worry about messing with your hand, as soon as your play is validated, your hand is reset.
    }


}
