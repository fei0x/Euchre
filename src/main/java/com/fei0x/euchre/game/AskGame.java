/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchregame;

import com.n8id.n8euchreexceptions.MissingPlayer;
import java.util.ArrayList;

/**
 *
 * @author jsweetman
 */
public interface AskGame {

    public void speak(String somethingToSay);
    public ArrayList<Trick> getPastTricks() throws IllegalStateException;
    public int getMyTeamsScore() throws MissingPlayer;
    public int getOpponentsScore();
    public String getPartnersName();
    public ArrayList<String> getOpponents();
    public String whoIsDealer();
    public String getLeadPlayer();

}
