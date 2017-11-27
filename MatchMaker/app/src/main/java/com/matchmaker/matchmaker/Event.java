package com.matchmaker.matchmaker;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private HashMap<String, Boolean> participants;

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue()
        //this("DefaultDate", "DefaultLocation", "DefaultOrganiser", "DefaultTime");
        this.participants = new HashMap<String, Boolean>();
    }

    public Event(String date, String location, String organiser, String time) {
        this(date, location, organiser, time, new HashMap<String, Boolean>());
    }

    public Event(String date, String location, String organiser, String time, HashMap<String, Boolean> participants) {
        this.organiser = organiser;
        this.date = date;
        this.time = time;
        this.location = location;
        participants.put(organiser, true);
        this.participants = participants;
    }

    public String toString() {
        String eventStringRepresentation = new String();
        eventStringRepresentation += this.organiser += ", " + this.date
                + ", " + this.time + ", " + this.location + ", participants: " + this.getParticipants().toString();
        return eventStringRepresentation;
    }

    public void attend(String user) {
        this.participants.put(user, true);
    }

    public void unattend(String user) {
        this.participants.remove(user);
    }

    public ArrayList<String> getParticipants() {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : this.participants.entrySet())
            result.add(entry.getKey());
        return result;
    }
}
