package com.example.sakshi.cabber.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sakshi.cabber.R;

/**
 * Created by sakshi on 16/6/18.
 */

public class CompleteRideInfoFragment extends Fragment {

    public CompleteRideInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete_rideinfo, container, false);
        return view;
    }
}
