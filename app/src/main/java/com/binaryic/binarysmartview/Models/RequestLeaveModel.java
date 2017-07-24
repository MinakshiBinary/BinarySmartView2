package com.binaryic.binarysmartview.Models;

/**
 * Created by admin on 10/17/2016.
 */
public class RequestLeaveModel {
    private String name;
    private String fromDate;
    private String toDate;
    private String leaveApplyDate;
    private String leaveType;
    private int leaveStatus;
    private String profilePic;
    private String leaveTitle;
    private String leaveId;
    private String leave_Duration;

    public String getLeaveTitle() {
        return leaveTitle;
    }

    public void setLeaveTitle(String leaveTitle) {
        this.leaveTitle = leaveTitle;
    }

    public String getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(String leaveId) {
        this.leaveId = leaveId;
    }

    public String getLeave_Duration() {
        return leave_Duration;
    }

    public void setLeave_Duration(String leave_Duration) {
        this.leave_Duration = leave_Duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveApplyDate() {
        return leaveApplyDate;
    }

    public void setLeaveApplyDate(String leaveApplyDate) {
        this.leaveApplyDate = leaveApplyDate;
    }

    public int getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(int leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
