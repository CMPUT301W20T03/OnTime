package com.example.ontime;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;

import javax.xml.validation.Validator;

public class SignupActivity extends AppCompatActivity {


    String TAG = "Sample";
    Button signUpButton;

    EditText addUsernameEditText;
    EditText addPasswordEditText;
    EditText addEmailEditText;
    EditText addPhoneNumberEditText;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        //validator = new Validator(this);
        //validator.setValidationListener(this);



        signUpButton = findViewById(R.id.signup_button);
        addUsernameEditText = findViewById(R.id.signup_name_text);
        addPasswordEditText = findViewById(R.id.signup_password_text);

        addEmailEditText = findViewById(R.id.signup_email_text);
        //addEmailEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        addPhoneNumberEditText= findViewById(R.id.signup_phonenumber_text);

        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Users");


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String usernameText = addUsernameEditText.getText().toString();
                final String passwordText = addPasswordEditText.getText().toString();
                final String emailText = addEmailEditText.getText().toString();
                final String phone_numberText = addPhoneNumberEditText.getText().toString();
                HashMap<String, String> data = new HashMap<>();
                if(usernameText.length()==0){
                    Toast.makeText(SignupActivity.this, "Please enter your username", Toast.LENGTH_LONG).show();
                }
                else if(emailText.length()==0 || emailText.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*") ){
                    Toast.makeText(SignupActivity.this, "Please valid Email Address", Toast.LENGTH_LONG).show();
                }
                else if(phone_numberText.length()==0 || phone_numberText.length()>10|| phone_numberText.length()<10){
                    Toast.makeText(SignupActivity.this, "Please enter valid Phone Number", Toast.LENGTH_LONG).show();
                }
                else if(passwordText.length()==0){
                    Toast.makeText(SignupActivity.this, "Please Set your Password", Toast.LENGTH_LONG).show();
                }
                else if(usernameText.length()>0 && passwordText.length()>0 && emailText.length()>0 && phone_numberText.length()>0){
                    data.put("password",passwordText);
                    data.put("email",emailText);
                    data.put("phone number",phone_numberText);
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