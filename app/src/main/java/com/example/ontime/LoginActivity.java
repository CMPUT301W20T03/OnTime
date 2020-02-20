package com.example.ontime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    String TAG = "Sample";

    Button LoginButton;
    EditText oldUserName,oldUserPassword;
    private String userName,userPassword;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        LoginButton = findViewById(R.id.login_button);
        oldUserName= findViewById(R.id.login_name_text);
        oldUserPassword = findViewById(R.id.login_password_text);

        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Users");

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = oldUserName.getText().toString();
                oldUserPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                userPassword = oldUserPassword.getText().toString();

                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(LoginActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                } else {
                    Query query = collectionReference.whereEqualTo("password", userPassword);
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                    String password = documentSnapshot.getString("password");

                                    if (password.equals(userPassword)) {
                                        Log.d(TAG, "User Exists");
                                        Toast.makeText(LoginActivity.this, "User exists", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                                    }
                                }
                            }

                            if (task.getResult().size() == 0) {
                                Log.d(TAG, "User does not exist / wrong password");
                                Toast.makeText(LoginActivity.this, "User does not exist / wrong password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
