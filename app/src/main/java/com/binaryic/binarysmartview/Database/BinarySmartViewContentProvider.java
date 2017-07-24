package com.binaryic.binarysmartview.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import static com.binaryic.binarysmartview.Common.Constants.AUTHORITY;
import static com.binaryic.binarysmartview.Common.Constants.PATH_ALL_USER_LEAVE;
import static com.binaryic.binarysmartview.Common.Constants.PATH_ALL_USER_MEETINGS;
import static com.binaryic.binarysmartview.Common.Constants.PATH_ALL_USER_SHIFT_CAHNGE;
import static com.binaryic.binarysmartview.Common.Constants.PATH_ATTENDENCE_DAYS;
import static com.binaryic.binarysmartview.Common.Constants.PATH_BIRTHDAY;
import static com.binaryic.binarysmartview.Common.Constants.PATH_DEPARMENT_INFO;
import static com.binaryic.binarysmartview.Common.Constants.PATH_GROUP_CHANNEL;
import static com.binaryic.binarysmartview.Common.Constants.PATH_HOLIDAYS;
import static com.binaryic.binarysmartview.Common.Constants.PATH_MAC;
import static com.binaryic.binarysmartview.Common.Constants.PATH_MEMBERS;
import static com.binaryic.binarysmartview.Common.Constants.PATH_NOTIFICATION;
import static com.binaryic.binarysmartview.Common.Constants.PATH_PHOTO;
import static com.binaryic.binarysmartview.Common.Constants.PATH_REGISTER_USER;
import static com.binaryic.binarysmartview.Common.Constants.PATH_REPORTS;
import static com.binaryic.binarysmartview.Common.Constants.PATH_USER;
import static com.binaryic.binarysmartview.Common.Constants.PATH_USER_INFO;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_ALL_USER_LEAVE;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_ALL_USER_MEETINGS;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_ALL_USER_SHIFT_CHANGE;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_ANNOUNCEMENT;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_ATTENDECE_DAYS;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_BIRTHDAYS;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_DEPARMENT_INFO;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_GROUP_CHANNEL;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_HOLIDAY;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_MAC;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_MEMBERS;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_PHOTO;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_REGISTER_USER;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_REPORTS;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_USER;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_USER_INFO;


public class BinarySmartViewContentProvider extends ContentProvider {


    private static final int CODE_USER_INFO = 1;
    private static final int CODE_ATTENDECE_DATA = 2;
    private static final int CODE_HOLIDAY_DATA = 3;
    private static final int CODE_BIRTHDAY_DATA = 4;
    private static final int CODE_PHOTO = 5;
    private static final int CODE_NOTIFICATION = 6;
    private static final int CODE_MAC = 7;
    private static final int CODE_ALL_USER_LEAVE = 8;
    private static final int CODE_ALL_USER_MEETINGS = 9;
    private static final int CODE_ALL_USER_SHIFT_CAHNGE = 12;
    private static final int CODE_REGISTER_USER = 10;
    private static final int CODE_DEPARMENT_INFO = 11;
    private static final int CODE_REPORTS = 13;
    private static final int CODE_USER = 14;
    private static final int CODE_MEMBERS = 15;
    private static final int CODE_GROUP_CHANNEL = 16;
    private MyDbHelper helper;
    private SQLiteDatabase database;
    private UriMatcher matcher;

    @Override
    public boolean onCreate() {
        helper = new MyDbHelper(getContext());
        database = helper.getWritableDatabase();
        matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(AUTHORITY, PATH_ALL_USER_SHIFT_CAHNGE, CODE_ALL_USER_SHIFT_CAHNGE);
        matcher.addURI(AUTHORITY, PATH_DEPARMENT_INFO, CODE_DEPARMENT_INFO);
        matcher.addURI(AUTHORITY, PATH_REGISTER_USER, CODE_REGISTER_USER);
        matcher.addURI(AUTHORITY, PATH_ALL_USER_MEETINGS, CODE_ALL_USER_MEETINGS);
        matcher.addURI(AUTHORITY, PATH_ALL_USER_LEAVE, CODE_ALL_USER_LEAVE);
        matcher.addURI(AUTHORITY, PATH_MAC, CODE_MAC);
        matcher.addURI(AUTHORITY, PATH_USER_INFO, CODE_USER_INFO);
        matcher.addURI(AUTHORITY, PATH_ATTENDENCE_DAYS, CODE_ATTENDECE_DATA);
        matcher.addURI(AUTHORITY, PATH_HOLIDAYS, CODE_HOLIDAY_DATA);
        matcher.addURI(AUTHORITY, PATH_BIRTHDAY, CODE_BIRTHDAY_DATA);
        matcher.addURI(AUTHORITY, PATH_NOTIFICATION, CODE_NOTIFICATION);
        matcher.addURI(AUTHORITY, PATH_PHOTO, CODE_PHOTO);
        matcher.addURI(AUTHORITY, PATH_REPORTS, CODE_REPORTS);
        matcher.addURI(AUTHORITY, PATH_USER, CODE_USER);
        matcher.addURI(AUTHORITY, PATH_MEMBERS, CODE_MEMBERS);
        matcher.addURI(AUTHORITY, PATH_GROUP_CHANNEL, CODE_GROUP_CHANNEL);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int code = matcher.match(uri);
        Cursor cursor = null;
        if (code == CODE_DEPARMENT_INFO) {
            cursor = database.query(TABLE_DEPARMENT_INFO, projection, selection, null, null, null, sortOrder);
        }
        if (code == CODE_ALL_USER_SHIFT_CAHNGE) {
            cursor = database.query(TABLE_ALL_USER_SHIFT_CHANGE, projection, selection, null, null, null, sortOrder);
        }
        if (code == CODE_REGISTER_USER) {
            cursor = database.query(TABLE_REGISTER_USER, projection, selection, null, null, null, sortOrder);
        }
        if (code == CODE_ALL_USER_MEETINGS) {
            cursor = database.query(TABLE_ALL_USER_MEETINGS, projection, selection, null, null, null, sortOrder);
        }
        if (code == CODE_ALL_USER_LEAVE) {
            cursor = database.query(TABLE_ALL_USER_LEAVE, projection, selection, null, null, null, sortOrder);
        }
        if (code == CODE_MAC) {
            cursor = database.query(TABLE_MAC, projection, selection, null, null, null, sortOrder);
        }
        if (code == CODE_USER_INFO) {
            cursor = database.query(TABLE_USER_INFO, projection, selection, null, null, null, sortOrder);
        }
        if (code == CODE_ATTENDECE_DATA) {
            cursor = database.query(TABLE_ATTENDECE_DAYS, projection, selection, null, null, null, sortOrder);
        }
        if (code == CODE_HOLIDAY_DATA) {
            cursor = database.query(TABLE_HOLIDAY, projection, selection, null, null, null, sortOrder);
        }
        if (code == CODE_BIRTHDAY_DATA) {
            cursor = database.query(TABLE_BIRTHDAYS, projection, selection, null, null, null, sortOrder);
        }
        if (code == CODE_NOTIFICATION) {
            cursor = database.query(TABLE_ANNOUNCEMENT, projection, selection, null, null, null, sortOrder);
        }
        if (code == CODE_PHOTO) {
            cursor = database.query(TABLE_PHOTO, projection, selection, null, null, null, sortOrder);
        }if (code == CODE_REPORTS) {
            cursor = database.query(TABLE_REPORTS, projection, selection, null, null, null, sortOrder);
        }
        if (code == CODE_USER) {
            cursor = database.query(TABLE_USER, projection, selection, selectionArgs, null, null, sortOrder);
        }
        if (code == CODE_MEMBERS) {
            cursor = database.query(TABLE_MEMBERS, projection, selection, selectionArgs, null, null, sortOrder);
        } if (code == CODE_GROUP_CHANNEL) {
            cursor = database.query(TABLE_GROUP_CHANNEL, projection, selection, selectionArgs, null, null, sortOrder);
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int code = matcher.match(uri);
        if (code == CODE_DEPARMENT_INFO) {
            database.insert(TABLE_DEPARMENT_INFO, null, values);
        } if (code == CODE_ALL_USER_SHIFT_CAHNGE) {
            database.insert(TABLE_ALL_USER_SHIFT_CHANGE, null, values);
        }
        if (code == CODE_REGISTER_USER) {
            database.insert(TABLE_REGISTER_USER, null, values);
        }
        if (code == CODE_ALL_USER_MEETINGS) {
            database.insert(TABLE_ALL_USER_MEETINGS, null, values);
        }
        if (code == CODE_ALL_USER_LEAVE) {
            database.insert(TABLE_ALL_USER_LEAVE, null, values);
        }
        if (code == CODE_MAC) {
            database.insert(TABLE_MAC, null, values);
        }
        if (code == CODE_USER_INFO) {
            database.insert(TABLE_USER_INFO, null, values);
        }
        if (code == CODE_ATTENDECE_DATA) {
            database.insert(TABLE_ATTENDECE_DAYS, null, values);
        }
        if (code == CODE_HOLIDAY_DATA) {
            database.insert(TABLE_HOLIDAY, null, values);
        }
        if (code == CODE_BIRTHDAY_DATA) {
            database.insert(TABLE_BIRTHDAYS, null, values);
        }
        if (code == CODE_NOTIFICATION) {
            database.insert(TABLE_ANNOUNCEMENT, null, values);
        }
        if (code == CODE_PHOTO) {
            database.insert(TABLE_PHOTO, null, values);
        }if (code == CODE_REPORTS) {
            database.insert(TABLE_REPORTS, null, values);
        }
        if (code == CODE_USER) {
            database.insert(TABLE_USER, null, values);
        }
        if (code == CODE_MEMBERS) {
            database.insert(TABLE_MEMBERS, null, values);
        } if (code == CODE_GROUP_CHANNEL) {
            database.insert(TABLE_GROUP_CHANNEL, null, values);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereargs) {
        int code = matcher.match(uri);
        int delete = 0;
        if (code == CODE_DEPARMENT_INFO) {
            delete = database.delete(TABLE_DEPARMENT_INFO, where, whereargs);
        }if (code == CODE_ALL_USER_SHIFT_CAHNGE) {
            delete = database.delete(TABLE_ALL_USER_SHIFT_CHANGE, where, whereargs);
        }
        if (code == CODE_REGISTER_USER) {
            delete = database.delete(TABLE_REGISTER_USER, where, whereargs);
        }
        if (code == CODE_ALL_USER_MEETINGS) {
            delete = database.delete(TABLE_ALL_USER_MEETINGS, where, whereargs);
        }
        if (code == CODE_ALL_USER_LEAVE) {
            delete = database.delete(TABLE_ALL_USER_LEAVE, where, whereargs);
        }
        if (code == CODE_MAC) {
            delete = database.delete(TABLE_MAC, where, whereargs);
        }
        if (code == CODE_USER_INFO) {
            delete = database.delete(TABLE_USER_INFO, where, whereargs);
        }
        if (code == CODE_ATTENDECE_DATA) {
            delete = database.delete(TABLE_ATTENDECE_DAYS, where, whereargs);
        }
        if (code == CODE_HOLIDAY_DATA) {
            delete = database.delete(TABLE_HOLIDAY, where, whereargs);
        }
        if (code == CODE_BIRTHDAY_DATA) {
            delete = database.delete(TABLE_BIRTHDAYS, where, whereargs);
        }
        if (code == CODE_NOTIFICATION) {
            delete = database.delete(TABLE_ANNOUNCEMENT, where, whereargs);
        }
        if (code == CODE_PHOTO) {
            delete = database.delete(TABLE_PHOTO, where, whereargs);
        }  if (code == CODE_REPORTS) {
            delete = database.delete(TABLE_REPORTS, where, whereargs);
        }
        if (code == CODE_USER) {
            delete = database.delete(TABLE_USER, where, whereargs);
        }
        if (code == CODE_MEMBERS) {
            delete = database.delete(TABLE_MEMBERS, where, whereargs);
        } if (code == CODE_GROUP_CHANNEL) {
            delete = database.delete(TABLE_GROUP_CHANNEL, where, whereargs);
        }

        return delete;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int code = matcher.match(uri);
        int row = 0;
        if (code == CODE_DEPARMENT_INFO) {
            row = database.update(TABLE_DEPARMENT_INFO, values, selection, selectionArgs);
        }
        if (code == CODE_ALL_USER_SHIFT_CAHNGE) {
            row = database.update(TABLE_ALL_USER_SHIFT_CHANGE, values, selection, selectionArgs);
        }
        if (code == CODE_REGISTER_USER) {
            row = database.update(TABLE_REGISTER_USER, values, selection, selectionArgs);
        }
        if (code == CODE_ALL_USER_MEETINGS) {
            row = database.update(TABLE_ALL_USER_MEETINGS, values, selection, selectionArgs);
        }
        if (code == CODE_ALL_USER_LEAVE) {
            row = database.update(TABLE_ALL_USER_LEAVE, values, selection, selectionArgs);
        }
        if (code == CODE_MAC) {
            row = database.update(TABLE_MAC, values, selection, selectionArgs);
        }
        if (code == CODE_USER_INFO) {
            row = database.update(TABLE_USER_INFO, values, selection, selectionArgs);
        }
        if (code == CODE_ATTENDECE_DATA) {
            row = database.update(TABLE_ATTENDECE_DAYS, values, selection, selectionArgs);
        }
        if (code == CODE_HOLIDAY_DATA) {
            row = database.update(TABLE_HOLIDAY, values, selection, selectionArgs);
        }
        if (code == CODE_BIRTHDAY_DATA) {
            row = database.update(TABLE_BIRTHDAYS, values, selection, selectionArgs);
        }
        if (code == CODE_NOTIFICATION) {
            row = database.update(TABLE_ANNOUNCEMENT, values, selection, selectionArgs);
        }
        if (code == CODE_PHOTO) {
            row = database.update(TABLE_PHOTO, values, selection, selectionArgs);
        } if (code == CODE_REPORTS) {
            row = database.update(TABLE_REPORTS, values, selection, selectionArgs);
        }
        if (code == CODE_USER) {
            row = database.update(TABLE_USER, values, selection, selectionArgs);
        }
        if (code == CODE_MEMBERS) {
            row = database.update(TABLE_MEMBERS, values, selection, selectionArgs);
        } if (code == CODE_GROUP_CHANNEL) {
            row = database.update(TABLE_GROUP_CHANNEL, values, selection, selectionArgs);
        }
        return row;
    }
}
