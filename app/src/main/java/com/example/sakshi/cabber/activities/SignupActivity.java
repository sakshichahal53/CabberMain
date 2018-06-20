package com.example.sakshi.cabber;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {

    private Button btn_signup;
    private TextView tv_registered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tv_registered = findViewById(R.id.tv_registered);
        Spannable already_reg = new SpannableString("Already Registered ? ");
        already_reg.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_Color)), 0, already_reg.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_registered.setText(already_reg);

        Spannable signin = new SpannableString("Sign in");
        signin.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, signin.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_registered.append(signin);

        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, SignupCodeActivity.class));
            }
        });

    }


}
