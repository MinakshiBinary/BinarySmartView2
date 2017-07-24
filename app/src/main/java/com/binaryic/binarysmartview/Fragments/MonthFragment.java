package com.binaryic.binarysmartview.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binaryic.binarysmartview.Adapter.CalendarAdapter;
import com.binaryic.binarysmartview.Models.AttendenceModel;
import com.binaryic.binarysmartview.R;
import com.binaryic.binarysmartview.Common.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 10-05-2016.
 */
public class MonthFragment extends Fragment {
    TextView prevmonth, nextmonth;
    public TextView currentMonth;
    RecyclerView calanderlist;
    int month, year;
    DateFormat dateFormatter = new DateFormat();
    String dateTemplate = "MMMM yyyy";
    Date date = null;
    Calendar c;
    List<AttendenceModel> listAttendence = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.month_fragment, container, false);
        c = Calendar.getInstance();
        month = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);

        prevmonth = (TextView) view.findViewById(R.id.prevMonth);
        nextmonth = (TextView) view.findViewById(R.id.nextMonth);
        currentMonth = (TextView) view.findViewById(R.id.currentMonth);
        calanderlist = (RecyclerView) view.findViewById(R.id.calandarlist);
        calanderlist.setNestedScrollingEnabled(false);
        setCalenderData();
        SetPreButton();
        setupCalendar(month, year);
        prevmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (month <= 1) {
                    month = 12;
                    year--;
                } else {
                    month--;
                }
                setupCalendar(month, year);
                SetPreButton();

            }
        });
        nextmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (month > 11) {
                    month = 1;
                    year++;
                } else {
                    month++;
                }
                setupCalendar(month, year);
                SetPreButton();
            }
        });
        return view;
    }

    private void SetPreButton() {
        try {
            int m = month;
            int y = year;
            if (m <= 1) {
                m = 12;
                y--;
            } else {
                m--;
            }
            SimpleDateFormat formatterCheck = new SimpleDateFormat("yyyy-MM-dd");
            Date dateMin = formatterCheck.parse(listAttendence.get(listAttendence.size() - 1).getDate());
            Date dateCheck = formatterCheck.parse(y + "-" + (m < 10 ? "0" + m : m) + "-" + "28");
            if (dateMin.compareTo(dateCheck) > 0) {
                // prevmonth.setVisibility(View.GONE);
                prevmonth.setTextColor(getResources().getColor(R.color.caldroid_gray));
                prevmonth.setEnabled(false);
            } else {
                //   prevmonth.setVisibility(View.VISIBLE);
                prevmonth.setTextColor(getResources().getColor(R.color.white));
                prevmonth.setEnabled(true);
            }
            m = month;
            y = year;
            if (m > 11) {
                m = 1;
                y++;
            } else {
                m++;
            }
            try {
                Date dateMinNext = formatterCheck.parse(listAttendence.get(0).getDate());
                Date dateCheckNext = formatterCheck.parse(y + "-" + (m < 10 ? "0" + m : m) + "-" + "01");
                if (dateMinNext.compareTo(dateCheckNext) < 0) {
                    //nextmonth.setVisibility(View.GONE);
                    nextmonth.setTextColor(getResources().getColor(R.color.caldroid_gray));
                    nextmonth.setEnabled(false);
                } else {
                    //  nextmonth.setVisibility(View.VISIBLE);
                    nextmonth.setTextColor(getResources().getColor(R.color.white));
                    nextmonth.setEnabled(true);
                }
            } catch (Exception ex) {
            }
        } catch (Exception ex) {
        }
    }

    private void setupCalendar(int month, int year) {
        c.set(year, month - 1, c.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate, c.getTime()));
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 7);
        calanderlist.setLayoutManager(layoutManager);
        CalendarAdapter adapter = new CalendarAdapter(getActivity(), month, year, listAttendence);
        calanderlist.setAdapter(adapter);
    }


    private void setCalenderData() {
        listAttendence = new ArrayList<>();
        Cursor cursor_Calender = getActivity().getContentResolver().query(Constants.CONTENT_ATTENDECE_INFO, null, null, null, null);
        if (cursor_Calender.getCount() > 0) {
            for (int i = 0; i < cursor_Calender.getCount(); i++) {
                cursor_Calender.moveToNext();
                AttendenceModel model = new AttendenceModel();
                model.setDate(cursor_Calender.getString(cursor_Calender.getColumnIndex(Constants.COLUMN_DATE)));
                model.setDay(cursor_Calender.getString(cursor_Calender.getColumnIndex(Constants.COLUMN_DAY)));
                model.setInTime(cursor_Calender.getString(cursor_Calender.getColumnIndex(Constants.COLUMN_IN_TIME)));
                model.setMarkStatus(cursor_Calender.getString(cursor_Calender.getColumnIndex(Constants.COLUMN_MARK_STATUS)));
                model.setOriginalInTime(cursor_Calender.getString(cursor_Calender.getColumnIndex(Constants.COLUMN_ORIGINAL_INTIME)));
                model.setOriginalOutTime(cursor_Calender.getString(cursor_Calender.getColumnIndex(Constants.COLUMN_ORIGINAL_OUTTIME)));
                model.setOutTime(cursor_Calender.getString(cursor_Calender.getColumnIndex(Constants.COLUMN_OUT_TIME)));
                model.setPresentStatus(cursor_Calender.getString(cursor_Calender.getColumnIndex(Constants.COLUMN_PRESNET_STATUS)));
                model.setUserID(cursor_Calender.getString(cursor_Calender.getColumnIndex(Constants.COLUMN_USER_ID)));
                listAttendence.add(model);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = dateFormat.parse(cursor_Calender.getString(cursor_Calender.getColumnIndex(Constants.COLUMN_DATE)));
                } catch (ParseException e) {
                    Log.e("errrorrrr", "===" + e.getMessage());
                }
            }
        }
    }
}
