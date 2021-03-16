package com.springboot.attendsys.model;

import java.sql.Timestamp;

public class Course {

    private Integer cId;
    private Timestamp cBegintime;
    private Timestamp cEndtime;

    public Timestamp getcAtime() {
        return cAtime;
    }

    public void setcAtime(Timestamp cAtime) {
        this.cAtime = cAtime;
    }

    private Timestamp cAtime;
    private String cName;
    private String cCover;
    private String cCreater;

    public Double getcLon() {
        return cLon;
    }

    public void setcLon(Double cLon) {
        this.cLon = cLon;
    }

    public Double getcLa() {
        return cLa;
    }

    public void setcLa(Double cLa) {
        this.cLa = cLa;
    }

    private Double cLon;
    private Double cLa;

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Timestamp getcBegintime() {
        return cBegintime;
    }

    public void setcBegintime(Timestamp cBegintime) {
        this.cBegintime = cBegintime;
    }

    public Timestamp getcEndtime() {
        return cEndtime;
    }

    public void setcEndtime(Timestamp cEndtime) {
        this.cEndtime = cEndtime;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcCover() {
        return cCover;
    }

    public void setcCover(String cCover) {
        this.cCover = cCover;
    }

    public String getcCreater() {
        return cCreater;
    }

    public void setcCreater(String cCreater) {
        this.cCreater = cCreater;
    }

}
