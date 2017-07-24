package com.binaryic.binarysmartview.Models;

/**
 * Created by Administrator on 3/31/2016.
 */
public class AttendenceModel {
    private String inTime;
    private String outTime;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    private String day;

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPresentStatus() {
        return presentStatus;
    }

    public void setPresentStatus(String presentStatus) {
        this.presentStatus = presentStatus;
    }

    public String getMarkStatus() {
        return markStatus;
    }

    public void setMarkStatus(String markStatus) {
        this.markStatus = markStatus;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    private String date;
    private String originalInTime;

    public String getOriginalOutTime() {
        return originalOutTime;
    }

    public void setOriginalOutTime(String originalOutTime) {
        this.originalOutTime = originalOutTime;
    }

    public String getOriginalInTime() {
        return originalInTime;
    }

    public void setOriginalInTime(String originalInTime) {
        this.originalInTime = originalInTime;
    }

    private String originalOutTime;
    private String presentStatus;
    private String markStatus;
    private String userID;
}
