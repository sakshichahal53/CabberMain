package com.example.sakshi.cabber.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sakshi.cabber.R;

public class FareAndRateActivity extends AppCompatActivity {

    Button btn_submit_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_and_rate);

        btn_submit_rating = findViewById(R.id.btn_submit_rating);
        btn_submit_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FareAndRateActivity.this, PaymentsActivity.class));
            }
        });
    }
}
