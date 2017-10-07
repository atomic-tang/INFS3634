package com.example.android.infs3634;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PakinLertthamanon on 9/26/17.
 */

public class TaskAdapter extends ArrayAdapter<String> {

    public TaskAdapter(Context context, String[] ids) {
        super(context, R.layout.row_task, ids);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View taskView = inflater.inflate(R.layout.row_task, parent, false);

        String id = getItem(position);
        ImageView imageView = (ImageView) taskView.findViewById(R.id.taskRowImageView);
        TextView textView = (TextView) taskView.findViewById(R.id.taskRowTextView);

        //set Image
        textView.setText(id);

        return taskView;
    }
}
