package com.example.ontime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OLRider_CR extends AppCompatActivity {


    TextView rider_nameTextview,destinationTextview,driver_nameTextview,srcLocationTextview,dirver_phone_numberTextview;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_current_request);




        rider_nameTextview = findViewById(R.id.rider_name);
        driver_nameTextview = findViewById(R.id.driver_name);
        srcLocationTextview = findViewById(R.id.srcLocationTextview);
        destinationTextview = findViewById(R.id.destinationTextview);
        dirver_phone_numberTextview = findViewById(R.id.dirver_phone_number);


        SharedPreferences sp = getSharedPreferences("current_request", Context.MODE_PRIVATE);
        String srcLocationText= sp.getString("srcLocationText","");
        String destinationText= sp.getString("destinationText","");
        String rider_name= sp.getString("rider","");
        String driver_name= sp.getString("driver_name","");
        String dirver_phone_number= sp.getString("dirver_phone_number","");

        rider_nameTextview.setText(rider_name);
        driver_nameTextview.setText(driver_name);
        dirver_phone_numberTextview.setText(dirver_phone_number);
        srcLocationTextview.setText(srcLocationText);
        destinationTextview.setText(destinationText);

    }
}



