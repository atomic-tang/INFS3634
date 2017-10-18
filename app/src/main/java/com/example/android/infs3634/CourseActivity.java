package com.example.android.infs3634;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Week> weeks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        final CourseActivity activity = this;

        Course course = (Course) getIntent().getSerializableExtra("Course");
        if (course != null) {
            getSupportActionBar().setTitle(course.getCourseId());

            DataService.instance.getWeeks(this, this, course.getLessonIds());
        }

        ListAdapter adapter = new WeekAdaptor(this, weeks);
        listView = findViewById(R.id.weekListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Week week = weeks.get(i);
                Intent intent = new Intent(activity, WeekActivity.class);
                intent.putExtra("Week", week);
                startActivity(intent);
            }
        });

    }
}
