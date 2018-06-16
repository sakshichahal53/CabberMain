package com.example.sakshi.cabber.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sakshi.cabber.R;

/**
 * Created by sakshi on 15/6/18.
 */

public class PastTripsFragment extends Fragment {

    public PastTripsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_past_trips, container, false);
        return rootView;

    }

}
