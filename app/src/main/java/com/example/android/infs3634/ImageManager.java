package com.example.android.infs3634;

import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;

public class ImageManager {

    String INTRO_TO_JAVA = "INFS1609Week1";
    String ELEMENTARY_PROGRAMMING = "INFS1609Week2";
    String SELECTIONS = "INFS1609Week3";
    String LOOPS = "INFS1609Week4";
    String METHODS = "INFS1609Week5";
    String ARRAYS = "INFS1609Week6";
    String OBJECTS = "INFS1609Week7";
    String JAVA_FUNDAMENTALS = "INFS2605Week1";
    String INHERITANCE = "INFS2605Week2";
    String POLYMORPHISM = "INFS2605Week3";
    String JAVAFX = "INFS2605Week4";
    String EVENT_DRIVEN_PROGRAMMING = "INFS2605Week5";
    String DATABASE = "INFS2605Week6";
    String HCI = "INFS2605Week7";
    String ANDROID_FUNDAMENTALS = "INFS3634Week1";
    String ACTIVITIES = "INFS3634Week2";
    String INTENTS = "INFS3634Week3";
    String JSON = "INFS3634Week4";
    String SQLLITE = "INFS3634Week5";
    String CONTENT_PROVIDERS = "INFS3634Week6";

    static ImageManager manager = new ImageManager();

    public void setIconImageView(String weekId, ImageView imageView) {

        DataService.instance.checkCompleteLess(weekId, imageView);

        if (weekId.equals(INTRO_TO_JAVA)) {
            imageView.setBackgroundResource(R.drawable.intro_to_java_60);
        } else if (weekId.equals(ELEMENTARY_PROGRAMMING)) {
            imageView.setBackgroundResource(R.drawable.elementary_programming_60);
        } else if (weekId.equals(SELECTIONS)) {
            imageView.setBackgroundResource(R.drawable.selections_60);
        } else if (weekId.equals(LOOPS)) {
            imageView.setBackgroundResource(R.drawable.loops_60);
        } else if (weekId.equals(METHODS)) {
            imageView.setBackgroundResource(R.drawable.methods_60);
        } else if (weekId.equals(ARRAYS)) {
            imageView.setBackgroundResource(R.drawable.arrays_60);
        } else if (weekId.equals(OBJECTS)) {
            imageView.setBackgroundResource(R.drawable.objects_60);
        } else if (weekId.equals(JAVA_FUNDAMENTALS)) {
            imageView.setBackgroundResource(R.drawable.java_fundamentals_60);
        } else if (weekId.equals(INHERITANCE)) {
            imageView.setBackgroundResource(R.drawable.inheritance_60);
        } else if (weekId.equals(POLYMORPHISM)) {
            imageView.setBackgroundResource(R.drawable.polymorphism_60);
        } else if (weekId.equals(JAVAFX)) {
            imageView.setBackgroundResource(R.drawable.java_fundamentals_60);
        } else if (weekId.equals(EVENT_DRIVEN_PROGRAMMING)) {
            imageView.setBackgroundResource(R.drawable.event_driven_programming_60);
        } else if (weekId.equals(DATABASE)) {
            imageView.setBackgroundResource(R.drawable.database_60);
        } else if (weekId.equals(HCI)) {
            imageView.setBackgroundResource(R.drawable.hci_60);
        } else if (weekId.equals(ANDROID_FUNDAMENTALS)) {
            imageView.setBackgroundResource(R.drawable.android_fundamentals_60);
        } else if (weekId.equals(ACTIVITIES)) {
            imageView.setBackgroundResource(R.drawable.activities_60);
        } else if (weekId.equals(INTENTS)) {
            imageView.setBackgroundResource(R.drawable.intents_60);
        } else if (weekId.equals(JSON)) {
            imageView.setBackgroundResource(R.drawable.json_60);
        } else if (weekId.equals(SQLLITE)) {
            imageView.setBackgroundResource(R.drawable.sqllite_60);
        } else if (weekId.equals(CONTENT_PROVIDERS)) {
            imageView.setBackgroundResource(R.drawable.content_provider_60);
        }
    }

    public void setFinishIconImageView(String weekId, ImageView imageView) {

        if (weekId.equals(INTRO_TO_JAVA)) {
            imageView.setBackgroundResource(R.drawable.intro_to_jave_finished);
        } else if (weekId.equals(ELEMENTARY_PROGRAMMING)) {
            imageView.setBackgroundResource(R.drawable.elementary_programming_finished);
        } else if (weekId.equals(SELECTIONS)) {
            imageView.setBackgroundResource(R.drawable.selections_finished);
        } else if (weekId.equals(LOOPS)) {
            imageView.setBackgroundResource(R.drawable.loops_finished);
        } else if (weekId.equals(METHODS)) {
            imageView.setBackgroundResource(R.drawable.methods_finished);
        } else if (weekId.equals(ARRAYS)) {
            imageView.setBackgroundResource(R.drawable.arrays_finished);
        } else if (weekId.equals(OBJECTS)) {
            imageView.setBackgroundResource(R.drawable.objects_finished);
        } else if (weekId.equals(JAVA_FUNDAMENTALS)) {
            imageView.setBackgroundResource(R.drawable.java_fundamentals_finished);
        } else if (weekId.equals(INHERITANCE)) {
            imageView.setBackgroundResource(R.drawable.inheritance_finished);
        } else if (weekId.equals(POLYMORPHISM)) {
            imageView.setBackgroundResource(R.drawable.polymorphism_finished);
        } else if (weekId.equals(JAVAFX)) {
            imageView.setBackgroundResource(R.drawable.java_fundamentals_finished);
        } else if (weekId.equals(EVENT_DRIVEN_PROGRAMMING)) {
            imageView.setBackgroundResource(R.drawable.event_driven_programming_finished);
        } else if (weekId.equals(DATABASE)) {
            imageView.setBackgroundResource(R.drawable.database_finished);
        } else if (weekId.equals(HCI)) {
            imageView.setBackgroundResource(R.drawable.hci_finished);
        } else if (weekId.equals(ANDROID_FUNDAMENTALS)) {
            imageView.setBackgroundResource(R.drawable.android_fundamentals_finished);
        } else if (weekId.equals(ACTIVITIES)) {
            imageView.setBackgroundResource(R.drawable.activities_finished);
        } else if (weekId.equals(INTENTS)) {
            imageView.setBackgroundResource(R.drawable.intents_finished);
        } else if (weekId.equals(JSON)) {
            imageView.setBackgroundResource(R.drawable.json_finished);
        } else if (weekId.equals(SQLLITE)) {
            imageView.setBackgroundResource(R.drawable.sqllite_finished);
        } else if (weekId.equals(CONTENT_PROVIDERS)) {
            imageView.setBackgroundResource(R.drawable.content_provider_finished);
        }
    }

    public void setResultBadge(String weekId, ImageView imageView) {
        if (weekId.equals(INTRO_TO_JAVA)) {
            imageView.setBackgroundResource(R.drawable.intro_to_java_192);
        } else if (weekId.equals(ELEMENTARY_PROGRAMMING)) {
            imageView.setBackgroundResource(R.drawable.elementary_programming_192);
        } else if (weekId.equals(SELECTIONS)) {
            imageView.setBackgroundResource(R.drawable.selections_192);
        } else if (weekId.equals(LOOPS)) {
            imageView.setBackgroundResource(R.drawable.loops_192);
        } else if (weekId.equals(METHODS)) {
            imageView.setBackgroundResource(R.drawable.methods_192);
        } else if (weekId.equals(ARRAYS)) {
            imageView.setBackgroundResource(R.drawable.arrays_192);
        } else if (weekId.equals(OBJECTS)) {
            imageView.setBackgroundResource(R.drawable.objects_192);
        } else if (weekId.equals(JAVA_FUNDAMENTALS)) {
            imageView.setBackgroundResource(R.drawable.java_fundamentals_192);
        } else if (weekId.equals(INHERITANCE)) {
            imageView.setBackgroundResource(R.drawable.inheritance_192);
        } else if (weekId.equals(POLYMORPHISM)) {
            imageView.setBackgroundResource(R.drawable.polymorphism_192);
        } else if (weekId.equals(JAVAFX)) {
            imageView.setBackgroundResource(R.drawable.java_fundamentals_192);
        } else if (weekId.equals(EVENT_DRIVEN_PROGRAMMING)) {
            imageView.setBackgroundResource(R.drawable.event_driven_programming_192);
        } else if (weekId.equals(DATABASE)) {
            imageView.setBackgroundResource(R.drawable.database_192);
        } else if (weekId.equals(HCI)) {
            imageView.setBackgroundResource(R.drawable.hci_192);
        } else if (weekId.equals(ANDROID_FUNDAMENTALS)) {
            imageView.setBackgroundResource(R.drawable.android_fundamentals_192);
        } else if (weekId.equals(ACTIVITIES)) {
            imageView.setBackgroundResource(R.drawable.activities_192);
        } else if (weekId.equals(INTENTS)) {
            imageView.setBackgroundResource(R.drawable.intents_192);
        } else if (weekId.equals(JSON)) {
            imageView.setBackgroundResource(R.drawable.json_192);
        } else if (weekId.equals(SQLLITE)) {
            imageView.setBackgroundResource(R.drawable.sqllite_192);
        } else if (weekId.equals(CONTENT_PROVIDERS)) {
            imageView.setBackgroundResource(R.drawable.content_provider_192);
        }
    }

}