package com.springboot.attendsys.controller;

import com.springboot.attendsys.model.User;
import com.springboot.attendsys.service.UserService;
import com.springboot.attendsys.util.IpUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String jump(){
        return "redirect:to_login";
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/to_login")
    public String hello(){
        return "login";
    }

    @PostMapping("/do_login")
    @ResponseBody
    public String dologin(@RequestBody Map o, HttpServletRequest request, HttpServletResponse response){
        //获取登录用户ip地址
        String ipAddress = IpUtil.getIpAddr(request);
        //System.out.println(ipAddress);
        //获取用户登录时间
        Timestamp t = new Timestamp(System.currentTimeMillis());

        //解析json字符串
        String pwd = (String) o.get("password");
        String email = (String) o.get("email");

        Map<String, Object> map = new HashMap<String, Object>();
        //判断用户是否存在
        User userExist = userService.getByEmail(email);
        if(userExist != null){
            if(userExist.getuPassword().equals(pwd)){
                userService.login(response,userExist,t,ipAddress);
                if(userExist.getuRole().equals("admin")){
                    map.put("code",1);
                    map.put("msg","管理员登陆成功");
                } else {
                    map.put("code",2);
                    map.put("msg","用户登录成功");
                }
            } else{
                map.put("code",0);
                map.put("msg","密码错误");
            }
        } else{
            map.put("code",0);
            map.put("msg","用户未注册");
        }
        String result = new JSONObject(map).toString();
        return result;
    }

    @PostMapping("/do_regist")
    @ResponseBody
    public String doregist(@RequestBody Map o, HttpServletRequest request){
        //获取注册用户ip地址
        String ipAddress = IpUtil.getIpAddr(request);
        //获取用户注册时间
        Timestamp t = new Timestamp(System.currentTimeMillis());
        //解析json字符串
        String name = (String) o.get("username");
        String pwd = (String) o.get("password");
        String email = (String) o.get("email");

        Map<String, Object> map = new HashMap<String, Object>();
        //判断邮箱是否已被注册
        User userExist = userService.getByEmail(email);
        if (userExist == null) {
            User newUser = new User();
            newUser.setuName(name);
            newUser.setuPassword(pwd);
            newUser.setuEmail(email);
            newUser.setuRegip(ipAddress);
            newUser.setuRegtime(t);
            newUser.setuRole("user");
            /*插入数据，同步防止数据库出现多条邮箱一样的*/
            int i = userService.addUser(newUser);
            if (i == 1) {
                //TODO
                map.put("code",1);
                map.put("msg","注册成功！");
            } else {
                map.put("code",0);
                map.put("msg","注册失败！");  //并发现象 繁忙
            }
        } else {
            map.put("code",0);
            map.put("msg","该邮箱已被注册！");//该邮箱已被注册
        }
        String result = new JSONObject(map).toString();
        return result;
    }
}
