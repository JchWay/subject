package com.springboot.attendsys.controller;

import com.springboot.attendsys.util.LayuiTableUtil;
import com.springboot.attendsys.model.Course;
import com.springboot.attendsys.model.User;
import com.springboot.attendsys.redis.RedisService;
import com.springboot.attendsys.service.CourseService;
import com.springboot.attendsys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @RequestMapping("")
    public String manage(User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("admin")) {
            return "redirect:to_login";
        }
        model.addAttribute(user);
        return "admin/manage";
    }

    @PostMapping("/savecourse")
    @ResponseBody
    public String savecourse(@RequestParam("image") MultipartFile file, User user, HttpServletRequest request) throws FileNotFoundException {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("admin")) {
            return "redirect:to_login";
        }

        Map<String, Object> map = new HashMap<String, Object>();
        if (file.isEmpty()) {
            map.put("msg", "保存失败！");
        }
        String filename = file.getOriginalFilename();
        String path = "D:\\attStatic\\cover";
        System.out.println(path);
        File dir = new File(path + File.separator + filename);
        if (!dir.exists()) {
            dir.mkdir();
        }
        try {
            file.transferTo(dir);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("msg", "保存失败！");
        }

        String creater = user.getuName();
        String coursename = (String) request.getParameter("coursename");
        String cover = "/cover/" + filename;
        Timestamp bt = Timestamp.valueOf(request.getParameter("begintime"));
        Timestamp et = Timestamp.valueOf(request.getParameter("endtime"));

        int i = courseService.createCourse(creater, coursename, cover, bt, et);
        if (i == 1) {
            map.put("msg", "创建成功！");
        } else {
            map.put("msg", "创建失败！");  //并发现象 繁忙
        }
        String result = new JSONObject(map).toString();
        return result;
    }

    @RequestMapping("/profile")
    public String profile(User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("admin")) {
            return "redirect:to_login";
        }
        model.addAttribute(user);
        return "admin/profile";
    }

    @RequestMapping("/createcourse")
    public String createcourse(User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("admin")) {
            return "redirect:to_login";
        }
        model.addAttribute(user);
        return "admin/createcourse";
    }

    @RequestMapping("/coursedetail/{cid}")
    public String coursedetail(@PathVariable("cid") int cid, User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("admin")) {
            return "redirect:to_login";
        }
        Course coursedetail = courseService.getCourseByCid(cid);
        model.addAttribute("coursedetail", coursedetail);
        return "admin/coursedetail";
    }

    @RequestMapping("/studentdetail/{uid}")
    public String studentdetail(@PathVariable("uid") int uid, User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("admin")) {
            return "redirect:to_login";
        }
        User studentdetail = userService.getUserById(uid);
        model.addAttribute("studentdetail", studentdetail);
        return "admin/studentdetail";
    }

    @RequestMapping("/deletestudent")
    @ResponseBody
    public String deletestudent(@RequestBody Map o, User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("admin")) {
            return "redirect:to_login";
        }
        Map<String, Object> map = new HashMap<String, Object>();

        int uid = (Integer) o.get("uid");
        User userExsit = userService.getUserById(uid);
        if (userExsit != null) {
            int i = userService.deleteUserById(uid);
            if (i == 1) {
                map.put("msg", "删除成功！");
            } else {
                map.put("msg", "删除失败！");
            }
        } else {
            map.put("msg", "该生已被删除！");
        }

        String result = new JSONObject(map).toString();
        return result;
    }

    @RequestMapping("/deletecourse")
    @ResponseBody
    public String deletecourse(@RequestBody Map o, User user, Model model) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("admin")) {
            return "redirect:to_login";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        int cid = (Integer) o.get("cid");
        Course courseExsit = courseService.getCourseByCid(cid);
        if (courseExsit != null) {
            int i = courseService.deleteCourseById(cid);
            if (i == 1) {
                map.put("msg", "删除成功！");
            } else {
                map.put("msg", "删除失败！");
            }
        } else {
            map.put("msg", "该课程已被删除！");
        }
        String result = new JSONObject(map).toString();
        return result;
    }

    @RequestMapping("/studentlist")
    public String studentlist(User user, Model model, HttpServletRequest request) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("admin")) {
            return "redirect:to_login";
        }
        return "admin/studentlist";
    }

    @RequestMapping("/courselist")
    public String courselist(User user, Model model, HttpServletRequest request) {
        if (user == null) {
            return "redirect:to_login";
        } else if (!user.getuRole().equals("admin")) {
            return "redirect:to_login";
        }
        return "admin/courselist";
    }

    @RequestMapping("/showstudent")
    @ResponseBody()
    public LayuiTableUtil<List> showstudents(User user, Model model, HttpServletRequest request) {
        if (user == null) {
            return null;
        } else if (!user.getuRole().equals("admin")) {
            return null;
        }
        int limit = Integer.parseInt(request.getParameter("limit").trim());//每页显示的记录数
        int page = Integer.parseInt(request.getParameter("page").trim());//当前显示的页码
        List<User> userList = userService.getAllUser(limit, page);
        int count = userService.countAllUser();

        //LayuiTableUtil 工具类封装了layui的数据表格的返回数据格式 countAdmin:为查询的记录总数
        LayuiTableUtil<List> list = new LayuiTableUtil<List>("", userList, 0, count);
        if (userList != null) {
            return list;
        }
        return null;
    }

    @RequestMapping("/showcourse")
    @ResponseBody
    public LayuiTableUtil<List> showcourse(User user, HttpServletRequest request) {
        if (user == null) {
            return null;
        } else if (!user.getuRole().equals("admin")) {
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

}
