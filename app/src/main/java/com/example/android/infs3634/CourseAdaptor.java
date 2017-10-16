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

public class CourseAdaptor extends ArrayAdapter<Course> {

    CourseAdaptor(Context context, ArrayList<Course> courses) {
        super(context, R.layout.row_course, courses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View courseView = inflater.inflate(R.layout.row_course, parent, false);

        Course course = getItem(position);
        TextView titleTextView = courseView.findViewById(R.id.courseTitleTextView);
        TextView codeTextView = courseView.findViewById(R.id.courseCodeTextView);

        titleTextView.setText(course.getCourseTitle());
        codeTextView.setText(course.getCourseId());

        return courseView;
    }
}
