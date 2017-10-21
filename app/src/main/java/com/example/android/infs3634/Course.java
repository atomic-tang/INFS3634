package com.example.android.infs3634;

import com.google.firebase.database.DataSnapshot;
import java.io.Serializable;
import java.util.ArrayList;

// Class to create course objects from data retrieved from firebase database
public class Course implements Serializable {

    private String courseId;
    private String courseTitle;
    private  ArrayList<String> lessonIds;

    // Constructor
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

    // Getter methods
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
