package com.binaryic.binarysmartview.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binaryic.binarysmartview.Models.ReportModel;
import com.binaryic.binarysmartview.R;

import java.util.ArrayList;

/**
 * Created by admin on 11/5/2016.
 */
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportHolder> {
    Activity context;
    ArrayList<ReportModel> arrayData;

    public ReportAdapter(Activity context, ArrayList<ReportModel> arrayData) {
        this.context = context;
        this.arrayData = arrayData;
    }

    @Override
    public ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_list_view, parent, false);
        ReportHolder reportHolder = new ReportHolder(view);
        return reportHolder;
    }

    @Override
    public void onBindViewHolder(ReportHolder holder, int position) {
      //  holder.tv_User_Name.setText(arrayData.get(position).getName());
        holder.tv_Date.setText(arrayData.get(position).getDate());
        holder.tv_Page_Name.setText(arrayData.get(position).getPageName());
        holder.tv_Time.setText(arrayData.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return arrayData.size();
    }

    public class ReportHolder extends RecyclerView.ViewHolder {
        TextView tv_User_Name;
        TextView tv_Page_Name;
        TextView tv_Date;
        TextView tv_Time;

        public ReportHolder(View itemView) {
            super(itemView);
           // tv_User_Name = (TextView) itemView.findViewById(R.id.tv_User_Name);
            tv_Page_Name = (TextView) itemView.findViewById(R.id.tv_Page_Name);
            tv_Date = (TextView) itemView.findViewById(R.id.tv_Date);
            tv_Time = (TextView) itemView.findViewById(R.id.tv_Time);
        }
    }
}
