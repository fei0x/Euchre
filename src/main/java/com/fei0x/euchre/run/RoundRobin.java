/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.run;

import com.fei0x.euchre.game.EuchreGame;
import com.fei0x.euchre.game.Player;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class plays multiple games and builds statistics around the players successess to determine which PlayerAI implementation is the best
 * @author jsweetman
 */
public class RoundRobin {

    /**
     * The player's in the round robin.
     */
    private List<PlayerStats> playerStats = new ArrayList<PlayerStats>();

    /**
     * A place to write the round robins output to. As well as any game output.
     */
    private PrintStream out = null;

    /**
     * Whether or not to write out detailed game output during the round robin.
     */
    private int verbose = 0;

    /**
     * The number of games each team (combo of two players) will play together against each other team.
     * ie, with 5 players and this value as 2 there will be 60 games. .. .or 90??
     */
    private int numOfGamesPerArragnement = 1;


    /**
     * Construct a round robin
     * @param players the players to participate in the round robin (you can have more than 4, but not less)
     * @param numOfGamesPerArragnement number of games to play with each combination of 4 players
     * @param verbose the verbose level either 0 - very little, 1 - basics, 2 - full
     * @param output the stream to print the results to
     * @throws IllegalArgumentException if there are any issues with the inputs
     */
    public RoundRobin(List<Player> players, int numOfGamesPerArragnement, int verbose, PrintStream output) throws IllegalArgumentException{
        if(players.size() < 4){
            throw new IllegalArgumentException("There must be at minimum four players to play a round robin.");
        }
        this.verbose = verbose;
        this.numOfGamesPerArragnement = numOfGamesPerArragnement;
        this.out = output;

        
        //confirm player names are unique
        if ( players.size() !=  players.stream().map(p -> p.getName()).distinct().count()){
        	throw new IllegalArgumentException("Multiple players are sharing the same name. All player names must be unique.");
        }
        
        //setup player stats for each player
        players.stream().forEach(p -> playerStats.add(new PlayerStats(p)));
    }


    /**
     * Play the round robin... if you play a round robin more than once the player's stats from the last round robin will carry over, but everything is reset
     */
    public void playRoundRobin(){
        //clear wins/losses:
    	playerStats.stream().forEach(s -> {s.loses = 0; s.wins = 0;});
    	
    	//setup the 'seats' at the table
        PlayerStats playera;
        PlayerStats playerb;
        PlayerStats playerc;
        PlayerStats playerd;
        
        //Iterate through all player combinations:
        //first find team1 players a and b
        //then find team2 players c and d
        for(int a = 0; a < playerStats.size(); a++){
            for(int b = a+1; b < playerStats.size(); b++){
                playera = playerStats.get(a);
                playerb = playerStats.get(b);
                //playera and playerb are the first team, find the second one.
                
                for(int c = 0; c < playerStats.size(); c++){
                    if(c != a && c != b){  //makes sure they don't play against themselves...
                        for(int d = c+1; d < playerStats.size(); d++){
                            if(d != a && d != b){  //makes sure they don't play against themselves... again...
                                if(!(a < c || b < c)){//make sure they dont play a team they've already played.
                                	
                                    playerc = playerStats.get(c);
                                    playerd = playerStats.get(d);
                                    //playerc and playerd are the second team. 
                                    
                                    //play the matches
                                    for(int games = 0; games < numOfGamesPerArragnement; games++){
                                        EuchreGame game = new EuchreGame(playera.player,playerb.player,playerc.player,playerd.player,verbose,out);
                                        game.playGame();
                                        playerStats.stream().filter(ps -> game.getWinningPlayers().contains(ps.player)).forEach(ps -> ps.wins++);
                                        playerStats.stream().filter(ps -> game.getLosingPlayers().contains(ps.player)).forEach(ps -> ps.loses++);
                                    }
                                    
                                }
                            }
                        }
                    }
                }
            }
        }
        out.println("                               ");
        out.println(" ============================= ");
        out.println("===== ROUND ROBIN RESULTS =====");
        out.println(" ============================= ");
        out.println("                               ");

        //all games have been played, print results.
        //sort the players by wins.
        Collections.sort(playerStats);

        int position = 1;
        
        //print out first place separately, need to compare in the loop to show ties.
        PlayerStats winner = playerStats.get(0);
        out.println(position + ")  " + winner.statString());
        
        for(int i = 1; i < playerStats.size(); i++){
            PlayerStats player = playerStats.get(i);
            if(player.wins < playerStats.get(i-1).wins){
                position++;
            }
            out.println(position + ")  " + player.statString());
        }

    }

    

    /**
     * A subclass which keeps track of each player in the round robin, and their stats for the round robin.
     */
    private class PlayerStats implements Comparable<PlayerStats>{

        /**
         * the player competing in the round robin.
         */
        public Player player = null;

        /**
         * the wins of this player
         */
        public int wins = 0;

        /**
         * the loses of this player
         */
        public int loses = 0;

        /**
         * Constructor for PlayerStats using a player
         * @param player the player to keep track of.
         */
        public PlayerStats(Player player){
            this.player = player;
        }

        /**
         * Return  the win percentage of this player's wins and loses
         * @return the win percentage of this player's wins and loses
         */
        public double winPercent(){
        	return ((double)wins / (double)(wins + loses)) * 100;
        }
        
        /**
         * Return  a string outlining the player's stats
         * @return a string outlining the player's stats
         */
        public String statString(){
        	return player.getName() + "   Wins: " + wins + "  Loses: " + loses + "  Win%: " + winPercent();
        }
        
        /**
         * Compare function so that the player stats can be sorted by wins.
         * @param the object to compare it to... if it's notPlayerStats will return 0
         * @return 1 if this player has less wins. -1 if it has more, (cause -1 means you come first and we want decending order) 0 if it's the same or the object is not a playerStats
         */
        @Override
        public int compareTo(PlayerStats arg0) {
        	return Integer.compare(wins, arg0.wins);
        }

    }
}
