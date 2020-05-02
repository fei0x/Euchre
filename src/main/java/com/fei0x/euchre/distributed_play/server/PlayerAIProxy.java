/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.distributed_play.server;

import com.fei0x.euchre.distributed_play.client.EuchreClient;
import com.fei0x.euchre.exceptions.MissingPlayer;
import com.fei0x.euchre.game.AskGame;
import com.fei0x.euchre.game.Card;
import com.fei0x.euchre.game.PlayerAI;
import com.fei0x.euchre.game.Suit;
import com.fei0x.euchre.game.Trick;

import java.rmi.RemoteException;

/**
 * A player AI implementation that is the server uses to ask remote client
 * players for their moves. instead of completing it's logic it looks up the
 * results from the client using RMI
 * 
 * @author jsweetman
 */
public class PlayerAIProxy extends PlayerAI {

	/**
	 * the client hosting the real player ai class, we use this client as a
	 * means of fulfilling
	 */
	private EuchreClient client;

	/**
	 * Construct a player AI Proxy
	 */
	public PlayerAIProxy(EuchreClient client) {
		super();
		this.client = client;
	}

	/**
	 * When remote player's Ask Game is assigned, in the remote scenario we do
	 * not send it off, instead we store it locally in the RemotePlayer object.
	 * When the remote player makes a call to use it's AskRemoteGame Object, the
	 * query will eventually route to retrieve their AskGame object locaated
	 * here, so this class must provide a getter, to retrieve the object.
	 * 
	 * @return this remote player's AskGame
	 */
	public AskGame getAskGame() {
		return super.askGame;
	}

	@Override
	public boolean callItUp(Card faceUpCard) {
		try {
			return client.callItUp(faceUpCard);
		} catch (RemoteException ex) {
			throw new MissingPlayer("", "Remote Method Invocation Error, on remote player ", ex);
		}
	}

	@Override
	public Card swapWithFaceUpCard(Card faceUpCard) {
		try {
			return client.swapWithFaceUpCard(faceUpCard);
		} catch (RemoteException ex) {
			throw new MissingPlayer("", "Remote Method Invocation Error, on remote player ", ex);
		}
	}

	@Override
	public Suit callSuit(Card turnedDownCard) {
		try {
			return client.callSuit(turnedDownCard);
		} catch (RemoteException ex) {
			throw new MissingPlayer("", "Remote Method Invocation Error, on remote player ", ex);
		}
	}

	@Override
	public Suit stickTheDealer(Card turnedDownCard) {
		try {
			return client.stickTheDealer(turnedDownCard);
		} catch (RemoteException ex) {
			throw new MissingPlayer("", "Remote Method Invocation Error, on remote player ", ex);
		}
	}

	@Override
	public boolean playAlone() {
		try {
			return client.playAlone();
		} catch (RemoteException ex) {
			throw new MissingPlayer("", "Remote Method Invocation Error, on remote player ", ex);
		}
	}

	@Override
	public Card playCard(Trick currentTrick) {
		try {
			return client.playCard(currentTrick);
		} catch (RemoteException ex) {
			throw new MissingPlayer("", "Remote Method Invocation Error, on remote player ", ex);
		}
	}

}
