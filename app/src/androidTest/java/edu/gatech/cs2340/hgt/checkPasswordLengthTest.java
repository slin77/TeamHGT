package edu.gatech.cs2340.hgt;

import junit.framework.TestCase;

/**
 * Created by Bin Cao on 3/29/2015.
 */
public class checkPasswordLengthTest extends TestCase {
    public void testNullPassword() {
        boolean testNullCondition = UserService.checkPasswordLength(null);
        assertFalse(testNullCondition);
    }

    public void testValidPassword() {
        boolean t1 = UserService.checkPasswordLength("12345678");
        assertTrue(t1);
        boolean t2 = UserService.checkPasswordLength("1234567891234567");
        assertTrue(t2);
        boolean t3 = UserService.checkPasswordLength("A123456789045s");
        assertTrue(t3);
        boolean t4 = UserService.checkPasswordLength("1d");
        assertFalse(t4);
        boolean t5 = UserService.checkPasswordLength("12345678123456abc456123456789");
        assertFalse(t5);
    }
}
