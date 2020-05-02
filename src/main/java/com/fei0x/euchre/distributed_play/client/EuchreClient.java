/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.distributed_play.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.fei0x.euchre.game.Card;
import com.fei0x.euchre.game.Suit;
import com.fei0x.euchre.game.Trick;

/**
 * interface allowing euchre servers to inquire about euchre ai decisions from clients
 * 
 * @author jsweetman
 */
public interface EuchreClient extends Remote {

	/**
	 * Allows the server to send a message to the client player
	 * @param message the message to send to the player
	 * @throws RemoteException when there is an issue calling the client
	 */
	public void tellClient(String message) throws RemoteException;

	/**
	 * Lets the client know that the server has ended the session and the client should disconnect.
	 * @throws RemoteException
	 */
	public void disconnect() throws RemoteException;

	
	
	/**
	 * These methods are called by the server to the client. They are used to
	 * ask the player what they want to do. They are effectively 1-to-1 with the
	 * PlayerAI methods, and these calls should make their way to the remote
	 * player's AI implementation (whatever that is)
	 */
	public boolean callItUp(Card faceUpCard) throws RemoteException;

	public Card swapWithFaceUpCard(Card faceUpCard) throws RemoteException;

	public Suit callSuit(Card turnedDownCard) throws RemoteException;

	public Suit stickTheDealer(Card turnedDownCard) throws RemoteException;

	public boolean playAlone() throws RemoteException;

	public Card playCard(Trick currentTrick) throws RemoteException;
}
