package com.binaryic.binarysmartview.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.binaryic.binarysmartview.Common.Constants.CONTENT_ATTENDECE_INFO;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_UPCOMING_BIRTHDAYS;

public class TimeChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

       // Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
        context.getContentResolver().delete(CONTENT_UPCOMING_BIRTHDAYS, null, null);
        context.getContentResolver().delete(CONTENT_ATTENDECE_INFO, null, null);
        //Do whatever you need to
    }

}