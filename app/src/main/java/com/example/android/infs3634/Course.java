package com.example.android.infs3634;

import android.support.constraint.solver.widgets.Snapshot;
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

    Course(DataSnapshot snapshot) {

        String key = snapshot.getKey();
        if (key != null) {
            courseId = key;
        } else {
            courseId = "";
        }
        String name = snapshot.child("name").getValue().toString();
        if (name != null) {
            courseTitle = name;
        } else {
            courseTitle = "";
        }

        ArrayList<String> strings = new ArrayList<>();

        for (DataSnapshot snap: snapshot.child("lessons").getChildren()) {
            String lessonKey = snap.getValue().toString();
            if (lessonKey != null) {
                strings.add(lessonKey);
            }
        }

        lessonIds = strings;

    }

    Course(String id, String title, ArrayList<String> ids) {
        courseTitle = title;
        courseId = id;
        lessonIds = ids;
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
