package com.matchmaker.matchmaker;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pamela on 18/11/17.
 */
/**************************************************************************************************
 - Name of activity here -
 Authors:
 Date:
 Course: COMP 41690 Android Programming
 Usage:
 **************************************************************************************************/


// Not sure why we need this class -  but all examples of reading data from Firebase have it...
@IgnoreExtraProperties
public class Event implements Serializable {
    private String eventName;
    public String date;
    public String location;
    public String organiser;
    public String time;
    private HashMap<String, Boolean> participants;
    private String category;

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue()
        //this("DefaultDate", "DefaultLocation", "DefaultOrganiser", "DefaultTime");
    }

    public Event(String date, String location, String organiser, String time) {
        this(date, location, organiser, time, new HashMap<String, Boolean>());
    }

//    public Event(String eventName, String date, String location, String organiser, String time, HashMap<String, Boolean> participants) {
//        this.eventName = eventName;
//        this.organiser = organiser;
//        this.date = date;
//        this.time = time;
//        this.location = location;
//        participants.put(organiser, true);
//        this.participants = participants;
//    }

    public Event(String date, String location, String organiser, String time, HashMap<String, Boolean> participants) {
        this.organiser = organiser;
        this.date = date;
        this.time = time;
        this.location = location;
        this.participants = new HashMap<String, Boolean>();
        //participants.put(organiser, true);
        this.participants = participants;
    }

    public String toString() {
        String eventStringRepresentation = new String();
        eventStringRepresentation += this.organiser += ", " + this.date
                + ", " + this.time + ", " + this.location + ", participants: " + this.participants.toString();
        return eventStringRepresentation;
    }

    public void setCategory(String category) {
        //TODO: check whterh category is valid
        this.category = category;
    }
    public String getCategory() { return this.category; }

    public String toDebugString() {
        return "<< NAME: " + this.eventName + " |" +
                "ORG: " + this.organiser + " |"
                + " DATE: " + this.date + " |"
                + " TIME: " + this.time + " |"
                + " LOC: " + this.location + " |"
                + " PARTIC: " + this.getParticipants().toString() + " >>";
    }

    // might not need this method
    public void attend(String user) {
        this.participants.put(user, true);
    }

    public void attend(FirebaseUser user) {
        String email = user.getEmail().split("\\.")[0];
        // Firebase doesn't like dots in keys - split to be just the email up to the dot
        this.participants.put(email, true);
    }

    public String getEventName() { return this.eventName;}
    public void setEventName(String eventName) {this.eventName = eventName;}

    public void unattend(String user) {
        this.participants.remove(user);
    }

    public void unattend(FirebaseUser user) {
        this.participants.remove(user.getEmail());
    }
    public HashMap<String, Boolean> getParticipants() {
        return this.participants;
    }
    public void setParticipants(HashMap<String, Boolean> map) {
        this.participants = map;
    }

//    public Map<String, Object> getParticipantsMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        for (Map.Entry<String, Boolean> entry : this.participants.entrySet())
//            result.put(entry.getKey(), (Object) entry.getValue());
//
//        return (Map<String, Object>) result;
//    }

    public ArrayList<String> getParticipantsList() {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : this.participants.entrySet())
            result.add(entry.getKey());
        return result;
    }
}
