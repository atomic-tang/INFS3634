package com.example.android.infs3634;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;

public class UserSetupActivity extends AppCompatActivity {

    EditText firstNameEdit;
    EditText lastNameEdit;
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setup);

        final UserSetupActivity userSetupActivity = this;

        getSupportActionBar().setTitle("Set User");

        firstNameEdit = findViewById(R.id.firstNameEdit);
        lastNameEdit = findViewById(R.id.lastNameEdit);
        continueButton = findViewById(R.id.continuePressed);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = firstNameEdit.getText().toString();
                String last = lastNameEdit.getText().toString();

                if (first != null && first != "" & last != null & last != "") {
                    DataService.instance.createUserDetails(first, last);
                    Intent intent = new Intent(userSetupActivity, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
