package edu.gatech.cs2340.hgt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class UserDetailDB extends SQLiteOpenHelper {
    private final Context context;
    private final String SHORT_DESCRIPTION_COL = "short_description";
    private final String INTEREST_COL = "interest";
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
                "reporter Text," +
                "lat REAL," +
                "lgn REAL)");
    }

    /**
     * get full matches of the items of a certain user
     * @param username
     * @return
     */
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

    /**
       call this method whenever the db is opened, so we can apply updates on schema
     * @param db
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        onCreate(db);
    }

    /**
     * need future implements
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //not yes implement
    }

    /**
     * insert a new user Report
     * @param itemName
     * @param price
     * @param location
     * @param username
     * @return
     */
    public boolean insertNewReport(String itemName, String price, String location, String username) {
        SQLiteDatabase db =  getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("item_name", itemName);
        cv.put("price", price);
        cv.put("location", location);
        cv.put("reporter", username);
        cv.put("lat", 0);
        cv.put("lgn", 0);
        Long ret = db.insert("reports", null, cv);
        return ret != 0;
    }

    /**
     * insert new Report
     * @param itemName
     * @param price
     * @param location
     * @param username
     * @param lat
     * @param lgn
     * @return
     */
    public boolean insertNewReport(String itemName, String price, String location, String username,
                                   double lat, double lgn) {
        SQLiteDatabase db =  getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("item_name", itemName);
        cv.put("price", price);
        cv.put("location", location);
        cv.put("reporter", username);
        cv.put("lat", lat);
        cv.put("lgn", lgn);
        Long ret = db.insert("reports", null, cv);
        return ret != 0;
    }

    /**
     * get reports for a certain username
     * @param username
     * @return
     */
    public List<Report> getReports(String username) {
        SQLiteDatabase db  = getReadableDatabase();
        Cursor cursor = db.query("reports", new String[] {},"reporter=?"
                ,new String[] {username}, null, null, null);
        ArrayList<Report> reports = new ArrayList<>(10);
        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            String itemName = cursor.getString(cursor.getColumnIndex("item_name"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            String reporter = cursor.getString(cursor.getColumnIndex("reporter"));
            double lat = cursor.getDouble(cursor.getColumnIndex("lat"));
            double lgn = cursor.getDouble(cursor.getColumnIndex("lgn"));
            //System.out.println(itemName + price+ location + reporter);
            reports.add(new Report(itemName, location, price, reporter, lat, lgn));
        }
        return reports;
    }

    /**
     * get Sales of a certain Username
     * @param username
     * @return
     */
    public List<Sale> getSales(String username) {
        SQLiteDatabase db  = getReadableDatabase();
        //String selection = "username="+username;
        Cursor cursor = db.query("sales", new String[] {},"username=?"
                ,new String[] {username}, null, null, null);
        ArrayList<Sale> sales = new ArrayList<>(10);
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

    /**
     * insert a new Sale into dataBase
     * @param username
     * @param itemName
     * @param thresholdPrice
     * @param timestamp
     * @return
     */
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

    /**
     * get whole username information of a user
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
        String GENDER_COL = "gender";
        String gender = getFieldByUsername(username, GENDER_COL);
        String interest = getFieldByUsername(username, INTEREST_COL);
        user.setHasDetail(true);
        user.setShortDescription(sd);
        user.setInterest(interest);
        String MALE = "male";
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
        String USER_DETAIL_TABLE = "user_detail";
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

    /**
     * get all interests of the user
     * @param username
     * @return
     */
    public String getInterest(String username) {
        return getFieldByUsername(username, INTEREST_COL);
    }

    private List<Sale> getSalesByItemName(String itemName){
        SQLiteDatabase db  = getReadableDatabase();
        //String selection = "username="+username;
        Cursor cursor = db.query("sales", new String[] {},"item_name=?"
                ,new String[] {itemName}, null, null, null);
        ArrayList<Sale> sales = new ArrayList<>(10);
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
