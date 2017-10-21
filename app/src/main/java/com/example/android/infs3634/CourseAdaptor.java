package com.example.android.infs3634;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// Array Adapter for courses available in application
public class CourseAdaptor extends ArrayAdapter<Course> {
    // Constructor
    CourseAdaptor(Context context, ArrayList<Course> courses) {
        super(context, R.layout.row_course, courses);
    }

    // Get view template for each course in listview
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate the view to show each course
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View courseView = inflater.inflate(R.layout.row_course, parent, false);

        // Set information for each course
        Course course = getItem(position);
        TextView titleTextView = courseView.findViewById(R.id.courseTitleTextView);
        TextView codeTextView = courseView.findViewById(R.id.courseCodeTextView);

        titleTextView.setText(course.getCourseTitle());
        codeTextView.setText(course.getCourseId());

        return courseView;
    }
}
