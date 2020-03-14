package com.example.ontime;

import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.util.Property;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.CameraPreview;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class CustomViewfinderViewTest {
    @Mock
    LinearGradient mLinearGradient;
    //Field mPositions of type float[] - was not mocked since Mockito doesn't mock arrays
    //Field mScanLineColor of type int[] - was not mocked since Mockito doesn't mock arrays
    @Mock
    Paint paint;
    //Field resultBitmap of type Bitmap - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    List<ResultPoint> possibleResultPoints;
    @Mock
    List<ResultPoint> lastPossibleResultPoints;
    @Mock
    CameraPreview cameraPreview;
    //Field framingRect of type Rect - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    //Field previewFramingRect of type Rect - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    Property<View, Float> ALPHA;
    @Mock
    Property<View, Float> ROTATION;
    @Mock
    Property<View, Float> ROTATION_X;
    @Mock
    Property<View, Float> ROTATION_Y;
    @Mock
    Property<View, Float> SCALE_X;
    @Mock
    Property<View, Float> SCALE_Y;
    @Mock
    Property<View, Float> TRANSLATION_X;
    @Mock
    Property<View, Float> TRANSLATION_Y;
    @Mock
    Property<View, Float> TRANSLATION_Z;
    @Mock
    Property<View, Float> X;
    @Mock
    Property<View, Float> Y;
    @Mock
    Property<View, Float> Z;
    @InjectMocks
    CustomViewfinderView customViewfinderView;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testOnDraw() {
        customViewfinderView.onDraw(null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme