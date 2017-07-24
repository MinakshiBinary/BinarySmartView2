package com.binaryic.binarysmartview.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.binaryic.binarysmartview.Common.CommonFunction;
import com.binaryic.binarysmartview.Controller.ReportingController;
import com.binaryic.binarysmartview.Controller.SendMessageController;
import com.binaryic.binarysmartview.Controller.UserInfoController;
import com.binaryic.binarysmartview.R;

import java.util.ArrayList;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DEPARTMENT_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_DEPARTMENT_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_ID;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_DEPARMENT_INFO;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_REGISTER_USER;


public class SendMessageActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener, OnItemSelectedListener {
    private LinearLayout ll_SendNotification;
    private EditText et_Message;
    private EditText et_Title;
    private Button bt_Send;
    private Toolbar toolbar;
    private String receiverType;
    private String receiverId;
    private ArrayList<String> single;
    private ArrayList<String> department;

    private RadioButton radio_Single;
    private RadioButton radio_Dipartment;
    private RadioButton radio_All;
    private AutoCompleteTextView actv_RadioResponce;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        ReportingController.reportingApi(SendMessageActivity.this, "SendMessage Activity", UserInfoController.getUserEmail(SendMessageActivity.this), UserInfoController.getUserEcode(SendMessageActivity.this));

        init();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Send Message");
        }


        actv_RadioResponce.setOnItemSelectedListener(this);
        actv_RadioResponce.setOnItemClickListener(this);
        radio_All.setOnClickListener(this);
        radio_Single.setOnClickListener(this);
        radio_Dipartment.setOnClickListener(this);
        bt_Send.setOnClickListener(this);
    }

    private void init() {
        ll_SendNotification = (LinearLayout) findViewById(R.id.ll_SendNotification);
        et_Title = (EditText) findViewById(R.id.et_Title);
        et_Message = (EditText) findViewById(R.id.et_Message);
        bt_Send = (Button) findViewById(R.id.bt_Send);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        radio_Single = (RadioButton) findViewById(R.id.radio_Single);
        radio_Dipartment = (RadioButton) findViewById(R.id.radio_Dipartment);
        radio_All = (RadioButton) findViewById(R.id.radio_All);
        actv_RadioResponce = (AutoCompleteTextView) findViewById(R.id.actv_RadioResponce);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_All:
                receiverType = "all";
                actv_RadioResponce.setVisibility(View.INVISIBLE);
                actv_RadioResponce.setText("all");
                break;
            case R.id.radio_Single:
                receiverType = "single";
                actv_RadioResponce.setVisibility(View.VISIBLE);
                actv_RadioResponce.setText("");
                Cursor cursor = getContentResolver().query(CONTENT_REGISTER_USER, null, null, null, null);
                Log.e("SendMessageActivity", "sigle cursor==" + cursor.getCount());
                single = new ArrayList<String>();

                if (cursor.getCount() > 0) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToNext();
                        single.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                    }
                }
                cursor.close();
                Log.e("SendMessageActivity", "single[]" + single.size());
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, single);
                actv_RadioResponce.setThreshold(0);
                actv_RadioResponce.setAdapter(adapter);

                break;
            case R.id.radio_Dipartment:
                receiverType = "department";
                actv_RadioResponce.setVisibility(View.VISIBLE);
                actv_RadioResponce.setText("");
                Cursor cursorDepartment = getContentResolver().query(CONTENT_DEPARMENT_INFO, null, null, null, null);
                Log.e("SendMessageActivity", "department cursor==" + cursorDepartment.getCount());
                department = new ArrayList<String>();

                if (cursorDepartment.getCount() > 0) {
                    for (int i = 0; i < cursorDepartment.getCount(); i++) {
                        cursorDepartment.moveToNext();
                        department.add(cursorDepartment.getString(cursorDepartment.getColumnIndex(COLUMN_DEPARTMENT_NAME)));
                    }
                }
                cursorDepartment.close();
                Log.e("SendMessageActivity", "department[]" + department.size());
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, department);
                actv_RadioResponce.setThreshold(1);
                actv_RadioResponce.setAdapter(adapter);
                break;
            case R.id.bt_Send:
                if (TextUtils.isEmpty(actv_RadioResponce.getText())) {
                    CommonFunction.createSnackbar(ll_SendNotification, "Please Select Receiver");
                } else if (TextUtils.isEmpty(et_Title.getText())) {
                    CommonFunction.createSnackbar(ll_SendNotification, "Please Enter Message Title");
                } else if (TextUtils.isEmpty(et_Message.getText())) {
                    CommonFunction.createSnackbar(ll_SendNotification, "Please Enter Message");
                } else {
                    Log.e("SendMessageActivity", "send Notification==" + receiverType + "," + getReceiverId() + "," + et_Title.getText().toString().trim() + "," + et_Message.getText().toString().trim());
                    SendMessageController.sendMessageApiCall(SendMessageActivity.this, ll_SendNotification, receiverType, receiverId, et_Title.getText().toString().trim(), et_Message.getText().toString().trim());
                    et_Title.setText("");
                    et_Message.setText("");
                    actv_RadioResponce.setText("");
                }
                break;
        }

    }

    private String getReceiverId() {
        String selection;
        Cursor cursor;
        switch (receiverType) {
            case "all":
                receiverId = "";
                break;
            case "single":
                selection = COLUMN_NAME + " ='" + actv_RadioResponce.getText() + "'";
                Log.e("SendMessageActivity", "selection==" + selection);
                cursor = getContentResolver().query(CONTENT_REGISTER_USER, null, selection, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                    receiverId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
                }
                break;
            case "department":
                selection = COLUMN_DEPARTMENT_NAME + " ='" + actv_RadioResponce.getText().toString().trim() + "'";
                Log.e("SendMessageActivity", "selection==" + selection);
                cursor = getContentResolver().query(CONTENT_DEPARMENT_INFO, null, selection, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                    receiverId = cursor.getString(cursor.getColumnIndex(COLUMN_DEPARTMENT_ID));
                }

                break;
        }
        return receiverId;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // TODO Auto-generated method stub
        //Log.d("AutocompleteContacts", "onItemSelected() position " + position);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
// TODO Auto-generated method stub

        // Show Alert
        Toast.makeText(getBaseContext(), "Position:" + position + " Month:" + parent.getItemAtPosition(position),
                Toast.LENGTH_LONG).show();

        Log.d("AutocompleteContacts", "Position:" + position + " Month:" + parent.getItemAtPosition(position));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
// TODO Auto-generated method stub

        InputMethodManager imm = (InputMethodManager) getSystemService(
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
