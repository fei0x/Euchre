/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.distributed_play.server;

import com.fei0x.euchre.exceptions.MissingPlayer;
import com.fei0x.euchre.game.Card;
import com.fei0x.euchre.game.Trick;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The Server hosts methods accessible to clients to allow their remote AI to inquire about the game state
 * 
 * @author jsweetman
 */
public interface EuchreServer extends Remote {

	/**
	 * Allows the client to join the server. returns the session id for the player, if 0 is returned it's too late to join the game.
	 * The sessionID is sort of used like a password 
	 * @param playerName the name of the player the client wishes to join as, the name must be unique from other players, and is used sort of as a username
	 * @param clientAddress the address of the client to receive calls from the server
	 * @param clientPort the port of the client to receive calls from the server
	 * @return the session ID for this client. Used somewhat like a password, when the client wants to make 'authenticated' requests to the server
	 * @throws RemoteException
	 */
    public long joinServer(String playerName, String clientAddress, int clientPort) throws RemoteException;

    /**
     * Keep alive is used by the client and lets RMI know not to drop the connection.
     * @throws RemoteException when the connection has been disconnected
     */
    public void keepAlive() throws RemoteException;

    /**************************
     * AskGame methods made available to client AI's
     * String player names and sessionIds are used like usernames and passwords to authenticate
     *************************/

	public List<Card> myHand(String playerName,long sessionID) throws RemoteException;    
	public String partnersName(String playerName,long sessionID) throws RemoteException;    
    public String myTeamName(String playerName,long sessionID) throws RemoteException;    
    public String opponentsTeamName(String playerName,long sessionID) throws RemoteException;    
    public List<String> opponentsNames(String playerName,long sessionID) throws RemoteException;    
    public void speak(String playerName,long sessionID, String somethingToSay) throws RemoteException;    
    public List<Trick> pastTricks(String playerName,long sessionID)  throws RemoteException, IllegalStateException;
    public int myTeamsScore(String playerName,long sessionID)  throws RemoteException, MissingPlayer;
    public int opponentsScore(String playerName,long sessionID) throws RemoteException;
    public String whoIsDealer(String playerName,long sessionID) throws RemoteException;
    public String whoIsLeadPlayer(String playerName,long sessionID) throws RemoteException;
    

}
