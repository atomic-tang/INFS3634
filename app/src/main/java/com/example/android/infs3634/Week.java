package com.example.android.infs3634;

import com.google.firebase.database.DataSnapshot;
import java.io.Serializable;
import java.util.ArrayList;

// Class to create lesson weeks from data retrieved from firebase database
public class Week implements Serializable {

    private String weekId;
    private String weekNumber;
    private String weekTopic;
    private String weekDetails;
    private ArrayList<String> activityKeys;

    // Constructor
    Week(DataSnapshot snapshot) {
        String key = snapshot.getKey();
        if (key != null) {
            weekId = key;
        } else {
            weekId = "";
        }

        String number = snapshot.child("weekNo").getValue().toString();
        if (number != null) {
            weekNumber = number;
        } else {
            weekNumber = "1";
        }

        String topic = snapshot.child("name").getValue().toString();
        if (topic != null) {
            weekTopic = topic;
        } else {
            weekTopic = "";
        }

        String details = snapshot.child("details").getValue().toString();
        if (details != null) {
            weekDetails = details;
        } else {
            weekDetails = "";
        }

        ArrayList<String> strings = new ArrayList<>();

        for (DataSnapshot snap: snapshot.child("activities").getChildren()) {
            String activityKey = snap.getValue().toString();
            if (activityKey != null) {
                strings.add(activityKey);
            }
        }

        activityKeys = strings;

    }

    // Getter methods
    public String getWeekId() {
        return weekId;
    }

    public String getWeekNumber() {
        return weekNumber;
    }

    public String getWeekTopic() {
        return weekTopic;
    }

    public String getWeekDetails() {
        return weekDetails;
    }

    public ArrayList<String> getActivityKeys() {
        return activityKeys;
    }
}
