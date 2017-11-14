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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class userAccount extends AppCompatActivity {
    myDbAdapter dbAdapter;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        dbAdapter = new myDbAdapter(this);
        System.out.println("The database was created");

        LinearLayout layout = (LinearLayout) findViewById(R.id.boxes);
        System.out.println("the child count is " + layout.getChildCount());

        // https://stackoverflow.com/questions/39438950/get-all-child-views-inside-linear-layout
        for (int x = 0; x< layout.getChildCount();x++) {
            CheckBox cb = (CheckBox) layout.getChildAt(x);
            System.out.println(cb.getText());
            if (cb.isChecked()) {
                System.out.println("This checkbox");
            }
        }




    }

    // view the data sent from the profile activity
    // send name and email here, then we can call to the db
    public void addData(View view) {
        //dbAdapter = new myDbAdapter(this);
        //String data = dbAdapter.getData();
        EditText editText = (EditText) findViewById(R.id.inputName);
        EditText editText1 = (EditText) findViewById(R.id.preferences);
        String name = editText.getText().toString();
        String preferences = editText1.getText().toString();
//        System.out.println(preferences.length());
//
//        Character[] arr = new Character[preferences.length()];
//
//        for(int x = 0; x < preferences.length(); x++) {
////            System.out.print(preferences.charAt(x));
////            System.out.print("\n");
//            arr[x] = preferences.charAt(x);
//        }
//        System.out.println("\n\n\n the preferences are " + preferences + "\n\n\n");
//        System.out.println(arr);
//        // create db and entry with this info
        //long id = dbAdapter.insertData(name, "1234");




        //TextView textView = (TextView) findViewById(R.id.displayData);
        //textView.setText(data);
        //Message.message(this,data);
    }

    public void displayAllData(View view){
        String data = dbAdapter.getData();
        Message.message(this, data);
    }


}
