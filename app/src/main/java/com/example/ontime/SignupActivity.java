package com.example.ontime;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {


    String TAG = "Sample";
    Button signUpButton;

    EditText addUsernameEditText;
    EditText addPasswordEditText;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        signUpButton = findViewById(R.id.signup_button);
        addUsernameEditText = findViewById(R.id.signup_name_text);
        addPasswordEditText = findViewById(R.id.signup_password_text);


        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Users");


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String usernameText = addUsernameEditText.getText().toString();
                final String passwordText = addPasswordEditText.getText().toString();

                HashMap<String, String> data = new HashMap<>();
                if(usernameText.length()>0 && passwordText.length()>0){
                    data.put("password",passwordText);

                    collectionReference
                            .document(usernameText)
                            .set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Data addition successful");

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Data addition failed" + e.toString());
                                }
                            });
                }
            }
        });

    }
}