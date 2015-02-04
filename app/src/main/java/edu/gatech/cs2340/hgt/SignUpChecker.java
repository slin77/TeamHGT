package edu.gatech.cs2340.hgt;

import java.util.HashMap;
import java.util.regex.*;

/**
 * Created by Qi on 2015/2/3.
 */
public class SignUpChecker {
    private HashMap<String, String> userdata;
    public SignUpChecker() {
        this.userdata = new HashMap<>(20);
        userdata.put("Sizhe.HGT++", "123456789");
        userdata.put("Shizhe.HGT++", "123456789");
        userdata.put("Bin.HGT++", "123456789");
        userdata.put("Qi.HGT++", "123456789");
        userdata.put("Jing.HGT++", "123456789");
    }
    public boolean check(String username, String password, String retyped, String email) {
        Matcher matcher;
        Pattern pattern;
        pattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,10}");
        matcher = pattern.matcher(password);
        if ((username == null) || (password == null) || (retyped == null) || (email == null)) {
            return false;
        } else if (!password.equals(retyped)){
            return false;
        } else if ((username.length() < 6) || (username.length() > 10)){
            return false;
        } else if(!(userdata.get(username) == null)){
            return false;
        } else if (!matcher.matches()){
            return false;
        } else{
            userdata.put(username, password);
            return true;
        }
    }
}
