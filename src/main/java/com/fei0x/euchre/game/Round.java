/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fei0x.euchre.exceptions.IllegalPlay;
import com.fei0x.euchre.exceptions.MissingPlayer;
import com.fei0x.euchre.game.Player;

/**
 * A round consists of a dealer, a trump determining phase, a trump and 5 tricks.
 * The winning team of a round receives points based on their performance.
 * A round is NOT intended to be given to players as it contains all cards in play, the game however may select items in the round to show to players, such as each player's hand.
 * @author jsweetman
 */
public class Round {


    /*********************
     * Variables Storing all the cards
     *********************/

    /**
     * the 5 tricks of the round. Does not contain the current trick.
     */
    private List<Trick> tricks = new ArrayList<Trick>();

    /**
     * the current trick, once it is complete will move into tricks
     */
    private Trick currentTrick = null;

    /**
     * The 4 cards in the kitty, may change after trump is decided if the player chooses to swap a card into the kitty
     * The top card (element 0) of the kitty is revealed to the table as the faceUpCard and copied to the faceUpCardCopy, so everybody may reference it later.
     * If the face-up card is turned down it remains in the kitty, if the face-up card is taken, it is removed from the kitty and replaced with another card.
     */
    private List<Card> kitty = new ArrayList<Card>();
    
    /**
     * The Players, in order, starting with the dealer
     * The player's hands are managed through this list
     */
    private List<Player> orderedPlayers = new ArrayList<Player>();

    /****************************
     * Deciding Trump and Which Team Calls It
     ****************************/

    /**
     * A copy of the card which was face-up to the table for determining trump.
     */
    private Card faceUpCardCopy = null;

    /**
     * the trump for this round
     */
    private Suit trump = null;

    /**
     * the player who called trump this round
     */
    private Player trumpPlayer = null;

    /**
     * the player who called trump this round
     */
    private Player trumpPlayerTeammate = null;

    /**
     * whether or not the player who called trump has opted to go alone
     */
    private boolean playingAlone = false;

    /**
     * The two teams playing this round.
     */
    private List<Team> teams = new ArrayList<Team>();


    /*********************
     * Ongoing Round Data
     *******************/

    /**
     * the player who is required to play the next card.
     */
    private int nextPlayerIndex = 0; //start it as the dealer, but do 'setNextPlayer' to move to the first player after the dealer after trump and alone have been decided.

    /**
     * Farmer's Hand, marks whether or not we're playing with it.
     * Farmer's Hand means if any player is dealt only 9's and 10's you redeal.
     */
    private boolean farmersHand = true;


    /***********************
     * End of the Round Data
     ***********************/

    /**
     * Winning Team
     */
     private Team winners;
     
     /**
      * Winning Point Take
      */
     private int winAmount;


    /**
     * Initiate a round of Euchre, given the players in a specific order for play.
     * It builds the deck, shuffles the cards, deals the cards, and turns up the top card of the kitty.
     * @param playerOrder the order of the players, the first player is the dealer.
     * @param teams the two teams
     * @throws IllegalArgumentException if the playerOrder and teams are not in alignment.
     */
    public Round(List<Player> playerOrder, List<Team> teams) throws IllegalArgumentException{

        validateTeams(playerOrder,teams);
        this.teams = teams;
        orderedPlayers.addAll(playerOrder);
        
        //assemble cards
        List<Card> deck = makeDeck();

        boolean redeal = true; //flag set true to do the first deal
        int dealIndex = 0;

        //deal cards and validate no farmers hands (which cause a redeal)
        dealing: while(redeal){
        	//note any existing hands will be overwritten
            redeal = false; //hopefully only need to deal this one time.
            dealIndex = 0;

            //shuffle the cards
            shuffleCards(deck);

            //deal cards to each player
            for(Player player : playerOrder){
                List<Card> cards = new ArrayList<Card>();
                //deal hand
                for(int i=0; i < 5; i++){
                    cards.add(deck.get(dealIndex));
                    dealIndex++;
                }
                //check for a farmers hand, if there is one, redeal.
                if(farmersHand && checkFarmersHand(cards)){
                    redeal = true;
                    continue dealing;
                }
                //record hand and player
                player.setHand(new Hand(cards));
            }

        }

        //deal the kitty
        for(int i=0; i < 4; i++){
            kitty.add(deck.get(dealIndex));
            dealIndex++;
        }
        
        //denote (via copy) the top-card of the kitty for the table
        faceUpCardCopy = kitty.get(0).clone();

        //at this point the round is setup, and player interactions may begin.
    }


    /**
     * helper function to affirm that the player order and teams passed into the Round constructor are correct,
     * that all four players are unique and that each player belongs to a team
     * @throws IllegalArgumentException
     */
    private void validateTeams(List<Player> playerOrder, List<Team> teams) throws IllegalArgumentException{
        //confirm only 4 players
    	if(playerOrder.size() != 4){
            throw new IllegalArgumentException("There must be exactly 4 players in a round.");
        }
        
        //confirm player names are unique
        if ( 4 !=  playerOrder.stream().map(p -> p.getName()).distinct().count()){
        	throw new IllegalArgumentException("Multiple players are sharing the same name in the round. All player names must be unique.");
        }
        
        //confirm that the teams match the players and their order
        List<Player> teamA = Arrays.asList(playerOrder.get(0),playerOrder.get(2));
        List<Player> teamB = Arrays.asList(playerOrder.get(1),playerOrder.get(3));

        if ( !(teams.get(0).getPlayers().containsAll(teamA) && teams.get(1).getPlayers().containsAll(teamB))
        	&& !(teams.get(0).getPlayers().containsAll(teamB) && teams.get(1).getPlayers().containsAll(teamA))
        	){
            throw new IllegalArgumentException("the players in player order to not match/or sit in accordance with the teams provided");
        }
    }

    /**
     * Checks a player's hand for a farmers hand (a hand consisting of only 9's and 10's)
     * @param hand the hand to check
     * @return true if the player has a farmer's hand.
     */
    private boolean checkFarmersHand(List<Card> hand){
        //if all cards are a 9 or 10 then there is a farmers hand
    	return hand.stream().allMatch(c -> (c.getRank() == Rank.NINE || c.getRank() == Rank.TEN) );
    }

    /**
     * Constructs a proper Euchre deck of 24 cards 9-A in all four suits
     * @return the Euchre deck.
     */
    public static ArrayList<Card> makeDeck(){
        ArrayList<Card> newdeck = new ArrayList<Card>();
        for(Suit suit : Suit.values()){
            for(Rank rank : Rank.values()){
                newdeck.add(new Card(rank, suit));
            }
        }
        return newdeck;
    }

    /**
     * shuffles the order of the cards passed. In fact shuffles them twice just to be sure.
     * @param cards the cards to shuffle
     */
    public static void shuffleCards(List<Card> cards){
        Collections.shuffle(cards);
        Collections.shuffle(cards); //second time just to be sure ;P (if '0 is never rolled' the top card wont change...)
    }


    /**
     * returns the dealer for this round
     * @return the dealer's name
     */
    public Player dealer(){
        return orderedPlayers.get(0);
    }

    /**
     * Returns the player who will play/has played first this round. If going alone has not been decided, it will be the player after the dealer.
     * @return the player who will play/has played first this round. If going alone has not been decided, it will be the player after the dealer.
     */
    public Player getLeadPlayer(){
        if(!tricks.isEmpty()){ //the first trick is already over... go check who played it.
            String playerName = tricks.get(0).getLedPlay().getPlayer();
            return orderedPlayers.stream().filter(p -> p.getName().equalsIgnoreCase(playerName)).findFirst().get();
        }else if(currentTrick != null){
            if(!currentTrick.getPlays().isEmpty()){ //the first play has already played... go check who played it.
            	String playerName = currentTrick.getLedPlay().getPlayer();
                return orderedPlayers.stream().filter(p -> p.getName().equalsIgnoreCase(playerName)).findFirst().get();
            }
            //if the trick exists but the player hasn't played yet, getNextPlayer will point to the lead player because of (prepareRoundAndFirstTrick())
            return getNextPlayer();
        }else{
            //it should be the player after the dealer.. since playing alone would not yet have been decided...
            return orderedPlayers.get(1);
        }
    }

    /**
     * returns the team who called trump this round
     * @return the team who called trump this round
     */
    public Team teamWhoCalledTrump(){
    	if(trumpPlayer == null){
    		throw new IllegalStateException("No player has called trump yet this round.");
    	}
    	return teams.stream().filter(t -> t.hasMember(trumpPlayer)).findFirst().get();
    }

    /**
     * returns the team who is playing for a Euchre
     * @return the team who is playing for a Euchre
     */
    public Team opposingTrumpTeam(){
    	if(trumpPlayer == null){
    		throw new IllegalStateException("No player has called trump yet this round.");
    	}
    	return teams.stream().filter(t -> ! t.hasMember(trumpPlayer)).findFirst().get();

    }
    /**
     * Returns the teammate of the given player
     * @param player the player to find the teammate for
     * @return the player's teammate
     * @throws MissingPlayer if the player could not be found.
     */
    public Player teammate(Player player) throws MissingPlayer{
    	int playerIdx = orderedPlayers.indexOf(player);
    	if (playerIdx < 0){
    		throw new MissingPlayer(player.getName(), "Player " + player.getName() + " could not be found on either team");
    	}
    	return orderedPlayers.get((playerIdx+2)%4);
        
    }

    /**
     * Denotes that player has decided that the face-up card will be trump. If the playingAlone is true, then the player will play alone.
     * If the player is the dealer's partner, then the playingAlone parameter is ignored, and the player will play alone.
     * @param player  the player who called trump
     * @param playingAlone if the player is playing alone or not (if he is the dealer's partner then this is ignored, and the function will use true)
     * @throws IllegalStateException if trump has already been selected
     * @throws MissingPlayer thrown if the supplied player could not be found
     * @throws IllegalPlay thrown if the player tries to call a suit that they have no cards for
     */
    public void playerCallsUpCard(Player player, boolean playingAlone) throws IllegalStateException, MissingPlayer, IllegalPlay{
    	validateTrumpCall(player,faceUpCardCopy.getSuit(null),playingAlone);
    	
        if(teammate(player).equals(dealer())){ //forced to play alone if your partner is the dealer.
            playingAlone = true;  
        }
        
        prepareRoundAndFirstTrick(player, faceUpCardCopy.getSuit(null), playingAlone);
    }


    /**
     * Denotes that the player has decided to call trump. Their team will be responsible to collect 3 tricks in order to score a single point.
     * @param player the player who has decided to call trump
     * @param trumpSuit the suit the player has decided to call
     * @param playingAlone whether or not the player has chosen to play alone
     * @throws IllegalStateException if trump has already been selected
     * @throws MissingPlayer thrown if the supplied player could not be found
     * @throws IllegalPlay thrown if the player tries to call a suit that they have no cards for
     */
    public void playerCallsTrump(Player player, Suit trumpSuit, boolean playingAlone) throws IllegalStateException, MissingPlayer, IllegalPlay{
    	validateTrumpCall(player,trumpSuit,playingAlone);
    	
        if(trumpSuit == faceUpCardCopy.getSuit(null)){
            throw new IllegalPlay(player.getName(), "The suit " + trumpSuit + "cannot be called, as it was the suit of the face-up card this round.");
        }
        
        prepareRoundAndFirstTrick(player,trumpSuit,playingAlone);
    }

    /**
     * Basic checks to ensure that the player can make a valid trump call 
     * @param player the player who has decided to call trump
     * @param trumpSuit the suit the player has decided to call
     * @param playingAlone whether or not the player has chosen to play alone
     * @throws IllegalStateException if trump has already been selected
     * @throws MissingPlayer thrown if the supplied player could not be found
     * @throws IllegalPlay thrown if the player tries to call a suit that they have no cards for
     */
    private void validateTrumpCall(Player player, Suit trumpSuit, boolean playingAlone) throws IllegalStateException, MissingPlayer, IllegalPlay{
        if(trump != null){  //validate this is not being called more than once a round
            throw new IllegalStateException("Trump has been decided, cannot call trump again.");
        }
        if(trumpSuit == null){
            throw new IllegalPlay(player.getName(), "The player must pick a proper suit, even when it comes down to 'stick the dealer'");
        }
        if(orderedPlayers.indexOf(player) < 0){    //validate that player is in the round
            throw new MissingPlayer(player.getName(), "Player: " + player.getName() + " is not playing in this round.");
        }
        if(!playerHasSuitToCall(player,trumpSuit)){//validate the player has a card of that suit to call it.
            throw new IllegalPlay(player.getName(), "Player: " + player.getName() + " does not have a card in " + trumpSuit.getName() + ", and therefore cannot call this trump.");
        }
    }
    
    /**
     * Helper function for setting up the trump, trump player, playing alone or not, and establishing the first trick, and setting the pointer to the start player.
     * @param player the player who called trump
     * @param trumpSuit the suit of trump for the round
     * @param playingAlone whether or not the player who called trump will be playing alone or not.
     */
    private void prepareRoundAndFirstTrick(Player player, Suit trumpSuit, boolean playingAlone){
        this.trump = trumpSuit;
        this.trumpPlayer = player;
        this.trumpPlayerTeammate = teammate(player);
        this.playingAlone = playingAlone;
        currentTrick = new Trick(trump,playingAlone); //setup first trick
        setNextPlayer(); //sets the start player, first player after the dealer (needs to be done after trick is created)
    }

    /**
     * Denotes the dealer placing the face-up card in hand and putting back another card.
     * @param cardToReturn the card to give back to the kitty face down.
     */
    public void dealerTakesFaceUpCard(Card cardToReturn) throws IllegalPlay{
        Card faceupCard = kitty.remove(0);
        
        Card kittyReplacement;
        try{
        	kittyReplacement = dealer().getHand().swapWithFaceUpCard(faceupCard, cardToReturn);  //this function validates both that the player has the card to return and that the player is picking up the correct card.
        }catch(IllegalPlay ex){
        	throw new IllegalPlay(dealer().getName(), "Dealer " + dealer().getName() + " could not swap with face-up card.", ex );
        } 
        kitty.add(kittyReplacement);
    }
    

    /**
     * Returns a player in this hand given their name. Needed when referring to tricks which don't hold the player
     * @param name of the player to check for
     * @return the player is playing in this round.
     */
    public Player findPlayer(String playerName){
    	Player player = orderedPlayers.stream().filter(p -> p.getName().equals(playerName)).findFirst().orElse(null);
    	if(player == null){
    		throw new MissingPlayer(playerName, "Player: " + playerName + " is not playing in this round. Could not find player");
    	}
    	return player;
    }


    /**
     * Validates that the player calling suit has a card of that suit. otherwise it would be illegal
     * @param player the player calling suit
     * @param callingSuit the suit the player called
     * @return true if the player has a natural card of that suit. (Left Bowers do not count)
     */
    private boolean playerHasSuitToCall(Player player, Suit callingSuit) throws MissingPlayer{
        return player.getHand().hasSuit(callingSuit, null);
    }

    /**
     * Returns the player who must play next
     * @return the next player up
     */
    public Player getNextPlayer(){
        return orderedPlayers.get(nextPlayerIndex);
    }


    /**
     * Once a player has played a card, call this method, and then the next player will change to shuffle to the next next player
     * accounts for 'playing alone' and moving along in order.
     */
    private void setNextPlayer() throws MissingPlayer {
        if(currentTrick.isTrickEmpty() && tricks.size() != 0){ //we're at the beginning of a trick that is not the first trick
        	
            //set the next player to the last trick taker
            String nextPlayerName = tricks.get(tricks.size() -1).getTrickTaker();
            for(int i =0; i < 4; i++){
                if(orderedPlayers.get(i).getName().equalsIgnoreCase(nextPlayerName)){
                    nextPlayerIndex = i;
                    return;
                }
            }
            throw new MissingPlayer(nextPlayerName, "Could not find player " + nextPlayerName + " said to be a trick leader to lead next trick.");
        }else{ //otherwise continue play
            nextPlayerIndex++;
            nextPlayerIndex = nextPlayerIndex%4;
            
            //check if the player set is supposed to be skipped because of playing alone.
            if(playingAlone && getNextPlayer().equals(trumpPlayerTeammate) ){
                setNextPlayer();  //do this once again.
            }
        }
    }

    /**
     * Player plays a card to the trick, remove the card from his/her hand, move it into the trick
     * @param card   the card played
     * @param player the player who played the card.
     * @throws IllegalStateException if trump has not been selected yet, or there have already been 5 tricks played, or a player plays a card they do not have then this exception is thrown.
     * @throws MissingPlayer thrown if the supplied player could not be found
     */
    public void playerPlaysCardToTrick(Card card, Player player) throws IllegalStateException, MissingPlayer, IllegalPlay{
        if(trump == null){  //validate this is not called too early
            throw new IllegalStateException("Trump has not been decided yet, player cannot play a card.");
        }
        if(tricks.size() == 5){  //validate this is not called too late
            throw new IllegalStateException("5 Tricks have already been played, the player cannot play a card.");
        }
        if(orderedPlayers.indexOf(player) < 0){  ///validate that player is in the round
            throw new MissingPlayer(player.getName(), "Player: " + player.getName() + " is not playing in this round.");
        }
        if(!getNextPlayer().equals(player)){  //validate that it is this player's turn.
            throw new IllegalStateException("It is not " + player.getName() + "'s turn to play.");
        }
        if(!currentTrick.isTrickEmpty()){
            //check to make sure the play is legal (they are following suit) (validating the card in hand is done below)
            List<Card> tempHand = Card.findLegalCards(player.getHand().getCards(), currentTrick.getLedCard(), trump);
            if(!tempHand.contains(card)){
                throw new IllegalPlay(player.getName(), "The player played an illegal card from their hand. They played: " + card.getName() + ". The legal cards are: " + tempHand.toString());
            }
        }

        Card cardFromPlayerHand;
        try{
        	cardFromPlayerHand = player.getHand().pullCard(card);  //take the card from the hand & validate it's in their hand.
        }catch(IllegalPlay ex){
        	throw new IllegalPlay(player.getName(), "Player " + player + " could not pull card to play to trick.", ex );
        }
        currentTrick.addPlay(player.getName(), cardFromPlayerHand);  //add the card to the trick as a play. (again tricks shouldn't hold refs to players, as theyre passed to playerai)


        //if it's the last card in the trick, add to tricks.
        if(currentTrick.isTrickComplete()){
            tricks.add(currentTrick);

            if(isRoundComplete()){  //if its the last trick calculate the winner
                calculateWinner();
            }else{   //otherwise make the next trick
                currentTrick = new Trick(trump,playingAlone);            	
            }
        }

        setNextPlayer();  //this player is done, move to the next player.
    }


    /**
     * Returns true if there are 5 complete tricks.
     * @return true if there are 5 complete tricks.
     */
    public boolean isRoundComplete(){
        if(tricks.size() != 5){
            return false;
        }
        for(Trick trick : tricks){
            if(!trick.isTrickComplete()){
                return false;
            }
        }
        return true;
    }

    /**
     * Helper function to calculate the winning team of the round and their points.
     * @throws MissingPlayer thrown a player could not be found
     */
    private void calculateWinner() throws IllegalStateException, MissingPlayer{
        //count the tricks for the team who called trump
        long callingTeamTricks  = tricks.stream().map(t -> t.getTrickTaker()).filter(pn -> teamWhoCalledTrump().playerNames().contains(pn)).count();
        
        
        if(callingTeamTricks >= 3){
        	
            //the team who called trump took at least 3 tricks
            winners = teamWhoCalledTrump();
            
            if(callingTeamTricks == 5){
                if(playingAlone){
                    winAmount = 4; //perfect alone hand
                }else{
                    winAmount = 2; //full 5 tricks for 2
                }
            }else{
                winAmount = 1; //alone or team play but only 3 or 4 tricks
            }
            
        }else{
            //EUCHRED (opposing team gets two points
            winners = opposingTrumpTeam();
            winAmount = 2;
        }
    }



    /**************************
     *  Game Facing Getters
     **************************/

    /**
     * get the currentTrick in the round
     * @return the currentTrick. or null if called too early.
     */
    public Trick getCurrentTrick() {
        return currentTrick;
    }

    /**
     * returns the card that was face up to the table whether picked up or not.
     * @return the card that was face up to the table whether picked up or not.
     */
    public Card getFaceUpCardCopy() {
        return faceUpCardCopy;
    }

    /**
     * returns if the player who called trump decided to play alone or not
     * @return if the player who called trump decided to play alone or not
     */
    public boolean isPlayingAlone() {
        return playingAlone;
    }

    /**
     * Returns all the tricks that have been completed in the round so far
     * @return all the tricks that have been completed in the round so far
     */
    public List<Trick> getTricks() {
        return tricks;
    }

    /**
     * Get the trump for the current round. Returns null if the trump is not yet determined
     * @return the trump for the round.
     */
    public Suit getTrump() {
        return trump;
    }

    /**
     * Returns the player who called trump
     * @return the player who called trump
     */
    public Player getTrumpPlayer() {
        return trumpPlayer;
    }

    /**
     * get the amount of points which the team who scored earned.
     * @return the amount of points earned this round by the winning team
     */
    public int getWinAmount() {
        return winAmount;
    }

    /**
     * get the team which won the round
     * @return the team which won the round
     */
    public Team getWinners() {
        return winners;
    }



    
            
}
