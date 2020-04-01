package com.example.ontime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OLRider_CR extends AppCompatActivity {


    TextView rider_nameTextview,destinationTextview,driver_nameTextview,srcLocationTextview,driver_phone_numberTextview;
    Button cancelButton;
    FirebaseFirestore db;
    private String TAG = "ActiveRequestDetail";
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_current_request);




        rider_nameTextview = findViewById(R.id.rider_name);
        driver_nameTextview = findViewById(R.id.driver_name);
        srcLocationTextview = findViewById(R.id.srcLocationTextview);
        destinationTextview = findViewById(R.id.destinationTextview);
        driver_phone_numberTextview = findViewById(R.id.dirver_phone_number);
        cancelButton = findViewById(R.id.cancel_button);

        SharedPreferences sp = getSharedPreferences("current_request", Context.MODE_PRIVATE);
        String srcLocationText= sp.getString("srcLocationText","");
        String destinationText= sp.getString("destinationText","");
        final String rider_name= sp.getString("rider","");
        String driver_name= sp.getString("driver_name","");
        String driver_phone_number= sp.getString("driver_phone_number","");

        rider_nameTextview.setText(rider_name);
        driver_nameTextview.setText(driver_name);
        driver_phone_numberTextview.setText(driver_phone_number);
        srcLocationTextview.setText(srcLocationText);
        destinationTextview.setText(destinationText);

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
    }
}


