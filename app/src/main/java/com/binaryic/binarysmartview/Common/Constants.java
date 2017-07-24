package com.binaryic.binarysmartview.Common;

import android.net.Uri;

/**
 * Created by admin on 1/29/2016.
 */
public class Constants {
    public static final String URL_REPORTING = "http://www.binaryic.com/IntranetAPI/reporting.php";
    public static final String URL_RETRIVE_REPORT = "http://www.binaryic.com/IntranetAPI/retreive_report.php";
    public static final String URL_GET_ALL_USERS_SHIFT_CHANGE_DETAILS = "http://www.binaryic.com/IntranetAPI/getallshifttimechangedetails.php";
    public static final String URL_UPDATE_SHIFT_CHANGE_ACCEPTANCE_STATUS = "http://www.binaryic.com/IntranetAPI/changeshifttimerequest.php";
    public static final String URL_UPDATE_MEETING_APPLICATION_ACCEPTANCE_STATUS = "http://www.binaryic.com/IntranetAPI/changemeeetingrequest.php";
    public static final String URL_UPDATE_LEAVE_ACCEPTANCE_STATUS = "http://www.binaryic.com/IntranetAPI/changeleaverequest.php";
    public static final String URL_RETRIVE_MESSAGE = "http://www.binaryic.com/IntranetAPI/retrievenotificationbysir.php";
    public static final String URL_SEND_MESSAGE = "http://www.binaryic.com/IntranetAPI/sendnotificationbysir.php";
    public static final String URL_GET_DEPARTMENT_INFO = "http://www.binaryic.com/IntranetAPI/getdepartmentnames.php";
    public static final String URL_GET_ALL_REGISTER_USERS = "http://www.binaryic.com/IntranetAPI/getandroidregusers.php";
    public static final String URL_GET_ALL_USERS_MEETING_DETAILS = "http://www.binaryic.com/IntranetAPI/getmeetingdetails.php";
    public static final String URL_GET_ALL_USERS_LEAVE_DETAILS = "http://www.binaryic.com/IntranetAPI/getleavedetails.php";
    public static final String URL_GET_USER_STATUS = "http://www.binaryic.com/IntranetAPI/loginstatus.php";
    public static final String URL_MEETING_APPLICATION = "http://www.binaryic.com/IntranetAPI/meetingapp.php";
    public static final String URL_GET_SALARY_SLIP = "http://www.binaryic.com/intranet/salary_pdf/BINARY-SALARY-SLIP.php";
    public static final String URL_GET_LOGIN_DATA = "http://www.binaryic.com/binary_smartview_api/login.php";
    public static final String URL_CHECK_OTP = "http://www.binaryic.com/IntranetAPI/otpverify.php";
    public static final String URL_CHECK_IN = "http://www.binaryic.com/IntranetAPI/checkin.php";
    public static final String URL_GET_ATTENDANCE_DATA = "http://www.binaryic.com/binary_smartview_api/attendance.php";
    public static final String URL_GET_UPCOMING_BIRTHDAY = "http://binaryic.com/binary_smartview_api/birthday.php";
    public static final String URL_GET_UPCOMING_HOLIDAY = "http://www.binaryic.com/binary_smartview_api/holiday.php";
    public static final String URL_GET_ALL_ANNOUNCEMENT = "http://www.binaryic.com/IntranetAPI/announcements.php";
    public static final String URL_GET_FORGOT_PASSWORD = "http://www.binaryic.com/IntranetAPI/forgot_password.php";
    public static String URL_GET_CHANGE_PASSWORD = "http://www.binaryic.com/IntranetAPI/changepassword.php";
    public static String URL_GET_UPDATE_LEAVES = "http://www.binaryic.com/IntranetAPI/leavebalance.php";
    public static String URL_APPLY_SHIFT_CHANGE = "http://www.binaryic.com/IntranetAPI/changeshifttime.php";
    public static String URL_CHANGE_IN_AND_OUT_TIME = "http://www.binaryic.com/IntranetAPI/changelogintime.php";
    public static String URL_EMP_PROFILE_UPDATE = "http://www.binaryic.com/IntranetAPI/empprofileupdate.php";
    public static String URL_APPLY_LEAVE = "http://www.binaryic.com/IntranetAPI/leaverequest.php";

    public static final String CONETNT_PROTOCOL = "content://";
    public static final String AUTHORITY = "com.binary.binarysmartview";
    public static final String PATH_REGISTER_USER = "PATH_REGISTER_USER";
    public static final String PATH_ALL_USER_SHIFT_CAHNGE = "PATH_ALL_USER_SHIFT_CAHNGE";
    public static final String PATH_ALL_USER_MEETINGS = "PATH_ALL_USER_MEETINGS";
    public static final String PATH_MAC = "PATH_MAC";
    public static final String PATH_USER_INFO = "_luserinfodata";
    public static final String PATH_ATTENDENCE_DAYS = "_lattendecedays";
    public static final String PATH_HOLIDAYS = "_latpathholidays";
    public static final String PATH_BIRTHDAY = "_latpathbirthday";
    public static final String PATH_NOTIFICATION = "PATH_NOTIFICATION";
    public static final String PATH_PHOTO = "_latpathphoto";
    public static final String PATH_ALL_USER_LEAVE = "PATH_ALL_USER_LEAVE";
    public static final String PATH_DEPARMENT_INFO = "PATH_DEPARMENT_INFO";
    public static final String PATH_REPORTS = "PATH_REPORTS";
    public static String TABLE_ALL_USER_SHIFT_CHANGE = "TABLE_ALL_USER_SHIFT_CHANGE";
    public static String TABLE_REGISTER_USER = "TABLE_REGISTER_USER";
    public static String TABLE_ALL_USER_MEETINGS = "TABLE_ALL_USER_MEETINGS";
    public static String TABLE_ALL_USER_LEAVE = "TABLE_ALL_USER_LEAVE";
    public static String TABLE_MAC = "TABLE_MAC";
    public static String TABLE_USER_INFO = "tbl_user_info";
    public static String TABLE_ATTENDECE_DAYS = "tbl_attendece_days";
    public static String TABLE_BIRTHDAYS = "tbl_birthdays";
    public static String TABLE_HOLIDAY = "tbl_holiday";
    public static String TABLE_ANNOUNCEMENT = "TABLE_ANNOUNCEMENT";
    public static String TABLE_PHOTO = "tbl_photo";
    public static String TABLE_DEPARMENT_INFO = "TABLE_DEPARMENT_INFO";
    public static String TABLE_REPORTS = "TABLE_REPORTS";
    //columes of table userinfo
    public static String COLUMN_TIME = "COLUMN_TIME";
    public static String COLUMN_NEW_OUT = "COLUMN_NEW_OUT";
    public static String COLUMN_NEW_IN = "COLUMN_NEW_IN";
    public static String COLUMN_CURRENT_OUT = "COLUMN_CURRENT_OUT";
    public static String COLUMN_CURRENT_IN = "COLUMN_CURRENT_IN";
    public static String COLUMN_DEPARTMENT_ID = "COLUMN_DEPARTMENT_ID";
    public static String COLUMN_TO_TIME = "COLUMN_TO_TIME";
    public static String COLUMN_FROM_TIME = "COLUMN_FROM_TIME";
    public static String COLUMN_PURPOSE = "COLUMN_PURPOSE";
    public static String COLUMN_APPLICATION_ID = "COLUMN_APPLICATION_ID";
    public static String COLUMN_DATE_OF_APPLY = "COLUMN_DATE_OF_APPLY";
    public static String COLUMN_STATUS = "COLUMN_STATUS";
    public static String COLUMN_DURATION = "COLUMN_DURATION";
    public static String COLUMN_TYPE = "COLUMN_TYPE";
    public static String COLUMN_TO_DATE = "COLUMN_TO_DATE";
    public static String COLUMN_FROM_DATE = "COLUMN_FROM_DATE";
    public static String COLUMN_MAC_ID = "COLUMN_MAC_ID";
    public static String COLUMN_NAME = "colname";
    public static String COLUMN_HOLIDAY_NAME = "colholidayname";
    public static String COLUMN_HOLIDAY_REMAINING_DAYS = "colholidayremainingday";
  //  public static String COLUMN_USER_ID = "coluserid";
    public static String COLUMN_TITLE = "COLUMN_TITLE";
    public static String COLUMN_MESSAGE = "COLUMN_MESSAGE";
    public static String COLUMN_IN_TIME = "colintime";
    public static String COLUMN_DESIGNATION_NAME = "coldesignationname";
    public static String COLUMN_DEPARTMENT_NAME = "COLUMN_DEPARTMENT_NAME";
    public static String COLUMN_OUT_TIME = "colouttime";
    public static String COLUMN_PRESNET_STATUS = "colpresnetstatus";
    public static String COLUMN_MARK_STATUS = "collmarkstatus";
    public static String COLUMN_DATE = "coldate";
    public static String COLUMN_DATE_OF_JOINING = "coldateofjoining";
    public static String COLUMN_USER_LOGIN_STATUS = "colloginstatus";
    public static String COLUMN_USER_DESIGNATION = "coluserdesignation";
    public static String COLUMN_MOBILE = "colmobile";
    public static String COLUMN_EMAIL_ID = "colemailid";
    public static String COLUMN_ORIGINAL_OUTTIME = "coloriginalouttime";
    public static String COLUMN_ORIGINAL_INTIME = "coloriginalintime";
    public static String COLUMN_DAY = "colday";
    public static String COLUMN_PERSONAL_EMAIL_ID = "colpersonalemail";
    public static String COLUMN_USER_IMAGE = "coluserimage";
    public static String COLUMN_GRADUATION = "colgraduation";
    public static String COLUMN_DATE_OF_BIRTH = "coldateofbirth";
    public static String COLUMN_PASSWORD = "colpassword";
    public static String COLUMN_SL = "colsl";
    public static String COLUMN_CL = "colcl";
    public static String COLUMN_PL = "colpl";
    public static String COLUMN_COVER = "colcover";
    public static String COLUMN_PAGE_NAME = "COLUMN_PAGE_NAME";

    public static final Uri CONTENT_ALL_USER_SHIFT_CHANGE = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_ALL_USER_SHIFT_CAHNGE);
    public static final Uri CONTENT_DEPARMENT_INFO = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_DEPARMENT_INFO);
    public static final Uri CONTENT_REGISTER_USER = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_REGISTER_USER);
    public static final Uri CONTENT_ALL_USER_MEETINGS = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_ALL_USER_MEETINGS);
    public static final Uri CONTENT_ALL_USER_LEAVE = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_ALL_USER_LEAVE);
    public static final Uri CONTENT_MAC = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_MAC);
    public static final Uri CONTENT_USER_INFO = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_USER_INFO);
    public static final Uri CONTENT_ATTENDECE_INFO = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_ATTENDENCE_DAYS);
    public static final Uri CONTENT_UPCOMING_HOLIDAYS = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_HOLIDAYS);
    public static final Uri CONTENT_UPCOMING_BIRTHDAYS = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_BIRTHDAY);
    public static final Uri CONTENT_NOTIFICAION = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_NOTIFICATION);
    public static final Uri CONTENT_PHOTO = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_PHOTO);
    public static final Uri CONTENT_REPORTS = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_REPORTS);

    //chat
    //path
    public static final String PATH_USER = "PATH_USER";
    public static final String PATH_MEMBERS = "PATH_MEMBERS";
    public static final String PATH_GROUP_CHANNEL = "PATH_GROUP_CHANNEL";

    //table
    public static String TABLE_MEMBERS = "TABLE_MEMBERS";
    public static String TABLE_USER = "TABLE_USER";
    public static String TABLE_GROUP_CHANNEL = "TABLE_GROUP_CHANNEL";


    //columns
    public static String COLUMN_ID = "COLUMN_ID";
    public static String COLUMN_MSG = "COLUMN_MSG";
    public static String COLUMN_CHANNEL_URL = "COLUMN_CHANNEL_URL";
    public static String COLUMN_USER_ID = "COLUMN_USER_ID";
    public static String COLUMN_NICKNAME = "COLUMN_NICKNAME";
    public static String COLUMN_PROFILE_IMAGE = "COLUMN_PROFILE_IMAGE";
    public static String COLUMN_GROUP_NAME = "COLUMN_GROUP_NAME";
    public static String COLUMN_GROUP_USER_ID = "COLUMN_GROUP_USER_ID";
    public static String COLUMN_IS_ACTIVE = "COLUMN_IS_ACTIVE";

    //content
    public static final Uri CONTENT_USER = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_USER);
    public static final Uri CONTENT_MEMBERS = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_MEMBERS);
    public static final Uri CONTENT_GROUP_CHANNEL = Uri.parse(CONETNT_PROTOCOL + AUTHORITY + "/" + PATH_GROUP_CHANNEL);
}
