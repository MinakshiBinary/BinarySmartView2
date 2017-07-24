package com.binaryic.binarysmartview.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.binaryic.binarysmartview.Controller.LoginController;
import com.binaryic.binarysmartview.Fragments.ChannelListFragment;
import com.binaryic.binarysmartview.R;


public class GroupListActivity extends AppCompatActivity {
    public static final String EXTRA_GROUP_CHANNEL_URL = "GROUP_CHANNEL_URL";
    public String channelUrl = "";
    public String nickname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_group_channel);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        if (savedInstanceState == null) {
            // If started from launcher, load list of Open Channels
            Fragment fragment = ChannelListFragment.newInstance();

            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStack();

            manager.beginTransaction()
                    .replace(R.id.container_group_channel, fragment)
                    .commit();
        }


        //  String channelUrl = getIntent().getStringExtra("groupChannelUrl");
        if (getIntent().hasExtra("groupChannel")) {
            channelUrl = getIntent().getStringExtra("groupChannel");
            nickname = getIntent().getStringExtra("nickname");

            LoginController.connectToSendBird(this, LoginController.getLoginDetails(this).getUser_ID(), LoginController.getLoginDetails(this).getNick_Name(), channelUrl, nickname);


        }

    }

    /* @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu_main_chat, menu);
         return true;
     }
 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
           /* Intent upIntent = NavUtils.getParentActivityIntent(this);
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
*/
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
