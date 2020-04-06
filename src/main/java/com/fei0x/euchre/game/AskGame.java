/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.game;

import java.util.List;

import com.fei0x.euchre.exceptions.MissingPlayer;

/**
 * Interface to define how players can see what is happening in the game.
 * The player AI will use this 'window' to gather all the information they need to determin what moves they will make, when prompted.
 * @author jsweetman
 */
public interface AskGame {

    public String myName();
    public List<Card> myHand();
    public void speak(String somethingToSay);
    public List<Trick> pastTricks() throws IllegalStateException;
    public String myTeamName();
    public String opponentsTeamName();
    public int myTeamsScore() throws MissingPlayer;
    public int opponentsScore();
    public String partnersName();
    public List<String> opponentsNames();
    public String whoIsDealer();
    public String whoIsLeadPlayer();

}
