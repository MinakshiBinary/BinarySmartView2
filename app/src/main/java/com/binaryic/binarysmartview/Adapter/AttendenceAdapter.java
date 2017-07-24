package com.binaryic.binarysmartview.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binaryic.binarysmartview.Models.AttendenceModel;
import com.binaryic.binarysmartview.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 4/4/2016.
 */
public class AttendenceAdapter extends RecyclerView.Adapter<AttendenceAdapter.AttendenceHolder> {
    Activity context;
    ArrayList<AttendenceModel> array_Data;

    public AttendenceAdapter(Activity context, ArrayList<AttendenceModel> array_Data) {
        this.context = context;
        this.array_Data = array_Data;
    }

    @Override
    public AttendenceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.attendance_list_view, parent, false);
        AttendenceHolder holder = new AttendenceHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AttendenceHolder holder, int position) {
        holder.tv_Date.setText(array_Data.get(position).getDate());
        if (array_Data.get(position).getPresentStatus().matches("present")) {
            if (array_Data.get(position).getMarkStatus().matches("lateMark")) {
                holder.tv_In_Time.setBackground(context.getResources().getDrawable(R.drawable.my_border_red_gray_border));
            } else if (array_Data.get(position).getMarkStatus().matches("earlyGo")) {
                holder.tv_Out_Time.setBackground(context.getResources().getDrawable(R.drawable.my_border_red_gray_border));
            }
            String[] in_Time_Array = array_Data.get(position).getInTime().split(":");
            String[] in_Out_Array = array_Data.get(position).getOutTime().split(":");
            holder.tv_In_Time.setText(in_Time_Array[0] + ":" + in_Time_Array[1]);
            holder.tv_Out_Time.setText(in_Out_Array[0] + ":" + in_Out_Array[1]);
        } else {
            holder.tv_Out_Time.setText("A");
            holder.tv_In_Time.setText("A");
        }
        holder.tv_In_Time.setText(array_Data.get(position).getInTime());
        holder.tv_Out_Time.setText(array_Data.get(position).getOutTime());
    }

    @Override
    public int getItemCount() {
        return array_Data.size();
    }

    public class AttendenceHolder extends RecyclerView.ViewHolder {
        TextView tv_Date;
        TextView tv_In_Time;
        TextView tv_Out_Time;
        public AttendenceHolder(View itemView) {
            super(itemView);
             tv_Date = (TextView) itemView.findViewById(R.id.tv_Date);
             tv_In_Time = (TextView) itemView.findViewById(R.id.tv_In_Time);
             tv_Out_Time = (TextView) itemView.findViewById(R.id.tv_Out_Time);
        }
    }
}
