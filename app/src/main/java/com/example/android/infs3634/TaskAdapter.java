package com.example.android.infs3634;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PakinLertthamanon on 9/26/17.
 */

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, R.layout.row_task, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View taskView = inflater.inflate(R.layout.row_task, parent, false);

        Task task = getItem(position);
        ImageView imageView = taskView.findViewById(R.id.taskRowImageView);
        TextView textView = taskView.findViewById(R.id.taskRowTextView);

        //set Image
        textView.setText(task.getTitle());
        if (task.getType().equals("video")) {
            imageView.setImageResource(R.drawable.icon_video);
        } else {
            imageView.setImageResource(R.drawable.icon_quiz);
        }

        return taskView;
    }
}
