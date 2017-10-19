package com.example.android.infs3634;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    MainActivity mainActivity;

    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;
    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setViews();
//        FirebaseAuth.getInstance().signOut();

        mainActivity = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            nextActivity(false);
        }
    }

    private void setViews() {

        final String TAG = "setViews";

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        progressDialog = new ProgressDialog(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = emailEditText.getText().toString().trim();
                final String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(mainActivity, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(mainActivity, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.setMessage("Logging in");
                progressDialog.show();



                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            nextActivity(false);
                        } else {
                            FirebaseAuthException exception = (FirebaseAuthException) task.getException();
                            if (exception != null) {
                                String errorCode = exception.getErrorCode();
                                if (errorCode == "ERROR_USER_NOT_FOUND") {
                                    progressDialog.setMessage("Creating user");
                                    createuser(email, password);
                                } else if (errorCode == "ERROR_INVALID_EMAIL") {
                                    progressDialog.setMessage("Invalid Email");
                                } else if (errorCode == "ERROR_WRONG_PASSWORD") {
                                    progressDialog.setMessage("Wrong password");
                                } else {
                                    progressDialog.setMessage("Please check your user and password");
                                }
                            }
                        }
                    }
                });


            }
        });
    }

    private void createuser(final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    nextActivity(true);
                } else {
                    FirebaseAuthException exception = (FirebaseAuthException) task.getException();
                    if (exception != null) {
                        String errorCode = exception.getErrorCode();
                        if (errorCode == "ERROR_WEAK_PASSWORD") {
                            progressDialog.setMessage("Weak password");
                        } else if (errorCode == "ERROR_INVALID_EMAIL") {
                            progressDialog.setMessage("Invalid Email");
                        } else if (errorCode == "ERROR_EMAIL_ALREADY_IN_USE") {
                            progressDialog.setMessage("Email already in use");
                        } else {
                            progressDialog.setMessage("Cannot create user");
                        }
                    }
                }
            }
        });
    }

    private void nextActivity(boolean newAccount) {
        Intent intent;
        if (newAccount) {
            intent = new Intent(this, UserSetupActivity.class);
        } else {
            intent = new Intent(this, HomeActivity.class);
        }
        startActivity(intent);
    }

}
