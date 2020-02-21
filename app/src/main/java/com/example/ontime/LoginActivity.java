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
import android.widget.RadioButton;
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
    RadioButton rider_choice;
    RadioButton driver_choice;
    int rider_choice_tag=0;
    int driver_choice_tag=0;

    private Button mBtnLogin;
    private EditText et_username,et_psw;
    private String userName,pswd;

    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mBtnLogin = findViewById(R.id.login_button);
        et_username= findViewById(R.id.login_name_text);
        et_psw = findViewById(R.id.login_password_text);

        db = FirebaseFirestore.getInstance();
        rider_choice = findViewById(R.id.rider);
        driver_choice = findViewById(R.id.driver);


        if(rider_choice.isChecked()){
            rider_choice_tag=1;
        }else {
            rider_choice_tag=2;
        }

        rider_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rider_choice_tag==1){
                    rider_choice.setChecked(false);
                    rider_choice_tag=2;
                }else if(rider_choice_tag==2){
                    rider_choice.setChecked(true);
                    rider_choice_tag=1;
                }
            }
        });


        if(driver_choice.isChecked()){
            driver_choice_tag=1;
        }else {
            driver_choice_tag=2;
        }
        driver_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(driver_choice_tag==1){
                    driver_choice.setChecked(false);
                    driver_choice_tag=2;
                }else if(driver_choice_tag==2){
                    driver_choice.setChecked(true);
                    driver_choice_tag=1;
                }
            }
        });









        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rider_choice.isChecked()==true && driver_choice.isChecked()==false) {
                    final CollectionReference collectionReference = db.collection("Riders");
                    userName = et_username.getText().toString();
                    et_psw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pswd = et_psw.getText().toString();

                    if (TextUtils.isEmpty(userName)) {
                        Toast.makeText(LoginActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(pswd)) {
                        Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    } else {
                        Query query = collectionReference.whereEqualTo("password", pswd);
                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                        String password = documentSnapshot.getString("password");

                                        if (password.equals(pswd)) {
                                            Log.d(TAG, "User Exists");
                                            Toast.makeText(LoginActivity.this, "Username exists", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(getBaseContext(), UserMapActivity.class);
                                            intent.putExtra("username", userName);
                                            startActivity(intent);
                                            //startActivity(new Intent(LoginActivity.this, UserMapActivity.class));
                                        }
                                    }
                                }

                                if (task.getResult().size() == 0) {
                                    Log.d(TAG, "User does not exist or wrong password");
                                    Toast.makeText(LoginActivity.this, "User does not exist or wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                else if(driver_choice.isChecked()==true && rider_choice.isChecked()== false){
                    final CollectionReference collectionReference = db.collection("Drivers");
                    userName = et_username.getText().toString();
                    et_psw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pswd = et_psw.getText().toString();

                    if (TextUtils.isEmpty(userName)) {
                        Toast.makeText(LoginActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(pswd)) {
                        Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    } else {
                        Query query = collectionReference.whereEqualTo("password", pswd);
                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                        String password = documentSnapshot.getString("password");

                                        if (password.equals(pswd)) {
                                            Log.d(TAG, "User Exists");
                                            Toast.makeText(LoginActivity.this, "Username exists", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(getBaseContext(), UserMapActivity.class);
                                            intent.putExtra("username", userName);
                                            startActivity(intent);
                                        }
                                    }
                                }

                                if (task.getResult().size() == 0) {
                                    Log.d(TAG, "User does not exist or wrong password");
                                    Toast.makeText(LoginActivity.this, "User does not exist or wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                else if(driver_choice.isChecked()==true && rider_choice.isChecked()==true){
                    Toast.makeText(LoginActivity.this, "You can just choice one", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
