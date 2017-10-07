package com.example.android.infs3634;

/**
 * Created by PakinLertthamanon on 9/26/17.
 */

public class Week {

    private int weekNumber;
    private String weekTopic;
    private String weekDetails;

    Week(int number, String topic, String details) {
        weekNumber = number;
        weekTopic = topic;
        weekDetails = details;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public String getWeekTopic() {
        return weekTopic;
    }

    public String getWeekDetails() {
        return weekDetails;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public void setWeekTopic(String weekTopic) {
        this.weekTopic = weekTopic;
    }

    public void setWeekDetails(String weekDetails) {
        this.weekDetails = weekDetails;
    }
}
