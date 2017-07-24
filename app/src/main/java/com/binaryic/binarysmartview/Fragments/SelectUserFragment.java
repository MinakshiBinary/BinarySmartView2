package com.binaryic.binarysmartview.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.binaryic.binarysmartview.Adapter.SelectableUserListAdapter;
import com.binaryic.binarysmartview.Controller.LoginController;
import com.binaryic.binarysmartview.Models.UserModel;
import com.binaryic.binarysmartview.R;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.android.UserListQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minakshi on 4/7/17.
 */

public class SelectUserFragment extends Fragment {
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private RelativeLayout mRootLayout;
    private SelectableUserListAdapter mListAdapter;
    private SwipeRefreshLayout swipeContainer;

    private UserListQuery mUserListQuery;
    private UsersSelectedListener mListener;

    private List<String> mSelectedUserIds;


    // To pass selected user IDs to the parent Activity.
    public interface UsersSelectedListener {
        void onUserSelected(boolean selected, String userId);
    }

    public static SelectUserFragment newInstance() {
        SelectUserFragment fragment = new SelectUserFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_select_user, container, false);
        mRootLayout = (RelativeLayout) rootView.findViewById(R.id.mRootLayout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_select_user);
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        mListAdapter = new SelectableUserListAdapter(getActivity());
        mSelectedUserIds = new ArrayList<>();

        mListAdapter.setItemCheckedChangeListener(new SelectableUserListAdapter.OnItemCheckedChangeListener() {
            @Override
            public void OnItemChecked(UserModel user, boolean checked) {
                if (checked) {
                    mListener.onUserSelected(true, user.getUser_ID());
                } else {
                    mListener.onUserSelected(false, user.getUser_ID());
                }
            }
        });

        mListener = (UsersSelectedListener) getActivity();

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                loadInitialUserList(15);
                swipeContainer.setRefreshing(false);

            }
        });


        setUpRecyclerView();

        loadInitialUserList(15);

        //  ((CreateGroupChannelActivity) getActivity()).setState(CreateGroupChannelActivity.STATE_SELECT_USERS);

        return rootView;
    }

    public void setUpRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mListAdapter);
        //   mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (mLayoutManager.findLastVisibleItemPosition() == mListAdapter.getItemCount() - 1) {
                    loadNextUserList(10);
                }
            }
        });
    }

    /**
     * Replaces current user list with new list.
     * Should be used only on initial load.
     */
    public void loadInitialUserList(int size) {

        Log.e("SelectUserFragment", "loadInitialUserList");

        mUserListQuery = SendBird.createUserListQuery();

        mUserListQuery.setLimit(size);
        mUserListQuery.next(new UserListQuery.UserListQueryResultHandler() {
            @Override
            public void onResult(List<User> list, SendBirdException e) {

                List<UserModel> main_list = new ArrayList<UserModel>();

                if (e != null) {
                    Log.e("SelectUserFragment", "Error==" + e.getMessage());
                    swipeContainer.setRefreshing(false);

                    return;
                }

                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).getUserId().matches(LoginController.getLoginUser(getActivity()).getUser_ID())) {
                        main_list.add(LoginController.convert(getActivity(), list.get(i)));
                    }

                    if (main_list != null) {
                        LoginController.putMembersInDatabase(getActivity(), main_list);
                        mListAdapter.setUserList(main_list);
                    }

                }
            }
        });
    }


    /**
     * Loads users and adds them to current user list.
     * <p>
     * A PreviousMessageListQuery must have been already initialized through {@link #loadInitialUserList(int)}
     */
    public void loadNextUserList(int size) {
        mUserListQuery.setLimit(size);

        mUserListQuery.next(new UserListQuery.UserListQueryResultHandler() {
            @Override
            public void onResult(List<User> list, SendBirdException e) {
                if (e != null) {
                    // Error!
                    swipeContainer.setRefreshing(false);
                    return;
                }


                for (User user : list) {
                    mListAdapter.addLast(LoginController.convert(getActivity(), user));
                }
            }
        });
    }

}
