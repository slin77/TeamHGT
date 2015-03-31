package edu.gatech.cs2340.hgt;

import junit.framework.TestCase;


/**
 * Created by Shizhe Chen on 3/28/15.
 */
public class isValidEmailTest extends TestCase {
    public void testNullEmail() {
        boolean out6 = UserService.isValidEmail(null);
        assertFalse(out6);
    }

    public void testValidEmail() {
        boolean out1 = UserService.isValidEmail("123@me.com");
        assertTrue(out1);

        boolean out2 = UserService.isValidEmail("jason@me.com");
        assertTrue(out2);

        boolean out3 = UserService.isValidEmail("caesar@gmail.com");
        assertTrue(out3);
    }

    public void testInvalidEmail() {
        boolean out4 = UserService.isValidEmail("123.com");
        assertFalse(out4);

        boolean out5 = UserService.isValidEmail("helloJunit");
        assertFalse(out5);
    }
}
