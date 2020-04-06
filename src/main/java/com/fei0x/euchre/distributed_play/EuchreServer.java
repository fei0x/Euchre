/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.distributed_play;

import com.fei0x.euchre.exceptions.MissingPlayer;
import com.fei0x.euchre.game.Card;
import com.fei0x.euchre.game.Trick;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author jsweetman
 */
public interface EuchreServer extends Remote {

    public long joinServer(String playerName, String clientAddress, int clientPort) throws RemoteException;

    public void keepAlive() throws RemoteException;

    /**************************
     * Ask Server Remote Functions
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
