package com.matchmaker.matchmaker;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by pamela on 18/11/17.
 */

// Not sure why we need this class -  but all examples of reading data from Firebase have it...
@IgnoreExtraProperties
public class Event {

    public String event_name;
    public String activity;
    public String organiser;
    public String date;
    public String time;
    public String location;

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue()
    }

    public Event(String organiser, String event_name, String activity, String date,
                 String time, String location) {
        this.event_name = event_name;
        this.organiser = organiser;
        this.activity = activity;
        this.date = date;
        this.time = time;
        this.location = location;
    }
}
