package com.binaryic.binarysmartview.Models;

/**
 * Created by Administrator on 4/11/2016.
 */
public class HolidaysModel {

    private String userId;
    private String remainingDays;
    private String date;
    private String holidayName;
private String holidayImages;

    public String getHolidayImages() {
        return holidayImages;
    }

    public void setHolidayImages(String holidayImages) {
        this.holidayImages = holidayImages;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(String remainingDays) {
        this.remainingDays = remainingDays;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }
}
