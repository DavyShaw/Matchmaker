package com.matchmaker.matchmaker;
/**************************************************************************************************
 Register screen
 Authors: Davy Shaw, Emma Byrne
 Date: 06/11/2017
 Course: COMP 41690 Android Programming
 Desc:
 Usage:

 **************************************************************************************************/
//Part of Code taken from https://www.simplifiedcoding.net/android-firebase-tutorial-1/

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {

    myDbAdapter dbAdapter;
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Create the SQLite db
        dbAdapter = new myDbAdapter(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();

            startActivity(new Intent(getApplicationContext(), ProfileActivity.class ));
        }

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);

        buttonRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
    }

    private void registerUser(){
        // changing email to final - have to do this to create database below
        final String email = editTextEmail.getText().toString().trim();
        //String email = editTextEmail.getText().toString().trim();
        // Is this safe to be passing around the password in plain text? - Pam
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            //stops function from executing further
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            //stops function from executing further
            return;
        }
        //valid user

        progressDialog.setMessage("Registering you now!");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            //user registered and logged in
                            // add to db - name will be email
                            // parse the email to make it the username - or could just use email...
                            String[] accountName = email.split("\\@"); // - accountName[0].toString() = name before @ sign
                            long id = dbAdapter.insertData(accountName[0].toString(), "no_preferences", email, "no_events_yet");
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }
                        else{
                            Message.message(RegisterScreen.this, "Registration Error");
                            editTextEmail.setText("");
                            editTextPassword.setText("");
                            //Toast.makeText(RegisterScreen.this, "Registration Error", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view){
        if(view == buttonRegister){
            registerUser();
        }
        if(view == textViewSignIn){
            startActivity(new Intent(this, LoginActivity.class));
        }
    }


    // Test function
    public void proceedToAcc(View view) {
        // Create new intent to link button click to activity
        Intent intent = new Intent(this, userAccount.class);
        startActivity(intent);
    }
}