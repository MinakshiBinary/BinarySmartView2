package com.binaryic.binarysmartview.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.binarysmartview.Common.AlertDialogNegative;
import com.binaryic.binarysmartview.Common.InternetConnectionDetector;
import com.binaryic.binarysmartview.Common.MyApplication;
import com.binaryic.binarysmartview.Common.Validation;
import com.binaryic.binarysmartview.Controller.ReportingController;
import com.binaryic.binarysmartview.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_CL;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DATE_OF_BIRTH;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DATE_OF_JOINING;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_GRADUATION;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_MOBILE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PASSWORD;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PERSONAL_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PL;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_SL;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_DESIGNATION;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_IMAGE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_LOGIN_STATUS;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_USER_INFO;
import static com.binaryic.binarysmartview.Common.Constants.URL_GET_FORGOT_PASSWORD;
import static com.binaryic.binarysmartview.Common.Constants.URL_GET_LOGIN_DATA;


public class LoginActivity extends AppCompatActivity {
    public static final int STARTUP_DELAY = 100;
    public static final int ANIM_ITEM_DURATION = 2000;
    public static final int EDITTEXT_DELAY = 300;
    public static final int BUTTON_DELAY = 100;
    public static final int VIEW_DELAY = 600;
    private ViewGroup rl_Container;
    private ImageView iv_Logo;
    private Button bt_DialogSubmit;
    private Button bt_DialogCancel;
    private EditText et_DialogEmail;
    private EditText et_UserEmailId;
    private EditText et_Password;
    private EditText et_Phone;
    private TextView tv_ForgotPassword;
    private Button bt_SignIn;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_forgot_password);
        bt_DialogCancel = (Button) dialog.findViewById(R.id.bt_DialogCancel);
        bt_DialogSubmit = (Button) dialog.findViewById(R.id.bt_DialogSubmit);
        et_DialogEmail = (EditText) dialog.findViewById(R.id.et_DialogEmail);

        rl_Container = (ViewGroup) findViewById(R.id.rl_Container);
        iv_Logo = (ImageView) findViewById(R.id.iv_Logo);
        et_UserEmailId = (EditText) findViewById(R.id.et_UserEmailId);
        et_Password = (EditText) findViewById(R.id.et_Password);
        et_Phone = (EditText) findViewById(R.id.et_Phone);
        tv_ForgotPassword = (TextView) findViewById(R.id.tv_ForgotPassword);
        bt_SignIn = (Button) findViewById(R.id.bt_SignIn);

        ViewCompat.animate(iv_Logo)
                .translationY(-250)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();

        for (int i = 0; i < rl_Container.getChildCount(); i++) {
            View v = rl_Container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            if (v instanceof EditText) {
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((EDITTEXT_DELAY * i) + 500)
                        .setDuration(500);
            } else if (v instanceof Button) {
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((BUTTON_DELAY * i) + 500)
                        .setDuration(500);
            } else if (v instanceof TextView) {
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((BUTTON_DELAY * i) + 500)
                        .setDuration(500);
            } else {
                viewAnimator = ViewCompat.animate(v)
                        .translationY(50).alpha(1)
                        .setStartDelay((VIEW_DELAY * i) + 500)
                        .setDuration(1000);
            }

            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();

        }
        tv_ForgotPassword.setVisibility(View.VISIBLE);
        bt_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation.logIn(LoginActivity.this, et_UserEmailId.getText().toString(), et_Password.getText().toString())) {
                    if (InternetConnectionDetector.isConnectingToInternet(LoginActivity.this, true)) {

                        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                                Manifest.permission.READ_SMS)
                                != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                                    Manifest.permission.READ_SMS)) {
                                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_SMS}, 0);
                            } else {
                                ActivityCompat.requestPermissions(LoginActivity.this,
                                        new String[]{Manifest.permission.READ_SMS}, 0);
                            }
                        } else
                            loginApiCall(et_UserEmailId.getText().toString(), et_Password.getText().toString());
                    }
                }
            }
        });

        tv_ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        bt_DialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        bt_DialogSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validation.changePassword(LoginActivity.this, et_DialogEmail.getText().toString())) {
                    if (InternetConnectionDetector.isConnectingToInternet(LoginActivity.this, true)) {
                        dialog.dismiss();
                        forgotPasswordApiCall();
                    }
                } else {
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loginApiCall(et_UserEmailId.getText().toString(), et_Password.getText().toString());
            } else
                Toast.makeText(LoginActivity.this, "Please accept permission for go ahead.", Toast.LENGTH_LONG).show();
        }
    }

    private void loginApiCall(final String emailID, final String password) {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Checking Credentials..!!!!");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_GET_LOGIN_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("LoginActivity", " loginResponce " + response_String);
                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);
                    if (response.getString("Result").matches("1")) {

                        JSONObject userInfoObject = response.getJSONObject("Login_Data");
                        ContentValues contentValues = new ContentValues();
                        String name[] = userInfoObject.getString("full_name").split(" ");
                        switch (name.length) {
                            case 2:
                                contentValues.put(COLUMN_NAME, name[0] + " " + name[1]);
                                break;
                            case 3:
                                contentValues.put(COLUMN_NAME, name[0] + " " + name[2]);
                                break;
                            case 4:
                                contentValues.put(COLUMN_NAME, name[0] + " " + name[3]);
                                break;
                        }

                        contentValues.put(COLUMN_EMAIL_ID, userInfoObject.getString("user_name"));
                        contentValues.put(COLUMN_USER_ID, userInfoObject.getString("emp_code"));
                        contentValues.put(COLUMN_DATE_OF_JOINING, userInfoObject.getString("join_date"));
                        contentValues.put(COLUMN_USER_DESIGNATION, userInfoObject.getString("designation_name"));
                        contentValues.put(COLUMN_MOBILE, userInfoObject.getString("mob_no"));
                        contentValues.put(COLUMN_PERSONAL_EMAIL_ID, userInfoObject.getString("personal_email_id"));
                        contentValues.put(COLUMN_USER_IMAGE, userInfoObject.getString("Image"));
                        contentValues.put(COLUMN_GRADUATION, userInfoObject.getString("education"));
                        contentValues.put(COLUMN_DATE_OF_BIRTH, userInfoObject.getString("dob"));
                        // contentValues.put(COLUMN_USER_LOGIN_STATUS, "1");
                        contentValues.put(COLUMN_PASSWORD, et_Password.getText().toString().trim());
                        contentValues.put(COLUMN_SL, userInfoObject.getString("sl"));
                        contentValues.put(COLUMN_CL, userInfoObject.getString("cl"));
                        contentValues.put(COLUMN_PL, userInfoObject.getString("pl"));


                        //remove this code when otp starts work
                        contentValues.put(COLUMN_USER_LOGIN_STATUS, "1");

                        String selection = COLUMN_EMAIL_ID + " ='" + userInfoObject.getString("user_name") + "' ";
                        Cursor cursor = getContentResolver().query(CONTENT_USER_INFO, null, selection, null, null);
                        if (cursor.getCount() > 0) {
                            getContentResolver().update(CONTENT_USER_INFO, contentValues, selection, null);
                        } else {
                            getContentResolver().insert(CONTENT_USER_INFO, contentValues);
                        }
                        cursor.close();
                        // MyApplication.phone_Number_To_Verify = phone;


                        //add this code when otp starts work
                       /* Intent i = new Intent(getApplicationContext(), OtpActivity.class);
                        startActivity(i);*/


                        //remove this code when otp starts work
                        ReportingController.reportingApi(LoginActivity.this,"Login Activity",userInfoObject.getString("user_name"),userInfoObject.getString("emp_code"));
                        startActivity(new Intent(LoginActivity.this, IntroActivity.class));
                        finish();
                    } else {
                        AlertDialogNegative.showAlertDialog(LoginActivity.this, "Sorry..!!!!", "Please Check Your Credintials..");
                    }
                    pDialog.dismiss();

                } catch (Exception e) {
                    Log.e("Main Activity", "loginApiCall error " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("errorrrrr", "==" + error.getLocalizedMessage());
                Log.e("errorrrrr", "==" + error);
                NetworkResponse response = error.networkResponse;
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
                params.put("password", password);
                Log.e("paramssss", "===" + params.toString());

                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "attendance");
    }

    private void forgotPasswordApiCall() {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_GET_FORGOT_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("Main activity", " Responce " + response_String);

                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);

                    if (response.getString("Result").equalsIgnoreCase("1")) {
                        Toast.makeText(LoginActivity.this, "Password has been sent to your email id ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Main Activity", " error " + e.getMessage());
                }
                pDialog.hide();
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
                pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email ", et_DialogEmail.getText().toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "attendance");
    }
}
