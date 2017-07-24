package com.binaryic.binarysmartview.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binaryic.binarysmartview.Common.InternetConnectionDetector;
import com.binaryic.binarysmartview.Common.MyApplication;
import com.binaryic.binarysmartview.Common.TextAwesome;
import com.binaryic.binarysmartview.Models.AttendenceModel;
import com.binaryic.binarysmartview.R;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PASSWORD;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_USER_INFO;
import static com.binaryic.binarysmartview.Common.Constants.URL_CHANGE_IN_AND_OUT_TIME;

/**
 * Created by User on 11-05-2016.
 */
public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> implements TimePickerDialog.OnTimeSetListener {
    List<AttendenceModel> list;
    private Dialog dialog = null;
    private TextView tv_ChangeDate;
    private TextView tv_Change_In_Time;
    private TextView tv_Change_Out_Time;
    private LinearLayout ll_Change_In_Time;
    private LinearLayout ll_Change_Out_Time;
    private String entry_Time, in_Time, out_Time, dateShow, changeDate;
    Activity context;

    public DayAdapter(Activity context, List<AttendenceModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String[] date = list.get(position).getDate().split("-");
        dateShow = date[2] + " " + GetMonthStr(date[1]) + " " + date[0];
        holder.lblDate.setText(dateShow);
        Log.e("Time", list.get(position).getInTime() + " " + list.get(position).getOutTime());

        if (list.get(position).getPresentStatus().equals("absent")) {
            holder.lblStatus.setText("A");
            holder.lblStatus.setBackground(context.getResources().getDrawable(R.drawable.red_circle));
            holder.lblInTime.setText("   A   ");
            holder.lblOutTime.setText("   A   ");
        } else {
            if (list.get(position).getInTime().equals(""))
                holder.lblInTime.setText(" NA ");
            else
                holder.lblInTime.setText(MyApplication.GetHour(list.get(position).getInTime()));
            if (list.get(position).getOutTime().equals(""))
                holder.lblOutTime.setText(" NA ");
            else
                holder.lblOutTime.setText(MyApplication.GetHour(list.get(position).getOutTime()));
            if (list.get(position).getMarkStatus().equals("lateMark")) {
                holder.lblStatus.setText("L");
                holder.lblStatus.setBackground(context.getResources().getDrawable(R.drawable.blue_circle));
            } else if (list.get(position).getMarkStatus().equals("earlyGo")) {
                holder.lblStatus.setText("E");
                holder.lblStatus.setBackground(context.getResources().getDrawable(R.drawable.blue_circle));
            } else {
                holder.lblStatus.setText("P");
                holder.lblStatus.setBackground(context.getResources().getDrawable(R.drawable.green_circle));
            }
        }
        holder.ta_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inAndOutTimeDialog(v, position, list.get(position).getInTime(), list.get(position).getOutTime());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        if (entry_Time.matches("inTime")) {
            in_Time = hourOfDay + ":" + minute;
            tv_Change_In_Time.setText(MyApplication.GetHour(in_Time));

        } else if (entry_Time.matches("outTime")) {
            out_Time = hourOfDay + ":" + minute;
            tv_Change_Out_Time.setText(MyApplication.GetHour(out_Time));
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblStatus, lblDate, lblInTime, lblOutTime;
        TextAwesome ta_Edit;

        public ViewHolder(View itemView) {
            super(itemView);
            ta_Edit = (TextAwesome) itemView.findViewById(R.id.ta_Edit);
            lblStatus = (TextView) itemView.findViewById(R.id.lblStatus);
            lblDate = (TextView) itemView.findViewById(R.id.lblDate);
            lblInTime = (TextView) itemView.findViewById(R.id.lblInTime);
            lblOutTime = (TextView) itemView.findViewById(R.id.lblOutTime);
        }
    }

    private void inAndOutTimeDialog(View v, int position, String inTime, String outTime) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_change_in_out_time);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv_ChangeDate = (TextView) dialog.findViewById(R.id.tv_ChangeDate);
        tv_Change_In_Time = (TextView) dialog.findViewById(R.id.tv_Change_In_Time);
        tv_Change_Out_Time = (TextView) dialog.findViewById(R.id.tv_Change_Out_Time);
        ll_Change_In_Time = (LinearLayout) dialog.findViewById(R.id.ll_Change_In_Time);
        ll_Change_Out_Time = (LinearLayout) dialog.findViewById(R.id.ll_Change_Out_Time);
        if (list.get(position).getPresentStatus().equals("absent")) {
            tv_Change_In_Time.setText("   A   ");
            in_Time = "A";
            tv_Change_Out_Time.setText("   A   ");
            out_Time = "A";
        } else {
            if (list.get(position).getInTime().equals("")) {
                tv_Change_In_Time.setText(" NA ");
                in_Time = "NA";
            } else {
                tv_Change_In_Time.setText(MyApplication.GetHour(inTime));
                in_Time = MyApplication.GetHour(inTime);

            }
            if (list.get(position).getOutTime().equals("")) {
                tv_Change_Out_Time.setText(" NA ");
                out_Time = "NA";

            } else {
                tv_Change_Out_Time.setText(MyApplication.GetHour(outTime));
                out_Time = MyApplication.GetHour(outTime);
            }
        }
        String[] date = list.get(position).getDate().split("-");
        dateShow = date[2] + " " + GetMonthStr(date[1]) + " " + date[0];
        tv_ChangeDate.setText(dateShow);
        changeDate = dateShow;
        dialog.show();
        ll_Change_In_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry_Time = "inTime";
                Calendar now = Calendar.getInstance();
                TimePickerDialog dpd = TimePickerDialog.newInstance(
                        DayAdapter.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND), false
                );
                dpd.show(context.getFragmentManager(), "In");
            }
        });
        ll_Change_Out_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry_Time = "outTime";
                Calendar now = Calendar.getInstance();
                TimePickerDialog dpd = TimePickerDialog.newInstance(
                        DayAdapter.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND), false
                );
                dpd.show(context.getFragmentManager(), "Out");
            }
        });

        Button bt_DialogSubmit = (Button) dialog.findViewById(R.id.bt_DialogSubmit);
        Button bt_DialogCancel = (Button) dialog.findViewById(R.id.bt_DialogCancel);
        bt_DialogSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.dismiss();
                    OpenAskPass(in_Time, out_Time);

                } catch (Exception ex) {
                    Log.e("MainActivity", "eror" + ex.getMessage());
                }
            }
        });
        bt_DialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void OpenAskPass(final String in_Time, final String out_Time) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.salary_password);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final EditText et_pass = (EditText) dialog.findViewById(R.id.et_frgt_dialog_email);
            final TextInputLayout TextInputDiaEmail = (TextInputLayout) dialog.findViewById(R.id.TextInputDiaEmail);
            TextView btn_submit = (TextView) dialog.findViewById(R.id.btn_frgt_dia_submit);
            TextView btn_cancel = (TextView) dialog.findViewById(R.id.btn_frgt_dia_cancel);
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (et_pass.getText().toString().isEmpty()) {
                            TextInputDiaEmail.setError("Enter Password");
                        } else {
                            Cursor cursor = context.getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
                            if (cursor.getCount() > 0) {
                                cursor.moveToLast();
                                String pass = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
                                if (pass.equals(et_pass.getText().toString().trim())) {
                                    InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(et_pass.getWindowToken(), 0);
                                    dialog.dismiss();
                                    if (InternetConnectionDetector.isConnectingToInternet(context, false)) {
                                        changeInAndOutTimeApiCall(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)), in_Time, out_Time, changeDate);
                                    } else
                                        Toast.makeText(context, "No Internet..", Toast.LENGTH_SHORT).show();
                                } else {
                                    TextInputDiaEmail.setError("Wrong Password");
                                    et_pass.setText("");
                                }
                            }
                            cursor.close();
                        }
                    } catch (Exception ex) {
                    }
                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        } catch (Exception e) {
        }
    }

    private void changeInAndOutTimeApiCall(final String emailID, final String inTime, final String outTime, final String changeDate) {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_CHANGE_IN_AND_OUT_TIME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response_String) {
                Log.e("Main activity", " Responce " + response_String);

                JSONObject response = null;
                try {
                    response = new JSONObject(response_String);

                    if (response.getString("success").equalsIgnoreCase("1")) {
                        Toast.makeText(context, response.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Main Activity", " error " + e.getMessage());
                }
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                Log.e("Main Activity", " Error " + error.getMessage());
                if (response != null) {
                    if (response.statusCode == 400) {

                    } else {

                    }
                } else {

                }
                pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("emailID", emailID);
                params.put("inTime", inTime);
                params.put("outTime", outTime);
                params.put("changeDate", changeDate);
                Log.e("MainActivity", "parameters" + params.toString());
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(strReq, "attendance");
    }

    private String GetMonthStr(String month) {
        switch (month) {
            case "01":
                return "Jan";
            case "02":
                return "Feb";
            case "03":
                return "Mar";
            case "04":
                return "Apr";
            case "05":
                return "May";
            case "06":
                return "Jun";
            case "07":
                return "Jul";
            case "08":
                return "Aug";
            case "09":
                return "Sep";
            case "10":
                return "Oct";
            case "11":
                return "Nov";
            case "12":
                return "Dec";
            default:
                return "00";
        }
    }
}
