package com.binaryic.binarysmartview.Common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;

import static com.binaryic.binarysmartview.Common.Constants.URL_EMP_PROFILE_UPDATE;



/**
 * Created by admin on 2/23/2016.
 */
public class UploadFileToServer extends AsyncTask<Void, Integer, String> {
    String responseString = null;
    String responseString_msg = null;
    String user_Names;
    private final String emailID;
    Activity context;
    private final File file;
    private ProgressDialog pDialog;
    private boolean flag;
    long totalSize = 0;



    public UploadFileToServer(Activity context, String emailID, File file) {
        this.file = file;
        this.context = context;
        this.emailID = emailID;
    }

    @Override
    protected void onPreExecute() {
       pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        // Making progress bar visible
        // updating progress bar value

        Log.e("progress[0]", "==" + progress[0]);
        // updating percentage value
    }

    @Override
    protected String doInBackground(Void... params) {
        return uploadFile();
    }

    @SuppressWarnings("deprecation")
    private String uploadFile() {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(URL_EMP_PROFILE_UPDATE);

        try {
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {

                        @Override
                        public void transferred(long num) {
                            publishProgress((int) ((num / (float) totalSize) * 100));
                        }
                    });

            // Adding file data to http body

            //  entity.addPart("filename", new FileBody(file));

            entity.addPart("emailID",new StringBody(emailID));

            entity.addPart("emp_image", new FileBody(file));

            Log.e("BODYYY EE", "" + entity);
            //  Log.e("titlkeeeeee", "" + temppTitleArray.get(uploadcount));

            totalSize = entity.getContentLength();
            httppost.setEntity(entity);

            // Making server call
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity r_entity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // Server response
                responseString = EntityUtils.toString(r_entity);
                Log.e("responseString", "" + responseString);

                try {
                    JSONObject object = new JSONObject(responseString);
                    responseString = object.getString("success");
                    responseString_msg = object.getString("msg");
                    Log.e("responseString_msg", "==" + responseString_msg);
                } catch (Exception e) {
                    Log.e("uploadFileToServer", "errorrr==" + e.getMessage());
                }
            } else {
                responseString = "Error occurred! Http Status Code: "
                        + statusCode;
            }

        } catch (Exception e) {
            Log.e("erorrrr", "==" + e.getMessage());
            responseString = e.toString();
        }
        if (responseString.equalsIgnoreCase("1")) {
            flag = true;

        } else {
            Log.e("dataaaaa", "===");
        }
        return responseString;

    }

    @Override
    protected void onPostExecute(String s) {
        Log.e("flaggggg", "==" + flag);
        if (flag) {

        }
        pDialog.dismiss();

    }
}
