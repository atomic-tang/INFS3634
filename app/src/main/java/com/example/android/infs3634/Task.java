package com.example.android.infs3634;

import java.io.Serializable;

public class Task implements Serializable {

    protected String taskKey;
    protected String title;
    protected String type;

    public String getTitle() {
        return title;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public String getType() {
        return type;
    }

}
