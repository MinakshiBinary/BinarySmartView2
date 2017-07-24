package com.binaryic.binarysmartview.Activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.binaryic.binarysmartview.Adapter.BirthdayAdapter;
import com.binaryic.binarysmartview.Common.MyApplication;
import com.binaryic.binarysmartview.Controller.ReportingController;
import com.binaryic.binarysmartview.Controller.UserInfoController;
import com.binaryic.binarysmartview.Models.BirthdayModel;
import com.binaryic.binarysmartview.R;
import com.flaviofaria.kenburnsview.KenBurnsView;

import java.util.ArrayList;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DATE_OF_BIRTH;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_IMAGE;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_UPCOMING_BIRTHDAYS;

@SuppressLint("SimpleDateFormat")
public class BirthdayActivity extends AppCompatActivity {
    private RecyclerView rv_Birthdays;
    private TextView tv_Name;
    private TextView tv_Designation;
    KenBurnsView header_picture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        ReportingController.reportingApi(BirthdayActivity.this, "Birthday Activity", UserInfoController.getUserEmail(BirthdayActivity.this), UserInfoController.getUserEcode(BirthdayActivity.this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        rv_Birthdays = (RecyclerView) findViewById(R.id.rv_Birthdays);
        header_picture = (KenBurnsView) findViewById(R.id.header_picture);
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        tv_Designation = (TextView) findViewById(R.id.tv_Designation);
        header_picture.setAlpha(100);
        header_picture.setImageBitmap(MyApplication.imageCover);
        rv_Birthdays.setLayoutManager(new LinearLayoutManager(BirthdayActivity.this));
        if (getIntent().hasExtra("UserName")) {
            tv_Name.setText(getIntent().getStringExtra("UserName"));
            tv_Designation.setText(getIntent().getStringExtra("UserDesignation"));
        }
        addData();
    }

    private void addData() {
        ArrayList<BirthdayModel> birthdayModelArray = new ArrayList<BirthdayModel>();

        Cursor cursor = getContentResolver().query(CONTENT_UPCOMING_BIRTHDAYS, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                BirthdayModel model = new BirthdayModel();
                model.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                model.setDate_Of_Birth(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_OF_BIRTH)));
                model.setEmailID(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                model.setUser_Image(cursor.getString(cursor.getColumnIndex(COLUMN_USER_IMAGE)));
                birthdayModelArray.add(model);

            }
        }

        BirthdayAdapter adapter = new BirthdayAdapter(BirthdayActivity.this, birthdayModelArray);
        rv_Birthdays.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
