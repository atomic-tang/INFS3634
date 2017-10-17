package com.example.android.infs3634;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class Question {

    private String questionId;
    private String question;
    private ArrayList<String> options;
    private int answer;

    Question (DataSnapshot snapshot) {
        String questionKey = snapshot.getKey().toString();
        if (questionKey != null) {
            questionId = questionKey;
        } else {
            questionId = "";
        }

        String questionText = snapshot.child("question").getValue().toString();
        if (questionText != null) {
            question = snapshot.child("question").getValue().toString();
        } else {
            question = "";
        }

        ArrayList<String> strings = new ArrayList<>();
        for (DataSnapshot snap: snapshot.child("options").getChildren()) {
            strings.add(snap.getValue().toString());
        }
        options = strings;

        String answerValue = snapshot.child("answer").getValue().toString();
        if (answerValue != null) {
            answer = Integer.parseInt(answerValue);
        } else {
            answer = 0;
        }

    }

    public String getQuestionKey() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public int getAnswer() {
        return answer;
    }
}
