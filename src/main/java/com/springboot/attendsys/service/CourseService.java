package com.springboot.attendsys.service;


import com.springboot.attendsys.mapper.CourseMapper;
import com.springboot.attendsys.model.Course;
import com.springboot.attendsys.model.User;
import com.springboot.attendsys.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseMapper courseMapper;
    public int createCourse(String creater, String coursename, String cover, Timestamp bt, Timestamp et) {
        int i = courseMapper.createCourse(creater, coursename, cover, bt, et);
        return i;
    }

    /*public List<Course> getCourseByCreater(User user){
        String email = user.getuEmail();
        return courseMapper.getCourseByCreater(email);
    }*/

    public List<Course> getCourseBySelected(int uid, int pageSize,int pageNow){
        return courseMapper.getCourseBySelected(uid,(pageNow-1)*pageSize,pageSize);
    }

    public Course getCourseByCid(int cid){
        return courseMapper.getCourseById(cid);
    }

    public int selected(int cid, int uid){
        return courseMapper.selectById(cid,uid);
    }

    public int pubAttend(double lon, double la, int cid,Timestamp atime) {
        return courseMapper.pubAttend(lon,la,cid,atime);
    }

    public List<Course> getAllCourse(int pageSize,int pageNow) {
        List<Course> list = courseMapper.getAllCourse((pageNow-1)*pageSize,pageSize);
        return list;
    }

    public int countAllCourse() {
        int count = courseMapper.countAllCourse();
        return count;
    }

    public int deleteCourseById(int cid) {
        return courseMapper.deleteCourseById(cid);
    }

    public int countAllMyCourse(int uid) {
        int count = courseMapper.countAllMyCourse(uid);
        return count;
    }

    public int deleteselected(int cid, int uid) {
        int count = courseMapper.deleteselected(cid,uid);
        return count;
    }
}