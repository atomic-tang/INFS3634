package com.example.android.infs3634;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

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

        DataService.instance.getCourses(this, this);

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

    private void setCustomToolbar(final Context context, String title) {
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
