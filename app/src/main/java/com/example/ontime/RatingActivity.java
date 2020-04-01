package com.example.ontime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RatingActivity extends AppCompatActivity {

    Button good_comment_txt;
    Button bad_comment_txt;
    String driver_name;
    Double rating_mark;
    FirebaseFirestore db;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating);

        good_comment_txt = findViewById(R.id.good_comment_txt);
        bad_comment_txt = findViewById(R.id.bad_comment_txt);


        SharedPreferences sp = getSharedPreferences("current_request", Context.MODE_PRIVATE);
        driver_name= sp.getString("driver_name","");


        good_comment_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CollectionReference collectionReference = db.collection("Drivers");
                final DocumentReference user = db.collection("Drivers").document(driver_name);
                user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        rating_mark = documentSnapshot.getDouble("rating");
                        rating_mark+=1;
                    }
                });
                Toast.makeText(RatingActivity.this, "Rating Success!!!!!", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(RatingActivity.this,WalletActivity.class);
                startActivity(intent);

            }
        });

        bad_comment_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CollectionReference collectionReference = db.collection("Drivers");
                final DocumentReference user = db.collection("Drivers").document(driver_name);
                user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        rating_mark = documentSnapshot.getDouble("rating");
                        rating_mark-=1;
                    }
                });
                Toast.makeText(RatingActivity.this, "Rating Success!!!!!", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(RatingActivity.this,WalletActivity.class);
                startActivity(intent);
            }
        });



}
}
