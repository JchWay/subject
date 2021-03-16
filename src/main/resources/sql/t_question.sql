DROP TABLE IF EXISTS `t_question`;
CREATE TABLE `t_question` (
  `q_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` varchar(20) NOT NULL DEFAULT '' COMMENT '提问学生id',
  `c_id` varchar(20) NOT NULL DEFAULT '' COMMENT '所属课程编号',
  `q_content` varchar(200) NOT NULL DEFAULT '' COMMENT '问题内容',
  `q_status` varchar(10) NOT NULL DEFAULT '' COMMENT '问题状态',
  `q_time` TIMESTAMP NOT NULL COMMENT '提问时间',
  `q_del` int(3) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`q_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;