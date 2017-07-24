package com.binaryic.binarysmartview.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryic.binarysmartview.Adapter.MeetingAdapter;
import com.binaryic.binarysmartview.Adapter.RequestChangeShiftAdapter;
import com.binaryic.binarysmartview.Adapter.RequestLeaveAdapter;
import com.binaryic.binarysmartview.Common.TextAwesome;
import com.binaryic.binarysmartview.R;


public class AdminDetailsActivity extends AppCompatActivity {
    private LinearLayout ll_Title;
    private TextView tv_Name;
    private TextView tv_Designation;
    private TextView tv_Title;
    private Toolbar toolbar;
    private RecyclerView rv_AdminDetails;
    private TextAwesome ta_icon;

    private void init() {
        ll_Title = (LinearLayout) findViewById(R.id.ll_Title);
        tv_Designation = (TextView) findViewById(R.id.tv_Designation);
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        tv_Title = (TextView) findViewById(R.id.tv_Title);
        ta_icon = (TextAwesome) findViewById(R.id.ta_icon);
        rv_AdminDetails = (RecyclerView) findViewById(R.id.rv_AdminDetails);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_details);
        init();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Meeing Application");
        }
        if (getIntent().hasExtra("UserName")) {
            tv_Name.setText(getIntent().getStringExtra("UserName"));
            tv_Designation.setText(getIntent().getStringExtra("UserDesignation"));
        }

        rv_AdminDetails.hasFixedSize();
        rv_AdminDetails.setLayoutManager(new LinearLayoutManager(AdminDetailsActivity.this));
        if (getIntent().hasExtra("data_from")) {
            switch (getIntent().getStringExtra("data_from")) {
                case "leave":
                    Log.e("AdminDetailsActivity", "leaveModelArrayList" + AdminActivity.leaveModelArrayList.size());
                    rv_AdminDetails.setAdapter(new RequestLeaveAdapter(AdminDetailsActivity.this, AdminActivity.leaveModelArrayList, 0));
                    ta_icon.setText(R.string.fa_file_text_o);
                    tv_Title.setText("Leave Applications");
                    ll_Title.setBackgroundColor(getResources().getColor(R.color.leave_color));
                    break;
                case "shift":
                    rv_AdminDetails.setAdapter(new RequestChangeShiftAdapter(AdminDetailsActivity.this, AdminActivity.shiftModelArrayList,0));
                    ta_icon.setText(R.string.fa_calendar);
                    tv_Title.setText("Shift Change Applications");
                    ll_Title.setBackgroundColor(getResources().getColor(R.color.daily_attendance));

                    break;
                case "meeting":
                    Log.e("AdminDetailsActivity", "meetingApplicationArrayList" + AdminActivity.meetingApplicationArrayList.size());
                    rv_AdminDetails.setAdapter(new MeetingAdapter(AdminDetailsActivity.this, AdminActivity.meetingApplicationArrayList,0));
                    ta_icon.setText(R.string.fa_users);
                    tv_Title.setText("Meeting Applications");
                    ll_Title.setBackgroundColor(getResources().getColor(R.color.monthly_pink));

                    break;
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
