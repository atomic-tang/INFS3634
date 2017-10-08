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

/**
 * Created by PakinLertthamanon on 10/8/17.
 */

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

    public void createUserDetails(String firstName, String lastName) {
        getCurrentUserRef().child("firstName").setValue(firstName);
        getCurrentUserRef().child("lastName").setValue(lastName);
    }

    public void getCourses(final Context context, final HomeActivity activity) {
        final ArrayList<Course> courses = new ArrayList<>();
        getBaseReference().child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    Course course = new Course(snap.getKey(), snap.child("name").getValue().toString(), snap);
                    courses.add(course);
                }
                activity.courses = courses;
                ListAdapter adapter = new CourseAdaptor(activity, courses);
                activity.listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Error Loading Courses", Toast.LENGTH_SHORT);
            }
        });
    }

    public void getWeeks(Context context, CourseActivity activity, ArrayList<String> lessonIds) {
        //TODO: Load Weeks
    }

}
