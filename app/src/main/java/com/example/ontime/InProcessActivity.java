package com.example.ontime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class InProcessActivity extends AppCompatActivity {
    public Button finishButton;
    FirebaseFirestore db;
    private String TAG = "driverInProcess";
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_progress);
        userName = getIntent().getStringExtra("username"); // rider name

        finishButton = findViewById(R.id.finish_button);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                final CollectionReference collectionReference = db.collection("Requests");
                Map<String, Object> data = new HashMap<>();
                data.put("status","Completed");

                collectionReference
                        .document(userName) // rider name
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
                Intent intent=new Intent(InProcessActivity.this, WalletActivity.class);
                startActivity(intent);
            }
        });

        
    }
}
