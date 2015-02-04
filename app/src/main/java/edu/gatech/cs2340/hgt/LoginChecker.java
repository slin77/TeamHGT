package edu.gatech.cs2340.hgt;

import java.util.HashMap;

/**
 * Created by root on 2/3/15.
 */

public class LoginChecker {
        private HashMap<String, String> userdata;
    public LoginChecker() {
        this.userdata = new HashMap<>(20);
        userdata.put("Sizhe.HGT++", "123456789");
        userdata.put("Shizhe.HGT++", "123456789");
        userdata.put("Bin.HGT++", "123456789");
        userdata.put("Qi.HGT++", "123456789");
        userdata.put("Jing.HGT++", "123456789");
    }
    public boolean validate(String username, String password) {
        if (username == null || password == null) {
            return false;
        } else if(userdata.get(username) == null) {
            return false;
        } else {
            String pwd = userdata.get(username);
            if (pwd.equals(password)) {
                return true;
            }
            return false;
        }
    }
}
