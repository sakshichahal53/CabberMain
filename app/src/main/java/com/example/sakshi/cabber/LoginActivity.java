package com.example.sakshi.cabber;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.example.sakshi.cabber.R;
import com.google.android.gms.common.SignInButton;

public class LoginActivity extends AppCompatActivity {

    private SignInButton btn_google_signin;
    TextView tv_nav_signup,tv_fgot_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//
          tv_fgot_pass=findViewById(R.id.tv_forgot_password);
          tv_nav_signup=findViewById(R.id.tv_nav_signup);

        Spannable forgot = new SpannableString("Forgot ");
        forgot.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_Color)), 0, forgot.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_fgot_pass.setText(forgot);
        Spannable password = new SpannableString("Password?");

        password.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, password.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_fgot_pass.append(password);


        Spannable new_user = new SpannableString("New user? ");
        new_user.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_Color)), 0, new_user.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_nav_signup.setText(forgot);
        Spannable signup = new SpannableString("Sign up");

        signup.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, signup.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_nav_signup.append(password);
    }
}
