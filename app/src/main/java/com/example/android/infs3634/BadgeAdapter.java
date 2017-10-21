package com.example.android.infs3634;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

// Array Adapter for badges earned when users complete a lesson
public class BadgeAdapter extends ArrayAdapter<String> {
    // Constructor
    public BadgeAdapter(Context context, ArrayList<String> badges) {
        super(context, R.layout.grid_badge, badges);
    }

    // Get view template for badges earned in gridview
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate the view to show each badge earned
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View badgeView = inflater.inflate(R.layout.grid_badge, parent, false);

        // Set the badge image for the lessons completed
        String weekId = getItem(position);
        ImageView badgeImgView = badgeView.findViewById(R.id.badge);
        ImageManager.manager.setIconImageView(weekId, badgeImgView);

        return badgeView;
    }
}
