package com.binaryic.binarysmartview.Controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.binaryic.binarysmartview.Activity.ChatActivity;
import com.binaryic.binarysmartview.Activity.GroupListActivity;
import com.binaryic.binarysmartview.Activity.LoginActivity;
import com.binaryic.binarysmartview.Activity.MainActivity;
import com.binaryic.binarysmartview.Models.UserModel;
import com.binaryic.binarysmartview.util.PreferenceUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;

import java.util.ArrayList;
import java.util.List;

import static com.binaryic.binarysmartview.Common.Constants.COLUMN_EMAIL_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_NICKNAME;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_PROFILE_IMAGE;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_ID;
import static com.binaryic.binarysmartview.Common.Constants.COLUMN_USER_IMAGE;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_MEMBERS;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_UPCOMING_BIRTHDAYS;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_USER;
import static com.binaryic.binarysmartview.Common.Constants.CONTENT_USER_INFO;


/**
 * Created by minakshi on 5/7/17.
 */

public class LoginController {

    public static UserModel getLoginDetails(Activity context) {
        UserModel userModel = new UserModel();

        Cursor cursor = context.getContentResolver().query(CONTENT_USER_INFO, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            userModel.setUser_ID(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID)));
            userModel.setNick_Name(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            userModel.setProfile_Url(cursor.getString(cursor.getColumnIndex(COLUMN_USER_IMAGE)));
        }
        cursor.close();
        return userModel;
    }

    public static boolean contactExists(Activity context, String number) {
        Log.e("LoginController", "number==" + number);
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.
                CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        while (phones.moveToNext()) {
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
           /* if (PhoneNumberUtils.compare(number, phoneNumber)) {*/
            if (phoneNumber.contains(number)) {

                Log.e("LoginController", "true==" + number);

                return true;
            }
        }
        Log.e("LoginController", "false==" + number);

        return false;
    }

    public static void connectToSendBird(final Activity context, final String userId, final String userNickname, final String groupChannel, final String notification_Nickname) {
        // Show the loading indicator
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Getting Connected...!!!!");
        pDialog.show();


        SendBird.connect(userId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {

                // Callback received; hide the progress bar.
                pDialog.dismiss();

                Log.e("LoginACtivity", "user details::");
                Log.e("LoginACtivity", "user UserId::" + user.getUserId());
                Log.e("LoginACtivity", "user Nickname::" + user.getNickname());
                Log.e("LoginACtivity", "user ProfileUrl::" + user.getProfileUrl());
                Log.e("LoginACtivity", "user LastSeen::" + user.getLastSeenAt());
                Log.e("LoginACtivity", "user ConnectionStatus::" + user.getConnectionStatus());

                ContentValues values = new ContentValues();
                values.put(COLUMN_USER_ID, user.getUserId());
                values.put(COLUMN_NICKNAME, user.getNickname());
                values.put(COLUMN_PROFILE_IMAGE, user.getProfileUrl());

                String selection = COLUMN_USER_ID + " = '" + user.getUserId() + "'";

                Cursor cursor = context.getContentResolver().query(CONTENT_USER, null, selection, null, null);
                if (cursor.getCount() > 0) {
                    context.getContentResolver().update(CONTENT_USER, values, null, null);
                } else {
                    context.getContentResolver().insert(CONTENT_USER, values);
                }

                if (e != null) {
                    // Error!
                    Toast.makeText(
                            context, "" + e.getCode() + ": " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    // Show login failure snackbar
                    Toast.makeText(context, "Login to SendBird failed", Toast.LENGTH_SHORT).show();

                    PreferenceUtils.setConnected(context, false);
                    return;
                }

                PreferenceUtils.setConnected(context, true);

                // Update the user's nickname
                updateCurrentUserInfo(context, userNickname);
                updateCurrentUserPushToken(context);

                // Proceed to MainActivity
                //  Intent intent = new Intent(context, CreateGroupActivity.class);
                Intent intent;
                if (TextUtils.isEmpty(groupChannel)) {
                    intent = new Intent(context, GroupListActivity.class);

                } else {
                    intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("groupChannel", groupChannel);
                    intent.putExtra("nickname", notification_Nickname);
                }
                context.startActivity(intent);
                if (context instanceof MainActivity) {
                    context.finish();
                }

            }
        });
    }

    public static void updateCurrentUserPushToken(final Activity context) {
        // Register Firebase Token
        SendBird.registerPushTokenForCurrentUser(FirebaseInstanceId.getInstance().getToken(),
                new SendBird.RegisterPushTokenWithStatusHandler() {
                    @Override
                    public void onRegistered(SendBird.PushTokenRegistrationStatus pushTokenRegistrationStatus, SendBirdException e) {
                        if (e != null) {
                            // Error!
                            Toast.makeText(context, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.e("LoginController", "Push token registered.");
                    }
                });
    }

    public static void updateCurrentUserPushToken(final Context context) {
        // Register Firebase Token
        SendBird.registerPushTokenForCurrentUser(FirebaseInstanceId.getInstance().getToken(),
                new SendBird.RegisterPushTokenWithStatusHandler() {
                    @Override
                    public void onRegistered(SendBird.PushTokenRegistrationStatus pushTokenRegistrationStatus, SendBirdException e) {
                        if (e != null) {
                            // Error!
                            Toast.makeText(context, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.e("LoginController", "Push token registered.");
                    }
                });
    }

    public static void updateCurrentUserInfo(final Activity context, String userNickname) {
        SendBird.updateCurrentUserInfo(userNickname, null, new SendBird.UserInfoUpdateHandler() {
            @Override
            public void onUpdated(SendBirdException e) {
                if (e != null) {
                    // Error!
                    Toast.makeText(
                            context, "" + e.getCode() + ":" + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    // Show update failed snackbar
                    Toast.makeText(context, "Update user nickname failed", Toast.LENGTH_SHORT).show();

                    return;
                }

            }
        });
    }

    public static void updateCurrentUserInfo(final Context context, String userNickname) {
        SendBird.updateCurrentUserInfo(userNickname, null, new SendBird.UserInfoUpdateHandler() {
            @Override
            public void onUpdated(SendBirdException e) {
                if (e != null) {
                    // Error!
                    Toast.makeText(
                            context, "" + e.getCode() + ":" + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    // Show update failed snackbar
                    Toast.makeText(context, "Update user nickname failed", Toast.LENGTH_SHORT).show();

                    return;
                }

            }
        });
    }

    public static boolean isLogin(Activity context) {
        Cursor cursor = context.getContentResolver().query(CONTENT_USER, null, null, null, null);
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public static UserModel getLoginUser(Activity context) {
        UserModel userModel = new UserModel();

        Cursor cursor = context.getContentResolver().query(CONTENT_USER, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            userModel.setUser_ID(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)));
            userModel.setProfile_Url(cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_IMAGE)));
            userModel.setNick_Name(cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME)));
        }
        cursor.close();
        return userModel;
    }

    public static UserModel getLoginUser(Context context) {
        UserModel userModel = new UserModel();

        Cursor cursor = context.getContentResolver().query(CONTENT_USER, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            userModel.setUser_ID(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)));
            userModel.setProfile_Url(cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_IMAGE)));
            userModel.setNick_Name(cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME)));
        }
        cursor.close();
        return userModel;
    }

    public static void deleteDatabase(Activity context) {
        context.getContentResolver().delete(CONTENT_USER, null, null);
        context.getContentResolver().delete(CONTENT_MEMBERS, null, null);

    }

    public static void disconnect(final Activity context) {
        SendBird.unregisterPushTokenAllForCurrentUser(new SendBird.UnregisterPushTokenHandler() {
            @Override
            public void onUnregistered(SendBirdException e) {
                if (e != null) {
                    // Error!
                    e.printStackTrace();

                    // Don't return because we still need to disconnect.
                } else {
                    Toast.makeText(context, "All push tokens unregistered.", Toast.LENGTH_SHORT)
                            .show();
                }

                SendBird.disconnect(new SendBird.DisconnectHandler() {
                    @Override
                    public void onDisconnected() {
                        LoginController.deleteDatabase(context);
                        PreferenceUtils.setConnected(context, false);
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                        context.finish();
                    }
                });
            }
        });
    }

    public static void putMembersInDatabase(Activity context, List<UserModel> list) {
        for (int i = 0; i < list.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID, list.get(i).getUser_ID());
            values.put(COLUMN_NICKNAME, list.get(i).getNick_Name());
            values.put(COLUMN_PROFILE_IMAGE, list.get(i).getProfile_Url());

            String selection = COLUMN_USER_ID + " ='" + list.get(i).getUser_ID() + "'";
            Cursor cursor = context.getContentResolver().query(CONTENT_MEMBERS, null, selection, null, null);
            if (cursor.getCount() > 0) {
                context.getContentResolver().update(CONTENT_MEMBERS, values, selection, null);
            } else {
                context.getContentResolver().insert(CONTENT_MEMBERS, values);
            }
            cursor.close();

        }

    }

    public static List<UserModel> getMembersFromDatabase(Activity context) {
        List<UserModel> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(CONTENT_MEMBERS, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                UserModel model = new UserModel();
                model.setUser_ID(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)));
                model.setNick_Name(cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME)));
                model.setProfile_Url(cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_IMAGE)));
                list.add(model);
            }

        }
        cursor.close();
        return list;
    }

    public static UserModel getMemberByNicknameFromDatabase(Activity context, String nickname) {
        String selection = COLUMN_NICKNAME + " ='" + nickname + "'";
        UserModel model = new UserModel();
        String profile_pic = "";
        Cursor cursor = context.getContentResolver().query(CONTENT_MEMBERS, null, selection, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                model.setUser_ID(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)));
                model.setNick_Name(cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME)));
                profile_pic = LoginController.getUserProfilePic(context, cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)));
                if (android.text.TextUtils.isEmpty(profile_pic)) {
                    model.setProfile_Url(cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_IMAGE)));
                } else {
                    model.setProfile_Url(profile_pic);
                }
            }
        }
        cursor.close();
        return model;
    }

    public static UserModel convert(Activity context, User user) {
        UserModel model = new UserModel();
        model.setUser_ID(user.getUserId());
        model.setNick_Name(user.getNickname());
        String profilePic = LoginController.getUserProfilePic(context, user.getUserId());
        if (TextUtils.isEmpty(profilePic)) {
            model.setProfile_Url(user.getProfileUrl());
        } else {
            model.setProfile_Url(LoginController.getUserProfilePic(context, user.getUserId()));
        }

        return model;
    }

    public static void addNumberIntoTable(Activity context, String nickname) {
        UserModel model = new UserModel();
        List<UserModel> list = new ArrayList<>();

        model = getMemberByNicknameFromDatabase(context, nickname);
        list.add(model);
        putMembersInDatabase(context, list);
    }

    public static String getUserProfilePic(Activity context, String userId) {
        String profilePic = "";
        Log.e("LoginController", "userId==" + userId);

        String selection = COLUMN_EMAIL_ID + " ='" + userId + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_UPCOMING_BIRTHDAYS, null, selection, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            profilePic = cursor.getString(cursor.getColumnIndex(COLUMN_USER_IMAGE));
        }
        cursor.close();
        Log.e("LoginController", "profilePic==" + profilePic);

        return profilePic;
    }

    public static String getUserProfilePic(Context context, String userId) {
        String profilePic = "";
        Log.e("LoginController", "userId==" + userId);

        String selection = COLUMN_EMAIL_ID + " ='" + userId + "'";
        Cursor cursor = context.getContentResolver().query(CONTENT_UPCOMING_BIRTHDAYS, null, selection, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            profilePic = cursor.getString(cursor.getColumnIndex(COLUMN_USER_IMAGE));
        }
        cursor.close();
        Log.e("LoginController", "profilePic==" + profilePic);

        return profilePic;
    }


      /* public static boolean putAllMembersInDatabase(Activity context, List<User> list) {
        boolean isInserted = false;
        for (int i = 0; i < list.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID, list.get(i).getUserId());
            values.put(COLUMN_NICKNAME, list.get(i).getNickname());
            values.put(COLUMN_PROFILE_IMAGE, list.get(i).getProfileUrl());

            String selection = COLUMN_USER_ID + " ='" + list.get(i).getUserId() + "'";
            Cursor cursor = context.getContentResolver().query(CONTENT_ALL_MEMBERS, null, selection, null, null);
            if (cursor.getCount() > 0) {
                context.getContentResolver().update(CONTENT_ALL_MEMBERS, values, selection, null);
            } else {
                context.getContentResolver().insert(CONTENT_ALL_MEMBERS, values);
                isInserted = true;
            }
            cursor.close();
        }
        return isInserted;

    }*/

}

