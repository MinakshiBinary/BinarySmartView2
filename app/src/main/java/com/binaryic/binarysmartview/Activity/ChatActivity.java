package com.binaryic.binarysmartview.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.binaryic.binarysmartview.Controller.LoginController;
import com.binaryic.binarysmartview.Fragments.GroupChatFragment;
import com.binaryic.binarysmartview.R;
import com.binaryic.binarysmartview.Util;


public class ChatActivity extends AppCompatActivity {
    FrameLayout container_group_chat;
    String groupChannel = "";
    String nickname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getExtra();

        init();
        if (savedInstanceState == null) {
            callFragment();
        }

    }

    private void callFragment() {
        Fragment fragment = GroupChatFragment.newInstance(groupChannel);
        Util.addFragment(R.id.container_group_chat, fragment, ChatActivity.this);

    }

    private void setToolBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_group_chat);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setActionBarTitle(nickname);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // onBackPressed();
            Intent upIntent = NavUtils.getParentActivityIntent(this);
            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                // This activity is NOT part of this app's task, so create a new task
                // when navigating up, with a synthesized back stack.
                TaskStackBuilder.create(this)
                        // Add all of this activity's parents to the back stack
                        .addNextIntentWithParentStack(upIntent)
                        // Navigate up to the closest parent
                        .startActivities();
            } else {
                // This activity is part of this app's task, so simply
                // navigate up to the logical parent activity.
                NavUtils.navigateUpTo(this, upIntent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void getExtra() {
        if (getIntent().hasExtra("groupChannel")) {
            groupChannel = getIntent().getStringExtra("groupChannel");
            nickname = getIntent().getStringExtra("nickname");
            LoginController.addNumberIntoTable(this, nickname);
        }
    }

    private void init() {
        container_group_chat = (FrameLayout) findViewById(R.id.container_group_chat);
        setToolBar();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
