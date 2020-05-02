/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.game;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

import com.fei0x.euchre.exceptions.MissingPlayer;

/**
 * This class serves acts as an interface to allow players to inquire about particular things about the Euchre game in progress.
 * We break this out to make it very clear what information the player AI is allowed to use to make decisions
 * Also is responsible for copying objects before passing them to the player.
 * @author jsweetman
 */
public class AskGameImpl implements AskGame {

    /**
     * The output stream that players can talk to each other via.
     */
    private PrintStream messenger;
    
    /**
     * The game in progress for the player to make inquiries to.
     */
    private EuchreGame game;

    /**
     * The player who this askGame is for.
     */
    private Player player;

    /**
     * The player's partner
     */
    private Player partner;

    /**
     * The player's team.
     */
    private Team myTeam;
    
    /**
     * The opponents's team.
     */
    private Team opponentTeam;

    /**
     * Create an interface to give to the player for asking questions to the game
     * @param game the game to ask questions to.
     * @param player the player, who will ask questions, used to create the context for answering the questions.
     */
    protected AskGameImpl(EuchreGame game, Player player, PrintStream ps){
        this.game = game;
        this.player = player;
        this.myTeam = game.getTeams().stream().filter(t -> t.getPlayers().contains(player)).findFirst().get();
        if(myTeam == null){
        	throw new MissingPlayer(player.getName(), "Player: " + player + " has not been assigned to a team in this game.");
        }
        this.partner = myTeam.getTeammate(player);
        List<Team> teamsNotIn = game.getTeams().stream().filter(t -> !t.getPlayers().contains(player)).collect(Collectors.toList());
        if(teamsNotIn.size() != 1 ){
        	throw new MissingPlayer(player.getName(), "Opposing team for player: " + player + "could not be found in this game.");
        }
        this.opponentTeam = teamsNotIn.get(0);
    }

    /**
     * returns this players name
     * @return this players name
     */
	public String myName() {
		return player.getName();
	}

	/**
	 * returns the name of this players' partner
     * @return the name of this players' partner
	 */
	public String partnersName() {
		return partner.getName();
	}

	/**
	 * returns a copy of the cards in this players hand
     * @return a copy of the cards in this players hand
	 */
	public List<Card> myHand() {
		return player.getHand().clone().getCards();
	}
    
    /**
     * Write something to the rest of the players output
     * @param somethingToSay something to write to the log/all other players
     */
    public void speak(String somethingToSay){
    	messenger.println(player.getName() + " says: " + somethingToSay);
    }

    /**
     * Returns your team's name
     * @return your team's name
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    public String myTeamName(){
        return myTeam.getName();
    }

    /**
     * Returns opponent's team name
     * @return opponent's team name
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    public String opponentsTeamName(){
        return opponentTeam.getName();
    }


    /**
     * Returns the 2 names of this player's opponents
     * @return the 2 names of this player's opponents
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    public List<String> opponentsNames(){
        return opponentTeam.playerNames();
    }
    
    /**
     * Returns your team's current score
     * @return your team's current score
     */
    public int myTeamsScore(){
       return myTeam.getScore();
    }

    /**
     * Returns your opponents current score
     * @return your opponents current score
     * @throws MissingPlayer if this player cannot be found playing the current game.
     */
    public int opponentsScore(){
        return opponentTeam.getScore();
    }


    /**
     * Returns the name of the dealer
     * @return the name of the dealer
     */
    public String whoIsDealer(){
        return game.getCurrentRound().dealer().getName();
    }


    /**
     * Returns the player who will play/has played first this round. If going alone has not yet been decided, it will be the player after the dealer.
     * @return the player who will play/has played first this round. If going alone has not yet been decided, it will be the player after the dealer.
     */
    public String whoIsLeadPlayer(){
        return game.getCurrentRound().leadPlayer().getName();
    }

    /**
     * returns all of the past tricks in the round. This lets the player know who played every card and all cards played this round. (this tricks are cloned so the ai player wont be able to manipulate the game.
     * @return a list of all the completed tricks this round. In order in which it was played.
     * @throws IllegalStateException if this is called outside the scope of a round. (but all player functions should be within the round)
     */
    public List<Trick> pastTricks() {
        return game.getCurrentRound().getTricks().stream().map(t -> t.clone()).collect(Collectors.toList());    
    }
 
}
