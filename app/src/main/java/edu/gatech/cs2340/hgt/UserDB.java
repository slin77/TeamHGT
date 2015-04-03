package edu.gatech.cs2340.hgt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class UserDB extends SQLiteOpenHelper{

    public UserDB(Context context) {
        super(context, "users", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "users(" +
                "name TEXT," +
                "username TEXT," +
                "email Text," +
                "password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //not yet implement
    }

    public String getPassword(String username) {
        SQLiteDatabase db  = getReadableDatabase();
        //String selection = "username="+username;
        Cursor cursor = db.query("users", new String[] {"password"},"username=?"
                ,new String[] {username}, null, null, null);
        if (cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();//move the cursor to first index otherwise it will not point to result
        String pw = cursor.getString(cursor.getColumnIndex("password"));
        db.close();
        return pw;
    }

    /**
     * get the full user lists
     * @return
     */
    public List<User> getAllUsers() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM 'users'", null);
        ArrayList<User> list = new ArrayList<>(10);
        String name, username, email;
        while(cursor.moveToNext()) {
            username = cursor.getString(cursor.getColumnIndex("username"));
            name = cursor.getString(cursor.getColumnIndex("name"));
            email = cursor.getString(cursor.getColumnIndex("email"));
            list.add(new User(username, name, email));
        }
        return list;
    }

    /**
     * helper to get certain fields by username
     * @param username
     * @param fieldName
     * @return
     */
   private String getFieldByUsername(String username, String fieldName) {

       SQLiteDatabase db  = getReadableDatabase();
       //String selection = "username="+username;
       Cursor cursor = db.query("users", new String[] {fieldName},"username=?"
               ,new String[] {username}, null, null, null);
       if (cursor.getCount() == 0 || cursor.getColumnIndex(fieldName) < 0) {
           return null;
       }
       cursor.moveToFirst();
       String re = cursor.getString(cursor.getColumnIndex(fieldName));
       db.close();
       return re;
   }

   public String getEmail(String username) {

       return getFieldByUsername(username, "email");
   }

   public String getName(String username) {
       return getFieldByUsername(username, "name");
   }

    public boolean isUserExist(String username) {
        String pw = getPassword(username);
        return pw != null;
    }

    /**
     * insert new user
     * @param name
     * @param username
     * @param password
     * @param email
     * @return
     */
    public boolean insertUser(String name, String username, String password, String email) {
        if (isUserExist(username)) {
            return false;
        }
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("username", username);
        cv.put("password", password);
        cv.put("email", email);
        Long ret = db.insert("users", null, cv);//will return the col ID if success otherwise -1
        db.close();
        return ret != -1;
    }

    /**
     * delete the user from dataBase
     * @param username
     * @return
     */
    public boolean deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int colNum = db.delete("users", "username=?", new String[] {username});
        db.close();
        return colNum != 0;
    }




}
