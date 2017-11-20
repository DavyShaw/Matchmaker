package com.matchmaker.matchmaker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SearchResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        String[] events = {"Monday Football", "Tuesday Golf", "Wednesday Tennis", "Friday Rugby", "Tuesday Basketball", "Thursday Tennis", "Friday Frisbee", "Saturday Gaelic", "Sunday Rugby", "Wednesday Football", "Thursday Tennis", "Friday Frisbee", "Saturday Gaelic", "Sunday Rugby"};
        ListAdapter eventsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, events);
        ListView resultsListView = (ListView) findViewById(R.id.resultsListView);
        resultsListView.setAdapter(eventsAdapter);
    }
}
