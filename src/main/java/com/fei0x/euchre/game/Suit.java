/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.game;

import java.io.Serializable;

/**
 *
 * @author jsweetman
 */
public enum Suit implements Serializable  {
    HEARTS("HEARTS",Suit.RED),
    DIAMONDS("DIAMONDS",Suit.RED),
    CLUBS("CLUBS",Suit.BLACK),
    SPADES("SPADES",Suit.BLACK);

    /**
     * The suit color Red
     */
    public final static String RED = "Red";
    /**
     * The suit color Black
     */
    public final static String BLACK = "Black";


    private String name;
    private String color;

    /**
     * The Card Suits
     * @param name the name of the suit
     * @param color the color of the suit
     */
    Suit(String name, String color){
        this.name = name;
        this.color = color;
    }

    /**
     * Returns true if this suit color is red
     * @return true if red
     */
    public boolean isRed(){
        if (color.equalsIgnoreCase(RED)){
            return true;
        }
        return false;
    }

    /**
     * Returns true if this suit color is black
     * @return true if black
     */
    public boolean isBlack(){
        if (color.equalsIgnoreCase(BLACK)){
            return true;
        }
        return false;
    }

    /**
     * Returns the suit color of this suit
     * @return color of this suit
     */
    public String getColor(){
        return color;
    }

    /**
     * Returns the name of this suit
     * @return name of this suit
     */
    public String getName(){
        return name;
    }

    /**
     * Returns true if this suit is the same color as the other suit
     * @param other the other suit to color compare against
     * @return true if the suits are the same color
     */
    public boolean sameColor(Suit other){
        if(this.color.equalsIgnoreCase(other.getColor())){
            return true;
        }
        return false;
    }


}
