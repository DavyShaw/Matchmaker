package com.matchmaker.matchmaker;

/**************************************************************************************************
 - Name of activity here -
 Authors:
 Date:
 Course: COMP 41690 Android Programming
 Usage:
 **************************************************************************************************/

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class MatchDetailsActivity extends AppCompatActivity  {
    private static String TAG = "MatchDetailsActivity";
    private FirebaseAuth firebaseAuth;
    private Event event;

    myDbAdapter dbAdapter;

    String matchDetails;
    String activityUserChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbAdapter = new myDbAdapter(this); // start a db instance
        Bundle extras = getIntent().getExtras();
        // Match Details - Should contain Organiser, Date, Time, Location (currently null not sure why)
        // TODO: Fix Location as null
        activityUserChoice = extras.getString("Activity");
        matchDetails = extras.getString("Match Details");
        this.event = (Event) extras.getSerializable("event");
        //Log.d(TAG, "EVENT="+event.toDebugString());
        Log.d(TAG, "MATCH_DETAILS=" + matchDetails);
        //TODO: SOMETIMES CRASHES HERE TRYING TO SPLIT
        String[] matchDetailsArray = matchDetails.split(",");
        //this.event = new Event(matchDetailsArray[1], matchDetailsArray[0], matchDetailsArray[2], matchDetailsArray[3]);
        System.out.println("The event: \n\n" + event.toString());
        //this.event = new Event(matchDetailsArray[1], matchDetailsArray[0], matchDetailsArray[2], matchDetailsArray[3]);
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
        String when = event.date + " " + event.time;
        tvWhen.setText(when);
        //String[] sampleParticipants = {"Pam", "Emma", "Andy", "Cara", "Davy"};
        // https://stackoverflow.com/questions/5070830/populating-a-listview-using-an-arraylist
        ListView lvParticipants = (ListView) findViewById(R.id.lvParticipants);
        Log.d(TAG, "PARTICPS="+this.event.getParticipants().toString());
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, this.event.getParticipantsList());
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
        joinEvent();

        // add the event to the users' local db
        // get the values from the textviews for time etc. add them to db with event name
        if (isUserInEvent(view)) {
            Message.message(this, "You are already part of this event.");
        } else {
            //addToUserDB(view, event.getEventName(), event.time, event.date);
            Message.message(this, "Successfully added to event");
        }
    }

    public void joinEvent() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Log.d(TAG, "USER NOT FOUND");
            return;
        }
        Log.d(TAG, "USER EMAIL: " + user.getEmail());
        Log.d(TAG, this.event.toDebugString());
        this.event.attend(user);
        Log.d(TAG, this.event.toDebugString());
        DatabaseReference dbEvents = FirebaseDatabase.getInstance().getReference("events");
        Map<String, Object> participantsMap = (Map) event.getParticipants();

        Log.d(TAG, "EVENT_NAME=" + event.getEventName());
        Log.d(TAG, "UIS=" + user.getUid());

        dbEvents.child(event.getCategory())
                .child(event.getEventName())
                .child("participants")
                .child(user.getUid()).setValue(true);

        Log.d(TAG, "FIREBSAE_DB=UPDATED?");
    }
    // Method to add the current user to the local database - called when user joins the event
    public void addToUserDB(View view, String eventName, String eventTime, String eventDate){
        // get user details
        FirebaseUser user2 = firebaseAuth.getCurrentUser();
        String email  = user2.getEmail(); // get the users' email to pass into updateEvents
        String oldEvent = dbAdapter.getEventData(email);

        StringBuilder eventInfo = new StringBuilder();
        eventInfo.append(eventName + ", " + eventTime + ", " + eventDate);
        dbAdapter.updateEvents(email, oldEvent, eventInfo.toString());
    }

    // method to check if the user is already part of the event
    public Boolean isUserInEvent(View view){
        // get user event data
        // if the event name is in the user event data then true, else false
        // true if user is already in the event
        FirebaseUser user2 = firebaseAuth.getInstance().getCurrentUser();
        String eventData = dbAdapter.getEventData(user2.getEmail());
        System.out.println("the data is " + eventData);
        System.out.println("the name is: " + event.getEventName()); // this isn't working
        if (eventData.contains(event.getEventName())){
            return true;
        } else {
            return false;
        }
    }
}


