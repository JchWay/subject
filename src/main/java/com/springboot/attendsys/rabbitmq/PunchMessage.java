package com.springboot.attendsys.rabbitmq;

import com.springboot.attendsys.model.User;


public class PunchMessage {

    private int uId;
    private int cId;

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }
}
