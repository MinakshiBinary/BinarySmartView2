package com.binaryic.binarysmartview.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.binaryic.binarysmartview.Activity.ChatActivity;
import com.binaryic.binarysmartview.Activity.CreateGroupActivity;
import com.binaryic.binarysmartview.Activity.GroupListActivity;
import com.binaryic.binarysmartview.Adapter.GroupChannelListAdapter;
import com.binaryic.binarysmartview.Controller.GroupController;
import com.binaryic.binarysmartview.Controller.LoginController;
import com.binaryic.binarysmartview.R;
import com.sendbird.android.BaseChannel;
import com.sendbird.android.BaseMessage;
import com.sendbird.android.GroupChannel;
import com.sendbird.android.GroupChannelListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by minakshi on 13/7/17.
 */

public class ChannelListFragment extends Fragment {

    private static final String CHANNEL_HANDLER_ID = "CHANNEL_HANDLER_GROUP_CHANNEL_LIST";
    public static final String EXTRA_GROUP_CHANNEL_URL = "GROUP_CHANNEL_URL";
    public static final int INTENT_REQUEST_NEW_GROUP_CHANNEL = 302;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private GroupChannelListAdapter mChannelListAdapter;
    private FloatingActionButton mCreateChannelFab;
    private GroupChannelListQuery mChannelListQuery;
    private SwipeRefreshLayout mSwipeRefresh;

    public static ChannelListFragment newInstance() {
        Log.e("ChannelListFragment","");
        ChannelListFragment fragment = new ChannelListFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_channel_list, container, false);
        setRetainInstance(true);

        // Change action bar title
        ((GroupListActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.all_group_channels));

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_group_channel_list);
        mChannelListAdapter = new GroupChannelListAdapter(getActivity());
        mCreateChannelFab = (FloatingActionButton) rootView.findViewById(R.id.fab_group_channel_list);

        mSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_layout_group_channel_list);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(true);
                refreshChannelList(15);
            }
        });

        mCreateChannelFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateGroupActivity.class);
                startActivityForResult(intent, INTENT_REQUEST_NEW_GROUP_CHANNEL);
            }
        });

        setUpRecyclerView();
        setUpChannelListAdapter();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mChannelListAdapter.load();
    }

    @Override
    public void onStop() {
        super.onStop();
        mChannelListAdapter.save();
    }

    @Override
    public void onResume() {
        Log.d("LIFECYCLE", "GroupChannelListFragment onResume()");

        refreshChannelList(15);

        SendBird.addChannelHandler(CHANNEL_HANDLER_ID, new SendBird.ChannelHandler() {
            @Override
            public void onMessageReceived(BaseChannel baseChannel, BaseMessage baseMessage) {
                mChannelListAdapter.updateOrInsert(baseChannel);
            }

            @Override
            public void onTypingStatusUpdated(GroupChannel channel) {
                mChannelListAdapter.notifyDataSetChanged();
            }
        });

        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("LIFECYCLE", "GroupChannelListFragment onPause()");

        SendBird.removeChannelHandler(CHANNEL_HANDLER_ID);
        super.onPause();
    }

    @Override
    public void onDetach() {
        Log.d("LIFECYCLE", "GroupChannelListFragment onDetach()");
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_REQUEST_NEW_GROUP_CHANNEL) {
            if (resultCode == RESULT_OK) {
                // Channel successfully created
                // Enter the newly created channel.
                /*String newChannelUrl = data.getStringExtra(CreateGroupChannelActivity.EXTRA_NEW_CHANNEL_URL);
                if (newChannelUrl != null) {
                    enterGroupChannel(newChannelUrl);
                }*/
            } else {
                Log.d("GrChLIST", "resultCode not STATUS_OK");
            }
        }
    }

    // Sets up recycler view
    private void setUpRecyclerView() {
        Log.d("ChannelListFragment", "setUpRecyclerView");

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mChannelListAdapter);
        //  mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // If user scrolls to bottom of the list, loads more channels.
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (mLayoutManager.findLastVisibleItemPosition() == mChannelListAdapter.getItemCount() - 1) {
                    loadNextChannelList();
                }
            }
        });
    }

    // Sets up channel list adapter
    private void setUpChannelListAdapter() {
        mChannelListAdapter.setOnItemClickListener(new GroupChannelListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GroupChannel channel) {

                enterGroupChannel(channel);
            }
        });

        mChannelListAdapter.setOnItemLongClickListener(new GroupChannelListAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(final GroupChannel channel) {
                showChannelOptionsDialog(channel);
            }
        });
    }

    /**
     * Displays a dialog listing channel-specific options.
     */
    private void showChannelOptionsDialog(final GroupChannel channel) {

        String[] options;
        final boolean pushCurrentlyEnabled = channel.isPushEnabled();

        options = pushCurrentlyEnabled
                ? new String[]{"Block ", "Delete ", "Turn push notifications OFF"}
                : new String[]{"Block ", "Delete ", "Turn push notifications ON"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Options")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            // Show a dialog to confirm that the user wants to leave the channel.
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Do you Want to Block " + channel.getName() + "?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            leaveChannel(channel);
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .create().show();
                        } else if (which == 1) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Do you Want to Delete " + channel.getName() + "?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            deleteChannel(channel);
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .create().show();
                        } else if (which == 2) {
                            setChannelPushPreferences(channel, !pushCurrentlyEnabled);
                        }
                    }
                });
        builder.create().show();
    }

    private void deleteChannel(GroupChannel channel) {
        GroupController.changeStatus(getActivity(),channel.getUrl(),"false");
        refreshChannelList(30);

    }

    /**
     * Turns push notifications on or off for a selected channel.
     *
     * @param channel The channel for which push preferences should be changed.
     * @param on      Whether to set push notifications on or off.
     */
    private void setChannelPushPreferences(final GroupChannel channel, final boolean on) {
        // Change push preferences.
        channel.setPushPreference(on, new GroupChannel.GroupChannelSetPushPreferenceHandler() {
            @Override
            public void onResult(SendBirdException e) {
                if (e != null) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                String toast = on
                        ? "Push notifications have been turned ON"
                        : "Push notifications have been turned OFF";

                Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    /**
     * Enters a Group Channel. Upon entering, a GroupChatFragment will be inflated
     * to display messages within the channel.
     *
     * @param channel The Group Channel to enter.
     */
    void enterGroupChannel(GroupChannel channel) {
        final String channelUrl = channel.getUrl();

        enterGroupChannel(channelUrl, channel.getMembers());
    }

    /**
     * Enters a Group Channel with a URL.
     *
     * @param channelUrl The URL of the channel to enter.
     * @param members
     */
    void enterGroupChannel(String channelUrl, List<User> members) {

        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("groupChannel", channelUrl);
        for (int i = 0; i < members.size(); i++) {
            if (!LoginController.getLoginUser(getActivity()).getUser_ID().matches(members.get(i).getUserId()))
                intent.putExtra("nickname", members.get(i).getNickname());
        }
        startActivity(intent);

        /*GroupChatFragment fragment = GroupChatFragment.newInstance(channelUrl);
        getFragmentManager().beginTransaction()
                .replace(R.id.container_group_channel, fragment)
                .addToBackStack(null)
                .commit();*/
    }

    /**
     * Creates a new query to get the list of the user's Group Channels,
     * then replaces the existing dataset.
     *
     * @param numChannels The number of channels to load.
     */
    private void refreshChannelList(int numChannels) {
        Log.d("ChannelListFragment", "refreshChannelList");

        mChannelListQuery = GroupChannel.createMyGroupChannelListQuery();
        mChannelListQuery.setLimit(numChannels);

        mChannelListQuery.next(new GroupChannelListQuery.GroupChannelListQueryResultHandler() {
            @Override
            public void onResult(List<GroupChannel> list, SendBirdException e) {

                List<GroupChannel> main_list = new ArrayList<GroupChannel>();

                if (e != null) {
                    // Error!
                    e.printStackTrace();
                    return;
                }
                for (int i = 0; i < list.size(); i++) {

                    if (list.get(i).getMemberCount() > 1 && GroupController.isActivated(getActivity(),list.get(i).getUrl()).matches("true")) {
                        main_list.add(list.get((i)));
                    }

                }
                GroupController.addDataInDatabase(getActivity(), main_list);
                mChannelListAdapter.setGroupChannelList(main_list);
            }
        });

        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    /**
     * Loads the next channels from the current query instance.
     */
    private void loadNextChannelList() {
        Log.d("ChannelListFragment", "loadNextChannelList");

        mChannelListQuery.next(new GroupChannelListQuery.GroupChannelListQueryResultHandler() {
            @Override
            public void onResult(List<GroupChannel> list, SendBirdException e) {
                if (e != null) {
                    // Error!
                    e.printStackTrace();
                    return;
                }

                for (GroupChannel channel : list) {
                    mChannelListAdapter.addLast(channel);
                }
            }
        });
    }

    /**
     * Leaves a Group Channel.
     *
     * @param channel The channel to leave.
     */
    private void leaveChannel(final GroupChannel channel) {
        channel.leave(new GroupChannel.GroupChannelLeaveHandler() {
            @Override
            public void onResult(SendBirdException e) {
                if (e != null) {
                    // Error!
                    return;
                }

                // Re-query message list
                refreshChannelList(30);
            }
        });
    }

}
