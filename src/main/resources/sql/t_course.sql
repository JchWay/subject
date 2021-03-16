DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(50) NOT NULL DEFAULT '' COMMENT '课程名',
  `c_cover` varchar(50) NOT NULL DEFAULT '' COMMENT '课程封面',
  `c_file` varchar(50) NOT NULL DEFAULT '' COMMENT '课程课件',
  `c_creater` varchar(50) NOT NULL DEFAULT '' COMMENT '任课教师',
  `c_begintime` TIMESTAMP NOT NULL COMMENT '上课时间',
  `c_endtime` TIMESTAMP NOT NULL COMMENT '下课时间',

  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;