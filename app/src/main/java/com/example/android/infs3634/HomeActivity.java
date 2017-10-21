package com.example.android.infs3634;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

// Homepage Activity to view courses available on application
public class HomeActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Course> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView = findViewById(R.id.courseListView);

        setCustomToolbar(this, "Home");

        final HomeActivity activity = this;

        // Get courses from firebase database
        DataService.instance.getCourses(this, this);

        // Display courses in listview
        ListAdapter adapter = new CourseAdaptor(this, courses);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Course course = courses.get(i);
                Intent intent = new Intent(activity, CourseActivity.class);
                intent.putExtra("Course", course);
                startActivity(intent);
            }
        });
    }

    // Method to change action bar with custom toolbar with profile picture
    private void setCustomToolbar(final Context context, String title) {
        // Inflate custom toolbar and set to replace action bar
        View customToolbar = getLayoutInflater().inflate(R.layout.custom_toolbar, null);
        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setCustomView(customToolbar);

        TextView activityTitle = customToolbar.findViewById(R.id.activityTitle);
        ImageView profileImg = customToolbar.findViewById(R.id.profileImgBtn);

        activityTitle.setText(title);
        DataService.instance.getProfileImg(context, profileImg);

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
