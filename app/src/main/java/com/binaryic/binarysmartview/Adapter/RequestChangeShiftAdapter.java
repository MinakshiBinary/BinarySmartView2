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
import com.binaryic.binarysmartview.Models.RequestChangeShiftTimeModel;
import com.binaryic.binarysmartview.R;

import java.util.ArrayList;

/**
 * Created by admin on 10/18/2016.
 */
public class RequestChangeShiftAdapter extends RecyclerView.Adapter<RequestChangeShiftAdapter.ChangeShiftTimeHolder> {
    Activity context;
    int ItemCount;
    ArrayList<RequestChangeShiftTimeModel> array_Data;

    public RequestChangeShiftAdapter(Activity context, ArrayList<RequestChangeShiftTimeModel> array_Data, int ItemCount) {
        this.context = context;
        this.array_Data = array_Data;
        this.ItemCount = ItemCount;
    }

    @Override
    public ChangeShiftTimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.change_shift_time_list, parent, false);
        ChangeShiftTimeHolder holder = new ChangeShiftTimeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChangeShiftTimeHolder holder, final int position) {
        holder.tv_Name.setText(array_Data.get(position).getName());
        holder.tv_Out_Time.setText(array_Data.get(position).getNew_Out_Time());
        holder.tv_In_Time.setText(array_Data.get(position).getNew_In_Time());
        holder.tv_ApplicationDate.setText(array_Data.get(position).getRequest_On_Date());
        if (array_Data.get(position).getChange_Time_Status() == 2) {
            holder.ll_Accept.setVisibility(View.VISIBLE);
            holder.ll_Status.setVisibility(View.GONE);
        } else {
            holder.ll_Accept.setVisibility(View.GONE);
            holder.ll_Status.setVisibility(View.VISIBLE);
            if (array_Data.get(position).getChange_Time_Status() == 0) {
                holder.tv_Accept.setVisibility(View.VISIBLE);
                holder.tv_Reject.setVisibility(View.GONE);
            } else {
                holder.tv_Accept.setVisibility(View.GONE);
                holder.tv_Reject.setVisibility(View.VISIBLE);
            }
        }
        holder.rl_NotificationFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeaveRequestController.openShiftChangeRequestDialog(context, array_Data.get(position).getShiftChangeId(), array_Data.get(position).getName(), array_Data.get(position).getRequest_On_Date(), array_Data.get(position).getNew_In_Time(), array_Data.get(position).getNew_Out_Time(), (array_Data.get(position).getChange_Time_Status()));

            }
        });
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
    }

    @Override
    public int getItemCount() {
        if (ItemCount == 0) {
            return array_Data.size();
        } else
            return ItemCount;
    }

    class ChangeShiftTimeHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rl_NotificationFirst;
        private TextView tv_Name;
        private TextView tv_Out_Time;
        private Button bt_Accept;
        private Button bt_Reject;
        private TextView tv_Reject;
        private TextView tv_Accept;
        private TextView tv_In_Time;
        private TextView tv_ApplicationDate;
        private LinearLayout ll_Accept;
        private LinearLayout ll_Status;

        public ChangeShiftTimeHolder(View itemView) {
            super(itemView);
            rl_NotificationFirst = (RelativeLayout) itemView.findViewById(R.id.rl_NotificationFirst);
            tv_Name = (TextView) itemView.findViewById(R.id.tv_Name);
            tv_Out_Time = (TextView) itemView.findViewById(R.id.tv_Out_Time);
            tv_In_Time = (TextView) itemView.findViewById(R.id.tv_In_Time);
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
