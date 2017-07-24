package com.binaryic.binarysmartview.Common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by admin on 1/30/2016.
 */
public class CommonFunction {
    public static String loadSavedPreferencesstring(Context activityCon, String key) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activityCon);
        String value = sharedPreferences.getString(key, "0");
        return value;
    }

    public static void savePreferencesstring(Context activityCon, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activityCon);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void createSnackbar(View view, String message) {
        final Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        snackbar.show();
    }

    public static String checkTime() {
        String time;
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.AM_PM) == Calendar.AM) {
            time = c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + "AM ";
        } else {
            time = c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + "PM ";
        }
        return time;

    }

    public static String checkDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static void colorizeToolbar(Toolbar toolbarView, int toolbarIconsColor, Activity activity) {
        final PorterDuffColorFilter colorFilter
                = new PorterDuffColorFilter(toolbarIconsColor, PorterDuff.Mode.MULTIPLY);

        for (int i = 0; i < toolbarView.getChildCount(); i++) {
            final View v = toolbarView.getChildAt(i);

            //Step 1 : Changing the color of back button (or open drawer button).
            if (v instanceof ImageButton) {
                //Action Bar back button
                ((ImageButton) v).getDrawable().setColorFilter(colorFilter);
            }

            if (v instanceof ActionMenuView) {
                for (int j = 0; j < ((ActionMenuView) v).getChildCount(); j++) {

                    //Step 2: Changing the color of any ActionMenuViews - icons that
                    //are not back button, nor text, nor overflow menu icon.
                    final View innerView = ((ActionMenuView) v).getChildAt(j);

                    if (innerView instanceof ActionMenuItemView) {
                        int drawablesCount = ((ActionMenuItemView) innerView).getCompoundDrawables().length;
                        for (int k = 0; k < drawablesCount; k++) {
                            if (((ActionMenuItemView) innerView).getCompoundDrawables()[k] != null) {
                                final int finalK = k;

                                //Important to set the color filter in seperate thread,
                                //by adding it to the message queue
                                //Won't work otherwise.
                                innerView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((ActionMenuItemView) innerView).getCompoundDrawables()[finalK].setColorFilter(colorFilter);
                                    }
                                });
                            }
                        }
                    }
                }
            }

            //Step 3: Changing the color of title and subtitle.
            toolbarView.setTitleTextColor(toolbarIconsColor);
            toolbarView.setSubtitleTextColor(toolbarIconsColor);

            //Step 4: Changing the color of the Overflow Menu icon.
            //setOverflowButtonColor(activity, colorFilter);
        }
    }


    /*
        private static void setOverflowButtonColor(final Activity activity, final PorterDuffColorFilter colorFilter) {
            final String overflowDescription = activity.getString(R.string.abc_action_menu_overflow_description);
            final ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            final ViewTreeObserver viewTreeObserver = decorView.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    final ArrayList<View> outViews = new ArrayList<View>();
                    decorView.findViewsWithText(outViews, overflowDescription,
                            View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                    if (outViews.isEmpty()) {
                        return;
                    }
                    TintImageView overflow=(TintImageView) outViews.get(0);
                    overflow.setColorFilter(colorFilter);
                    removeOnGlobalLayoutListener(decorView,this);
                }
            });
        }
    */
    public static String getDate(String date) {
        String[] rawDate = date.trim().split(" ");
        String[] day = rawDate[0].trim().split("-");
        return day[2] + "/" + day[1] + "/" + day[0];
    }
}
