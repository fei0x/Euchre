/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchregame;

import com.n8id.n8euchreexceptions.MissingPlayer;
import java.util.ArrayList;

/**
 * A team is two players as well as some information about their standings in the current game. (aka their score)
 * Intended to be provided to the players, therefore only the player names are kept here.
 * @author jsweetman
 */
public class Team {

    /**
     * The 2 players on the team (Reliably always of size 2)
     */
    private ArrayList<String> players = new ArrayList<String>();

    /**
     * The team's score
     */
    private int score;

    /**
     * Team constructor, takes the two players
     * @param playerOneName the name of the first player
     * @param playerTwoName the name of the second player
     */
    public Team(String playerOneName, String playerTwoName){
        players.add(playerOneName);
        players.add(playerTwoName);
    }

    /**
     * Get the player names for the first team member
     * @return
     */
    public String getPlayerOne(){
        return players.get(0);
    }

    /**
     * Get the player names for the second team member
     * @return
     */
    public String getPlayerTwo(){
        return players.get(1);
    }

    /**
     * Returns the names of both players together joined with an "&"
     * @return the names of both players together
     */
    public String getNames(){
        return players.get(0) + " & " + players.get(1);
    }

    /**
     * Returns the team's score
     * @return the team's score
     */
    public int getScore(){
        return score;
    }

    /**
     * Returns true if the team has more than 10 points.
     * @param amount the amount to increase the score by
     * @return true if the team has more than 10 points(aka, has won)
     */
    protected boolean increaseScore(int amount){
        score+= amount;
        if(score >= 10){
            return true;
        }else{
            return false;
        }
    }


    /**
     * Returns true if the given player is on this team
     * @param player the player to check for
     * @return true if the player is on this team
     */
    public boolean hasMember(String player){
        if(player.equalsIgnoreCase(players.get(0)) || player.equalsIgnoreCase(players.get(1))){
            return true;
        }
        return false;
    }


    /**
     * Returns the name of the teammate of the given player
     * @param player the player to find the teammate for
     * @return the player's teammate
     * @throws MissingPlayer if the player could not be found.
     */
    public String getTeammate(String player) throws MissingPlayer{
        if(player.equalsIgnoreCase(players.get(0))){
            return players.get(1);
        }else if(player.equalsIgnoreCase(players.get(1))){
            return players.get(0);
        }
        throw new MissingPlayer("Player " + player + " could not be found on this team.");
    }


    /**
     * Makes a copy of the team, so that players who get their hands on teams, will actually get their hands on copys. that way they can't cheat!!
     * @return a COPY of this team.
     */
    @Override
    protected Team clone(){
        return new Team(new String(getPlayerOne()), new String(getPlayerTwo()),score);
    }
     /**
     * Clone constructor, takes the two players and their score
     * @param playerOneName the name of the first player
     * @param playerTwoName the name of the second player
     * @param score the teams score
     */
    private Team(String playerOneName, String playerTwoName, int score){
        players.add(playerOneName);
        players.add(playerTwoName);
        this.score = score;
    }
}
