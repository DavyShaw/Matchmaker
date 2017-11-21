package com.matchmaker.matchmaker;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by pamela on 18/11/17.
 */

// Not sure why we need this class -  but all examples of reading data from Firebase have it...
@IgnoreExtraProperties
public class Event {

    public String date;
    public String location;
    public String organiser;
    public String time;

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue()
    }

    public Event(String date, String location, String organiser, String time) {

        this.organiser = organiser;
        this.date = date;
        this.time = time;
        this.location = location;
    }
}
