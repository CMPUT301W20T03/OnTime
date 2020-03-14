package com.example.ontime;

import android.content.Context;
import android.view.View;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

class RequestListTest {
    @Mock
    ArrayList<CurrentRequests> requests;
    @Mock
    Context context;
    @InjectMocks
    RequestList requestList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetView() {
        View result = requestList.getView(0, null, null);
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme