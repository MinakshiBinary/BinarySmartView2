package com.binaryic.binarysmartview.Controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.binarysmartview.Activity.SendMessageActivity;
import com.binaryic.binarysmartview.Common.CommonFunction;
import com.binaryic.binarysmartview.Common.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DEPARTMENT_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DEPARTMENT_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_ID;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_DEPARMENT_INFO;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_REGISTER_USER;
import static com.binaryic.binarysmartview.Common.Constants.URL_GET_ALL_REGISTER_USERS;
import static com.binaryic.binarysmartview.Common.Constants.URL_GET_DEPARTMENT_INFO;
import static com.binaryic.binarysmartview.Common.Constants.URL_SEND_MESSAGE;

/**
 * Created by minakshi on 3/12/16.
 */

public class SendMessageController {

    public static void allRegisterUsersApiCall(final Activity context) {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading..!!!!");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_GET_ALL_REGISTER_USERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("SendMessageController", " RegisterUsersResponce " + response_String);
                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);
                    if (response.getString("Result").matches("1")) {
                        context.getContentResolver().delete(CONTENT_REGISTER_USER, null, null);
                        JSONArray userDataArray = response.getJSONArray("userdata");
                        for (int i = 0; i < userDataArray.length(); i++) {
                            JSONObject userDataObject = userDataArray.getJSONObject(i);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(COLUMN_USER_ID, userDataObject.getString("ecode"));
                            contentValues.put(COLUMN_EMAIL_ID, userDataObject.getString("username"));
                            contentValues.put(COLUMN_NAME, userDataObject.getString("full_name"));
                            String selection = COLUMN_USER_ID + "= '" + userDataObject.getString("ecode") + "'";

                            Cursor cursor = context.getContentResolver().query(CONTENT_REGISTER_USER, null, selection, null, null);
                            if (cursor.getCount() > 0) {
                                context.getContentResolver().update(CONTENT_REGISTER_USER, contentValues, selection, null);
                            } else {
                                context.getContentResolver().insert(CONTENT_REGISTER_USER, contentValues);
                            }
                            cursor.close();
                        }

                    } else {
                        //CommonFunction.createSnackbar(,"");
                    }
                    pDialog.dismiss();

                } catch (Exception e) {
                    Log.e("SendMessageController", "allRegisterUsersApiCall error " + e.getMessage());
                }
            }
        }

                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SendMessageController", "error==" + error.getLocalizedMessage());
                Log.e("SendMessageController", "error==" + error);
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    if (response.statusCode == 400) {
                    } else {
                    }
                } else {
                }
                pDialog.hide();
            }
        }

        )

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        MyApplication.getInstance().

                addToRequestQueue(strReq, "getAllRegisterUsers");
    }

    public static void allDepartmentsApiCall(final Activity context) {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading..!!!!");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_GET_DEPARTMENT_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("SendMessageController", " allDepartmentsApiCallResponce " + response_String);
                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);
                    if (response.getString("Result").matches("1")) {
                        context.getContentResolver().delete(CONTENT_DEPARMENT_INFO, null, null);
                        JSONArray departmentArray = response.getJSONArray("dept_names");
                        for (int i = 0; i < departmentArray.length(); i++) {
                            JSONObject departmentObject = departmentArray.getJSONObject(i);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(COLUMN_DEPARTMENT_ID, departmentObject.getString("department_master_id"));
                            contentValues.put(COLUMN_DEPARTMENT_NAME, departmentObject.getString("department_name"));
                            String selection = COLUMN_DEPARTMENT_NAME + "= '" + departmentObject.getString("department_name") + "'";
                            Cursor cursor = context.getContentResolver().query(CONTENT_DEPARMENT_INFO, null, selection, null, null);
                            if (cursor.getCount() > 0) {
                                context.getContentResolver().update(CONTENT_DEPARMENT_INFO, contentValues, selection, null);
                            } else {
                                context.getContentResolver().insert(CONTENT_DEPARMENT_INFO, contentValues);
                            }
                            cursor.close();
                        }
                        context.startActivity(new Intent(context, SendMessageActivity.class));
                    } else {
                        //CommonFunction.createSnackbar(,"");
                    }
                    pDialog.dismiss();

                } catch (Exception e) {
                    Log.e("SendMessageController", "allDepartmentsApiCall error " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SendMessageController", "error==" + error.getLocalizedMessage());
                Log.e("SendMessageController", "error==" + error);
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
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "allDepartmentsApiCall");
    }

    public static void sendMessageApiCall(final Activity context, final View view, final String receiver_type, final String receiver_id, final String title, final String message) {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading..!!!!");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_SEND_MESSAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("SendMessageController", " sendMessageApiCallResponce " + response_String);
                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);
                    if (response.getString("Result").matches("1")) {
                        CommonFunction.createSnackbar(view, "Message Send Successfully");

                        /*context.getContentResolver().delete(CONTENT_DEPARMENT_INFO, null, null);
                        JSONArray departmentArray = response.getJSONArray("dept_names");
                        for (int i = 0; i < departmentArray.length(); i++) {
                            JSONObject departmentObject = departmentArray.getJSONObject(i);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(COLUMN_DEPARTMENT_ID, departmentObject.getString("department_name"));
                            contentValues.put(COLUMN_DEPARTMENT_NAME, departmentObject.getString("department_name"));
                            String selection = COLUMN_DEPARTMENT_NAME + "= '" + departmentObject.getString("department_name") + "'";
                            Cursor cursor = context.getContentResolver().query(CONTENT_DEPARMENT_INFO, null, selection, null, null);
                            if (cursor.getCount() > 0) {
                                context.getContentResolver().update(CONTENT_DEPARMENT_INFO, contentValues, selection, null);
                            } else {
                                context.getContentResolver().insert(CONTENT_DEPARMENT_INFO, contentValues);
                            }
                            cursor.close();
                        }
                        context.startActivity(new Intent(context, SendMessageActivity.class));
                   */
                    } else {
                        //CommonFunction.createSnackbar(,"");
                    }

                    pDialog.dismiss();
                } catch (Exception e) {
                    Log.e("SendMessageController", "sendMessageApiCall error " + e.getMessage());
                    if (pDialog.isShowing()) {
                        pDialog.dismiss();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SendMessageController", "error==" + error.getLocalizedMessage());
                Log.e("SendMessageController", "error==" + error);
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
                params.put("receiver", receiver_type);
                params.put("id", receiver_id);
                params.put("title", title);
                params.put("msg", message);
                Log.e("SendMessageController", "params==" + params.toString());

                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "sendMessageApiCall");
    }

}
