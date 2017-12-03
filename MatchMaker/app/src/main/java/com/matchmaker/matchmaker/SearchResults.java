package com.matchmaker.matchmaker;
/**************************************************************************************************
 SearchResults
 Authors: Cara Delorey, Pamela Kelly, Andrew Cameron
 Date:
 Course: COMP 41690 Android Programming
 Usage: An Activity for presenting a list of results of events based on user's input preferences
 **************************************************************************************************/

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Semaphore;

// save data before moving onto next intent
// or flush it to a file or a store or something

public class SearchResults extends AppCompatActivity {
    String stringResults;
    String userActivityChoice;
    String[] arrayResults = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        // get extra data sent with intent creation
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            userActivityChoice = savedInstanceState.getString("userActivityChoice");
            arrayResults = savedInstanceState.getStringArray("arrayResults");
        } else {
            Bundle extras = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();
            // Event Strings give details in the order of: Event Name, Organiser, Date, Time, Location and Participants
            // Participants is separated by a ; and everything else can be split by ,
            arrayResults[0] = extras.getString("Event0");
            arrayResults[1] = extras.getString("Event1");
            arrayResults[2] = extras.getString("Event2");
            arrayResults[3] = extras.getString("Event3");
            arrayResults[4] = extras.getString("Event4");
            userActivityChoice = extras.getString("Activity");
        }

        // Array to show results
        ArrayAdapter<String> eventsAdapter = new ArrayAdapter<String>(SearchResults.this,
                android.R.layout.simple_list_item_1, arrayResults);

        final ListView resultsListView = (ListView) findViewById(R.id.resultsListView);

        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchResults.this, MatchDetailsActivity.class);
                intent.putExtra("Activity", userActivityChoice);
                intent.putExtra("Match Details", resultsListView.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });
        resultsListView.setAdapter(eventsAdapter);
    }

    // Ref: https://stackoverflow.com/questions/151777/saving-android-activity-state-using-save-instance-state
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putString("userActivityChoice", userActivityChoice);
        savedInstanceState.putStringArray("arrayResults", arrayResults);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        String userActivityChoice = savedInstanceState.getString("userActivityChoice");
        String[] arrayResults = savedInstanceState.getStringArray("arrayResults");
    }
}

//reference for setOnItemClickListener https://www.youtube.com/watch?v=wSCIuIbS-nk