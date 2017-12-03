package com.matchmaker.matchmaker;
/**************************************************************************************************
 LoginActivity
 Authors: Davy Shaw, Emma Byrne
 Course: COMP 41690 Android Programming
 Usage: Activity which enables a user to login to their account. If they have an account and enter
 their details correctly, they are brought to the ProfileActivity activity. If they do not have an
 account, if they enter their name or email incorrectly, a Toast will appear informing them their
 login was unsuccessful. If either email or password are left blank a Toast will appear telling
 the user to fill out the required field. There is a link to the RegisterScreen activity if the user
 does not have an account and would like to sign up for one.
 **************************************************************************************************/


//Part of code taken from https://www.simplifiedcoding.net/firebase-user-authentication-tutorial/

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewRegister;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = firebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignin);
        textViewRegister = (TextView) findViewById(R.id.textViewRegister);

        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);

    }

    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();





        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Signing in now!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        } else {
                            Message.message(getApplicationContext(), "Login unsuccessful. Please try again");
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        if (view == buttonSignIn){
            userLogin();
        }

        if(view == textViewRegister){
            finish();
            startActivity(new Intent(this, RegisterScreen.class));
        }
    }
}
