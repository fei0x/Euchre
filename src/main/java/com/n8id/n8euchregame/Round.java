/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchregame;

import com.n8id.n8euchreexceptions.IllegalPlay;
import com.n8id.n8euchreexceptions.MissingPlayer;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A round consists of a dealer, a trump determining phase, a trump and 5 tricks.
 * The winning team of a round recieves points based on their performance.
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
    private ArrayList<Trick> tricks = new ArrayList<Trick>();

    /**
     * the current trick, once it is complete will move into tricks
     */
    private Trick currentTrick = null;

    /**
     * The 4 cards in the kitty, may change after trump is decided if the player chooses to swap a card into the kitty
     * The top card (element 0) of the kitty is revealed to the table as the faceUpCard and copied to the faceUpCardCopy, so everybody may reference it later.
     * If the faceup card is turned down it remains in the kitty, if the faceup card is taken, it is removed from the kitty and replaced with another card.
     */
    private ArrayList<Card> kitty = new ArrayList<Card>();
    
    /**
     * The Players in this round in the order of this list, the first element is the dealer.
     * This also includes their hand of cards.
     */
    private ArrayList<PlayerHand> playerHandsAndOrder = new ArrayList<PlayerHand>();

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
    private String trumpPlayer = null;

    /**
     * whether or not the player has opted to go alone
     */
    private boolean playingAlone = false;

    /**
     * The two teams playing this round.
     */
    private ArrayList<Team> teams = new ArrayList<Team>();


    /*********************
     * Ongoing Round Data
     *******************/

    /**
     * the player who is required to play the next card.
     */
    private int nextPlayer = 0;//start it as the dealer, but do 'setNextPlayer' to move to the first player after the dealer after trump and alone have been decided.

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
     * Iniate a round of euchre, given the players (by name) in a specific order for play.
     * It builds the deck, shuffles the cards, deals the cards, and turns up the top card of the kitty.
     * @param playerOrder the order of the players, the first player is the dealer.
     * @param teams the two teams
     * @throws IllegalArgumentException if the playerOrder and teams are not in alignment.
     */
    public Round(ArrayList<String> playerOrder, ArrayList<Team> teams) throws IllegalArgumentException{

        validateTeams(playerOrder,teams);
        this.teams = teams;
        //assemble cards
        ArrayList<Card> deck = makeDeck();

        boolean redeal= true;//true to get in.
        int dealIndex = 0;

        while(redeal){
            redeal = false;//hopefully only need to deal this one time.
            playerHandsAndOrder.clear();
            dealIndex = 0;

            //shuffle the cards
            shuffleCards(deck);

            //deal cards to each player
            for(String player : playerOrder){
                ArrayList<Card> hand = new ArrayList<Card>();
                //deal hand
                for(int i=0; i < 5; i++){
                    hand.add(deck.get(dealIndex));
                    dealIndex++;
                }
                //check for a farmers hand, if there is one, redeal.
                if(farmersHand && checkFarmersHand(hand)){
                    redeal = true;
                }
                //record hand and player
                playerHandsAndOrder.add(new PlayerHand(player,hand));
            }

        }

        //deal the kitty
        for(int i=0; i < 4; i++){
            kitty.add(deck.get(dealIndex));
            dealIndex++;
        }
        //denote (via copy) the top-card of the kitty for the table
        faceUpCardCopy = kitty.get(0).clone();

        //TODO CHECK FOR FARMERS HAND

        //at this point the round is setup, and player interactions may begin.
    }


    /**
     * helper function to affirm that the player order and teams passed into the Round constructor are correct,
     * that all four players are unique, that each player belongs to a team, and that the
     * @throws IllegalArgumentException
     */
    private void validateTeams(ArrayList<String> playerOrder, ArrayList<Team> teams) throws IllegalArgumentException{
        if(playerOrder.size() != 4){
            throw new IllegalArgumentException("There must be exactly 4 players in a round.");
        }
        //confirm player names are unique
        for(int i= 0; i < 4; i++){
            for(int j= i+1; j < 4; j++){
                if(playerOrder.get(i).equalsIgnoreCase(playerOrder.get(j))){
                    throw new IllegalArgumentException("Player" + i + " (" + playerOrder.get(i) + ") and Player" + j + "(" + playerOrder.get(i) + ") share the same name");
                }
            }
        }
        //confirm teams are sitting opposite.
        boolean badTeams = false;
        if(teams.get(0).hasMember(playerOrder.get(0))){
            if(!teams.get(0).hasMember(playerOrder.get(2))){
                badTeams = true;
            }
            if(!teams.get(1).hasMember(playerOrder.get(1))){
                badTeams = true;
            }
            if(!teams.get(1).hasMember(playerOrder.get(3))){
                badTeams = true;
            }
        }else if(teams.get(1).hasMember(playerOrder.get(0))){
            if(!teams.get(1).hasMember(playerOrder.get(2))){
                badTeams = true;
            }
            if(!teams.get(0).hasMember(playerOrder.get(1))){
                badTeams = true;
            }
            if(!teams.get(0).hasMember(playerOrder.get(3))){
                badTeams = true;
            }
        }else{
            badTeams = true;
        }
        if(badTeams){
            throw new IllegalArgumentException("the players in player order to not match/or sit in accordance with the teams provided");
        }
    }

    /**
     * Checks a player's hand for a farmers hand (a hand consisting of only 9's and 10's
     * @param hand the hand to check
     * @return true if the player has a farmer's hand.
     */
    private boolean checkFarmersHand(ArrayList<Card> hand){
        for(Card card : hand){
            if(!(card.getRank() == Rank.NINE || card.getRank() == Rank.TEN)){
                //if the card is not a 9 or a 10 they don't have a farmers hand.
                return false;
            }
        }//return true if any non-9or10 cards can't be found.
        return true;
    }

    /**
     * Constructs a proper eucher deck of 24 cards 9-A in all four suits
     * @return the euchre deck.
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
    public static void shuffleCards(ArrayList<Card> cards){
        Collections.shuffle(cards);
        Collections.shuffle(cards);//second time just to be sure =P (if '0 is never rolled' the top card wont change...)
    }

    /**
     * Returns the players in their seating order starting with the deal in this round.
     * @return the players in the round, staring with the dealer.
     */
    public ArrayList<String> getPlayersInOrder(){
        ArrayList<String> playerOrder = new ArrayList<String>();
        for(PlayerHand player : playerHandsAndOrder){
            playerOrder.add(player.getPlayerName());
        }
        return playerOrder;
    }

    /**
     * returns the dealer for this round
     * @return the dealer's name
     */
    public String getDealer(){
        return playerHandsAndOrder.get(0).getPlayerName();
    }

    /**
     * Returns the player who will play/has played first this round. If going alone has not been decided, it will be the player after the dealer.
     * @return the player who will play/has played first this round. If going alone has not been decided, it will be the player after the dealer.
     */
    public String getLeadPlayer(){
        if(!tricks.isEmpty()){//the first trick is already over... go check who played it.
            return tricks.get(0).getLedPlay().getPlayer();
        }else if(currentTrick != null){
            if(!currentTrick.getPlays().isEmpty()){//the first play has already played... go check who played it.
                return currentTrick.getLedPlay().getPlayer();
            }
            //if the trick exists but the player hasn't played yet, getNextPlayer will point to the lead player because of (prepareRoundAndFirstTrick())
            return getNextPlayer();
        }else{
            //it should be the player after the dealer.. since playing alone has not yet been decided...
            return playerHandsAndOrder.get(1).getPlayerName();
        }
    }

    /**
     * returns the team who called trump this round
     * @return the team who called trump this round
     */
    public Team getTeamWhoCalledTrump(){
        if(teams.get(0).hasMember(trumpPlayer)){
            return teams.get(0);
        }else{
            return teams.get(1);
        }

    }

    /**
     * returns the team who is playing for a euchre
     * @return the team who is playing for a euchre
     */
    public Team getAposingTrumpTeam(){
        if(!teams.get(0).hasMember(trumpPlayer)){
            return teams.get(0);
        }else{
            return teams.get(1);
        }

    }
    /**
     * Returns the name of the teammate of the given player
     * @param player the player to find the teammate for
     * @return the player's teammate
     * @throws MissingPlayer if the player could not be found.
     */
    public String getTeammate(String player) throws MissingPlayer{
        for(Team team : teams){
            if(team.hasMember(player)){
                return team.getTeammate(player);
            }
        }
        throw new MissingPlayer(player, "Player " + player + " could not be found on either team");
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
    public void playerCallsUpCard(String player, boolean playingAlone) throws IllegalStateException, MissingPlayer, IllegalPlay{
        if(trump != null){//validate this is not being called again
            throw new IllegalStateException("Trump has been decided, cannot call trump again.");
        }
        if(!playerInRound(player)){//validate that player is in the round
            throw new MissingPlayer(player, "Player: " + player + " is not playing in this round.");
        } 
        if(!playerHasSuitToCall(player,faceUpCardCopy.getSuit(null))){//validate the player has a card of that suit to call it.
            throw new IllegalPlay(player, "Player: " + player + " does not have a card in " + faceUpCardCopy.getSuit(null).getName() + ", and therefore cannot call this trump.");
        }
        if(player.equals(getTeammate(getDealer()))){
            playingAlone = true; //forced to play alone if your partner is the dealer.
        }
        
        prepareRoundAndFirstTrick(player, faceUpCardCopy.getSuit(null), playingAlone);
    }


    /**
     * Denotes tha the player has decided to call trump. Their team will be responsible to collect 3 tricks in order to score a single point.
     * @param player the player who has decided to call trump
     * @param trumpSuit the suit the player has decided to call
     * @param playingAlone whether or not the player has chosen to play alone
     * @throws IllegalStateException if trump has already been selected
     * @throws MissingPlayer thrown if the supplied player could not be found
     * @throws IllegalPlay thrown if the player tries to call a suit that they have no cards for
     */
    public void playerCallsTrump(String player, Suit trumpSuit, boolean playingAlone) throws IllegalStateException, MissingPlayer, IllegalPlay{
        if(trump != null){//validate this is not being called again
            throw new IllegalStateException("Trump has been decided, cannot call trump again.");
        }
        if(!playerInRound(player)){//validate that player is in the round
            throw new MissingPlayer(player, "Player: " + player + " is not playing in this round.");
        }
        if(!playerHasSuitToCall(player,trumpSuit)){//validate the player has a card of that suit to call it.
            throw new IllegalPlay(player, "Player: " + player + " does not have a card in " + trumpSuit.getName() + ", and therefore cannot call this trump.");
        }
        if(trumpSuit == faceUpCardCopy.getSuit(null)){
            throw new IllegalPlay(player, "The suit " + trumpSuit + "cannot be called, as it was the suit of the face-up card this round.");
        }
        prepareRoundAndFirstTrick(player,trumpSuit,playingAlone);
    }

    /**
     * Helper function for setting up the trump, trump player, playing alone or not, and establishing the first trick, and setting the pointer to the start player.
     * @param player the player who called trump
     * @param trumpSuit the suit of trump for the round
     * @param playingAlone whether or not the player who called trump will be playing alone or not.
     */
    private void prepareRoundAndFirstTrick(String player, Suit trumpSuit, boolean playingAlone){
        this.trump = trumpSuit;
        this.trumpPlayer = player;
        this.playingAlone = playingAlone;
        currentTrick = new Trick(trump,playingAlone); //setup first trick
        setNextPlayer();//sets the start player, first player after the dealer (needs to be done after trick is created)
    }

    /**
     * Denotes the dealer placing the face-up card in hi haand and putting back another card.
     * @param cardToReturn the card to give back to the kitty face down.
     */
    public void dealerTakesFaceUpCard(Card cardToReturn) throws IllegalPlay{
        Card faceupCard = kitty.remove(0);
        PlayerHand hand = playerHandsAndOrder.get(0);
        Card kittyReplacement = hand.swapWithFaceUpCard(faceupCard, cardToReturn);//this function validates both that the player has the card to return and that the player is picking up the correct card.
        kitty.add(kittyReplacement);
    }

    /**
     * Tells you if this player is playing in this round.
     * @param playerName the player to check for
     * @return true if the player is playing this round.
     */
    public boolean playerInRound(String playerName){
        for(PlayerHand player : playerHandsAndOrder){
            if(player.getPlayerName().equalsIgnoreCase(playerName)){
                return true;
            }
        }
        return false;
    }

    /**
     * Validates that the player calling suit has a card of that suit. otherwise it would be illegal
     * @param playerName the name of the player calling suit
     * @param callingSuit the suit the player called
     * @return true if the player has a natural card of that suit. (Left Bowers do not count)
     */
    private boolean playerHasSuitToCall(String playerName, Suit callingSuit) throws MissingPlayer{
        return getPlayerHand(playerName).hasSuit(callingSuit, null);
    }

    /**
     * Returns the name of the player who must play next
     * @return the name of the next player up
     */
    public String getNextPlayer(){
        return playerHandsAndOrder.get(nextPlayer).getPlayerName();
    }


    /**
     * Once a player has played a card (call this), and then the next player will change to the correct player
     * accounts for 'playing alone' and moving along in order.
     */
    private void setNextPlayer() throws MissingPlayer {
        if(currentTrick.isTrickEmpty() && tricks.size() != 0){
            //set the next player to the last trick taker
            String nextPlayerName = tricks.get(tricks.size() -1).getTrickTaker();
            for(int i =0; i < 4; i++){
                if(playerHandsAndOrder.get(i).getPlayerName().equalsIgnoreCase(nextPlayerName)){
                    nextPlayer = i;
                    return;
                }
            }
            throw new MissingPlayer(nextPlayerName, "Could not find player " + nextPlayerName + " said to be a trick leader to lead next trick.");
        }else{
            nextPlayer++;
            nextPlayer = nextPlayer%4;
            //check if the player set is supposed to be skipped because of playing alone.
            if(playingAlone && getNextPlayer().equalsIgnoreCase(getTeammate(trumpPlayer)) ){
                setNextPlayer();//do this once again.
            }
        }
    }

    /**
     * Player plays a card to the trick, remove the card from his/her hand, move it into the trick
     * @param card   the card played
     * @param player the player who played the card.
     * @throws IllegalStateException if trump has not been selected yet, or there have already been 5 tricks played, or a player plays a card they do not have then this exception is thrown.
     */
    public void playerPlaysCardToTrick(Card card, String player)throws IllegalStateException, MissingPlayer, IllegalPlay{
        if(trump == null){//validate this is not called too early
            throw new IllegalStateException("Trump has not been decided yet, player cannot play a card.");
        }
        if(tricks.size() == 5){//validate this is not called too late
            throw new IllegalStateException("5 Tricks have already been played, the player cannot play a card.");
        }
        if(!playerInRound(player)){///validate that player is in the round
            throw new MissingPlayer(player, "Player: " + player + " is not playing in this round.");
        }
        if(!getNextPlayer().equalsIgnoreCase(player)){//validate that it is this player's turn.
            throw new IllegalStateException("It is not " + player + "'s turn to play.");
        }
        if(!currentTrick.isTrickEmpty()){
            //check to make sure the play is legal (aka they are following suit) (validating the card in hand is done below)
            PlayerHand tempLegalHandCopy = new PlayerHand("temp", getPlayerHand(player).clone().showLegalPlays(currentTrick.getLedCard(), trump));
            if(!tempLegalHandCopy.hasCard(card)){
                throw new IllegalPlay(player, "The player played an illegal card from their hand. They played: " + card.getName() + ". The legal cards are: " + tempLegalHandCopy.toString());
            }
        }

        Card cardFromPlayerHand = getPlayerHand(player).pullCard(card);//take the card from the hand & validate it's in their hand.
        currentTrick.addPlay(player, cardFromPlayerHand); //add the card to the trick as a play.


        //if it's the last card in the trick, add to tricks.
        if(currentTrick.isTrickComplete()){
            tricks.add(currentTrick);
            currentTrick = new Trick(trump,playingAlone);
            //if its the last trick calculate the winner
            if(tricks.size() == 5){
                calculateWinner();
            }
        }

        setNextPlayer();//ok, this player is done, set the next one.
    }

    /**
     * returns the player who took the last completed trick
     * @return the name of the player who took the last trick
     * @throws IllegalStateException if there are no completed tricks yet
     */
    public String getPlayerWhoMadeLastTrick() throws IllegalStateException{
        if(tricks.size() == 0){
            throw new IllegalStateException("There have been no competed tricks yet to determine who played the last trick");
        }
        return tricks.get(tricks.size()-1).getTrickTaker();
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
     * Helper function calculating the winner of the round.
     * @throws IllegalStateException throws illegal state exception if there are not 5 complete tricks.
     */
    private void calculateWinner() throws IllegalStateException, MissingPlayer{
        if(!isRoundComplete()){
            throw new IllegalStateException("Cannot calculate winner now, round is not complete.");
        }else{
            //count the tricks for each team
            int callingTeamTricks = 0;
            String trumpPlayerTeammate = getTeammate(trumpPlayer);
            for(Trick trick: tricks){
                if(trick.getTrickTaker().equalsIgnoreCase(trumpPlayer)
                        || trick.getTrickTaker().equalsIgnoreCase(trumpPlayerTeammate)){
                    //calling team takes the trick
                    callingTeamTricks++;
                }
            }
            if(callingTeamTricks >= 3){
                //the team who called trump took at least 3 tricks
                winners = getTeamWhoCalledTrump();
                if(callingTeamTricks == 5){
                    if(playingAlone){
                        winAmount = 4; //perfect lone hand
                    }else{
                        winAmount = 2; //full 5 for 2
                    }
                }else{
                    winAmount = 1; //alone or team play but only 3 or 4 tricks
                }
            }else{
                //EUCHRED (aposing team gets two points
                winners = getAposingTrumpTeam();
                winAmount = 2;
            }
        }
    }


    /**
     * get the hand of the given player
     * @param player the player to retrieve the hand for
     * @return the hand of the player
     * @throws MissingPlayer if a the specified player cannot be found.
     */
    public PlayerHand getPlayerHand(String player) throws MissingPlayer{
        for(PlayerHand hand : playerHandsAndOrder){
            if(hand.getPlayerName().equalsIgnoreCase(player)){
                return hand;
            }
        }
        throw new MissingPlayer(player, "Cannot find hand for player " + player );
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
     * returns the card that was face up to the table wheter picked up or not.
     * @return the card that was face up to the table wheter picked up or not.
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
    public ArrayList<Trick> getTricks() {
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
    public String getTrumpPlayer() {
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
