package com.example.ontime;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.firebase.ui.database.FirebaseListAdapter;
//import com.firebase.ui.database.FirebaseListOptions;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;

/**
 * This is a class that implements DriverMapActivity object
 */
public class DriverMapActivity extends FragmentActivity implements OnMapReadyCallback,AddFragment.OnFragmentInteractionListener {

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
    private ImageView mGps,mPoly;
    public String from_src,from_dest;
    public String src_Coor,dst_Coor;

    GoogleMap mMap;
    GoogleSignInAccount account;
    LocationRequest mLocationRequest;

    //set the firebase connection for store and read the data
    FirebaseFirestore db;
    FirebaseDatabase database;
    DatabaseReference reff;

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
    public Button wallet_button;
    public TextView show_name;
    private TextView current_user_model;
    private String userName;


    /*protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getStringExtra("username");
        setContentView(R.layout.activity_driver_map);
        mGps = findViewById(R.id.ic_gps);
        mPoly = findViewById(R.id.ic_poly);

        //createLocationRequest();

        // populate request list (active requests only)
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Requests");
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        final List<CurrentRequests> requestList = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if (documentSnapshot.getString("status").equals("Active")) {
                                    CurrentRequests currentRequest = documentSnapshot.toObject(CurrentRequests.class);
                                    requestList.add(currentRequest);
                                }
                            }
                            final ListView requestListView = (ListView) findViewById(R.id.request_list);
                            final CRequestAdapter requestAdapter = new CRequestAdapter(DriverMapActivity.this, requestList);
                            requestListView.setAdapter(requestAdapter);
                            requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    CurrentRequests requests = requestList.get(position);
                                    src_Coor = requests.getSrcCoordinate();
                                    dst_Coor = requests.getDstCoordinate();

                                    AddFragment.newInstance(requests).show(getSupportFragmentManager(), "Request");
                                }
                            });
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
        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });

        mPoly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //src_Coor =
                //dst_Coor = getIntent().getStringExtra("dstCoordinate");


                Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(src_Coor);
                while(m.find()){
                    from_src = m.group(1);
                }
                String[] srcVal = from_src.split(",");
                double src_lat = Double.parseDouble(srcVal[0]);
                double src_lon = Double.parseDouble(srcVal[1]);

                Matcher n = Pattern.compile("\\(([^)]+)\\)").matcher(dst_Coor);
                while(n.find()){
                    from_dest = n.group(1);
                }
                String[] destVal = from_dest.split(",");
                double dest_lat = Double.parseDouble(destVal[0]);
                double dest_lon = Double.parseDouble(destVal[1]);

                Polyline line = mMap.addPolyline(new PolylineOptions().add(new LatLng(src_lat,src_lon),new LatLng(dest_lat,dest_lon))
                        .width(5).color(Color.RED));
            }
        });
        userName = getIntent().getStringExtra("username");

        final String usernameText = getIntent().getStringExtra("username");

        reff = FirebaseDatabase.getInstance().getReference().child("DriversAvailable").
                child(userName).child("driverL");
        //reff.addListenerForSingleValueEvent();
        getLocationPermission();

    }

    public String getDriver() {
        return userName;
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
                            /*
                            db = FirebaseFirestore.getInstance();
                            final CollectionReference collectionReference = db.collection("DriversAvailable");
                            HashMap<String, LatLng> data = new HashMap<>();
                            final String driverL = myLastLocation.toString();
                            data.put("driverL",myLastLocation);
                            //data.put("driver",userName);
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
                                    });*/

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
        String userId = userName;//FirebaseAuth.getInstance().getCurrentUser().getUid();

        reff.child("longitude").setValue(latLng.longitude);
        reff.child("latitude").setValue(latLng.latitude);




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
        wallet_button=customView.findViewById(R.id.wallet_button);

        findViewById(R.id.driver_main_layout).post(new Runnable() {
            @Override
            public void run() {
                customView = (ViewGroup)layoutInflater.inflate(R.layout.hamburger_menus, null);
                coverView = (ViewGroup)layoutInflater.inflate(R.layout.cover_layout, null);
                popupCover.showAtLocation(main, Gravity.LEFT,0,0);
                popupWindow.showAtLocation(main, Gravity.LEFT,0,0);
                current_user_model.setText("user model: driver");

                db = FirebaseFirestore.getInstance();
                final CollectionReference collectionReference = db.collection("Drivers");
                userName = getIntent().getStringExtra("username");
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
                        Intent intent=new Intent(DriverMapActivity.this,DriverProfile.class);
                        intent.putExtra("username", userName);
                        //DriverMapActivity.this.startActivity(intent);
                        startActivity(intent);
                    }
                });
                wallet_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(DriverMapActivity.this,WalletActivity.class);
                        //RiderMapActivity.this.startActivity(intent);
                        startActivity(intent);
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

    @Override
    public void onOkPressed(RequestList new_request) {

    }
}

