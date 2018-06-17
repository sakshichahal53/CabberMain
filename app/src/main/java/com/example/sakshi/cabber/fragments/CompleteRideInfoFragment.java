package com.example.sakshi.cabber.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sakshi.cabber.R;
import com.example.sakshi.cabber.activities.FareAndRateActivity;
import com.example.sakshi.cabber.activities.ReferNowActivity;

/**
 * Created by sakshi on 16/6/18.
 */

public class CompleteRideInfoFragment extends Fragment {

    Button btn_complete_ride_info;

    public CompleteRideInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete_rideinfo, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_complete_ride_info = view.findViewById(R.id.btn_contact_driver);
        btn_complete_ride_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), FareAndRateActivity.class);
                startActivity(intent);
            }
        });
    }
}
