package com.example.sakshi.cabber.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sakshi.cabber.R;
import com.example.sakshi.cabber.others.SplashScreen;

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
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        signin.setSpan(boldSpan, 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {     //for back button on actionbar
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(SignupActivity.this, SplashScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
