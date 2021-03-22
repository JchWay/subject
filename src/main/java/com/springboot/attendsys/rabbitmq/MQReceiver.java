package com.springboot.attendsys.rabbitmq;


import com.springboot.attendsys.model.Attendance;
import com.springboot.attendsys.model.Course;
import com.springboot.attendsys.model.User;
import com.springboot.attendsys.redis.RedisService;
import com.springboot.attendsys.service.AttendService;
import com.springboot.attendsys.service.CourseService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangyunxiong on 2018/5/29.
 */
@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);


    @Autowired
    RedisService redisService;

    @Autowired
    CourseService courseService;

    @Autowired
    AttendService attendService;

    @RabbitListener(queues=MQConfig.QUEUE)
    public void receive(String message){
        log.info("receive message:"+message);
        PunchMessage m = RedisService.stringToBean(message, PunchMessage.class);
        User user = m.getUser();
        int cId = m.getcId();
        int uId = user.getuId();

        attendService.punch(uId,cId);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info(" topic  queue1 message:" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" topic  queue2 message:" + message);
    }
}
