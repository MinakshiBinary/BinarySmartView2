package com.binaryic.binarysmartview.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.binarysmartview.Common.CommonFunction;
import com.binaryic.binarysmartview.Common.InternetConnectionDetector;
import com.binaryic.binarysmartview.Common.MyApplication;
import com.binaryic.binarysmartview.Controller.ReportingController;
import com.binaryic.binarysmartview.Controller.UserInfoController;
import com.binaryic.binarysmartview.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.binaryic.binarysmartview.Common.Constants.URL_MEETING_APPLICATION;

public class MeetingApplicationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private Toolbar toolbar;
    private LinearLayout ll_MeetingApplication;
    private TextInputLayout TextInputFromDate;
    private TextInputLayout TextInputMeetingDate;
    private EditText et_MeetingDate;
    private EditText et_FromTime;
    private EditText et_ToTime;
    private EditText et_Title;
    private EditText et_Purpose;
    private Button bt_Submit;
    private String entry_Time;
    private String in_Time;
    private String emailID;
    private String ecode;
    private String out_Time;
    private String dateForApi;


    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        ll_MeetingApplication = (LinearLayout) findViewById(R.id.ll_MeetingApplication);
        TextInputMeetingDate = (TextInputLayout) findViewById(R.id.TextInputMeetingDate);
        TextInputFromDate = (TextInputLayout) findViewById(R.id.TextInputFromDate);
        TextInputMeetingDate = (TextInputLayout) findViewById(R.id.TextInputMeetingDate);
        et_MeetingDate = (EditText) findViewById(R.id.et_MeetingDate);
        et_FromTime = (EditText) findViewById(R.id.et_FromTime);
        et_ToTime = (EditText) findViewById(R.id.et_ToTime);
        et_Title = (EditText) findViewById(R.id.et_Title);
        et_Purpose = (EditText) findViewById(R.id.et_Purpose);
        bt_Submit = (Button) findViewById(R.id.bt_Submit);
        et_MeetingDate.addTextChangedListener(new MyTextWatcher(et_MeetingDate));
        et_FromTime.addTextChangedListener(new MyTextWatcher(et_FromTime));
        et_ToTime.addTextChangedListener(new MyTextWatcher(et_ToTime));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_application);
        ReportingController.reportingApi(MeetingApplicationActivity.this, "MeetingApplication Activity", UserInfoController.getUserEmail(MeetingApplicationActivity.this), UserInfoController.getUserEcode(MeetingApplicationActivity.this));

        findViews();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Meeting Application");

        }
        if (getIntent().hasExtra("emailID")) {
            emailID = (getIntent().getStringExtra("emailID"));
            ecode = (getIntent().getStringExtra("ecode"));
        }


        et_MeetingDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            MeetingApplicationActivity.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.show(getFragmentManager(), "Date");
                }
                return false;
            }
        });
        et_FromTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    entry_Time = "inTime";
                    Calendar now = Calendar.getInstance();
                    TimePickerDialog tpd = TimePickerDialog.newInstance(
                            MeetingApplicationActivity.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            now.get(Calendar.SECOND), true
                    );
                    tpd.show(getFragmentManager(), "In");
                }
                return false;
            }
        });
        et_ToTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    entry_Time = "outTime";
                    Calendar now = Calendar.getInstance();
                    TimePickerDialog tpd = TimePickerDialog.newInstance(
                            MeetingApplicationActivity.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            now.get(Calendar.SECOND), true
                    );
                    tpd.show(getFragmentManager(), "Out");
                }
                return false;
            }
        });
        bt_Submit.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if (InternetConnectionDetector.isConnectingToInternet(MeetingApplicationActivity.this, false)) {
                                                 if (TextUtils.isEmpty(et_Title.getText().toString().trim())) {
                                                     CommonFunction.createSnackbar(ll_MeetingApplication, "Please Enter Meeting Title");
                                                 } else if (TextUtils.isEmpty(et_Purpose.getText().toString().trim())) {
                                                     CommonFunction.createSnackbar(ll_MeetingApplication, "Please Enter Meeting Purpose");
                                                 } else if (TextUtils.isEmpty(et_MeetingDate.getText().toString().trim())) {
                                                     CommonFunction.createSnackbar(ll_MeetingApplication, "Please Enter Meeting Date");
                                                 } else if (TextUtils.isEmpty(et_FromTime.getText().toString().trim())) {
                                                     CommonFunction.createSnackbar(ll_MeetingApplication, "Please Enter Meeting Start Time");
                                                 } else if (TextUtils.isEmpty(et_ToTime.getText().toString().trim())) {
                                                     CommonFunction.createSnackbar(ll_MeetingApplication, "Please Enter Meeting End Time");
                                                 } else {
                                                     meetingApplicationApiCall(emailID, ecode, dateForApi, in_Time, out_Time, et_Title.getText().toString().trim(), et_Purpose.getText().toString().trim());
                                                 }
                                             } else {
                                                 CommonFunction.createSnackbar(ll_MeetingApplication, "No Internet Connection");
                                             }
                                         }
                                     }
        );
    }


    private void meetingApplicationApiCall(final String emailID, final String ecode, final String Date, final String fromTime, final String toTime, final String title, final String leavepurpose) {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_MEETING_APPLICATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("MeetingActivity", " Responce " + response_String);
                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);

                    if (response.getString("Result").equalsIgnoreCase("1")) {
                        CommonFunction.createSnackbar(ll_MeetingApplication, "Succesfully Request Has Been Sent");
                        startActivity(new Intent(MeetingApplicationActivity.this, MainActivity.class));
                        finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("MeetingActivity", " error " + e.getMessage());
                }
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                Log.e("MeetingActivity", " Error " + error.getMessage());
                if (response != null) {
                    if (response.statusCode == 400) {

                    } else {

                    }
                } else {
                }
                pDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", emailID);
                params.put("ecode", ecode);
                params.put("meeting_date", Date);
                params.put("meeting_start_time", fromTime);
                params.put("meeting_end_time", toTime);
                params.put("title", title);
                params.put("purpose", leavepurpose);
                Log.e("MeetingActivity", "params==" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "attendance");
    }


    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        if (entry_Time.matches("inTime")) {
            in_Time = hourOfDay + ":" + minute + ":" + second;
            et_FromTime.setText(MyApplication.GetHour(in_Time));

        } else if (entry_Time.matches("outTime")) {
            out_Time = hourOfDay + ":" + minute + ":" + second;
            et_ToTime.setText(MyApplication.GetHour(out_Time));
        }
    }

    private class MyTextWatcher implements TextWatcher {
        View view;

        public MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (view.getId()) {
                case R.id.txtFromDate:
                    TextInputMeetingDate.setErrorEnabled(false);
                    break;
                case R.id.et_MeetingDate:
                    TextInputMeetingDate.setErrorEnabled(false);
                    break;
                case R.id.et_ToTime:
                    TextInputFromDate.setErrorEnabled(false);
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.txtFromDate:
                    TextInputMeetingDate.setErrorEnabled(false);
                    break;
                case R.id.et_MeetingDate:
                    TextInputMeetingDate.setErrorEnabled(false);
                    break;
                case R.id.et_ToTime:
                    TextInputFromDate.setErrorEnabled(false);
                    break;
            }
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        et_MeetingDate.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
        dateForApi = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


}
