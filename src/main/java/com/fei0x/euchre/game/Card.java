/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A Card in Euchre, can be compared to other cards, but only to determine if one card is stronger than other card.
 * Sometimes (when cards are of Different suits, and both not trump) cards are not comparable. So this class does not implement comparable.
 * @author jsweetman
 */
public class Card implements Cloneable, Serializable {
    
	/**
	 * ID for Serialization
	 */
	private static final long serialVersionUID = 1L;
	
    /**
     * The rank of this card
     */
    private Rank rank;
    /**
     * The suit of this card
     */
    private Suit suit;

    /**
     * Card Constructor
     * @param r  Rank of the new card
     * @param s  Suit of the new card
     */
    public Card(Rank r, Suit s){
        rank = r;
        suit = s;
    }

    /**
     * Get the rank of this card
     * @return this card's rank
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Get the suit of this card.
     * If trump is specified a left bower will return the trump suit.
     * If trump is null then all jacks return their natural suit.
     * @param trump if trump is null, then trump is not considered. in other words the left bower will return it's natural suit.
     * @return the suit of this card.
     */
    public Suit getSuit(Suit trump) {
        if(trump == null){
            return suit;
        }else if(suit != trump && suit.sameColor(trump) && rank == Rank.JACK){
            //if this card is the left bower return the trump suit, otherwise it is the natural suit.
            return trump;
        }else{
            return suit;
        }
    }

    /**
     * Returns true if this is the jack in the suit chosen trump
     * @return true if this card is the right bower
     */
    public boolean isRightBower(Suit trump){
        if(this.rank == Rank.JACK && this.suit == trump){
            return true;
        }
        return false;
    }


    /**
     * Returns true if this is the jack NOT in the suit chosen trump, but the same color
     * @return true if this card is the left bower
     */
    public boolean isLeftBower(Suit trump){
        if(this.rank == Rank.JACK && this.suit != trump && this.suit.sameColor(trump)){
            return true;
        }
        return false;
    }


    /**
     * Returns true if this card is the same suit as the supplied suit (considering trump)
     * @param trump the suit to test for
     * @param trump the current trump suit
     * @return true if this card is trump
     */
    public boolean isSuit(Suit suit, Suit trump){
    	if (trump == null) return this.suit == suit;
    	
        if(this.suit == trump){
            return true;
        }
        if(this.suit.sameColor(trump) && rank == Rank.JACK){
            return true;
        }
        return this.suit == suit;
    }


    /**
     * Returns true if this card is a trump card
     * @param trump the current trump suit
     * @return true if this card is trump
     */
    public boolean isTrump(Suit trump){
        return isSuit(trump,trump);
    }

    /**
     * Checks to see if the current card has the same rank as the supplied card
     * @param other the card to check against
     * @return true if the cards share the same rank
     */
    public boolean sameRank(Card that){
        return  this.rank == that.rank;
    }
    
    /**
     * Checks to see if the current card has the same suit as the supplied card
     * @param other the card to check against
     * @return true if the cards share the same suit
     */
    public boolean sameSuit(Card that, Suit trump){
    	return this.getSuit(trump) == that.getSuit(trump);
    }
    
    /**
     * Checks to see if the current cart is lower than supplied card. (ignoring trump  suit)
     * @param other the card to check against
     * @return true if supplied card is higher
     */
    public boolean lowerThan(Card that){
        return this.rank.getValue() < that.rank.getValue();
    }

    /**
     * Checks to see if the current cart is higher than supplied card. (ignoring trump)
     * @param other the card to check against
     * @return true if the supplied cart is lower
     */
    public boolean higherThan(Card that){
        return this.rank.getValue() > that.rank.getValue();
    }    
        
    
    /**
     * Checks to see if the current card would beat the supplied card. (Considering trump)
     * @param other the card to check against
     * @return true if the supplied cart is lower (considering trump)
     */
    public boolean beats(Card that, Suit trump){
    	if (this.equals(that)) return false;
    	if (trump == null){
    		if (that.sameSuit(this, trump)) return this.higherThan(that);
    		else return false;
    	}
    	return 
		   (this.isRightBower(trump))                              //every card is lower than the right bower
        || (this.isLeftBower(trump) && !that.isRightBower(trump))  //every card except the right bower is lower than the left bower
        || (this.isTrump(trump) && !that.isTrump(trump))           //every card not trump is lower than a trump
        || (this.higherThan(that) && this.sameSuit(that, trump) && !that.isRightBower(trump) && !that.isLeftBower(trump))     //when both cards are both trump or not trump and neither are bowers, then it comes to rank
        ;                                                    
    }   
    


    /**
     * Given a set of cards and trump, return the top cards. (a hand with trump will return the highest trump) (a hand without trump will return all the cards of the highest rank). assumes that there are no duplicate cards.
     * @param cards the set of cards to look through (unexpected behaviour if the cards contain duplicates, though it should still work.)
     * @param trump the trump suit, if null then find the best cards ignoring trump.
     * @return return the top cards. (a hand with trump will return the highest trump) (a hand without trump will return all the cards of the highest rank)
     */
    public static List<Card> findStrongestCards(List<Card> cards, Suit trump){
        List<Card> topCards = new ArrayList<Card>(); //if more than one they should all share the same rank.
        //iterate through all cards
        for(Card card : cards){
            if(topCards.isEmpty()){ //if it's the first card add it.
                topCards.add(card);
            }else if(card.beats(topCards.get(0),trump)){ //if the card is better replace the current top cards
                topCards.clear();
                topCards.add(card);
            }else{                
                if(trump != null){ //rules when considering trump
                    //the card cannot beat the current card(s), but it can be added if it's equal rank. (assuming current cards are not trump)
                    if(card.sameRank(topCards.get(0)) && !topCards.get(0).isTrump(trump) ){
                        topCards.add(card);
                    }
                }else{ //rules when not considering trump
                	if(card.sameRank(topCards.get(0))){  //add it if it's the same rank
                        topCards.add(card);
                    }else if(card.higherThan(topCards.get(0))){ //replace if it's higher rank
                        topCards.clear();
                        topCards.add(card);
                    }
                }
            }
        }
        return topCards;
    }

    /**
     * Given a set of cards and trump, return the low cards. (only a hand with pure trump will return a trump) (a hand will normally return all the cards of the lowest rank (not trump)). assumes that there are no duplicate cards.
     * @param cards the set of cards to look through (unexpected behaviour if the cards contain duplicates)
     * @param trump the trump suit, if null then find the best cards ignoring trump.
     * @return
     */
    public static List<Card> findWeakestCards(List<Card> cards, Suit trump){
        List<Card> lowCards = new ArrayList<Card>(); //if more than one they should all share the same rank.
        //iterate through all cards
        for(Card card : cards){
            if(lowCards.isEmpty()){ //if it's the first card add it.
                lowCards.add(card);
            }else if(lowCards.get(0).beats(card,trump)){ //if the card is beaten by the current top cards
            	lowCards.clear();
            	lowCards.add(card);
            }else{
                if(trump != null){ //rules when considering trump
                    //the card is not worse than the current card(s), but it can be added if it's equal rank (and not trump).
                    if(card.sameRank(lowCards.get(0)) && !card.isTrump(trump) ){
                        lowCards.add(card);
                    }
                }else{ //rules for not considering trump
                    if(card.sameRank(lowCards.get(0))){  //add it if it's the same rank
                        lowCards.add(card);
                    }else if(card.lowerThan(lowCards.get(0))){ //replace if it's lower rank
                        lowCards.clear();
                        lowCards.add(card);
                    }
                }
            }
        }
        return lowCards;
    }

    /**
     * Returns cards that match the suit (considering trump)
     * @param cards the cards to filter
     * @param suit the suit to test for
     * @param trump the suit that is trump
     * @return all the cards matching suit
     */
    public static List<Card> findCardsOfSuit(List<Card> cards, Suit suit, Suit trump){
    	return cards.stream().filter(c -> c.isSuit(suit, trump)).collect(Collectors.toList());
    }
    
    /**
     * Returns cards that are trump
     * @param cards the cards to filter
     * @param trump the suit that is trump
     * @return all trump cards
     */
    public static List<Card> findTrumpCards(List<Card> cards, Suit trump){
    	return cards.stream().filter(c -> c.isTrump(trump)).collect(Collectors.toList());
    }

    /**
     * Returns cards that are trump
     * @param cards the cards to filter
     * @param trump the suit that is trump
     * @return all trump cards
     */
    public static List<Card> findNotTrumpCards(List<Card> cards, Suit trump){
    	return cards.stream().filter(c -> ! c.isTrump(trump)).collect(Collectors.toList());
    }

    /**
     * Return the cards that may be played given the card that was led and trump (Helpful for not accidentally playing illegal cards!)
     * @param led the card that led (the trick)
     * @param trump the trump so that you know you can play the left bower, or can't play a certain jack.
     * @return the list of cards that may be played.
     */
    public static List<Card> findLegalCards(List<Card> cards, Card led, Suit trump){
        List<Card> legalCards = cards.stream().filter(c -> c.getSuit(trump) == led.getSuit(trump)).collect(Collectors.toList());
        if(legalCards.isEmpty()) return cards; //if no card in hand is legal, then you may play any card
        else return legalCards; 
    }

    
    
    /**
     * Checks to see if the supplied card is the same as the current card.
     * @param other the card to check against
     * @return true if the cards share the same rank and suit (naturally)
     */
    public boolean equals(Card other){
        if(this.rank == other.rank && this.suit == other.suit) return true;
        else return false;
    }

    /**
     * Checks to see if the supplied card is the same as the current card.
     * @param other the card to check against
     * @return true if the cards share the same rank and suit (naturally)
     */
    public boolean equals(Object obj) {
    	if(!(obj instanceof Card)) return false;
    	else return equals((Card)obj);
    }
    
    
    /**
     * The full title of this card
     * @return readable title for this card.
     */
    @Override
    public String toString(){
        return getName();
    }

    /**
     * The full title of this card
     * @return readable title for this card.
     */
    public String getName(){
        return rank + " of " + suit;
    }

    /**
     * Makes a copy of the card, so that players who get their hands on cards, will actually get their hands on copies. that way they can't cheat!!
     * @return a COPY of this card.
     */
    @Override
    protected Card clone(){
        return new Card(rank,suit);
    }

}
