package com.example.android.infs3634;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

// Activity to view result at the end of lesson
public class ResultActivity extends AppCompatActivity {

    ImageView resultImageView;
    Button continueButton;
    Week week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultImageView = findViewById(R.id.resultImageView);
        continueButton = findViewById(R.id.continuePressed);

        // On-click listener to finish lesson and return to lesson overview
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, WeekActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Week", week);
                startActivity(intent);
            }
        });

        // Display badge earned for lesson completion
        week = (Week) getIntent().getSerializableExtra("Week");
        if (week != null) {
            ImageManager.manager.setResultBadge(week.getWeekId(), resultImageView);
            DataService.instance.completeLesson(week.getWeekId());
        }

    }
}
