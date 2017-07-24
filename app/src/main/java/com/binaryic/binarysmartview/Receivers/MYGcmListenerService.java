/*
package com.binaryic.binarysmartview.Receivers;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

*/
/**
 * Created by Administrator on 1/12/2016.
 *//*


public class MYGcmListenerService extends GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle data) {

        Log.e("data","=="+data);
        Log.e("datadata","=="+data.toString());
        Log.e("from","=="+from.toString());
      String  message = data.getString("message");
        Log.e("MESSS", "From: " + from);
        Log.e("MESSS", "Message: " + message);
        //Toast.makeText(getApplicationContext(),"Message received ."+data.toString(),Toast.LENGTH_SHORT).show();

        */
/**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         * 	- Store message in local database.
         * 	- Update UI.
         *//*


        */
/**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         *//*

        Log.e("MESSAAGE", "" + message);
        //  sendNotification(message);
    }
}

*/
