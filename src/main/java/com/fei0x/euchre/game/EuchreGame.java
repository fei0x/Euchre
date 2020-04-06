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
 * Runs the game of Euchre, prompts players to take actions, and manages storing the data and delegating copies to players.
 * @author jsweetman
 */
public class EuchreGame {

    /**
     * The two teams playing this round.
     */
    private ArrayList<Team> teams = new ArrayList<Team>();

    /**
     * The players playing the game in Euchre, seated around in the table in order
     * The first dealer (element 0) and order is determined randomly.
     */
    private ArrayList<Player> playersInSeatingOrder = new ArrayList<Player>();

    /**
     * index in the playersInSeatingOrder which denotes the player should be the next dealer
     */
    private int dealerIdx = 0;
    
    /**
     * An output stream to write messages to.
     */
    private PrintStream messenger;

    /**
     * The current incomplete round
     */
    private Round round;

    /**
     * The completed rounds played in this game of Euchre (may not need...)
     */
    private ArrayList<Round> rounds = new ArrayList<Round>();

    /**
     * the them which won, indicates the game is over.
     */
    private Team winningTeam;

    /**
     * Indicates whether or not to display all the messages or only the essential ones.
     */
    private int verbose;


    /**
     * create a Euchre game with the 4 players, randomly arranges teams, verifies player names and assigns game interfaces to the players.
     * @param team1player1 the first player of the first team
     * @param team1player2 the second player of the first team
     * @param team2player1 the first player of the second team
     * @param team2player2 the second player of the second team
     * @param verbose if 2 then print all the messages, if 1 then only essential messages, otherwise don't print any messages.
     * @param ps the print stream to write messages to.
     * @throws IllegalArgumentException thrown if the player names are not unique. As names are provided to other players as unique player identifiers.
     */
    public EuchreGame(Player team1player1, Player team1player2, Player team2player1, Player team2player2, int verbose, PrintStream ps) throws IllegalArgumentException{
        messenger = ps;
        this.verbose = verbose;
        Random rnd = new Random();
        
        //put some new lines to separate from any startup logs
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
        playersInSeatingOrder.stream().forEach(p -> p.setAskGame(new AskGameImpl(this, p)));
              

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
                    writeMessage("--Begin Round--", true);
                	
                    ///SETUP THE ROUND///
                    //make player order for round
                    List<Player> playerOrder = new ArrayList<Player>();
                    for(int i = 0; i < 4; i++){
                        playerOrder.add(playersInSeatingOrder.get((i + dealerIdx) %4));
                    }
                    
                    //make the round, deal the cards
                    round = new Round(playerOrder, teams);
                    writeMessage(round.dealer().getName() + " deals the cards.", true);

                    
                    //get face-up card to share with everybody
                    Card faceup = round.getFaceUpCardCopy();
                    writeMessage(round.dealer().getName() + " turns up the top card of the kitty. It is the: " + faceup.getName(), true);

                    
                    
                    ///DETERMINE THE TRUMP AND THE TRUMP CALLING TEAM///
                    //ask each player about the face-up card first
                    for(int i = 0; i < 4 && round.getTrump() == null; i++){
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
                            round.playerCallsUpCard(player, alone); //update the round with the call-up decision
                            writeMessage(player.getName() + " calls up the " + faceup.getName() + ".", true);
                            if(alone){
                                writeMessage(player.getName() + " decides to play alone.", true);
                            }

                            //dealer swaps a card with the kitty (if he's playing)
                            if( i != 1 ){
	                            Card swapOut = null;
	                            try{
	                                swapOut = round.dealer().getAi().swapWithFaceUpCard(faceup.clone()); //ask dealer which card to swap with the kitty
	                            }catch(Throwable t){
	                                throw new PlayerException(player.getName(),"The player caused an exception during play.", t);
	                            }
	                            round.dealerTakesFaceUpCard(swapOut); //update the round with the card swap
	                            writeMessage(round.dealer().getName() + " picks up the " + faceup.getName() + " and places another card in the kitty", true);
                            }
                        }else{
                            writeMessage(player.getName() + " passes.", false);
                        }
                    }
                    //if trump is still not decided ask each non-dealer player about making trump
                    for(int i = 0; i < 3 && round.getTrump() == null; i++){
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
                            round.playerCallsTrump(player,trump, alone); //update the round with the trump call (it validates the suit picked)

                            writeMessage(player.getName() + " calls trump to be " + trump.getName() + ".", true);
                            
                            if(alone){
                                writeMessage(player.getName() + " decides to play alone.", true);
                            }
                        }else{
                            writeMessage(player.getName() + " passes.", false);
                        }
                    }

                    //if the trump is STILL null then the dealer must pick a trump.  a.k.a. 'stick the dealer'
                    if(round.getTrump() == null){
                    	
                        Suit suit = null;
                        try{
                            suit = round.dealer().getAi().stickTheDealer(faceup.clone()); //Ask dealer to decide on suit, null and the faceup card's suit are not valid
                        }catch(Throwable t){
                            throw new PlayerException(round.dealer().getName(),"The player caused an exception during play.", t);
                        }
                        //determining trump and playing alone
                        boolean alone = false;
                        try{
                            alone = round.dealer().getAi().playAlone(); //ask the dealer if they will play alone.
                        }catch(Throwable t){
                            throw new PlayerException(round.dealer().getName(),"The player caused an exception during play.", t);
                        }
                        round.playerCallsTrump(round.dealer(),suit, alone); //update the round with the trump call (it validates the suit picked)

                        writeMessage(round.dealer().getName() + " calls trump to be " + suit.getName() + ".", true);
                        
                        if(alone){
                            writeMessage(round.dealer().getName() + " decides to play alone.", true);
                        }

                    }

                    //Trump should now be decided
                    writeMessage("Trump is: " + round.getTrump().getName(), true);
                    writeMessage("Hands are:", false);

                    playersInSeatingOrder.stream().forEach(p -> writeMessage(p.getName() + ":" + p.getHand(), false));
                    

                    ///PLAY THE TRICKS IN THE ROUND///
                    //each player will continue to play cards until all cards are played. The players are given the current state of each trick as they play, but can ask the game for more pubilc information.
                    while(!round.isRoundComplete()){
                        Player player = round.getNextPlayer();
                        Card playCard = null;
                        
                        //Ask the next player to play their card into the current trick
                        try{
                            playCard = player.getAi().playCard(round.getCurrentTrick().clone()); //tell the player to play a card, and give them the trick so far (a copy of it)
                        }catch(Throwable t){
                            throw new PlayerException(player.getName(),"The player caused an exception during play.", t);
                        }
                        
                        round.playerPlaysCardToTrick(playCard, player); //update the round with the card played

                        //Notify of the play (and possibly the taking of the trick)
                        writeMessage(player.getName() + " lays the " + playCard.getName(), true);
                        if(round.getCurrentTrick().isTrickEmpty() || round.isRoundComplete()){  //when the last card is played in a trick, the round clears the trick
                            writeMessage(round.getTricks().get(round.getTricks().size()-1).getTrickTaker() + " takes the trick.", true);
                        }
                    }
                    
                    //ASSIGN POINTS FOR THE ROUND
                    //once all tricks are played, the points counted can be assigned
                    int points = round.getWinAmount();
                    Team winners = round.getWinners();
                    winners.increaseScore(points);
                    writeMessage(winners.getTeamAndPlayerNames() + " made " + points + "pts from this round", true);
                    writeMessage("Current Scores: ",true);
                    teams.stream().forEach(t -> writeMessage(t.getTeamAndPlayerNames() + " " + t.getScore() + "pts ", true));


                    //PASS DEALER
                    //The round is complete, increment dealer, and add the next round
                    dealerIdx++;
                    dealerIdx = dealerIdx %4;
                    rounds.add(round);
                }
                
                //GAME OVER
                //declare the winners (winner assigned during loop checkForWinner)
                writeMessage(winningTeam.getTeamAndPlayerNames() + " wins this game", true);
                writeMessage("Final Scores: ",true);
                teams.stream().forEach(t -> writeMessage(t.getTeamAndPlayerNames() + " " + t.getScore() + "pts ", true));

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
     * Returns the players that won
     * @return the players that won
     */
    public List<Player> getWinningPlayers() throws MissingPlayer{
        return new ArrayList<Player>(winningTeam.getPlayers());
    }
    
    /**
     * Returns the players that lost
     * @return the players that lost
     */
    public List<Player> getLosingPlayers() throws MissingPlayer{
        return teams.stream().filter(t -> ! t.equals(winningTeam)).findFirst().get().getPlayers();
    }


    /**
     * Set the printStream to use for this game.
     * @param ps the printstream to use
     */
    public void setPrintStream(PrintStream ps){
        messenger = ps;
    }

    /**
     * Write a message for everybody to see.
     * Display the message if the verbose setting is at level 2 OR it is at level 1 and the message is 'essential'
     * @param message the message to pass on to everybody
     * @param essentialMessage an indicator if this message should always be displayed or on in verbose mode.
     */
    public void writeMessage(String message, boolean essentialMessage){
        if( (verbose == 1 && essentialMessage) 
        	|| verbose == 2 ){
                messenger.println(message);
        }
    }

    /**
     * Method allowing players to speak
     * @param player the player making the message
     * @param message the message for the other players
     */
    public void playerSpeaks(String playerName, String message){
        writeMessage(playerName + " says: " + message, false);
    }

    /**
     * Get the current round in play, intended for the askGame class to look through and pass answers to the player appropriately
     * @return the current round
     * @throws IllegalStateException if a round hasn't started yet.
     */
     protected Round getCurrentRound() throws IllegalStateException{
         if(round == null){ throw new IllegalStateException("There is no current round to report on.");}
         return round;
     }

     /**
      * Get the current teams, intended for the askGame class to look through and pass answers to the players appropriately
      * @return the teams playing this game.
      */
     protected List<Team> getTeams(){
         return teams;
     }
}
