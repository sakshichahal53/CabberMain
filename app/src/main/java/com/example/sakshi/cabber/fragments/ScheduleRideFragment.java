package com.example.sakshi.cabber.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.sakshi.cabber.R;

/**
 * Created by sakshi on 15/6/18.
 */

public class ScheduleRideFragment extends Fragment {

    CardView schedule_ride_card;
    FrameLayout ripple_frame_layout;
    Button btn_continue_ride;

    public ScheduleRideFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedule_ride, container, false);
        schedule_ride_card = view.findViewById(R.id.card_view_schedule_ride);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_continue_ride = view.findViewById(R.id.btn_continue_ride);
        btn_continue_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ripple_fragment, new RippleEffectFragment(), "Ride Info");
                schedule_ride_card.setVisibility(View.GONE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });
    }
}
