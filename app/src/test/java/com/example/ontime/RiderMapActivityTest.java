package com.example.ontime;

import android.content.SharedPreferences;
import android.location.Address;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class RiderMapActivityTest {
    @Mock
    FusedLocationProviderClient mFusedLocationProviderClient;
    //Field myLastLocation of type LatLng - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    ImageView mGps;
    @Mock
    ImageView mPicker;
    //Field currentPolyline of type Polyline - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    Address address;
    @Mock
    Query query;
    @Mock
    List<Polyline> polylineList;
    @Mock
    Button wallet_button;
    @Mock
    DecimalFormat df2;
    //Field mMap of type GoogleMap - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    EditText destination;
    //Field srcLagLng of type LatLng - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    //Field destLagLng of type LatLng - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    Button RequestConfirmButton;
    @Mock
    FirebaseFirestore db;
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
    Button current_request_button;
    @Mock
    Button logout_button;
    @Mock
    TextView show_name;
    @Mock
    TextView current_user_model;
    @Mock
    SharedPreferences sharedPreferences;
    @Mock
    TextView Amount;
    @Mock
    ArrayList<MarkerOptions> markers;
    //Field mDriverMarker of type Marker - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
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
    RiderMapActivity riderMapActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testOnCreate() {
        riderMapActivity.onCreate(null);
    }

    @Test
    void testOnTaskDone() {
        riderMapActivity.onTaskDone("values");
    }

    @Test
    void testGetInfos() {
        riderMapActivity.getInfos();
    }

    @Test
    void testOnMapReady() {
        riderMapActivity.onMapReady(null);
    }

    @Test
    void testOnRequestPermissionsResult() {
        riderMapActivity.onRequestPermissionsResult(0, new String[]{"permissions"}, new int[]{0});
    }

    @Test
    void testInitPopUpView() {
        riderMapActivity.initPopUpView();
    }

    @Test
    void testShowPopUpView() {
        riderMapActivity.showPopUpView();
    }
}

