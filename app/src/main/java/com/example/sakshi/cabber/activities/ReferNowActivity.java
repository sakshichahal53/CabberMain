package com.example.sakshi.cabber.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.sakshi.cabber.helpers.CustomStatusBar;
import com.example.sakshi.cabber.R;

public class ReferNowActivity extends AppCompatActivity {

    View statusbar;
    ImageButton btn_back_from_refer_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_now);


        statusbar = findViewById(R.id.statusBarBackground_refernow);
        CustomStatusBar customStatusBar = new CustomStatusBar(ReferNowActivity.this, this);
        customStatusBar.setStatusBarColor(statusbar, getResources().getColor(R.color.transparent_black));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        btn_back_from_refer_ = findViewById(R.id.btn_back_from_refer);
        btn_back_from_refer_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReferNowActivity.this, WhereToGoActivity.class));
            }
        });
    }

}
