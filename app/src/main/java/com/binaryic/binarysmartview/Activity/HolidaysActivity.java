package com.binaryic.binarysmartview.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.binaryic.binarysmartview.Adapter.HolidaysAdapter;
import com.binaryic.binarysmartview.Common.MyApplication;
import com.binaryic.binarysmartview.Controller.ReportingController;
import com.binaryic.binarysmartview.Controller.UserInfoController;
import com.binaryic.binarysmartview.Models.HolidaysModel;
import com.binaryic.binarysmartview.R;
import com.flaviofaria.kenburnsview.KenBurnsView;

import java.util.ArrayList;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DATE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_HOLIDAY_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_HOLIDAY_REMAINING_DAYS;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_UPCOMING_HOLIDAYS;

public class HolidaysActivity extends AppCompatActivity {
    private RecyclerView rv_Holidays;
    KenBurnsView header_picture;
    private TextView tv_Name;
    private TextView tv_Designation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays);
        ReportingController.reportingApi(HolidaysActivity.this, "Holidays Activity", UserInfoController.getUserEmail(HolidaysActivity.this), UserInfoController.getUserEcode(HolidaysActivity.this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        tv_Designation = (TextView) findViewById(R.id.tv_Designation);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        if (getIntent().hasExtra("UserName")) {
            tv_Name.setText(getIntent().getStringExtra("UserName"));
            tv_Designation.setText(getIntent().getStringExtra("UserDesignation"));
        }
        rv_Holidays = (RecyclerView) findViewById(R.id.rv_Holidays);
        header_picture = (KenBurnsView) findViewById(R.id.header_picture);
        header_picture.setAlpha(100);
        header_picture.setImageBitmap(MyApplication.imageCover);
        rv_Holidays.setLayoutManager(new LinearLayoutManager(HolidaysActivity.this));

        addData();
    }

    private void addData() {
        ArrayList<HolidaysModel> holidaysModelArray = new ArrayList<HolidaysModel>();

        Cursor cursor = getContentResolver().query(CONTENT_UPCOMING_HOLIDAYS, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                HolidaysModel model = new HolidaysModel();
                model.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                model.setHolidayName(cursor.getString(cursor.getColumnIndex(COLUMN_HOLIDAY_NAME)));
                model.setRemainingDays(cursor.getString(cursor.getColumnIndex(COLUMN_HOLIDAY_REMAINING_DAYS)));
                model.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                holidaysModelArray.add(model);

            }
        }

        HolidaysAdapter adapter = new HolidaysAdapter(HolidaysActivity.this, holidaysModelArray);
        rv_Holidays.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
