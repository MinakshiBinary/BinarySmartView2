package com.binaryic.binarysmartview.Controller;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.binarysmartview.Common.ApiCallBack;
import com.binaryic.binarysmartview.Common.MyApplication;
import com.binaryic.binarysmartview.Models.ReportModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_APPLICATION_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DATE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PAGE_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TIME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_ID;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_REGISTER_USER;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_REPORTS;
import static com.binaryic.binarysmartview.Common.Constants.URL_REPORTING;
import static com.binaryic.binarysmartview.Common.Constants.URL_RETRIVE_REPORT;

/**
 * Created by minakshi on 21/3/17.
 */

public class ReportingController {

    public static void reportingApi(final Activity context, final String page_name, final String email, final String ecode) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_REPORTING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("ReportingController", " reportingApi " + response_String);
                JSONObject response = null;

                try {
                    response = new JSONObject(response_String);
                    if (response.getString("Result").matches("1")) {

                    }

                } catch (Exception e) {
                    Log.e("ReportingController", "reportingApi error " + e.getMessage());
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
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("ecode", ecode);
                params.put("page_name", page_name);
                Log.e("ReportingController", "reportingApi params " + params);

                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "reportingApi");
    }

    public static void retriveReportingApi(final Activity context, final String ecode, final ApiCallBack callback) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_RETRIVE_REPORT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("ReportingController", " retriveReportingApi " + response_String);
                JSONObject response = null;

                try {
                    response = new JSONObject(response_String);
                    if (response.getString("Result").matches("1")) {
                        JSONArray ReportArray = response.getJSONArray("reportdata");
                        for (int i = 0; i < ReportArray.length(); i++) {
                            JSONObject ReportObject = ReportArray.getJSONObject(i);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(COLUMN_USER_ID, ReportObject.getString("ecode"));
                            contentValues.put(COLUMN_DATE, ReportObject.getString("date"));
                            contentValues.put(COLUMN_TIME, ReportObject.getString("time"));
                            contentValues.put(COLUMN_PAGE_NAME, ReportObject.getString("page_name"));
                            contentValues.put(COLUMN_EMAIL_ID, ReportObject.getString("email"));
                            contentValues.put(COLUMN_APPLICATION_ID, ReportObject.getString("id"));
                            // contentValues.put(COLUMN_NAME, ReportObject.getString("name"));
                            String selection = COLUMN_APPLICATION_ID + "= '" + ReportObject.getString("id") + "'";
                            Cursor cursor = context.getContentResolver().query(CONTENT_REPORTS, null, selection, null, null);
                            if (cursor.getCount() > 0) {
                                context.getContentResolver().update(CONTENT_REPORTS, contentValues, selection, null);
                            } else {
                                context.getContentResolver().insert(CONTENT_REPORTS, contentValues);
                            }
                            cursor.close();
                        }
                        callback.onSuccess("success");
                    } else {
                        callback.onError("error");
                    }
                } catch (Exception e) {
                    callback.onError(e.getMessage());

                    Log.e("ReportingController", "retriveReportingApi error " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                callback.onError(error.getMessage());

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
                params.put("ecode", ecode);
                Log.e("ReportingController", "retriveReportingApi params " + params);

                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "retriveReportingApi");
    }

    public static ArrayList<String> getAllnameFromTable(Activity context) {
        ArrayList<String> arrayUser = new ArrayList<String>();
        Cursor cursor = context.getContentResolver().query(CONTENT_REGISTER_USER, null, null, null, null);
        Log.e("ReportController", "arrayUser cursor==" + cursor.getCount());
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                arrayUser.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            }
        }
        cursor.close();
        return arrayUser;

    }

    public static String getEcodeFromTable(Activity context, String name) {
        String ecode = "";
        String selection = COLUMN_NAME + " = '" + name + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_REGISTER_USER, null, selection, null, null);
        Log.e("ReportController", "arrayUser cursor==" + cursor.getCount());
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                ecode = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
            }
        }
        cursor.close();
        return ecode;
    }

    public static ArrayList<ReportModel> getDataFromReportTable(Activity context, String ecode) {
        String selection = "";
        ArrayList<ReportModel> reportModels = new ArrayList<>();
        if (!ecode.matches("")) {
            selection = COLUMN_USER_ID + " = '" + ecode + "'";
        }
        Cursor cursor = context.getContentResolver().query(CONTENT_REPORTS, null, selection, null, null);
        Log.e("ReportController", "reports cursor==" + cursor.getCount());
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                ReportModel reportModel = new ReportModel();
                reportModel.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                reportModel.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
                reportModel.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                reportModel.setPageName(cursor.getString(cursor.getColumnIndex(COLUMN_PAGE_NAME)));
                reportModels.add(reportModel);
            }
        }
        cursor.close();
        return reportModels;
    }
}
