package com.binaryic.binarysmartview.Common;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class AlertDialoguePositive {

    public void showAlertDialog(Context context, String title2, String message) {
//

        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title2)
                .setContentText(message)
                .show();
    }
}