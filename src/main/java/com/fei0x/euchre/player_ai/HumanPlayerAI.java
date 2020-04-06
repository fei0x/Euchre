/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchreplayers;

import com.n8id.n8euchregame.Card;
import com.n8id.n8euchregame.Play;
import com.n8id.n8euchregame.Suit;
import com.n8id.n8euchregame.Trick;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;


/**
 * This player implementation prompts via System.in (command line input) for input from a human!
 * With this player, you can PLAY Euchre against computer opponents.
 * @author jsweetman
 */
public class HumanPlayer extends Player {

    /**
     * where to write the player prompts
     */
    private PrintStream out;

    /**
     * where to read the player inputs from.
     */
    private BufferedReader in;

    /**
     * A human player for euchre
     * @param playerName the name of the player
     * @param out the outputstream for prompting the user.
     * @param in the inputstream for reading commands from the user.
     */
    public HumanPlayer(String playerName, PrintStream out, InputStream in){
        super(playerName);
        this.out = out;
        this.in = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public boolean callItUp(Card faceUpCard) {
        out.println("| Your hand is:");
        for(Card card : hand.getHand()){
            out.println("|   " + card.getName());
        }
        out.println("| Face-Up Card is:");
        out.println("|   " + faceUpCard.getName());
        String response;
        while(true){
            if(askGame.whoIsDealer().equalsIgnoreCase(getName())){
                out.println("| Pick it up? (Y/N)"); //if you're not the dealer
            }else if(askGame.whoIsDealer().equalsIgnoreCase(askGame.getPartnersName())){
                out.println("| Call it up? And play it alone. (Y/N)"); //if you're not the dealer
            }else{
                out.println("| Call it up? (Y/N)"); //if you're not the dealer
            }
            out.print(">> ");
            try {
                response = in.readLine();
                //out.println("you said:" + response);
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
        ArrayList<Card> cards = hand.getHand();
        for(int i=0; i < cards.size(); i++){
            out.println("|   (" + (i+1) + ") " + cards.get(i).getName());
        }
        String response;
        while(true){
            out.println("| Select card (by number listed above)");
            out.print(">> ");
            try {
                response = in.readLine();
                //out.println("you said:" + response);
            } catch (IOException ex) {throw new RuntimeException("Failed to Read Player Input.", ex);}

            int responseValue;
            try{
                responseValue = Integer.parseInt(response);
                Card playCard = cards.get((responseValue-1));
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
        for(Card card : hand.getHand()){
            out.println("|   " + card.getName());
        }
        out.println("| Choose a suit or pass:");
        out.println("|   (1) Spades");
        out.println("|   (2) Diamonds");
        out.println("|   (3) Clubs");
        out.println("|   (4) Hearts");
        out.println("|   (5) Pass");

        String response;
        while(true){
            out.println("| Select suit (by number listed above)");
            out.print(">> ");
            try {
                response = in.readLine();
                //out.println("you said:" + response);
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
        for(Card card : hand.getHand()){
            out.println("|   " + card.getName());
        }
        out.println("| Since you are the dealer you may not pass, Choose a suit:");
        out.println("|   (1) Spades");
        out.println("|   (2) Diamonds");
        out.println("|   (3) Clubs");
        out.println("|   (4) Hearts");

        String response;
        while(true){
            out.println("| Select suit (by number listed above)");
            out.print(">> ");
            try {
                response = in.readLine();
                //out.println("you said:" + response);
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
        String response;
        while(true){
            out.println("| You called trump, will you play alone? (Y/N)");
            out.print(">> ");
            try {
                response = in.readLine();
                //out.println("you said:" + response);
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
        out.println("| Your turn to play, The trick so far:");
        ArrayList<Play> plays = currentTrick.getPlays();
        for(int i = 0; i < plays.size(); i++ ){
            out.println("|   " + plays.get(i).getCard().getName() + "  - " + plays.get(i).getPlayer());
//            if((plays.size() % 2 != 0) && (i % 2 != 0)){
//                out.println(" (opponent)");
//            }else{
//                out.println(" (partner)");
//            }
        }

        out.println("| Select a card to play (trump is " + currentTrick.getTrump().getName() + ") :");
        ArrayList<Card> cards = hand.getHand();
        for(int i=0; i < cards.size(); i++){
            out.println("|   (" + (i+1) + ") " + cards.get(i).getName());
        }
        String response;
        while(true){
            out.println("| Select card (by number listed above)");
            out.print(">> ");
            try {
                response = in.readLine();
                //out.println("you said:" + response);
            } catch (IOException ex) {throw new RuntimeException("Failed to Read Player Input.", ex);}

            int responseValue;
            try{
                responseValue = Integer.parseInt(response);
                Card playCard = cards.get((responseValue-1));
                return playCard;
            }catch(Exception e){//error parsing input, try again

            }
        }
    }


}
