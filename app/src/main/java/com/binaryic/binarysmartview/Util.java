package com.binaryic.binarysmartview;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


/**
 * Created by minakshi on 3/7/17.
 */

public class Util {

    public static void addFragmentBack(int containerId, Fragment fragment, Activity context) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerId, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

    public static void addFragment(int containerId, Fragment fragment, Activity context) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
       /* transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_right_exit);*/
        transaction.add(containerId, fragment).commit();
    }


}
