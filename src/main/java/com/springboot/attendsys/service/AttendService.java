package com.springboot.attendsys.service;

import com.springboot.attendsys.model.Attendance;
import com.springboot.attendsys.model.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.attendsys.mapper.AttendMapper;

import java.util.List;

@Service
public class AttendService {
    @Autowired
    AttendMapper attendMappper;

    public int publeave(int uid, int cid, String lreason) {
        return attendMappper.publeave(uid, cid, lreason);
    }

    public Leave getleavebyids(int uid, int cid) {
        return attendMappper.getleavebyids(uid, cid);
    }

    public Attendance getattendbyids(int uid, int cid) {
        return attendMappper.getattendbyids(uid, cid);
    }

    public int punch(int uid, int cid) {
        return attendMappper.punch(uid,cid);
    }

    public List<Leave> getallleavebyuser(String uemail,int pageSize,int pageNow) {
        return attendMappper.getallleavebyuser(uemail,(pageNow-1)*pageSize,pageSize);
    }

    public int countallleave() {
        return attendMappper.countallleave();
    }

    public Leave getleavebylid(int lid) {
        return attendMappper.getleavebylid(lid);
    }

    public int approveleave(int lid, String status) {
        return attendMappper.approveleave(lid,status);
    }

    public List<Leave> getallleavebystudent(String uemail, int pageSize, int pageNow) {
        return attendMappper.getallleavebystudent(uemail,(pageNow-1)*pageSize,pageSize);
    }
}
