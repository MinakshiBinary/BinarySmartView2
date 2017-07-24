package com.binaryic.binarysmartview.Controller;

import android.app.Activity;
import android.database.Cursor;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_ID;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_USER_INFO;

/**
 * Created by minakshi on 21/3/17.
 */

public class UserInfoController {
    public static String getUserEmail(Activity context) {
        String email = "";
        Cursor cursor = context.getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID));
        }
        cursor.close();
        return email;
    }

    public static String getUserEcode(Activity context) {
        String ecode = "";
        Cursor cursor = context.getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            ecode = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
        }
        cursor.close();
        return ecode;
    }
}
