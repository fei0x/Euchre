/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchredistributedplay;

import com.n8id.n8euchreexceptions.MissingPlayer;
import com.n8id.n8euchregame.AskGame;
import com.n8id.n8euchregame.Card;
import com.n8id.n8euchregame.PlayerHand;
import com.n8id.n8euchregame.Suit;
import com.n8id.n8euchregame.Trick;
import com.n8id.n8euchreplayers.Player;
import java.rmi.RemoteException;

/**
 * A player implementation that instead of completing it's logic looks up the results from an RMI client
 * @author jsweetman
 */
public class RemotePlayer extends Player {


    /**
     * the client containing the real player class, we use this client as a means of fullfilling
     */
    EuchreClient client;

    /**
     * The player's recorded playername,to be used for error messages.
     */
    String playerName;

    /**
     * Remote players' need to get the name from the real player implementation.
     * @param playername the name of the remote player should be whatever is returned from getName on that player.
     */
    public RemotePlayer(String playername, EuchreClient client) {
        super(playername);
        this.client = client;
    }

    /**
     * When remote player's Ask Game is assigned, in the remote scenario we do not send it off, instead we store it locally in the RemotePlayer object.
     * When the remote player makes a call to use it's AskRemoteGame Object, the query will eventually route to retrieve thier AskGame object loacated here, so this
     * class must provide a getter, to retrive the object.
     * @return this remote player's AskGame
     */
    public AskGame getAskGame(){
        return askGame;
    }



    @Override
    public void setHand(PlayerHand handCopy) {
        try{
            client.setHand(handCopy);
        } catch (RemoteException ex) {
            throw new MissingPlayer(playerName,"Remote Method Invocation Error, on remote player " + playerName, ex);
        }
    }


    @Override
    public boolean callItUp(Card faceUpCard){
        try {
            return client.callItUp(faceUpCard);
        } catch (RemoteException ex) {
            throw new MissingPlayer(playerName,"Remote Method Invocation Error, on remote player " + playerName, ex);
        }
    }

    @Override
    public Card swapWithFaceUpCard(Card faceUpCard) {
        try {
            return client.swapWithFaceUpCard(faceUpCard);
        } catch (RemoteException ex) {
            throw new MissingPlayer(playerName,"Remote Method Invocation Error, on remote player " + playerName, ex);
        }
    }

    @Override
    public Suit callSuit(Card turnedDownCard) {
        try {
            return client.callSuit(turnedDownCard);
        } catch (RemoteException ex) {
            throw new MissingPlayer(playerName,"Remote Method Invocation Error, on remote player " + playerName, ex);
        }
    }

    @Override
    public Suit stickTheDealer(Card turnedDownCard) {
        try {
            return client.stickTheDealer(turnedDownCard);
        } catch (RemoteException ex) {
            throw new MissingPlayer(playerName,"Remote Method Invocation Error, on remote player " + playerName, ex);
        }
    }

    @Override
    public boolean playAlone() {
        try {
            return client.playAlone();
        } catch (RemoteException ex) {
            throw new MissingPlayer(playerName,"Remote Method Invocation Error, on remote player " + playerName, ex);
        }
    }

    @Override
    public Card playCard(Trick currentTrick) {
        try {
            return client.playCard(currentTrick);
        } catch (RemoteException ex) {
            throw new MissingPlayer(playerName,"Remote Method Invocation Error, on remote player " + playerName, ex);
        }
    }

}
