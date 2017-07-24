package com.binaryic.binarysmartview.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.binaryic.binarysmartview.Models.BirthdayModel;
import com.binaryic.binarysmartview.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by User on 13-05-2016.
 */
public class BirthDayTempAdapter extends RecyclerView.Adapter<BirthDayTempAdapter.ViewHolder> {
    Activity context;
    List<BirthdayModel> list;
    DisplayMetrics dm;
    public BirthDayTempAdapter(Activity context,List<BirthdayModel> list){
        this.list = list;
        this.context = context;
        dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.birthday_temp_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (list.get(position).getName().contains(" ")) {
            String[] name_Array = list.get(position).getName().split(" ");
            if (name_Array.length == 4) {
                holder.lblName.setText(name_Array[0] + " " + name_Array[3]);
            } else if (name_Array.length == 3) {
                holder.lblName.setText(name_Array[0] + " " + name_Array[2]);
            } else if (name_Array.length <= 2) {
                holder.lblName.setText(list.get(position).getName());
            }
        } else {
            holder.lblName.setText(list.get(position).getName());
        }
        holder.lblDate.setText(list.get(position).getDate_Of_Birth());
        Picasso.with(context).load(list.get(position).getUser_Image()).into(holder.image);

        TableRow.LayoutParams params = new TableRow.LayoutParams(dm.widthPixels/3,TableRow.LayoutParams.WRAP_CONTENT); // (width, height)
        holder.view.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView lblName,lblDate;
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            image = (CircleImageView) itemView.findViewById(R.id.image);
            lblName = (TextView) itemView.findViewById(R.id.lblName);
            lblDate = (TextView) itemView.findViewById(R.id.lblDate);
        }
    }

}
