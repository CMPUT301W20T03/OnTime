package com.example.ontime;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The type Qr activity.
 */
public class QrActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_qr);
        ImageView mImageView = (ImageView) findViewById(R.id.imageView);
        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap("PAY SUCCESS!!!", 1000, 1000);
        mImageView.setImageBitmap(mBitmap);



    }
}
