package com.example.android.infs3634;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class HomeActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("Home");

        final HomeActivity activity = this;

        Course course1 = new Course("Introduction to Java", "INFS1609");
        Course course2 = new Course("Advanced Java", "INFS2605");
        Course course3 = new Course("Andriod Development", "INFS3634");

        final Course[] courses = {course1, course2, course3};
        ListAdapter adapter = new CourseAdaptor(this, courses);
        listView = (ListView) findViewById(R.id.courseListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = courses[i].getCourseId();
                Intent intent = new Intent(activity, CourseActivity.class);
                intent.putExtra("Course ID", id);
                startActivity(intent);
            }
        });

    }
}
