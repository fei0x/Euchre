/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchreexceptions;

/**
 * An exception thrown when a player by name cannot be found, for whatever reason.
 * @author jsweetman
 */
public class MissingPlayer extends RuntimeException{

    String playername;

    /**
     * Plain Constructor
     * @param playername the name of the player who is missing
     */
    public MissingPlayer(String playername){
        super();
        this.playername = playername;
    }

    /**
     * Message Constructor
     * @param message a message
     * @param playername the name of the player who is missing
     */
    public MissingPlayer(String playername, String message){
        super(message);
        this.playername = playername;
    }

    /**
     * Message and Throwable Constructor
     * @param message a message
     * @param cause another throwable
     * @param playername the name of the player who is missing
     */
    public MissingPlayer(String playername, String message, Throwable cause){
        super(message, cause);
        this.playername = playername;
    }

    /**
     * Throwable Constructor
     * @param cause another throwable
     * @param playername the name of the player who is missing
     */
    public MissingPlayer(String playername,Throwable cause){
        super(cause);
        this.playername = playername;
    }

    /**
     * The player who is missing
     * @return the name of the player
     */
    public String getPlayerName(){
        return playername;
    }

}
