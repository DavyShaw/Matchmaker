package com.matchmaker.matchmaker;

/**************************************************************************************************
 - Name of activity here -
 Authors:
 Date:
 Course: COMP 41690 Android Programming
 Usage:
 **************************************************************************************************/


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MatchPreferencesActivity extends AppCompatActivity {
    private static String TAG = "MatchPreferencesActivity";

    private int mYear, mMonth, mDay, mHour, mMinute;
    private String userTimeChoice;
    private String userDateChoice;
    private String userActivityChoice;
    String[] matchResults = new String[5];
    StringBuilder userPreferences = new StringBuilder();
    int count = 0;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_preferences);
        // Get the Intent that start this activity and extract the string
        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(R.string.select_preferences_message);


        //######################################SPINNER########################################
        // Setting up the Spinner for sport choice
        Spinner spinner = (Spinner) findViewById(R.id.sports_spinner);

        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.sports_list_values,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                userActivityChoice = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //##################################### Time Picker Set Up #################################
    // reference: https://www.journaldev.com/9976/android-date-time-picker-dialog
    public void showTimePickerDialog(View v) {
        // function for displaying the time picker
        final EditText editTextTime = (EditText) findViewById(R.id.time);
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        editTextTime.setText(hourOfDay + ":" + minute);
                        userTimeChoice = hourOfDay + ":" + minute;
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    //##################################### Date Picker Set Up ##################################
    // reference: https://www.journaldev.com/9976/android-date-time-picker-dialog
    public void showDatePickerDialog(View v) {
        final EditText editTextDate = (EditText) findViewById(R.id.date);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                userDateChoice = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void sendPreferences(View view) {
        getMatches();
        System.out.println("Called getMatches()");
    }

    public void getMatches() {
        // Method to get the data from Firebase
        //################### Get Data from Firebase ############################

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("events");
        Query eventQuery = myRef.child(userActivityChoice.toLowerCase());
        eventQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // HashMap<String, Event> events = new HashMap<>();
                ArrayList<Event> events = new ArrayList<>();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    String eventName = singleSnapshot.getKey();
                    System.out.println("KEY");
                    System.out.println(eventName);
                    Event event = singleSnapshot.getValue(Event.class);
                    event.setEventName(singleSnapshot.getKey());
                    event.setCategory(userActivityChoice);
                    //events.put(event.getEventName(), event);
                    events.add(event);
                    Log.d(TAG, "EVENT="+event.toDebugString());
                    // might be able to get rid of this
                    String eventString = event.toString();
                    matchResults[count] = eventString;
                    count += 1;
                    // Doesn't always get to 5!
                    if (count >= 5) {
                        break;
                    }
                    for (String match : matchResults) {
                        if (match != null) {
                            userPreferences.append(match);
                            userPreferences.append("!");
                        }
                    }
                    String userPrefs = userPreferences.toString();
                    System.out.println(userPrefs); // doesn't include name of the match
                    Intent intent = new Intent(getBaseContext(), SearchResults.class);
                    intent.putExtra("Activity", userActivityChoice);
                    intent.putExtra("userPreferences", userPrefs);
                    intent.putExtra("events", events);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post Failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }


        });

    }
    }



