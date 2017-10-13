#
# Structure for table "h_user"
#

DROP TABLE IF EXISTS `h_user`;
CREATE TABLE `h_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `openid` varchar(64) DEFAULT NULL COMMENT '微信信息',
  `createtime` varchar(32) DEFAULT NULL COMMENT '关注时间',
  `nickname` varchar(128) DEFAULT NULL COMMENT '微信昵称',
  `IMGURL` varchar(128) DEFAULT NULL,
  `untime` varchar(32) DEFAULT NULL COMMENT '取消关注时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `openid` (`openid`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='用户关注表';

#
# Data for table "h_user"
#

INSERT INTO `h_user` VALUES (1,'123',NULL,'思思',NULL,NULL),(2,'oWSmDs-8vfuh4VzFiROA3aeDFg_8','2016-01-25 10:05:48',NULL,NULL,NULL),(5,'oaxT-s2rC50VXgRyJ6awutVvGiZ8','2016-03-14 19:04:14','宋凯',NULL,'2016-03-14 20:17:13'),(8,'oaxT-s8L5a1kwJRQrZncr6kE52fk','2016-03-14 20:16:57','转角*',NULL,'2016-06-24 18:44:20'),(11,'oaxT-sy1T4efegOUz6wTKOmxVmp0','2016-03-27 07:42:01','郭',NULL,'2016-03-27 07:51:47'),(12,'oaxT-s7cG1q8QzTMDsgLYjNfRLqA','2016-04-16 16:18:57','波赛东',NULL,'2016-04-26 22:02:08'),(13,'oaxT-szW7tkFJ82DSs54GGVPIX0I','2016-04-26 20:18:06','王智',NULL,NULL),(14,'oaxT-s-srywnvWuZaSAda7rabBIE','2016-05-05 21:01:52','青葫','http://wx.qlogo.cn/mmopen/6oyicKR1sib030GibacbleQS42rbibWrD5kgDS9YKsdjYOOQ7Xmbkra2KCkKn34ahnyoQHZZP0foR0lkMKd6nxPn5y6xC9Dta4U0/0',NULL),(15,'oaxT-s6sdTyD25GnsxrG8rsk4-tI','2016-05-09 16:51:30',NULL,NULL,NULL),(16,'oaxT-szS1zKHrEbrhqZgENR06p74','2016-05-09 16:56:51','刘竞波','http://wx.qlogo.cn/mmopen/4FAFTIibIFYI8qkXOq2bte7LKuAy32n72EBRPuty1PPHIsaIaKdNsABPkgEibNIKUyRTUIDlfh4ibys5ZZKTelpxQ/0','2016-06-16 19:14:16'),(17,'oaxT-s1QkyNDep5fojNYJjr2Qh5M','2016-05-09 17:03:09','陈','http://wx.qlogo.cn/mmopen/UdyoSHQMDKAOBq790BKia6HY46rX1eMh9fyY5Fo2JObW4PibMq5SlknQSMChdPMqgvzvAOkO8LSicxwrh6brRAtRqy05ibrvIoyF/0',NULL),(18,'oaxT-s3ecPR1w0MVFP0BsZt32yk0','2016-05-25 15:42:48',NULL,NULL,NULL),(19,'oaxT-szb9cgaG9H8NF8MGLqLW7ok','2016-05-26 16:23:47',NULL,NULL,NULL),(20,'oaxT-s_r-zyQHKxgUoiBubF659-Q','2016-05-31 15:42:51',NULL,NULL,NULL),(21,'oaxT-s8M8y_4GXftaRoBGoZC1izM','2016-06-12 18:46:56',NULL,NULL,'2016-08-27 00:22:01'),(22,'oaxT-s6SLo-MLTA0ESd86GMs7_7Q','2016-06-21 10:14:40','周元','http://wx.qlogo.cn/mmopen/PiajxSqBRaEKhnRLicjafaEOIR0ibuE8g1uFI5DEX4iaB3QerzKGOzKarhxdXlQDpswYIgDOlymQEPbYrgyJ6a5DFA/0',NULL),(23,'oaxT-s5_XGnGJm_QoFgZRLj3dHO4','2016-06-21 10:21:46',NULL,NULL,NULL),(24,'oaxT-s2gE4RvntF9rkJbs8RSKo-Q','2016-06-22 13:03:07',NULL,NULL,'2016-09-01 17:31:33');