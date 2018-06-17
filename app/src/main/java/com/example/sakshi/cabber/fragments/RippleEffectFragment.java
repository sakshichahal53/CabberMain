package com.example.sakshi.cabber.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sakshi.cabber.R;
import com.skyfishjy.library.RippleBackground;

/**
 * Created by sakshi on 16/6/18.
 */

public class RippleEffectFragment extends Fragment {

    ImageView image_view;
    RippleBackground ripple_background;

    public RippleEffectFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ripple_effect, container, false);
        ripple_background = view.findViewById(R.id.content);
        ripple_background.startRippleAnimation();

        image_view = view.findViewById(R.id.centerImage);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ripple_background.stopRippleAnimation();
                android.app.FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.map_fragment, new CompleteRideInfoFragment(), "Ride Info");

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }
}
