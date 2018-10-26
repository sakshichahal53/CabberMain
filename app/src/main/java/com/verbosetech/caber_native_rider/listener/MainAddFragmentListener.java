package com.verbosetech.caber_native_rider.listener;

import android.support.v4.app.Fragment;

public interface MainAddFragmentListener {
    void addFragment(Fragment fragment, boolean addToBackStack, boolean popPrevious);
}
