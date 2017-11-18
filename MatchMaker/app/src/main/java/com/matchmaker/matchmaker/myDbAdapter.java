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
    public long insertData(String name, String pass, String email) {
        SQLiteDatabase dbb = dbAdapter.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.MyPASSWORD, pass);
        contentValues.put(myDbHelper.EMAIL, email);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        System.out.println("Insertion successful");
        return id;
    }

    // This gets the data for a single user
    public String getSingleData(String email) {
        // Get the user id here? - use user name for now - need to get a value from the user login
        //String userName = new String("emma");
        SQLiteDatabase db = dbAdapter.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPASSWORD,myDbHelper.EMAIL};
        //String query = "SELECT * FROM " + myDbHelper.TABLE_NAME + " WHERE " + myDbHelper.NAME + " = '" + userName + "'";
        String query = "SELECT * FROM " +myDbHelper.TABLE_NAME + " WHERE " + myDbHelper.EMAIL + " = '" + email + "'";
        //Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        Cursor cursor = db.rawQuery(query, null);
        StringBuffer buffer= new StringBuffer();
        if (cursor.moveToFirst()) {
            do {
                int cid = cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
                String name = cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
                String password = cursor.getString(cursor.getColumnIndex(myDbHelper.MyPASSWORD));
                buffer.append(cid+ "   " + name + "   " + password + " " + email + " \n");
            } while (cursor.moveToNext());
        }

//        while (cursor.moveToNext())
//        {
//            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
//            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
//            String  password =cursor.getString(cursor.getColumnIndex(myDbHelper.MyPREFERENCES));
//            buffer.append(cid+ "   " + name + "   " + password +" \n");
//        }
        return buffer.toString();
    }

    // This gets all the data in the database and returns it as a string
    public String getData() {
        System.out.println("\n\n\nIn the get data method\n\n\n");
        SQLiteDatabase db = dbAdapter.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPASSWORD,myDbHelper.EMAIL};
        System.out.println("the columns are " + columns);
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            String  password =cursor.getString(cursor.getColumnIndex(myDbHelper.MyPASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(myDbHelper.EMAIL));
            buffer.append(cid+ "   " + name + "   " + password + " " + email + " \n");
        }
        // maybe do a check here to return false?
        return buffer.toString();
    }

    // This deletes data in the database
    public  int delete(String uname) {
        SQLiteDatabase db = dbAdapter.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.NAME+" = ?",whereArgs);
        return  count;
    }

    // This updates data in the database
//    public Boolean updateData(String oldName, String oldPass, String newName, String newPass){
    public Boolean updateData(String email, String newName, String newPass){
        SQLiteDatabase db = dbAdapter.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME,newName);
        contentValues.put(myDbHelper.MyPASSWORD,newPass);
        //System.out.println("The new pass is " + newPass);
        //System.out.println("the old pass is " + oldPass);
        //String[] changes = {oldName, oldPass};
        // https://stackoverflow.com/questions/28107277/update-value-to-specific-primary-key-in-sqlite-android
        String[] changes = {email};
        db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.EMAIL+"=?", changes);
        //db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.NAME+"=? AND " + myDbHelper.MyPASSWORD + "=?",changes);
        return true;
    }

//    public int updateData(String oldName , String newName)
//    {
//        SQLiteDatabase db = dbAdapter.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(myDbHelper.NAME,newName);
//        String[] whereArgs= {oldName};
//        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.NAME+" = ?",whereArgs );
//        return count;
//    }

    static class myDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String NAME = "Name";    //Column II
        private static final String MyPASSWORD= "Password";    // Column III
        //private static final String MyPREFERENCES="Preferences"; // Column III
        private static final String EMAIL = "Email";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+ MyPASSWORD+" VARCHAR(225) ," + EMAIL+" VARCHAR(255));";
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
