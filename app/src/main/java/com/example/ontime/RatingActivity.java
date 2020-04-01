package com.example.ontime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class RatingActivity extends AppCompatActivity {

    Button good_comment_txt;
    Button bad_comment_txt;
    String driverName;
    String userName;
    String rating_mark;
    FirebaseFirestore db;
    String TAG="0";
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating);

        good_comment_txt = findViewById(R.id.good_comment_txt);
        bad_comment_txt = findViewById(R.id.bad_comment_txt);





        good_comment_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("current_request", Context.MODE_PRIVATE);
                String rider_name= sp.getString("rider","");
                System.out.println(rider_name);


                db = FirebaseFirestore.getInstance();
                final DocumentReference user = db.collection("Requests").document(rider_name);
                user.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()){
                                    driverName = documentSnapshot.getString("driver");
                                    System.out.printf(driverName);

                                    db = FirebaseFirestore.getInstance();
                                    final DocumentReference user1 = db.collection("Drivers").document(driverName);
                                    user1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            rating_mark = documentSnapshot.getString("rating");
                                            int mark=Integer.parseInt(rating_mark);
                                            mark+=1;
                                            rating_mark=Integer.toString(mark);
                                            final CollectionReference collectionReference = db.collection("Drivers");
                                            Map<String, Object> data = new HashMap<>();

                                            data.put("rating", rating_mark);
                                            collectionReference
                                                    .document(driverName)
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
                                        }
                                    });


                                }else{
                                    Toast.makeText(RatingActivity.this,"user not exist",Toast.LENGTH_SHORT).show();
                                }
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
                SharedPreferences sp = getSharedPreferences("current_request", Context.MODE_PRIVATE);
                String rider_name= sp.getString("rider","");
                System.out.println(rider_name);


                db = FirebaseFirestore.getInstance();
                final DocumentReference user = db.collection("Requests").document(rider_name);
                user.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()){
                                    driverName = documentSnapshot.getString("driver");
                                    System.out.printf(driverName);

                                    db = FirebaseFirestore.getInstance();
                                    final DocumentReference user1 = db.collection("Drivers").document(driverName);
                                    user1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            rating_mark = documentSnapshot.getString("rating");
                                            int mark=Integer.parseInt(rating_mark);
                                            mark-=1;
                                            rating_mark=Integer.toString(mark);
                                            final CollectionReference collectionReference = db.collection("Drivers");
                                            Map<String, Object> data = new HashMap<>();

                                            data.put("rating", rating_mark);
                                            collectionReference
                                                    .document(driverName)
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
                                        }
                                    });


                                }else{
                                    Toast.makeText(RatingActivity.this,"user not exist",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



                Toast.makeText(RatingActivity.this, "Rating Success!!!!!", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(RatingActivity.this,WalletActivity.class);
                startActivity(intent);
            }
        });



    }
}
