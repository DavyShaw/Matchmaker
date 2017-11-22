package com.matchmaker.matchmaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SearchResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ArrayAdapter<String> eventsAdapter = new ArrayAdapter<String>(SearchResults.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.dummyList));
        final ListView resultsListView = (ListView) findViewById(R.id.resultsListView);

        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchResults.this, MatchDetailsActivity.class);
                intent.putExtra("Match Title", resultsListView.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });
        resultsListView.setAdapter(eventsAdapter);
    }

}
//reference for setOnItemClickListener https://www.youtube.com/watch?v=wSCIuIbS-nk