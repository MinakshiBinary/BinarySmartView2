package com.binaryic.binarysmartview.Common;

import android.support.v4.view.ViewPager;
import android.util.Log;

public class DetailOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {

        private int currentPage;

        @Override
        public void onPageSelected(int position) {
            Log.e("DetailOn","position"+position);
            currentPage = position;
        }

        public final int getCurrentPage() {
            return currentPage;
        }
    }