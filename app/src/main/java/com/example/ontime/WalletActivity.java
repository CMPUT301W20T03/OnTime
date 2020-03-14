package com.example.ontime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * The type Wallet activity.
 */
public class WalletActivity extends AppCompatActivity {
    private Button scan_button;
    private Button qr_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        scan_button=findViewById(R.id.scan_button);
        qr_button=findViewById(R.id.qr_button);

        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  crete IntentIntegrator object
                IntentIntegrator intentIntegrator = new IntentIntegrator(WalletActivity.this);
                intentIntegrator.setPrompt("This is the payment interface");
                // start scan
                intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.initiateScan();
            }
        });
        qr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to generate qr activity
                Intent intent=new Intent(WalletActivity.this,QrActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Get parsing results
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancel Scan", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scan Information:" + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
