package com.binaryic.binarysmartview.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.binarysmartview.Adapter.SalarySlipAdapter;
import com.binaryic.binarysmartview.Common.FileDownloader;
import com.binaryic.binarysmartview.Common.MyApplication;
import com.binaryic.binarysmartview.Controller.ReportingController;
import com.binaryic.binarysmartview.Controller.UserInfoController;
import com.binaryic.binarysmartview.Models.SalarySlipModel;
import com.binaryic.binarysmartview.R;
import com.binaryic.binarysmartview.Common.Constants;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 18-05-2016.
 */
public class SalarySlipActivity extends AppCompatActivity implements SalarySlipAdapter.ClickListener {
    private RecyclerView rv_salarySlip;
    private TextView tv_Name;
    private TextView tv_Designation;
    KenBurnsView header_picture;
    List<SalarySlipModel> listSalary;
    String eid = "";
    int pos = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salaryslip);
        ReportingController.reportingApi(SalarySlipActivity.this, "Salary SlipActivity", UserInfoController.getUserEmail(SalarySlipActivity.this), UserInfoController.getUserEcode(SalarySlipActivity.this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        rv_salarySlip = (RecyclerView) findViewById(R.id.rv_salarySlip);
        header_picture = (KenBurnsView) findViewById(R.id.header_picture);
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        tv_Designation = (TextView) findViewById(R.id.tv_Designation);
        header_picture.setAlpha(100);
        header_picture.setImageBitmap(MyApplication.imageCover);
        GetExtra();
        setUpRecycleView();

    }

    private void GetExtra() {
        try {
            listSalary = new ArrayList<>();
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String json = extras.getString("list");
                eid = extras.getString("eid");
                tv_Name.setText(getIntent().getStringExtra("UserName"));
                tv_Designation.setText(getIntent().getStringExtra("UserDesignation"));
                Type listType = new TypeToken<ArrayList<SalarySlipModel>>() {
                }.getType();
                listSalary = new Gson().fromJson(json, listType);
            }
        } catch (Exception ex) {
        }
    }

    private void setUpRecycleView() {
        rv_salarySlip.setLayoutManager(new GridLayoutManager(this, 3));
        SalarySlipAdapter adapter = new SalarySlipAdapter(this, listSalary);
        adapter.setClickListener(this);
        rv_salarySlip.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void ItemClicked(View view, int position) {
        try{
            pos = position;
            if (ContextCompat.checkSelfPermission(SalarySlipActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(SalarySlipActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(SalarySlipActivity.this, new String[]{Manifest.permission.CAMERA}, 12);
                } else {
                    ActivityCompat.requestPermissions(SalarySlipActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 12);
                }
            } else {
                OpenAskPass();

            }
        }catch (Exception ex){}
    }

    private class DownloadFile extends AsyncTask<String, Void, String> {
        final ProgressDialog pDialog = new ProgressDialog(SalarySlipActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
                String fileName = strings[1];  // -> maven.pdf
                Log.e("SalaryUrl",strings[0]);
                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                File folder = new File(extStorageDirectory, "BinarySalarySlip");
                folder.mkdir();
                File pdfFile = new File(folder, fileName);
                try {
                    pdfFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileDownloader.downloadFile(fileUrl, pdfFile);

                return fileName;
            } catch (Exception ex) {
            }
            return "";
        }

        @Override
        protected void onPostExecute(String str) {
            pDialog.dismiss();
            if (!str.equals("")) {
                ViewPdf(str);
            } else
                Toast.makeText(SalarySlipActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    public void ViewPdf(String fileName) {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/BinarySalarySlip/" + fileName);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(SalarySlipActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 12: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        OpenAskPass();
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
    private void OpenAskPass() {
        try {
            final Dialog dialog = new Dialog(SalarySlipActivity.this);
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
                            Cursor cursor = getContentResolver().query(Constants.CONTENT_USER_INFO, null, null, null, null);
                            if (cursor.getCount() > 0) {
                                cursor.moveToLast();
                                String pass = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_PASSWORD));
                                if(pass.equals(et_pass.getText().toString().trim())){
                                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(et_pass.getWindowToken(), 0);
                                    dialog.dismiss();
                                    new DownloadFile().execute(MyApplication.SalaryUrl + "eid=" + eid + "&date_month=" + listSalary.get(pos).getYear() + "-" + listSalary.get(pos).getMm() + "-01", listSalary.get(pos).getMm() + listSalary.get(pos).getYear() + ".pdf");
                                }else{
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
}
