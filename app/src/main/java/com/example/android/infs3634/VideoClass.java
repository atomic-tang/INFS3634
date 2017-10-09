package com.example.android.infs3634;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by PakinLertthamanon on 10/9/17.
 */

public class VideoClass extends Task {

    private String description;
    private String url;

    VideoClass(DataSnapshot snap) {
        snapshot = snap;

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

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
