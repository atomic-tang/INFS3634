package com.example.android.infs3634;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class WeekActivity extends AppCompatActivity {

    TextView weekActivityTextView;
    TextView weekActivityDescTextView;
    ListView listView;
    Button startWeekButton;

    ArrayList<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        listView = findViewById(R.id.taskListView);
        weekActivityTextView = findViewById(R.id.weekActivityTextView);
        weekActivityDescTextView = findViewById(R.id.weekActivityDescTextView);

        final WeekActivity activity = this;

        Week week = (Week) getIntent().getSerializableExtra("Week");
        if (week != null) {
            getSupportActionBar().setTitle(week.getWeekTopic());
            weekActivityTextView.setText(week.getWeekTopic());
            weekActivityDescTextView.setText(week.getWeekDetails());
            DataService.instance.getTask(this, this, week.getActivityKeys());
        }

        ListAdapter adapter = new TaskAdapter(this, tasks);
        listView.setAdapter(adapter);

        startWeekButton = findViewById(R.id.startWeekButton);
        startWeekButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, QuizActivity.class);
                startActivity(intent);
            }
        });


    }
}
