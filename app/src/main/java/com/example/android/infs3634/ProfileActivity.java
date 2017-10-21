package com.example.android.infs3634;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

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

        DataService.instance.getProfile(this, profileImg, username);
        DataService.instance.getBadges(this, this);

        ListAdapter adapter = new BadgeAdapter(this, badges);
        gridView.setAdapter(adapter);

        changeProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
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
                try {
                    Uri uri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(uri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), selectedImage);
                    drawable.setCircular(true);
                    DataService.instance.uploadImage(uri, DataService.instance.getStudentId(), this);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
