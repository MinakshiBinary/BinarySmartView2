package com.binaryic.binarysmartview.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.app.DownloadManager.COLUMN_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_APPLICATION_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_CL;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_COVER;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_CURRENT_IN;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_CURRENT_OUT;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DATE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DATE_OF_APPLY;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DATE_OF_BIRTH;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DATE_OF_JOINING;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DAY;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DEPARTMENT_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DEPARTMENT_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DURATION;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_FROM_DATE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_FROM_TIME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_GRADUATION;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_HOLIDAY_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_HOLIDAY_REMAINING_DAYS;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_IN_TIME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_MAC_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_MARK_STATUS;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_MESSAGE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_MOBILE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NEW_IN;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NEW_OUT;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_ORIGINAL_INTIME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_ORIGINAL_OUTTIME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_OUT_TIME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PAGE_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PASSWORD;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PERSONAL_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PL;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PRESNET_STATUS;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PURPOSE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_SL;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_STATUS;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TIME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TITLE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TO_DATE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TO_TIME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TYPE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_DESIGNATION;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_IMAGE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_LOGIN_STATUS;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_ALL_USER_LEAVE;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_ALL_USER_MEETINGS;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_ALL_USER_SHIFT_CHANGE;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_ANNOUNCEMENT;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_ATTENDECE_DAYS;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_BIRTHDAYS;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_DEPARMENT_INFO;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_HOLIDAY;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_MAC;
import static com.binaryic.binarysmartview.Common.Constants.*;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_REGISTER_USER;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_REPORTS;
import static com.binaryic.binarysmartview.Common.Constants.TABLE_USER_INFO;

public class MyDbHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "com.binaryic.binarysmartview";
    public static int DATABASE_VERSION = 31;


    public static String DATABASE_REPORTS = "create table " + TABLE_REPORTS + "( " + COLUMN_APPLICATION_ID + " text, " + COLUMN_NAME + " text, " + COLUMN_EMAIL_ID + " text, "+ COLUMN_DATE + " text, "+ COLUMN_TIME + " text, "+ COLUMN_PAGE_NAME + " text, " + COLUMN_USER_ID + " text );";
    public static String DATABASE_ALL_USER_SHIFT_CHANGE = "create table " + TABLE_ALL_USER_SHIFT_CHANGE + "( " + COLUMN_NAME + " text, " + COLUMN_APPLICATION_ID + " text, " + COLUMN_USER_ID + " text, " + COLUMN_EMAIL_ID + " text, " + COLUMN_CURRENT_IN + " text, " + COLUMN_CURRENT_OUT + " text, " + COLUMN_NEW_IN + " text, " + COLUMN_NEW_OUT + " text, " + COLUMN_STATUS + " int, " + COLUMN_USER_IMAGE + " text, " + COLUMN_DATE_OF_APPLY + " text );";
    public static String DATABASE_ALL_USER_MEETINGS = "create table " + TABLE_ALL_USER_MEETINGS + "( " + COLUMN_NAME + " text, " + COLUMN_APPLICATION_ID + " text, " + COLUMN_USER_ID + " text, " + COLUMN_TITLE + " text, " + COLUMN_PURPOSE + " text, " + COLUMN_FROM_TIME + " text, " + COLUMN_TO_TIME + " text, " + COLUMN_EMAIL_ID + " text, " + COLUMN_STATUS + " int, " + COLUMN_USER_IMAGE + " text, " + COLUMN_DATE_OF_APPLY + " text );";
    public static String DATABASE_ALL_USER_LEAVE = "create table " + TABLE_ALL_USER_LEAVE + "( " + COLUMN_NAME + " text, " + COLUMN_TITLE + " text, " + COLUMN_APPLICATION_ID + " text, " + COLUMN_USER_ID + " text, " + COLUMN_FROM_DATE + " text, " + COLUMN_TO_DATE + " text, " + COLUMN_TYPE + " text, " + COLUMN_DURATION + " text, " + COLUMN_EMAIL_ID + " text, " + COLUMN_STATUS + " int, " + COLUMN_USER_IMAGE + " text, " + COLUMN_DATE_OF_APPLY + " text );";
    public static String DATABASE_USER_INFO = "create table " + TABLE_USER_INFO + "( " + COLUMN_NAME + " text, " + COLUMN_USER_ID + " text, " + COLUMN_DATE_OF_JOINING + " text, " + COLUMN_USER_LOGIN_STATUS + " text, " + COLUMN_USER_DESIGNATION + " text, " + COLUMN_MOBILE + " text, " + COLUMN_EMAIL_ID + " text, " + COLUMN_PERSONAL_EMAIL_ID + " text, " + COLUMN_USER_IMAGE + " text, " + COLUMN_GRADUATION + " text, " + COLUMN_DATE_OF_BIRTH + " text, " + COLUMN_PASSWORD + " text, " + COLUMN_CL + " text, " + COLUMN_SL + " text, " + COLUMN_PL + " text );";
    public static String DATABASE_ATTENDECE_DAYS = "create table " + TABLE_ATTENDECE_DAYS + "( " + COLUMN_USER_ID + " text, " + COLUMN_IN_TIME + " text, " + COLUMN_DAY + " text, " + COLUMN_OUT_TIME + " text, " + COLUMN_PRESNET_STATUS + " text, " + COLUMN_ORIGINAL_INTIME + " text, " + COLUMN_ORIGINAL_OUTTIME + " text, " + COLUMN_MARK_STATUS + " text, " + COLUMN_DATE + " text );";
    public static String DATABASE_HOLIDAYS = "create table " + TABLE_HOLIDAY + "( " + COLUMN_USER_ID + " text, " + COLUMN_HOLIDAY_REMAINING_DAYS + " text, " + COLUMN_DATE + " text, " + COLUMN_HOLIDAY_NAME + " text );";
    public static String DATABASE_BIRTHDAYS = "create table " + TABLE_BIRTHDAYS + "( " + COLUMN_USER_ID + " text, " + COLUMN_NAME + " text, " + COLUMN_USER_IMAGE + " text, " + COLUMN_DATE_OF_BIRTH + " text, " + COLUMN_EMAIL_ID + " text );";
    public static String DATABASE_MAC = "create table " + TABLE_MAC + "( " + COLUMN_MAC_ID + " text, " + COLUMN_NAME + " text );";
    public static String DATABASE_ANNOUNCEMENT = "create table " + TABLE_ANNOUNCEMENT + "( " + COLUMN_USER_ID + " text, " + COLUMN_TITLE + " text, " + COLUMN_NAME + " text, " + COLUMN_MESSAGE + " text, " + COLUMN_DATE + " text, " + COLUMN_EMAIL_ID + " text );";
    public static String DATABASE_PHOTO = "create table " + TABLE_PHOTO + "( " + COLUMN_COVER + " text );";
    public static String DATABASE_REGISTER_USER = "create table " + TABLE_REGISTER_USER + "( " + COLUMN_USER_ID + " text, " + COLUMN_EMAIL_ID + " text, " + COLUMN_NAME + " text );";
    public static String DATABASE_DEPARMENT_INFO = "create table " + TABLE_DEPARMENT_INFO + "( " + COLUMN_DEPARTMENT_ID + " text, " + COLUMN_DEPARTMENT_NAME + " text );";
    public static String DATABASE_MEMBERS = "create table " + TABLE_MEMBERS + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_ID + " text, "  + COLUMN_NICKNAME + " text, " + COLUMN_PROFILE_IMAGE + " text );";
    public static String DATABASE_USER = "create table " + TABLE_USER + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_ID + " text, "  + COLUMN_NICKNAME + " text, " + COLUMN_PROFILE_IMAGE + " text );";
    public static String DATABASE_GROUP_CHANNEL = "create table " + TABLE_GROUP_CHANNEL + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_IS_ACTIVE + " text, "  + COLUMN_CHANNEL_URL + " text, " + COLUMN_GROUP_USER_ID + " text, "  + COLUMN_GROUP_NAME + " text, " + COLUMN_PROFILE_IMAGE + " text );";

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_REPORTS);
        db.execSQL(DATABASE_ALL_USER_SHIFT_CHANGE);
        db.execSQL(DATABASE_DEPARMENT_INFO);
        db.execSQL(DATABASE_REGISTER_USER);
        db.execSQL(DATABASE_ALL_USER_MEETINGS);
        db.execSQL(DATABASE_ALL_USER_LEAVE);
        db.execSQL(DATABASE_USER_INFO);
        db.execSQL(DATABASE_ATTENDECE_DAYS);
        db.execSQL(DATABASE_HOLIDAYS);
        db.execSQL(DATABASE_BIRTHDAYS);
        db.execSQL(DATABASE_ANNOUNCEMENT);
        db.execSQL(DATABASE_PHOTO);
        db.execSQL(DATABASE_MAC);
        db.execSQL(DATABASE_USER);
        db.execSQL(DATABASE_MEMBERS);
        db.execSQL(DATABASE_GROUP_CHANNEL);
        db.execSQL("INSERT INTO " + TABLE_MAC + " (" + COLUMN_MAC_ID + "," + COLUMN_NAME + ") VALUES ('84:1b:5e:3c:6f:5e','Bhim')");
        db.execSQL("INSERT INTO " + TABLE_MAC + " (" + COLUMN_MAC_ID + "," + COLUMN_NAME + ") VALUES ('bc:f6:85:c7:28:b0','binaryteam')");
        db.execSQL("INSERT INTO " + TABLE_MAC + " (" + COLUMN_MAC_ID + "," + COLUMN_NAME + ") VALUES ('1c:5f:2b:3e:d4:90','Binary01')");
        db.execSQL("INSERT INTO " + TABLE_MAC + " (" + COLUMN_MAC_ID + "," + COLUMN_NAME + ") VALUES ('18:a6:f7:da:71:b4','Binary_Hathway')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_USER_SHIFT_CHANGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPARMENT_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_USER_MEETINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_USER_LEAVE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDECE_DAYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOLIDAY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANNOUNCEMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIRTHDAYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_CHANNEL);
        onCreate(db);
    }
}
