package com.example.ontime;

import android.app.Activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class DestroyActivityUtilTest {
    @Mock
    List<Activity> activityList;
    @InjectMocks
    DestroyActivityUtil destroyActivityUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExit() {
        destroyActivityUtil.exit();
    }
}
