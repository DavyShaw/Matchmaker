package com.matchmaker.matchmaker;

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


import java.io.IOException;
import java.util.List;


public class MatchDetailsActivity extends AppCompatActivity  {
    private static String TAG = "MatchDetailsActivity";

    String matchDetails;
    String activityUserChoice;

    public static String address = "";

    public static LatLng event_location = new LatLng(0,0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        // Match Details - Should contain Organiser, Date, Time, Location (currently null not sure why)
        // TODO: Fix Location as null
        activityUserChoice = extras.getString("Activity");
        matchDetails = extras.getString("Match Details");
        String[] matchDetailsArray = matchDetails.split(";");
        Event event = new Event(matchDetailsArray[1], matchDetailsArray[0], matchDetailsArray[2], matchDetailsArray[3], matchDetailsArray[4]);
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
        System.out.println(matchDetailsArray);
        String participants = matchDetailsArray[4];
        System.out.println(participants);
        String[] participantsArray = participants.split(",");
        // https://stackoverflow.com/questions/5070830/populating-a-listview-using-an-arraylist
        ListView lvParticipants = (ListView) findViewById(R.id.lvParticipants);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, participantsArray);
        lvParticipants.setAdapter(aa);

        Geocoder coder = new Geocoder(this);

        String str = tvWhen.getText().toString();

        String parts[] = str.trim().split("\\s+",2);

        String suffix = ", Ireland";

        address = parts[1] + suffix;
        Log.i(TAG, str);
        Log.i(TAG, parts[0]);
        Log.i(TAG, parts[1]);
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