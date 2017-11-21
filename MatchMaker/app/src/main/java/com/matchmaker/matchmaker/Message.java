package com.matchmaker.matchmaker; /**
 * Created by EByrn on 09/11/2017.
 */


import android.content.Context;
import android.widget.Toast;

// Message class to produce a Toast - adapted from http://abhiandroid.com/database/sqlite#Creating_And_Updating_Database_In_Android
public class Message {
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}