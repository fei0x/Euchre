/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchregame;

import com.n8id.n8euchreexceptions.MissingPlayer;
import java.util.ArrayList;

/**
 * This class serves acts as an interface to allow players to inquire about particular things about the euchre game in progress.
 * Also is responsible for copying objects before passing them to the player.
 * @author jsweetman
 */
public class AskGameImpl implements AskGame {

    /**
     * The game in progress for the player to make inquiries to.
     */
    private EuchreGame game;

    /**
     * The player's name, the player who this askGame is for.
     */
    private String playerName;


    /**
     * Create an interface to give to the player for asking questions to the game
     * @param game the game to ask questions to.
     * @param playerName the name of the player, to write in their message names.
     */
    public AskGameImpl(EuchreGame game, String playerName){
        this.game = game;
        this.playerName = playerName;
    }

    /**
     * Write something to the rest of the players output
     * @param somethingToSay something to write to the log/all other players
     */
    public void speak(String somethingToSay){
        game.playerSpeaks(playerName, somethingToSay);
    }

    /**
     * returns all of the past tricks in the round. This lets the player know who played every card and all cards played this round.
     * @return an arraylist of all the completed tricks this round. In order in which it was played.
     * @throws IllegalStateException if this is called outside the scope of a round. (but all player functions should be within the round)
     */
    public ArrayList<Trick> getPastTricks() throws IllegalStateException{
        ArrayList<Trick> copiedTricks = new ArrayList<Trick>();
        for(Trick trick :game.getCurrentRound().getTricks()){
            copiedTricks.add(trick.clone());
        }
        return copiedTricks;
    }

    /**
     * Returns your team's current score
     * @return your team's current score
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    public int getMyTeamsScore() throws MissingPlayer{
        ArrayList<Team> teams = game.getTeams();
        if(teams.get(0).hasMember(playerName)){
            return teams.get(0).getScore();
        }else if(teams.get(1).hasMember(playerName)){
            return teams.get(1).getScore();
        }
        throw new MissingPlayer(playerName,playerName + " cannot be found playing this game.");
    }

    /**
     * Returns your opponents current score
     * @return your opponents current score
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    public int getOpponentsScore(){
        ArrayList<Team> teams = game.getTeams();
        if(teams.get(0).hasMember(playerName)){
            return teams.get(1).getScore();
        }else if(teams.get(1).hasMember(playerName)){
            return teams.get(0).getScore();
        }
        throw new MissingPlayer(playerName,playerName + " cannot be found playing this game.");
    }

    /**
     * Returns the name of this player's partner
     * @return the name of this player's partner
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    public String getPartnersName(){
        ArrayList<Team> teams = game.getTeams();
        if(teams.get(0).hasMember(playerName)){
            return teams.get(0).getTeammate(playerName);
        }else if(teams.get(1).hasMember(playerName)){
            return teams.get(1).getTeammate(playerName);
        }
        throw new MissingPlayer(playerName,playerName + " cannot be found playing this game.");
    }

    /**
     * Returns the 2 names of this player's opponents
     * @return the 2 names of this player's opponents
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    public ArrayList<String> getOpponents(){
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Team> teams = game.getTeams();
        if(teams.get(0).hasMember(playerName)){
            names.add(teams.get(1).getPlayerOne());
            names.add(teams.get(1).getPlayerTwo());
            return names;
        }else if(teams.get(1).hasMember(playerName)){
            names.add(teams.get(0).getPlayerOne());
            names.add(teams.get(0).getPlayerTwo());
            return names;
        }
        throw new MissingPlayer(playerName,playerName + " cannot be found playing this game.");
    }

    /**
     * Returns the name of the dealer
     * @return the name of the dealer
     */
    public String whoIsDealer(){
        return game.getCurrentRound().getDealer();
    }


    /**
     * Returns the player who will play/has played first this round. If going alone has not yet been decided, it will be the player after the dealer.
     * @return the player who will play/has played first this round. If going alone has not yet been decided, it will be the player after the dealer.
     */
    public String getLeadPlayer(){
        return game.getCurrentRound().getLeadPlayer();
    }

}
