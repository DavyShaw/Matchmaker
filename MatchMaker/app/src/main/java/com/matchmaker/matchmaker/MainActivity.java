package com.matchmaker.matchmaker;
/**************************************************************************************************
 The following code template was supplied by Android studio using the new activity functionality.
 MainActivity
 Authors: Emma Byrne
 Date: 30/10/2017
 Course: COMP 41690 Android Programming
 Desc:
 Usage:

 **************************************************************************************************/

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {
    // public static final String EXTRA_MESSAGE = "com.matchmaker.MESSAGE";

    TextView time;
    TimePicker simpleTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    // Method to bring the user to the login screen when they click the login button
    public void proceedToLogin(View view) {
        // Create new intent to link button click to activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    /** Called when the user taps the send button
     * Note the necessary requirements in order for this method
     * to be recognised by the onclick property in the activity_main.xml
     * file:
     * Public Access, A void return value, A View as the only arg
     * (It is the View object that was clicked)*/
    public void sendMessage (View view) {
        Intent intent = new Intent(this, MatchPreferencesActivity.class);
        startActivity(intent);
    }

}
