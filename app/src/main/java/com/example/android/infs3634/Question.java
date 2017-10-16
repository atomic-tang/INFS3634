package com.example.android.infs3634;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class Question {

    private String questionId;
    private String question;
    private ArrayList<String> options;
    private int answer;

    Question (DataSnapshot snapshot) {
        questionId = snapshot.getKey().toString();
        question = snapshot.child("question").getValue().toString();
        for (DataSnapshot snap: snapshot.child("options").getChildren()) {
            options.add(snap.getValue().toString());
        }
        answer = (int) snapshot.child("answer").getValue();
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
