/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.player_ai;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.fei0x.euchre.game.Card;
import com.fei0x.euchre.game.Play;
import com.fei0x.euchre.game.Suit;
import com.fei0x.euchre.game.Trick;


/**
 * This player implementation prompts via System.in (command line input) for input from a human!
 * With this player, you can PLAY Euchre against computer opponents.
 * @author jsweetman
 */
public class HumanPlayerAI extends PlayerAI {

    /**
     * where to write the player prompts
     */
    private PrintStream out;

    /**
     * where to read the player inputs from.
     */
    private BufferedReader in;

    /**
     * A human player for Euchre
     * @param playerName the name of the player
     * @param out the outputstream for prompting the user.
     * @param in the inputstream for reading commands from the user.
     */
    public HumanPlayerAI(PrintStream out, InputStream in){
        super();
        this.out = out;
        this.in = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public boolean callItUp(Card faceUpCard) {
    	//show the player their hand
        out.println("| Your hand is:");
        printMyCards();
        
        //show player the face-up card
        out.println("| Face-Up Card is:");
        out.println("|   " + faceUpCard.getName());
        
        //get a decision from the player
        String response;
        while(true){
            //ask the player if they want to call/pick it up?
        	
            if(askGame.whoIsDealer().equalsIgnoreCase(askGame.myName())){  //if you're the dealer
                out.println("| Pick it up? (Y/N)"); 
            }else if(askGame.whoIsDealer().equalsIgnoreCase(askGame.partnersName())){  //if you're partner's the dealer
                out.println("| Call it up? And play it alone. (Y/N)");
            }else{
                out.println("| Call it up? (Y/N)"); //if you're not the dealer
            }
            
            out.print(">> ");
            try {
                response = in.readLine();
            } catch (IOException ex) {throw new RuntimeException("Failed to Read Player Input.", ex);}
            if(response.equalsIgnoreCase("Y")){
                return true;
            }else if(response.equalsIgnoreCase("N")){
                return false;
            }
        }
    }

    @Override
    public Card swapWithFaceUpCard(Card faceUpCard) {
        out.println("| You have been called up, the face-up card is:");
        out.println("|   " + faceUpCard.getName());

        out.println("| Select a card to swap with the kitty:");
        printMyCards();
        
      //get a decision from the player
        String response;
        while(true){
            
        	out.println("| Select card (by number listed above)");
            out.print(">> ");
            
            try {
                response = in.readLine();
            } catch (IOException ex) {throw new RuntimeException("Failed to Read Player Input.", ex);}

            int responseValue;
            try{
                responseValue = Integer.parseInt(response);
                Card playCard = askGame.myHand().get((responseValue-1));
                return playCard;
            }catch(Exception e){//error parsing input, try again

            }
        }
    }

    @Override
    public Suit callSuit(Card turnedDownCard) {
        out.println("| The top-card of the kitty was turned down, it was:");
        out.println("|   " + turnedDownCard.getName());

        out.println("| Your hand is:");
        printMyCards();
        
        out.println("| Choose a suit or pass:");
        out.println("|   (1) Spades");
        out.println("|   (2) Diamonds");
        out.println("|   (3) Clubs");
        out.println("|   (4) Hearts");
        out.println("|   (5) Pass");

      //get a decision from the player
        String response;
        while(true){
            out.println("| Select suit (by number listed above)");
            out.print(">> ");
            try {
                response = in.readLine();
            } catch (IOException ex) {throw new RuntimeException("Failed to Read Player Input.", ex);}

            int responseValue;
            try{
                responseValue = Integer.parseInt(response);
                if(responseValue == 1){
                    return Suit.SPADES;
                }else if(responseValue == 2){
                    return Suit.DIAMONDS;
                }else if(responseValue == 3){
                    return Suit.CLUBS;
                }else if(responseValue == 4){
                    return Suit.HEARTS;
                }else if(responseValue == 5){
                    return null;
                }
            }catch(Exception e){//error parsing input, try again

            }
        }
    }

    @Override
    public Suit stickTheDealer(Card turnedDownCard) {
        out.println("| The top-card of the kitty was turned down, it was:");
        out.println("|   " + turnedDownCard.getName());

        out.println("| Your hand is:");
        printMyCards();
        
        out.println("| Since you are the dealer you may not pass, Choose a suit:");
        out.println("|   (1) Spades");
        out.println("|   (2) Diamonds");
        out.println("|   (3) Clubs");
        out.println("|   (4) Hearts");

       //get a decision from the player
        String response;
        while(true){
            
        	out.println("| Select suit (by number listed above)");
            out.print(">> ");
            
            try {
                response = in.readLine();
                
            } catch (IOException ex) {throw new RuntimeException("Failed to Read Player Input.", ex);}

            int responseValue;
            try{
                responseValue = Integer.parseInt(response);
                if(responseValue == 1){
                    return Suit.SPADES;
                }else if(responseValue == 2){
                    return Suit.DIAMONDS;
                }else if(responseValue == 3){
                    return Suit.CLUBS;
                }else if(responseValue == 4){
                    return Suit.HEARTS;
                }
            }catch(Exception e){//error parsing input, try again

            }
        }
    }

    @Override
    public boolean playAlone() {
        //get a response from the player
    	String response;
        while(true){
            
        	out.println("| You called trump, will you play alone? (Y/N)");
            out.print(">> ");
            
            try {
                response = in.readLine();

            } catch (IOException ex) {throw new RuntimeException("Failed to Read Player Input.", ex);}
            if(response.equalsIgnoreCase("Y")){
                return true;
            }else if(response.equalsIgnoreCase("N")){
                return false;
            }
        }
    }

    @Override
    public Card playCard(Trick currentTrick) {
    	
    	//show cards played so far
        out.println("| Your turn to play, The trick so far:");
        List<Play> plays = currentTrick.getPlays();
        plays.stream().forEach(p -> out.println("|   " + p.getCard().getName() + "  - " + p.getPlayer()));

        //show my cards
        out.println("| Select a card to play (trump is " + currentTrick.getTrump().getName() + ") :");
        printMyCards();
       
       //get a decision from the player
        String response;

        while(true){
            out.println("| Select card (by number listed above)");
            out.print(">> ");
        
            try {
                response = in.readLine();
            } catch (IOException ex) {throw new RuntimeException("Failed to Read Player Input.", ex);}

            int responseValue;
            try{
                responseValue = Integer.parseInt(response);
                Card playCard = askGame.myHand().get((responseValue-1));
                return playCard;
            }catch(Exception e){//error parsing input, try again

            }
        }
    }


    /**
     * Convenience function to print all of my current cards
     */
    private void printMyCards(){
        List<Card> cards = askGame.myHand();
        for(int i=0; i < cards.size(); i++){
            out.println("|   (" + (i+1) + ") " + cards.get(i).getName());
        }
    }
    
}
