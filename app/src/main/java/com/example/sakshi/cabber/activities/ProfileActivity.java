package com.example.sakshi.cabber.activities;

import android.content.Intent;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.sakshi.cabber.helpers.CustomStatusBar;
import com.example.sakshi.cabber.R;

public class ProfileActivity extends AppCompatActivity {

    View statusbar;
    ImageButton btn_back_from_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btn_back_from_profile = findViewById(R.id.btn_back_from_profile);

        statusbar = findViewById(R.id.statusBarBackground_profile);
        CustomStatusBar customStatusBar = new CustomStatusBar(ProfileActivity.this, this);
        customStatusBar.setStatusBarColor(statusbar, getResources().getColor(R.color.transparent_black));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        btn_back_from_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, WhereToGoActivity.class));
            }
        });
    }
}
