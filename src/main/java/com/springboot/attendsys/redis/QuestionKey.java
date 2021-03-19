package com.springboot.attendsys.redis;

import com.springboot.attendsys.model.Question;

public class QuestionKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600*24 *2;//默认两天
    /**
     * 防止被外部类实例化
     */
    private QuestionKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 需要缓存的字段
     */
    public static QuestionKey getByIds = new QuestionKey(0, "ids");
}
