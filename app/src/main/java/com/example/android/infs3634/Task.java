package com.example.android.infs3634;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by PakinLertthamanon on 10/9/17.
 */

public class Task {

    protected String taskKey;
    protected String title;
    protected String type;
    protected DataSnapshot snapshot;

    public String getTitle() {
        return title;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public String getType() {
        return type;
    }

    public DataSnapshot getSnapshot() {
        return snapshot;
    }
}
