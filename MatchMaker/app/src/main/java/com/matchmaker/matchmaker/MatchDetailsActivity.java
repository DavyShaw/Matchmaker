package com.matchmaker.matchmaker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MatchDetailsActivity extends AppCompatActivity  {
    private static String TAG = "MatchDetailsActivity";
    String matchDetails;
    String activityUserChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        // Match Details - Should contain Organiser, Date, Time, Location (currently null not sure why)
        // TODO: Fix Location as null
        activityUserChoice = extras.getString("Activity");
        matchDetails = extras.getString("Match Details");
        System.out.println("Match Details");
        Log.d(TAG, matchDetails);
        //TODO: SOMETIMES CRASHES HERE TRYING TO SPLIT
        String[] matchDetailsArray = matchDetails.split(",");
        Event event = new Event(matchDetailsArray[1], matchDetailsArray[0], matchDetailsArray[2], matchDetailsArray[3]);
        System.out.println(event.toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);

        // MatchData should probably be passed as an intent from the previous screen?
        // Or maybe make an API call to the MatchMaker server here.
        // MatchData md = getMatchData();

        TextView tvWhat = (TextView) findViewById(R.id.tvWhat);
        tvWhat.setText(activityUserChoice);

        TextView tvWhere = (TextView) findViewById(R.id.tvWhere);
        tvWhere.setText(event.location);

        TextView tvWhen = (TextView) findViewById(R.id.tvWhen);
        String when = event.date + event.time;
        tvWhen.setText(when);
        String[] sampleParticipants = {"Pam", "Emma", "Andy", "Cara", "Davy"};
        // https://stackoverflow.com/questions/5070830/populating-a-listview-using-an-arraylist
        ListView lvParticipants = (ListView) findViewById(R.id.lvParticipants);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, sampleParticipants);
        lvParticipants.setAdapter(aa);
    }
    //https://stackoverflow.com/questions/6554317/savedinstancestate-is-always-null
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onJoinEventClick(View view) {
        Log.d(TAG, "JoinedButtonClicked");
        DatabaseReference eventsDB = FirebaseDatabase.getInstance().getReference("events");
        Log.d(TAG, eventsDB.toString());
        Bundle extras = getIntent().getExtras();
        Log.d(TAG, extras.toString());
    }

    public void joinEvent() {

    }
}


