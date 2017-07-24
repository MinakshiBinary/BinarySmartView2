package com.binaryic.binarysmartview.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.binaryic.binarysmartview.Activity.ChatActivity;
import com.binaryic.binarysmartview.Controller.LoginController;
import com.binaryic.binarysmartview.Models.UserModel;
import com.binaryic.binarysmartview.R;
import com.binaryic.binarysmartview.util.ImageUtils;
import com.sendbird.android.GroupChannel;
import com.sendbird.android.SendBirdException;

import java.util.ArrayList;
import java.util.List;

import static com.binaryic.binarysmartview.Activity.CreateGroupActivity.EXTRA_NEW_CHANNEL_URL;


/**
 * Populates a RecyclerView with a list of users, each with a checkbox.
 */

public class SelectableUserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserModel> mUsers;
    public Activity mContext;
    private static List<String> mSelectedUserIds;

    // For the adapter to track which users have been selected
    private OnItemCheckedChangeListener mCheckedChangeListener;

    public interface OnItemCheckedChangeListener {
        void OnItemChecked(UserModel user, boolean checked);
    }

    public SelectableUserListAdapter(Activity context) {
        mContext = context;
        mUsers = new ArrayList<>();
        mSelectedUserIds = new ArrayList<>();
    }

    public void setItemCheckedChangeListener(OnItemCheckedChangeListener listener) {
        mCheckedChangeListener = listener;
    }

    public void setUserList(List<UserModel> users) {
        mUsers = users;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_selectable_user, parent, false);
        return new SelectableUserHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Log.e("SelectableUserAdaptre", "LoginController getUser_ID==" + LoginController.getLoginUser(mContext).getUser_ID());
        Log.e("SelectableUserAdaptre", "getUser_ID==" + mUsers.get(position).getUser_ID());
        if (mUsers != null)
            if (!mUsers.get(position).getUser_ID().matches(LoginController.getLoginUser(mContext).getUser_ID())) {

                ((SelectableUserHolder) holder).bind(
                        mContext,
                        mUsers.get(position),
                        isSelected(mUsers.get(position)),
                        mCheckedChangeListener);
            }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public boolean isSelected(UserModel user) {
        return mSelectedUserIds.contains(user.getUser_ID());
    }

    public void addLast(UserModel user) {
        mUsers.add(user);
        notifyDataSetChanged();
    }

    public class SelectableUserHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private ImageView profileImage;
        private CheckBox checkbox;

        public SelectableUserHolder(View itemView) {
            super(itemView);

            this.setIsRecyclable(false);

            nameText = (TextView) itemView.findViewById(R.id.text_selectable_user_list_nickname);
            profileImage = (ImageView) itemView.findViewById(R.id.image_selectable_user_list_profile);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox_selectable_user_list);
        }

        private void bind(final Context context, final UserModel user, boolean isSelected, final OnItemCheckedChangeListener listener) {

            nameText.setText(user.getNick_Name());
            ImageUtils.displayRoundImageFromUrl(context, user.getProfile_Url(), profileImage);

            if (isSelected) {
                checkbox.setChecked(true);
            } else {
                checkbox.setChecked(false);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createGroupChannel(user, true);

                    //checkbox.setChecked(!checkbox.isChecked());
                }
            });

            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listener.OnItemChecked(user, isChecked);

                    if (isChecked) {
                        mSelectedUserIds.add(user.getUser_ID());
                    } else {
                        mSelectedUserIds.remove(user.getUser_ID());
                    }
                }
            });
        }

        private void createGroupChannel(final UserModel user, boolean distinct) {
            List<String> userIds = new ArrayList<>();
            userIds.add(user.getUser_ID());

            GroupChannel.createChannelWithUserIds(userIds, distinct, new GroupChannel.GroupChannelCreateHandler() {
                @Override
                public void onResult(GroupChannel groupChannel, SendBirdException e) {
                    if (e != null) {
                        Log.e("CreateGroupActivity", "error==" + e.getMessage());
                        return;
                    }


                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_NEW_CHANNEL_URL, groupChannel.getUrl());
                    //setResult(RESULT_OK, intent);

                    Intent intent1 = new Intent(mContext, ChatActivity.class);
                    intent1.putExtra("groupChannel", groupChannel.getUrl());
                    intent1.putExtra("nickname", user.getNick_Name());

                    mContext.startActivity(intent1);
                    mContext.finish();
                }
            });
        }

    }
}
