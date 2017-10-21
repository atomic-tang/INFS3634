package com.example.android.infs3634;

import com.google.firebase.database.DataSnapshot;

// Task subclass to create video objects from data retrieved from firebase database
public class Video extends Task {

    private String description;
    private String url;

    // Constructor
    Video(DataSnapshot snapshot) {
        String key = snapshot.getKey();
        if (key != null) {
            taskKey = key;
        } else {
            taskKey = "";
        }

        String titleString = snapshot.child("title").getValue().toString();
        if (titleString != null) {
            title = titleString;
        } else {
            title = "";
        }

        String taskType = snapshot.child("type").getValue().toString();
        if (taskType != null) {
            type = taskType;
        } else {
            type = "";
        }

        String desc = snapshot.child("description").getValue().toString();
        if (desc != null) {
            description = desc;
        } else {
            description = "";
        }

        String videoUrl = snapshot.child("url").getValue().toString();
        if (videoUrl != null) {
            url = videoUrl;
        } else {
            url = "";
        }
    }

    // Getter methods
    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
