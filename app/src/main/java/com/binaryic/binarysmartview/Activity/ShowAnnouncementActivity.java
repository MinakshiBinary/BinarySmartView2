package com.binaryic.binarysmartview.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.binaryic.binarysmartview.Common.CommonFunction;
import com.binaryic.binarysmartview.Controller.ReportingController;
import com.binaryic.binarysmartview.Controller.UserInfoController;
import com.binaryic.binarysmartview.R;

public class ShowAnnouncementActivity extends AppCompatActivity {
    private TextView tv_NoticeTitle;
    private TextView tv_NoticeDate;
    private TextView tv_Name;
    private TextView tv_Designation;
    private Toolbar toolbar;
    private WebView web_View;
    private String message;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_announcement);
        ReportingController.reportingApi(ShowAnnouncementActivity.this, "ShowAnnouncement Activity", UserInfoController.getUserEmail(ShowAnnouncementActivity.this), UserInfoController.getUserEcode(ShowAnnouncementActivity.this));

        tv_NoticeDate = (TextView) findViewById(R.id.tv_NoticeDate);
        tv_NoticeTitle = (TextView) findViewById(R.id.tv_NoticeTitle);
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        tv_Designation = (TextView) findViewById(R.id.tv_Designation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        web_View = (WebView) findViewById(R.id.web_View);
        if (getIntent().hasExtra("UserName")) {
            tv_Name.setText(getIntent().getStringExtra("UserName"));
            tv_Designation.setText(getIntent().getStringExtra("UserDesignation"));
            tv_NoticeTitle.setText(getIntent().getStringExtra("NoticeTitle"));
            tv_NoticeDate.setText(CommonFunction.getDate(getIntent().getStringExtra("NoticeDate")));
            message = getIntent().getStringExtra("NoticeMessage");
        }
        web_View.loadDataWithBaseURL(null, "<html><body><head><link href='https://fonts.googleapis.com/css?family=" + "sans-serif" + "' rel='stylesheet' type='text/css'><style>div {text-align: justify;text-justify: inter-word;color:#7f7f7f;font-family: '" + "sans-serif" + "', sans-serif;font-size:13px;margin:0 0 5px;padding:0;}</style></head><div>" + message.trim() + "</div></body></html>", "text/html", "utf-8", null);

    }
}
