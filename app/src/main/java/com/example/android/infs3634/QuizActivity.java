package com.example.android.infs3634;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    TextView lessonTextView;
    TextView progressTextView;
    ProgressBar progressBar;
    TextView questionTextView;
    Button aButton;
    Button bButton;
    Button cButton;
    Button dButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        getSupportActionBar().hide();

    }
}
