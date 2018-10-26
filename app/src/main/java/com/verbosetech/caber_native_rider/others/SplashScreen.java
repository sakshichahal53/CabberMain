package com.verbosetech.caber_native_rider.others;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.verbosetech.caber_native_rider.R;
import com.verbosetech.caber_native_rider.activities.LoginActivity;
import com.verbosetech.caber_native_rider.activities.SignupActivity;
import com.verbosetech.caber_native_rider.helpers.CustomStatusBar;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private int ACCESS_LOCATION_STORAGE = 100;

    private View statusbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        statusbar = findViewById(R.id.statusBarBackground);
        CustomStatusBar customStatusBar = new CustomStatusBar(SplashScreen.this, this);
        customStatusBar.setStatusBarColor(statusbar, getResources().getColor(R.color.transparent_black));

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ContextCompat.checkSelfPermission(SplashScreen.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(SplashScreen.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SplashScreen.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    ACCESS_LOCATION_STORAGE);

        } else {

            runthread();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 100: {

                if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    runthread();

                } else {
                    Toast.makeText(this, "Please give app permissions in Settings for the app to work!", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    public void runthread() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

