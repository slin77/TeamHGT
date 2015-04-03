package edu.gatech.cs2340.hgt;

import android.test.ActivityInstrumentationTestCase2;

//import junit.framework.TestCase;

/**
 * Created by jinghong on 3/29/15.
 */
public class checkPasswordMatchTest extends ActivityInstrumentationTestCase2<SignUpActivity> {

    public checkPasswordMatchTest() {
        super(SignUpActivity.class);
    }

    public void testNullPassword() {
        boolean testNull = UserService.checkPasswordMatch(null, null);
        assertFalse(testNull);
    }

    // since we check if password is valid before checking if it is matched, the first argument must
    // with the valid length
    public void testUnmatchedPassword() {

        // unmatched number
        boolean test1 = UserService.checkPasswordMatch("password123", "password124");
        assertFalse(test1);

        // unmatched length
        boolean test2 = UserService.checkPasswordMatch("password123", "ppassword123");
        assertFalse(test2);

        boolean test3 = UserService.checkPasswordMatch("ppassword123", "password123");
        assertFalse(test3);

        // unmatched letter (case sensitive)
        boolean test4 = UserService.checkPasswordMatch("123password", "123Password");
        assertFalse(test4);

        // unmatched letter
        boolean test5 = UserService.checkPasswordMatch("123password", "123passward");
        assertFalse(test5);
    }

    public void testMatchedPassword() {

        boolean test6 = UserService.checkPasswordMatch("password123", "password123");
        assertTrue(test6);

        boolean test7 = UserService.checkPasswordMatch("1234password", "1234password");
        assertTrue(test7);

        boolean test8 = UserService.checkPasswordMatch("pass12word", "pass12word");
        assertTrue(test8);
    }

}
