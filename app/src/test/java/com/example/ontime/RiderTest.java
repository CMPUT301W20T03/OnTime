package com.example.ontime;

import android.media.Image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class RiderTest {
    @Mock
    Image photo;
    @InjectMocks
    Rider rider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSetUsername() {
        rider.setUsername("username");
    }

    @Test
    void testUploadPhoto() {
        rider.uploadPhoto(null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme