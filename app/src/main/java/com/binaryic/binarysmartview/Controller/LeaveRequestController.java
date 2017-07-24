package com.binaryic.binarysmartview.Controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.binarysmartview.Common.InternetConnectionDetector;
import com.binaryic.binarysmartview.Common.MyApplication;
import com.binaryic.binarysmartview.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.binaryic.binarysmartview.Activity.MainActivity.pDialog;
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
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PURPOSE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_STATUS;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TITLE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TO_DATE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TO_TIME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TYPE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_IMAGE;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_ALL_USER_LEAVE;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_ALL_USER_MEETINGS;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_ALL_USER_SHIFT_CHANGE;
import static com.binaryic.binarysmartview.Common.Constants.URL_GET_ALL_USERS_LEAVE_DETAILS;
import static com.binaryic.binarysmartview.Common.Constants.URL_GET_ALL_USERS_MEETING_DETAILS;
import static com.binaryic.binarysmartview.Common.Constants.URL_GET_ALL_USERS_SHIFT_CHANGE_DETAILS;
import static com.binaryic.binarysmartview.Common.Constants.URL_UPDATE_LEAVE_ACCEPTANCE_STATUS;
import static com.binaryic.binarysmartview.Common.Constants.URL_UPDATE_MEETING_APPLICATION_ACCEPTANCE_STATUS;
import static com.binaryic.binarysmartview.Common.Constants.URL_UPDATE_SHIFT_CHANGE_ACCEPTANCE_STATUS;

/**
 * Created by minakshi on 5/12/16.
 */

public class LeaveRequestController {

    public static void getAllUsersLeaveDetailsApi(final Activity context) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_GET_ALL_USERS_LEAVE_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("LeaveController", " AllUsersLeaveDetails " + response_String);
                JSONObject response = null;

                try {
                    response = new JSONObject(response_String);
                    if (response.getString("Result").matches("1")) {
                        context.getContentResolver().delete(CONTENT_ALL_USER_LEAVE, null, null);
                        Log.e("LeaveController", " leavedata " + response.getJSONArray("leavedata"));
                        if (response.getJSONArray("leavedata") != null) {
                            JSONArray arrayleaveData = response.getJSONArray("leavedata");
                            for (int i = 0; i < arrayleaveData.length(); i++) {
                                JSONObject objectLeaveData = arrayleaveData.getJSONObject(i);
                                ContentValues values = new ContentValues();
                                String name[] = objectLeaveData.getString("name").split(" ");
                                switch (name.length) {
                                    case 2:
                                        values.put(COLUMN_NAME, name[0] + " " + name[1]);
                                        break;
                                    case 3:
                                        values.put(COLUMN_NAME, name[0] + " " + name[2]);
                                        break;
                                    case 4:
                                        values.put(COLUMN_NAME, name[0] + " " + name[3]);
                                        break;
                                }
                                values.put(COLUMN_DURATION, objectLeaveData.getString("leave_duration"));
                                values.put(COLUMN_USER_ID, objectLeaveData.getString("emp_code"));
                                values.put(COLUMN_FROM_DATE, objectLeaveData.getString("leave_from_date"));
                                values.put(COLUMN_TO_DATE, objectLeaveData.getString("leave_to_date"));
                                values.put(COLUMN_TYPE, objectLeaveData.getString("leave_type"));
                                values.put(COLUMN_EMAIL_ID, objectLeaveData.getString("username"));
                                values.put(COLUMN_STATUS, objectLeaveData.getString("acceptance_status"));
                                values.put(COLUMN_USER_IMAGE, objectLeaveData.getString("profile_pic"));
                                values.put(COLUMN_DATE_OF_APPLY, objectLeaveData.getString("apply_date"));
                                values.put(COLUMN_TITLE, objectLeaveData.getString("title"));
                                values.put(COLUMN_APPLICATION_ID, objectLeaveData.getString("application_id"));
                                String selection = COLUMN_APPLICATION_ID + " ='" + objectLeaveData.getString("application_id") + "'";

                                Cursor cursor = context.getContentResolver().query(CONTENT_ALL_USER_LEAVE, null, selection, null, null);
                                if (cursor.getCount() > 0) {
                                    context.getContentResolver().update(CONTENT_ALL_USER_LEAVE, values, selection, null);
                                } else {
                                    context.getContentResolver().insert(CONTENT_ALL_USER_LEAVE, values);
                                }
                                cursor.close();
                            }
                        }
                    }

                    getAllUsersMeetingDetailsApi(context);

                } catch (Exception e) {
                    Log.e("LeaveController", "AllUsersLeaveDetails error " + e.getMessage());
                    getAllUsersMeetingDetailsApi(context);

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
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "getAllUsersLeaveDetailsApi");
    }

    public static void getAllUsersShiftChangeDetailsApi(final Activity context) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_GET_ALL_USERS_SHIFT_CHANGE_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("LeaveController", " AllUsersShiftChangeDetails " + response_String);
                JSONObject response = null;

                try {
                    response = new JSONObject(response_String);
                    if (response.getString("Result").matches("1")) {
                        context.getContentResolver().delete(CONTENT_ALL_USER_SHIFT_CHANGE, null, null);
                        if (response.getJSONArray("shifttimedata") != null) {

                            JSONArray arrayShiftChangeData = response.getJSONArray("shifttimedata");
                            for (int i = 0; i < arrayShiftChangeData.length(); i++) {
                                JSONObject objectShiftChangeData = arrayShiftChangeData.getJSONObject(i);
                                ContentValues values = new ContentValues();
                                String name[] = objectShiftChangeData.getString("full_name").split(" ");
                                switch (name.length) {
                                    case 2:
                                        values.put(COLUMN_NAME, name[0] + " " + name[1]);
                                        break;
                                    case 3:
                                        values.put(COLUMN_NAME, name[0] + " " + name[2]);
                                        break;
                                    case 4:
                                        values.put(COLUMN_NAME, name[0] + " " + name[3]);
                                        break;
                                }
                                values.put(COLUMN_USER_ID, objectShiftChangeData.getString("empcode"));
                                values.put(COLUMN_EMAIL_ID, objectShiftChangeData.getString("username"));
                                values.put(COLUMN_STATUS, objectShiftChangeData.getString("accept_status"));
                                values.put(COLUMN_USER_IMAGE, objectShiftChangeData.getString("profile_pic"));
                                values.put(COLUMN_DATE_OF_APPLY, objectShiftChangeData.getString("applied_date"));
                                values.put(COLUMN_APPLICATION_ID, objectShiftChangeData.getString("application_id"));
                                values.put(COLUMN_CURRENT_IN, objectShiftChangeData.getString("current_in"));
                                values.put(COLUMN_CURRENT_OUT, objectShiftChangeData.getString("current_out"));
                                values.put(COLUMN_NEW_IN, objectShiftChangeData.getString("new_in"));
                                values.put(COLUMN_NEW_OUT, objectShiftChangeData.getString("new_out"));

                                String selection = COLUMN_APPLICATION_ID + " ='" + objectShiftChangeData.getString("application_id") + "'";

                                Cursor cursor = context.getContentResolver().query(CONTENT_ALL_USER_SHIFT_CHANGE, null, selection, null, null);
                                Log.e("LeaveController", "cursor = " + cursor);
                                if (cursor.getCount() > 0) {
                                    context.getContentResolver().update(CONTENT_ALL_USER_SHIFT_CHANGE, values, selection, null);
                                } else {
                                    context.getContentResolver().insert(CONTENT_ALL_USER_SHIFT_CHANGE, values);
                                }
                                cursor.close();
                            }
                        }


                    }
                    if (InternetConnectionDetector.isConnectingToInternet(context, true)) {
                        SendMessageController.allRegisterUsersApiCall(context);
                    }

                } catch (Exception e) {
                    Log.e("LeaveController", "AllUsersShiftChangeDetails error " + e.getMessage());
                    if (InternetConnectionDetector.isConnectingToInternet(context, true)) {
                        SendMessageController.allRegisterUsersApiCall(context);
                    }
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
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "AllUsersShiftChangeDetailsApi");
    }

    public static void updateLeaveAcceptanceStatusApi(final Activity context, final String applicationId, final String acceptanceValue) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_UPDATE_LEAVE_ACCEPTANCE_STATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("LeaveController", " updateLeaveAcceptanceStatus " + response_String);
                JSONObject response = null;

                try {
                    response = new JSONObject(response_String);
                    if (response.getString("Result").matches("1")) {

                    }

                } catch (Exception e) {
                    Log.e("LeaveController", "updateLeaveAcceptanceStatus error " + e.getMessage());
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
                params.put("status", acceptanceValue);
                params.put("id", applicationId);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "updateLeaveAcceptanceStatus");
    }

    public static void updateMeetingApplicationAcceptanceStatusApi(final Activity context, final String applicationId, final String acceptanceValue) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_UPDATE_MEETING_APPLICATION_ACCEPTANCE_STATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("LeaveController", " updateMeetingApplicationAcceptanceStatus " + response_String);
                JSONObject response = null;

                try {
                    response = new JSONObject(response_String);
                    if (response.getString("Result").matches("1")) {

                    }

                } catch (Exception e) {
                    Log.e("LeaveController", "updateMeetingApplicationAcceptanceStatus error " + e.getMessage());
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
                params.put("status", acceptanceValue);
                params.put("id", applicationId);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "updateMeetingApplicationAcceptanceStatus");
    }

    public static void updateShiftChangeAcceptanceStatusApi(final Activity context, final String applicationId, final String acceptanceValue) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_UPDATE_SHIFT_CHANGE_ACCEPTANCE_STATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("LeaveController", " updateShiftChangeAcceptanceStatus " + response_String);
                JSONObject response = null;

                try {
                    response = new JSONObject(response_String);
                    if (response.getString("Result").matches("1")) {

                    }

                } catch (Exception e) {
                    Log.e("LeaveController", "updateShiftChangeAcceptanceStatus error " + e.getMessage());
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
                params.put("status", acceptanceValue);
                params.put("id", applicationId);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "updateShiftChangeAcceptanceStatus");
    }

    public static void getAllUsersMeetingDetailsApi(final Activity context) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_GET_ALL_USERS_MEETING_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("LeaveController", " AllUsersMeetingDetails " + response_String);
                JSONObject response = null;

                try {
                    response = new JSONObject(response_String);
                    if (response.getString("Result").matches("1")) {
                        context.getContentResolver().delete(CONTENT_ALL_USER_MEETINGS, null, null);
                        if (response.getJSONArray("meetingdata") != null) {
                            JSONArray arrayMeetingData = response.getJSONArray("meetingdata");
                            for (int i = 0; i < arrayMeetingData.length(); i++) {
                                JSONObject objectMeetingData = arrayMeetingData.getJSONObject(i);
                                ContentValues values = new ContentValues();
                                String name[] = objectMeetingData.getString("full_name").split(" ");
                                switch (name.length) {
                                    case 2:
                                        values.put(COLUMN_NAME, name[0] + " " + name[1]);
                                        break;
                                    case 3:
                                        values.put(COLUMN_NAME, name[0] + " " + name[2]);
                                        break;
                                    case 4:
                                        values.put(COLUMN_NAME, name[0] + " " + name[3]);
                                        break;
                                }
                                values.put(COLUMN_APPLICATION_ID, objectMeetingData.getString("application_id"));
                                values.put(COLUMN_USER_ID, objectMeetingData.getString("empcode"));
                                values.put(COLUMN_FROM_TIME, objectMeetingData.getString("meeeing_start_time"));
                                values.put(COLUMN_TO_TIME, objectMeetingData.getString("meeeing_end_time"));
                                values.put(COLUMN_TITLE, objectMeetingData.getString("title"));
                                values.put(COLUMN_PURPOSE, objectMeetingData.getString("purpose"));
                                values.put(COLUMN_EMAIL_ID, objectMeetingData.getString("user_name"));
                                values.put(COLUMN_STATUS, objectMeetingData.getString("application_status"));
                                //values.put(COLUMN_USER_IMAGE, objectMeetingData.getString("days"));
                                values.put(COLUMN_DATE_OF_APPLY, objectMeetingData.getString("meeting_date"));

                                String selection = COLUMN_APPLICATION_ID + " ='" + objectMeetingData.getString("application_id") + "'";

                                Cursor cursor = context.getContentResolver().query(CONTENT_ALL_USER_MEETINGS, null, selection, null, null);
                                Log.e("LeaveController", "cursor = " + cursor);
                                if (cursor.getCount() > 0) {
                                    context.getContentResolver().update(CONTENT_ALL_USER_MEETINGS, values, selection, null);
                                } else {
                                    context.getContentResolver().insert(CONTENT_ALL_USER_MEETINGS, values);
                                }
                                cursor.close();
                            }
                        }
                    }


                    getAllUsersShiftChangeDetailsApi(context);
                } catch (Exception e) {
                    Log.e("LeaveController", "AllUsersMeetingDetails error " + e.getMessage());
                    getAllUsersShiftChangeDetailsApi(context);
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
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "getAllUsersLeaveDetailsApi");
    }

    public static void openLeaveRequestDialog(final Activity context, final String applicationId, String name, String requestedDate, String fromDate, String toDate, String title, int leaveStatus) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_open_leave_equest);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final LinearLayout ll_AcceptRejectButtons = (LinearLayout) dialog.findViewById(R.id.ll_AcceptRejectButtons);
        final TextView tv_Name = (TextView) dialog.findViewById(R.id.tv_Name);
        final TextView tv_RequestDate = (TextView) dialog.findViewById(R.id.tv_RequestDate);
        final TextView tv_Reason = (TextView) dialog.findViewById(R.id.tv_Reason);
        final TextView tv_FromDate = (TextView) dialog.findViewById(R.id.tv_FromDate);
        final TextView tv_ToDate = (TextView) dialog.findViewById(R.id.tv_ToDate);
        TextView tv_DialogStatus = (TextView) dialog.findViewById(R.id.tv_DialogStatus);
        Button bt_DialogReject = (Button) dialog.findViewById(R.id.bt_DialogReject);
        Button bt_DialogAccept = (Button) dialog.findViewById(R.id.bt_DialogAccept);
        tv_Name.setText(name);
        tv_RequestDate.setText(requestedDate);
        tv_Reason.setText(title);
        tv_FromDate.setText(fromDate);
        tv_ToDate.setText(toDate);
        //0 accepted ,1 for rejected ,2 for not yet selected
        switch (leaveStatus) {
            case 0:
                ll_AcceptRejectButtons.setVisibility(View.GONE);
                tv_DialogStatus.setText("Accepted");
                break;
            case 1:
                ll_AcceptRejectButtons.setVisibility(View.GONE);
                tv_DialogStatus.setText("Rejected");
                break;
            case 2:
                ll_AcceptRejectButtons.setVisibility(View.VISIBLE);
                break;
        }

        bt_DialogAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                LeaveRequestController.updateLeaveAcceptanceStatusApi(context, applicationId, "approve");
            }
        });

        bt_DialogReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                LeaveRequestController.updateLeaveAcceptanceStatusApi(context, applicationId, "disapprove");

            }
        });
        dialog.show();
    }

    public static void openMeetingApplicationDialog(final Activity context, final String applicationId, String name, String requestedDate, String fromDate, String toDate, String title, int leaveStatus) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_open_meeting_equest);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final LinearLayout ll_AcceptRejectButtons = (LinearLayout) dialog.findViewById(R.id.ll_AcceptRejectButtons);
        final TextView tv_Name = (TextView) dialog.findViewById(R.id.tv_Name);
        final TextView tv_RequestDate = (TextView) dialog.findViewById(R.id.tv_RequestDate);
        final TextView tv_Reason = (TextView) dialog.findViewById(R.id.tv_Reason);
        final TextView tv_FromDate = (TextView) dialog.findViewById(R.id.tv_FromDate);
        final TextView tv_ToDate = (TextView) dialog.findViewById(R.id.tv_ToDate);
        TextView tv_DialogStatus = (TextView) dialog.findViewById(R.id.tv_DialogStatus);
        Button bt_DialogReject = (Button) dialog.findViewById(R.id.bt_DialogReject);
        Button bt_DialogAccept = (Button) dialog.findViewById(R.id.bt_DialogAccept);
        tv_Name.setText(name);
        tv_RequestDate.setText(requestedDate);
        tv_Reason.setText(title);
        tv_FromDate.setText(fromDate);
        tv_ToDate.setText(toDate);
        //0 accepted ,1 for rejected ,2 for not yet selected
        switch (leaveStatus) {
            case 0:
                ll_AcceptRejectButtons.setVisibility(View.GONE);
                tv_DialogStatus.setText("Accepted");
                break;
            case 1:
                ll_AcceptRejectButtons.setVisibility(View.GONE);
                tv_DialogStatus.setText("Rejected");
                break;
            case 2:
                ll_AcceptRejectButtons.setVisibility(View.VISIBLE);
                break;
        }

        bt_DialogAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                LeaveRequestController.updateMeetingApplicationAcceptanceStatusApi(context, applicationId, "approve");
            }
        });

        bt_DialogReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                LeaveRequestController.updateMeetingApplicationAcceptanceStatusApi(context, applicationId, "disapprove");

            }
        });
        dialog.show();
    }

    public static void openShiftChangeRequestDialog(final Activity context, final String applicationId, String name, String requestedDate, String newInTime, String newOutTime, int leaveStatus) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_open_shift_shange_request);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final LinearLayout ll_AcceptRejectButtons = (LinearLayout) dialog.findViewById(R.id.ll_AcceptRejectButtons);
        final TextView tv_Name = (TextView) dialog.findViewById(R.id.tv_Name);
        final TextView tv_RequestDate = (TextView) dialog.findViewById(R.id.tv_RequestDate);
        ;
        final TextView tv_FromTime = (TextView) dialog.findViewById(R.id.tv_FromTime);
        final TextView tv_ToTime = (TextView) dialog.findViewById(R.id.tv_ToTime);
        TextView tv_DialogStatus = (TextView) dialog.findViewById(R.id.tv_DialogStatus);
        Button bt_DialogReject = (Button) dialog.findViewById(R.id.bt_DialogReject);
        Button bt_DialogAccept = (Button) dialog.findViewById(R.id.bt_DialogAccept);
        tv_Name.setText(name);
        tv_RequestDate.setText(requestedDate);
        tv_FromTime.setText(newInTime);
        tv_ToTime.setText(newOutTime);
        //0 accepted ,1 for rejected ,2 for not yet selected
        switch (leaveStatus) {
            case 0:
                ll_AcceptRejectButtons.setVisibility(View.GONE);
                tv_DialogStatus.setText("Accepted");
                break;
            case 1:
                ll_AcceptRejectButtons.setVisibility(View.GONE);
                tv_DialogStatus.setText("Rejected");
                break;
            case 2:
                ll_AcceptRejectButtons.setVisibility(View.VISIBLE);
                break;
        }

        bt_DialogAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                LeaveRequestController.updateShiftChangeAcceptanceStatusApi(context, applicationId, "approve");
            }
        });

        bt_DialogReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                LeaveRequestController.updateShiftChangeAcceptanceStatusApi(context, applicationId, "disapprove");
            }
        });
        dialog.show();
    }

}
