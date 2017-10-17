package com.example.android.infs3634;

import android.content.Context;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataService {

    static DataService instance = new DataService();

    public String getStudentId() {
        FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
        String email = mAuth.getEmail();
        String[] splitEmail = email.split("@");
        return splitEmail[0];
    }

    public DatabaseReference getBaseReference() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        return reference;
    }

    public DatabaseReference getCurrentUserRef() {
        return getBaseReference().child("users").child(getStudentId());
    }

    public DatabaseReference getLessonRef() {
        return getBaseReference().child("lessons");
    }

    public DatabaseReference getTaskRef() {
        return  getBaseReference().child("activities");
    }

    public DatabaseReference getQuestionRef() {
        return getBaseReference().child("questions");
    }

    public void createUserDetails(String firstName, String lastName) {
        getCurrentUserRef().child("firstName").setValue(firstName);
        getCurrentUserRef().child("lastName").setValue(lastName);
    }

    public void getCourses(final Context context, final HomeActivity activity) {
        getBaseReference().child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    Course course = new Course(snap);
                    activity.courses.add(course);
                }
                ListAdapter adapter = new CourseAdaptor(activity, activity.courses);
                activity.listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Error Loading Courses", Toast.LENGTH_SHORT);
            }
        });
    }

    public void getWeeks(final Context context, final CourseActivity activity, final ArrayList<String> weekIds) {
        for (int index = 0; index < weekIds.size(); index++) {
            final int finalIndex = index;
            getLessonRef().child(weekIds.get(index)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Week week = new Week(dataSnapshot);
                    activity.weeks.add(week);
                    if (finalIndex == weekIds.size() - 1) {
                        ListAdapter adapter = new WeekAdaptor(activity, activity.weeks);
                        activity.listView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(context, "Error Loading weeks", Toast.LENGTH_SHORT);
                }
            });
        }

    }

    public void getTask(final Context context, final WeekActivity activity, final ArrayList<String> taskIds) {
        for (int index = 0; index < taskIds.size(); index++) {
            final int finalIndex = index;
            getTaskRef().child(taskIds.get(index)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String type = dataSnapshot.child("type").getValue().toString();
                    if (type.equals("video")) {
                        Video video = new Video(dataSnapshot);
                        activity.tasks.add(video);
                    } else {
                        Quiz quiz = new Quiz(dataSnapshot);
                        activity.tasks.add(quiz);
                    }
                    if (finalIndex == taskIds.size() - 1) {
                        ListAdapter adapter = new TaskAdapter(activity, activity.tasks);
                        activity.listView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(context, "Error Loading tasks", Toast.LENGTH_SHORT);
                }
            });
        }
    }

    public void getQuestion(final Context context, final QuizActivity activity, final ArrayList<String> questionIds) {
        for (int index = 0; index < questionIds.size(); index++) {
            final int finalIndex = index;
            getQuestionRef().child(questionIds.get(index)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Question question = new Question(dataSnapshot);
                    activity.questions.add(question);
                    if (finalIndex == questionIds.size() - 1) {
                        activity.renderQuiz();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(context, "Error Loading tasks", Toast.LENGTH_SHORT);
                }
            });
        }
    }


}
