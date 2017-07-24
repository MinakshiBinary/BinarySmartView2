package com.binaryic.binarysmartview.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryic.binarysmartview.Adapter.MeetingAdapter;
import com.binaryic.binarysmartview.Adapter.RequestChangeShiftAdapter;
import com.binaryic.binarysmartview.Adapter.RequestLeaveAdapter;
import com.binaryic.binarysmartview.Common.CommonFunction;
import com.binaryic.binarysmartview.Common.InternetConnectionDetector;
import com.binaryic.binarysmartview.Common.TextAwesome;
import com.binaryic.binarysmartview.Controller.LeaveRequestController;
import com.binaryic.binarysmartview.Controller.SendMessageController;
import com.binaryic.binarysmartview.Models.MeetingApplicationModel;
import com.binaryic.binarysmartview.Models.RequestChangeShiftTimeModel;
import com.binaryic.binarysmartview.Models.RequestLeaveModel;
import com.binaryic.binarysmartview.R;

import java.util.ArrayList;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_APPLICATION_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_CURRENT_IN;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_CURRENT_OUT;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DATE_OF_APPLY;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DURATION;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_FROM_DATE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_FROM_TIME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NEW_IN;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NEW_OUT;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_STATUS;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TITLE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TO_DATE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TO_TIME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TYPE;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_ALL_USER_LEAVE;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_ALL_USER_MEETINGS;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_ALL_USER_SHIFT_CHANGE;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_USER_INFO;


public class AdminActivity extends AppCompatActivity {
    private int noOfDataToShow;
    private LinearLayout ll_main;
    private TextAwesome ta_LoginUsers;
    private TextAwesome ta_Report;
    private FloatingActionButton fab_SendNotification;
    private LinearLayout ll_GetMeetingApplication;
    private LinearLayout ll_GetShiftTime;
    private LinearLayout ll_GetLeaves;
    private Toolbar toolbar;
    private TextView tv_No_Request_Leaves;
    private TextView tv_No_MeetingApplication;
    private TextView tv_No_Request_Shift;
    private TextView tv_Name;
    private TextView tv_Designation;
    private RecyclerView rv_Request_Leaves;
    private RecyclerView rv_MeetingApplication;
    private RecyclerView rv_Request_Shift;
    public static ArrayList<RequestLeaveModel> leaveModelArrayList = new ArrayList<RequestLeaveModel>();
    public static ArrayList<RequestChangeShiftTimeModel> shiftModelArrayList = new ArrayList<RequestChangeShiftTimeModel>();
    public static ArrayList<MeetingApplicationModel> meetingApplicationArrayList = new ArrayList<MeetingApplicationModel>();


    private void init() {

        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        ta_LoginUsers = (TextAwesome) findViewById(R.id.ta_LoginUsers);
        ta_Report = (TextAwesome) findViewById(R.id.ta_Report);
        fab_SendNotification = (FloatingActionButton) findViewById(R.id.fab_SendNotification);
        ll_GetMeetingApplication = (LinearLayout) findViewById(R.id.ll_GetMeetingApplication);
        ll_GetShiftTime = (LinearLayout) findViewById(R.id.ll_GetShiftTime);
        ll_GetLeaves = (LinearLayout) findViewById(R.id.ll_GetLeaves);
        tv_No_Request_Leaves = (TextView) findViewById(R.id.tv_No_Request_Leaves);
        tv_No_Request_Shift = (TextView) findViewById(R.id.tv_No_Request_Shift);
        tv_No_MeetingApplication = (TextView) findViewById(R.id.tv_No_MeetingApplication);
        tv_Designation = (TextView) findViewById(R.id.tv_Designation);
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rv_Request_Leaves = (RecyclerView) findViewById(R.id.rv_Request_Leaves);
        rv_MeetingApplication = (RecyclerView) findViewById(R.id.rv_MeetingApplication);
        rv_Request_Shift = (RecyclerView) findViewById(R.id.rv_Request_Shift);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        init();

        Cursor cursor = getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
        Log.e("AdminActivity", "CONTENT_USER_INFO=" + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            if (cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)).matches("minakshi@binaryic.com")) {
                LeaveRequestController.getAllUsersLeaveDetailsApi(AdminActivity.this);
            }
        }
        cursor.close();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Meeing Application");
        }
        if (getIntent().hasExtra("UserName")) {
            tv_Name.setText(getIntent().getStringExtra("UserName"));
            tv_Designation.setText(getIntent().getStringExtra("UserDesignation"));
        }


        addLeaveData();
        if (leaveModelArrayList.size() > 0) {
            rv_Request_Leaves.setVisibility(View.VISIBLE);
            tv_No_Request_Leaves.setVisibility(View.GONE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(AdminActivity.this);
            Log.e("AdminActivity", "leaveModelArrayList" + leaveModelArrayList.size());
            if (leaveModelArrayList.size() <= 2)
                noOfDataToShow = leaveModelArrayList.size();
            else
                noOfDataToShow = 3;
            RequestLeaveAdapter leaveAdapter = new RequestLeaveAdapter(AdminActivity.this, leaveModelArrayList, noOfDataToShow);
            rv_Request_Leaves.setLayoutManager(layoutManager);
            rv_Request_Leaves.setAdapter(leaveAdapter);
        } else {
            rv_Request_Leaves.setVisibility(View.GONE);
            tv_No_Request_Leaves.setVisibility(View.VISIBLE);
        }

        addChangeShiftTimeData();
        if (shiftModelArrayList.size() > 0) {
            rv_Request_Shift.setVisibility(View.VISIBLE);
            tv_No_Request_Shift.setVisibility(View.GONE);
            LinearLayoutManager shiftLayoutManager = new LinearLayoutManager(AdminActivity.this);
            if (shiftModelArrayList.size() <= 2)
                noOfDataToShow = shiftModelArrayList.size();
            else
                noOfDataToShow = 3;

            RequestChangeShiftAdapter changeShiftTimeAdapter = new RequestChangeShiftAdapter(AdminActivity.this, shiftModelArrayList, noOfDataToShow);
            rv_Request_Shift.setLayoutManager(shiftLayoutManager);
            rv_Request_Shift.setAdapter(changeShiftTimeAdapter);
        } else {
            rv_Request_Shift.setVisibility(View.GONE);
            tv_No_Request_Shift.setVisibility(View.VISIBLE);
        }


        addMeetingApplicationData();

        if (meetingApplicationArrayList.size() > 0) {
            rv_MeetingApplication.setVisibility(View.VISIBLE);
            tv_No_MeetingApplication.setVisibility(View.GONE);
            LinearLayoutManager meetingLayoutManager = new LinearLayoutManager(AdminActivity.this);
            Log.e("AdminActivity", "meetingApplicationArrayList" + meetingApplicationArrayList.size());
            if (meetingApplicationArrayList.size() <= 2)
                noOfDataToShow = meetingApplicationArrayList.size();
            else
                noOfDataToShow = 3;

            MeetingAdapter meetingAdapter = new MeetingAdapter(AdminActivity.this, meetingApplicationArrayList, noOfDataToShow);
            rv_MeetingApplication.setLayoutManager(meetingLayoutManager);
            rv_MeetingApplication.setAdapter(meetingAdapter);

        } else {
            rv_MeetingApplication.setVisibility(View.GONE);
            tv_No_MeetingApplication.setVisibility(View.VISIBLE);

        }

        ll_GetLeaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leaveModelArrayList.size() > 0) {
                    Intent intent = new Intent(AdminActivity.this, AdminDetailsActivity.class);
                    intent.putExtra("data_from", "leave");
                    intent.putExtra("UserName", getIntent().getStringExtra("UserName"));
                    intent.putExtra("UserDesignation", getIntent().getStringExtra("UserDesignation"));
                    startActivity(intent);
                } else {
                    CommonFunction.createSnackbar(ll_main, "NO Leave Application");
                }
            }
        });
        ll_GetShiftTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shiftModelArrayList.size() > 0) {
                    Intent intent = new Intent(AdminActivity.this, AdminDetailsActivity.class);
                    intent.putExtra("data_from", "shift");
                    intent.putExtra("UserName", getIntent().getStringExtra("UserName"));
                    intent.putExtra("UserDesignation", getIntent().getStringExtra("UserDesignation"));
                    startActivity(intent);
                } else {
                    CommonFunction.createSnackbar(ll_main, "NO Shift Change Application");
                }
            }
        });
        ll_GetMeetingApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meetingApplicationArrayList.size() > 0) {
                    Intent intent = new Intent(AdminActivity.this, AdminDetailsActivity.class);
                    intent.putExtra("data_from", "meeting");
                    intent.putExtra("UserName", getIntent().getStringExtra("UserName"));
                    intent.putExtra("UserDesignation", getIntent().getStringExtra("UserDesignation"));
                    startActivity(intent);
                } else {
                    CommonFunction.createSnackbar(ll_main, "NO Meeting Application");
                }
            }
        });
        fab_SendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetConnectionDetector.isConnectingToInternet(AdminActivity.this, true)) {
                    SendMessageController.allDepartmentsApiCall(AdminActivity.this);
                }
            }
        });
        ta_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ReportActivity.class);
                intent.putExtra("UserName", getIntent().getStringExtra("UserName"));
                intent.putExtra("UserDesignation", getIntent().getStringExtra("UserDesignation"));
                startActivity(intent);
            }
        });
        ta_LoginUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, MessageReportActivity.class);
                intent.putExtra("UserName", getIntent().getStringExtra("UserName"));
                intent.putExtra("UserDesignation", getIntent().getStringExtra("UserDesignation"));
                startActivity(intent);
            }
        });
    }

    private void addMeetingApplicationData() {
        meetingApplicationArrayList.clear();
        Cursor cursor = getContentResolver().query(CONTENT_ALL_USER_MEETINGS, null, null, null, null);
        if (cursor.getCount() > 0) {
            Log.e("AdminActivity", "addMeetingData" + cursor.getCount());
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                MeetingApplicationModel meetingApplicationModel = new MeetingApplicationModel();
                meetingApplicationModel.setApplicationId(cursor.getString(cursor.getColumnIndex(COLUMN_APPLICATION_ID)));
                meetingApplicationModel.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                meetingApplicationModel.setFrom_Time(cursor.getString(cursor.getColumnIndex(COLUMN_FROM_TIME)));
                meetingApplicationModel.setTo_Time(cursor.getString(cursor.getColumnIndex(COLUMN_TO_TIME)));
                meetingApplicationModel.setMeeting_Date(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_OF_APPLY)));
                meetingApplicationModel.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                meetingApplicationModel.setAcceptance_status(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));
                meetingApplicationArrayList.add(meetingApplicationModel);
            }
        }
    }

    private void addChangeShiftTimeData() {
        shiftModelArrayList.clear();
        Cursor cursor = getContentResolver().query(CONTENT_ALL_USER_SHIFT_CHANGE, null, null, null, null);
        if (cursor.getCount() > 0) {
            Log.e("AdminActivity", "addTimeShiftData" + cursor.getCount());
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                RequestChangeShiftTimeModel shiftModel = new RequestChangeShiftTimeModel();
                shiftModel.setShiftChangeId(cursor.getString(cursor.getColumnIndex(COLUMN_APPLICATION_ID)));
                shiftModel.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                shiftModel.setNew_Out_Time(cursor.getString(cursor.getColumnIndex(COLUMN_NEW_OUT)));
                shiftModel.setNew_In_Time(cursor.getString(cursor.getColumnIndex(COLUMN_NEW_IN)));
                shiftModel.setCurrent_In_Time(cursor.getString(cursor.getColumnIndex(COLUMN_CURRENT_IN)));
                shiftModel.setCurrent_Out_Time(cursor.getString(cursor.getColumnIndex(COLUMN_CURRENT_OUT)));
                shiftModel.setRequest_On_Date(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_OF_APPLY)));
                shiftModel.setChange_Time_Status(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));
                shiftModelArrayList.add(shiftModel);
            }
        }
    }

    private void addLeaveData() {
        leaveModelArrayList.clear();
        String[] date;
        Cursor cursor = getContentResolver().query(CONTENT_ALL_USER_LEAVE, null, null, null, null);
        Log.e("AdminActivity", "addLeaveData" + cursor.getCount());

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                RequestLeaveModel leaveModel = new RequestLeaveModel();
                leaveModel.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                date = cursor.getString(cursor.getColumnIndex(COLUMN_FROM_DATE)).split(" ");
                leaveModel.setFromDate(date[0]);
                date = cursor.getString(cursor.getColumnIndex(COLUMN_TO_DATE)).split(" ");
                leaveModel.setToDate(date[0]);
                date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_OF_APPLY)).split(" ");
                leaveModel.setLeaveApplyDate(date[0]);
                leaveModel.setLeaveType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
                leaveModel.setLeave_Duration(cursor.getString(cursor.getColumnIndex(COLUMN_DURATION)));
                leaveModel.setLeaveStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));
                leaveModel.setLeaveTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                leaveModel.setLeaveId(cursor.getString(cursor.getColumnIndex(COLUMN_APPLICATION_ID)));
                leaveModelArrayList.add(leaveModel);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


}
