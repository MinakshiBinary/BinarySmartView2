package com.binaryic.binarysmartview.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binaryic.binarysmartview.Models.BirthdayModel;
import com.binaryic.binarysmartview.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 4/6/2016.
 */
public class BirthdayAdapter extends RecyclerView.Adapter<BirthdayAdapter.BirthdayHolder> {
    Activity context;
    ArrayList<BirthdayModel> array_Data;

    public BirthdayAdapter(Activity context, ArrayList<BirthdayModel> array_Data) {
        this.context = context;
        this.array_Data = array_Data;
    }

    @Override
    public BirthdayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.birthday_list_view, parent, false);
        BirthdayHolder holder = new BirthdayHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BirthdayHolder holder, int position) {

        if (array_Data.get(position).getName().contains(" ")) {
            String[] name_Array = array_Data.get(position).getName().split(" ");
            if (name_Array.length == 4) {
                holder.tv_Name.setText(name_Array[0] + " " + name_Array[3]);
            } else if (name_Array.length == 3) {
                holder.tv_Name.setText(name_Array[0] + " " + name_Array[2]);
            } else if (name_Array.length <= 2) {
                holder.tv_Name.setText(array_Data.get(position).getName());
            }
        } else {
            holder.tv_Name.setText(array_Data.get(position).getName());
        }
        holder.tv_Date.setTextColor(context.getResources().getColor(R.color.birthday_green));
        holder.tv_Date.setText(array_Data.get(position).getDate_Of_Birth());
        Picasso.with(context).load(array_Data.get(position).getUser_Image()).into(holder.civ_Profile);
    }

    @Override
    public int getItemCount() {
        return array_Data.size();
    }

    public class BirthdayHolder extends RecyclerView.ViewHolder {
        private CircleImageView civ_Profile;
        private TextView tv_Name;
        private TextView tv_Date;

        public BirthdayHolder(View itemView) {
            super(itemView);
            civ_Profile = (CircleImageView) itemView.findViewById(R.id.civ_Profile);
            tv_Name = (TextView) itemView.findViewById(R.id.tv_Name);
            tv_Date = (TextView) itemView.findViewById(R.id.tv_Date);
        }
    }
}
