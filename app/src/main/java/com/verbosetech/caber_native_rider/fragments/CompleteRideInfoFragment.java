package com.verbosetech.caber_native_rider.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.verbosetech.caber_native_rider.R;
import com.verbosetech.caber_native_rider.activities.FareAndRateActivity;
import com.verbosetech.caber_native_rider.listener.MainAddFragmentListener;

/**
 * Created by sakshi on 16/6/18.
 */

public class CompleteRideInfoFragment extends android.support.v4.app.Fragment {

    private MainAddFragmentListener fragmentAddListener;

    public CompleteRideInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentAddListener = (MainAddFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement MainAddFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentAddListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete_rideinfo, container, false);
        view.findViewById(R.id.btn_contact_driver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FareAndRateActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
