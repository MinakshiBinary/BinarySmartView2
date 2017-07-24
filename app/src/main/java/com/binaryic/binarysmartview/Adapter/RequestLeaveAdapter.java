package com.binaryic.binarysmartview.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.binarysmartview.Controller.LeaveRequestController;
import com.binaryic.binarysmartview.Models.RequestLeaveModel;
import com.binaryic.binarysmartview.R;

import java.util.ArrayList;

/**
 * Created by admin on 10/17/2016.
 */
public class RequestLeaveAdapter extends RecyclerView.Adapter<RequestLeaveAdapter.LeaveHolder> {
    Activity context;
    ArrayList<RequestLeaveModel> array_Data;
    int itemCount;

    public RequestLeaveAdapter(Activity context, ArrayList<RequestLeaveModel> array_Data, int itemCount) {
        this.context = context;
        this.array_Data = array_Data;
        this.itemCount = itemCount;


    }

    @Override
    public LeaveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_leave_list, parent, false);
        LeaveHolder holder = new LeaveHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(LeaveHolder holder, final int position) {
        Log.e("RequestLeaveAdapter", "array_Data.size" + array_Data.size());
        holder.tv_Name.setText(array_Data.get(position).getName());
        holder.tv_FromDate.setText(array_Data.get(position).getFromDate());
        holder.tv_ToDate.setText(array_Data.get(position).getToDate());
        holder.tv_ApplicationDate.setText(array_Data.get(position).getLeaveApplyDate());
        holder.tv_Title.setText(array_Data.get(position).getLeaveTitle());
        if (array_Data.get(position).getLeaveStatus() == 2) {

            holder.ll_Accept.setVisibility(View.VISIBLE);
            holder.ll_Status.setVisibility(View.GONE);

        } else {

            holder.ll_Accept.setVisibility(View.GONE);
            holder.ll_Status.setVisibility(View.VISIBLE);


            if (array_Data.get(position).getLeaveStatus() == 1) {//0 accepted and 1 for rejected
                holder.tv_Accept.setVisibility(View.GONE);
                holder.tv_Reject.setVisibility(View.VISIBLE);
            } else {
                holder.tv_Accept.setVisibility(View.VISIBLE);
                holder.tv_Reject.setVisibility(View.GONE);

            }

        }

        holder.bt_Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Accept Button", Toast.LENGTH_SHORT).show();
            }
        });
        holder.bt_Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Reject Button", Toast.LENGTH_SHORT).show();
            }
        });
        holder.rl_LeaveRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeaveRequestController.openLeaveRequestDialog(context, array_Data.get(position).getLeaveId(), array_Data.get(position).getName(), array_Data.get(position).getLeaveApplyDate(), array_Data.get(position).getFromDate(), array_Data.get(position).getToDate(), array_Data.get(position).getLeaveTitle(), (array_Data.get(position).getLeaveStatus()));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (itemCount == 0)
            return array_Data.size();
        else
            return itemCount;
    }

    class LeaveHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rl_LeaveRequest;
        private TextView tv_Title;
        private TextView tv_Name;
        private TextView tv_FromDate;
        private Button bt_Accept;
        private Button bt_Reject;
        private TextView tv_Reject;
        private TextView tv_Accept;
        private TextView tv_ToDate;
        private TextView tv_ApplicationDate;
        private LinearLayout ll_Accept;
        private LinearLayout ll_Status;

        public LeaveHolder(View itemView) {
            super(itemView);
            rl_LeaveRequest = (RelativeLayout) itemView.findViewById(R.id.rl_LeaveRequest);
            tv_Title = (TextView) itemView.findViewById(R.id.tv_Title);
            tv_Name = (TextView) itemView.findViewById(R.id.tv_Name);
            tv_FromDate = (TextView) itemView.findViewById(R.id.tv_FromDate);
            tv_ToDate = (TextView) itemView.findViewById(R.id.tv_ToDate);
            tv_Reject = (TextView) itemView.findViewById(R.id.tv_Reject);
            tv_Accept = (TextView) itemView.findViewById(R.id.tv_Accept);
            tv_ApplicationDate = (TextView) itemView.findViewById(R.id.tv_ApplicationDate);
            bt_Accept = (Button) itemView.findViewById(R.id.bt_Accept);
            bt_Reject = (Button) itemView.findViewById(R.id.bt_Reject);
            ll_Accept = (LinearLayout) itemView.findViewById(R.id.ll_Accept);
            ll_Status = (LinearLayout) itemView.findViewById(R.id.ll_Status);
        }
    }


}
