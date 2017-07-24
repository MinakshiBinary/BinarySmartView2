package com.binaryic.binarysmartview.Models;

/**
 * Created by admin on 10/18/2016.
 */
public class MeetingApplicationModel {
    private String applicationId;
    private String name;
    private String meeting_Date;
    private String from_Time;
    private String to_Time;
    private String title;
    private String purpose;
    private int acceptance_status;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeeting_Date() {
        return meeting_Date;
    }

    public void setMeeting_Date(String meeting_Date) {
        this.meeting_Date = meeting_Date;
    }

    public String getFrom_Time() {
        return from_Time;
    }

    public void setFrom_Time(String from_Time) {
        this.from_Time = from_Time;
    }

    public String getTo_Time() {
        return to_Time;
    }

    public void setTo_Time(String to_Time) {
        this.to_Time = to_Time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAcceptance_status() {
        return acceptance_status;
    }

    public void setAcceptance_status(int acceptance_status) {
        this.acceptance_status = acceptance_status;
    }
}
