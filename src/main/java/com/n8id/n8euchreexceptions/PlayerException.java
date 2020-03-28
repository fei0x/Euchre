/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchreexceptions;

/**
 * The player cause an exception somewhere in their code, which propogated to the game class.
 * @author jsweetman
 */
public class PlayerException extends Exception {

    String playername;

    /**
     * Plain Constructor
     * @param playername the name of the player who caused the exception
     */
    public PlayerException(String playername){
        super();
        this.playername = playername;
    }

    /**
     * Message Constructor
     * @param message a message
     * @param playername the name of the player who caused the exception
     */
    public PlayerException(String playername, String message){
        super(message);
        this.playername = playername;
    }

    /**
     * Message and Throwable Constructor
     * @param message a message
     * @param cause another throwable
     * @param playername the name of the player who caused the exception
     */
    public PlayerException(String playername, String message, Throwable cause){
        super(message, cause);
        this.playername = playername;
    }

    /**
     * Throwable Constructor
     * @param cause another throwable
     * @param playername the name of the player who caused the exception
     */
    public PlayerException(String playername,Throwable cause){
        super(cause);
        this.playername = playername;
    }

    /**
     * The player who caused the exception
     * @return the name of the player
     */
    public String getPlayerName(){
        return playername;
    }

}
