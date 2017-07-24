package com.binaryic.binarysmartview.Adapter;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.binaryic.binarysmartview.Activity.MainActivity;
import com.binaryic.binarysmartview.Common.MyFragment;
import com.binaryic.binarysmartview.Common.MyLinearLayout;
import com.binaryic.binarysmartview.R;

/**
 * Created by MY PC on 17-May-16.
 */
public class MyPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    private MyLinearLayout cur = null;
    private MyLinearLayout next = null;
    private MyLinearLayout previous = null;
    private MainActivity context;
    private FragmentManager fm;
    private float scale;
    int pCount = 0;

    public MyPagerAdapter(MainActivity context, FragmentManager fm) {
        super(fm);
        this.fm = fm;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // make the first pager bigger than others
        try {
            if (position == MainActivity.FIRST_PAGE)
                scale = MainActivity.BIG_SCALE;
            else
                scale = MainActivity.SMALL_SCALE;

            position = position % MainActivity.count;

        } catch (Exception e) {
            // TODO: handle exception
        }
        return MyFragment.newInstance(context, position, scale);
    }

    @Override
    public int getCount() {
        int count = 0;
        try {
            count = MainActivity.count * MainActivity.LOOPS;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        try {
            if (positionOffset >= 0f && positionOffset <= 1f) {
                cur = getRootView(position);
                next = getRootView(position + 1);
                previous = getRootView(position - 1);
                ((TextView)next.findViewById(R.id.lblName)).setTextColor(context.getResources().getColor(R.color.caldroid_333));
                ((TextView)next.findViewById(R.id.lblName)).setTypeface(null, Typeface.NORMAL);
                ((TextView)previous.findViewById(R.id.lblName)).setTextColor(context.getResources().getColor(R.color.caldroid_333));
                ((TextView)previous.findViewById(R.id.lblName)).setTypeface(null, Typeface.NORMAL);
                ((TextView)cur.findViewById(R.id.lblName)).setTextColor(context.getResources().getColor(R.color.red));
                ((TextView)cur.findViewById(R.id.lblName)).setTypeface(null, Typeface.BOLD);
                cur.setScaleBoth(MainActivity.BIG_SCALE - MainActivity.DIFF_SCALE * positionOffset);
                next.setScaleBoth(MainActivity.SMALL_SCALE + MainActivity.DIFF_SCALE * positionOffset);

                pCount++;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private MyLinearLayout getRootView(int position) {
        return (MyLinearLayout) fm.findFragmentByTag(this.getFragmentTag(position)).getView().findViewById(R.id.root);
    }

    private String getFragmentTag(int position) {
        return "android:switcher:" + context.mCoverFlow.getId() + ":" + position;
    }
}
