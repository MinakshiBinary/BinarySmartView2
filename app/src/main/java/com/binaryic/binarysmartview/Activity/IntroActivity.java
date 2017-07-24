package com.binaryic.binarysmartview.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.binaryic.binarysmartview.Adapter.BannerAdapter;
import com.binaryic.binarysmartview.Common.CirclePageIndicator;
import com.binaryic.binarysmartview.Common.CommonFunction;
import com.binaryic.binarysmartview.Common.DetailOnPageChangeListener;
import com.binaryic.binarysmartview.Controller.ReportingController;
import com.binaryic.binarysmartview.Controller.UserInfoController;
import com.binaryic.binarysmartview.R;

import java.util.ArrayList;
import java.util.List;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_MAC_ID;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_MAC;

public class IntroActivity extends AppCompatActivity {
    public static ViewPager mPager;
    private CirclePageIndicator mIndicator;
    private RelativeLayout rl_Container;
    private boolean wifiMatch = false;
    List<String> list_Images = new ArrayList<String>();
    public static FloatingActionButton fab;

    public String getMacId() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getBSSID();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ReportingController.reportingApi(IntroActivity.this, "Intro Activity", UserInfoController.getUserEmail(IntroActivity.this), UserInfoController.getUserEcode(IntroActivity.this));

        mPager = (ViewPager) findViewById(R.id.pager);
        rl_Container = (RelativeLayout) findViewById(R.id.rl_Container);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        list_Images.add("image_01");
        list_Images.add("image_02");
        list_Images.add("image_03");
        //  list_Images.add("image_04");
        DetailOnPageChangeListener detailOnPageChangeListener = new DetailOnPageChangeListener();
        mPager.setOnPageChangeListener(detailOnPageChangeListener);
        BannerAdapter mAdapter = new BannerAdapter(IntroActivity.this, list_Images);
        mPager.setAdapter(mAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursorWifi = getContentResolver().query(CONTENT_MAC, null, null, null, null);
                Log.e("IntroActivity", "cursorWifi count=" + cursorWifi.getCount());
                Log.e("COLUMN_MAC_ID", "===" + getMacId());
                if (getMacId() != null) {
                    if (cursorWifi.getCount() > 0) {
                        for (int i = 0; i < cursorWifi.getCount(); i++) {
                            cursorWifi.moveToNext();
                            if (getMacId().matches(cursorWifi.getString(cursorWifi.getColumnIndex(COLUMN_MAC_ID)))) {
                                wifiMatch = true;
                                break;
                            }
                        }
                        if (wifiMatch) {
                            startActivity(new Intent(IntroActivity.this, UserProfileActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(IntroActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                } else {
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                    finish();
                    CommonFunction.createSnackbar(rl_Container, "Please Check Your WIFI Connection");

                }
            }
        });
        if (list_Images.size() > 1) {
            mIndicator.setViewPager(mPager);

            //  mPager.setInterval(3000);
            // mPager.startAutoScroll();
        }
    }
}
