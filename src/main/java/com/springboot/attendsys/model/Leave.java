package com.springboot.attendsys.model;

import java.sql.Timestamp;

public class Leave {
    public Integer getlId() {
        return lId;
    }

    public void setlId(Integer lId) {
        this.lId = lId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getlReason() {
        return lReason;
    }

    public void setlReason(String lReason) {
        this.lReason = lReason;
    }

    public String getlStatus() {
        return lStatus;
    }

    public void setlStatus(String lStatus) {
        this.lStatus = lStatus;
    }

    public String getlContent() {
        return lContent;
    }

    public void setlContent(String lContent) {
        this.lContent = lContent;
    }

    public Timestamp getlTime() {
        return lTime;
    }

    public void setlTime(Timestamp lTime) {
        this.lTime = lTime;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    private Integer lId;
    private Integer uId;
    private Integer cId;
    private String lReason;
    private String lStatus;
    private String lContent;
    private Timestamp lTime;

}
