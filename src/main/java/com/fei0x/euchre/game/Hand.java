/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fei0x.euchre.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fei0x.euchre.exceptions.IllegalPlay;


/**
 * This class represents the hand of each player.
 * At first should contain 5 cards, and dwindle to 0 as the round proceeds
 * The cards here represent the Player's REAL hand. the hands in the Player class should only be a copy of this hand.
 * @author jsweetman
 */
public class Hand implements Cloneable, Serializable {
    
	/**
	 * ID for Serialization
	 */
	private static final long serialVersionUID = 1L;
	
    /**
     * The player's hand of cards
     */
    private List<Card> cards = new ArrayList<Card>();
    
    /**
     * Holds a set of cards 'a hand' for a player
     * @param playerName the name of the player
     * @param cards the hand for the player
     */
    protected Hand(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * get the hand for this player
     * @return the hand for this player.
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * removes all cards from the hand
     */
    protected void clear(){
    	cards.clear();
    }

    /**
     * Checks to see if this player has the card in question. (card equality is based on rank and suit, not the same obj)
     * @param copy some other copy of the card the player inquires to have.
     * @return true if the player has this card
     */
    public boolean hasCard(Card other) {
       return cards.stream().anyMatch(c -> c.equals(other));
    }

    /**
     * Removes a card from the players hand. (to be used when playing cards)
     * @param to pull the intended card to pull from the hand (well, a copy of it, uses equals to check equivalency)
     * @return the card being removed from the hand. (should use this copy of the hand to add to the trick)
     * @throws IllegalPlay throws an IllegalPlay exception if this player does not have this card to pull
     */
    protected Card pullCard(Card topull) throws IllegalPlay {
        boolean found = cards.removeIf(c -> c.equals(topull));       
        if (found == false) throw new IllegalPlay("", "The card " + topull.name() + " is not in player's hand to pull/play.");
        else return topull.clone(); //extra safety that we don't use the card the player has a reference to.  
    }


    /**
     * Removes a card from the player's hand (using the pullCard function) and then replaces it with a new card (the only use case is currently the face-up card from the kitty)
     * @param toAdd the card to add
     * @param topull the intended card to pull from the hand ( a copy of it, uses equals to check equivalence)
     * @return  the card being removed from the hand. (should use this copy of the hand to add back to the kitty)
     * @throws IllegalPlay throws an IllegalPlay exception if this player does not have this card to pull (or if the card to add was already in the player's hand)
     */
    protected Card swapCard(Card toAdd, Card topull) throws IllegalPlay{
        if (hasCard(toAdd)){
            throw new IllegalPlay("", "The card " + toAdd.name() + " which claims to be from the kitty and is being swapped in-hand is already in the player's hand");
        }else{  //note: pull card validates that the player has the card to pull.
            cards.add(toAdd);
            return pullCard(topull);
        }
    }


    /**
     * Returns true if the player has a card of that suit, provided the given trump. If you wish to not consider trump, pass null to the trump parameter, and left bowers will be considered as their natural suit.
     * @param suit the suit the player wishes to look for
     * @param trump the trump to consider for left bowers, null if you wish to not consider left bowers by their modified suit.
     * @return true if the hand contains at least one card of that suit.
     */
    public boolean hasSuit(Suit suit, Suit trump){
    	return cards.stream().anyMatch(c -> c.getSuit(trump) == suit);
    }

    /**
     * Writes the hand to a string
     * @return a string of each card in the hand
     */
    public String toString(){
    	return cards.stream().map(c -> c.toString()).collect(Collectors.joining(", "));
    }

    /**
     * Makes a copy of the hand, so that players who get access to other player's hands, will actually get copies. that way they can't cheat!!
     * @return a deep COPY of this hand.
     */
    @Override
    protected Hand clone(){
        List<Card> clonecards = cards.stream().map(c -> c.clone()).collect(Collectors.toList());
        return new Hand(clonecards);
    }

}
