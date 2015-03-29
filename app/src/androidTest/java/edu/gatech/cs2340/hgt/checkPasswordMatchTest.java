package edu.gatech.cs2340.hgt;

import junit.framework.TestCase;

/**
 * Created by jinghong on 3/29/15.
 */
public class checkPasswordMatchTest extends TestCase {

    public void testNullPassword() {
        boolean testNull = UserService.checkPasswordMatch(null, null);
        assertFalse(testNull);
    }

    public void testValidEmail() {
        boolean test1 = UserService.checkPasswordMatch("password123", "password124");
        assertFalse(test1);

        boolean test2 = UserService.checkPasswordMatch("password123", "ppassword123");
        assertFalse(test2);

        boolean test3 = UserService.checkPasswordMatch("password123", "password123");
        assertTrue(test3);

        boolean test4 = UserService.checkPasswordMatch("password1234", "password1234");
        assertTrue(test4);

        boolean test5 = UserService.checkPasswordMatch("123password", "123Password");
        assertFalse(test5);
    }

}
