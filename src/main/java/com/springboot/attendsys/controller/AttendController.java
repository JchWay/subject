package com.springboot.attendsys.controller;

import com.springboot.attendsys.model.Course;
import com.springboot.attendsys.model.Leave;
import com.springboot.attendsys.model.User;
import com.springboot.attendsys.service.CourseService;
import com.springboot.attendsys.service.AttendService;
import com.springboot.attendsys.util.LayuiTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AttendController {
    @Autowired
    private AttendService attendService;
    @Autowired
    private CourseService courseService;

    @PostMapping("/pub_attend")
    @ResponseBody
    public String pub_attend(@RequestBody Map o, User user, HttpServletRequest request) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("admin")) {
            return "redirect:to_login";
        }
        Map<String, Object> map = new HashMap<String, Object>();

        //获取课程信息
        int cid = (Integer) o.get("cid");
        Course coursedetail = courseService.getCourseByCid(cid);
        //判断是否已经发布打卡任务
        if (coursedetail.getcAtime() == null) {
            if (o.get("lon") != null && o.get("la") != null) {
                double lon = Double.parseDouble((String) o.get("lon"));
                double la = Double.parseDouble((String) o.get("la"));
                Timestamp atime = new Timestamp(System.currentTimeMillis());
                //发布打卡
                int i = courseService.pubAttend(lon, la, cid, atime);
                if (i == 1) {
                    map.put("msg", "发布成功！");
                } else {
                    map.put("msg", "发布失败！");
                }
            } else {
                map.put("msg", "获取您的当前位置失败！");
            }
        } else {
            map.put("msg", "请勿重复发布打卡！");
        }
        String result = new JSONObject(map).toString();
        return result;
    }

    @RequestMapping("/show_attend")
    public String show_attend(@RequestBody Map o, User user, HttpServletRequest request) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("admin")) {
            return "redirect:to_login";
        }
        Map<String, Object> map = new HashMap<String, Object>();

        //获取课程信息
        int cid = (Integer) o.get("cid");
        Course coursedetail = courseService.getCourseByCid(cid);
        //判断是否已经发布打卡任务
        if (coursedetail.getcAtime() != null) {
/*            List<User> pList = attendService.getpstudent(cid);
            List<User> unpList = attendService.getunpstudent(cid);
            List<User> leList = attendService.getleaveList(cid);*/
        } else {
            map.put("msg", "尚未发布打卡任务");
        }
        String result = new JSONObject(map).toString();
        return result;
    }

    @RequestMapping("/leavelist")
    public String leavelist(User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("admin")) {
            return "redirect:to_login";
        }
        return "admin/leavelist";

    }

    @RequestMapping("/showleavelist")
    @ResponseBody
    public LayuiTableUtil<List> showleavelist(User user, HttpServletRequest request) {
        if (user == null) {
            return null;
        } else if (!user.getuRole().equals("admin")) {
            return null;
        }
        String uemail = user.getuEmail();
        int limit = Integer.parseInt(request.getParameter("limit").trim());//每页显示的记录数
        int page = Integer.parseInt(request.getParameter("page").trim());//当前显示的页码
        List<Leave> leaveList = attendService.getallleavebyuser(uemail, limit, page);
        int count = attendService.countallleave();

        //LayuiTableUtil 工具类封装了layui的数据表格的返回数据格式 countAdmin:为查询的记录总数
        LayuiTableUtil<List> list = new LayuiTableUtil<List>("", leaveList, 0, count);
        if (leaveList != null) {
            return list;
        }
        return null;
    }


    @PostMapping("/pub_leave")
    @ResponseBody
    public String pub_leave(User user, HttpServletRequest request) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("user")) {
            return "redirect:to_login";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        String lreason = request.getParameter("lreason");
        int cid = Integer.parseInt(request.getParameter("cid"));
        int uid = user.getuId();
        //todo 判断请假时间
        Timestamp qt = new Timestamp(System.currentTimeMillis());
        Timestamp ct = courseService.getCourseByCid(cid).getcBegintime();

        if (qt.before(ct)) {
            Leave leaveExsit = attendService.getleavebyids(uid, cid);
            if (leaveExsit == null) {
                int i = attendService.publeave(uid, cid, lreason);
                if (i == 1) {
                    map.put("msg", "提交请假成功！");
                } else {
                    map.put("msg", "提交请假失败！");
                }
            } else {
                map.put("msg", "请勿重复请假！");
            }
        } else {
            map.put("msg", "当前时间不可请假！");
        }
        String result = new JSONObject(map).toString();
        return result;
    }

    @PostMapping("/approveleave")
    @ResponseBody
    public String approveleave(User user, @RequestBody Map o) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("admin")) {
            return "redirect:to_login";
        }
        Map<String, Object> map = new HashMap<String, Object>();

        String status = "批准";
        int lid = (Integer) o.get("lid");
        Leave leavedetail = attendService.getleavebylid(lid);

        if (leavedetail.getlStatus().equals("")) {
            int i = attendService.approveleave(lid, status);
            if (i == 1) {
                map.put("msg", "已批准！");
            } else {
                map.put("msg", "审批失败！");
            }
        } else {
            map.put("msg", "请勿重复审批！");
        }

        String result = new JSONObject(map).toString();
        return result;
    }

    @PostMapping("/rejectleave")
    @ResponseBody
    public String rejectleave(User user, @RequestBody Map o) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("admin")) {
            return "redirect:to_login";
        }
        Map<String, Object> map = new HashMap<String, Object>();

        String status = "拒绝";
        int lid = (Integer) o.get("lid");
        Leave leavedetail = attendService.getleavebylid(lid);

        if (leavedetail.getlStatus().equals("")) {
            int i = attendService.approveleave(lid, status);
            if (i == 1) {
                map.put("msg", "已拒绝！");
            } else {
                map.put("msg", "审批失败！");
            }
        } else {
            map.put("msg", "请勿重复审批！");
        }

        String result = new JSONObject(map).toString();
        return result;
    }
}
