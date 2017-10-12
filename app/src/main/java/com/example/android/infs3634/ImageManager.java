package com.example.android.infs3634;

import android.widget.ImageView;

/**
 * Created by PakinLertthamanon on 10/12/17.
 */

public class ImageManager {

    static ImageManager manager = new ImageManager();

    public void setImageToImageView(Lesson lesson , ImageView imageView) {
        switch (lesson) {
            case INTRO_TO_JAVA:
                imageView.setBackgroundResource(R.drawable.intro_to_java);
                break;
            case ELEMENTARY_PROGRAMMING:
                imageView.setBackgroundResource(R.drawable.elementary_programming);
                break;
            case SELECTIONS:
                imageView.setBackgroundResource(R.drawable.selections);
                break;
            case LOOPS:
                imageView.setBackgroundResource(R.drawable.loops);
                break;
            case METHODS:
                imageView.setBackgroundResource(R.drawable.methods);
                break;
            case ARRAYS:
                imageView.setBackgroundResource(R.drawable.arrays);
                break;
            case OBJECTS:
                imageView.setBackgroundResource(R.drawable.objects);
                break;
            case JAVA_FUNDAMENTALS:
                imageView.setBackgroundResource(R.drawable.java_fundamentals);
                break;
            case INHERITANCE:
                imageView.setBackgroundResource(R.drawable.inheritance);
                break;
            case POLYMORPHISM:
                imageView.setBackgroundResource(R.drawable.polymorphism);
                break;
            case JAVAFX:
                imageView.setBackgroundResource(R.drawable.javafx);
                break;
            case EVENT_DRIVEN_PROGRAMMING:
                imageView.setBackgroundResource(R.drawable.event_driven_programming);
                break;
            case DATABASE:
                imageView.setBackgroundResource(R.drawable.database);
                break;
            case HCI:
                imageView.setBackgroundResource(R.drawable.hci);
                break;
            case ANDROID_FUNDAMENTALS:
                imageView.setBackgroundResource(R.drawable.android_fundamentals);
                break;
            case ACTIVITIES:
                imageView.setBackgroundResource(R.drawable.activities);
                break;
            case INTENTS:
                imageView.setBackgroundResource(R.drawable.intents);
                break;
            case JSON:
                imageView.setBackgroundResource(R.drawable.json);
                break;
            case SQLLITE:
                imageView.setBackgroundResource(R.drawable.sqllite);
                break;
            case CONTENT_PROVIDERS:
                imageView.setBackgroundResource(R.drawable.content_provider);
                break;
        }

    }

}
