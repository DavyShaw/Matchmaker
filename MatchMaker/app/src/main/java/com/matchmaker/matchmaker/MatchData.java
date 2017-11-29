package com.matchmaker.matchmaker;

import java.util.ArrayList;

/**
 * Created by andrew on 13/11/2017.
 */

/**************************************************************************************************
 - Name of activity here -
 Authors:
 Date:
 Course: COMP 41690 Android Programming
 Usage:
 **************************************************************************************************/


public class MatchData {
    private String what;
    private String when;
    private String where;
    private ArrayList<String> participants;

    public MatchData(String what, String when, String where) {
        this.what = what;
        this.where = where;
        this.when = when;
        this.participants = new ArrayList<String>();
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public void addParticipant(String name) {
        this.participants.add(name);
    }

    public ArrayList<String> getParticipants() {
        return this.participants;
    }
}
