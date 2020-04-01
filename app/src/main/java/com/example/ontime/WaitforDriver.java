package com.example.ontime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class WaitforDriver extends AppCompatActivity {

    private String userName;
    FirebaseDatabase db;
    NotificationManager manager;
    Notification myNotication;
    Button CancelButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getStringExtra("username");
        setContentView(R.layout.activity_rider_map);//
        CancelButton = findViewById(R.id.finish_button);

        db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("requests");
        ref.child("Requests")
                .child(userName)
                .child("status")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Intent intent = new Intent("com.rj.notitfications.SECACTIVITY");

                        PendingIntent pendingIntent = PendingIntent.getActivity(WaitforDriver.this, 1, intent, 0);

                        Notification.Builder builder = new Notification.Builder(WaitforDriver.this);

                        builder.setAutoCancel(false);
                        builder.setTicker("this is ticker text");
                        builder.setContentTitle("WhatsApp Notification");
                        builder.setContentText("You have a new message");
                        builder.setSmallIcon(R.mipmap.ic_launcher_foreground);
                        builder.setContentIntent(pendingIntent);
                        builder.setOngoing(true);
                        builder.setSubText("This is subtext...");   //API level 16
                        builder.setNumber(100);
                        builder.build();

                        myNotication = builder.build();
                        manager.notify(11, myNotication);

                        Intent intent1=new Intent(WaitforDriver.this,MainActivity.class); // to be changed
                        intent1.putExtra("username", userName);
                        startActivity(intent1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

    });


}
}
