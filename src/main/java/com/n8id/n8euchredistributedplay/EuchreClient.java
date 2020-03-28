/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchredistributedplay;

import com.n8id.n8euchregame.AskGame;
import com.n8id.n8euchregame.Card;
import com.n8id.n8euchregame.PlayerHand;
import com.n8id.n8euchregame.Suit;
import com.n8id.n8euchregame.Trick;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jsweetman
 */
public interface EuchreClient extends Remote {

    public void tellClient(String message) throws RemoteException;

    public void disconnect() throws RemoteException;

    //Functions to simulate /remotely invoke the player class
    public boolean callItUp(Card faceUpCard) throws RemoteException;
    public Card swapWithFaceUpCard(Card faceUpCard) throws RemoteException;
    public Suit callSuit(Card turnedDownCard) throws RemoteException;
    public Suit stickTheDealer(Card turnedDownCard) throws RemoteException;
    public boolean playAlone() throws RemoteException;
    public Card playCard(Trick currentTrick) throws RemoteException;
    public String getName() throws RemoteException;
    public void setHand(PlayerHand handCopy) throws RemoteException;
}
