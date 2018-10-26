package com.verbosetech.caber_native_rider.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.verbosetech.caber_native_rider.R;
import com.skyfishjy.library.RippleBackground;
import com.verbosetech.caber_native_rider.listener.MainAddFragmentListener;

/**
 * Created by sakshi on 16/6/18.
 */

public class RippleEffectFragment extends android.support.v4.app.Fragment {
    private RippleBackground ripple_background;

    private MainAddFragmentListener fragmentAddListener;
    private boolean shown;

    public RippleEffectFragment() {
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

        View view = inflater.inflate(R.layout.fragment_ripple_effect, container, false);
        ripple_background = view.findViewById(R.id.content);
        ripple_background.startRippleAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showRideInfo();
            }
        }, 2000);
        view.findViewById(R.id.centerImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRideInfo();
            }
        });
        return view;
    }

    private void showRideInfo() {
        if (!shown) {
            ripple_background.stopRippleAnimation();
            if (fragmentAddListener != null) {
                fragmentAddListener.addFragment(new CompleteRideInfoFragment(), true, true);
            }
            shown = true;
        }
    }
}
