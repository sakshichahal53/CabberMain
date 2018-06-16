package com.example.sakshi.cabber.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sakshi.cabber.R;

/**
 * Created by sakshi on 15/6/18.
 */

public class UpcomingTripsFragment extends android.support.v4.app.Fragment {

    public UpcomingTripsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_upcoming_trips, container, false);
        return rootView;
    }
}
