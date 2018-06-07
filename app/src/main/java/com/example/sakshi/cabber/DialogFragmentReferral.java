package com.example.sakshi.cabber;

import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sakshi on 7/6/18.
 */

public class DialogFragmentReferral extends DialogFragment{



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.referral_code, container,false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getDialog().setTitle("DialogFragment Tutorial");
        }

        return rootView;
    }
}


