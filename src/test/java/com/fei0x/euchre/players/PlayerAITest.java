/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.players;

import java.util.List;

import com.fei0x.euchre.exceptions.MissingPlayer;
import com.fei0x.euchre.game.AskGame;
import com.fei0x.euchre.game.Card;
import com.fei0x.euchre.game.PlayerAI;
import com.fei0x.euchre.game.Suit;
import com.fei0x.euchre.game.Trick;

import junit.framework.TestCase;

/**
 *
 * @author jsweetman
 */
public class PlayerAITest extends TestCase {
    
    public PlayerAITest() {
        super();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of setAskGame method, of class Player.
     */
    public void testSetAskGame() {
        System.out.println("setAskGame");
        PlayerAI ai = new PlayerAIImpl();
        AskGame askGame = new AskGameTestImpl();
        ai.setAskGame(askGame);
    }


    public class PlayerAIImpl extends PlayerAI {

        public PlayerAIImpl() {
            super();
        }

        public boolean callItUp(Card faceUpCard) {
            return false;
        }

        public Card swapWithFaceUpCard(Card faceUpCard) {
            return null;
        }

        public Suit callSuit(Card turnedDownCard) {
            return null;
        }

        public Suit stickTheDealer(Card turnedDownCard) {
            return null;
        }

        public boolean playAlone() {
            return false;
        }

        public Card playCard(Trick currentTrick) {
            return null;
        }
    }


    public class AskGameTestImpl implements AskGame {

        public AskGameTestImpl() {
            super();
        }

		public String myName() {
			return null;
		}

		public List<Card> myHand() {
			return null;
		}

		public void speak(String somethingToSay) {
		}

		public List<Trick> pastTricks() throws IllegalStateException {
			return null;
		}

		public String myTeamName() {
			return null;
		}

		public String opponentsTeamName() {
			return null;
		}

		public int myTeamsScore() throws MissingPlayer {
			return 0;
		}

		public int opponentsScore() {
			return 0;
		}

		public String partnersName() {
			return null;
		}

		public List<String> opponentsNames() {
			return null;
		}

		public String whoIsDealer() {
			return null;
		}

		public String whoIsLeadPlayer() {
			return null;
		}
    }

    
}
