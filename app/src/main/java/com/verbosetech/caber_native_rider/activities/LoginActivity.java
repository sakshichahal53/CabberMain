package com.verbosetech.caber_native_rider.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.verbosetech.caber_native_rider.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.MontserratBoldTextAppearance);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        TextView tv_fgot_pass = findViewById(R.id.tv_forgot_password);
        TextView tv_nav_signup = findViewById(R.id.tv_nav_signup);

        Spannable forgot = new SpannableString(getString(R.string.forgot_password));
        forgot.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), forgot.toString().indexOf("Re"), forgot.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_fgot_pass.setText(forgot);


        Spannable new_user = new SpannableString(getString(R.string.new_user_sign_up));
        new_user.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), new_user.toString().indexOf("Sign"), new_user.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_nav_signup.setText(new_user);

        Button btn_continuew_login = findViewById(R.id.btn_logged_in);

        tv_nav_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btn_continuew_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, WhereToGoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
