package com.springboot.attendsys.model;

import java.sql.Timestamp;

public class Question {
    private int qId;
    private int uId;
    private String qTopic;
    private String qContent;
    private Timestamp qTime;

    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getqTopic() {
        return qTopic;
    }

    public void setqTopic(String qTopic) {
        this.qTopic = qTopic;
    }

    public String getqContent() {
        return qContent;
    }

    public void setqContent(String qContent) {
        this.qContent = qContent;
    }

    public Timestamp getqTime() {
        return qTime;
    }

    public void setqTime(Timestamp qTime) {
        this.qTime = qTime;
    }

}
