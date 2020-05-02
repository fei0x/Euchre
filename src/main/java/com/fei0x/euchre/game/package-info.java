/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This Package contains all the code to play a game of Euchre (minus the player logic code, this is externalized to the player_ai package)
 * Each class contains it's own set of relevant helper functions for player class implementers. like the 'findStrongestCards' in the Card class.
 * All external classes should communicate directly with the EuchreGame object as it has hooks to getting you elements of a game in progress. 
 *
 * The following classes are related in this order: ('bottom' up)
 * Rank and Suit (identifying attributes of a card, constructed as enums for ease of use)
 * Card          (the core element of the game of Euchre)
 * Play          (a card related to a player (name) indicating that the player played that card)
 * Trick         (a set of 4 plays each a different card by a different player, the first card laid demarking the led suit)
 * Hand          (starts as a set of 5 cards, held the player who has them, as the round progresses the number of cards will decrease)
 * PlayerAI      (an abstract class which hosts the AI logic for the player)
 * Player        (a player in the context of the game)
 * Team          (a pair of players, sat in opposition, mainly used to keep score, but also to inform who is whose partner for AskGame)
 * Round         (a set of 5 tricks, 4 players' and all 24 cards involved)
 *
 *
 * Whereas the remaining classes deal with game processing
 * EuchreGame    (manages the player move prompting, main game loop, notifications/logging and overall data management.)
 * AskGame       (is an interface to allow players to inquire about the game in progress.)
 *
 * AskGame gives AI access to the following objects: Rank, Suit, Card, Play, Trick
 */
package com.fei0x.euchre.game;
