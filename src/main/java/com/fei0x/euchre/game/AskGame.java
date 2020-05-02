/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.game;

import java.util.List;

import com.fei0x.euchre.exceptions.MissingPlayer;

/**
 * Interface to define how players can see what is happening in the game.
 * The player AI will use this 'window' to gather all the information they need to determine what moves they will make, when prompted.
 * @author jsweetman
 */
public interface AskGame {

    /**
     * returns this players name
     * @return this players name
     */
    public String myName();
	
    /**
	 * returns the name of this players' partner
     * @return the name of this players' partner
	 */
    public String partnersName();
    
	/**
	 * returns a copy of the cards in this players hand
     * @return a copy of the cards in this players hand
	 */
    public List<Card> myHand();
    
    /**
     * Write something to the rest of the players output
     * @param somethingToSay something to write to the log/all other players
     */
    public void speak(String somethingToSay);
    
    /**
     * Returns your team's name
     * @return your team's name
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    public String myTeamName();
    
    /**
     * Returns opponent's team name
     * @return opponent's team name
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    public String opponentsTeamName();
    
    /**
     * Returns the 2 names of this player's opponents
     * @return the 2 names of this player's opponents
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    public List<String> opponentsNames();
    
    /**
     * Returns your team's current score
     * @return your team's current score
     */
    public int myTeamsScore();
    
    /**
     * Returns your opponents current score
     * @return your opponents current score
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    public int opponentsScore();
      
    /**
     * Returns the name of the dealer
     * @return the name of the dealer
     */
    public String whoIsDealer();
    
    /**
     * Returns the player who will play/has played first this round. If going alone has not yet been decided, it will be the player after the dealer.
     * @return the player who will play/has played first this round. If going alone has not yet been decided, it will be the player after the dealer.
     */
    public String whoIsLeadPlayer();

    /**
     * returns all of the past tricks in the round. This lets the player know who played every card and all cards played this round. (this tricks are cloned so the ai player wont be able to manipulate the game.
     * @return a list of all the completed tricks this round. In order in which it was played.
     */
    public List<Trick> pastTricks();
    
}
