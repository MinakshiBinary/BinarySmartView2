package com.binaryic.binarysmartview.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.binaryic.binarysmartview.Adapter.MessageReportAdapter;
import com.binaryic.binarysmartview.Controller.ReportingController;
import com.binaryic.binarysmartview.Controller.UserInfoController;
import com.binaryic.binarysmartview.Models.MessageReportModel;
import com.binaryic.binarysmartview.R;

import java.util.ArrayList;

public class MessageReportActivity extends AppCompatActivity {
    private RecyclerView rv_Report;
    private ArrayList<MessageReportModel> arrayReport;
    private Toolbar toolbar;
    private TextView tv_Name;
    private TextView tv_Designation;

    private void init() {
        rv_Report = (RecyclerView) findViewById(R.id.rv_Report);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        tv_Designation = (TextView) findViewById(R.id.tv_Designation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ReportingController.reportingApi(MessageReportActivity.this, "MessageReport Activity", UserInfoController.getUserEmail(MessageReportActivity.this), UserInfoController.getUserEcode(MessageReportActivity.this));

        init();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        if (getIntent().hasExtra("UserName")) {
            tv_Name.setText(getIntent().getStringExtra("UserName"));
            tv_Designation.setText(getIntent().getStringExtra("UserDesignation"));
        }
        setAdapter();
        arrayReport = new ArrayList<MessageReportModel>();
        rv_Report.hasFixedSize();
        rv_Report.setLayoutManager(new LinearLayoutManager(MessageReportActivity.this));

    }

    void setAdapter() {
        arrayReport = new ArrayList<MessageReportModel>();
        for (int i = 0; i < 5; i++) {
            MessageReportModel reportModel = new MessageReportModel();
            reportModel.setMessage("message");
            switch (i) {
                case 0:
                case 4:
                    reportModel.setMsg_status("wait");
                    reportModel.setReceiver("Snehal Joshi");
                    reportModel.setDate("24 Nov");
                    reportModel.setTitle("Reminder");
                    break;
                case 2:
                    reportModel.setMsg_status("send");
                    reportModel.setReceiver("Abhishek Dongre");
                    reportModel.setDate("12 Nov");
                    reportModel.setTitle("Happy bday");
                    break;
                case 1:
                case 3:
                    reportModel.setMsg_status("read");
                    reportModel.setReceiver("Minaj Shekh");
                    reportModel.setDate("29 Nov");
                    reportModel.setTitle("Meeting");
                    break;
            }


            arrayReport.add(reportModel);
        }
        rv_Report.setAdapter(new MessageReportAdapter(MessageReportActivity.this, arrayReport));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
