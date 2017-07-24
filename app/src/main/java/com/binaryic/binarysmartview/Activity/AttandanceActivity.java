package com.binaryic.binarysmartview.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.binaryic.binarysmartview.Common.MyApplication;
import com.binaryic.binarysmartview.Controller.ReportingController;
import com.binaryic.binarysmartview.Controller.UserInfoController;
import com.binaryic.binarysmartview.Fragments.DayFragment;
import com.binaryic.binarysmartview.Fragments.MonthFragment;
import com.binaryic.binarysmartview.R;
import com.flaviofaria.kenburnsview.KenBurnsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 10-05-2016.
 */
public class AttandanceActivity extends AppCompatActivity {
    KenBurnsView imageView;
    private TextView tv_Name;
    private TextView tv_Designation;
    ViewPager viewpager;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandace);
        ReportingController.reportingApi(AttandanceActivity.this, "Attandance Activity", UserInfoController.getUserEmail(AttandanceActivity.this), UserInfoController.getUserEcode(AttandanceActivity.this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        imageView = (KenBurnsView) findViewById(R.id.header_picture);
        imageView.setAlpha(100);
        imageView.setImageBitmap(MyApplication.imageCover);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        tv_Designation = (TextView) findViewById(R.id.tv_Designation);
        tabs = (TabLayout) findViewById(R.id.tabs);
        if (getIntent().hasExtra("UserName")) {
            tv_Name.setText(getIntent().getStringExtra("UserName"));
            tv_Designation.setText(getIntent().getStringExtra("UserDesignation"));
        }
        SetFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }



    private void SetFragment() {
        try {
            if (viewpager != null) {
                Adapter adapter = new Adapter(getSupportFragmentManager());
                adapter.addFragment(new MonthFragment(), "Month");
                adapter.addFragment(new DayFragment(), "Day");
                viewpager.setAdapter(adapter);
            }
            tabs.setupWithViewPager(viewpager);
        } catch (Exception ex) {
        }
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
