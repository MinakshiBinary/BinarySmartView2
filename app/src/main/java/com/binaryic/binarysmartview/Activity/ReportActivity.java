package com.binaryic.binarysmartview.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.binaryic.binarysmartview.Adapter.ReportAdapter;
import com.binaryic.binarysmartview.Common.ApiCallBack;
import com.binaryic.binarysmartview.Controller.ReportingController;
import com.binaryic.binarysmartview.Controller.UserInfoController;
import com.binaryic.binarysmartview.Models.ReportModel;
import com.binaryic.binarysmartview.R;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private RecyclerView rv_Report;
    private String ecode = "";
    private ArrayList<String> arrayUser;
    private ArrayList<ReportModel> arrayReport;
    private Toolbar toolbar;
    private TextView tv_NoData;
    private TextView tv_Name;
    private TextView tv_Designation;
    private Spinner spinner_SelectUser;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ReportingController.reportingApi(ReportActivity.this, "Report Activity", UserInfoController.getUserEmail(ReportActivity.this), UserInfoController.getUserEcode(ReportActivity.this));

        setMenu();
        init();
    }

    private void setMenu() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
    }

    private void init() {
        tv_NoData = (TextView) findViewById(R.id.tv_NoData);
        spinner_SelectUser = (Spinner) findViewById(R.id.spinner_SelectUser);
        rv_Report = (RecyclerView) findViewById(R.id.rv_Report);
        arrayReport = new ArrayList<ReportModel>();
        rv_Report.hasFixedSize();
        rv_Report.setLayoutManager(new LinearLayoutManager(ReportActivity.this));
        setArrayUser();
    }

    private void setArrayUser() {
        arrayUser = new ArrayList<String>();
        arrayUser = ReportingController.getAllnameFromTable(ReportActivity.this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ReportActivity.this, android.R.layout.simple_spinner_item, arrayUser);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_SelectUser.setAdapter(dataAdapter);
        spinner_SelectUser.setOnItemSelectedListener(this);

        callApi();
        setReportAdapter();
    }

    void setReportAdapter() {

        Log.e("ReportActivity", "in adapter");
        arrayReport = new ArrayList<ReportModel>();
        Log.e("ReportActivity", "spinner_SelectUser" + spinner_SelectUser.getSelectedItem().toString().trim());
        if (spinner_SelectUser.getSelectedItem().toString().trim().matches("Select Employee Name")) {
            arrayReport = ReportingController.getDataFromReportTable(ReportActivity.this, " ");
        } else {
            arrayReport = ReportingController.getDataFromReportTable(ReportActivity.this, ReportingController.getEcodeFromTable(ReportActivity.this, spinner_SelectUser.getSelectedItem().toString().trim()));
        }
        if (arrayReport.size() > 0) {
            tv_NoData.setVisibility(View.GONE);
            rv_Report.setVisibility(View.VISIBLE);
            rv_Report.setAdapter(new ReportAdapter(ReportActivity.this, arrayReport));
        } else {
            tv_NoData.setVisibility(View.VISIBLE);
            rv_Report.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("ReportActivity", "in selection");

        if (!spinner_SelectUser.getSelectedItem().toString().trim().matches("Select Employee Name")) {
            ecode = ReportingController.getEcodeFromTable(ReportActivity.this, spinner_SelectUser.getSelectedItem().toString().trim());
        }
        callApi();

        setReportAdapter();
    }

    private void callApi() {
        final ProgressDialog dialog = new ProgressDialog(ReportActivity.this);
        dialog.setMessage("Please Wait...!!");
        dialog.setCancelable(false);
        dialog.show();
        ReportingController.retriveReportingApi(ReportActivity.this, ecode, new ApiCallBack() {
            @Override
            public void onSuccess(Object success) {
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {
                dialog.dismiss();

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
