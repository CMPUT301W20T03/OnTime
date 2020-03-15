package com.example.ontime;

import android.graphics.Bitmap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class QRCodeUtilTest {

    @Test
    void testCreateQRCodeBitmap() {
        Bitmap result = QRCodeUtil.createQRCodeBitmap("content", 0, 0);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCreateQRCodeBitmap2() {
        Bitmap result = QRCodeUtil.createQRCodeBitmap("content", 0, 0, "character_set", "error_correction", "margin", 0, 0);
        Assertions.assertEquals(null, result);
    }
}

