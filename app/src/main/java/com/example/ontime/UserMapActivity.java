package com.example.ontime;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UserMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText srcLocation; ///
    EditText destination;
    Button RequestConfirmButton;
    FirebaseFirestore db;
    String usernameText = getIntent().getStringExtra("username");
    String TAG = "Sample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        RequestConfirmButton = findViewById(R.id.confirm_request_btn); ///
        srcLocation = findViewById(R.id.srcLocation);
        destination = findViewById(R.id.dstLocation);
        db = FirebaseFirestore.getInstance();
        RequestConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CollectionReference collectionReference = db.collection("Requests");
                final String srcLocationText = srcLocation.getText().toString();
                final String destinationText = destination.getText().toString();
                HashMap<String, String> data = new HashMap<>();
                if (srcLocationText.length() == 0) {
                    Toast.makeText(UserMapActivity.this, "Please enter your source location", Toast.LENGTH_LONG).show();
                }
                else if (destinationText.length() == 0) {
                    Toast.makeText(UserMapActivity.this, "Please enter your destination", Toast.LENGTH_LONG).show();
                }
                data.put("srcLocation", srcLocationText);
                data.put("destination", destinationText);
                data.put("rider", usernameText);
                data.put("status", "Active");
                collectionReference
                        .document(usernameText)
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Data addition successful");
                                Toast.makeText(UserMapActivity.this, "Request successful", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Data addition failed" + e.toString());
                            }
                        });

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
