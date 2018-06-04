package com.example.sakshi.cabber.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.example.sakshi.cabber.Fragments.FragmentSignupCode;
import com.example.sakshi.cabber.Fragments.FragmentSignupDetails;
import com.example.sakshi.cabber.Fragments.FragmentSignupVisa;
import com.example.sakshi.cabber.R;
import com.example.sakshi.cabber.Adapters.SignupFragmentAdapter;

public class SignupActivity extends AppCompatActivity {

    private ViewPager m_viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        m_viewpager = findViewById(R.id.viewPager);
        set_viewpager(m_viewpager);
    }

    public void set_viewpager(ViewPager viewpager) {
        SignupFragmentAdapter adapter = new SignupFragmentAdapter(getSupportFragmentManager());
        adapter.add_fragment(new FragmentSignupCode());
        adapter.add_fragment(new FragmentSignupDetails());
        adapter.add_fragment(new FragmentSignupVisa());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(m_viewpager, true);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 200, 0);
            tab.requestLayout();
        }
        viewpager.setAdapter(adapter);


    }

    public void set_nav_pager(int fragment_number) {
        m_viewpager.setCurrentItem(fragment_number);
    }
}
