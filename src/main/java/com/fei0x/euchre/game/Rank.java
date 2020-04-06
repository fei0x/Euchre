/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.game;

import java.io.Serializable;

/**
 * Enumeration for Ranks... apparently i have to put them in their own class...
 * @author jsweetman
 */
public enum Rank implements Serializable {
    NINE ("NINE",1),
    TEN ("TEN", 2),
    JACK ("JACK",3),
    QUEEN ("QUEEN",4),
    KING ("KING",5),
    ACE ("ACE",6) ;

    /**
     * Name of this Rank
     */
    private final String name;

    /**
     * Value of this Rank, when no trump is involved
     */
    private final int value;

    /**
     * Ranks of Cards
     * @param name   name of the card
     * @param value  the value of this card (not counting trump scenarios)
     */
    Rank(String name, int value){
        this.name = name;
        this.value = value;
    }

    /**
     * The name of this rank
     * @return name of the rank
     */
    public String getName() {
        return name;
    }

    /**
     * Value of this rank (excluding trump scenarios)
     * @return value of this rank (excluding trump scenarios)
     */
    public int getValue() {
        return value;
    }


};