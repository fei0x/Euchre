/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchretable;

import com.n8id.n8euchregame.EuchreGame;
import com.n8id.n8euchreplayers.Player;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author jsweetman
 */
public class RoundRobin {

    /**
     * The player's in the round robin.
     */
    private ArrayList<PlayerStats> players = new ArrayList<PlayerStats>();

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


    public RoundRobin(ArrayList<Player> players, int numOfGamesPerArragnement, int verbose, PrintStream output) throws IllegalArgumentException{
        if(players.size() < 4){
            throw new IllegalArgumentException("There must be at minimum four players to play a round robin.");
        }
        this.verbose = verbose;
        this.numOfGamesPerArragnement = numOfGamesPerArragnement;
        this.out = output;

        //confirm player names are unique and setup player stats
        for(int i= 0; i < players.size(); i++){
            for(int j= i+1; j < players.size(); j++){
                if(players.get(i).getName().equalsIgnoreCase(players.get(j).getName())){
                    throw new IllegalArgumentException("Player" + i + " (" + players.get(i).getName() + ") and Player" + j + "(" + players.get(i).getName() + ") share the same name");
                }
            }
            //player has a unique name, set up stats for them.
            this.players.add(new PlayerStats(players.get(i)));
        }
    }


    /**
     * Play the round robin... if you play more than once the player's stats from the last round robin will carry over, except wins/loses...
     */
    public void playRoundRobin(){
        for(PlayerStats stats : players){
            //clear wins/losses:
            stats.loses = 0;
            stats.wins = 0;
        }
        PlayerStats playera;
        PlayerStats playerb;
        PlayerStats playerc;
        PlayerStats playerd;
        for(int a = 0; a < players.size(); a++){
            for(int b = a+1; b < players.size(); b++){
                playera = players.get(a);
                playerb = players.get(b);
                //playera and playerb are the first team, find the second one.
                for(int c = 0; c < players.size(); c++){
                    if(c != a && c != b){//makes sure they don't play agains themselves...
                        for(int d = c+1; d < players.size(); d++){
                            if(d != a && d != b){//makes sure they don't play agains themselves... again...
                                if(!(a < c || b < c)){//make sure they dont play a team they've already played.
                                    playerc = players.get(c);
                                    playerd = players.get(d);
                                    //playerc and playerd are the first team. play the matches
                                    for(int games = 0; games < numOfGamesPerArragnement; games++){
                                        EuchreGame game = new EuchreGame(playera.player,playerb.player,playerc.player,playerd.player,verbose,out);
                                        game.playGame();
                                        ArrayList<Player> winners = game.getWinningPlayers();
                                        if(playera.player == winners.get(0) || playera.player == winners.get(1)){
                                            playera.wins++;
                                        }else{
                                            playera.loses++;
                                        }
                                        if(playerb.player == winners.get(0) || playerb.player == winners.get(1)){
                                            playerb.wins++;
                                        }else{
                                            playerb.loses++;
                                        }
                                        if(playerc.player == winners.get(0) || playerc.player == winners.get(1)){
                                            playerc.wins++;
                                        }else{
                                            playerc.loses++;
                                        }
                                        if(playerd.player == winners.get(0) || playerd.player == winners.get(1)){
                                            playerd.wins++;
                                        }else{
                                            playerd.loses++;
                                        }
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
        Collections.sort(players);
        int position = 1;
        //print out first place seperatly, need to compare in the loop to show ties.
        PlayerStats winner = players.get(0);
        double winpercent = (double)((int)((double)winner.wins / (double)(winner.wins + winner.loses) * 10000)) /100;
        out.println(position + ")  " + winner.player.getName() + "   Wins: " + winner.wins + "  Loses: " + winner.loses + "  Win%: " + winpercent);
        for(int i = 1; i < players.size(); i++){
            PlayerStats player = players.get(i);
            if(player.wins < players.get(i-1).wins){
                position++;
            }
            winpercent = (double)((int)((double)player.wins / (double)(player.wins + player.loses) * 10000)) /100;
            out.println(position + ")  " + player.player.getName() + "   Wins: " + player.wins + "  Loses: " + player.loses + "  Win%: " + winpercent);
        }

    }


    /**
     * A subclass which keeps track of each player in the round robin, and their stats for the round robin.
     */
    private class PlayerStats implements Comparable{

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
         * Constructor for Player and stats
         * @param player the player to keep track of.
         */
        public PlayerStats(Player player){
            this.player = player;
        }

        /**
         * Compare function so that the player stats can be sorted by wins.
         * @param the object to compare it to... if it's notPlayerStats will return 0
         * @return 1 if this player has less wins. -1 if it has more, (cause -1 means you come first and we want decending order) 0 if it's the same or the object is not a playerStats
         */
        @Override
        public int compareTo(Object arg0) {
            if(arg0 instanceof PlayerStats){
                if( wins > ((PlayerStats)arg0).wins){
                    return -1;
                }else if( wins < ((PlayerStats)arg0).wins){
                    return 1;
                }else{
                    return 0;
                }
                
            }else{
                return 0;
            }
        }

    }
}
