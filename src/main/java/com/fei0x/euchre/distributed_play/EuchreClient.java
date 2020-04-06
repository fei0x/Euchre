/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.distributed_play;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.fei0x.euchre.game.Card;
import com.fei0x.euchre.game.Suit;
import com.fei0x.euchre.game.Trick;

/**
 * interface for allowing servers to call the ai methods on the remote client player
 * @author jsweetman
 */
public interface EuchreClient extends Remote {

    public void tellClient(String message) throws RemoteException;

    public void disconnect() throws RemoteException;

    /**
     * These methods are called by the server to the client.
     * They are used to ask the player what they want to do.
     * They are effectively 1-to-1 with the PlayerAI methods, and these calls should make their way to the remote player's AI implementation (whatever that is)
     */
    public boolean callItUp(Card faceUpCard) throws RemoteException;
    public Card swapWithFaceUpCard(Card faceUpCard) throws RemoteException;
    public Suit callSuit(Card turnedDownCard) throws RemoteException;
    public Suit stickTheDealer(Card turnedDownCard) throws RemoteException;
    public boolean playAlone() throws RemoteException;
    public Card playCard(Trick currentTrick) throws RemoteException;
}
