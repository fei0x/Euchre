/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.exceptions;

/**
 * An IllegalPlay exception, for the the card laid shouldn't have been played. A Good reason to make the player lose the game.
 * @author jsweetman
 */
public class IllegalPlay extends Exception {

    /**
	 * ID for Serialization
	 */
	private static final long serialVersionUID = 1L;
	
	
	String playername;

    /**
     * Plain Constructor
     * @param playername the name of the player who made an illegal play
     */
    public IllegalPlay(String playername){
        super();
        this.playername = playername;
    }

    /**
     * Message Constructor
     * @param message a message
     * @param playername the name of the player who made an illegal play
     */
    public IllegalPlay(String playername, String message){
        super(message);
        this.playername = playername;
    }

    /**
     * Message and Throwable Constructor
     * @param message a message
     * @param cause another throwable
     * @param playername the name of the player who made an illegal play
     */
    public IllegalPlay(String playername, String message, Throwable cause){
        super(message, cause);
        this.playername = playername;
    }

    /**
     * Throwable Constructor
     * @param cause another throwable
     * @param playername the name of the player who made an illegal play
     */
    public IllegalPlay(String playername,Throwable cause){
        super(cause);
        this.playername = playername;
    }

    /**
     * The player who made the illegal play
     * @return the name of the player
     */
    public String getPlayerName(){
        return playername;
    }

}
