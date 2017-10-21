package com.example.android.infs3634;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

// Activity to view list of lessons for each course
public class CourseActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Week> weeks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        final CourseActivity activity = this;

        // Get Lessons for selected course From Home Activity
        Course course = (Course) getIntent().getSerializableExtra("Course");
        if (course != null) {
            getSupportActionBar().setTitle(course.getCourseId());
            DataService.instance.getWeeks(this, this, course.getLessonIds());
        }

        // Display lessons in listview for selected course
        ListAdapter adapter = new WeekAdaptor(this, weeks);
        listView = findViewById(R.id.weekListView);
        listView.setAdapter(adapter);

        // On-click listener to view tasks in lesson
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
