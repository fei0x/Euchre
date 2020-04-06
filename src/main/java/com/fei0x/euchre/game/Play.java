/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchregame;

import java.io.Serializable;

/**
 * This class represents a card played and the player who played it.
 * @author jsweetman
 */
public class Play implements Cloneable, Serializable{
    /**
     * the name of the player who played the card
     */
    private String player;

    /**
     * the card the player played
     */
    private Card card;

    /**
     * Create a play (aka. play a card)
     * @param player the player playing the card
     * @param card the card the player is playing
     */
    public Play(String player, Card card){
        this.player = player;
        this.card = card;
    }

    /**
     * Get the card played
     * @return the card which was played
     */
    public Card getCard() {
        return card;
    }

    /**
     * Get the player who played the card
     * @return the player
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Makes a copy of the play, so that players who get their hands on plays, will actually get their hands on copys. that way they can't cheat!!
     * @return a COPY of this play.
     */
    @Override
    protected Play clone(){
        return new Play(new String(player),card.clone());
    }


}
