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
 * This example player, plays very agressively, by looking up all legal moves and playing the strongest card, whenever he has two trump he calls it and whenever his team is in the lead, he goes alone.
 * For function descriptions please see the Player class.
 * Tip: don't always play your best card... you wont win.
 * @author jsweetman
 */
public class ExamplePlayerAggressive extends Player {

    Random rdm = new Random(); //a random generator for this player to break strength ties.

    public ExamplePlayerAggressive(String playerName){
        super(playerName);
        //the name of your player goes to the super constructor. like so.
        //Remember: super must be the first line in this function
    }

    @Override
    public boolean callItUp(Card faceUpCard) {
        //the following checks function 'showTrumpCards' shows you all cards you have in trump under a certain suit being trump.
        //we pass in the face-up card's suit as the trump suit, to find how many card's the player has of that suit.
        //then we compare it to see if we have 2 or more cards in that suit.
        Suit potentialTrump = faceUpCard.getSuit(null);
        if(hand.showTrumpCards(potentialTrump).size() >= 2 ){
            return true; //you have 2 of the face-up card's suit in your hand, call it up.
        }
        return false; //you have 0 or 1 cards of the face-up card's suit, don't call it up.
    }

    @Override
    public Card swapWithFaceUpCard(Card faceUpCard) {
        //get all the would-be non-trump cards
        Suit potentialTrump = faceUpCard.getSuit(null);
        ArrayList<Card> cards = hand.showNonTrumpCards(potentialTrump);

        //if your hand is all trump, you got no cards, so just grab the hand
        if(cards.size()==0){
            cards = hand.showHand();
        }
        //find the weakest cards
        cards = Card.findWeakestCards(cards, potentialTrump);

        //shuffle the weakest cards
        Collections.shuffle(cards);
        
        //return a weakest card
        return cards.get(0);
    }

    @Override
    public Suit callSuit(Card turnedDownCard) {
        //check each non-turnedDownCard's suit, if you have to cards of that suit, call it.
        for(Suit suit : Suit.values()){
            if(suit != turnedDownCard.getSuit(null)){ //dont' consider the one that is turned down

                //the following checks function 'showTrumpCards' shows you all cards you have in trump under a certain suit being trump.
                //we pass in the face-up card's suit as the trump suit, to find how many card's the player has of that suit.
                //then we compare it to see if we have 2 or more cards in that suit.
                if(hand.showTrumpCards(suit).size() >= 2 ){
                    return suit; //you have 2 of this suit in your hand, call it up.
                }
            }
        }
        return null; //you do not have two of any suit at this point... we all know this shouldn't happen... but just in case, we'll pass.

    }

    @Override
    public Suit stickTheDealer(Card turnedDownCard) {
        //do what we do for callSuit
        return callSuit(turnedDownCard);
        //Note: callSuit is permitted to throw null, and this function is not.
        //However given our knowledge of the game, our algorithm for callsuit should never return null, so we can be okay risking an
        //   illegalPlay, if this function does return null, the game will take care of it and this players team will automatically lose.
    }

    @Override
    public boolean playAlone() {
        //Check to see if this player's team is in the lead
        if(askGame.getMyTeamsScore() > askGame.getOpponentsScore()){
            //If this player's team is in the lead, then play alone
            return true;
        }
        return false;
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
        }
        //Note: the current trick can tell you who the current leader is, what card led, the suit that led, who played each card and the order the cards where played in.
        //select your strongest cards
        cards = Card.findStrongestCards(cards, currentTrick.getTrump());
        //randomly shuffle your strongest cards
        Collections.shuffle(cards);
        //draw the top strong card
        Card card = cards.get(0);

        //if the player is about to play the right bower, he brags a bit to the table
        if(card.isRightBower(currentTrick.getTrump())){
            askGame.speak("Hand 'em over boys.");
        }
        
        //play the strong card.
        return card;
        //Note: again you don't need to worry about messing with your hand, as soon as your play is validated, your hand is reset.
    }

}