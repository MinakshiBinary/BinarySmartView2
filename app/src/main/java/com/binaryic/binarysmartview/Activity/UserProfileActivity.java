package com.binaryic.binarysmartview.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_MAC_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_DESIGNATION;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_IMAGE;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_MAC;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_USER_INFO;
import static com.binaryic.binarysmartview.Common.Constants.URL_CHECK_IN;
import static com.binaryic.binarysmartview.Common.Constants.URL_GET_USER_STATUS;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_GoodMorning;
    Timer timer;
    MyTimerTask myTimerTask;
    private TextView tv_time;
    private TextView tvAm;
    private TextView tv_Date;
    private TextView tv_Name;
    private TextView tv_Designation;
    private Button bt_LoginLogout;
    private Button bt_Enter;
    private ImageView iv_UserPic;
    private RelativeLayout rl_Container;
    private String email;
    private String ecode;
    private boolean loginStatus;
    private boolean wifiStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        final Calendar c = Calendar.getInstance();
        init();
        setUserData();
        SetDateTime();
        if (InternetConnectionDetector.isConnectingToInternet(UserProfileActivity.this, false)) {
            getUserStatusApi(ecode, c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
        } else {
            CommonFunction.createSnackbar(rl_Container, "No Internet Connection");
        }
    }

    private void init() {
        rl_Container = (RelativeLayout) findViewById(R.id.rl_Container);
        tv_GoodMorning = (TextView) findViewById(R.id.tv_GoodMorning);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tvAm = (TextView) findViewById(R.id.tvAm);
        tv_Date = (TextView) findViewById(R.id.tv_Date);
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        tv_Designation = (TextView) findViewById(R.id.tv_Designation);
        bt_LoginLogout = (Button) findViewById(R.id.bt_LoginLogout);
        bt_Enter = (Button) findViewById(R.id.bt_Enter);
        iv_UserPic = (ImageView) findViewById(R.id.iv_UserPic);
        bt_LoginLogout.setOnClickListener(this);
        bt_Enter.setOnClickListener(this);
    }

    private void setUserData() {
        Cursor cursor = getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            tv_Name.setText(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            tv_Designation.setText(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DESIGNATION)));
            email = (cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
            ecode = (cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)));
            Glide.with(UserProfileActivity.this).load(cursor.getString(cursor.getColumnIndex(COLUMN_USER_IMAGE))).into(iv_UserPic);
            Log.e("MainActivity", "entry value user_Image= " + cursor.getString(cursor.getColumnIndex(COLUMN_USER_IMAGE)));
        }
        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_LoginLogout:

                if (InternetConnectionDetector.isConnectingToInternet(UserProfileActivity.this, false)) {
                    if (loginStatus) {
                        ReportingController.reportingApi(UserProfileActivity.this, "CheckOut Button", UserInfoController.getUserEmail(UserProfileActivity.this), UserInfoController.getUserEcode(UserProfileActivity.this));

                        alertLoginLogout("Check In", false, "Check Out");
                    } else {
                        ReportingController.reportingApi(UserProfileActivity.this, "CheckIn Button", UserInfoController.getUserEmail(UserProfileActivity.this), UserInfoController.getUserEcode(UserProfileActivity.this));

                        alertLoginLogout("Check Out", true, "Check In");
                    }
                } else {
                    CommonFunction.createSnackbar(rl_Container, "No Internet Connection");
                }
                break;
            case R.id.bt_Enter:
                ReportingController.reportingApi(UserProfileActivity.this, "Enter Button", UserInfoController.getUserEmail(UserProfileActivity.this), UserInfoController.getUserEcode(UserProfileActivity.this));

                startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    private void alertLoginLogout(final String loginCurrentValue, final boolean Status, final String loginFutureValue) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to " + loginCurrentValue + " ?")
                .setCancelable(false)
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.e("UserProfileActivity", "loginStatus =" + loginStatus);
                        if (loginStatus) {
                            Cursor cursorWifi = getContentResolver().query(CONTENT_MAC, null, null, null, null);
                            if (cursorWifi.getCount() > 0) {
                                for (int i = 0; i < cursorWifi.getCount(); i++) {
                                    cursorWifi.moveToNext();
                                    if (getMacId().matches(cursorWifi.getString(cursorWifi.getColumnIndex(COLUMN_MAC_ID)))) {
                                        wifiStatus = true;
                                        break;
                                    }
                                }
                                if (wifiStatus) {
                                    checkInCheckOutApi(email, ecode, loginCurrentValue, Status, loginFutureValue);
                                } else {
                                    CommonFunction.createSnackbar(rl_Container, "Please Connect With Authorised Wifi");
                                }
                            }
                            cursorWifi.close();
                        } else {
                            checkInCheckOutApi(email, ecode, loginCurrentValue, Status, loginFutureValue);
                        }
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void alert_On_LateLogin_and_EarlyLogout(String login_Type) {
        String messge = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (login_Type.matches("half_day")) {
            messge = "Reason of half Day..?";
        } else if (login_Type.matches("early")) {
            messge = "Reason of Early Mark..?";
        } else if (login_Type.matches("late")) {
            messge = "Reason of Late Mark..?";
        }
        builder.setMessage(messge)
                .setCancelable(false)
                .setNegativeButton("Other", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .setPositiveButton("Meeting", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.e("UserProfileActivity", "loginStatus =" + loginStatus);
                        Intent intent = new Intent(UserProfileActivity.this, MeetingApplicationActivity.class);
                        intent.putExtra("emailID", email);
                        intent.putExtra("ecode", ecode);
                        startActivity(intent);
                        finish();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void getUserStatusApi(final String ecode, final String date) {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Your Details...");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_GET_USER_STATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("UserProfileactivity", " getUserStatus " + response_String);
                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);
                    if (response.getString("success").matches("1")) {
                        if (response.getString("login_status") != null) {
                            loginStatus = (response.getString("login_status").matches("login") ? false : true);
                            if (loginStatus) {
                                bt_LoginLogout.setText("Check In");
                            } else {
                                bt_LoginLogout.setText("Check Out");
                            }
                        }
                    }
                    pDialog.dismiss();

                } catch (Exception e) {
                    Log.e("UserProfileactivity", "getUserStatusApi error " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    if (response.statusCode == 400) {
                    } else {
                    }
                } else {
                }
                pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ecode", ecode);
                params.put("date", date);
                Log.e("UserProfileActivity", "params==" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "checkInCheckOutApi");
    }

    private void checkInCheckOutApi(final String email, final String ecode, final String login_value, final boolean Status, final String loginFutureValue) {

        final String login_type;
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Updating Your Status...");
        pDialog.show();
        Log.e("UserProfileactivity", " checkIn " + login_value);
        if (login_value.matches("Check In")) {
            login_type = "login";
        } else {
            login_type = "logout";
        }

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_CHECK_IN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("UserProfileactivity", " checkIn " + response_String);
                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);
                    if (response.getString("Result").matches("1")) {
                        loginStatus = Status;
                        bt_LoginLogout.setText(loginFutureValue);
                        if (!TextUtils.isEmpty(response.getString("message"))) {
                            if (response.getString("message").toLowerCase().matches("half_day") || response.getString("message").toLowerCase().matches("early") || response.getString("message").toLowerCase().matches("late")) {
                                alert_On_LateLogin_and_EarlyLogout(response.getString("message").toLowerCase());
                            }
                        } else {
                            CommonFunction.createSnackbar(rl_Container, "Succesfully " + login_type.substring(0, 1).toUpperCase() + login_type.substring(1));
                        }

                    } else if (response.getString("Result").matches("2")) {
                        Toast.makeText(UserProfileActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                    pDialog.dismiss();

                } catch (Exception e) {
                    Log.e("UserProfileactivity", "checkInCheckOutApi error " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    if (response.statusCode == 400) {
                    } else {
                    }
                } else {
                }
                pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", email);
                params.put("login_type", login_type);
                params.put("ecode", ecode);

                Log.e("UserProfileActivity", "params==" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "checkInCheckOutApi");
    }

    public String getMacId() {

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        return wifiInfo.getBSSID();
    }

    private void SetDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        tv_Date.setText(new SimpleDateFormat("EEE").format(c.getTime()) + ", " + formattedDate);
        // Now formattedDate have current date/time
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            tv_GoodMorning.setText("Good Morning");
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            tv_GoodMorning.setText("Good Afternoon");
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            tv_GoodMorning.setText("Good Evening");
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            tv_GoodMorning.setText("Good Night");
        }
        timer = new Timer();
        myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 1000, 1000);
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            final Calendar calendar = Calendar.getInstance();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String time = MyApplication.GetHour(calendar.getTime().getHours() + ":" + calendar.getTime().getMinutes());
                    tv_time.setText(time.split(" ")[0]);
                    tvAm.setText(time.split(" ")[1]);
                }
            });
        }
    }
}
