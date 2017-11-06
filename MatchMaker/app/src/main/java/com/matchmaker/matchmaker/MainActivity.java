package com.matchmaker.matchmaker;
/**************************************************************************************************
 The following code template was supplied by Android studio using the new activity functionality.
 MainActivity
 Author1: Emma Byrne
 Date: 30/10/2017
 Course: COMP 41690 Android Programming
 Desc:
 Usage:


 **************************************************************************************************/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

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
}