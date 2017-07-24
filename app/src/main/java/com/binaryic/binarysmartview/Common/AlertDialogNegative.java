package com.binaryic.binarysmartview.Common;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AlertDialogNegative {

    public  static  void showAlertDialog(Context context, String title2, String message) {
//

        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title2)
                .setContentText(message)
                .setConfirmText("Ok")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.cancel();
            }
        })
                .show();

    }
}