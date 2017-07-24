package com.binaryic.binarysmartview.Common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryic.binarysmartview.Activity.MainActivity;
import com.binaryic.binarysmartview.R;
import com.squareup.picasso.Picasso;

/**
 * Created by MY PC on 17-May-16.
 */
public class MyFragment extends Fragment {

    static int posi;
    public static Fragment newInstance(MainActivity context, int pos, float scale) {
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putFloat("scale", scale);
        posi = pos;
        return Fragment.instantiate(context, MyFragment.class.getName(), b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        DisplayMetrics dm = new DisplayMetrics();
        (MainActivity.mainActivityCtx).getWindowManager().getDefaultDisplay().getMetrics(dm);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dm.widthPixels/3,dm.widthPixels/3);
        LinearLayout fragmentLL  = (LinearLayout) inflater.inflate(R.layout.mf, container, false);
        int pos   = this.getArguments().getInt("pos");
        TextView tv  = (TextView) fragmentLL.findViewById(R.id.lblName);
        TextView lblDate  = (TextView) fragmentLL.findViewById(R.id.lblDate);

        ImageView iv = (ImageView) fragmentLL.findViewById(R.id.pagerImg);
        iv.setLayoutParams(layoutParams);
        Picasso.with(MainActivity.mainActivityCtx).load(MainActivity.mainActivityCtx.mData.get(pos).imageResId).into(iv);
        tv.setText(MainActivity.mainActivityCtx.mData.get(pos).titleResId.split(" ")[0]);
        lblDate.setText(MainActivity.mainActivityCtx.mData.get(pos).date);
        iv.setPadding(10, 10, 10, 10);
        MyLinearLayout root = (MyLinearLayout) fragmentLL.findViewById(R.id.root);
        float scale   = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);
        return fragmentLL;
    }
}
