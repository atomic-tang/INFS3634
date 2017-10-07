package com.example.android.infs3634;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CourseActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {

        } else {
            String value = extras.getString("Course ID");
            if (value != null) {
                getSupportActionBar().setTitle(value);
            }
        }

        final CourseActivity activity = this;
        Week week1 = new Week(1, "Introduction", "Introduction to Andriod");
        Week week2 = new Week(2, "Life Cycle", "Introduction to Life Cycle");
        Week week3 = new Week(3, "Activity", "Introduction to Activity");

        final Week[] weeks = {week1, week2, week3};
        ListAdapter adapter = new WeekAdaptor(this, weeks);
        listView = (ListView) findViewById(R.id.weekListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = "" + weeks[i].getWeekNumber();
                Intent intent = new Intent(activity, WeekActivity.class);
                intent.putExtra("Week ID", id);
                startActivity(intent);
            }
        });

    }
}
