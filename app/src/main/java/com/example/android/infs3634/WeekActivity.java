package com.example.android.infs3634;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

// Activity to view tasks to be completed for each lesson
public class WeekActivity extends AppCompatActivity {

    TextView weekActivityTextView;
    TextView weekActivityDescTextView;
    ImageView weekActivityImageView;
    ListView listView;
    Button startWeekButton;

    ArrayList<Task> tasks = new ArrayList<Task>();
    Week week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        listView = findViewById(R.id.taskListView);
        weekActivityTextView = findViewById(R.id.weekActivityTextView);
        weekActivityDescTextView = findViewById(R.id.weekActivityDescTextView);
        weekActivityImageView = findViewById(R.id.weekActivityImageView);
        startWeekButton = findViewById(R.id.startWeekButton);

        final WeekActivity activity = this;
        final int taskIndex = 0;

        // Get tasks to be completed for selected lesson from Course Activity
        week = (Week) getIntent().getSerializableExtra("Week");
        if (week != null) {
            getSupportActionBar().setTitle(week.getWeekTopic());
            weekActivityTextView.setText(week.getWeekTopic());
            weekActivityDescTextView.setText(week.getWeekDetails());
            DataService.instance.getTask(this, this, week.getActivityKeys());
            ImageManager.manager.setIconImageView(week.getWeekId(), weekActivityImageView);
        }

        // display tasks in listview for selected lesson
        ListAdapter adapter = new TaskAdapter(this, tasks);
        listView.setAdapter(adapter);

        // On-click listener to start lesson and view first task
        startWeekButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent;
                if (tasks.get(0).getType().equalsIgnoreCase("quiz")) {
                    // if first task is a quiz create intent for Quiz Activity
                    intent = new Intent(activity, QuizActivity.class);
                } else {
                    // if first task is a video create intent for Video Activity
                    intent = new Intent(activity, VideoActivity.class);
                }
                // Pass task list to first task activity
                intent.putExtra("Tasks",tasks);
                intent.putExtra("Task Index", taskIndex);
                intent.putExtra("Total Tasks", tasks.size());
                intent.putExtra("Week", week);
                startActivity(intent);
            }
        });
    }
}
