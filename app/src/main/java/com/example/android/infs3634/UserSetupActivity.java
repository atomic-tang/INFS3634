package com.example.android.infs3634;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.FileNotFoundException;
import java.io.InputStream;

// Activity to set up new user
public class UserSetupActivity extends AppCompatActivity {

    EditText firstNameEdit;
    EditText lastNameEdit;
    Button continueButton;
    ImageView imageButton;
    final int ACTIVITY_SELECT_IMAGE = 1234;
    final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setup);

        final UserSetupActivity userSetupActivity = this;

        getSupportActionBar().setTitle("Set User");

        firstNameEdit = findViewById(R.id.firstNameEdit);
        lastNameEdit = findViewById(R.id.lastNameEdit);
        continueButton = findViewById(R.id.continuePressed);
        imageButton = findViewById(R.id.imageButton);

        // On-click listener to save new user details
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = firstNameEdit.getText().toString();
                String last = lastNameEdit.getText().toString();

                if (first != null && first != "" & last != null & last != "") {
                    DataService.instance.createUserDetails(first, last, uri, UserSetupActivity.this);
                }
            }
        });

        // On-click listener to set profile image
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Request permission to access photos
                // https://developer.android.com/training/permissions/requesting.html
                if (ContextCompat.checkSelfPermission(userSetupActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(userSetupActivity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                } else {
                    // If permission granted set profile picture
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
                }
            }
        });

    }

    // handle photo gallery
    // https://stackoverflow.com/questions/6016000/how-to-open-phones-gallery-through-code
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_SELECT_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    uri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(uri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imageButton.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // Method to handle permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // if permission is granted by logged-in user set profile picture
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
                }
                return;
            }
        }
    }

}
