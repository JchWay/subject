package com.springboot.attendsys.redis;

public class UserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600*24 *2;//默认两天
    /**
     * 防止被外部类实例化
     */
    private UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 需要缓存的字段
     */
    public static UserKey token = new UserKey(TOKEN_EXPIRE,"token");
    public static UserKey getByEmail = new UserKey(0, "email");
}
