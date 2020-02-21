package com.example.ontime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class DriverProfile extends AppCompatActivity {
    Button editButton;

    EditText profileNameEditText;
    EditText profilePasswordEditText;
    EditText profileEmailEditText;
    EditText profilePhoneNumberEditText;

    FirebaseFirestore db;

    private String userName;
    private String userPhone;
    private String userEmail;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        editButton = findViewById(R.id.edit_button);

        profileNameEditText = findViewById(R.id.profile_name_text);
        profilePasswordEditText = findViewById(R.id.profile_password_text);
        profileEmailEditText = findViewById(R.id.profile_email_text);
        profilePhoneNumberEditText = findViewById(R.id.profile_phonenumber_text);

        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Drivers");



    }

}

