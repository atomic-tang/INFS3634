package com.example.android.infs3634;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by PakinLertthamanon on 10/9/17.
 */

public class Quiz extends Task {

    private ArrayList<Question> questions;

    public Quiz(DataSnapshot snap) {

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

    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

}
