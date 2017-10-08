package com.example.android.infs3634;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by PakinLertthamanon on 9/26/17.
 */

public class Course implements Serializable {

    private String courseId;
    private String courseTitle;
    private  ArrayList<String> lessonIds;

    Course(String id, String title) {
        courseTitle = title;
        courseId = id;
    }

    Course(String id, String title, DataSnapshot snap) {
        courseTitle = title;
        courseId = id;
        lessonIds = new ArrayList<>();
        DataSnapshot snapshot = snap.child("lessons");

        for (DataSnapshot s: snapshot.getChildren()) {
            lessonIds.add(s.getValue().toString());
        }
    }

    public ArrayList<String> getLessonIds() {
        return lessonIds;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseId() {
        return courseId;
    }


}
