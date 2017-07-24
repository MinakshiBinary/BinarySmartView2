package com.binaryic.binarysmartview.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.binaryic.binarysmartview.Activity.ShowAnnouncementActivity;
import com.binaryic.binarysmartview.Common.CommonFunction;
import com.binaryic.binarysmartview.Models.NotificationModel;
import com.binaryic.binarysmartview.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Administrator on 4/6/2016.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {
    Activity context;
    ArrayList<NotificationModel> array_Data;

    public NotificationAdapter(Activity context, ArrayList<NotificationModel> array_Data) {
        this.context = context;
        this.array_Data = array_Data;
    }

    @Override
    public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notificaiton_list_view, parent, false);
        NotificationHolder holder = new NotificationHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NotificationHolder holder, final int position) {
        holder.tv_Name.setText(array_Data.get(position).getName());
        Log.e("rqweadfasd", "==" + array_Data.get(position).getMessage());
        //  holder.web_View.loadDataWithBaseURL(array_Data.get(position).getMessage().trim(), "text/html; charset=utf-8", "utf-8");
        //holder.web_View.loadDataWithBaseURL(null, "<html><body><head><link href='https://fonts.googleapis.com/css?family=" + "sans-serif" + "' rel='stylesheet' type='text/css'><style>div {text-align: justify;text-justify: inter-word;color:#7f7f7f;font-family: '" + "sans-serif" + "', sans-serif;font-size:13px;margin:0 0 5px;padding:0;}</style></head><div>" + array_Data.get(position).getMessage().trim() + "</div></body></html>", "text/html", "utf-8", null);


        holder.rl_Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowAnnouncementActivity.class);
                intent.putExtra("UserName", array_Data.get(position).getUserName());
                intent.putExtra("UserDesignation", array_Data.get(position).getDesignation());
                intent.putExtra("NoticeTitle", array_Data.get(position).getName());
                intent.putExtra("NoticeMessage", array_Data.get(position).getMessage());
                intent.putExtra("NoticeDate", array_Data.get(position).getDate());
                context.startActivity(intent);
                /*Log.e("rl_Notification","=="+(holder.web_View.getVisibility() == View.VISIBLE));
                if (holder.web_View.getVisibility() == View.VISIBLE)
                    holder.web_View.setVisibility(View.GONE);
                else
                    holder.web_View.setVisibility(View.VISIBLE);*/
            }
        });

        holder.tv_Date.setTextColor(context.getResources().getColor(R.color.monthly_pink));
        holder.tv_Date.setText(CommonFunction.getDate(array_Data.get(position).getDate()));
        //Picasso.with(context).load(array_Data.get(position).getImage()).into(holder.civ_Profile);
    }

    @Override
    public int getItemCount() {
        return array_Data.size();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        private CircleImageView civ_Profile;
        private TextView tv_Name;
        private TextView tv_Date;
        private RelativeLayout rl_Notification;
        private WebView web_View;

        public NotificationHolder(View itemView) {
            super(itemView);
            civ_Profile = (CircleImageView) itemView.findViewById(R.id.civ_Profile);
            tv_Name = (TextView) itemView.findViewById(R.id.tv_Name);
            tv_Date = (TextView) itemView.findViewById(R.id.tv_Date);
            rl_Notification = (RelativeLayout) itemView.findViewById(R.id.rl_Notification);
            web_View = (WebView) itemView.findViewById(R.id.web_View);
        }
    }
}
