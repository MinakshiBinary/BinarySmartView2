package com.binaryic.binarysmartview.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.binaryic.binarysmartview.Controller.LoginController;
import com.binaryic.binarysmartview.Fragments.GroupChatFragment;
import com.binaryic.binarysmartview.Fragments.SelectUserFragment;
import com.binaryic.binarysmartview.R;
import com.binaryic.binarysmartview.Util;
import com.sendbird.android.GroupChannel;
import com.sendbird.android.SendBirdException;

import java.util.ArrayList;
import java.util.List;


public class CreateGroupActivity extends AppCompatActivity implements SelectUserFragment.UsersSelectedListener, View.OnClickListener {
    private List<String> mSelectedIds;
    private Button bt_Create_group_channel;
    public static final String EXTRA_NEW_CHANNEL_URL = "EXTRA_NEW_CHANNEL_URL";
    private Button bt_Next;
    private ImageView iv_Sync;
    private ImageView iv_Logout;
    private ImageView iv_AddNew;


    private List<String> mSelectedUserIds;

    /*private SyncClickListener mListener;
    // To pass selected user IDs to the parent Activity.
    public interface SyncClickListener {
        void onSyncClick();
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        init();

        //   getExtra();
        Fragment fragment = SelectUserFragment.newInstance();
        Util.addFragment(R.id.container_create_group_channel, fragment, this);

     /* if (savedInstanceState == null) {
            Fragment fragment = SelectUserFragment.newInstance();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.container_create_group_channel, fragment)
                    .commit();
        }*/
    }

  /*  private void getExtra() {
        if (getIntent().hasExtra("groupChannelUrl")) {
            String channelUrl = getIntent().getStringExtra("groupChannelUrl");
            if (channelUrl != null) {
                // If started from notification
                Fragment fragment = GroupChatFragment.newInstance(channelUrl);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container_create_group_channel, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }*/


    private void init() {
        bt_Create_group_channel = (Button) findViewById(R.id.bt_Create_group_channel);
        bt_Next = (Button) findViewById(R.id.bt_Next);
        iv_Sync = (ImageView) findViewById(R.id.iv_Sync);
        iv_Logout = (ImageView) findViewById(R.id.iv_Logout);
        iv_AddNew = (ImageView) findViewById(R.id.iv_AddNew);

        mSelectedIds = new ArrayList<>();
        bt_Next.setOnClickListener(this);
        iv_Sync.setOnClickListener(this);
        iv_Logout.setOnClickListener(this);
        iv_AddNew.setOnClickListener(this);
        setToolBar();
    }

    @Override
    public void onUserSelected(boolean selected, String userId) {
        Log.e("CreateGroupactivity", "onUserSelected =" + mSelectedIds);
        if (selected) {
            mSelectedIds.add(userId);
        } else {
            mSelectedIds.remove(userId);
        }

        if (mSelectedIds.size() > 0) {
            bt_Next.setEnabled(true);
        } else {
            bt_Next.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_Next:
                createGroupChannel(mSelectedIds, true);
                break;
            case R.id.iv_Sync:
                Fragment fragment = SelectUserFragment.newInstance();
                Util.addFragment(R.id.container_create_group_channel, fragment, this);
                break;
            case R.id.iv_Logout:
                LoginController.disconnect(this);
                break;
            case R.id.iv_AddNew:
                break;

        }
    }

    private void createGroupChannel(List<String> userIds, boolean distinct) {
        GroupChannel.createChannelWithUserIds(userIds, distinct, new GroupChannel.GroupChannelCreateHandler() {
            @Override
            public void onResult(GroupChannel groupChannel, SendBirdException e) {
                if (e != null) {
                    Log.e("CreateGroupActivity", "error==" + e.getMessage());
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra(EXTRA_NEW_CHANNEL_URL, groupChannel.getUrl());
                setResult(RESULT_OK, intent);


                Fragment fragment = GroupChatFragment.newInstance(groupChannel.getUrl());
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container_create_group_channel, fragment)
                        .commit();

            }
        });
    }

    private void setToolBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_group_channel);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setActionBarTitle("Select Member");
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_chat, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
