package com.springboot.attendsys.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.springboot.attendsys.model.Attendance;
import com.springboot.attendsys.model.Course;
import com.springboot.attendsys.model.Selected;
import com.springboot.attendsys.model.User;
import com.springboot.attendsys.rabbitmq.MQSender;
import com.springboot.attendsys.rabbitmq.PunchMessage;
import com.springboot.attendsys.redis.RedisService;
import com.springboot.attendsys.service.AttendService;
import com.springboot.attendsys.service.CourseService;
import com.springboot.attendsys.service.SelectedService;
import com.springboot.attendsys.service.UserService;
import com.springboot.attendsys.util.DistanceUtil;
import com.springboot.attendsys.util.LayuiTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SelectedService selectedService;
    @Autowired
    private AttendService attendService;
    @Autowired
    MQSender sender;
    //基于令牌桶算法的限流实现类,每秒限制运行50个线程
    RateLimiter rateLimiter = RateLimiter.create(50);

    @RequestMapping("")
    public String manage(User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("user")) {
            return "redirect:to_login";
        }
        model.addAttribute(user);
        return "user/user";
    }

    @PostMapping("/saveuser")
    @ResponseBody
    public String saveuser(@RequestParam("image") MultipartFile file, HttpServletRequest request, User user) {
        if (user == null) {
            return "redirect:to_login";
        }
        String name = request.getParameter("username");
        String pwd = request.getParameter("password");
        String sex = request.getParameter("sex");

        Map<String, Object> map = new HashMap<String, Object>();
        if (file.isEmpty()) {
            map.put("msg", "保存失败！");
            return new JSONObject(map).toString();
        }
        String filename = file.getOriginalFilename();
        String path = "D:\\attStatic\\photo";
        File dir = new File(path + File.separator + filename);
        if (!dir.exists()) {
            dir.mkdir();
        }
        try {
            file.transferTo(dir);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("msg", "保存失败！");
            return new JSONObject(map).toString();
        }

        String photo = "/photo/" + filename;
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        int i = userService.saveadmin(user, name, photo, sex, pwd, token);
        if (i == 1) {
            //TODO
            map.put("code", 1);
            map.put("msg", "保存成功！");
        } else {
            map.put("code", 0);
            map.put("msg", "保存失败！");
        }
        String result = new JSONObject(map).toString();
        return result;
    }

    @PostMapping("/punch")
    @ResponseBody
    public String punch(User user, HttpServletRequest request) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("user")) {
            return "redirect:to_login";
        }
    //public String punch(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            map.put("msg", "打卡高峰期！请稍后重试！");
            return new JSONObject(map).toString();
        }
        //判断是否接收到客户端经纬度，若未接收到则不执行下述逻辑
        if (request.getParameter("lon") != null && request.getParameter("la") != null) {
            double lon = Double.parseDouble(request.getParameter("lon"));
            double la = Double.parseDouble(request.getParameter("la"));
            //获取课程信息
            int cid = Integer.valueOf(request.getParameter("cid"));
            Course coursedetail = courseService.getCourseByCid(cid);
            //判断是否发布打卡
            if (coursedetail.getcLon() != null && coursedetail.getcLa() != null) {
                double tlon = coursedetail.getcLon();
                double tla = coursedetail.getcLa();
                //判断是否在打卡范围内
                if (DistanceUtil.GetDistance(tlon, tla, lon, la) <= 25) {
                    long pt = System.currentTimeMillis() / 1000;
                    long at = coursedetail.getcAtime().getTime() / 1000;
                    //判断是否在打卡时间内
                    if ((pt >= at) && (pt <= at + 36000)) {
                        //todo 测试环境需要修改了uid获取方式
                        //int uid = Integer.valueOf(request.getParameter("uid"));
                        int uid = user.getuId();
                        Attendance attendExsit = attendService.getattendbyids(uid, cid);
                        //判断重复打卡
                        if (attendExsit == null) {
                            PunchMessage message = new PunchMessage();
                            message.setuId(uid);
                            message.setcId(cid);
                            sender.sendPunchMessage(message);
                            map.put("msg", "已提交打卡！");
                        } else {
                            map.put("msg", "请勿重复打卡！");
                        }
                    } else {
                        map.put("msg", "当前时间无法打卡！");
                    }
                } else {
                    map.put("msg", "当前位置无法打卡！");
                }
            } else {
                map.put("msg", "尚未发布打卡任务！");
            }
        } else {
            map.put("msg", "获取您的当前位置失败！");
        }
        return new JSONObject(map).toString();
    }

    @RequestMapping("/profile")
    public String profile(User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("user")) {
            return "redirect:to_login";
        }
        model.addAttribute(user);
        return "user/profile";
    }

    @RequestMapping("/courselist")
    public String courselist(User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("user")) {
            return "redirect:to_login";
        }
        return "user/courselist";
    }

    @RequestMapping("/showcourse")
    @ResponseBody()
    public LayuiTableUtil<List> showcourse(User user, Model model, HttpServletRequest request) {
        if (user == null) {
            return null;
        } else if (!user.getuRole().equals("user")) {
            return null;
        }
        int limit = Integer.parseInt(request.getParameter("limit").trim());//每页显示的记录数
        int page = Integer.parseInt(request.getParameter("page").trim());//当前显示的页码
        List<Course> courseList = courseService.getAllCourse(limit, page);
        int count = courseService.countAllCourse();

        //LayuiTableUtil 工具类封装了layui的数据表格的返回数据格式 countAdmin:为查询的记录总数
        LayuiTableUtil<List> list = new LayuiTableUtil<List>("", courseList, 0, count);
        if (courseList != null) {
            return list;
        }
        return null;
    }

    @RequestMapping("/coursedetail/{cid}")
    public String coursedetail(@PathVariable("cid") int cid, User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("user")) {
            return "redirect:to_login";
        }
        Course coursedetail = courseService.getCourseByCid(cid);
        model.addAttribute("coursedetail", coursedetail);
        return "user/coursedetail";
    }

    @RequestMapping("/mycoursedetail/{cid}")
    public String mycoursedetail(@PathVariable("cid") int cid, User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("user")) {
            return "redirect:to_login";
        }
        Course coursedetail = courseService.getCourseByCid(cid);
        model.addAttribute("coursedetail", coursedetail);
        return "user/mycoursedetail";
    }

    @RequestMapping("/showmycourse")
    @ResponseBody()
    public LayuiTableUtil<List> selectedcourse(User user, Model model, HttpServletRequest request) {
        if (user == null) {
            return null;
        } else if (!user.getuRole().equals("user")) {
            return null;
        }
        int uid = user.getuId();
        int limit = Integer.parseInt(request.getParameter("limit").trim());//每页显示的记录数
        int page = Integer.parseInt(request.getParameter("page").trim());//当前显示的页码
        List<Course> courseList = courseService.getCourseBySelected(uid, limit, page);
        int count = courseService.countAllMyCourse(uid);

        //LayuiTableUtil 工具类封装了layui的数据表格的返回数据格式 countAdmin:为查询的记录总数
        LayuiTableUtil<List> list = new LayuiTableUtil<List>("", courseList, 0, count);
        if (courseList != null) {
            return list;
        }
        return null;
    }

    @RequestMapping("/selectedcourse")
    public String selectedcourse(User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("user")) {
            return "redirect:to_login";
        }

        return "user/selectedcourse";
    }

    @RequestMapping("/selectcourse")
    @ResponseBody
    public String selectcourse(@RequestBody Map o, User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("user")) {
            return "redirect:to_login";
        }
        Map<String, Object> map = new HashMap<String, Object>();

        int cid = (Integer) o.get("cid");
        int uid = user.getuId();
        //todo
        Timestamp st = new Timestamp(System.currentTimeMillis());
        Timestamp ct = courseService.getCourseByCid(cid).getcBegintime();

        if (st.before(ct)) {
            Selected selectedExist = selectedService.getByIds(cid, uid);
            if (selectedExist == null) {
                int i = courseService.selected(cid, uid);
                if (i == 1) {
                    //TODO
                    //选课成功
                    map.put("msg", "选课成功！");
                } else {
                    //繁忙
                    map.put("msg", "选课失败！");
                }
            } else {
                //已选择该课程
                map.put("msg", "您已选择该课程！");
            }
        } else {
            map.put("msg", "当前时间无法选择该课程！");
        }

        String result = new JSONObject(map).toString();
        return result;
    }

    @RequestMapping("/deleteselected")
    @ResponseBody
    public String deletecourse(@RequestBody Map o, User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("user")) {
            return "redirect:to_login";
        }
        Map<String, Object> map = new HashMap<String, Object>();

        int cid = (Integer) o.get("cid");
        int uid = user.getuId();

        Timestamp dt = new Timestamp(System.currentTimeMillis());
        Timestamp ct = courseService.getCourseByCid(cid).getcBegintime();

        if (dt.before(ct)) {
            Selected selectedExist = selectedService.getByIds(cid, uid);
            if (selectedExist != null) {
                int i = courseService.deleteselected(cid, uid);
                if (i == 1) {
                    //TODO
                    map.put("code", 1);
                    map.put("msg", "删除成功！");
                } else {
                    //繁忙
                    map.put("code", 0);
                    map.put("msg", "删除失败！");
                }
            } else {
                map.put("msg", "您未选择该课程！");
            }
        } else {
            map.put("msg", "当前时间无法删除该课程！");
        }

        String result = new JSONObject(map).toString();
        return result;
    }

}
