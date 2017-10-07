package com.example.android.infs3634;

/**
 * Created by PakinLertthamanon on 9/26/17.
 */

public class Course {

    private String courseTitle;
    private String courseId;

    Course(String title, String id) {
        courseTitle = title;
        courseId = id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
