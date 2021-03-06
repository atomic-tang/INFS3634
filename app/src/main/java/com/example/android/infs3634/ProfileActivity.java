package com.example.android.infs3634;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

// Activity for logged-in users to view their profiles
public class ProfileActivity extends AppCompatActivity {

    public ArrayList<String> badges = new ArrayList<String>();
    ImageView profileImg;
    TextView username;
    Button changeProfileBtn;
    Button logoutBtn;
    GridView gridView;
    final int ACTIVITY_SELECT_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");

        profileImg = findViewById(R.id.profileImg);
        username = findViewById(R.id.username);
        changeProfileBtn = findViewById(R.id.changeProfileBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        gridView = findViewById(R.id.badgeGridView);

        // Render profile activity with logged-in user's profile image, full name and badges achieved
        DataService.instance.getProfile(this, profileImg, username);
        DataService.instance.getBadges(this, this);
        ListAdapter adapter = new BadgeAdapter(this, badges);
        gridView.setAdapter(adapter);

        // On-click listener to access photos for profile image
        changeProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
            }
        });

        // On-click listener to logout of app
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_SELECT_IMAGE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                // Upload selected photo onto firebase storage and update profile image
                DataService.instance.uploadImage(uri, DataService.instance.getStudentId(), this);
            }
        }
    }
}
