package com.example.ontime;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Welcome_page extends AppCompatActivity implements Animation.AnimationListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_welcome);
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                Intent intent1=new Intent(Welcome_page.this,MainActivity.class);
                startActivity(intent1);
                Welcome_page.this.finish();
            }
        };
        timer.schedule(timerTask, 2000); //1.5秒后跳转
    }
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
