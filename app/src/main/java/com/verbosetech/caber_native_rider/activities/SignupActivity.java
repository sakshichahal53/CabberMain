package com.verbosetech.caber_native_rider.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.verbosetech.caber_native_rider.R;
import com.verbosetech.caber_native_rider.activities.SignupCodeActivity;

public class SignupActivity extends AppCompatActivity {

    private Button btn_signup;
    private TextView tv_registered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.MontserratBoldTextAppearance);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tv_registered = findViewById(R.id.tv_registered);
        Spannable already_reg = new SpannableString(getString(R.string.old_user_sign_in));
        already_reg.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), already_reg.toString().indexOf("Sign"), already_reg.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_registered.setText(already_reg);

        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, SignupCodeActivity.class));
            }
        });

    }


}
