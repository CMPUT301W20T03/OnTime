package com.example.ontime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OLRider_CR extends AppCompatActivity {


    TextView rider_nameTextview,destinationTextview,driver_nameTextview,srcLocationTextview,driver_phone_numberTextview;
    Button cancelButton;
    Button finishButton;
    Button driverDetail;
    FirebaseFirestore db;
    private String userName;
    private String driverName;
    private String rStatus;
    private String TAG = "ActiveRequestDetail";
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_current_request);
        userName = getIntent().getStringExtra("username");

        rider_nameTextview = findViewById(R.id.rider_name);
        driver_nameTextview = findViewById(R.id.driver_name);
        srcLocationTextview = findViewById(R.id.srcLocationTextview);
        destinationTextview = findViewById(R.id.destinationTextview);
        driver_phone_numberTextview = findViewById(R.id.dirver_phone_number);
        cancelButton = findViewById(R.id.cancel_button);
        finishButton = findViewById(R.id.finish_button3);
        driverDetail = findViewById(R.id.detail_button);

        SharedPreferences sp = getSharedPreferences("current_request", Context.MODE_PRIVATE);
        String srcLocationText= sp.getString("srcLocationText","");
        String destinationText= sp.getString("destinationText","");
        final String rider_name= sp.getString("rider","");
//        final String driver_name= sp.getString("driver_name","");
//        String driver_phone_number= sp.getString("driver_phone_number","");

        db = FirebaseFirestore.getInstance();
        final DocumentReference user = db.collection("Requests").document(rider_name);
        user.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            driverName = documentSnapshot.getString("driver");
                            System.out.printf(driverName);
                            driver_nameTextview .setText(driverName);
                        }else{
                            Toast.makeText(OLRider_CR.this,"user not exist",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OLRider_CR.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });

        rider_nameTextview.setText(rider_name);
//        driver_nameTextview.setText(driver_name);
//        driver_phone_numberTextview.setText(driver_phone_number);
        srcLocationTextview.setText(srcLocationText);
        destinationTextview.setText(destinationText);

        driverDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OLRider_CR.this,DriverDetail.class);
                intent.putExtra("DriverName",driverName);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                final CollectionReference collectionReference = db.collection("Requests");
                Map<String, Object> data = new HashMap<>();
                data.put("status","Canceled");

                collectionReference
                        .document(rider_name)
                        .update(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Data modification successful");
                                // after accepting the request ...
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Data modification failed" + e.toString());
                            }
                        });
                Intent intent=new Intent(OLRider_CR.this,RiderMapActivity.class);
                startActivity(intent);

            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db1;
                db1 = FirebaseFirestore.getInstance();
                final DocumentReference user = db1.collection("Requests").document(userName);
                user.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    rStatus = documentSnapshot.getString("status");
                                    if (rStatus.equals("Completed")) {
                                        Intent intent1 = new Intent(OLRider_CR.this, WalletActivity.class);
                                        intent1.putExtra("username", userName);
                                        startActivity(intent1);
                                    }
                                    else {
                                        Toast.makeText(OLRider_CR.this, "Completing your ride . . .", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Data retrieve failed");
                            }
                        });
            }
        });
    }
}


