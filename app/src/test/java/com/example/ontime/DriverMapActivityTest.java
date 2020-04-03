package com.example.ontime;

import android.content.SharedPreferences;
import android.location.Location;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.collection.SimpleArrayMap;
import androidx.collection.SparseArrayCompat;
import androidx.core.app.ComponentActivity;
import androidx.fragment.app.FragmentController;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

class DriverMapActivityTest {
    @Mock
    Location currentLocation;
    @Mock
    FusedLocationProviderClient mFusedLocationProviderClient;
    //Field myLastLocation of type LatLng - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    ImageView mGps;
    @Mock
    ImageView mPoly;
    //Field mMap of type GoogleMap - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    GoogleSignInAccount account;
    //Field mLocationRequest of type LocationRequest - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    FirebaseFirestore db;
    @Mock
    FirebaseDatabase database;
    @Mock
    DatabaseReference reff;
    @Mock
    PopupWindow popupWindow;
    @Mock
    PopupWindow popupCover;
    @Mock
    LinearLayout main;
    @Mock
    ViewGroup customView;
    @Mock
    ViewGroup coverView;
    @Mock
    LayoutInflater layoutInflater;
    @Mock
    WindowManager windowManager;
    @Mock
    DisplayMetrics metrics;
    @Mock
    Button hamburger_button;
    @Mock
    Button profile_button;
    @Mock
    Button request_button;
    @Mock
    Button wallet_button;
    @Mock
    Button current_request_button;
    @Mock
    TextView show_name;
    @Mock
    TextView current_user_model;
    @Mock
    Button logout_button;
    @Mock
    SharedPreferences sharedPreferences;
    @Mock
    FragmentController mFragments;
    @Mock
    LifecycleRegistry mFragmentLifecycleRegistry;
    @Mock
    SparseArrayCompat<String> mPendingFragmentActivityResults;
    @Mock
    LifecycleRegistry mLifecycleRegistry;
    //Field mSavedStateRegistryController of type SavedStateRegistryController - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    ViewModelStore mViewModelStore;
    //Field mOnBackPressedDispatcher of type OnBackPressedDispatcher - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    SimpleArrayMap<Class<? extends ComponentActivity.ExtraData>, ComponentActivity.ExtraData> mExtraDataMap;

    @InjectMocks
    DriverMapActivity driverMapActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testOnCreate() {
        driverMapActivity.onCreate(null);
    }

    @Test
    void testOnMapReady() {
        driverMapActivity.onMapReady(null);
    }

    @Test
    void testOnRequestPermissionsResult() {
        driverMapActivity.onRequestPermissionsResult(0, new String[]{"permissions"}, new int[]{0});
    }

    @Test
    void testInitPopUpView() {
        driverMapActivity.initPopUpView();
    }

    @Test
    void testShowPopUpView() {
        driverMapActivity.showPopUpView();
    }

    @Test
    void testOnOkPressed() {
        driverMapActivity.onOkPressed(new RequestList(null, new ArrayList<CurrentRequests>(Arrays.asList(new CurrentRequests("name", "phone", "email", "srcLocationText", "destinationText", "amount", "status")))));
    }
}

