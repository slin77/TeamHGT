package edu.gatech.cs2340.hgt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FriendDB extends SQLiteOpenHelper {
    private final Context context;

    /**
     * @param context
     */
    public FriendDB(Context context) {
        super(context, "friendship", null, 1);
        this.context = context;
    }

    /**
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + "friendship("
                + "username1 TEXT,"
                + "username2 TEXT,"
                + "timestamp TEXT)");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        onCreate(db);
    }

    /**
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //not yet implement
    }

    /**
     * @param currentUser
     * @param targetUser
     * @return
     */
    public boolean addNewFriendship(String currentUser, String targetUser) {

        if (areFriend(currentUser, targetUser)) {
            return false;
        } else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("username1", currentUser);
            cv.put("username2", targetUser);
            DateFormat simpleDateFormatformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            cv.put("timestamp", simpleDateFormatformat.format(date));
            Long ret = db.insert("friendship", null, cv);
            db.close();
            return ret != -1;
        }
    }

    /**
     * @param currentUser
     * @param targetUser
     * @return
     */
    public boolean areFriend(String currentUser, String targetUser) {
        SQLiteDatabase db = getReadableDatabase();
        //SQLiteQueryBuilder sb = new SQLiteQueryBuilder();
        currentUser = "'" + currentUser + "'";
        targetUser = "'" + targetUser + "'";
        Cursor cursor = db.rawQuery(SQLiteQueryBuilder.buildQueryString(false, "friendship", null
                , "(username1 = " + currentUser + " AND username2 = " + targetUser + ") OR (username2 =  " + currentUser + " AND username1 = " + targetUser + ")", null, null, null, null)
                , null);
        int count = cursor.getCount();
        db.close();
        return count != 0;
    }

    /**
     * @param username
     * @return
     */
    private String[] getFriends(String username) {
        //System.out.println(username);
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> friends = new ArrayList<>();
        Cursor cursor = db.query(true, "friendship", new String[]{"username2"}, "username1=?"
                , new String[]{username}, null, null, null, null);
        while (cursor.moveToNext()) { // the cursor is positioned before the first entry
            friends.add(cursor.getString(cursor.getColumnIndex("username2")));
        }
        cursor = db.query(true, "friendship", new String[]{"username1"}, "username2=?"
                , new String[]{username}, null, null, null, null);
        while (cursor.moveToNext()) {
            friends.add(cursor.getString(cursor.getColumnIndex("username1")));
        }
        db.close();
        System.out.println(friends.size());
        return friends.toArray(new String[friends.size()]);

    }

    /**
     * @param username
     * @return
     */
    public List<User> getFriendList(String username) {
        String[] friends = getFriends(username);
        List<User> friendList = new ArrayList<>(10);
        UserDB db = new UserDB(context);
        for (String friend : friends) {
            friendList.add(new User(friend, db.getName(friend), db.getEmail(friend)));
        }
        return friendList;
    }

    public boolean deleteFriendShip(String username1, String username2) {
        SQLiteDatabase db = getWritableDatabase();
        int res = db.delete("friendship", "(username1 = ? AND username2 = ?) OR (username1 = ? AND username2 = ?)"
                , new String[]{username1, username2, username2, username1});
        System.out.println("deleted" + res);
        return res != 0;
    }


}
