package com.binaryic.binarysmartview.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;

import com.binaryic.binarysmartview.Fragments.DayFragment;
import com.binaryic.binarysmartview.Fragments.MonthFragment;
import com.binaryic.binarysmartview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 25-05-2016.
 */
public class testactivity extends AppCompatActivity {

    ViewPager viewpager;
    TabLayout tabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tabs = (TabLayout) findViewById(R.id.tabs);
        SetFragment();
    }
    private void SetFragment(){
        try{
            if (viewpager != null) {
                Adapter adapter = new Adapter(getSupportFragmentManager());
                adapter.addFragment(new MonthFragment(), "Month");
                adapter.addFragment(new DayFragment(), "Day");
                viewpager.setAdapter(adapter);
            }
            tabs.setupWithViewPager(viewpager);
        }catch (Exception ex){}
    }
    public class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
