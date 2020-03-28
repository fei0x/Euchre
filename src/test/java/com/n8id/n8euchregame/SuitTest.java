/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchregame;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author jsweetman
 */
public class SuitTest extends TestCase {
    
    public SuitTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(SuitTest.class);
        return suite;
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
     * Test of isRed method, of class Suit.
     */
    public void testIsRed() {
        System.out.println("isRed");
        Suit instance = Suit.DIAMONDS;
        boolean expResult = true;
        boolean result = instance.isRed();
        assertEquals(expResult, result);
    }

    /**
     * Test of isRed method, of class Suit.
     */
    public void testIsRed2() {
        System.out.println("isRed2");
        Suit instance = Suit.SPADES;
        boolean expResult = false;
        boolean result = instance.isRed();
        assertEquals(expResult, result);
    }

    /**
     * Test of isBlack method, of class Suit.
     */
    public void testIsBlack() {
        System.out.println("isBlack");
        Suit instance = Suit.CLUBS;
        boolean expResult = true;
        boolean result = instance.isBlack();
        assertEquals(expResult, result);
    }

    /**
     * Test of isBlack method, of class Suit.
     */
    public void testIsBlack2() {
        System.out.println("isBlack2");
        Suit instance = Suit.HEARTS;
        boolean expResult = false;
        boolean result = instance.isBlack();
        assertEquals(expResult, result);
    }

    /**
     * Test of getColor method, of class Suit.
     */
    public void testGetColor() {
        System.out.println("getColor");
        Suit instance = Suit.HEARTS;
        String expResult = "Red";
        String result = instance.getColor();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class Suit.
     */
    public void testGetName() {
        System.out.println("getName");
        Suit instance = Suit.SPADES;
        String expResult = "SPADES";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of sameColor method, of class Suit.
     */
    public void testSameColor() {
        System.out.println("sameColor");
        Suit other = Suit.DIAMONDS;
        Suit instance = Suit.HEARTS;
        boolean expResult = true;
        boolean result = instance.sameColor(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of sameColor method, of class Suit.
     */
    public void testSameColor2() {
        System.out.println("sameColor2");
        Suit other = Suit.CLUBS;
        Suit instance = Suit.SPADES;
        boolean expResult = true;
        boolean result = instance.sameColor(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of sameColor method, of class Suit.
     */
    public void testSameColor3() {
        System.out.println("sameColor3");
        Suit other = Suit.SPADES;
        Suit instance = Suit.HEARTS;
        boolean expResult = false;
        boolean result = instance.sameColor(other);
        assertEquals(expResult, result);
    }

}
