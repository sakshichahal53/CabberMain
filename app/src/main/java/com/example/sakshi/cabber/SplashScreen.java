package com.example.sakshi.cabber;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    private View statusbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        statusbar=findViewById(R.id.statusBarBackground);
        CustomStatusBar customStatusBar=new CustomStatusBar(SplashScreen.this,this);
        customStatusBar.setStatusBarColor(statusbar,getResources().getColor(R.color.transparent_black));


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, SignupActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }




}
