package com.binaryic.binarysmartview.Activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.binarysmartview.Common.CommonFunction;
import com.binaryic.binarysmartview.Common.MyApplication;
import com.binaryic.binarysmartview.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_MOBILE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_LOGIN_STATUS;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_USER_INFO;
import static com.binaryic.binarysmartview.Common.Constants.URL_CHECK_OTP;


public class OtpActivity extends AppCompatActivity {
    public static EditText et_OTP;
    public static SpotsDialog spotsDialog;
    public SpotsDialog progressDialog;
    private String TOKEN;
  //  private GoogleCloudMessaging gcmObj;
    private Button bt_Submit;
    private RelativeLayout rl_Container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        bt_Submit = (Button) findViewById(R.id.bt_Submit);
        et_OTP = (EditText) findViewById(R.id.et_OTP);
        rl_Container = (RelativeLayout) findViewById(R.id.rl_Container);

        // new GetGcmToken().execute();

        bt_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                    Log.e("OtpActivity", "otp ==" + et_OTP.getText().toString().trim());
                    verifyOtp(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE)), et_OTP.getText().toString().trim());
                }
            }
        });
       /* tv_OTP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("OTPACTIVITY", "Editable ==" + s);
                Cursor cursor = getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                    verifyOtp(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE)), tv_OTP.getText().toString());
                }
            }
        });*/
    }


 /*  public void verifyOTP(String email, String contact_no, String otp) {
        if (InternetConnectionDetector.isConnectingToInternet(this,true)) {
            progressDialog = new SpotsDialog(this, "Check OTP...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            Log.e("emailemail", "==" + email);
            Log.e("access_tocess_token", "==" + MyApplication.setting.getString("access_token", "no_access_token"));
            Log.e("contact_nocontact_no", "==" + contact_no);
            Log.e("otpotpotp", "==" + otp);
            String url = "http://www.binaryic.com/IntranetAPI/otpverify.php";
            List<Pair<String, String>> paramslist = new ArrayList<>();
            paramslist.add(new Pair<String, String>("email", email));
            paramslist.add(new Pair<String, String>("contact_no", contact_no));
            paramslist.add(new Pair<String, String>("otp", otp));
            paramslist.add(new Pair<String, String>("access_token", MyApplication.setting.getString("access_token", "no_access_token")));
            paramslist.add(new Pair<String, String>("user_id", MyApplication.setting.getString("customerId", "")));
            AsyncReuse asyncReuse = new AsyncReuse(OtpActivity.this, url, paramslist, false);
            asyncReuse.jsonResponse = this;
            asyncReuse.execute();
        }
    }*/

  /*  private class GetGcmToken extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {


            InstanceID instanceID = InstanceID.getInstance(OtpActivity.this);

            Log.e("INSTANCE", "" + instanceID);




         *//*   try {
                if (gcmObj == null) {
                    gcmObj = GoogleCloudMessaging
                            .getInstance(getApplicationContext());
                }
                regId = gcmObj
                        .register("958057817378");
                msg = "Registration ID :" + regId;

            } catch (IOException ex) {
                msg = "Error :" + ex.getMessage();
            }*//*


            try {
                TOKEN = instanceID.getToken("958057817378", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);


                Log.e("TOKENNN", "" + TOKEN);


            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.e("EXCEPTION", "" + e.getMessage());
                //Toast.makeText(getApplicationContext(), "EX  "+e.getMessage(), 800).show();

                e.printStackTrace();
            }

            return null;
        }
    }*/


    private void verifyOtp(final String email, final String contact_no, final String otp) {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Check OTP...");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_CHECK_OTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("verifyotp", " verifyotp " + response_String);
                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);
                    if (response.getString("message").matches("Verification success")) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(COLUMN_USER_LOGIN_STATUS, "1");
                        String selection = COLUMN_EMAIL_ID + " ='" + email + "' ";
                        Cursor cursor = getContentResolver().query(CONTENT_USER_INFO, null, selection, null, null);
                        if (cursor.getCount() > 0) {
                            getContentResolver().update(CONTENT_USER_INFO, contentValues, selection, null);
                        }
                        finish();
                        startActivity(new Intent(OtpActivity.this, IntroActivity.class));
                    } else {
                        CommonFunction.createSnackbar(rl_Container, "Wrong OTP");
                    }
                    pDialog.dismiss();

                } catch (Exception e) {
                    Log.e("otp Activity", "verifyotp error " + e.getMessage());
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
                params.put("otp", otp);
                params.put("phone", contact_no);
                params.put("email", email);

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.fcf_Preference), Context.MODE_PRIVATE);
                String token = sharedPreferences.getString(getString(R.string.fcf_Token), "");
                params.put("tokenid", token);
                params.put("deviceid", getDeviceId());

                Log.e("otpactivity", "params==" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "attendance");
    }

   /* private void GetRegistedIdGCM() {
        GCMClientManager pushClientManager = new GCMClientManager(this, getResources().getString(R.string.uuid));
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                UpdateUuid(registrationId, deviceId);
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
                String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                UpdateUuid(MyApplication.setting.getString("uuid", ""), deviceId);
            }
        });

    }*/

   /* @Override
    public void GetData(JSONObject response) {
        Log.e("OTPACTIVITY", "response ==" + response);
        try {
            progressDialog.dismiss();
            if (response.getString("status").matches("2")) {
                MyApplication.setting.edit().putBoolean("isLogin", true).commit();
                MyApplication.setting.edit().putString("customerId", response.getString("user_id")).commit();
                GetRegistedIdGCM();
                finish();
                startActivity(new Intent(OtpActivity.this, HomeActivity.class));
            }else{
                Util.showMessageBox("Oops !", "your otp does not match", "OK", "", false, OtpActivity.this);
            }
        } catch (Exception e) {
            Log.e("OTPACTIVITY", "errorrrr ==" + e.getMessage());
        }

    }*/


/*
    private void UpdateUuid(final String uuid, final String deviceId) {
        String url = "http://mobileapps.fashionic.in/uuid/manage";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getBoolean("success")) {
                        MyApplication.setting.edit().putString("uuid", uuid).commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("access_token", MyApplication.setting.getString("access_token", "no_access_token"));
                params.put("user_id", MyApplication.setting.getString("customerId", ""));
                params.put("uuid", uuid);
                params.put("device_id", deviceId);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, "SaveUuid");
    }
*/

    private String getDeviceId() {
        return Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
