package com.matchmaker.matchmaker;
/**************************************************************************************************
 LoginActivity
 Author1: Emma Byrne
 Date: 30/10/2017
 Course: COMP 41690 Android Programming
 Desc: Activity in charge of changing the user information
 Usage:


 **************************************************************************************************/

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class userAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
    }

    // Make function to enable a user to change their settings
    public void editSettings (View view) {
        // Do stuff here to change settings
        // Get text by id and change it to stuff
    }
}
