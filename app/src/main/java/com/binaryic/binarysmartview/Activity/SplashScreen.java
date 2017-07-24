package com.binaryic.binarysmartview.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.binarysmartview.Common.Constants;
import com.binaryic.binarysmartview.Common.InternetConnectionDetector;
import com.binaryic.binarysmartview.Common.MyApplication;
import com.binaryic.binarysmartview.Controller.ReportingController;
import com.binaryic.binarysmartview.Controller.UserInfoController;
import com.binaryic.binarysmartview.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Cursor cursor = getContentResolver().query(Constants.CONTENT_UPCOMING_BIRTHDAYS, null, null, null, null);
        if (cursor.getCount() > 0) {
            downTimer.start();
        } else {
            if (InternetConnectionDetector.isConnectingToInternet(SplashScreen.this, true)) {
                upcomingHolidayApiCall();
            }
            if (InternetConnectionDetector.isConnectingToInternet(SplashScreen.this, false))
                getAllAnnouncement();

        }
    }

    CountDownTimer downTimer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            finish();
            String selection = Constants.COLUMN_USER_LOGIN_STATUS + " = '" + "1" + "'";
            Cursor cursor = getContentResolver().query(Constants.CONTENT_USER_INFO, null, selection, null, null);

            if (cursor.getCount() > 0) {
                startActivity(new Intent(SplashScreen.this, IntroActivity.class));
            } else {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));

            }
            cursor.close();
        }
    };


    private void upcomingBirthdayApiCall() {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.URL_GET_UPCOMING_BIRTHDAY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("birrthdayResponce", " birrthdayResponce " + response_String);

                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);
                    JSONArray arraybirthday = null;
                    if (response.getString("Result").equalsIgnoreCase("1")) {
                        arraybirthday = response.getJSONArray("Birth_day");

                        for (int i = 0; i < arraybirthday.length(); i++) {
                            JSONObject jsonObject = arraybirthday.getJSONObject(i);
                            ContentValues values = new ContentValues();
                            values.put(Constants.COLUMN_NAME, jsonObject.getString("full_name"));
                            values.put(Constants.COLUMN_EMAIL_ID, jsonObject.getString("user_name"));
                            values.put(Constants.COLUMN_DATE_OF_BIRTH, jsonObject.getString("dob"));
                            values.put(Constants.COLUMN_USER_IMAGE, jsonObject.getString("Image"));
                            String selection = Constants.COLUMN_EMAIL_ID + " = '" + jsonObject.getString("user_name") + "'";
                            Cursor cursor = getContentResolver().query(Constants.CONTENT_UPCOMING_BIRTHDAYS, null, selection, null, null);
                            if (cursor.getCount() > 0)
                                getContentResolver().update(Constants.CONTENT_UPCOMING_BIRTHDAYS, values, selection, null);
                            else
                                getContentResolver().insert(Constants.CONTENT_UPCOMING_BIRTHDAYS, values);
                            cursor.close();
                        }
                        finish();

                        String selection = Constants.COLUMN_USER_LOGIN_STATUS + " = '" + "1" + "'";
                        Cursor cursor = getContentResolver().query(Constants.CONTENT_USER_INFO, null, selection, null, null);
                        pDialog.dismiss();
                        if (cursor.getCount() > 0) {
                            ReportingController.reportingApi(SplashScreen.this, "SplashScreen Activity", UserInfoController.getUserEmail(SplashScreen.this), UserInfoController.getUserEcode(SplashScreen.this));

                            startActivity(new Intent(SplashScreen.this, IntroActivity.class));
                        } else {
                            startActivity(new Intent(SplashScreen.this, LoginActivity.class));

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    if (response.statusCode == 400) {

                    } else {

                    }
                } else {
                }
            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "birthdays");
    }


    private void getAllAnnouncement() {

        String url = Constants.URL_GET_ALL_ANNOUNCEMENT;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            public JSONArray data_Array = null;

            @Override
            public void onResponse(String response_String) {
                JSONObject response = null;
                try {
                    Log.e("getAllAnnouncement", " getAllAnnouncement " + response_String);
                    response = new JSONObject(response_String);
                    data_Array = response.getJSONArray("data ");
                    for (int i = 0; i < data_Array.length(); i++) {
                        JSONObject jsonObject = data_Array.getJSONObject(i);

                        ContentValues values = new ContentValues();
                        values.put(Constants.COLUMN_TITLE, jsonObject.getString("announcement_title"));
                        values.put(Constants.COLUMN_NAME, jsonObject.getString("announcement_name"));
                        values.put(Constants.COLUMN_DATE, jsonObject.getString("announcement_date"));
                        values.put(Constants.COLUMN_MESSAGE, jsonObject.getString("announcement"));
                        String selection = Constants.COLUMN_TITLE + " = '" + jsonObject.getString("announcement_title") + "'";
                        Cursor cursor = getContentResolver().query(Constants.CONTENT_NOTIFICAION, null, selection, null, null);
                        if (cursor.getCount() > 0)
                            getContentResolver().update(Constants.CONTENT_NOTIFICAION, values, selection, null);
                        else
                            getContentResolver().insert(Constants.CONTENT_NOTIFICAION, values);
                        cursor.close();
                    }

                } catch (Exception e) {
                    Log.e("ExExceptionception", "===" + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "getAnnouncment");

    }

    private void upcomingHolidayApiCall() {
        pDialog = new ProgressDialog(SplashScreen.this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        String url = Constants.URL_GET_UPCOMING_HOLIDAY;
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                JSONObject response = null;
                try {
                    Log.e("MupcomingHolidayApiCall", "Responce " + response_String + "hiiiiii");
                    response = new JSONObject(response_String);
                    JSONArray arrayholiday = null;

                    if (response.getString("Result").equalsIgnoreCase("1")) {
                        arrayholiday = response.getJSONArray("Holiday");
                        for (int i = 0; i < arrayholiday.length(); i++) {
                            JSONObject jsonObject = arrayholiday.getJSONObject(i);

                            ContentValues values = new ContentValues();
                            values.put(Constants.COLUMN_DATE, jsonObject.getString("holiday_date"));
                            values.put(Constants.COLUMN_HOLIDAY_NAME, jsonObject.getString("holiday_title"));
                            values.put(Constants.COLUMN_HOLIDAY_REMAINING_DAYS, jsonObject.getString("RemainingDay"));
                            String selection = Constants.COLUMN_DATE + " = '" + jsonObject.getString("holiday_date") + "'";
                            Cursor cursor = getContentResolver().query(Constants.CONTENT_UPCOMING_HOLIDAYS, null, selection, null, null);
                            if (cursor.getCount() > 0)
                                getContentResolver().update(Constants.CONTENT_UPCOMING_HOLIDAYS, values, selection, null);
                            else
                                getContentResolver().insert(Constants.CONTENT_UPCOMING_HOLIDAYS, values);
                            cursor.close();
                        }
                    } else if (response.getString("Result").equalsIgnoreCase("0")) {

                    }

                    upcomingBirthdayApiCall();

                } catch (JSONException e) {
                    Log.e("Main Activity", " error " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                Log.e("Main Activity", " Error " + error.getMessage());
                if (response != null) {
                    if (response.statusCode == 400) {

                    } else {

                    }
                } else {
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "holidays");
    }


    private void attendanceApiCall() {
        final ProgressDialog pDialog = new ProgressDialog(SplashScreen.this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.GET,
                "https://api.cinemalytics.com/v1/movie/year/2016/?auth_token=DD15584A7825EAE1D957C6BE275355F6", new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("Main activity", " attendence Responce " + response_String);
                String qry = "";
                try {
                    JSONArray arr = new JSONArray(response_String);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject object = (JSONObject) arr.get(i);
                        String name = object.getString("OriginalTitle");
                        qry = qry + "db.execSQL(\"INSERT INTO \" + TABLE_ADD_MOVIE + \" (\" + COLUMN_TITLE + \",\" + COLUMN_YEAR + \") VALUES ('" + name + "','2016')\");";
                    }
                    String temp = qry;
                    Log.e("String", qry);
                } catch (Exception e) {
                    Log.e("errorrrrrrr", "==" + e.getMessage());
                }
                pDialog.hide();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("errorerrorerror", "==" + error.getMessage());
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    if (response.statusCode == 400) {

                    } else {

                    }
                } else {
                }
                pDialog.hide();
            }
        });
        MyApplication.getInstance().addToRequestQueue(strReq, "attendance");
    }

    private void ShowDialog() {


      /*  dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(
                R.color.transparent);
        dialogTransparent.setContentView(view);*/
        final Dialog dialog = new Dialog(SplashScreen.this, R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_layout);
        dialog.getWindow().setBackgroundDrawableResource(
                R.color.transperent);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

}
