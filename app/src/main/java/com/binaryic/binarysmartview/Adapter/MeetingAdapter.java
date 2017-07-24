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

import com.binaryic.binarysmartview.Controller.LeaveRequestController;
import com.binaryic.binarysmartview.Models.MeetingApplicationModel;
import com.binaryic.binarysmartview.R;

import java.util.ArrayList;

/**
 * Created by admin on 10/18/2016.
 */
public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingHolder> {
    int itemCount;
    Activity context;
    ArrayList<MeetingApplicationModel> array_Data;

    public MeetingAdapter(Activity context, ArrayList<MeetingApplicationModel> array_Data, int itemCount) {
        this.context = context;
        this.array_Data = array_Data;
        this.itemCount = itemCount;
    }

    @Override
    public MeetingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_application_list, parent, false);
        MeetingHolder holder = new MeetingHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MeetingHolder holder, final int position) {
        Log.e("MeetingAdapter","position ="+position);
        holder.tv_Name.setText(array_Data.get(position).getName());
        holder.tv_Title.setText(array_Data.get(position).getTitle());
        holder.tv_FromDate.setText(array_Data.get(position).getFrom_Time());
        holder.tv_ToDate.setText(array_Data.get(position).getTo_Time());
        holder.tv_ApplicationDate.setText(array_Data.get(position).getMeeting_Date());
        Log.e("ChangeShiftAdapter", "status =" + array_Data.get(position).getAcceptance_status());

        if (array_Data.get(position).getAcceptance_status()==2) {
            holder.ll_Accept.setVisibility(View.VISIBLE);
            holder.ll_Status.setVisibility(View.GONE);
        } else {
            holder.ll_Accept.setVisibility(View.GONE);
            holder.ll_Status.setVisibility(View.VISIBLE);
            if (array_Data.get(position).getAcceptance_status()==0) {//0=accepted,1=rejected,2=yet not selected
                holder.tv_Accept.setVisibility(View.VISIBLE);
                holder.tv_Reject.setVisibility(View.GONE);
            } else {
                holder.tv_Accept.setVisibility(View.GONE);
                holder.tv_Reject.setVisibility(View.VISIBLE);
            }
        }
        holder.rl_LeaveRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeaveRequestController.openMeetingApplicationDialog(context, array_Data.get(position).getApplicationId(), array_Data.get(position).getName(), array_Data.get(position).getMeeting_Date(), array_Data.get(position).getFrom_Time(), array_Data.get(position).getTo_Time(), array_Data.get(position).getTitle(), (array_Data.get(position).getAcceptance_status()));

            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("MeetingAdapter","itemCount ="+itemCount);

        if (itemCount == 0) {
            return array_Data.size();
        } else return itemCount;
    }

    class MeetingHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_LeaveRequest;
        private TextView tv_Name;
        private TextView tv_Title;
        private TextView tv_FromDate;
        private Button bt_Accept;
        private Button bt_Reject;
        private TextView tv_Reject;
        private TextView tv_Accept;
        private TextView tv_ToDate;
        private TextView tv_ApplicationDate;
        private LinearLayout ll_Accept;
        private LinearLayout ll_Status;

        public MeetingHolder(View itemView) {
            super(itemView);
            rl_LeaveRequest = (RelativeLayout) itemView.findViewById(R.id.rl_LeaveRequest);
            tv_Name = (TextView) itemView.findViewById(R.id.tv_Name);
            tv_Title = (TextView) itemView.findViewById(R.id.tv_Title);
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
