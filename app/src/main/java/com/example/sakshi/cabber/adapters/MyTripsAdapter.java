package com.example.sakshi.cabber.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sakshi.cabber.fragments.PastTripsFragment;
import com.example.sakshi.cabber.fragments.UpcomingTripsFragment;

/**
 * Created by sakshi on 15/6/18.
 */

public class MyTripsAdapter extends FragmentPagerAdapter {

    public MyTripsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new PastTripsFragment();
            case 1:
                return new UpcomingTripsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
