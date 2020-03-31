package com.example.ontime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ontime.R;

public class OLDriver_CR extends AppCompatActivity {
    TextView rider_nameTextview, destinationTextview, driver_nameTextview, srcLocationTextview, rider_phone_numberTextview;
    public SharedPreferences sharedPreferences;
    public Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_current_request);


        rider_nameTextview = findViewById(R.id.rider_name);
        driver_nameTextview = findViewById(R.id.driver_name);
        srcLocationTextview = findViewById(R.id.srcLocationTextview);
        destinationTextview = findViewById(R.id.destinationTextview);
        rider_phone_numberTextview = findViewById(R.id.rider_phone_number);
        startButton = findViewById(R.id.start_button);

        SharedPreferences sp = getSharedPreferences("current_request", Context.MODE_PRIVATE);
        String srcLocationText = sp.getString("srcLocationText", "");
        String destinationText = sp.getString("destinationText", "");
        String rider_name = sp.getString("rider", "");
        String driver_name = sp.getString("driver_name", "");
        String dirver_phone_number = sp.getString("rider_phone_number", "");

        rider_nameTextview.setText(rider_name);
        driver_nameTextview.setText(driver_name);
        rider_phone_numberTextview.setText(dirver_phone_number);
        srcLocationTextview.setText(srcLocationText);
        destinationTextview.setText(destinationText);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OLDriver_CR.this, InProcessActivity.class);
                startActivity(intent);
            }
        });

    }
}
