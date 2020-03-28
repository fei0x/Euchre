/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchregame;

import com.n8id.n8euchreexceptions.IllegalPlay;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * An ordered set of plays, placed into an array, at most size 4. (3 if the trick is played in a lone round)
 * The first element in the array is the led play.
 * The full set of 3/4 plays creats the trick, which is taken by a player (who will end up leading the next trick)
 * Tricks in progress will be presented to the players to decide which card to play on the current trick.
 * Past tricks should be accessible to all players at all times through the EuchreGame class. Because everybody has perfect memory....
 * @author jsweetman
 */
public class Trick implements Cloneable, Serializable {

    /**
     * The set of ordered plays in this trick (3 or 4 total depending on 'alonePlay')
     */
    private ArrayList<Play> plays = new ArrayList<Play>() ;

    /**
     * A record of the player who took this trick.
     * If the trick is not complete, it is the player currently taking the trick.
     */
    private String trickTaker = null;

    /**
     * The Trump for the trick. Used to determine trickTaker.
     */
    private Suit trump;

    /**
     * indicates if this trick is in a round which is played alone, in which case there are only 3 cards instead of 4
     */
    private boolean alonePlay;

    /**
     * An empty trick constructor
     */
    public Trick(Suit trump, boolean alonePlay){
        this.trump = trump;
        this.alonePlay = alonePlay;
    }

    /**
     * Add the next play to the trick. Calculate the trick taker (current leader or final leader depending on the number of tricks played)
     * If this is called once the trick is comlete, nothing happens.
     * @param nextPlay the next play to add.
     */
    public void addPlay(String player, Card card){
        if(isTrickComplete()){ return; }
        plays.add(new Play(player, card));
        trickTaker = getWinningPlay(plays, trump).getPlayer();
    }


    /**
     * Returns if the trick is empty or not
     * @return true if the trick is empty
     */
    public boolean isTrickEmpty(){
        return plays.isEmpty();
    }


    /**
     * Get the play which led the trick
     * @return the first play of the trick
     * @throws IllegalStateException if the trick is empty (call isTrickEmpty to check)
     */
    public Play getLedPlay() throws IllegalStateException {
        if(isTrickEmpty()){
            throw new IllegalStateException("The trick is not complete. There is no trick taker yet.");
        }
        return plays.get(0);
    }

    /**
     * Get the card which led the trick
     * @return the first play of the trick
     * @throws IllegalStateException if the trick is empty (call isTrickEmpty to check)
     */
    public Card getLedCard() throws IllegalStateException {
        return getLedPlay().getCard();
    }


    /**
     * Get which suit led. If you pass true to consider trump the the left bower will be reported as trump suit. Otherwise it will not.
     * @param considerTrump pass true if you want the left bower to report the suit of trump. false, if you want it to be it's natrual suit.
     * @return the suit of the led card.
     * @throws IllegalStateException if the trick is empty (call isTrickEmpty to check)
     */
    public Suit getLedSuit(boolean considerTrump) throws IllegalStateException {
        if(considerTrump){
            return getLedCard().getSuit(trump);
        }else{
            return getLedCard().getSuit(null);
        }
    }


    /**
     * Get the plays in this trick.
     * @return all the plays in the trick so far.
     */
    public ArrayList<Play> getPlays(){
        return plays;
    }

    /**
     * Get the winning play for this trick
     * @return the winning play
     * @throws IllegalStateException if the trick is not complete, a winner is not defined.
     */
    public Play getWinningPlay()throws IllegalStateException{
        if(!isTrickComplete()){
            throw new IllegalStateException("The trick is not complete. There is no winning play.");
        }
        return Trick.getWinningPlay(plays, trump);
    }
    
    /**
     * Get the winning play amongst a list of plays, the first play is assumed to be the led play, and therefore non-trump cards not following suit cannot be the winning play.
     * @param plays the plays to evaluate, the play at element 0 is said to be the led play.
     * @return the winning play. returns null if there are no plays passed in.
     */
    private static Play getWinningPlay(ArrayList<Play> plays, Suit currentTrump ){
        Play topPlay = null;
        //iterate through all plays
        for(Play play : plays){
            if(topPlay == null){//if it's the first card set it.
                topPlay = play;
            }else if(play.getCard().isStrongerThan(topPlay.getCard(),currentTrump)){//see if this card is stronger than the top one
                //if the card is stronger swap it.
                topPlay = play;
            }//otherwise it's no better.. do nothing.
        }
        return topPlay;

    }

    /**
     * Returns the winner of the trick. If the trick is empty
     * then this function throws an IllegalStateException. Check isTrickComplete to see if the trick is empty.
     * @return
     */
    public String getTrickTaker() throws IllegalStateException {
        if(trickTaker == null){
            throw new IllegalStateException("The trick is not complete. There is no trick taker yet.");
        }
        return trickTaker;
    }

    /**
     * Returns true if the trick is complete (aka the number of plays = 4)
     * @return true if the trick is complete.
     */
    public boolean isTrickComplete(){
        if((!alonePlay && plays.size() == 4)
                || (alonePlay && plays.size() == 3)){
            return true;
        }else{
            return false;
        }
    }


    /**
     * Checks to see if the supplied card is present in this trick. 
     * @param card the card to look for.
     * @return true if the card is in the trick.
     */
    public boolean hasCard(Card card){
        for(Play play: plays){
            if(play.getCard().equals(card)){
                return true;
            }
        }
        return false;
    }

    /**
     * Get the trump for this trick (which was passed down from the round)
     * @return get the trump
     */
    public Suit getTrump(){
        return trump;
    }

    /**
     * Makes a copy of the trick, so that players who get their hands on tricks, will actually get their hands on copys. that way they can't cheat!!
     * @return a COPY of this trick.
     */
    @Override
    protected Trick clone(){
        ArrayList<Play> cloneplays = new ArrayList<Play>();
        for(Play play : plays){
            cloneplays.add(play.clone());
        }
        return new Trick(cloneplays,trickTaker,trump,alonePlay);
    }


    /**
     * A private constructor for the clone function
     * @param plays
     * @param trickTaker
     */
    private Trick(ArrayList<Play> plays, String trickTaker,Suit trump, boolean alonePlay){
        this.plays = plays;
        this.trickTaker = trickTaker;
        this.trump = trump;
        this.alonePlay = alonePlay;
    }

}
