/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchregame;

import com.n8id.n8euchreexceptions.IllegalPlay;
import com.n8id.n8euchreexceptions.MissingPlayer;
import com.n8id.n8euchreexceptions.PlayerException;
import com.n8id.n8euchreplayers.Player;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Runds the game of euchre, prompts players to take actions, and manages storing the data and delegating copies to players.
 * @author jsweetman
 */
public class EuchreGame {


    /**
     * The two teams playing this round.
     */
    private ArrayList<Team> teams = new ArrayList<Team>();

    /**
     * The players playing the game in euchre, seated around in the table in order
     * The first dealer (element 0) and order is determined randomly.
     */
    private ArrayList<Player> playersInSeatingOrder = new ArrayList<Player>();

    /**
     * index in the playersInSeatingOrder which denotes the player should be the next dealer
     */
    private int dealer = 0;
    
    /**
     * An output stream to write messages to.
     */
    private PrintStream messenger;

    /**
     * The current incompleted round
     */
    private Round round;

    /**
     * The completed rounds played in this game of euchre (may not need...)
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
     * create a euchre game with the 4 players, randomly arranges teams, verifies player names and assigns game interfaces to the players.
     * @param team1player1 the first player of the first team
     * @param team1player2 the second player of the first team
     * @param team2player1 the first player of the second team
     * @param team2player2 the second player of the second team
     * @param verbose if true then print all the messages, if false then only the essential ones.
     * @param ps the print stream to write messages to.
     * @throws IllegalArgumentException thrown if the player names are not unique. As names are provided to other players as unique player identifiers.
     */
    public EuchreGame(Player team1player1,Player team1player2, Player team2player1,Player team2player2, int verbose, PrintStream ps) throws IllegalArgumentException{
        messenger = ps;
        this.verbose = verbose;
        Random rnd = new Random();
        //create teams
        teams.add(new Team(team1player1.getName(), team1player2.getName()));
        teams.add(new Team(team2player1.getName(), team2player2.getName()));
        //randomly determine player seating
        int startTeam = rnd.nextInt(2);
        int startPlayerTeam1 = rnd.nextInt(2);
        int startPlayerTeam2 = rnd.nextInt(2);
        playersInSeatingOrder = new ArrayList<Player>();
        if(startTeam == 0 && startPlayerTeam1 == 0){
            playersInSeatingOrder.add(team1player1);
            if(startPlayerTeam2 == 0){                  //Permutation1: 11,21,12,22
                playersInSeatingOrder.add(team2player1);
                playersInSeatingOrder.add(team1player2);
                playersInSeatingOrder.add(team2player2);
                writeMessage("Team Permutation1 Selected: 11,21,12,22", false);
            }else{//startPlayerTeam2 == 1               //Permutation2: 11,22,12,21
                playersInSeatingOrder.add(team2player2);
                playersInSeatingOrder.add(team1player2);
                playersInSeatingOrder.add(team2player1);
                writeMessage("Team Permutation2 Selected: 11,22,12,21", false);
            }
        }else if(startTeam == 0 && startPlayerTeam1 == 1){
            playersInSeatingOrder.add(team1player2);
            if(startPlayerTeam2 == 0){                  //Permutation3: 12,21,11,22
                playersInSeatingOrder.add(team2player1);
                playersInSeatingOrder.add(team1player1);
                playersInSeatingOrder.add(team2player2);
                writeMessage("Team Permutation3 Selected: 12,21,11,22", false);
            }else{//startPlayerTeam2 == 1               //Permutation4: 12,22,11,21
                playersInSeatingOrder.add(team2player2);
                playersInSeatingOrder.add(team1player1);
                playersInSeatingOrder.add(team2player1);
                writeMessage("Team Permutation4 Selected: 12,22,11,21", false);
            }
        }else if(startTeam == 1 && startPlayerTeam2 == 0){
            playersInSeatingOrder.add(team2player1);
            if(startPlayerTeam1 == 0){                  //Permutation5: 21,11,22,12
                playersInSeatingOrder.add(team1player1);
                playersInSeatingOrder.add(team2player2);
                playersInSeatingOrder.add(team1player2);
                writeMessage("Team Permutation5 Selected: 21,11,22,12", false);
            }else{//startPlayerTeam2 == 1               //Permutation6: 21,12,22,11
                playersInSeatingOrder.add(team1player2);
                playersInSeatingOrder.add(team2player2);
                playersInSeatingOrder.add(team1player1);
                writeMessage("Team Permutation6 Selected: 21,12,22,11", false);
            }
        }else{//if(startTeam == 1 && startPlayerTeam2 == 1)
            playersInSeatingOrder.add(team2player2);
            if(startPlayerTeam1 == 0){                  //Permutation7: 22,11,21,12
                playersInSeatingOrder.add(team1player1);
                playersInSeatingOrder.add(team2player1);
                playersInSeatingOrder.add(team1player2);
                writeMessage("Team Permutation7 Selected: 22,11,21,12", false);
            }else{//startPlayerTeam2 == 1               //Permutation8: 22,12,21,11
                playersInSeatingOrder.add(team1player2);
                playersInSeatingOrder.add(team2player1);
                playersInSeatingOrder.add(team1player1);
                writeMessage("Team Permutation8 Selected: 22,12,21,11", false);
            }
        }
        //confirm player names are unique and assign interfaces for players to ask questions
        for(int i= 0; i < 4; i++){
            for(int j= i+1; j < 4; j++){
                if(playersInSeatingOrder.get(i).getName().equalsIgnoreCase(playersInSeatingOrder.get(j).getName())){
                    throw new IllegalArgumentException("Player" + i + " (" + playersInSeatingOrder.get(i).getName() + ") and Player" + j + "(" + playersInSeatingOrder.get(i).getName() + ") share the same name");
                }
            }
            playersInSeatingOrder.get(i).setAskGame(new AskGameImpl(this,playersInSeatingOrder.get(i).getName())); //assign the interface for
        }
        writeMessage("Players sit at the table in order: " + playersInSeatingOrder.get(0).getName() + ", " + playersInSeatingOrder.get(1).getName() + ", " + playersInSeatingOrder.get(2).getName() + ", " + playersInSeatingOrder.get(3).getName() , false);
    }

    /**
     * plays the game or euchre, after it is finished the winning team can be retrieved by calling, getWinningPlayers()
     * @throws IllegalStateException if the game has already been completed then it throws this exception
     */
    public void playGame() throws IllegalStateException, MissingPlayer{
        if(winningTeam != null){
            throw new IllegalStateException("The game has already completed, it cannot be re-played.");
        }else{
            try{
                //iterate through rounds in this game
                while(!checkForWinner()){
                    ///SETUP THE ROUND///
                    //make player order for round
                    ArrayList<String> playerOrder = new ArrayList<String>();
                    for(int i = dealer; i < dealer + 4; i++){
                        playerOrder.add(playersInSeatingOrder.get(i%4).getName());
                    }
                    round = new Round(playerOrder, teams);

                    //distribute hands
                    for(Player player : playersInSeatingOrder){
                        player.setHand(round.getPlayerHand(player.getName()).clone());//give player a copy of their hand
                    }
                    writeMessage(getDealer().getName() + " deals the cards.", true);

                    //get faceup card to share with everybody
                    Card faceup = round.getFaceUpCardCopy();
                    writeMessage(getDealer().getName() + " turns up the top card of the kitty. It is the: " + faceup.getName(), true);

                    ///DETERMINE THE TRUMP AND THE TRUMP CALLING TEAM///
                    //ask each player about the face-up card first
                    for(int i = 0; i < 4 && round.getTrump() == null; i++){
                        int askPlayer = (dealer + 1 + i) %4; //the player to ask. (start with the guy after the dealer, add number of guys already asked, mod 4
                        Player player = playersInSeatingOrder.get(askPlayer);
                        boolean heCallsIt = false;
                        try{
                            heCallsIt = player.callItUp(faceup.clone()); //Ask player if they want to call up the face-up card
                        }catch(Throwable t){
                            throw new PlayerException(player.getName(),"The player caused an exception during play.", t);
                        }
                        if(heCallsIt){
                            //determining trump and playing alone
                            boolean alone = true; //for when i = 1, aka, the parter must play alone
                            if(i != 1){
                                try{
                                    alone = player.playAlone(); //ask the player if they will play alone.
                                }catch(Throwable t){
                                    throw new PlayerException(player.getName(),"The player caused an exception during play.", t);
                                }
                            }
                            round.playerCallsUpCard(player.getName(), alone); //Ask player if they are going to play alone.
                            writeMessage(player.getName() + " calls up the " + faceup.getName() + ".", true);
                            if(alone){
                                writeMessage(player.getName() + " decides to play alone.", true);
                            }

                            //dealer swapping card
                            Card swapOut = null;
                            Player dealer = getDealer();
                            try{
                                swapOut = dealer.swapWithFaceUpCard(faceup.clone());//ask dealer to swap a card with the kitty
                            }catch(Throwable t){
                                throw new PlayerException(player.getName(),"The player caused an exception during play.", t);
                            }
                            round.dealerTakesFaceUpCard(swapOut); //tell the round what card was swapped
                            dealer.setHand(round.getPlayerHand(dealer.getName()).clone());//update the dealers hand, with the correctly swapped hand. (a copy)
                            writeMessage(dealer.getName() + " picks up the " + faceup.getName() + " and places another card in the kitty", true);
                        }else{
                            writeMessage(player.getName() + " passes.", false);
                        }
                    }
                    //if still no trump ask each player about making trump
                    for(int i = 0; i < 3 && round.getTrump() == null; i++){
                        int askPlayer = (dealer + 1 + i) %4; //the player to ask. (start with the guy after the dealer, add number of guys already asked, mod 4
                        Player player = playersInSeatingOrder.get(askPlayer);
                        Suit suit = null;
                        try{
                            suit = player.callSuit(faceup.clone()); //Ask player if they want to call suit, given the turned down card and the suit they can't pick.
                        }catch(Throwable t){
                            throw new PlayerException(player.getName(),"The player caused an exception during play.", t);
                        }
                        if(suit != null){
                            //determining trump and playing alone
                            boolean alone = false;
                            try{
                                alone = player.playAlone(); //ask the player if they will play alone.
                            }catch(Throwable t){
                                throw new PlayerException(player.getName(),"The player caused an exception during play.", t);
                            }
                            round.playerCallsTrump(player.getName(),suit, alone); //update the round (it'll verify the suit is valid)
                            writeMessage(player.getName() + " calls trump to be " + suit.getName() + ".", true);
                            if(alone){
                                writeMessage(player.getName() + " decides to play alone.", true);
                            }
                        }else{
                            writeMessage(player.getName() + " passes.", false);
                        }
                    }

                    //if the trump is STILL null 'stick the dealer'
                    if(round.getTrump() == null){
                        Player dealer = getDealer(); //get the dealer
                        Suit suit = null;
                        try{
                            suit = dealer.stickTheDealer(faceup.clone()); //Ask dealer to decide on suit, null and teh faceup card's suit are not valid
                        }catch(Throwable t){
                            throw new PlayerException(dealer.getName(),"The player caused an exception during play.", t);
                        }
                        if(suit == null){
                            //the round doesn't validate the failure to pick suit, so catch him here
                            throw new IllegalPlay(dealer.getName(), "The dealer must pick a suit when it comes down to 'stick the dealer'");
                        }
                        //determining trump and playing alone
                        boolean alone = false;
                        try{
                            alone = dealer.playAlone(); //ask the player if they will play alone.
                        }catch(Throwable t){
                            throw new PlayerException(dealer.getName(),"The player caused an exception during play.", t);
                        }
                        round.playerCallsTrump(dealer.getName(),suit, alone); //update the round (it'll verify the suit is valid)

                        writeMessage(dealer.getName() + " calls trump to be " + suit.getName() + ".", true);
                        if(alone){
                            writeMessage(dealer.getName() + " decides to play alone.", true);
                        }

                    }

                    writeMessage("Trump is:" + round.getTrump().getName(), true);
                    writeMessage("Hands are:", false);

                    for(String player : round.getPlayersInOrder()){
                        writeMessage(player + ":" + round.getPlayerHand(player), false);
                    }


                    ///PLAY THE TRICKS IN THE ROUND///
                    while(!round.isRoundComplete()){
                        Player player = getPlayer(round.getNextPlayer());
                        Card playCard = null;
                        try{
                            playCard = player.playCard(round.getCurrentTrick().clone());//tell the player to play a card, and give them the trick so far (a copy of it)
                        }catch(Throwable t){
                            throw new PlayerException(player.getName(),"The player caused an exception during play.", t);
                        }
                        round.playerPlaysCardToTrick(playCard, player.getName());//update the round with the card played
                        player.setHand(round.getPlayerHand(player.getName()));//update the player hand so that the correct card was removed.
                        writeMessage(player.getName() + " lays the " + playCard.getName(), true);
                        if(round.getCurrentTrick().isTrickEmpty()){//last trick finished so round created a new one.
                            writeMessage(round.getTricks().get(round.getTricks().size()-1).getTrickTaker() + " takes the trick.", true);
                        }
                    }
                    
                    //ASSIGN POINTS FOR THE ROUND
                    int points = round.getWinAmount();
                    if(teams.get(0).hasMember(round.getWinners().getPlayerOne())){
                        teams.get(0).increaseScore(points);
                        writeMessage(teams.get(0).getNames() + " made " + points + "pts from this round", true);
                    }else{
                        teams.get(1).increaseScore(points);
                        writeMessage(teams.get(1).getNames() + " made " + points + "pts from this round", true);
                    }
                    writeMessage("Current Scores: " + teams.get(0).getNames() + " " + teams.get(0).getScore() + "pts " + teams.get(1).getNames() + " " + teams.get(1).getScore() + "pts ", true);

                    //round complete, increment dealer, add the round
                    dealer++;
                    dealer = dealer %4;
                    rounds.add(round);
                }
                writeMessage(winningTeam.getNames() + " wins this game", true);
                writeMessage("Final Scores: " + teams.get(0).getNames() + " " + teams.get(0).getScore() + "pts " + teams.get(1).getNames() + " " + teams.get(1).getScore() + "pts ", true);

            }catch(MissingPlayer mp){
                //this exception should not have been caused by a player.. so we'll write it out and push it up
                writeMessage("Error finding player " + mp.getPlayerName() + ": " + mp.getMessage(),true);
                throw mp;
            }catch(IllegalPlay ip){//player initaited failure
                writeMessage("Illegal play caused by " + ip.getPlayerName() + ": " + ip.getMessage(),true);
                playerErrored(ip.getPlayerName(), ip);
            }catch(PlayerException pe){//player initaited failure
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
        if(teams.get(0).getScore() >= 10){
            winningTeam = teams.get(0);
            return true;
        }else if(teams.get(1).getScore() >= 10){
            winningTeam = teams.get(1);
            return true;
        }
        return false;
    }

    /**
     * get the dealer player
     * @return the dealer player
     */
    private Player getDealer(){
        return playersInSeatingOrder.get(dealer); //get the dealer
    }

    /**
     * A player caused an exception, their teeam loses, if the player cannot be found again, throw runtime Exception
     * @param playerName the name of the player who errored.
     */
    private void playerErrored(String playerName, Exception e){
        if(teams.get(0).hasMember(playerName)){
            winningTeam = teams.get(1);
            writeMessage(winningTeam.getNames() + " wins this game by opponent error.", true);
        }else if(teams.get(1).hasMember(playerName)){
            winningTeam = teams.get(0);
            writeMessage(winningTeam.getNames() + " wins this game by opponent error.", true);
        }else{
            throw new Error("Could not force erroring player " + playerName + " to lose, throwing error. see contained exception for root cause.", e);
        }
    }

    /**
     * Returns the players that wons
     * @return the players that won
     */
    public ArrayList<Player> getWinningPlayers() throws MissingPlayer{
        ArrayList<Player> winners = new ArrayList<Player>();
        winners.add(getPlayer(winningTeam.getPlayerOne()));
        winners.add(getPlayer(winningTeam.getPlayerTwo()));
        return winners;
    }

    /**
     * Get a player from his name
     * @param playerName the name of the player to retrieve
     * @return the player
     */
    private Player getPlayer(String playerName) throws MissingPlayer{
        for(Player player : playersInSeatingOrder){
            if(player.getName().equalsIgnoreCase(playerName)){
                return player;
            }
        }
        throw new MissingPlayer("Could not find player " + playerName + " in the current game");
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
     * @param message the message to pass on to everybody
     * @param essentialMessage an indicator if this message should always be displayed or on in verbose mode.
     */
    public void writeMessage(String message, boolean essentialMessage){
        if(verbose == 1 || verbose == 2 ){
            if(essentialMessage || verbose == 2){
                messenger.println(message);
            }
        }
    }

    /**
     * Methode for allowing players to speak
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
     protected ArrayList<Team> getTeams(){
         return teams;
     }
}
