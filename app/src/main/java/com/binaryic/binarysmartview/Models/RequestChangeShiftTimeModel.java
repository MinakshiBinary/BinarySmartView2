package com.binaryic.binarysmartview.Models;

/**
 * Created by admin on 10/18/2016.
 */
public class RequestChangeShiftTimeModel {
    private String shiftChangeId;
    private String name;
    private int Change_Time_Status;
    private String current_In_Time;
    private String current_Out_Time;
    private String new_In_Time;
    private String new_Out_Time;
    private String request_On_Date;

    public String getShiftChangeId() {
        return shiftChangeId;
    }

    public void setShiftChangeId(String shiftChangeId) {
        this.shiftChangeId = shiftChangeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChange_Time_Status() {
        return Change_Time_Status;
    }

    public void setChange_Time_Status(int change_Time_Status) {
        Change_Time_Status = change_Time_Status;
    }

    public String getCurrent_In_Time() {
        return current_In_Time;
    }

    public void setCurrent_In_Time(String current_In_Time) {
        this.current_In_Time = current_In_Time;
    }

    public String getCurrent_Out_Time() {
        return current_Out_Time;
    }

    public void setCurrent_Out_Time(String current_Out_Time) {
        this.current_Out_Time = current_Out_Time;
    }

    public String getNew_In_Time() {
        return new_In_Time;
    }

    public void setNew_In_Time(String new_In_Time) {
        this.new_In_Time = new_In_Time;
    }

    public String getNew_Out_Time() {
        return new_Out_Time;
    }

    public void setNew_Out_Time(String new_Out_Time) {
        this.new_Out_Time = new_Out_Time;
    }

    public String getRequest_On_Date() {
        return request_On_Date;
    }

    public void setRequest_On_Date(String request_On_Date) {
        this.request_On_Date = request_On_Date;
    }


}
