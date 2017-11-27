package com.matchmaker.matchmaker;
/**************************************************************************************************
Profile Activity
Authors: Emma Byrne, Davy Shaw, Pamela Kelly
Date: 06/11/2017
Course: COMP 41690 Android Programming
Desc:
Usage:

 **************************************************************************************************/

//Part of code taken from https://www.simplifiedcoding.net/firebase-user-authentication-tutorial/

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    myDbAdapter dbAdapter;

    private TextView textViewUserEmail;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dbAdapter = new myDbAdapter(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textviewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        textViewUserEmail.setText("Welcome "+user.getEmail()+"!");

        // Get the info for the user that is stored in the database
        String email  = user.getEmail();
        //String data = dbAdapter.getSingleData(email);
        String data = dbAdapter.getSingleData_NoEvents(email);
        TextView textView = (TextView) findViewById(R.id.displayData);

        if (data == "") {
            textView.setText("Please update your information below");
        } else {
            textView.setText(data);
        }

        TextView eventsView = (TextView) findViewById(R.id.displayEvents);
        String eventData = dbAdapter.getEventData(email);
        eventsView.setText(eventData);
        buttonLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    // Method which updates the user information when they click the update button
    public void update_user_info(View view){
        // check if the database is populated or not
        // if it isn't, create a db and populate it with the info given
        // if it is, call the update method to update the db
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String[] accountName = user.getEmail().split("\\@"); // - accountName[0].toString() = name before @ sign
        String data = dbAdapter.getSingleData(user.getEmail());

        // Get the information
        EditText nickname = (EditText) findViewById(R.id.nickname); // username
        String username = nickname.getText().toString();
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.boxes); // get the preferences
        StringBuilder preferences = new StringBuilder();

        // Idea for how to get the checked checkboxes adapted from https://stackoverflow.com/questions/39438950/get-all-child-views-inside-linear-layout
        for (int x = 0; x < layout.getChildCount();x++) {
            CheckBox cb = (CheckBox) layout.getChildAt(x);
            if (cb.isChecked()) {
                // Add each checked one to a string
                preferences.append(cb.getText() + ", ");
            }
        }
        String pref = preferences.toString();
        // Remove trailing comma in the string - https://stackoverflow.com/questions/9292940/how-to-remove-comma-if-string-having-comma-at-end
        pref = pref.replaceAll(", $", "");

        // If there is no data in the database, create a new database
        if (dbAdapter.getData() == "") {
            dbAdapter = new myDbAdapter(this);
            // insert the data - ask for name first
            if(username.isEmpty()){
                Message.message(this, "Please enter a name");
            } else {
                // Get the user information - email
                long id = dbAdapter.insertData(username, pref, user.getEmail(), "");
                // populate the text view
                TextView textView = (TextView) findViewById(R.id.displayData);
                textView.setText(dbAdapter.getSingleData(user.getEmail()));
                nickname.setText("");
            }
        } else {
            // update the data in the db - get current username - use that to update db
            String[] splitted = data.split("\\s+"); // index 1 is name
            if (username.isEmpty()){
                // if no username is provided, use the name that is already there
                username = splitted[1];
            }
            dbAdapter.updateData(user.getEmail(), username, pref);
            // update the textview
            TextView textView = (TextView) findViewById(R.id.displayData);
            textView.setText(dbAdapter.getSingleData(user.getEmail()));
            nickname.setText("");
        }

        //############### Storing Data Remotely in Firebase ####################
        // Add the user to the Users Info Table in the Firebase Database

        // Get an instance of the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Get a reference for the "users" section
        DatabaseReference myRef = database.getReference("users");


        // Create a HashMap with all of user's details in it
        Map<String, String> myMap = new HashMap<String, String>();
        myMap.put("nickname", username);
        myMap.put("preferences", preferences.toString());
        // Create these as empty for the moment
        myMap.put("upcoming_matches", "");
        myMap.put("past_matches", "");

        // Push this info to the database as a child node of "users" with the key username
        // Use email as key because nickname can change
        String myEmail = user.getEmail();
        // if you want to split by . you have to escape it because . in regex means any character
        myEmail = myEmail.split("\\.")[0];
        myRef.child(myEmail).setValue(myMap);
    }

    // Populates the textview to display the user data
    public void setTextView_userData(View view){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        textViewUserEmail = (TextView) findViewById(R.id.textviewUserEmail);
        textViewUserEmail.setText("Welcome "+user.getEmail());
        // click edit, change the xml file, in the other page, when they click done, bring back to main page with info updated
        String email  = user.getEmail();
        String data = dbAdapter.getSingleData_NoEvents(email);
        TextView textView = (TextView) findViewById(R.id.displayData);
        if (data == "") {
            textView.setText("Please update your information below");
        } else {
            textView.setText(data);
        }
    }

    public void go_to_edit(View view){
        setContentView(R.layout.activity_profile_edit);
        setTextView_userData(view);
    }

    public void back_to_profile(View view){
        setContentView(R.layout.activity_profile);
        setTextView_userData(view);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        TextView eventsView = (TextView) findViewById(R.id.displayEvents);
        String eventData = dbAdapter.getEventData(user.getEmail());
        eventsView.setText(eventData);
    }
}

