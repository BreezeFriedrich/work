# 创建数据库 swipe
CREATE DATABASE	swipe DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
# 使用数据库 swipe
USE swipe;

DROP TABLE IF EXISTS `SwipeRecord`;

# 创建数据表 SwipeRecord
CREATE TABLE `SwipeRecord` (
  `SEQ` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `deviceid` varchar(20) NOT NULL COMMENT '模块id',
  `deviceip` varchar(20) NOT NULL COMMENT '模块ip',
  `clientid` varchar(20) NOT NULL COMMENT '刷卡设备id',
  `clientip` varchar(20) NOT NULL COMMENT '刷卡设备ip',
  `result` tinyint(4) NOT NULL COMMENT '刷卡结果:0 刷卡成功,1 刷卡失败',
  `timestamp` varchar(20) NOT NULL COMMENT '时间戳',
  PRIMARY KEY (`SEQ`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='刷卡信息表';

# 创建数据用于测试
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000072','192.168.111.83','','49.65.203.223','0','2017/03/25 13:35:29');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000071','192.168.111.82','','49.65.203.223','0','2015/08/08 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000070','192.168.111.81','','49.65.203.223','0','2014/11/06 13:35:39');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000025','192.168.111.79','','49.65.203.223','0','2016/11/12 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000036','192.168.111.69','','49.65.203.223','0','2014/07/07 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000005','192.168.111.65','','49.65.203.223','0','2004/02/03 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000001','192.168.111.89','','49.65.203.223','0','2016/09/27 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000006','192.168.111.88','','49.65.203.223','0','2017/01/16 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000040','192.168.111.48','','49.65.203.223','0','2015/11/12 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000015','192.168.111.44','','49.65.203.223','0','2016/09/24 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000065','192.168.111.94','','49.65.203.223','1','2014/07/03 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000043','192.168.111.47','','49.65.203.223','0','2016/03/09 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000084','192.168.111.74','','49.65.203.223','1','2016/04/17 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000068','192.168.111.58','','49.65.203.223','1','2014/02/28 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000049','192.168.111.41','','49.65.203.223','1','2015/02/25 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000044','192.168.111.14','','49.65.203.223','0','2016/03/04 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000082','192.168.111.12','','49.65.203.223','0','2017/02/28 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000017','192.168.111.25','','49.65.203.223','1','2015/01/29 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000097','192.168.111.142','','49.65.203.223','0','2015/05/24 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000048','192.168.111.112','','49.65.203.223','0','2017/03/01 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000007','192.168.111.123','','49.65.203.223','0','2016/09/09 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000056','192.168.111.101','','49.65.203.223','0','2015/07/08 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000075','192.168.111.36','','49.65.203.223','1','2014/03/05 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000041','192.168.111.18','','49.65.203.223','0','2017/01/06 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000055','192.168.111.136','','49.65.203.223','0','2014/11/22 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000058','192.168.111.35','','49.65.203.223','1','2015/06/08 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000029','192.168.111.62','','49.65.203.223','0','2015/08/08 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000018','192.168.111.45','','49.65.203.223','0','2016/05/16 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000039','192.168.111.33','','49.65.203.223','0','2015/07/06 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000051','192.168.111.51','','49.65.203.223','0','2017/04/14 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000033','192.168.111.26','','49.65.203.223','1','2017/05/24 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000019','192.168.111.201','','49.65.203.223','0','2014/07/26 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000008','192.168.111.11','','49.65.203.223','0','2014/12/21 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000091','192.168.111.23','','49.65.203.223','0','2015/09/03 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000027','192.168.111.76','','49.65.203.223','1','2017/04/09 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000045','192.168.111.56','','49.65.203.223','0','2016/12/29 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000024','192.168.111.66','','49.65.203.223','0','2014/01/09 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000030','192.168.111.203','','49.65.203.223','0','2015/06/15 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000013','192.168.111.15','','49.65.203.223','1','2017/07/28 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000014','192.168.111.75','','49.65.203.223','0','2015/02/05 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000023','192.168.111.27','','49.65.203.223','0','2017/07/05 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000087','192.168.111.53','','49.65.203.223','1','2014/02/21 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000059','192.168.111.17','','49.65.203.223','0','2015/03/18 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000076','192.168.111.24','','49.65.203.223','1','2016/08/16 13:36:01');
INSERT INTO `SwipeRecord` VALUES (NULL,'SHTG1420151201000099','192.168.111.19','','49.65.203.223','0','2017/05/20 13:36:01');

INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000009','192.168.111.82','','49.65.203.223','0','2015/06/01 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000008','192.168.111.82','','49.65.203.223','0','2015/06/02 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000073','192.168.111.82','','49.65.203.223','0','2015/06/03 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000075','192.168.111.82','','49.65.203.223','0','2015/06/04 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000035','192.168.111.82','','49.65.203.223','0','2015/06/05 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000034','192.168.111.82','','49.65.203.223','0','2015/06/06 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000053','192.168.111.82','','49.65.203.223','0','2015/06/07 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000087','192.168.111.82','','49.65.203.223','0','2015/06/08 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000091','192.168.111.82','','49.65.203.223','0','2015/06/09 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000043','192.168.111.82','','49.65.203.223','0','2015/06/10 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000064','192.168.111.82','','49.65.203.223','0','2015/06/11 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000061','192.168.111.82','','49.65.203.223','0','2015/06/12 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000022','192.168.111.82','','49.65.203.223','0','2015/06/13 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000031','192.168.111.82','','49.65.203.223','0','2015/06/14 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000032','192.168.111.82','','49.65.203.223','0','2015/06/15 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000013','192.168.111.82','','49.65.203.223','0','2015/06/16 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000024','192.168.111.82','','49.65.203.223','0','2015/06/17 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000054','192.168.111.82','','49.65.203.223','0','2015/06/18 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000090','192.168.111.82','','49.65.203.223','0','2015/06/19 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000023','192.168.111.82','','49.65.203.223','0','2015/06/20 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000043','192.168.111.82','','49.65.203.223','0','2015/06/21 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000046','192.168.111.82','','49.65.203.223','0','2015/06/22 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000078','192.168.111.82','','49.65.203.223','0','2015/06/23 13:35:34');

INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000098','192.168.111.82','','49.65.203.223','1','2015/06/23 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000005','192.168.111.82','','49.65.203.223','1','2015/06/14 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000004','192.168.111.82','','49.65.203.223','1','2015/06/07 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000012','192.168.111.82','','49.65.203.223','1','2015/06/18 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000032','192.168.111.82','','49.65.203.223','1','2015/06/17 13:35:34');

INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000098','192.168.111.82','','49.65.203.223','0','2015/06/23 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000005','192.168.111.82','','49.65.203.223','0','2015/06/14 13:35:34');
INSERT INTO `SwipeRecord` VALUES (NULL ,'SHTG1420151201000004','192.168.111.82','','49.65.203.223','0','2015/06/07 13:35:34');


DROP TABLE IF EXISTS `DeviceStatus`;
# 创建数据表 DeviceStatus
CREATE TABLE `DeviceStatus`(
  `SEQ` INTEGER(11) PRIMARY KEY AUTO_INCREMENT comment '序号',
  `deviceid` VARCHAR(20) NOT NULL comment '模块id',
  `deviceip` VARCHAR(20) NOT NULL comment '模块ip',
  `status` INTEGER(11) NOT NULL comment '模块状态:0 正常,1 异常',
  `timestamp` VARCHAR(20) NOT NULL comment '时间戳',
  `info` VARCHAR(200) NOT NULL comment '',
  `reserved` CHAR(8) NOT NULL comment ''
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='模块状态';

# 创建数据用于测试
INSERT INTO `DeviceStatus` VALUES('1','SHTG1420151201000072','192.168.111.83','0','2015/12/10 13:35:29','','');
INSERT INTO `DeviceStatus` VALUES('2','SHTG1420151201000071','192.168.111.82','0','2015/12/10 13:35:34','','');
INSERT INTO `DeviceStatus` VALUES('3','SHTG1420151201000070','192.168.111.81','0','2015/12/10 13:35:39','','');
INSERT INTO `DeviceStatus` VALUES('4','SHTG1420151201000068','192.168.111.79','0','2015/12/10 13:36:01','','');

COMMIT;