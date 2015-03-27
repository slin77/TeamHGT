package edu.gatech.cs2340.hgt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

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

        //db.execSQL("DROP table IF EXISTS sales");
        //db.execSQL("DROP table IF EXISTS reports");
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "user_detail(" +
                "username TEXT, " +
                "gender TEXT, " +
                "interest TEXT, " +
                "short_description TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "sales(" +
                "username TEXT, " +
                "item_name TEXT, " +
                "threshold_price REAL, " +
                "timestamp TEXT)");
        //sales table is the table for interests
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "reports(" +
                "item_name TEXT," +
                "price REAL," +
                "location TEXT," +
                "reporter Text)");
    }

    public List<Report> getMatchReports(String username) {
        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.execSQL("SELECT item_name, price, location, reporter" +
//                "FROM sales AS s JOIN reports AS r ON s.item_name = r.item_name " +
//                "WHERE s.username = " + username + " AND s.threshold_price > r.price");
        Cursor cursor = db.rawQuery("SELECT s.item_name, price, location, reporter " +
                "FROM sales AS s JOIN reports AS r ON s.item_name = r.item_name " +
                "WHERE s.username = ? AND s.threshold_price > r.price", new String[] {username});
        System.out.println(cursor.getCount());
        cursor.moveToFirst();
        String itemName, price, location, reporter;
        List<Report> reports = new ArrayList<>(5);
        while(cursor.moveToNext()) {
            itemName = cursor.getString(cursor.getColumnIndex("item_name"));
            price = cursor.getString(cursor.getColumnIndex("price"));
            location = cursor.getString(cursor.getColumnIndex("location"));
            reporter = cursor.getString(cursor.getColumnIndex("reporter"));
            //System.out.println(itemName + price+ location + reporter);
            reports.add(new Report(price, location, itemName, reporter));
        }
        db.close();
        return reports;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        onCreate(db);
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

    public boolean insertNewReport(String itemName, String price, String location, String username) {
        SQLiteDatabase db =  getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("item_name", itemName);
        cv.put("price", price);
        cv.put("location", location);
        cv.put("reporter", username);
        Long ret = db.insert("reports", null, cv);
        return ret != 0;
    }


    public List<Sale> getSales(String username) {
        SQLiteDatabase db  = getReadableDatabase();
        //String selection = "username="+username;
        Cursor cursor = db.query("sales", new String[] {},"username=?"
                ,new String[] {username}, null, null, null);
        ArrayList<Sale> sales = new ArrayList<Sale>(10);
        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            String itemName = cursor.getString(cursor.getColumnIndex("item_name"));
            String timestamp = cursor.getString(cursor.getColumnIndex("timestamp"));
            String price = cursor.getString(cursor.getColumnIndex("threshold_price"));
            sales.add(new Sale(itemName, username, timestamp, price));
        }

        db.close();
        return sales;
    }

    public boolean insertNewSale(String username, String itemName, String thresholdPrice, String timestamp) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("item_name", itemName);
        cv.put("threshold_price", thresholdPrice);
        cv.put("timestamp", timestamp);
        Long ret = db.insert("sales", null, cv);
        return ret != 0;
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

    public String getInterest(String username) {
        return getFieldByUsername(username, INTEREST_COL);
    }

    private List<Sale> getSalesByItemName(String itemName){
        SQLiteDatabase db  = getReadableDatabase();
        //String selection = "username="+username;
        Cursor cursor = db.query("sales", new String[] {},"item_name=?"
                ,new String[] {itemName}, null, null, null);
        ArrayList<Sale> sales = new ArrayList<Sale>(10);
        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String timestamp = cursor.getString(cursor.getColumnIndex("timestamp"));
            String price = cursor.getString(cursor.getColumnIndex("threshold_price"));
            sales.add(new Sale(itemName, username, timestamp, price));
        }

        db.close();
        return sales;
    }

}
