package com.example.sakshi.cabber.activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.sakshi.cabber.helpers.CustomStatusBar;
import com.example.sakshi.cabber.R;

public class ProfileActivity extends AppCompatActivity {

    View statusbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        statusbar = findViewById(R.id.statusBarBackground_profile);
        CustomStatusBar customStatusBar = new CustomStatusBar(ProfileActivity.this, this);
        customStatusBar.setStatusBarColor(statusbar, getResources().getColor(R.color.transparent_black));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
