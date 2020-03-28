/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchredistributedplay;

import com.n8id.n8euchreexceptions.MissingPlayer;
import com.n8id.n8euchregame.AskGame;
import com.n8id.n8euchregame.Trick;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Overloads all the methods of ask game, to ask them via RMI
 * @author jsweetman
 */
public class AskRemoteGame implements AskGame {

    /**
     * The player's name, the player who this askGame is for.
     */
    private String playerName;

    /**
     * The server containing the game to ask.
     */
    private EuchreServer server;

    /**
     * Session ID
     */
    private long seesionID;


    /**
     * Create an interface to give to the player for asking questions to the game
     * @param sever the server objec to connect to for answers.
     * @param game the game to ask questions to.
     * @param playerName the name of the player, to write in their message names.
     */
    public AskRemoteGame(EuchreServer server, long seesionID, String playerName){
        this.server = server;
        this.seesionID = seesionID;
        this.playerName = playerName;
    }

    /**
     * Write something to the rest of the players output
     * @param somethingToSay something to write to the log/all other players
     */
    @Override
    public void speak(String somethingToSay){
        try {
            server.speak(playerName, seesionID, somethingToSay);
        } catch (RemoteException ex) {
            throw new RuntimeException("Connection Failed.");
        }
    }

    /**
     * returns all of the past tricks in the round. This lets the player know who played every card and all cards played this round.
     * @return an arraylist of all the completed tricks this round. In order in which it was played.
     * @throws IllegalStateException if this is called outside the scope of a round. (but all player functions should be within the round)
     */
    @Override
    public ArrayList<Trick> getPastTricks() throws IllegalStateException{
        try {
            return server.getPastTricks(playerName, seesionID);
        } catch (RemoteException ex) {
            throw new RuntimeException("Connection Failed.");
        }
    }

    /**
     * Returns your team's current score
     * @return your team's current score
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    @Override
    public int getMyTeamsScore() throws MissingPlayer{
        try {
            return server.getMyTeamsScore(playerName, seesionID);
        } catch (RemoteException ex) {
            throw new RuntimeException("Connection Failed.");
        }
    }

    /**
     * Returns your opponents current score
     * @return your opponents current score
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    @Override
    public int getOpponentsScore(){
        try {
            return server.getOpponentsScore(playerName, seesionID);
        } catch (RemoteException ex) {
            throw new RuntimeException("Connection Failed.");
        }
    }

    /**
     * Returns the name of this player's partner
     * @return the name of this player's partner
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    @Override
    public String getPartnersName(){
        try {
            return server.getPartnersName(playerName, seesionID);
        } catch (RemoteException ex) {
            throw new RuntimeException("Connection Failed.");
        }
    }

    /**
     * Returns the 2 names of this player's opponents
     * @return the 2 names of this player's opponents
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    @Override
    public ArrayList<String> getOpponents(){
        try {
            return server.getOpponents(playerName, seesionID);
        } catch (RemoteException ex) {
            throw new RuntimeException("Connection Failed.");
        }
    }

    /**
     * Returns the name of the dealer
     * @return the name of the dealer
     */
    @Override
    public String whoIsDealer(){
        try {
            return server.whoIsDealer(playerName, seesionID);
        } catch (RemoteException ex) {
            throw new RuntimeException("Connection Failed.");
        }
    }


    /**
     * Returns the player who will play/has played first this round. If going alone has not yet been decided, it will be the player after the dealer.
     * @return the player who will play/has played first this round. If going alone has not yet been decided, it will be the player after the dealer.
     */
    @Override
    public String getLeadPlayer(){
        try {
            return server.getLeadPlayer(playerName, seesionID);
        } catch (RemoteException ex) {
            throw new RuntimeException("Connection Failed.");
        }
    }

}
