package com.binaryic.binarysmartview.Receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.binaryic.binarysmartview.Activity.MainActivity;
import com.binaryic.binarysmartview.R;

import java.util.Calendar;

/**
 * Created by admin on 10/27/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean notification;
        int weekOfMonth;
        int dayOfWeek;

        Log.e("AlarmReceiver", "AlarmReceiver");
        Intent myIntent = new Intent(context, MainActivity.class);
        Calendar calendar = Calendar.getInstance();
        weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (weekOfMonth != 2 || weekOfMonth != 4) {
            if (dayOfWeek != 1) {
                notification = true;
            } else {
                notification = false;
            }
        } else {
            if (dayOfWeek != 1 || dayOfWeek != 7) {
                notification = true;
            } else {
                notification = false;
            }
        }

        if (notification) {
            Notification.Builder mBuilder =
                    new Notification.Builder(context)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle("Logout Reminder")
                            .setContentText("This is time to do logout");
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, myIntent, 0);
            mBuilder.setContentIntent(pendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
        }
    }
}
