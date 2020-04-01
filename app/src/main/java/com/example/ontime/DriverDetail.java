package com.example.ontime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DriverDetail extends AppCompatActivity {
    private TextView driver_name;
    private TextView phone;
    private TextView email;
    Button backButton;
    private String driver;
    private String driverName;
    private String driverPhone;
    private String driverEmail;


    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_details);


        backButton = findViewById(R.id.back_button);
        driver_name =  findViewById(R.id.driver_name_text);
        phone = findViewById(R.id.driver_phone_text);
        email = findViewById(R.id.driver_email_text);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DriverDetail.this,OLRider_CR.class);
                startActivity(intent);
            }
        });
        db = FirebaseFirestore.getInstance();
        driver = getIntent().getStringExtra("DriverName");
        final DocumentReference user = db.collection("Drivers").document(driver);
        user.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            driverName = documentSnapshot.getString("userName");
                            driverPhone = documentSnapshot.getString("phone number");
                            driverEmail = documentSnapshot.getString("email");

                            driver_name.setText(driverName);
                            phone.setText(driverPhone);
                            email.setText(driverEmail);
                        }else{
                            Toast.makeText(DriverDetail.this,"user not exist",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DriverDetail.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
