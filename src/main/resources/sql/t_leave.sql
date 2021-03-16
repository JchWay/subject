DROP TABLE IF EXISTS `t_leave`;
CREATE TABLE `t_leave` (
  `l_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '请假编号',
  `u_id` int(11) NOT NULL COMMENT '学生id',
  `l_reason`varchar(200) NOT NULL DEFAULT '' COMMENT '请假原因',
  `l_status` varchar(10) NOT NULL DEFAULT '' COMMENT '批复状态',
  `l_content` varchar(10) NOT NULL DEFAULT '' COMMENT '批复内容',
  `l_time` TIMESTAMP NOT NULL COMMENT '请假时间',

  PRIMARY KEY (`l_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;