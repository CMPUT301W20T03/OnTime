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
    void testUploadPhoto() {
        driver.uploadPhoto(null);
    }
}
