/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchreplayers;

import com.n8id.n8euchregame.*;
import java.util.ArrayList;

/**
 * This abstract class outlines all of the required functionality a contender must fill out to implement a player
 * There are private elements and game called functions that cannot be manipulated by the player.
 * A contender is cheating if he/she uses introspection to change the members/functions in this class he/she is not entitled to.
 * @author jsweetman
 */
public abstract class Player {

    /***************
     * The following private members cannot be touched by implementations by the player, as they are controlled by the game.
     * (eg. Player's cannot cheat by putting cards in their hand they do not have, or change their name. etc.)
     ****************/

    /**
     * players name, private so you can't change it.
     */
    private String name;

    /**
     * The interface for asking the current game of euchre questions about the game, such as score, player names, player teams, past tricks, etc.
     * Assigned at the creation of each game.
     */
    protected AskGame askGame;

    /**
     * A copy of the player's current hand, so the player can look at it and whatnot,
     * this hand will be automatically updated each time it changes (the player plays a card, picks-up the face-up card, or a new round begins.)
     */
    protected PlayerHand hand;


    /**
     * Protected constructor for the player class. Implementations should use this constructor and pass an imutable name to represent this player to other players and to the game mechanics
     * @param name the name of this player
     */
    protected Player(String name){
        this.name = name;
    }

    /**
     * Returns the player's name
     * @return the player's name
     */
    public final String getName(){
        return name;
    }

    /**
     * Sets the player's hand, so that we can be sure that the player has a hand that is always correct.
     * Player implementers should avoid using this function, it is only meant to be set by the game class.
     * @param handCopy a copy of the player's hand so that the player can see what's in their hand.
     */
    public void setHand(PlayerHand handCopy) {
        this.hand = handCopy;
    }

    /**
     * Used by the game, the game sets this to allow the player an interface for asking questions about the game.
     * @param askGame the askGame class to set
     */
    public void setAskGame(AskGame askGame){
        this.askGame = askGame;
    }


    /**
     * Call It Up - defines the logic behind the decision of whether or not to tell the dealer (be it yourself or somebody else) to pick up the face-up card (provided) and play with that card's suit as trump. A player may not call a suit trump if they do not have a card of that suit.
     * @param faceUpCard the card to be picked-up by the dealer or not.
     * @return true if the player wants the dealer to pick up the card and play with that cards suit as trump, otherwise false.
     */
    public abstract boolean callItUp(Card faceUpCard);

    /**
     * Swap With Face Up Card - defines the logic behind the decision of which card the dealer should remove from their hand to replace the face-up card they will add to theri hand. This occurs after any player tells the dealer to pick up the face-up card via the 'callItUp' function.
     * @param faceUpCard the card that will enter the dealer's/player's hand.
     * @return the card to remove from the dealer's/player's hand
     */
    public abstract Card swapWithFaceUpCard(Card faceUpCard);

    /**
     * Call Suit - defines the logic behind the decision of whether or not chose a suit and for the round. The suit chosen may not be of the same suit as the card which was turned down (provided). A player may not call a suit trump if they do not have a card of that suit.
     * @param turnedDownCard the card to be which was turned down by all players
     * @return null if the player chooses to pass, otherwise the suit the player wants to call.
     */
    public abstract Suit callSuit(Card turnedDownCard);

    /**
     * Stick The Dealer - defines the logic behind the decision of what suit and for the round when you are the dealer and everybody else has passed. The suit chosen may not be of the same suit as the card which was turned down (provided). A player may not call a suit trump if they do not have a card of that suit.
     * @param turnedDownCard the card to be which was turned down by all players
     * @return null will not be accepted, otherwise the suit the player wants to call.
     */
    public abstract Suit stickTheDealer(Card turnedDownCard);

    /**
     * Play Alone - defines the decision to play this round of euchre without your partner or not. It will be called after deciding to call trump from any of 'callItUp', 'callSuit' or 'stickTheDealer'. Return true if the player wishes to play alone, for a chance for more points.
     * @return true if the player chooses to play alone.
     */
    public abstract boolean playAlone();

    /**
     * Play Card - defines the decision of which card to play in any given trick. The current trick in progress is provided for convience. The Trick's 'plays' can be found via the getPlays() function (amongst other helpful functions). The plays provided are in order in which they played. A play consists of the card played and the player who played it. If the trick has no plays, then you are the first player on this trick.
     * To aid the decision of which card to play, the implementer of this function may use the AskGameImpl object to ask the game for things such as: what is trump, who is on which team, what the score is, past trick information, and so on.
     * This function returns the card to be played into the trick. The trick supplied is mearly a copy of the trick, and therefore if implementors change the trick, it will not effect the game.
     * After this card is validated by the game, the player's hand will be automatically updated with the submitted card removed from the hand.
     * @param currentTrick the trick in progress
     * @return the card to play into the trick.
     */
    public abstract Card playCard(Trick currentTrick);

}
