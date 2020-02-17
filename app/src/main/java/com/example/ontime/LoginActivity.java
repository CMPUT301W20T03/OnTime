package com.example.ontime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button mBtnLogin;
    private EditText et_username,et_psw;
    private String userName,psw,spPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mBtnLogin = findViewById(R.id.button_login);
        et_username= (EditText) findViewById(R.id.login_name);
        et_psw = findViewById(R.id.login_password);

        mBtnLogin.setOnClickListener(new view.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = et_username.getText().toString().trim();
                et_psw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                psw = et_psw.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(LoginActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "hi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
