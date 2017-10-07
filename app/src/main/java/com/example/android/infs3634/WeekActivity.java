package com.example.android.infs3634;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class WeekActivity extends AppCompatActivity {

    ListView listView;
    Button startWeekButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);
        final WeekActivity activity = this;

        String[] ids = {"Introduction", "Course Outline", "Quiz"};
        ListAdapter adapter = new TaskAdapter(this, ids);
        listView = (ListView)findViewById(R.id.taskListView);
        listView.setAdapter(adapter);

        startWeekButton = (Button)findViewById(R.id.startWeekButton);
        startWeekButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, QuizActivity.class);
                startActivity(intent);
            }
        });


    }
}
