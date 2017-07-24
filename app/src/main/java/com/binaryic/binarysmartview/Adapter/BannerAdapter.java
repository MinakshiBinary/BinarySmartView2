package com.binaryic.binarysmartview.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.binaryic.binarysmartview.Activity.IntroActivity;
import com.binaryic.binarysmartview.R;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by User on 01-03-2016.
 */
public class BannerAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<String> mResources;

    public BannerAdapter(Context context, List<String> banners) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResources = banners;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.banner_list_item, container, false);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

        Uri uri = Uri.parse("android.resource://com.binaryic.binarysmartview/drawable/" + mResources.get(position).toString());
        Picasso.with(mContext).load(uri).into(imageView);
        Log.e("position","=="+position);
        Log.e("mResources","=="+IntroActivity.mPager.getCurrentItem());

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}
