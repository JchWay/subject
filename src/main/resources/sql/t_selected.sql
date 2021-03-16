DROP TABLE IF EXISTS `t_selected`;
CREATE TABLE `t_selected` (
  `e_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '选课编号',
  `u_id` int(11) NOT NULL COMMENT '学生id',
  `c_id` int(11) NOT NULL COMMENT '课程编号',

  PRIMARY KEY (`e_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;