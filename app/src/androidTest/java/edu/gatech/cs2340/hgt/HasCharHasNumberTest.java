package edu.gatech.cs2340.hgt;

import junit.framework.TestCase;


public class HasCharHasNumberTest extends TestCase {

    public void testValidUserName() {
        boolean out1 = UserService.hasCharHasNumber("77haha");
        assertTrue(out1);

        boolean out2 = UserService.hasCharHasNumber("qzhang300");
        assertTrue(out2);

        boolean out3 = UserService.hasCharHasNumber("lifeinusa2010");
        assertTrue(out3);
    }

    public void testInvalidUserName() {
        boolean out6 = UserService.hasCharHasNumber("");
        assertFalse(out6);

        boolean out4 = UserService.hasCharHasNumber("georgiatech");
        assertFalse(out4);

        boolean out5 = UserService.hasCharHasNumber("7777777");
        assertFalse(out5);
    }
}