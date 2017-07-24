package com.binaryic.binarysmartview.Common;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sendbird.android.SendBird;


/**
 * Created by User on 04-02-16.
 */
public class MyApplication extends Application {
    public static SharedPreferences setting;
    public static String phone_Number_To_Verify = "";
    public static String Url;
    private static MyApplication mInstance;
    private RequestQueue mRequestQueue;
    public static final String TAG = MyApplication.class.getSimpleName();
    public static final String SalaryUrl = "http://www.binaryic.com/intranet/salary_pdf/BINARY-SALARY-SLIP.php?";
    public static Bitmap imageCover = null;
    //chat
    private static final String APP_ID = "C613D47E-1076-48D7-8793-55CC531EB905"; // US-1 Demo


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        setting = getSharedPreferences("BinarysmartView", MODE_PRIVATE);

        SendBird.init(APP_ID, getApplicationContext());
        FirebaseApp.initializeApp(getApplicationContext());
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();

        Fresco.initialize(this);

    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        RetryPolicy policy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        getRequestQueue().add(req);
    }

    public static String GetHour(String time) {
        String hour = "";
        String minute = "";
        String ex = "";
        if (!time.equals("")) {
            if (!time.equals("00:00:00")) {
                String h = time.split(":")[0];

                if (!h.equals("")) {
                    if (Integer.parseInt(h) > 12) {
                        hour = Integer.parseInt(h) - 12 > 9 ? (Integer.parseInt(h) - 12) + "" : "0" + (Integer.parseInt(h) - 12);
                        ex = "PM";
                    } else {
                        hour = Integer.parseInt(h) > 9 ? (Integer.parseInt(h)) + "" : "0" + (Integer.parseInt(h));
                        ex = "AM";
                    }
                }
                if (!time.split(":")[1].equals(""))
                    minute = Integer.parseInt(time.split(":")[1]) > 9 ? time.split(":")[1] : "0" + time.split(":")[1];
                return hour + ":" + minute + " " + ex;
            } else
                return "-";
        } else
            return "-";

    }
}