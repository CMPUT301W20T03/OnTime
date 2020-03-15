package com.example.ontime;

import android.app.Dialog;
import android.os.Handler;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentHostCallback;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

class AddFragmentTest {
    @Mock
    TextView username;
    @Mock
    TextView phone;
    @Mock
    TextView start;
    @Mock
    TextView end;
    @Mock
    TextView amount;
    @Mock
    TextView email;
    @Mock
    AddFragment.OnFragmentInteractionListener listener;
    @Mock
    CurrentRequests current_request;
    @Mock
    Handler mHandler;
    @Mock
    Runnable mDismissRunnable;
    @Mock
    Dialog mDialog;
    @Mock
    Object USE_DEFAULT_TRANSITION;
    //Field mSavedFragmentState of type Bundle - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    SparseArray<Parcelable> mSavedViewState;
    //Field mArguments of type Bundle - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    Fragment mTarget;
    //Field mFragmentManager of type FragmentManagerImpl - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    FragmentHostCallback mHost;
    //Field mChildFragmentManager of type FragmentManagerImpl - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    Fragment mParentFragment;
    @Mock
    ViewGroup mContainer;
    @Mock
    View mView;
    @Mock
    View mInnerView;

    @Mock
    Runnable mPostponedDurationRunnable;
    @Mock
    LayoutInflater mLayoutInflater;
    //Field mMaxState of type State - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    LifecycleRegistry mLifecycleRegistry;

    @Mock
    MutableLiveData<LifecycleOwner> mViewLifecycleOwnerLiveData;
    //Field mSavedStateRegistryController of type SavedStateRegistryController - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @InjectMocks
    AddFragment addFragment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testOnAttach() {
        addFragment.onAttach(null);
    }

    @Test
    void testNewInstance() {
        AddFragment result = AddFragment.newInstance(new CurrentRequests("name", "phone", "email", "srcLocationText", "destinationText", "amount"));
        Assertions.assertEquals(new AddFragment(), result);
    }

    @Test
    void testOnCreateDialog() {
        when(current_request.getName()).thenReturn("getNameResponse");
        when(current_request.getPhone()).thenReturn("getPhoneResponse");
        when(current_request.getEmail()).thenReturn("getEmailResponse");
        when(current_request.getSrcLocation()).thenReturn("getSrcLocationResponse");
        when(current_request.getDestination()).thenReturn("getDestinationResponse");
        when(current_request.getAmount()).thenReturn("getAmountResponse");

        Dialog result = addFragment.onCreateDialog(null);
        Assertions.assertEquals(null, result);
    }
}
