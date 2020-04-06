/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fei0x.euchre.exceptions.MissingPlayer;
import com.fei0x.euchre.game.Player;

/**
 * A team is two players as well as some information about their standings in the current game. (aka their score)
 * Intended to be provided to the players, therefore only the player names are kept here.
 * @author jsweetman
 */
public class Team {

	/**
	 * Name of the team, for now it'll just be 1 or 2
	 */
    private String name;

    /**
     * The 2 players on the team (Reliably always of size 2)
     */
    private List<Player> players = new ArrayList<Player>();

    /**
     * The team's score
     */
    private int score;

    /**
     * Team constructor, takes the two players
     * @param playerOneName the name of the first player
     * @param playerTwoName the name of the second player
     */
    public Team(String teamName, Player playerOneName, Player playerTwoName){
    	this.name = teamName;
        players.add(playerOneName);
        players.add(playerTwoName);
    }

    /**
     * Get the name of the team
     * @return
     */
    public String getName(){
        return name;
    }
    
    /**
     * Get both players
     * @return
     */
    public List<Player> getPlayers(){
        return players;
    }
    
    /**
     * Get both player names
     * @return
     */
    public List<String> playerNames(){
        return players.stream().map(p -> p.getName()).collect(Collectors.toList());
    }

    /**
     * Returns the names of both players together joined with an "&"
     * @return the names of both players together
     */
    public String getTeamAndPlayerNames(){
        return "Team " + name + ": " + players.get(0).getName() + " & " + players.get(1).getName();
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
    public boolean hasMember(String playerName){
    	return players.stream().anyMatch(p -> p.getName().equals(playerName));
    }

    /**
     * Returns true if the given player is on this team
     * @param player the player to check for
     * @return true if the player is on this team
     */
    public boolean hasMember(Player player){
    	return players.stream().anyMatch(p -> p.equals(player));
    }
    

    /**
     * Returns the name of the teammate of the given player
     * @param player the player to find the teammate for
     * @return the player's teammate
     * @throws MissingPlayer if the player could not be found.
     */
    public Player getTeammate(Player player) throws MissingPlayer{
    	return players.stream()
    			.filter(p -> !p.equals(player))
    			.reduce((a, b) -> {throw new MissingPlayer("Player " + player + " could not be found on this team."); })
    			.get();	
    }


    /**
     * Makes a copy of the team, so that players who get their hands on teams, will actually get their hands on copys. that way they can't cheat!!
     * @return a COPY of this team.
     */
    @Override
    protected Team clone(){
        return new Team(players.get(0), players.get(1), score);
    }
    
     /**
     * Clone constructor, takes the two players and their score
     * @param playerOne the first player
     * @param playerTwo the second player
     * @param score the teams score
     */
    private Team(Player playerOne, Player playerTwo, int score){
        this.players.add(playerOne);
        this.players.add(playerTwo);
        this.score = score;
    }
}
