package com.example.android.infs3634;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

// Data service class to handle all queries and actions on the firebase database
public class DataService {

    static DataService instance = new DataService();

    // Get the student id of logged-in user
    public String getStudentId() {
        FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
        String email = mAuth.getEmail();
        String[] splitEmail = email.split("@");
        return splitEmail[0];
    }

    // Set a reference to the firebase database
    public DatabaseReference getBaseReference() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        return reference;
    }

    // Get reference to logged-in user's details from the firebase database
    public DatabaseReference getCurrentUserRef() {
        return getBaseReference().child("users").child(getStudentId());
    }

    // Get reference to lessons stored on the firebase database
    public DatabaseReference getLessonRef() {
        return getBaseReference().child("lessons");
    }

    // Get reference to tasks stored on the firebase database
    public DatabaseReference getTaskRef() {
        return  getBaseReference().child("activities");
    }

    // Get reference to questions stored on the firebase database
    public DatabaseReference getQuestionRef() {
        return getBaseReference().child("questions");
    }

    // Create new users on firebase upon login
    public void createUserDetails(String firstName, String lastName, Uri uri, UserSetupActivity activity) {
        getCurrentUserRef().child("firstName").setValue(firstName);
        getCurrentUserRef().child("lastName").setValue(lastName);
        uploadImageOnSetup(uri, getStudentId(), activity);
    }

    // Get all courses from the firebase database and render in a listview
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

    // Get all weekly lessons from the firebase database and render in a listview
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

    // Get all video and quiz tasks for each lesson from the firebase database and render in a listview
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


    // Get all questions for each quiz task from the firebase database and render in QuizActivity
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


    // Update firebase database with the lessons completed by logged-in user
    public void completeLesson(String key) {
        getCurrentUserRef().child("completedWeeks").child(key).setValue("true");
    }

    // Decrease the opacity of completed lessons in the listview of lessons for each course
    public boolean checkCompleteLess(final String key, final ConstraintLayout layout) {
        boolean complete = false;
        getCurrentUserRef().child("completedWeeks").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String snapString = dataSnapshot.getValue() + "";
                if (snapString.equals("true")) {
                    layout.setAlpha(0.5f);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //
            }
        });
        return complete;
    }

    // Change the badge image of completed lessons in the listview of lessons for each course
    public boolean checkCompleteLess(final String key, final ImageView imageView) {
        boolean complete = false;
        getCurrentUserRef().child("completedWeeks").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String snapString = dataSnapshot.getValue() + "";
                if (snapString.equals("true")) {
                    ImageManager.manager.setFinishIconImageView(key, imageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //
            }
        });
        return complete;
    }


    // Get the profile image of logged-in user from the firebase database
    public void getProfileImg (final Context context, final ImageView profileImage) {
        getCurrentUserRef().child("profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String url = dataSnapshot.getValue().toString();
                    if (url != null) {
                        // Load image from firebase into image view
                        // https://stackoverflow.com/questions/39702304/retrieve-stored-image-from-firebase-storage
                        Picasso.with(context).load(url).into(profileImage);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Get the profile details of logged-in user from teh firebase database
    public void getProfile(final Context context, final ImageView profileImage, final TextView username) {
        getProfileImg(context, profileImage);

        getCurrentUserRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstName = dataSnapshot.child("firstName").getValue().toString();
                String lastName = dataSnapshot.child("lastName").getValue().toString();
                username.setText(firstName + " " + lastName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    // Get the lessons completed by logged-in user from the firebase database to reward badges
    public void getBadges(final Context context, final ProfileActivity activity) {
        getCurrentUserRef().child("completedWeeks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    String badge = snap.getKey().toString();
                    activity.badges.add(badge);
                }
                if (activity.badges.size() > 0) {
                    ListAdapter adapter = new BadgeAdapter(context, activity.badges);
                    activity.gridView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Upload the profile image of logged-in user on profile setup onto the firebase database
    private void uploadImageOnSetup(Uri uri, String zId, final UserSetupActivity activity) {
        if (uri != null) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference().child("images").child(zId);
            UploadTask task = storageRef.putFile(uri);

            task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    getCurrentUserRef().child("profile").setValue(downloadUrl + "");

                    Intent intent = new Intent(activity, HomeActivity.class);
                    activity.startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(activity, HomeActivity.class);
            activity.startActivity(intent);
        }
    }

    // Change the profile image of logged-in user
    public void uploadImage(Uri uri, String zId , final ProfileActivity activity) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images").child(zId);
        UploadTask task = storageRef.putFile(uri);

        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                getCurrentUserRef().child("profile").setValue(downloadUrl + "");
                Toast.makeText(activity, "Profile picture changed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
