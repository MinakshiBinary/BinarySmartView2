package com.binaryic.binarysmartview.Controller;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.sendbird.android.GroupChannel;

import java.util.List;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_CHANNEL_URL;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_GROUP_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_GROUP_USER_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_IS_ACTIVE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PROFILE_IMAGE;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_GROUP_CHANNEL;

/**
 * Created by minakshi on 19/7/17.
 */

public class GroupController {

    public static void addDataInDatabase(Activity context, List<GroupChannel> list) {

        for (int i = 0; i < list.size(); i++) {
            ContentValues values = new ContentValues();
            String selection = COLUMN_CHANNEL_URL + " ='" + list.get(i).getUrl() + "'";
            Cursor cursor = context.getContentResolver().query(CONTENT_GROUP_CHANNEL, null, selection, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                values.put(COLUMN_IS_ACTIVE, cursor.getString(cursor.getColumnIndex(COLUMN_IS_ACTIVE)));
            } else {
                values.put(COLUMN_IS_ACTIVE, "true");
            }
            values.put(COLUMN_CHANNEL_URL, list.get(i).getUrl());
            for (int j = 0; j < list.get(i).getMemberCount(); j++) {
                if (!list.get(i).getMembers().get(j).getUserId().matches(LoginController.getLoginUser(context).getUser_ID())) {
                    values.put(COLUMN_GROUP_USER_ID, list.get(i).getMembers().get(j).getUserId());
                    values.put(COLUMN_GROUP_NAME, list.get(i).getMembers().get(j).getNickname());
                    values.put(COLUMN_PROFILE_IMAGE, LoginController.getUserProfilePic(context, list.get(i).getMembers().get(j).getUserId()));
                }
            }
            Log.e("GroupController", "addDataInDatabase ==" + values);

            if (cursor.getCount() > 0) {
                context.getContentResolver().update(CONTENT_GROUP_CHANNEL, values, selection, null);
            } else {
                context.getContentResolver().insert(CONTENT_GROUP_CHANNEL, values);
            }
        }

    }

    public static void changeStatus(Activity context, String channel, String val) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_ACTIVE, val);

        String selection = COLUMN_CHANNEL_URL + " ='" + channel + "'";

        Log.e("GroupController", "changeStatus ==" + values);

        Cursor cursor = context.getContentResolver().query(CONTENT_GROUP_CHANNEL, null, selection, null, null);
        if (cursor.getCount() > 0) {
            context.getContentResolver().update(CONTENT_GROUP_CHANNEL, values, selection, null);
        }
    }

    public static void changeStatus(Context context, String channel, String val) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_ACTIVE, val);

        String selection = COLUMN_CHANNEL_URL + " ='" + channel + "'";

        Log.e("GroupController", "changeStatus ==" + values);

        Cursor cursor = context.getContentResolver().query(CONTENT_GROUP_CHANNEL, null, selection, null, null);
        if (cursor.getCount() > 0) {
            context.getContentResolver().update(CONTENT_GROUP_CHANNEL, values, selection, null);
        }
    }

    public static String isActivated(Activity context, String url) {
        String isActive = "false";
        String selection = COLUMN_CHANNEL_URL + " ='" + url + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_GROUP_CHANNEL, null, selection, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            Log.e("GroupController", "isActivated ==" + cursor.getString(cursor.getColumnIndex(COLUMN_CHANNEL_URL)));
            Log.e("GroupController", "isActivated ==" + cursor.getString(cursor.getColumnIndex(COLUMN_GROUP_USER_ID)));
            Log.e("GroupController", "isActivated ==" + cursor.getString(cursor.getColumnIndex(COLUMN_GROUP_NAME)));
            Log.e("GroupController", "isActivated ==" + cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_IMAGE)));
            Log.e("GroupController", "isActivated ==" + cursor.getString(cursor.getColumnIndex(COLUMN_IS_ACTIVE)));
            isActive = cursor.getString(cursor.getColumnIndex(COLUMN_IS_ACTIVE));
        } else isActive = "true";
        Log.e("GroupController", "isActivated ==" + isActive);

        return isActive;
    }
}
