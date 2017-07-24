package com.binaryic.binarysmartview.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.binarysmartview.Adapter.MyPagerAdapter;
import com.binaryic.binarysmartview.CarouselLiberary.GameEntity;
import com.binaryic.binarysmartview.Common.CommonFunction;
import com.binaryic.binarysmartview.Common.FileDownloader;
import com.binaryic.binarysmartview.Common.InternetConnectionDetector;
import com.binaryic.binarysmartview.Common.MyApplication;
import com.binaryic.binarysmartview.Common.ScalingUtilities;
import com.binaryic.binarysmartview.Common.TextAwesome;
import com.binaryic.binarysmartview.Controller.LoginController;
import com.binaryic.binarysmartview.Controller.ReportingController;
import com.binaryic.binarysmartview.Controller.UserInfoController;
import com.binaryic.binarysmartview.Models.AttendenceModel;
import com.binaryic.binarysmartview.Models.NotificationModel;
import com.binaryic.binarysmartview.Models.SalarySlipModel;
import com.binaryic.binarysmartview.R;
import com.binaryic.binarysmartview.Receivers.AlarmReceiver;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.gson.Gson;
import com.sendbird.android.SendBird;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_CL;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_COVER;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DATE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DATE_OF_BIRTH;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DAY;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_HOLIDAY_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_HOLIDAY_REMAINING_DAYS;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_IN_TIME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_MARK_STATUS;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_MESSAGE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_ORIGINAL_INTIME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_ORIGINAL_OUTTIME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_OUT_TIME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PASSWORD;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PL;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PRESNET_STATUS;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_SL;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_TITLE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_DESIGNATION;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_IMAGE;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_ATTENDECE_INFO;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_GROUP_CHANNEL;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_MEMBERS;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_NOTIFICAION;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_PHOTO;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_UPCOMING_BIRTHDAYS;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_UPCOMING_HOLIDAYS;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_USER;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_USER_INFO;
import static com.binaryic.binarysmartview.Common.Constants.URL_APPLY_SHIFT_CHANGE;
import static com.binaryic.binarysmartview.Common.Constants.URL_GET_ATTENDANCE_DATA;
import static com.binaryic.binarysmartview.Common.Constants.URL_GET_CHANGE_PASSWORD;
import static com.binaryic.binarysmartview.Common.Constants.URL_GET_UPDATE_LEAVES;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, AppBarLayout.OnOffsetChangedListener {
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    private int showAnnouncementSize = 0;
    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    private RelativeLayout rl_Container;
    private TextView textviewTitle;
    private AppBarLayout app_bar;
    private LinearLayout linearlayoutTitle;
    private ImageView imageview_placeholder;
    public final static int LOOPS = 1000;
    public final static float BIG_SCALE = 1.0f;
    public final static float SMALL_SCALE = 0.7f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;
    public static int FIRST_PAGE; // = count * LOOPS / 2;
    public static int count;
    public static MainActivity mainActivityCtx;
    public static int i = 10;
    public ArrayList<GameEntity> mData = new ArrayList<>(0);
    public ViewPager mCoverFlow;
    KenBurnsView imageView;
    Timer timer;
    MyTimerTask myTimerTask;
    List<SalarySlipModel> listSalarySlip = new ArrayList<>();
    String eid = "";
    String[] strArg = new String[2];
    private Button bt_LoginLogout;
    private Button bt_MeetingApplication;
    private Toolbar toolbar;
    private SimpleDraweeView mHeaderLogo;
    private String emailID;
    private String ecode;
    private TextView tv_Shift_Out_Time;
    private TextView tv_Shift_In_Time;
    private TextView tv_GoodMorning;
    private TextView tv_Date;
    private TextView tv_Name;
    private TextView tv_Designation;
    private int INTENT_REQUEST_GET_IMAGES = 1;
    private String image_Selection = "";
    private RelativeLayout rl_FirstHoliday;
    private RelativeLayout rl_SecondHoliday;
    private RelativeLayout rl_ThirdHoliday;
    private RelativeLayout rl_No_Holiday;
    private RelativeLayout relative_ShiftChange;
    private RelativeLayout relative_UpcomingBirthdays;
    private RelativeLayout relative_UpcomingHolidays, layMonthlyAtt, rl_salary, rl_Leave, rl_dailyAttendence;
    private TextView tv_View;
    private TextView tv_FirstDate;
    private TextView tv_First_In_Time;
    private TextView tv_First_Out_Time;
    private TextView tv_SecondDate;
    private TextView tv_Second_In_Time;
    private TextView tv_Second_Out_Time;
    private TextView tv_Calender_Day_First;
    private TextView tv_Calender_Date_First;
    private TextView tv_Calender_Day_Second;
    private TextView tv_Calender_Date_Second;
    private TextView tv_Calender_Day_Third;
    private TextView tv_Calender_Date_Third;
    private TextView tv_Calender_Day_Fourth;
    private TextView tv_Calender_Date_Fourth;
    private TextView tv_Calender_Day_Fifth;
    private TextView tv_Calender_Date_Fifth;
    private TextView tv_Calender_Day_Six;
    private TextView tv_Calender_Date_Six;
    private TextView tv_Calender_Day_Seven;
    private TextView tv_Calender_Date_Seven;
    private TextView tv_Third_Holiday;
    private TextView tv_Second_Holiday;
    private TextView tv_First_Holiday;
    private TextView tv_First_Holiday_Date;
    private TextView tv_Second_Holiday_Date;
    private TextView tv_Third_Holiday_Date;
    private TextView tv_First_Holiday_Remaining;
    private TextView tv_Second_Holiday_Remaining;
    private TextView tv_Third_Holiday_Remaining;
    private LinearLayout linear_dailyAttendence;
    private TextView tv_Calender_Dot_First;
    private TextView tv_Calender_Dot_Second;
    private TextView tv_Calender_Dot_Third;
    private TextView tv_Calender_Dot_Fourth;
    private TextView tv_Calender_Dot_Seven;
    private TextView tv_Calender_Dot_Fifth;
    private TextView tv_Calender_Dot_Six;
    private TextView tv_time, tvAm, tv_month, lblSL, lblCL, lblPL, lblTotal;
    private ImageView civ_FirstHoliday, civ_SecondHoliday, civ_ThirdHoliday;
    private LinearLayout laySalary1, laySalary2, laySalary3;
    ArrayList<NotificationModel> notificationModels;
    private TextView lblSalaryMonth1, lblSalaryMonth2, lblSalaryMonth3, lblSalaryYear1, lblSalaryYear2, lblSalaryYear3;
    private TextAwesome imgSalary1, imgSalary2, imgSalary3;
    private TextSwitcher mTitle;
    private ArrayList<AttendenceModel> arrayAttendeceModel = new ArrayList<AttendenceModel>();
    private RelativeLayout layMainSalary;
    private RelativeLayout rl_NotificationFirst;
    private RelativeLayout rl_NotificationSecond;
    private RelativeLayout rl_NotificationThird;
    private RelativeLayout rl_NoData;
    private RelativeLayout rl_SecondLatestDay;
    private RelativeLayout rl_LatestDay;
    private RelativeLayout rl_NotificationHeader;
    private TextView tv_NameFirst;
    private TextView tv_DateFirst;
    private TextView tv_NameSecond;
    private TextView tv_DateSecond;
    private TextView tv_NameThird;
    private TextView tv_DateThird;
    private String logoutTime;

    public static ProgressDialog pDialog;

    public static int compareToDay(String holiday_Date) {
        Date date2 = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date2 = dateFormat.parse(holiday_Date);
        } catch (ParseException e) {
            Log.e("errorr date", "===" + e.getMessage());
        }
        return date2.compareTo(new Date());

    }

    private void findViews() {
        notificationModels = new ArrayList<NotificationModel>();
        imageView = (KenBurnsView) findViewById(R.id.header_picture);
        rl_Container = (RelativeLayout) findViewById(R.id.rl_Container);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        bt_LoginLogout = (Button) findViewById(R.id.bt_LoginLogout);
        bt_MeetingApplication = (Button) findViewById(R.id.bt_MeetingApplication);
        app_bar = (AppBarLayout) findViewById(R.id.app_bar);
        textviewTitle = (TextView) findViewById(R.id.textview_title);
        linearlayoutTitle = (LinearLayout) findViewById(R.id.linearlayout_title);
        imageview_placeholder = (ImageView) findViewById(R.id.imageview_placeholder);
        tv_NameFirst = (TextView) findViewById(R.id.tv_NameFirst);
        tv_DateFirst = (TextView) findViewById(R.id.tv_DateFirst);
        tv_NameSecond = (TextView) findViewById(R.id.tv_NameSecond);
        tv_DateSecond = (TextView) findViewById(R.id.tv_DateSecond);
        tv_NameThird = (TextView) findViewById(R.id.tv_NameThird);
        tv_DateThird = (TextView) findViewById(R.id.tv_DateThird);
        rl_NotificationFirst = (RelativeLayout) findViewById(R.id.rl_NotificationFirst);
        rl_NotificationSecond = (RelativeLayout) findViewById(R.id.rl_NotificationSecond);
        rl_NotificationThird = (RelativeLayout) findViewById(R.id.rl_NotificationThird);
        rl_NoData = (RelativeLayout) findViewById(R.id.rl_NoData);
        rl_SecondLatestDay = (RelativeLayout) findViewById(R.id.rl_SecondLatestDay);
        rl_LatestDay = (RelativeLayout) findViewById(R.id.rl_LatestDay);
        tv_Shift_Out_Time = (TextView) findViewById(R.id.tv_Shift_Out_Time);
        tv_Shift_In_Time = (TextView) findViewById(R.id.tv_Shift_In_Time);
        tv_Designation = (TextView) findViewById(R.id.tv_Designation);
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        tv_Date = (TextView) findViewById(R.id.tv_Date);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tvAm = (TextView) findViewById(R.id.tvAm);
        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_GoodMorning = (TextView) findViewById(R.id.tv_GoodMorning);
        mHeaderLogo = (SimpleDraweeView) findViewById(R.id.header_thumbnail);
        lblSL = (TextView) findViewById(R.id.lblSL);
        lblCL = (TextView) findViewById(R.id.lblCL);
        lblPL = (TextView) findViewById(R.id.lblPL);
        lblTotal = (TextView) findViewById(R.id.lblTotal);
        relative_ShiftChange = (RelativeLayout) findViewById(R.id.relative_ShiftChange);
        relative_UpcomingBirthdays = (RelativeLayout) findViewById(R.id.relative_UpcomingBirthdays);
        relative_UpcomingHolidays = (RelativeLayout) findViewById(R.id.relative_UpcomingHolidays);
        layMonthlyAtt = (RelativeLayout) findViewById(R.id.layMonthlyAtt);
        tv_View = (TextView) findViewById(R.id.tv_View);
        tv_FirstDate = (TextView) findViewById(R.id.tv_FirstDate);
        tv_SecondDate = (TextView) findViewById(R.id.tv_SecondDate);
        tv_First_In_Time = (TextView) findViewById(R.id.tv_First_In_Time);
        tv_Second_In_Time = (TextView) findViewById(R.id.tv_Second_In_Time);
        tv_First_Out_Time = (TextView) findViewById(R.id.tv_First_Out_Time);
        tv_Second_Out_Time = (TextView) findViewById(R.id.tv_Second_Out_Time);
        tv_Calender_Day_First = (TextView) findViewById(R.id.tv_Calender_Day_First);
        tv_Calender_Date_First = (TextView) findViewById(R.id.tv_Calender_Date_First);
        tv_Calender_Day_Second = (TextView) findViewById(R.id.tv_Calender_Day_Second);
        tv_Calender_Date_Second = (TextView) findViewById(R.id.tv_Calender_Date_Second);
        tv_Calender_Day_Third = (TextView) findViewById(R.id.tv_Calender_Day_Third);
        tv_Calender_Date_Third = (TextView) findViewById(R.id.tv_Calender_Date_Third);
        tv_Calender_Day_Fourth = (TextView) findViewById(R.id.tv_Calender_Day_Fourth);
        tv_Calender_Date_Fourth = (TextView) findViewById(R.id.tv_Calender_Date_Fourth);
        tv_Calender_Day_Fifth = (TextView) findViewById(R.id.tv_Calender_Day_Fifth);
        tv_Calender_Date_Fifth = (TextView) findViewById(R.id.tv_Calender_Date_Fifth);
        tv_Calender_Day_Six = (TextView) findViewById(R.id.tv_Calender_Day_Six);
        tv_Calender_Date_Six = (TextView) findViewById(R.id.tv_Calender_Date_Six);
        tv_Calender_Day_Seven = (TextView) findViewById(R.id.tv_Calender_Day_Seven);
        tv_Calender_Date_Seven = (TextView) findViewById(R.id.tv_Calender_Date_Seven);
        tv_First_Holiday = (TextView) findViewById(R.id.tv_First_Holiday);
        tv_Second_Holiday = (TextView) findViewById(R.id.tv_Second_Holiday);
        tv_Third_Holiday = (TextView) findViewById(R.id.tv_Third_Holiday);
        tv_First_Holiday_Date = (TextView) findViewById(R.id.tv_First_Holiday_Date);
        tv_Second_Holiday_Date = (TextView) findViewById(R.id.tv_Second_Holiday_Date);
        tv_Third_Holiday_Date = (TextView) findViewById(R.id.tv_Third_Holiday_Date);
        tv_First_Holiday_Remaining = (TextView) findViewById(R.id.tv_First_Holiday_Remaining);
        tv_Second_Holiday_Remaining = (TextView) findViewById(R.id.tv_Second_Holiday_Remaining);
        tv_Third_Holiday_Remaining = (TextView) findViewById(R.id.tv_Third_Holiday_Remaining);
        tv_Calender_Dot_First = (TextView) findViewById(R.id.tv_Calender_Dot_First);
        tv_Calender_Dot_Second = (TextView) findViewById(R.id.tv_Calender_Dot_Second);
        tv_Calender_Dot_Third = (TextView) findViewById(R.id.tv_Calender_Dot_Third);
        tv_Calender_Dot_Fourth = (TextView) findViewById(R.id.tv_Calender_Dot_Fourth);
        tv_Calender_Dot_Fifth = (TextView) findViewById(R.id.tv_Calender_Dot_Fifth);
        tv_Calender_Dot_Six = (TextView) findViewById(R.id.tv_Calender_Dot_Six);
        tv_Calender_Dot_Seven = (TextView) findViewById(R.id.tv_Calender_Dot_Seven);
        linear_dailyAttendence = (LinearLayout) findViewById(R.id.linear_dailyAttendence);

        rl_NotificationHeader = (RelativeLayout) findViewById(R.id.rl_NotificationHeader);
        rl_FirstHoliday = (RelativeLayout) findViewById(R.id.rl_FirstHoliday);
        rl_SecondHoliday = (RelativeLayout) findViewById(R.id.rl_SecondHoliday);
        rl_ThirdHoliday = (RelativeLayout) findViewById(R.id.rl_ThirdHoliday);
        rl_No_Holiday = (RelativeLayout) findViewById(R.id.rl_No_Holiday);
        rl_Leave = (RelativeLayout) findViewById(R.id.rl_Leave);
        rl_salary = (RelativeLayout) findViewById(R.id.rl_salary);
        layMainSalary = (RelativeLayout) findViewById(R.id.layMainSalary);
        laySalary1 = (LinearLayout) findViewById(R.id.laySalary1);
        laySalary2 = (LinearLayout) findViewById(R.id.laySalary2);
        laySalary3 = (LinearLayout) findViewById(R.id.laySalary3);
        lblSalaryMonth2 = (TextView) findViewById(R.id.lblSalaryMonth2);
        lblSalaryMonth1 = (TextView) findViewById(R.id.lblSalaryMonth1);
        lblSalaryMonth3 = (TextView) findViewById(R.id.lblSalaryMonth3);

        lblSalaryYear2 = (TextView) findViewById(R.id.lblSalaryYear2);
        lblSalaryYear1 = (TextView) findViewById(R.id.lblSalaryYear1);
        lblSalaryYear3 = (TextView) findViewById(R.id.lblSalaryYear3);

        imgSalary2 = (TextAwesome) findViewById(R.id.imgSalary2);
        imgSalary1 = (TextAwesome) findViewById(R.id.imgSalary1);
        imgSalary3 = (TextAwesome) findViewById(R.id.imgSalary3);

        civ_ThirdHoliday = (ImageView) findViewById(R.id.civ_ThirdHoliday);
        civ_SecondHoliday = (ImageView) findViewById(R.id.civ_SecondHoliday);
        civ_FirstHoliday = (ImageView) findViewById(R.id.civ_FirstHoliday);
        mTitle = (TextSwitcher) findViewById(R.id.title);
        mCoverFlow = (ViewPager) findViewById(R.id.coverflow);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReportingController.reportingApi(MainActivity.this, "Main Activity", UserInfoController.getUserEmail(MainActivity.this), UserInfoController.getUserEcode(MainActivity.this));

        findViews();
        pDialog = new ProgressDialog(MainActivity.this);
        toolbar.setTitle("");
        Cursor cursorUserInfo = getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
        if (cursorUserInfo.getCount() > 0) {
            cursorUserInfo.moveToLast();
            emailID = cursorUserInfo.getString(cursorUserInfo.getColumnIndex(COLUMN_EMAIL_ID));
            ecode = cursorUserInfo.getString(cursorUserInfo.getColumnIndex(COLUMN_USER_ID));
        }
        cursorUserInfo.close();
        setSupportActionBar(toolbar);
       /* DisplayMetrics metric = getResources().getDisplayMetrics();
        int densityDpi = (int) (metric.density * 160f);
*/
        SetDateTime();

        if (InternetConnectionDetector.isConnectingToInternet(MainActivity.this, false)) {
            updateLeavesApiCall();
        } else
            CommonFunction.createSnackbar(rl_Container, "No Internet Connection");


        //imageView.setAlpha(100);
        mHeaderLogo.setOnClickListener(this);
        getSupportActionBar().setBackgroundDrawable(null);
        getSupportActionBar().setTitle("");
        app_bar.addOnOffsetChangedListener(this);
        startAlphaAnimation(textviewTitle, 0, View.INVISIBLE);
        imageview_placeholder.setImageResource(R.drawable.photo1);

        SetUserData();
        rl_NotificationFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowAnnouncementActivity.class);
                intent.putExtra("UserName", tv_Name.getText().toString());
                intent.putExtra("UserDesignation", tv_Designation.getText().toString());
                intent.putExtra("NoticeTitle", notificationModels.get(0).getName());
                intent.putExtra("NoticeMessage", notificationModels.get(0).getMessage());
                intent.putExtra("NoticeDate", notificationModels.get(0).getDate());
                startActivity(intent);
            }
        });
        rl_NotificationSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowAnnouncementActivity.class);
                intent.putExtra("UserName", tv_Name.getText().toString());
                intent.putExtra("UserDesignation", tv_Designation.getText().toString());
                intent.putExtra("NoticeTitle", notificationModels.get(1).getName());
                intent.putExtra("NoticeMessage", notificationModels.get(1).getMessage());
                intent.putExtra("NoticeDate", notificationModels.get(1).getDate());
                startActivity(intent);
            }
        });
        rl_NotificationThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowAnnouncementActivity.class);
                intent.putExtra("UserName", tv_Name.getText().toString());
                intent.putExtra("UserDesignation", tv_Designation.getText().toString());
                intent.putExtra("NoticeTitle", notificationModels.get(2).getName());
                intent.putExtra("NoticeMessage", notificationModels.get(2).getMessage());
                intent.putExtra("NoticeDate", notificationModels.get(2).getDate());
                startActivity(intent);
            }
        });
        relative_ShiftChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertChangeShiftTime();
            }
        });
        relative_UpcomingBirthdays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BirthdayActivity.class);
                intent.putExtra("UserName", tv_Name.getText().toString());
                intent.putExtra("UserDesignation", tv_Designation.getText().toString());
                startActivity(intent);
            }
        });
        relative_UpcomingHolidays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HolidaysActivity.class);
                intent.putExtra("UserName", tv_Name.getText().toString());
                intent.putExtra("UserDesignation", tv_Designation.getText().toString());
                startActivity(intent);
            }
        });
        layMonthlyAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AttandanceActivity.class);
                intent.putExtra("UserName", tv_Name.getText().toString());
                intent.putExtra("UserDesignation", tv_Designation.getText().toString());
                startActivity(intent);
            }
        });
        getLastSevenDays();
        SetBirthDay();
        getHolidays();

        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                TextView textView = (TextView) inflater.inflate(R.layout.item_title, null);
                return textView;
            }
        });
       /* Animation in = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_out_bottom);
        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);*/
        mainActivityCtx = this;
        count = mData.size();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int pageMargin = 0;
        pageMargin = (int) ((metrics.widthPixels / 3.2) * 2);
        mCoverFlow.setPageMargin(-pageMargin);
        try {
            MyPagerAdapter adapter = new MyPagerAdapter(this, this.getSupportFragmentManager());
            mCoverFlow.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            FIRST_PAGE = count * LOOPS / 2;
            mCoverFlow.setOnPageChangeListener(adapter);
            mCoverFlow.setCurrentItem(FIRST_PAGE); // FIRST_PAGE
            mCoverFlow.setOffscreenPageLimit(3);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(metrics.widthPixels, (metrics.widthPixels / 3+180));
        //mCoverFlow.setLayoutParams(layoutParams);

        Cursor cursor = getContentResolver().query(CONTENT_ATTENDECE_INFO, null, null, null, null);
        if (cursor.getCount() > 0) {
            setDailyAttendence();
        } else {
            attendanceApiCall();
        }
        cursor.close();
        getShiftTime();
        alarmAtLogout();
        lastMonthName();
        laySalary1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strArg[0] = MyApplication.SalaryUrl + "eid=" + eid + "&date_month=" + lblSalaryYear1.getText().toString() + "-" + lblSalaryMonth1.getTag().toString() + "-01";
                strArg[1] = lblSalaryMonth1.getTag().toString() + lblSalaryYear1.getText().toString() + ".pdf";
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
                    }
                } else {
                    if (InternetConnectionDetector.isConnectingToInternet(MainActivity.this, false)) {
                        OpenAskPass("");
                    } else
                        CommonFunction.createSnackbar(rl_Container, "No Internet Connection");
                }

            }
        });
        laySalary2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strArg[0] = MyApplication.SalaryUrl + "eid=" + eid + "&date_month=" + lblSalaryYear2.getText().toString() + "-" + lblSalaryMonth2.getTag().toString() + "-01";
                strArg[1] = lblSalaryMonth2.getTag().toString() + lblSalaryYear2.getText().toString() + ".pdf";
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
                    }
                } else {
                    if (InternetConnectionDetector.isConnectingToInternet(MainActivity.this, false)) {
                        OpenAskPass("");
                    } else
                        CommonFunction.createSnackbar(rl_Container, "No Internet Connection");
                }

            }
        });
        laySalary3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strArg[0] = MyApplication.SalaryUrl + "eid=" + eid + "&date_month=" + lblSalaryYear3.getText().toString() + "-" + lblSalaryMonth3.getTag().toString() + "-01";
                strArg[1] = lblSalaryMonth3.getTag().toString() + lblSalaryYear3.getText().toString() + ".pdf";
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
                    }
                } else {
                    if (InternetConnectionDetector.isConnectingToInternet(MainActivity.this, false)) {
                        OpenAskPass("");
                    } else
                        CommonFunction.createSnackbar(rl_Container, "No Internet Connection");
                }
            }
        });

        rl_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SalarySlipActivity.class);
                String json = new Gson().toJson(listSalarySlip);
                intent.putExtra("list", json);
                intent.putExtra("eid", eid);
                intent.putExtra("UserName", tv_Name.getText().toString());
                intent.putExtra("UserDesignation", tv_Designation.getText().toString());
                startActivity(intent);
            }
        });
        rl_Leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeaveRequest.class);
                intent.putExtra("eid", eid);
               /* String json = new Gson().toJson(listSalarySlip);
                intent.putExtra("list", json);
                intent.putExtra("UserName", tv_Name.getText().toString());
                intent.putExtra("UserDesignation", tv_Designation.getText().toString());*/
                startActivity(intent);
            }
        });
        tv_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnnouncementActivity.class);
                intent.putExtra("UserName", tv_Name.getText().toString());
                intent.putExtra("UserDesignation", tv_Designation.getText().toString());
                startActivity(intent);
            }
        });
        bt_LoginLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
       /* bt_MeetingApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MeetingApplicationActivity.class);
                intent.putExtra("emailID", emailID);
                intent.putExtra("ecode", ecode);
                startActivity(intent);
            }
        });*/
        // GetCoverImage();
        CommonFunction.colorizeToolbar(toolbar, Color.BLACK, this);
    }

    private void alertChangeShiftTime() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to change Shift time?")
                .setCancelable(false)
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Calendar now = Calendar.getInstance();
                        TimePickerDialog dpd = TimePickerDialog.newInstance(
                                MainActivity.this,
                                now.get(Calendar.HOUR_OF_DAY),
                                now.get(Calendar.MINUTE),
                                now.get(Calendar.SECOND), false
                        );
                        dpd.show(getFragmentManager(), "Datepickerdialog");
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_thumbnail:
                image_Selection = "profile_Image";
                if (InternetConnectionDetector.isConnectingToInternet(MainActivity.this, false)) {
                    getImages();
                } else {
                    CommonFunction.createSnackbar(rl_Container, "No Internet Connection");
                }
                break;
        }
    }

    private void getImages() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 12);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 12);
            }
        } else {
            /*Intent intent = new Intent(MainActivity.this, ImagePickerActivity.class);
            Config config = new Config.Builder()
                    .setTabBackgroundColor(R.color.white)    // set tab background color. Default white.
                    .setTabSelectionIndicatorColor(R.color.blue)
                    .setCameraButtonColor(R.color.green)
                    .setSelectionLimit(1)    // set photo selection limit. Default unlimited selection.
                    .build();
            ImagePickerActivity.setConfig(config);
            startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);*/
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.e("MainActivity", "Inside result");
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_REQUEST_GET_IMAGES) {
                //  Parcelable[] parcelableUris = intent.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

               /* if (parcelableUris == null) {
                    return;
                }*/
                // Java doesn't allow array casting, this is a little hack
                /*Uri[] uris = new Uri[parcelableUris.length];
                System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);
*/
              /*  if (uris != null) {
                    for (Uri uri : uris) {
                        Log.e("MainActivity", "image_Selection" + image_Selection);
                        if (image_Selection.matches("header_Image")) {
                            imageView.setImageURI(uri);
                            //Glide.with(MainActivity.this).load(uri).into(imageView);
                            AddToLocalImage(uri + "");
                        } else {
                            Log.e("MainActivity", " uri: " + uri);
                            //  mHeaderLogo.setImageURI(uri);
                            Glide.with(MainActivity.this).load(uri).into(mHeaderLogo);
                            Log.e("MainActivity", "profile image");
                            File userProfilePicture = new File(decodeFile(uri.getPath(), 121, 121));
                            Glide.with(MainActivity.this).load(decodeFile(uri.getPath(), 121, 121)).into(mHeaderLogo);
                            new UploadFileToServer(MainActivity.this, emailID, userProfilePicture).execute();
                            ContentValues values = new ContentValues();
                            // values.put(COLUMN_USER_IMAGE, decodeFile(uri.getPath(), 121, 121));
                            values.put(COLUMN_USER_IMAGE, String.valueOf(uri));
                            Log.e("MainActivity", "values= " + values);
                            Cursor cursor = getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
                            Log.e("MainActivity", "cursor.getCount()= " + cursor.getCount());
                            if (cursor.getCount() > 0) {
                                cursor.moveToLast();
                                getContentResolver().update(CONTENT_USER_INFO, values, null, null);
                            }
                            cursor.close();
                            Log.e("TAG", " uri: " + uri);
                        }
                    }

                }*/
            }
        }
    }

    private void alarmAtLogout() {
        Cursor cursor = getContentResolver().query(CONTENT_ATTENDECE_INFO, null, null, null, null);
        Log.e("MainActivity", "cursor=" + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            Log.e("MainActivity", "logoutTime=" + cursor.getColumnIndex(COLUMN_ORIGINAL_OUTTIME));
            logoutTime = cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_OUTTIME));
        }
        cursor.close();
        if (logoutTime != null) {

            Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            String[] time = logoutTime.split(":");
            //int interval = 24 * 60 * 60 * 1000;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            Log.e("MainActivity", "in alarm");
            Log.e("MainActivity", "HOUR_OF_DAY =" + Integer.parseInt(time[0]));
            Log.e("MainActivity", "MINUTE =" + Integer.parseInt(time[1]));
            Log.e("MainActivity", "DAY_OF_WEEK" + calendar.get(Calendar.DAY_OF_WEEK));
            Log.e("MainActivity", "WEEK_OF_MONTH" + calendar.get(Calendar.WEEK_OF_MONTH));

            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));

            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private String decodeFile(String path, int DESIREDWIDTH, int DESIREDHEIGHT) {
        String strMyImagePath = null;
        Bitmap scaledBitmap = null;

        try {
            // Part 1: Decode image
            Bitmap unscaledBitmap = ScalingUtilities.decodeFile(path, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);

            if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap.getHeight() <= DESIREDHEIGHT)) {
                // Part 2: Scale image
                scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);
            } else {
                unscaledBitmap.recycle();
                return path;
            }

            // Store to tmp file

            String extr = Environment.getExternalStorageDirectory().toString();
            File mFolder = new File(extr + "/TMMFOLDER");
            if (!mFolder.exists()) {
                mFolder.mkdir();
            }

            String s = "tmp.png";

            File f = new File(mFolder.getAbsolutePath(), s);

            strMyImagePath = f.getAbsolutePath();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                Log.e("MainActivity", "FileNotFoundException =" + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("MainActivity", "Exception =" + e.getMessage());
                e.printStackTrace();
            }

            scaledBitmap.recycle();
        } catch (Throwable e) {
            Log.e("MainActivity", "Throwable =" + e.getMessage());
        }

        if (strMyImagePath == null) {
            return path;
        }
        return strMyImagePath;

    }

    private void AddToLocalImage(String image) {
        if (!image.equals("")) {
            getContentResolver().delete(CONTENT_PHOTO, null, null);
            ContentValues values = new ContentValues();
            values.put(COLUMN_COVER, image);
            getContentResolver().insert(CONTENT_PHOTO, values);
        }
    }

    private void GetBitmapFromUri() {
        Bitmap bit = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        MyApplication.imageCover = bit;
        getStringImage(bit);
    }

    public void getStringImage(Bitmap bit) {
        String encodedImage = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bit.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (Exception ex) {
        }
        AddToLocalImage(encodedImage);
    }

    private void GetCoverImage() {
        Cursor cursor = getContentResolver().query(CONTENT_PHOTO, null, null, null, null);
        Log.e("MainActivity", "GetCoverImage cursorcount=" + cursor.getCount());


        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            Log.e("MainActivity", "GetCoverImage value=" + cursor.getString(cursor.getColumnIndex(COLUMN_COVER)));
            String encodedImage = cursor.getString(cursor.getColumnIndex(COLUMN_COVER));
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            MyApplication.imageCover = decodedByte;
            Glide.with(MainActivity.this).load(cursor.getString(cursor.getColumnIndex(COLUMN_COVER))).into(imageView);

        } else {
            MyApplication.imageCover = BitmapFactory.decodeResource(getResources(), R.drawable.photo1);
        }

        imageView.setImageBitmap(MyApplication.imageCover);
        cursor.close();
    }

    private void SetDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        tv_Date.setText(new SimpleDateFormat("EEE").format(c.getTime()) + ", " + formattedDate);
        // Now formattedDate have current date/time
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            tv_GoodMorning.setText("Good Morning");
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            tv_GoodMorning.setText("Good Afternoon");
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            tv_GoodMorning.setText("Good Evening");
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            tv_GoodMorning.setText("Good Night");
        }
        timer = new Timer();
        myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 1000, 1000);
    }

    private void SetUserData() {

        Cursor cursor = getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            tv_Name.setText(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            textviewTitle.setText(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            Glide.with(MainActivity.this).load(cursor.getString(cursor.getColumnIndex(COLUMN_USER_IMAGE))).into(mHeaderLogo);
            Log.e("MainActivity", "entry value user_Image= " + cursor.getString(cursor.getColumnIndex(COLUMN_USER_IMAGE)));
            tv_Designation.setText(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DESIGNATION)));

            if (!cursor.getString(cursor.getColumnIndex(COLUMN_CL)).matches("null"))
                lblCL.setText(cursor.getString(cursor.getColumnIndex(COLUMN_CL)));
            else
                lblCL.setText("0");

            if (!cursor.getString(cursor.getColumnIndex(COLUMN_SL)).matches("null"))
                lblSL.setText(cursor.getString(cursor.getColumnIndex(COLUMN_SL)));
            else
                lblSL.setText("0");

            if (!cursor.getString(cursor.getColumnIndex(COLUMN_SL)).matches("null"))
                lblPL.setText(cursor.getString(cursor.getColumnIndex(COLUMN_PL)));
            else
                lblPL.setText("0");

            eid = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));


            lblTotal.setText("" + totalLeave(lblCL.getText().toString().trim(), lblSL.getText().toString().trim(), lblPL.getText().toString().trim()));
        }
        cursor.close();
        Cursor cursor_Notification = getContentResolver().query(CONTENT_NOTIFICAION, null, null, null, null);
        Log.e("MainActivity", "cursor_Notification==" + cursor_Notification.getCount());
        if (cursor_Notification.getCount() > 0) {
            rl_NotificationHeader.setVisibility(View.VISIBLE);
            if (cursor_Notification.getCount() >= 3) {
                showAnnouncementSize = 3;
                rl_NotificationFirst.setVisibility(View.VISIBLE);
                rl_NotificationSecond.setVisibility(View.VISIBLE);
                rl_NotificationThird.setVisibility(View.VISIBLE);
            } else if (cursor_Notification.getCount() == 2) {
                rl_NotificationFirst.setVisibility(View.VISIBLE);
                rl_NotificationSecond.setVisibility(View.VISIBLE);
                showAnnouncementSize = 2;
            } else if (cursor_Notification.getCount() == 1) {
                rl_NotificationFirst.setVisibility(View.VISIBLE);
                showAnnouncementSize = 1;
            }

            for (int i = 0; i < showAnnouncementSize; i++) {
                cursor_Notification.moveToNext();
                NotificationModel model = new NotificationModel();
                model.setTitle(cursor_Notification.getString(cursor_Notification.getColumnIndex(COLUMN_TITLE)));
                model.setMessage(cursor_Notification.getString(cursor_Notification.getColumnIndex(COLUMN_MESSAGE)));
                model.setName(cursor_Notification.getString(cursor_Notification.getColumnIndex(COLUMN_NAME)));
                model.setDate(cursor_Notification.getString(cursor_Notification.getColumnIndex(COLUMN_DATE)));
                notificationModels.add(model);
                switch (i) {
                    case 0:
                        tv_NameFirst.setText(cursor_Notification.getString(cursor_Notification.getColumnIndex(COLUMN_NAME)));
                        tv_DateFirst.setText(CommonFunction.getDate(cursor_Notification.getString(cursor_Notification.getColumnIndex(COLUMN_DATE))));
                        break;
                    case 1:
                        tv_NameSecond.setText(cursor_Notification.getString(cursor_Notification.getColumnIndex(COLUMN_NAME)));
                        tv_DateSecond.setText(CommonFunction.getDate(cursor_Notification.getString(cursor_Notification.getColumnIndex(COLUMN_DATE))));
                        break;
                    case 2:
                        tv_NameThird.setText(cursor_Notification.getString(cursor_Notification.getColumnIndex(COLUMN_NAME)));
                        tv_DateThird.setText(CommonFunction.getDate(cursor_Notification.getString(cursor_Notification.getColumnIndex(COLUMN_DATE))));
                        break;
                }
            }
        } else {
            rl_NotificationHeader.setVisibility(View.GONE);
        }
        cursor_Notification.close();


    }

    private int totalLeave(String cl, String sl, String pl) {
        Log.e("clclcl", "==" + cl);
        Log.e("slslsl", "==" + sl);
        Log.e("plplpl", "==" + pl);
        return (Integer.parseInt(cl) + Integer.parseInt(sl) + Integer.parseInt(pl));
    }

    private void getShiftTime() {
        Cursor cursor = getContentResolver().query(CONTENT_ATTENDECE_INFO, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            logoutTime = cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_OUTTIME));
            tv_Shift_In_Time.setText(MyApplication.GetHour("" + cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_INTIME))));
            tv_Shift_Out_Time.setText(MyApplication.GetHour("" + cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_OUTTIME))));

        }
        cursor.close();
    }

    private void getLastSevenDays() {
        for (int i = -7; i < 0; i++) {
            Log.e("iiiiiiiii", "iii==" + i);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_YEAR, i);
            Date newDate = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            Log.e("newDatenewDate", "newDate==" + newDate);
            CharSequence time = DateFormat.format("EEE", newDate); // gives like (Wednesday)
            String selection = COLUMN_DATE + " = '" + dateFormat.format(newDate) + "'";
            Log.e("selection", "===" + selection);
            Cursor cursor = getContentResolver().query(CONTENT_ATTENDECE_INFO, null, selection, null, null);
            tv_Calender_Dot_First.setVisibility(View.VISIBLE);
            tv_Calender_Dot_Second.setVisibility(View.VISIBLE);
            tv_Calender_Dot_Third.setVisibility(View.VISIBLE);
            tv_Calender_Dot_Fourth.setVisibility(View.VISIBLE);
            tv_Calender_Dot_Fifth.setVisibility(View.VISIBLE);
            tv_Calender_Dot_Six.setVisibility(View.VISIBLE);
            tv_Calender_Dot_Seven.setVisibility(View.VISIBLE);

            switch (i) {

                case -7:
                    tv_Calender_Day_First.setText("" + time);
                    tv_Calender_Date_First.setText("" + dayOfMonth);
                    Log.e("cursor.getCount()", "===" + cursor.getCount());
                    if (cursor.getCount() > 0) {
                        cursor.moveToLast();
                        if (cursor.getString(cursor.getColumnIndex(COLUMN_PRESNET_STATUS)).matches("present")) {

                            if (cursor.getString(cursor.getColumnIndex(COLUMN_MARK_STATUS)).matches("lateMark")) {
                                tv_Calender_Date_First.setVisibility(View.VISIBLE);
                                tv_Calender_Date_First.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Dot_First.setTextColor(getResources().getColor(R.color.blue));

                            } else if (cursor.getString(cursor.getColumnIndex(COLUMN_MARK_STATUS)).matches("earlyGo")) {
                                tv_Calender_Date_First.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Dot_First.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Date_First.setVisibility(View.VISIBLE);
                            }
                        } else {
                            tv_Calender_Date_First.setVisibility(View.VISIBLE);
                            tv_Calender_Dot_First.setTextColor(getResources().getColor(R.color.calender_red));
                            tv_Calender_Date_First.setTextColor(getResources().getColor(R.color.calender_red));


                        }
                    }
                    break;
                case -6:
                    tv_Calender_Day_Second.setText("" + time);
                    tv_Calender_Date_Second.setText("" + dayOfMonth);

                    if (cursor.getCount() > 0) {
                        cursor.moveToLast();
                        if (cursor.getString(cursor.getColumnIndex(COLUMN_PRESNET_STATUS)).matches("present")) {
                            if (cursor.getString(cursor.getColumnIndex(COLUMN_MARK_STATUS)).matches("lateMark")) {
                                tv_Calender_Date_Second.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Dot_Second.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Date_Second.setVisibility(View.VISIBLE);

                            } else if (cursor.getString(cursor.getColumnIndex(COLUMN_MARK_STATUS)).matches("earlyGo")) {
                                tv_Calender_Date_Second.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Dot_Second.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Date_Second.setVisibility(View.VISIBLE);
                            }
                        } else {
                            tv_Calender_Date_Second.setVisibility(View.VISIBLE);
                            tv_Calender_Dot_Second.setTextColor(getResources().getColor(R.color.calender_red));
                            tv_Calender_Date_Second.setTextColor(getResources().getColor(R.color.calender_red));
                        }
                    }
                    break;
                case -5:
                    tv_Calender_Day_Third.setText("" + time);
                    tv_Calender_Date_Third.setText("" + dayOfMonth);
                    if (cursor.getCount() > 0) {
                        cursor.moveToLast();
                        if (cursor.getString(cursor.getColumnIndex(COLUMN_PRESNET_STATUS)).matches("present")) {
                            if (cursor.getString(cursor.getColumnIndex(COLUMN_MARK_STATUS)).matches("lateMark")) {
                                tv_Calender_Date_Third.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Dot_Third.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Date_Third.setVisibility(View.VISIBLE);

                            } else if (cursor.getString(cursor.getColumnIndex(COLUMN_MARK_STATUS)).matches("earlyGo")) {
                                tv_Calender_Date_Third.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Dot_Third.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Date_Third.setVisibility(View.VISIBLE);
                            }
                        } else {
                            tv_Calender_Date_Third.setVisibility(View.VISIBLE);
                            tv_Calender_Dot_Third.setTextColor(getResources().getColor(R.color.calender_red));
                            tv_Calender_Date_Third.setTextColor(getResources().getColor(R.color.calender_red));
                        }
                    }
                    break;
                case -4:
                    tv_Calender_Day_Fourth.setText("" + time);
                    tv_Calender_Date_Fourth.setText("" + dayOfMonth);
                    if (cursor.getCount() > 0) {
                        cursor.moveToLast();
                        if (cursor.getString(cursor.getColumnIndex(COLUMN_PRESNET_STATUS)).matches("present")) {
                            if (cursor.getString(cursor.getColumnIndex(COLUMN_MARK_STATUS)).matches("lateMark")) {
                                tv_Calender_Date_Fourth.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Dot_Fourth.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Date_Fourth.setVisibility(View.VISIBLE);

                            } else if (cursor.getString(cursor.getColumnIndex(COLUMN_MARK_STATUS)).matches("earlyGo")) {
                                tv_Calender_Date_Fourth.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Dot_Fourth.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Date_Fourth.setVisibility(View.VISIBLE);
                            }

                        } else {
                            tv_Calender_Date_Fourth.setVisibility(View.VISIBLE);
                            tv_Calender_Date_Fourth.setTextColor(getResources().getColor(R.color.calender_red));
                            tv_Calender_Dot_Fourth.setTextColor(getResources().getColor(R.color.calender_red));

                        }
                    }
                    break;
                case -3:
                    tv_Calender_Day_Fifth.setText("" + time);
                    tv_Calender_Date_Fifth.setText("" + dayOfMonth);
                    if (cursor.getCount() > 0) {
                        cursor.moveToLast();
                        if (cursor.getString(cursor.getColumnIndex(COLUMN_PRESNET_STATUS)).matches("present")) {
                            if (cursor.getString(cursor.getColumnIndex(COLUMN_MARK_STATUS)).matches("lateMark")) {
                                tv_Calender_Date_Fifth.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Dot_Fifth.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Date_Fifth.setVisibility(View.VISIBLE);

                            } else if (cursor.getString(cursor.getColumnIndex(COLUMN_MARK_STATUS)).matches("earlyGo")) {
                                tv_Calender_Date_Fifth.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Dot_Fifth.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Date_Fifth.setVisibility(View.VISIBLE);
                            }
                        } else {
                            tv_Calender_Date_Fifth.setVisibility(View.VISIBLE);
                            tv_Calender_Dot_Fifth.setTextColor(getResources().getColor(R.color.calender_red));
                            tv_Calender_Date_Fifth.setTextColor(getResources().getColor(R.color.calender_red));
                        }
                    }
                    break;
                case -2:
                    tv_Calender_Day_Six.setText("" + time);
                    tv_Calender_Date_Six.setText("" + dayOfMonth);
                    if (cursor.getCount() > 0) {
                        cursor.moveToLast();
                        if (cursor.getString(cursor.getColumnIndex(COLUMN_PRESNET_STATUS)).matches("present")) {
                            if (cursor.getString(cursor.getColumnIndex(COLUMN_MARK_STATUS)).matches("lateMark")) {
                                tv_Calender_Date_Six.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Dot_Six.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Date_Six.setVisibility(View.VISIBLE);

                            } else if (cursor.getString(cursor.getColumnIndex(COLUMN_MARK_STATUS)).matches("earlyGo")) {
                                tv_Calender_Date_Six.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Dot_Six.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Date_Six.setVisibility(View.VISIBLE);
                            }
                        } else {
                            tv_Calender_Date_Six.setVisibility(View.VISIBLE);
                            tv_Calender_Dot_Six.setTextColor(getResources().getColor(R.color.calender_red));
                            tv_Calender_Date_Six.setTextColor(getResources().getColor(R.color.calender_red));
                        }
                    }
                    break;
                case -1:
                    tv_Calender_Day_Seven.setText("" + time);
                    tv_Calender_Date_Seven.setText("" + dayOfMonth);
                    if (cursor.getCount() > 0) {
                        cursor.moveToLast();
                        if (cursor.getString(cursor.getColumnIndex(COLUMN_PRESNET_STATUS)).matches("present")) {
                            if (cursor.getString(cursor.getColumnIndex(COLUMN_MARK_STATUS)).matches("lateMark")) {
                                tv_Calender_Date_Seven.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Dot_Seven.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Date_Seven.setVisibility(View.VISIBLE);

                            } else if (cursor.getString(cursor.getColumnIndex(COLUMN_MARK_STATUS)).matches("earlyGo")) {
                                tv_Calender_Date_Seven.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Dot_Seven.setTextColor(getResources().getColor(R.color.blue));
                                tv_Calender_Date_Seven.setVisibility(View.VISIBLE);
                            }

                        } else {
                            tv_Calender_Date_Seven.setVisibility(View.VISIBLE);
                            tv_Calender_Dot_Seven.setTextColor(getResources().getColor(R.color.calender_red));
                            tv_Calender_Date_Seven.setTextColor(getResources().getColor(R.color.calender_red));
                        }
                    }
                    break;

            }
            cursor.close();
        }

    }

    private void SetBirthDay() {
        Cursor cursor_Birthday = getContentResolver().query(CONTENT_UPCOMING_BIRTHDAYS, null, null, null, null);
        if (cursor_Birthday.getCount() > 0) {
            for (int i = 0; i < cursor_Birthday.getCount(); i++) {
                cursor_Birthday.moveToNext();
                Log.e("COLUMN_NAME", "==" + cursor_Birthday.getString(cursor_Birthday.getColumnIndex(COLUMN_NAME)));
                mData.add(new GameEntity(cursor_Birthday.getString(cursor_Birthday.getColumnIndex(COLUMN_USER_IMAGE))
                        , cursor_Birthday.getString(cursor_Birthday.getColumnIndex(COLUMN_NAME)), cursor_Birthday.getString(cursor_Birthday.getColumnIndex(COLUMN_DATE_OF_BIRTH))));

            }
        }
        cursor_Birthday.close();
    }

    private void getHolidays() {
        Cursor cursor_Holiday = getContentResolver().query(CONTENT_UPCOMING_HOLIDAYS, null, null, null, null);
        int holiday_Count = 0;
        if (cursor_Holiday.getCount() > 0) {
            for (int i = 0; i < cursor_Holiday.getCount(); i++) {
                cursor_Holiday.moveToNext();
                if (compareToDay(cursor_Holiday.getString(cursor_Holiday.getColumnIndex(COLUMN_DATE))) == 1) {
                    switch (holiday_Count) {
                        case 0:
                            rl_FirstHoliday.setVisibility(View.VISIBLE);
                            tv_First_Holiday.setText(cursor_Holiday.getString(cursor_Holiday.getColumnIndex(COLUMN_HOLIDAY_NAME)));
                            tv_First_Holiday_Date.setText(parseDateToddMMyyyy(cursor_Holiday.getString(cursor_Holiday.getColumnIndex(COLUMN_DATE))));
                            tv_First_Holiday_Remaining.setText(cursor_Holiday.getString(cursor_Holiday.getColumnIndex(COLUMN_HOLIDAY_REMAINING_DAYS)) + "\n Days \n Remaining");
                            Glide.with(this).load("http://www.binaryic.com/binary_smartview_api/gallery/" + cursor_Holiday.getString(cursor_Holiday.getColumnIndex(COLUMN_HOLIDAY_NAME)).replace(" ", "") + ".png").placeholder(R.drawable.ic_launcher).into(civ_FirstHoliday);
                            break;
                        case 1:
                            rl_SecondHoliday.setVisibility(View.VISIBLE);
                            tv_Second_Holiday.setText(cursor_Holiday.getString(cursor_Holiday.getColumnIndex(COLUMN_HOLIDAY_NAME)));
                            tv_Second_Holiday_Date.setText(parseDateToddMMyyyy(cursor_Holiday.getString(cursor_Holiday.getColumnIndex(COLUMN_DATE))));
                            tv_Second_Holiday_Remaining.setText(cursor_Holiday.getString(cursor_Holiday.getColumnIndex(COLUMN_HOLIDAY_REMAINING_DAYS)) + "\n Days \n Remaining");
                            Glide.with(this).load("http://www.binaryic.com/binary_smartview_api/gallery/" + cursor_Holiday.getString(cursor_Holiday.getColumnIndex(COLUMN_HOLIDAY_NAME)).replace(" ", "") + ".png").placeholder(R.drawable.ic_launcher).into(civ_SecondHoliday);
                            break;
                        case 2:
                            rl_ThirdHoliday.setVisibility(View.VISIBLE);
                            tv_Third_Holiday.setText(cursor_Holiday.getString(cursor_Holiday.getColumnIndex(COLUMN_HOLIDAY_NAME)));
                            tv_Third_Holiday_Date.setText(parseDateToddMMyyyy(cursor_Holiday.getString(cursor_Holiday.getColumnIndex(COLUMN_DATE))));
                            tv_Third_Holiday_Remaining.setText(cursor_Holiday.getString(cursor_Holiday.getColumnIndex(COLUMN_HOLIDAY_REMAINING_DAYS)) + "\n Days \n Remaining");
                            Glide.with(this).load("http://www.binaryic.com/binary_smartview_api/gallery/" + cursor_Holiday.getString(cursor_Holiday.getColumnIndex(COLUMN_HOLIDAY_NAME)).replace(" ", "") + ".png").placeholder(R.drawable.ic_launcher).into(civ_ThirdHoliday);
                            break;
                    }
                    holiday_Count++;
                }
                if (holiday_Count == 3)
                    break;
            }
            if (holiday_Count == 0) {
                rl_No_Holiday.setVisibility(View.VISIBLE);
            }
        } else {
            rl_No_Holiday.setVisibility(View.VISIBLE);
        }
        cursor_Holiday.close();
    }

    private void setDailyAttendence() {

        Cursor cursor = getContentResolver().query(CONTENT_ATTENDECE_INFO, null, null, null, null);
        Log.e("cursor.getCount()", "==" + cursor.getCount());
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                AttendenceModel attendenceModel = new AttendenceModel();
                attendenceModel.setOriginalInTime(cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_INTIME)));
                attendenceModel.setDay(cursor.getString(cursor.getColumnIndex(COLUMN_DAY)));
                attendenceModel.setOriginalOutTime(cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_OUTTIME)));
                attendenceModel.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                attendenceModel.setInTime(cursor.getString(cursor.getColumnIndex(COLUMN_IN_TIME)));
                attendenceModel.setOutTime(cursor.getString(cursor.getColumnIndex(COLUMN_OUT_TIME)));
                attendenceModel.setPresentStatus(cursor.getString(cursor.getColumnIndex(COLUMN_PRESNET_STATUS)));
                attendenceModel.setMarkStatus(cursor.getString(cursor.getColumnIndex(COLUMN_MARK_STATUS)));
                arrayAttendeceModel.add(attendenceModel);
            }

            getShiftTime();

        }
        cursor.close();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        if (arrayAttendeceModel.size() > 0) {
            rl_NoData.setVisibility(View.GONE);
            rl_LatestDay.setVisibility(View.VISIBLE);
            if (arrayAttendeceModel.get(0).getPresentStatus().equals("absent")) {
                tv_First_Out_Time.setText("    A    ");
                tv_First_In_Time.setText("    A    ");
            } else {
                if (arrayAttendeceModel.get(0).getMarkStatus().equals("lateMark") || arrayAttendeceModel.get(0).getMarkStatus().equals("earlyGo")) {
                    tv_First_In_Time.setTextColor(getResources().getColor(R.color.calender_red));
                    tv_First_In_Time.setTypeface(null, Typeface.BOLD);
                }
                if (!arrayAttendeceModel.get(0).getInTime().equals(""))
                    tv_First_In_Time.setText(MyApplication.GetHour(arrayAttendeceModel.get(0).getInTime()));
                else
                    tv_First_In_Time.setText("  NA  ");
                if (!arrayAttendeceModel.get(0).getOutTime().equals(""))
                    tv_First_Out_Time.setText(MyApplication.GetHour(arrayAttendeceModel.get(0).getOutTime()));
                else
                    tv_First_Out_Time.setText("  NA  ");
            }
            try {
                String date = (String) android.text.format.DateFormat.format("dd", dateFormat.parse((arrayAttendeceModel.get(0).getDate())));
                String month = (String) android.text.format.DateFormat.format("MMM", dateFormat.parse((arrayAttendeceModel.get(0).getDate())));
                tv_FirstDate.setText(date + " " + month);
            } catch (Exception e) {
            }
            if (arrayAttendeceModel.size() >= 1) {
                rl_SecondLatestDay.setVisibility(View.VISIBLE);
                if (arrayAttendeceModel.get(1).getPresentStatus().equals("absent")) {
                    tv_Second_Out_Time.setText("    A    ");
                    tv_Second_In_Time.setText("    A    ");
                } else {
                    if (arrayAttendeceModel.get(1).getMarkStatus().equals("lateMark") || arrayAttendeceModel.get(1).getMarkStatus().equals("earlyGo")) {
                        tv_Second_In_Time.setTextColor(getResources().getColor(R.color.calender_red));
                        tv_Second_In_Time.setTypeface(null, Typeface.BOLD);
                    }
                    if (!arrayAttendeceModel.get(1).getInTime().equals(""))
                        tv_Second_In_Time.setText(MyApplication.GetHour(arrayAttendeceModel.get(1).getInTime()));
                    else
                        tv_Second_In_Time.setText("  NA  ");
                    if (!arrayAttendeceModel.get(1).getOutTime().equals(""))
                        tv_Second_Out_Time.setText(MyApplication.GetHour(arrayAttendeceModel.get(1).getOutTime()));
                    else
                        tv_Second_Out_Time.setText("  NA  ");
                }
                try {
                    String date = (String) android.text.format.DateFormat.format("dd", dateFormat.parse((arrayAttendeceModel.get(1).getDate())));
                    String month = (String) android.text.format.DateFormat.format("MMM", dateFormat.parse((arrayAttendeceModel.get(1).getDate())));
                    tv_SecondDate.setText(date + " " + month);
                } catch (Exception e) {
                }
            } else {

            }
        } else {
            rl_NoData.setVisibility(View.VISIBLE);
            rl_LatestDay.setVisibility(View.GONE);
            rl_SecondLatestDay.setVisibility(View.GONE);
        }

        SetSalarySlip();
    }

    private void attendanceApiCall() {

        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_GET_ATTENDANCE_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("MainActivity", " attendence Responce " + response_String);
                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);
                    Log.e("MainActivity", "attendence Responce" + response.toString());
                    if (response.getString("Result").equalsIgnoreCase("1")) {
                        JSONArray arrayAttendence = response.getJSONArray("attendence");
                        JSONObject originalINOutTime = response.getJSONObject("inout_Time");

                        for (int i = 0; i < arrayAttendence.length(); i++) {
                            JSONObject jsonObjectPresent = arrayAttendence.getJSONObject(i);
                            ContentValues values = new ContentValues();
                            values.put(COLUMN_ORIGINAL_INTIME, originalINOutTime.getString("inTime"));
                            values.put(COLUMN_ORIGINAL_OUTTIME, originalINOutTime.getString("outTime"));
                            logoutTime = originalINOutTime.getString("outTime");
                            values.put(COLUMN_DATE, jsonObjectPresent.getString("date"));
                            values.put(COLUMN_IN_TIME, jsonObjectPresent.getString("in_time"));
                            values.put(COLUMN_PRESNET_STATUS, jsonObjectPresent.getString("status"));
                            values.put(COLUMN_OUT_TIME, jsonObjectPresent.getString("out_time"));
                            values.put(COLUMN_MARK_STATUS, jsonObjectPresent.getString("mark"));
                            values.put(COLUMN_DAY, jsonObjectPresent.getString("day"));

                            String selection = COLUMN_DATE + " = '" + jsonObjectPresent.getString("date") + "'";
                            Cursor cursor = getContentResolver().query(CONTENT_ATTENDECE_INFO, null, selection, null, null);
                            if (cursor.getCount() > 0)
                                getContentResolver().update(CONTENT_ATTENDECE_INFO, values, selection, null);
                            else
                                getContentResolver().insert(CONTENT_ATTENDECE_INFO, values);
                            cursor.close();
                        }
                    }
                } catch (Exception e) {
                    Log.e("errorrrrrrr", "==" + e.getMessage());
                }
                setDailyAttendence();
                pDialog.dismiss();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("errorerrorerror", "==" + error.getMessage());
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    if (response.statusCode == 400) {

                    } else {

                    }
                } else {
                }
                pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Cursor cursor = getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                    params.put("email", cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
                }
                cursor.close();
                Date todays_Date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -1);
                Date date = calendar.getTime();
                String dateOutput = dateFormat.format(date);
                params.put("todate", dateOutput);
                params.put("enddate", dateFormat.format(todays_Date));
                Log.e("MainActivity", "attendanceApiCall param=" + params);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "attendance");
    }


    private void lastMonthName() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        String previousMonthYear = new SimpleDateFormat("MMM-yyyy").format(cal.getTime());
        Log.e("previousMonthYear", "==" + previousMonthYear);

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        if (InternetConnectionDetector.isConnectingToInternet(MainActivity.this, false)) {
            OpenAskPass(hourOfDay + ":" + minute);
        } else
            CommonFunction.createSnackbar(rl_Container, "No Internet Connection");
        //Toast.makeText(MainActivity.this, "selected time " + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private void SetSalarySlip() {


        Log.e("arrayAttendeceModelsize", "==" + arrayAttendeceModel.size());
        if (arrayAttendeceModel.size() > 0) {
            layMainSalary.setVisibility(View.VISIBLE);
            rl_salary.setVisibility(View.VISIBLE);

            String lastM = "";
            String lastY = "";
            for (AttendenceModel attendenceModel : arrayAttendeceModel) {
                if (!lastM.equals(attendenceModel.getDate().split("-")[1]) || !lastY.equals(attendenceModel.getDate().split("-")[0])) {
                    SalarySlipModel salarySlipModel = new SalarySlipModel();
                    lastM = attendenceModel.getDate().split("-")[1];
                    lastY = attendenceModel.getDate().split("-")[0];
                    salarySlipModel.setMm(lastM);
                    salarySlipModel.setYear(lastY);
                    salarySlipModel.setMmm(GetMonthStr(lastM));
                    listSalarySlip.add(salarySlipModel);
                }
            }
            if (listSalarySlip.size() > 0) {
                tv_month.setText(listSalarySlip.get(0).getMmm() + " " + listSalarySlip.get(0).getYear());
                listSalarySlip.remove(0);
            } else
                tv_month.setText("");

        } else {
            layMainSalary.setVisibility(View.GONE);

        }

        if (listSalarySlip.size() > 0)
            rl_salary.setVisibility(View.VISIBLE);
        else
            rl_salary.setVisibility(View.GONE);


        laySalary1.setVisibility(View.GONE);
        laySalary2.setVisibility(View.GONE);
        laySalary3.setVisibility(View.GONE);
        for (int i = 0; i < listSalarySlip.size(); i++) {
            if (i == 2) {
                laySalary1.setVisibility(View.VISIBLE);
                lblSalaryMonth1.setText(listSalarySlip.get(i).getMmm());
                lblSalaryMonth1.setTag(listSalarySlip.get(i).getMm());
                lblSalaryYear1.setText(listSalarySlip.get(i).getYear());
            }
            if (i == 1) {
                laySalary2.setVisibility(View.VISIBLE);
                lblSalaryMonth2.setText(listSalarySlip.get(i).getMmm());
                lblSalaryMonth2.setTag(listSalarySlip.get(i).getMm());
                lblSalaryYear2.setText(listSalarySlip.get(i).getYear());
            }
            if (i == 0) {
                laySalary3.setVisibility(View.VISIBLE);
                lblSalaryMonth3.setText(listSalarySlip.get(i).getMmm());
                lblSalaryMonth3.setTag(listSalarySlip.get(i).getMm());
                lblSalaryYear3.setText(listSalarySlip.get(i).getYear());
            }
        }

    }

    private String GetMonthStr(String month) {
        switch (month) {
            case "01":
                return "Jan";
            case "02":
                return "Feb";
            case "03":
                return "Mar";
            case "04":
                return "Apr";
            case "05":
                return "May";
            case "06":
                return "Jun";
            case "07":
                return "Jul";
            case "08":
                return "Aug";
            case "09":
                return "Sep";
            case "10":
                return "Oct";
            case "11":
                return "Nov";
            case "12":
                return "Dec";
            default:
                return "00";
        }
    }

    public void ViewPdf(String fileName) {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/BinarySalarySlip/" + fileName);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            CommonFunction.createSnackbar(rl_Container, "No Application available to view PDF");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        if (InternetConnectionDetector.isConnectingToInternet(MainActivity.this, false)) {
                            OpenAskPass("");
                        } else
                            CommonFunction.createSnackbar(rl_Container, "No Internet Connection");
                    }
                }
            }
            break;
            case 12: {
                /*if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MainActivity.this, ImagePickerActivity.class);
                    Config config = new Config.Builder()
                            .setTabBackgroundColor(R.color.white)    // set tab background color. Default white.
                            .setTabSelectionIndicatorColor(R.color.blue)
                            .setCameraButtonColor(R.color.green)
                            .setSelectionLimit(1)    // set photo selection limit. Default unlimited selection.
                            .build();
                    ImagePickerActivity.setConfig(config);
                    startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
                }*/
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!emailID.matches("minakshi@binaryic.com")) {
            menu.findItem(R.id.action_admin).setVisible(false);
        } else
            menu.findItem(R.id.action_admin).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_rateus:
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;
            case R.id.action_meeting_application:
                intent = new Intent(MainActivity.this, MeetingApplicationActivity.class);
                intent.putExtra("emailID", emailID);
                intent.putExtra("ecode", ecode);
                startActivity(intent);
                break;
            case R.id.action_admin:
                if (InternetConnectionDetector.isConnectingToInternet(MainActivity.this, false)) {
                    Intent intent_Admin = new Intent(MainActivity.this, AdminActivity.class);
                    intent_Admin.putExtra("UserName", tv_Name.getText().toString());
                    intent_Admin.putExtra("UserDesignation", tv_Designation.getText().toString());
                    startActivity(intent_Admin);
                } else {
                    CommonFunction.createSnackbar(rl_Container, "No Internet Connection");
                }

                break;
            case R.id.action_logout:
                LogOut();
                break;
            case R.id.action_chat:
                LoginController.connectToSendBird(this, LoginController.getLoginDetails(MainActivity.this).getUser_ID(), LoginController.getLoginDetails(MainActivity.this).getNick_Name(),"","");
                break;
            case R.id.action_change_password:
                changePasswordDialog();
                break;
            case R.id.action_apply_leave:
                Intent leaveIntent = new Intent(MainActivity.this, LeaveRequest.class);
                leaveIntent.putExtra("eid", eid);
                startActivity(leaveIntent);
                break;
            case R.id.action_Notification:
                Intent intent_Notification = new Intent(MainActivity.this, AnnouncementActivity.class);
                intent_Notification.putExtra("UserName", tv_Name.getText().toString());
                intent_Notification.putExtra("UserDesignation", tv_Designation.getText().toString());

                startActivity(intent_Notification);
                break;
            case R.id.action_change_shift_time:
                alertChangeShiftTime();
                break;
            case R.id.action_edit:
                image_Selection = "header_Image";
                if (InternetConnectionDetector.isConnectingToInternet(MainActivity.this, false)) {
                    getImages();
                } else
                    CommonFunction.createSnackbar(rl_Container, "No Internet Connection");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changePasswordDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_change_password);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText et_change_dialog_email = (EditText) dialog.findViewById(R.id.et_change_dialog_email);
        final EditText et_change_dialog_OldPassword = (EditText) dialog.findViewById(R.id.et_change_dialog_OldPassword);
        final EditText et_change_dialog_NewPassword = (EditText) dialog.findViewById(R.id.et_change_dialog_NewPassword);
        final EditText et_change_dialog_ConfirmPassword = (EditText) dialog.findViewById(R.id.et_change_dialog_ConfirmPassword);
        final TextInputLayout TextInputDiaConfirmPassword = (TextInputLayout) dialog.findViewById(R.id.TextInputDiaConfirmPassword);
        final TextInputLayout TextInputDiaEmail = (TextInputLayout) dialog.findViewById(R.id.TextInputDiaEmail);
        final TextInputLayout TextInputDiaOldPassword = (TextInputLayout) dialog.findViewById(R.id.TextInputDiaOldPassword);
        final TextInputLayout TextInputDiaNewPassword = (TextInputLayout) dialog.findViewById(R.id.TextInputDiaNewPassword);
        TextView btn_change_dia_submit = (TextView) dialog.findViewById(R.id.btn_change_dia_submit);
        TextView btn_change_dia_cancel = (TextView) dialog.findViewById(R.id.btn_change_dia_cancel);
        btn_change_dia_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                   /* if (TextUtils.isEmpty(et_change_dialog_email.getText().toString())) {
                        TextInputDiaEmail.setError("Enter EmailId");*/
                   /* } else*/
                    if (TextUtils.isEmpty(et_change_dialog_OldPassword.getText().toString())) {
                        /*if (!Validation.changePassword(MainActivity.this, et_change_dialog_email.getText().toString()))
                            TextInputDiaEmail.setError("Please Enter Proper EmailID");
                        else*/
                        TextInputDiaOldPassword.setError("Please Enter Password");
                    } else if (TextUtils.isEmpty(et_change_dialog_NewPassword.getText().toString())) {
                        TextInputDiaNewPassword.setError("Please Enter Password Once Again");
                    } else if (TextUtils.isEmpty(et_change_dialog_ConfirmPassword.getText().toString())) {
                        TextInputDiaConfirmPassword.setError("Please Enter Password Once Again");
                    } else if (!et_change_dialog_NewPassword.getText().toString().matches(et_change_dialog_ConfirmPassword.getText().toString())) {
                        TextInputDiaConfirmPassword.setError("Password Mismatch,Try Again");
                    } else {
                        dialog.dismiss();
                        if (InternetConnectionDetector.isConnectingToInternet(MainActivity.this, false)) {
                            changePassword(emailID, et_change_dialog_OldPassword.getText().toString(), et_change_dialog_NewPassword.getText().toString());
                        } else
                            CommonFunction.createSnackbar(rl_Container, "No Internet Connection");
                    }
                } catch (Exception ex) {
                    Log.e("MainActivity", "eror" + ex.getMessage());
                }
            }
        });
        btn_change_dia_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void changePassword(final String emailID, final String oldpassword, final String newpassword) {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        URL_GET_CHANGE_PASSWORD = URL_GET_CHANGE_PASSWORD + "?" + "emailID=" + emailID + "&oldpassword=" + oldpassword + "&newpassword=" + newpassword;
        Log.e("Main activity", " URL_GET_CHANGE_PASSWORD " + URL_GET_CHANGE_PASSWORD);

        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL_GET_CHANGE_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("Main activity", " Responce " + response_String);

                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);

                    if (response.getString("success").equalsIgnoreCase("1")) {
                        CommonFunction.createSnackbar(rl_Container, "Password has been change successfully");
                    } else {
                        CommonFunction.createSnackbar(rl_Container, response.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Main Activity", " error " + e.getMessage());
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                Log.e("Main Activity", " Error " + error.getMessage());
                if (response != null) {
                    if (response.statusCode == 400) {

                    } else {

                    }
                } else {
                }
                pDialog.hide();
            }
        });
        MyApplication.getInstance().addToRequestQueue(strReq, "attendance");
    }

    private void LogOut() {
        try {
            getContentResolver().delete(CONTENT_USER_INFO, null, null);
            getContentResolver().delete(CONTENT_ATTENDECE_INFO, null, null);
            getContentResolver().delete(CONTENT_MEMBERS, null, null);
            getContentResolver().delete(CONTENT_USER, null, null);
            getContentResolver().delete(CONTENT_GROUP_CHANNEL, null, null);
            disconnectChat();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } catch (Exception ex) {
        }
    }

    private void disconnectChat() {
        SendBird.disconnect(new SendBird.DisconnectHandler() {
            @Override
            public void onDisconnected() {
                // You are disconnected from SendBird.
            }
        });
    }

    private void OpenAskPass(final String time) {
        try {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.salary_password);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final EditText et_pass = (EditText) dialog.findViewById(R.id.et_frgt_dialog_email);
            final TextInputLayout TextInputDiaEmail = (TextInputLayout) dialog.findViewById(R.id.TextInputDiaEmail);
            TextView btn_submit = (TextView) dialog.findViewById(R.id.btn_frgt_dia_submit);
            TextView btn_cancel = (TextView) dialog.findViewById(R.id.btn_frgt_dia_cancel);
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (et_pass.getText().toString().isEmpty()) {
                            TextInputDiaEmail.setError("Enter Password");
                        } else {
                            Cursor cursor = getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
                            if (cursor.getCount() > 0) {
                                cursor.moveToLast();
                                String pass = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
                                if (pass.equals(et_pass.getText().toString().trim())) {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(et_pass.getWindowToken(), 0);
                                    dialog.dismiss();
                                    if (TextUtils.isEmpty(time))
                                        new DownloadFile().execute(strArg);
                                    else
                                        changeShiftApiCall(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)), time);
                                } else {
                                    TextInputDiaEmail.setError("Wrong Password");
                                    et_pass.setText("");
                                }
                            }
                            cursor.close();
                        }
                    } catch (Exception ex) {
                    }
                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        } catch (Exception e) {
        }
    }

    private void changeShiftApiCall(final String emailID, final String inTime) {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_APPLY_SHIFT_CHANGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("Main activity", " Responce " + response_String);

                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);

                    if (response.getString("success").equalsIgnoreCase("1")) {
                        CommonFunction.createSnackbar(rl_Container, response.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Main Activity", " error " + e.getMessage());
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                Log.e("Main Activity", " Error " + error.getMessage());
                if (response != null) {
                    if (response.statusCode == 400) {

                    } else {

                    }
                } else {
                }
                pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("emailID", emailID);
                params.put("inTime", inTime);
                Log.e("MainActivity", "parameters" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "attendance");
    }

    private void updateLeavesApiCall() {
        Cursor cursor = getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            emailID = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID));
            URL_GET_UPDATE_LEAVES = URL_GET_UPDATE_LEAVES + "?" + "emailID=" + emailID;
            Log.e("Main activity", " URL_GET_UPDATE_LEAVES " + URL_GET_UPDATE_LEAVES);
        }
        cursor.close();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL_GET_UPDATE_LEAVES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("Main activity", " Responce " + response_String);

                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);

                    if (response.getString("success").equalsIgnoreCase("1")) {
                        ContentValues values = new ContentValues();
                        values.put(COLUMN_SL, response.getString("SL"));
                        values.put(COLUMN_CL, response.getString("CL"));
                        values.put(COLUMN_PL, response.getString("PL"));
                        String selection = COLUMN_EMAIL_ID + " ='" + emailID + "' ";
                        Cursor cursor = getContentResolver().query(CONTENT_USER_INFO, null, selection, null, null);
                        if (cursor.getCount() > 0) {
                            getContentResolver().update(CONTENT_USER_INFO, values, selection, null);
                        } else {
                            getContentResolver().insert(CONTENT_USER_INFO, values);
                        }
                        cursor.moveToLast();
                        lblCL.setText(cursor.getString(cursor.getColumnIndex(COLUMN_CL)));
                        lblSL.setText(cursor.getString(cursor.getColumnIndex(COLUMN_SL)));
                        lblPL.setText(cursor.getString(cursor.getColumnIndex(COLUMN_PL)));
                        cursor.close();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Activity", " error " + e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                Log.e("Main Activity", " Error " + error.getMessage());
                if (response != null) {
                    if (response.statusCode == 400) {

                    } else {

                    }
                } else {
                }

            }
        });
        MyApplication.getInstance().addToRequestQueue(strReq, "attendance");
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(textviewTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(textviewTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(linearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(linearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            final Calendar calendar = Calendar.getInstance();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String time = MyApplication.GetHour(calendar.getTime().getHours() + ":" + calendar.getTime().getMinutes());
                    tv_time.setText(time.split(" ")[0]);
                    tvAm.setText(time.split(" ")[1]);
                }
            });
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, String> {
        final ProgressDialog pDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
                String fileName = strings[1];  // -> maven.pdf
                Log.e("SalaryUrl", strings[0]);
                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                File folder = new File(extStorageDirectory, "BinarySalarySlip");
                folder.mkdir();
                File pdfFile = new File(folder, fileName);
                try {
                    pdfFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileDownloader.downloadFile(fileUrl, pdfFile);

                return fileName;
            } catch (Exception ex) {
            }
            return "";
        }

        @Override
        protected void onPostExecute(String str) {
            pDialog.dismiss();
            if (!str.equals("")) {
                ViewPdf(str);
            } else
                CommonFunction.createSnackbar(rl_Container, "Something went wrong");
        }
    }

    @Override
    public void onBackPressed() {
        alertForExit();
    }

    private void alertForExit() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Exit ?")
                .setCancelable(false)
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}





