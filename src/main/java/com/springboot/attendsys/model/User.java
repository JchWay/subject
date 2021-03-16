package com.springboot.attendsys.model;

import java.sql.Timestamp;

public class User {

    //管理员可编辑属性
    private Integer uId;
    private Timestamp uRegtime;
    private String uRegip;
    private Timestamp uLastlogtime;
    private String uLastlogip;
    private Integer uLogcount;
    //用户可编辑属性
    private String uName;
    private String uPassword;
    private String uEmail;
    private String uPhoto;
    private String uRealname;
    private String uPhone;
    private String uSex;
    private String uRole;
    private String uDel;

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Timestamp getuRegtime() {
        return uRegtime;
    }

    public void setuRegtime(Timestamp uRegtime) {
        this.uRegtime = uRegtime;
    }

    public String getuRegip() {
        return uRegip;
    }

    public void setuRegip(String uRegip) {
        this.uRegip = uRegip;
    }

    public Timestamp getuLastlogtime() {
        return uLastlogtime;
    }

    public void setuLastlogtime(Timestamp uLastlogtime) {
        this.uLastlogtime = uLastlogtime;
    }

    public String getuLastlogip() {
        return uLastlogip;
    }

    public void setuLastlogip(String uLastlogip) {
        this.uLastlogip = uLastlogip;
    }

    public Integer getuLogcount() {
        return uLogcount;
    }

    public void setuLogcount(Integer uLogcount) {
        this.uLogcount = uLogcount;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuPhoto() {
        return uPhoto;
    }

    public void setuPhoto(String uPhoto) {
        this.uPhoto = uPhoto;
    }

    public String getuRealname() {
        return uRealname;
    }

    public void setuRealname(String uRealname) {
        this.uRealname = uRealname;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getuSex() {
        return uSex;
    }

    public void setuSex(String uSex) {
        this.uSex = uSex;
    }

    public String getuRole() {
        return uRole;
    }

    public void setuRole(String uRole) {
        this.uRole = uRole;
    }

    public String getuDel() {
        return uDel;
    }

    public void setuDel(String uDel) {
        this.uDel = uDel;
    }

}
