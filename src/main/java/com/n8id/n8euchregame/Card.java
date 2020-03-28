/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchregame;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * A Card in euchre, can be compared to other cards, but only to determine if one card is stronger than other card.
 * As sometimes (when cards are of differen suits, and both not trump) cards are not compariable.
 * Which is why this class does not implement comparable.
 * @author jsweetman
 */
public class Card implements Cloneable, Serializable {

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
     * Returns true if this card is a trump card
     * @param trump the current trump suit
     * @return true if this card is trump
     */
    public boolean isTrump(Suit trump){
        if(suit == trump){
            return true;
        }
        if(suit.sameColor(trump) && rank == Rank.JACK){
            return true;
        }
        return false;
    }

    /**
     * Returns true if this card is stronger than some other card, given the current Trump
     * @param other the other card to compare against
     * @param trump the current trump (if trump is null, assume no trump)
     * @return true if this card is stronger than the other card, false if they cannot compare (both different non-trump suits) or the card is weaker.
     */
    public boolean isStrongerThan(Card other, Suit trump){
        boolean thisIsTrump = (trump != null) ? this.isTrump(trump) : false;
        boolean otherIsTrump = (trump != null) ? other.isTrump(trump) : false;
        boolean sameSuit = (this.getSuit(trump) == other.getSuit(trump));
        if(!thisIsTrump && !otherIsTrump){//if neither is trump
            if(sameSuit){
                //same suit, simple value comparison
                if(this.rank.getValue() > other.rank.getValue()){
                    return true;//same suit and this card has a higher value, therefore it is stronger
                }
            }
            return false; //otherwise they do not compare, and therefore it is NOT stronger than the other.
        }else if(thisIsTrump && !otherIsTrump){//only this card is trump
            return true;
        }else if(!thisIsTrump && otherIsTrump){//only the other card is trump
            return false;
        }else{//BOTH are trump
           //check jacks first
           if(this.isRightBower(trump)){
               return true; //this right bower
           }else if(other.isRightBower(trump)){
               return false; //others right bower
           }else if(this.isLeftBower(trump)){
               return true;  //this left bower
           }else if(other.isLeftBower(trump)){
               return false; //others left bower
           }else{
                //with jacks out of the way remaining cards can be value compared normally.
                if(this.rank.getValue() > other.rank.getValue()){
                    return true;//same suit and this card has a higher value, therefore it is stronger
                }else{
                    return false;
                }
           }
        }
    }

    /**
     * Given a set of cards and trump, return the top cards. (a hand with trump will return the highest trump) (a hand without trump will return all the cards of the highest rank). assumes that there are no duplicate cards.
     * @param cards the set of cards to look through (unexpected behaviour if the cards contain duplicates, though it should still work.)
     * @param trump the trump suit, if null then find the best cards ignoring trump.
     * @return return the top cards. (a hand with trump will return the highest trump) (a hand without trump will return all the cards of the highest rank)
     */
    public static ArrayList<Card> findStrongestCards(ArrayList<Card> cards, Suit trump){
        ArrayList<Card> topCards = new ArrayList<Card>(); //if more than one they should all share the same rank.
        //iterate through all cards
        for(Card card : cards){
            if(topCards.isEmpty()){//if it's the first card add it.
                topCards.add(card);
            }else if(card.isStrongerThan(topCards.get(0),trump)){//see if this card is stronger than the current top card(s)
                //if the card is stronger replace them all.
                topCards.clear();
                topCards.add(card);
            }else{
                //rules when considering trump
                if(trump != null){
                    //the given card is not stronger than the current stronges, but check to see if it is of the same rank.
                    //if it IS and is NOT trump, then the card is equally strong.
                    if(card.rank == topCards.get(0).rank && !card.isTrump(trump)){
                        topCards.add(card);//add it if it's the same strength
                    }
                }else{//rules when not considering trump
                    if(card.rank == topCards.get(0).rank){
                        topCards.add(card);//add it if it's the same strength
                    }else if(card.rank.getValue() > topCards.get(0).getRank().getValue()){
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
    public static ArrayList<Card> findWeakestCards(ArrayList<Card> cards, Suit trump){
        ArrayList<Card> lowCards = new ArrayList<Card>(); //if more than one they should all share the same rank.
        //iterate through all cards
        for(Card card : cards){
            if(lowCards.isEmpty()){//if it's the first card add it.
                lowCards.add(card);
            }else if(card.isStrongerThan(lowCards.get(0),trump)){//see if this card is stronger than the current low card(s)
                //if the card is stronger do nothing
            }else{
                //rules when considering trump
                if(trump != null){
                    //if it is trump, see if it is lower
                    if(lowCards.get(0).isTrump(trump)){
                        if((lowCards.get(0).isRightBower(trump))//everycard is lower than the right bower
                                || (lowCards.get(0).isLeftBower(trump) && !card.isRightBower(trump))//everycard except the right bower is lower than the left bower
                                || (!card.isTrump(trump)) //every card not trump is lower than a trump
                                || (lowCards.get(0).getRank().getValue() > card.getRank().getValue() && card.getRank() != Rank.JACK) //when both cards are trump and the new card is has lower natural value (exlcuding bower cases, cause it would be higer)
                                ){
                            //replace lower cards in all bower scenarios
                            lowCards.clear();
                            lowCards.add(card);
                        }//otherwise the other card is stronger
                    }else{
                        if(!card.isTrump(trump)){//if card is trump it's not lower than not trump.
                            //so we just compare suit
                            if(card.rank == lowCards.get(0).rank){//if they are equal add it
                                lowCards.add(card);
                            }else if(card.rank.getValue() < lowCards.get(0).rank.getValue()){//if it is less than replace them
                                lowCards.clear();
                                lowCards.add(card);
                            }
                        }
                    }
                //rules for not considering trump (a lot easier)
                }else{
                    //so we just compare suit
                    if(card.rank == lowCards.get(0).rank){//if they are equal add it
                        lowCards.add(card);
                    }else if(card.rank.getValue() < lowCards.get(0).rank.getValue()){//if it is less than replace them
                        lowCards.clear();
                        lowCards.add(card);
                    }
                }
            }
        }
        return lowCards;
    }

    /**
     * Checks to see if the supplied card is the same as the current card.
     * @param other the card to check against
     * @return true if the cards share the same rank and suit (naturally)
     */
    public boolean equals(Card other){
        if(this.rank == other.rank && this.suit == other.suit){
            return true;
        }else{
            return false;
        }
    }

    /**
     * The full title of this card
     * @return readable title for this card.
     */
    @Override
    public String toString(){
        return rank + " of " + suit;
    }

    /**
     * The full title of this card
     * @return readable title for this card.
     */
    public String getName(){
        return rank + " of " + suit;
    }

    /**
     * Makes a copy of the card, so that players who get their hands on cards, will actually get their hands on copys. that way they can't cheat!!
     * @return a COPY of this card.
     */
    @Override
    protected Card clone(){
        return new Card(rank,suit);
    }

}
