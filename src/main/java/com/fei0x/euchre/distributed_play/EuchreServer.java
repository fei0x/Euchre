/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchredistributedplay;

import com.n8id.n8euchreexceptions.MissingPlayer;
import com.n8id.n8euchregame.Trick;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author jsweetman
 */
public interface EuchreServer extends Remote {

    public long joinServer(String playerName, String clientAddress, int clientPort) throws RemoteException;

    public void keepAlive() throws RemoteException;

    /**************************
     * Ask Server Remote Functions
     *************************/
    public void speak(String playerName,long sessionID, String somethingToSay) throws RemoteException;
    public ArrayList<Trick> getPastTricks(String playerName,long sessionID)  throws RemoteException, IllegalStateException;
    public int getMyTeamsScore(String playerName,long sessionID)  throws RemoteException, MissingPlayer;
    public int getOpponentsScore(String playerName,long sessionID) throws RemoteException;
    public String getPartnersName(String playerName,long sessionID) throws RemoteException;
    public ArrayList<String> getOpponents(String playerName,long sessionID) throws RemoteException;
    public String whoIsDealer(String playerName,long sessionID) throws RemoteException;
    public String getLeadPlayer(String playerName,long sessionID) throws RemoteException;

}
