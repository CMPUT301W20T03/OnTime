
package com.example.ontime;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


import com.example.ontime.helper.FetchURL;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static com.google.android.gms.location.places.ui.PlacePicker.*;
import com.example.ontime.helper.FetchURL;
import com.example.ontime.helper.TaskLoadedCallback;
import com.google.firebase.firestore.GeoPoint;
import com.google.firestore.v1.StructuredQuery;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class RiderMapActivity extends FragmentActivity implements OnMapReadyCallback {


    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final float DEFAULT_ZOOM = 15f;
    private Boolean mLocationPermissionsGranted = false;
    private LatLng myLastLocation;
    private ImageView mGps,mPicker;
    private Polyline currentPolyline;
    private Address address;
    private LatLng dLocation;
    private Query query;
    Button wallet_button;

    GoogleMap mMap;


    private EditText destination;
    public String destinationText;
    public String srcLocationText;
    private LatLng srcLagLng;
    private LatLng destLagLng;
    //EditText destination;
    Button RequestConfirmButton;
    FirebaseFirestore db;
    DatabaseReference reff;
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
    private String phone;
    private String email;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userName = getIntent().getStringExtra("username");
        getInfos();


        setContentView(R.layout.activity_rider_map);
        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(),"AIzaSyBW45jLYXpnPRI9rdYAQr24cMs9jvU8yDg");
        }


        RequestConfirmButton = findViewById(R.id.confirm_request_btn); ///
        //destination = findViewById(R.id.input_search);
        //destination = findViewById(R.id.dstLocation);
        hamburger_button = findViewById(R.id.hamburger_button);
        mGps = findViewById(R.id.ic_gps);
        mPicker = findViewById(R.id.ic_picker);

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
/*
        mPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(RiderMapActivity.this),PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesRepairableException:" + e.getMessage() );
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesNotAvailableException:" + e.getMessage() );
                }
            }
        });*/
        mPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDriverLocation();
            }
        });

        getLocationPermission();
        RequestConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getUrl(myLastLocation,new LatLng(address.getLatitude(), address.getLongitude()),"driving");
                new FetchURL(RiderMapActivity.this).execute(url,"driving");
            }
        });


        AutocompleteSupportFragment autocompleteFragment2 = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_src);
        autocompleteFragment2.setHint("Where are you?");
        // Specify the types of place data to return.
        assert autocompleteFragment2 != null;
        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                //destinationText = place.getAddress();
                srcLocationText = place.getName();

                //LatLng address = place.getLatLng();
                //moveCamera(address, DEFAULT_ZOOM, name);
                //System.out.print(destinationText);
                geoLocate(srcLocationText,"src");
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_dest);
        autocompleteFragment.setHint("Where are you going?");
        // Specify the types of place data to return.
        assert autocompleteFragment != null;
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                //destinationText = place.getAddress();
                destinationText = place.getName();

                //LatLng address = place.getLatLng();
                //moveCamera(address, DEFAULT_ZOOM, name);
                //System.out.print(destinationText);
                geoLocate(destinationText,"dest");
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        RequestConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                final CollectionReference collectionReference = db.collection("Requests");

                if (srcLagLng == null || destLagLng == null) {
                    Toast.makeText(RiderMapActivity.this, "invalid location", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getBaseContext(), RiderMapActivity.class));
                }
                final String srcLag = srcLagLng.toString();
                final String destLag = destLagLng.toString();
                HashMap<String, String> data = new HashMap<>();
                if (srcLocationText.length() == 0) {
                    Toast.makeText(RiderMapActivity.this, "Please enter your source location", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getBaseContext(), RiderMapActivity.class));
                } else if (destinationText.length() == 0) {
                    Toast.makeText(RiderMapActivity.this, "Please enter your destination", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getBaseContext(), RiderMapActivity.class));
                }
                data.put("srcLocationText", srcLocationText);
                data.put("destinationText", destinationText);
                data.put("srcLag", srcLag);
                data.put("destLag", destLag);
                data.put("phoneNumber", phone);
                data.put("rider", userName);
                data.put("email", email);
                data.put("status", "Active");// Active, Finish/Unfinish -> Past
                collectionReference
                        .document(userName)
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
        reff = FirebaseDatabase.getInstance().getReference().child("DriversAvailable").child(userName).child("driverL");
    }
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PLACE_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                Place place = (Place) getPlace(this, data);
//                StringBuilder stringBuilder = new StringBuilder();
//                String latitude = String.valueOf(place.getLatLng().latitude);
//                String longitude = String.valueOf(place.getLatLng().longitude);
//                stringBuilder.append(latitude);
//                stringBuilder.append(",");
//                stringBuilder.append(longitude);
//            }
//        }
//    }


    /*private void init() {
        Log.d(TAG, "init: initializing");

        destination.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });


        hideSoftKeyboard();
    }*/

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    public void getInfos() {
        db = FirebaseFirestore.getInstance();
        final DocumentReference user = db.collection("Riders").document(userName);
        user.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            phone = documentSnapshot.getString("phone number");
                            email = documentSnapshot.getString("email");
                        }
                    }
                });
    }


    private void geoLocate(String s,String locMode) {
        Log.d(TAG, "geoLocate: geolocating");
        Toast.makeText(this,"Locating...", Toast.LENGTH_SHORT).show();


        Geocoder geocoder = new Geocoder(RiderMapActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(s, 1);

            int count = 0;
            while (list.size() <= 0 && count < 10) {
                list = geocoder.getFromLocationName(s, 1);
                count++;
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
            Log.e(TAG, "geoLocate: Exception: " + e.getMessage());
        }

        if (list.size() > 0) {
            address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
            if (locMode == "src") {
                srcLagLng = new LatLng(address.getLatitude(), address.getLongitude());
            }
            else {
                destLagLng = new LatLng(address.getLatitude(), address.getLongitude());
            }

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, s);
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
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
            //getDriverLocation();
        }


    }
    private Marker mDriverMarker;
    private void getDriverLocation(){
        //final String [] DLocation;
        db = FirebaseFirestore.getInstance();

        //reff = reff.child("DriversAvailable");
        //reff = reff.child(userName);
        //reff = reff.child("driverL");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //if(dataSnapshot.exists()){
                    String lon_str = dataSnapshot.child("longitude").getValue().toString();
                    String lat_str = dataSnapshot.child("latitude").getValue().toString();
                    double locLat = 0;
                    double locLng = 0;
                    boolean flag = false;
                    if(lat_str != null){
                        locLat = Double.parseDouble(lat_str);
                    }
                    else{
                        flag = true;
                        Toast.makeText(RiderMapActivity.this, "loclat is null", Toast.LENGTH_SHORT).show();
                    }
                    if(lon_str!= null){
                        locLng = Double.parseDouble(lon_str);
                    } else{
                        flag = true;
                    }
                    if (!flag) {
                        LatLng driverLocation = new LatLng(locLat,locLng);
                        if(mDriverMarker != null){
                            mDriverMarker.remove();
                        }
                        mDriverMarker = mMap.addMarker(new MarkerOptions().position(driverLocation).title("your driver"));
                        moveCamera(driverLocation, DEFAULT_ZOOM, "driver Location");
                    }

                //}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //System.out.println(dLocation);

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

                            moveCamera(myLastLocation, DEFAULT_ZOOM, "My Location");

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(RiderMapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
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

        mapFragment.getMapAsync(RiderMapActivity.this);
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
        wallet_button=customView.findViewById(R.id.wallet_button);
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
                        //RiderMapActivity.this.startActivity(intent);
                        startActivity(intent);
                    }
                });
                wallet_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(RiderMapActivity.this,WalletActivity.class);
                        //RiderMapActivity.this.startActivity(intent);
                        startActivity(intent);
                    }
                });
//scan QR---------------------------------------------------------------------------

//scan QR---------------------------------------------------------------------------

//generate QR---------------------------------------------------------------------------

//generate QR---------------------------------------------------------------------------


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
    //scan QR---------------------------------------------------------------------------

//scan QR---------------------------------------------------------------------------

}
