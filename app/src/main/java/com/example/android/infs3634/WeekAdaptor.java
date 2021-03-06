package com.example.android.infs3634;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

// Array Adapter for weekly lessons available in each course
public class WeekAdaptor extends ArrayAdapter<Week> {
    public WeekAdaptor(Context context, ArrayList<Week> weeks) {
        super(context, R.layout.row_week,weeks);
    }

    // Get view template for each lesson in listview
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate the view to show each lesson
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View weekView = inflater.inflate(R.layout.row_week, parent, false);

        Week week = getItem(position);
        ConstraintLayout layout = weekView.findViewById(R.id.layout);
        ImageView imageView = weekView.findViewById(R.id.weekImageView);
        TextView titleTextView = weekView.findViewById(R.id.weekTitleTextView);
        TextView descriptionTextView = weekView.findViewById(R.id.weekDescriptionTextView);

        // Set information and badge image for each lesson
        ImageManager.manager.setIconImageView(week.getWeekId(), imageView);
        DataService.instance.checkCompleteLess(week.getWeekId(), layout);
        titleTextView.setText("Week " + week.getWeekNumber() + ": " + week.getWeekTopic());
        descriptionTextView.setText(week.getWeekDetails());

        return weekView;
    }
}
