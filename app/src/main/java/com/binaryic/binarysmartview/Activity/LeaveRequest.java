package com.binaryic.binarysmartview.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_USER_INFO;
import static com.binaryic.binarysmartview.Common.Constants.URL_APPLY_LEAVE;

public class LeaveRequest extends AppCompatActivity {

    private static final int DATE_PICKER_ID = 1111;
    private static final int DATE_PICKER_IDD = 111;
    private String emailID;

    EditText txtFromDate, txtToDate, txtPurpose;
    TextInputLayout TextInputFromDate, TextInputToDate, TextInputPurpose;
    LinearLayout ll_Container;
    Button btnSubmit;
    Spinner SpnLeaveRequest;
    RadioGroup rgLeaveDuration;
    RadioButton rbLeaveDuration;
    Calendar c;
    String TAG_LEAVE = "tag_leave";
    int year, month, day, selectedtype, selectedduration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_request);
        ReportingController.reportingApi(LeaveRequest.this, "LeaveRequest Activity", UserInfoController.getUserEmail(LeaveRequest.this), UserInfoController.getUserEcode(LeaveRequest.this));

        Cursor cursor = getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            emailID = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID));
        }
        cursor.close();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Leave Request");
        }
        ll_Container = (LinearLayout) findViewById(R.id.ll_Container);
        txtFromDate = (EditText) findViewById(R.id.txtFromDate);
        txtToDate = (EditText) findViewById(R.id.txtToDate);
        txtPurpose = (EditText) findViewById(R.id.txtPurpose);
        TextInputFromDate = (TextInputLayout) findViewById(R.id.TextInputFromDate);
        TextInputToDate = (TextInputLayout) findViewById(R.id.TextInputTodate);
        TextInputPurpose = (TextInputLayout) findViewById(R.id.TextInputPurpose);
        rgLeaveDuration = (RadioGroup) findViewById(R.id.rgLeaveDuration);
        SpnLeaveRequest = (Spinner) findViewById(R.id.SpnLeaveRequest);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        txtFromDate.addTextChangedListener(new MyTextWatcher(txtFromDate));
        txtToDate.addTextChangedListener(new MyTextWatcher(txtToDate));
        txtPurpose.addTextChangedListener(new MyTextWatcher(txtPurpose));
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        txtFromDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDialog(DATE_PICKER_ID);
                return true;
            }
        });
        txtToDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDialog(DATE_PICKER_IDD);
                return true;
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetConnectionDetector.isConnectingToInternet(LeaveRequest.this, false)) {
                    if (txtFromDate.getText().toString().isEmpty()) {
                        TextInputFromDate.setError("Select Date");
                    }
                    if (txtToDate.getText().toString().isEmpty()) {
                        TextInputToDate.setError("Select Date");
                    }
                    if (txtPurpose.getText().toString().isEmpty()) {
                        TextInputPurpose.setError("Enter Purpose");
                    } else {

                        String type = SpnLeaveRequest.getSelectedItem().toString().toLowerCase();
                        selectedduration = rgLeaveDuration.getCheckedRadioButtonId();
                        rbLeaveDuration = (RadioButton) findViewById(selectedduration);
                        String duration = String.valueOf(rbLeaveDuration.getText());
                        String startDate = txtFromDate.getText().toString();
                        String endDate = txtToDate.getText().toString();
                        applyLeaveApiCall(emailID, startDate, endDate, type, duration, txtPurpose.getText().toString());

                    }
                } else
                    CommonFunction.createSnackbar(ll_Container, "No Internet Connection");
            }
        });
    }


    private void applyLeaveApiCall(final String emailID, final String startDate, final String endDate, final String leavetype, final String leaveDuration, final String leavepurpose) {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_APPLY_LEAVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("LeaveRequest", " Responce " + response_String);

                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);

                    if (response.getString("success").equalsIgnoreCase("1")) {
                        CommonFunction.createSnackbar(ll_Container, response.getString("msg"));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("LeaveRequest", " error " + e.getMessage());
                }
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                Log.e("LeaveRequest", " Error " + error.getMessage());
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
                params.put("emailID", emailID);
                params.put("startDate", startDate);
                params.put("endDate", endDate);
                params.put("leavetype", leavetype);
                params.put("leaveDuration", leaveDuration);
                params.put("leavepurpose", leavepurpose);
                Log.e("LeaveRequest", "parameters" + params);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "attendance");
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                DatePickerDialog dialog = new DatePickerDialog(this, pickerlistener, year, month, day);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                    dialog.getDatePicker().setMinDate(c.getTimeInMillis());
                }
                return dialog;
            case DATE_PICKER_IDD:
                DatePickerDialog dialog1 = new DatePickerDialog(this, mpickerlistener, year, month, day);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                    dialog1.getDatePicker().setMinDate(c.getTimeInMillis());
                }
                return dialog1;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerlistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedyear, int monthOfYear, int dayOfMonth) {
            try {
                year = selectedyear;
                month = monthOfYear + 1;

                String sday, smonth;
                if (dayOfMonth < 10) {
                    sday = "0" + dayOfMonth;
                } else {
                    sday = String.valueOf(dayOfMonth);
                }
                if (month < 10) {
                    smonth = "0" + month;
                } else {
                    smonth = String.valueOf(month);
                }
                txtFromDate.setText(new StringBuilder().append(year).append("-").append(smonth).append("-").append(sday).append(" "));
            } catch (Exception ex) {
            }
        }
    };
    private DatePickerDialog.OnDateSetListener mpickerlistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedyear, int monthOfYear, int dayOfMonth) {
            try {
                year = selectedyear;
                month = monthOfYear + 1;

                String sday, smonth;
                if (dayOfMonth < 10) {
                    sday = "0" + dayOfMonth;
                } else {
                    sday = String.valueOf(dayOfMonth);
                }
                if (month < 10) {
                    smonth = "0" + month;
                } else {
                    smonth = String.valueOf(month);
                }
                txtToDate.setText(new StringBuilder().append(year).append("-").append(smonth).append("-").append(sday).append(" "));
            } catch (Exception ex) {
            }
        }
    };

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
                    TextInputFromDate.setErrorEnabled(false);
                    break;
                case R.id.txtToDate:
                    TextInputToDate.setErrorEnabled(false);
                    break;
                case R.id.txtPurpose:
                    TextInputPurpose.setErrorEnabled(false);
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.txtFromDate:
                    TextInputFromDate.setErrorEnabled(false);
                    break;
                case R.id.txtToDate:
                    TextInputToDate.setErrorEnabled(false);
                    break;
                case R.id.txtPurpose:
                    TextInputPurpose.setErrorEnabled(false);
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
