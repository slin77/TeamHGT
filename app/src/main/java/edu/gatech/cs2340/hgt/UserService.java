package edu.gatech.cs2340.hgt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

/**
 * Created by root on 2/3/15.
 * @author Sizhe Lin
 */

/**
 *
 */
public class UserService {
    //private HashMap<String, String> userdata;
    private UserDB userDB;

    /**
     *
     * @param context
     */
    public UserService(Context context) {
        userDB = new UserDB(context);
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public boolean validate(String username, String password) {
        if (username == null || password == null) {
            return false;
        } else {
            String pw = userDB.getPassword(username);
            System.out.println(pw);
            return password.equals(pw);
        }
    }

    /**
     *
     * @param username
     * @return
     */
    public boolean isUsernameExist(String username) {
        return userDB.getPassword(username) != null;
    }

    /**
     *
     * @param username
     * @return
     */
    public boolean checkUsernameFormat(String username) {
        if (username == null) return false;
        return !username.isEmpty() && checkUsernameLength(username);
    }

    /**
     *
     * @param username
     * @return
     */
    private boolean checkUsernameLength(String username) {
          return username.length() >= 8 && username.length() <= 16;
    }

    /**
     *
     * @param password
     * @return
     */
    public boolean checkPasswordFormat(String password) {
        if (password == null) return false;
        //need to add more function to check illegal characters
        return checkUsernameFormat(password) && checkPassword(password);
    }

    /**
     * check if two input passwords are the same case sensitive
     * test by Jing Hong
     * @param pw
     * @param pwR
     * @return
     */
    public boolean checkPasswordMatch(String pw, String pwR) {
        if (pw == null || pwR == null) {
            return false;
        }
        return pw.equals(pwR);
    }

    /**
     *
     * @param email
     * @return
     */
    public boolean checkEmailFormat(String email) {
        return true;
    }

    /**
     *
     * @param password
     * @return
     */
    private boolean checkPassword(String password) {
        //need to add more functions
        return !password.contains(" ");
    }

    /**
     * test by Bin Cao
     * test if the length of the password between 8 to 16
     * @param password
     * @return
     */
    public boolean checkPasswordLength(String password) {
       return checkUsernameLength(password);
    }

    /**
     *
     * @param name
     * @param username
     * @param pw
     * @param email
     * @return
     */
    public boolean createUser(String name, String username, String pw, String email) {
        return userDB.insertUser(name, username, pw, email);
    }

    /**
     *
     * @param username
     * @return
     */
    public boolean deleteUser(String username) {
        return userDB.deleteUser(username);

    }


    /**
     * test by Shizhe Chen
     * test if the input string is ***@***format
     * @param email
     * @return
     */
    public boolean isValidEmail(String email) {
        boolean result = false;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                result = true;
            }
        }
        return result;
    }

    /**
     * test by Sizhe Lin
     * if the input String contains %&^*
     * @param input
     * @return
     */
    public boolean hasSpecialCharacters(String input) {
        return false;
    }

    /**
     * test by Qizhang
     * if the password has characters and numbers
     * @param input
     * @return
     */
    public boolean hasCharHasNumber(String input) {
        return false;
    }




}


