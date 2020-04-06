/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n8id.n8euchregame;

import com.n8id.n8euchreexceptions.IllegalPlay;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;


/**
 * This class represents the hand of each player.
 * At first should contain 5 cards, and dwindle to 0 as the round proceeds
 * The card's here represent the Player's REAL hand. the hands in the Player class should only be a copy of this hand.
 * 'Abstinence is the best means of prevention.'
 * @author jsweetman
 */
public class PlayerHand implements Cloneable, Serializable {

    /**
     * The player's hand of cards
     */
    private ArrayList<Card> hand = new ArrayList<Card>();
    /**
     * The Player's name
     */
    private String playerName;

    /**
     * Holds a set of cards 'a hand' for a player
     * @param playerName the name of the player
     * @param hand the hand for the player
     */
    public PlayerHand(String playerName, ArrayList<Card> hand) {
        this.playerName = playerName;
        this.hand = hand;
    }

    /**
     * get the hand for this player
     * @return the hand for this player.
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * get the name of this player
     * @return the name of this player.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Checks to see if this player has the card in question.
     * @param copy some other copy of the card the player inquires to have.
     * @return true if the player has this card
     */
    public boolean hasCard(Card other) {
        for(Card card: hand){
            if(card.equals(other)){
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a card from the players hand. (to be used when playing cards)
     * @param topull the intended card to pull from the hand (well, a copy of it, uses equals to check equivalancy)
     * @return the card being removed from the hand. (should use this copy of the hand to add to the trick)
     * @throws IllegalPlay throws an IllegalPlay exception if this player does not have this card to pull
     */
    public Card pullCard(Card topull) throws IllegalPlay {
        ListIterator i = hand.listIterator();
        while(i.hasNext()){
            Card currCard = (Card)i.next();
            if(currCard.equals(topull)){//card is the same as the card passed
                i.remove();//remove the card
                return currCard; //return the removed card.
            }
        }
        throw new IllegalPlay(playerName, "The " + topull.getName() + " is not in " + playerName + "'s hand to pull/play.");
    }


    /**
     * Removes a card from the player's hand (using the pullCard function) and then replaces it with a new card
     * @param toAdd the card to add
     * @param topull the intended card to pull from the hand (well, a copy of it, uses equals to check equivalancy)
     * @return  the card being removed from the hand. (should use this copy of the hand to add back to the kitty)
     * @throws IllegalPlay throws an IllegalPlay exception if this player does not have this card to pull (or if the card to add was already in the player's hand)
     */
    public Card swapWithFaceUpCard(Card toAdd, Card topull) throws IllegalPlay{
        if (hasCard(toAdd)){
            throw new IllegalPlay(playerName, "The card " + toAdd.getName() + " which claims to be from the kitty and is being swapped in-hand is already in " + playerName + "'s hand");
        }else{ //note: pullcard validates that the player has the card to pull.
            hand.add(toAdd);
            return pullCard(topull);
        }
    }

    /**
     * Return the cards in this hand
     * @return the cards in the hand
     */
    public ArrayList<Card> showHand(){
        return hand;
    }

    /**
     * Return the cards that may be played given the suit that was led (Helpful for not accidentally playing illegal cards!)
     * @param led the card that led (the trick)
     * @param trump the trump so that you know you can play the left bower, or can't play a certain jack.
     * @return the list of cards that may be played.
     */
    public ArrayList<Card> showLegalPlays(Card led, Suit trump){
        ArrayList<Card> legalCards = new ArrayList<Card>();
        for(Card card: hand){
            if(card.getSuit(trump) == led.getSuit(trump)){
                legalCards.add(card);
            }
        }
        if(legalCards.isEmpty()){
            return hand;
        }else{
            return legalCards;
        }
    }

    /**
     * Returns only cards that are trump from the player's hand
     * @param trump the suit that is trump
     * @return all trump cards in the player's hand
     */
    public ArrayList<Card> showTrumpCards(Suit trump){
        ArrayList<Card> trumpCards = new ArrayList<Card>();
        for(Card card: hand){
            if(card.getSuit(trump) == trump){
                trumpCards.add(card);
            }
        }
        return trumpCards;
    }


    /**
     * Returns only cards that are NOT trump from the player's hand
     * @param trump the suit that is trump
     * @return all non-trump cards in the player's hand
     */
    public ArrayList<Card> showNonTrumpCards(Suit trump){
        ArrayList<Card> trumpCards = new ArrayList<Card>();
        for(Card card: hand){
            if(card.getSuit(trump) != trump){
                trumpCards.add(card);
            }
        }
        return trumpCards;
    }


    /**
     * Returns true if the player has a card of that suit, provided the given trump. If you wish to not consider trump, pass null to the trump parameter, and left bowers will be considered as their natural suit.
     * @param suit the suit the player wishes to look for
     * @param trump the trump to consider for left bowers, null if you wish to not consider left bowers by their modified suit.
     * @return true if the hand contains at least one card of that suit.
     */
    public boolean hasSuit(Suit suit, Suit trump){
        for(Card card : hand){
            if(card.getSuit(trump) == suit){
                return true;
            }
        }
        return false;

    }

    /**
     * Writes the hand to a string
     * @return
     */
    public String toString(){
        String stringhand = "";
        for(Card card : hand){
            stringhand = stringhand + card.toString() + ", ";
        }
        return stringhand;
    }

    /**
     * Makes a copy of the playerhand, so that players who get their hands on player hands, will actually get their hands on copys. that way they can't cheat!!
     * @return a COPY of this player's hand.
     */
    @Override
    public PlayerHand clone(){
        ArrayList<Card> clonecards = new ArrayList<Card>();
        for(Card card : hand){
            clonecards.add(card.clone());
        }
        return new PlayerHand(new String(playerName),clonecards);
    }

}
