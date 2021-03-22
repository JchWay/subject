package com.springboot.attendsys.rabbitmq;

import com.springboot.attendsys.model.User;


public class PunchMessage {

    private User user;
    private int cId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }
}
