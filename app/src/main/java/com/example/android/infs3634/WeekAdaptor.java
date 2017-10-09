package com.example.android.infs3634;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PakinLertthamanon on 9/26/17.
 */

public class WeekAdaptor extends ArrayAdapter<Week> {

    public WeekAdaptor(Context context, ArrayList<Week> weeks) {
        super(context, R.layout.row_week,weeks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View weekView = inflater.inflate(R.layout.row_week, parent, false);

        Week week = getItem(position);
        ImageView imageView = (ImageView) weekView.findViewById(R.id.weekImageView);
        TextView titleTextView = (TextView) weekView.findViewById(R.id.weekTitleTextView);
        TextView descriptionTextView = (TextView) weekView.findViewById(R.id.weekDescriptionTextView);

        //set Image
        titleTextView.setText("Week" + week.getWeekNumber() + ": " + week.getWeekTopic());
        descriptionTextView.setText(week.getWeekDetails());

        return weekView;

    }
}
