package edu.gatech.cs2340.hgt;

import android.app.AlertDialog;
import android.content.Context;

import java.util.List;


public class SalesNotifier {
    //private String username;
    private final UserDetailDB db;
    private final FriendDB friendship;
    private final User currentUser;
    private final Context context;

    /**
     *
     * @param username username
     * @param context context
     */
    public SalesNotifier(String username, Context context) {
        db = new UserDetailDB(context);
        currentUser = db.getFullDetailUser(username);
        this.context = context;
        this.friendship = new FriendDB(context);
    }

    /**
     * return all the item that matches all user's requirement
     * @return void
     */
    List<Report> getMatches() {
            return db.getMatchReports(currentUser.getUsername());
    }

    private void displayAlert(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(s)
                .setTitle("There is a match found!")
                .create()
                .show();
    }

    /**
     * create a dialogBox to show all matches
     */
    public void notifyMatch() {
        List<Report> reports= getMatches();
        StringBuilder builder = new StringBuilder();
        for (Report report: reports) {
            if (friendship.areFriend(currentUser.getUsername(), report.getReporter()))
                builder.append(report.toString());
        }
        if (builder.length() != 0) {
            displayAlert(builder.toString());
        }
    }

    /**
     * create a dialogbox for all matches of the specific item
     * @param itemName item name
     */
    public void notifyMatch(String itemName) {
        List<Report>reports = getMatches();
        StringBuilder builder = new StringBuilder();
        for (Report report : reports) {
            if(report.getName().equals(itemName)) {
                    builder.append(report);
            }
        }
        if (builder.length() != 0) {
            displayAlert(builder.toString());
        }
    }
}
