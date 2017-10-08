package com.example.android.infs3634;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public DatabaseReference getCurrentUserRef() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        return mDatabase.child("users").child(getStudentId());
    }

    public void createUserDetails(String firstName, String lastName) {
        getCurrentUserRef().child("firstName").setValue(firstName);
        getCurrentUserRef().child("lastName").setValue(lastName);
    }

}
