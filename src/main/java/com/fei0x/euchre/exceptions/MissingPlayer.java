/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.exceptions;

/**
 * An exception thrown when a player by name cannot be found, for whatever reason.
 * @author jsweetman
 */
public class MissingPlayer extends RuntimeException{
    
	/**
	 * ID for Serialization
	 */
	private static final long serialVersionUID = 1L;
	
	
    String playerName;

    /**
     * Plain Constructor
     * @param playername the name of the player who is missing
     */
    public MissingPlayer(String playername){
        super();
        this.playerName = playername;
    }

    /**
     * Message Constructor
     * @param message a message
     * @param playername the name of the player who is missing
     */
    public MissingPlayer(String playername, String message){
        super(message);
        this.playerName = playername;
    }

    /**
     * Message and Throwable Constructor
     * @param message a message
     * @param cause another throwable
     * @param playername the name of the player who is missing
     */
    public MissingPlayer(String playername, String message, Throwable cause){
        super(message, cause);
        this.playerName = playername;
    }

    /**
     * Throwable Constructor
     * @param cause another throwable
     * @param playername the name of the player who is missing
     */
    public MissingPlayer(String playername,Throwable cause){
        super(cause);
        this.playerName = playername;
    }

    /**
     * The player who is missing
     * @return the name of the player
     */
    public String getPlayerName(){
        return playerName;
    }

}
