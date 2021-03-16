DROP TABLE IF EXISTS `t_answer`;
CREATE TABLE `t_answer` (
  `a_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '解答编号',
  `u_id` int(11) NOT NULL COMMENT '解答教师id',
  `c_id` int(11) NOT NULL COMMENT '所属课程编号',
  `a_content` varchar(200) NOT NULL DEFAULT '' COMMENT '解答内容',
  `a_time` TIMESTAMP NOT NULL COMMENT '提问时间',
  `a_del` int(3) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;