package com.binaryic.binarysmartview.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.binaryic.binarysmartview.Adapter.NotificationAdapter;
import com.binaryic.binarysmartview.Controller.ReportingController;
import com.binaryic.binarysmartview.Controller.UserInfoController;
import com.binaryic.binarysmartview.Models.NotificationModel;
import com.binaryic.binarysmartview.R;

import java.util.ArrayList;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DATE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_MESSAGE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TITLE;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_NOTIFICAION;

public class AnnouncementActivity extends AppCompatActivity {
    private RecyclerView rv_Announcement;
    private TextView tv_Name;
    private TextView tv_Designation;
    private String name = "";
    private String designation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ReportingController.reportingApi(AnnouncementActivity.this, "Announcement Activity", UserInfoController.getUserEmail(AnnouncementActivity.this), UserInfoController.getUserEcode(AnnouncementActivity.this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        rv_Announcement = (RecyclerView) findViewById(R.id.rv_Announcement);
        rv_Announcement.setLayoutManager(new LinearLayoutManager(AnnouncementActivity.this));
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        tv_Designation = (TextView) findViewById(R.id.tv_Designation);
        if (getIntent().hasExtra("UserName")) {
            tv_Name.setText(getIntent().getStringExtra("UserName"));
            name = (getIntent().getStringExtra("UserName"));
            tv_Designation.setText(getIntent().getStringExtra("UserDesignation"));
            designation = (getIntent().getStringExtra("UserDesignation"));
        }
        addData();
    }

    private void addData() {
        ArrayList<NotificationModel> notificationModels = new ArrayList<NotificationModel>();
        Cursor cursor = getContentResolver().query(CONTENT_NOTIFICAION, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                NotificationModel model = new NotificationModel();
                model.setUserName(name);
                model.setDesignation(designation);
                model.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                model.setMessage(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE)));
                model.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                model.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                notificationModels.add(model);

            }
        }

        NotificationAdapter adapter = new NotificationAdapter(AnnouncementActivity.this, notificationModels);
        rv_Announcement.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
