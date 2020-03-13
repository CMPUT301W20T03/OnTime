package com.example.ontime;

import android.content.res.Resources;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.collection.SimpleArrayMap;
import androidx.collection.SparseArrayCompat;
import androidx.core.app.ComponentActivity;
import androidx.fragment.app.FragmentController;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CustomCaptureActivityTest {
    @Mock
    CaptureManager mCaptureManager;
    @Mock
    DecoratedBarcodeView mBarcodeView;
    @Mock
    AppCompatDelegate mDelegate;
    @Mock
    Resources mResources;
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
    CustomCaptureActivity customCaptureActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testOnCreate() {
        customCaptureActivity.onCreate(null);
    }

    @Test
    void testOnResume() {
        customCaptureActivity.onResume();
    }

    @Test
    void testOnPause() {
        customCaptureActivity.onPause();
    }

    @Test
    void testOnDestroy() {
        customCaptureActivity.onDestroy();
    }

    @Test
    void testOnSaveInstanceState() {
        customCaptureActivity.onSaveInstanceState(null);
    }

    @Test
    void testOnRequestPermissionsResult() {
        customCaptureActivity.onRequestPermissionsResult(0, new String[]{"permissions"}, new int[]{0});
    }

    @Test
    void testOnKeyDown() {
        boolean result = customCaptureActivity.onKeyDown(0, null);
        Assertions.assertEquals(true, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme