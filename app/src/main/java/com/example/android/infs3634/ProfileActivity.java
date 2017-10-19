package com.example.android.infs3634;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    public ArrayList<String> badges = new ArrayList<String>();
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");

        ImageView profileImg = findViewById(R.id.profileImg);
        TextView username = findViewById(R.id.username);
        gridView = findViewById(R.id.badgeGridView);

        DataService.instance.getProfile(this, profileImg, username);
        DataService.instance.getBadges(this, this);

        ListAdapter adapter = new BadgeAdapter(this, badges);
        gridView.setAdapter(adapter);

    }
}
