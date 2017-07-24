package com.binaryic.binarysmartview.Receivers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.binaryic.binarysmartview.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;

/**
 * Created by user on 9/5/2016.
 */
public class FirebaseTokenId extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {

        String recent_Token = FirebaseInstanceId.getInstance().getToken();
        Log.e("recent_Token", "==" + recent_Token);

        sendRegistrationToServer(recent_Token);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.fcf_Preference), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.fcf_Token), recent_Token);
        editor.commit();

    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        SendBird.registerPushTokenForCurrentUser(token, new SendBird.RegisterPushTokenWithStatusHandler() {
            @Override
            public void onRegistered(SendBird.PushTokenRegistrationStatus pushTokenRegistrationStatus, SendBirdException e) {
                if (e != null) {
                    Toast.makeText(FirebaseTokenId.this, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pushTokenRegistrationStatus == SendBird.PushTokenRegistrationStatus.PENDING) {
                    Toast.makeText(FirebaseTokenId.this, "Connection required to register push token.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
