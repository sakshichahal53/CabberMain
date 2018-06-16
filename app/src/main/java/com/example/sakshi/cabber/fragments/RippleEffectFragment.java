package com.example.sakshi.cabber.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.sakshi.cabber.R;
import com.skyfishjy.library.RippleBackground;

import java.nio.file.attribute.GroupPrincipal;

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

        View view = inflater.inflate(R.layout.activity_ripple_effect, container, false);
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

            }
        });
    }
}
