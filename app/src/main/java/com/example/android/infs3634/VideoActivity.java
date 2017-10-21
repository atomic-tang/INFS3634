package com.example.android.infs3634;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;
import java.util.ArrayList;

// Activity to view video task in lesson
public class VideoActivity extends AppCompatActivity {

    Week week;
    TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getSupportActionBar().hide();

        // set status bar colour
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        final VideoActivity activity = this;

        Button mNextBtn = findViewById(R.id.nextBtn);
        VideoView mVideoView = findViewById(R.id.videoView);
        TextView mWeekProgress = findViewById(R.id.textView3);
        ProgressBar mProgressBar = findViewById(R.id.progressBar2);
        TextView mTitle = findViewById(R.id.title);
        TextView mDescription = findViewById(R.id.description);
        titleTextView = findViewById(R.id.videoWeekTitle);

        // Get list tasks for lesson
        final ArrayList<Task> tasks = (ArrayList<Task>) getIntent().getSerializableExtra("Tasks");
        final int taskIndex = (int) getIntent().getSerializableExtra("Task Index") + 1;
        final int totalTasks = (int) getIntent().getSerializableExtra("Total Tasks");
        week = (Week) getIntent().getSerializableExtra("Week");

        int progress = Math.round(100 * taskIndex / totalTasks);

        // Get current video from list of tasks
        Video video = (Video) tasks.get(0);
        Uri videoUri = Uri.parse(video.getUrl());

        titleTextView.setText("Week " + week.getWeekNumber() + ": " + week.getWeekTopic());
        mWeekProgress.setText(taskIndex + " / " + totalTasks);
        mProgressBar.setProgress(progress);
        mTitle.setText(video.getTitle());
        mDescription.setText(video.getDescription());

        // Set media controller for video
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);
        mVideoView.setVideoURI(videoUri);
        mVideoView.requestFocus();

        // remove current quiz from list of tasks for lesson
        tasks.remove(0);

        // Set text for button
        if (tasks.size() > 0) {
            mNextBtn.setText("Next");
        } else {
            mNextBtn.setText("Finish Lesson");
        }

        // On-click listener to proceed to next task or finish lesson
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Determine if there are more tasks left for lesson
                if (tasks.size() > 0) {
                    Intent intent;
                    if (tasks.get(0).getType().equalsIgnoreCase("quiz")) {
                        // if next task is a quiz create intent for Quiz Activity
                        intent = new Intent(activity, QuizActivity.class);
                    } else  {
                        // if next task is a video create intent for another Video Activity
                        intent = new Intent(activity, VideoActivity.class);
                    }

                    // Pass remaining task list to next task activity
                    intent.putExtra("Tasks", tasks);
                    intent.putExtra("Task Index", taskIndex);
                    intent.putExtra("Total Tasks", totalTasks);
                    intent.putExtra("Week", week);
                    startActivity(intent);
                } else {
                    // If there are no more tasks left for lesson, show result activity
                    Intent intent = new Intent(activity, ResultActivity.class);
                    intent.putExtra("Week", week);
                    startActivity(intent);
                }
            }
        });

    }
}
