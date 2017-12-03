package com.matchmaker.matchmaker;

/**************************************************************************************************
 Event Class
 Authors: Pamela Kelly
 Course: COMP 41690 Android Programming
 Usage: Model Class to represent user objects from the Firebase database.
 **************************************************************************************************/

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String nickname;
    public String past_matches;
    public String preferences;
    public String upcoming_matches;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue()
    }

    public User(String nickname, String past_matches, String preferences, String upcoming_matches) {
        this.nickname = nickname;
        this.past_matches = past_matches;
        this.preferences = preferences;
        this.upcoming_matches = upcoming_matches;
    }

    public String toString() {
        String userStringRepresentation = new String();
        userStringRepresentation += (this.nickname);
        if (this.preferences != "") {
            userStringRepresentation += (", " + (this.preferences));
        }
        if (this.past_matches != "") {
            userStringRepresentation += ("; " + (this.past_matches));
        }
        if (this.upcoming_matches != "") {
            userStringRepresentation += ("; " + (this.upcoming_matches));
        }
        return userStringRepresentation;
    }
}

