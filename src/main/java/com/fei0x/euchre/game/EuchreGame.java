/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.game;

import com.fei0x.euchre.exceptions.IllegalPlay;
import com.fei0x.euchre.exceptions.MissingPlayer;
import com.fei0x.euchre.exceptions.PlayerException;
import com.fei0x.euchre.game.Player;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Runs a game of Euchre, prompts player ai to take actions, manages output and protecting data from the AI.
 * The playGame method can only be run once. After which the winners and losers can be retrieved.
 * The game manages the progression of the rounds and the score. The rounds store the cards, player roles and the mechanics of the moves
 * @author jsweetman
 */
public class EuchreGame {


    /**
     * Indicates whether or not to display all the messages or only the essential ones.
     */
    private boolean verbose;

    /**
     * An output stream to write messages to.
     */
    private PrintStream messenger;
    
    /**
     * The two teams playing this round.
     */
    private ArrayList<Team> teams = new ArrayList<Team>();

    /**
     * The them which won.
     * Also used to indicate game state, when non-null indicates the game is over.
     */
    private Team winningTeam;
    
    /**
     * The players playing the game in Euchre, seated around in the table in order
     * The first dealer (element 0) and order is determined randomly.
     */
    private ArrayList<Player> playersInSeatingOrder = new ArrayList<Player>();

    /**
     * index in the playersInSeatingOrder which denotes the player who is the dealer for the current round
     */
    private int dealerIdx = 0;
    
    /**
     * The completed rounds played in this game of Euchre (may not need...)
     * By keeping the rounds we're able to track a history of the game
     */
    private ArrayList<Round> completedRounds = new ArrayList<Round>();
    
    /**
     * The current incomplete round
     */
    private Round currentRound;


    /**
     * create a Euchre game with the 4 players, randomly arranges teams, verifies player names and assigns game interfaces to the players.
     * @param team1player1 the first player of the first team
     * @param team1player2 the second player of the first team
     * @param team2player1 the first player of the second team
     * @param team2player2 the second player of the second team
     * @param verbose if true then print all the messages, otherwise only essential messages
     * @param ps the print stream to write messages to.
     * @throws IllegalArgumentException thrown if the player names are not unique. As names are provided to other players as unique player identifiers.
     */
    public EuchreGame(Player team1player1, Player team1player2, Player team2player1, Player team2player2, boolean verbose, PrintStream ps) throws IllegalArgumentException{
    	messenger = ps;
        this.verbose = verbose;
        Random rnd = new Random();

        //put some new lines to separate from any prior logs
    	writeMessage("",true);
    	writeMessage("",true);
    	
        //create teams
        teams.add(new Team("1", team1player1, team1player2));
        teams.add(new Team("2", team2player1, team2player2));
        
        //sit the players at the table (we will shuffle their order randomly below)
        playersInSeatingOrder = new ArrayList<Player>();
        playersInSeatingOrder.add(team1player1);
        playersInSeatingOrder.add(team2player1);
        playersInSeatingOrder.add(team1player2);
        playersInSeatingOrder.add(team2player2);

        //confirm player names are unique
        if ( 4 !=  playersInSeatingOrder.stream().map(p -> p.getName()).distinct().count()){
        	throw new IllegalArgumentException("Multiple players are sharing the same name. All player names must be unique.");
        }

        //randomly determine player seating order              
        //swap team 1 player seating?
        if(rnd.nextBoolean()){
            playersInSeatingOrder.set(0,team1player2);
            playersInSeatingOrder.set(2,team1player1);
        }
        
        //swap team 2 player seating?
        if(rnd.nextBoolean()){
            playersInSeatingOrder.set(1,team2player2);
            playersInSeatingOrder.set(3,team2player1);
        }
        
        //swap team that goes first?
        if(rnd.nextBoolean()){
        	playersInSeatingOrder.add(0,playersInSeatingOrder.remove(1));
        	playersInSeatingOrder.add(2,playersInSeatingOrder.remove(3));
        }
                
        //assign 'AskGame' to players so they can 'observe' the game they are in.
        playersInSeatingOrder.stream().forEach(p -> p.setAskGame(new AskGameImpl(this, p, ps)));
              

        //display teams and order
        writeMessage("Players sit at the table in order:",true);
        playersInSeatingOrder.stream().forEach(p -> writeMessage("Team " + p.getTeamAndName(), true));
    }
    

    /**
     * plays the game or Euchre, after it is finished the winning team can be retrieved by calling, getWinningPlayers()
     * @throws IllegalStateException if the game has already been completed then it throws this exception
     */
    public void playGame() throws IllegalStateException, MissingPlayer{
        if(winningTeam != null){
            throw new IllegalStateException("The game has already completed, it cannot be re-played.");
        }else{
        	
            try{
            	
            	//iterate through rounds in this game
                while(!checkForWinner()){

                    writeMessage("", true);
                    writeMessage("", true);
                    writeMessage("--Begin Round (" + (completedRounds.size() + 1) + ")--", true);
                	
                    
                    /*********************
                     * SETUP THE ROUND
                     *********************/
                    
                    //make player order for round
                    List<Player> playerOrder = new ArrayList<Player>();
                    for(int i = 0; i < 4; i++){
                        playerOrder.add(playersInSeatingOrder.get((i + dealerIdx) %4));
                    }
                    
                    //make the round, deal the cards
                    currentRound = new Round(playerOrder, teams);
                    writeMessage(currentRound.dealer().getName() + " deals the cards.", true);

                    
                    //get face-up card to share with everybody
                    Card faceup = currentRound.getFaceUpCardCopy();
                    writeMessage(currentRound.dealer().getName() + " turns up the top card of the kitty. It is the: " + faceup.name(), true);

                    
                    
                    /*********************
                     * DETERMINE THE TRUMP AND THE TRUMP CALLING TEAM
                     *********************/
                    
                    //ask each player about the face-up card first
                    for(int i = 0; i < 4 && currentRound.getTrump() == null; i++){
                        int askPlayerIdx = (dealerIdx + 1 + i) %4; //the player to ask. (start with the player after the dealer, add number of players already asked, mod 4
                        Player player = playersInSeatingOrder.get(askPlayerIdx);
                        
                        //ask the player if he wants to call trump with the face-up card
                        boolean heCallsIt = false;
                        try{
                            heCallsIt = player.getAi().callItUp(faceup.clone()); //Ask player if they want to call up the face-up card
                        }catch(Throwable t){
                            throw new PlayerException(player.getName(),"The player caused an exception during play.", t);
                        }
                        
                        
                        if(heCallsIt){
                            //determining trump and playing alone
                            boolean alone = true; //default to playing alone, and if he is the partner (i=1 partner always asked second), than he has no choice
                            if( i != 1 ){
                                try{
                                    alone = player.getAi().playAlone(); //ask the player if they will play alone.
                                }catch(Throwable t){
                                    throw new PlayerException(player.getName(),"The player caused an exception during play.", t);
                                }
                            }
                            currentRound.playerCallsUpCard(player, alone); //update the round with the call-up decision
                            writeMessage(player.getName() + " calls up the " + faceup.name() + ".", true);
                            if(alone){
                                writeMessage(player.getName() + " decides to play alone.", true);
                            }

                            //dealer swaps a card with the kitty (if he's playing)
                            if( i != 1 ){
	                            Card swapOut = null;
	                            try{
	                                swapOut = currentRound.dealer().getAi().swapWithFaceUpCard(faceup.clone()); //ask dealer which card to swap with the kitty
	                            }catch(Throwable t){
	                                throw new PlayerException(player.getName(),"The player caused an exception during play.", t);
	                            }
	                            currentRound.dealerTakesFaceUpCard(swapOut); //update the round with the card swap
	                            writeMessage(currentRound.dealer().getName() + " picks up the " + faceup.name() + " and places another card in the kitty", true);
                            }
                        }else{
                            writeMessage(player.getName() + " passes.", false);
                        }
                    }
                    
                    
                    //if trump is still not decided ask each non-dealer player about making trump
                    for(int i = 0; i < 3 && currentRound.getTrump() == null; i++){
                        int askPlayerIdx = (dealerIdx + 1 + i) %4; // the player to ask. (start with the player after the dealer, add number of players already asked, mod 4
                        Player player = playersInSeatingOrder.get(askPlayerIdx);
                        
                        //ask the player if he wants to call trump, if so the player will name a suit
                        Suit trump = null;
                        try{
                        	trump = player.getAi().callSuit(faceup.clone()); //Ask player if they want to call suit, given the turned down card and the suit they can't pick.
                        }catch(Throwable t){
                            throw new PlayerException(player.getName(),"The player caused an exception during play.", t);
                        }
                        
                        //if a trump suit was named finalize setup
                        if(trump != null){
                            //determining if playing alone
                            boolean alone = false;
                            try{
                                alone = player.getAi().playAlone();  //ask the player if they will play alone.
                            }catch(Throwable t){
                                throw new PlayerException(player.getName(),"The player caused an exception during play.", t);
                            }
                            currentRound.playerCallsTrump(player,trump, alone); //update the round with the trump call (it validates the suit picked)

                            writeMessage(player.getName() + " calls trump to be " + trump.getName() + ".", true);
                            
                            if(alone){
                                writeMessage(player.getName() + " decides to play alone.", true);
                            }
                        }else{
                            writeMessage(player.getName() + " passes.", false);
                        }
                    }

                    //if the trump is STILL null then the dealer must pick a trump.  a.k.a. 'stick the dealer'
                    if(currentRound.getTrump() == null){
                    	
                        Suit suit = null;
                        try{
                            suit = currentRound.dealer().getAi().stickTheDealer(faceup.clone()); //Ask dealer to decide on suit, null and the faceup card's suit are not valid
                        }catch(Throwable t){
                            throw new PlayerException(currentRound.dealer().getName(),"The player caused an exception during play.", t);
                        }
                        //determining trump and playing alone
                        boolean alone = false;
                        try{
                            alone = currentRound.dealer().getAi().playAlone(); //ask the dealer if they will play alone.
                        }catch(Throwable t){
                            throw new PlayerException(currentRound.dealer().getName(),"The player caused an exception during play.", t);
                        }
                        currentRound.playerCallsTrump(currentRound.dealer(),suit, alone); //update the round with the trump call (it validates the suit picked)

                        writeMessage(currentRound.dealer().getName() + " calls trump to be " + suit.getName() + ".", true);
                        
                        if(alone){
                            writeMessage(currentRound.dealer().getName() + " decides to play alone.", true);
                        }

                    }

                    //Trump should now be decided
                    writeMessage("Trump is: " + currentRound.getTrump().getName(), true);
                    writeMessage("Hands are:", false);
                    writeMessage("-", true);
                    
                    playersInSeatingOrder.stream().forEach(p -> writeMessage(p.getName() + ":" + p.getHand(), false));
                    
                    
                    
                    /*********************
                     * PLAY THE TRICKS IN THE ROUND
                     *********************/
                    
                    //each player will continue to play cards until all cards are played. The players are given the current state of each trick as they play, but can ask the game for more pubilc information.
                    while(!currentRound.isRoundComplete()){
                        Player player = currentRound.nextPlayer();
                        Card playCard = null;
                        
                        //Ask the next player to play their card into the current trick
                        try{
                            playCard = player.getAi().playCard(currentRound.getCurrentTrick().clone()); //tell the player to play a card, and give them the trick so far (a copy of it)
                        }catch(Throwable t){
                            throw new PlayerException(player.getName(),"The player caused an exception during play.", t);
                        }
                        
                        currentRound.playerPlaysCardToTrick(playCard, player); //update the round with the card played

                        //Notify of the play (and possibly the taking of the trick)
                        writeMessage(player.getName() + " lays the " + playCard.name(), true);
                        if(currentRound.getCurrentTrick().trickEmpty() || currentRound.isRoundComplete()){  //when the last card is played in a trick, the round clears the trick
                            writeMessage(currentRound.getTricks().get(currentRound.getTricks().size()-1).getTrickTaker() + " takes the trick.", true);
                            writeMessage("-", true);
                        }
                    }
                    
                    
                    /*********************
                     * ASSIGN POINTS FOR THE ROUND
                     *********************/
                    
                    //once all tricks are played, the points counted can be assigned
                    int points = currentRound.getWinAmount();
                    Team roundWinners = currentRound.getWinners();
                    roundWinners.increaseScore(points);
                    
                    //Announce new scores
                    writeMessage(roundWinners.getTeamAndPlayerNames() + " made " + points + "pts from this round", true);
                    writeMessage("Current Scores: ",true);
                    teams.stream().forEach(t -> writeMessage(t.getTeamAndPlayerNames() + " " + t.getScore() + "pts ", true));


                    
                    /*********************
                     * PASS DEALER
                     *********************/
                    
                    //The round is complete, increment dealer, and add the next round
                    dealerIdx = ++dealerIdx %4;
                    completedRounds.add(currentRound);
                }

                
                /*********************
                 * GAME END
                 *********************/
                
                //declare the winners (winner assigned during loop checkForWinner)
                writeMessage("", true);
                writeMessage("", true);
                writeMessage("--Game Over--", true);
                writeMessage(winningTeam.getTeamAndPlayerNames() + " wins this game", true);
                writeMessage("Final Scores: ",true);
                teams.stream().forEach(t -> writeMessage(t.getTeamAndPlayerNames() + " " + t.getScore() + "pts ", true));
                writeMessage("", true);

            }catch(MissingPlayer mp){
                //this exception should not have been caused by a player.. so we'll write it out and push it up
                writeMessage("Error finding player " + mp.getPlayerName() + ": " + mp.getMessage(), true);
                throw mp;
                
            }catch(IllegalPlay ip){  //player initiated failure
                writeMessage("Illegal play caused by " + ip.getPlayerName() + ": " + ip.getMessage(), true);
                playerErrored(ip.getPlayerName(), ip);
                
            }catch(PlayerException pe){  //player initiated failure
                writeMessage("Error caused by " + pe.getPlayerName() + ": " + pe.getMessage(),true);
                pe.printStackTrace(messenger);
                writeMessage("Error cause " + pe.getCause().getMessage(),false);
                playerErrored(pe.getPlayerName(), pe);
            }
        }
    }

    
    /**
     * check the team scores and set a winner if a team has 10 points or more.
     * If there is a winner, it sets winningTeam and therefore ends the game.
     * @return true if there is a winning team.
     */
    private boolean checkForWinner(){
    	//because only one team gets points each round, this should only ever return 1 winning team
    	winningTeam = teams.stream().filter(t -> t.getScore() >= 10).findFirst().orElse(null);
    	return (winningTeam != null);
    }

    /**
     * A player caused an exception, their team loses, if the player cannot be found again, throw runtime Exception
     * @param playerName the name of the player who errored.
     */
    private void playerErrored(String errorPlayer, Exception e){
    	Team winningTeam = getTeams().stream().filter(t -> !t.hasMember(errorPlayer)).findFirst().orElse(null);
        if (winningTeam != null){
        	writeMessage("Team " + winningTeam.getTeamAndPlayerNames() + " wins this game by opponent error.", true);
        }else{
            throw new Error("Could not force erroring player " + errorPlayer + " to lose, throwing error. see contained exception for root cause.", e);
        }
    }


    
    /**
     * Write a message for everybody to see.
     * Display the message if the verbose setting is at level 2 OR it is at level 1 and the message is 'essential'
     * @param message the message to pass on to everybody
     * @param essentialMessage an indicator if this message should always be displayed or on in verbose mode.
     */
    private void writeMessage(String message, boolean essentialMessage){
        if( verbose || essentialMessage) {
        	messenger.println(message);
        }
    }

    /**
     * Get the current round in play, intended for the askGame class to look through and pass answers to the player appropriately
     * @return the current round
     * @throws IllegalStateException if a round hasn't started yet.
     */
     protected Round getCurrentRound() throws IllegalStateException{
         if(currentRound == null){ throw new IllegalStateException("There is no current round to report on.");}
         return currentRound;
     }

     /**
      * Get the current teams, intended for the askGame class to look through and pass answers to the players appropriately
      * @return the teams playing this game.
      */
     protected List<Team> getTeams(){
         return teams;
     }

     /**
      * Returns the players that won
      * @return the players that won
      */
     public List<Player> getWinningPlayers(){
         if (winningTeam == null){
         	throw new IllegalStateException("Game is not yet complete, run playGame() first");
         }
         return new ArrayList<Player>(winningTeam.getPlayers());
     }
     
     /**
      * Returns the players that lost
      * @return the players that lost
      */
     public List<Player> getLosingPlayers(){
         if (winningTeam == null){
         	throw new IllegalStateException("Game is not yet complete, run playGame() first");
         }
         return teams.stream().filter(t -> ! t.equals(winningTeam)).findFirst().get().getPlayers();
     }
     
     
}
