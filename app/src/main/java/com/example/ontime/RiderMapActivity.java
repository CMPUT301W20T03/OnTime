package com.example.ontime;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.view.LayoutInflaterCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class RiderMapActivity extends FragmentActivity implements OnMapReadyCallback {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    EditText srcLocation; ///
    EditText destination;
    Button RequestConfirmButton;
    FirebaseFirestore db;
    String TAG = "Sample";

    //Popup Window
    private PopupWindow popupWindow;
    private PopupWindow popupCover;
    private LinearLayout main;
    private ViewGroup customView;
    private ViewGroup coverView;
    private LayoutInflater layoutInflater;
    private WindowManager windowManager;
    private DisplayMetrics metrics;
    public Button hamburger_button;
    public Button profile_button;
    public Button request_button;
    public TextView show_name;
    private TextView current_user_model;
    private String userName;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        final String usernameText = getIntent().getStringExtra("username");

        RequestConfirmButton = findViewById(R.id.confirm_request_btn); ///
        srcLocation = findViewById(R.id.srcLocation);
        destination = findViewById(R.id.dstLocation);
        hamburger_button = findViewById(R.id.hamburger_button);
        initPopUpView();
        hamburger_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: shit");
                showPopUpView();
            }
        });



        db = FirebaseFirestore.getInstance();
        RequestConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CollectionReference collectionReference = db.collection("Requests");
                final String srcLocationText = srcLocation.getText().toString();
                final String destinationText = destination.getText().toString();
                HashMap<String, String> data = new HashMap<>();
                if (srcLocationText.length() == 0) {
                    Toast.makeText(RiderMapActivity.this, "Please enter your source location", Toast.LENGTH_LONG).show();
                } else if (destinationText.length() == 0) {
                    Toast.makeText(RiderMapActivity.this, "Please enter your destination", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(RiderMapActivity.this, "Request successful", Toast.LENGTH_LONG).show();
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

    public void initPopUpView(){
        layoutInflater = (LayoutInflater)RiderMapActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView = (ViewGroup)layoutInflater.inflate(R.layout.hamburger_menus, null);
        coverView = (ViewGroup)layoutInflater.inflate(R.layout.cover_layout, null);
        main = findViewById(R.id.rider_main_layout);
        windowManager = getWindowManager();
        metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
    }

    public void showPopUpView(){
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        popupCover = new PopupWindow(coverView, width, height, false);
        popupWindow = new PopupWindow(customView,(int)(width*0.7),height,true);
        profile_button=customView.findViewById(R.id.profile_button);
        request_button=customView.findViewById(R.id.current_request_button);
        show_name=customView.findViewById(R.id.show_name);
        current_user_model=customView.findViewById(R.id.current_user_model);
        findViewById(R.id.rider_main_layout).post(new Runnable() {
            @Override
            public void run() {
                popupCover.setAnimationStyle(R.style.pop_animation);
                popupWindow.setAnimationStyle(R.style.pop_animation);
                popupCover.showAtLocation(main, Gravity.LEFT,0,0);
                popupWindow.showAtLocation(main, Gravity.LEFT,0,0);
                current_user_model.setText("user model: rider");
                db = FirebaseFirestore.getInstance();
                final CollectionReference collectionReference = db.collection("Riders");
                userName = getIntent().getStringExtra("username");
                final DocumentReference user = db.collection("Rider").document(userName);
                user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        show_name.setText(userName);
                    }
                });

                profile_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(RiderMapActivity.this,RiderProfile.class);
                        RiderMapActivity.this.startActivity(intent);
                    }
                });
                coverView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        popupCover.dismiss();
                        Log.d(TAG, "onDismiss: test");
                    }
                });




            }
        });
    }




    private void fetchLastLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude()+""+currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(RiderMapActivity.this);
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("you are here");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
        googleMap.addMarker(markerOptions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }
}