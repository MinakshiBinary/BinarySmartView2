package com.binaryic.binarysmartview.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binaryic.binarysmartview.Models.SalarySlipModel;
import com.binaryic.binarysmartview.R;

import java.util.List;

/**
 * Created by User on 18-05-2016.
 */
public class SalarySlipAdapter extends RecyclerView.Adapter<SalarySlipAdapter.ViewHolder> {
    private ClickListener clickListener;
    Context context;
    List<SalarySlipModel> list;
    public SalarySlipAdapter(Context context,List<SalarySlipModel> list){
        this.context = context;
        this.list = list;
    }
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.salary_slip_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.lblSalaryMonth.setText(list.get(position).getMmm());
        holder.lblSalaryYear.setText(list.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView lblSalaryMonth,lblSalaryYear;
        public ViewHolder(View itemView) {
            super(itemView);
            lblSalaryMonth = (TextView) itemView.findViewById(R.id.lblSalaryMonth);
            lblSalaryYear = (TextView) itemView.findViewById(R.id.lblSalaryYear);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.ItemClicked(v, getPosition());
            }
        }
    }
    public interface ClickListener {
        public void ItemClicked(View view, int position);
    }

}
