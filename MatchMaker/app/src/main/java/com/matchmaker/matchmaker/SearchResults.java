package com.matchmaker.matchmaker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Semaphore;

public class SearchResults extends AppCompatActivity {
    String[] matchResults = new String[5];
    String stringResults;
    String userActivityChoice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get extra data sent with intent creation
        Bundle extras = getIntent().getExtras();
        stringResults = extras.getString("userPreferences");
        userActivityChoice = extras.getString("Activity");
        //String userDateChoice = extras.getString("datePref");
        //String userTimeChoice = extras.getString("timePref");

        // Get the match results
        String[] arrayResults = stringResults.split("!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

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

}
//reference for setOnItemClickListener https://www.youtube.com/watch?v=wSCIuIbS-nk