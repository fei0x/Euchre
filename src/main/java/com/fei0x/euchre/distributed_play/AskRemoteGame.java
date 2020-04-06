/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.distributed_play;

import com.fei0x.euchre.exceptions.MissingPlayer;
import com.fei0x.euchre.game.AskGame;
import com.fei0x.euchre.game.Card;
import com.fei0x.euchre.game.Player;
import com.fei0x.euchre.game.Trick;

import java.rmi.RemoteException;
import java.util.List;

/**
 * This class is used by the Euchre Client, the Remote Player AI uses it to ask questions about the game (following the interface as local server players) 
 * Overloads all the methods of ask game, to ask them via RMI
 * @author jsweetman
 */
public class AskRemoteGame implements AskGame {
    /**
     * The server containing the game to ask.
     */
    private EuchreServer server;

    /**
     * Session ID
     */
    private long seesionID;

    /**
     * The player who this askGame is for.
     */
    private Player player;

    /**
     * The player's partner
     */
    private String playerName;


   

    /**
     * Create an interface to give to the player for asking questions to the game
     * @param sever the server object to connect to for answers.
     * @param game the game to ask questions to.
     * @param playerName the name of the player, to write in their message names.
     */
    public AskRemoteGame(EuchreServer server, long seesionID, Player player){
        this.server = server;
        this.seesionID = seesionID;
        this.player = player;
    }


    /**
    * returns this players name
    * @return this players name
    */
    @Override
	public String myName() {
		return playerName;
	}
    
    
    
    /***
     * The following methods are normally asked directly from the game,
     * but in this case we need to go to the connected server first, and then it can ask the game on our behalf
     */  

	/**
	 * returns a copy of the cards in this players hand
    * @return a copy of the cards in this players hand
	 */
    @Override
	public List<Card> myHand() {
        try {
            return server.myHand(playerName, seesionID);
        } catch (RemoteException ex) {
            throw new RuntimeException("Connection Failed.");
        }
	}

    
	/**
	 * returns the name of this players' partner
     * @return the name of this players' partner
	 */
    @Override
	public String partnersName() {
        try {
            return server.partnersName(playerName, seesionID);
        } catch (RemoteException ex) {
            throw new RuntimeException("Connection Failed.");
        }
	}

    /**
     * Returns your team's name
     * @return your team's name
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    @Override
    public String myTeamName(){
        try {
            return server.myTeamName(playerName, seesionID);
        } catch (RemoteException ex) {
            throw new RuntimeException("Connection Failed.");
        }
    }

    /**
     * Returns opponent's team name
     * @return opponent's team name
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    @Override
    public String opponentsTeamName(){
        try {
            return server.opponentsTeamName(playerName, seesionID);
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
    public List<String> opponentsNames(){
        try {
            return server.opponentsNames(playerName, seesionID);
        } catch (RemoteException ex) {
            throw new RuntimeException("Connection Failed.");
        }
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
     * @return a list of all the completed tricks this round. In order in which it was played.
     * @throws IllegalStateException if this is called outside the scope of a round. (but all player functions should be within the round)
     */
    @Override
    public List<Trick> pastTricks() throws IllegalStateException{
        try {
            return server.pastTricks(player.getName(), seesionID);
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
    public int myTeamsScore() throws MissingPlayer{
        try {
            return server.myTeamsScore(player.getName(), seesionID);
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
    public int opponentsScore(){
        try {
            return server.opponentsScore(player.getName(), seesionID);
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
            return server.whoIsDealer(player.getName(), seesionID);
        } catch (RemoteException ex) {
            throw new RuntimeException("Connection Failed.");
        }
    }


    /**
     * Returns the player who will play/has played first this round. If going alone has not yet been decided, it will be the player after the dealer.
     * @return the player who will play/has played first this round. If going alone has not yet been decided, it will be the player after the dealer.
     */
    @Override
    public String whoIsLeadPlayer(){
        try {
            return server.whoIsLeadPlayer(player.getName(), seesionID);
        } catch (RemoteException ex) {
            throw new RuntimeException("Connection Failed.");
        }
    }

}
