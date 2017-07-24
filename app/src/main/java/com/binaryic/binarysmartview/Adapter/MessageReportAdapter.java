package com.binaryic.binarysmartview.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binaryic.binarysmartview.Common.TextAwesome;
import com.binaryic.binarysmartview.Models.MessageReportModel;
import com.binaryic.binarysmartview.R;

import java.util.ArrayList;

/**
 * Created by admin on 11/5/2016.
 */
public class MessageReportAdapter extends RecyclerView.Adapter<MessageReportAdapter.ReportHolder> {
    Activity context;
    ArrayList<MessageReportModel> arrayData;

    public MessageReportAdapter(Activity context, ArrayList<MessageReportModel> arrayData) {
        this.context = context;
        this.arrayData = arrayData;
    }

    @Override
    public ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_report_list_view, parent, false);
        ReportHolder reportHolder = new ReportHolder(view);
        return reportHolder;
    }

    @Override
    public void onBindViewHolder(ReportHolder holder, int position) {
        holder.tv_Name.setText(arrayData.get(position).getReceiver());
        holder.tv_Date.setText(arrayData.get(position).getDate());
        holder.tv_Title.setText(arrayData.get(position).getTitle());
        if (arrayData.get(position).getMsg_status().matches("wait")) {
            holder.ta_Wait.setVisibility(View.VISIBLE);
            holder.ta_Send.setVisibility(View.GONE);
        } else if (arrayData.get(position).getMsg_status().matches("send")) {
            holder.ta_Wait.setVisibility(View.GONE);
            holder.ta_Send.setVisibility(View.VISIBLE);
            holder.ta_Send.setTextColor(context.getResources().getColor(R.color.caldroid_middle_gray));
        } else {
            holder.ta_Wait.setVisibility(View.GONE);
            holder.ta_Send.setVisibility(View.VISIBLE);
            holder.ta_Send.setTextColor(context.getResources().getColor(R.color.Total_green));
        }

    }

    @Override
    public int getItemCount() {
        return arrayData.size();
    }

    public class ReportHolder extends RecyclerView.ViewHolder {
        TextView tv_Name;
        TextAwesome ta_Wait;
        TextView tv_Date;
        TextView tv_Title;
        TextAwesome ta_Send;

        public ReportHolder(View itemView) {
            super(itemView);
            ta_Wait = (TextAwesome) itemView.findViewById(R.id.ta_Wait);
            tv_Name = (TextView) itemView.findViewById(R.id.tv_Name);
            tv_Date = (TextView) itemView.findViewById(R.id.tv_Date);
            tv_Title = (TextView) itemView.findViewById(R.id.tv_Title);
            ta_Send = (TextAwesome) itemView.findViewById(R.id.ta_Send);
        }
    }
}