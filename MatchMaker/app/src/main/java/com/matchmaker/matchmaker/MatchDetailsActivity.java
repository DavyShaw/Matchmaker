package com.matchmaker.matchmaker;
/**************************************************************************************************
 MatchDetailsActivity
 Authors: Andrew Cameron, Pamela Kelly
 Date:
 Course: COMP 41690 Android Programming
 Usage: An Activity for presenting the details of an individual event.
 **************************************************************************************************/

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;


import java.io.IOException;
import java.util.List;


public class MatchDetailsActivity extends AppCompatActivity  {
    private static String TAG = "MatchDetailsActivity";
    private FirebaseAuth firebaseAuth;
    myDbAdapter dbAdapter;
    String matchDetails;
    String activityUserChoice;
    // event details
    String eventDetails;
    String participants;
    String eventName;
    String eventOrganiser;
    String eventDate;
    String eventTime;
    String eventLocation;

    public static String address = "";
    public static LatLng event_location = new LatLng(0,0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbAdapter = new myDbAdapter(this); // start a db instance
        Bundle extras = getIntent().getExtras();
        // Match Details Contains: Name, Organiser, Date, Time, Location, Participants
        activityUserChoice = extras.getString("Activity");
        matchDetails = extras.getString("Match Details");
        eventDetails = matchDetails.split(";")[0];
        participants = matchDetails.split(";")[1];
        eventName = eventDetails.split(",")[0];
        eventOrganiser = eventDetails.split(",")[1];
        eventDate = eventDetails.split(",")[2];
        eventTime = eventDetails.split(",")[3];
        eventLocation = eventDetails.split(",")[4];

        Event event = new Event(eventDate, eventLocation, eventName, eventOrganiser, participants, eventTime);
        System.out.println("checking event details");
        System.out.println(event.toStringPretty());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);

        TextView tvWhat = (TextView) findViewById(R.id.tvWhat);
        tvWhat.setText(activityUserChoice);

        TextView tvWhere = (TextView) findViewById(R.id.tvWhere);
        tvWhere.setText(event.location);

        TextView tvWhen = (TextView) findViewById(R.id.tvWhen);
        String when = event.date + event.time;
        tvWhen.setText(when);
        String[] participantsArray = participants.split(",");
        // https://stackoverflow.com/questions/5070830/populating-a-listview-using-an-arraylist
        ListView lvParticipants = (ListView) findViewById(R.id.lvParticipants);
        ArrayAdapter<String> participantsArrayAdapt = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, participantsArray);
        lvParticipants.setAdapter(participantsArrayAdapt);

        // Getting Directions for the User to the Event
        Geocoder coder = new Geocoder(this);
        String str = tvWhere.getText().toString();
        str.replace(";", "");

        String parts[] = str.trim().split("\\s+",2);

        String suffix = ", Ireland";

        address = str + suffix;
        event_location = getLocationFromAddress(coder, address);
        FloatingActionButton mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(), Navigation.class);
                startActivity(intent1);
            }
        });
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
        DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("events")
                .child(activityUserChoice.toLowerCase()).child(eventName);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // user should never be null because unauthorised users don't have access to this page
        if (participants.contains(user.getEmail())) {
            Message.message(this, "You are already attending this event");
        }
        else {
            participants += (", " + user.getEmail());
            // replaces the current string with new updated string
            eventRef.child("participants").setValue(participants);
            Message.message(this, "Successfully added to event");
            // change join event button: id: bJoinEvent
            Button p1_button = (Button) findViewById(R.id.bJoinEvent);
            p1_button.setText("Your are a Member of This Event");
            String[] participantsArray = participants.split(",");
            // https://stackoverflow.com/questions/5070830/populating-a-listview-using-an-arraylist
            ListView lvParticipants = (ListView) findViewById(R.id.lvParticipants);
            ArrayAdapter<String> participantsArrayAdapt = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, participantsArray);
            lvParticipants.setAdapter(participantsArrayAdapt);
        }
    }

    public void addToUserDB(View view, String eventName, String eventDate) {
        // get user details
        FirebaseUser user2 = firebaseAuth.getCurrentUser();
        String email = user2.getEmail();
        String oldEvent = dbAdapter.getEventData(email);

        StringBuilder eventInfo = new StringBuilder();
        eventInfo.append(eventName + ", " + eventTime + ", " + eventDate);
        dbAdapter.updateEvents(email, oldEvent, eventInfo.toString());
    }

    public LatLng getLocationFromAddress(Geocoder coder, String strAddress) {
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            else {
                Address location = address.get(0);
                location.getLatitude();
                location.getLongitude();
                p1 = new LatLng(location.getLatitude(), location.getLongitude() );
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p1;
    }
}