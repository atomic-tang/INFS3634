package com.example.android.infs3634;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        final VideoActivity activity = this;

        Button mNextBtn = findViewById(R.id.nextBtn);
        VideoView mVideoView = findViewById(R.id.videoView);
        TextView mWeekProgress = findViewById(R.id.textView3);
        ProgressBar mProgressBar = findViewById(R.id.progressBar2);
        TextView mTitle = findViewById(R.id.title);
        TextView mDescription = findViewById(R.id.description);

        ArrayList<Task> tasks = (ArrayList<Task>) getIntent().getSerializableExtra("Tasks");
        int taskIndex = (int) getIntent().getSerializableExtra("Task Index") + 1;
        int totalTasks = (int) getIntent().getSerializableExtra("Total Tasks");
        int progress = Math.round(100 * taskIndex / totalTasks);
        Video video = (Video) tasks.get(0);
        Uri videoUri = Uri.parse(video.getUrl());

        mWeekProgress.setText(taskIndex + " / " + totalTasks);
        mProgressBar.setProgress(progress);
        mTitle.setText(video.getTitle());
        mDescription.setText(video.getDescription());

        MediaController mediaController = new MediaController(this);
        mVideoView.setMediaController(mediaController);
        mVideoView.setVideoURI(videoUri);
        mVideoView.requestFocus();
        mVideoView.start();

        tasks.remove(0);
        final ArrayList<Task> newTasks = tasks;

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newTasks.size() > 0) {
                    if (newTasks.get(0).getType().equalsIgnoreCase("quiz")) {
                        Intent intent = new Intent(activity, QuizActivity.class);
                        intent.putExtra("Tasks", newTasks);
                        startActivity(intent);
                    } else if (newTasks.get(0).getType().equalsIgnoreCase("video")) {
                        Intent intent = new Intent(activity, VideoActivity.class);
                        intent.putExtra("Tasks", newTasks);
                        startActivity(intent);
                    }
                } else {

                }
            }
        });

    }
}
