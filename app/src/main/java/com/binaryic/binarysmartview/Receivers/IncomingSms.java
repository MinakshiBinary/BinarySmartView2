package com.binaryic.binarysmartview.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by comp3 on 11/21/2015.
 */
public class IncomingSms extends BroadcastReceiver {
    public static String[] otp;
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {
                String message = "";
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    message = currentMessage.getDisplayMessageBody();

                    Log.e("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);


                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    // Toast toast = Toast.makeText(context, "senderNum: " + senderNum + ", message: " + message, duration);
                    //  toast.show();

                } // end for loop
                if (message.contains("OTP IS :")) {

                    otp = message.split(":");

                    Log.e("otp[1]otp[1]otp[1]", "==" + otp[1]);
                   // OtpActivity.et_OTP.setText(otp[1].trim());

                }
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e.getMessage());

        }
    }
}