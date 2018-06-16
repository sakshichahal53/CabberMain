package com.example.sakshi.cabber.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.example.sakshi.cabber.R;
import com.example.sakshi.cabber.adapters.MyTripsAdapter;

public class MyTripsActivity extends AppCompatActivity {


    private ViewPager view_pager;
    private MyTripsAdapter m_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);


        Toolbar toolbar = findViewById(R.id.toolbar_mytrips);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        view_pager = (ViewPager) findViewById(R.id.pager);
        m_adapter = new MyTripsAdapter(getSupportFragmentManager());
        view_pager.setAdapter(m_adapter);
        view_pager.setOffscreenPageLimit(2);


        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void go_to_past(View view) {
        view_pager.setCurrentItem(0);
    }

    public void go_to_upcoming(View view) {
        view_pager.setCurrentItem(1);
    }
}
