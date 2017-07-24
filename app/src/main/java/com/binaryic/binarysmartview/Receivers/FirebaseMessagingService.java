package com.binaryic.binarysmartview.Receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.binaryic.binarysmartview.Activity.GroupListActivity;
import com.binaryic.binarysmartview.Activity.MainActivity;
import com.binaryic.binarysmartview.Controller.GroupController;
import com.binaryic.binarysmartview.R;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{
    ArrayList<String> msgList = new ArrayList<>();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("remoteMessagegetData","=="+remoteMessage.getData());
        Log.e("getData","=="+remoteMessage.getData().get("message"));
        Log.e("notify_type","=="+remoteMessage.getData().get("notify_type"));
        Log.e("notify_type","=="+remoteMessage.getData().get("notify_type"));
        Log.e("announcement_data","=="+remoteMessage.getData().get("announcement_data"));
      //  Log.e("getNotification","=="+remoteMessage.getNotification().getTitle());
        showNotification(remoteMessage.getData().get("birthday_person"),remoteMessage.getData().get("message"));


        //chat
        String channelUrl = null;
        try {
            JSONObject sendBird = new JSONObject(remoteMessage.getData().get("sendbird"));
            JSONObject channel = (JSONObject) sendBird.get("channel");
            channelUrl = (String) channel.get("channel_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendNotification(this, remoteMessage.getData().get("message"), channelUrl);

    }

    public void sendNotification(Context context, String messageBody, String channelUrl) {
        Intent intent = new Intent(context, GroupListActivity.class);
        GroupController.changeStatus(context, channelUrl, "true");


        Log.e("messageBody==", messageBody);
        msgList.add(messageBody);
        String[] nickname = messageBody.split(":");
        intent.putExtra("groupChannel", channelUrl);
        intent.putExtra("nickname", nickname[0]);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void showNotification(String title,String message) {

        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }


}
