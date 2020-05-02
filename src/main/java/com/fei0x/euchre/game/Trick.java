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
 * An ordered set of plays, placed into an array, at most size 4. (3 if the trick is played in a lone round)
 * The first element in the array is the led play.
 * The full set of 4 (or 3) plays creates the trick, which is taken by a player (who will end up leading the next trick)
 * Tricks in progress will be presented to the players to decide which card to play on the current trick.
 * Past tricks should be accessible to all players at all times through the EuchreGame class. Because everybody has perfect memory....
 * Tricks do not carry references to players to avoid players getting references to othe players
 * @author jsweetman
 */
public class Trick implements Cloneable, Serializable {
	
    /**
	 * ID for Serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The set of ordered plays in this trick (3 or 4 total depending on 'alonePlay')
     */
    private List<Play> plays = new ArrayList<Play>() ;

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
     * Get the plays in this trick.
     * @return all the plays in the trick so far.
     */
    public List<Play> getPlays(){
        return plays;
    }

    /**
     * Get the trump for this trick (which was passed down from the round)
     * @return get the trump
     */
    public Suit getTrump(){
        return trump;
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
     * returns true if this trick is in a round where the play is alone (in which case there would be 3 cards instead of 4)
     * @return true if this trick is in a round where the play is alone (in which case there would be 3 cards instead of 4)
     */
    public boolean isAlonePlay(){
        return alonePlay;
    }
    
    
    /**
     * Trick constructor, for a new empty trick
     * @param trump the trump to be applied to the trick.
     * @param alonePlay is this trick happening in a play alone round (should only have 3 cards instead of 4).
     */
    protected Trick(Suit trump, boolean alonePlay){
        this.trump = trump;
        this.alonePlay = alonePlay;
    }

    /**
     * Add the next play to the trick. (Update the current trick taker, if the play is the last play)
     * If this is called once the trick is complete, nothing happens.
     * Cloning the card as it comes into ensure it's not one a player ai has reference to.
     * @param nextPlay the next play to add.
     */
    protected void addPlay(String player, Card card){
        if(trickComplete()){ return; } //ignore any cards trying to be added to the trick after
        
        plays.add(new Play(player, card.clone()));
        
        if(trickComplete()){
            trickTaker = determineWinningPlay().getPlayer();
        }
    }


    /**
     * Returns if the trick is empty or not
     * @return true if the trick is empty
     */
    public boolean trickEmpty(){
        return plays.isEmpty();
    }


    /**
     * Get the play which led the trick
     * @return the first play of the trick
     * @throws IllegalStateException if the trick is empty (call isTrickEmpty to check)
     */
    public Play ledPlay() throws IllegalStateException {
        if(trickEmpty()){
            throw new IllegalStateException("The trick has not started there is no play led yet.");
        }
        return plays.get(0);
    }

    /**
     * Get the card which led the trick
     * @return the first play of the trick
     * @throws IllegalStateException if the trick is empty (call isTrickEmpty to check)
     */
    public Card ledCard() throws IllegalStateException {
        return ledPlay().getCard();
    }

    /**
     * Get which suit led. If you pass true to consider trump the the left bower will be reported as trump suit. Otherwise it will not.
     * @param considerTrump pass true if you want the left bower to report the suit of trump. false, if you want it to be it's natrual suit.
     * @return the suit of the led card.
     * @throws IllegalStateException if the trick is empty (call isTrickEmpty to check)
     */
    public Suit ledSuit(boolean considerTrump) throws IllegalStateException {
        if(considerTrump){
            return ledCard().getSuit(trump);
        }else{
            return ledCard().getSuit(null);
        }
    }


    /**
     * Get the winning play amongst a list of plays, the first play is assumed to be the led play, and therefore non-trump cards not following suit cannot be the winning play.
     * @param plays the plays to evaluate, the play at element 0 is said to be the led play.
     * @return the winning play. returns null if there are no plays passed in.
     */
    private Play determineWinningPlay(){     
        //sort the cards by which beats which, and take the top
    	Play topPlay = null;
    	for(Play p : plays){
    		if(topPlay == null){
    			topPlay = p;
    		}else if(p.getCard().beats(topPlay.getCard(),trump)){
    			topPlay = p;
    		}
    	}
    	return topPlay;
    }

    /**
     * Returns true if the trick is complete (aka the number of plays = 4)
     * @return true if the trick is complete.
     */
    public boolean trickComplete(){
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
        return plays.stream().map(p -> p.getCard()).anyMatch(c -> c.equals(card));
    }

    /**
     * Makes a copy of the trick, so that players who get their hands on tricks, will actually get their hands on copies. that way they can't cheat!!
     * Need a 'deep' copy to ensure the cards are cloned
     * @return a COPY of this trick.
     */
    @Override
    protected Trick clone(){
        List<Play> clonedplays = plays.stream().map(p -> p.clone()).collect(Collectors.toList());
        return new Trick(clonedplays,trickTaker,trump,alonePlay);
    }

    /**
     * A private constructor for the clone function
     * @param plays
     * @param trickTaker
     */
    private Trick(List<Play> plays, String trickTaker,Suit trump, boolean alonePlay){
        this.plays = plays;
        this.trickTaker = trickTaker;
        this.trump = trump;
        this.alonePlay = alonePlay;
    }

}
