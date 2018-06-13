package com.example.sakshi.cabber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScheduleRideActivity extends AppCompatActivity {

    Button btn_continue_ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule_ride);


        btn_continue_ride = findViewById(R.id.btn_continue_ride);
        btn_continue_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScheduleRideActivity.this, FareAndRateActivity.class));
            }
        });


    }
}
