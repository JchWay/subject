package com.springboot.attendsys.rabbitmq;

import com.springboot.attendsys.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangyunxiong on 2018/5/29.
 */
@Service
public class MQSender {

    private static Logger log = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendPunchMessage(PunchMessage message){
        String msg = RedisService.beanToString(message);
        log.info("send message:"+msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
    }
}
