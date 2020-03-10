package com.example.ontime;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


//import com.firebase.ui.database.FirebaseListAdapter;
//import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;


public class DriverMapActivity extends FragmentActivity implements OnMapReadyCallback {

    Location currentLocation;
    FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private static final float DEFAULT_ZOOM = 15f;
    private Boolean mLocationPermissionsGranted = false;
    private LatLng myLastLocation;
    private String userId;
    ListView requestList;
    GoogleMap mMap;
    GoogleSignInAccount account;
    LocationRequest mLocationRequest;

    //set the firebase connection for store and read the data
    FirebaseFirestore db;
    FirebaseDatabase database;
    DatabaseReference dbReference;


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

    //This part is for showing the riders requests in listView
    ListView requestsList;
    ArrayAdapter<CurrentRequests> requestsAdapter;
    ArrayList<CurrentRequests> requestsDataList;
    CurrentRequests currentRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getStringExtra("username");
        setContentView(R.layout.activity_driver_map);

        // populate request list
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Requests");
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<CurrentRequests> requestList = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                CurrentRequests currentRequest = documentSnapshot.toObject(CurrentRequests.class);
                                requestList.add(currentRequest);
                            }
                            ListView requestListView = (ListView) findViewById(R.id.request_list);
                            CRequestAdapter requestAdapter = new CRequestAdapter(DriverMapActivity.this, requestList);
                            requestListView.setAdapter(requestAdapter);
                        }
                        else {
                            Log.d(TAG, "Error getting requests", task.getException());
                        }
                    }
                });

        hamburger_button = findViewById(R.id.hamburger_button);
        initPopUpView();
        hamburger_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: It is hamburger button!");
                showPopUpView();
            }
        });
        userName = getIntent().getStringExtra("username");

        final String usernameText = getIntent().getStringExtra("username");

        getLocationPermission();
    }

    @Override
    public void onMapReady(GoogleMap googleMap)throws SecurityException{
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            //init();
        }


    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            myLastLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

                            // to do
                            db = FirebaseFirestore.getInstance();
                            final CollectionReference collectionReference = db.collection("DriversAvailable");
                            HashMap<String, String> data = new HashMap<>();
                            final String driverL = myLastLocation.toString();
                            data.put("driverL",driverL);
                            data.put("driver",userName);
                            collectionReference
                                    .document(userName)
                                    .set(data)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: location addtion successful");
                                            Toast.makeText(DriverMapActivity.this,"location successful",Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: location additon failed" + e.toString());
                                        }
                                    });

                            moveCamera(myLastLocation, DEFAULT_ZOOM, "My Location");

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(DriverMapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        //mMap.clear();

        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title("Last_dest");
            mMap.addMarker(options);
        }

        hideSoftKeyboard();


    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(DriverMapActivity.this);

    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void initPopUpView(){
        layoutInflater = (LayoutInflater)DriverMapActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView = (ViewGroup)layoutInflater.inflate(R.layout.hamburger_menus, null);
        coverView = (ViewGroup)layoutInflater.inflate(R.layout.cover_layout, null);
        main = findViewById(R.id.driver_main_layout);
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
        findViewById(R.id.driver_main_layout).post(new Runnable() {
            @Override
            public void run() {
                popupCover.setAnimationStyle(R.style.pop_animation);
                popupWindow.setAnimationStyle(R.style.pop_animation);
                popupCover.showAtLocation(main, Gravity.LEFT,0,0);
                popupWindow.showAtLocation(main, Gravity.LEFT,0,0);
                current_user_model.setText("user mode: driver");
                db = FirebaseFirestore.getInstance();
                final CollectionReference collectionReference = db.collection("Drivers");
                final DocumentReference user = db.collection("Drivers").document(userName);
                user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        show_name.setText(userName);
                    }
                });

                profile_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(DriverMapActivity.this,RiderProfile.class);
                        DriverMapActivity.this.startActivity(intent);
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

}
