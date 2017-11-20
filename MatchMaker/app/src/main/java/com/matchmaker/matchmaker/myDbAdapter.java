package com.matchmaker.matchmaker;

/**************************************************************************************************
 The following code template was supplied by http://abhiandroid.com/database/sqlite#Creating_And_Updating_Database_In_Android
 and https://developer.android.com/training/data-storage/sqlite.html.
 myDbAdapter
 Authors: Emma Byrne
 Date: 05/11/2017
 Course: COMP 41690 Android Programming
 Desc:
 Usage:
 **************************************************************************************************/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class myDbAdapter {
    myDbHelper dbAdapter;
    public myDbAdapter(Context context) {
        dbAdapter = new myDbHelper(context);
    }

    // This method inserts data into the database
    public long insertData(String name, String preferences, String email, String events) {
        SQLiteDatabase dbb = dbAdapter.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.MyPREFERENCES, preferences);
        contentValues.put(myDbHelper.EMAIL, email);
        contentValues.put(myDbHelper.MyEVENTS, events);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        System.out.println("Insertion successful");
        return id;
    }

    // This gets the data for a single user
    public String getSingleData(String email) {
        // Get the user id here? - use user name for now - need to get a value from the user login
        SQLiteDatabase db = dbAdapter.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPREFERENCES,myDbHelper.EMAIL};
        String query = "SELECT * FROM " +myDbHelper.TABLE_NAME + " WHERE " + myDbHelper.EMAIL + " = '" + email + "'";
        Cursor cursor = db.rawQuery(query, null);
        StringBuffer buffer= new StringBuffer();
        if (cursor.moveToFirst()) {
            do {
                int cid = cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
                String name = cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
                String preferences = cursor.getString(cursor.getColumnIndex(myDbHelper.MyPREFERENCES));
                String events = cursor.getString(cursor.getColumnIndex(myDbHelper.MyEVENTS));
                buffer.append(cid+ "   " + name + "   " + preferences + " " + email + " " + events + " \n");
            } while (cursor.moveToNext());
        }
        return buffer.toString();
    }

    public String getEventData(String email) {
        // Get the user id here? - use user name for now - need to get a value from the user login
        SQLiteDatabase db = dbAdapter.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPREFERENCES,myDbHelper.EMAIL};
        String query = "SELECT * FROM " +myDbHelper.TABLE_NAME + " WHERE " + myDbHelper.EMAIL + " = '" + email + "'";
        Cursor cursor = db.rawQuery(query, null);
        StringBuffer userEvents = new StringBuffer();
        if (cursor.moveToFirst()) {
            do {
                String events = cursor.getString(cursor.getColumnIndex(myDbHelper.MyEVENTS));
//                int cid = cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
//                String name = cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
//                String preferences = cursor.getString(cursor.getColumnIndex(myDbHelper.MyPREFERENCES));
//                String events = cursor.getString(cursor.getColumnIndex(myDbHelper.MyEVENTS));
                userEvents.append(events);
            } while (cursor.moveToNext());
        }
        return userEvents.toString();
    }

    // This gets all the data in the database and returns it as a string
    public String getData() {
        System.out.println("\n\n\nIn the get data method\n\n\n");
        SQLiteDatabase db = dbAdapter.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPREFERENCES,myDbHelper.MyEVENTS,myDbHelper.EMAIL};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            String  preferences =cursor.getString(cursor.getColumnIndex(myDbHelper.MyPREFERENCES));
            String events = cursor.getString(cursor.getColumnIndex(myDbHelper.MyEVENTS));
            String email = cursor.getString(cursor.getColumnIndex(myDbHelper.EMAIL));
            buffer.append(cid+ "   " + name + "   " + preferences + " " + email + " " + events + " \n");
        }
        // maybe do a check here to return false?
        return buffer.toString();
    }

    // This deletes data in the database - change this to delete events?
    public  int delete(String uname) {
        SQLiteDatabase db = dbAdapter.getWritableDatabase();
        String[] whereArgs ={uname};
        int count = db.delete(myDbHelper.TABLE_NAME ,myDbHelper.NAME+" = ?",whereArgs);
        return  count;
    }

    // This updates data in the database
    public Boolean updateData(String email, String newName, String newPass){
        SQLiteDatabase db = dbAdapter.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME,newName);
        contentValues.put(myDbHelper.MyPREFERENCES,newPass);
        // https://stackoverflow.com/questions/28107277/update-value-to-specific-primary-key-in-sqlite-android
        String[] changes = {email};
        db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.EMAIL+"=?", changes);
        return true;
    }

    // Method to remove an event from the user - could use the delete method from above?

    // Method to update the event info for a user - this will add an event
    public Boolean updateEvents(String email, String oldEvents, String newEvents){
        // Take in the event string, add it to the db
        SQLiteDatabase db = dbAdapter.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // Get the old value, then append the new event to it
        String totalEvents = oldEvents + "\n" + newEvents;
        contentValues.put(myDbHelper.MyEVENTS, totalEvents);
        // https://stackoverflow.com/questions/28107277/update-value-to-specific-primary-key-in-sqlite-android
        String[] changes = {email};
        db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.EMAIL+"=?", changes);
        return true;
    }

    static class myDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String NAME = "Name";    //Column II
        private static final String MyPREFERENCES="Preferences"; // Column III
        private static final String MyEVENTS="Events"; // Column IV
        private static final String EMAIL = "Email"; // Column V
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"
                + MyPREFERENCES+" VARCHAR(225) ," + MyEVENTS+" VARCHAR(255) ," +EMAIL+" VARCHAR(255));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }
    }
}