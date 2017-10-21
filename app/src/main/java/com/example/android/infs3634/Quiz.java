package com.example.android.infs3634;

import android.os.Parcel;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;
import java.util.ArrayList;

// Task subclass to create quiz objects from data retrieved from firebase database
public class Quiz extends Task {

    private ArrayList<String> questionIds;

    // Constructor
    public Quiz(DataSnapshot snapshot) {
        super();

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

        ArrayList<String> strings = new ArrayList<>();

        for (DataSnapshot snap: snapshot.child("questions").getChildren()) {
            String questionKey = snap.getValue().toString();
            if (questionKey != null) {
                strings.add(questionKey);
            }
        }

        questionIds = strings;
    }

    // Getter method
    public ArrayList<String> getQuestionIds() {
        return questionIds;
    }
}
