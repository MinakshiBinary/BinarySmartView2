package com.binaryic.binarysmartview.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binaryic.binarysmartview.Models.HolidaysModel;
import com.binaryic.binarysmartview.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 4/11/2016.
 */
public class HolidaysAdapter extends RecyclerView.Adapter<HolidaysAdapter.HolidaysHolder> {
    Activity context;
    ArrayList<HolidaysModel> array_Data;

    public HolidaysAdapter(Activity context, ArrayList<HolidaysModel> array_Data) {
        this.context = context;
        this.array_Data = array_Data;
    }


    @Override
    public HolidaysHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.holidays_list_view, parent, false);
        HolidaysHolder holder = new HolidaysHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(HolidaysHolder holder, int position) {
        holder.tv_Name.setText(array_Data.get(position).getHolidayName());

        holder.tv_Date.setText(parseDateToddMMyyyy(array_Data.get(position).getDate()));
        if (position != 0)
            Picasso.with(context).load("http://www.binaryic.com/binary_smartview_api/gallery/" + array_Data.get(position).getHolidayName().replace(" ", "") + ".png").placeholder(R.drawable.ic_launcher).into(holder.civ_Profile);
        Log.e("url", "http://www.binaryic.com/binary_smartview_api/gallery/" + array_Data.get(position).getHolidayName().replace(" ", "") + ".png");

    }

    @Override
    public int getItemCount() {
        return array_Data.size();
    }

    public class HolidaysHolder extends RecyclerView.ViewHolder {
        private CircleImageView civ_Profile;
        private TextView tv_Name;
        private TextView tv_Date;

        public HolidaysHolder(View itemView) {
            super(itemView);
            civ_Profile = (CircleImageView) itemView.findViewById(R.id.civ_Profile);
            tv_Name = (TextView) itemView.findViewById(R.id.tv_Name);
            tv_Date = (TextView) itemView.findViewById(R.id.tv_Date);
        }
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
}
