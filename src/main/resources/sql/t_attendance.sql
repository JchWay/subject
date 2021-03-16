DROP TABLE IF EXISTS `t_attendence`;
CREATE TABLE `t_attendence` (
  `a_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '考勤编号',
  `c_id` int(11) NOT NULL COMMENT '课程编号',
  `u_id` int(11) NOT NULL COMMENT '学生id',
  `a_lon` double(10,6) DEFAULT NULL COMMENT '打卡经度',
  `a_la` double(10,6) DEFAULT NULL COMMENT '打卡维度',
  `a_type` varchar(50) DEFAULT NULL COMMENT '打卡类型',
  `a_time` TIMESTAMP NOT NULL COMMENT '打卡时间',

  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;