package edu.gatech.cs2340.hgt;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by root on 2/23/15.
 */
public class UserDetailDB extends SQLiteOpenHelper {
    Context context;
    private final String USER_DETAIL_TABLE = "user_detail";
    private final String SHORT_DESCRIPTION_COL = "short_description";
    private final String GENDER_COL = "gender";
    private final String INTEREST_COL = "interest";
    private final String MALE = "male";
    private final String FEMALE = "female";
    public UserDetailDB(Context context) {
        super(context, "user_detail", null, 1);
        this.context = context;
    }

    /**
     * create the table is not exist
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "user_detail(" +
                "username TEXT " +
                "gender TEXT " +
                "interest TEXT " +
                "short_description TEXT)");
    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //not yes implement
    }

    /**
     * get the FullDetailedUser from Database
     * @param username
     * @return
     */
    public User getFullDetailUser(String username) {
        UserDB userDB = new UserDB(context);
        if (!userDB.isUserExist(username)) {
            return null;
        }
        String name = userDB.getName(username);
        String email = userDB.getEmail(username);
        User user = new User(username, name, email);
        if (!isDetailSet(username)) {
            user.setHasDetail(false);
            return user;
        }
        String sd = getFieldByUsername(username, SHORT_DESCRIPTION_COL);
        String gender = getFieldByUsername(username, GENDER_COL);
        String interest = getFieldByUsername(username, INTEREST_COL);
        user.setHasDetail(true);
        user.setShortDescription(sd);
        user.setInterest(interest);
        if (gender.equals(MALE)) {
            user.setMale(true);
        } else {
            user.setMale(false);
        }
        return user;
    }


    /**
     * to show if the user has set his detail
     * @param username
     * @return
     */
    private boolean isDetailSet(String username) {
        return getFieldByUsername(username, SHORT_DESCRIPTION_COL) != null;
    }

    /**
     * helper for query
     * @param username
     * @param fieldName
     * @return
     */
    private String getFieldByUsername(String username, String fieldName) {

        SQLiteDatabase db  = getReadableDatabase();
        //String selection = "username="+username;
        Cursor cursor = db.query(USER_DETAIL_TABLE, new String[] {},"username=?"
                ,new String[] {username}, null, null, null);
        if (cursor.getCount() == 0 || cursor.getColumnIndex(fieldName) < 0) {
            return null;
        }
        cursor.moveToFirst();
        String re = cursor.getString(cursor.getColumnIndex(fieldName));
        db.close();
        return re;
    }

}
