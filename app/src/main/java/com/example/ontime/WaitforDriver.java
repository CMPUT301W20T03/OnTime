package com.example.ontime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WaitforDriver extends AppCompatActivity {

    private String userName;
    private String TAG = "WaitForDriver";

    private String rStatus;
    FirebaseDatabase db;
    Button CancelButton;
    Button ContinueButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getStringExtra("username");
        setContentView(R.layout.in_progress2);
        CancelButton = findViewById(R.id.finish_button2);
        ContinueButton = findViewById(R.id.continue_button);

//        db = FirebaseDatabase.getInstance();
//        DatabaseReference ref = db.getReference("Requests").child(userName);
//        ref.addValueEventListener(new ValueEventListener()  {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                         for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
//                            System.out.println(userSnap.getValue(String.class));
//                            Notification.Builder builder = new Notification.Builder(getBaseContext());
//
//                            builder.setAutoCancel(true)
//                                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
//                                    .setWhen(System.currentTimeMillis())
//                                    .setSmallIcon(R.mipmap.ic_launcher_foreground)
//                                    .setContentTitle("Your request was accepted!")
//                                    .setContentText("Click here to confirm your ride");
//                            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                            manager.notify(1, builder.build());



                   // }

//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }


        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db1;
                db1 = FirebaseFirestore.getInstance();
                final CollectionReference collectionReference = db1.collection("Requests");
                Map<String, Object> data = new HashMap<>();

                data.put("status", "Cancelled");


                collectionReference
                        .document(userName)
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

                Intent intent =new Intent(WaitforDriver.this,RiderMapActivity.class);
                intent.putExtra("username", userName);
                startActivity(intent);
            }

    });

    ContinueButton.setOnClickListener(new View.OnClickListener() {
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
                                if (rStatus.equals("Accepted")) {
                                    Intent intent1 = new Intent(WaitforDriver.this, OLRider_CR.class);
                                    intent1.putExtra("username", userName);
                                    startActivity(intent1);
                                }
                                else {
                                    Toast.makeText(WaitforDriver.this, "Searching for a driver . . .", Toast.LENGTH_LONG).show();
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
