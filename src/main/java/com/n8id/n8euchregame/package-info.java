/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This Package contains all the code to play a game of Euchre (minus the player logic code)
 * Each class contains it's own set of relevant helper functions for player class implementers. like the 'findStrongestCards' in the Card class.
 * All external classes should communicate directly with the EuchreGame object as it has hooks to getting you elements of a game in progress. However, many of the games components can be built outside of the game if you want.
 *
 * The following (data, data managment & validation Only) classes are related in this order: (bottom up)
 * Rank and Suit (identifying attributes of a card, constructed as enums for ease of use)
 * Card          (the core singleton element of the game of euchre)
 * Play          (a card related to a player (name) indicating that the player played that card)
 * Trick         (a set of 4 plays each a diffrent card by a different player, with the first card laid demarking the led suit)
 * Team          (is unrelated to the above structure, but tracks the score for each team)
 * PlayerHand    (starts as a set of 5 cards, and the name of the player who has them, as the round progesses the number of cards will decrease)
 * Round         (a set of 5 tricks, 4 players' and all 24 cards involved, uses teams to determine poi)
 *
 *
 * Whereas the remaining classes deal with game processing
 * EuchreGame    (manages the player move prompting, main game loop, notifications/logging and overall data management.)
 * AskGame       (is an interface to allow players to inquire about the game in progress.)
 *
 */
package com.n8id.n8euchregame;
