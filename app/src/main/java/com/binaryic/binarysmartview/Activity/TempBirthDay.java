package com.binaryic.binarysmartview.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.binaryic.binarysmartview.Adapter.BirthDayTempAdapter;
import com.binaryic.binarysmartview.Models.BirthdayModel;
import com.binaryic.binarysmartview.R;
import com.binaryic.binarysmartview.Common.Constants;
import com.lsjwzh.widget.recyclerviewpager.ViewUtils;

import java.util.ArrayList;

/**
 * Created by User on 13-05-2016.
 */
public class TempBirthDay extends AppCompatActivity {
    RecyclerView listBirthday;
    ArrayList<BirthdayModel> birthdayModelArray = new ArrayList<BirthdayModel>();
    BirthDayTempAdapter adapter;
    float mFlingFactor = 0.15f;
    float mTriggerOffset = 0.1f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_birth);
        listBirthday = (RecyclerView) findViewById(R.id.listBirthday);
        GetBirthDates();
        setupRecyclerView();
        listBirthday.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //super.onScrolled(recyclerView, dx, dy);
                adjustPositionX(dx);
            }
        });
    }

    private void GetBirthDates() {
        Cursor cursor = getContentResolver().query(Constants.CONTENT_UPCOMING_BIRTHDAYS, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                BirthdayModel model = new BirthdayModel();
                model.setName(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME)));
                model.setDate_Of_Birth(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_DATE_OF_BIRTH)));
                model.setEmailID(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_EMAIL_ID)));
                model.setUser_Image(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_USER_IMAGE)));
                birthdayModelArray.add(model);

            }
        }

    }
    private void setupRecyclerView() {
        try {
            //new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            listBirthday.setLayoutManager(layoutManager);
            adapter = new BirthDayTempAdapter(this, birthdayModelArray);
            listBirthday.setAdapter(adapter);
        } catch (Exception ex) {
        }
    }


    private int getFlingCount(int velocity, int cellSize) {
        if(velocity == 0) {
            return 0;
        } else {
            int sign = velocity > 0?1:-1;
            return (int)((double)sign * Math.ceil((double)((float)(velocity * sign) * this.mFlingFactor / (float)cellSize - this.mTriggerOffset)));
        }
    }
    protected void adjustPositionX(int velocityX) {


        int childCount = birthdayModelArray.size();
        if(childCount > 0) {
            int curPosition = ViewUtils.getCenterXChildPosition(listBirthday);
            int childWidth = listBirthday.getWidth() - listBirthday.getPaddingLeft() - listBirthday.getPaddingRight();
            int flingCount = this.getFlingCount(velocityX, childWidth);
            int targetPosition = curPosition + flingCount;
            if(true) {
                flingCount = Math.max(-1, Math.min(1, flingCount));
                targetPosition = flingCount == 0?curPosition:-1 + flingCount;
            }

            targetPosition = Math.max(targetPosition, 0);
            targetPosition = Math.min(targetPosition, birthdayModelArray.size() - 1);
//            if(targetPosition == curPosition && (true && -1 == curPosition || !true)) {
//                View centerXChild = ViewUtils.getCenterXChild(listBirthday);
//                if(centerXChild != null) {
//                    if(this.mTouchSpan > (float)centerXChild.getWidth() * this.mTriggerOffset * this.mTriggerOffset && targetPosition != 0) {
//                        if(!this.reverseLayout) {
//                            --targetPosition;
//                        } else {
//                            ++targetPosition;
//                        }
//                    } else if(this.mTouchSpan < (float)centerXChild.getWidth() * -this.mTriggerOffset && targetPosition != this.mViewPagerAdapter.getItemCount() - 1) {
//                        if(!this.reverseLayout) {
//                            ++targetPosition;
//                        } else {
//                            --targetPosition;
//                        }
//                    }
//                }
//            }

            listBirthday.smoothScrollToPosition(this.safeTargetPosition(targetPosition, birthdayModelArray.size()));
        }


    }
    private int safeTargetPosition(int position, int count) {
        return position < 0?0:(position >= count?count - 1:position);
    }
}
