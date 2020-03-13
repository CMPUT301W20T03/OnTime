package com.example.ontime;

import android.media.Image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class DriverTest {
    @Mock
    Image photo;
    @InjectMocks
    Driver driver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSetUsername() {
        driver.setUsername("username");
    }

    @Test
    void testUploadPhoto() {
        driver.uploadPhoto(null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme