package com.verbosetech.caber_native_rider.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.verbosetech.caber_native_rider.R;
import com.verbosetech.caber_native_rider.adapters.ViewPagerAdapter;
import com.verbosetech.caber_native_rider.fragments.PastTripsFragment;
import com.verbosetech.caber_native_rider.fragments.UpcomingTripsFragment;

public class MyTripsActivity extends AppCompatActivity {
    private ViewPager view_pager;
    private TabLayout tabLayout;
    private ViewPagerAdapter m_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);

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


        tabLayout = findViewById(R.id.tabLayout);
        view_pager = (ViewPager) findViewById(R.id.pager);
        m_adapter =  new ViewPagerAdapter(getSupportFragmentManager());
        m_adapter.addFragment(new PastTripsFragment(),"Past");
        m_adapter.addFragment(new UpcomingTripsFragment(),"Upcoming");
        view_pager.setAdapter(m_adapter);
        view_pager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(view_pager);

    }
}
