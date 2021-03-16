DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `u_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `u_password` varchar(64) DEFAULT NULL COMMENT '密码',
  `u_email` varchar(255) DEFAULT NULL COMMENT '电子邮箱',
  `u_photo` varchar(50) NOT NULL DEFAULT '' COMMENT '教师照片',
  `u_role` varchar(50) DEFAULT NULL COMMENT '用户角色',
  `u_regtime` TIMESTAMP NOT NULL COMMENT '注册时间',
  `u_regip` varchar(50) DEFAULT NULL COMMENT '注册ip',
  `u_realname` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `u_phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `u_lastlogtime` varchar(50) DEFAULT NULL COMMENT '最后登录时间',
  `u_lastlogip` varchar(50) DEFAULT NULL COMMENT '最后登录ip',
  `u_sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `u_logcount` int(11) DEFAULT NULL COMMENT '登录次数',
  `u_del` int(3) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;