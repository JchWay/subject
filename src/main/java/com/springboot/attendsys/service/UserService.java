package com.springboot.attendsys.service;

import com.alibaba.druid.util.StringUtils;
import com.springboot.attendsys.mapper.UserMapper;
import com.springboot.attendsys.model.User;
import com.springboot.attendsys.redis.RedisService;
import com.springboot.attendsys.redis.UserKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisService redisService;

    public static final String COOKIE_NAME_TOKEN = "token";

    public int saveadmin(User user, String username, String photo, String sex, String password, String token) {
        String email = user.getuEmail();
        int i = userMapper.updateWhenSaveAdmin(email, username, password, photo, sex);
        User newUser = userMapper.getByEmail(email);
        if (newUser != null) {
            redisService.set(UserKey.token, "" + token, newUser);
        }
        return i;
    }

    public User getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get(UserKey.token, token, User.class);
        //延长有效期，有效期等于最后一次操作+有效期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    public String login(HttpServletResponse response, User user, Timestamp t, String ip) {
        String email = user.getuEmail();
        userMapper.updateWhenLog(email, t, ip);
        String token = UUID.randomUUID().toString().replace("-", "");
        addCookie(response, token, user);
        return token;
    }

    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    public User getByEmail(String email) {
        //取缓存对象
        User user = redisService.get(UserKey.getByEmail, "" + email, User.class);
        if (user != null) {
            return user;
        }
        //取数据库
        user = userMapper.getByEmail(email);
        //再存入缓存
        if (user != null) {
            redisService.set(UserKey.getByEmail, "" + email, user);
        }
        return user;
    }

    public User getUserById(int uid) {
        return userMapper.getById(uid);
    }

    public List<User> getAllUser(int pageSize, int pageNow) {
        List<User> list = userMapper.getAllUser((pageNow - 1) * pageSize, pageSize);
        return list;
    }

    public int countAllUser() {
        int count = userMapper.countAllUser();
        return count;
    }

    /**
     * 将token做为key，用户信息做为value 存入redis模拟session
     * 同时将token存入cookie，保存登录状态
     */
    public void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");//设置为网站根目录
        response.addCookie(cookie);
    }

    public int deleteUserById(int uid) {
        return userMapper.deleteUserById(uid);
    }
}