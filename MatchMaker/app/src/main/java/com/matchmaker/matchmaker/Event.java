package com.matchmaker.matchmaker;
/**************************************************************************************************
 Event Class
 Authors: Pamela Kelly, Andrew Cameron
 Date:
 Course: COMP 41690 Android Programming
 Usage: Model Class to represent event objects from the Firebase database.
 **************************************************************************************************/

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by pamela on 18/11/17.
 */

@IgnoreExtraProperties
public class Event {

    public String date;
    public String name;
    public String location;
    public String organiser;
    public String participants = "";
    public String time;

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue()
    }

    public Event(String date, String location, String name, String organiser, String participants, String time) {

        this.organiser = organiser;
        this.name = name;
        this.date = date;
        this.time = time;
        this.participants = participants;
        this.location = location;
    }

    public String toString() {
        String eventStringRepresentation = new String();
        eventStringRepresentation += (this.name + ", " + this.organiser + ", " + this.date
                + ", " + this.time + ", " + this.location + " ");
        if (this.participants != "") {
            eventStringRepresentation += ("; " + participants);
        }
        return eventStringRepresentation;
    }

    public String toStringPretty() {
        String eventStringRepresentation = new String();
        eventStringRepresentation += ("Organiser: " + this.organiser + ",\n " + "Date: " + ",\n"
                + this.date + "Time: " + this.time + ",\n" + "Location: " + this.location);
        if (this.participants != "") {
            eventStringRepresentation += (";\nParticipants: " + participants);
        }
        return eventStringRepresentation;
    }
}


