package com.binaryic.binarysmartview.Models;

/**
 * Created by minakshi on 19/7/17.
 */

public class GroupChannelModel {
    private String group_name;
    private String group_image_url;
    private String group_url;
    private String group_member_user_id;
    private boolean is_group_active;

    public String getGroup_url() {
        return group_url;
    }

    public void setGroup_url(String group_url) {
        this.group_url = group_url;
    }

    public String getGroup_member_user_id() {
        return group_member_user_id;
    }

    public void setGroup_member_user_id(String group_member_user_id) {
        this.group_member_user_id = group_member_user_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_image_url() {
        return group_image_url;
    }

    public void setGroup_image_url(String group_image_url) {
        this.group_image_url = group_image_url;
    }

    public boolean is_group_active() {
        return is_group_active;
    }

    public void setIs_group_active(boolean is_group_active) {
        this.is_group_active = is_group_active;
    }
}
