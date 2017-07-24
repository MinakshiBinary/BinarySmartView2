package com.binaryic.binarysmartview.Common;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Pattern;

/**
 * Created by Abhishek on 26-08-2015.
 */
public class Validation {
    private final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(

            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");
    private static AlertDialogNegative alertDialog = new AlertDialogNegative();
    private static AlertDialoguePositive positive = new AlertDialoguePositive();

    public static boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static boolean signUp(Context context, String firstName, String lastName, String emailID, String password, String rePassword) {
        boolean validity = false;
        Log.e("firstName", "" + firstName);

        if (firstName.matches("")) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter First Name");
        } else if (lastName.matches("")) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Last Name");
        } else if (emailID.matches("")) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter EmailID");
        } else if (password.matches("")) {
            if (!checkEmail(emailID))
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Proper EmailID");
            else
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Password");
        } else if (rePassword.matches("")) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Password Once Again");
        } else if (!password.matches(rePassword)) {
            alertDialog.showAlertDialog(context, "Failed", "Password Mismatch,Try Again");
        } else {
            positive.showAlertDialog(context, "Congratulation", "You SignUp Successfully..");

            validity = true;
        }

        return validity;
    }

    public static boolean signUp(Context context, String name, String emailID, String password, String rePassword) {
        boolean validity = false;
        Log.e("firstName", "" + name);
        if (name.matches("")) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Last Name");
        } else if (emailID.matches("")) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter EmailID");
        } else if (password.matches("")) {
            if (!checkEmail(emailID))
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Proper EmailID");
            else
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Password");
        } else if (rePassword.matches("")) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Password Once Again");
        } else if (!password.matches(rePassword)) {
            alertDialog.showAlertDialog(context, "Failed", "Password Mismatch,Try Again");
        } else {
            // positive.showAlertDialog(context, "Congratulation", "You SignUp Successfully..");

            validity = true;
        }

        return validity;
    }

    public static boolean signUp(Context context, String name, String emailID, String phone_Number, String address, String password, String rePassword) {
        boolean validity = false;
        Log.e("firstName", "" + name);
        if (TextUtils.isEmpty(name)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Name");
        } else if (TextUtils.isEmpty(emailID)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter EmailID");
        } else if (!checkEmail(emailID)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Proper EmailID");
        } else if (TextUtils.isEmpty(phone_Number)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Mobile Number");

        } else if (phone_Number.length() < 10) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Valid Mobile Number");
        } else if (TextUtils.isEmpty(address)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Address");
        } else if (TextUtils.isEmpty(password)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter password");
        } else if (TextUtils.isEmpty(rePassword)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Confirm password");
        } else if (!password.matches(rePassword)) {
            alertDialog.showAlertDialog(context, "Failed", "Password Mismatch,Try Again");
        } else {

            validity = true;
        }

        return validity;
    }

    public static boolean signUp(Context context, String emailID, String password, String rePassword) {
        boolean validity = false;
        if (emailID.matches("")) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter EmailID");
        } else if (password.matches("")) {
            if (!checkEmail(emailID))
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Proper EmailID");
            else
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Password");
        } else if (rePassword.matches("")) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Password Once Again");
        } else if (!password.matches(rePassword)) {
            alertDialog.showAlertDialog(context, "Failed", "Password Mismatch,Try Again");
        } else {
            positive.showAlertDialog(context, "Congratulation", "You SignUp Successfully..");

            validity = true;
        }

        return validity;
    }

    public static boolean logIn(Context context, String emailID, String password) {
        boolean validity = false;
        if (TextUtils.isEmpty(emailID)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter EmailID");
        } else if (!checkEmail(emailID))
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Proper EmailID");
        else if (TextUtils.isEmpty(password)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Password");
        } else {
            //positive.showAlertDialog(context, "Congratulation", "You SignUp Successfully..");

            validity = true;
        }

        return validity;
    }

    public static boolean logIn(Context context, String name, String emailID, String password) {
        boolean validity = false;
        Log.e("firstName", "" + name);
        if (name.matches("")) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Last Name");
        } else if (emailID.matches("")) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter EmailID");
        } else if (password.matches("")) {
            if (!checkEmail(emailID))
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Proper EmailID");
            else
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Password");
        } else {
            positive.showAlertDialog(context, "Congratulation", "You SignUp Successfully..");

            validity = true;
        }

        return validity;
    }

    public static boolean changePassword(Context context, String emailID, String password, String rePassword) {
        boolean validity = false;
        if (TextUtils.isEmpty(emailID)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter EmailID");
        } else if (TextUtils.isEmpty(password)) {
            if (!checkEmail(emailID))
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Proper EmailID");
            else
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Password");
        } else if (TextUtils.isEmpty(rePassword)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Password Once Again");
        } else if (!password.matches(rePassword)) {
            alertDialog.showAlertDialog(context, "Failed", "Password Mismatch,Try Again");
        } else {

            validity = true;
        }

        return validity;
    }

    public static boolean changePassword(Context context, String emailID, String password) {
        boolean validity = false;
        if (TextUtils.isEmpty(emailID)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter EmailID");
        } else if (TextUtils.isEmpty(password)) {
            if (!checkEmail(emailID))
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Proper EmailID");
            else
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Password");
        } else {
            positive.showAlertDialog(context, "Congratulation", "You SignUp Successfully..");

            validity = true;
        }

        return validity;
    }

    public static boolean changePassword(Context context, String emailID) {
        boolean validity = false;
        if (TextUtils.isEmpty(emailID)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter EmailID");
        } else {

            validity = true;
        }

        return validity;
    }

    public static boolean forgotPassword(Context context, String emailID) {
        boolean validity = false;
        if (TextUtils.isEmpty(emailID)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter EmailID");
        }
        if (!checkEmail(emailID)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Valid EmailID");
        } else {
            //  positive.showAlertDialog(context, "Congratulation", "You SignUp Successfully..");

            validity = true;
        }

        return validity;
    }


    public static boolean getOTP(Context context, String first_Name, String last_Name, String emailID, String mobile_Number) {
        boolean validity = false;
        if (TextUtils.isEmpty(first_Name)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter First Name");
        } else if (TextUtils.isEmpty(last_Name)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Last Name");
        } else if (TextUtils.isEmpty(emailID)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter EmailID");
        } else if (TextUtils.isEmpty(mobile_Number)) {
            if (!checkEmail(emailID))
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Proper EmailID");
            else
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Phone Number");
        } else if (mobile_Number.length() < 10) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Valid Mobile Number");
        } else {
            validity = true;
        }

        return validity;

    }

    public static boolean editProfile(Context context, String first_Name, String last_Name, String emailID, String mobile_Number, String location, String address) {
        boolean validity = false;
        if (TextUtils.isEmpty(first_Name)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter First Name");
        } else if (TextUtils.isEmpty(last_Name)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Last Name");
        } else if (TextUtils.isEmpty(emailID)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter EmailID");
        } else if (TextUtils.isEmpty(mobile_Number)) {
            if (!checkEmail(emailID))
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Proper EmailID");
            else
                alertDialog.showAlertDialog(context, "Failed", "Please Enter Phone Number");
        } else if (TextUtils.isEmpty(location)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Location");
        } else if (TextUtils.isEmpty(address)) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Address");
        } else if (mobile_Number.length() < 10) {
            alertDialog.showAlertDialog(context, "Failed", "Please Enter Valid Mobile Number");
        } else {
            validity = true;
        }

        return validity;
    }


}
