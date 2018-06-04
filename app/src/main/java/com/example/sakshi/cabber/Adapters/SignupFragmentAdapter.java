package com.example.sakshi.cabber.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakshi on 2/6/18.
 */

public class SignupFragmentAdapter extends FragmentStatePagerAdapter {

    private LayoutInflater layoutInflater;
    private final List<Fragment> m_ragment_list = new ArrayList<>();

    public SignupFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return m_ragment_list.get(position);
    }

    @Override
    public int getCount() {
        return m_ragment_list.size();
    }

    public void add_fragment(Fragment fragment) {
        m_ragment_list.add(fragment);
    }


}