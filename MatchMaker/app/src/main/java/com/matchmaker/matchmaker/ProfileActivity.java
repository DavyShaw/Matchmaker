package com.matchmaker.matchmaker;
/**************************************************************************************************
Profile Activity
Authors: Emma Byrne, Davy Shaw
Date: 06/11/2017
Course: COMP 41690 Android Programming
Desc:
Usage:

 **************************************************************************************************/

//Part of code taken from https://www.simplifiedcoding.net/firebase-user-authentication-tutorial/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        EditText nickname = (EditText) findViewById(R.id.nickname);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textviewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        textViewUserEmail.setText("Welcome "+user.getEmail());

        // Get the info for the user that is stored in the database
        String email  = user.getEmail();
        String data = dbAdapter.getSingleData(email);
        TextView textView = (TextView) findViewById(R.id.displayData);

        if (data == "") {
            textView.setText("Please update your information below");
        } else {
            textView.setText(data);
        }

        //String[] accountName = email.split("\\@"); // to use the bit before the @ sign as username
        //String userName = accountName[0].toString();

        //String[] accountName = email.split("\\@");
        //long id = dbAdapter.insertData(accountName[0].toString(), "helloworld");
        //long id = dbAdapter.insertData(email, "");
        //System.out.println("The user is " + userName);
        //String data = dbAdapter.getSingleData(email);
        //String data = dbAdapter.getSingleData(userName);
//        System.out.println("The data is " + data);
//        if (data == ""){
//            userName = email;
//            data = dbAdapter.getSingleData(userName);
//        }

        // query the db, if no info is present, display a message
        // else show the data


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
        String data = dbAdapter.getSingleData(user.getEmail());


        // Get the information
        EditText nickname = (EditText) findViewById(R.id.nickname); // username
        String username = nickname.getText().toString();
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.boxes); // get the preferences
        System.out.println("the child count is " + layout.getChildCount());
        StringBuilder preferences = new StringBuilder();

        // Idea for how to get the checked checkboxes adapted from https://stackoverflow.com/questions/39438950/get-all-child-views-inside-linear-layout
        for (int x = 0; x< layout.getChildCount();x++) {
            CheckBox cb = (CheckBox) layout.getChildAt(x);
            System.out.println(cb.getText());
            if (cb.isChecked()) {
                System.out.println("This checkbox is checked");
                // Add each checked one to a string
                preferences.append(cb.getText() + "  "); // two spaces here to separate the values
            }
        }
        System.out.println("\n\n The string is " + preferences + "\n\n");

        if (dbAdapter.getData() == null) {
            dbAdapter = new myDbAdapter(this);
            System.out.println("The database was created");
            // insert the data - ask for name first
            if(username.isEmpty()){
                Message.message(this, "Please enter a name");
            } else {
                long id = dbAdapter.insertData(username, preferences.toString(), data);
            }

        } else {
            // update the data in the db - get current username - use that to update db
            String[] splitted = data.split("\\s+"); // index 1 is name, index 2 is pass
            if (username.isEmpty()){
                // if no username is provided, use the name that is already there
                username = splitted[1];
            }
            String updatePref = preferences.toString();
            dbAdapter.updateData(user.getEmail(), username, updatePref);
            //dbAdapter.updateData(splitted[1], splitted[2], username, updatePref);
            // update the textview
            TextView textView = (TextView) findViewById(R.id.displayData);
            textView.setText(dbAdapter.getSingleData(user.getEmail()));
            nickname.setText("");
        }
    }
}

