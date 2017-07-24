package com.binaryic.binarysmartview.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binaryic.binarysmartview.Adapter.DayAdapter;
import com.binaryic.binarysmartview.Models.AttendenceModel;
import com.binaryic.binarysmartview.R;
import com.binaryic.binarysmartview.Common.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11-05-2016.
 */
public class DayFragment extends Fragment {
    List<AttendenceModel> listAttendence = new ArrayList<>();
    RecyclerView dayList;
    DayAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_fragment, container, false);
        dayList = (RecyclerView) view.findViewById(R.id.dayList);
        setCalenderData();
        setupRecyclerView();
        return view;
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
            }
        }
    }
    private void setupRecyclerView() {
        try {
            dayList.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new DayAdapter(getActivity(), listAttendence);
            dayList.setAdapter(adapter);
        } catch (Exception ex) {
        }
    }
}
