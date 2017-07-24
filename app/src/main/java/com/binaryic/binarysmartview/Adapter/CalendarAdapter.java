package com.binaryic.binarysmartview.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.binarysmartview.Common.InternetConnectionDetector;
import com.binaryic.binarysmartview.Common.MyApplication;
import com.binaryic.binarysmartview.Models.AttendenceModel;
import com.binaryic.binarysmartview.R;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.sephiroth.android.library.tooltip.Tooltip;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PASSWORD;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_USER_INFO;
import static com.binaryic.binarysmartview.Common.Constants.URL_CHANGE_IN_AND_OUT_TIME;

/**
 * Created by MY PC on 3/10/2016.
 */
public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> implements TimePickerDialog.OnTimeSetListener {
    private Dialog dialog = null;
    private TextView tv_ChangeDate;
    private TextView tv_Change_In_Time;
    private TextView tv_Change_Out_Time;
    private LinearLayout ll_Change_In_Time;
    private LinearLayout ll_Change_Out_Time;
    Activity context;
    private int dayOfWeek;
    ArrayList<FullDate> list;
    ClickListener clickListener;
    private static final int DAY_OFFSET = 1;
    int month, year, daysInMonth, currentDayOfMonth, currentWeekDay;
    private final String[] weekdays = new String[]{"Sun", "Mon", "Tue",
            "Wed", "Thu", "Fri", "Sat"};
    private final String[] months = {"January", "February", "March",
            "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
    private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30,
            31, 30, 31};

    List<AttendenceModel> listAttendence = new ArrayList<>();
    private String entry_Time, in_Time, out_Time, changeDate;


    public CalendarAdapter(Activity context, int month, int year, List<AttendenceModel> listAttendence) {
        this.context = context;
        this.month = month;
        this.year = year;
        list = new ArrayList<FullDate>();
        this.listAttendence = listAttendence;
        Calendar calendar = Calendar.getInstance();
        setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
        setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
        printMonth(month, year);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.screen_gridcell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            holder.calendarday.setText(list.get(position).getDate());
            Log.e("Calender Adapter", "date =" + list.get(position).getDate());
            Log.e("Calender Adapter", "date =" + list.get(position).getMonth());
            Log.e("Calender Adapter", "date =" + list.get(position).getYear());
            final Calendar calendar = Calendar.getInstance();
            String listDate = Integer.parseInt(list.get(position).getDate()) + "/" + GetMonthInt(list.get(position).getMonth()) + "/" + list.get(position).getYear();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(listDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            Date todayDate = calendar.getTime();
           /* holder.calendarday.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return true;
                }
            });*/
            if (date.compareTo(todayDate) <= 0) {
                SimpleDateFormat formatterCheck = new SimpleDateFormat("yyyy-MM-dd");
                Date dateMin = formatterCheck.parse(listAttendence.get(listAttendence.size() - 1).getDate());
                if (dateMin.compareTo(date) <= 0) {
                    if (GetMonthInteger(list.get(position).getMonth()) != month) {
                        holder.calendarday.setTextColor(context.getResources().getColor(R.color.calander_privious));
                    }
                    holder.calendarday.setText(list.get(position).getDate());
                    if (holder.calendarday.getText().equals(""))
                        holder.calendarday.setVisibility(View.GONE);
                    String tempDate = list.get(position).getYear() + "-" + GetMonthInt(list.get(position).getMonth()) + "-" + (Integer.parseInt(list.get(position).getDate()) < 10 ? "0" + Integer.parseInt(list.get(position).getDate()) : Integer.parseInt(list.get(position).getDate()));

                    for (AttendenceModel model : listAttendence) {

                        if (model.getDate().equals(tempDate)) {
                            holder.calendarday.setTag(model.getInTime().equals("") ? " NA " : MyApplication.GetHour(model.getInTime()) + "  " + (model.getOutTime().equals("") ? " NA " : MyApplication.GetHour(model.getOutTime())));

                            if (model.getPresentStatus().equals("absent")) {
                                holder.calendarday.setTag("A" + "  " + "A");
                                holder.layDot.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.red_circle));
                                break;
                            } else {
                                if (model.getMarkStatus().equals("lateMark") || model.getMarkStatus().equals("earlyGo")) {
                                    holder.layDot.setBackground(context.getResources().getDrawable(R.drawable.blue_circle));
                                    break;
                                }
                            }
                        }
                    }
                    if (dayOfWeek == Calendar.SUNDAY)
                        holder.calendarday.setTextColor(context.getResources().getColor(R.color.red));
                } else {
                    holder.itemView.setVisibility(View.GONE);
                }
            } else {
                holder.calendarday.setTextColor(context.getResources().getColor(R.color.calander_privious));
                if (dayOfWeek == Calendar.SUNDAY)
                    holder.calendarday.setTextColor(context.getResources().getColor(R.color.red));
            }

        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setClickListener(ClickListener clickListener) {
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        if (entry_Time.matches("inTime")) {
            in_Time = hourOfDay + ":" + minute;
            tv_Change_In_Time.setText(MyApplication.GetHour(in_Time));

        } else if (entry_Time.matches("outTime")) {
            out_Time = hourOfDay + ":" + minute;
            tv_Change_Out_Time.setText(MyApplication.GetHour(out_Time));
        }


    }

    private void OpenAskPass(final String in_Time, final String out_Time) {
        try {
            final Dialog dialog = new Dialog(context);
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
                            Cursor cursor = context.getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
                            if (cursor.getCount() > 0) {
                                cursor.moveToLast();
                                String pass = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
                                if (pass.equals(et_pass.getText().toString().trim())) {
                                    InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(et_pass.getWindowToken(), 0);
                                    dialog.dismiss();
                                    if (InternetConnectionDetector.isConnectingToInternet(context, false)) {
                                        changeInAndOutTimeApiCall(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)), in_Time, out_Time, changeDate);
                                    } else
                                        Toast.makeText(context, "No Internet..", Toast.LENGTH_SHORT).show();
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

    private void changeInAndOutTimeApiCall(final String emailID, final String inTime, final String outTime, final String changeDate) {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_CHANGE_IN_AND_OUT_TIME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("Main activity", " Responce " + response_String);

                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);

                    if (response.getString("success").equalsIgnoreCase("1")) {
                        Toast.makeText(context, response.getString("msg"), Toast.LENGTH_SHORT).show();
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
                params.put("outTime", outTime);
                params.put("changeDate", changeDate);
                Log.e("MainActivity", "parameters" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "attendance");
    }

    public interface ClickListener {
        public void ItemClicked(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView calendarday;
        LinearLayout layDot;
        View itemView;
        // View view1;

        public ViewHolder(final View itemView) {
            super(itemView);
            calendarday = (TextView) itemView.findViewById(R.id.calendar_day_gridcell);
            layDot = (LinearLayout) itemView.findViewById(R.id.layDots);
            this.itemView = itemView;
            //  view1 = itemView.findViewById(R.id.view1);
            calendarday.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            String[] date = String.valueOf(v.getTag()).split("  ");
            if (dayOfWeek == Calendar.SUNDAY) {
                Toast.makeText(context, "It's a Holiday", Toast.LENGTH_SHORT).show();
            } else {
                if (date.length > 1)
                    inAndOutTimeDialog(v, getPosition(), date[0], date[1]);
                else
                    Toast.makeText(context, "Data is not Updated", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void inAndOutTimeDialog(View v, int position, String inTime, String outTime) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_change_in_out_time);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv_ChangeDate = (TextView) dialog.findViewById(R.id.tv_ChangeDate);
        tv_Change_In_Time = (TextView) dialog.findViewById(R.id.tv_Change_In_Time);
        tv_Change_Out_Time = (TextView) dialog.findViewById(R.id.tv_Change_Out_Time);
        ll_Change_In_Time = (LinearLayout) dialog.findViewById(R.id.ll_Change_In_Time);
        ll_Change_Out_Time = (LinearLayout) dialog.findViewById(R.id.ll_Change_Out_Time);
        tv_Change_In_Time.setText(inTime);
        in_Time = inTime;
        tv_Change_Out_Time.setText(outTime);
        out_Time = outTime;
        list.get(position).getDate();
        list.get(position).getMonth();
        list.get(position).getYear();
        changeDate = list.get(position).getYear() + "-" + list.get(position).getMonth() + "-" + list.get(position).getDate();
        tv_ChangeDate.setText("" + list.get(position).getDate() + " " + list.get(position).getMonth());
        dialog.show();


        ll_Change_In_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry_Time = "inTime";
                Calendar now = Calendar.getInstance();
                TimePickerDialog dpd = TimePickerDialog.newInstance(
                        CalendarAdapter.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND), false
                );
                dpd.show(context.getFragmentManager(), "In");


            }
        });
        ll_Change_Out_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry_Time = "outTime";
                Calendar now = Calendar.getInstance();
                TimePickerDialog dpd = TimePickerDialog.newInstance(
                        CalendarAdapter.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND), false
                );
                dpd.show(context.getFragmentManager(), "Out");

            }
        });

        Button bt_DialogSubmit = (Button) dialog.findViewById(R.id.bt_DialogSubmit);
        Button bt_DialogCancel = (Button) dialog.findViewById(R.id.bt_DialogCancel);
        bt_DialogSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.dismiss();
                    Log.e("CalenderAdapter", "in_Time =" + in_Time + "out_Time =" + out_Time);
                    OpenAskPass(in_Time, out_Time);

                } catch (Exception ex) {
                    Log.e("CalenderAdapter", "eror" + ex.getMessage());
                }
            }
        });
        bt_DialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    public int getCurrentDayOfMonth() {
        return currentDayOfMonth;
    }

    private void setCurrentDayOfMonth(int currentDayOfMonth) {
        this.currentDayOfMonth = currentDayOfMonth;
    }

    public void setCurrentWeekDay(int currentWeekDay) {
        this.currentWeekDay = currentWeekDay;
    }

    public int getCurrentWeekDay() {
        return currentWeekDay;
    }

    private String getMonthAsString(int i) {
        return months[i];
    }

    private String getWeekDayAsString(int i) {
        return weekdays[i];
    }

    private int getNumberOfDaysOfMonth(int i) {
        return daysOfMonth[i];
    }

    private void printMonth(int mm, int yy) {
        Log.d("Log", "==> printMonth: mm: " + mm + " " + "yy: " + yy);
        int trailingSpaces = 0;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;

        int currentMonth = mm - 1;
        String currentMonthName = getMonthAsString(currentMonth);
        daysInMonth = getNumberOfDaysOfMonth(currentMonth);

        Log.d("Log", "Current Month: " + " " + currentMonthName + " having "
                + daysInMonth + " days.");

        GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
        Log.d("Log", "Gregorian Calendar:= " + cal.getTime().toString());

        if (currentMonth == 11) {
            prevMonth = currentMonth - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 0;
            prevYear = yy;
            nextYear = yy + 1;
            Log.d("Log", "*->PrevYear: " + prevYear + " PrevMonth:"
                    + prevMonth + " NextMonth: " + nextMonth
                    + " NextYear: " + nextYear);
        } else if (currentMonth == 0) {
            prevMonth = 11;
            prevYear = yy - 1;
            nextYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 1;
            Log.d("Log", "**--> PrevYear: " + prevYear + " PrevMonth:"
                    + prevMonth + " NextMonth: " + nextMonth
                    + " NextYear: " + nextYear);
        } else {
            prevMonth = currentMonth - 1;
            nextMonth = currentMonth + 1;
            nextYear = yy;
            prevYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            Log.d("Log", "***---> PrevYear: " + prevYear + " PrevMonth:"
                    + prevMonth + " NextMonth: " + nextMonth
                    + " NextYear: " + nextYear);
        }

        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        trailingSpaces = currentWeekDay;

        Log.d("Log", "Week Day:" + currentWeekDay + " is "
                + getWeekDayAsString(currentWeekDay));
        Log.d("Log", "No. Trailing space to Add: " + trailingSpaces);
        Log.d("Log", "No. of Days in Previous Month: " + daysInPrevMonth);

        if (cal.isLeapYear(cal.get(Calendar.YEAR)))
            if (mm == 2)
                ++daysInMonth;
            else if (mm == 3)
                ++daysInPrevMonth;

        // Trailing Month days
        for (int i = 0; i < trailingSpaces; i++) {
            Log.d("Log",
                    "PREV MONTH:= "
                            + prevMonth
                            + " => "
                            + getMonthAsString(prevMonth)
                            + " "
                            + String.valueOf((daysInPrevMonth
                            - trailingSpaces + DAY_OFFSET)
                            + i));
            /*list.add(String
                    .valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
                            + i)
                    + "-"
                    + getMonthAsString(prevMonth)
                    + "-"
                    + prevYear);*/
            list.add(new FullDate(String
                    .valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
                            + i), getMonthAsString(prevMonth), String.valueOf(prevYear)));
        }

        // Current Month Days
        for (int i = 1; i <= daysInMonth; i++) {
            Log.d(currentMonthName, String.valueOf(i) + " "
                    + getMonthAsString(currentMonth) + " " + yy);
            if (i == getCurrentDayOfMonth()) {
                /*list.add(String.valueOf(i) + "-"
                        + getMonthAsString(currentMonth) + "-" + yy);*/
                list.add(new FullDate(String.valueOf(i), getMonthAsString(currentMonth), String.valueOf(yy)));
            } else {
                /*list.add(String.valueOf(i) + "-"
                        + getMonthAsString(currentMonth) + "-" + yy);*/
                list.add(new FullDate(String.valueOf(i), getMonthAsString(currentMonth), String.valueOf(yy)));
            }
        }

        // Leading Month days
        for (int i = 0; i < list.size() % 7; i++) {
            Log.d("Log", "NEXT MONTH:= " + getMonthAsString(nextMonth));
            /*list.add(String.valueOf(i + 1) + "-"
                    + getMonthAsString(nextMonth) + "-" + nextYear);*/
            list.add(new FullDate(String.valueOf(i + 1), getMonthAsString(nextMonth), String.valueOf(nextYear)));
        }
    }

    private class FullDate {
        String date;
        String month;

        public FullDate() {
        }

        public String getSelected() {
            return selected;
        }

        public void setSelected(String selected) {
            this.selected = selected;
        }

        String selected;

        public FullDate(String date, String month, String year) {
            this.date = date;
            this.month = month;
            this.year = year;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        String year;
    }

    private String GetMonthInt(String month) {
        switch (month) {
            case "January":
                return "01";
            case "February":
                return "02";
            case "March":
                return "03";
            case "April":
                return "04";
            case "May":
                return "05";
            case "June":
                return "06";
            case "July":
                return "07";
            case "August":
                return "08";
            case "September":
                return "09";
            case "October":
                return "10";
            case "November":
                return "11";
            case "December":
                return "12";
            default:
                return "00";
        }
    }

    private int GetMonthInteger(String month) {
        switch (month) {
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
            default:
                return 0;
        }
    }

    private void OpenPopup(View view) {
        //does not remove try catch.
        try {
            String[] time = String.valueOf(view.getTag()).split("  ");

            Tooltip.make(context,
                    new Tooltip.Builder(101)
                            .anchor(view, Tooltip.Gravity.BOTTOM)
                            .closePolicy(new Tooltip.ClosePolicy()
                                    .insidePolicy(true, false)
                                    .outsidePolicy(true, false), 3000)
                            .activateDelay(800)
                            .showDelay(300)
                            .text("In Time : " + time[0] + "\n" + "\n Out Time : " + time[1])
                            .maxWidth(800)
                            .withArrow(true)
                            .withOverlay(true)
                            .withStyleId(R.style.tooltipStyle)
                            .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                            .build()
            ).show();

        } catch (Exception ex) {
        }
    }
}
