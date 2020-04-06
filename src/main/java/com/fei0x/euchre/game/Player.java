/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.game;

import com.fei0x.euchre.player_ai.PlayerAI;

/**
 * This abstract class outlines all of the required functionality a contender must fill out to implement a player
 * There are private elements and game called functions that cannot be manipulated by the player.
 * A contender is cheating if he/she uses introspection to change the members/functions in this class he/she is not entitled to.
 * @author jsweetman
 */
public class Player {

    /***************
     * The following private members cannot be touched by implementations by the player, as they are controlled by the game.
     * (eg. Player's cannot cheat by putting cards in their hand they do not have, or change their name. etc.)
     ****************/

    /**
     * players name, private so you can't change it.
     */
    private String name;

    /**
     * The AI algorithms that this player uses
     */
    private PlayerAI ai;
    
    /**
     * This is the hand for the player's current round of Euchre
     * The Round will control the data in the hand
     */
    protected Hand hand;

    /**
     * The interface for asking the current game of Euchre questions about the game, such as score, player names, player teams, past tricks, etc.
     * Assigned at the creation of each game.
     */
    protected AskGame askGame;

    /**
     * Protected constructor for the player class. Implementations should use this constructor and pass an imutable name to represent this player to other players and to the game mechanics
     * @param name the name of this player
     */
    public Player(String name, PlayerAI ai){
        this.name = name;
        this.ai = ai;
    }

    /**
     * Returns the player's name
     * @return the player's name
     */
    public final String getName(){
        return name;
    }

    /**
     * Returns the player's ai controls
     * @return the player's ai controls
     */
    public PlayerAI getAi(){
        return ai;
    }    

    /**
     * Returns the player's hand
     * @return the player's hand
     */
    public final Hand getHand(){
        return hand;
    }
    
    /**
     * Returns the player's name with team (if available)
     * @return the player's name with team (if available)
     */
    public final String getTeamAndName(){
    	if(askGame != null){
    		return askGame.myTeamName() + ": " + name;
    	}
    	return name;
        
    }

    /**
     * Sets the player's hand, so that we can be sure that the player has a hand that is always correct.
     * Player implementers should avoid using this function, it is only meant to be set by the game class.
     * @param handCopy a copy of the player's hand so that the player can see what's in their hand.
     */
    public void setHand(Hand handCopy) {
        this.hand = handCopy;
    }

    /**
     * Used by the game, the game sets this to allow the player an interface for asking questions about the game.
     * @param askGame the askGame class to set
     */
    public void setAskGame(AskGame askGame){
        this.askGame = askGame;
        ai.setAskGame(askGame);
    }


   
}
